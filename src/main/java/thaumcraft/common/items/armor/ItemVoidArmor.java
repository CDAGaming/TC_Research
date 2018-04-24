package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class ItemVoidArmor extends ItemArmor implements IWarpingGear, IThaumcraftItems
{
    public ItemVoidArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
        super(enumarmormaterial, j, k);
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
        if (stack.func_77973_b() == ItemsTC.voidHelm || stack.func_77973_b() == ItemsTC.voidChest || stack.func_77973_b() == ItemsTC.voidBoots) {
            return "thaumcraft:textures/entity/armor/void_1.png";
        }
        if (stack.func_77973_b() == ItemsTC.voidLegs) {
            return "thaumcraft:textures/entity/armor/void_2.png";
        }
        return "thaumcraft:textures/entity/armor/void_1.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 1)) || super.func_82789_a(stack1, stack2);
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
        if (!world.field_72995_K && stack.func_77951_h() && entity.field_70173_aa % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.func_77972_a(-1, (EntityLivingBase)entity);
        }
    }
    
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack armor) {
        super.onArmorTick(world, player, armor);
        if (!world.field_72995_K && armor.func_77952_i() > 0 && player.field_70173_aa % 20 == 0) {
            armor.func_77972_a(-1, (EntityLivingBase)player);
        }
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 1;
    }
}
