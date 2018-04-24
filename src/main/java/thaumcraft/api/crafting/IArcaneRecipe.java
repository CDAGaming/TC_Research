package thaumcraft.api.crafting;

import net.minecraft.item.crafting.*;
import thaumcraft.api.aspects.*;

public interface IArcaneRecipe extends IRecipe
{
    int getVis();
    
    String getResearch();
    
    AspectList getCrystals();
}
