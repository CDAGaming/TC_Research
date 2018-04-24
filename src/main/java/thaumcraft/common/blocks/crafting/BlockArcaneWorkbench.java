package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;

public class BlockArcaneWorkbench extends BlockTCDevice
{
    public BlockArcaneWorkbench() {
        super(Material.field_151575_d, TileArcaneWorkbench.class, "arcane_workbench");
        this.func_149672_a(SoundType.field_185848_a);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        player.openGui((Object)Thaumcraft.instance, 13, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        return true;
    }
    
    @Override
    public void func_180663_b(final World world, final BlockPos pos, final IBlockState state) {
        final TileEntity tileEntity = world.func_175625_s(pos);
        if (tileEntity != null && tileEntity instanceof TileArcaneWorkbench) {
            InventoryHelper.func_180175_a(world, pos, (IInventory)((TileArcaneWorkbench)tileEntity).inventoryCraft);
        }
        super.func_180663_b(world, pos, state);
        world.func_175713_t(pos);
    }
}
