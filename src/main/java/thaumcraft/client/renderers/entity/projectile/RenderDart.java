package thaumcraft.client.renderers.entity.projectile;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import thaumcraft.client.fx.*;

@SideOnly(Side.CLIENT)
public class RenderDart extends Render
{
    private static final ResourceLocation arrowTextures;
    int size1;
    int size2;
    
    public RenderDart(final RenderManager renderManager) {
        super(renderManager);
        this.size1 = 0;
        this.size2 = 0;
    }
    
    public void renderArrow(final EntityArrow arrow, final double x, final double y, final double z, final float ns, final float prt) {
        this.func_180548_c((Entity)arrow);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(arrow.field_70126_B + (arrow.field_70177_z - arrow.field_70126_B) * prt - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(arrow.field_70127_C + (arrow.field_70125_A - arrow.field_70127_C) * prt, 0.0f, 0.0f, 1.0f);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final byte b0 = 0;
        final float f2 = 0.0f;
        final float f3 = 0.5f;
        final float f4 = (0 + b0 * 10) / 32.0f;
        final float f5 = (5 + b0 * 10) / 32.0f;
        final float f6 = 0.0f;
        final float f7 = 0.15625f;
        final float f8 = (5 + b0 * 10) / 32.0f;
        final float f9 = (10 + b0 * 10) / 32.0f;
        final float f10 = 0.033f;
        GL11.glEnable(32826);
        final float f11 = arrow.field_70249_b - prt;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.func_76126_a(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(f10, 0.0f, 0.0f);
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        tessellator.func_178180_c().func_181662_b(-7.0, -2.0, -2.0).func_187315_a((double)f6, (double)f8).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, -2.0, 2.0).func_187315_a((double)f7, (double)f8).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, 2.0, 2.0).func_187315_a((double)f7, (double)f9).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, 2.0, -2.0).func_187315_a((double)f6, (double)f9).func_181675_d();
        tessellator.func_78381_a();
        GL11.glNormal3f(-f10, 0.0f, 0.0f);
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        tessellator.func_178180_c().func_181662_b(-7.0, 2.0, -2.0).func_187315_a((double)f6, (double)f8).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, 2.0, 2.0).func_187315_a((double)f7, (double)f8).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, -2.0, 2.0).func_187315_a((double)f7, (double)f9).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-7.0, -2.0, -2.0).func_187315_a((double)f6, (double)f9).func_181675_d();
        tessellator.func_78381_a();
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, f10);
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
            tessellator.func_178180_c().func_181662_b(-8.0, -2.0, 0.0).func_187315_a((double)f2, (double)f4).func_181675_d();
            tessellator.func_178180_c().func_181662_b(8.0, -2.0, 0.0).func_187315_a((double)f3, (double)f4).func_181675_d();
            tessellator.func_178180_c().func_181662_b(8.0, 2.0, 0.0).func_187315_a((double)f3, (double)f5).func_181675_d();
            tessellator.func_178180_c().func_181662_b(-8.0, 2.0, 0.0).func_187315_a((double)f2, (double)f5).func_181675_d();
            tessellator.func_78381_a();
        }
        GL11.glDisable(32826);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.func_110776_a(ParticleEngine.particleTexture);
        GL11.glPopMatrix();
    }
    
    protected ResourceLocation getArrowTextures(final EntityArrow par1EntityArrow) {
        return RenderDart.arrowTextures;
    }
    
    protected ResourceLocation func_110775_a(final Entity par1Entity) {
        return this.getArrowTextures((EntityArrow)par1Entity);
    }
    
    public void func_76986_a(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderArrow((EntityArrow)par1Entity, par2, par4, par6, par8, par9);
    }
    
    static {
        arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }
}
