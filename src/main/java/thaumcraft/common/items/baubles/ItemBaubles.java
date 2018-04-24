package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import thaumcraft.api.items.*;
import baubles.api.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ItemBaubles extends ItemTCBase implements IBauble, IVisDiscountGear
{
    public ItemBaubles() {
        super("baubles", new String[] { "amulet_mundane", "ring_mundane", "girdle_mundane", "ring_apprentice", "amulet_fancy", "ring_fancy", "girdle_fancy" });
        this.field_77777_bU = 1;
        this.func_77656_e(0);
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        switch (itemstack.func_77952_i()) {
            case 1:
            case 3:
            case 5: {
                return BaubleType.RING;
            }
            case 2:
            case 6: {
                return BaubleType.BELT;
            }
            default: {
                return BaubleType.AMULET;
            }
        }
    }
    
    public EnumRarity func_77613_e(final ItemStack stack) {
        if (stack.func_77952_i() >= 3) {
            return EnumRarity.UNCOMMON;
        }
        return super.func_77613_e(stack);
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        if (stack.func_77952_i() == 3) {
            return 5;
        }
        return 0;
    }
}
