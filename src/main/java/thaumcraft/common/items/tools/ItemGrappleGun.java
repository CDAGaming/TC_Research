package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import thaumcraft.api.items.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class ItemGrappleGun extends ItemTCBase implements IRechargable
{
    public ItemGrappleGun() {
        super("grapple_gun", new String[0]);
        this.func_77625_d(1);
        this.func_185043_a(new ResourceLocation("type"), (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                return (stack.func_77942_o() && stack.func_77978_p().func_74771_c("loaded") == 1) ? 1.0f : 0.0f;
            }
        });
    }
    
    @Override
    public int getMaxCharge(final ItemStack stack, final EntityLivingBase player) {
        return 100;
    }
    
    @Override
    public EnumChargeDisplay showInHud(final ItemStack stack, final EntityLivingBase player) {
        return EnumChargeDisplay.NORMAL;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        if (!EntityGrapple.grapples.containsKey(entityIn.func_145782_y()) && stack.func_77942_o() && stack.func_77978_p().func_74771_c("loaded") == 1) {
            stack.func_77983_a("loaded", (NBTBase)new NBTTagByte((byte)0));
        }
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        player.func_184185_a(SoundsTC.ice, 3.0f, 0.8f + world.field_73012_v.nextFloat() * 0.1f);
        if (!world.field_72995_K && RechargeHelper.getCharge(player.func_184586_b(hand)) > 0) {
            final EntityGrapple grapple = new EntityGrapple(world, (EntityLivingBase)player, 3, true, hand);
            grapple.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, -5.0f, 1.5f, 0.0f);
            final double px = -MathHelper.func_76134_b((player.field_70177_z - 0.5f) / 180.0f * 3.141593f) * 0.2f * ((grapple.hand == EnumHand.MAIN_HAND) ? 1 : -1);
            final double pz = -MathHelper.func_76126_a((player.field_70177_z - 0.5f) / 180.0f * 3.141593f) * 0.3f * ((grapple.hand == EnumHand.MAIN_HAND) ? 1 : -1);
            final Vec3d vl = player.func_70040_Z();
            final EntityGrapple entityGrapple = grapple;
            entityGrapple.field_70165_t += px + vl.field_72450_a;
            final EntityGrapple entityGrapple2 = grapple;
            entityGrapple2.field_70161_v += pz + vl.field_72448_b;
            if (world.func_72838_d((Entity)grapple)) {
                RechargeHelper.consumeCharge(player.func_184586_b(hand), (EntityLivingBase)player, 1);
                player.func_184586_b(hand).func_77983_a("loaded", (NBTBase)new NBTTagByte((byte)1));
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
    
    public boolean shouldCauseReequipAnimation(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged) {
        if (oldStack.func_77973_b() != null && oldStack.func_77973_b() == this && newStack.func_77973_b() != null && newStack.func_77973_b() == this && !ItemStack.func_77970_a(oldStack, newStack)) {
            final boolean b1 = !oldStack.func_77942_o() || oldStack.func_77978_p().func_74771_c("loaded") == 0;
            final boolean b2 = !newStack.func_77942_o() || newStack.func_77978_p().func_74771_c("loaded") == 0;
            return b1 != b2;
        }
        return newStack.func_77973_b() != oldStack.func_77973_b();
    }
}
