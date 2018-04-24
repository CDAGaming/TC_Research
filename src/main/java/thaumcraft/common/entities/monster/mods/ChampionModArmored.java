package thaumcraft.common.entities.monster.mods;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.renderer.entity.*;

public class ChampionModArmored implements IChampionModifierEffect
{
    @Override
    public float performEffect(final EntityLivingBase mob, final EntityLivingBase target, final DamageSource source, float amount) {
        if (!source.func_76363_c()) {
            final float f1 = amount * 19.0f;
            amount = f1 / 25.0f;
        }
        return amount;
    }
    
    @Override
    public void showFX(final EntityLivingBase boss) {
        if (boss.field_70170_p.field_73012_v.nextInt(4) != 0) {
            return;
        }
        final float w = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float d = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float h = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70131_O;
        FXDispatcher.INSTANCE.drawGenericParticles(boss.func_174813_aQ().field_72340_a + w, boss.func_174813_aQ().field_72338_b + h, boss.func_174813_aQ().field_72339_c + d, 0.0, 0.0, 0.0, 0.9f, 0.9f, 0.9f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.7f, false, 448, 9, 1, 5 + boss.field_70170_p.field_73012_v.nextInt(4), 0, 0.6f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.0f, 0);
    }
    
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
    }
}
