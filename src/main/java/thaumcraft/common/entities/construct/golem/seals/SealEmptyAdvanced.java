package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import thaumcraft.api.golems.*;
import thaumcraft.api.items.*;

public class SealEmptyAdvanced extends SealEmpty implements ISealConfigToggles
{
    ResourceLocation icon;
    private static final ItemStack item;
    
    public SealEmptyAdvanced() {
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_empty_advanced");
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:empty_advanced";
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
    public NonNullList<ItemStack> getInv(final int c) {
        if (this.getToggles()[4].value && !this.isBlacklist()) {
            final ArrayList<ItemStack> w = new ArrayList<ItemStack>();
            for (final ItemStack s : super.getInv()) {
                if (s != null && !s.func_190926_b()) {
                    w.add(s);
                }
            }
            if (w.size() > 0) {
                final int i = Math.abs(c % w.size());
                return (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)w.get(i));
            }
        }
        return super.getInv();
    }
    
    @Override
    public NonNullList<ItemStack> getInv() {
        return super.getInv();
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 1, 3, 0, 4 };
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.SMART };
    }
    
    static {
        item = new ItemStack(ItemsTC.seals, 1, 5);
    }
}
