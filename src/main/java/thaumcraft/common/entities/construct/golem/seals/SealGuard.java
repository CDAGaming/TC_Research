package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.nbt.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;

public class SealGuard implements ISeal, ISealGui, ISealConfigArea
{
    int delay;
    protected ISealConfigToggles.SealToggle[] props;
    ResourceLocation icon;
    
    public SealGuard() {
        this.delay = new Random(System.nanoTime()).nextInt(22);
        this.props = new ISealConfigToggles.SealToggle[] { new ISealConfigToggles.SealToggle(true, "pmob", "golem.prop.mob"), new ISealConfigToggles.SealToggle(false, "panimal", "golem.prop.animal"), new ISealConfigToggles.SealToggle(false, "pplayer", "golem.prop.player") };
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_guard");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:guard";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay++ % 20 != 0) {
            return;
        }
        final AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
        final List list = world.func_72872_a((Class)EntityLivingBase.class, area);
        if (list.size() > 0) {
            for (final Object e : list) {
                final EntityLivingBase target = (EntityLivingBase)e;
                if (this.isValidTarget(target)) {
                    final Task task = new Task(seal.getSealPos(), (Entity)target);
                    task.setPriority(seal.getPriority());
                    task.setLifespan((short)10);
                    TaskHandler.addTask(world.field_73011_w.getDimension(), task);
                }
            }
        }
    }
    
    private boolean isValidTarget(final EntityLivingBase target) {
        boolean valid = false;
        if (this.props[0].value && (target instanceof IMob || target instanceof EntityMob)) {
            valid = true;
        }
        if (this.props[1].value && (target instanceof EntityAnimal || target instanceof IAnimals)) {
            valid = true;
        }
        if (this.props[2].value && FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W() && target instanceof EntityPlayer) {
            valid = true;
        }
        return valid;
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
        if (task.getEntity() != null && task.getEntity() instanceof EntityLivingBase && this.isValidTarget((EntityLivingBase)task.getEntity())) {
            ((EntityLiving)golem).func_70624_b((EntityLivingBase)task.getEntity());
            golem.addRankXp(1);
        }
        task.setSuspended(true);
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        return !golem.getGolemEntity().func_184191_r((Entity)task.getEntity());
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
        return new EnumGolemTrait[] { EnumGolemTrait.FIGHTER };
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return null;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
    }
    
    @Override
    public void readCustomNBT(final NBTTagCompound nbt) {
    }
    
    @Override
    public void writeCustomNBT(final NBTTagCompound nbt) {
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
}
