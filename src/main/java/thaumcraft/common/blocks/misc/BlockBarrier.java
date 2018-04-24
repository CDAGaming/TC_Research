package thaumcraft.common.blocks.misc;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.tiles.misc.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockBarrier extends Block
{
    public static final Material barrierMat;
    
    public BlockBarrier() {
        super(BlockBarrier.barrierMat);
        this.func_149647_a((CreativeTabs)null);
        this.func_149713_g(0);
        this.func_149663_c("barrier");
        this.setRegistryName("thaumcraft", "barrier");
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
    }
    
    public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player) {
        return ItemStack.field_190927_a;
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing o) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public void func_185477_a(final IBlockState state, final World world, final BlockPos pos, final AxisAlignedBB mask, final List list, final Entity collidingEntity, final boolean isActualState) {
        if (collidingEntity != null && collidingEntity instanceof EntityLivingBase && !(collidingEntity instanceof EntityPlayer)) {
            int a = 1;
            if (world.func_180495_p(pos.func_177979_c(a)).func_177230_c() != BlocksTC.pavingStoneBarrier) {
                ++a;
            }
            if (world.func_175687_A(pos.func_177979_c(a)) == 0) {
                list.add(BlockBarrier.field_185505_j.func_186670_a(pos));
            }
        }
    }
    
    public void func_189540_a(final IBlockState state, final World world, final BlockPos pos, final Block neighborBlock, final BlockPos pos2) {
        if (world.func_180495_p(pos.func_177979_c(1)) != BlocksTC.pavingStoneBarrier.func_176223_P() && world.func_180495_p(pos.func_177979_c(1)) != this.func_176223_P()) {
            world.func_175698_g(pos);
        }
    }
    
    public boolean func_176205_b(final IBlockAccess worldIn, final BlockPos pos) {
        for (int a = 1; a < 3; ++a) {
            final TileEntity te = worldIn.func_175625_s(pos.func_177979_c(a));
            if (te != null && te instanceof TileBarrierStone) {
                return te.func_145831_w().func_175687_A(pos.func_177979_c(a)) > 0;
            }
        }
        return true;
    }
    
    public boolean func_176200_f(final IBlockAccess worldIn, final BlockPos pos) {
        return true;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
    
    public boolean isAir(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return false;
    }
    
    static {
        barrierMat = new MaterialBarrier();
    }
    
    private static class MaterialBarrier extends Material
    {
        public MaterialBarrier() {
            super(MapColor.field_151660_b);
        }
        
        public boolean func_76230_c() {
            return true;
        }
        
        public boolean func_76220_a() {
            return false;
        }
        
        public boolean func_76228_b() {
            return false;
        }
    }
}
