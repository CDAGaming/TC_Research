package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import thaumcraft.api.aura.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;

public class BlockAlembic extends BlockTCTile implements ILabelable
{
    public BlockAlembic() {
        super(Material.field_151575_d, TileAlembic.class, "alembic");
        this.func_149672_a(SoundType.field_185848_a);
    }
    
    @Override
    public TileEntity func_149915_a(final World world, final int metadata) {
        if (metadata == 0) {
            return new TileAlembic();
        }
        return null;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.func_176203_a(meta);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileAlembic && player.func_70093_af() && ((TileAlembic)te).aspectFilter != null && side.ordinal() == ((TileAlembic)te).facing) {
            ((TileAlembic)te).aspectFilter = null;
            ((TileAlembic)te).facing = EnumFacing.DOWN.ordinal();
            te.func_70296_d();
            world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
            if (world.field_72995_K) {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.page, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
            else {
                world.func_72838_d((Entity)new EntityItem(world, (double)(pos.func_177958_n() + 0.5f + side.func_82601_c() / 3.0f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f + side.func_82599_e() / 3.0f), new ItemStack(ItemsTC.label)));
            }
        }
        else if (te != null && te instanceof TileAlembic && player.func_70093_af() && player.func_184614_ca() == null && (((TileAlembic)te).aspectFilter == null || side.ordinal() != ((TileAlembic)te).facing)) {
            ((TileAlembic)te).aspect = null;
            if (world.field_72995_K) {
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.jar, SoundCategory.BLOCKS, 0.4f, 1.0f, false);
                world.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.5f, 1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.3f, false);
            }
            else {
                AuraHelper.polluteAura(world, pos, ((TileAlembic)te).amount, true);
            }
            ((TileAlembic)te).amount = 0;
            te.func_70296_d();
            world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
        }
        return true;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileAlembic) {
            final float r = ((TileAlembic)tile).amount / ((TileAlembic)tile).maxAmount;
            return MathHelper.func_76141_d(r * 14.0f) + ((((TileAlembic)tile).amount > 0) ? 1 : 0);
        }
        return super.func_180641_l(state, world, pos);
    }
    
    @Override
    public boolean applyLabel(final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ItemStack labelstack) {
        final TileEntity te = player.field_70170_p.func_175625_s(pos);
        if (te == null || !(te instanceof TileAlembic) || side.ordinal() <= 1 || ((TileAlembic)te).aspectFilter != null) {
            return false;
        }
        Aspect la = null;
        if (((IEssentiaContainerItem)labelstack.func_77973_b()).getAspects(labelstack) != null) {
            la = ((IEssentiaContainerItem)labelstack.func_77973_b()).getAspects(labelstack).getAspects()[0];
        }
        if (((TileAlembic)te).amount == 0 && la == null) {
            return false;
        }
        Aspect aspect = null;
        if (((TileAlembic)te).amount == 0 && la != null) {
            aspect = la;
        }
        if (((TileAlembic)te).amount > 0) {
            aspect = ((TileAlembic)te).aspect;
        }
        if (aspect == null) {
            return false;
        }
        final IBlockState state = player.field_70170_p.func_180495_p(pos);
        this.func_180633_a(player.field_70170_p, pos, state, (EntityLivingBase)player, (ItemStack)null);
        ((TileAlembic)te).aspectFilter = aspect;
        ((TileAlembic)te).facing = side.ordinal();
        te.func_70296_d();
        player.field_70170_p.markAndNotifyBlock(pos, player.field_70170_p.func_175726_f(pos), state, state, 3);
        player.field_70170_p.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.page, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return true;
    }
}
