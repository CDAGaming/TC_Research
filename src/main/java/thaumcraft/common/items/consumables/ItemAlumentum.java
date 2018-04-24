package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ItemAlumentum extends ItemTCBase
{
    public ItemAlumentum() {
        super("alumentum", new String[0]);
        this.func_77625_d(64);
        this.func_77627_a(true);
        this.func_77656_e(0);
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!player.field_71075_bZ.field_75098_d) {
            player.func_184586_b(hand).func_190918_g(1);
        }
        player.func_184185_a(SoundEvents.field_187511_aA, 0.3f, 0.4f / (ItemAlumentum.field_77697_d.nextFloat() * 0.4f + 0.8f));
        if (!world.field_72995_K) {
            final EntityAlumentum alumentum = new EntityAlumentum(world, (EntityLivingBase)player);
            alumentum.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, -5.0f, 0.4f, 2.0f);
            world.func_72838_d((Entity)alumentum);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
}
