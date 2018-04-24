package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;

public class FXSmokeSpiral extends Particle
{
    private float radius;
    private int start;
    private int miny;
    
    public FXSmokeSpiral(final World world, final double d, final double d1, final double d2, final float radius, final int start, final int miny) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.radius = 1.0f;
        this.start = 0;
        this.miny = 0;
        this.field_70545_g = -0.01f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70544_f *= 1.0f;
        this.field_70547_e = 20 + world.field_73012_v.nextInt(10);
        this.func_187115_a(0.01f, 0.01f);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.radius = radius;
        this.start = start;
        this.miny = miny;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.66f * this.field_82339_as);
        final int particle = (int)(1.0f + this.field_70546_d / this.field_70547_e * 4.0f);
        final float r1 = this.start + 720.0f * ((this.field_70546_d + f) / this.field_70547_e);
        final float r2 = 90.0f - 180.0f * ((this.field_70546_d + f) / this.field_70547_e);
        float mX = -MathHelper.func_76126_a(r1 / 180.0f * 3.1415927f) * MathHelper.func_76134_b(r2 / 180.0f * 3.1415927f);
        float mZ = MathHelper.func_76134_b(r1 / 180.0f * 3.1415927f) * MathHelper.func_76134_b(r2 / 180.0f * 3.1415927f);
        float mY = -MathHelper.func_76126_a(r2 / 180.0f * 3.1415927f);
        mX *= this.radius;
        mY *= this.radius;
        mZ *= this.radius;
        final float var8 = particle % 16 / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = particle / 16 / 64.0f;
        final float var11 = var10 + 0.015625f;
        final float var12 = 0.15f * this.field_70544_f;
        final float var13 = (float)(this.field_187126_f + mX - FXSmokeSpiral.field_70556_an);
        final float var14 = (float)(Math.max(this.field_187127_g + mY, this.miny + 0.1f) - FXSmokeSpiral.field_70554_ao);
        final float var15 = (float)(this.field_187128_h + mZ - FXSmokeSpiral.field_70555_ap);
        final float var16 = 1.0f;
        final int i = this.func_189214_a(f);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.66f * this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.66f * this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.66f * this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, 0.66f * this.field_82339_as).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_70537_b() {
        return 1;
    }
    
    public void func_189213_a() {
        this.func_82338_g((this.field_70547_e - this.field_70546_d) / this.field_70547_e);
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
    }
}
