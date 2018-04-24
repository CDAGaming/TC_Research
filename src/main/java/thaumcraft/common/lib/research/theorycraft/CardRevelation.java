package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;

public class CardRevelation extends TheorycraftCard
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
        return new TextComponentTranslation("card.revelation.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.revelation.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
        data.addTotal(s[player.func_70681_au().nextInt(s.length)], MathHelper.func_76136_a(player.func_70681_au(), 5, 10));
        data.addTotal("ELDRITCH", 30);
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, 5, IPlayerWarp.EnumWarpType.TEMPORARY);
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.NORMAL);
        ++data.penaltyStart;
        return true;
    }
}
