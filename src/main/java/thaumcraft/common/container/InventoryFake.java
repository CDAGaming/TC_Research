package thaumcraft.common.container;

import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;

public class InventoryFake extends InventoryBasic
{
    public InventoryFake(final int size) {
        super("container.fake", false, size);
    }
    
    public InventoryFake(final NonNullList<ItemStack> inv) {
        super("container.fake", false, inv.size());
        for (int a = 0; a < inv.size(); ++a) {
            this.func_70299_a(a, (ItemStack)inv.get(a));
        }
    }
    
    public InventoryFake(final ItemStack... stacks) {
        super("container.fake", false, stacks.length);
        for (int a = 0; a < stacks.length; ++a) {
            this.func_70299_a(a, stacks[a]);
        }
    }
    
    public InventoryFake(final ArrayList<ItemStack> inv) {
        super("container.fake", false, inv.size());
        for (int a = 0; a < inv.size(); ++a) {
            this.func_70299_a(a, (ItemStack)inv.get(a));
        }
    }
}
