package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.events.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import java.awt.*;
import thaumcraft.client.fx.*;
import net.minecraft.network.datasync.*;

public class EntityFocusProjectile extends EntityThrowable implements IEntityAdditionalSpawnData
{
    FocusPackage focusPackage;
    private static final DataParameter<Integer> SPECIAL;
    private static final DataParameter<Integer> OWNER;
    boolean noTouchy;
    private Entity target;
    boolean firstParticle;
    public float lastRenderTick;
    FocusEffect[] effects;
    
    public EntityFocusProjectile(final World par1World) {
        super(par1World);
        this.noTouchy = false;
        this.firstParticle = false;
        this.lastRenderTick = 0.0f;
        this.effects = null;
        this.func_70105_a(0.15f, 0.15f);
    }
    
    public EntityFocusProjectile(final FocusPackage pack, final float speed, final Trajectory trajectory, final int special) {
        super(pack.world, pack.getCaster());
        this.noTouchy = false;
        this.firstParticle = false;
        this.lastRenderTick = 0.0f;
        this.effects = null;
        this.focusPackage = pack;
        this.func_70107_b(trajectory.source.field_72450_a + trajectory.direction.field_72450_a * pack.getCaster().field_70130_N * 2.1, trajectory.source.field_72448_b + trajectory.direction.field_72448_b * pack.getCaster().field_70130_N * 2.1, trajectory.source.field_72449_c + trajectory.direction.field_72449_c * pack.getCaster().field_70130_N * 2.1);
        this.func_70186_c(trajectory.direction.field_72450_a, trajectory.direction.field_72448_b, trajectory.direction.field_72449_c, speed, 0.0f);
        this.func_70105_a(0.15f, 0.15f);
        this.setSpecial(special);
        this.field_184539_c = (Entity)pack.getCaster();
        this.setOwner(this.func_85052_h().func_145782_y());
    }
    
