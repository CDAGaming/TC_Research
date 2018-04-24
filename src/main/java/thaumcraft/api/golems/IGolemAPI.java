package thaumcraft.api.golems;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public interface IGolemAPI
{
    EntityLivingBase getGolemEntity();
    
    IGolemProperties getProperties();
    
    void setProperties(final IGolemProperties p0);
    
    World getGolemWorld();
    
    ItemStack holdItem(final ItemStack p0);
    
    ItemStack dropItem(final ItemStack p0);
    
    boolean canCarry(final ItemStack p0, final boolean p1);
    
    boolean isCarrying(final ItemStack p0);
    
    NonNullList<ItemStack> getCarrying();
    
    void addRankXp(final int p0);
    
    byte getGolemColor();
    
    void swingArm();
    
    boolean isInCombat();
}
