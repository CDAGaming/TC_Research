package thaumcraft.common.lib.crafting;

import thaumcraft.api.crafting.*;
import net.minecraft.item.crafting.*;
import thaumcraft.api.items.*;
import thaumcraft.common.lib.events.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.item.*;
import baubles.api.*;
import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;

public class InfusionRunicAugmentRecipe extends InfusionRecipe
{
    public InfusionRunicAugmentRecipe() {
        super("RUNICSHIELDING", null, 0, null, Ingredient.field_193370_a, new Object[] { ItemsTC.amber, ItemsTC.salisMundus });
    }
    
    public InfusionRunicAugmentRecipe(final ItemStack in) {
        super("RUNICSHIELDING", null, 0, null, in, new Object[] { new ItemStack(ItemsTC.salisMundus), new ItemStack(ItemsTC.amber) });
        final int fc = PlayerEvents.getRunicCharge(in);
        if (fc > 0) {
            this.components.clear();
            final ArrayList<ItemStack> com = new ArrayList<ItemStack>();
            this.components.add((Object)Ingredient.func_193367_a(ItemsTC.salisMundus));
            this.components.add((Object)Ingredient.func_193367_a(ItemsTC.amber));
            int c = 0;
            while (c < fc) {
                ++c;
                this.components.add((Object)Ingredient.func_193367_a(ItemsTC.amber));
            }
        }
    }
    
    @Override
    public boolean matches(final List<ItemStack> input, final ItemStack central, final World world, final EntityPlayer player) {
        return this.getRecipeInput() != null && ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research) && (central.func_77973_b() instanceof ItemArmor || central.func_77973_b() instanceof IBauble) && (this.getRecipeInput() == Ingredient.field_193370_a || this.getRecipeInput().apply(central)) && RecipeMatcher.findMatches((List)input, (List)this.getComponents(central)) != null;
    }
    
    @Override
    public Object getRecipeOutput(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        if (input == null) {
            return null;
        }
        final ItemStack out = input.func_77946_l();
        final int base = PlayerEvents.getRunicCharge(input) + 1;
        out.func_77983_a("TC.RUNIC", (NBTBase)new NBTTagByte((byte)base));
        return out;
    }
    
    @Override
    public AspectList getAspects(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        final AspectList out = new AspectList();
        final int vis = 20 + (int)(20.0 * Math.pow(2.0, PlayerEvents.getRunicCharge(input)));
        if (vis > 0) {
            out.add(Aspect.PROTECT, vis);
            out.add(Aspect.CRYSTAL, vis / 2);
            out.add(Aspect.ENERGY, vis / 2);
        }
        return out;
    }
    
    @Override
    public int getInstability(final EntityPlayer player, final ItemStack input, final List<ItemStack> comps) {
        final int i = 5 + PlayerEvents.getRunicCharge(input) / 2;
        return i;
    }
    
    public NonNullList<Ingredient> getComponents(final ItemStack input) {
        final NonNullList<Ingredient> com = (NonNullList<Ingredient>)NonNullList.func_191196_a();
        com.add((Object)Ingredient.func_193367_a(ItemsTC.salisMundus));
        com.add((Object)Ingredient.func_193367_a(ItemsTC.amber));
        final int fc = PlayerEvents.getRunicCharge(input);
        if (fc > 0) {
            for (int c = 0; c < fc; ++c) {
                com.add((Object)Ingredient.func_193367_a(ItemsTC.amber));
            }
        }
        return com;
    }
}
