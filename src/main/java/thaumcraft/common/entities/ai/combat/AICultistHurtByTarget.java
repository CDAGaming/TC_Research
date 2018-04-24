package thaumcraft.common.entities.ai.combat;

import net.minecraft.entity.ai.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.cult.*;
import java.util.*;

public class AICultistHurtByTarget extends EntityAITarget
{
    boolean entityCallsForHelp;
    private int field_142052_b;
    
    public AICultistHurtByTarget(final EntityCreature p_i1660_1_, final boolean p_i1660_2_) {
        super(p_i1660_1_, false);
        this.entityCallsForHelp = p_i1660_2_;
        this.func_75248_a(1);
    }
    
    public boolean func_75250_a() {
        final int i = this.field_75299_d.func_142015_aE();
        return i != this.field_142052_b && this.func_75296_a(this.field_75299_d.func_70638_az(), false);
    }
    
    public void func_75249_e() {
        this.field_75299_d.func_70624_b(this.field_75299_d.func_70638_az());
        this.field_142052_b = this.field_75299_d.func_142015_aE();
        if (this.entityCallsForHelp) {
            final double d0 = this.func_111175_f() * 2.0;
            final List list = this.field_75299_d.field_70170_p.func_72872_a((Class)EntityCultist.class, new AxisAlignedBB(this.field_75299_d.field_70165_t, this.field_75299_d.field_70163_u, this.field_75299_d.field_70161_v, this.field_75299_d.field_70165_t + 1.0, this.field_75299_d.field_70163_u + 1.0, this.field_75299_d.field_70161_v + 1.0).func_72314_b(d0, 10.0, d0));
            for (final EntityCreature entitycreature : list) {
                if (this.field_75299_d != entitycreature && entitycreature.func_70638_az() == null && !entitycreature.func_184191_r((Entity)this.field_75299_d.func_70638_az())) {
                    if (entitycreature instanceof EntityCultistCleric && ((EntityCultistCleric)entitycreature).getIsRitualist()) {
                        final EntityCultistCleric entityCultistCleric = (EntityCultistCleric)entitycreature;
                        ++entityCultistCleric.rage;
                        this.field_75299_d.field_70170_p.func_72960_a((Entity)entitycreature, (byte)19);
                        if (this.field_75299_d.field_70170_p.field_73012_v.nextInt(3) != 0) {
                            continue;
                        }
                        ((EntityCultistCleric)entitycreature).setIsRitualist(false);
                        entitycreature.func_70624_b(this.field_75299_d.func_70638_az());
                    }
                    else {
                        entitycreature.func_70624_b(this.field_75299_d.func_70638_az());
                    }
                }
            }
        }
        super.func_75249_e();
    }
}
