package thaumcraft.api.internal;

import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.nbt.*;

public class WorldCoordinates implements Comparable
{
    public BlockPos pos;
    public int dim;
    
    public WorldCoordinates() {
    }
    
    public WorldCoordinates(final BlockPos pos, final int d) {
        this.pos = pos;
        this.dim = d;
    }
    
    public WorldCoordinates(final TileEntity tile) {
        this.pos = tile.func_174877_v();
        this.dim = tile.func_145831_w().field_73011_w.getDimension();
    }
    
    public WorldCoordinates(final WorldCoordinates par1ChunkCoordinates) {
        this.pos = par1ChunkCoordinates.pos;
        this.dim = par1ChunkCoordinates.dim;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof WorldCoordinates)) {
            return false;
        }
        final WorldCoordinates coordinates = (WorldCoordinates)par1Obj;
        return this.pos.equals((Object)coordinates.pos) && this.dim == coordinates.dim;
    }
    
    @Override
    public int hashCode() {
        return this.pos.func_177958_n() + this.pos.func_177956_o() << 8 + this.pos.func_177952_p() << 16 + this.dim << 24;
    }
    
    public int compareWorldCoordinate(final WorldCoordinates par1) {
        return (this.dim == par1.dim) ? this.pos.compareTo((Vec3i)par1.pos) : -1;
    }
    
    public void set(final BlockPos pos, final int d) {
        this.pos = pos;
        this.dim = d;
    }
    
    public double getDistanceSquared(final BlockPos pos) {
        return this.pos.func_177951_i((Vec3i)pos);
    }
    
    public double getDistanceSquaredToWorldCoordinates(final WorldCoordinates par1ChunkCoordinates) {
        return this.getDistanceSquared(par1ChunkCoordinates.pos);
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareWorldCoordinate((WorldCoordinates)par1Obj);
    }
    
    public void readNBT(final NBTTagCompound nbt) {
        final int x = nbt.func_74762_e("w_x");
        final int y = nbt.func_74762_e("w_y");
        final int z = nbt.func_74762_e("w_z");
        this.pos = new BlockPos(x, y, z);
        this.dim = nbt.func_74762_e("w_d");
    }
    
    public void writeNBT(final NBTTagCompound nbt) {
        nbt.func_74768_a("w_x", this.pos.func_177958_n());
        nbt.func_74768_a("w_y", this.pos.func_177956_o());
        nbt.func_74768_a("w_z", this.pos.func_177952_p());
        nbt.func_74768_a("w_d", this.dim);
    }
}
