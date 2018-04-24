package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import thaumcraft.common.items.tools.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;

public class FXGeneric extends Particle
{
    boolean doneFrames;
    boolean flipped;
    double windX;
    double windZ;
    int layer;
    float dr;
    float dg;
    float db;
    boolean loop;
    float rotationSpeed;
    int startParticle;
    int numParticles;
    int particleInc;
    float[] scaleKeys;
    float[] scaleFrames;
    float[] alphaKeys;
    float[] alphaFrames;
    double slowDown;
    float randomX;
    float randomY;
    float randomZ;
    int[] finalFrames;
    boolean angled;
    float angleYaw;
    float anglePitch;
    int gridSize;
    boolean thaumClamp;
    
    public FXGeneric(final World world, final double x, final double y, final double z, final double xx, final double yy, final double zz) {
        super(world, x, y, z, xx, yy, zz);
        this.doneFrames = false;
        this.flipped = false;
        this.layer = 0;
        this.dr = 0.0f;
        this.dg = 0.0f;
        this.db = 0.0f;
        this.loop = false;
        this.rotationSpeed = 0.0f;
        this.startParticle = 0;
        this.numParticles = 1;
        this.particleInc = 1;
        this.scaleKeys = new float[] { 1.0f };
        this.scaleFrames = new float[] { 0.0f };
        this.alphaKeys = new float[] { 1.0f };
        this.alphaFrames = new float[] { 0.0f };
        this.slowDown = 0.9800000190734863;
        this.finalFrames = null;
        this.angled = false;
        this.gridSize = 64;
        this.thaumClamp = false;
        this.func_187115_a(0.1f, 0.1f);
        this.func_187109_b(x, y, z);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.field_187123_c = x;
        this.field_187124_d = y;
        this.field_187125_e = z;
        this.field_70548_b = 0.0f;
        this.field_70549_c = 0.0f;
        this.field_187129_i = xx;
        this.field_187130_j = yy;
        this.field_187131_k = zz;
    }
    
    public FXGeneric(final World world, final double x, final double y, final double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.doneFrames = false;
        this.flipped = false;
        this.layer = 0;
        this.dr = 0.0f;
        this.dg = 0.0f;
        this.db = 0.0f;
        this.loop = false;
        this.rotationSpeed = 0.0f;
        this.startParticle = 0;
        this.numParticles = 1;
        this.particleInc = 1;
        this.scaleKeys = new float[] { 1.0f };
        this.scaleFrames = new float[] { 0.0f };
        this.alphaKeys = new float[] { 1.0f };
        this.alphaFrames = new float[] { 0.0f };
        this.slowDown = 0.9800000190734863;
        this.finalFrames = null;
        this.angled = false;
        this.gridSize = 64;
        this.thaumClamp = false;
        this.func_187115_a(0.1f, 0.1f);
        this.func_187109_b(x, y, z);
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        this.field_187123_c = x;
        this.field_187124_d = y;
        this.field_187125_e = z;
        this.field_70548_b = 0.0f;
        this.field_70549_c = 0.0f;
    }
    
    void calculateFrames() {
        this.doneFrames = true;
        if (this.alphaKeys == null) {
            this.func_82338_g(1.0f);
        }
        this.alphaFrames = new float[this.field_70547_e + 1];
        float inc = (this.alphaKeys.length - 1) / this.field_70547_e;
        float is = 0.0f;
        for (int a = 0; a <= this.field_70547_e; ++a) {
            final int isF = MathHelper.func_76141_d(is);
            float diff = (isF < this.alphaKeys.length - 1) ? (diff = this.alphaKeys[isF + 1] - this.alphaKeys[isF]) : 0.0f;
            final float pa = is - isF;
            this.alphaFrames[a] = this.alphaKeys[isF] + diff * pa;
            is += inc;
        }
        if (this.scaleKeys == null) {
            this.setScale(1.0f);
        }
        this.scaleFrames = new float[this.field_70547_e + 1];
        inc = (this.scaleKeys.length - 1) / this.field_70547_e;
        is = 0.0f;
        for (int a = 0; a <= this.field_70547_e; ++a) {
            final int isF = MathHelper.func_76141_d(is);
            float diff = (isF < this.scaleKeys.length - 1) ? (diff = this.scaleKeys[isF + 1] - this.scaleKeys[isF]) : 0.0f;
            final float pa = is - isF;
            this.scaleFrames[a] = this.scaleKeys[isF] + diff * pa;
            is += inc;
        }
    }
    
