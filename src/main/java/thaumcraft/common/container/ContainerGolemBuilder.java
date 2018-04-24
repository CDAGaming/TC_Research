package thaumcraft.common.container;

import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;

public class ContainerGolemBuilder extends Container
{
    private TileGolemBuilder builder;
    public static boolean redo;
    private int lastCost;
    private int lastMaxCost;
    
    public ContainerGolemBuilder(final InventoryPlayer par1InventoryPlayer, final TileGolemBuilder tileEntity) {
        this.builder = tileEntity;
        this.func_75146_a((Slot)new SlotOutput((IInventory)tileEntity, 0, 160, 104));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 24 + j * 18, 142 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, i, 24 + i * 18, 200));
        }
    }
    
    public ItemStack func_184996_a(final int slotId, final int clickedButton, final ClickType mode, final EntityPlayer playerIn) {
        ContainerGolemBuilder.redo = true;
        return super.func_184996_a(slotId, clickedButton, mode, playerIn);
    }
    
    public void func_75141_a(final int p_75141_1_, final ItemStack p_75141_2_) {
        ContainerGolemBuilder.redo = true;
        super.func_75141_a(p_75141_1_, p_75141_2_);
    }
    
    public boolean func_75140_a(final EntityPlayer p, final int button) {
        if (button == 99) {
            ContainerGolemBuilder.redo = true;
        }
        return false;
    }
    
    public void func_75132_a(final IContainerListener par1ICrafting) {
        super.func_75132_a(par1ICrafting);
        par1ICrafting.func_71112_a((Container)this, 0, this.builder.cost);
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        for (int i = 0; i < this.field_75149_d.size(); ++i) {
            final IContainerListener icrafting = this.field_75149_d.get(i);
            if (this.lastCost != this.builder.cost) {
                icrafting.func_71112_a((Container)this, 0, this.builder.cost);
            }
            if (this.lastMaxCost != this.builder.maxCost) {
                icrafting.func_71112_a((Container)this, 1, this.builder.maxCost);
            }
        }
        this.lastCost = this.builder.cost;
        this.lastMaxCost = this.builder.maxCost;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
        if (par1 == 0) {
            this.builder.cost = par2;
        }
        if (par1 == 1) {
            this.builder.maxCost = par2;
        }
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.builder.func_70300_a(par1EntityPlayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot == 0) {
                if (!this.builder.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 1, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.builder.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, 1, false)) {
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
    
    static {
        ContainerGolemBuilder.redo = false;
    }
}
