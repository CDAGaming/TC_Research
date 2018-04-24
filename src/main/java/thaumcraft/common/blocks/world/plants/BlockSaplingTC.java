package thaumcraft.common.blocks.world.plants;

import net.minecraft.block.properties.*;
import thaumcraft.common.config.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraftforge.event.terraingen.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.world.objects.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class BlockSaplingTC extends BlockBush implements IGrowable
{
    public static final PropertyInteger STAGE;
    protected static final AxisAlignedBB SAPLING_AABB;
    
    public BlockSaplingTC(final String name) {
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockSaplingTC.STAGE, (Comparable)0));
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149672_a(SoundType.field_185850_c);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return BlockSaplingTC.SAPLING_AABB;
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 60;
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 30;
    }
    
    public void func_180650_b(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.field_72995_K) {
            super.func_180650_b(worldIn, pos, state, rand);
            if (worldIn.func_175671_l(pos.func_177984_a()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand);
            }
        }
    }
    
    public void grow(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if ((int)state.func_177229_b((IProperty)BlockSaplingTC.STAGE) == 0) {
            worldIn.func_180501_a(pos, state.func_177231_a((IProperty)BlockSaplingTC.STAGE), 4);
        }
        else {
            this.generateTree(worldIn, pos, state, rand);
        }
    }
    
    public void generateTree(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) {
            return;
        }
        Object object = (rand.nextInt(10) == 0) ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        final int i = 0;
        final int j = 0;
        if (state.func_177230_c() == BlocksTC.saplingGreatwood) {
            object = new WorldGenGreatwoodTrees(true, false);
        }
        else {
            object = new WorldGenSilverwoodTrees(true, 7, 4);
        }
        final IBlockState iblockstate1 = Blocks.field_150350_a.func_176223_P();
        worldIn.func_180501_a(pos, iblockstate1, 4);
        if (!((WorldGenerator)object).func_180709_b(worldIn, rand, pos.func_177982_a(i, 0, j))) {
            worldIn.func_180501_a(pos, state, 4);
        }
    }
    
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_176473_a(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient) {
        return true;
    }
    
    public boolean func_180670_a(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        return worldIn.field_73012_v.nextFloat() < 0.25;
    }
    
    public void func_176474_b(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        this.grow(worldIn, pos, state, rand);
    }
    
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockSaplingTC.STAGE, (Comparable)((meta & 0x8) >> 3));
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        i |= (int)state.func_177229_b((IProperty)BlockSaplingTC.STAGE) << 3;
        return i;
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockSaplingTC.STAGE });
    }
    
    static {
        STAGE = PropertyInteger.func_177719_a("stage", 0, 1);
        SAPLING_AABB = new AxisAlignedBB(0.09999999403953552, 0.0, 0.09999999403953552, 0.8999999761581421, 0.800000011920929, 0.8999999761581421);
    }
}
