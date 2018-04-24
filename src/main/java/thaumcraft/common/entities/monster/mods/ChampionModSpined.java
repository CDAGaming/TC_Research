package thaumcraft.common.entities.monster.mods;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;

public class ChampionModSpined implements IChampionModifierEffect
{
    @Override
    public float performEffect(final EntityLivingBase boss, final EntityLivingBase target, final DamageSource source, final float amount) {
        if (target == null || source.field_76373_n.equalsIgnoreCase("thorns")) {
            return amount;
        }
        target.func_70097_a(DamageSource.func_92087_a((Entity)boss), (float)(1 + boss.field_70170_p.field_73012_v.nextInt(3)));
        target.func_184185_a(SoundEvents.field_187903_gc, 0.5f, 1.0f);
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
        final int p = 704 + boss.field_70170_p.field_73012_v.nextInt(4) * 3;
        FXDispatcher.INSTANCE.drawGenericParticles(boss.func_174813_aQ().field_72340_a + w, boss.func_174813_aQ().field_72338_b + h, boss.func_174813_aQ().field_72339_c + d, 0.0, 0.0, 0.0, 0.5f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.1f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.1f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.7f, false, p, 3, 1, 3, 0, 1.2f + boss.field_70170_p.field_73012_v.nextFloat() * 0.3f, 0.0f, 0);
    }
    
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
    }
}
