package thaumcraft.common.container;

import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerPotionSprayer extends Container
{
    private TilePotionSprayer sprayer;
    private int lastBreakTime;
    
    public ContainerPotionSprayer(final InventoryPlayer par1InventoryPlayer, final TilePotionSprayer tilePotionSprayer) {
        this.sprayer = tilePotionSprayer;
        this.func_75146_a((Slot)new SlotPotion((IInventory)tilePotionSprayer, 0, 56, 64));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 16 + j * 18, 151 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 16 + i * 18, 209));
        }
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.sprayer.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot == 0) {
                if (!this.sprayer.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 1, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.sprayer.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, 1, false)) {
                return ItemStack.field_190927_a;
            }
            if (stackInSlot.func_190916_E() == 0) {
                slotObject.func_75215_d(ItemStack.field_190927_a);
            }
            else {
                slotObject.func_75218_e();
            }
        }
        return stack;
    }
}
