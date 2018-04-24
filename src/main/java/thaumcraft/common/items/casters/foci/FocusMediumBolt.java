package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import java.awt.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;

public class FocusMediumBolt extends FocusMediumTouch
{
    @Override
    public String getResearch() {
        return "FOCUSBOLT";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.BOLT";
    }
    
    @Override
    public int getComplexity() {
        return 5;
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.ENERGY;
    }
    
    @Override
    public boolean execute(final Trajectory trajectory) {
        final float range = 16.0f;
        Vec3d end = trajectory.direction.func_72432_b();
        RayTraceResult ray = EntityUtils.getPointedEntityRay(this.getPackage().world, (Entity)this.getPackage().getCaster(), trajectory.source, end, 0.25, range, 0.25f, false);
        if (ray == null) {
            end = end.func_186678_a((double)range);
            end = end.func_178787_e(trajectory.source);
            ray = this.getPackage().world.func_72933_a(trajectory.source, end);
            if (ray != null) {
                end = ray.field_72307_f;
            }
        }
        else if (ray.field_72308_g != null) {
            end = end.func_186678_a(trajectory.source.func_72438_d(ray.field_72308_g.func_174791_d()));
            end = end.func_178787_e(trajectory.source);
        }
        int r = 0;
        int g = 0;
        int b = 0;
        for (final FocusEffect ef : this.getPackage().getFocusEffects()) {
            final Color c = new Color(FocusEngine.getElementColor(ef.getKey()));
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }
        r /= this.getPackage().getFocusEffects().length;
        g /= this.getPackage().getFocusEffects().length;
        b /= this.getPackage().getFocusEffects().length;
        final Color c2 = new Color(r, g, b);
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXZap(trajectory.source, end, c2.getRGB(), this.getPackage().getPower() * 0.66f), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), trajectory.source.field_72450_a, trajectory.source.field_72448_b, trajectory.source.field_72449_c, 64.0));
        return true;
    }
}
