package thaumcraft.client.fx.beams;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.particle.*;
import thaumcraft.client.lib.*;
import thaumcraft.client.fx.*;

public class FXBeamBore extends Particle
{
    public int particle;
    private double offset;
    private double tX;
    private double tY;
    private double tZ;
    private double ptX;
    private double ptY;
    private double ptZ;
    private float length;
    private float rotYaw;
    private float rotPitch;
    private float prevYaw;
    private float prevPitch;
    private Entity targetEntity;
    private int type;
    private float endMod;
    private boolean reverse;
    private boolean pulse;
    private int rotationspeed;
    private float prevSize;
    public int impact;
    ResourceLocation beam;
    ResourceLocation beam1;
    ResourceLocation beam2;
    ResourceLocation beam3;
    
    public FXBeamBore(final World par1World, final double px, final double py, final double pz, final double tx, final double ty, final double tz, final float red, final float green, final float blue, final int age) {
        super(par1World, px, py, pz, 0.0, 0.0, 0.0);
        this.particle = 16;
        this.offset = 0.0;
        this.tX = 0.0;
        this.tY = 0.0;
        this.tZ = 0.0;
        this.ptX = 0.0;
        this.ptY = 0.0;
        this.ptZ = 0.0;
        this.length = 0.0f;
        this.rotYaw = 0.0f;
        this.rotPitch = 0.0f;
        this.prevYaw = 0.0f;
        this.prevPitch = 0.0f;
        this.targetEntity = null;
        this.type = 0;
        this.endMod = 1.0f;
        this.reverse = false;
        this.pulse = true;
        this.rotationspeed = 5;
        this.prevSize = 0.0f;
        this.beam = new ResourceLocation("thaumcraft", "textures/misc/beam.png");
        this.beam1 = new ResourceLocation("thaumcraft", "textures/misc/beam1.png");
        this.beam2 = new ResourceLocation("thaumcraft", "textures/misc/beam2.png");
        this.beam3 = new ResourceLocation("thaumcraft", "textures/misc/beam3.png");
        this.field_70552_h = red;
        this.field_70553_i = green;
        this.field_70551_j = blue;
        this.func_187115_a(0.02f, 0.02f);
        this.field_187129_i = 0.0;
        this.field_187130_j = 0.0;
        this.field_187131_k = 0.0;
        this.tX = tx;
        this.tY = ty;
        this.tZ = tz;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        this.field_70547_e = age;
        final Entity renderentity = FMLClientHandler.instance().getClient().func_175606_aa();
        int visibleDistance = 64;
        if (!FMLClientHandler.instance().getClient().field_71474_y.field_74347_j) {
            visibleDistance = 32;
        }
        if (renderentity != null && renderentity.func_70011_f(this.field_187126_f, this.field_187127_g, this.field_187128_h) > visibleDistance) {
            this.field_70547_e = 0;
        }
    }
    
    public void updateBeam(final double sx, final double sy, final double sz, final double x, final double y, final double z) {
        this.field_187126_f = sx;
        this.field_187127_g = sy;
        this.field_187128_h = sz;
        this.tX = x;
        this.tY = y;
        this.tZ = z;
        while (this.field_70547_e - this.field_70546_d < 4) {
            ++this.field_70547_e;
        }
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g + this.offset;
        this.field_187125_e = this.field_187128_h;
        this.ptX = this.tX;
        this.ptY = this.tY;
        this.ptZ = this.tZ;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        final float xd = (float)(this.field_187126_f - this.tX);
        final float yd = (float)(this.field_187127_g - this.tY);
        final float zd = (float)(this.field_187128_h - this.tZ);
        this.length = MathHelper.func_76129_c(xd * xd + yd * yd + zd * zd);
        final double var7 = MathHelper.func_76129_c(xd * xd + zd * zd);
        this.rotYaw = (float)(Math.atan2(xd, zd) * 180.0 / 3.141592653589793);
        this.rotPitch = (float)(Math.atan2(yd, var7) * 180.0 / 3.141592653589793);
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        if (this.impact > 0) {
            --this.impact;
        }
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
    }
    
