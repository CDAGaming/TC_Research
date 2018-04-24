package thaumcraft.api.casters;

public abstract class FocusMedium extends FocusNode
{
    @Override
    public IFocusElement.EnumUnitType getType() {
        return IFocusElement.EnumUnitType.MEDIUM;
    }
    
    @Override
    public final EnumSupplyType[] mustBeSupplied() {
        final EnumSupplyType[] array;
        if (!(this instanceof FocusMediumRoot)) {
            array = new EnumSupplyType[] { EnumSupplyType.TRAJECTORY };
        }
        return array;
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET };
    }
    
    public boolean hasIntermediary() {
        return false;
    }
    
    public boolean execute(final Trajectory trajectory) {
        return true;
    }
    
    public boolean isExclusive() {
        return false;
    }
}
