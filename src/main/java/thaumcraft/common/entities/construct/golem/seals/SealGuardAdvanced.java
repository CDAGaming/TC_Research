package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import thaumcraft.api.golems.*;
import thaumcraft.api.items.*;

public class SealGuardAdvanced extends SealGuard implements ISealConfigToggles
{
    ResourceLocation icon;
    private static final ItemStack item;
    
    public SealGuardAdvanced() {
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_guard_advanced");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:guard_advanced";
    }
    
    @Override
    public ResourceLocation getSealIcon() {
        return this.icon;
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 2, 3, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.FIGHTER, EnumGolemTrait.SMART };
    }
    
    static {
        item = new ItemStack(ItemsTC.seals, 1, 10);
    }
}
