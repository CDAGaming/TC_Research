package thaumcraft.common.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.api.aspects.*;

public class SlotLimitedHasAspects extends Slot
{
    public SlotLimitedHasAspects(final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
    }
    
    public boolean func_75214_a(final ItemStack stack1) {
        final AspectList al = ThaumcraftCraftingManager.getObjectTags(stack1);
        return al != null && al.size() > 0;
    }
}
