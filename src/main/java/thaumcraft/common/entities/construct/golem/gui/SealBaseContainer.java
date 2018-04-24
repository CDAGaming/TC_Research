package thaumcraft.common.entities.construct.golem.gui;

import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class SealBaseContainer extends Container
{
    private World world;
    ISealEntity seal;
    EntityPlayer player;
    InventoryFake temp;
    int[] categories;
    int category;
    InventoryPlayer pinv;
    int t;
    private byte lastPriority;
    private byte lastColor;
    private int lastAreaX;
    private int lastAreaY;
    private int lastAreaZ;
    
    public SealBaseContainer(final InventoryPlayer iinventory, final World par2World, final ISealEntity seal) {
        this.seal = null;
        this.player = null;
        this.category = -1;
        this.t = 0;
        this.world = par2World;
        this.player = iinventory.field_70458_d;
        this.pinv = iinventory;
        this.seal = seal;
        if (seal.getSeal() instanceof ISealGui) {
            this.categories = ((ISealGui)seal.getSeal()).getGuiCategories();
        }
        else {
            this.categories = new int[] { 0 };
        }
        this.setupCategories();
    }
    
    void setupCategories() {
        this.field_75153_a = NonNullList.func_191196_a();
        this.field_75151_b = Lists.newArrayList();
        this.t = 0;
        if (this.category < 0) {
            this.category = this.categories[0];
        }
        switch (this.category) {
            case 1: {
                this.setupFilterInventory();
                break;
            }
        }
        this.bindPlayerInventory(this.pinv);
    }
    
    private void setupFilterInventory() {
        if (this.seal.getSeal() instanceof ISealConfigFilter) {
            final int s = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
            final int sx = 16 + (s - 1) % 3 * 12;
            final int sy = 16 + (s - 1) / 3 * 12;
            final int middleX = 88;
            final int middleY = 72;
            this.temp = new InventoryFake(((ISealConfigFilter)this.seal.getSeal()).getInv());
            for (int a = 0; a < s; ++a) {
                final int x = a % 3;
                final int y = a / 3;
                this.func_75146_a((Slot)new SlotGhost((IInventory)this.temp, a, middleX + x * 24 - sx + 8, middleY + y * 24 - sy + 8));
                ++this.t;
            }
        }
    }
    
    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a(new Slot((IInventory)inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 150 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.func_75146_a(new Slot((IInventory)inventoryPlayer, i, 8 + i * 18, 208));
        }
    }
    
    public boolean func_75145_c(final EntityPlayer var1) {
        return true;
    }
    
    public boolean func_75140_a(final EntityPlayer player, final int par2) {
        if (par2 >= 0 && par2 < this.categories.length) {
            this.category = this.categories[par2];
            this.setupCategories();
            return true;
        }
        if (this.category == 3 && this.seal.getSeal() instanceof ISealConfigToggles && par2 >= 30 && par2 < 30 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length) {
            final ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
            cp.setToggle(par2 - 30, true);
            return true;
        }
        if (this.category == 3 && this.seal.getSeal() instanceof ISealConfigToggles && par2 >= 60 && par2 < 60 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length) {
            final ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
            cp.setToggle(par2 - 60, false);
            return true;
        }
        if (this.category == 0 && par2 >= 25 && par2 <= 26) {
            this.seal.setLocked(par2 == 25);
            return true;
        }
        if (par2 >= 27 && par2 <= 28) {
            this.seal.setRedstoneSensitive(par2 == 27);
            return true;
        }
        if (this.category == 1 && this.seal.getSeal() instanceof ISealConfigFilter && par2 >= 20 && par2 <= 21) {
            final ISealConfigFilter cp2 = (ISealConfigFilter)this.seal.getSeal();
            cp2.setBlacklist(par2 == 20);
            return true;
        }
        if (par2 == 80 && this.seal.getPriority() > -5) {
            this.seal.setPriority((byte)(this.seal.getPriority() - 1));
            return true;
        }
        if (par2 == 81 && this.seal.getPriority() < 5) {
            this.seal.setPriority((byte)(this.seal.getPriority() + 1));
            return true;
        }
        if (par2 == 82 && this.seal.getColor() > 0) {
            this.seal.setColor((byte)(this.seal.getColor() - 1));
            return true;
        }
        if (par2 == 83 && this.seal.getColor() < 16) {
            this.seal.setColor((byte)(this.seal.getColor() + 1));
            return true;
        }
        if (this.seal.getSeal() instanceof ISealConfigArea) {
            if (par2 == 90 && this.seal.getArea().func_177956_o() > 1) {
                this.seal.setArea(this.seal.getArea().func_177982_a(0, -1, 0));
                return true;
            }
            if (par2 == 91 && this.seal.getArea().func_177956_o() < 8) {
                this.seal.setArea(this.seal.getArea().func_177982_a(0, 1, 0));
                return true;
            }
            if (par2 == 92 && this.seal.getArea().func_177958_n() > 1) {
                this.seal.setArea(this.seal.getArea().func_177982_a(-1, 0, 0));
                return true;
            }
            if (par2 == 93 && this.seal.getArea().func_177958_n() < 8) {
                this.seal.setArea(this.seal.getArea().func_177982_a(1, 0, 0));
                return true;
            }
            if (par2 == 94 && this.seal.getArea().func_177952_p() > 1) {
                this.seal.setArea(this.seal.getArea().func_177982_a(0, 0, -1));
                return true;
            }
            if (par2 == 95 && this.seal.getArea().func_177952_p() < 8) {
                this.seal.setArea(this.seal.getArea().func_177982_a(0, 0, 1));
                return true;
            }
        }
        return super.func_75140_a(player, par2);
    }
    
    public void func_75132_a(final IContainerListener crafting) {
        super.func_75132_a(crafting);
        crafting.func_71112_a((Container)this, 0, (int)this.seal.getPriority());
        crafting.func_71112_a((Container)this, 4, (int)this.seal.getColor());
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        for (int i = 0; i < this.field_75149_d.size(); ++i) {
            final IContainerListener icrafting = this.field_75149_d.get(i);
            if (this.lastPriority != this.seal.getPriority()) {
                icrafting.func_71112_a((Container)this, 0, (int)this.seal.getPriority());
            }
            if (this.lastAreaX != this.seal.getArea().func_177958_n()) {
                icrafting.func_71112_a((Container)this, 1, this.seal.getArea().func_177958_n());
            }
            if (this.lastAreaY != this.seal.getArea().func_177956_o()) {
                icrafting.func_71112_a((Container)this, 2, this.seal.getArea().func_177956_o());
            }
            if (this.lastAreaZ != this.seal.getArea().func_177952_p()) {
                icrafting.func_71112_a((Container)this, 3, this.seal.getArea().func_177952_p());
            }
            if (this.lastColor != this.seal.getColor()) {
                icrafting.func_71112_a((Container)this, 4, (int)this.seal.getColor());
            }
        }
        this.lastPriority = this.seal.getPriority();
        this.lastColor = this.seal.getColor();
        this.lastAreaX = this.seal.getArea().func_177958_n();
        this.lastAreaY = this.seal.getArea().func_177956_o();
        this.lastAreaZ = this.seal.getArea().func_177952_p();
        if (this.seal.getSeal() instanceof ISealConfigFilter && this.temp != null) {
            for (int a = 0; a < this.temp.func_70302_i_(); ++a) {
                ((ISealConfigFilter)this.seal.getSeal()).setFilterSlot(a, this.temp.func_70301_a(a));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
        if (par1 == 0) {
            this.seal.setPriority((byte)par2);
        }
        if (par1 == 1) {
            this.seal.setArea(new BlockPos(par2, this.seal.getArea().func_177956_o(), this.seal.getArea().func_177952_p()));
        }
        if (par1 == 2) {
            this.seal.setArea(new BlockPos(this.seal.getArea().func_177958_n(), par2, this.seal.getArea().func_177952_p()));
        }
        if (par1 == 3) {
            this.seal.setArea(new BlockPos(this.seal.getArea().func_177958_n(), this.seal.getArea().func_177956_o(), par2));
        }
        if (par1 == 4) {
            this.seal.setColor((byte)par2);
        }
    }
    
    public ItemStack func_184996_a(final int slotId, final int clickedButton, final ClickType mode, final EntityPlayer playerIn) {
        if (slotId >= 0) {
            final Slot slot = this.field_75151_b.get(slotId);
            final InventoryPlayer inventoryplayer = playerIn.field_71071_by;
            ItemStack ic = ItemStack.field_190927_a;
            if (inventoryplayer.func_70445_o() != null && !inventoryplayer.func_70445_o().func_190926_b()) {
                ic = inventoryplayer.func_70445_o().func_77946_l();
            }
            if (slot != null && slot instanceof SlotGhost) {
                final boolean filter = ((ISealConfigFilter)this.seal.getSeal()).hasStacksizeLimiters();
                if (!playerIn.field_70170_p.field_72995_K) {
                    if (clickedButton == 1) {
                        if (!filter) {
                            slot.func_75215_d(ItemStack.field_190927_a);
                        }
                        else if (ic == ItemStack.field_190927_a) {
                            if (mode == ClickType.QUICK_MOVE) {
                                slot.func_75215_d(ItemStack.field_190927_a);
                            }
                            else if (slot.func_75216_d()) {
                                slot.func_75211_c().func_190918_g(1);
                                if (slot.func_75211_c().func_190916_E() < 0) {
                                    slot.func_75215_d(ItemStack.field_190927_a);
                                }
                            }
                        }
                        else if (slot.func_75216_d() && slot.func_75211_c().func_190916_E() == 0) {
                            slot.func_75215_d(ItemStack.field_190927_a);
                        }
                        else if (slot.func_75216_d() && ItemStack.func_179545_c(ic, slot.func_75211_c()) && ItemStack.func_77970_a(ic, slot.func_75211_c())) {
                            slot.func_75211_c().func_190918_g(ic.func_190916_E());
                        }
                    }
                    else if (ic == ItemStack.field_190927_a) {
                        if (filter && slot.func_75216_d()) {
                            slot.func_75211_c().func_190917_f(1);
                        }
                    }
                    else {
                        if (!filter) {
                            ic.func_190920_e(1);
                        }
                        else if (slot.func_75216_d() && ItemStack.func_179545_c(ic, slot.func_75211_c()) && ItemStack.func_77970_a(ic, slot.func_75211_c())) {
                            ic.func_190917_f(slot.func_75211_c().func_190916_E());
                        }
                        slot.func_75215_d(ic);
                    }
                    if (slot.func_75216_d() && slot.func_75211_c().func_190916_E() < 0) {
                        slot.func_75211_c().func_190920_e(0);
                    }
                    this.func_75142_b();
                }
                return ItemStack.field_190927_a;
            }
        }
        return super.func_184996_a(slotId, clickedButton, mode, playerIn);
    }
    
    public ItemStack func_82846_b(final EntityPlayer player, final int par2) {
        ItemStack itemstack = null;
        final Slot slot = this.field_75151_b.get(par2);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (itemstack2.func_190916_E() == 0) {
                slot.func_75215_d(ItemStack.field_190927_a);
            }
            else {
                slot.func_75218_e();
            }
            if (itemstack2.func_190916_E() == itemstack.func_190916_E()) {
                return ItemStack.field_190927_a;
            }
            slot.func_190901_a(player, itemstack2);
        }
        return itemstack;
    }
}
