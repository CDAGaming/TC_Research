package thaumcraft.common.items.armor;

import net.minecraftforge.common.*;
import thaumcraft.common.items.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import thaumcraft.client.renderers.models.gear.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.api.items.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class ItemFortressArmor extends ItemArmor implements ISpecialArmor, IGoggles, IRevealer, IThaumcraftItems
{
    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;
    
    public ItemFortressArmor(final String name, final ItemArmor.ArmorMaterial material, final int renderIndex, final EntityEquipmentSlot armorType) {
        super(material, renderIndex, armorType);
        this.model1 = null;
        this.model2 = null;
        this.model = null;
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
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, final ModelBiped _default) {
        if (this.model1 == null) {
            this.model1 = new ModelFortressArmor(1.0f);
        }
        if (this.model2 == null) {
            this.model2 = new ModelFortressArmor(0.5f);
        }
        return this.model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, this.model, this.model1, this.model2);
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        return "thaumcraft:textures/entity/armor/fortress_armor.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("goggles")) {
            tooltip.add(TextFormatting.DARK_PURPLE + I18n.func_74838_a("item.goggles.name"));
        }
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("mask")) {
            tooltip.add(TextFormatting.GOLD + I18n.func_74838_a("item.fortress_helm.mask." + stack.func_77978_p().func_74762_e("mask")));
        }
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public ISpecialArmor.ArmorProperties getProperties(final EntityLivingBase player, final ItemStack armor, final DamageSource source, final double damage, final int slot) {
        int priority = 0;
        double ratio = this.field_77879_b / 25.0;
        if (source.func_82725_o()) {
            priority = 1;
            ratio = this.field_77879_b / 35.0;
        }
        else if (source.func_76347_k() || source.func_94541_c()) {
            priority = 1;
            ratio = this.field_77879_b / 20.0;
        }
        else if (source.func_76363_c()) {
            priority = 0;
            ratio = 0.0;
        }
        if (player instanceof EntityPlayer) {
            double set = 0.875;
            for (int a = 1; a < 4; ++a) {
                final ItemStack piece = (ItemStack)((EntityPlayer)player).field_71071_by.field_70460_b.get(a);
                if (piece != null && !piece.func_190926_b() && piece.func_77973_b() instanceof ItemFortressArmor) {
                    set += 0.125;
                    if (piece.func_77942_o() && piece.func_77978_p().func_74764_b("mask")) {
                        set += 0.05;
                    }
                }
            }
            ratio *= set;
        }
        return new ISpecialArmor.ArmorProperties(priority, ratio, armor.func_77958_k() + 1 - armor.func_77952_i());
    }
    
    public int getArmorDisplay(final EntityPlayer player, final ItemStack armor, final int slot) {
        return this.field_77879_b;
    }
    
    public void damageArmor(final EntityLivingBase entity, final ItemStack stack, final DamageSource source, final int damage, final int slot) {
        if (source != DamageSource.field_76379_h) {
            stack.func_77972_a(damage, entity);
        }
    }
    
    public boolean showNodes(final ItemStack itemstack, final EntityLivingBase player) {
        return itemstack.func_77942_o() && itemstack.func_77978_p().func_74764_b("goggles");
    }
    
    public boolean showIngamePopups(final ItemStack itemstack, final EntityLivingBase player) {
        return itemstack.func_77942_o() && itemstack.func_77978_p().func_74764_b("goggles");
    }
}
