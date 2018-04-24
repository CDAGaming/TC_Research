package thaumcraft.common.entities.monster.mods;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;

public class ChampionModPoison implements IChampionModifierEffect
{
    @Override
    public float performEffect(final EntityLivingBase boss, final EntityLivingBase target, final DamageSource source, final float amount) {
        if (boss.field_70170_p.field_73012_v.nextFloat() < 0.4f) {
            target.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 100));
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
        FXDispatcher.INSTANCE.drawGenericParticles(boss.func_174813_aQ().field_72340_a + w, boss.func_174813_aQ().field_72338_b + h, boss.func_174813_aQ().field_72339_c + d, 0.0, 0.02, 0.0, 0.2f, 0.6f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.2f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.7f, false, 579, 4, 1, 8 + boss.field_70170_p.field_73012_v.nextInt(4), 0, 0.5f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.5f, 0);
    }
    
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
        GL11.glColor4f(0.6f, 1.0f, 0.7f, 1.0f);
    }
}
