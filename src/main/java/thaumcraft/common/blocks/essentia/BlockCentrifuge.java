package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockCentrifuge extends BlockTCDevice
{
    public BlockCentrifuge() {
        super(Material.field_151575_d, TileCentrifuge.class, "centrifuge");
        this.func_149672_a(SoundType.field_185848_a);
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
