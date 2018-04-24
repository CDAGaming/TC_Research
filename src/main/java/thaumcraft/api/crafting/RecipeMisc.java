package thaumcraft.api.crafting;

import net.minecraft.item.*;

public class RecipeMisc
{
    MiscRecipeType type;
    ItemStack input;
    ItemStack output;
    
    public RecipeMisc(final ItemStack input, final ItemStack output, final MiscRecipeType type) {
        this.input = input;
        this.output = output;
        this.type = type;
    }
    
    public MiscRecipeType getType() {
        return this.type;
    }
    
    public void setType(final MiscRecipeType type) {
        this.type = type;
    }
    
    public ItemStack getInput() {
        return this.input;
    }
    
    public void setInput(final ItemStack input) {
        this.input = input;
    }
    
    public ItemStack getOutput() {
        return this.output;
    }
    
    public void setOutput(final ItemStack output) {
        this.output = output;
    }
    
    public enum MiscRecipeType
    {
        SMELTING;
    }
}
