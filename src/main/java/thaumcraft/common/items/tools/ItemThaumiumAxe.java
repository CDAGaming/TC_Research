package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;
import thaumcraft.api.items.*;

public class ItemThaumiumAxe extends ItemAxe implements IThaumcraftItems
{
    public ItemThaumiumAxe(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial, 8.0f, -3.0f);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("thaumium_axe");
        this.func_77655_b("thaumium_axe");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public Item getItem() {
        return (Item)this;
    }
    
    public String[] getVariantNames() {
        return new String[] { "normal" };
    }
    
    public int[] getVariantMeta() {
        return new int[] { 0 };
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        return new ModelResourceLocation("thaumcraft:" + variant);
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        return (Set<String>)ImmutableSet.of((Object)"axe");
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
}
