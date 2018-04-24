package thaumcraft.common.container;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.common.items.casters.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;

public class ContainerFocusPouch extends Container implements IInventoryChangedListener
{
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    private int blockSlot;
    public IInventory input;
    ItemStack pouch;
    EntityPlayer player;
    
    public ContainerFocusPouch(final InventoryPlayer iinventory, final World par2World, final int par3, final int par4, final int par5) {
        this.input = (IInventory)new InventoryFocusPouch((IInventoryChangedListener)this);
        this.pouch = null;
        this.player = null;
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.player = iinventory.field_70458_d;
        this.pouch = iinventory.func_70448_g();
        this.blockSlot = iinventory.field_70461_c + 45;
        for (int a = 0; a < 18; ++a) {
            this.func_75146_a((Slot)new SlotLimitedByClass(ItemFocus.class, this.input, a, 37 + a % 6 * 18, 51 + a / 6 * 18));
        }
        this.bindPlayerInventory(iinventory);
        if (!par2World.field_72995_K) {
            try {
                final NonNullList<ItemStack> list = ((ItemFocusPouch)this.pouch.func_77973_b()).getInventory(this.pouch);
                for (int a2 = 0; a2 < list.size(); ++a2) {
                    this.input.func_70299_a(a2, (ItemStack)list.get(a2));
                }
            }
            catch (Exception ex) {}
        }
        this.func_75130_a(this.input);
    }
    
    public void func_76316_a(final IInventory invBasic) {
        this.func_75142_b();
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 209));
        }
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        if (slot == this.blockSlot) {
            return ItemStack.field_190927_a;
        }
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot < 18) {
                if (!this.input.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 18, this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.input.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, 18, false)) {
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
    
    public boolean func_75145_c(final EntityPlayer var1) {
        return true;
    }
    
    public ItemStack func_184996_a(final int slotId, final int dragType, final ClickType clickTypeIn, final EntityPlayer player) {
        if (slotId == this.blockSlot) {
            return null;
        }
        return super.func_184996_a(slotId, dragType, clickTypeIn, player);
    }
    
    public void func_75134_a(final EntityPlayer par1EntityPlayer) {
        super.func_75134_a(par1EntityPlayer);
        if (!this.worldObj.field_72995_K) {
            final NonNullList<ItemStack> list = (NonNullList<ItemStack>)NonNullList.func_191197_a(18, (Object)ItemStack.field_190927_a);
            for (int a = 0; a < list.size(); ++a) {
                list.set(a, (Object)this.input.func_70301_a(a));
            }
            ((ItemFocusPouch)this.pouch.func_77973_b()).setInventory(this.pouch, list);
            if (this.player == null) {
                return;
            }
            if (this.player.func_184586_b(this.player.func_184600_cs()).func_77969_a(this.pouch)) {
                this.player.func_184611_a(this.player.func_184600_cs(), this.pouch);
            }
            this.player.field_71071_by.func_70296_d();
        }
    }
}
