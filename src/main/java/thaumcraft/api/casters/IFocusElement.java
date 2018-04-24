package thaumcraft.api.casters;

public interface IFocusElement
{
    String getKey();
    
    String getResearch();
    
    EnumUnitType getType();
    
    public enum EnumUnitType
    {
        EFFECT, 
        MEDIUM, 
        MOD, 
        PACKAGE;
    }
}
