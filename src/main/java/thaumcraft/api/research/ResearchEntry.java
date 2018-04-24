package thaumcraft.api.research;

import net.minecraft.item.*;
import net.minecraft.util.text.translation.*;
import java.util.*;

public class ResearchEntry
{
    String key;
    String category;
    String name;
    String[] parents;
    String[] siblings;
    int displayColumn;
    int displayRow;
    Object[] icons;
    EnumResearchMeta[] meta;
    ItemStack[] rewardItem;
    ResearchStage.Knowledge[] rewardKnow;
    ResearchStage[] stages;
    ResearchAddendum[] addenda;
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLocalizedName() {
        return I18n.func_74838_a(this.getName());
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String[] getParents() {
        return this.parents;
    }
    
    public String[] getParentsClean() {
        String[] out = null;
        if (this.parents != null) {
            out = this.getParentsStripped();
            for (int q = 0; q < out.length; ++q) {
                if (out[q].contains("@")) {
                    out[q] = out[q].substring(0, out[q].indexOf("@"));
                }
            }
        }
        return out;
    }
    
    public String[] getParentsStripped() {
        String[] out = null;
        if (this.parents != null) {
            out = new String[this.parents.length];
            for (int q = 0; q < out.length; ++q) {
                out[q] = "" + this.parents[q];
                if (out[q].startsWith("~")) {
                    out[q] = out[q].substring(1);
                }
            }
        }
        return out;
    }
    
    public void setParents(final String[] parents) {
        this.parents = parents;
    }
    
    public String[] getSiblings() {
        return this.siblings;
    }
    
    public void setSiblings(final String[] siblings) {
        this.siblings = siblings;
    }
    
    public int getDisplayColumn() {
        return this.displayColumn;
    }
    
    public void setDisplayColumn(final int displayColumn) {
        this.displayColumn = displayColumn;
    }
    
    public int getDisplayRow() {
        return this.displayRow;
    }
    
    public void setDisplayRow(final int displayRow) {
        this.displayRow = displayRow;
    }
    
    public Object[] getIcons() {
        return this.icons;
    }
    
    public void setIcons(final Object[] icons) {
        this.icons = icons;
    }
    
    public EnumResearchMeta[] getMeta() {
        return this.meta;
    }
    
    public boolean hasMeta(final EnumResearchMeta me) {
        return this.meta != null && Arrays.asList(this.meta).contains(me);
    }
    
    public void setMeta(final EnumResearchMeta[] meta) {
        this.meta = meta;
    }
    
    public ResearchStage[] getStages() {
        return this.stages;
    }
    
    public void setStages(final ResearchStage[] stages) {
        this.stages = stages;
    }
    
    public ItemStack[] getRewardItem() {
        return this.rewardItem;
    }
    
    public void setRewardItem(final ItemStack[] rewardItem) {
        this.rewardItem = rewardItem;
    }
    
    public ResearchStage.Knowledge[] getRewardKnow() {
        return this.rewardKnow;
    }
    
    public void setRewardKnow(final ResearchStage.Knowledge[] rewardKnow) {
        this.rewardKnow = rewardKnow;
    }
    
    public ResearchAddendum[] getAddenda() {
        return this.addenda;
    }
    
    public void setAddenda(final ResearchAddendum[] addenda) {
        this.addenda = addenda;
    }
    
    public enum EnumResearchMeta
    {
        ROUND, 
        SPIKY, 
        REVERSE, 
        HIDDEN, 
        AUTOUNLOCK, 
        HEX;
    }
}
