package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class ItemBathSalts extends ItemTCBase
{
    public ItemBathSalts() {
        super("bath_salts", new String[0]);
        this.func_77627_a(false);
    }
    
    public int getEntityLifespan(final ItemStack itemStack, final World world) {
        return 200;
    }
}
