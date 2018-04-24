package thaumcraft.api.casters;

import net.minecraft.util.math.*;

public class Trajectory
{
    public Vec3d source;
    public Vec3d direction;
    
    public Trajectory(final Vec3d source, final Vec3d direction) {
        this.source = source;
        this.direction = direction;
    }
}
