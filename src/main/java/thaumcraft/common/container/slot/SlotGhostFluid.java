package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import net.minecraft.entity.player.*;

public class SlotGhostFluid extends SlotGhost
{
    public SlotGhostFluid(final IInventory par1iInventory, final int par2, final int par3, final int par4) {
        super(par1iInventory, par2, par3, par4);
    }
    
    @Override
    public int func_75219_a() {
        return 1;
    }
    
    public boolean func_75214_a(final ItemStack stack1) {
        return FluidUtil.getFluidHandler(stack1) != null;
    }
    
    @Override
    public boolean func_82869_a(final EntityPlayer par1EntityPlayer) {
        return false;
    }
}
