package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.lib.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class BlockMirror extends BlockTCDevice implements IBlockFacing
{
    public BlockMirror(final Class cls, final String name) {
        super(Material.field_151573_f, cls, name);
        this.func_149672_a(SoundsTC.JAR);
        this.func_149711_c(0.1f);
        this.setHarvestLevel((String)null, 0);
        final IBlockState bs = this.field_176227_L.func_177621_b();
        bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)EnumFacing.UP);
        this.func_180632_j(bs);
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.JAR;
    }
    
    @Override
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacing.FACING, (Comparable)facing);
        return bs;
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        final EnumFacing d = BlockStateUtils.getFacing(state);
        if (!worldIn.func_180495_p(pos.func_177972_a(d.func_176734_d())).isSideSolid((IBlockAccess)worldIn, pos.func_177972_a(d.func_176734_d()), d)) {
            this.func_176226_b(worldIn, pos, this.func_176223_P(), 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing facing = BlockStateUtils.getFacing(state);
        switch (facing.ordinal()) {
            default: {
                return new AxisAlignedBB(0.0, 0.875, 0.0, 1.0, 1.0, 1.0);
            }
            case 1: {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
            }
            case 2: {
                return new AxisAlignedBB(0.0, 0.0, 0.875, 1.0, 1.0, 1.0);
            }
            case 3: {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.125);
            }
            case 4: {
                return new AxisAlignedBB(0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            case 5: {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 0.125, 1.0, 1.0);
            }
        }
    }
    
    public boolean func_176198_a(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return worldIn.func_180495_p(pos.func_177972_a(side.func_176734_d())).isSideSolid((IBlockAccess)worldIn, pos.func_177972_a(side.func_176734_d()), side);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        return !world.field_72995_K || true;
    }
    
    public void func_180653_a(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileMirror || te instanceof TileMirrorEssentia) {
            this.dropMirror(worldIn, pos, state, te);
        }
        else {
            super.func_180653_a(worldIn, pos, state, chance, fortune);
        }
    }
    
    public void func_180657_a(final World worldIn, final EntityPlayer player, final BlockPos pos, final IBlockState state, final TileEntity te, final ItemStack stack) {
        if (te instanceof TileMirror || te instanceof TileMirrorEssentia) {
            this.dropMirror(worldIn, pos, state, te);
        }
        else {
            super.func_180657_a(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
    
    public void dropMirror(final World world, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (this.tileClass == TileMirror.class) {
            final TileMirror tm = (TileMirror)te;
            final ItemStack drop = new ItemStack((Block)this, 1, 0);
            if (tm != null && tm instanceof TileMirror) {
                if (tm.linked) {
                    drop.func_77964_b(1);
                    drop.func_77983_a("linkX", (NBTBase)new NBTTagInt(tm.linkX));
                    drop.func_77983_a("linkY", (NBTBase)new NBTTagInt(tm.linkY));
                    drop.func_77983_a("linkZ", (NBTBase)new NBTTagInt(tm.linkZ));
                    drop.func_77983_a("linkDim", (NBTBase)new NBTTagInt(tm.linkDim));
                    tm.invalidateLink();
                }
                func_180635_a(world, pos, drop);
            }
        }
        else {
            final TileMirrorEssentia tm2 = (TileMirrorEssentia)te;
            final ItemStack drop = new ItemStack((Block)this, 1, 0);
            if (tm2 != null && tm2 instanceof TileMirrorEssentia) {
                if (tm2.linked) {
                    drop.func_77964_b(1);
                    drop.func_77983_a("linkX", (NBTBase)new NBTTagInt(tm2.linkX));
                    drop.func_77983_a("linkY", (NBTBase)new NBTTagInt(tm2.linkY));
                    drop.func_77983_a("linkZ", (NBTBase)new NBTTagInt(tm2.linkZ));
                    drop.func_77983_a("linkDim", (NBTBase)new NBTTagInt(tm2.linkDim));
                    tm2.invalidateLink();
                }
                func_180635_a(world, pos, drop);
            }
        }
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (!world.field_72995_K && this.tileClass == TileMirror.class && entity instanceof EntityItem && !entity.field_70128_L && ((EntityItem)entity).field_71088_bW == 0) {
            final TileMirror taf = (TileMirror)world.func_175625_s(pos);
            if (taf != null) {
                taf.transport((EntityItem)entity);
            }
        }
        super.func_180634_a(world, pos, state, entity);
    }
}
