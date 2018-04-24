package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.common.util.*;
import thaumcraft.api.items.*;

public class ItemCrimsonBlade extends ItemSword implements IWarpingGear, IThaumcraftItems
{
    public static Item.ToolMaterial toolMatCrimsonVoid;
    
    public ItemCrimsonBlade() {
        super(ItemCrimsonBlade.toolMatCrimsonVoid);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("crimson_blade");
        this.func_77655_b("crimson_blade");
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
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.EPIC;
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
        if (stack.func_77951_h() && entity != null && entity.field_70173_aa % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.func_77972_a(-1, (EntityLivingBase)entity);
        }
    }
    
    public boolean func_77644_a(final ItemStack is, final EntityLivingBase target, final EntityLivingBase hitter) {
        if (!target.field_70170_p.field_72995_K) {
            if (!(target instanceof EntityPlayer) || !(hitter instanceof EntityPlayer) || FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W()) {
                try {
                    target.func_70690_d(new PotionEffect(MobEffects.field_76437_t, 60));
                    target.func_70690_d(new PotionEffect(MobEffects.field_76438_s, 120));
                }
                catch (Exception ex) {}
            }
        }
        return super.func_77644_a(is, target, hitter);
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 2;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GOLD + I18n.func_74838_a("enchantment.special.sapgreat"));
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
    
    static {
        ItemCrimsonBlade.toolMatCrimsonVoid = EnumHelper.addToolMaterial("CVOID", 4, 200, 8.0f, 3.5f, 20).setRepairItem(new ItemStack(ItemsTC.ingots, 1, 1));
    }
}
