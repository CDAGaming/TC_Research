package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import thaumcraft.api.crafting.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockCandle extends BlockTC implements IInfusionStabiliser
{
    public final EnumDyeColor dye;
    
    public BlockCandle(final String name, final EnumDyeColor dye) {
        super(Material.field_151594_q, name);
        this.func_149711_c(0.1f);
        this.func_149672_a(SoundType.field_185854_g);
        this.func_149715_a(0.9375f);
        this.dye = dye;
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return MapColor.func_193558_a(this.dye);
    }
    
    public boolean func_176196_c(final World par1World, final BlockPos pos) {
        return par1World.isSideSolid(pos, EnumFacing.UP);
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (!this.func_176196_c(worldIn, pos.func_177977_b())) {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean func_176198_a(final World par1World, final BlockPos pos, final EnumFacing par5) {
        return this.func_176196_c(par1World, pos.func_177977_b());
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.5, 0.625);
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public void func_180655_c(final IBlockState state, final World par1World, final BlockPos pos, final Random par5Random) {
        final double var7 = pos.func_177958_n() + 0.5f;
        final double var8 = pos.func_177956_o() + 0.7f;
        final double var9 = pos.func_177952_p() + 0.5f;
        par1World.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, var7, var8, var9, 0.0, 0.0, 0.0, new int[0]);
        par1World.func_175688_a(EnumParticleTypes.FLAME, var7, var8, var9, 0.0, 0.0, 0.0, new int[0]);
    }
    
    @Override
    public boolean canStabaliseInfusion(final World world, final BlockPos pos) {
        return true;
    }
}
