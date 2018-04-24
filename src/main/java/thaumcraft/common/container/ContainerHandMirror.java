package thaumcraft.common.container;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.items.tools.*;
import net.minecraft.inventory.*;

public class ContainerHandMirror extends Container implements IInventoryChangedListener
{
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    public IInventory input;
    ItemStack mirror;
    EntityPlayer player;
    
    public ContainerHandMirror(final InventoryPlayer iinventory, final World par2World, final int par3, final int par4, final int par5) {
        this.input = (IInventory)new InventoryHandMirror((IInventoryChangedListener)this);
        this.mirror = null;
        this.player = null;
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.player = iinventory.field_70458_d;
        this.mirror = iinventory.func_70448_g();
        this.func_75146_a(new Slot(this.input, 0, 80, 24));
        this.bindPlayerInventory(iinventory);
        this.func_75130_a(this.input);
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
    
    public void func_75130_a(final IInventory par1IInventory) {
        if (!this.input.func_70301_a(0).func_190926_b() && ItemStack.func_77989_b(this.input.func_70301_a(0), this.mirror)) {
            this.player.field_71070_bA = this.player.field_71069_bz;
        }
        else if (!this.worldObj.field_72995_K && !this.input.func_70301_a(0).func_190926_b() && this.player != null && ItemHandMirror.transport(this.mirror, this.input.func_70301_a(0), this.player, this.worldObj)) {
            this.input.func_70299_a(0, ItemStack.field_190927_a);
            for (int var4 = 0; var4 < this.field_75149_d.size(); ++var4) {
                this.field_75149_d.get(var4).func_71111_a((Container)this, 0, (ItemStack)null);
            }
        }
    }
    
    public ItemStack func_184996_a(final int slotId, final int dragType, final ClickType clickTypeIn, final EntityPlayer player) {
        try {
            final ItemStack s = this.func_75139_a(slotId).func_75211_c();
            if (s.func_77973_b() instanceof ItemHandMirror) {
                return null;
            }
        }
        catch (Exception ex) {}
        return super.func_184996_a(slotId, dragType, clickTypeIn, player);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d() && !(slotObject.func_75211_c().func_77973_b() instanceof ItemHandMirror)) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot == 0) {
                if (!this.mergeItemStack(stackInSlot, 1, this.field_75151_b.size(), true, 64)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.mergeItemStack(stackInSlot, 0, 1, false, 64)) {
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
    
    public void func_75134_a(final EntityPlayer par1EntityPlayer) {
        super.func_75134_a(par1EntityPlayer);
        if (!this.worldObj.field_72995_K) {
            for (int var2 = 0; var2 < 1; ++var2) {
                final ItemStack var3 = this.input.func_70304_b(var2);
                par1EntityPlayer.func_71019_a(var3, false);
            }
        }
    }
    
    protected boolean mergeItemStack(final ItemStack stackin, final int par2, final int par3, final boolean par4, final int limit) {
        boolean var5 = false;
        int var6 = par2;
        if (par4) {
            var6 = par3 - 1;
        }
        if (stackin.func_77985_e()) {
            while (stackin.func_190916_E() > 0 && ((!par4 && var6 < par3) || (par4 && var6 >= par2))) {
                final Slot var7 = this.field_75151_b.get(var6);
                final ItemStack stack8 = var7.func_75211_c();
                if (stack8 != null && !stack8.func_190926_b() && stack8.func_77973_b() == stackin.func_77973_b() && (!stackin.func_77981_g() || stackin.func_77952_i() == stack8.func_77952_i()) && ItemStack.func_77970_a(stackin, stack8)) {
                    final int var8 = stack8.func_190916_E() + stackin.func_190916_E();
                    if (var8 <= Math.min(stackin.func_77976_d(), limit)) {
                        stackin.func_190920_e(0);
                        stack8.func_190920_e(var8);
                        var7.func_75218_e();
                        var5 = true;
                    }
                    else if (stack8.func_190916_E() < Math.min(stackin.func_77976_d(), limit)) {
                        stackin.func_190918_g(Math.min(stackin.func_77976_d(), limit) - stack8.func_190916_E());
                        stack8.func_190920_e(Math.min(stackin.func_77976_d(), limit));
                        var7.func_75218_e();
                        var5 = true;
                    }
                }
                if (par4) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        if (stackin.func_190916_E() > 0) {
            if (par4) {
                var6 = par3 - 1;
            }
            else {
                var6 = par2;
            }
            while ((!par4 && var6 < par3) || (par4 && var6 >= par2)) {
                final Slot var7 = this.field_75151_b.get(var6);
                final ItemStack stack8 = var7.func_75211_c();
                if (stack8 == null || stack8.func_190926_b()) {
                    final ItemStack res = stackin.func_77946_l();
                    res.func_190920_e(Math.min(res.func_190916_E(), limit));
                    var7.func_75215_d(res);
                    var7.func_75218_e();
                    stackin.func_190918_g(res.func_190916_E());
                    var5 = true;
                    break;
                }
                if (par4) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        return var5;
    }
    
    public void func_76316_a(final IInventory invBasic) {
        this.func_75142_b();
    }
}
