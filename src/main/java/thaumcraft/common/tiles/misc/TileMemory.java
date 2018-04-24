package thaumcraft.common.tiles.misc;

import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class TileMemory extends TileEntity
{
    public IBlockState oldblock;
    public NBTTagCompound tileEntityCompound;
    
    public TileMemory() {
        this.oldblock = Blocks.field_150350_a.func_176223_P();
    }
    
    public TileMemory(final IBlockState bi) {
        this.oldblock = Blocks.field_150350_a.func_176223_P();
        this.oldblock = bi;
    }
    
    public void func_145839_a(final NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        final Block b = Block.func_149729_e(nbttagcompound.func_74762_e("oldblock"));
        final int meta = nbttagcompound.func_74762_e("oldmeta");
        this.oldblock = b.func_176203_a(meta);
    }
    
    public NBTTagCompound func_189515_b(final NBTTagCompound nbttagcompound) {
        super.func_189515_b(nbttagcompound);
        nbttagcompound.func_74768_a("oldblock", Block.func_149682_b(this.oldblock.func_177230_c()));
        nbttagcompound.func_74768_a("oldmeta", this.oldblock.func_177230_c().func_176201_c(this.oldblock));
        return nbttagcompound;
    }
}
