package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;

public class BlockPotionSprayer extends BlockTCDevice implements IBlockFacing, IBlockEnabled
{
    public BlockPotionSprayer() {
        super(Material.field_151573_f, TilePotionSprayer.class, "potion_sprayer");
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        player.openGui((Object)Thaumcraft.instance, 21, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        return true;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
}
