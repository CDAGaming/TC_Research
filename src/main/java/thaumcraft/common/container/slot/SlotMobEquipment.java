package thaumcraft.common.container.slot;

import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class SlotMobEquipment extends Slot
{
    EntityLiving entity;
    
    public SlotMobEquipment(final EntityLiving entity, final int par3, final int par4, final int par5) {
        super((IInventory)null, par3, par4, par5);
        this.entity = entity;
    }
    
    public ItemStack func_75211_c() {
        return this.entity.func_184586_b(EnumHand.MAIN_HAND);
    }
    
    public void func_75215_d(final ItemStack stack) {
        this.entity.func_184611_a(EnumHand.MAIN_HAND, stack);
        if (stack != null && !stack.func_190926_b() && stack.func_190916_E() > this.func_75219_a()) {
            stack.func_190920_e(this.func_75219_a());
        }
        this.func_75218_e();
    }
    
    public void func_75218_e() {
    }
    
    public int func_75219_a() {
        return 64;
    }
    
    public ItemStack func_75209_a(final int amount) {
        if (this.func_75211_c().func_190926_b()) {
            return ItemStack.field_190927_a;
        }
        if (this.func_75211_c().func_190916_E() <= amount) {
            final ItemStack itemstack = this.func_75211_c();
            this.func_75215_d(ItemStack.field_190927_a);
            return itemstack;
        }
        final ItemStack itemstack = this.func_75211_c().func_77979_a(amount);
        if (this.func_75211_c().func_190916_E() == 0) {
            this.func_75215_d(ItemStack.field_190927_a);
        }
        return itemstack;
    }
    
    public boolean func_75217_a(final IInventory inv, final int slotIn) {
        return slotIn == this.getSlotIndex();
    }
}
