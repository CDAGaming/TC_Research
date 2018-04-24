package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;

public class CardDragonEgg extends TheorycraftCard
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
        return new TextComponentTranslation("card.dragonegg.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.dragonegg.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
        for (int a = 0; a < 10; ++a) {
            final String cat = s[player.func_70681_au().nextInt(s.length)];
            data.addTotal(cat, MathHelper.func_76136_a(player.func_70681_au(), 2, 5));
        }
        return true;
    }
}
