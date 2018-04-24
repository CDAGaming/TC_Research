package thaumcraft.common.items.casters.foci;

import thaumcraft.api.aspects.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.misc.*;
import thaumcraft.api.casters.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class FocusEffectRift extends FocusEffect
{
    @Override
    public String getResearch() {
        return "FOCUSRIFT";
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.RIFT";
    }
    
    @Override
    public Aspect getAspect() {
        return Aspect.ELDRITCH;
    }
    
    @Override
    public int getComplexity() {
        return 3 + this.getSettingValue("duration") / 2 + this.getSettingValue("depth") / 4;
    }
    
    @Override
    public boolean execute(final RayTraceResult target, final Trajectory trajectory, final float finalPower, final int num) {
        if (target.field_72313_a != RayTraceResult.Type.BLOCK) {
            return false;
        }
        if (this.getPackage().world.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId) {
            this.getPackage().world.func_184148_a((EntityPlayer)null, target.func_178782_a().func_177958_n() + 0.5, target.func_178782_a().func_177956_o() + 0.5, target.func_178782_a().func_177952_p() + 0.5, SoundsTC.wandfail, SoundCategory.PLAYERS, 1.0f, 1.0f);
            return false;
        }
        final float maxdis = this.getSettingValue("depth") * finalPower;
        final int dur = 20 * this.getSettingValue("duration");
        int distance = 0;
        BlockPos pos = new BlockPos((Vec3i)target.func_178782_a());
        for (distance = 0; distance < maxdis; ++distance) {
            final IBlockState bi = this.getPackage().world.func_180495_p(pos);
            if (BlockUtils.isPortableHoleBlackListed(bi) || bi.func_177230_c() == Blocks.field_150357_h || bi.func_177230_c() == BlocksTC.hole || bi.func_177230_c().isAir(bi, (IBlockAccess)this.getPackage().world, pos)) {
                break;
            }
            if (bi.func_185887_b(this.getPackage().world, pos) == -1.0f) {
                break;
            }
            pos = pos.func_177972_a(target.field_178784_b.func_176734_d());
        }
        createHole(this.getPackage().world, target.func_178782_a(), target.field_178784_b, (byte)Math.round(distance + 1), dur);
        return true;
    }
    
    public static boolean createHole(final World world, final BlockPos pos, final EnumFacing side, final byte count, final int max) {
        final IBlockState bs = world.func_180495_p(pos);
        if (!world.field_72995_K && world.func_175625_s(pos) == null && !BlockUtils.isPortableHoleBlackListed(bs) && bs.func_177230_c() != Blocks.field_150357_h && bs.func_177230_c() != BlocksTC.hole && (bs.func_177230_c().isAir(bs, (IBlockAccess)world, pos) || !bs.func_177230_c().func_176196_c(world, pos)) && bs.func_185887_b(world, pos) != -1.0f) {
            if (world.func_175656_a(pos, BlocksTC.hole.func_176223_P())) {
                final TileHole ts = (TileHole)world.func_175625_s(pos);
                ts.oldblock = bs;
                ts.countdownmax = (short)max;
                ts.count = count;
                ts.direction = side;
                ts.func_70296_d();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public NodeSetting[] createSettings() {
        final int[] depth = { 8, 16, 24, 32 };
        final String[] depthDesc = { "8", "16", "24", "32" };
        return new NodeSetting[] { new NodeSetting("depth", "focus.rift.depth", new NodeSetting.NodeSettingIntList(depth, depthDesc)), new NodeSetting("duration", "focus.common.duration", new NodeSetting.NodeSettingIntRange(2, 10)) };
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void renderParticleFX(final World world, final double posX, final double posY, final double posZ, final double motionX, final double motionY, final double motionZ) {
        final FXGeneric fb = new FXGeneric(world, posX, posY, posZ, motionX, motionY, motionZ);
        fb.func_187114_a(16 + world.field_73012_v.nextInt(16));
        fb.setParticles(384 + world.field_73012_v.nextInt(16), 1, 1);
        fb.setSlowDown(0.75);
        fb.setAlphaF(1.0f, 0.0f);
        fb.setScale((float)(0.699999988079071 + world.field_73012_v.nextGaussian() * 0.30000001192092896));
        fb.func_70538_b(0.25f, 0.25f, 1.0f);
        fb.setRandomMovementScale(0.01f, 0.01f, 0.01f);
        ParticleEngine.addEffectWithDelay(world, fb, 0);
    }
    
    @Override
    public void onCast(final Entity caster) {
        caster.field_70170_p.func_184133_a((EntityPlayer)null, caster.func_180425_c().func_177984_a(), SoundEvents.field_190021_aL, SoundCategory.PLAYERS, 0.2f, 0.7f);
    }
}