    public void func_189213_a() {
        if (!this.doneFrames) {
            this.calculateFrames();
        }
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        if (this.field_70546_d++ >= this.field_70547_e) {
            this.func_187112_i();
        }
        this.field_190015_G = this.field_190014_F;
        this.field_190014_F += 3.1415927f * this.rotationSpeed * 2.0f;
        this.field_187130_j -= 0.04 * this.field_70545_g;
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= this.slowDown;
        this.field_187130_j *= this.slowDown;
        this.field_187131_k *= this.slowDown;
        this.field_187129_i += this.field_187122_b.field_73012_v.nextGaussian() * this.randomX;
        this.field_187130_j += this.field_187122_b.field_73012_v.nextGaussian() * this.randomY;
        this.field_187131_k += this.field_187122_b.field_73012_v.nextGaussian() * this.randomZ;
        this.field_187129_i += this.windX;
        this.field_187131_k += this.windZ;
        if (this.field_187132_l && this.slowDown != 1.0) {
            this.field_187129_i *= 0.699999988079071;
            this.field_187131_k *= 0.699999988079071;
        }
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        if (this.thaumClamp) {
            final boolean canSee = ((Minecraft.func_71410_x().field_71439_g.func_184614_ca() != null && Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() instanceof ItemThaumometer) || (Minecraft.func_71410_x().field_71439_g.func_184592_cb() != null && Minecraft.func_71410_x().field_71439_g.func_184592_cb().func_77973_b() instanceof ItemThaumometer)) && EntityUtils.isVisibleTo(1.15f, (Entity)Minecraft.func_71410_x().field_71439_g, this.field_187126_f, this.field_187127_g, this.field_187128_h, 24.0f);
            if (!canSee) {
                return;
            }
        }
        if (this.loop) {
            this.func_70536_a(this.startParticle + this.field_70546_d / this.particleInc % this.numParticles);
        }
        else {
            final float fs = this.field_70546_d / this.field_70547_e;
            this.func_70536_a((int)(this.startParticle + Math.min(this.numParticles * fs, this.numParticles - 1)));
        }
        if (this.finalFrames != null && this.field_70546_d > this.field_70547_e - this.finalFrames.length) {
            final int frame = this.field_70547_e - this.field_70546_d;
            this.func_70536_a(this.finalFrames[frame]);
        }
        this.field_82339_as = this.alphaFrames[Math.min(this.field_70546_d, this.alphaFrames.length - 1)];
        this.field_70544_f = this.scaleFrames[Math.min(this.field_70546_d, this.scaleFrames.length - 1)];
        this.draw(wr, entity, f, f1, f2, f3, f4, f5);
    }
    
    public boolean isFlipped() {
        return this.flipped;
    }
    
    public void setFlipped(final boolean flip) {
        this.flipped = flip;
    }
    
