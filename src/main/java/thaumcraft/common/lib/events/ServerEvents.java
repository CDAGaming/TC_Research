package thaumcraft.common.lib.events;

import java.text.*;
import com.google.common.base.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.eventhandler.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.common.tiles.devices.*;
import java.util.concurrent.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.config.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import thaumcraft.common.world.*;
import net.minecraftforge.fml.common.*;
import org.apache.logging.log4j.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import thaumcraft.api.aura.*;
import net.minecraft.item.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import thaumcraft.api.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import thaumcraft.common.entities.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.util.*;
import net.minecraft.entity.player.*;
import javax.annotation.*;

@Mod.EventBusSubscriber
public class ServerEvents
{
    long lastcheck;
    static HashMap<Integer, Integer> serverTicks;
    public static ConcurrentHashMap<Integer, AuraThread> auraThreads;
    DecimalFormat myFormatter;
    public static HashMap<Integer, LinkedBlockingQueue<BreakData>> breakList;
    public static HashMap<Integer, LinkedBlockingQueue<VirtualSwapper>> swapList;
    public static HashMap<Integer, ArrayList<ChunkPos>> chunksToGenerate;
    public static final Predicate<SwapperPredicate> DEFAULT_PREDICATE;
    private static HashMap<Integer, LinkedBlockingQueue<RunnableEntry>> serverRunList;
    private static LinkedBlockingQueue<RunnableEntry> clientRunList;
    
