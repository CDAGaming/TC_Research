package thaumcraft.common.tiles;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class TileThaumcraftInventory extends TileThaumcraft implements ISidedInventory, ITickable
{
    private NonNullList<ItemStack> stacks;
    protected int[] syncedSlots;
    protected String customName;
    private int[] faceSlots;
    boolean initial;
    int ticksExisted;
    
    public TileThaumcraftInventory(final int size) {
        this.stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)ItemStack.field_190927_a);
        this.syncedSlots = new int[0];
        this.initial = true;
        this.ticksExisted = 0;
        this.stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(size, (Object)ItemStack.field_190927_a);
        this.faceSlots = new int[size];
        for (int a = 0; a < size; ++a) {
            this.faceSlots[a] = a;
        }
    }
    
    public int func_70302_i_() {
        return this.stacks.size();
    }
    
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }
    
    public ItemStack func_70301_a(final int index) {
        return (ItemStack)this.getItems().get(index);
    }
    
    public ItemStack func_70298_a(final int index, final int count) {
        final ItemStack itemstack = ItemStackHelper.func_188382_a((List)this.getItems(), index, count);
        if (!itemstack.func_190926_b()) {
            this.func_70296_d();
            if (this.isSyncedSlot(index)) {
                this.syncTile(false);
            }
        }
        return itemstack;
    }
    
    public ItemStack func_70304_b(final int index) {
        final ItemStack s = ItemStackHelper.func_188383_a((List)this.getItems(), index);
        if (this.isSyncedSlot(index)) {
            this.syncTile(false);
        }
        return s;
    }
    
    public void func_70299_a(final int index, @Nullable final ItemStack stack) {
        this.getItems().set(index, (Object)stack);
        if (stack.func_190916_E() > this.func_70297_j_()) {
            stack.func_190920_e(this.func_70297_j_());
        }
        this.func_70296_d();
        if (this.isSyncedSlot(index)) {
            this.syncTile(false);
        }
    }
    
    public String func_70005_c_() {
        return this.func_145818_k_() ? this.customName : "container.thaumcraft";
    }
    
    public boolean func_145818_k_() {
        return this.customName != null && this.customName.length() > 0;
    }
    
    public ITextComponent func_145748_c_() {
        return null;
    }
    
    private boolean isSyncedSlot(final int slot) {
        for (final int s : this.syncedSlots) {
            if (s == slot) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbtCompound) {
        this.stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.func_70302_i_(), (Object)ItemStack.field_190927_a);
        final NBTTagList nbttaglist = nbtCompound.func_150295_c("ItemsSynced", 10);
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
            final byte b0 = nbttagcompound1.func_74771_c("Slot");
            if (this.isSyncedSlot(b0)) {
                this.stacks.set((int)b0, (Object)new ItemStack(nbttagcompound1));
            }
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbtCompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.stacks.size(); ++i) {
            if (!((ItemStack)this.stacks.get(i)).func_190926_b() && this.isSyncedSlot(i)) {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                ((ItemStack)this.stacks.get(i)).func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
            }
        }
        nbtCompound.func_74782_a("ItemsSynced", (NBTBase)nbttaglist);
        return nbtCompound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        if (nbtCompound.func_74764_b("CustomName")) {
            this.customName = nbtCompound.func_74779_i("CustomName");
        }
        ItemStackHelper.func_191283_b(nbtCompound, (NonNullList)(this.stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.func_70302_i_(), (Object)ItemStack.field_190927_a)));
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbtCompound) {
        super.func_189515_b(nbtCompound);
        if (this.func_145818_k_()) {
            nbtCompound.func_74778_a("CustomName", this.customName);
        }
        ItemStackHelper.func_191282_a(nbtCompound, (NonNullList)this.stacks);
        return nbtCompound;
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public boolean func_70300_a(final EntityPlayer par1EntityPlayer) {
        return this.field_145850_b.func_175625_s(this.func_174877_v()) == this && par1EntityPlayer.func_174831_c(this.func_174877_v()) <= 64.0;
    }
    
    public boolean func_94041_b(final int par1, final ItemStack stack2) {
        return true;
    }
    
    public int[] func_180463_a(final EnumFacing par1) {
        return this.faceSlots;
    }
    
    public void func_174889_b(final EntityPlayer player) {
    }
    
    public void func_174886_c(final EntityPlayer player) {
    }
    
    public int func_174887_a_(final int id) {
        return 0;
    }
    
    public void func_174885_b(final int id, final int value) {
    }
    
    public int func_174890_g() {
        return 0;
    }
    
    public void func_174888_l() {
    }
    
    public boolean func_180462_a(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return this.func_94041_b(par1, stack2);
    }
    
    public boolean func_180461_b(final int par1, final ItemStack stack2, final EnumFacing par3) {
        return true;
    }
    
    public boolean func_191420_l() {
        for (final ItemStack itemstack : this.stacks) {
            if (!itemstack.func_190926_b()) {
                return false;
            }
        }
        return true;
    }
    
    public void func_73660_a() {
        if (this.ticksExisted++ > 50 && this.initial) {
            this.initial = false;
            if (!this.field_145850_b.field_72995_K && this.syncedSlots.length > 0) {
                this.syncTile(false);
            }
        }
    }
}
