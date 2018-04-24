package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.events.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class FocusEffectBreak extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSBREAK";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.BREAK";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.ENTROPY;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("power") * 3 + this.getSettingValue("silk") * 4 + ((this.getSettingValue("fortune") == 0) ? 0 : ((this.getSettingValue("fortune") + 1) * 3));
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        if (target.field_72313_a == RayTraceResult.Type.BLOCK) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusPartImpact(target.func_178782_a().func_177958_n() + 0.5, target.func_178782_a().func_177956_o() + 0.5, target.func_178782_a().func_177952_p() + 0.5, new String[] { this.getKey() }), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
            final boolean silk = this.getSettingValue("silk") > 0;
            final int fortune = this.getSettingValue("fortune");
            final float strength = this.getSettingValue("power") * finalPower;
            float dur = this.getPackage().world.func_180495_p(target.func_178782_a()).func_185887_b(this.getPackage().world, target.func_178782_a()) * 100.0f;
            dur = (float)Math.sqrt(dur);
            if (this.getPackage().getCaster() instanceof EntityPlayer) {
                ServerEvents.addBreaker(this.getPackage().world, target.func_178782_a(), this.getPackage().world.func_180495_p(target.func_178782_a()), (EntityPlayer)this.getPackage().getCaster(), true, silk, fortune, strength, dur, dur, (int)(dur / strength / 3.0f * num), 0.25f + (silk ? 0.25f : 0.0f) + fortune * 0.1f, null);
            }
            return true;
        }
        return true;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] silk = { 0, 1 };
        final String[] silkDesc = { "focus.common.no", "focus.common.yes" };
        final int[] fortune = { 0, 1, 2, 3, 4 };
        final String[] fortuneDesc = { "focus.common.no", "I", "II", "III", "IV" };
        return new NodeSetting[] { new NodeSetting("power", "focus.break.power", new NodeSetting.NodeSettingIntRange(1, 5)), new NodeSetting("fortune", "focus.common.fortune", new NodeSetting.NodeSettingIntList(fortune, fortuneDesc)), new NodeSetting("silk", "focus.common.silk", new NodeSetting.NodeSettingIntList(silk, silkDesc)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(6 + world.field_73012_v.nextInt(6));
        final int q = world.field_73012_v.nextInt(4);
        fb.setParticles(704 + q * 3, 3, 1);
        fb.setSlowDown(0.8);
        fb.setScale((float)(1.7000000476837158 + world.field_73012_v.nextGaussian() * 0.30000001192092896));
        ParticleEngine.addEffect(world, fb);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187598_bd, SoundCategory.PLAYERS, 0.1f, 2.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.05000000074505806));
    }
}
