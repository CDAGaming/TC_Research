package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.item.*;
import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.golems.tasks.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;

public class SealEmpty extends SealFiltered
{
    int delay;
    int filterInc;
    HashMap<Integer, ItemStack> cache;
    ResourceLocation icon;
    protected ISealConfigToggles.SealToggle[] props;
    
    public SealEmpty() {
        this.delay = new Random(System.nanoTime()).nextInt(30);
        this.filterInc = 0;
        this.cache = new HashMap<Integer, ItemStack>();
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_empty");
        this.props = new ISealConfigToggles.SealToggle[] { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod"), new ISealConfigToggles.SealToggle(false, "pcycle", "golem.prop.cycle"), new ISealConfigToggles.SealToggle(false, "pleave", "golem.prop.leave") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:empty";
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
        if (this.delay++ % 20 != 0) {
            return;
        }
        final ItemStack stack = InventoryUtils.findFirstMatchFromFilter(this.getInv(this.filterInc), this.isBlacklist(), InventoryUtils.getItemHandlerAt(world, seal.getSealPos().pos, seal.getSealPos().face), seal.getSealPos().face, new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value), this.props[5].value);
        if (stack != null && !stack.func_190926_b()) {
            final Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
            task.setPriority(seal.getPriority());
            task.setLifespan((short)5);
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
            this.cache.put(task.getId(), stack);
        }
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        ItemStack stack = this.cache.get(task.getId());
        final int sa = InventoryUtils.countStackIn(world, task.getSealPos().pos, task.getSealPos().face, stack, new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value));
        if (stack != null && !stack.func_190926_b() && this.props[5].value && sa <= stack.func_190916_E()) {
            stack = stack.func_77946_l();
            stack.func_190920_e(sa - 1);
        }
        if (stack != null && !stack.func_190926_b() && golem.canCarry(stack, true)) {
            final ItemStack s = golem.holdItem(InventoryUtils.removeStackFrom(world, task.getSealPos().pos, task.getSealPos().face, stack.func_77946_l(), new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value), false));
            if (!s.func_190926_b()) {
                InventoryUtils.ejectStackAt(world, task.getSealPos().pos, task.getSealPos().face, s);
            }
            ((Entity)golem).func_184185_a(SoundEvents.field_187638_cR, 0.125f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            golem.swingArm();
        }
        this.cache.remove(task.getId());
        ++this.filterInc;
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        final ItemStack stack = this.cache.get(task.getId());
        return stack != null && !stack.func_190926_b() && golem.canCarry(stack, true);
    }
    
    @Override
    public boolean canPlaceAt(final World world, final BlockPos pos, final EnumFacing side) {
        final TileEntity te = world.func_175625_s(pos);
        return te != null && te instanceof IInventory;
    }
    
    public NonNullList<ItemStack> getInv(final int c) {
        return super.getInv();
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
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
        this.cache.remove(task.getId());
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
    }
}
