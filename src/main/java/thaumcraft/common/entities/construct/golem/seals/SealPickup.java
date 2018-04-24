package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.world.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.item.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;

public class SealPickup extends SealFiltered implements ISealConfigArea
{
    int delay;
    static HashMap<Integer, HashMap<Integer, Integer>> itemEntities;
    ResourceLocation icon;
    protected ISealConfigToggles.SealToggle[] props;
    
    public SealPickup() {
        this.delay = new Random(System.nanoTime()).nextInt(100);
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_pickup");
        this.props = new ISealConfigToggles.SealToggle[] { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:pickup";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay++ % 20 != 0) {
            return;
        }
        if (!SealPickup.itemEntities.containsKey(world.field_73011_w.getDimension())) {
            SealPickup.itemEntities.put(world.field_73011_w.getDimension(), new HashMap<Integer, Integer>());
        }
        final AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
        final List list = world.func_72872_a((Class)EntityItem.class, area);
        if (list.size() > 0) {
            for (final Object e : list) {
                final EntityItem ent = (EntityItem)e;
                if (ent != null && ent.field_70122_E && !ent.func_174874_s() && ent.func_92059_d() != null && !SealPickup.itemEntities.get(world.field_73011_w.getDimension()).containsValue(ent.func_145782_y())) {
                    final ItemStack stack = InventoryUtils.findFirstMatchFromFilter(this.filter, this.isBlacklist(), (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)ent.func_92059_d()), new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value));
                    if (stack != null && !stack.func_190926_b()) {
                        final Task task = new Task(seal.getSealPos(), (Entity)ent);
                        task.setPriority(seal.getPriority());
                        SealPickup.itemEntities.get(world.field_73011_w.getDimension()).put(task.getId(), ent.func_145782_y());
                        TaskHandler.addTask(world.field_73011_w.getDimension(), task);
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.delay % 100 != 0) {
            final HashMap<Integer, Integer> hm = SealPickup.itemEntities.get(world.field_73011_w.getDimension());
            final Iterator<Integer> it = hm.values().iterator();
            while (it.hasNext()) {
                final Entity e2 = world.func_73045_a((int)it.next());
                if (e2 != null) {
                    if (!e2.field_70128_L) {
                        continue;
                    }
                }
                try {
                    it.remove();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        if (!SealPickup.itemEntities.containsKey(world.field_73011_w.getDimension())) {
            SealPickup.itemEntities.put(world.field_73011_w.getDimension(), new HashMap<Integer, Integer>());
        }
        final EntityItem ei = this.getItemEntity(world, task);
        if (ei != null && !ei.func_92059_d().func_190926_b()) {
            final ItemStack stack = InventoryUtils.findFirstMatchFromFilter(this.filter, this.isBlacklist(), (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)ei.func_92059_d()), new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value));
            if (stack != null && !stack.func_190926_b()) {
                final ItemStack is = golem.holdItem(ei.func_92059_d());
                if (is != null && !is.func_190926_b() && is.func_190916_E() > 0) {
                    ei.func_92058_a(is);
                }
                if (is == null || is.func_190926_b() || is.func_190916_E() <= 0) {
                    ei.func_70106_y();
                }
                ((Entity)golem).func_184185_a(SoundEvents.field_187638_cR, 0.125f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                golem.swingArm();
            }
        }
        task.setSuspended(true);
        SealPickup.itemEntities.get(world.field_73011_w.getDimension()).remove(task.getId());
        return true;
    }
    
    protected EntityItem getItemEntity(final World world, final Task task) {
        final Integer ei = SealPickup.itemEntities.get(world.field_73011_w.getDimension()).get(task.getId());
        if (ei != null) {
            final Entity ent = world.func_73045_a((int)ei);
            if (ent != null && ent instanceof EntityItem) {
                return (EntityItem)ent;
            }
        }
        return null;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        final EntityItem ei = this.getItemEntity(golem.getGolemWorld(), task);
        if (ei == null || ei.func_92059_d() == null) {
            return false;
        }
        if (ei.field_70128_L) {
            task.setSuspended(true);
            return false;
        }
        return golem.canCarry(ei.func_92059_d(), true);
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
        return new int[] { 2, 1, 0, 4 };
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
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
    }
    
    static {
        SealPickup.itemEntities = new HashMap<Integer, HashMap<Integer, Integer>>();
    }
}
