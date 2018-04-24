package thaumcraft.common.items.armor;

import thaumcraft.api.items.*;
import thaumcraft.common.items.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ItemCultistBoots extends ItemArmor implements IWarpingGear, IVisDiscountGear, IThaumcraftItems
{
    public ItemCultistBoots() {
        super(ItemArmor.ArmorMaterial.IRON, 2, EntityEquipmentSlot.FEET);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("crimson_boots");
        this.func_77655_b("crimson_boots");
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
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        return "thaumcraft:textures/entity/armor/cultistboots.png";
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(Items.field_151042_j)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 1;
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        return 1;
    }
}
