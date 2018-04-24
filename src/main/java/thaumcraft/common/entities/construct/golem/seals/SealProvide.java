package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraftforge.items.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import thaumcraft.common.entities.construct.golem.*;
import net.minecraft.entity.*;
import java.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;

public class SealProvide extends SealFiltered implements ISealConfigToggles
{
    int delay;
    HashMap<Integer, ProvisionRequest> cache;
    ResourceLocation icon;
    protected SealToggle[] props;
    
    public SealProvide() {
        this.delay = new Random(System.nanoTime()).nextInt(88);
        this.cache = new HashMap<Integer, ProvisionRequest>();
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_provider");
        this.props = new SealToggle[] { new SealToggle(true, "pmeta", "golem.prop.meta"), new SealToggle(true, "pnbt", "golem.prop.nbt"), new SealToggle(false, "pore", "golem.prop.ore"), new SealToggle(false, "pmod", "golem.prop.mod"), new SealToggle(false, "psing", "golem.prop.single"), new SealToggle(false, "pleave", "golem.prop.leave") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:provider";
    }
    
    @Override
    public int getFilterSize() {
        return 9;
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
        final IItemHandler inv = InventoryUtils.getItemHandlerAt(world, seal.getSealPos().pos, seal.getSealPos().face);
        if (inv != null && GolemHelper.provisionRequests.containsKey(world.field_73011_w.getDimension())) {
            final ListIterator<ProvisionRequest> it2 = GolemHelper.provisionRequests.get(world.field_73011_w.getDimension()).listIterator();
            while (it2.hasNext()) {
                final ProvisionRequest pr = it2.next();
                if ((pr.getSeal() != null && pr.getSeal().getSealPos().pos.func_177951_i((Vec3i)seal.getSealPos().pos) < 4096.0) || (pr.getEntity() != null && seal.getSealPos().pos.func_177954_c(pr.getEntity().field_70165_t, pr.getEntity().field_70163_u, pr.getEntity().field_70161_v) < 4096.0) || (pr.getPos() != null && seal.getSealPos().pos.func_177951_i((Vec3i)pr.getPos()) < 4096.0)) {
                    final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)pr.getStack());
                    if (!InventoryUtils.findFirstMatchFromFilter(this.getInv(), this.blacklist, stacks, new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value)).func_190926_b() && InventoryUtils.countStackIn(inv, pr.getStack(), InventoryUtils.InvFilter.STRICT) > (this.props[5].value ? 1 : 0)) {
                        final Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
                        task.setPriority((byte)((pr.getSeal() != null) ? pr.getSeal().getPriority() : 5));
                        task.setLifespan((short)((pr.getSeal() != null) ? 10 : 31000));
                        TaskHandler.addTask(world.field_73011_w.getDimension(), task);
                        this.cache.put(task.getId(), pr);
                        it2.remove();
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    public boolean matchesFilters(final ItemStack stack) {
        return InventoryUtils.matchesFilters(this.getInv(), this.blacklist, stack, new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value));
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        if (task.getData() == 0) {
            final IItemHandler inv = InventoryUtils.getItemHandlerAt(world, task.getSealPos().pos, task.getSealPos().face);
            if (inv != null) {
                ItemStack stack = null;
                try {
                    stack = this.cache.get(task.getId()).getStack().func_77946_l();
                }
                catch (Exception ex) {}
                if (stack != null && this.props[4].value) {
                    stack.func_190920_e(1);
                }
                int sa = 0;
                if (stack != null && !stack.func_190926_b() && this.props[5].value && (sa = InventoryUtils.countStackIn(inv, stack, InventoryUtils.InvFilter.STRICT)) <= stack.func_190916_E()) {
                    stack.func_190920_e(sa - 1);
                }
                if (stack != null && !stack.func_190926_b() && golem.canCarry(stack, true)) {
                    final ItemStack s = golem.holdItem(InventoryUtils.removeStackFrom(inv, stack.func_77946_l(), InventoryUtils.InvFilter.STRICT, false));
                    if (s != null && !s.func_190926_b()) {
                        InventoryUtils.ejectStackAt(world, task.getSealPos().pos, task.getSealPos().face, s);
                    }
                    ((Entity)golem).func_184185_a(SoundEvents.field_187638_cR, 0.125f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    golem.addRankXp(1);
                    golem.swingArm();
                    final ProvisionRequest pr2 = this.cache.remove(task.getId());
                    if (pr2.getEntity() != null || pr2.getPos() != null) {
                        Task task2 = null;
                        if (pr2.getEntity() != null) {
                            task2 = new Task(task.getSealPos(), pr2.getEntity());
                        }
                        else {
                            task2 = new Task(task.getSealPos(), pr2.getPos());
                        }
                        task2.setPriority(task.getPriority());
                        task2.setData((pr2.getEntity() != null) ? 1 : 2);
                        task2.setLifespan((short)31000);
                        TaskHandler.addTask(world.field_73011_w.getDimension(), task2);
                        this.cache.put(task2.getId(), pr2);
                    }
                }
            }
        }
        else if (this.cache.get(task.getId()) != null) {
            final ProvisionRequest pr3 = this.cache.get(task.getId());
            final ItemStack cs = pr3.getStack();
            final ItemStack s2 = golem.dropItem(cs);
            if (s2.func_190916_E() < cs.func_190916_E()) {
                final ItemStack ps = cs.func_77946_l();
                ps.func_190920_e(cs.func_190916_E() - s2.func_190916_E());
                if (task.getData() == 1) {
                    GolemHelper.requestProvisioning(world, pr3.getEntity(), ps);
                }
                else {
                    GolemHelper.requestProvisioning(world, pr3.getPos(), pr3.getSide(), ps);
                }
            }
            if (task.getData() == 1) {
                InventoryUtils.dropItemAtEntity(world, s2, pr3.getEntity());
            }
            else {
                InventoryUtils.ejectStackAt(world, pr3.getPos().func_177972_a(pr3.getSide()), pr3.getSide().func_176734_d(), s2);
            }
            ((Entity)golem).func_184185_a(SoundEvents.field_187638_cR, 0.125f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 1.0f);
            golem.swingArm();
            this.cache.remove(task.getId());
        }
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        final ProvisionRequest pr = this.cache.get(task.getId());
        final boolean b = pr != null && ((pr.getSeal() != null && ((EntityThaumcraftGolem)golem).func_180485_d(pr.getSeal().getSealPos().pos)) || (pr.getEntity() != null && ((EntityThaumcraftGolem)golem).func_180485_d(pr.getEntity().func_180425_c())) || (pr.getPos() != null && ((EntityThaumcraftGolem)golem).func_180485_d(pr.getPos())));
        if (task.getData() == 0) {
            return b && this.areGolemTagsValidForTask(pr.getSeal(), golem) && pr.getStack() != null && !golem.isCarrying(pr.getStack()) && golem.canCarry(pr.getStack(), true);
        }
        return b && this.areGolemTagsValidForTask(pr.getSeal(), golem) && pr.getStack() != null && golem.isCarrying(pr.getStack());
    }
    
    private boolean areGolemTagsValidForTask(final ISealEntity se, final IGolemAPI golem) {
        if (se == null) {
            return true;
        }
        if (se.isLocked() && !((IEntityOwnable)golem).func_184753_b().equals(se.getOwner())) {
            return false;
        }
        if (se.getSeal().getRequiredTags() != null && !golem.getProperties().getTraits().containsAll(Arrays.asList(se.getSeal().getRequiredTags()))) {
            return false;
        }
        if (se.getSeal().getForbiddenTags() != null) {
            for (final EnumGolemTrait tag : se.getSeal().getForbiddenTags()) {
                if (golem.getProperties().getTraits().contains(tag)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
        this.cache.remove(task.getId());
    }
    
    @Override
    public boolean canPlaceAt(final World world, final BlockPos pos, final EnumFacing side) {
        final TileEntity te = world.func_175625_s(pos);
        return te != null && te instanceof IInventory;
    }
    
    @Override
    public ResourceLocation getSealIcon() {
        return this.icon;
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 1, 3, 0, 4 };
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
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
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
