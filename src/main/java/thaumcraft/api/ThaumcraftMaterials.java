package thaumcraft.api;

import net.minecraftforge.common.util.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class ThaumcraftMaterials
{
    public static Item.ToolMaterial TOOLMAT_THAUMIUM;
    public static Item.ToolMaterial TOOLMAT_VOID;
    public static Item.ToolMaterial TOOLMAT_ELEMENTAL;
    public static ItemArmor.ArmorMaterial ARMORMAT_THAUMIUM;
    public static ItemArmor.ArmorMaterial ARMORMAT_SPECIAL;
    public static ItemArmor.ArmorMaterial ARMORMAT_VOID;
    public static ItemArmor.ArmorMaterial ARMORMAT_VOIDROBE;
    public static ItemArmor.ArmorMaterial ARMORMAT_FORTRESS;
    public static final Material MATERIAL_TAINT;
    
    static {
        ThaumcraftMaterials.TOOLMAT_THAUMIUM = EnumHelper.addToolMaterial("THAUMIUM", 3, 500, 7.0f, 2.5f, 22).setRepairItem(new ItemStack(ItemsTC.ingots));
        ThaumcraftMaterials.TOOLMAT_VOID = EnumHelper.addToolMaterial("VOID", 4, 150, 8.0f, 3.0f, 10).setRepairItem(new ItemStack(ItemsTC.ingots, 1, 1));
        ThaumcraftMaterials.TOOLMAT_ELEMENTAL = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 9.0f, 3.0f, 18).setRepairItem(new ItemStack(ItemsTC.ingots));
        ThaumcraftMaterials.ARMORMAT_THAUMIUM = EnumHelper.addArmorMaterial("THAUMIUM", "THAUMIUM", 25, new int[] { 2, 5, 6, 2 }, 25, SoundEvents.field_187725_r, 1.0f);
        ThaumcraftMaterials.ARMORMAT_SPECIAL = EnumHelper.addArmorMaterial("SPECIAL", "SPECIAL", 25, new int[] { 1, 2, 3, 1 }, 25, SoundEvents.field_187728_s, 1.0f);
        ThaumcraftMaterials.ARMORMAT_VOID = EnumHelper.addArmorMaterial("VOID", "VOID", 10, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.field_187713_n, 1.0f);
        ThaumcraftMaterials.ARMORMAT_VOIDROBE = EnumHelper.addArmorMaterial("VOIDROBE", "VOIDROBE", 18, new int[] { 4, 7, 9, 4 }, 10, SoundEvents.field_187728_s, 2.0f);
        ThaumcraftMaterials.ARMORMAT_FORTRESS = EnumHelper.addArmorMaterial("FORTRESS", "FORTRESS", 40, new int[] { 3, 6, 7, 3 }, 25, SoundEvents.field_187725_r, 3.0f);
        MATERIAL_TAINT = new MaterialTaint();
    }
    
    public static class MaterialTaint extends Material
    {
        public MaterialTaint() {
            super(MapColor.field_151678_z);
            this.func_76219_n();
        }
        
        public boolean func_76230_c() {
            return true;
        }
    }
}
