package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;

public class FXWispEG extends Particle
{
    Entity target;
    double rx;
    double ry;
    double rz;
    public int blendmode;
    
    public FXWispEG(final World world, final double posX, final double posY, final double posZ, final Entity target2) {
        super(world, posX, posY, posZ, 0.0, 0.0, 0.0);
        this.target = null;
        this.rx = 0.0;
        this.ry = 0.0;
        this.rz = 0.0;
        this.blendmode = 1;
        this.target = target2;
        this.field_187129_i = this.field_187136_p.nextGaussian() * 0.03;
        this.field_187130_j = -0.05;
        this.field_187131_k = this.field_187136_p.nextGaussian() * 0.03;
        this.field_70544_f *= 0.4f;
        this.field_70547_e = (int)(40.0 / (Math.random() * 0.3 + 0.7));
        this.func_187115_a(0.01f, 0.01f);
        this.field_187123_c = posX;
        this.field_187124_d = posY;
        this.field_187125_e = posZ;
        this.blendmode = 771;
        this.field_70552_h = this.field_187136_p.nextFloat() * 0.05f;
        this.field_70553_i = this.field_187136_p.nextFloat() * 0.05f;
        this.field_70551_j = this.field_187136_p.nextFloat() * 0.05f;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final Entity e = Minecraft.func_71410_x().func_175606_aa();
        final float agescale = 1.0f - this.field_70546_d / this.field_70547_e;
        final float d6 = 1024.0f;
        final double dist = new Vec3d(e.field_70165_t, e.field_70163_u, e.field_70161_v).func_72436_e(new Vec3d(this.field_187126_f, this.field_187127_g, this.field_187128_h));
        final float base = (float)(1.0 - Math.min(d6, dist) / d6);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f * base);
        final float f6 = 0.5f * this.field_70544_f;
        final float f7 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXWispEG.field_70556_an);
        final float f8 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXWispEG.field_70554_ao);
        final float f9 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXWispEG.field_70555_ap);
        final float var8 = this.field_70546_d % 13 / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = 0.046875f;
        final float var11 = var10 + 0.015625f;
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(f7 - f1 * f6 - f4 * f6), (double)(f8 - f2 * f6), (double)(f9 - f3 * f6 - f5 * f6)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.2f * agescale * base).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(f7 - f1 * f6 + f4 * f6), (double)(f8 + f2 * f6), (double)(f9 - f3 * f6 + f5 * f6)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.2f * agescale * base).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(f7 + f1 * f6 + f4 * f6), (double)(f8 + f2 * f6), (double)(f9 + f3 * f6 + f5 * f6)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.2f * agescale * base).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(f7 + f1 * f6 - f4 * f6), (double)(f8 - f2 * f6), (double)(f9 + f3 * f6 - f5 * f6)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.2f * agescale * base).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_70537_b() {
        return (this.blendmode != 1) ? 1 : 0;
    }
    
    public void func_189213_a() {
        super.func_189213_a();
        if (this.target != null && !this.field_187132_l) {
            this.field_187126_f += this.target.field_70159_w;
            this.field_187128_h += this.target.field_70179_y;
        }
    }
}
