package thaumcraft.common.container;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.slot.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import thaumcraft.common.lib.utils.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraftforge.items.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;

public class ContainerLogistics extends Container implements IInventoryChangedListener
{
    private World worldObj;
    EntityPlayer player;
    public IInventory input;
    TreeMap<String, ItemStack> items;
    int lastTotal;
    public int start;
    public int end;
    public String searchText;
    int lastStart;
    int lastEnd;
    public boolean updated;
    
    public ContainerLogistics(final InventoryPlayer iinventory, final World par2World) {
        this.player = null;
        this.input = (IInventory)new InventoryLogistics((IInventoryChangedListener)this);
        this.items = new TreeMap<String, ItemStack>();
        this.lastTotal = 0;
        this.start = 0;
        this.end = 0;
        this.searchText = "";
        this.lastStart = 0;
        this.lastEnd = 0;
        this.updated = false;
        this.worldObj = par2World;
        this.player = iinventory.field_70458_d;
        for (int a = 0; a < this.input.func_70302_i_(); ++a) {
            this.func_75146_a((Slot)new SlotGhostFull(this.input, a, 19 + a % 9 * 19, 19 + a / 9 * 19));
        }
        this.refreshItemList(true);
    }
    
    public void refreshItemList(final boolean full) {
        int newTotal = this.lastTotal;
        final TreeMap<String, ItemStack> ti = new TreeMap<String, ItemStack>();
        if (full) {
            newTotal = 0;
            final CopyOnWriteArrayList<SealEntity> seals = SealHandler.getSealsInRange(this.worldObj, this.player.func_180425_c(), 32);
            for (final SealEntity seal : seals) {
                if (seal.getSeal() instanceof SealProvide && seal.getOwner().equals(this.player.func_110124_au().toString())) {
                    final IItemHandler handler = InventoryUtils.getItemHandlerAt(this.worldObj, seal.getSealPos().pos, seal.getSealPos().face);
                    for (int slot = 0; slot < handler.getSlots(); ++slot) {
                        final ItemStack stack = handler.getStackInSlot(slot);
                        if (((SealProvide)seal.getSeal()).matchesFilters(stack)) {
                            if (this.searchText.isEmpty() || stack.func_82833_r().toLowerCase().contains(this.searchText.toLowerCase())) {
                                final String key = stack.func_82833_r() + stack.func_77952_i() + stack.func_77978_p();
                                if (ti.containsKey(key)) {
                                    stack.func_190917_f(ti.get(key).func_190916_E());
                                }
                                ti.put(key, stack);
                                newTotal += stack.func_190916_E();
                            }
                        }
                    }
                }
            }
        }
        if (this.lastTotal != newTotal || this.start != this.lastStart) {
            this.lastTotal = newTotal;
            if (full) {
                this.items = ti;
            }
            this.input.func_174888_l();
            int j = 0;
            int q = 0;
            for (final String key2 : this.items.keySet()) {
                if (++j <= this.start * 9) {
                    continue;
                }
                this.input.func_70299_a(q, (ItemStack)this.items.get(key2));
                if (++q >= this.input.func_70302_i_()) {
                    break;
                }
            }
            this.end = this.items.size() / 9 - 8;
        }
    }
    
    public void func_75132_a(final IContainerListener listener) {
        super.func_75132_a(listener);
        listener.func_175173_a((Container)this, this.input);
        listener.func_71112_a((Container)this, 0, this.start);
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        for (int i = 0; i < this.field_75149_d.size(); ++i) {
            final IContainerListener icrafting = this.field_75149_d.get(i);
            if (this.lastStart != this.start) {
                icrafting.func_71112_a((Container)this, 0, this.start);
            }
            if (this.lastEnd != this.end) {
                icrafting.func_71112_a((Container)this, 1, this.end);
            }
        }
        this.lastStart = this.start;
        this.lastEnd = this.end;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
        if (par1 == 0) {
            this.start = par2;
            this.updated = true;
        }
        if (par1 == 1) {
            this.end = par2;
            this.updated = true;
        }
    }
    
    public boolean func_75140_a(final EntityPlayer par1EntityPlayer, final int par2) {
        if (par2 == 22) {
            this.refreshItemList(true);
            return true;
        }
        if (par2 == 0) {
            if (this.start < this.items.size() / 9 - 8) {
                ++this.start;
                this.refreshItemList(false);
            }
            return true;
        }
        if (par2 == 1) {
            if (this.start > 0) {
                --this.start;
                this.refreshItemList(false);
            }
            return true;
        }
        if (par2 >= 100) {
            final int s = par2 - 100;
            if (s >= 0 && s <= this.items.size() / 9 - 8) {
                this.start = s;
                this.refreshItemList(false);
            }
            return true;
        }
        return super.func_75140_a(par1EntityPlayer, par2);
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int slot) {
        ItemStack stack = ItemStack.field_190927_a;
        final Slot slotObject = this.field_75151_b.get(slot);
        if (slotObject != null && slotObject.func_75216_d()) {
            final ItemStack stackInSlot = slotObject.func_75211_c();
            stack = stackInSlot.func_77946_l();
            if (slot < this.input.func_70302_i_()) {
                if (!this.input.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, this.input.func_70302_i_(), this.field_75151_b.size(), true)) {
                    return ItemStack.field_190927_a;
                }
            }
            else if (!this.input.func_94041_b(slot, stackInSlot) || !this.func_75135_a(stackInSlot, 0, this.input.func_70302_i_(), false)) {
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
    
    public void func_76316_a(final IInventory invBasic) {
        this.func_75142_b();
    }
}
