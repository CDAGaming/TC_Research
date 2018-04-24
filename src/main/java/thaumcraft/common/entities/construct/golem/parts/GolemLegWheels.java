package thaumcraft.common.entities.construct.golem.parts;

import thaumcraft.api.golems.parts.*;
import java.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class GolemLegWheels implements GolemLeg.ILegFunction
{
    public static HashMap<Integer, Float> ani;
    
    @Override
    public void onUpdateTick(final IGolemAPI golem) {
        if (golem.getGolemWorld().field_72995_K) {
            final double dist = golem.getGolemEntity().func_70011_f(golem.getGolemEntity().field_70169_q, golem.getGolemEntity().field_70167_r, golem.getGolemEntity().field_70166_s);
            float lastRot = 0.0f;
            if (GolemLegWheels.ani.containsKey(golem.getGolemEntity().func_145782_y())) {
                lastRot = GolemLegWheels.ani.get(golem.getGolemEntity().func_145782_y());
            }
            final double d0 = golem.getGolemEntity().field_70165_t - golem.getGolemEntity().field_70169_q;
            final double d2 = golem.getGolemEntity().field_70163_u - golem.getGolemEntity().field_70167_r;
            final double d3 = golem.getGolemEntity().field_70161_v - golem.getGolemEntity().field_70166_s;
            final float dirTravel = (float)(Math.atan2(d3, d0) * 180.0 / 3.141592653589793) - 90.0f;
            final double dir = 360.0f - (golem.getGolemEntity().field_70177_z - dirTravel);
            lastRot += (float)(dist / 1.571 * dir);
            if (lastRot > 360.0f) {
                lastRot -= 360.0f;
            }
            GolemLegWheels.ani.put(golem.getGolemEntity().func_145782_y(), lastRot);
            if (golem.getGolemEntity().field_70122_E && !golem.getGolemEntity().func_70090_H() && dist > 0.25) {
                final int i = MathHelper.func_76128_c(golem.getGolemEntity().field_70165_t);
                final int j = MathHelper.func_76128_c(golem.getGolemEntity().field_70163_u - 0.20000000298023224);
                final int k = MathHelper.func_76128_c(golem.getGolemEntity().field_70161_v);
                final BlockPos blockpos = new BlockPos(i, j, k);
                final IBlockState iblockstate = golem.getGolemEntity().field_70170_p.func_180495_p(blockpos);
                final Block block = iblockstate.func_177230_c();
                if (block.func_149645_b(iblockstate) != EnumBlockRenderType.INVISIBLE) {
                    golem.getGolemEntity().field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, golem.getGolemEntity().field_70165_t + (golem.getGolemWorld().field_73012_v.nextFloat() - 0.5) * golem.getGolemEntity().field_70130_N, golem.getGolemEntity().func_174813_aQ().field_72338_b + 0.1, golem.getGolemEntity().field_70161_v + (golem.getGolemWorld().field_73012_v.nextFloat() - 0.5) * golem.getGolemEntity().field_70130_N, -golem.getGolemEntity().field_70159_w * 4.0, 1.5, -golem.getGolemEntity().field_70179_y * 4.0, new int[] { Block.func_176210_f(iblockstate) });
                }
            }
        }
    }
    
    static {
        GolemLegWheels.ani = new HashMap<Integer, Float>();
    }
}
