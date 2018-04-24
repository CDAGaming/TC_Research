package thaumcraft.client.fx.other;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.particle.*;

public class FXBlockWard extends Particle
{
    ResourceLocation[] tex1;
    EnumFacing side;
    int rotation;
    float sx;
    float sy;
    float sz;
    
    public FXBlockWard(final World world, final double d, final double d1, final double d2, final EnumFacing side, final float f, final float f1, final float f2) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.tex1 = new ResourceLocation[15];
        this.rotation = 0;
        this.sx = 0.0f;
        this.sy = 0.0f;
        this.sz = 0.0f;
        this.side = side;
        this.field_70545_g = 0.0f;
        final double field_187129_i = 0.0;
        this.field_187131_k = field_187129_i;
        this.field_187130_j = field_187129_i;
        this.field_187129_i = field_187129_i;
        this.field_70547_e = 12 + this.field_187136_p.nextInt(5);
        this.func_187115_a(0.01f, 0.01f);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.field_70544_f = (float)(1.4 + this.field_187136_p.nextGaussian() * 0.30000001192092896);
        this.rotation = this.field_187136_p.nextInt(360);
        this.sx = MathHelper.func_76131_a(f - 0.6f + this.field_187136_p.nextFloat() * 0.2f, -0.4f, 0.4f);
        this.sy = MathHelper.func_76131_a(f1 - 0.6f + this.field_187136_p.nextFloat() * 0.2f, -0.4f, 0.4f);
        this.sz = MathHelper.func_76131_a(f2 - 0.6f + this.field_187136_p.nextFloat() * 0.2f, -0.4f, 0.4f);
        if (side.func_82601_c() != 0) {
            this.sx = 0.0f;
        }
        if (side.func_96559_d() != 0) {
            this.sy = 0.0f;
        }
        if (side.func_82599_e() != 0) {
            this.sz = 0.0f;
        }
        for (int a = 0; a < 15; ++a) {
            this.tex1[a] = new ResourceLocation("thaumcraft", "textures/models/hemis" + (a + 1) + ".png");
        }
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        final float fade = (this.field_70546_d + f) / this.field_70547_e;
        final int frame = Math.min(15, (int)(15.0f * fade));
        Minecraft.func_71410_x().field_71446_o.func_110577_a(this.tex1[frame - 1]);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.field_82339_as / 2.0f);
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBlockWard.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBlockWard.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBlockWard.field_70555_ap);
        GL11.glTranslated((double)(var13 + this.sx), (double)(var14 + this.sy), (double)(var15 + this.sz));
        GL11.glRotatef(90.0f, (float)this.side.func_96559_d(), (float)(-this.side.func_82601_c()), (float)this.side.func_82599_e());
        GL11.glRotatef((float)this.rotation, 0.0f, 0.0f, 1.0f);
        if (this.side.func_82599_e() > 0) {
            GL11.glTranslated(0.0, 0.0, 0.5049999952316284);
            GL11.glRotatef(180.0f, 0.0f, -1.0f, 0.0f);
        }
        else {
            GL11.glTranslated(0.0, 0.0, -0.5049999952316284);
        }
        final float var16 = this.field_70544_f;
        final float var17 = 1.0f;
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b(-0.5 * var16, 0.5 * var16, 0.0).func_187315_a(0.0, 1.0).func_181666_a(this.field_70552_h * var17, this.field_70553_i * var17, this.field_70551_j * var17, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var16, 0.5 * var16, 0.0).func_187315_a(1.0, 1.0).func_181666_a(this.field_70552_h * var17, this.field_70553_i * var17, this.field_70551_j * var17, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(0.5 * var16, -0.5 * var16, 0.0).func_187315_a(1.0, 0.0).func_181666_a(this.field_70552_h * var17, this.field_70553_i * var17, this.field_70551_j * var17, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b(-0.5 * var16, -0.5 * var16, 0.0).func_187315_a(0.0, 0.0).func_181666_a(this.field_70552_h * var17, this.field_70553_i * var17, this.field_70551_j * var17, this.field_82339_as / 2.0f).func_187314_a(j, k).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleManager.field_110737_b);
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
