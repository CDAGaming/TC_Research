package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import java.util.*;
import net.minecraft.item.*;
import baubles.api.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;

public class ItemCloudRing extends ItemTCBase implements IBauble
{
    public static HashMap<String, Boolean> jumpList;
    
    public ItemCloudRing() {
        super("cloud_ring", new String[0]);
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.RING;
    }
    
    public void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
        player.field_70143_R /= 2.0f;
        if (player.func_130014_f_().field_72995_K) {
            final boolean spacePressed = Minecraft.func_71410_x().field_71474_y.field_74314_A.func_151468_f();
            if (spacePressed && !ItemCloudRing.jumpList.containsKey(player.func_70005_c_())) {
                ItemCloudRing.jumpList.put(player.func_70005_c_(), true);
            }
            if (spacePressed && !player.field_70122_E && player.field_70773_bE == 0 && ItemCloudRing.jumpList.containsKey(player.func_70005_c_()) && ItemCloudRing.jumpList.get(player.func_70005_c_())) {
                FXDispatcher.INSTANCE.drawBamf(player.field_70165_t, player.field_70163_u + 0.5, player.field_70161_v, 1.0f, 1.0f, 1.0f, false, false, EnumFacing.UP);
                player.func_130014_f_().func_184134_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187730_dW, SoundCategory.PLAYERS, 0.1f, 1.0f + (float)player.func_130014_f_().field_73012_v.nextGaussian() * 0.05f, false);
                ItemCloudRing.jumpList.put(player.func_70005_c_(), false);
                player.field_70181_x = 0.75;
                if (player.func_70644_a(MobEffects.field_76430_j)) {
                    player.field_70181_x += (player.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1f;
                }
                if (player.func_70051_ag()) {
                    final float f = player.field_70177_z * 0.017453292f;
                    player.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
                    player.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
                }
                player.field_70143_R = 0.0f;
                ForgeHooks.onLivingJump(player);
            }
            if (player.field_70122_E && player.field_70773_bE == 0) {
                ItemCloudRing.jumpList.remove(player.func_70005_c_());
            }
        }
    }
    
    static {
        ItemCloudRing.jumpList = new HashMap<String, Boolean>();
    }
}
