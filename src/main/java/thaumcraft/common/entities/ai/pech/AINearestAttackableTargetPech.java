package thaumcraft.common.entities.ai.pech;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.*;

public class AINearestAttackableTargetPech extends EntityAINearestAttackableTarget
{
    public AINearestAttackableTargetPech(final EntityCreature p_i45878_1_, final Class p_i45878_2_, final boolean p_i45878_3_) {
        super(p_i45878_1_, p_i45878_2_, p_i45878_3_);
    }
    
    public boolean func_75250_a() {
        return (!(this.field_75299_d instanceof EntityPech) || ((EntityPech)this.field_75299_d).getAnger() != 0) && super.func_75250_a();
    }
}