    public void draw(final BufferBuilder wr, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        float tx1 = this.field_94054_b / this.gridSize;
        float tx2 = tx1 + 1.0f / this.gridSize;
        float ty1 = this.field_94055_c / this.gridSize;
        float ty2 = ty1 + 1.0f / this.gridSize;
        final float ts = 0.1f * this.field_70544_f;
        if (this.field_187119_C != null) {
            tx1 = this.field_187119_C.func_94209_e();
            tx2 = this.field_187119_C.func_94212_f();
            ty1 = this.field_187119_C.func_94206_g();
            ty2 = this.field_187119_C.func_94210_h();
        }
        if (this.flipped) {
            final float t = tx1;
            tx1 = tx2;
            tx2 = t;
        }
        final float fs = MathHelper.func_76131_a((this.field_70546_d + partialTicks) / this.field_70547_e, 0.0f, 1.0f);
        final float pr = this.field_70552_h + (this.dr - this.field_70552_h) * fs;
        final float pg = this.field_70553_i + (this.dg - this.field_70553_i) * fs;
        final float pb = this.field_70551_j + (this.db - this.field_70551_j) * fs;
        final int i = this.func_189214_a(partialTicks);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks - FXGeneric.field_70556_an);
        final float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks - FXGeneric.field_70554_ao);
        final float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks - FXGeneric.field_70555_ap);
        if (this.angled) {
            Tessellator.func_178181_a().func_78381_a();
            GL11.glPushMatrix();
            GL11.glTranslated((double)f5, (double)f6, (double)f7);
            GL11.glRotatef(-this.angleYaw + 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.anglePitch + 90.0f, 1.0f, 0.0f, 0.0f);
            if (this.field_190014_F != 0.0f) {
                final float f8 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
                GL11.glRotated(f8 * 57.29577951308232, 0.0, 0.0, 1.0);
            }
            wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
            wr.func_181662_b((double)(-ts), (double)(-ts), 0.0).func_187315_a((double)tx2, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b((double)(-ts), (double)ts, 0.0).func_187315_a((double)tx2, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b((double)ts, (double)ts, 0.0).func_187315_a((double)tx1, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b((double)ts, (double)(-ts), 0.0).func_187315_a((double)tx1, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            Tessellator.func_178181_a().func_78381_a();
            GL11.glPopMatrix();
            wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        }
        else {
            final Vec3d[] avec3d = { new Vec3d((double)(-rotationX * ts - rotationXY * ts), (double)(-rotationZ * ts), (double)(-rotationYZ * ts - rotationXZ * ts)), new Vec3d((double)(-rotationX * ts + rotationXY * ts), (double)(rotationZ * ts), (double)(-rotationYZ * ts + rotationXZ * ts)), new Vec3d((double)(rotationX * ts + rotationXY * ts), (double)(rotationZ * ts), (double)(rotationYZ * ts + rotationXZ * ts)), new Vec3d((double)(rotationX * ts - rotationXY * ts), (double)(-rotationZ * ts), (double)(rotationYZ * ts - rotationXZ * ts)) };
            if (this.field_190014_F != 0.0f) {
                final float f9 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
                final float f10 = MathHelper.func_76134_b(f9 * 0.5f);
                final float f11 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXGeneric.field_190016_K.field_72450_a;
                final float f12 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXGeneric.field_190016_K.field_72448_b;
                final float f13 = MathHelper.func_76126_a(f9 * 0.5f) * (float)FXGeneric.field_190016_K.field_72449_c;
                final Vec3d vec3d = new Vec3d((double)f11, (double)f12, (double)f13);
                for (int l = 0; l < 4; ++l) {
                    avec3d[l] = vec3d.func_186678_a(2.0 * avec3d[l].func_72430_b(vec3d)).func_178787_e(avec3d[l].func_186678_a(f10 * f10 - vec3d.func_72430_b(vec3d))).func_178787_e(vec3d.func_72431_c(avec3d[l]).func_186678_a((double)(2.0f * f10)));
                }
            }
            wr.func_181662_b(f5 + avec3d[0].field_72450_a, f6 + avec3d[0].field_72448_b, f7 + avec3d[0].field_72449_c).func_187315_a((double)tx2, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(f5 + avec3d[1].field_72450_a, f6 + avec3d[1].field_72448_b, f7 + avec3d[1].field_72449_c).func_187315_a((double)tx2, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(f5 + avec3d[2].field_72450_a, f6 + avec3d[2].field_72448_b, f7 + avec3d[2].field_72449_c).func_187315_a((double)tx1, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
            wr.func_181662_b(f5 + avec3d[3].field_72450_a, f6 + avec3d[3].field_72448_b, f7 + avec3d[3].field_72449_c).func_187315_a((double)tx1, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        }
    }
    
    public void setWind(final double d) {
        final int m = this.field_187122_b.func_72853_d();
        final Vec3d vsource = new Vec3d(0.0, 0.0, 0.0);
        Vec3d vtar = new Vec3d(0.1, 0.0, 0.0);
        vtar = Utils.rotateAroundY(vtar, m * (40 + this.field_187122_b.field_73012_v.nextInt(10)) / 180.0f * 3.1415927f);
        final Vec3d vres = vsource.func_72441_c(vtar.field_72450_a, vtar.field_72448_b, vtar.field_72449_c);
        this.windX = vres.field_72450_a * d;
        this.windZ = vres.field_72449_c * d;
    }
    
    public void setLayer(final int layer) {
        this.layer = layer;
    }
    
    public void func_70538_b(final float particleRedIn, final float particleGreenIn, final float particleBlueIn) {
        super.func_70538_b(particleRedIn, particleGreenIn, particleBlueIn);
        this.dr = particleRedIn;
        this.dg = particleGreenIn;
        this.db = particleBlueIn;
    }
    
    public void setRBGColorF(final float particleRedIn, final float particleGreenIn, final float particleBlueIn, final float r2, final float g2, final float b2) {
        super.func_70538_b(particleRedIn, particleGreenIn, particleBlueIn);
        this.dr = r2;
        this.dg = g2;
        this.db = b2;
    }
    
    public int func_70537_b() {
        return this.layer;
    }
    
    public void setLoop(final boolean loop) {
        this.loop = loop;
    }
    
    public void setRotationSpeed(final float rot) {
        this.rotationSpeed = (float)(rot * 0.017453292519943);
    }
    
    public void setRotationSpeed(final float start, final float rot) {
        this.field_190014_F = (float)(start * 3.141592653589793 * 2.0);
        this.rotationSpeed = (float)(rot * 0.017453292519943);
    }
    
    public void func_187114_a(final int max) {
        this.field_70547_e = max;
    }
    
    public void setParticles(final int startParticle, final int numParticles, final int particleInc) {
        this.numParticles = numParticles;
        this.particleInc = particleInc;
        this.func_70536_a(this.startParticle = startParticle);
    }
    
    public void setParticle(final int startParticle) {
        this.numParticles = 1;
        this.particleInc = 1;
        this.func_70536_a(this.startParticle = startParticle);
    }
    
    public void setScale(final float... scale) {
        this.field_70544_f = scale[0];
        this.scaleKeys = scale;
    }
    
    public void setAlphaF(final float... a1) {
        super.func_82338_g(a1[0]);
        this.alphaKeys = a1;
    }
    
    public void func_82338_g(final float a1) {
        super.func_82338_g(a1);
        (this.alphaKeys = new float[1])[0] = a1;
    }
    
    public void setSlowDown(final double slowDown) {
        this.slowDown = slowDown;
    }
    
    public void setRandomMovementScale(final float x, final float y, final float z) {
        this.randomX = x;
        this.randomY = y;
        this.randomZ = z;
    }
    
    public void setFinalFrames(final int... frames) {
        this.finalFrames = frames;
    }
    
    public void setAngles(final float yaw, final float pitch) {
        this.angleYaw = yaw;
        this.anglePitch = pitch;
        this.angled = true;
    }
    
    public void setGravity(final float g) {
        this.field_70545_g = g;
    }
    
    public void func_70536_a(final int p_70536_1_) {
        this.field_94054_b = p_70536_1_ % this.gridSize;
        this.field_94055_c = p_70536_1_ / this.gridSize;
    }
    
    public void setGridSize(final int gridSize) {
        this.gridSize = gridSize;
    }
    
    public void setNoClip(final boolean clip) {
        this.field_190017_n = clip;
    }
    
    public void setThaumClamp(final boolean thaumclamp) {
        this.thaumClamp = thaumclamp;
    }
}
