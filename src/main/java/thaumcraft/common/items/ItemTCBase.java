package thaumcraft.common.items;

import thaumcraft.common.config.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public class ItemTCBase extends Item implements IThaumcraftItems
{
    protected final String BASE_NAME;
    protected String[] VARIANTS;
    protected int[] VARIANTS_META;
    
    public ItemTCBase(final String name, final String... variants) {
        this.setRegistryName(name);
        this.func_77655_b(name);
        this.func_77637_a(ConfigItems.TABTC);
        this.setNoRepair();
        this.func_77627_a(variants.length > 1);
        this.BASE_NAME = name;
        if (variants.length == 0) {
            this.VARIANTS = new String[] { name };
        }
        else {
            this.VARIANTS = variants;
        }
        this.VARIANTS_META = new int[this.VARIANTS.length];
        for (int m = 0; m < this.VARIANTS.length; ++m) {
            this.VARIANTS_META[m] = m;
        }
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public String func_77667_c(final ItemStack itemStack) {
        if (this.field_77787_bX && itemStack.func_77960_j() < this.VARIANTS.length && this.VARIANTS[itemStack.func_77960_j()] != this.BASE_NAME) {
            return String.format(super.func_77658_a() + ".%s", this.VARIANTS[itemStack.func_77960_j()]);
        }
        return super.func_77667_c(itemStack);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC || tab == CreativeTabs.field_78027_g) {
            if (!this.func_77614_k()) {
                super.func_150895_a(tab, (NonNullList)items);
            }
            else {
                for (int meta = 0; meta < this.VARIANTS.length; ++meta) {
                    items.add((Object)new ItemStack((Item)this, 1, meta));
                }
            }
        }
    }
    
    public Item getItem() {
        return this;
    }
    
    public String[] getVariantNames() {
        return this.VARIANTS;
    }
    
    public int[] getVariantMeta() {
        return this.VARIANTS_META;
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        if (variant.equals(this.BASE_NAME)) {
            return new ModelResourceLocation("thaumcraft:" + this.BASE_NAME);
        }
        return new ModelResourceLocation("thaumcraft:" + this.BASE_NAME, variant);
    }
}
