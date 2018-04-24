package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.misc.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import thaumcraft.client.lib.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileBannerRenderer extends TileEntitySpecialRenderer
{
    private ModelBanner model;
    private static final ResourceLocation TEX_CULT;
    private static final ResourceLocation TEX_BLANK;
    
    public TileBannerRenderer() {
        this.model = new ModelBanner();
    }
    
    public void renderTileEntityAt(final TileBanner banner, final double par2, final double par4, final double par6, final float par8) {
        GL11.glPushMatrix();
        if (banner.getAspect() == null && banner.getColor() == -1) {
            this.func_147499_a(TileBannerRenderer.TEX_CULT);
        }
        else {
            this.func_147499_a(TileBannerRenderer.TEX_BLANK);
        }
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 1.5f, (float)par6 + 0.5f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (banner.func_145831_w() != null) {
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            final float f2 = banner.getBannerFacing() * 360 / 16.0f;
            GL11.glRotatef(f2, 0.0f, 1.0f, 0.0f);
        }
        if (!banner.getWall()) {
            this.model.renderPole();
        }
        else {
            GL11.glTranslated(0.0, 1.0, -0.4125);
        }
        this.model.renderBeam();
        if (banner.getColor() != -1) {
            final Color c = new Color(banner.getColor());
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
        }
        this.model.renderTabs();
        final EntityPlayer p = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        final float f3 = banner.func_174877_v().func_177958_n() * 7 + banner.func_174877_v().func_177956_o() * 9 + banner.func_174877_v().func_177952_p() * 13 + p.field_70173_aa + par8;
        final float rx = 0.02f - MathHelper.func_76126_a(f3 / 11.0f) * 0.02f;
        this.model.Banner.field_78795_f = rx;
        this.model.renderBanner();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (banner.getAspect() != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.05001f);
            GL11.glScaled(0.0375, 0.0375, 0.0375);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-rx * 57.295776f * 2.0f, 1.0f, 0.0f, 0.0f);
            UtilsFX.drawTag(-8, 0, banner.getAspect(), 0.0f, 0, 0.0, 771, 0.75f, false);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileBanner)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_CULT = new ResourceLocation("thaumcraft", "textures/models/banner_cultist.png");
        TEX_BLANK = new ResourceLocation("thaumcraft", "textures/models/banner_blank.png");
    }
}
