package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.state.*;
import thaumcraft.api.casters.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class FocusEffectFrost extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSELEMENTAL";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.FROST";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.COLD;
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
        if (target.field_72313_a == RayTraceResult.Type.ENTITY && target.field_72308_g != null) {
            final float damage = this.getDamageForDisplay(finalPower);
            final int duration = 20 * this.getSettingValue("duration");
            final int potency = (int)(1.0f + this.getSettingValue("power") * finalPower / 3.0f);
            target.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)((target.field_72308_g != null) ? target.field_72308_g : this.getPackage().getCaster()), (Entity)this.getPackage().getCaster()), damage);
            if (target.field_72308_g instanceof EntityLivingBase) {
                ((EntityLivingBase)target.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76421_d, duration, potency));
            }
        }
        else if (target.field_72313_a == RayTraceResult.Type.BLOCK) {
            final float f = Math.min(16.0f, 2 * this.getSettingValue("power") * finalPower);
            for (final BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.func_177975_b(target.func_178782_a().func_177963_a((double)(-f), (double)(-f), (double)(-f)), target.func_178782_a().func_177963_a((double)f, (double)f, (double)f))) {
                if (blockpos$mutableblockpos1.func_177957_d(target.field_72307_f.field_72450_a, target.field_72307_f.field_72448_b, target.field_72307_f.field_72449_c) <= f * f) {
                    final IBlockState iblockstate1 = this.getPackage().world.func_180495_p((BlockPos)blockpos$mutableblockpos1);
                    if (iblockstate1.func_185904_a() != Material.field_151586_h || (int)iblockstate1.func_177229_b((IProperty)BlockLiquid.field_176367_b) != 0 || !this.getPackage().world.func_190527_a(Blocks.field_185778_de, (BlockPos)blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity)null)) {
                        continue;
                    }
                    this.getPackage().world.func_175656_a((BlockPos)blockpos$mutableblockpos1, Blocks.field_185778_de.func_176223_P());
                    this.getPackage().world.func_175684_a(blockpos$mutableblockpos1.func_185334_h(), Blocks.field_185778_de, MathHelper.func_76136_a(this.getPackage().world.field_73012_v, 60, 120));
                }
            }
        }
        return false;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[] { new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5)), new NodeSetting("duration", "focus.common.duration", new NodeSetting.NodeSettingIntRange(2, 10)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(40 + world.field_73012_v.nextInt(40));
        fb.setAlphaF(1.0f, 0.0f);
        fb.setParticles(8, 1, 1);
        fb.setGravity(0.033f);
        fb.setSlowDown(0.8);
        fb.setRandomMovementScale(0.0025f, 1.0E-4f, 0.0025f);
        fb.setScale((float)(0.699999988079071 + world.field_73012_v.nextGaussian() * 0.30000001192092896));
        fb.setRotationSpeed(world.field_73012_v.nextFloat() * 3.0f, (float)world.field_73012_v.nextGaussian() / 4.0f);
        ParticleEngine.addEffectWithDelay(world, fb, 0);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_187942_hp, SoundCategory.PLAYERS, 0.2f, 1.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.05000000074505806));
    }
}
