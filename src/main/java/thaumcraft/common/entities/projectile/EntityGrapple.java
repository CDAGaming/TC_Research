package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class EntityGrapple extends EntityThrowable implements IEntityAdditionalSpawnData
{
    private int potency;
    public boolean sticky;
    public EnumHand hand;
    EntityLivingBase cthrower;
    boolean p;
    boolean boost;
    int prevDist;
    int count;
    boolean added;
    public float ampl;
    public static HashMap<Integer, Integer> grapples;
    
    public EntityGrapple(final World par1World) {
        super(par1World);
        this.sticky = false;
        this.hand = EnumHand.MAIN_HAND;
        this.p = false;
        this.prevDist = 0;
        this.count = 0;
        this.added = false;
        this.ampl = 0.0f;
        this.func_70105_a(0.1f, 0.1f);
    }
    
    public boolean func_70112_a(final double distance) {
        return distance < 4096.0;
    }
    
    public void func_70186_c(final double x, final double y, final double z, final float velocity, final float inaccuracy) {
        super.func_70186_c(x, y, z, velocity, 0.0f);
    }
    
    public EntityGrapple(final World par1World, final EntityLivingBase par2EntityLiving, final int potency, final boolean sticky, final EnumHand hand) {
        super(par1World, par2EntityLiving);
        this.sticky = false;
        this.hand = EnumHand.MAIN_HAND;
        this.p = false;
        this.prevDist = 0;
        this.count = 0;
        this.added = false;
        this.ampl = 0.0f;
        this.potency = potency;
        this.sticky = sticky;
        this.func_70105_a(0.1f, 0.1f);
        this.hand = hand;
    }
    
    public EntityGrapple(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.sticky = false;
        this.hand = EnumHand.MAIN_HAND;
        this.p = false;
        this.prevDist = 0;
        this.count = 0;
        this.added = false;
        this.ampl = 0.0f;
        this.func_70105_a(0.1f, 0.1f);
    }
    
    public void writeSpawnData(final ByteBuf data) {
        int id = -1;
        if (this.func_85052_h() != null) {
            id = this.func_85052_h().func_145782_y();
        }
        data.writeInt(id);
        data.writeByte(this.potency);
        data.writeBoolean(this.sticky);
        data.writeBoolean(this.hand == EnumHand.MAIN_HAND);
    }
    
    public void readSpawnData(final ByteBuf data) {
        final int id = data.readInt();
        this.potency = data.readByte();
        this.sticky = data.readBoolean();
        this.hand = (data.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
        try {
            if (id >= 0) {
                this.cthrower = (EntityLivingBase)this.field_70170_p.func_73045_a(id);
            }
        }
        catch (Exception ex) {}
    }
    
    public EntityLivingBase func_85052_h() {
        if (this.cthrower != null) {
            return this.cthrower;
        }
        return super.func_85052_h();
    }
    
    protected float func_70185_h() {
        return this.getPulling() ? 0.0f : 0.03f;
    }
    
    public void func_70088_a() {
        super.func_70088_a();
    }
    
    public void setPulling() {
        this.p = true;
    }
    
    public boolean getPulling() {
        return this.p;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.getPulling() && !this.field_70128_L && (this.field_70173_aa > 10 + this.potency * 5 || this.func_85052_h() == null)) {
            if (this.func_85052_h() != null) {
                EntityGrapple.grapples.remove(this.func_85052_h().func_145782_y());
            }
            this.func_70106_y();
        }
        if (this.func_85052_h() != null) {
            if (!this.field_70170_p.field_72995_K && !this.field_70128_L && !this.added) {
                if (EntityGrapple.grapples.containsKey(this.func_85052_h().func_145782_y())) {
                    final int ii = EntityGrapple.grapples.get(this.func_85052_h().func_145782_y());
                    if (ii != this.func_145782_y()) {
                        final Entity e = this.field_70170_p.func_73045_a(ii);
                        if (e != null) {
                            e.func_70106_y();
                        }
                    }
                }
                EntityGrapple.grapples.put(this.func_85052_h().func_145782_y(), this.func_145782_y());
                this.added = true;
            }
            if (EntityGrapple.grapples.containsKey(this.func_85052_h().func_145782_y()) && EntityGrapple.grapples.get(this.func_85052_h().func_145782_y()) != this.func_145782_y()) {
                this.func_70106_y();
            }
            final double dis = this.func_85052_h().func_70032_d((Entity)this);
            if (this.getPulling() && !this.field_70128_L) {
                if ((!this.sticky && dis < 2.0) || this.func_85052_h().func_70093_af() || (!this.sticky && this.count > 20)) {
                    EntityGrapple.grapples.remove(this.func_85052_h().func_145782_y());
                    this.func_70106_y();
                }
                else {
                    if (!this.field_70170_p.field_72995_K && this.func_85052_h() instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.func_85052_h()).field_71135_a.field_147365_f = 0;
                    }
                    this.func_85052_h().field_70143_R = 0.0f;
                    double mx = this.field_70165_t - this.func_85052_h().field_70165_t;
                    double my = this.field_70163_u - this.func_85052_h().field_70163_u;
                    double mz = this.field_70161_v - this.func_85052_h().field_70161_v;
                    double dd = dis;
                    if (this.sticky && dis < 8.0) {
                        dd = dis * (8.0 - dis);
                    }
                    dd = Math.max(1.0E-9, dd);
                    mx /= dd * 5.0;
                    my /= dd * 5.0;
                    mz /= dd * 5.0;
                    Vec3d v2 = new Vec3d(mx, my, mz);
                    if (v2.func_72433_c() > 0.25) {
                        v2 = v2.func_72432_b();
                        mx = v2.field_72450_a / 4.0;
                        my = v2.field_72448_b / 4.0;
                        mz = v2.field_72449_c / 4.0;
                    }
                    final EntityLivingBase func_85052_h = this.func_85052_h();
                    func_85052_h.field_70159_w += mx;
                    final EntityLivingBase func_85052_h2 = this.func_85052_h();
                    func_85052_h2.field_70181_x += my + 0.033;
                    final EntityLivingBase func_85052_h3 = this.func_85052_h();
                    func_85052_h3.field_70179_y += mz;
                    if (!this.boost) {
                        final EntityLivingBase func_85052_h4 = this.func_85052_h();
                        func_85052_h4.field_70181_x += 0.4000000059604645;
                        this.boost = true;
                    }
                    final int d = (int)(dis / 2.0);
                    if (d == this.prevDist) {
                        ++this.count;
                    }
                    else {
                        this.count = 0;
                    }
                    this.prevDist = d;
                }
            }
            if (this.field_70170_p.field_72995_K) {
                if (!this.getPulling()) {
                    this.ampl += 0.02f;
                }
                else {
                    this.ampl *= 0.66f;
                }
            }
        }
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        this.setPulling();
        this.field_70159_w = 0.0;
        this.field_70181_x = 0.0;
        this.field_70179_y = 0.0;
        this.field_70165_t = mop.field_72307_f.field_72450_a;
        this.field_70163_u = mop.field_72307_f.field_72448_b;
        this.field_70161_v = mop.field_72307_f.field_72449_c;
    }
    
    static {
        EntityGrapple.grapples = new HashMap<Integer, Integer>();
    }
}
