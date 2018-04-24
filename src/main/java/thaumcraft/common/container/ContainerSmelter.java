package thaumcraft.common.container;

import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.api.aspects.*;

public class ContainerSmelter extends Container
{
    private TileSmelter furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private int lastVis;
    private int lastSmelt;
    private int lastFlux;
    
    public ContainerSmelter(final InventoryPlayer par1InventoryPlayer, final TileSmelter tileEntity) {
        this.furnace = tileEntity;
        this.func_75146_a((Slot)new SlotLimitedHasAspects((IInventory)tileEntity, 0, 80, 8));
        this.func_75146_a(new Slot((IInventory)tileEntity, 1, 80, 48));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public void func_75132_a(final IContainerListener listener) {
        super.func_75132_a(listener);
        listener.func_175173_a((Container)this, (IInventory)this.furnace);
        listener.func_71112_a((Container)this, 0, this.furnace.furnaceCookTime);
        listener.func_71112_a((Container)this, 1, this.furnace.furnaceBurnTime);
        listener.func_71112_a((Container)this, 2, this.furnace.currentItemBurnTime);
        listener.func_71112_a((Container)this, 3, this.furnace.vis);
        listener.func_71112_a((Container)this, 4, this.furnace.smeltTime);
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        for (int i = 0; i < this.field_75149_d.size(); ++i) {
            final IContainerListener icrafting = this.field_75149_d.get(i);
            if (this.lastCookTime != this.furnace.furnaceCookTime) {
                icrafting.func_71112_a((Container)this, 0, this.furnace.furnaceCookTime);
            }
            if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
                icrafting.func_71112_a((Container)this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
                icrafting.func_71112_a((Container)this, 2, this.furnace.currentItemBurnTime);
            }
            if (this.lastVis != this.furnace.vis) {
                icrafting.func_71112_a((Container)this, 3, this.furnace.vis);
            }
            if (this.lastSmelt != this.furnace.smeltTime) {
                icrafting.func_71112_a((Container)this, 4, this.furnace.smeltTime);
            }
        }
        this.lastCookTime = this.furnace.furnaceCookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
        this.lastVis = this.furnace.vis;
        this.lastSmelt = this.furnace.smeltTime;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
        if (par1 == 0) {
            this.furnace.furnaceCookTime = par2;
        }
        if (par1 == 1) {
            this.furnace.furnaceBurnTime = par2;
        }
        if (par1 == 2) {
            this.furnace.currentItemBurnTime = par2;
        }
        if (par1 == 3) {
            this.furnace.vis = par2;
        }
        if (par1 == 4) {
            this.furnace.smeltTime = par2;
        }
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.furnace.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack itemstack = ItemStack.field_190927_a;
        final Slot slot = this.field_75151_b.get(par2);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (par2 != 1 && par2 != 0) {
                final AspectList al = ThaumcraftCraftingManager.getObjectTags(itemstack2);
                if (TileSmelter.isItemFuel(itemstack2)) {
                    if (!this.func_75135_a(itemstack2, 1, 2, false) && !this.func_75135_a(itemstack2, 0, 1, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (al != null && al.size() > 0) {
                    if (!this.func_75135_a(itemstack2, 0, 1, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (par2 >= 2 && par2 < 29) {
                    if (!this.func_75135_a(itemstack2, 29, 38, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (par2 >= 29 && par2 < 38 && !this.func_75135_a(itemstack2, 2, 29, false)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.func_75135_a(itemstack2, 2, 38, false)) {
                return ItemStack.field_190927_a;
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
