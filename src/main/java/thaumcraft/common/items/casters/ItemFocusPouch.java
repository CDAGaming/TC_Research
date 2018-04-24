package thaumcraft.common.items.casters;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import baubles.api.*;
import net.minecraft.entity.*;

public class ItemFocusPouch extends ItemTCBase implements IBauble
{
    public ItemFocusPouch() {
        super("focus_pouch", new String[0]);
        this.func_77625_d(1);
        this.func_77627_a(false);
        this.func_77656_e(0);
    }
    
    public boolean func_77651_p() {
        return true;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_77636_d(final ItemStack stack1) {
        return false;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        if (!worldIn.field_72995_K) {
            playerIn.openGui((Object)Thaumcraft.instance, 5, worldIn, MathHelper.func_76128_c(playerIn.field_70165_t), MathHelper.func_76128_c(playerIn.field_70163_u), MathHelper.func_76128_c(playerIn.field_70161_v));
        }
        return (ActionResult<ItemStack>)super.func_77659_a(worldIn, playerIn, hand);
    }
    
    public NonNullList<ItemStack> getInventory(final ItemStack item) {
        final NonNullList<ItemStack> stackList = (NonNullList<ItemStack>)NonNullList.func_191197_a(18, (Object)ItemStack.field_190927_a);
        if (item.func_77942_o()) {
            final NBTTagList var2 = item.func_77978_p().func_150295_c("Inventory", 10);
            for (int var3 = 0; var3 < var2.func_74745_c(); ++var3) {
                final NBTTagCompound var4 = var2.func_150305_b(var3);
                final int var5 = var4.func_74771_c("Slot") & 0xFF;
                if (var5 >= 0 && var5 < stackList.size()) {
                    stackList.add(var5, (Object)new ItemStack(var4));
                }
            }
        }
        return stackList;
    }
    
    public void setInventory(final ItemStack item, final NonNullList<ItemStack> stackList) {
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < stackList.size(); ++var3) {
            if (!((ItemStack)stackList.get(var3)).func_190926_b()) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.func_74774_a("Slot", (byte)var3);
                ((ItemStack)stackList.get(var3)).func_77955_b(var4);
                var2.func_74742_a((NBTBase)var4);
            }
        }
        item.func_77983_a("Inventory", (NBTBase)var2);
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.BELT;
    }
    
    public void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    public void onEquipped(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    public void onUnequipped(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    public boolean canEquip(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
    
    public boolean canUnequip(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
}
