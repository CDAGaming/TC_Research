package thaumcraft.common.entities.construct.golem;

import thaumcraft.common.items.*;
import thaumcraft.api.golems.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import thaumcraft.common.lib.*;
import thaumcraft.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.util.math.*;

public class ItemGolemBell extends ItemTCBase implements ISealDisplayer
{
    public ItemGolemBell() {
        super("golem_bell", new String[0]);
        this.func_77627_a(false);
        this.func_77625_d(1);
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        playerIn.func_184609_a(hand);
        if (!worldIn.field_72995_K) {
            final RayTraceResult mop = RayTracer.retrace(playerIn);
            if (mop != null && (mop.field_72313_a == RayTraceResult.Type.BLOCK || mop.field_72313_a == RayTraceResult.Type.ENTITY)) {
                final ISealEntity se = getSeal(playerIn);
                if (se != null) {
                    if (playerIn.func_70093_af()) {
                        SealHandler.removeSealEntity(playerIn.field_70170_p, se.getSealPos(), false);
                        worldIn.func_184133_a((EntityPlayer)null, se.getSealPos().pos, SoundsTC.zap, SoundCategory.BLOCKS, 0.5f, 1.0f);
                    }
                    else {
                        playerIn.openGui((Object)Thaumcraft.instance, 18, playerIn.field_70170_p, se.getSealPos().pos.func_177958_n(), se.getSealPos().pos.func_177956_o(), se.getSealPos().pos.func_177952_p());
                    }
                }
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)playerIn.func_184586_b(hand));
            }
            if (playerIn.func_70093_af() && ThaumcraftCapabilities.knowsResearch(playerIn, "GOLEMLOGISTICS")) {
                playerIn.openGui((Object)Thaumcraft.instance, 20, playerIn.field_70170_p, (int)playerIn.field_70165_t, (int)playerIn.field_70163_u, (int)playerIn.field_70161_v);
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)playerIn.func_184586_b(hand));
            }
        }
        else {
            playerIn.func_184185_a(SoundEvents.field_187604_bf, 0.6f, 1.0f + worldIn.field_73012_v.nextFloat() * 0.1f);
        }
        return (ActionResult<ItemStack>)super.func_77659_a(worldIn, playerIn, hand);
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        player.func_184609_a(hand);
        if (!world.field_72995_K) {
            final ISealEntity se = SealHandler.getSealEntity(world.field_73011_w.getDimension(), new SealPos(pos, side));
            if (se != null) {
                if (player.func_70093_af()) {
                    SealHandler.removeSealEntity(world, se.getSealPos(), false);
                    world.func_184133_a((EntityPlayer)null, pos, SoundsTC.zap, SoundCategory.BLOCKS, 0.5f, 1.0f);
                }
                else {
                    player.openGui((Object)Thaumcraft.instance, 18, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
                }
                return EnumActionResult.SUCCESS;
            }
            if (player.func_70093_af() && ThaumcraftCapabilities.knowsResearch(player, "GOLEMLOGISTICS")) {
                player.openGui((Object)Thaumcraft.instance, 20, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }
    
    public static ISealEntity getSeal(final EntityPlayer playerIn) {
        final float f = playerIn.field_70125_A;
        final float f2 = playerIn.field_70177_z;
        final double d0 = playerIn.field_70165_t;
        final double d2 = playerIn.field_70163_u + playerIn.func_70047_e();
        final double d3 = playerIn.field_70161_v;
        final Vec3d vec0 = new Vec3d(d0, d2, d3);
        final float f3 = MathHelper.func_76134_b(-f2 * 0.017453292f - 3.1415927f);
        final float f4 = MathHelper.func_76126_a(-f2 * 0.017453292f - 3.1415927f);
        final float f5 = -MathHelper.func_76134_b(-f * 0.017453292f);
        final float f6 = MathHelper.func_76126_a(-f * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final double d4 = 5.0;
        final Vec3d vec2 = vec0.func_72441_c(f7 * d4, f6 * d4, f8 * d4);
        final Vec3d vec3 = new Vec3d(f7 * d4, f6 * d4, f8 * d4);
        Vec3d vec4 = vec0.func_72441_c(vec3.field_72450_a / 10.0, vec3.field_72448_b / 10.0, vec3.field_72449_c / 10.0);
        for (int a = 0; a < vec3.func_72433_c() * 10.0; ++a) {
            final BlockPos pos = new BlockPos(vec4);
            final RayTraceResult mop = collisionRayTrace(playerIn.field_70170_p, pos, vec0, vec2);
            if (mop != null) {
                final ISealEntity se = SealHandler.getSealEntity(playerIn.field_70170_p.field_73011_w.getDimension(), new SealPos(pos, mop.field_178784_b));
                if (se != null) {
                    return se;
                }
            }
            vec4 = vec4.func_72441_c(vec3.field_72450_a / 10.0, vec3.field_72448_b / 10.0, vec3.field_72449_c / 10.0);
        }
        return null;
    }
    
    private static boolean isVecInsideYZBounds(final Vec3d point, final BlockPos pos) {
        return point != null && (point.field_72448_b >= pos.func_177956_o() && point.field_72448_b <= pos.func_177956_o() + 1 && point.field_72449_c >= pos.func_177952_p() && point.field_72449_c <= pos.func_177952_p() + 1);
    }
    
    private static boolean isVecInsideXZBounds(final Vec3d point, final BlockPos pos) {
        return point != null && (point.field_72450_a >= pos.func_177958_n() && point.field_72450_a <= pos.func_177958_n() + 1 && point.field_72449_c >= pos.func_177952_p() && point.field_72449_c <= pos.func_177952_p() + 1);
    }
    
    private static boolean isVecInsideXYBounds(final Vec3d point, final BlockPos pos) {
        return point != null && (point.field_72450_a >= pos.func_177958_n() && point.field_72450_a <= pos.func_177958_n() + 1 && point.field_72448_b >= pos.func_177956_o() && point.field_72448_b <= pos.func_177956_o() + 1);
    }
    
    private static RayTraceResult collisionRayTrace(final World worldIn, final BlockPos pos, final Vec3d start, final Vec3d end) {
        Vec3d vec3 = start.func_72429_b(end, (double)pos.func_177958_n());
        Vec3d vec4 = start.func_72429_b(end, (double)(pos.func_177958_n() + 1));
        Vec3d vec5 = start.func_72435_c(end, (double)pos.func_177956_o());
        Vec3d vec6 = start.func_72435_c(end, (double)(pos.func_177956_o() + 1));
        Vec3d vec7 = start.func_72434_d(end, (double)pos.func_177952_p());
        Vec3d vec8 = start.func_72434_d(end, (double)(pos.func_177952_p() + 1));
        if (!isVecInsideYZBounds(vec3, pos)) {
            vec3 = null;
        }
        if (!isVecInsideYZBounds(vec4, pos)) {
            vec4 = null;
        }
        if (!isVecInsideXZBounds(vec5, pos)) {
            vec5 = null;
        }
        if (!isVecInsideXZBounds(vec6, pos)) {
            vec6 = null;
        }
        if (!isVecInsideXYBounds(vec7, pos)) {
            vec7 = null;
        }
        if (!isVecInsideXYBounds(vec8, pos)) {
            vec8 = null;
        }
        Vec3d vec9 = null;
        if (vec3 != null && (vec9 == null || start.func_72436_e(vec3) < start.func_72436_e(vec9))) {
            vec9 = vec3;
        }
        if (vec4 != null && (vec9 == null || start.func_72436_e(vec4) < start.func_72436_e(vec9))) {
            vec9 = vec4;
        }
        if (vec5 != null && (vec9 == null || start.func_72436_e(vec5) < start.func_72436_e(vec9))) {
            vec9 = vec5;
        }
        if (vec6 != null && (vec9 == null || start.func_72436_e(vec6) < start.func_72436_e(vec9))) {
            vec9 = vec6;
        }
        if (vec7 != null && (vec9 == null || start.func_72436_e(vec7) < start.func_72436_e(vec9))) {
            vec9 = vec7;
        }
        if (vec8 != null && (vec9 == null || start.func_72436_e(vec8) < start.func_72436_e(vec9))) {
            vec9 = vec8;
        }
        if (vec9 == null) {
            return null;
        }
        EnumFacing enumfacing = null;
        if (vec9 == vec3) {
            enumfacing = EnumFacing.WEST;
        }
        if (vec9 == vec4) {
            enumfacing = EnumFacing.EAST;
        }
        if (vec9 == vec5) {
            enumfacing = EnumFacing.DOWN;
        }
        if (vec9 == vec6) {
            enumfacing = EnumFacing.UP;
        }
        if (vec9 == vec7) {
            enumfacing = EnumFacing.NORTH;
        }
        if (vec9 == vec8) {
            enumfacing = EnumFacing.SOUTH;
        }
        return new RayTraceResult(vec9.func_72441_c((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()), enumfacing, pos);
    }
}
