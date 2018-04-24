package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class AIArrowAttack extends EntityAIAttackRanged
{
    private final EntityLiving entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private int rangedAttackTime;
    private double entityMoveSpeed;
    private int field_75318_f;
    private int field_96561_g;
    private int maxRangedAttackTime;
    private float field_96562_i;
    private float maxAttackDistance;
    
    public AIArrowAttack(final IRangedAttackMob attacker, final double movespeed, final int p_i1650_4_, final int maxAttackTime, final float maxAttackDistanceIn) {
        super(attacker, movespeed, p_i1650_4_, maxAttackTime, maxAttackDistanceIn);
        this.rangedAttackTime = -1;
        if (!(attacker instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.rangedAttackEntityHost = attacker;
        this.entityHost = (EntityLiving)attacker;
        this.entityMoveSpeed = movespeed;
        this.field_96561_g = p_i1650_4_;
        this.maxRangedAttackTime = maxAttackTime;
        this.field_96562_i = maxAttackDistanceIn;
        this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
        this.func_75248_a(3);
    }
    
    public boolean func_75250_a() {
        return this.entityHost.func_70638_az() != null;
    }
    
    public boolean func_75253_b() {
        return this.func_75250_a() || !this.entityHost.func_70661_as().func_75500_f();
    }
    
    public void func_75251_c() {
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }
    
    public void func_75246_d() {
        if (this.entityHost.func_70638_az() == null) {
            return;
        }
        final double d0 = this.entityHost.func_70092_e(this.entityHost.func_70638_az().field_70165_t, this.entityHost.func_70638_az().func_174813_aQ().field_72338_b, this.entityHost.func_70638_az().field_70161_v);
        final boolean flag = this.entityHost.func_70635_at().func_75522_a((Entity)this.entityHost.func_70638_az());
        if (flag) {
            ++this.field_75318_f;
        }
        else {
            this.field_75318_f = 0;
        }
        if (d0 <= this.maxAttackDistance && this.field_75318_f >= 20) {
            this.entityHost.func_70661_as().func_75499_g();
        }
        else {
            this.entityHost.func_70661_as().func_75497_a((Entity)this.entityHost.func_70638_az(), this.entityMoveSpeed);
        }
        this.entityHost.func_70671_ap().func_75651_a((Entity)this.entityHost.func_70638_az(), 10.0f, 30.0f);
        final int rangedAttackTime = this.rangedAttackTime - 1;
        this.rangedAttackTime = rangedAttackTime;
        if (rangedAttackTime == 0) {
            if (d0 > this.maxAttackDistance || !flag) {
                return;
            }
            final float f = MathHelper.func_76133_a(d0) / this.field_96562_i;
            final float lvt_5_1_ = MathHelper.func_76131_a(f, 0.1f, 1.0f);
            this.rangedAttackEntityHost.func_82196_d(this.entityHost.func_70638_az(), lvt_5_1_);
            this.rangedAttackTime = MathHelper.func_76141_d(f * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
        else if (this.rangedAttackTime < 0) {
            final float f2 = MathHelper.func_76133_a(d0) / this.field_96562_i;
            this.rangedAttackTime = MathHelper.func_76141_d(f2 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
    }
}
