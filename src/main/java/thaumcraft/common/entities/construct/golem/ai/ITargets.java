package thaumcraft.common.entities.construct.golem.ai;

public interface ITargets
{
    boolean getTargetAnimal();
    
    void setTargetAnimal(final boolean p0);
    
    boolean getTargetMob();
    
    void setTargetMob(final boolean p0);
    
    boolean getTargetPlayer();
    
    void setTargetPlayer(final boolean p0);
    
    boolean getTargetFriendly();
    
    void setTargetFriendly(final boolean p0);
}
