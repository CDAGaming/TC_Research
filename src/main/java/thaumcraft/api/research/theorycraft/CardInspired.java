package thaumcraft.api.research.theorycraft;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.text.*;

public class CardInspired extends TheorycraftCard
{
    String cat;
    int amt;
    
    public CardInspired() {
        this.cat = null;
    }
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("cat", this.cat);
        nbt.func_74768_a("amt", this.amt);
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.cat = nbt.func_74779_i("cat");
        this.amt = nbt.func_74762_e("amt");
    }
    
    @Override
    public String getResearchCategory() {
        return this.cat;
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        if (data.categoryTotals.size() < 1) {
            return false;
        }
        int hVal = 0;
        String hKey = "";
        for (final String category : data.categoryTotals.keySet()) {
            final int q = data.getTotal(category);
            if (q > hVal) {
                hVal = q;
                hKey = category;
            }
        }
        this.cat = hKey;
        this.amt = 10 + hVal / 2;
        return true;
    }
    
    @Override
    public int getInspirationCost() {
        return 2;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.inspired.name", new Object[0]).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.inspired.text", new Object[] { this.amt, TextFormatting.BOLD + new TextComponentTranslation("tc.research_category." + this.cat, new Object[0]).func_150254_d() + TextFormatting.RESET }).func_150260_c();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        data.addTotal(this.cat, this.amt);
        return true;
    }
}
