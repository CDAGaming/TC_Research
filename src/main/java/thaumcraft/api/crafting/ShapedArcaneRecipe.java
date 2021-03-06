package thaumcraft.api.crafting;

import net.minecraftforge.oredict.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraftforge.common.crafting.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import thaumcraft.api.*;
import thaumcraft.api.items.*;
import thaumcraft.api.aspects.*;

public class ShapedArcaneRecipe extends ShapedOreRecipe implements IArcaneRecipe
{
    private String research;
    private int vis;
    private AspectList crystals;
    
    public ShapedArcaneRecipe(final ResourceLocation group, final String res, final int vis, final AspectList crystals, final Block result, final Object... recipe) {
        this(group, res, vis, crystals, new ItemStack(result), recipe);
    }
    
    public ShapedArcaneRecipe(final ResourceLocation group, final String res, final int vis, final AspectList crystals, final Item result, final Object... recipe) {
        this(group, res, vis, crystals, new ItemStack(result), recipe);
    }
    
    public ShapedArcaneRecipe(final ResourceLocation group, final String res, final int vis, final AspectList crystals, @Nonnull final ItemStack result, final Object... recipe) {
        this(group, res, vis, crystals, result, CraftingHelper.parseShaped(recipe));
    }
    
    public ShapedArcaneRecipe(final ResourceLocation group, final String res, final int vis, final AspectList crystals, @Nonnull final ItemStack result, final CraftingHelper.ShapedPrimer primer) {
        super(group, result, primer);
        this.research = res;
        this.vis = vis;
        this.crystals = crystals;
    }
    
    public ItemStack func_77572_b(final InventoryCrafting var1) {
        if (!(var1 instanceof IArcaneWorkbench)) {
            return ItemStack.field_190927_a;
        }
        return super.func_77572_b(var1);
    }
    
    public boolean func_77569_a(final InventoryCrafting inv, final World world) {
        if (inv.func_70302_i_() < 15) {
            return false;
        }
        final InventoryCrafting dummy = new InventoryCrafting((Container)new ContainerDummy(), 3, 3);
        for (int a = 0; a < 9; ++a) {
            dummy.func_70299_a(a, inv.func_70301_a(a));
        }
        if (this.crystals != null) {
            for (final Aspect aspect : this.crystals.getAspects()) {
                final ItemStack cs = ThaumcraftApiHelper.makeCrystal(aspect, this.crystals.getAmount(aspect));
                boolean b = false;
                for (int i = 0; i < 6; ++i) {
                    final ItemStack itemstack1 = inv.func_70301_a(9 + i);
                    if (itemstack1.func_77973_b() == ItemsTC.crystalEssence && itemstack1.func_190916_E() >= cs.func_190916_E() && ItemStack.func_77970_a(cs, itemstack1)) {
                        b = true;
                    }
                }
                if (!b) {
                    return false;
                }
            }
        }
        return inv instanceof IArcaneWorkbench && super.func_77569_a(dummy, world);
    }
    
    public int getVis() {
        return this.vis;
    }
    
    public String getResearch() {
        return this.research;
    }
    
    public AspectList getCrystals() {
        return this.crystals;
    }
}
