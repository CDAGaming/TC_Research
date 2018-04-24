package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class SlotGhostFull extends Slot
{
    int limit;
    
    public SlotGhostFull(final IInventory par1iInventory, final int par2, final int par3, final int par4, final int par5) {
        super(par1iInventory, par2, par3, par4);
        this.limit = Integer.MAX_VALUE;
        this.limit = par5;
    }
    
    public SlotGhostFull(final IInventory par1iInventory, final int par2, final int par3, final int par4) {
        super(par1iInventory, par2, par3, par4);
        this.limit = Integer.MAX_VALUE;
    }
    
    public int func_75219_a() {
        return this.limit;
    }
    
    public boolean func_82869_a(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return false;
    }
}
