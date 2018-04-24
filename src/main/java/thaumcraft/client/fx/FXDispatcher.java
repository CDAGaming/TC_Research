package thaumcraft.client.fx;

import net.minecraftforge.fml.client.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.*;
import java.awt.*;
import net.minecraft.block.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.particle.*;
import thaumcraft.client.fx.other.*;
import thaumcraft.api.items.*;
import net.minecraft.init.*;
import thaumcraft.client.fx.beams.*;
import thaumcraft.client.fx.particles.*;

public class FXDispatcher
{
    public static FXDispatcher INSTANCE;
    static int q;
    
    public World getWorld() {
        return (World)FMLClientHandler.instance().getClient().field_71441_e;
    }
    
    public void drawFireMote(final float x, final float y, final float z, final float vx, final float vy, final float vz, final float r, final float g, final float b, final float alpha, final float scale) {
        final boolean bb = this.getWorld().field_73012_v.nextBoolean();
        final FXFireMote glow = new FXFireMote(this.getWorld(), x, y, z, vx, vy, vz, r, g, b, bb ? (scale / 3.0f) : scale, bb ? 1 : 0);
        glow.func_82338_g(alpha);
        ParticleEngine.addEffect(this.getWorld(), glow);
    }
    
    public void drawAlumentum(final float x, final float y, final float z, final float vx, final float vy, final float vz, final float r, final float g, final float b, final float alpha, final float scale) {
        final FXFireMote glow = new FXFireMote(this.getWorld(), x, y, z, vx, vy, vz, r, g, b, scale, 1);
        glow.func_82338_g(alpha);
        ParticleEngine.addEffect(this.getWorld(), glow);
    }
    
