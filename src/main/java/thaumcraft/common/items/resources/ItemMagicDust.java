package thaumcraft.common.items.resources;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.api.crafting.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemMagicDust extends ItemTCBase
{
    public ItemMagicDust() {
        super("salis_mundus", new String[0]);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        if (!player.func_175151_a(pos, side, player.func_184586_b(hand))) {
            return EnumActionResult.FAIL;
        }
        if (player.func_70093_af()) {
            return EnumActionResult.PASS;
        }
        player.func_184609_a(hand);
        for (final IDustTrigger trigger : IDustTrigger.triggers) {
            final IDustTrigger.Placement place = trigger.getValidFace(world, player, pos, side);
            if (place != null) {
                if (!player.field_71075_bZ.field_75098_d) {
                    player.func_184586_b(hand).func_190918_g(1);
                }
                trigger.execute(world, player, pos, place, side);
                if (world.field_72995_K) {
                    this.doSparkles(player, world, pos, hitX, hitY, hitZ, hand, trigger, place);
                    break;
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
    
    private void doSparkles(final EntityPlayer player, final World world, final BlockPos pos, final float hitX, final float hitY, final float hitZ, final EnumHand hand, final IDustTrigger trigger, final IDustTrigger.Placement place) {
        final Vec3d v1 = EntityUtils.posToHand((Entity)player, hand);
        Vec3d v2 = new Vec3d((Vec3i)pos);
        v2 = v2.func_72441_c(0.5, 0.5, 0.5);
        v2 = v2.func_178788_d(v1);
        for (int cnt = 50, a = 0; a < cnt; ++a) {
            final boolean floaty = a < cnt / 3;
            final float r = MathHelper.func_76136_a(world.field_73012_v, 255, 255) / 255.0f;
            final float g = MathHelper.func_76136_a(world.field_73012_v, 189, 255) / 255.0f;
            final float b = MathHelper.func_76136_a(world.field_73012_v, 64, 255) / 255.0f;
            FXDispatcher.INSTANCE.drawSimpleSparkle(world.field_73012_v, v1.field_72450_a, v1.field_72448_b, v1.field_72449_c, v2.field_72450_a / 6.0 + world.field_73012_v.nextGaussian() * 0.05, v2.field_72448_b / 6.0 + world.field_73012_v.nextGaussian() * 0.05 + (floaty ? 0.05 : 0.15), v2.field_72449_c / 6.0 + world.field_73012_v.nextGaussian() * 0.05, 0.5f, r, g, b, world.field_73012_v.nextInt(5), floaty ? (0.3f + world.field_73012_v.nextFloat() * 0.5f) : 0.85f, floaty ? 0.2f : 0.5f, false, 16);
        }
        world.func_184134_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundsTC.dust, SoundCategory.PLAYERS, 0.33f, 1.0f + (float)world.field_73012_v.nextGaussian() * 0.05f, false);
        final List<BlockPos> sparkles = trigger.sparkle(world, player, pos, place);
        if (sparkles != null) {
            final Vec3d v3 = new Vec3d((Vec3i)pos).func_72441_c((double)hitX, (double)hitY, (double)hitZ);
            for (final BlockPos p : sparkles) {
                FXDispatcher.INSTANCE.drawBlockSparkles(p, v3);
            }
        }
    }
}
