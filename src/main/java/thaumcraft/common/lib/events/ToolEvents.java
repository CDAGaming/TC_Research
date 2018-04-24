package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import thaumcraft.common.lib.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.lib.*;
import java.util.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.common.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.state.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.entities.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;

@Mod.EventBusSubscriber
public class ToolEvents
{
    static HashMap<Integer, EnumFacing> lastFaceClicked;
    public static HashMap<Integer, ArrayList<BlockPos>> blockedBlocks;
    static boolean blockDestructiveRecursion;
    
    @SubscribeEvent
    public static void playerAttack(final AttackEntityEvent event) {
        if (event.getEntityPlayer().func_184600_cs() == null) {
            return;
        }
        final ItemStack heldItem = event.getEntityPlayer().func_184586_b(event.getEntityPlayer().func_184600_cs());
        if (heldItem != null && !heldItem.func_190926_b()) {
            final List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
            if (list.contains(EnumInfusionEnchantment.ARCING) && event.getTarget().func_70089_S()) {
                final int rank = EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.ARCING);
                final List targets = event.getEntityPlayer().field_70170_p.func_72839_b((Entity)event.getEntityPlayer(), event.getTarget().func_174813_aQ().func_72314_b(1.5 + rank, (double)(1.0f + rank / 2.0f), 1.5 + rank));
                int count = 0;
                if (targets.size() > 1) {
                    for (int var9 = 0; var9 < targets.size(); ++var9) {
                        final Entity var10 = targets.get(var9);
                        if (!var10.field_70128_L) {
                            if (!EntityUtils.isFriendly(event.getEntity(), var10)) {
                                if (var10 instanceof EntityLiving && var10.func_145782_y() != event.getTarget().func_145782_y()) {
                                    if (!(var10 instanceof EntityPlayer) || ((EntityPlayer)var10).func_70005_c_() != event.getEntityPlayer().func_70005_c_()) {
                                        if (var10.func_70089_S()) {
                                            final float f = (float)event.getEntityPlayer().func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
                                            event.getEntityPlayer().func_70652_k(var10);
                                            if (var10.func_70097_a(DamageSource.func_76365_a(event.getEntityPlayer()), f * 0.5f)) {
                                                try {
                                                    if (var10 instanceof EntityLivingBase) {
                                                        EnchantmentHelper.func_151384_a((EntityLivingBase)var10, (Entity)event.getEntityPlayer());
                                                    }
                                                }
                                                catch (Exception ex) {}
                                                var10.func_70024_g((double)(-MathHelper.func_76126_a(event.getEntityPlayer().field_70177_z * 3.1415927f / 180.0f) * 0.5f), 0.1, (double)(MathHelper.func_76134_b(event.getEntityPlayer().field_70177_z * 3.1415927f / 180.0f) * 0.5f));
                                                ++count;
                                                if (!event.getEntityPlayer().field_70170_p.field_72995_K) {
                                                    PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXSlash(event.getTarget().func_145782_y(), var10.func_145782_y()), new NetworkRegistry.TargetPoint(event.getEntityPlayer().field_70170_p.field_73011_w.getDimension(), event.getTarget().field_70165_t, event.getTarget().field_70163_u, event.getTarget().field_70161_v, 64.0));
                                                }
                                            }
                                        }
                                    }
                                }
                                if (count >= rank) {
                                    break;
                                }
                            }
                        }
                    }
                    if (count > 0 && !event.getEntityPlayer().field_70170_p.field_72995_K) {
                        event.getEntityPlayer().func_184185_a(SoundsTC.wind, 1.0f, 0.9f + event.getEntityPlayer().field_70170_p.field_73012_v.nextFloat() * 0.2f);
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXSlash(event.getEntityPlayer().func_145782_y(), event.getTarget().func_145782_y()), new NetworkRegistry.TargetPoint(event.getEntityPlayer().field_70170_p.field_73011_w.getDimension(), event.getEntityPlayer().field_70165_t, event.getEntityPlayer().field_70163_u, event.getEntityPlayer().field_70161_v, 64.0));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void playerRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().field_72995_K && event.getEntityPlayer() != null) {
            final ItemStack heldItem = event.getEntityPlayer().func_184586_b(event.getEntityPlayer().func_184600_cs());
            if (heldItem != null && !heldItem.func_190926_b()) {
                final List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
                if (list.contains(EnumInfusionEnchantment.SOUNDING) && !event.getEntityPlayer().func_70093_af()) {
                    heldItem.func_77972_a(5, (EntityLivingBase)event.getEntityPlayer());
                    event.getWorld().func_184148_a((EntityPlayer)null, event.getPos().func_177958_n() + 0.5, event.getPos().func_177956_o() + 0.5, event.getPos().func_177952_p() + 0.5, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.2f, 0.2f + event.getWorld().field_73012_v.nextFloat() * 0.2f);
                    PacketHandler.INSTANCE.sendTo((IMessage)new PacketFXScanSource(event.getPos(), EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.SOUNDING)), (EntityPlayerMP)event.getEntityPlayer());
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void playerInteract(final PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntityPlayer() != null) {
            ToolEvents.lastFaceClicked.put(event.getEntityPlayer().func_145782_y(), event.getFace());
        }
    }
    
    public static void addBlockedBlock(final World world, final BlockPos pos) {
        if (!ToolEvents.blockedBlocks.containsKey(world.field_73011_w.getDimension())) {
            ToolEvents.blockedBlocks.put(world.field_73011_w.getDimension(), new ArrayList<BlockPos>());
        }
        final ArrayList<BlockPos> list = ToolEvents.blockedBlocks.get(world.field_73011_w.getDimension());
        if (!list.contains(pos)) {
            list.add(pos);
        }
    }
    
    public static void clearBlockedBlock(final World world, final BlockPos pos) {
        if (!ToolEvents.blockedBlocks.containsKey(world.field_73011_w.getDimension())) {
            ToolEvents.blockedBlocks.put(world.field_73011_w.getDimension(), new ArrayList<BlockPos>());
            return;
        }
        final ArrayList<BlockPos> list = ToolEvents.blockedBlocks.get(world.field_73011_w.getDimension());
        list.remove(pos);
    }
    
    @SubscribeEvent
    public static void breakBlockEvent(final BlockEvent.BreakEvent event) {
        if (ToolEvents.blockedBlocks.containsKey(event.getWorld().field_73011_w.getDimension())) {
            ArrayList<BlockPos> list = ToolEvents.blockedBlocks.get(event.getWorld().field_73011_w.getDimension());
            if (list == null) {
                list = new ArrayList<BlockPos>();
                ToolEvents.blockedBlocks.put(event.getWorld().field_73011_w.getDimension(), list);
            }
            if (list.contains(event.getPos())) {
                event.setCanceled(true);
            }
        }
        if (!event.getWorld().field_72995_K && event.getPlayer() != null) {
            final ItemStack heldItem = event.getPlayer().func_184586_b(event.getPlayer().func_184600_cs());
            if (heldItem != null && !heldItem.func_190926_b()) {
                final List<EnumInfusionEnchantment> list2 = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
                if (ForgeHooks.isToolEffective((IBlockAccess)event.getWorld(), event.getPos(), heldItem) && list2.contains(EnumInfusionEnchantment.BURROWING) && !event.getPlayer().func_70093_af() && isValidBurrowBlock(event.getWorld(), event.getPos())) {
                    event.setCanceled(true);
                    heldItem.func_77972_a(1, (EntityLivingBase)event.getPlayer());
                    BlockUtils.breakFurthestBlock(event.getWorld(), event.getPos(), event.getState(), event.getPlayer());
                }
            }
        }
    }
    
    private static boolean isValidBurrowBlock(final World world, final BlockPos pos) {
        return Utils.isWoodLog((IBlockAccess)world, pos) || Utils.isOreBlock(world, pos);
    }
    
    @SubscribeEvent
    public static void harvestBlockEvent(final BlockEvent.HarvestDropsEvent event) {
        if (!event.getWorld().field_72995_K && !event.isSilkTouching() && event.getState().func_177230_c() != null && ((event.getState().func_177230_c() == Blocks.field_150482_ag && event.getWorld().field_73012_v.nextFloat() < 0.05) || (event.getState().func_177230_c() == Blocks.field_150412_bA && event.getWorld().field_73012_v.nextFloat() < 0.075) || (event.getState().func_177230_c() == Blocks.field_150369_x && event.getWorld().field_73012_v.nextFloat() < 0.01) || (event.getState().func_177230_c() == Blocks.field_150365_q && event.getWorld().field_73012_v.nextFloat() < 0.001) || (event.getState().func_177230_c() == Blocks.field_150439_ay && event.getWorld().field_73012_v.nextFloat() < 0.01) || (event.getState().func_177230_c() == Blocks.field_150450_ax && event.getWorld().field_73012_v.nextFloat() < 0.01) || (event.getState().func_177230_c() == Blocks.field_150449_bY && event.getWorld().field_73012_v.nextFloat() < 0.01) || (event.getState().func_177230_c() == BlocksTC.oreAmber && event.getWorld().field_73012_v.nextFloat() < 0.05) || (event.getState().func_177230_c() == BlocksTC.oreQuartz && event.getWorld().field_73012_v.nextFloat() < 0.05))) {
            event.getDrops().add(new ItemStack(ItemsTC.nuggets, 1, 10));
        }
        if (!event.getWorld().field_72995_K && event.getHarvester() != null) {
            final ItemStack heldItem = event.getHarvester().func_184586_b(event.getHarvester().func_184600_cs());
            if (heldItem != null && !heldItem.func_190926_b()) {
                final List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
                if (event.isSilkTouching() || ForgeHooks.isToolEffective((IBlockAccess)event.getWorld(), event.getPos(), heldItem) || (heldItem.func_77973_b() instanceof ItemTool && ((ItemTool)heldItem.func_77973_b()).func_150893_a(heldItem, event.getState()) > 1.0f)) {
                    if (list.contains(EnumInfusionEnchantment.REFINING)) {
                        final int fortune = 1 + EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.REFINING);
                        final float chance = fortune * 0.125f;
                        boolean b = false;
                        for (int a = 0; a < event.getDrops().size(); ++a) {
                            final ItemStack is = event.getDrops().get(a);
                            final ItemStack smr = Utils.findSpecialMiningResult(is, chance, event.getWorld().field_73012_v);
                            if (!is.func_77969_a(smr)) {
                                event.getDrops().set(a, smr);
                                b = true;
                            }
                        }
                        if (b) {
                            event.getWorld().func_184133_a((EntityPlayer)null, event.getPos(), SoundEvents.field_187604_bf, SoundCategory.PLAYERS, 0.2f, 0.7f + event.getWorld().field_73012_v.nextFloat() * 0.2f);
                        }
                    }
                    if (!ToolEvents.blockDestructiveRecursion && list.contains(EnumInfusionEnchantment.DESTRUCTIVE) && !event.getHarvester().func_70093_af()) {
                        ToolEvents.blockDestructiveRecursion = true;
                        EnumFacing face = ToolEvents.lastFaceClicked.get(event.getHarvester().func_145782_y());
                        if (face == null) {
                            face = EnumFacing.func_190914_a(event.getPos(), (EntityLivingBase)event.getHarvester());
                        }
                        for (int aa = -1; aa <= 1; ++aa) {
                            for (int bb = -1; bb <= 1; ++bb) {
                                if (aa != 0 || bb != 0) {
                                    int xx = 0;
                                    int yy = 0;
                                    int zz = 0;
                                    if (face.ordinal() <= 1) {
                                        xx = aa;
                                        zz = bb;
                                    }
                                    else if (face.ordinal() <= 3) {
                                        xx = aa;
                                        yy = bb;
                                    }
                                    else {
                                        zz = aa;
                                        yy = bb;
                                    }
                                    final IBlockState bl = event.getWorld().func_180495_p(event.getPos().func_177982_a(xx, yy, zz));
                                    if (bl.func_185887_b(event.getWorld(), event.getPos().func_177982_a(xx, yy, zz)) >= 0.0f && (ForgeHooks.isToolEffective((IBlockAccess)event.getWorld(), event.getPos().func_177982_a(xx, yy, zz), heldItem) || (heldItem.func_77973_b() instanceof ItemTool && ((ItemTool)heldItem.func_77973_b()).func_150893_a(heldItem, bl) > 1.0f))) {
                                        heldItem.func_77972_a(1, (EntityLivingBase)event.getHarvester());
                                        BlockUtils.harvestBlock(event.getWorld(), event.getHarvester(), event.getPos().func_177982_a(xx, yy, zz));
                                    }
                                }
                            }
                        }
                        ToolEvents.blockDestructiveRecursion = false;
                    }
                    if (list.contains(EnumInfusionEnchantment.COLLECTOR) && !event.getHarvester().func_70093_af()) {
                        InventoryUtils.dropHarvestsAtPos(event.getWorld(), event.getPos(), event.getDrops(), true, 10, (Entity)event.getHarvester());
                        event.getDrops().clear();
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void fillBucket(final FillBucketEvent event) {
        if (event.getTarget() != null && event.getTarget().field_72313_a == RayTraceResult.Type.BLOCK) {
            final IBlockState bs = event.getWorld().func_180495_p(event.getTarget().func_178782_a());
            if (bs.func_177230_c() == BlocksTC.purifyingFluid && ((BlockFluidClassic)bs.func_177230_c()).isSourceBlock((IBlockAccess)event.getWorld(), event.getTarget().func_178782_a())) {
                event.getWorld().func_175698_g(event.getTarget().func_178782_a());
                event.setFilledBucket(new ItemStack(ItemsTC.bucketPure));
                event.setResult(Event.Result.ALLOW);
                return;
            }
            if (bs.func_177230_c() == BlocksTC.liquidDeath && ((BlockFluidClassic)bs.func_177230_c()).isSourceBlock((IBlockAccess)event.getWorld(), event.getTarget().func_178782_a())) {
                event.getWorld().func_175698_g(event.getTarget().func_178782_a());
                event.setFilledBucket(new ItemStack(ItemsTC.bucketDeath));
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
    
    @SubscribeEvent
    public static void livingDrops(final LivingDropsEvent event) {
        if (event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityPlayer) {
            final ItemStack heldItem = ((EntityPlayer)event.getSource().func_76346_g()).func_184586_b(((EntityPlayer)event.getSource().func_76346_g()).func_184600_cs());
            if (heldItem != null && !heldItem.func_190926_b()) {
                final List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
                if (list.contains(EnumInfusionEnchantment.COLLECTOR)) {
                    for (int a = 0; a < event.getDrops().size(); ++a) {
                        final EntityItem ei = event.getDrops().get(a);
                        final ItemStack is = ei.func_92059_d().func_77946_l();
                        final EntityItem nei = new EntityFollowingItem(event.getEntity().field_70170_p, ei.field_70165_t, ei.field_70163_u, ei.field_70161_v, is, event.getSource().func_76346_g(), 10);
                        nei.field_70159_w = ei.field_70159_w;
                        nei.field_70181_x = ei.field_70181_x;
                        nei.field_70179_y = ei.field_70179_y;
                        nei.func_174869_p();
                        ei.func_70106_y();
                        event.getDrops().set(a, nei);
                    }
                }
                if (list.contains(EnumInfusionEnchantment.ESSENCE)) {
                    final AspectList as = AspectHelper.getEntityAspects((Entity)event.getEntityLiving());
                    if (as != null && as.size() > 0) {
                        final AspectList aspects = as.copy();
                        final int q = EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.ESSENCE);
                        Aspect[] al = aspects.getAspects();
                        for (int b = (event.getEntity().field_70170_p.field_73012_v.nextInt(5) < q) ? 0 : 99; b < q && al != null && al.length > 0; b += 1 + event.getEntity().field_70170_p.field_73012_v.nextInt(2)) {
                            final Aspect aspect = al[event.getEntity().field_70170_p.field_73012_v.nextInt(al.length)];
                            if (aspects.getAmount(aspect) > 0) {
                                aspects.remove(aspect, 1);
                                final ItemStack stack = ThaumcraftApiHelper.makeCrystal(aspect);
                                if (list.contains(EnumInfusionEnchantment.COLLECTOR)) {
                                    event.getDrops().add(new EntityFollowingItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, stack, event.getSource().func_76346_g(), 10));
                                }
                                else {
                                    event.getDrops().add(new EntityItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, stack));
                                }
                                ++b;
                            }
                            al = aspects.getAspects();
                            if (event.getEntity().field_70170_p.field_73012_v.nextInt(q) == 0) {}
                        }
                    }
                }
            }
        }
    }
    
    static {
        ToolEvents.lastFaceClicked = new HashMap<Integer, EnumFacing>();
        ToolEvents.blockedBlocks = new HashMap<Integer, ArrayList<BlockPos>>();
        ToolEvents.blockDestructiveRecursion = false;
    }
}
