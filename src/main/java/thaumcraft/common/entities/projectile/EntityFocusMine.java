package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.events.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntityFocusMine extends EntityThrowable implements IEntityAdditionalSpawnData
{
    FocusPackage focusPackage;
    boolean friendly;
    private static final DataParameter<Boolean> ARMED;
    public int counter;
    FocusEffect[] effects;
    
    public EntityFocusMine(final World par1World) {
        super(par1World);
        this.friendly = false;
        this.counter = 40;
        this.effects = null;
        this.func_70105_a(0.15f, 0.15f);
    }
    
    public EntityFocusMine(final FocusPackage pack, final Trajectory trajectory, final boolean friendly) {
        super(pack.world, pack.getCaster());
        this.friendly = false;
        this.counter = 40;
        this.effects = null;
        this.focusPackage = pack;
        this.friendly = friendly;
        this.func_70107_b(trajectory.source.field_72450_a, trajectory.source.field_72448_b, trajectory.source.field_72449_c);
        this.func_70186_c(trajectory.direction.field_72450_a, trajectory.direction.field_72448_b, trajectory.direction.field_72449_c, 0.0f, 0.0f);
        this.func_70105_a(0.15f, 0.15f);
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityFocusMine.ARMED, (Object)false);
    }
    
    public boolean getIsArmed() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityFocusMine.ARMED);
    }
    
    public void setIsArmed(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityFocusMine.ARMED, (Object)par1);
    }
    
    protected float func_70185_h() {
        return 0.01f;
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
        nbt.func_74757_a("armed", this.getIsArmed());
        nbt.func_74782_a("pack", (NBTBase)this.focusPackage.serialize());
        nbt.func_74757_a("friendly", this.friendly);
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.friendly = nbt.func_74767_n("friendly");
        this.setIsArmed(nbt.func_74767_n("armed"));
        if (this.getIsArmed()) {
            this.counter = 0;
        }
        try {
            (this.focusPackage = new FocusPackage()).deserialize(nbt.func_74775_l("pack"));
        }
        catch (Exception ex) {}
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        if (mop != null && this.func_85052_h() != null) {
            this.setIsArmed(true);
        }
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.func_145771_j(this.field_70165_t, this.field_70163_u, this.field_70161_v)) {
            this.field_70159_w *= 0.25;
            this.field_70181_x *= 0.25;
            this.field_70179_y *= 0.25;
        }
        if (this.field_70173_aa > 1200 || (!this.field_70170_p.field_72995_K && this.func_85052_h() == null)) {
            this.func_70106_y();
        }
        if (this.func_70089_S() && this.getIsArmed()) {
            if (this.counter > 0) {
                --this.counter;
            }
            if (this.counter <= 0 && this.field_70173_aa % 5 == 0) {
                if (this.field_70170_p.field_72995_K) {
                    if (this.effects == null) {
                        this.effects = this.focusPackage.getFocusEffects();
                    }
                    if (this.effects != null && this.effects.length > 0) {
                        final FocusEffect eff = this.effects[this.field_70146_Z.nextInt(this.effects.length)];
                        eff.renderParticleFX(this.field_70170_p, this.field_70165_t + this.field_70170_p.field_73012_v.nextGaussian() * 0.1, this.field_70163_u + this.field_70170_p.field_73012_v.nextGaussian() * 0.1, this.field_70161_v + this.field_70170_p.field_73012_v.nextGaussian() * 0.1, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582);
                    }
                }
                else {
                    final List<EntityLivingBase> list2 = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends EntityLivingBase>)EntityLivingBase.class, 1.0);
                    int d = 0;
                    for (final EntityLivingBase e : list2) {
                        if (e.field_70128_L) {
                            continue;
                        }
                        if (this.friendly) {
                            if (!EntityUtils.isFriendly((Entity)this.focusPackage.getCaster(), (Entity)e)) {
                                continue;
                            }
                        }
                        else if (EntityUtils.isFriendly((Entity)this.focusPackage.getCaster(), (Entity)e)) {
                            continue;
                        }
                        final Vec3d epv = e.func_174791_d().func_72441_c(0.0, (double)(e.field_70131_O / 2.0f), 0.0);
                        ServerEvents.addRunnableServer(this.func_130014_f_(), new Runnable() {
                            @Override
                            public void run() {
                                final RayTraceResult ray = new RayTraceResult((Entity)e);
                                ray.field_72307_f = e.func_174791_d().func_72441_c(0.0, (double)(e.field_70131_O / 2.0f), 0.0);
                                FocusEngine.runFocusPackage(EntityFocusMine.this.focusPackage.copy(EntityFocusMine.this.func_85052_h()), new Trajectory[] { new Trajectory(EntityFocusMine.this.func_174791_d(), epv.func_178788_d(EntityFocusMine.this.func_174791_d()).func_72432_b()) }, new RayTraceResult[] { ray });
                            }
                        }, d++);
                    }
                    if (d > 0) {
                        this.func_70106_y();
                    }
                }
            }
        }
    }
    
    static {
        ARMED = EntityDataManager.func_187226_a((Class)EntityFocusMine.class, DataSerializers.field_187198_h);
    }
}
