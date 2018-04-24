package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.common.tiles.crafting.*;

public class CardScripting extends TheorycraftCard
{
    @Override
    public int getInspirationCost() {
        return 1;
    }
    
    @Override
    public String getResearchCategory() {
        return "GOLEMANCY";
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.scripting.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.scripting.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        if (data.table != null && ((TileResearchTable)data.table).func_70301_a(0) != null && ((TileResearchTable)data.table).func_70301_a(0).func_77952_i() < ((TileResearchTable)data.table).func_70301_a(0).func_77958_k() && ((TileResearchTable)data.table).func_70301_a(1) != null) {
            ((TileResearchTable)data.table).consumeInkFromTable();
            ((TileResearchTable)data.table).consumepaperFromTable();
            data.addTotal(this.getResearchCategory(), 25);
            return true;
        }
        return false;
    }
}
