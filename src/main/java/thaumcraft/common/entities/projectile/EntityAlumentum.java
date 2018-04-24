package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class EntityAlumentum extends EntityThrowable
{
    public EntityAlumentum(final World par1World) {
        super(par1World);
    }
    
    public EntityAlumentum(final World par1World, final EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }
    
    public EntityAlumentum(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
    }
    
    public void func_70186_c(final double x, final double y, final double z, final float velocity, final float inaccuracy) {
        super.func_70186_c(x, y, z, 0.75f, inaccuracy);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70170_p.field_72995_K) {
            for (double i = 0.0; i < 3.0; ++i) {
                final double coeff = i / 3.0;
                FXDispatcher.INSTANCE.drawAlumentum((float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * coeff), (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * coeff) + this.field_70131_O / 2.0f, (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * coeff), 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f), 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f), 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f), this.field_70146_Z.nextFloat() * 0.2f, this.field_70146_Z.nextFloat() * 0.1f, this.field_70146_Z.nextFloat() * 0.1f, 0.5f, 4.0f);
                FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t + this.field_70170_p.field_73012_v.nextGaussian() * 0.20000000298023224, this.field_70163_u + this.field_70170_p.field_73012_v.nextGaussian() * 0.20000000298023224, this.field_70161_v + this.field_70170_p.field_73012_v.nextGaussian() * 0.20000000298023224, 0.0, 0.0, 0.0, 1.0f, 1.0f, 1.0f, 0.7f, false, 448, 8, 1, 8, 0, 0.3f, 0.0f, 1);
            }
        }
    }
    
    protected void func_70184_a(final RayTraceResult par1RayTraceResult) {
        if (!this.field_70170_p.field_72995_K) {
            final boolean var2 = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
            this.field_70170_p.func_72876_a((Entity)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.1f, var2);
            this.func_70106_y();
        }
    }
}
