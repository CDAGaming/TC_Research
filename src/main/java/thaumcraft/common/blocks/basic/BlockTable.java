package thaumcraft.common.blocks.basic;

import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.items.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;

public class BlockTable extends BlockTC
{
    public BlockTable(final Material mat, final String name, final SoundType st) {
        super(mat, name, st);
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return side == EnumFacing.UP;
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
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
        if (this == BlocksTC.tableWood && player.func_184586_b(hand).func_77973_b() instanceof IScribeTools) {
            IBlockState bs = BlocksTC.researchTable.func_176223_P();
            bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)player.func_174811_aO());
            world.func_175656_a(pos, bs);
            final TileResearchTable tile = (TileResearchTable)world.func_175625_s(pos);
            tile.func_70299_a(0, player.func_184586_b(hand).func_77946_l());
            player.func_184611_a(hand, ItemStack.field_190927_a);
            player.field_71071_by.func_70296_d();
            tile.func_70296_d();
            world.markAndNotifyBlock(pos, world.func_175726_f(pos), bs, bs, 3);
            FMLCommonHandler.instance().firePlayerCraftingEvent(player, new ItemStack(BlocksTC.researchTable), (IInventory)new InventoryFake(1));
        }
        return true;
    }
}
