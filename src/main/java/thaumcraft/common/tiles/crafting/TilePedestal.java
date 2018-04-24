package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;

public class TilePedestal extends TileThaumcraftInventory
{
    public TilePedestal() {
        super(1);
        this.syncedSlots = new int[] { 0 };
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 2), (double)(this.func_174877_v().func_177952_p() + 1));
    }
    
    public void setInventorySlotContentsFromInfusion(final int par1, final ItemStack stack2) {
        this.func_70299_a(par1, stack2);
        this.func_70296_d();
        if (!this.field_145850_b.field_72995_K) {
            this.syncTile(false);
        }
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 11) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.drawBamf(this.field_174879_c.func_177984_a(), 0.75f, 0.0f, 0.5f, true, true, null);
            }
            return true;
        }
        if (i == 12) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.drawBamf(this.field_174879_c.func_177984_a(), true, true, null);
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
}
