package thaumcraft.api.research.theorycraft;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;
import thaumcraft.api.capabilities.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;

public class CardAnalyze extends TheorycraftCard
{
    String cat;
    
    public CardAnalyze() {
        this.cat = null;
    }
    
    @Override
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = super.serialize();
        nbt.func_74778_a("cat", this.cat);
        return nbt;
    }
    
    @Override
    public void deserialize(final NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.cat = nbt.func_74779_i("cat");
    }
    
    @Override
    public String getResearchCategory() {
        return this.cat;
    }
    
    @Override
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        final Random r = new Random(this.getSeed());
        final ArrayList<String> cats = new ArrayList<String>();
        for (final ResearchCategory rc : ResearchCategories.researchCategories.values()) {
            if (rc.key == "BASICS") {
                continue;
            }
            if (ThaumcraftCapabilities.getKnowledge(player).getKnowledge(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.researchCategories.get(this.cat)) <= 0) {
                continue;
            }
            cats.add(rc.key);
        }
        if (cats.size() > 0) {
            this.cat = cats.get(r.nextInt(cats.size()));
        }
        return this.cat != null;
    }
    
    @Override
    public int getInspirationCost() {
        return 2;
    }
    
    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.analyze.name", new Object[] { TextFormatting.DARK_BLUE + "" + TextFormatting.BOLD + new TextComponentTranslation("tc.research_category." + this.cat, new Object[0]).func_150254_d() + TextFormatting.RESET }).func_150260_c();
    }
    
    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.analyze.text", new Object[] { TextFormatting.BOLD + new TextComponentTranslation("tc.research_category." + this.cat, new Object[0]).func_150254_d() + TextFormatting.RESET, TextFormatting.BOLD + new TextComponentTranslation("tc.research_category.BASICS", new Object[0]).func_150254_d() + TextFormatting.RESET }).func_150260_c();
    }
    
    @Override
    public boolean activate(final EntityPlayer player, final ResearchTableData data) {
        final ResearchCategory rc = ResearchCategories.getResearchCategory(this.cat);
        final int k = ThaumcraftCapabilities.getKnowledge(player).getKnowledge(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc);
        if (k >= 1) {
            data.addTotal("BASICS", 5);
            ThaumcraftCapabilities.getKnowledge(player).addKnowledge(IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc, -IPlayerKnowledge.EnumKnowledgeType.OBSERVATION.getProgression());
            data.addTotal(this.cat, MathHelper.func_76136_a(player.func_70681_au(), 25, 50));
            return true;
        }
        return false;
    }
}
