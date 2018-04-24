package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import thaumcraft.client.renderers.models.gear.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemCultistPlateArmor extends ItemArmor implements IThaumcraftItems
{
    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;
    
    public ItemCultistPlateArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
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
        return (entity instanceof EntityInhabitedZombie) ? "thaumcraft:textures/entity/armor/zombie_plate_armor.png" : "thaumcraft:textures/entity/armor/cultist_plate_armor.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(Items.field_151042_j)) || super.func_82789_a(stack1, stack2);
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, final ModelBiped _default) {
        if (this.model1 == null) {
            this.model1 = new ModelKnightArmor(1.0f);
        }
        if (this.model2 == null) {
            this.model2 = new ModelKnightArmor(0.5f);
        }
        return this.model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, this.model, this.model1, this.model2);
    }
}
