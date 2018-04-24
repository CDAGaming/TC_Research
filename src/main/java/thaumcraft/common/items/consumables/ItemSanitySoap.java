package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.potions.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.*;

public class ItemSanitySoap extends ItemTCBase
{
    public ItemSanitySoap() {
        super("sanity_soap", new String[0]);
        this.func_77627_a(false);
    }
    
    public int func_77626_a(final ItemStack p_77626_1_) {
        return 100;
    }
    
    public EnumAction func_77661_b(final ItemStack p_77661_1_) {
        return EnumAction.BLOCK;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World p_77659_2_, final EntityPlayer player, final EnumHand hand) {
        player.func_184598_c(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
    
    public void onUsingTick(final ItemStack stack, final EntityLivingBase player, final int count) {
        final int ticks = this.func_77626_a(stack) - count;
        if (ticks > 95) {
            player.func_184597_cx();
        }
        if (player.field_70170_p.field_72995_K) {
            if (player.field_70170_p.field_73012_v.nextFloat() < 0.2f) {
                player.field_70170_p.func_184134_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187540_ab, SoundCategory.PLAYERS, 0.1f, 1.5f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f, false);
            }
            for (int a = 0; a < 10; ++a) {
                FXDispatcher.INSTANCE.crucibleBubble((float)player.field_70165_t - 0.5f + player.field_70170_p.field_73012_v.nextFloat(), (float)player.func_174813_aQ().field_72338_b + player.field_70170_p.field_73012_v.nextFloat() * player.field_70131_O, (float)player.field_70161_v - 0.5f + player.field_70170_p.field_73012_v.nextFloat(), 1.0f, 0.8f, 0.9f);
            }
        }
    }
    
    public void func_77615_a(final ItemStack stack, final World world, final EntityLivingBase player, final int timeLeft) {
        final int qq = this.func_77626_a(stack) - timeLeft;
        if (qq > 95 && player instanceof EntityPlayer) {
            stack.func_190918_g(1);
            if (!world.field_72995_K) {
                final IPlayerWarp warp = ThaumcraftCapabilities.getWarp((EntityPlayer)player);
                int amt = 1;
                if (player.func_70644_a(PotionWarpWard.instance)) {
                    ++amt;
                }
                final int i = MathHelper.func_76128_c(player.field_70165_t);
                final int j = MathHelper.func_76128_c(player.field_70163_u);
                final int k = MathHelper.func_76128_c(player.field_70161_v);
                if (world.func_180495_p(new BlockPos(i, j, k)).func_177230_c() == BlocksTC.purifyingFluid) {
                    ++amt;
                }
                if (warp.get(IPlayerWarp.EnumWarpType.NORMAL) > 0) {
                    ThaumcraftApi.internalMethods.addWarpToPlayer((EntityPlayer)player, -amt, IPlayerWarp.EnumWarpType.NORMAL);
                }
                if (warp.get(IPlayerWarp.EnumWarpType.TEMPORARY) > 0) {
                    ThaumcraftApi.internalMethods.addWarpToPlayer((EntityPlayer)player, -warp.get(IPlayerWarp.EnumWarpType.TEMPORARY), IPlayerWarp.EnumWarpType.TEMPORARY);
                }
            }
            else {
                player.field_70170_p.func_184134_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundsTC.craftstart, SoundCategory.PLAYERS, 0.25f, 1.0f, false);
                for (int a = 0; a < 40; ++a) {
                    FXDispatcher.INSTANCE.crucibleBubble((float)player.field_70165_t - 0.5f + player.field_70170_p.field_73012_v.nextFloat() * 1.5f, (float)player.func_174813_aQ().field_72338_b + player.field_70170_p.field_73012_v.nextFloat() * player.field_70131_O, (float)player.field_70161_v - 0.5f + player.field_70170_p.field_73012_v.nextFloat() * 1.5f, 1.0f, 0.7f, 0.9f);
                }
            }
        }
    }
}
