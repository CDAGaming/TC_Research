package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import net.minecraft.util.math.*;

public class CardEnchantment extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public boolean isAidOnly() {
        return true;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.enchantment.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.enchantment.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        if (player.field_71068_ca >= 5) {
            player.func_82242_a(-5);
            data.addTotal("INFUSION", MathHelper.func_76136_a(player.func_70681_au(), 15, 20));
            data.addTotal("AUROMANCY", MathHelper.func_76136_a(player.func_70681_au(), 15, 20));
            return true;
        }
        return false;
    }
}
