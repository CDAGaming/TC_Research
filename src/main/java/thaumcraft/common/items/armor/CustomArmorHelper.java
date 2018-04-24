package thaumcraft.common.items.armor;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class CustomArmorHelper
{
    protected static ModelBiped getCustomArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, ModelBiped model, final ModelBiped model1, final ModelBiped model2) {
        if (model == null) {
            final EntityEquipmentSlot type = ((ItemArmor)itemStack.func_77973_b()).field_77881_a;
            if (type == EntityEquipmentSlot.CHEST || type == EntityEquipmentSlot.FEET) {
                model = model1;
            }
            else {
                model = model2;
            }
        }
        if (model != null) {
            model.field_78116_c.field_78806_j = (armorSlot == EntityEquipmentSlot.HEAD);
            model.field_178720_f.field_78806_j = (armorSlot == EntityEquipmentSlot.HEAD);
            model.field_78115_e.field_78806_j = (armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.LEGS);
            model.field_178723_h.field_78806_j = (armorSlot == EntityEquipmentSlot.CHEST);
            model.field_178724_i.field_78806_j = (armorSlot == EntityEquipmentSlot.CHEST);
            model.field_178721_j.field_78806_j = (armorSlot == EntityEquipmentSlot.LEGS);
            model.field_178722_k.field_78806_j = (armorSlot == EntityEquipmentSlot.LEGS);
            model.field_78117_n = entityLiving.func_70093_af();
            model.field_78093_q = entityLiving.func_184218_aH();
            model.field_78091_s = entityLiving.func_70631_g_();
            final ItemStack itemstack = entityLiving.func_184614_ca();
            final ItemStack itemstack2 = entityLiving.func_184592_cb();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose2 = ModelBiped.ArmPose.EMPTY;
            if (itemstack != null && !itemstack.func_190926_b()) {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;
                if (entityLiving.func_184605_cv() > 0) {
                    final EnumAction enumaction = itemstack.func_77975_n();
                    if (enumaction == EnumAction.BLOCK) {
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    }
                    else if (enumaction == EnumAction.BOW) {
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }
            if (itemstack2 != null && !itemstack2.func_190926_b()) {
                modelbiped$armpose2 = ModelBiped.ArmPose.ITEM;
                if (entityLiving.func_184605_cv() > 0) {
                    final EnumAction enumaction2 = itemstack2.func_77975_n();
                    if (enumaction2 == EnumAction.BLOCK) {
                        modelbiped$armpose2 = ModelBiped.ArmPose.BLOCK;
                    }
                }
            }
            if (entityLiving.func_184591_cq() == EnumHandSide.RIGHT) {
                model.field_187076_m = modelbiped$armpose;
                model.field_187075_l = modelbiped$armpose2;
            }
            else {
                model.field_187076_m = modelbiped$armpose2;
                model.field_187075_l = modelbiped$armpose;
            }
        }
        return model;
    }
}
