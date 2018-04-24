package thaumcraft.common.items.armor;

import thaumcraft.api.items.*;
import thaumcraft.common.items.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import thaumcraft.client.renderers.models.gear.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemCultistRobeArmor extends ItemArmor implements IVisDiscountGear, IWarpingGear, IThaumcraftItems
{
    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;
    
    public ItemCultistRobeArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
        super(enumarmormaterial, j, k);
        this.model1 = null;
        this.model2 = null;
        this.model = null;
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName(name);
        this.func_77655_b(name);
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
        return "thaumcraft:textures/entity/armor/cultist_robe_armor.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(Items.field_151042_j)) || super.func_82789_a(stack1, stack2);
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        return 1;
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, final ModelBiped _default) {
        if (this.model1 == null) {
            this.model1 = new ModelRobe(1.0f);
        }
        if (this.model2 == null) {
            this.model2 = new ModelRobe(0.5f);
        }
        return this.model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, this.model, this.model1, this.model2);
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 1;
    }
}
