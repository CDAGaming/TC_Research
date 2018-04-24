package thaumcraft.common.items.casters.foci;

import thaumcraft.api.casters.*;
import java.util.*;
import net.minecraft.util.math.*;

public class FocusModScatter extends FocusMod
{
    @Override
    public String getResearch() {
        return "FOCUSSCATTER";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.SCATTER";
    }
    
    @Override
    public int getComplexity() {
        return (int)Math.max(2.0f, 2.0f * (this.getSettingValue("forks") - this.getSettingValue("cone") / 45.0f));
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] angles = { 10, 30, 60, 90, 180, 270, 360 };
        final String[] anglesDesc = { "10\u00ef申", "30\u00ef申", "60\u00ef申", "90\u00ef申", "180\u00ef申", "270\u00ef申", "360\u00ef申" };
        return new NodeSetting[] { new NodeSetting("forks", "focus.scatter.forks", new NodeSetting.NodeSettingIntRange(2, 10)), new NodeSetting("cone", "focus.scatter.cone", new NodeSetting.NodeSettingIntList(angles, anglesDesc)) };
    }
    
    @Override
    public EnumSupplyType[] mustBeSupplied() {
        return new EnumSupplyType[] { EnumSupplyType.TRAJECTORY };
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TRAJECTORY };
    }
    
    @Override
    public Trajectory[] supplyTrajectories() {
        if (this.getParent() == null) {
            return new Trajectory[0];
        }
        final ArrayList<Trajectory> tT = new ArrayList<Trajectory>();
        final int forks = this.getSettingValue("forks");
        final int angle = this.getSettingValue("cone");
        if (this.getParent().supplyTrajectories() != null) {
            for (final Trajectory sT : this.getParent().supplyTrajectories()) {
                for (int a = 0; a < forks; ++a) {
                    final Vec3d sV = sT.source;
                    Vec3d dV = sT.direction;
                    dV = dV.func_72432_b();
                    dV = dV.func_72441_c(this.getPackage().world.field_73012_v.nextGaussian() * 0.007499999832361937 * angle, this.getPackage().world.field_73012_v.nextGaussian() * 0.007499999832361937 * angle, this.getPackage().world.field_73012_v.nextGaussian() * 0.007499999832361937 * angle);
                    tT.add(new Trajectory(sV, dV.func_72432_b()));
                }
            }
        }
        return tT.toArray(new Trajectory[0]);
    }
    
    @Override
    public float getPowerMultiplier() {
        return 1.0f / (this.getSettingValue("forks") / 2.0f);
    }
    
    @Override
    public boolean execute() {
        return true;
    }
}
