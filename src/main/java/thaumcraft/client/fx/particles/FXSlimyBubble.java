package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class FXSlimyBubble extends Particle
{
    int particle;
    
    public FXSlimyBubble(final World world, final double d, final double d1, final double d2, final float f) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.particle = 144;
        this.field_70552_h = 1.0f;
        this.field_70553_i = 1.0f;
        this.field_70551_j = 1.0f;
        this.field_70545_g = 0.0f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70544_f = f;
        this.field_70547_e = 15 + world.field_73012_v.nextInt(5);
        this.func_187115_a(0.01f, 0.01f);
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.field_82339_as);
        final float var8 = this.particle % 16 / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = this.particle / 16 / 64.0f;
        final float var11 = var10 + 0.015625f;
        final float var12 = this.field_70544_f;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXSlimyBubble.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXSlimyBubble.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXSlimyBubble.field_70555_ap);
        final int i = this.func_189214_a(f);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_70537_b() {
        return 1;
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
        if (this.field_70546_d - 1 < 6) {
            this.particle = 144 + this.field_70546_d / 2;
            if (this.field_70546_d == 5) {
                this.field_187127_g += 0.1;
            }
        }
        else if (this.field_70546_d < this.field_70547_e - 4) {
            this.field_187130_j += 0.005;
            this.particle = 147 + this.field_70546_d % 4 / 2;
        }
        else {
            this.field_187130_j /= 2.0;
            this.particle = 150 - (this.field_70547_e - this.field_70546_d) / 2;
        }
        this.field_187127_g += this.field_187130_j;
    }
}
