package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import java.awt.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class FXVent2 extends Particle
{
    float grav;
    float psm;
    
    public FXVent2(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final int color) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.grav = 0.0f;
        this.psm = 1.0f;
        this.func_187115_a(0.02f, 0.02f);
        this.field_70544_f = this.field_187136_p.nextFloat() * 0.1f + 0.05f;
        this.field_187129_i = par8;
        this.field_187130_j = par10;
        this.field_187131_k = par12;
        final Color c = new Color(color);
        this.field_70552_h = (float)MathHelper.func_151237_a(c.getRed() / 255.0f + this.field_187136_p.nextGaussian() * 0.05, 0.0, 1.0);
        this.field_70551_j = (float)MathHelper.func_151237_a(c.getBlue() / 255.0f + this.field_187136_p.nextGaussian() * 0.05, 0.0, 1.0);
        this.field_70553_i = (float)MathHelper.func_151237_a(c.getGreen() / 255.0f + this.field_187136_p.nextGaussian() * 0.05, 0.0, 1.0);
        final Entity renderentity = FMLClientHandler.instance().getClient().func_175606_aa();
        int visibleDistance = 50;
        if (!FMLClientHandler.instance().getClient().field_71474_y.field_74347_j) {
            visibleDistance = 25;
        }
        if (renderentity.func_70011_f(this.field_187126_f, this.field_187127_g, this.field_187128_h) > visibleDistance) {
            this.field_70547_e = 0;
        }
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.grav = (float)(this.field_187136_p.nextGaussian() * 0.0075);
    }
    
    public void setScale(final float f) {
        this.field_70544_f *= f;
        this.psm *= f;
    }
    
    public void setHeading(double par1, double par3, double par5, final float par7, final float par8) {
        final float f2 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f2;
        par3 /= f2;
        par5 /= f2;
        par1 += this.field_187136_p.nextGaussian() * (this.field_187136_p.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
        par3 += this.field_187136_p.nextGaussian() * (this.field_187136_p.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
        par5 += this.field_187136_p.nextGaussian() * (this.field_187136_p.nextBoolean() ? -1 : 1) * 0.007499999832361937 * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        this.field_187129_i = par1;
        this.field_187130_j = par3;
        this.field_187131_k = par5;
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        ++this.field_70546_d;
        if (this.field_70544_f >= this.psm) {
            this.func_187112_i();
        }
        this.field_187130_j += this.grav;
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= 0.8500000190734863;
        this.field_187130_j *= 0.8500000190734863;
        this.field_187131_k *= 0.8500000190734863;
        if (this.field_70544_f < this.psm) {
            this.field_70544_f *= 1.2;
        }
        if (this.field_70544_f > this.psm) {
            this.field_70544_f = this.psm;
        }
        if (this.field_187132_l) {
            this.field_187129_i *= 0.699999988079071;
            this.field_187131_k *= 0.699999988079071;
        }
    }
    
    public void setRGB(final float r, final float g, final float b) {
        this.field_70552_h = r;
        this.field_70553_i = g;
        this.field_70551_j = b;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.33f);
        final int part = (int)(1.0f + this.field_70544_f / this.psm * 4.0f);
        final float var8 = part % 16 / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = part / 64 / 64.0f;
        final float var11 = var10 + 0.015625f;
        final float var12 = 0.3f * this.field_70544_f;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXVent2.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXVent2.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXVent2.field_70555_ap);
        final float var16 = 1.0f;
        final int i = this.func_189214_a(f);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final float alpha = this.field_82339_as * ((this.psm - this.field_70544_f) / this.psm);
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, alpha).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, alpha).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, alpha).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, alpha).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_70537_b() {
        return 1;
    }
}
