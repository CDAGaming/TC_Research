package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import baubles.api.render.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import baubles.api.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import thaumcraft.client.lib.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemGoggles extends ItemArmor implements IVisDiscountGear, IRevealer, IGoggles, IThaumcraftItems, IBauble, IRenderBauble
{
    ResourceLocation tex;
    
    public ItemGoggles() {
        super(ThaumcraftMaterials.ARMORMAT_SPECIAL, 4, EntityEquipmentSlot.HEAD);
        this.tex = new ResourceLocation("thaumcraft", "textures/items/goggles_bauble.png");
        this.func_77656_e(350);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("goggles");
        this.func_77655_b("goggles");
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
        return "thaumcraft:textures/entity/armor/goggles.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 2)) || super.func_82789_a(stack1, stack2);
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        return 5;
    }
    
    public boolean showNodes(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
    
    public boolean showIngamePopups(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
    
    public BaubleType getBaubleType(final ItemStack arg0) {
        return BaubleType.HEAD;
    }
    
    @SideOnly(Side.CLIENT)
    public void onPlayerBaubleRender(final ItemStack stack, final EntityPlayer player, final IRenderBauble.RenderType type, final float ticks) {
        if (type == IRenderBauble.RenderType.HEAD) {
            final boolean armor = player.func_184582_a(EntityEquipmentSlot.HEAD) != null;
            Minecraft.func_71410_x().field_71446_o.func_110577_a(this.tex);
            IRenderBauble.Helper.translateToHeadLevel(player);
            IRenderBauble.Helper.translateToFace();
            IRenderBauble.Helper.defaultTransforms();
            GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179137_b(-0.5, -0.5, armor ? 0.11999999731779099 : 0.0);
            UtilsFX.renderTextureIn3D(0.0f, 0.0f, 1.0f, 1.0f, 16, 26, 0.1f);
        }
    }
}
