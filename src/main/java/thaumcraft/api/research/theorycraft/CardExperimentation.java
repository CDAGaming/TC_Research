package thaumcraft.api.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;

public class CardExperimentation extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.experimentation.name", new Object[0]).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.experimentation.text", new Object[0]).func_150260_c();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        try {
            final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
            final String cat = s[player.func_70681_au().nextInt(s.length)];
            data.addTotal(cat, MathHelper.func_76136_a(player.func_70681_au(), 10, 25));
            data.addTotal("BASICS", MathHelper.func_76136_a(player.func_70681_au(), 1, 10));
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
