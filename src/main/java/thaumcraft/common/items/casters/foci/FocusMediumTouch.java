package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.api.casters.*;

public class FocusMediumTouch extends FocusMedium
{
    @Override
    public String getResearch() {
        return "BASEAUROMANCY";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.TOUCH";
    }
    
    @Override
    public int getComplexity() {
        return 2;
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TRAJECTORY, EnumSupplyType.TARGET };
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.AVERSION;
    }
    
    @Override
    public Trajectory[] supplyTrajectories() {
        if (this.getParent() == null) {
            return new Trajectory[0];
        }
        final ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
        final double range = (this instanceof FocusMediumBolt) ? 16.0 : RayTracer.getBlockReachDistance((EntityPlayer)this.getPackage().getCaster());
        for (final Trajectory sT : this.getParent().supplyTrajectories()) {
            Vec3d end = sT.direction.func_72432_b();
            RayTraceResult ray = EntityUtils.getPointedEntityRay(this.getPackage().world, (Entity)this.getPackage().getCaster(), sT.source, end, 0.25, range, 0.25f, false);
            if (ray == null) {
                end = end.func_186678_a(range);
                end = end.func_178787_e(sT.source);
                ray = this.getPackage().world.func_72933_a(sT.source, end);
                if (ray != null) {
                    end = ray.field_72307_f;
                }
            }
            else if (ray.field_72308_g != null) {
                end = end.func_186678_a(sT.source.func_72438_d(ray.field_72308_g.func_174791_d()));
                end = end.func_178787_e(sT.source);
            }
            trajectories.add(new Trajectory(end, sT.direction.func_72432_b()));
        }
        return trajectories.toArray(new Trajectory[0]);
    }
    
    @Override
    public RayTraceResult[] supplyTargets() {
        if (this.getParent() == null || !(this.getPackage().getCaster() instanceof EntityPlayer)) {
            return new RayTraceResult[0];
        }
        final ArrayList<RayTraceResult> targets = new ArrayList<RayTraceResult>();
        final double range = (this instanceof FocusMediumBolt) ? 16.0 : RayTracer.getBlockReachDistance((EntityPlayer)this.getPackage().getCaster());
        for (final Trajectory sT : this.getParent().supplyTrajectories()) {
            Vec3d end = sT.direction.func_72432_b();
            RayTraceResult ray = EntityUtils.getPointedEntityRay(this.getPackage().world, (Entity)this.getPackage().getCaster(), sT.source, end, 0.25, range, 0.25f, false);
            if (ray == null) {
                end = end.func_186678_a(range);
                end = end.func_178787_e(sT.source);
                ray = this.getPackage().world.func_72933_a(sT.source, end);
            }
            if (ray != null) {
                targets.add(ray);
            }
        }
        return targets.toArray(new RayTraceResult[0]);
    }
    
    @Override
    public boolean execute(final Trajectory trajectory) {
        final FocusEffect[] fe = this.getPackage().getFocusEffects();
        if (fe != null && fe.length > 0) {
            final String[] effects = new String[fe.length];
            for (int a = 0; a < fe.length; ++a) {
                effects[a] = fe[a].getKey();
            }
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusEffect((float)trajectory.source.field_72450_a, (float)trajectory.source.field_72448_b, (float)trajectory.source.field_72449_c, (float)trajectory.direction.field_72450_a / 2.0f, (float)trajectory.direction.field_72448_b / 2.0f, (float)trajectory.direction.field_72449_c / 2.0f, effects), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), (double)(float)trajectory.source.field_72450_a, (double)(float)trajectory.source.field_72448_b, (double)(float)trajectory.source.field_72449_c, 64.0));
        }
        return true;
    }
}
