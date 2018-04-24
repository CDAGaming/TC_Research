package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public class ItemChunksEdible extends ItemFood implements IThaumcraftItems
{
    public final int field_77855_a;
    private String[] variants;
    
    public ItemChunksEdible() {
        super(1, 0.3f, false);
        this.variants = new String[] { "beef", "chicken", "pork", "fish", "rabbit", "mutton" };
        this.field_77855_a = 10;
        this.func_77625_d(64);
        this.func_77627_a(true);
        this.func_77656_e(0);
        this.setRegistryName("chunk");
        this.func_77655_b("chunk");
        this.func_77637_a(ConfigItems.TABTC);
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public int func_77626_a(final ItemStack stack1) {
        return this.field_77855_a;
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            for (int a = 0; a < this.variants.length; ++a) {
                items.add((Object)new ItemStack((Item)this, 1, a));
            }
        }
    }
    
    public String func_77667_c(final ItemStack itemStack) {
        if (this.field_77787_bX && itemStack.func_77960_j() < this.variants.length && this.variants[itemStack.func_77960_j()] != "chunk") {
            return String.format(super.func_77658_a() + ".%s", this.variants[itemStack.func_77960_j()]);
        }
        return super.func_77667_c(itemStack);
    }
    
    public String[] getVariantNames() {
        return this.variants;
    }
    
    public int[] getVariantMeta() {
        return new int[] { 0, 1, 2, 3, 4, 5 };
    }
    
    public Item getItem() {
        return (Item)this;
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        if (variant.equals("chunk")) {
            return new ModelResourceLocation("thaumcraft:chunk");
        }
        return new ModelResourceLocation("thaumcraft:chunk", variant);
    }
}
