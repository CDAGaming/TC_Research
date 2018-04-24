package thaumcraft.codechicken.lib.raytracer;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.codechicken.lib.vec.*;
import net.minecraft.util.*;

public class ExtendedMOP extends RayTraceResult implements Comparable<ExtendedMOP>
{
    public double dist;
    
    public ExtendedMOP(final Entity entity, final Vector3 hit, final Object data, final double dist) {
        super(entity, hit.vec3());
        this.setData(data);
        this.dist = dist;
    }
    
    public ExtendedMOP(final Vector3 hit, final int side, final BlockCoord pos, final Object data, final double dist) {
        super(hit.vec3(), (side >= 0) ? EnumFacing.values()[side] : EnumFacing.UP, pos.pos());
        this.setData(data);
        this.dist = dist;
    }
    
    public void setData(final Object data) {
        if (data instanceof Integer) {
            this.subHit = (int)data;
        }
        this.hitInfo = data;
    }
    
    public int compareTo(final ExtendedMOP o) {
        return (this.dist == o.dist) ? 0 : ((this.dist < o.dist) ? -1 : 1);
    }
}
