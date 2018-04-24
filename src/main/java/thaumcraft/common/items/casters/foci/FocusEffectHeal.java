package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import thaumcraft.api.casters.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;

public class FocusEffectHeal extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSHEAL";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.HEAL";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.LIFE;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("power") * 4;
    }
    
    @Override
    public float getDamageForDisplay(final float finalPower) {
        return -this.getSettingValue("power") * finalPower;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusPartImpact(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, new String[] { this.getKey() }), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
        if (target.field_72313_a == RayTraceResult.Type.ENTITY && target.field_72308_g != null && target.field_72308_g instanceof EntityLivingBase) {
            if (((EntityLivingBase)target.field_72308_g).func_70662_br()) {
                target.field_72308_g.func_70097_a(DamageSource.func_76354_b((Entity)this.getPackage().getCaster(), (Entity)this.getPackage().getCaster()), this.getSettingValue("power") * finalPower * 1.5f);
            }
            else {
                ((EntityLivingBase)target.field_72308_g).func_70691_i(this.getSettingValue("power") * finalPower);
            }
        }
        return false;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[] { new NodeSetting("power", "focus.heal.power", new NodeSetting.NodeSettingIntRange(1, 5)) };
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187542_ac, SoundCategory.PLAYERS, 2.0f, 2.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.10000000149011612));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double x, final double y, final double z, final double vx, final double vy, final double vz) {
        final FXGeneric fb = new FXGeneric(world, x, y, z, vx + world.field_73012_v.nextGaussian() * 0.01, vy + world.field_73012_v.nextGaussian() * 0.01, vz + world.field_73012_v.nextGaussian() * 0.01);
        fb.func_187114_a((int)(10.0f + 10.0f * world.field_73012_v.nextFloat()));
        fb.func_70538_b(1.0f, 1.0f, 1.0f);
        fb.setAlphaF(0.0f, 0.7f, 0.7f, 0.0f);
        fb.setGridSize(64);
        fb.setParticles(0, 1, 1);
        fb.setScale(world.field_73012_v.nextFloat() * 2.0f, world.field_73012_v.nextFloat());
        fb.setSlowDown(0.8);
        fb.setGravity((float)(world.field_73012_v.nextGaussian() * 0.10000000149011612));
        fb.setRandomMovementScale(0.0125f, 0.0125f, 0.0125f);
        fb.setRotationSpeed((float)world.field_73012_v.nextGaussian());
        ParticleEngine.addEffectWithDelay(world, fb, world.field_73012_v.nextInt(4));
    }
}
