package thaumcraft.common.entities.monster.mods;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.entity.mob.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.fx.*;
import net.minecraft.entity.ai.attributes.*;

public class ChampionModTainted implements IChampionModifierEffect
{
    public static final IAttribute TAINTED_MOD;
    
    @Override
    public float performEffect(final EntityLivingBase boss, final EntityLivingBase target, final DamageSource source, final float amount) {
        resetAI((EntityCreature)boss);
        return amount;
    }
    
    public static void resetAI(final EntityCreature critter) {
        final IAttributeInstance modai = critter.func_110148_a(ChampionModTainted.TAINTED_MOD);
        if (!(critter instanceof EntityMob) && modai.func_111126_e() == 0.0) {
            try {
                critter.field_70714_bg.field_75782_a.clear();
                critter.field_70715_bh.field_75782_a.clear();
                critter.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)critter));
                critter.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIAttackMelee(critter, 1.2, false));
                critter.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(critter, 1.0));
                critter.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander(critter, 1.0));
                critter.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)critter, (Class)EntityPlayer.class, 8.0f));
                critter.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)critter));
                critter.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveThroughVillage(critter, 1.0, false));
                critter.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget(critter, true, new Class[] { EntityPigZombie.class }));
                critter.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget(critter, (Class)EntityPlayer.class, true));
                modai.func_111124_b(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 0.0, 0));
                modai.func_111121_a(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 1.0, 0));
            }
            catch (Exception ex) {}
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void preRender(final EntityLivingBase boss, final RenderLivingBase renderLivingBase) {
        if (!LayerTainted.taintLayers.contains(boss.func_145782_y())) {
            renderLivingBase.func_177094_a((LayerRenderer)new LayerTainted(boss.func_145782_y(), renderLivingBase, renderLivingBase.func_177087_b()));
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void showFX(final EntityLivingBase boss) {
        final float w = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float d = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70130_N;
        final float h = boss.field_70170_p.field_73012_v.nextFloat() * boss.field_70131_O;
        FXDispatcher.INSTANCE.drawGenericParticles(boss.func_174813_aQ().field_72340_a + w, boss.func_174813_aQ().field_72338_b + h, boss.func_174813_aQ().field_72339_c + d, 0.0, -0.01, 0.0, 0.1f + boss.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.0f, 0.1f + boss.field_70170_p.field_73012_v.nextFloat() * 0.1f, 0.25f, false, 1, 5, 1, 6 + boss.field_70170_p.field_73012_v.nextInt(6), 0, 2.0f + boss.field_70170_p.field_73012_v.nextFloat(), 0.5f, 1);
    }
    
    static {
        TAINTED_MOD = (IAttribute)new RangedAttribute((IAttribute)null, "tc.mobmodtaint", 0.0, 0.0, 1.0).func_111117_a("Tainted modifier");
    }
}
