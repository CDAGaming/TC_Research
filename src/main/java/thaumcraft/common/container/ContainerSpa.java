package thaumcraft.common.container;

import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerSpa extends Container
{
    private TileSpa spa;
    private int lastBreakTime;
    
    public ContainerSpa(final InventoryPlayer par1InventoryPlayer, final TileSpa tileEntity) {
        this.spa = tileEntity;
        this.func_75146_a((Slot)new SlotLimitedByClass(ItemBathSalts.class, (IInventory)tileEntity, 0, 65, 31));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public boolean func_75140_a(final EntityPlayer p, final int button) {
        if (button == 1) {
            this.spa.toggleMix();
        }
        return false;
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.spa.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot == 0) {
                if (!this.spa.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 1, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.spa.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, 1, false)) {
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
