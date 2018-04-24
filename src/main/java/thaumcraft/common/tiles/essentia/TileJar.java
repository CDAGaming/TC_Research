package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;

public class TileJar extends TileThaumcraft implements ITickable
{
    protected static Random rand;
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1));
    }
    
    public void func_73660_a() {
    }
    
    static {
        TileJar.rand = new Random();
    }
}
