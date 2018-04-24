package thaumcraft.common.blocks.misc;

import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.misc.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockHole extends BlockContainer
{
    public BlockHole() {
        super(Material.field_151576_e);
        this.func_149663_c("hole");
        this.setRegistryName("thaumcraft", "hole");
        this.func_149722_s();
        this.func_149752_b(6000000.0f);
        this.func_149672_a(SoundType.field_185854_g);
        this.func_149715_a(0.7f);
        this.func_149675_a(true);
        this.func_149647_a((CreativeTabs)null);
    }
    
    public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player) {
        return ItemStack.field_190927_a;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> par3List) {
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing o) {
        return true;
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return BlockHole.field_185505_j;
    }
    
    public AxisAlignedBB func_180640_a(final IBlockState blockState, final World worldIn, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public boolean func_149686_d(final IBlockState blockState) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState blockState) {
        return false;
    }
    
    public TileEntity func_149915_a(final World var1, final int var2) {
        return new TileHole();
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
}
