package thaumcraft.client.renderers.entity.projectile;

import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;

public class RenderEldritchOrb extends Render
{
    private Random random;
    
    public RenderEldritchOrb(final RenderManager renderManager) {
        super(renderManager);
        this.random = new Random();
        this.field_76989_e = 0.0f;
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        this.random.setSeed(187L);
        GL11.glPushMatrix();
        RenderHelper.func_74518_a();
        final float f1 = entity.field_70173_aa / 80.0f;
        float f2 = 0.9f;
        float f3 = 0.0f;
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        GL11.glEnable(2884);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        for (int i = 0; i < 12; ++i) {
            GL11.glRotatef(this.random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(this.random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.random.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(this.random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(this.random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.random.nextFloat() * 360.0f + f1 * 360.0f, 0.0f, 0.0f, 1.0f);
            tessellator.func_178180_c().func_181668_a(6, DefaultVertexFormats.field_181706_f);
            float fa = this.random.nextFloat() * 20.0f + 5.0f + f3 * 10.0f;
            float f4 = this.random.nextFloat() * 2.0f + 1.0f + f3 * 2.0f;
            fa /= 30.0f / (Math.min(entity.field_70173_aa, 10) / 10.0f);
            f4 /= 30.0f / (Math.min(entity.field_70173_aa, 10) / 10.0f);
            tessellator.func_178180_c().func_181662_b(0.0, 0.0, 0.0).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f - f3).func_181675_d();
            tessellator.func_178180_c().func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(64.0f, 64.0f, 64.0f, 255.0f * (1.0f - f3)).func_181675_d();
            tessellator.func_178180_c().func_181662_b(0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(64.0f, 64.0f, 64.0f, 255.0f * (1.0f - f3)).func_181675_d();
            tessellator.func_178180_c().func_181662_b(0.0, (double)fa, (double)(1.0f * f4)).func_181666_a(64.0f, 64.0f, 64.0f, 255.0f * (1.0f - f3)).func_181675_d();
            tessellator.func_178180_c().func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(64.0f, 64.0f, 64.0f, 255.0f * (1.0f - f3)).func_181675_d();
            tessellator.func_78381_a();
        }
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(2884);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        RenderHelper.func_74519_b();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_110776_a(ParticleEngine.particleTexture);
        f3 = entity.field_70173_aa % 13 / 64.0f;
        f2 = f3 + 0.015625f;
        final float f5 = 0.046875f;
        final float f6 = f5 + 0.015625f;
        final float f7 = 1.0f;
        final float f8 = 0.5f;
        final float f9 = 0.5f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f - this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181710_j);
        tessellator.func_178180_c();
        tessellator.func_178180_c().func_181662_b((double)(0.0f - f8), (double)(0.0f - f9), 0.0).func_187315_a((double)f3, (double)f6).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f7 - f8), (double)(0.0f - f9), 0.0).func_187315_a((double)f2, (double)f6).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f7 - f8), (double)(1.0f - f9), 0.0).func_187315_a((double)f2, (double)f5).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(0.0f - f8), (double)(1.0f - f9), 0.0).func_187315_a((double)f3, (double)f5).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return ParticleEngine.particleTexture;
    }
}
