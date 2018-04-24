package thaumcraft.api.research.theorycraft;

import net.minecraft.nbt.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CardReject extends TheorycraftCard
{
    private String cat1;
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("cat", this.cat1);
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.cat1 = nbt.func_74779_i("cat");
    }
    
    @Override
    public int getInspirationCost() {
        return 0;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.reject.name", new Object[] { TextFormatting.DARK_BLUE + "" + TextFormatting.BOLD + new TextComponentTranslation("tc.research_category." + this.cat1, new Object[0]).func_150260_c() + TextFormatting.RESET + "" + TextFormatting.BOLD }).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.reject.text", new Object[] { TextFormatting.BOLD + new TextComponentTranslation("tc.research_category." + this.cat1, new Object[0]).func_150254_d() + TextFormatting.RESET }).func_150260_c();
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final ArrayList<String> s = new ArrayList<String>();
        for (final String c : data.categoryTotals.keySet()) {
            if (!data.categoriesBlocked.contains(c)) {
                s.add(c);
            }
        }
        if (s.size() < 1) {
            return false;
        }
        final Random r = new Random(this.getSeed());
        this.cat1 = s.get(r.nextInt(s.size()));
        return this.cat1 != null;
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        if (this.cat1 == null) {
            return false;
        }
        data.addTotal("BASICS", 5);
        data.categoriesBlocked.add(this.cat1);
        return true;
    }
}
