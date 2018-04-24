package thaumcraft.client.fx.beams;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.particle.*;

public class FXArc extends Particle
{
    public int particle;
    ArrayList<Vec3d> points;
    private Entity targetEntity;
    private double tX;
    private double tY;
    private double tZ;
    ResourceLocation beam;
    public int blendmode;
    public float length;
    
    public FXArc(final World par1World, final double x, final double y, final double z, final double tx, final double ty, final double tz, final float red, final float green, final float blue, final double hg) {
        super(par1World, x, y, z, 0.0, 0.0, 0.0);
        this.particle = 16;
        this.points = new ArrayList<Vec3d>();
        this.targetEntity = null;
        this.tX = 0.0;
        this.tY = 0.0;
        this.tZ = 0.0;
        this.beam = new ResourceLocation("thaumcraft", "textures/misc/beamh.png");
        this.blendmode = 1;
        this.length = 1.0f;
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
        this.field_70547_e = 2;
        final double xx = 0.0;
        final double yy = 0.0;
        final double zz = 0.0;
        final double gravity = 0.115;
        final double noise = 0.25;
        final Vec3d vs = new Vec3d(xx, yy, zz);
        final Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
        Vec3d vc = new Vec3d(xx, yy, zz);
        this.length = (float)ve.func_72433_c();
        Vec3d vv = Utils.calculateVelocity(vs, ve, hg, gravity);
        final double l = Utils.distanceSquared3d(new Vec3d(0.0, 0.0, 0.0), vv);
        this.points.add(vs);
        for (int c = 0; Utils.distanceSquared3d(ve, vc) > l && c < 50; ++c) {
            Vec3d vt = vc.func_72441_c(vv.field_72450_a, vv.field_72448_b, vv.field_72449_c);
            vc = new Vec3d(vt.field_72450_a, vt.field_72448_b, vt.field_72449_c);
            vt = vt.func_72441_c((this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * noise, (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * noise, (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * noise);
            this.points.add(vt);
            vv = vv.func_178786_a(0.0, gravity / 1.9, 0.0);
        }
        this.points.add(ve);
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
    }
    
    public void setRGB(final float r, final float g, final float b) {
        this.field_70552_h = r;
        this.field_70553_i = g;
        this.field_70551_j = b;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        final double ePX = this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXArc.field_70556_an;
        final double ePY = this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXArc.field_70554_ao;
        final double ePZ = this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXArc.field_70555_ap;
        GL11.glTranslated(ePX, ePY, ePZ);
        final float size = 0.25f;
        Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(2884);
        final int i = 220;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final float alpha = 1.0f - (this.field_70546_d + f) / this.field_70547_e;
        wr.func_181668_a(5, DefaultVertexFormats.field_181711_k);
        final float f6 = 0.0f;
        final float f7 = 1.0f;
        for (int c = 0; c < this.points.size(); ++c) {
            final Vec3d v = this.points.get(c);
            final float f8 = c / this.length;
            final double dx = v.field_72450_a;
            final double dy = v.field_72448_b;
            final double dz = v.field_72449_c;
            wr.func_181662_b(dx, dy - size, dz).func_187315_a((double)f8, (double)f7).func_187314_a(j, k).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_181675_d();
            wr.func_181662_b(dx, dy + size, dz).func_187315_a((double)f8, (double)f6).func_187314_a(j, k).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_181675_d();
        }
        Tessellator.func_178181_a().func_78381_a();
        wr.func_181668_a(5, DefaultVertexFormats.field_181711_k);
        for (int c = 0; c < this.points.size(); ++c) {
            final Vec3d v = this.points.get(c);
            final float f8 = c / this.length;
            final double dx = v.field_72450_a;
            final double dy = v.field_72448_b;
            final double dz = v.field_72449_c;
            wr.func_181662_b(dx - size, dy, dz - size).func_187315_a((double)f8, (double)f7).func_187314_a(j, k).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_181675_d();
            wr.func_181662_b(dx + size, dy, dz + size).func_187315_a((double)f8, (double)f6).func_187314_a(j, k).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_181675_d();
        }
        Tessellator.func_178181_a().func_78381_a();
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
