package thaumcraft.api.casters;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;

public class FocusMediumRoot extends FocusMedium
{
    Trajectory[] trajectories;
    RayTraceResult[] targets;
    
    public FocusMediumRoot() {
        this.trajectories = null;
        this.targets = null;
    }
    
    public FocusMediumRoot(final Trajectory[] trajectories, final RayTraceResult[] targets) {
        this.trajectories = null;
        this.targets = null;
        this.trajectories = trajectories;
        this.targets = targets;
    }
    
    @Override
    public String getResearch() {
        return "BASEAUROMANCY";
    }
    
    @Override
    public String getKey() {
        return "ROOT";
    }
    
    @Override
    public int getComplexity() {
        return 0;
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET, EnumSupplyType.TRAJECTORY };
    }
    
    @Override
    public RayTraceResult[] supplyTargets() {
        return this.targets;
    }
    
    @Override
    public Trajectory[] supplyTrajectories() {
        return this.trajectories;
    }
    
    public void setupFromCaster(final EntityLivingBase caster) {
        this.trajectories = new Trajectory[] { new Trajectory(this.generateSourceVector(caster), caster.func_70040_Z()) };
        this.targets = new RayTraceResult[] { new RayTraceResult((Entity)caster) };
    }
    
    public void setupFromCasterToTarget(final EntityLivingBase caster, final Entity target, final double offset) {
        final Vec3d sv = this.generateSourceVector(caster);
        final double d0 = target.field_70165_t - sv.field_72450_a;
        final double d2 = target.func_174813_aQ().field_72338_b + target.field_70131_O / 2.0f - sv.field_72448_b;
        final double d3 = target.field_70161_v - sv.field_72449_c;
        final Vec3d lv = new Vec3d(d0, d2 + offset, d3);
        this.trajectories = new Trajectory[] { new Trajectory(sv, lv.func_72432_b()) };
        this.targets = new RayTraceResult[] { new RayTraceResult((Entity)caster) };
    }
    
    public void setupFromCasterToTargetLoc(final EntityLivingBase caster, final double x, final double y, final double z) {
        final Vec3d sv = this.generateSourceVector(caster);
        final double d0 = x - sv.field_72450_a;
        final double d2 = y - sv.field_72448_b;
        final double d3 = z - sv.field_72449_c;
        final Vec3d lv = new Vec3d(d0, d2, d3);
        this.trajectories = new Trajectory[] { new Trajectory(sv, lv.func_72432_b()) };
        this.targets = new RayTraceResult[] { new RayTraceResult((Entity)caster) };
    }
    
    private Vec3d generateSourceVector(final EntityLivingBase e) {
        Vec3d v = e.func_174791_d();
        v = v.func_72441_c(0.0, e.func_70047_e() - 0.10000000149011612, 0.0);
        return v;
    }
    
    @Override
    public Aspect getAspect() {
        return null;
    }
}
