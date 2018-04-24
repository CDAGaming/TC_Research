package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import net.minecraft.item.*;

public class ItemSanityChecker extends ItemTCBase
{
    public ItemSanityChecker() {
        super("sanity_checker", new String[0]);
        this.func_77625_d(1);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
}
