package thaumcraft.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import thaumcraft.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;
import thaumcraft.api.aura.*;

public class BlockTCTile extends BlockTC implements ITileEntityProvider
{
    protected final Class tileClass;
    protected static boolean keepInventory;
    protected static boolean spillEssentia;
    
    public BlockTCTile(final Material mat, final Class tc, final String name) {
        super(mat, name);
        this.func_149711_c(2.0f);
        this.func_149752_b(20.0f);
        this.tileClass = tc;
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public TileEntity func_149915_a(final World worldIn, final int meta) {
        if (this.tileClass == null) {
            return null;
        }
        try {
            return this.tileClass.newInstance();
        }
        catch (InstantiationException e) {
            Thaumcraft.log.catching((Throwable)e);
        }
        catch (IllegalAccessException e2) {
            Thaumcraft.log.catching((Throwable)e2);
        }
        return null;
    }
    
    public boolean hasTileEntity(final IBlockState state) {
        return true;
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        InventoryUtils.dropItems(worldIn, pos);
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (tileentity != null && tileentity instanceof IEssentiaTransport && BlockTCTile.spillEssentia && !worldIn.field_72995_K) {
            final int ess = ((IEssentiaTransport)tileentity).getEssentiaAmount(EnumFacing.UP);
            if (ess > 0) {
                AuraHelper.polluteAura(worldIn, pos, ess, true);
            }
        }
        super.func_180663_b(worldIn, pos, state);
        worldIn.func_175713_t(pos);
    }
    
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int id, final int param) {
        super.func_189539_a(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        return tileentity != null && tileentity.func_145842_c(id, param);
    }
    
    static {
        BlockTCTile.keepInventory = false;
        BlockTCTile.spillEssentia = true;
    }
}
