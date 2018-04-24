package thaumcraft.api.crafting;

import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.crafting.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.util.*;

public class CrucibleRecipe implements IThaumcraftRecipe
{
    private ItemStack recipeOutput;
    private Ingredient catalyst;
    private AspectList aspects;
    private String research;
    private String name;
    public int hash;
    private String group;
    
    public CrucibleRecipe(final String researchKey, final ItemStack result, final Object catalyst, final AspectList tags) {
        this.group = "";
        this.recipeOutput = result;
        this.name = "";
        this.setAspects(tags);
        this.research = researchKey;
        this.setCatalyst(CraftingHelper.getIngredient(catalyst));
        if (this.getCatalyst() == null) {
            throw new RuntimeException("Invalid crucible recipe catalyst: " + catalyst);
        }
        this.generateHash();
    }
    
    private void generateHash() {
        String hc = this.research;
        hc += this.recipeOutput.toString();
        for (final ItemStack is : this.getCatalyst().func_193365_a()) {
            hc += is.toString();
        }
        this.hash = hc.hashCode();
    }
    
    public boolean matches(final AspectList itags, final ItemStack cat) {
        if (!this.getCatalyst().apply(cat)) {
            return false;
        }
        if (itags == null) {
            return false;
        }
        for (final Aspect tag : this.getAspects().getAspects()) {
            if (itags.getAmount(tag) < this.getAspects().getAmount(tag)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean catalystMatches(final ItemStack cat) {
        return this.getCatalyst().apply(cat);
    }
    
    public AspectList removeMatching(AspectList itags) {
        final AspectList temptags = new AspectList();
        temptags.aspects.putAll((Map<?, ?>)itags.aspects);
        for (final Aspect tag : this.getAspects().getAspects()) {
            temptags.remove(tag, this.getAspects().getAmount(tag));
        }
        itags = temptags;
        return itags;
    }
    
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public String getResearch() {
        return this.research;
    }
    
    public Ingredient getCatalyst() {
        return this.catalyst;
    }
    
    public void setCatalyst(final Ingredient catalyst) {
        this.catalyst = catalyst;
    }
    
    public AspectList getAspects() {
        return this.aspects;
    }
    
    public void setAspects(final AspectList aspects) {
        this.aspects = aspects;
    }
    
    @Override
    public String getGroup() {
        return this.group;
    }
    
    public CrucibleRecipe setGroup(final ResourceLocation s) {
        this.group = s.toString();
        return this;
    }
}
