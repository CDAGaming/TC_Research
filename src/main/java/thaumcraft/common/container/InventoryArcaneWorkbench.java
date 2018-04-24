package thaumcraft.common.container;

import thaumcraft.api.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;

public class InventoryArcaneWorkbench extends InventoryCrafting implements IArcaneWorkbench
{
    TileEntity workbench;
    
    public InventoryArcaneWorkbench(final TileEntity tileEntity, final Container container) {
        super(container, 5, 3);
        this.workbench = tileEntity;
    }
    
    public String func_70005_c_() {
        return "container.arcaneworkbench";
    }
    
    public void func_70296_d() {
        super.func_70296_d();
        this.workbench.func_70296_d();
    }
}
