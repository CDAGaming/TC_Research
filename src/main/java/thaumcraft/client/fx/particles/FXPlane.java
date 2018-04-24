package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;

public class FXPlane extends Particle
{
    float angle;
    float angleYaw;
    float anglePitch;
    
    public FXPlane(final World world, final double d, final double d1, final double d2, final double m, final double m1, final double m2, final int life) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.field_70552_h = 1.0f;
        this.field_70553_i = 1.0f;
        this.field_70551_j = 1.0f;
        this.field_70545_g = 0.0f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70547_e = life;
        this.func_187115_a(0.01f, 0.01f);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.field_70544_f = 1.0f;
        this.field_82339_as = 0.0f;
        final double dx = m - this.field_187126_f;
        final double dy = m1 - this.field_187127_g;
        final double dz = m2 - this.field_187128_h;
        this.field_187129_i = dx / this.field_70547_e;
        this.field_187130_j = dy / this.field_70547_e;
        this.field_187131_k = dz / this.field_70547_e;
        this.field_94054_b = 22;
        this.field_94055_c = 10;
        final double d3 = MathHelper.func_76133_a(dx * dx + dz * dz);
        this.angleYaw = 0.0f;
        this.anglePitch = 0.0f;
        if (d3 >= 1.0E-7) {
            this.angleYaw = (float)(MathHelper.func_181159_b(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
            this.anglePitch = (float)(-(MathHelper.func_181159_b(dy, d3) * 180.0 / 3.141592653589793));
        }
        this.angle = (float)(this.field_187136_p.nextGaussian() * 20.0);
    }
    
    public int func_70537_b() {
        return 0;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.field_82339_as / 2.0f);
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXPlane.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXPlane.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXPlane.field_70555_ap);
        GL11.glTranslated((double)var13, (double)var14, (double)var15);
        GL11.glRotatef(-this.angleYaw + 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.anglePitch + 90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(this.angle, 0.0f, 1.0f, 0.0f);
        this.field_94054_b = 22 + Math.round((this.field_70546_d + f) / this.field_70547_e * 8.0f);
        final float var16 = this.field_94054_b / 32.0f;
        final float var17 = var16 + 0.03125f;
        final float var18 = this.field_94055_c / 32.0f;
        final float var19 = var18 + 0.03125f;
        final float var20 = this.field_70544_f * (0.5f + (this.field_70546_d + f) / this.field_70547_e);
        final float var21 = 1.0f;
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        GL11.glDisable(2884);
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        wr.func_181662_b(-0.5 * var20, 0.5 * var20, 0.0).func_187315_a((double)var17, (double)var19).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var20, 0.5 * var20, 0.0).func_187315_a((double)var17, (double)var18).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var20, -0.5 * var20, 0.0).func_187315_a((double)var16, (double)var18).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(-0.5 * var20, -0.5 * var20, 0.0).func_187315_a((double)var16, (double)var19).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        final float threshold = this.field_70547_e / 5.0f;
        if (this.field_70546_d <= threshold) {
            this.field_82339_as = this.field_70546_d / threshold;
        }
        else {
            this.field_82339_as = (this.field_70547_e - this.field_70546_d) / this.field_70547_e;
        }
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
        this.field_187126_f += this.field_187129_i;
        this.field_187127_g += this.field_187130_j;
        this.field_187128_h += this.field_187131_k;
    }
    
    public void setGravity(final float value) {
        this.field_70545_g = value;
    }
}
