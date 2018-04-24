package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.misc.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.api.aspects.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class BlockBannerTC extends BlockTC implements ITileEntityProvider
{
    public final EnumDyeColor dye;
    
    public BlockBannerTC(final String name, final EnumDyeColor dye) {
        super(Material.field_151575_d, name);
        this.func_149711_c(1.0f);
        this.func_149672_a(SoundType.field_185848_a);
        this.dye = dye;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return (this.dye == null) ? MapColor.field_151645_D : MapColor.func_193558_a(this.dye);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final TileEntity tile = source.func_175625_s(pos);
        if (tile != null && tile instanceof TileBanner) {
            if (!((TileBanner)tile).getWall()) {
                return new AxisAlignedBB(0.33000001311302185, 0.0, 0.33000001311302185, 0.6600000262260437, 2.0, 0.6600000262260437);
            }
            switch (((TileBanner)tile).getBannerFacing()) {
                case 0: {
                    return new AxisAlignedBB(0.0, -1.0, 0.0, 1.0, 1.0, 0.25);
                }
                case 8: {
                    return new AxisAlignedBB(0.0, -1.0, 0.75, 1.0, 1.0, 1.0);
                }
                case 12: {
                    return new AxisAlignedBB(0.0, -1.0, 0.0, 0.25, 1.0, 1.0);
                }
                case 4: {
                    return new AxisAlignedBB(0.75, -1.0, 0.0, 1.0, 1.0, 1.0);
                }
            }
        }
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_180639_a(final World w, final BlockPos pos, final IBlockState state, final EntityPlayer p, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (p.func_70093_af() || p.func_184586_b(hand).func_77973_b() instanceof ItemPhial) {
            final TileBanner te = (TileBanner)w.func_175625_s(pos);
            if (te != null && te.getColor() >= 0) {
                if (p.func_70093_af()) {
                    te.setAspect(null);
                }
                else if (((IEssentiaContainerItem)p.func_184586_b(hand).func_77973_b()).getAspects(p.func_184586_b(hand)) != null) {
                    te.setAspect(((IEssentiaContainerItem)p.func_184586_b(hand).func_77973_b()).getAspects(p.func_184586_b(hand)).getAspects()[0]);
                    p.func_184586_b(hand).func_190918_g(1);
                }
                w.markAndNotifyBlock(pos, w.func_175726_f(pos), state, state, 3);
                te.func_70296_d();
                w.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187550_ag, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
        return true;
    }
    
    public TileEntity func_149915_a(final World worldIn, final int meta) {
        return new TileBanner();
    }
    
    public boolean hasTileEntity(final IBlockState state) {
        return true;
    }
    
    public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player) {
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileBanner) {
            final ItemStack drop = new ItemStack((Block)this);
            if (((TileBanner)te).getColor() >= 0 || ((TileBanner)te).getAspect() != null) {
                drop.func_77982_d(new NBTTagCompound());
                if (((TileBanner)te).getAspect() != null) {
                    drop.func_77978_p().func_74778_a("aspect", ((TileBanner)te).getAspect().getTag());
                }
            }
            return drop;
        }
        return super.getPickBlock(state, target, world, pos, player);
    }
    
    public void func_180653_a(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileBanner) {
            final ItemStack drop = new ItemStack((Block)this);
            if (((TileBanner)te).getColor() >= 0 || ((TileBanner)te).getAspect() != null) {
                drop.func_77982_d(new NBTTagCompound());
                if (((TileBanner)te).getAspect() != null) {
                    drop.func_77978_p().func_74778_a("aspect", ((TileBanner)te).getAspect().getTag());
                }
            }
            func_180635_a(worldIn, pos, drop);
        }
        else {
            super.func_180653_a(worldIn, pos, state, chance, fortune);
        }
    }
    
    public void func_180657_a(final World worldIn, final EntityPlayer player, final BlockPos pos, final IBlockState state, final TileEntity te, final ItemStack stack) {
        if (te instanceof TileBanner) {
            final ItemStack drop = new ItemStack((Block)this);
            if (((TileBanner)te).getColor() >= 0 || ((TileBanner)te).getAspect() != null) {
                drop.func_77982_d(new NBTTagCompound());
                if (((TileBanner)te).getAspect() != null) {
                    drop.func_77978_p().func_74778_a("aspect", ((TileBanner)te).getAspect().getTag());
                }
            }
            func_180635_a(worldIn, pos, drop);
        }
        else {
            super.func_180657_a(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
}
