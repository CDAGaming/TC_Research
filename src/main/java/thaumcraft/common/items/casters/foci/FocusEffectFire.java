package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;

public class FocusEffectFire extends FocusEffect
{
    @Override
    public String getResearch() {
        return "BASEAUROMANCY";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.FIRE";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.FIRE;
    }
    
    @Override
    public int getComplexity() {
        return this.getSettingValue("duration") + this.getSettingValue("power") * 2;
    }
    
    @Override
    public float getDamageForDisplay(final float finalPower) {
        return (3 + this.getSettingValue("power")) * finalPower;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXFocusPartImpact(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, new String[] { this.getKey() }), new NetworkRegistry.TargetPoint(this.getPackage().world.field_73011_w.getDimension(), target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c, 64.0));
        if (target.field_72313_a != RayTraceResult.Type.ENTITY || target.field_72308_g == null) {
            if (target.field_72313_a == RayTraceResult.Type.BLOCK && this.getSettingValue("duration") > 0) {
                BlockPos pos = target.func_178782_a();
                pos = pos.func_177972_a(target.field_178784_b);
                if (this.getPackage().world.func_175623_d(pos) && this.getPackage().world.field_73012_v.nextFloat() < finalPower) {
                    this.getPackage().world.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187649_bu, SoundCategory.BLOCKS, 1.0f, this.getPackage().world.field_73012_v.nextFloat() * 0.4f + 0.8f);
                    this.getPackage().world.func_180501_a(pos, Blocks.field_150480_ab.func_176223_P(), 11);
                    return true;
                }
            }
            return false;
        }
        if (target.field_72308_g.func_70045_F()) {
            return false;
        }
        float fire = 1 + this.getSettingValue("duration") * this.getSettingValue("duration");
        final float damage = this.getDamageForDisplay(finalPower);
        fire *= finalPower;
        target.field_72308_g.func_70097_a(new EntityDamageSourceIndirect("fireball", (Entity)((target.field_72308_g != null) ? target.field_72308_g : this.getPackage().getCaster()), (Entity)this.getPackage().getCaster()).func_76361_j(), damage);
        if (fire > 0.0f) {
            target.field_72308_g.func_70015_d(Math.round(fire));
        }
        return true;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[] { new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5)), new NodeSetting("duration", "focus.fire.burn", new NodeSetting.NodeSettingIntRange(0, 5)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXDispatcher.GenPart pp = new FXDispatcher.GenPart();
        pp.grav = -0.2f;
        pp.age = 10;
        pp.alpha = new float[] { 0.7f };
        pp.partStart = 640;
        pp.partInc = 1;
        pp.partNum = 10;
        pp.slowDown = 0.75;
        pp.scale = new float[] { (float)(1.5 + world.field_73012_v.nextGaussian() * 0.20000000298023224) };
        FXDispatcher.INSTANCE.drawGenericParticles(posX, posY, posZ, motionX, motionY, motionZ, pp);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187616_bj, SoundCategory.PLAYERS, 1.0f, 1.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.05000000074505806));
    }
}
