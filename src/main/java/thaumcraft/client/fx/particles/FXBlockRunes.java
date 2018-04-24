package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;

public class FXBlockRunes extends Particle
{
    double ofx;
    double ofy;
    float rotation;
    int runeIndex;
    
    public FXBlockRunes(final World world, final double d, final double d1, final double d2, float f1, final float f2, final float f3, final int m) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.ofx = 0.0;
        this.ofy = 0.0;
        this.rotation = 0.0f;
        this.runeIndex = 0;
        if (f1 == 0.0f) {
            f1 = 1.0f;
        }
        this.rotation = this.field_187136_p.nextInt(4) * 90;
        this.field_70552_h = f1;
        this.field_70553_i = f2;
        this.field_70551_j = f3;
        this.field_70545_g = 0.0f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70547_e = 3 * m;
        this.func_187115_a(0.01f, 0.01f);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.runeIndex = (int)(Math.random() * 16.0 + 224.0);
        this.ofx = this.field_187136_p.nextFloat() * 0.2;
        this.ofy = -0.3 + this.field_187136_p.nextFloat() * 0.6;
        this.field_70544_f = (float)(1.0 + this.field_187136_p.nextGaussian() * 0.10000000149011612);
        this.field_82339_as = 0.0f;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.field_82339_as / 2.0f);
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBlockRunes.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBlockRunes.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBlockRunes.field_70555_ap);
        GL11.glTranslated((double)var13, (double)var14, (double)var15);
        GL11.glRotatef(this.rotation, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated(this.ofx, this.ofy, -0.51);
        final float var16 = this.runeIndex % 16 / 64.0f;
        final float var17 = var16 + 0.015625f;
        final float var18 = 0.09375f;
        final float var19 = var18 + 0.015625f;
        final float var20 = 0.3f * this.field_70544_f;
        final float var21 = 1.0f;
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b(-0.5 * var20, 0.5 * var20, 0.0).func_187315_a((double)var17, (double)var19).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var20, 0.5 * var20, 0.0).func_187315_a((double)var17, (double)var18).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var20, -0.5 * var20, 0.0).func_187315_a((double)var16, (double)var18).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(-0.5 * var20, -0.5 * var20, 0.0).func_187315_a((double)var16, (double)var19).func_181666_a(this.field_70552_h * var21, this.field_70553_i * var21, this.field_70551_j * var21, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
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
        this.field_187130_j -= 0.04 * this.field_70545_g;
        this.field_187126_f += this.field_187129_i;
        this.field_187127_g += this.field_187130_j;
        this.field_187128_h += this.field_187131_k;
    }
    
    public void setGravity(final float value) {
        this.field_70545_g = value;
    }
}
