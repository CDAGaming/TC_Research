package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import thaumcraft.api.golems.*;
import thaumcraft.api.items.*;

public class SealPickupAdvanced extends SealPickup implements ISealConfigToggles
{
    ResourceLocation icon;
    private static final ItemStack item;
    
    public SealPickupAdvanced() {
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_pickup_advanced");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:pickup_advanced";
    }
    
    @Override
    public int getFilterSize() {
        return 9;
    }
    
    @Override
    public ResourceLocation getSealIcon() {
        return this.icon;
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 2, 1, 3, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.SMART };
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
    }
    
    static {
        item = new ItemStack(ItemsTC.seals, 1, 7);
    }
}
