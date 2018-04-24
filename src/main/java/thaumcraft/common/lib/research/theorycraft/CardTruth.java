package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import net.minecraft.util.math.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;

public class CardTruth extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "ELDRITCH";
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.truth.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.truth.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal("ELDRITCH", MathHelper.func_76136_a(player.func_70681_au(), 10, 25));
        ++data.bonusDraws;
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, 3, IPlayerWarp.EnumWarpType.TEMPORARY);
        return true;
    }
}
