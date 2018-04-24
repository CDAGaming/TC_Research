package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import java.util.*;

public class CardDarkWhispers extends TheorycraftCard
{
    @Override
    public boolean isAidOnly() {
        return true;
    }
    
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
        return new TextComponentTranslation("card.darkwhisper.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.darkwhisper.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final int l = player.field_71068_ca;
        player.func_82242_a(-(10 + l));
        if (l > 0) {
            for (final String k : ResearchCategories.researchCategories.keySet()) {
                if (player.func_70681_au().nextBoolean()) {
                    continue;
                }
                data.addTotal(k, MathHelper.func_76136_a(player.func_70681_au(), 0, Math.max(1, (int)Math.sqrt(l))));
            }
        }
        data.addTotal("ELDRITCH", MathHelper.func_76136_a(player.func_70681_au(), Math.max(1, l / 5), Math.max(5, l / 2)));
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, Math.max(1, (int)Math.sqrt(l)), IPlayerWarp.EnumWarpType.NORMAL);
        if (player.func_70681_au().nextBoolean()) {
            ++data.bonusDraws;
        }
        return true;
    }
}
