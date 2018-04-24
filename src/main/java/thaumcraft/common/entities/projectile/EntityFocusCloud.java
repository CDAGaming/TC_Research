package thaumcraft.common.entities.projectile;

import net.minecraftforge.fml.common.registry.*;
import javax.annotation.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import thaumcraft.client.fx.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.events.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.*;

public class EntityFocusCloud extends Entity implements IEntityAdditionalSpawnData
{
    FocusPackage focusPackage;
    private EntityLivingBase owner;
    private UUID ownerUniqueId;
    private int duration;
    private static final DataParameter<Float> RADIUS;
    static HashMap<Long, Long> cooldownMap;
    FocusEffect[] effects;
    
    public EntityFocusCloud(final World par1World) {
        super(par1World);
        this.effects = null;
    }
    
    public EntityFocusCloud(final FocusPackage pack, final Trajectory trajectory, final float rad, final int dur) {
        super(pack.world);
        this.effects = null;
        this.focusPackage = pack;
        this.func_70107_b(trajectory.source.field_72450_a, trajectory.source.field_72448_b, trajectory.source.field_72449_c);
        this.func_70105_a(0.15f, 0.15f);
        this.setOwner(pack.getCaster());
        this.setRadius(rad);
        this.setDuration(dur);
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public void setDuration(final int durationIn) {
        this.duration = durationIn;
    }
    
    public void setOwner(@Nullable final EntityLivingBase ownerIn) {
        this.owner = ownerIn;
        this.ownerUniqueId = ((ownerIn == null) ? null : ownerIn.func_110124_au());
    }
    
    @Nullable
    public EntityLivingBase getOwner() {
        if (this.owner == null && this.ownerUniqueId != null && this.field_70170_p instanceof WorldServer) {
            final Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.ownerUniqueId);
            if (entity instanceof EntityLivingBase) {
                this.owner = (EntityLivingBase)entity;
            }
        }
        return this.owner;
    }
    
    public void func_70088_a() {
        this.func_184212_Q().func_187214_a((DataParameter)EntityFocusCloud.RADIUS, (Object)0.5f);
    }
    
