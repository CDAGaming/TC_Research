package thaumcraft.common.entities.monster.mods;

import net.minecraft.util.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;

public class ChampionModInfested implements IChampionModifierEffect
{
    @Override
    public float performEffect(final EntityLivingBase boss, final EntityLivingBase target, final DamageSource source, final float amount) {
        if (boss.field_70170_p.field_73012_v.nextFloat() < 0.4f && !boss.field_70170_p.field_72995_K) {
            final EntityTaintCrawler spiderling = new EntityTaintCrawler(boss.field_70170_p);
            spiderling.func_70012_b(boss.field_70165_t, boss.field_70163_u + boss.field_70131_O / 2.0f, boss.field_70161_v, boss.field_70170_p.field_73012_v.nextFloat() * 360.0f, 0.0f);
            boss.field_70170_p.func_72838_d((Entity)spiderling);
            boss.func_184185_a(SoundsTC.gore, 0.5f, 1.0f);
        }
        return amount;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void showFX(final EntityLivingBase boss) {
        if (boss.field_70170_p.field_73012_v.nextBoolean()) {
            FXDispatcher.INSTANCE.slimeJumpFX((Entity)boss, 0);
        }
    }
    
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
    }
}
