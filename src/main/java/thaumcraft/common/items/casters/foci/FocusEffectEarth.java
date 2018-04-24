package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.events.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class FocusEffectEarth extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSELEMENTAL";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.EARTH";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.EARTH;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("power") * 3;
    }
    
    @Override
    public float getDamageForDisplay(final float finalPower) {
        return 2 * this.getSettingValue("power") * finalPower;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusPartImpact(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, new String[] { this.getKey() }), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
        if (target.field_72313_a == RayTraceResult.Type.ENTITY && target.field_72308_g != null) {
            final float damage = this.getDamageForDisplay(finalPower);
            target.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)((target.field_72308_g != null) ? target.field_72308_g : this.getPackage().getCaster()), (Entity)this.getPackage().getCaster()), damage);
            return true;
        }
        if (target.field_72313_a == RayTraceResult.Type.BLOCK) {
            final BlockPos pos = target.func_178782_a();
            if (this.getPackage().getCaster() instanceof EntityPlayer && this.getPackage().world.func_180495_p(pos).func_185887_b(this.getPackage().world, pos) <= this.getDamageForDisplay(finalPower) / 25.0f) {
                ServerEvents.addBreaker(this.getPackage().world, pos, this.getPackage().world.func_180495_p(pos), (EntityPlayer)this.getPackage().getCaster(), false, false, 0, 1.0f, 0.0f, 1.0f, num, 0.1f, null);
            }
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
        pp.grav = 0.4f;
        pp.layer = 1;
        pp.age = 20 + world.field_73012_v.nextInt(10);
        pp.alpha = new float[] { 1.0f, 0.0f };
        pp.partStart = 75 + world.field_73012_v.nextInt(4);
        pp.partInc = 1;
        pp.partNum = 1;
        pp.slowDown = 0.9;
        pp.rot = (float)world.field_73012_v.nextGaussian();
        final float s = (float)(1.0 + world.field_73012_v.nextGaussian() * 0.20000000298023224);
        pp.scale = new float[] { s, s / 2.0f };
        FXDispatcher.INSTANCE.drawGenericParticles(posX, posY, posZ, motionX, motionY, motionZ, pp);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187523_aM, SoundCategory.PLAYERS, 0.25f, 1.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.05000000074505806));
    }
}
