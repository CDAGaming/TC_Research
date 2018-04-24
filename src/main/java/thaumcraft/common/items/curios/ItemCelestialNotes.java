package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemCelestialNotes extends ItemTCBase
{
    public ItemCelestialNotes() {
        super("celestial_notes", new String[] { "sun", "stars_1", "stars_2", "stars_3", "stars_4", "moon_1", "moon_2", "moon_3", "moon_4", "moon_5", "moon_6", "moon_7", "moon_8" });
    }
    
    @Override
    public String func_77667_c(final ItemStack itemStack) {
        return "item.celestial_notes";
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        try {
            tooltip.add(TextFormatting.AQUA + I18n.func_74838_a("item.celestial_notes." + this.getVariantNames()[stack.func_77952_i()] + ".text"));
        }
        catch (Exception ex) {}
    }
}
