package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;

public class ItemThaumiumArmor extends ItemArmor implements IThaumcraftItems
{
    public ItemThaumiumArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
        super(enumarmormaterial, j, k);
        this.setRegistryName(name);
        this.func_77655_b(name);
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
        this.func_77637_a(ConfigItems.TABTC);
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
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        if (stack.func_77973_b() == ItemsTC.thaumiumHelm || stack.func_77973_b() == ItemsTC.thaumiumChest || stack.func_77973_b() == ItemsTC.thaumiumBoots) {
            return "thaumcraft:textures/entity/armor/thaumium_1.png";
        }
        if (stack.func_77973_b() == ItemsTC.thaumiumLegs) {
            return "thaumcraft:textures/entity/armor/thaumium_2.png";
        }
        return "thaumcraft:textures/entity/armor/thaumium_1.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
}
