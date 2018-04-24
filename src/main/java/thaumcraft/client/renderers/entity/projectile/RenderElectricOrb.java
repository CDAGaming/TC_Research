package thaumcraft.client.renderers.entity.projectile;

import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.util.math.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderElectricOrb extends Render
{
    private Random random;
    
    public RenderElectricOrb(final RenderManager rm) {
        super(rm);
        this.random = new Random();
        this.field_76989_e = 0.0f;
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        this.func_110776_a(ParticleEngine.particleTexture);
        final float f2 = (1 + entity.field_70173_aa % 6) / 8.0f;
        final float f3 = f2 + 0.125f;
        float f4 = 0.875f;
        if (entity instanceof EntityGolemOrb && ((EntityGolemOrb)entity).red) {
            f4 = 0.75f;
        }
        final float f5 = f4 + 0.125f;
        final float f6 = 1.0f;
        final float f7 = 0.5f;
        final float f8 = 0.5f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glRotatef(180.0f - this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0f, 0.0f, 0.0f);
        final float bob = MathHelper.func_76126_a(entity.field_70173_aa / 5.0f) * 0.2f + 0.2f;
        GL11.glScalef(1.0f + bob, 1.0f + bob, 1.0f + bob);
        tessellator.func_178180_c().func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        final int i = 220;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        tessellator.func_178180_c().func_181662_b((double)(-f7), (double)(-f8), 0.0).func_187315_a((double)f2, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f6 - f7), (double)(-f8), 0.0).func_187315_a((double)f3, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f6 - f7), (double)(1.0f - f8), 0.0).func_187315_a((double)f3, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(-f7), (double)(1.0f - f8), 0.0).func_187315_a((double)f2, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
