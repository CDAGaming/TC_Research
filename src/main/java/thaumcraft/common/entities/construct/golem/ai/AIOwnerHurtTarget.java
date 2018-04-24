package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.entity.*;

public class AIOwnerHurtTarget extends EntityAITarget
{
    EntityOwnedConstruct theEntityTameable;
    EntityLivingBase theTarget;
    private int field_142050_e;
    
    public AIOwnerHurtTarget(final EntityOwnedConstruct p_i1668_1_) {
        super((EntityCreature)p_i1668_1_, false);
        this.theEntityTameable = p_i1668_1_;
        this.func_75248_a(1);
    }
    
    public boolean func_75250_a() {
        if (!this.theEntityTameable.isOwned()) {
            return false;
        }
        final EntityLivingBase entitylivingbase = this.theEntityTameable.getOwnerEntity();
        if (entitylivingbase == null) {
            return false;
        }
        this.theTarget = entitylivingbase.func_110144_aD();
        final int i = entitylivingbase.func_142013_aG();
        return i != this.field_142050_e && this.func_75296_a(this.theTarget, false);
    }
    
    public void func_75249_e() {
        this.field_75299_d.func_70624_b(this.theTarget);
        final EntityLivingBase entitylivingbase = this.theEntityTameable.getOwnerEntity();
        if (entitylivingbase != null) {
            this.field_142050_e = entitylivingbase.func_142013_aG();
        }
        super.func_75249_e();
    }
}
