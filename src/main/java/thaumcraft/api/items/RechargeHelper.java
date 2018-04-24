package thaumcraft.api.items;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.aura.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class RechargeHelper
{
    public static final String NBT_TAG = "tc.charge";
    
    public static float rechargeItem(final World world, final ItemStack is, final BlockPos pos, final EntityPlayer player, int amt) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return 0.0f;
        }
        final IRechargable chargeItem = (IRechargable)is.func_77973_b();
        if (player != null && AuraHelper.shouldPreserveAura(world, player, pos)) {
            return 0.0f;
        }
        amt = Math.min(amt, chargeItem.getMaxCharge(is, (EntityLivingBase)player) - getCharge(is));
        final int drained = (int)AuraHelper.drainVis(world, pos, amt, false);
        if (drained > 0) {
            addCharge(is, (EntityLivingBase)player, drained);
            return drained;
        }
        return 0.0f;
    }
    
    public static float rechargeItemBlindly(final ItemStack is, final EntityPlayer player, int amt) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return 0.0f;
        }
        final IRechargable chargeItem = (IRechargable)is.func_77973_b();
        amt = Math.min(amt, chargeItem.getMaxCharge(is, (EntityLivingBase)player) - getCharge(is));
        if (amt > 0) {
            addCharge(is, (EntityLivingBase)player, amt);
        }
        return amt;
    }
    
    private static void addCharge(final ItemStack is, final EntityLivingBase player, final int amt) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return;
        }
        final IRechargable chargeItem = (IRechargable)is.func_77973_b();
        final int amount = Math.min(chargeItem.getMaxCharge(is, player), amt + getCharge(is));
        is.func_77983_a("tc.charge", (NBTBase)new NBTTagInt(amount));
    }
    
    public static int getCharge(final ItemStack is) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return -1;
        }
        if (is.func_77942_o()) {
            return is.func_77978_p().func_74762_e("tc.charge");
        }
        return 0;
    }
    
    public static float getChargePercentage(final ItemStack is, final EntityPlayer player) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return -1.0f;
        }
        final float c = getCharge(is);
        final float m = ((IRechargable)is.func_77973_b()).getMaxCharge(is, (EntityLivingBase)player);
        return c / m;
    }
    
    public static boolean consumeCharge(final ItemStack is, final EntityLivingBase player, final int amt) {
        if (is == null || is.func_190926_b() || !(is.func_77973_b() instanceof IRechargable)) {
            return false;
        }
        if (is.func_77942_o()) {
            int charge = is.func_77978_p().func_74762_e("tc.charge");
            if (charge >= amt) {
                charge -= amt;
                is.func_77983_a("tc.charge", (NBTBase)new NBTTagInt(charge));
                return true;
            }
        }
        return false;
    }
}
