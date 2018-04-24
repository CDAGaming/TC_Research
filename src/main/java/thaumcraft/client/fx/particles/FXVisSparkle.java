package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;

public class FXVisSparkle extends Particle
{
    private double targetX;
    private double targetY;
    private double targetZ;
    float sizeMod;
    
    public FXVisSparkle(final World par1World, final double par2, final double par4, final double par6, final double tx, final double ty, final double tz) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.sizeMod = 0.0f;
        final float field_70552_h = 0.6f;
        this.field_70551_j = field_70552_h;
        this.field_70553_i = field_70552_h;
        this.field_70552_h = field_70552_h;
        this.field_70544_f = 0.0f;
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
        this.field_70547_e = 1000;
        final float f3 = 0.01f;
        this.field_187129_i = (float)this.field_187136_p.nextGaussian() * f3;
        this.field_187130_j = (float)this.field_187136_p.nextGaussian() * f3;
        this.field_187131_k = (float)this.field_187136_p.nextGaussian() * f3;
        this.sizeMod = 45 + this.field_187136_p.nextInt(15);
        this.field_70552_h = 0.2f;
        this.field_70553_i = 0.6f + this.field_187136_p.nextFloat() * 0.3f;
        this.field_70551_j = 0.2f;
        this.field_70545_g = 0.2f;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float bob = MathHelper.func_76126_a(this.field_70546_d / 3.0f) * 0.3f + 6.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final int part = this.field_70546_d % 16;
        final float var8 = part / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = 0.125f;
        final float var11 = var10 + 0.015625f;
        final float var12 = 0.1f * this.field_70544_f * bob;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXVisSparkle.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXVisSparkle.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXVisSparkle.field_70555_ap);
        final float var16 = 1.0f;
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.5f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.5f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.5f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.5f).func_187314_a(j, k).func_181675_d();
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= 0.985;
        this.field_187130_j *= 0.985;
        this.field_187131_k *= 0.985;
        double dx = this.targetX - this.field_187126_f;
        double dy = this.targetY - this.field_187127_g;
        double dz = this.targetZ - this.field_187128_h;
        final double d13 = 0.10000000149011612;
        final double d14 = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
        if (d14 < 2.0) {
            this.field_70544_f *= 0.95f;
        }
        if (d14 < 0.2) {
            this.field_70547_e = this.field_70546_d;
        }
        if (this.field_70546_d < 10) {
            this.field_70544_f = this.field_70546_d / this.sizeMod;
        }
        dx /= d14;
        dy /= d14;
        dz /= d14;
        this.field_187129_i += dx * d13;
        this.field_187130_j += dy * d13;
        this.field_187131_k += dz * d13;
        this.field_187129_i = MathHelper.func_76131_a((float)this.field_187129_i, -0.1f, 0.1f);
        this.field_187130_j = MathHelper.func_76131_a((float)this.field_187130_j, -0.1f, 0.1f);
        this.field_187131_k = MathHelper.func_76131_a((float)this.field_187131_k, -0.1f, 0.1f);
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
    }
    
    public void setGravity(final float value) {
        this.field_70545_g = value;
    }
}