    protected float func_70185_h() {
        return (this.getSpecial() > 1) ? 0.005f : 0.01f;
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityFocusProjectile.SPECIAL, (Object)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityFocusProjectile.OWNER, (Object)0);
    }
    
    public void setOwner(final int s) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityFocusProjectile.OWNER, (Object)s);
    }
    
    public int getOwner() {
        return (int)this.func_184212_Q().func_187225_a((DataParameter)EntityFocusProjectile.OWNER);
    }
    
    public EntityLivingBase func_85052_h() {
        if (this.field_70170_p.field_72995_K) {
            final Entity e = this.field_70170_p.func_73045_a(this.getOwner());
            if (e != null && e instanceof EntityLivingBase) {
                return (EntityLivingBase)e;
            }
        }
        return super.func_85052_h();
    }
    
    public void setSpecial(final int s) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityFocusProjectile.SPECIAL, (Object)s);
    }
    
    public int getSpecial() {
        return (int)this.func_184212_Q().func_187225_a((DataParameter)EntityFocusProjectile.SPECIAL);
    }
    
    public void writeSpawnData(final ByteBuf data) {
        Utils.writeNBTTagCompoundToBuffer(data, this.focusPackage.serialize());
    }
    
    public void readSpawnData(final ByteBuf data) {
        try {
            (this.focusPackage = new FocusPackage()).deserialize(Utils.readNBTTagCompoundFromBuffer(data));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74782_a("pack", (NBTBase)this.focusPackage.serialize());
        nbt.func_74768_a("special", this.getSpecial());
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setSpecial(nbt.func_74762_e("special"));
        try {
            (this.focusPackage = new FocusPackage()).deserialize(nbt.func_74775_l("pack"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.func_85052_h() != null) {
            this.setOwner(this.func_85052_h().func_145782_y());
        }
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        if (mop != null) {
            if (this.getSpecial() == 1 && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
                if (mop.field_178784_b.func_82599_e() != 0) {
                    this.field_70179_y *= -1.0;
                }
                if (mop.field_178784_b.func_82601_c() != 0) {
                    this.field_70159_w *= -1.0;
                }
                if (mop.field_178784_b.func_96559_d() != 0) {
                    this.field_70181_x *= -0.9;
                }
                this.field_70159_w *= 0.9;
                this.field_70181_x *= 0.9;
                this.field_70179_y *= 0.9;
                final float var20 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
                this.field_70165_t -= this.field_70159_w / var20 * 0.05000000074505806;
                this.field_70163_u -= this.field_70181_x / var20 * 0.05000000074505806;
                this.field_70161_v -= this.field_70179_y / var20 * 0.05000000074505806;
                return;
            }
            if (mop.field_72308_g != null) {
                mop.field_72307_f = this.func_174791_d();
            }
            final Vec3d pv = new Vec3d(this.field_70169_q, this.field_70167_r, this.field_70166_s);
            final Vec3d vf = new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            ServerEvents.addRunnableServer(this.func_130014_f_(), new Runnable() {
                @Override
                public void run() {
                    FocusEngine.runFocusPackage(EntityFocusProjectile.this.focusPackage, new Trajectory[] { new Trajectory(pv, vf.func_72432_b()) }, new RayTraceResult[] { mop });
                }
            }, 0);
        }
        this.func_70106_y();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70173_aa > 1200 || (!this.field_70170_p.field_72995_K && this.func_85052_h() == null)) {
            this.func_70106_y();
        }
        this.firstParticle = true;
        if (this.target == null && this.field_70173_aa % 5 == 0 && this.getSpecial() > 1) {
            final List<EntityLivingBase> list = EntityUtils.getEntitiesInRangeSorted(this.func_130014_f_(), (Entity)this, (Class<? extends EntityLivingBase>)EntityLivingBase.class, 16.0);
            for (final EntityLivingBase pt : list) {
                if (!pt.field_70128_L && EntityUtils.isVisibleTo(0.75f, (Entity)this, (Entity)pt, 16.0f)) {
                    if (!EntityUtils.canEntityBeSeen((Entity)this, (Entity)pt)) {
                        continue;
                    }
                    final boolean f = EntityUtils.isFriendly((Entity)this.func_85052_h(), (Entity)pt);
                    if (f && this.getSpecial() == 3) {
                        this.target = (Entity)pt;
                        break;
                    }
                    if (!f && this.getSpecial() == 2) {
                        this.target = (Entity)pt;
                        break;
                    }
                    continue;
                }
            }
        }
        if (this.target != null) {
            final double d = this.func_70068_e(this.target);
            final double dx = this.target.field_70165_t - this.field_70165_t;
            final double dy = this.target.func_174813_aQ().field_72338_b + this.target.field_70131_O * 0.6 - this.field_70163_u;
            final double dz = this.target.field_70161_v - this.field_70161_v;
            Vec3d v = new Vec3d(dx, dy, dz);
            v = v.func_72432_b();
            Vec3d mv = new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            final double lv = mv.func_72433_c();
            mv = mv.func_72432_b().func_178787_e(v.func_186678_a(0.25));
            mv = mv.func_72432_b().func_186678_a(lv);
            this.field_70159_w = mv.field_72450_a;
            this.field_70181_x = mv.field_72448_b;
            this.field_70179_y = mv.field_72449_c;
            if (this.field_70173_aa % 5 == 0 && (this.target.field_70128_L || !EntityUtils.isVisibleTo(0.75f, (Entity)this, this.target, 16.0f) || !EntityUtils.canEntityBeSeen((Entity)this, this.target))) {
                this.target = null;
            }
        }
    }
    
    public Vec3d func_70040_Z() {
        return new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72432_b();
    }
    
    public void renderParticle(final float coeff) {
        this.lastRenderTick = coeff;
        if (this.effects == null) {
            this.effects = this.focusPackage.getFocusEffects();
        }
        if (this.effects != null && this.effects.length > 0) {
            final FocusEffect eff = this.effects[this.field_70146_Z.nextInt(this.effects.length)];
            final float scale = 1.0f;
            final Color c1 = new Color(FocusEngine.getElementColor(eff.getKey()));
            FXDispatcher.INSTANCE.drawFireMote((float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * coeff), (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * coeff) + this.field_70131_O / 2.0f, (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * coeff), 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f) * scale, 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f) * scale, 0.0125f * (this.field_70146_Z.nextFloat() - 0.5f) * scale, c1.getRed() / 255.0f, c1.getGreen() / 255.0f, c1.getBlue() / 255.0f, 0.5f, 4.0f * scale);
            if (this.firstParticle) {
                this.firstParticle = false;
                eff.renderParticleFX(this.field_70170_p, this.field_70169_q + (this.field_70165_t - this.field_70169_q) * coeff + this.field_70170_p.field_73012_v.nextGaussian() * 0.10000000149011612, this.field_70167_r + (this.field_70163_u - this.field_70167_r) * coeff + this.field_70131_O / 2.0f + this.field_70170_p.field_73012_v.nextGaussian() * 0.10000000149011612, this.field_70166_s + (this.field_70161_v - this.field_70166_s) * coeff + this.field_70170_p.field_73012_v.nextGaussian() * 0.10000000149011612, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582);
            }
        }
    }
    
    static {
        SPECIAL = EntityDataManager.func_187226_a((Class)EntityFocusProjectile.class, DataSerializers.field_187192_b);
        OWNER = EntityDataManager.func_187226_a((Class)EntityFocusProjectile.class, DataSerializers.field_187192_b);
    }
}
