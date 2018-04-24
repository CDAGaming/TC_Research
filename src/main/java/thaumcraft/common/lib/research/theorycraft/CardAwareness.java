package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import net.minecraft.util.math.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;

public class CardAwareness extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "AUROMANCY";
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.awareness.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.awareness.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.getResearchCategory(), 20);
        if (player.func_70681_au().nextFloat() < 0.33) {
            data.addTotal("ELDRITCH", MathHelper.func_76136_a(player.func_70681_au(), 1, 5));
            ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.NORMAL);
        }
        return true;
    }
}
