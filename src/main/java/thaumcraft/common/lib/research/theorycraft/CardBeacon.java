package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;

public class CardBeacon extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return -2;
    }
    
    @Override
    public boolean isAidOnly() {
        return true;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.beacon.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.beacon.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        ++data.bonusDraws;
        ++data.penaltyStart;
        return true;
    }
}
