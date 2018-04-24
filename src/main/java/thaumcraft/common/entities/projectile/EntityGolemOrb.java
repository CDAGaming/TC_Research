package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.math.*;

public class EntityGolemOrb extends EntityThrowable implements IEntityAdditionalSpawnData
{
    int targetID;
    EntityLivingBase target;
    public boolean red;
    
    public EntityGolemOrb(final World par1World) {
        super(par1World);
        this.targetID = 0;
        this.red = false;
    }
    
    public EntityGolemOrb(final World par1World, final EntityLivingBase par2EntityLiving, final EntityLivingBase t, final boolean r) {
        super(par1World, par2EntityLiving);
        this.targetID = 0;
        this.red = false;
        this.target = t;
        this.red = r;
    }
    
    protected float func_70185_h() {
        return 0.0f;
    }
    
    public void writeSpawnData(final ByteBuf data) {
        int id = -1;
        if (this.target != null) {
            id = this.target.func_145782_y();
        }
        data.writeInt(id);
        data.writeBoolean(this.red);
    }
    
    public void readSpawnData(final ByteBuf data) {
        final int id = data.readInt();
        try {
            if (id >= 0) {
                this.target = (EntityLivingBase)this.field_70170_p.func_73045_a(id);
            }
        }
        catch (Exception ex) {}
        this.red = data.readBoolean();
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        if (!this.field_70170_p.field_72995_K && this.func_85052_h() != null && mop.field_72313_a == RayTraceResult.Type.ENTITY) {
            mop.field_72308_g.func_70097_a(DamageSource.func_76354_b((Entity)this, (Entity)this.func_85052_h()), (float)this.func_85052_h().func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * (this.red ? 1.0f : 0.6f));
        }
        this.func_184185_a(SoundsTC.shock, 1.0f, 1.0f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f);
        if (this.field_70170_p.field_72995_K) {
            FXDispatcher.INSTANCE.burst(this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0f);
        }
        this.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70173_aa > (this.red ? 240 : 160)) {
            this.func_70106_y();
        }
        if (this.target != null) {
            final double d = this.func_70068_e((Entity)this.target);
            double dx = this.target.field_70165_t - this.field_70165_t;
            double dy = this.target.func_174813_aQ().field_72338_b + this.target.field_70131_O * 0.6 - this.field_70163_u;
            double dz = this.target.field_70161_v - this.field_70161_v;
            final double d2 = 0.2;
            dx /= d;
            dy /= d;
            dz /= d;
            this.field_70159_w += dx * d2;
            this.field_70181_x += dy * d2;
            this.field_70179_y += dz * d2;
            this.field_70159_w = MathHelper.func_76131_a((float)this.field_70159_w, -0.25f, 0.25f);
            this.field_70181_x = MathHelper.func_76131_a((float)this.field_70181_x, -0.25f, 0.25f);
            this.field_70179_y = MathHelper.func_76131_a((float)this.field_70179_y, -0.25f, 0.25f);
        }
    }
    
    public boolean func_70097_a(final DamageSource source, final float damage) {
        if (this.func_180431_b(source)) {
            return false;
        }
        if (source.func_76346_g() != null) {
            final Vec3d vec3 = source.func_76346_g().func_70040_Z();
            if (vec3 != null) {
                this.field_70159_w = vec3.field_72450_a;
                this.field_70181_x = vec3.field_72448_b;
                this.field_70179_y = vec3.field_72449_c;
                this.field_70159_w *= 0.9;
                this.field_70181_x *= 0.9;
                this.field_70179_y *= 0.9;
                this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f);
            }
            return true;
        }
        return false;
    }
}
