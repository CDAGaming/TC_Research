package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockInfusionMatrix extends BlockTCDevice
{
    public BlockInfusionMatrix() {
        super(Material.field_151576_e, TileInfusionMatrix.class, "infusion_matrix");
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
