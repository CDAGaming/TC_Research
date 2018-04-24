package thaumcraft.client.renderers.entity.projectile;

import thaumcraft.client.renderers.models.entity.*;
import com.sasmaster.glelwjgl.java.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.math.*;
import thaumcraft.client.lib.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderGrapple extends Render
{
    ResourceLocation beam;
    ResourceLocation rope;
    private ModelGrappler model;
    CoreGLE gle;
    public ArrayList<Vec3d> points;
    public float length;
    
    public RenderGrapple(final RenderManager rm) {
        super(rm);
        this.beam = new ResourceLocation("thaumcraft", "textures/entity/grappler.png");
        this.rope = new ResourceLocation("thaumcraft", "textures/misc/rope.png");
        this.gle = new CoreGLE();
        this.points = new ArrayList<Vec3d>();
        this.length = 1.0f;
        this.field_76989_e = 0.0f;
        this.model = new ModelGrappler();
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        this.func_110776_a(this.beam);
        GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * pticks - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * pticks, 0.0f, 0.0f, 1.0f);
        this.model.render();
        GL11.glPopMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(2884);
        this.func_110776_a(ParticleEngine.particleTexture);
        final float f2 = (1 + entity.field_70173_aa % 6) / 32.0f;
        final float f3 = f2 + 0.03125f;
        final float f4 = 0.21875f;
        final float f5 = f4 + 0.03125f;
        final float f6 = 0.5f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glRotatef(180.0f - this.field_76990_c.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.field_76990_c.field_78732_j, 1.0f, 0.0f, 0.0f);
        final float bob = MathHelper.func_76126_a(entity.field_70173_aa / 5.0f) * 0.2f + 0.2f;
        GL11.glScalef(1.0f + bob, 1.0f + bob, 1.0f + bob);
        tessellator.func_178180_c().func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        final int i = 220;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        tessellator.func_178180_c().func_181662_b((double)(-f6), (double)(-f6), 0.0).func_187315_a((double)f2, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, 0.21f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)f6, (double)(-f6), 0.0).func_187315_a((double)f3, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, 0.21f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)f6, (double)f6, 0.0).func_187315_a((double)f3, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, 0.21f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(-f6), (double)f6, 0.0).func_187315_a((double)f2, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, 0.21f).func_187314_a(j, k).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glEnable(2884);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        this.calcPoints(((EntityGrapple)entity).func_85052_h(), (EntityGrapple)entity, pticks);
        GL11.glPushMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (this.points != null && this.points.size() > 2) {
            final double[][] pp = new double[this.points.size()][3];
            final float[][] colours = new float[this.points.size()][4];
            final double[] radii = new double[this.points.size()];
            for (int a = 0; a < this.points.size(); ++a) {
                pp[a][0] = this.points.get(a).field_72450_a + x;
                pp[a][1] = this.points.get(a).field_72448_b + y;
                pp[a][2] = this.points.get(a).field_72449_c + z;
                colours[a][0] = 1.0f;
                colours[a][1] = 1.0f;
                colours[a][2] = 1.0f;
                colours[a][3] = 1.0f;
                radii[a] = 0.025;
            }
            Minecraft.func_71410_x().field_71446_o.func_110577_a(this.rope);
            this.gle.set_POLYCYL_TESS(4);
            this.gle.gleSetJoinStyle(1042);
            this.gle.glePolyCone(pp.length, pp, colours, radii, 2.0f, this.length - this.points.size());
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    private void calcPoints(final EntityLivingBase thrower, final EntityGrapple grapple, final float pt) {
        if (thrower == null || grapple == null) {
            return;
        }
        double tx = thrower.field_70142_S + (thrower.field_70165_t - thrower.field_70142_S) * pt;
        double ty = thrower.field_70137_T + (thrower.field_70163_u - thrower.field_70137_T) * pt;
        double tz = thrower.field_70136_U + (thrower.field_70161_v - thrower.field_70136_U) * pt;
        if (Minecraft.func_71410_x().field_71474_y.field_74320_O == 0) {
            final double yy = thrower.field_70126_B + (thrower.field_70177_z - thrower.field_70126_B) * pt;
            final double px = -MathHelper.func_76134_b((float)((yy - 0.5) / 180.0 * 3.1415929794311523)) * 0.1f * ((grapple.hand == EnumHand.MAIN_HAND) ? 1 : -1);
            final double pz = -MathHelper.func_76126_a((float)((yy - 0.5) / 180.0 * 3.1415929794311523)) * 0.1f * ((grapple.hand == EnumHand.MAIN_HAND) ? 1 : -1);
            final Vec3d vl = thrower.func_70040_Z();
            tx += px + vl.field_72450_a / 5.0;
            ty += thrower.func_70047_e() / 2.6 + vl.field_72448_b / 5.0;
            tz += pz + vl.field_72449_c / 5.0;
        }
        final double gx = grapple.field_70142_S + (grapple.field_70165_t - grapple.field_70142_S) * pt;
        final double gy = grapple.field_70137_T + (grapple.field_70163_u - grapple.field_70137_T) * pt;
        final double gz = grapple.field_70136_U + (grapple.field_70161_v - grapple.field_70136_U) * pt;
        this.points.clear();
        final Vec3d vs = new Vec3d(0.0, 0.0, 0.0);
        final Vec3d ve = new Vec3d(tx - gx, ty - gy + thrower.field_70131_O / 2.0f, tz - gz);
        this.length = (float)(ve.func_72433_c() * 5.0);
        final int steps = (int)this.length;
        this.points.add(vs);
        for (int a = 1; a < steps - 1; ++a) {
            final float dist = a * (this.length / steps);
            final float ss = 1.0f - a / (steps * 1.25f);
            final double dx = (tx - gx) / steps * a + MathHelper.func_76126_a(dist / 10.0f) * grapple.ampl * ss;
            final double dy = (ty - gy + thrower.field_70131_O / 2.0f) / steps * a + MathHelper.func_76126_a(dist / 8.0f) * grapple.ampl * ss;
            final double dz = (tz - gz) / steps * a + MathHelper.func_76126_a(dist / 6.0f) * grapple.ampl * ss;
            final Vec3d vp = new Vec3d(dx, dy, dz);
            this.points.add(vp);
        }
        this.points.add(ve);
        this.points.add(ve);
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
