package thaumcraft.client.fx.particles;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class FXSwarmRunes extends Particle
{
    private Entity target;
    private float turnSpeed;
    private float speed;
    int deathtimer;
    float rotationPitch;
    float rotationYaw;
    public int particle;
    
    public FXSwarmRunes(final World par1World, final double x, final double y, final double z, final Entity target, final float r, final float g, final float b) {
        super(par1World, x, y, z, 0.0, 0.0, 0.0);
        this.turnSpeed = 10.0f;
        this.speed = 0.2f;
        this.deathtimer = 0;
        this.particle = 0;
        this.field_70552_h = r;
        this.field_70553_i = g;
        this.field_70551_j = b;
        this.field_70544_f = this.field_187136_p.nextFloat() * 0.5f + 1.0f;
        this.target = target;
        final float f3 = 0.2f;
        this.field_187129_i = (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * f3;
        this.field_187130_j = (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * f3;
        this.field_187131_k = (this.field_187136_p.nextFloat() - this.field_187136_p.nextFloat()) * f3;
        this.field_70545_g = 0.1f;
    }
    
    public FXSwarmRunes(final World par1World, final double x, final double y, final double z, final Entity target, final float r, final float g, final float b, final float sp, final float ts, final float pg) {
        this(par1World, x, y, z, target, r, g, b);
        this.speed = sp;
        this.turnSpeed = ts;
        this.field_70545_g = pg;
        this.particle = this.field_187136_p.nextInt(16);
    }
    
    public void func_180434_a(final BufferBuilder wr, final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float bob = MathHelper.func_76126_a(this.field_70546_d / 3.0f) * 0.25f + 1.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final float var8 = this.particle / 64.0f;
        final float var9 = var8 + 0.015625f;
        final float var10 = 0.09375f;
        final float var11 = var10 + 0.015625f;
        final float var12 = 0.07f * this.field_70544_f * bob;
        final float var13 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * f - FXSwarmRunes.field_70556_an);
        final float var14 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * f - FXSwarmRunes.field_70554_ao);
        final float var15 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * f - FXSwarmRunes.field_70555_ap);
        final float var16 = 1.0f;
        final float trans = (50.0f - this.deathtimer) / 50.0f * 0.66f;
        final int i = 240;
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        wr.func_181662_b((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12)).func_187315_a((double)var9, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, trans).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12)).func_187315_a((double)var9, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, trans).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12)).func_187315_a((double)var8, (double)var10).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, trans).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12)).func_187315_a((double)var8, (double)var11).func_181666_a(this.field_70552_h * var16, this.field_70553_i * var16, this.field_70551_j * var16, trans).func_187314_a(j, k).func_181675_d();
    }
    
    public int func_70537_b() {
        return 0;
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        ++this.field_70546_d;
        if (this.field_70546_d > 200 || this.target == null || this.target.field_70128_L || (this.target instanceof EntityLivingBase && ((EntityLivingBase)this.target).field_70725_aQ > 0)) {
            ++this.deathtimer;
            this.field_187129_i *= 0.9;
            this.field_187131_k *= 0.9;
            this.field_187130_j -= this.field_70545_g / 2.0f;
            if (this.deathtimer > 50) {
                this.func_187112_i();
            }
        }
        else {
            this.field_187130_j += this.field_70545_g;
        }
        this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
        this.field_187129_i *= 0.985;
        this.field_187130_j *= 0.985;
        this.field_187131_k *= 0.985;
        if (this.field_70546_d < 200 && this.target != null && !this.target.field_70128_L && (!(this.target instanceof EntityLivingBase) || ((EntityLivingBase)this.target).field_70725_aQ <= 0)) {
            boolean hurt = false;
            if (this.target instanceof EntityLivingBase) {
                hurt = (((EntityLivingBase)this.target).field_70737_aN > 0);
            }
            final Vec3d v1 = new Vec3d(this.field_187126_f, this.field_187127_g, this.field_187128_h);
            if (v1.func_186679_c(this.target.field_70165_t, this.target.field_70163_u, this.target.field_70161_v) > this.target.field_70130_N * this.target.field_70130_N && !hurt) {
                this.faceEntity(this.target, this.turnSpeed / 2.0f + this.field_187136_p.nextInt((int)(this.turnSpeed / 2.0f)), this.turnSpeed / 2.0f + this.field_187136_p.nextInt((int)(this.turnSpeed / 2.0f)));
            }
            else {
                if (hurt && v1.func_186679_c(this.target.field_70165_t, this.target.field_70163_u, this.target.field_70161_v) < this.target.field_70130_N * this.target.field_70130_N) {
                    this.field_70546_d += 100;
                }
                this.faceEntity(this.target, -(this.turnSpeed / 2.0f + this.field_187136_p.nextInt((int)(this.turnSpeed / 2.0f))), -(this.turnSpeed / 2.0f + this.field_187136_p.nextInt((int)(this.turnSpeed / 2.0f))));
            }
            this.field_187129_i = -MathHelper.func_76126_a(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.rotationPitch / 180.0f * 3.1415927f);
            this.field_187131_k = MathHelper.func_76134_b(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.rotationPitch / 180.0f * 3.1415927f);
            this.field_187130_j = -MathHelper.func_76126_a(this.rotationPitch / 180.0f * 3.1415927f);
            this.setHeading(this.field_187129_i, this.field_187130_j, this.field_187131_k, this.speed, 15.0f);
        }
    }
    
    public void faceEntity(final Entity par1Entity, final float par2, final float par3) {
        final double d0 = par1Entity.field_70165_t - this.field_187126_f;
        final double d2 = par1Entity.field_70161_v - this.field_187128_h;
        final double d3 = (par1Entity.func_174813_aQ().field_72338_b + par1Entity.func_174813_aQ().field_72337_e) / 2.0 - (this.func_187116_l().field_72338_b + this.func_187116_l().field_72337_e) / 2.0;
        final double d4 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
        final float f2 = (float)(Math.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f3 = (float)(-(Math.atan2(d3, d4) * 180.0 / 3.141592653589793));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, par3);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, par2);
    }
    
    private float updateRotation(final float par1, final float par2, final float par3) {
        float f3 = MathHelper.func_76142_g(par2 - par1);
        if (f3 > par3) {
            f3 = par3;
        }
        if (f3 < -par3) {
            f3 = -par3;
        }
        return par1 + f3;
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
}
