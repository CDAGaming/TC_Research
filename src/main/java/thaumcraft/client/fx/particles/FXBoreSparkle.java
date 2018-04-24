package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class FXBoreSparkle extends Particle
{
    private Entity target;
    private double targetX;
    private double targetY;
    private double targetZ;
    public int particle;
    
    public FXBoreSparkle(final World par1World, final double par2, final double par4, final double par6, final double tx, final double ty, final double tz) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.particle = 24;
        final float field_70552_h = 0.6f;
        this.field_70551_j = field_70552_h;
        this.field_70553_i = field_70552_h;
        this.field_70552_h = field_70552_h;
        this.field_70544_f = this.field_187136_p.nextFloat() * 0.5f + 0.5f;
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
        final double dx = tx - this.field_187126_f;
        final double dy = ty - this.field_187127_g;
        final double dz = tz - this.field_187128_h;
        int base = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 10.0f);
        if (base < 1) {
            base = 1;
        }
        this.field_70547_e = base / 2 + this.field_187136_p.nextInt(base);
        final float f3 = 0.01f;
        this.field_187129_i = (float)this.field_187136_p.nextGaussian() * f3;
        this.field_187130_j = (float)this.field_187136_p.nextGaussian() * f3;
        this.field_187131_k = (float)this.field_187136_p.nextGaussian() * f3;
        this.field_70552_h = 0.2f;
        this.field_70553_i = 0.6f + this.field_187136_p.nextFloat() * 0.3f;
        this.field_70551_j = 0.2f;
        this.field_70545_g = 0.2f;
        final Entity renderentity = FMLClientHandler.instance().getClient().func_175606_aa();
        int visibleDistance = 64;
        if (!FMLClientHandler.instance().getClient().field_71474_y.field_74347_j) {
            visibleDistance = 32;
        }
        if (renderentity.func_70011_f(this.field_187126_f, this.field_187127_g, this.field_187128_h) > visibleDistance) {
            this.field_70547_e = 0;
        }
    }
    
    public FXBoreSparkle(final World par1World, final double par2, final double par4, final double par6, final Entity t) {
        this(par1World, par2, par4, par6, t.field_70165_t, t.field_70163_u + t.func_70047_e(), t.field_70161_v);
        this.target = t;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float bob = MathHelper.func_76126_a(this.field_70546_d / 3.0f) * 0.5f + 1.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final int part = this.field_70546_d % 4;
        final float var8 = part / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = 0.0625f;
        final float var11 = var10 + 0.015625f;
        final float var12 = 0.1f * this.field_70544_f * bob;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBoreSparkle.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBoreSparkle.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBoreSparkle.field_70555_ap);
        final float var16 = 1.0f;
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 1.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 1.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 1.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 1.0f).func_187314_a(j, k).func_181675_d();
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.target != null) {
            this.targetX = this.target.field_70165_t;
            this.targetY = this.target.field_70163_u + this.target.func_70047_e();
            this.targetZ = this.target.field_70161_v;
        }
        if (this.field_70546_d++ >= this.field_70547_e || (MathHelper.func_76128_c(this.field_187126_f) == MathHelper.func_76128_c(this.targetX) && MathHelper.func_76128_c(this.field_187127_g) == MathHelper.func_76128_c(this.targetY) && MathHelper.func_76128_c(this.field_187128_h) == MathHelper.func_76128_c(this.targetZ))) {
            this.func_187112_i();
            return;
        }
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= 0.985;
        this.field_187130_j *= 0.95;
        this.field_187131_k *= 0.985;
        double dx = this.targetX - this.field_187126_f;
        double dy = this.targetY - this.field_187127_g;
        double dz = this.targetZ - this.field_187128_h;
        final double d11 = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
        final double clamp = Math.min(0.25, d11 / 15.0);
        if (d11 < 2.0) {
            this.field_70544_f *= 0.9f;
        }
        dx /= d11;
        dy /= d11;
        dz /= d11;
        this.field_187129_i += dx * clamp;
        this.field_187130_j += dy * clamp;
        this.field_187131_k += dz * clamp;
        this.field_187129_i = MathHelper.func_151237_a((double)(float)this.field_187129_i, -clamp, clamp);
        this.field_187130_j = MathHelper.func_151237_a((double)(float)this.field_187130_j, -clamp, clamp);
        this.field_187131_k = MathHelper.func_151237_a((double)(float)this.field_187131_k, -clamp, clamp);
        this.field_187129_i += this.field_187136_p.nextGaussian() * 0.01;
        this.field_187130_j += this.field_187136_p.nextGaussian() * 0.01;
        this.field_187131_k += this.field_187136_p.nextGaussian() * 0.01;
    }
    
    public void setGravity(final float value) {
        this.field_70545_g = value;
    }
}
