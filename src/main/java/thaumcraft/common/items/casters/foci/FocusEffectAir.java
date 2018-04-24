package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.*;

public class FocusEffectAir extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSELEMENTAL";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.AIR";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.AIR;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("power") * 2;
    }
    
    @Override
    public float getDamageForDisplay(final float finalPower) {
        return (1 + this.getSettingValue("power")) * finalPower;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusPartImpact(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, new String[] { this.getKey() }), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
        this.getPackage().world.func_184148_a((EntityPlayer)null, target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, SoundEvents.field_187524_aN, SoundCategory.PLAYERS, 0.5f, 0.66f);
        if (target.field_72313_a == RayTraceResult.Type.ENTITY && target.field_72308_g != null) {
            final float damage = this.getDamageForDisplay(finalPower);
            target.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)((target.field_72308_g != null) ? target.field_72308_g : this.getPackage().getCaster()), (Entity)this.getPackage().getCaster()), damage);
            if (target.field_72308_g instanceof EntityLivingBase) {
                if (trajectory != null) {
                    ((EntityLivingBase)target.field_72308_g).func_70653_a((Entity)this.getPackage().getCaster(), damage * 0.25f, -trajectory.direction.field_72450_a, -trajectory.direction.field_72449_c);
                }
                else {
                    ((EntityLivingBase)target.field_72308_g).func_70653_a((Entity)this.getPackage().getCaster(), damage * 0.25f, (double)(-MathHelper.func_76126_a(target.field_72308_g.field_70177_z * 0.017453292f)), (double)MathHelper.func_76134_b(target.field_72308_g.field_70177_z * 0.017453292f));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[] { new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXDispatcher.GenPart pp = new FXDispatcher.GenPart();
        pp.grav = -0.1f;
        pp.age = 20 + world.field_73012_v.nextInt(10);
        pp.alpha = new float[] { 0.5f, 0.0f };
        pp.grid = 32;
        pp.partStart = 337;
        pp.partInc = 1;
        pp.partNum = 5;
        pp.slowDown = 0.75;
        pp.rot = (float)world.field_73012_v.nextGaussian() / 2.0f;
        final float s = (float)(2.0 + world.field_73012_v.nextGaussian() * 0.5);
        pp.scale = new float[] { s, s * 2.0f };
        FXDispatcher.INSTANCE.drawGenericParticles(posX, posY, posZ, motionX, motionY, motionZ, pp);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundsTC.wind, SoundCategory.PLAYERS, 0.125f, 2.0f);
    }
}
