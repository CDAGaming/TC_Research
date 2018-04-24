package thaumcraft.api.research;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.api.capabilities.*;

public class ResearchStage
{
    String text;
    ResourceLocation[] recipes;
    ItemStack[] obtain;
    ItemStack[] craft;
    int[] craftReference;
    Knowledge[] know;
    String[] research;
    int warp;
    
    public String getText() {
        return this.text;
    }
    
    public String getTextLocalized() {
        return I18n.func_74838_a(this.getText());
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public ResourceLocation[] getRecipes() {
        return this.recipes;
    }
    
    public void setRecipes(final ResourceLocation[] recipes) {
        this.recipes = recipes;
    }
    
    public ItemStack[] getObtain() {
        return this.obtain;
    }
    
    public void setObtain(final ItemStack[] obtain) {
        this.obtain = obtain;
    }
    
    public ItemStack[] getCraft() {
        return this.craft;
    }
    
    public void setCraft(final ItemStack[] craft) {
        this.craft = craft;
    }
    
    public int[] getCraftReference() {
        return this.craftReference;
    }
    
    public void setCraftReference(final int[] craftReference) {
        this.craftReference = craftReference;
    }
    
    public Knowledge[] getKnow() {
        return this.know;
    }
    
    public void setKnow(final Knowledge[] know) {
        this.know = know;
    }
    
    public String[] getResearch() {
        return this.research;
    }
    
    public void setResearch(final String[] research) {
        this.research = research;
    }
    
    public int getWarp() {
        return this.warp;
    }
    
    public void setWarp(final int warp) {
        this.warp = warp;
    }
    
    public static class Knowledge
    {
        public IPlayerKnowledge.EnumKnowledgeType type;
        public ResearchCategory category;
        public int amount;
        
        public Knowledge(final IPlayerKnowledge.EnumKnowledgeType type, final ResearchCategory category, final int num) {
            this.amount = 0;
            this.type = type;
            this.category = category;
            this.amount = num;
        }
        
        public static Knowledge parse(final String text) {
            final String[] s = text.split(";");
            if (s.length == 2) {
                int num = 0;
                try {
                    num = Integer.parseInt(s[1]);
                }
                catch (Exception ex) {}
                final IPlayerKnowledge.EnumKnowledgeType t = IPlayerKnowledge.EnumKnowledgeType.valueOf(s[0].toUpperCase());
                if (t != null && !t.hasFields() && num > 0) {
                    return new Knowledge(t, null, num);
                }
            }
            else if (s.length == 3) {
                int num = 0;
                try {
                    num = Integer.parseInt(s[2]);
                }
                catch (Exception ex2) {}
                final IPlayerKnowledge.EnumKnowledgeType t = IPlayerKnowledge.EnumKnowledgeType.valueOf(s[0].toUpperCase());
                final ResearchCategory f = ResearchCategories.getResearchCategory(s[1].toUpperCase());
                if (t != null && f != null && num > 0) {
                    return new Knowledge(t, f, num);
                }
            }
            return null;
        }
    }
}
