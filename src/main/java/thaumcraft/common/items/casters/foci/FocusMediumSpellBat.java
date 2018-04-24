package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.entity.*;
import thaumcraft.api.casters.*;

public class FocusMediumSpellBat extends FocusMedium
{
    @Override
    public String getResearch() {
        return "FOCUSSPELLBAT";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.SPELLBAT";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.BEAST;
    }
    
    @Override
    public int getComplexity() {
        return 8;
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET };
    }
    
    @Override
    public boolean execute(final Trajectory trajectory) {
        final EntitySpellBat bat = new EntitySpellBat(this.getRemainingPackage(), this.getSettingValue("target") == 1);
        bat.func_70107_b(trajectory.source.field_72450_a, trajectory.source.field_72448_b, trajectory.source.field_72449_c);
        return this.getPackage().getCaster().field_70170_p.func_72838_d((Entity)bat);
    }
    
    @Override
    public boolean hasIntermediary() {
        return true;
    }
    
    @Override
    public float getPowerMultiplier() {
        return 0.33f;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] friend = { 0, 1 };
        final String[] friendDesc = { "focus.common.enemy", "focus.common.friend" };
        return new NodeSetting[] { new NodeSetting("target", "focus.common.target", new NodeSetting.NodeSettingIntList(friend, friendDesc)) };
    }
}
