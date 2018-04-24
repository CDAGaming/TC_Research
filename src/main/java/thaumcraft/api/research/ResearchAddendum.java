package thaumcraft.api.research;

import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;

public class ResearchAddendum
{
    String text;
    ResourceLocation[] recipes;
    String[] research;
    
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
    
    public String[] getResearch() {
        return this.research;
    }
    
    public void setResearch(final String[] research) {
        this.research = research;
    }
}