    public ServerEvents() {
        this.lastcheck = 0L;
        this.myFormatter = new DecimalFormat("#######.##");
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void clientWorldTick(final TickEvent.ClientTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        if (event.phase == TickEvent.Phase.END && !ServerEvents.clientRunList.isEmpty()) {
            final LinkedBlockingQueue<RunnableEntry> temp = new LinkedBlockingQueue<RunnableEntry>();
            while (!ServerEvents.clientRunList.isEmpty()) {
                final RunnableEntry current = ServerEvents.clientRunList.poll();
                if (current != null) {
                    if (current.delay > 0) {
                        final RunnableEntry runnableEntry = current;
                        --runnableEntry.delay;
                        temp.offer(current);
                    }
                    else {
                        try {
                            current.runnable.run();
                        }
                        catch (Exception ex) {}
                    }
                }
            }
            while (!temp.isEmpty()) {
                ServerEvents.clientRunList.offer(temp.poll());
            }
        }
    }
    
    @SubscribeEvent
    public static void worldTick(final TickEvent.WorldTickEvent event) {
        if (event.side == Side.CLIENT) {
            return;
        }
        final int dim = event.world.field_73011_w.getDimension();
        if (event.phase == TickEvent.Phase.START) {
            if (!ServerEvents.auraThreads.containsKey(dim) && AuraHandler.getAuraWorld(dim) != null) {
                final AuraThread at = new AuraThread(dim);
                final Thread thread = new Thread(at);
                thread.start();
                ServerEvents.auraThreads.put(dim, at);
            }
        }
        else {
            if (!ServerEvents.serverTicks.containsKey(dim)) {
                ServerEvents.serverTicks.put(dim, 0);
            }
            LinkedBlockingQueue<RunnableEntry> rlist = ServerEvents.serverRunList.get(dim);
            if (rlist == null) {
                ServerEvents.serverRunList.put(dim, rlist = new LinkedBlockingQueue<RunnableEntry>());
            }
            else if (!rlist.isEmpty()) {
                final LinkedBlockingQueue<RunnableEntry> temp = new LinkedBlockingQueue<RunnableEntry>();
                while (!rlist.isEmpty()) {
                    final RunnableEntry current = rlist.poll();
                    if (current != null) {
                        if (current.delay > 0) {
                            final RunnableEntry runnableEntry = current;
                            --runnableEntry.delay;
                            temp.offer(current);
                        }
                        else {
                            try {
                                current.runnable.run();
                            }
                            catch (Exception ex) {}
                        }
                    }
                }
                while (!temp.isEmpty()) {
                    rlist.offer(temp.poll());
                }
            }
            final int ticks = ServerEvents.serverTicks.get(dim);
            tickChunkRegeneration(event);
            tickBlockSwap(event.world);
            tickBlockBreak(event.world);
            final ArrayList<Integer[]> nbe = TileArcaneEar.noteBlockEvents.get(dim);
            if (nbe != null) {
                nbe.clear();
            }
            if (ticks % 20 == 0) {
                final CopyOnWriteArrayList<ChunkPos> dc = AuraHandler.dirtyChunks.get(dim);
                if (dc != null && dc.size() > 0) {
                    for (final ChunkPos pos : dc) {
                        event.world.func_175646_b(pos.func_180331_a(5, 5, 5), (TileEntity)null);
                    }
                    dc.clear();
                }
                if (AuraHandler.taintTrigger.containsKey(dim)) {
                    if (!ModConfig.CONFIG_MISC.wussMode) {
                        TaintEvents.taintEvent(event.world, AuraHandler.taintTrigger.get(dim));
                    }
                    AuraHandler.taintTrigger.remove(dim);
                }
                TaskHandler.clearSuspendedOrExpiredTasks(event.world);
            }
            SealHandler.tickSealEntities(event.world);
            ServerEvents.serverTicks.put(dim, ticks + 1);
        }
    }
    
    public static void tickChunkRegeneration(final TickEvent.WorldTickEvent event) {
        final int dim = event.world.field_73011_w.getDimension();
        int count = 0;
        ArrayList<ChunkPos> chunks = ServerEvents.chunksToGenerate.get(dim);
        if (chunks != null && chunks.size() > 0) {
            for (int a = 0; a < 10; ++a) {
                chunks = ServerEvents.chunksToGenerate.get(dim);
                if (chunks == null || chunks.size() <= 0) {
                    break;
                }
                ++count;
                final ChunkPos loc = chunks.get(0);
                final long worldSeed = event.world.func_72905_C();
                final Random fmlRandom = new Random(worldSeed);
                final long xSeed = fmlRandom.nextLong() >> 3;
                final long zSeed = fmlRandom.nextLong() >> 3;
                fmlRandom.setSeed(xSeed * loc.field_77276_a + zSeed * loc.field_77275_b ^ worldSeed);
                ThaumcraftWorldGenerator.INSTANCE.worldGeneration(fmlRandom, loc.field_77276_a, loc.field_77275_b, event.world, false);
                chunks.remove(0);
                ServerEvents.chunksToGenerate.put(dim, chunks);
            }
        }
        if (count > 0) {
            FMLCommonHandler.instance().getFMLLogger().log(Level.INFO, "[Thaumcraft] Regenerated " + count + " chunks. " + Math.max(0, chunks.size()) + " chunks left");
        }
    }
    
    private static void tickBlockSwap(final World world) {
        final int dim = world.field_73011_w.getDimension();
        final LinkedBlockingQueue<VirtualSwapper> queue = ServerEvents.swapList.get(dim);
        final LinkedBlockingQueue<VirtualSwapper> queue2 = new LinkedBlockingQueue<VirtualSwapper>();
        if (queue != null) {
            while (!queue.isEmpty()) {
                final VirtualSwapper vs = queue.poll();
                if (vs != null) {
                    final IBlockState bs = world.func_180495_p(vs.pos);
                    boolean allow = bs.func_185887_b(world, vs.pos) >= 0.0f;
                    if ((vs.source != null && vs.source instanceof IBlockState && vs.source != bs) || (vs.source != null && vs.source instanceof Material && vs.source != bs.func_185904_a())) {
                        allow = false;
                    }
                    if (vs.visCost > 0.0f && AuraHelper.getVis(world, vs.pos) < vs.visCost) {
                        allow = false;
                    }
                    if (!world.canMineBlockBody(vs.player, vs.pos) || !allow || (vs.target != null && !vs.target.func_190926_b() && vs.target.func_77969_a(new ItemStack(bs.func_177230_c(), 1, bs.func_177230_c().func_176201_c(bs)))) || ForgeEventFactory.onPlayerBlockPlace(vs.player, new BlockSnapshot(world, vs.pos, bs), EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled() || !vs.allowSwap.apply((Object)new SwapperPredicate(world, vs.player, vs.pos))) {
                        continue;
                    }
                    int slot = -1;
                    if (!vs.consumeTarget || vs.target == null || vs.target.func_190926_b()) {
                        slot = 1;
                    }
                    else {
                        slot = vs.player.field_71071_by.func_184429_b(vs.target);
                    }
                    if (vs.player.field_71075_bZ.field_75098_d) {
                        slot = 1;
                    }
                    boolean matches = false;
                    if (vs.source instanceof Material) {
                        matches = (bs.func_185904_a() == vs.source);
                    }
                    if (vs.source instanceof IBlockState) {
                        matches = (bs == vs.source);
                    }
                    if ((vs.source != null && !matches) || slot < 0) {
                        continue;
                    }
                    if (!vs.player.field_71075_bZ.field_75098_d) {
                        if (vs.consumeTarget) {
                            vs.player.field_71071_by.func_70298_a(slot, 1);
                        }
                        if (vs.pickup) {
                            List<ItemStack> ret = new ArrayList<ItemStack>();
                            if (vs.silk && bs.func_177230_c().canSilkHarvest(world, vs.pos, bs, vs.player)) {
                                final ItemStack itemstack = BlockUtils.getSilkTouchDrop(bs);
                                if (itemstack != null && !itemstack.func_190926_b()) {
                                    ret.add(itemstack);
                                }
                            }
                            else {
                                ret = (List<ItemStack>)bs.func_177230_c().getDrops((IBlockAccess)world, vs.pos, bs, vs.fortune);
                            }
                            if (ret.size() > 0) {
                                for (final ItemStack is : ret) {
                                    if (!vs.player.field_71071_by.func_70441_a(is)) {
                                        world.func_72838_d((Entity)new EntityItem(world, vs.pos.func_177958_n() + 0.5, vs.pos.func_177956_o() + 0.5, vs.pos.func_177952_p() + 0.5, is));
                                    }
                                }
                            }
                        }
                        if (vs.visCost > 0.0f) {
                            ThaumcraftApi.internalMethods.drainVis(world, vs.pos, vs.visCost, false);
                        }
                    }
                    if (vs.target == null || vs.target.func_190926_b()) {
                        world.func_175698_g(vs.pos);
                    }
                    else {
                        final Block tb = Block.func_149634_a(vs.target.func_77973_b());
                        if (tb != null && tb != Blocks.field_150350_a) {
                            world.func_180501_a(vs.pos, tb.func_176203_a(vs.target.func_77952_i()), 3);
                        }
                        else {
                            world.func_175698_g(vs.pos);
                            final EntitySpecialItem entityItem = new EntitySpecialItem(world, vs.pos.func_177958_n() + 0.5, vs.pos.func_177956_o() + 0.1, vs.pos.func_177952_p() + 0.5, vs.target.func_77946_l());
                            entityItem.field_70181_x = 0.0;
                            entityItem.field_70159_w = 0.0;
                            entityItem.field_70179_y = 0.0;
                            world.func_72838_d((Entity)entityItem);
                        }
                    }
                    if (vs.fx) {
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockBamf(vs.pos, vs.color, true, vs.fancy, null), new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), (double)vs.pos.func_177958_n(), (double)vs.pos.func_177956_o(), (double)vs.pos.func_177952_p(), 32.0));
                    }
                    if (vs.lifespan <= 0) {
                        continue;
                    }
                    for (int xx = -1; xx <= 1; ++xx) {
                        for (int yy = -1; yy <= 1; ++yy) {
                            for (int zz = -1; zz <= 1; ++zz) {
                                matches = false;
                                if (vs.source instanceof Material) {
                                    final IBlockState bb = world.func_180495_p(vs.pos.func_177982_a(xx, yy, zz));
                                    matches = (bb.func_177230_c().func_149688_o(bb) == vs.source);
                                }
                                if (vs.source instanceof IBlockState) {
                                    matches = (world.func_180495_p(vs.pos.func_177982_a(xx, yy, zz)) == vs.source);
                                }
                                if ((xx != 0 || yy != 0 || zz != 0) && matches && BlockUtils.isBlockExposed(world, vs.pos.func_177982_a(xx, yy, zz))) {
                                    queue2.offer(new VirtualSwapper(vs.pos.func_177982_a(xx, yy, zz), vs.source, vs.target, vs.consumeTarget, vs.lifespan - 1, vs.player, vs.fx, vs.fancy, vs.color, vs.pickup, vs.silk, vs.fortune, vs.allowSwap, vs.visCost));
                                }
                            }
                        }
                    }
                }
            }
            ServerEvents.swapList.put(dim, queue2);
        }
    }
    
    private static void tickBlockBreak(final World world) {
        final int dim = world.field_73011_w.getDimension();
        final LinkedBlockingQueue<BreakData> queue = ServerEvents.breakList.get(dim);
        final LinkedBlockingQueue<BreakData> queue2 = new LinkedBlockingQueue<BreakData>();
        if (queue != null) {
            while (!queue.isEmpty()) {
                final BreakData vs = queue.poll();
                if (vs != null) {
                    final IBlockState bs = world.func_180495_p(vs.pos);
                    if (bs == vs.source) {
                        if (vs.visCost > 0.0f && AuraHelper.getVis(world, vs.pos) < vs.visCost) {
                            continue;
                        }
                        if (!world.canMineBlockBody(vs.player, vs.pos) || bs.func_185887_b(world, vs.pos) < 0.0f) {
                            continue;
                        }
                        if (vs.fx) {
                            world.func_175715_c(vs.pos.hashCode(), vs.pos, (int)((1.0f - vs.durabilityCurrent / vs.durabilityMax) * 10.0f));
                        }
                        final BreakData breakData = vs;
                        breakData.durabilityCurrent -= vs.strength;
                        if (vs.durabilityCurrent <= 0.0f) {
                            BlockUtils.harvestBlock(world, vs.player, vs.pos, true, vs.silk, vs.fortune, false);
                            if (vs.fx) {
                                world.func_175715_c(vs.pos.hashCode(), vs.pos, -1);
                            }
                            if (vs.visCost <= 0.0f) {
                                continue;
                            }
                            ThaumcraftApi.internalMethods.drainVis(world, vs.pos, vs.visCost, false);
                        }
                        else {
                            queue2.offer(new BreakData(vs.strength, vs.durabilityCurrent, vs.durabilityMax, vs.pos, vs.source, vs.player, vs.fx, vs.silk, vs.fortune, vs.visCost));
                        }
                    }
                    else {
                        if (!vs.fx) {
                            continue;
                        }
                        world.func_175715_c(vs.pos.hashCode(), vs.pos, -1);
                    }
                }
            }
            ServerEvents.breakList.put(dim, queue2);
        }
    }
    
    public static void addSwapper(final World world, final BlockPos pos, final Object source, final ItemStack target, final boolean consumeTarget, final int life, final EntityPlayer player, final boolean fx, final boolean fancy, final int color, final boolean pickup, final boolean silk, final int fortune, final Predicate<SwapperPredicate> allowSwap, final float visCost) {
        final int dim = world.field_73011_w.getDimension();
        LinkedBlockingQueue<VirtualSwapper> queue = ServerEvents.swapList.get(dim);
        if (queue == null) {
            ServerEvents.swapList.put(dim, new LinkedBlockingQueue<VirtualSwapper>());
            queue = ServerEvents.swapList.get(dim);
        }
        queue.offer(new VirtualSwapper(pos, source, target, consumeTarget, life, player, fx, fancy, color, pickup, silk, fortune, allowSwap, visCost));
        ServerEvents.swapList.put(dim, queue);
    }
    
    public static void addBreaker(final World world, final BlockPos pos, final IBlockState source, final EntityPlayer player, final boolean fx, final boolean silk, final int fortune, final float str, final float durabilityCurrent, final float durabilityMax, final int delay, final float vis, final Runnable run) {
        final int dim = world.field_73011_w.getDimension();
        if (delay > 0) {
            addRunnableServer(world, new Runnable() {
                @Override
                public void run() {
                    ServerEvents.addBreaker(world, pos, source, player, fx, silk, fortune, str, durabilityCurrent, durabilityMax, 0, vis, run);
                }
            }, delay);
        }
        else {
            LinkedBlockingQueue<BreakData> queue = ServerEvents.breakList.get(dim);
            if (queue == null) {
                ServerEvents.breakList.put(dim, new LinkedBlockingQueue<BreakData>());
                queue = ServerEvents.breakList.get(dim);
            }
            queue.offer(new BreakData(str, durabilityCurrent, durabilityMax, pos, source, player, fx, silk, fortune, vis));
            ServerEvents.breakList.put(dim, queue);
            if (run != null) {
                run.run();
            }
        }
    }
    
    public static void addRunnableServer(final World world, final Runnable runnable, final int delay) {
        if (world.field_72995_K) {
            return;
        }
        LinkedBlockingQueue<RunnableEntry> rlist = ServerEvents.serverRunList.get(world.field_73011_w.getDimension());
        if (rlist == null) {
            ServerEvents.serverRunList.put(world.field_73011_w.getDimension(), rlist = new LinkedBlockingQueue<RunnableEntry>());
        }
        rlist.add(new RunnableEntry(runnable, delay));
    }
    
    public static void addRunnableClient(final World world, final Runnable runnable, final int delay) {
        if (!world.field_72995_K) {
            return;
        }
        ServerEvents.clientRunList.add(new RunnableEntry(runnable, delay));
    }
    
    static {
        ServerEvents.serverTicks = new HashMap<Integer, Integer>();
        ServerEvents.auraThreads = new ConcurrentHashMap<Integer, AuraThread>();
        ServerEvents.breakList = new HashMap<Integer, LinkedBlockingQueue<BreakData>>();
        ServerEvents.swapList = new HashMap<Integer, LinkedBlockingQueue<VirtualSwapper>>();
        ServerEvents.chunksToGenerate = new HashMap<Integer, ArrayList<ChunkPos>>();
        DEFAULT_PREDICATE = (Predicate)new Predicate<SwapperPredicate>() {
            public boolean apply(@Nullable final SwapperPredicate pred) {
                return true;
            }
        };
        ServerEvents.serverRunList = new HashMap<Integer, LinkedBlockingQueue<RunnableEntry>>();
        ServerEvents.clientRunList = new LinkedBlockingQueue<RunnableEntry>();
    }
    
    public static class BreakData
    {
        float strength;
        float durabilityCurrent;
        float durabilityMax;
        IBlockState source;
        BlockPos pos;
        EntityPlayer player;
        boolean fx;
        boolean silk;
        int fortune;
        float visCost;
        
        public BreakData(final float strength, final float durabilityCurrent, final float durabilityMax, final BlockPos pos, final IBlockState source, final EntityPlayer player, final boolean fx, final boolean silk, final int fortune, final float vis) {
            this.strength = 0.0f;
            this.durabilityCurrent = 1.0f;
            this.durabilityMax = 1.0f;
            this.player = null;
            this.strength = strength;
            this.source = source;
            this.pos = pos;
            this.player = player;
            this.fx = fx;
            this.silk = silk;
            this.fortune = fortune;
            this.durabilityCurrent = durabilityCurrent;
            this.durabilityMax = durabilityMax;
            this.visCost = vis;
        }
    }
    
    public static class SwapperPredicate
    {
        public World world;
        public EntityPlayer player;
        public BlockPos pos;
        
        public SwapperPredicate(final World world, final EntityPlayer player, final BlockPos pos) {
            this.world = world;
            this.player = player;
            this.pos = pos;
        }
    }
    
    public static class VirtualSwapper
    {
        int color;
        boolean fancy;
        Predicate<SwapperPredicate> allowSwap;
        int lifespan;
        BlockPos pos;
        Object source;
        ItemStack target;
        EntityPlayer player;
        boolean fx;
        boolean silk;
        boolean pickup;
        boolean consumeTarget;
        int fortune;
        float visCost;
        
        VirtualSwapper(final BlockPos pos, final Object source, final ItemStack t, final boolean consumeTarget, final int life, final EntityPlayer p, final boolean fx, final boolean fancy, final int color, final boolean pickup, final boolean silk, final int fortune, final Predicate<SwapperPredicate> allowSwap, final float cost) {
            this.lifespan = 0;
            this.player = null;
            this.pos = pos;
            this.source = source;
            this.target = t;
            this.lifespan = life;
            this.player = p;
            this.consumeTarget = consumeTarget;
            this.fx = fx;
            this.fancy = fancy;
            this.allowSwap = allowSwap;
            this.silk = silk;
            this.fortune = fortune;
            this.pickup = pickup;
            this.color = color;
            this.visCost = cost;
        }
    }
    
    public static class RunnableEntry
    {
        Runnable runnable;
        int delay;
        
        public RunnableEntry(final Runnable runnable, final int delay) {
            this.runnable = runnable;
            this.delay = delay;
        }
    }
}
