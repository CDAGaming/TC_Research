package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

@SideOnly(Side.CLIENT)
public class FXBreakingFade extends ParticleBreaking
{
    public FXBreakingFade(final World worldIn, final double p_i1197_2_, final double p_i1197_4_, final double p_i1197_6_, final double p_i1197_8_, final double p_i1197_10_, final double p_i1197_12_, final Item p_i1197_14_, final int p_i1197_15_) {
        super(worldIn, p_i1197_2_, p_i1197_4_, p_i1197_6_, p_i1197_8_, p_i1197_10_, p_i1197_12_, p_i1197_14_, p_i1197_15_);
    }
    
    public FXBreakingFade(final World worldIn, final double p_i1196_2_, final double p_i1196_4_, final double p_i1196_6_, final Item p_i1196_8_, final int p_i1196_9_) {
        super(worldIn, p_i1196_2_, p_i1196_4_, p_i1196_6_, p_i1196_8_, p_i1196_9_);
    }
    
    public FXBreakingFade(final World worldIn, final double p_i1195_2_, final double p_i1195_4_, final double p_i1195_6_, final Item p_i1195_8_) {
        super(worldIn, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_);
    }
    
    public void setParticleMaxAge(final int particleMaxAge) {
        this.field_70547_e = particleMaxAge;
    }
    
    public void setParticleGravity(final float f) {
        this.field_70545_g = f;
    }
    
    public int func_70537_b() {
        return 1;
    }
    
    public void setSpeed(final double x, final double y, final double z) {
        this.field_187129_i = x;
        this.field_187130_j = y;
        this.field_187131_k = z;
    }
    
    public void func_180434_a(final BufferBuilder p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        GlStateManager.func_179132_a(false);
        float f6 = (this.field_94054_b + this.field_70548_b / 4.0f) / 16.0f;
        float f7 = f6 + 0.015609375f;
        float f8 = (this.field_94055_c + this.field_70549_c / 4.0f) / 16.0f;
        float f9 = f8 + 0.015609375f;
        final float f10 = 0.1f * this.field_70544_f;
        final float fade = 1.0f - this.field_70546_d / this.field_70547_e;
        if (this.field_187119_C != null) {
            f6 = this.field_187119_C.func_94214_a((double)(this.field_70548_b / 4.0f * 16.0f));
            f7 = this.field_187119_C.func_94214_a((double)((this.field_70548_b + 1.0f) / 4.0f * 16.0f));
            f8 = this.field_187119_C.func_94207_b((double)(this.field_70549_c / 4.0f * 16.0f));
            f9 = this.field_187119_C.func_94207_b((double)((this.field_70549_c + 1.0f) / 4.0f * 16.0f));
        }
        final int i = this.func_189214_a(p_180434_3_);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * p_180434_3_ - FXBreakingFade.field_70556_an);
        final float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * p_180434_3_ - FXBreakingFade.field_70554_ao);
        final float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * p_180434_3_ - FXBreakingFade.field_70555_ap);
        p_180434_1_.func_181662_b((double)(f11 - p_180434_4_ * f10 - p_180434_7_ * f10), (double)(f12 - p_180434_5_ * f10), (double)(f13 - p_180434_6_ * f10 - p_180434_8_ * f10)).func_187315_a((double)f6, (double)f9).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * fade).func_187314_a(j, k).func_181675_d();
        p_180434_1_.func_181662_b((double)(f11 - p_180434_4_ * f10 + p_180434_7_ * f10), (double)(f12 + p_180434_5_ * f10), (double)(f13 - p_180434_6_ * f10 + p_180434_8_ * f10)).func_187315_a((double)f6, (double)f8).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * fade).func_187314_a(j, k).func_181675_d();
        p_180434_1_.func_181662_b((double)(f11 + p_180434_4_ * f10 + p_180434_7_ * f10), (double)(f12 + p_180434_5_ * f10), (double)(f13 + p_180434_6_ * f10 + p_180434_8_ * f10)).func_187315_a((double)f7, (double)f8).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * fade).func_187314_a(j, k).func_181675_d();
        p_180434_1_.func_181662_b((double)(f11 + p_180434_4_ * f10 - p_180434_7_ * f10), (double)(f12 - p_180434_5_ * f10), (double)(f13 + p_180434_6_ * f10 - p_180434_8_ * f10)).func_187315_a((double)f7, (double)f9).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as * fade).func_187314_a(j, k).func_181675_d();
        GlStateManager.func_179132_a(true);
    }
    
    public void boom() {
        final float f = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float f2 = MathHelper.func_76133_a(this.field_187129_i * this.field_187129_i + this.field_187130_j * this.field_187130_j + this.field_187131_k * this.field_187131_k);
        this.field_187129_i = this.field_187129_i / f2 * f * 0.9640000000596046;
        this.field_187130_j = this.field_187130_j / f2 * f * 0.9640000000596046 + 0.10000000149011612;
        this.field_187131_k = this.field_187131_k / f2 * f * 0.9640000000596046;
    }
}
