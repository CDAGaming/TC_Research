package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.util.*;
import thaumcraft.api.aspects.*;
import thaumcraft.client.fx.*;

public class TileRechargePedestal extends TileThaumcraftInventory implements IAspectContainer
{
    private static final int[] slots;
    int counter;
    
    public TileRechargePedestal() {
        super(1);
        this.counter = 0;
        this.syncedSlots = new int[] { 0 };
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1)).func_72314_b(2.0, 2.0, 2.0);
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        if (!this.func_145831_w().field_72995_K && this.counter++ % 10 == 0 && this.func_70301_a(0) != null && RechargeHelper.rechargeItem(this.func_145831_w(), this.func_70301_a(0), this.field_174879_c, null, 5) > 0.0f) {
            this.syncTile(false);
            this.func_70296_d();
            final ArrayList<Aspect> al = Aspect.getPrimalAspects();
            this.field_145850_b.func_175641_c(this.field_174879_c, this.func_145838_q(), 5, al.get(this.func_145831_w().field_73012_v.nextInt(al.size())).getColor());
        }
    }
    
    public void setInventorySlotContentsFromInfusion(final int par1, final ItemStack stack2) {
        this.func_70299_a(par1, stack2);
        this.func_70296_d();
        if (!this.field_145850_b.field_72995_K) {
            this.syncTile(false);
        }
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack) {
        return stack.func_77973_b() instanceof IRechargable;
    }
    
    @Override
    public int[] func_180463_a(final EnumFacing side) {
        return TileRechargePedestal.slots;
    }
    
    @Override
    public boolean func_180462_a(final int par1, final ItemStack stack, final EnumFacing par3) {
        return stack.func_77973_b() instanceof IRechargable;
    }
    
    @Override
    public boolean func_180461_b(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return true;
    }
    
    @Override
    public AspectList getAspects() {
        if (this.func_70301_a(0) != null && this.func_70301_a(0).func_77973_b() instanceof IRechargable) {
            final float c = RechargeHelper.getCharge(this.func_70301_a(0));
            return new AspectList().add(Aspect.ENERGY, Math.round(c));
        }
        return null;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
    }
    
    @Override
    public int addToContainer(final Aspect tag, final int amount) {
        return 0;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        return false;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        return false;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    @Override
    public int containerContains(final Aspect tag) {
        return 0;
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 5) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.visSparkle(this.field_174879_c.func_177958_n() + this.func_145831_w().field_73012_v.nextInt(3) - this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177984_a().func_177956_o() + this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177952_p() + this.func_145831_w().field_73012_v.nextInt(3) - this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177958_n(), this.field_174879_c.func_177984_a().func_177956_o(), this.field_174879_c.func_177952_p(), j);
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
    
    static {
        slots = new int[] { 0 };
    }
}
