package thaumcraft.api.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CardPonder extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 2;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.ponder.name", new Object[0]).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.ponder.text", new Object[0]).func_150260_c();
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        return data.categoriesBlocked.size() < data.categoryTotals.size();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        int a = 25;
        int tries = 0;
        while (a > 0 && tries < 1000) {
            ++tries;
            for (final String category : data.categoryTotals.keySet()) {
                if (data.categoriesBlocked.contains(category)) {
                    if (data.categoryTotals.size() <= 1) {
                        return false;
                    }
                    continue;
                }
                else {
                    data.addTotal(category, 1);
                    if (--a <= 0) {
                        break;
                    }
                    continue;
                }
            }
        }
        data.addTotal("BASICS", 5);
        ++data.bonusDraws;
        return a != 20;
    }
}
