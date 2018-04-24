package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import java.util.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class FocusEffectCurse extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSCURSE";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.CURSE";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.DEATH;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("duration") + this.getSettingValue("power") * 3;
    }
    
    @Override
    public float getDamageForDisplay(final float finalPower) {
        return (1.0f + this.getSettingValue("power")) * finalPower;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockBamf(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 6946821, true, true, null), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
        if (target.field_72313_a == RayTraceResult.Type.ENTITY && target.field_72308_g != null) {
            final float damage = this.getDamageForDisplay(finalPower);
            final int duration = 20 * this.getSettingValue("duration");
            int eff = (int)(this.getSettingValue("power") * finalPower / 2.0f);
            if (eff < 0) {
                eff = 0;
            }
            target.field_72308_g.func_70097_a(DamageSource.func_76354_b((Entity)((target.field_72308_g != null) ? target.field_72308_g : this.getPackage().getCaster()), (Entity)this.getPackage().getCaster()), damage);
            if (target.field_72308_g instanceof EntityLivingBase) {
                ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76436_u, duration, Math.round(eff)));
                float c = 0.85f;
                if (this.getPackage().world.field_73012_v.nextFloat() < c) {
                    ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76421_d, duration, Math.round(eff)));
                    c -= 0.15f;
                }
                if (this.getPackage().world.field_73012_v.nextFloat() < c) {
                    ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76437_t, duration, Math.round(eff)));
                    c -= 0.15f;
                }
                if (this.getPackage().world.field_73012_v.nextFloat() < c) {
                    ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76419_f, duration * 2, Math.round(eff)));
                    c -= 0.15f;
                }
                if (this.getPackage().world.field_73012_v.nextFloat() < c) {
                    ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76438_s, duration * 3, Math.round(eff)));
                    c -= 0.15f;
                }
                if (this.getPackage().world.field_73012_v.nextFloat() < c) {
                    ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_189112_A, duration * 3, Math.round(eff)));
                }
            }
        }
        else if (target.field_72313_a == RayTraceResult.Type.BLOCK) {
            final float f = (float)Math.min(8.0, 1.5 * this.getSettingValue("power") * finalPower);
            for (final BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.func_177975_b(target.func_178782_a().func_177963_a((double)(-f), (double)(-f), (double)(-f)), target.func_178782_a().func_177963_a((double)f, (double)f, (double)f))) {
                if (blockpos$mutableblockpos1.func_177957_d(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c) <= f * f && this.getPackage().world.func_175623_d(blockpos$mutableblockpos1.func_177984_a()) && this.getPackage().world.func_175665_u((BlockPos)blockpos$mutableblockpos1)) {
                    this.getPackage().world.func_175656_a(blockpos$mutableblockpos1.func_177984_a(), BlocksTC.effectSap.func_176223_P());
                }
            }
        }
        return false;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[] { new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5)), new NodeSetting("duration", "focus.common.duration", new NodeSetting.NodeSettingIntRange(1, 10)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(8);
        fb.func_70538_b(0.41f + world.field_73012_v.nextFloat() * 0.2f, 0.0f, 0.019f + world.field_73012_v.nextFloat() * 0.2f);
        fb.setAlphaF(0.0f, world.field_73012_v.nextFloat(), world.field_73012_v.nextFloat(), world.field_73012_v.nextFloat(), 0.0f);
        fb.setGridSize(16);
        fb.setParticles(72 + world.field_73012_v.nextInt(4), 1, 1);
        fb.setScale(2.0f + world.field_73012_v.nextFloat() * 4.0f);
        fb.setLoop(false);
        fb.setSlowDown(0.9);
        fb.setGravity(0.0f);
        fb.setRotationSpeed(world.field_73012_v.nextFloat(), 0.0f);
        ParticleEngine.addEffectWithDelay(world, fb, world.field_73012_v.nextInt(4));
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187514_aD, SoundCategory.PLAYERS, 0.15f, 1.0f + caster.func_130014_f_().field_73012_v.nextFloat() / 2.0f);
    }
}
