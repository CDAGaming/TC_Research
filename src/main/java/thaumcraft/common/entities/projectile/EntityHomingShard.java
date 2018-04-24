package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import thaumcraft.client.lib.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntityHomingShard extends EntityThrowable implements IEntityAdditionalSpawnData
{
    Class tclass;
    boolean persistant;
    int targetID;
    EntityLivingBase target;
    private static final DataParameter<Byte> STRENGTH;
    public ArrayList<UtilsFX.Vector> vl;
    
    public EntityHomingShard(final World par1World) {
        super(par1World);
        this.tclass = null;
        this.persistant = false;
        this.targetID = 0;
        this.vl = new ArrayList<UtilsFX.Vector>();
    }
    
    public EntityHomingShard(final World par1World, final EntityLivingBase p, final EntityLivingBase t, final int strength, final boolean b) {
        super(par1World, p);
        this.tclass = null;
        this.persistant = false;
        this.targetID = 0;
        this.vl = new ArrayList<UtilsFX.Vector>();
        this.target = t;
        this.tclass = t.getClass();
        this.persistant = b;
        this.setStrength(strength);
        final Vec3d v = p.func_70040_Z();
        this.func_70012_b(p.field_70165_t + v.field_72450_a / 2.0, p.field_70163_u + p.func_70047_e() + v.field_72448_b / 2.0, p.field_70161_v + v.field_72449_c / 2.0, p.field_70177_z, p.field_70125_A);
        final float f = 0.5f;
        final float ry = p.field_70177_z + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 60.0f;
        final float rp = p.field_70125_A + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 60.0f;
        this.field_70159_w = -MathHelper.func_76126_a(ry / 180.0f * 3.1415927f) * MathHelper.func_76134_b(rp / 180.0f * 3.1415927f) * f;
        this.field_70179_y = MathHelper.func_76134_b(ry / 180.0f * 3.1415927f) * MathHelper.func_76134_b(rp / 180.0f * 3.1415927f) * f;
        this.field_70181_x = -MathHelper.func_76126_a(rp / 180.0f * 3.1415927f) * f;
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityHomingShard.STRENGTH, (Object)(byte)0);
    }
    
    public void setStrength(final int str) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityHomingShard.STRENGTH, (Object)(byte)str);
    }
    
    public int getStrength() {
        return (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityHomingShard.STRENGTH);
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
    }
    
    public void readSpawnData(final ByteBuf data) {
        final int id = data.readInt();
        try {
            if (id >= 0) {
                this.target = (EntityLivingBase)this.field_70170_p.func_73045_a(id);
            }
        }
        catch (Exception ex) {}
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        if (!this.field_70170_p.field_72995_K && mop.field_72313_a == RayTraceResult.Type.ENTITY && (this.func_85052_h() == null || (this.func_85052_h() != null && mop.field_72308_g != this.func_85052_h()))) {
            mop.field_72308_g.func_70097_a(DamageSource.func_76354_b((Entity)this, (Entity)this.func_85052_h()), 1.0f + this.getStrength() * 0.5f);
            this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f);
            this.field_70170_p.func_72960_a((Entity)this, (byte)16);
            this.func_70106_y();
        }
        if (mop.field_72313_a == RayTraceResult.Type.BLOCK) {
            if (mop.field_178784_b.func_82599_e() != 0) {
                this.field_70179_y *= -0.8;
            }
            if (mop.field_178784_b.func_82601_c() != 0) {
                this.field_70159_w *= -0.8;
            }
            if (mop.field_178784_b.func_96559_d() != 0) {
                this.field_70181_x *= -0.8;
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            FXDispatcher.INSTANCE.burst(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.3f);
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public void func_70071_h_() {
        this.vl.add(0, new UtilsFX.Vector((float)this.field_70142_S, (float)this.field_70137_T, (float)this.field_70136_U));
        if (this.vl.size() > 6) {
            this.vl.remove(this.vl.size() - 1);
        }
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K) {
            if (this.persistant && (this.target == null || this.target.field_70128_L || this.target.func_70068_e((Entity)this) > 1250.0)) {
                final List<Entity> es = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends Entity>)this.tclass, 16.0);
                for (final Entity e : es) {
                    if (e instanceof EntityLivingBase && !e.field_70128_L && (this.func_85052_h() == null || e.func_145782_y() != this.func_85052_h().func_145782_y())) {
                        this.target = (EntityLivingBase)e;
                        break;
                    }
                }
            }
            if (this.target == null || this.target.field_70128_L) {
                this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                this.func_70106_y();
            }
        }
        if (this.field_70173_aa > 300) {
            this.field_70170_p.func_72960_a((Entity)this, (byte)16);
            this.func_70106_y();
        }
        if (this.field_70173_aa % 20 == 0 && this.target != null && !this.target.field_70128_L) {
            final double d = this.func_70032_d((Entity)this.target);
            double dx = this.target.field_70165_t - this.field_70165_t;
            double dy = this.target.func_174813_aQ().field_72338_b + this.target.field_70131_O * 0.6 - this.field_70163_u;
            double dz = this.target.field_70161_v - this.field_70161_v;
            dx /= d;
            dy /= d;
            dz /= d;
            this.field_70159_w = dx;
            this.field_70181_x = dy;
            this.field_70179_y = dz;
        }
        this.field_70159_w *= 0.85;
        this.field_70181_x *= 0.85;
        this.field_70179_y *= 0.85;
    }
    
    static {
        STRENGTH = EntityDataManager.func_187226_a((Class)EntityHomingShard.class, DataSerializers.field_187191_a);
    }
}
