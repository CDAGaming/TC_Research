package thaumcraft.client.renderers.entity.projectile;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.fx.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;

public class RenderHomingShard extends Render
{
    private Random random;
    private static final ResourceLocation beamTexture;
    
    public RenderHomingShard(final RenderManager rm) {
        super(rm);
        this.random = new Random();
        this.field_76989_e = 0.0f;
    }
    
    public void renderEntityAt(final EntityHomingShard entity, final double x, final double y, final double z, final float fq, final float pticks) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        this.func_110776_a(ParticleEngine.particleTexture);
        final float f2 = (8 + entity.field_70173_aa % 8) / 64.0f;
        final float f3 = f2 + 0.015625f;
        final float f4 = 0.0625f;
        final float f5 = f4 + 0.015625f;
        final float f6 = 1.0f;
        final float f7 = 0.5f;
        final float f8 = 0.5f;
        GL11.glColor4f(0.9f, 0.075f, 0.9525f, 1.0f);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(180.0f - this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.4f + 0.1f * entity.getStrength(), 0.4f + 0.1f * entity.getStrength(), 0.4f + 0.1f * entity.getStrength());
        tessellator.func_178180_c().func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        tessellator.func_178180_c().func_181662_b((double)(-f7), (double)(-f8), 0.0).func_187315_a((double)f2, (double)f5).func_181666_a(0.9f, 0.075f, 0.9525f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f6 - f7), (double)(-f8), 0.0).func_187315_a((double)f3, (double)f5).func_181666_a(0.9f, 0.075f, 0.9525f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(f6 - f7), (double)(1.0f - f8), 0.0).func_187315_a((double)f3, (double)f4).func_181666_a(0.9f, 0.075f, 0.9525f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(-f7), (double)(1.0f - f8), 0.0).func_187315_a((double)f2, (double)f4).func_181666_a(0.9f, 0.075f, 0.9525f, 1.0f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 1);
        this.func_110776_a(RenderHomingShard.beamTexture);
        final Minecraft mc = Minecraft.func_71410_x();
        final EntityPlayerSP p = mc.field_71439_g;
        final double doubleX = p.field_70142_S + (p.field_70165_t - p.field_70142_S) * pticks;
        final double doubleY = p.field_70137_T + (p.field_70163_u - p.field_70137_T) * pticks;
        final double doubleZ = p.field_70136_U + (p.field_70161_v - p.field_70136_U) * pticks;
        final UtilsFX.Vector player = new UtilsFX.Vector((float)doubleX, (float)doubleY, (float)doubleZ);
        final double dX = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * pticks;
        final double dY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * pticks;
        final double dZ = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * pticks;
        final UtilsFX.Vector start = new UtilsFX.Vector((float)dX, (float)dY, (float)dZ);
        if (entity.vl.size() == 0) {
            entity.vl.add(start);
        }
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
        UtilsFX.Vector vs = new UtilsFX.Vector((float)dX, (float)dY, (float)dZ);
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181711_k);
        int c = entity.vl.size();
        for (final UtilsFX.Vector nv : entity.vl) {
            UtilsFX.drawBeam(vs, nv, player, 0.25f * (c / entity.vl.size()), 240, 0.405f, 0.075f, 0.525f, 0.5f);
            vs = nv;
            --c;
        }
        tessellator.func_78381_a();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt((EntityHomingShard)entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
    
    static {
        beamTexture = new ResourceLocation("thaumcraft", "textures/misc/beaml.png");
    }
}
