package thaumcraft.common.items.casters.foci;

import thaumcraft.api.casters.*;
import net.minecraft.util.math.*;

public class FocusModSplitTarget extends FocusModSplit
{
    @Override
    public String getResearch() {
        return "FOCUSSPLIT";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.SPLITTARGET";
    }
    
    @Override
    public int getComplexity() {
        return 4;
    }
    
    @Override
    public EnumSupplyType[] mustBeSupplied() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET };
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET };
    }
    
    @Override
    public RayTraceResult[] supplyTargets() {
        return this.getParent().supplyTargets();
    }
    
    @Override
    public boolean execute() {
        return true;
    }
}
