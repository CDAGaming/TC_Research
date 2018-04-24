package thaumcraft.api.research;

import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class ScanBlockState implements IScanThing
{
    String research;
    IBlockState blockState;
    
    public ScanBlockState(final IBlockState blockState) {
        this.research = "!" + blockState.toString();
        this.blockState = blockState;
    }
    
    public ScanBlockState(final String research, final IBlockState blockState) {
        this.research = research;
        this.blockState = blockState;
    }
    
    public ScanBlockState(final String research, final IBlockState blockState, final boolean item) {
        this.research = research;
        this.blockState = blockState;
        if (item) {
            ScanningManager.addScannableThing(new ScanItem(research, new ItemStack(blockState.func_177230_c(), 1, blockState.func_177230_c().func_176201_c(blockState))));
        }
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        return obj != null && obj instanceof BlockPos && player.field_70170_p.func_180495_p((BlockPos)obj) == this.blockState;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
