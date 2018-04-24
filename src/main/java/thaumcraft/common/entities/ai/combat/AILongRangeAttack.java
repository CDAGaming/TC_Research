package thaumcraft.common.entities.ai.combat;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;

public class AILongRangeAttack extends EntityAIAttackRanged
{
    private final EntityLiving wielder;
    double minDistance;
    
    public AILongRangeAttack(final IRangedAttackMob par1IRangedAttackMob, final double min, final double p_i1650_2_, final int p_i1650_4_, final int p_i1650_5_, final float p_i1650_6_) {
        super(par1IRangedAttackMob, p_i1650_2_, p_i1650_4_, p_i1650_5_, p_i1650_6_);
        this.minDistance = 0.0;
        this.minDistance = min;
        this.wielder = (EntityLiving)par1IRangedAttackMob;
    }
    
    public boolean func_75250_a() {
        final boolean ex = super.func_75250_a();
        if (ex) {
            final EntityLivingBase var1 = this.wielder.func_70638_az();
            if (var1 == null) {
                return false;
            }
            if (var1.field_70128_L) {
                this.wielder.func_70624_b((EntityLivingBase)null);
                return false;
            }
            final double ra = this.wielder.func_70092_e(var1.field_70165_t, var1.func_174813_aQ().field_72338_b, var1.field_70161_v);
            if (ra < this.minDistance * this.minDistance) {
                return false;
            }
        }
        return ex;
    }
}
