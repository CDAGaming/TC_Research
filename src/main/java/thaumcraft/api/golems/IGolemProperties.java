package thaumcraft.api.golems;

import java.util.*;
import net.minecraft.item.*;
import thaumcraft.api.golems.parts.*;

public interface IGolemProperties
{
    Set<EnumGolemTrait> getTraits();
    
    boolean hasTrait(final EnumGolemTrait p0);
    
    long toLong();
    
    ItemStack[] generateComponents();
    
    void setMaterial(final GolemMaterial p0);
    
    GolemMaterial getMaterial();
    
    void setHead(final GolemHead p0);
    
    GolemHead getHead();
    
    void setArms(final GolemArm p0);
    
    GolemArm getArms();
    
    void setLegs(final GolemLeg p0);
    
    GolemLeg getLegs();
    
    void setAddon(final GolemAddon p0);
    
    GolemAddon getAddon();
    
    void setRank(final int p0);
    
    int getRank();
}