    public void drawTaintParticles(final float x, final float y, final float z, final float vx, final float vy, final float vz, final float scale) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, vx, vy, vz);
        fb.func_187114_a(80 + this.getWorld().field_73012_v.nextInt(20));
        fb.func_70538_b(0.4f + this.getWorld().field_73012_v.nextFloat() * 0.2f, 0.1f + this.getWorld().field_73012_v.nextFloat() * 0.3f, 0.5f + this.getWorld().field_73012_v.nextFloat() * 0.2f);
        fb.setAlphaF(0.75f, 0.0f);
        fb.setGridSize(16);
        fb.setParticles(57 + this.getWorld().field_73012_v.nextInt(3), 1, 1);
        fb.setScale(scale, scale / 4.0f);
        fb.setLayer(1);
        fb.setSlowDown(0.9750000238418579);
        fb.setGravity(0.2f);
        fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawLightningFlash(final double x, final double y, final double z, final float r, final float g, final float b, final float alpha, final float scale) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, 0.0, 0.0, 0.0);
        fb.func_187114_a(5 + this.getWorld().field_73012_v.nextInt(5));
        fb.setGridSize(16);
        fb.func_70538_b(r, g, b);
        fb.setAlphaF(alpha, 0.0f);
        fb.setParticles(108 + this.getWorld().field_73012_v.nextInt(4), 1, 1);
        fb.setScale(scale);
        fb.setLayer(0);
        fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), 0.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawGenericParticles(final double x, final double y, final double z, final double mx, final double my, final double mz, final GenPart part) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, mx, my, mz);
        fb.func_187114_a(part.age);
        fb.setRBGColorF(part.redStart, part.greenStart, part.blueStart, part.redEnd, part.greenEnd, part.blueEnd);
        fb.setAlphaF(part.alpha);
        fb.setLoop(part.loop);
        fb.setParticles(part.partStart, part.partNum, part.partInc);
        fb.setScale(part.scale);
        fb.setLayer(part.layer);
        fb.setRotationSpeed(part.rotstart, part.rot);
        fb.setSlowDown(part.slowDown);
        fb.setGravity(part.grav);
        fb.setGridSize(part.grid);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, part.delay);
    }
    
    public void drawGenericParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2, final float r, final float g, final float b, final float alpha, final boolean loop, final int start, final int num, final int inc, final int age, final int delay, final float scale, final float rot, final int layer) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.func_187114_a(age);
        fb.func_70538_b(r, g, b);
        fb.func_82338_g(alpha);
        fb.setLoop(loop);
        fb.setParticles(start, num, inc);
        fb.setScale(scale);
        fb.setLayer(layer);
        fb.setRotationSpeed(rot);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, delay);
    }
    
    public void drawGenericParticles16(final double x, final double y, final double z, final double x2, final double y2, final double z2, final float r, final float g, final float b, final float alpha, final boolean loop, final int start, final int num, final int inc, final int age, final int delay, final float scale, final float rot, final int layer) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.setGridSize(16);
        fb.func_187114_a(age);
        fb.func_70538_b(r, g, b);
        fb.func_82338_g(alpha);
        fb.setLoop(loop);
        fb.setParticles(start, num, inc);
        fb.setScale(scale);
        fb.setLayer(layer);
        fb.setRotationSpeed(rot);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, delay);
    }
    
    public void drawLevitatorParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.func_187114_a(200 + this.getWorld().field_73012_v.nextInt(100));
        fb.func_70538_b(0.5f, 0.5f, 0.2f);
        fb.setAlphaF(0.3f, 0.0f);
        fb.setGridSize(16);
        fb.setParticles(56, 1, 1);
        fb.setScale(2.0f, 5.0f);
        fb.setLayer(0);
        fb.setSlowDown(1.0);
        fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawGolemFlyParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        try {
            final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
            fb.func_187114_a(40 + this.getWorld().field_73012_v.nextInt(20));
            fb.setAlphaF(0.2f, 0.0f);
            fb.setGridSize(8);
            fb.setParticles(24, 1, 1);
            fb.setScale(2.0f, 8.0f);
            fb.setLayer(0);
            fb.setSlowDown(1.0);
            fb.setWind(0.001);
            fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
            ParticleEngine.addEffect(this.getWorld(), fb);
        }
        catch (Exception ex) {}
    }
    
    public void drawPollutionParticles(final BlockPos p) {
        final float x = p.func_177958_n() + 0.2f + this.getWorld().field_73012_v.nextFloat() * 0.6f;
        final float y = p.func_177956_o() + 0.2f + this.getWorld().field_73012_v.nextFloat() * 0.6f;
        final float z = p.func_177952_p() + 0.2f + this.getWorld().field_73012_v.nextFloat() * 0.6f;
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.005, 0.02, (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.005);
        fb.func_187114_a(400 + this.getWorld().field_73012_v.nextInt(100));
        fb.func_70538_b(1.0f, 0.3f, 0.9f);
        fb.setAlphaF(0.5f, 0.0f);
        fb.setGridSize(16);
        fb.setParticles(56, 1, 1);
        fb.setScale(2.0f, 5.0f);
        fb.setLayer(1);
        fb.setSlowDown(1.0);
        fb.setWind(0.001);
        fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawBlockSparkles(final BlockPos p, final Vec3d start) {
        final AxisAlignedBB bs = this.getWorld().func_180495_p(p).func_185900_c((IBlockAccess)this.getWorld(), p);
        bs.func_72314_b(0.1, 0.1, 0.1);
        final int num = (int)(bs.func_72320_b() * 20.0);
        for (final EnumFacing face : EnumFacing.values()) {
            final IBlockState state = this.getWorld().func_180495_p(p.func_177972_a(face));
            if (!state.func_185914_p()) {
                if (!state.isSideSolid((IBlockAccess)this.getWorld(), p.func_177972_a(face), face.func_176734_d())) {
                    final boolean rx = face.func_82601_c() == 0;
                    final boolean ry = face.func_96559_d() == 0;
                    final boolean rz = face.func_82599_e() == 0;
                    final double mx = 0.5 + face.func_82601_c() * 0.51;
                    final double my = 0.5 + face.func_96559_d() * 0.51;
                    final double mz = 0.5 + face.func_82599_e() * 0.51;
                    for (int a = 0; a < num * 2; ++a) {
                        double x = mx;
                        double y = my;
                        double z = mz;
                        if (rx) {
                            x += this.getWorld().field_73012_v.nextGaussian() * 0.6;
                        }
                        if (ry) {
                            y += this.getWorld().field_73012_v.nextGaussian() * 0.6;
                        }
                        if (rz) {
                            z += this.getWorld().field_73012_v.nextGaussian() * 0.6;
                        }
                        x = MathHelper.func_151237_a(x, bs.field_72340_a, bs.field_72336_d);
                        y = MathHelper.func_151237_a(y, bs.field_72338_b, bs.field_72337_e);
                        z = MathHelper.func_151237_a(z, bs.field_72339_c, bs.field_72334_f);
                        final float r = MathHelper.func_76136_a(this.getWorld().field_73012_v, 255, 255) / 255.0f;
                        final float g = MathHelper.func_76136_a(this.getWorld().field_73012_v, 189, 255) / 255.0f;
                        final float b = MathHelper.func_76136_a(this.getWorld().field_73012_v, 64, 255) / 255.0f;
                        final Vec3d v1 = new Vec3d(p.func_177958_n() + x, p.func_177956_o() + y, p.func_177952_p() + z);
                        final double delay = this.getWorld().field_73012_v.nextInt(5) + v1.func_72438_d(start) * 16.0;
                        this.drawSimpleSparkle(this.getWorld().field_73012_v, p.func_177958_n() + x, p.func_177956_o() + y, p.func_177952_p() + z, 0.0, 0.0025, 0.0, 0.4f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f, r, g, b, (int)delay, 1.0f, 0.01f, false, 16);
                    }
                }
            }
        }
    }
    
    public void drawSimpleSparkle(final Random rand, final double x, final double y, final double z, final double x2, final double y2, final double z2, final float scale, final float r, final float g, final float b, final int delay, final float decay, final float grav, final boolean thaumclamp, final int baseAge) {
        final boolean sp = rand.nextFloat() < 0.2;
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        final int age = baseAge * 4 + this.getWorld().field_73012_v.nextInt(baseAge);
        fb.func_187114_a(age);
        fb.func_70538_b(r, g, b);
        final float[] alphas = new float[6 + rand.nextInt(age / 3)];
        for (int a = 1; a < alphas.length - 1; ++a) {
            alphas[a] = rand.nextFloat();
        }
        fb.setAlphaF(alphas);
        fb.setParticles(sp ? 320 : 512, 16, 1);
        fb.setLoop(true);
        fb.setGravity(grav);
        fb.setScale(scale, scale * 2.0f);
        fb.setLayer(0);
        fb.setSlowDown(decay);
        fb.setRandomMovementScale(5.0E-4f, 0.001f, 5.0E-4f);
        fb.setWind(5.0E-4);
        fb.setThaumClamp(thaumclamp);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, delay);
    }
    
    public void drawSimpleSparkleGui(final Random rand, final double x, final double y, final double x2, final double y2, final float scale, final float r, final float g, final float b, final int delay, final float decay, final float grav) {
        final boolean sp = rand.nextFloat() < 0.2;
        final FXGenericGui fb = new FXGenericGui(this.getWorld(), x, y, 0.0, x2, y2, 0.0);
        fb.func_187114_a(32 + this.getWorld().field_73012_v.nextInt(8));
        fb.func_70538_b(r, g, b);
        fb.setAlphaF(0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f);
        fb.setParticles(sp ? 320 : 512, 16, 1);
        fb.setLoop(true);
        fb.setGravity(grav);
        fb.setScale(scale, scale * 2.0f);
        fb.setNoClip(false);
        fb.setLayer(4);
        fb.setSlowDown(decay);
        fb.setRandomMovementScale(0.025f, 0.025f, 0.0f);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, delay);
    }
    
    public void drawBlockMistParticles(final BlockPos p, final int c) {
        final AxisAlignedBB bs = this.getWorld().func_180495_p(p).func_185900_c((IBlockAccess)this.getWorld(), p);
        final Color color = new Color(c);
        for (int a = 0; a < 8; ++a) {
            final double x = p.func_177958_n() + bs.field_72340_a + this.getWorld().field_73012_v.nextFloat() * (bs.field_72336_d - bs.field_72340_a);
            final double y = p.func_177956_o() + bs.field_72338_b + this.getWorld().field_73012_v.nextFloat() * (bs.field_72337_e - bs.field_72338_b);
            final double z = p.func_177952_p() + bs.field_72339_c + this.getWorld().field_73012_v.nextFloat() * (bs.field_72334_f - bs.field_72339_c);
            final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, this.getWorld().field_73012_v.nextGaussian() * 0.01, this.getWorld().field_73012_v.nextFloat() * 0.075, this.getWorld().field_73012_v.nextGaussian() * 0.01);
            fb.func_187114_a(50 + this.getWorld().field_73012_v.nextInt(25));
            fb.func_70538_b(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            fb.setAlphaF(0.0f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f, 0.0f);
            fb.setGridSize(16);
            fb.setParticles(56, 1, 1);
            fb.setScale(5.0f, 1.0f);
            fb.setLayer(0);
            fb.setSlowDown(1.0);
            fb.setGravity(0.1f);
            fb.setWind(0.001);
            fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
            ParticleEngine.addEffect(this.getWorld(), fb);
        }
    }
    
    public void drawFocusCloudParticle(final double x, final double y, final double z, final double mx, final double my, final double mz, final int c) {
        final Color color = new Color(c);
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, mx, my, mz);
        fb.func_187114_a(20 + this.getWorld().field_73012_v.nextInt(10));
        fb.func_70538_b(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        fb.setAlphaF(0.0f, 0.66f, 0.0f);
        fb.setGridSize(16);
        fb.setParticles(56 + this.getWorld().field_73012_v.nextInt(4), 1, 1);
        fb.setScale(5.0f + this.getWorld().field_73012_v.nextFloat(), 10.0f + this.getWorld().field_73012_v.nextFloat());
        fb.setLayer(0);
        fb.setSlowDown(0.99);
        fb.setWind(0.001);
        fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -0.25f : 0.25f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawWispyMotesOnBlock(final BlockPos pp, final int age, final float grav) {
        this.drawWispyMotes(pp.func_177958_n() + this.getWorld().field_73012_v.nextFloat(), pp.func_177956_o(), pp.func_177952_p() + this.getWorld().field_73012_v.nextFloat(), 0.0, 0.0, 0.0, age, 0.4f + this.getWorld().field_73012_v.nextFloat() * 0.6f, 0.6f + this.getWorld().field_73012_v.nextFloat() * 0.4f, 0.6f + this.getWorld().field_73012_v.nextFloat() * 0.4f, grav);
    }
    
    public void drawWispyMotes(final double d, final double e, final double f, final double vx, final double vy, final double vz, final int age, final float grav) {
        this.drawWispyMotes(d, e, f, vx, vy, vz, age, 0.25f + this.getWorld().field_73012_v.nextFloat() * 0.75f, 0.25f + this.getWorld().field_73012_v.nextFloat() * 0.75f, 0.25f + this.getWorld().field_73012_v.nextFloat() * 0.75f, grav);
    }
    
    public void drawWispyMotes(final double d, final double e, final double f, final double vx, final double vy, final double vz, final int age, final float r, final float g, final float b, final float grav) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), d, e, f, vx, vy, vz);
        fb.func_187114_a((int)(age + age / 2 * this.getWorld().field_73012_v.nextFloat()));
        fb.func_70538_b(r, g, b);
        fb.setAlphaF(0.0f, 0.6f, 0.6f, 0.0f);
        fb.setGridSize(64);
        fb.setParticles(512, 16, 1);
        fb.setScale(1.0f, 0.5f);
        fb.setLoop(true);
        fb.setWind(0.001);
        fb.setGravity(grav);
        fb.setRandomMovementScale(0.0025f, 0.0f, 0.0025f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawBlockMistParticlesFlat(final BlockPos p, final int c) {
        final Block bs = this.getWorld().func_180495_p(p).func_177230_c();
        final Color color = new Color(c);
        for (int a = 0; a < 6; ++a) {
            final double x = p.func_177958_n() + this.getWorld().field_73012_v.nextFloat();
            final double y = p.func_177956_o() + this.getWorld().field_73012_v.nextFloat() * 0.125f;
            final double z = p.func_177952_p() + this.getWorld().field_73012_v.nextFloat();
            final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.005, 0.005, (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.005);
            fb.func_187114_a(400 + this.getWorld().field_73012_v.nextInt(100));
            fb.func_70538_b(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            fb.setAlphaF(1.0f, 0.0f);
            fb.setGridSize(8);
            fb.setParticles(24, 1, 1);
            fb.setScale(2.0f, 5.0f);
            fb.setLayer(0);
            fb.setSlowDown(1.0);
            fb.setWind(0.001);
            fb.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
            ParticleEngine.addEffect(this.getWorld(), fb);
        }
    }
    
    public void crucibleBubble(final float x, final float y, final float z, final float cr, final float cg, final float cb) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, 0.0, 0.0, 0.0);
        fb.func_187114_a(15 + this.getWorld().field_73012_v.nextInt(10));
        fb.setScale(this.getWorld().field_73012_v.nextFloat() * 0.3f + 0.3f);
        fb.func_70538_b(cr, cg, cb);
        fb.setRandomMovementScale(0.002f, 0.002f, 0.002f);
        fb.setGravity(-0.001f);
        fb.setParticle(64);
        fb.setFinalFrames(65, 66, 66);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void crucibleBoil(final BlockPos pos, final TileCrucible tile, final int j) {
        for (int a = 0; a < 2; ++a) {
            final FXGeneric fb = new FXGeneric(this.getWorld(), pos.func_177958_n() + 0.2f + this.getWorld().field_73012_v.nextFloat() * 0.6f, pos.func_177956_o() + 0.1f + tile.getFluidHeight(), pos.func_177952_p() + 0.2f + this.getWorld().field_73012_v.nextFloat() * 0.6f, 0.0, 0.002, 0.0);
            fb.func_187114_a((int)(7.0 + 8.0 / (Math.random() * 0.8 + 0.2)));
            fb.setScale(this.getWorld().field_73012_v.nextFloat() * 0.3f + 0.2f);
            if (tile.aspects.size() == 0) {
                fb.func_70538_b(1.0f, 1.0f, 1.0f);
            }
            else {
                final Color color = new Color(tile.aspects.getAspects()[this.getWorld().field_73012_v.nextInt(tile.aspects.getAspects().length)].getColor());
                fb.func_70538_b(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            }
            fb.setRandomMovementScale(0.001f, 0.001f, 0.001f);
            fb.setGravity(-0.025f * j);
            fb.setParticle(64);
            fb.setFinalFrames(65, 66);
            ParticleEngine.addEffect(this.getWorld(), fb);
        }
    }
    
    public void crucibleFroth(final float x, final float y, final float z) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, 0.0, 0.0, 0.0);
        fb.func_187114_a(4 + this.getWorld().field_73012_v.nextInt(3));
        fb.setScale(this.getWorld().field_73012_v.nextFloat() * 0.2f + 0.2f);
        fb.func_70538_b(0.5f, 0.5f, 0.7f);
        fb.setRandomMovementScale(0.001f, 0.001f, 0.001f);
        fb.setGravity(0.1f);
        fb.setParticle(64);
        fb.setFinalFrames(65, 66);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void crucibleFrothDown(final float x, final float y, final float z) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, 0.0, 0.0, 0.0);
        fb.func_187114_a(12 + this.getWorld().field_73012_v.nextInt(12));
        fb.setScale(this.getWorld().field_73012_v.nextFloat() * 0.2f + 0.4f);
        fb.func_70538_b(0.25f, 0.0f, 0.75f);
        fb.func_82338_g(0.8f);
        fb.setRandomMovementScale(0.001f, 0.001f, 0.001f);
        fb.setGravity(0.05f);
        fb.setNoClip(false);
        fb.setParticle(73);
        fb.setFinalFrames(65, 66);
        fb.setLayer(1);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawBamf(final BlockPos p, final boolean sound, final boolean flair, final EnumFacing side) {
        this.drawBamf(p.func_177958_n() + 0.5, p.func_177956_o() + 0.5, p.func_177952_p() + 0.5, sound, flair, side);
    }
    
    public void drawBamf(final BlockPos p, final float r, final float g, final float b, final boolean sound, final boolean flair, final EnumFacing side) {
        this.drawBamf(p.func_177958_n() + 0.5, p.func_177956_o() + 0.5, p.func_177952_p() + 0.5, r, g, b, sound, flair, side);
    }
    
    public void drawBamf(final BlockPos p, final int color, final boolean sound, final boolean flair, final EnumFacing side) {
        this.drawBamf(p.func_177958_n() + 0.5, p.func_177956_o() + 0.5, p.func_177952_p() + 0.5, color, sound, flair, side);
    }
    
    public void drawBamf(final double x, final double y, final double z, final int color, final boolean sound, final boolean flair, final EnumFacing side) {
        final Color c = new Color(color);
        final float r = c.getRed() / 255.0f;
        final float g = c.getGreen() / 255.0f;
        final float b = c.getBlue() / 255.0f;
        this.drawBamf(x, y, z, r, g, b, sound, flair, side);
    }
    
    public void drawBamf(final double x, final double y, final double z, final boolean sound, final boolean flair, final EnumFacing side) {
        this.drawBamf(x, y, z, 0.5f, 0.1f, 0.6f, sound, flair, side);
    }
    
    public void drawBamf(final double x, final double y, final double z, final float r, final float g, final float b, final boolean sound, final boolean flair, final EnumFacing side) {
        if (sound) {
            this.getWorld().func_184134_a(x, y, z, SoundsTC.poof, SoundCategory.BLOCKS, 0.4f, 1.0f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.05f, false);
        }
        for (int a = 0; a < 6 + this.getWorld().field_73012_v.nextInt(3) + 2; ++a) {
            double vx = (0.05f + this.getWorld().field_73012_v.nextFloat() * 0.05f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            double vy = (0.05f + this.getWorld().field_73012_v.nextFloat() * 0.05f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            double vz = (0.05f + this.getWorld().field_73012_v.nextFloat() * 0.05f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            if (side != null) {
                vx += side.func_82601_c() * 0.1f;
                vy += side.func_96559_d() * 0.1f;
                vz += side.func_82599_e() * 0.1f;
            }
            final FXGeneric fb2 = new FXGeneric(this.getWorld(), x + vx * 2.0, y + vy * 2.0, z + vz * 2.0, vx / 2.0, vy / 2.0, vz / 2.0);
            fb2.func_187114_a(20 + this.getWorld().field_73012_v.nextInt(15));
            fb2.func_70538_b(MathHelper.func_76131_a(r * (1.0f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f), 0.0f, 1.0f), MathHelper.func_76131_a(g * (1.0f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f), 0.0f, 1.0f), MathHelper.func_76131_a(b * (1.0f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f), 0.0f, 1.0f));
            fb2.setAlphaF(1.0f, 0.1f);
            fb2.setGridSize(16);
            fb2.setParticles(123, 5, 1);
            fb2.setScale(3.0f, 4.0f + this.getWorld().field_73012_v.nextFloat() * 3.0f);
            fb2.setLayer(1);
            fb2.setSlowDown(0.7);
            fb2.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? -1.0f : 1.0f);
            ParticleEngine.addEffect(this.getWorld(), fb2);
        }
        if (flair) {
            for (int a = 0; a < 2 + this.getWorld().field_73012_v.nextInt(3); ++a) {
                final double vx = (0.025f + this.getWorld().field_73012_v.nextFloat() * 0.025f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
                final double vy = (0.025f + this.getWorld().field_73012_v.nextFloat() * 0.025f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
                final double vz = (0.025f + this.getWorld().field_73012_v.nextFloat() * 0.025f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
                this.drawWispyMotes(x + vx * 2.0, y + vy * 2.0, z + vz * 2.0, vx, vy, vz, 15 + this.getWorld().field_73012_v.nextInt(10), -0.01f);
            }
            final FXGeneric fb3 = new FXGeneric(this.getWorld(), x, y, z, 0.0, 0.0, 0.0);
            fb3.func_187114_a(10 + this.getWorld().field_73012_v.nextInt(5));
            fb3.func_70538_b(1.0f, 0.9f, 1.0f);
            fb3.setAlphaF(1.0f, 0.0f);
            fb3.setGridSize(16);
            fb3.setParticles(77, 1, 1);
            fb3.setScale(10.0f + this.getWorld().field_73012_v.nextFloat() * 2.0f, 0.0f);
            fb3.setLayer(0);
            fb3.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), (float)this.getWorld().field_73012_v.nextGaussian());
            ParticleEngine.addEffect(this.getWorld(), fb3);
        }
        for (int a = 0; a < (flair ? 2 : 0) + this.getWorld().field_73012_v.nextInt(3); ++a) {
            double vx = (0.0025f + this.getWorld().field_73012_v.nextFloat() * 0.005f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            double vy = (0.0025f + this.getWorld().field_73012_v.nextFloat() * 0.005f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            double vz = (0.0025f + this.getWorld().field_73012_v.nextFloat() * 0.005f) * (this.getWorld().field_73012_v.nextBoolean() ? -1 : 1);
            if (side != null) {
                vx += side.func_82601_c() * 0.025f;
                vy += side.func_96559_d() * 0.025f;
                vz += side.func_82599_e() * 0.025f;
            }
            final FXGeneric fb2 = new FXGeneric(this.getWorld(), x + vx * 5.0, y + vy * 5.0, z + vz * 5.0, vx, vy, vz);
            if (a > 0 && this.getWorld().field_73012_v.nextBoolean()) {
                fb2.setAngles(90.0f * (float)this.getWorld().field_73012_v.nextGaussian(), 90.0f * (float)this.getWorld().field_73012_v.nextGaussian());
            }
            fb2.func_187114_a(25 + this.getWorld().field_73012_v.nextInt(20 + 20 * a));
            fb2.setRBGColorF((0.9f + this.getWorld().field_73012_v.nextFloat() * 0.1f + r) / 2.0f, (0.1f + g) / 2.0f, (0.5f + this.getWorld().field_73012_v.nextFloat() * 0.1f + b) / 2.0f, 0.1f + this.getWorld().field_73012_v.nextFloat() * 0.1f, 0.0f, 0.5f + this.getWorld().field_73012_v.nextFloat() * 0.1f);
            fb2.setAlphaF(0.75f, 0.0f);
            fb2.setGridSize(16);
            fb2.setParticles(60 + this.getWorld().field_73012_v.nextInt(4), 1, 1);
            fb2.setScale(5.0f, 10.0f + this.getWorld().field_73012_v.nextFloat() * 4.0f);
            fb2.setLayer(0);
            fb2.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? (-2.0f - this.getWorld().field_73012_v.nextFloat() * 2.0f) : (2.0f + this.getWorld().field_73012_v.nextFloat() * 2.0f));
            ParticleEngine.addEffect(this.getWorld(), fb2);
        }
    }
    
    public void pechsCurseTick(final double posX, final double posY, final double posZ) {
        final FXGeneric fb2 = new FXGeneric(this.getWorld(), posX, posY, posZ, 0.0, 0.0, 0.0);
        fb2.setAngles(90.0f * (float)this.getWorld().field_73012_v.nextGaussian(), 90.0f * (float)this.getWorld().field_73012_v.nextGaussian());
        fb2.func_187114_a(50 + this.getWorld().field_73012_v.nextInt(50));
        fb2.setRBGColorF(0.9f, 0.1f, 0.5f, 0.1f + this.getWorld().field_73012_v.nextFloat() * 0.1f, 0.0f, 0.5f + this.getWorld().field_73012_v.nextFloat() * 0.1f);
        fb2.setAlphaF(0.75f, 0.0f);
        fb2.setGridSize(8);
        fb2.setParticles(28 + this.getWorld().field_73012_v.nextInt(4), 1, 1);
        fb2.setScale(3.0f, 5.0f + this.getWorld().field_73012_v.nextFloat() * 2.0f);
        fb2.setLayer(0);
        fb2.setRotationSpeed(this.getWorld().field_73012_v.nextFloat(), this.getWorld().field_73012_v.nextBoolean() ? (-3.0f - this.getWorld().field_73012_v.nextFloat() * 3.0f) : (3.0f + this.getWorld().field_73012_v.nextFloat() * 3.0f));
        ParticleEngine.addEffect(this.getWorld(), fb2);
        this.drawWispyMotes(posX, posY, posZ, 0.0, 0.0, 0.0, 10 + this.getWorld().field_73012_v.nextInt(10), -0.01f);
    }
    
    public void scanHighlight(final BlockPos p) {
        AxisAlignedBB bb = this.getWorld().func_180495_p(p).func_185900_c((IBlockAccess)this.getWorld(), p);
        bb = bb.func_186670_a(p);
        this.scanHighlight(bb);
    }
    
    public void scanHighlight(final Entity e) {
        final AxisAlignedBB bb = e.func_174813_aQ();
        this.scanHighlight(bb);
    }
    
    public void scanHighlight(final AxisAlignedBB bb) {
        final int num = MathHelper.func_76143_f(bb.func_72320_b() * 2.0);
        final double ax = (bb.field_72340_a + bb.field_72336_d) / 2.0;
        final double ay = (bb.field_72338_b + bb.field_72337_e) / 2.0;
        final double az = (bb.field_72339_c + bb.field_72334_f) / 2.0;
        for (final EnumFacing face : EnumFacing.values()) {
            final double mx = 0.5 + face.func_82601_c() * 0.51;
            final double my = 0.5 + face.func_96559_d() * 0.51;
            final double mz = 0.5 + face.func_82599_e() * 0.51;
            for (int a = 0; a < num * 2; ++a) {
                double x = mx;
                double y = my;
                double z = mz;
                x += this.getWorld().field_73012_v.nextGaussian() * (bb.field_72336_d - bb.field_72340_a);
                y += this.getWorld().field_73012_v.nextGaussian() * (bb.field_72337_e - bb.field_72338_b);
                z += this.getWorld().field_73012_v.nextGaussian() * (bb.field_72334_f - bb.field_72339_c);
                x = MathHelper.func_151237_a(x, bb.field_72340_a - ax, bb.field_72336_d - ax);
                y = MathHelper.func_151237_a(y, bb.field_72338_b - ay, bb.field_72337_e - ay);
                z = MathHelper.func_151237_a(z, bb.field_72339_c - az, bb.field_72334_f - az);
                final float r = MathHelper.func_76136_a(this.getWorld().field_73012_v, 16, 32) / 255.0f;
                final float g = MathHelper.func_76136_a(this.getWorld().field_73012_v, 132, 165) / 255.0f;
                final float b = MathHelper.func_76136_a(this.getWorld().field_73012_v, 223, 239) / 255.0f;
                this.drawSimpleSparkle(this.getWorld().field_73012_v, ax + x, ay + y, az + z, 0.0, 0.0, 0.0, 0.4f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f, r, g, b, this.getWorld().field_73012_v.nextInt(10), 1.0f, 0.0f, true, 4);
            }
        }
    }
    
    public void sparkle(final float x, final float y, final float z, final float r, final float g, final float b) {
        if (this.getWorld().field_73012_v.nextInt(6) < 4) {
            this.drawGenericParticles(x, y, z, 0.0, 0.0, 0.0, r, g, b, 0.9f, true, 320, 16, 1, 6 + this.getWorld().field_73012_v.nextInt(4), 0, 0.6f + this.getWorld().field_73012_v.nextFloat() * 0.2f, 0.0f, 0);
        }
    }
    
    public void visSparkle(final int x, final int y, final int z, final int x2, final int y2, final int z2, final int color) {
        final FXVisSparkle fb = new FXVisSparkle(this.getWorld(), x + this.getWorld().field_73012_v.nextFloat(), y + this.getWorld().field_73012_v.nextFloat(), z + this.getWorld().field_73012_v.nextFloat(), x2 + 0.4 + this.getWorld().field_73012_v.nextFloat() * 0.2f, y2 + 0.4 + this.getWorld().field_73012_v.nextFloat() * 0.2f, z2 + 0.4 + this.getWorld().field_73012_v.nextFloat() * 0.2f);
        final Color c = new Color(color);
        fb.func_70538_b(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void splooshFX(final Entity e) {
        final float f = this.getWorld().field_73012_v.nextFloat() * 3.1415927f * 2.0f;
        final float f2 = this.getWorld().field_73012_v.nextFloat() * 0.5f + 0.5f;
        final float f3 = MathHelper.func_76126_a(f) * 2.0f * 0.5f * f2;
        final float f4 = MathHelper.func_76134_b(f) * 2.0f * 0.5f * f2;
        final FXBreakingFade fx = new FXBreakingFade(this.getWorld(), e.field_70165_t + f3, e.field_70163_u + this.getWorld().field_73012_v.nextFloat() * e.field_70131_O, e.field_70161_v + f4, Items.field_151123_aH, 0);
        if (this.getWorld().field_73012_v.nextBoolean()) {
            fx.func_70538_b(0.6f, 0.0f, 0.3f);
            fx.func_82338_g(0.4f);
        }
        else {
            fx.func_70538_b(0.3f, 0.0f, 0.3f);
            fx.func_82338_g(0.6f);
        }
        fx.setParticleMaxAge((int)(66.0f / (this.getWorld().field_73012_v.nextFloat() * 0.9f + 0.1f)));
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fx);
    }
    
    public void taintsplosionFX(final Entity e) {
        final FXBreakingFade fx = new FXBreakingFade(this.getWorld(), e.field_70165_t, e.field_70163_u + this.getWorld().field_73012_v.nextFloat() * e.field_70131_O, e.field_70161_v, Items.field_151123_aH);
        if (this.getWorld().field_73012_v.nextBoolean()) {
            fx.func_70538_b(0.6f, 0.0f, 0.3f);
            fx.func_82338_g(0.4f);
        }
        else {
            fx.func_70538_b(0.3f, 0.0f, 0.3f);
            fx.func_82338_g(0.6f);
        }
        fx.setSpeed(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0);
        fx.boom();
        fx.setParticleMaxAge((int)(66.0f / (this.getWorld().field_73012_v.nextFloat() * 0.9f + 0.1f)));
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fx);
    }
    
    public void tentacleAriseFX(final Entity e) {
        for (int j = 0; j < 2.0f * e.field_70131_O; ++j) {
            float f = this.getWorld().field_73012_v.nextFloat() * 3.1415927f * e.field_70131_O;
            float f2 = this.getWorld().field_73012_v.nextFloat() * 0.5f + 0.5f;
            float f3 = MathHelper.func_76126_a(f) * e.field_70131_O * 0.25f * f2;
            float f4 = MathHelper.func_76134_b(f) * e.field_70131_O * 0.25f * f2;
            final FXBreakingFade fx = new FXBreakingFade(this.getWorld(), e.field_70165_t + f3, e.field_70163_u, e.field_70161_v + f4, Items.field_151123_aH);
            fx.func_70538_b(0.4f, 0.0f, 0.4f);
            fx.func_82338_g(0.5f);
            fx.setParticleMaxAge((int)(66.0f / (this.getWorld().field_73012_v.nextFloat() * 0.9f + 0.1f)));
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fx);
            if (!this.getWorld().func_175623_d(e.func_180425_c().func_177977_b())) {
                f = this.getWorld().field_73012_v.nextFloat() * 3.1415927f * e.field_70131_O;
                f2 = this.getWorld().field_73012_v.nextFloat() * 0.5f + 0.5f;
                f3 = MathHelper.func_76126_a(f) * e.field_70131_O * 0.25f * f2;
                f4 = MathHelper.func_76134_b(f) * e.field_70131_O * 0.25f * f2;
                this.getWorld().func_175688_a(EnumParticleTypes.BLOCK_CRACK, e.field_70165_t + f3, e.field_70163_u, e.field_70161_v + f4, 0.0, 0.0, 0.0, new int[] { Block.func_176210_f(this.getWorld().func_180495_p(e.func_180425_c().func_177977_b())) });
            }
        }
    }
    
    public void slimeJumpFX(final Entity e, final int i) {
        final float f = this.getWorld().field_73012_v.nextFloat() * 3.1415927f * 2.0f;
        final float f2 = this.getWorld().field_73012_v.nextFloat() * 0.5f + 0.5f;
        final float f3 = MathHelper.func_76126_a(f) * i * 0.5f * f2;
        final float f4 = MathHelper.func_76134_b(f) * i * 0.5f * f2;
        final FXBreakingFade fx = new FXBreakingFade(this.getWorld(), e.field_70165_t + f3, (e.func_174813_aQ().field_72338_b + e.func_174813_aQ().field_72337_e) / 2.0, e.field_70161_v + f4, Items.field_151123_aH, 0);
        fx.func_70538_b(0.7f, 0.0f, 1.0f);
        fx.func_82338_g(0.4f);
        fx.setParticleMaxAge((int)(66.0f / (this.getWorld().field_73012_v.nextFloat() * 0.9f + 0.1f)));
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fx);
    }
    
    public void taintLandFX(final Entity e) {
        final float f = this.getWorld().field_73012_v.nextFloat() * 3.1415927f * 2.0f;
        final float f2 = this.getWorld().field_73012_v.nextFloat() * 0.5f + 0.5f;
        final float f3 = MathHelper.func_76126_a(f) * 2.0f * 0.5f * f2;
        final float f4 = MathHelper.func_76134_b(f) * 2.0f * 0.5f * f2;
        if (this.getWorld().field_72995_K) {
            final FXBreakingFade fx = new FXBreakingFade(this.getWorld(), e.field_70165_t + f3, (e.func_174813_aQ().field_72338_b + e.func_174813_aQ().field_72337_e) / 2.0, e.field_70161_v + f4, Items.field_151123_aH);
            fx.func_70538_b(0.1f, 0.0f, 0.1f);
            fx.func_82338_g(0.4f);
            fx.setParticleMaxAge((int)(66.0f / (this.getWorld().field_73012_v.nextFloat() * 0.9f + 0.1f)));
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fx);
        }
    }
    
    public void drawInfusionParticles1(final double x, final double y, final double z, final BlockPos pos, final Item id, final int md) {
        final FXBoreParticles fb = new FXBoreParticles(this.getWorld(), x, y, z, pos.func_177958_n() + 0.5, pos.func_177956_o() - 0.5, pos.func_177952_p() + 0.5, (float)this.getWorld().field_73012_v.nextGaussian() * 0.03f, (float)this.getWorld().field_73012_v.nextGaussian() * 0.03f, (float)this.getWorld().field_73012_v.nextGaussian() * 0.03f, id, md).getObjectColor(pos);
        fb.func_82338_g(0.3f);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb);
    }
    
    public void drawInfusionParticles2(final double x, final double y, final double z, final BlockPos pos, final IBlockState id, final int md) {
        final FXBoreParticles fb = new FXBoreParticles(this.getWorld(), x, y, z, pos.func_177958_n() + 0.5, pos.func_177956_o() - 0.5, pos.func_177952_p() + 0.5, id, md).getObjectColor(pos);
        fb.func_82338_g(0.3f);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb);
    }
    
    public void drawInfusionParticles3(final double x, final double y, final double z, final int x2, final int y2, final int z2) {
        final FXBoreSparkle fb = new FXBoreSparkle(this.getWorld(), x, y, z, x2 + 0.5, y2 - 0.5, z2 + 0.5);
        fb.func_70538_b(0.4f + this.getWorld().field_73012_v.nextFloat() * 0.2f, 0.2f, 0.6f + this.getWorld().field_73012_v.nextFloat() * 0.3f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawInfusionParticles4(final double x, final double y, final double z, final int x2, final int y2, final int z2) {
        final FXBoreSparkle fb = new FXBoreSparkle(this.getWorld(), x, y, z, x2 + 0.5, y2 - 0.5, z2 + 0.5);
        fb.func_70538_b(0.2f, 0.6f + this.getWorld().field_73012_v.nextFloat() * 0.3f, 0.3f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawVentParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color) {
        final FXVent fb = new FXVent(this.getWorld(), x, y, z, x2, y2, z2, color);
        fb.func_82338_g(0.4f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawVentParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color, final float scale) {
        final FXVent fb = new FXVent(this.getWorld(), x, y, z, x2, y2, z2, color);
        fb.func_82338_g(0.4f);
        fb.setScale(scale);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawVentParticles2(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color, final float scale) {
        final FXVent2 fb = new FXVent2(this.getWorld(), x, y, z, x2, y2, z2, color);
        fb.func_82338_g(0.4f);
        fb.setScale(scale);
        ParticleEngine.addEffect(this.getWorld(), fb);
        if (this.getWorld().field_73012_v.nextInt(6) < 2) {
            this.drawGenericParticles(x, y, z, x2 / 2.0, y2 / 2.0, z2 / 2.0, 1.0f, 0.7f, 0.2f, 0.9f, true, 320, 16, 1, 10 + this.getWorld().field_73012_v.nextInt(4), 0, 0.25f + this.getWorld().field_73012_v.nextFloat() * 0.1f, 0.0f, 0);
        }
    }
    
    public void spark(final double d, final double e, final double f, final float size, final float r, final float g, final float b, final float a) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), d, e, f, 0.0, 0.0, 0.0);
        fb.func_187114_a(5 + this.getWorld().field_73012_v.nextInt(5));
        fb.func_82338_g(a);
        fb.func_70538_b(r, g, b);
        fb.setGridSize(16);
        fb.setParticles(8 + this.getWorld().field_73012_v.nextInt(3) * 16, 8, 1);
        fb.setScale(size);
        fb.setFlipped(this.getWorld().field_73012_v.nextBoolean());
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void smokeSpiral(final double x, final double y, final double z, final float rad, final int start, final int miny, final int color) {
        final FXSmokeSpiral fx = new FXSmokeSpiral(this.getWorld(), x, y, z, rad, start, miny);
        final Color c = new Color(color);
        fx.func_70538_b(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
        ParticleEngine.addEffect(this.getWorld(), fx);
    }
    
    public void wispFXEG(final double posX, final double posY, final double posZ, final Entity target) {
        for (int a = 0; a < 2; ++a) {
            final FXWispEG ef = new FXWispEG(this.getWorld(), posX, posY, posZ, target);
            ParticleEngine.addEffect(this.getWorld(), ef);
        }
    }
    
    public void burst(final double sx, final double sy, final double sz, final float size) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), sx, sy, sz, 0.0, 0.0, 0.0);
        fb.func_187114_a(31);
        fb.setGridSize(16);
        fb.setParticles(208, 31, 1);
        fb.setScale(size);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void excavateFX(final BlockPos pos, final EntityLivingBase p, final int progress) {
        final RenderGlobal rg = Minecraft.func_71410_x().field_71438_f;
        rg.func_180441_b(p.func_145782_y(), pos, progress);
    }
    
    public Object beamCont(final EntityLivingBase p, final double tx, final double ty, final double tz, final int type, final int color, final boolean reverse, final float endmod, final Object input, final int impact) {
        FXBeamWand beamcon = null;
        final Color c = new Color(color);
        if (input instanceof FXBeamWand) {
            beamcon = (FXBeamWand)input;
        }
        if (beamcon == null || !beamcon.func_187113_k()) {
            beamcon = new FXBeamWand(this.getWorld(), p, tx, ty, tz, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 8);
            beamcon.setType(type);
            beamcon.setEndMod(endmod);
            beamcon.setReverse(reverse);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)beamcon);
        }
        else {
            beamcon.updateBeam(tx, ty, tz);
            beamcon.setEndMod(endmod);
            beamcon.impact = impact;
        }
        return beamcon;
    }
    
    public Object beamBore(final double px, final double py, final double pz, final double tx, final double ty, final double tz, final int type, final int color, final boolean reverse, final float endmod, final Object input, final int impact) {
        FXBeamBore beamcon = null;
        final Color c = new Color(color);
        if (input instanceof FXBeamBore) {
            beamcon = (FXBeamBore)input;
        }
        if (beamcon == null || !beamcon.func_187113_k()) {
            beamcon = new FXBeamBore(this.getWorld(), px, py, pz, tx, ty, tz, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 8);
            beamcon.setType(type);
            beamcon.setEndMod(endmod);
            beamcon.setReverse(reverse);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)beamcon);
        }
        else {
            beamcon.updateBeam(px, py, pz, tx, ty, tz);
            beamcon.setEndMod(endmod);
            beamcon.impact = impact;
        }
        return beamcon;
    }
    
    public void boreDigFx(final int x, final int y, final int z, final Entity e, final IBlockState bi, final int md, final int delay) {
        final float p = 50.0f;
        for (int a = 0; a < p / delay; ++a) {
            if (this.getWorld().field_73012_v.nextInt(4) == 0) {
                final FXBoreSparkle fb = new FXBoreSparkle(this.getWorld(), x + this.getWorld().field_73012_v.nextFloat(), y + this.getWorld().field_73012_v.nextFloat(), z + this.getWorld().field_73012_v.nextFloat(), e);
                ParticleEngine.addEffect(this.getWorld(), fb);
            }
            else {
                final FXBoreParticles fb2 = new FXBoreParticles(this.getWorld(), x + this.getWorld().field_73012_v.nextFloat(), y + this.getWorld().field_73012_v.nextFloat(), z + this.getWorld().field_73012_v.nextFloat(), e.field_70165_t, e.field_70163_u, e.field_70161_v, bi, md);
                fb2.setTarget(e);
                FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb2);
            }
        }
    }
    
    public void essentiaTrailFx(final BlockPos p1, final BlockPos p2, final int count, final int color, final float scale, final int ext) {
        final FXEssentiaStream fb = new FXEssentiaStream(this.getWorld(), p1.func_177958_n() + 0.5, p1.func_177956_o() + 0.5, p1.func_177952_p() + 0.5, p2.func_177958_n() + 0.5, p2.func_177956_o() + 0.5, p2.func_177952_p() + 0.5, count, color, scale, ext, 0.0);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void boreTrailFx(final BlockPos p1, final Entity e, final int count, final int color, final float scale, final int ext) {
        final FXBoreStream fb = new FXBoreStream(this.getWorld(), p1.func_177958_n() + 0.5, p1.func_177956_o() + 0.5, p1.func_177952_p() + 0.5, e, count, color, scale, ext, 0.0);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void essentiaDropFx(final double x, final double y, final double z, final float r, final float g, final float b, final float alpha) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, this.getWorld().field_73012_v.nextGaussian() * 0.004999999888241291, this.getWorld().field_73012_v.nextGaussian() * 0.004999999888241291, this.getWorld().field_73012_v.nextGaussian() * 0.004999999888241291);
        fb.func_187114_a(20 + this.getWorld().field_73012_v.nextInt(10));
        fb.func_70538_b(r, g, b);
        fb.func_82338_g(alpha);
        fb.setLoop(false);
        fb.setParticles(25, 1, 1);
        fb.setScale(0.4f + this.getWorld().field_73012_v.nextFloat() * 0.2f, 0.2f);
        fb.setLayer(1);
        fb.setGravity(0.01f);
        fb.setRotationSpeed(0.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void jarSplashFx(final double x, final double y, final double z) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x + this.getWorld().field_73012_v.nextGaussian() * 0.10000000149011612, y, z + this.getWorld().field_73012_v.nextGaussian() * 0.10000000149011612, this.getWorld().field_73012_v.nextGaussian() * 0.014999999664723873, this.getWorld().field_73012_v.nextFloat() * 0.05f, this.getWorld().field_73012_v.nextGaussian() * 0.014999999664723873);
        fb.func_187114_a(20 + this.getWorld().field_73012_v.nextInt(10));
        final Color c = new Color(2650102);
        fb.func_70538_b(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
        fb.func_82338_g(0.5f);
        fb.setLoop(false);
        fb.setParticles(25, 1, 1);
        fb.setScale(0.4f + this.getWorld().field_73012_v.nextFloat() * 0.2f);
        fb.setLayer(1);
        fb.setGravity(0.1f);
        fb.setRotationSpeed(0.0f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void waterTrailFx(final BlockPos p1, final BlockPos p2, final int count, final int color, final float scale) {
        final FXEssentiaStream fb = new FXEssentiaStream(this.getWorld(), p1.func_177958_n() + 0.5, p1.func_177956_o() + 0.66, p1.func_177952_p() + 0.5, p2.func_177958_n() + 0.5, p2.func_177956_o() + 0.5, p2.func_177952_p() + 0.5, count, color, scale, 0, 0.2);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void furnaceLavaFx(final int x, final int y, final int z, final int facingX, final int facingZ) {
        final float qx = (facingX == 0) ? ((this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.5f) : (facingX * this.getWorld().field_73012_v.nextFloat());
        final float qz = (facingZ == 0) ? ((this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.5f) : (facingZ * this.getWorld().field_73012_v.nextFloat());
        final Particle fb = new ParticleLava.Factory().func_178902_a(0, this.getWorld(), (double)(x + 0.5f + (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.3f + facingX * 1.0f), (double)(y + 0.3f), (double)(z + 0.5f + (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 0.3f + facingZ * 1.0f), (double)(0.15f * qx), (double)(0.2f * this.getWorld().field_73012_v.nextFloat()), (double)(0.15f * qz), new int[0]);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a(fb);
    }
    
    public void blockRunes(final double x, final double y, final double z, final float r, final float g, final float b, final int dur, final float grav) {
        final FXBlockRunes fb = new FXBlockRunes(this.getWorld(), x + 0.5, y + 0.5, z + 0.5, r, g, b, dur);
        fb.setGravity(grav);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawSlash(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int dur) {
        final FXPlane fb = new FXPlane(this.getWorld(), x, y, z, x2, y2, z2, dur);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void blockWard(final double x, final double y, final double z, final EnumFacing side, final float f, final float f1, final float f2) {
        final FXBlockWard fb = new FXBlockWard(this.getWorld(), x + 0.5, y + 0.5, z + 0.5, side, f, f1, f2);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb);
    }
    
    public Object swarmParticleFX(final Entity targetedEntity, final float f1, final float f2, final float pg) {
        final FXSwarm fx = new FXSwarm(this.getWorld(), targetedEntity.field_70165_t + (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 2.0f, targetedEntity.field_70163_u + (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 2.0f, targetedEntity.field_70161_v + (this.getWorld().field_73012_v.nextFloat() - this.getWorld().field_73012_v.nextFloat()) * 2.0f, targetedEntity, 0.8f + this.getWorld().field_73012_v.nextFloat() * 0.2f, this.getWorld().field_73012_v.nextFloat() * 0.4f, 1.0f - this.getWorld().field_73012_v.nextFloat() * 0.2f, f1, f2, pg);
        ParticleEngine.addEffect(this.getWorld(), fx);
        return fx;
    }
    
    public void bottleTaintBreak(final double x, final double y, final double z) {
        for (int k1 = 0; k1 < 8; ++k1) {
            this.getWorld().func_175688_a(EnumParticleTypes.ITEM_CRACK, x, y, z, this.getWorld().field_73012_v.nextGaussian() * 0.15, this.getWorld().field_73012_v.nextDouble() * 0.2, this.getWorld().field_73012_v.nextGaussian() * 0.15, new int[] { Item.func_150891_b(ItemsTC.bottleTaint) });
        }
        this.getWorld().func_184134_a(x, y, z, SoundEvents.field_187825_fO, SoundCategory.NEUTRAL, 1.0f, this.getWorld().field_73012_v.nextFloat() * 0.1f + 0.9f, false);
    }
    
    public void arcLightning(final double x, final double y, final double z, final double tx, final double ty, final double tz, final float r, final float g, final float b, final float h) {
        final FXArc efa = new FXArc(this.getWorld(), x, y, z, tx, ty, tz, r, g, b, h);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)efa);
    }
    
    public void arcBolt(final double x, final double y, final double z, final double tx, final double ty, final double tz, final float r, final float g, final float b, final float width) {
        final FXBolt efa = new FXBolt(this.getWorld(), x, y, z, tx, ty, tz, r, g, b, width);
        FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)efa);
    }
    
    public void cultistSpawn(final double x, final double y, final double z, final double a, final double b, final double c) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, a, b, c);
        fb.func_187114_a(10 + this.getWorld().field_73012_v.nextInt(10));
        fb.setRBGColorF(1.0f, 1.0f, 1.0f, 0.6f, 0.0f, 0.0f);
        fb.func_82338_g(0.8f);
        fb.setGridSize(16);
        fb.setParticles(160, 6, 1);
        fb.setScale(3.0f + this.getWorld().field_73012_v.nextFloat() * 2.0f);
        fb.setLayer(1);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawWispyMotesEntity(final double x, final double y, final double z, final Entity e, final float r, final float g, final float b) {
        final FXGenericP2E fb = new FXGenericP2E(this.getWorld(), x, y, z, e);
        fb.func_70538_b(r, g, b);
        fb.func_82338_g(0.6f);
        fb.setParticles(512, 16, 1);
        fb.setLoop(true);
        fb.setWind(0.001);
        fb.setRandomMovementScale(0.0025f, 0.0f, 0.0025f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawWispParticles(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color, final int a) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.func_187114_a(10 + this.getWorld().field_73012_v.nextInt(5));
        final Color c = new Color(color);
        fb.func_70538_b(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
        fb.func_82338_g(0.5f);
        fb.setLoop(true);
        fb.setGridSize(64);
        fb.setParticles(264, 8, 1);
        fb.setScale(1.0f + this.getWorld().field_73012_v.nextFloat() * 0.25f, 0.05f);
        fb.setWind(2.5E-4);
        fb.setRandomMovementScale(0.0025f, 0.0f, 0.0025f);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, a);
    }
    
    public void drawNitorCore(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.func_187114_a(10);
        fb.func_70538_b(1.0f, 1.0f, 1.0f);
        fb.func_82338_g(1.0f);
        fb.setParticles(457, 1, 1);
        fb.setScale(1.0f, 1.0f + (float)this.getWorld().field_73012_v.nextGaussian() * 0.1f, 1.0f);
        fb.setLayer(1);
        fb.setRandomMovementScale(2.0E-4f, 2.0E-4f, 2.0E-4f);
        ParticleEngine.addEffect(this.getWorld(), fb);
    }
    
    public void drawNitorFlames(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color, final int a) {
        final FXGeneric fb = new FXGeneric(this.getWorld(), x, y, z, x2, y2, z2);
        fb.func_187114_a(10 + this.getWorld().field_73012_v.nextInt(5));
        final Color c = new Color(color);
        fb.func_70538_b(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
        fb.func_82338_g(0.66f);
        fb.setLoop(true);
        fb.setGridSize(64);
        fb.setParticles(264, 8, 1);
        fb.setScale(3.0f + this.getWorld().field_73012_v.nextFloat(), 0.05f);
        fb.setRandomMovementScale(0.0025f, 0.0f, 0.0025f);
        ParticleEngine.addEffectWithDelay(this.getWorld(), fb, a);
    }
    
    static {
        FXDispatcher.INSTANCE = new FXDispatcher();
        FXDispatcher.q = 0;
    }
    
    public static class GenPart
    {
        public int grid;
        public int age;
        public float redStart;
        public float greenStart;
        public float blueStart;
        public float redEnd;
        public float greenEnd;
        public float blueEnd;
        public float[] alpha;
        public float[] scale;
        public float rot;
        public float rotstart;
        public boolean loop;
        public int partStart;
        public int partNum;
        public int partInc;
        public int layer;
        public double slowDown;
        public float grav;
        public int delay;
        
        public GenPart() {
            this.grid = 64;
            this.age = 0;
            this.redStart = 1.0f;
            this.greenStart = 1.0f;
            this.blueStart = 1.0f;
            this.redEnd = 1.0f;
            this.greenEnd = 1.0f;
            this.blueEnd = 1.0f;
            this.alpha = new float[] { 1.0f };
            this.scale = new float[] { 1.0f };
            this.rotstart = 0.0f;
            this.loop = false;
            this.partStart = 0;
            this.partNum = 1;
            this.partInc = 1;
            this.layer = 0;
            this.slowDown = 0.9800000190734863;
            this.grav = 0.0f;
            this.delay = 0;
        }
    }
}
