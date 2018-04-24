package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;

public class ItemEnchantmentPlaceholder extends ItemTCBase
{
    public ItemEnchantmentPlaceholder() {
        super("enchanted_placeholder", new String[0]);
        this.func_77625_d(1);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_77636_d(final ItemStack stack) {
        return true;
    }
    
    public boolean func_77616_k(final ItemStack stack) {
        return false;
    }
    
    public EnumRarity func_77613_e(final ItemStack stack) {
        return EnumRarity.RARE;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        tooltip.add(TextFormatting.ITALIC + "" + TextFormatting.DARK_AQUA + I18n.func_74838_a("item.enchanted_placeholder.text"));
    }
}
