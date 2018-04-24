package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;

public class BlockRechargePedestal extends BlockTCDevice
{
    public BlockRechargePedestal() {
        super(Material.field_151576_e, TileRechargePedestal.class, "recharge_pedestal");
        this.func_149672_a(SoundType.field_185851_d);
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
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileRechargePedestal) {
            final TileRechargePedestal ped = (TileRechargePedestal)tile;
            if (ped.func_70301_a(0).func_190926_b() && player.field_71071_by.func_70448_g().func_77973_b() instanceof IRechargable) {
                final ItemStack i = player.func_184586_b(hand).func_77946_l();
                i.func_190920_e(1);
                ped.func_70299_a(0, i);
                player.func_184586_b(hand).func_190918_g(1);
                if (player.func_184586_b(hand).func_190916_E() == 0) {
                    player.func_184611_a(hand, ItemStack.field_190927_a);
                }
                player.field_71071_by.func_70296_d();
                world.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187638_cR, SoundCategory.BLOCKS, 0.2f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 1.6f);
                return true;
            }
            if (!ped.func_70301_a(0).func_190926_b()) {
                InventoryUtils.dropItemsAtEntity(world, pos, (Entity)player);
                world.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187638_cR, SoundCategory.BLOCKS, 0.2f, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 1.5f);
                return true;
            }
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
}
