package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.theorycraft.*;

public class CardSculpting extends TheorycraftCard
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
        return new TextComponentTranslation("card.sculpting.name", new Object[0]).func_150254_d();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.sculpting.text", new Object[0]).func_150254_d();
    }
    
    @Override
    public ItemStack[] getRequiredItems() {
        return new ItemStack[] { new ItemStack(Items.field_151119_aD) };
    }
    
    @Override
    public boolean[] getRequiredItemsConsumed() {
        return new boolean[] { true };
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.getResearchCategory(), 20);
        ++data.bonusDraws;
        return true;
    }
}
