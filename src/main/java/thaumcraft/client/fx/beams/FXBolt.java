package thaumcraft.client.fx.beams;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.sasmaster.glelwjgl.java.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.vertex.*;

public class FXBolt extends Particle
{
    float field_187134_n;
    ArrayList<Vec3d> points;
    ArrayList<Float> pointsWidth;
    float dr;
    long seed;
    private Entity targetEntity;
    private double tX;
    private double tY;
    private double tZ;
    ResourceLocation beam;
    public float length;
    CoreGLE gle;
    
    public FXBolt(final World par1World, final double x, final double y, final double z, final double tx, final double ty, final double tz, final float red, final float green, final float blue, final float width) {
        super(par1World, x, y, z, 0.0, 0.0, 0.0);
        this.field_187134_n = 0.0f;
        this.points = new ArrayList<Vec3d>();
        this.pointsWidth = new ArrayList<Float>();
        this.dr = 0.0f;
        this.seed = 0L;
        this.targetEntity = null;
        this.tX = 0.0;
        this.tY = 0.0;
        this.tZ = 0.0;
        this.beam = new ResourceLocation("thaumcraft", "textures/misc/essentia.png");
        this.length = 1.0f;
        this.gle = new CoreGLE();
        this.field_70552_h = red;
        this.field_70553_i = green;
        this.field_70551_j = blue;
        this.func_187115_a(0.02f, 0.02f);
        this.field_187129_i = 0.0;
        this.field_187130_j = 0.0;
        this.field_187131_k = 0.0;
        this.tX = tx - x;
        this.tY = ty - y;
        this.tZ = tz - z;
        this.field_187134_n = width;
        this.field_70547_e = 3;
        final Vec3d vs = new Vec3d(0.0, 0.0, 0.0);
        final Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
        this.length = (float)(ve.func_72433_c() * 3.141592653589793);
        final int steps = (int)this.length;
        this.points.add(vs);
        this.pointsWidth.add(width);
        this.dr = (float)(this.field_187136_p.nextInt(50) * 3.141592653589793);
        final float ampl = 0.1f;
        for (int a = 1; a < steps - 1; ++a) {
            final float dist = a * (this.length / steps) + this.dr;
            double dx = this.tX / steps * a + MathHelper.func_76126_a(dist / 4.0f) * ampl;
            double dy = this.tY / steps * a + MathHelper.func_76126_a(dist / 3.0f) * ampl;
            double dz = this.tZ / steps * a + MathHelper.func_76126_a(dist / 2.0f) * ampl;
            dx += (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.1f;
            dy += (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.1f;
            dz += (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * 0.1f;
            final Vec3d vp = new Vec3d(dx, dy, dz);
            this.points.add(vp);
            this.pointsWidth.add(width);
        }
        this.pointsWidth.add(width);
        this.points.add(ve);
        this.seed = this.field_187136_p.nextInt(1000);
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
    }
    
    private void calcSteps(final float f) {
        final Random rr = new Random(this.seed);
        this.points.clear();
        this.pointsWidth.clear();
        final Vec3d vs = new Vec3d(0.0, 0.0, 0.0);
        final Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
        final int steps = (int)this.length;
        this.points.add(vs);
        this.pointsWidth.add(this.field_187134_n);
        final float ampl = (this.field_70546_d + f) / 10.0f;
        for (int a = 1; a < steps - 1; ++a) {
            final float dist = a * (this.length / steps) + this.dr;
            double dx = this.tX / steps * a + MathHelper.func_76126_a(dist / 4.0f) * ampl;
            double dy = this.tY / steps * a + MathHelper.func_76126_a(dist / 3.0f) * ampl;
            double dz = this.tZ / steps * a + MathHelper.func_76126_a(dist / 2.0f) * ampl;
            dx += (rr.nextFloat() - rr.nextFloat()) * 0.1f;
            dy += (rr.nextFloat() - rr.nextFloat()) * 0.1f;
            dz += (rr.nextFloat() - rr.nextFloat()) * 0.1f;
            final Vec3d vp = new Vec3d(dx, dy, dz);
            this.points.add(vp);
            this.pointsWidth.add(((rr.nextInt(4) == 0) ? (1.0f - this.field_70546_d * 0.25f) : 1.0f) * this.field_187134_n);
        }
        this.pointsWidth.add(this.field_187134_n);
        this.points.add(ve);
    }
    
    public void setRGB(final float r, final float g, final float b) {
        this.field_70552_h = r;
        this.field_70553_i = g;
        this.field_70551_j = b;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float cosyaw, final float cospitch, final float sinyaw, final float cossinpitch, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        final double ePX = this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBolt.field_70556_an;
        final double ePY = this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBolt.field_70554_ao;
        final double ePZ = this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBolt.field_70555_ap;
        GL11.glTranslated(ePX, ePY, ePZ);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(2884);
        this.calcSteps(f);
        final float alpha = MathHelper.func_76131_a(1.0f - (this.field_70546_d + f) / this.field_70547_e, 0.1f, 1.0f);
        if (this.points != null && this.points.size() > 2) {
            final double[][] pp = new double[this.points.size()][3];
            final float[][] colours = new float[this.points.size()][4];
            final double[] radii = new double[this.points.size()];
            for (int a = 0; a < this.points.size(); ++a) {
                pp[a][0] = this.points.get(a).field_72450_a;
                pp[a][1] = this.points.get(a).field_72448_b;
                pp[a][2] = this.points.get(a).field_72449_c;
                colours[a][0] = this.field_70552_h;
                colours[a][1] = this.field_70553_i;
                colours[a][2] = this.field_70551_j;
                colours[a][3] = alpha;
                radii[a] = this.pointsWidth.get(a) / 10.0f;
            }
            this.gle.set_POLYCYL_TESS(5);
            this.gle.gleSetJoinStyle(1042);
            this.gle.glePolyCone(pp.length, pp, colours, radii, 1.0f, 0.0f);
            for (int a = 0; a < this.points.size(); ++a) {
                radii[a] /= 3.0;
            }
            this.gle.glePolyCone(pp.length, pp, colours, radii, 1.0f, 0.0f);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2884);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleManager.field_110737_b);
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
    }
}
