package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import baubles.api.*;

public class ItemCharmUndying extends ItemTCBase implements IBauble
{
    public ItemCharmUndying() {
        super("charm_undying", new String[0]);
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.CHARM;
    }
}
