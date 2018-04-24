package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import net.minecraft.entity.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;

public class BlockArcaneWorkbenchCharger extends BlockTC
{
    public BlockArcaneWorkbenchCharger() {
        super(Material.field_151575_d, "arcane_workbench_charger");
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149711_c(1.25f);
        this.func_149752_b(10.0f);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_176196_c(final World worldIn, final BlockPos pos) {
        return super.func_176196_c(worldIn, pos) && (worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.arcaneWorkbench || worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.wandWorkbench);
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final TileEntity te = worldIn.func_175625_s(pos.func_177977_b());
        if (te != null && te instanceof TileArcaneWorkbench) {
            ((TileArcaneWorkbench)te).syncTile(true);
        }
        if (te != null && te instanceof TileFocalManipulator) {
            ((TileFocalManipulator)te).syncTile(true);
        }
        return super.func_180642_a(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() != BlocksTC.arcaneWorkbench && worldIn.func_180495_p(pos.func_177977_b()).func_177230_c() != BlocksTC.wandWorkbench) {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        if (world.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.arcaneWorkbench) {
            player.openGui((Object)Thaumcraft.instance, 13, world, pos.func_177958_n(), pos.func_177977_b().func_177956_o(), pos.func_177952_p());
        }
        if (world.func_180495_p(pos.func_177977_b()).func_177230_c() == BlocksTC.wandWorkbench) {
            player.openGui((Object)Thaumcraft.instance, 7, world, pos.func_177958_n(), pos.func_177977_b().func_177956_o(), pos.func_177952_p());
        }
        return true;
    }
}
