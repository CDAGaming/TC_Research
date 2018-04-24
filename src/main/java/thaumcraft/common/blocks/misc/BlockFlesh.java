package thaumcraft.common.blocks.misc;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.*;

public class BlockFlesh extends BlockTC
{
    public BlockFlesh() {
        super(Material.field_151583_m, "flesh_block");
        this.func_149752_b(2.0f);
        this.func_149711_c(0.25f);
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.GORE;
    }
}
