package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;

public class ItemPrimordialPearl extends ItemTCBase
{
    public ItemPrimordialPearl() {
        super("primordial_pearl", new String[0]);
        this.field_77777_bU = 1;
        this.func_77656_e(8);
        this.func_185043_a(new ResourceLocation("type"), (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                if (stack.func_77952_i() < 3) {
                    return 0.0f;
                }
                if (stack.func_77952_i() < 6) {
                    return 1.0f;
                }
                return 2.0f;
            }
        });
    }
    
    public boolean func_82789_a(final ItemStack toRepair, final ItemStack repair) {
        return false;
    }
    
    @Override
    public String func_77667_c(final ItemStack stack) {
        if (stack.func_77952_i() < 3) {
            return super.func_77658_a() + ".pearl";
        }
        if (stack.func_77952_i() < 6) {
            return super.func_77658_a() + ".nodule";
        }
        return super.func_77658_a() + ".mote";
    }
    
    public ItemStack getContainerItem(final ItemStack itemStack) {
        if (!this.hasContainerItem(itemStack)) {
            return ItemStack.field_190927_a;
        }
        return new ItemStack(itemStack.func_77973_b(), itemStack.func_190916_E(), itemStack.func_77952_i() + 1);
    }
    
    public boolean hasContainerItem(final ItemStack stack) {
        return stack.func_77952_i() < 7;
    }
    
    public EnumRarity func_77613_e(final ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
