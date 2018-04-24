package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import thaumcraft.common.items.casters.*;
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

public class FocusEffectExchange extends FocusEffect implements IFocusBlockPicker
{
    @Override
    public String getResearch() {
        return "FOCUSEXCHANGE";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.EXCHANGE";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.EXCHANGE;
    }
    
    @Override
    public int getComplexity() {
        return (5 + this.getSettingValue("silk") * 4 + this.getSettingValue("fortune") == 0) ? 0 : ((this.getSettingValue("fortune") + 1) * 3);
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        if (target.field_72313_a != RayTraceResult.Type.BLOCK) {
            return false;
        }
        ItemStack casterStack = ItemStack.field_190927_a;
        if (this.getPackage().getCaster().func_184614_ca() != null && this.getPackage().getCaster().func_184614_ca().func_77973_b() instanceof ItemCaster) {
            casterStack = this.getPackage().getCaster().func_184614_ca();
        }
        else if (this.getPackage().getCaster().func_184592_cb() != null && this.getPackage().getCaster().func_184592_cb().func_77973_b() instanceof ItemCaster) {
            casterStack = this.getPackage().getCaster().func_184592_cb();
        }
        if (casterStack.func_190926_b()) {
            return false;
        }
        final boolean silk = this.getSettingValue("silk") > 0;
        final int fortune = this.getSettingValue("fortune");
        if (this.getPackage().getCaster() instanceof EntityPlayer) {
            ServerEvents.addSwapper(this.getPackage().world, target.func_178782_a(), this.getPackage().world.func_180495_p(target.func_178782_a()), ((ItemCaster)casterStack.func_77973_b()).getPickedBlock(casterStack), true, 0, (EntityPlayer)this.getPackage().getCaster(), true, false, 8038177, true, silk, fortune, ServerEvents.DEFAULT_PREDICATE, 0.25f + (silk ? 0.25f : 0.0f) + fortune * 0.1f);
        }
        return true;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] silk = { 0, 1 };
        final String[] silkDesc = { "focus.common.no", "focus.common.yes" };
        final int[] fortune = { 0, 1, 2, 3, 4 };
        final String[] fortuneDesc = { "focus.common.no", "I", "II", "III", "IV" };
        return new NodeSetting[] { new NodeSetting("fortune", "focus.common.fortune", new NodeSetting.NodeSettingIntList(fortune, fortuneDesc)), new NodeSetting("silk", "focus.common.silk", new NodeSetting.NodeSettingIntList(silk, silkDesc)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double x, final double y, final double z, final double vx, final double vy, final double vz) {
        final FXGeneric fb = new FXGeneric(world, x, y, z, vx + world.field_73012_v.nextGaussian() * 0.01, vy + world.field_73012_v.nextGaussian() * 0.01, vz + world.field_73012_v.nextGaussian() * 0.01);
        fb.func_187114_a(9);
        fb.func_70538_b(0.25f + world.field_73012_v.nextFloat() * 0.25f, 0.25f + world.field_73012_v.nextFloat() * 0.25f, 0.25f + world.field_73012_v.nextFloat() * 0.25f);
        fb.setAlphaF(0.0f, 0.6f, 0.6f, 0.0f);
        fb.setGridSize(64);
        fb.setParticles(448, 9, 1);
        fb.setScale(0.5f, 0.25f);
        fb.setGravity((float)(world.field_73012_v.nextGaussian() * 0.009999999776482582));
        fb.setRandomMovementScale(0.0025f, 0.0025f, 0.0025f);
        ParticleEngine.addEffect(world, fb);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_190021_aL, SoundCategory.PLAYERS, 0.2f, 2.0f + (float)(caster.field_70170_p.field_73012_v.nextGaussian() * 0.05000000074505806));
    }
}
