package thaumcraft.common.container;

import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import thaumcraft.common.items.casters.*;

public class ContainerFocalManipulator extends Container
{
    private TileFocalManipulator table;
    private int lastBreakTime;
    
    public ContainerFocalManipulator(final InventoryPlayer inventoryPlayer, final TileFocalManipulator tileEntity) {
        this.table = tileEntity;
        this.func_75146_a((Slot)new SlotFocus((IInventory)tileEntity, 0, 31, 191));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, j + i * 9 + 9, i * 18 - 62, 64 + j * 18));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, i + j * 3, i * 18 - 62, j * 18 + 7));
            }
        }
    }
    
    public boolean func_75140_a(final EntityPlayer p, final int button) {
        if (button == 0 && !this.table.startCraft(button, p)) {
            this.table.func_145831_w().func_184133_a(p, this.table.func_174877_v(), SoundsTC.craftfail, SoundCategory.BLOCKS, 0.33f, 1.0f);
        }
        return false;
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.table.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack itemstack = ItemStack.field_190927_a;
        final Slot slot = this.field_75151_b.get(par2);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (par2 != 0) {
                if (itemstack2.func_77973_b() instanceof ItemFocus) {
                    if (!this.func_75135_a(itemstack2, 0, 1, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (par2 >= 1 && par2 < 28) {
                    if (!this.func_75135_a(itemstack2, 28, 37, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
                else if (par2 >= 28 && par2 < 37 && !this.func_75135_a(itemstack2, 1, 28, false)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.func_75135_a(itemstack2, 1, 37, false)) {
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
