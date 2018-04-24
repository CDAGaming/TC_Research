package thaumcraft.api.crafting;

import thaumcraft.api.aspects.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.crafting.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraft.util.*;

public class InfusionRecipe implements IThaumcraftRecipe
{
    public AspectList aspects;
    public String research;
    private String name;
    protected NonNullList<Ingredient> components;
    public Ingredient sourceInput;
    public Object recipeOutput;
    public int instability;
    private String group;
    
    public InfusionRecipe(final String research, final Object outputResult, final int inst, final AspectList aspects2, final Object centralItem, final Object... recipe) {
        this.components = (NonNullList<Ingredient>)NonNullList.func_191196_a();
        this.group = "";
        this.name = "";
        this.research = research;
        this.recipeOutput = outputResult;
        this.aspects = aspects2;
        this.instability = inst;
        this.sourceInput = CraftingHelper.getIngredient(centralItem);
        if (this.sourceInput == null) {
            final String ret = "Invalid infusion central item: " + centralItem;
            throw new RuntimeException(ret);
        }
        for (final Object in : recipe) {
            final Ingredient ing = CraftingHelper.getIngredient(in);
            if (ing == null) {
                String ret2 = "Invalid infusion recipe: ";
                for (final Object tmp : recipe) {
                    ret2 = ret2 + tmp + ", ";
                }
                ret2 += outputResult;
                throw new RuntimeException(ret2);
            }
            this.components.add((Object)ing);
        }
    }
    
    public boolean matches(final List<ItemStack> input, final ItemStack central, final World world, final EntityPlayer player) {
        return this.getRecipeInput() != null && ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research) && (this.getRecipeInput() == Ingredient.field_193370_a || this.getRecipeInput().apply(central)) && RecipeMatcher.findMatches((List)input, (List)this.getComponents()) != null;
    }
    
    @Override
    public String getResearch() {
        return this.research;
    }
    
    public Ingredient getRecipeInput() {
        return this.sourceInput;
    }
    
    public NonNullList<Ingredient> getComponents() {
        return this.components;
    }
    
    public Object getRecipeOutput() {
        return this.recipeOutput;
    }
    
    public AspectList getAspects() {
        return this.aspects;
    }
    
    public Object getRecipeOutput(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        return this.recipeOutput;
    }
    
    public AspectList getAspects(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        return this.aspects;
    }
    
    public int getInstability(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        return this.instability;
    }
    
    @Override
    public String getGroup() {
        return this.group;
    }
    
    public InfusionRecipe setGroup(final ResourceLocation s) {
        this.group = s.toString();
        return this;
    }
}
