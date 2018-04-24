package thaumcraft.common.entities.construct;

import thaumcraft.common.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemTurretPlacer extends ItemTCBase
{
    public ItemTurretPlacer() {
        super("turret", new String[] { "basic", "advanced", "bore" });
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        if (side == EnumFacing.DOWN) {
            return EnumActionResult.PASS;
        }
        final boolean flag = world.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)world, pos);
        final BlockPos blockpos = flag ? pos : pos.func_177972_a(side);
        if (!player.func_175151_a(blockpos, side, player.func_184586_b(hand))) {
            return EnumActionResult.PASS;
        }
        final BlockPos blockpos2 = blockpos.func_177984_a();
        boolean flag2 = !world.func_175623_d(blockpos) && !world.func_180495_p(blockpos).func_177230_c().func_176200_f((IBlockAccess)world, blockpos);
        flag2 |= (!world.func_175623_d(blockpos2) && !world.func_180495_p(blockpos2).func_177230_c().func_176200_f((IBlockAccess)world, blockpos2));
        if (flag2) {
            return EnumActionResult.PASS;
        }
        final double d0 = blockpos.func_177958_n();
        final double d2 = blockpos.func_177956_o();
        final double d3 = blockpos.func_177952_p();
        final List<Entity> list = (List<Entity>)world.func_72839_b((Entity)null, new AxisAlignedBB(d0, d2, d3, d0 + 1.0, d2 + 2.0, d3 + 1.0));
        if (!list.isEmpty()) {
            return EnumActionResult.PASS;
        }
        if (!world.field_72995_K) {
            world.func_175698_g(blockpos);
            world.func_175698_g(blockpos2);
            EntityOwnedConstruct turret = null;
            switch (player.func_184586_b(hand).func_77952_i()) {
                case 0: {
                    turret = new EntityTurretCrossbow(world, blockpos);
                    break;
                }
                case 1: {
                    turret = new EntityTurretCrossbowAdvanced(world, blockpos);
                    break;
                }
                case 2: {
                    turret = new EntityArcaneBore(world, blockpos, player.func_174811_aO());
                    break;
                }
            }
            if (turret != null) {
                world.func_72838_d((Entity)turret);
                turret.setOwned(true);
                turret.setValidSpawn();
                turret.setOwnerId(player.func_110124_au());
                world.func_184148_a((EntityPlayer)null, turret.field_70165_t, turret.field_70163_u, turret.field_70161_v, SoundEvents.field_187710_m, SoundCategory.BLOCKS, 0.75f, 0.8f);
            }
            player.func_184586_b(hand).func_190918_g(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
