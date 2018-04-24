package thaumcraft.common.entities.construct.golem.parts;

import thaumcraft.api.golems.parts.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.construct.golem.ai.*;

public class GolemArmDart implements GolemArm.IArmFunction
{
    @Override
    public void onMeleeAttack(final IGolemAPI golem, final Entity target) {
    }
    
    @Override
    public void onRangedAttack(final IGolemAPI golem, final EntityLivingBase target, final float range) {
        final EntityGolemDart entityarrow = new EntityGolemDart(golem.getGolemWorld(), golem.getGolemEntity());
        final float dmg = (float)golem.getGolemEntity().func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() / 3.0f;
        entityarrow.func_70239_b(dmg + range + golem.getGolemWorld().field_73012_v.nextGaussian() * 0.25);
        final double d0 = target.field_70165_t - golem.getGolemEntity().field_70165_t;
        final double d2 = target.func_174813_aQ().field_72338_b + target.func_70047_e() + range * range - entityarrow.field_70163_u;
        final double d3 = target.field_70161_v - golem.getGolemEntity().field_70161_v;
        entityarrow.func_70186_c(d0, d2, d3, 1.6f, 3.0f);
        golem.getGolemWorld().func_72838_d((Entity)entityarrow);
        golem.getGolemEntity().func_184185_a(SoundEvents.field_187737_v, 1.0f, 1.0f / (golem.getGolemWorld().field_73012_v.nextFloat() * 0.4f + 0.8f));
    }
    
    @Override
    public EntityAIAttackRanged getRangedAttackAI(final IRangedAttackMob golem) {
        return new AIArrowAttack(golem, 1.0, 20, 25, 16.0f);
    }
    
    @Override
    public void onUpdateTick(final IGolemAPI golem) {
    }
}
