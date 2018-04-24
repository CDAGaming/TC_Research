package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.util.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.*;

public class SealLumber implements ISeal, ISealGui, ISealConfigArea
{
    int delay;
    HashMap<Integer, Long> cache;
    ResourceLocation icon;
    
    public SealLumber() {
        this.delay = new Random(System.nanoTime()).nextInt(33);
        this.cache = new HashMap<Integer, Long>();
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_lumber");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:lumber";
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
        if (!this.cache.containsValue(p.func_177986_g()) && Utils.isWoodLog((IBlockAccess)world, p)) {
            final Task task = new Task(seal.getSealPos(), p);
            task.setPriority(seal.getPriority());
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
            this.cache.put(task.getId(), p.func_177986_g());
        }
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        if (this.cache.containsKey(task.getId()) && Utils.isWoodLog((IBlockAccess)world, task.getPos())) {
            final FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
            fp.func_70107_b(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u, golem.getGolemEntity().field_70161_v);
            final IBlockState bs = world.func_180495_p(task.getPos());
            golem.swingArm();
            if (BlockUtils.breakFurthestBlock(world, task.getPos(), bs, (EntityPlayer)fp)) {
                task.setLifespan((short)Math.max(task.getLifespan(), 10L));
                golem.addRankXp(1);
                return false;
            }
            this.cache.remove(task.getId());
        }
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        if (this.cache.containsKey(task.getId()) && Utils.isWoodLog((IBlockAccess)golem.getGolemWorld(), task.getPos())) {
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
    public void readCustomNBT(final NBTTagCompound nbt) {
    }
    
    @Override
    public void writeCustomNBT(final NBTTagCompound nbt) {
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
        return new int[] { 2, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.BREAKER, EnumGolemTrait.SMART };
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return null;
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
    }
}
