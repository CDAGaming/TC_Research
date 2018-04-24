package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public class AINearestValidTarget extends EntityAITarget
{
    protected final Class targetClass;
    private final int targetChance;
    protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    protected Predicate targetEntitySelector;
    protected EntityLivingBase targetEntity;
    private int targetUnseenTicks;
    
    public AINearestValidTarget(final EntityCreature p_i45878_1_, final Class p_i45878_2_, final boolean p_i45878_3_) {
        this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
    }
    
    public AINearestValidTarget(final EntityCreature p_i45879_1_, final Class p_i45879_2_, final boolean p_i45879_3_, final boolean p_i45879_4_) {
        this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, null);
    }
    
    public AINearestValidTarget(final EntityCreature p_i45880_1_, final Class p_i45880_2_, final int p_i45880_3_, final boolean p_i45880_4_, final boolean p_i45880_5_, final Predicate tselector) {
        super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
        this.targetClass = p_i45880_2_;
        this.targetChance = p_i45880_3_;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter((Entity)p_i45880_1_);
        this.func_75248_a(1);
        this.targetEntitySelector = (Predicate)new Predicate() {
            private static final String __OBFID = "CL_00001621";
            
            public boolean applySelection(final EntityLivingBase entity) {
                if (tselector != null && !tselector.apply((Object)entity)) {
                    return false;
                }
                if (entity instanceof EntityPlayer) {
                    double d0 = AINearestValidTarget.access$000(AINearestValidTarget.this);
                    if (entity.func_70093_af()) {
                        d0 *= 0.800000011920929;
                    }
                    if (entity.func_82150_aj()) {
                        float f = ((EntityPlayer)entity).func_82243_bO();
                        if (f < 0.1f) {
                            f = 0.1f;
                        }
                        d0 *= 0.7f * f;
                    }
                    if (entity.func_70032_d((Entity)AINearestValidTarget.this.field_75299_d) > d0) {
                        return false;
                    }
                }
                return AINearestValidTarget.this.func_75296_a(entity, false);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.applySelection((EntityLivingBase)p_apply_1_);
            }
        };
    }
    
    public boolean func_75253_b() {
        final EntityLivingBase entitylivingbase = this.field_75299_d.func_70638_az();
        if (entitylivingbase == null) {
            return false;
        }
        if (!entitylivingbase.func_70089_S()) {
            return false;
        }
        final Team team = this.field_75299_d.func_96124_cp();
        final Team team2 = entitylivingbase.func_96124_cp();
        if (team != null && team2 == team && !((ITargets)this.field_75299_d).getTargetFriendly()) {
            return false;
        }
        if (team != null && team2 != team && ((ITargets)this.field_75299_d).getTargetFriendly()) {
            return false;
        }
        final double d0 = this.func_111175_f();
        if (this.field_75299_d.func_70068_e((Entity)entitylivingbase) > d0 * d0) {
            return false;
        }
        if (this.field_75297_f) {
            if (this.field_75299_d.func_70635_at().func_75522_a((Entity)entitylivingbase)) {
                this.targetUnseenTicks = 0;
            }
            else if (++this.targetUnseenTicks > 60) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean func_75296_a(final EntityLivingBase p_75296_1_, final boolean p_75296_2_) {
        return this.isGoodTarget((EntityLiving)this.field_75299_d, p_75296_1_, p_75296_2_, this.field_75297_f) && this.field_75299_d.func_180485_d(new BlockPos((Entity)p_75296_1_));
    }
    
    private boolean isGoodTarget(final EntityLiving attacker, final EntityLivingBase posTar, final boolean p_179445_2_, final boolean checkSight) {
        if (posTar == null) {
            return false;
        }
        if (posTar == attacker) {
            return false;
        }
        if (!posTar.func_70089_S()) {
            return false;
        }
        if (!attacker.func_70686_a((Class)posTar.getClass())) {
            return false;
        }
        final Team team = attacker.func_96124_cp();
        final Team team2 = posTar.func_96124_cp();
        if (team != null && team2 == team && !((ITargets)attacker).getTargetFriendly()) {
            return false;
        }
        if (team != null && team2 != team && ((ITargets)attacker).getTargetFriendly()) {
            return false;
        }
        if (attacker instanceof IEntityOwnable && StringUtils.isNotEmpty((CharSequence)((IEntityOwnable)attacker).func_184753_b().toString())) {
            if (posTar instanceof IEntityOwnable && ((IEntityOwnable)attacker).func_184753_b().equals(((IEntityOwnable)posTar).func_184753_b()) && !((ITargets)attacker).getTargetFriendly()) {
                return false;
            }
            if (!(posTar instanceof IEntityOwnable) && !(posTar instanceof EntityPlayer) && ((ITargets)attacker).getTargetFriendly()) {
                return false;
            }
            if (posTar instanceof IEntityOwnable && !((IEntityOwnable)attacker).func_184753_b().equals(((IEntityOwnable)posTar).func_184753_b()) && ((ITargets)attacker).getTargetFriendly()) {
                return false;
            }
            if (posTar == ((IEntityOwnable)attacker).func_70902_q() && !((ITargets)attacker).getTargetFriendly()) {
                return false;
            }
        }
        else if (posTar instanceof EntityPlayer && !p_179445_2_ && ((EntityPlayer)posTar).field_71075_bZ.field_75102_a && !((ITargets)attacker).getTargetFriendly()) {
            return false;
        }
        return !checkSight || attacker.func_70635_at().func_75522_a((Entity)posTar);
    }
    
    public boolean func_75250_a() {
        if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0) {
            return false;
        }
        final double d0 = this.func_111175_f();
        final List list = this.field_75299_d.field_70170_p.func_175647_a(this.targetClass, this.field_75299_d.func_174813_aQ().func_72314_b(d0, 4.0, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.field_180132_d));
        Collections.sort((List<Object>)list, (Comparator<? super Object>)this.theNearestAttackableTargetSorter);
        if (list.isEmpty()) {
            return false;
        }
        this.targetEntity = list.get(0);
        return true;
    }
    
    public void func_75249_e() {
        this.field_75299_d.func_70624_b(this.targetEntity);
        this.targetUnseenTicks = 0;
        super.func_75249_e();
    }
    
    static /* synthetic */ double access$000(final AINearestValidTarget x0) {
        return x0.func_111175_f();
    }
    
    public class Sorter implements Comparator
    {
        private final Entity theEntity;
        private static final String __OBFID = "CL_00001622";
        
        public Sorter(final Entity p_i1662_1_) {
            this.theEntity = p_i1662_1_;
        }
        
        public int compare(final Entity p_compare_1_, final Entity p_compare_2_) {
            final double d0 = this.theEntity.func_70068_e(p_compare_1_);
            final double d2 = this.theEntity.func_70068_e(p_compare_2_);
            return (d0 < d2) ? -1 : ((d0 > d2) ? 1 : 0);
        }
        
        @Override
        public int compare(final Object p_compare_1_, final Object p_compare_2_) {
            return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
