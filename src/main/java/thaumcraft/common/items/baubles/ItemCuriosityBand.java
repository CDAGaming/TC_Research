package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import baubles.api.render.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import baubles.api.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import thaumcraft.client.lib.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemCuriosityBand extends ItemTCBase implements IBauble, IRenderBauble
{
    ResourceLocation tex;
    
    public ItemCuriosityBand() {
        super("curiosity_band", new String[0]);
        this.tex = new ResourceLocation("thaumcraft", "textures/items/curiosity_band_worn.png");
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.HEAD;
    }
    
    @SideOnly(Side.CLIENT)
    public void onPlayerBaubleRender(final ItemStack stack, final EntityPlayer player, final IRenderBauble.RenderType type, final float ticks) {
        if (type == IRenderBauble.RenderType.HEAD) {
            final boolean armor = !player.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b();
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
