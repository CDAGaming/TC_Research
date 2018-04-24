package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.entity.*;
import thaumcraft.api.casters.*;

public class FocusMediumMine extends FocusMedium
{
    @Override
    public String getResearch() {
        return "FOCUSMINE";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.MINE";
    }
    
    @Override
    public int getComplexity() {
        return 4;
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.TRAP;
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET, EnumSupplyType.TRAJECTORY };
    }
    
    @Override
    public boolean execute(final Trajectory trajectory) {
        final EntityFocusMine projectile = new EntityFocusMine(this.getRemainingPackage(), trajectory, this.getSettingValue("target") == 1);
        return this.getPackage().getCaster().field_70170_p.func_72838_d((Entity)projectile);
    }
    
    @Override
    public boolean hasIntermediary() {
        return true;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] friend = { 0, 1 };
        final String[] friendDesc = { "focus.common.enemy", "focus.common.friend" };
        return new NodeSetting[] { new NodeSetting("target", "focus.common.target", new NodeSetting.NodeSettingIntList(friend, friendDesc)) };
    }
}
