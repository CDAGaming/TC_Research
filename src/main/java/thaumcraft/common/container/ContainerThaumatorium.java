package thaumcraft.common.container;

import thaumcraft.common.tiles.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import java.util.*;
import thaumcraft.api.crafting.*;
import net.minecraft.item.*;

public class ContainerThaumatorium extends Container
{
    private TileThaumatorium thaumatorium;
    private EntityPlayer player;
    public ArrayList<CrucibleRecipe> recipes;
    
    public ContainerThaumatorium(final InventoryPlayer par1InventoryPlayer, final TileThaumatorium tileEntity) {
        this.player = null;
        this.recipes = new ArrayList<CrucibleRecipe>();
        this.player = par1InventoryPlayer.field_70458_d;
        this.thaumatorium = tileEntity;
        ((ContainerThaumatorium)(this.thaumatorium.eventHandler = this)).func_75146_a(new Slot((IInventory)tileEntity, 0, 48, 16));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
        this.func_75130_a((IInventory)this.thaumatorium);
    }
    
    public void func_75130_a(final IInventory par1iInventory) {
        super.func_75130_a(par1iInventory);
        this.updateRecipes();
    }
    
    public void func_75134_a(final EntityPlayer par1EntityPlayer) {
        super.func_75134_a(par1EntityPlayer);
        if (!this.thaumatorium.func_145831_w().field_72995_K) {
            this.thaumatorium.eventHandler = null;
        }
    }
    
    public void updateRecipes() {
        this.recipes.clear();
        if (this.thaumatorium.func_70301_a(0) != null || this.thaumatorium.func_70301_a(0).func_190926_b() || this.thaumatorium.recipeHash != null) {
            for (final Object r : ThaumcraftApi.getCraftingRecipes().values()) {
                if (r instanceof CrucibleRecipe[]) {
                    final CrucibleRecipe[] array;
                    final CrucibleRecipe[] creps = array = (CrucibleRecipe[])r;
                    for (final CrucibleRecipe cr : array) {
                        if (ThaumcraftCapabilities.knowsResearchStrict(this.player, cr.getResearch()) && cr.catalystMatches(this.thaumatorium.func_70301_a(0))) {
                            this.recipes.add(cr);
                        }
                        else if (this.thaumatorium.recipeHash != null && this.thaumatorium.recipeHash.size() > 0) {
                            for (final Integer hash : this.thaumatorium.recipeHash) {
                                if (cr.hash == hash) {
                                    this.recipes.add(cr);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean func_75140_a(final EntityPlayer par1EntityPlayer, final int button) {
        if (this.recipes.size() > 0 && button >= 0 && button < this.recipes.size()) {
            boolean found = false;
            for (int a = 0; a < this.thaumatorium.recipeHash.size(); ++a) {
                if (this.recipes.get(button).hash == this.thaumatorium.recipeHash.get(a)) {
                    found = true;
                    this.thaumatorium.recipeEssentia.remove(a);
                    this.thaumatorium.recipePlayer.remove(a);
                    this.thaumatorium.recipeHash.remove(a);
                    this.thaumatorium.currentCraft = -1;
                    break;
                }
            }
            if (!found) {
                this.thaumatorium.recipeEssentia.add(this.recipes.get(button).getAspects().copy());
                this.thaumatorium.recipePlayer.add(par1EntityPlayer.func_70005_c_());
                this.thaumatorium.recipeHash.add(this.recipes.get(button).hash);
            }
            this.thaumatorium.func_70296_d();
            this.thaumatorium.syncTile(false);
            return true;
        }
        return false;
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.thaumatorium.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack itemstack = ItemStack.field_190927_a;
        final Slot slot = this.field_75151_b.get(par2);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (par2 != 0) {
                if (!this.func_75135_a(itemstack2, 0, 1, false)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (par2 >= 1 && par2 < 28) {
                if (!this.func_75135_a(itemstack2, 28, 37, false)) {
                    return ItemStack.field_190927_a;
                }
            }
            else {
                if (par2 >= 28 && par2 < 37 && !this.func_75135_a(itemstack2, 1, 28, false)) {
                    return ItemStack.field_190927_a;
                }
                if (!this.func_75135_a(itemstack2, 1, 37, false)) {
                    return ItemStack.field_190927_a;
                }
            }
            if (itemstack2.func_190916_E() == 0) {
                slot.func_75215_d(ItemStack.field_190927_a);
            }
            else {
                slot.func_75218_e();
            }
            if (itemstack2.func_190916_E() == itemstack.func_190916_E()) {
                return ItemStack.field_190927_a;
            }
            slot.func_190901_a(par1EntityPlayer, itemstack2);
        }
        return itemstack;
    }
}
