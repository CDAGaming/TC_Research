package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.*;

public class SealBreaker extends SealFiltered implements ISealConfigArea, ISealConfigToggles
{
    int delay;
    HashMap<Integer, Long> cache;
    ResourceLocation icon;
    protected SealToggle[] props;
    
    public SealBreaker() {
        this.delay = new Random(System.nanoTime()).nextInt(42);
        this.cache = new HashMap<Integer, Long>();
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_breaker");
        this.props = new SealToggle[] { new SealToggle(true, "pmeta", "golem.prop.meta") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:breaker";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay % 100 == 0) {
            final Iterator<Integer> it = this.cache.keySet().iterator();
            while (it.hasNext()) {
                final Task t = TaskHandler.getTask(world.field_73011_w.getDimension(), it.next());
                if (t == null) {
                    it.remove();
                }
            }
        }
        ++this.delay;
        final BlockPos p = GolemHelper.getPosInArea(seal, this.delay);
        if (!this.cache.containsValue(p.func_177986_g()) && this.isValidBlock(world, p)) {
            final Task task = new Task(seal.getSealPos(), p);
            task.setPriority(seal.getPriority());
            task.setData((int)(world.func_180495_p(p).func_185887_b(world, p) / 3.0f));
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
            this.cache.put(task.getId(), p.func_177986_g());
        }
    }
    
    private boolean isValidBlock(final World world, final BlockPos p) {
        final IBlockState bs = world.func_180495_p(p);
        if (!world.func_175623_d(p) && bs.func_185887_b(world, p) >= 0.0f) {
            final ItemStack ts = this.getFilterSlot(0);
            if (ts != null && !ts.func_190926_b()) {
                ItemStack fs = BlockUtils.getSilkTouchDrop(bs);
                if (fs == null || !fs.func_190926_b()) {
                    fs = new ItemStack(bs.func_177230_c(), 1, this.getToggles()[0].value ? bs.func_177230_c().func_176201_c(bs) : 32767);
                }
                if (!this.getToggles()[0].value) {
                    fs.func_77964_b(32767);
                }
                if (this.isBlacklist()) {
                    if (OreDictionary.itemMatches(fs, this.getFilterSlot(0), this.getToggles()[0].value)) {
                        return false;
                    }
                }
                else if (!OreDictionary.itemMatches(fs, this.getFilterSlot(0), this.getToggles()[0].value)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        final IBlockState bs = world.func_180495_p(task.getPos());
        if (this.cache.containsKey(task.getId()) && this.isValidBlock(world, task.getPos())) {
            final FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
            fp.func_70107_b(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u, golem.getGolemEntity().field_70161_v);
            golem.swingArm();
            if (task.getData() > 0) {
                final float bh = bs.func_185887_b(world, task.getPos()) / 3.0f;
                world.func_184133_a((EntityPlayer)null, task.getPos(), bs.func_177230_c().func_185467_w().func_185845_c(), SoundCategory.BLOCKS, (bs.func_177230_c().func_185467_w().func_185843_a() + 0.7f) / 8.0f, bs.func_177230_c().func_185467_w().func_185847_b() * 0.5f);
                BlockUtils.destroyBlockPartially(world, golem.getGolemEntity().func_145782_y(), task.getPos(), (int)(9.0f * (1.0f - task.getData() / bh)));
                task.setLifespan((short)Math.max(task.getLifespan(), 10L));
                task.setData(task.getData() - 1);
                return false;
            }
            BlockUtils.harvestBlock(world, (EntityPlayer)fp, task.getPos(), true, false, 0, true);
            golem.addRankXp(1);
            this.cache.remove(task.getId());
        }
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        if (this.cache.containsKey(task.getId()) && this.isValidBlock(golem.getGolemWorld(), task.getPos())) {
            return true;
        }
        task.setSuspended(true);
        return false;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
        this.cache.remove(task.getId());
    }
    
    @Override
    public boolean canPlaceAt(final World world, final BlockPos pos, final EnumFacing side) {
        return !world.func_175623_d(pos);
    }
    
    @Override
    public ResourceLocation getSealIcon() {
        return this.icon;
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
    }
    
    @Override
    public Object returnContainer(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseContainer(player.field_71071_by, world, seal);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Object returnGui(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseGUI(player.field_71071_by, world, seal);
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 2, 1, 3, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.BREAKER };
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return null;
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
    }
}
