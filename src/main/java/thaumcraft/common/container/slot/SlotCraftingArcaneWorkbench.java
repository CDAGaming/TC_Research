package thaumcraft.common.container.slot;

import net.minecraft.entity.player.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.inventory.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraftforge.common.*;
import net.minecraft.item.crafting.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.api.*;
import thaumcraft.api.items.*;
import net.minecraft.util.*;
import thaumcraft.api.crafting.*;
import thaumcraft.api.aspects.*;

public class SlotCraftingArcaneWorkbench extends Slot
{
    private final InventoryCrafting craftMatrix;
    private EntityPlayer player;
    private int amountCrafted;
    private TileArcaneWorkbench tile;
    
    public SlotCraftingArcaneWorkbench(final TileArcaneWorkbench te, final EntityPlayer par1EntityPlayer, final InventoryCrafting inventory, final IInventory par3IInventory, final int par4, final int par5, final int par6) {
        super(par3IInventory, par4, par5, par6);
        this.player = par1EntityPlayer;
        this.craftMatrix = inventory;
        this.tile = te;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return false;
    }
    
    public ItemStack func_75209_a(final int amount) {
        if (this.func_75216_d()) {
            this.amountCrafted += Math.min(amount, this.func_75211_c().func_190916_E());
        }
        return super.func_75209_a(amount);
    }
    
    protected void func_75210_a(final ItemStack stack, final int amount) {
        this.amountCrafted += amount;
        this.func_75208_c(stack);
    }
    
    protected void func_190900_b(final int p_190900_1_) {
        this.amountCrafted += p_190900_1_;
    }
    
    protected void func_75208_c(final ItemStack stack) {
        if (this.amountCrafted > 0) {
            stack.func_77980_a(this.player.field_70170_p, this.player, this.amountCrafted);
            FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, (IInventory)this.craftMatrix);
        }
        this.amountCrafted = 0;
        final InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.field_75224_c;
        final IRecipe irecipe = inventorycraftresult.func_193055_i();
        if (irecipe != null && !irecipe.func_192399_d()) {
            this.player.func_192021_a((List)Lists.newArrayList((Object[])new IRecipe[] { irecipe }));
            inventorycraftresult.func_193056_a((IRecipe)null);
        }
    }
    
    public ItemStack func_190901_a(final EntityPlayer thePlayer, final ItemStack stack) {
        this.func_75208_c(stack);
        ForgeHooks.setCraftingPlayer(thePlayer);
        final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)CraftingManager.func_180303_b(this.craftMatrix, thePlayer.field_70170_p);
        ForgeHooks.setCraftingPlayer((EntityPlayer)null);
        final IArcaneRecipe recipe = ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.craftMatrix, thePlayer);
        int vis = 0;
        AspectList crystals = null;
        if (recipe != null) {
            vis = recipe.getVis();
            vis *= (int)(1.0f - CasterManager.getTotalVisDiscount(thePlayer));
            crystals = recipe.getCrystals();
        }
        if (vis > 0) {
            this.tile.getAura();
            this.tile.spendAura(vis);
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = this.craftMatrix.func_70301_a(i);
            final ItemStack itemstack2 = (ItemStack)nonnulllist.get(i);
            if (!itemstack.func_190926_b()) {
                this.craftMatrix.func_70298_a(i, 1);
                itemstack = this.craftMatrix.func_70301_a(i);
            }
            if (!itemstack2.func_190926_b()) {
                if (itemstack.func_190926_b()) {
                    this.craftMatrix.func_70299_a(i, itemstack2);
                }
                else if (ItemStack.func_179545_c(itemstack, itemstack2) && ItemStack.func_77970_a(itemstack, itemstack2)) {
                    itemstack2.func_190917_f(itemstack.func_190916_E());
                    this.craftMatrix.func_70299_a(i, itemstack2);
                }
                else if (!this.player.field_71071_by.func_70441_a(itemstack2)) {
                    this.player.func_71019_a(itemstack2, false);
                }
            }
        }
        if (crystals != null) {
            for (final Aspect aspect : crystals.getAspects()) {
                final ItemStack cs = ThaumcraftApiHelper.makeCrystal(aspect, crystals.getAmount(aspect));
                for (int j = 0; j < 6; ++j) {
                    final ItemStack itemstack3 = this.craftMatrix.func_70301_a(9 + j);
                    if (itemstack3.func_77973_b() == ItemsTC.crystalEssence && ItemStack.func_77970_a(cs, itemstack3)) {
                        this.craftMatrix.func_70298_a(9 + j, cs.func_190916_E());
                    }
                }
            }
        }
        return stack;
    }
}
