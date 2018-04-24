package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import java.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;

public class SealButcher implements ISeal, ISealGui, ISealConfigArea
{
    int delay;
    boolean wait;
    ResourceLocation icon;
    
    public SealButcher() {
        this.delay = new Random(System.nanoTime()).nextInt(200);
        this.wait = false;
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_butcher");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:butcher";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay++ % 200 != 0 || this.wait) {
            return;
        }
        final AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
        final List list = world.func_72872_a((Class)EntityLivingBase.class, area);
        if (list.size() > 0) {
            for (final Object e : list) {
                final EntityLivingBase target = (EntityLivingBase)e;
                if (this.isValidTarget(target)) {
                    final List var55 = world.func_72872_a((Class)target.getClass(), area);
                    Iterator var56;
                    int count;
                    EntityLivingBase var57;
                    for (var56 = var55.iterator(), count = 0; var56.hasNext() && count < 3; ++count) {
                        var57 = var56.next();
                        if (this.isValidTarget(var57)) {}
                    }
                    if (count > 2) {
                        final Task task = new Task(seal.getSealPos(), (Entity)target);
                        task.setPriority(seal.getPriority());
                        task.setLifespan((short)10);
                        TaskHandler.addTask(world.field_73011_w.getDimension(), task);
                        this.wait = true;
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    private boolean isValidTarget(final EntityLivingBase target) {
        return (target instanceof EntityAnimal || target instanceof IAnimals) && !(target instanceof IMob) && (!(target instanceof EntityTameable) || !((EntityTameable)target).func_70909_n()) && !(target instanceof EntityGolem) && (!(target instanceof EntityAnimal) || !((EntityAnimal)target).func_70631_g_());
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
        if (task.getEntity() != null && task.getEntity() instanceof EntityLivingBase && this.isValidTarget((EntityLivingBase)task.getEntity())) {
            ((EntityLiving)golem).func_70624_b((EntityLivingBase)task.getEntity());
            golem.addRankXp(1);
        }
        task.setSuspended(true);
        this.wait = false;
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        task.setSuspended(true);
        this.wait = false;
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        return true;
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
    public int[] getGuiCategories() {
        return new int[] { 2, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.FIGHTER, EnumGolemTrait.SMART };
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return null;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
        this.wait = false;
    }
    
    @Override
    public void readCustomNBT(final NBTTagCompound nbt) {
    }
    
    @Override
    public void writeCustomNBT(final NBTTagCompound nbt) {
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
        this.wait = false;
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
}
