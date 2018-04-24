package thaumcraft.common.entities.monster.mods;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;

public class ChampionModWarded implements IChampionModifierEffect
{
    @Override
    public float performEffect(final EntityLivingBase mob, final EntityLivingBase target, final DamageSource source, final float amount) {
        if (mob.field_70172_ad <= 0 && mob.field_70173_aa % 25 == 0) {
            final int bh = (int)mob.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() / 2;
            if (mob.func_110139_bj() < bh) {
                mob.func_110149_m(mob.func_110139_bj() + 1.0f);
            }
        }
        return amount;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void showFX(final EntityLivingBase boss) {
        if (boss.field_70170_p.field_73012_v.nextBoolean()) {
            return;
        }
        final float w = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float d = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float h = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70131_O;
        FXDispatcher.INSTANCE.drawGenericParticles(boss.func_174813_aQ().field_72340_a + w, boss.func_174813_aQ().field_72338_b + h, boss.func_174813_aQ().field_72339_c + d, 0.0, 0.0, 0.0, 0.5f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.5f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.5f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.6f, true, 69, 4, 1, 4 + boss.field_70170_p.field_73012_v.nextInt(4), 0, 0.8f + boss.field_70170_p.field_73012_v.nextFloat() * 0.3f, 0.0f, 0);
    }
    
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
    }
}
