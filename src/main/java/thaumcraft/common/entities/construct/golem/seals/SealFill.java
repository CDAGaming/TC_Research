package thaumcraft.common.entities.construct.golem.seals;

import java.util.*;
import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraftforge.items.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;

public class SealFill extends SealFiltered
{
    int delay;
    int watchedTask;
    protected ISealConfigToggles.SealToggle[] props;
    ResourceLocation icon;
    
    public SealFill() {
        this.delay = new Random(System.nanoTime()).nextInt(50);
        this.watchedTask = Integer.MIN_VALUE;
        this.props = new ISealConfigToggles.SealToggle[] { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod"), new ISealConfigToggles.SealToggle(false, "pexist", "golem.prop.exist") };
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_fill");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:fill";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay++ % 20 != 0) {
            return;
        }
        final Task oldTask = TaskHandler.getTask(world.field_73011_w.getDimension(), this.watchedTask);
        if (oldTask == null || oldTask.isReserved() || oldTask.isSuspended() || oldTask.isCompleted()) {
            final Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
            task.setPriority(seal.getPriority());
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
            this.watchedTask = task.getId();
        }
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
        final ISealEntity se = SealHandler.getSealEntity(world.field_73011_w.getDimension(), task.getSealPos());
        if (se != null && !se.isStoppedByRedstone(world)) {
            final Task newTask = new Task(task.getSealPos(), task.getSealPos().pos);
            newTask.setPriority(se.getPriority());
            TaskHandler.addTask(world.field_73011_w.getDimension(), newTask);
            this.watchedTask = newTask.getId();
        }
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        final InventoryUtils.InvFilter filter = new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
        final Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(this.getInv(), this.isBlacklist(), golem.getCarrying(), filter);
        if (tuple.func_76341_a() != null && !((ItemStack)tuple.func_76341_a()).func_190926_b()) {
            final IItemHandler inv = InventoryUtils.getItemHandlerAt(world, task.getSealPos().pos, task.getSealPos().face);
            int limit = ((ItemStack)tuple.func_76341_a()).func_190916_E();
            if (this.hasStacksizeLimiters() && tuple.func_76340_b() != null && ((ItemStack)tuple.func_76340_b()).func_190916_E() > 0) {
                final int c = (inv == null) ? InventoryUtils.countStackInWorld(golem.getGolemWorld(), task.getSealPos().pos, (ItemStack)tuple.func_76340_b(), 1.5, filter) : InventoryUtils.countStackIn(inv, (ItemStack)tuple.func_76340_b(), filter);
                if (c < ((ItemStack)tuple.func_76340_b()).func_190916_E()) {
                    limit = ((ItemStack)tuple.func_76340_b()).func_190916_E() - c;
                }
                else {
                    limit = 0;
                }
            }
            if (limit > 0) {
                final ItemStack t = ((ItemStack)tuple.func_76341_a()).func_77946_l();
                t.func_190920_e(limit);
                final ItemStack s = golem.dropItem(t);
                if (inv == null) {
                    final EntityItem entityItem;
                    final EntityItem ie = entityItem = new EntityItem(world, task.getSealPos().pos.func_177958_n() + 0.5 + task.getSealPos().face.func_82601_c(), task.getSealPos().pos.func_177956_o() + 0.5 + task.getSealPos().face.func_96559_d(), task.getSealPos().pos.func_177952_p() + 0.5 + task.getSealPos().face.func_82599_e(), s);
                    entityItem.field_70159_w /= 5.0;
                    final EntityItem entityItem2 = ie;
                    entityItem2.field_70181_x /= 2.0;
                    final EntityItem entityItem3 = ie;
                    entityItem3.field_70179_y /= 5.0;
                    world.func_72838_d((Entity)ie);
                }
                else {
                    golem.holdItem(ItemHandlerHelper.insertItemStacked(inv, s, false));
                }
                ((Entity)golem).func_184185_a(SoundEvents.field_187638_cR, 0.125f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 1.0f);
                golem.addRankXp(1);
                golem.swingArm();
            }
        }
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        final InventoryUtils.InvFilter filter = new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
        final Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(this.getInv(), this.isBlacklist(), golem.getCarrying(), filter);
        if (tuple.func_76341_a() != null && !((ItemStack)tuple.func_76341_a()).func_190926_b()) {
            final IItemHandler inv = InventoryUtils.getItemHandlerAt(golem.getGolemWorld(), task.getSealPos().pos, task.getSealPos().face);
            if (inv != null) {
                if (tuple.func_76341_a() != null && !((ItemStack)tuple.func_76341_a()).func_190926_b() && this.props[4].value && InventoryUtils.countStackIn(inv, (ItemStack)tuple.func_76341_a(), filter) <= 0) {
                    return false;
                }
                if (tuple.func_76341_a() != null && !((ItemStack)tuple.func_76341_a()).func_190926_b() && InventoryUtils.hasRoomFor(golem.getGolemWorld(), task.getSealPos().pos, task.getSealPos().face, (ItemStack)tuple.func_76341_a())) {
                    if (!this.hasStacksizeLimiters() || tuple.func_76340_b() == null || ((ItemStack)tuple.func_76340_b()).func_190916_E() <= 0) {
                        return true;
                    }
                    if (InventoryUtils.countStackIn(inv, (ItemStack)tuple.func_76340_b(), filter) < ((ItemStack)tuple.func_76340_b()).func_190916_E()) {
                        return true;
                    }
                }
            }
            else if (tuple.func_76341_a() != null && !((ItemStack)tuple.func_76341_a()).func_190926_b()) {
                return !this.hasStacksizeLimiters() || tuple.func_76340_b() == null || ((ItemStack)tuple.func_76340_b()).func_190916_E() <= 0 || InventoryUtils.countStackInWorld(golem.getGolemWorld(), task.getSealPos().pos, (ItemStack)tuple.func_76340_b(), 1.5, filter) < ((ItemStack)tuple.func_76340_b()).func_190916_E();
            }
        }
        return false;
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
        return new int[] { 1, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return null;
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.CLUMSY };
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
    }
    
    @Override
    public boolean hasStacksizeLimiters() {
        return !this.isBlacklist();
    }
}
