package thaumcraft.codechicken.lib.raytracer;

import thaumcraft.codechicken.lib.math.*;
import thaumcraft.codechicken.lib.vec.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;

public class RayTracer
{
    private Vector3 vec;
    private Vector3 vec2;
    private Vector3 s_vec;
    private double s_dist;
    private int s_side;
    private IndexedCuboid6 c_cuboid;
    private static ThreadLocal<RayTracer> t_inst;
    
    public RayTracer() {
        this.vec = new Vector3();
        this.vec2 = new Vector3();
        this.s_vec = new Vector3();
    }
    
    public static RayTracer instance() {
        RayTracer inst = RayTracer.t_inst.get();
        if (inst == null) {
            RayTracer.t_inst.set(inst = new RayTracer());
        }
        return inst;
    }
    
    private void traceSide(final int side, final Vector3 start, final Vector3 end, final Cuboid6 cuboid) {
        this.vec.set(start);
        Vector3 hit = null;
        switch (side) {
            case 0: {
                hit = this.vec.XZintercept(end, cuboid.min.y);
                break;
            }
            case 1: {
                hit = this.vec.XZintercept(end, cuboid.max.y);
                break;
            }
            case 2: {
                hit = this.vec.XYintercept(end, cuboid.min.z);
                break;
            }
            case 3: {
                hit = this.vec.XYintercept(end, cuboid.max.z);
                break;
            }
            case 4: {
                hit = this.vec.YZintercept(end, cuboid.min.x);
                break;
            }
            case 5: {
                hit = this.vec.YZintercept(end, cuboid.max.x);
                break;
            }
        }
        if (hit == null) {
            return;
        }
        switch (side) {
            case 0:
            case 1: {
                if (!MathHelper.between(cuboid.min.x, hit.x, cuboid.max.x) || !MathHelper.between(cuboid.min.z, hit.z, cuboid.max.z)) {
                    return;
                }
                break;
            }
            case 2:
            case 3: {
                if (!MathHelper.between(cuboid.min.x, hit.x, cuboid.max.x) || !MathHelper.between(cuboid.min.y, hit.y, cuboid.max.y)) {
                    return;
                }
                break;
            }
            case 4:
            case 5: {
                if (!MathHelper.between(cuboid.min.y, hit.y, cuboid.max.y) || !MathHelper.between(cuboid.min.z, hit.z, cuboid.max.z)) {
                    return;
                }
                break;
            }
        }
        final double dist = this.vec2.set(hit).subtract(start).magSquared();
        if (dist < this.s_dist) {
            this.s_side = side;
            this.s_dist = dist;
            this.s_vec.set(this.vec);
        }
    }
    
    private boolean rayTraceCuboid(final Vector3 start, final Vector3 end, final Cuboid6 cuboid) {
        this.s_dist = Double.MAX_VALUE;
        this.s_side = -1;
        for (int i = 0; i < 6; ++i) {
            this.traceSide(i, start, end, cuboid);
        }
        return this.s_side >= 0;
    }
    
    public ExtendedMOP rayTraceCuboid(final Vector3 start, final Vector3 end, final Cuboid6 cuboid, final BlockCoord pos, final Object data) {
        return this.rayTraceCuboid(start, end, cuboid) ? new ExtendedMOP(this.s_vec, this.s_side, pos, data, this.s_dist) : null;
    }
    
    public ExtendedMOP rayTraceCuboid(final Vector3 start, final Vector3 end, final Cuboid6 cuboid, final Entity entity, final Object data) {
        return this.rayTraceCuboid(start, end, cuboid) ? new ExtendedMOP(entity, this.s_vec, data, this.s_dist) : null;
    }
    
    public void rayTraceCuboids(final Vector3 start, final Vector3 end, final List<IndexedCuboid6> cuboids, final BlockCoord pos, final Block block, final List<ExtendedMOP> hitList) {
        for (final IndexedCuboid6 cuboid : cuboids) {
            final ExtendedMOP mop = this.rayTraceCuboid(start, end, cuboid, pos, cuboid.data);
            if (mop != null) {
                hitList.add(mop);
            }
        }
    }
    
    public static RayTraceResult retraceBlock(final World world, final EntityPlayer player, final BlockPos pos) {
        final IBlockState b = world.func_180495_p(pos);
        final Vec3d headVec = getCorrectedHeadVec(player);
        final Vec3d lookVec = player.func_70676_i(1.0f);
        final double reach = getBlockReachDistance(player);
        final Vec3d endVec = headVec.func_72441_c(lookVec.field_72450_a * reach, lookVec.field_72448_b * reach, lookVec.field_72449_c * reach);
        return b.func_185910_a(world, pos, headVec, endVec);
    }
    
    private static double getBlockReachDistance_server(final EntityPlayerMP player) {
        return player.field_71134_c.getBlockReachDistance();
    }
    
    @SideOnly(Side.CLIENT)
    private static double getBlockReachDistance_client() {
        return Minecraft.func_71410_x().field_71442_b.func_78757_d();
    }
    
    public static RayTraceResult retrace(final EntityPlayer player) {
        return retrace(player, getBlockReachDistance(player));
    }
    
    public static RayTraceResult retrace(final EntityPlayer player, final double reach) {
        final Vec3d headVec = getCorrectedHeadVec(player);
        final Vec3d lookVec = player.func_70676_i(1.0f);
        final Vec3d endVec = headVec.func_72441_c(lookVec.field_72450_a * reach, lookVec.field_72448_b * reach, lookVec.field_72449_c * reach);
        return player.field_70170_p.func_147447_a(headVec, endVec, true, false, true);
    }
    
    public static Vec3d getCorrectedHeadVec(final EntityPlayer player) {
        final Vector3 v = Vector3.fromEntity((Entity)player);
        if (player.field_70170_p.field_72995_K) {
            final Vector3 vector3 = v;
            vector3.y += player.func_70047_e();
        }
        else {
            final Vector3 vector4 = v;
            vector4.y += player.func_70047_e();
            if (player instanceof EntityPlayerMP && player.func_70093_af()) {
                final Vector3 vector5 = v;
                vector5.y -= 0.08;
            }
        }
        return v.vec3();
    }
    
    public static Vec3d getStartVec(final EntityPlayer player) {
        return getCorrectedHeadVec(player);
    }
    
    public static double getBlockReachDistance(final EntityPlayer player) {
        return player.field_70170_p.field_72995_K ? getBlockReachDistance_client() : ((player instanceof EntityPlayerMP) ? getBlockReachDistance_server((EntityPlayerMP)player) : 5.0);
    }
    
    public static Vec3d getEndVec(final EntityPlayer player) {
        final Vec3d headVec = getCorrectedHeadVec(player);
        final Vec3d lookVec = player.func_70676_i(1.0f);
        final double reach = getBlockReachDistance(player);
        return headVec.func_72441_c(lookVec.field_72450_a * reach, lookVec.field_72448_b * reach, lookVec.field_72449_c * reach);
    }
    
    static {
        RayTracer.t_inst = new ThreadLocal<RayTracer>();
    }
}