    public void setRadius(final float radiusIn) {
        final double d0 = this.field_70165_t;
        final double d2 = this.field_70163_u;
        final double d3 = this.field_70161_v;
        this.func_70105_a(radiusIn * 2.0f, 0.5f);
        this.func_70107_b(d0, d2, d3);
        if (!this.field_70170_p.field_72995_K) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityFocusCloud.RADIUS, (Object)radiusIn);
        }
    }
    
    public float getRadius() {
        return (float)this.func_184212_Q().func_187225_a((DataParameter)EntityFocusCloud.RADIUS);
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
        nbt.func_74768_a("Age", this.field_70173_aa);
        nbt.func_74768_a("Duration", this.duration);
        nbt.func_74776_a("Radius", this.getRadius());
        if (this.ownerUniqueId != null) {
            nbt.func_186854_a("OwnerUUID", this.ownerUniqueId);
        }
        nbt.func_74782_a("pack", (NBTBase)this.focusPackage.serialize());
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        this.field_70173_aa = nbt.func_74762_e("Age");
        this.duration = nbt.func_74762_e("Duration");
        this.setRadius(nbt.func_74760_g("Radius"));
        this.ownerUniqueId = nbt.func_186857_a("OwnerUUID");
        try {
            (this.focusPackage = new FocusPackage()).deserialize(nbt.func_74775_l("pack"));
        }
        catch (Exception ex) {}
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        final float rad = this.getRadius();
        final int dur = this.getDuration();
        if (!this.field_70170_p.field_72995_K && (this.field_70173_aa > dur * 20 || this.getOwner() == null)) {
            this.func_70106_y();
        }
        if (this.func_70089_S()) {
            if (this.field_70170_p.field_72995_K) {
                if (this.effects == null) {
                    this.effects = this.focusPackage.getFocusEffects();
                }
                if (this.effects != null && this.effects.length > 0) {
                    for (int a = 0; a < rad; ++a) {
                        final FocusEffect eff = this.effects[this.field_70146_Z.nextInt(this.effects.length)];
                        FXDispatcher.INSTANCE.drawFocusCloudParticle(this.field_70165_t + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0 * 0.85, this.field_70163_u + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0 * 0.85, this.field_70161_v + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0 * 0.85, this.field_70170_p.field_73012_v.nextGaussian() * 0.01, this.field_70170_p.field_73012_v.nextGaussian() * 0.01, this.field_70170_p.field_73012_v.nextGaussian() * 0.01, FocusEngine.getElementColor(eff.getKey()));
                        eff.renderParticleFX(this.field_70170_p, this.field_70165_t + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0, this.field_70163_u + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0, this.field_70161_v + this.field_70170_p.field_73012_v.nextGaussian() * rad / 2.0, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582, this.field_70170_p.field_73012_v.nextGaussian() * 0.009999999776482582);
                    }
                }
            }
            else if (this.field_70173_aa % 5 == 0) {
                final long t = System.currentTimeMillis();
                final ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
                final ArrayList<RayTraceResult> targets = new ArrayList<RayTraceResult>();
                final List<Entity> list = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends Entity>)Entity.class, (double)rad);
                for (final Entity e : list) {
                    if (e.field_70128_L) {
                        continue;
                    }
                    if (e instanceof EntityFocusCloud) {
                        final Vec3d v = e.func_174791_d().func_178788_d(this.func_174791_d());
                        e.func_70091_d(MoverType.SELF, v.field_72450_a / 50.0, v.field_72448_b / 50.0, v.field_72449_c / 50.0);
                        ((EntityFocusCloud)e).func_145771_j(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                    }
                    if (!(e instanceof EntityLivingBase)) {
                        continue;
                    }
                    if (EntityFocusCloud.cooldownMap.containsKey(e.func_145782_y()) && EntityFocusCloud.cooldownMap.get(e.func_145782_y()) > t) {
                        continue;
                    }
                    EntityFocusCloud.cooldownMap.put((long)e.func_145782_y(), t + 2000L);
                    final RayTraceResult ray = new RayTraceResult(e);
                    ray.field_72307_f = e.func_174791_d().func_72441_c(0.0, (double)(e.field_70131_O / 2.0f), 0.0);
                    final Trajectory tra = new Trajectory(this.func_174791_d(), this.func_174791_d().func_72444_a(ray.field_72307_f));
                    targets.add(ray);
                    trajectories.add(tra);
                }
                for (int a2 = 0; a2 < rad; ++a2) {
                    Vec3d dV = new Vec3d(this.field_70146_Z.nextGaussian(), this.field_70146_Z.nextGaussian(), this.field_70146_Z.nextGaussian());
                    dV = dV.func_72432_b();
                    RayTraceResult br = this.field_70170_p.func_72933_a(this.func_174791_d(), this.func_174791_d().func_178787_e(dV.func_186678_a((double)rad)));
                    long bl = 0L;
                    if (br != null) {
                        bl = br.func_178782_a().func_177986_g();
                        if (EntityFocusCloud.cooldownMap.containsKey(bl)) {
                            if (EntityFocusCloud.cooldownMap.get(bl) <= t) {
                                EntityFocusCloud.cooldownMap.remove(bl);
                            }
                            else {
                                br = null;
                            }
                        }
                    }
                    if (br != null) {
                        targets.add(br);
                        final Trajectory tra2 = new Trajectory(this.func_174791_d(), dV);
                        trajectories.add(tra2);
                        EntityFocusCloud.cooldownMap.put(bl, t + 2000L);
                    }
                }
                if (!targets.isEmpty()) {
                    ServerEvents.addRunnableServer(this.func_130014_f_(), new Runnable() {
                        @Override
                        public void run() {
                            FocusEngine.runFocusPackage(EntityFocusCloud.this.focusPackage.copy(EntityFocusCloud.this.getOwner()), trajectories.toArray(new Trajectory[0]), targets.toArray(new RayTraceResult[0]));
                        }
                    }, 0);
                }
            }
        }
    }
    
    static {
        RADIUS = EntityDataManager.func_187226_a((Class)EntityAreaEffectCloud.class, DataSerializers.field_187193_c);
        EntityFocusCloud.cooldownMap = new HashMap<Long, Long>();
    }
}
