package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.misc.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.client.fx.*;
import java.util.*;

public class BlockPavingStone extends BlockTC
{
    public BlockPavingStone(final String name) {
        super(Material.field_151576_e, name);
        this.func_149711_c(2.5f);
        this.func_149672_a(SoundType.field_185851_d);
        this.func_149675_a(true);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public boolean hasTileEntity(final IBlockState state) {
        return state.func_177230_c() == BlocksTC.pavingStoneBarrier;
    }
    
    public TileEntity createTileEntity(final World world, final IBlockState state) {
        return (state.func_177230_c() == BlocksTC.pavingStoneBarrier) ? new TileBarrierStone() : null;
    }
    
    public void func_176199_a(final World worldIn, final BlockPos pos, final Entity e) {
        final IBlockState state = worldIn.func_180495_p(pos);
        if (!worldIn.field_72995_K && state.func_177230_c() == BlocksTC.pavingStoneTravel && e instanceof EntityLivingBase) {
            ((EntityLivingBase)e).func_70690_d(new PotionEffect(MobEffects.field_76424_c, 40, 1, false, false));
            ((EntityLivingBase)e).func_70690_d(new PotionEffect(MobEffects.field_76430_j, 40, 0, false, false));
        }
        super.func_176199_a(worldIn, pos, e);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random random) {
        if (state.func_177230_c() == BlocksTC.pavingStoneBarrier) {
            if (world.func_175687_A(pos) > 0) {
                for (int a = 0; a < 4; ++a) {
                    FXDispatcher.INSTANCE.blockRunes(pos.func_177958_n(), pos.func_177956_o() + 0.7f, pos.func_177952_p(), 0.2f + random.nextFloat() * 0.4f, random.nextFloat() * 0.3f, 0.8f + random.nextFloat() * 0.2f, 20, -0.02f);
                }
            }
            else if ((world.func_180495_p(pos.func_177981_b(1)) == BlocksTC.barrier.func_176223_P() && world.func_180495_p(pos.func_177981_b(1)).func_177230_c().func_176205_b((IBlockAccess)world, pos.func_177981_b(1))) || (world.func_180495_p(pos.func_177981_b(2)) == BlocksTC.barrier.func_176223_P() && world.func_180495_p(pos.func_177981_b(2)).func_177230_c().func_176205_b((IBlockAccess)world, pos.func_177981_b(2)))) {
                for (int a = 0; a < 6; ++a) {
                    FXDispatcher.INSTANCE.blockRunes(pos.func_177958_n(), pos.func_177956_o() + 0.7f, pos.func_177952_p(), 0.9f + random.nextFloat() * 0.1f, random.nextFloat() * 0.3f, random.nextFloat() * 0.3f, 24, -0.02f);
                }
            }
            else {
                final List list = world.func_72839_b((Entity)null, new AxisAlignedBB((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), (double)(pos.func_177958_n() + 1), (double)(pos.func_177956_o() + 1), (double)(pos.func_177952_p() + 1)).func_72314_b(1.0, 1.0, 1.0));
                if (!list.isEmpty()) {
                    for (final Entity entity : list) {
                        if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer)) {
                            FXDispatcher.INSTANCE.blockRunes(pos.func_177958_n(), pos.func_177956_o() + 0.6f + random.nextFloat() * Math.max(0.8f, entity.func_70047_e()), pos.func_177952_p(), 0.6f + random.nextFloat() * 0.4f, 0.0f, 0.3f + random.nextFloat() * 0.7f, 20, 0.0f);
                            break;
                        }
                    }
                }
            }
        }
    }
}
