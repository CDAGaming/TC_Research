package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class FXFireMote extends Particle
{
    float baseScale;
    float baseAlpha;
    int glowlayer;
    
    public FXFireMote(final World worldIn, final double x, final double y, final double z, final double vx, final double vy, final double vz, final float r, final float g, final float b, final float scale, final int layer) {
        super(worldIn, x, y, z, 0.0, 0.0, 0.0);
        this.baseScale = 0.0f;
        this.baseAlpha = 1.0f;
        this.glowlayer = 0;
        float colorR = r;
        float colorG = g;
        float colorB = b;
        if (colorR > 1.0) {
            colorR /= 255.0f;
        }
        if (colorG > 1.0) {
            colorG /= 255.0f;
        }
        if (colorB > 1.0) {
            colorB /= 255.0f;
        }
        this.glowlayer = layer;
        this.func_70538_b(colorR, colorG, colorB);
        this.field_70547_e = 16;
        this.field_70544_f = scale;
        this.baseScale = scale;
        this.field_187129_i = vx;
        this.field_187130_j = vy;
        this.field_187131_k = vz;
        this.field_190014_F = 6.2831855f;
        this.func_70536_a(7);
    }
    
    public void func_70536_a(final int particleTextureIndex) {
        this.field_94054_b = particleTextureIndex % 64;
        this.field_94055_c = particleTextureIndex / 64;
    }
    
    public void func_180434_a(final BufferBuilder worldRendererIn, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        float f = this.field_94054_b / 64.0f;
        float f2 = f + 0.015625f;
        float f3 = this.field_94055_c / 64.0f;
        float f4 = f3 + 0.015625f;
        final float f5 = 0.1f * this.field_70544_f;
        if (this.field_187119_C != null) {
            f = this.field_187119_C.func_94209_e();
            f2 = this.field_187119_C.func_94212_f();
            f3 = this.field_187119_C.func_94206_g();
            f4 = this.field_187119_C.func_94210_h();
        }
        final float f6 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks - FXFireMote.field_70556_an);
        final float f7 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks - FXFireMote.field_70554_ao);
        final float f8 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks - FXFireMote.field_70555_ap);
        final int i = this.func_189214_a(partialTicks);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final Vec3d[] avec3d = { new Vec3d((double)(-rotationX * f5 - rotationXY * f5), (double)(-rotationZ * f5), (double)(-rotationYZ * f5 - rotationXZ * f5)), new Vec3d((double)(-rotationX * f5 + rotationXY * f5), (double)(rotationZ * f5), (double)(-rotationYZ * f5 + rotationXZ * f5)), new Vec3d((double)(rotationX * f5 + rotationXY * f5), (double)(rotationZ * f5), (double)(rotationYZ * f5 + rotationXZ * f5)), new Vec3d((double)(rotationX * f5 - rotationXY * f5), (double)(-rotationZ * f5), (double)(rotationYZ * f5 - rotationXZ * f5)) };
        if (this.field_190014_F != 0.0f) {
            final float f9 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
            final float f10 = MathHelper.func_76134_b(f9 * 0.5f);
            final float f11 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXFireMote.field_190016_K.field_72450_a;
            final float f12 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXFireMote.field_190016_K.field_72448_b;
            final float f13 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXFireMote.field_190016_K.field_72449_c;
            final Vec3d vec3d = new Vec3d((double)f11, (double)f12, (double)f13);
            for (int l = 0; l < 4; ++l) {
                avec3d[l] = vec3d.func_186678_a(2.0 * avec3d[l].func_72430_b(vec3d)).func_178787_e(avec3d[l].func_186678_a(f10 * f10 - vec3d.func_72430_b(vec3d))).func_178787_e(vec3d.func_72431_c(avec3d[l]).func_186678_a((double)(2.0f * f10)));
            }
        }
        worldRendererIn.func_181662_b(f6 + avec3d[0].field_72450_a, f7 + avec3d[0].field_72448_b, f8 + avec3d[0].field_72449_c).func_187315_a((double)f2, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * this.baseAlpha).func_187314_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b(f6 + avec3d[1].field_72450_a, f7 + avec3d[1].field_72448_b, f8 + avec3d[1].field_72449_c).func_187315_a((double)f2, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * this.baseAlpha).func_187314_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b(f6 + avec3d[2].field_72450_a, f7 + avec3d[2].field_72448_b, f8 + avec3d[2].field_72449_c).func_187315_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * this.baseAlpha).func_187314_a(j, k).func_181675_d();
        worldRendererIn.func_181662_b(f6 + avec3d[3].field_72450_a, f7 + avec3d[3].field_72448_b, f8 + avec3d[3].field_72449_c).func_187315_a((double)f, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * this.baseAlpha).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_189214_a(final float pTicks) {
        return 255;
    }
    
    public int func_70537_b() {
        return this.glowlayer;
    }
    
    public void func_189213_a() {
        super.func_189213_a();
        if (this.field_187122_b.field_73012_v.nextInt(6) == 0) {
            ++this.field_70546_d;
        }
        if (this.field_70546_d >= this.field_70547_e) {
            this.func_187112_i();
        }
        final float lifespan = this.field_70546_d / this.field_70547_e;
        this.field_70544_f = this.baseScale - this.baseScale * lifespan;
        this.baseAlpha = 1.0f - lifespan;
        this.field_190015_G = this.field_190014_F;
        ++this.field_190014_F;
    }
}
