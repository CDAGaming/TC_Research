package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ItemBottleTaint extends ItemTCBase
{
    public ItemBottleTaint() {
        super("bottle_taint", new String[0]);
        this.field_77777_bU = 8;
        this.func_77656_e(0);
        this.func_77637_a(ConfigItems.TABTC);
        this.func_77627_a(false);
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!player.field_71075_bZ.field_75098_d) {
            player.func_184586_b(hand).func_190918_g(1);
        }
        player.func_184185_a(SoundEvents.field_187511_aA, 0.5f, 0.4f / (ItemBottleTaint.field_77697_d.nextFloat() * 0.4f + 0.8f));
        if (!world.field_72995_K) {
            final EntityBottleTaint entityBottle = new EntityBottleTaint(world, (EntityLivingBase)player);
            entityBottle.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, 0.0f, 0.66f, 1.0f);
            world.func_72838_d((Entity)entityBottle);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
}
