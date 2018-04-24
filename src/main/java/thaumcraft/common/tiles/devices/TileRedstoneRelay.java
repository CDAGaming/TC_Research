package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.*;
import thaumcraft.codechicken.lib.vec.*;

public class TileRedstoneRelay extends TileThaumcraft
{
    private int in;
    private int out;
    
    public TileRedstoneRelay() {
        this.in = 1;
        this.out = 15;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbt) {
        this.setIn(nbt.func_74771_c("in"));
        this.setOut(nbt.func_74771_c("out"));
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        nbt.func_74774_a("in", (byte)this.getIn());
        nbt.func_74774_a("out", (byte)this.getOut());
        return nbt;
    }
    
    public void increaseIn() {
        if (!this.field_145850_b.field_72995_K) {
            this.setIn(this.getIn() + 1);
            if (this.getIn() > 15) {
                this.setIn(1);
            }
            this.func_70296_d();
            this.syncTile(false);
        }
    }
    
    public void increaseOut() {
        if (!this.field_145850_b.field_72995_K) {
            this.setOut(this.getOut() + 1);
            if (this.getOut() > 15) {
                this.setOut(1);
            }
            this.func_70296_d();
            this.syncTile(false);
        }
    }
    
    public RayTraceResult rayTrace(final World world, final Vec3d vec3d, final Vec3d vec3d1, final RayTraceResult fullblock) {
        return fullblock;
    }
    
    public void addTraceableCuboids(final List<IndexedCuboid6> cuboids) {
        final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p());
        cuboids.add(new IndexedCuboid6(0, this.getCuboid0(facing)));
        cuboids.add(new IndexedCuboid6(1, this.getCuboid1(facing)));
    }
    
    public Cuboid6 getCuboid0(final EnumFacing facing) {
        Transformation rot = Rotation.quarterRotations[0];
        switch (facing) {
            case WEST: {
                rot = Rotation.quarterRotations[1];
                break;
            }
            case NORTH: {
                rot = Rotation.quarterRotations[2];
                break;
            }
            case EAST: {
                rot = Rotation.quarterRotations[3];
                break;
            }
        }
        return new Cuboid6(-0.375, 0.0625, -0.375, -0.125, 0.25, -0.125).apply(rot).add(new Vector3(this.func_174877_v().func_177958_n() + 0.5, this.func_174877_v().func_177956_o(), this.func_174877_v().func_177952_p() + 0.5));
    }
    
    public Cuboid6 getCuboid1(final EnumFacing facing) {
        Transformation rot = Rotation.quarterRotations[0];
        switch (facing) {
            case WEST: {
                rot = Rotation.quarterRotations[1];
                break;
            }
            case NORTH: {
                rot = Rotation.quarterRotations[2];
                break;
            }
            case EAST: {
                rot = Rotation.quarterRotations[3];
                break;
            }
        }
        return new Cuboid6(-0.125, 0.0625, 0.125, 0.125, 0.25, 0.375).apply(rot).add(new Vector3(this.func_174877_v().func_177958_n() + 0.5, this.func_174877_v().func_177956_o(), this.func_174877_v().func_177952_p() + 0.5));
    }
    
    public int getOut() {
        return this.out;
    }
    
    public void setOut(final int out) {
        this.out = out;
    }
    
    public int getIn() {
        return this.in;
    }
    
    public void setIn(final int in) {
        this.in = in;
    }
}