    public void setRGB(final float r, final float g, final float b) {
        this.field_70552_h = r;
        this.field_70553_i = g;
        this.field_70551_j = b;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    public void setEndMod(final float endMod) {
        this.endMod = endMod;
    }
    
    public void setReverse(final boolean reverse) {
        this.reverse = reverse;
    }
    
    public void setPulse(final boolean pulse) {
        this.pulse = pulse;
    }
    
    public void setRotationspeed(final int rotationspeed) {
        this.rotationspeed = rotationspeed;
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity p_180434_2_, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPushMatrix();
        final float var9 = 1.0f;
        final float slide = Minecraft.func_71410_x().field_71439_g.field_70173_aa;
        final float rot = this.field_187122_b.field_73011_w.getWorldTime() % (360 / this.rotationspeed) * this.rotationspeed + this.rotationspeed * f;
        float size = 1.0f;
        if (this.pulse) {
            size = Math.min(this.field_70546_d / 4.0f, 1.0f);
            size = this.prevSize + (size - this.prevSize) * f;
        }
        float op = 0.4f;
        if (this.pulse && this.field_70547_e - this.field_70546_d <= 4) {
            op = 0.4f - (4 - (this.field_70547_e - this.field_70546_d)) * 0.1f;
        }
        switch (this.type) {
            default: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam);
                break;
            }
            case 1: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam1);
                break;
            }
            case 2: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam2);
                break;
            }
            case 3: {
                Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam3);
                break;
            }
        }
        GL11.glTexParameterf(3553, 10242, 10497.0f);
        GL11.glTexParameterf(3553, 10243, 10497.0f);
        GL11.glDisable(2884);
        float var10 = slide + f;
        if (this.reverse) {
            var10 *= -1.0f;
        }
        final float var11 = -var10 * 0.2f - MathHelper.func_76141_d(-var10 * 0.1f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDepthMask(false);
        final float xx = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBeamBore.field_70556_an);
        final float yy = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBeamBore.field_70554_ao);
        final float zz = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBeamBore.field_70555_ap);
        GL11.glTranslated((double)xx, (double)yy, (double)zz);
        final float ry = this.prevYaw + (this.rotYaw - this.prevYaw) * f;
        final float rp = this.prevPitch + (this.rotPitch - this.prevPitch) * f;
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(180.0f + ry, 0.0f, 0.0f, -1.0f);
        GL11.glRotatef(rp, 1.0f, 0.0f, 0.0f);
        final double var12 = -0.15 * size;
        final double var13 = 0.15 * size;
        final double var44b = -0.15 * size * this.endMod;
        final double var17b = 0.15 * size * this.endMod;
        final int i = 200;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
        for (int t = 0; t < 3; ++t) {
            final double var14 = this.length * size * var9;
            final double var15 = 0.0;
            final double var16 = 1.0;
            final double var17 = -1.0f + var11 + t / 3.0f;
            final double var18 = this.length * size * var9 + var17;
            GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
            wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
            wr.func_181662_b(var44b, var14, 0.0).func_187315_a(var16, var18).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(var12, 0.0, 0.0).func_187315_a(var16, var17).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(var13, 0.0, 0.0).func_187315_a(var15, var17).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(var17b, var14, 0.0).func_187315_a(var15, var18).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
            Tessellator.func_178181_a().func_78381_a();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        if (this.impact > 0) {
            this.renderImpact(Tessellator.func_178181_a(), f, f1, f2, f3, f4, f5);
        }
        this.renderSource(Tessellator.func_178181_a(), f, f1, f2, f3, f4, f5);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleManager.field_110737_b);
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        this.prevSize = size;
    }
    
    public void renderSource(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(UtilsFX.nodeTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.66f);
        final int part = this.field_70546_d % 32;
        float op = 0.8f;
        if (this.pulse && this.field_70547_e - this.field_70546_d <= 4) {
            op = 0.8f - (4 - (this.field_70547_e - this.field_70546_d)) * 0.2f;
        }
        final float var8 = part / 32.0f;
        final float var9 = var8 + 0.03125f;
        final float var10 = 0.09375f;
        final float var11 = var10 + 0.03125f;
        final float var12 = 0.33f;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXBeamBore.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXBeamBore.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXBeamBore.field_70555_ap);
        final int i = 200;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181704_d);
        tessellator.func_178180_c().func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, op).func_187314_a(j, k).func_181675_d();
        tessellator.func_78381_a();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    public void renderImpact(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(ParticleEngine.particleTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.66f);
        final int part = this.field_70546_d % 16;
        final float var8 = part / 16.0f;
        final float var9 = var8 + 0.0624375f;
        final float var10 = 0.3125f;
        final float var11 = var10 + 0.0624375f;
        final float var12 = this.endMod / 2.0f / (6 - this.impact);
        final float var13 = (float)(this.ptX + (this.tX - this.ptX) * f - FXBeamBore.field_70556_an);
        final float var14 = (float)(this.ptY + (this.tY - this.ptY) * f - FXBeamBore.field_70554_ao);
        final float var15 = (float)(this.ptZ + (this.tZ - this.ptZ) * f - FXBeamBore.field_70555_ap);
        final int i = 200;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181704_d);
        tessellator.func_178180_c().func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.66f).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.66f).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.66f).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 0.66f).func_187314_a(j, k).func_181675_d();
        tessellator.func_78381_a();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}
