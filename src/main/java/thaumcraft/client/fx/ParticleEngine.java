package thaumcraft.client.fx;

import net.minecraftforge.fml.common.*;
import net.minecraft.world.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.crash.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraftforge.fml.client.*;
import java.util.concurrent.*;
import java.util.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class ParticleEngine
{
    public static final ResourceLocation particleTexture;
    protected World world;
    private static HashMap<Integer, ArrayList<Particle>>[] particles;
    private static ArrayList<ParticleDelay> particlesDelayed;
    private Random rand;
    
    public ParticleEngine() {
        this.rand = new Random();
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderTick(final TickEvent.RenderTickEvent event) {
        if (Minecraft.func_71410_x().field_71441_e == null) {
            return;
        }
        if (event.phase == TickEvent.Phase.END) {
            final float frame = event.renderTickTime;
            final Entity entity = (Entity)Minecraft.func_71410_x().field_71439_g;
            final TextureManager renderer = Minecraft.func_71410_x().field_71446_o;
            final int dim = Minecraft.func_71410_x().field_71441_e.field_73011_w.getDimension();
            GL11.glPushMatrix();
            final ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, sr.func_78327_c(), sr.func_78324_d(), 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179147_l();
            GL11.glEnable(3042);
            GL11.glAlphaFunc(516, 0.003921569f);
            renderer.func_110577_a(ParticleEngine.particleTexture);
            GlStateManager.func_179132_a(false);
            for (int layer = 5; layer >= 4; --layer) {
                if (ParticleEngine.particles[layer].containsKey(dim)) {
                    final ArrayList<Particle> parts = ParticleEngine.particles[layer].get(dim);
                    if (parts.size() != 0) {
                        switch (layer) {
                            case 4: {
                                GlStateManager.func_179112_b(770, 1);
                                break;
                            }
                            case 5: {
                                GlStateManager.func_179112_b(770, 771);
                                break;
                            }
                        }
                        final Tessellator tessellator = Tessellator.func_178181_a();
                        final BufferBuilder buffer = tessellator.func_178180_c();
                        for (int j = 0; j < parts.size(); ++j) {
                            final Particle Particle = parts.get(j);
                            if (Particle != null) {
                                try {
                                    Particle.func_180434_a(buffer, entity, frame, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                                }
                                catch (Throwable throwable) {
                                    final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Rendering Particle");
                                    final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Particle being rendered");
                                    crashreportcategory.func_189529_a("Particle", (ICrashReportDetail)new ICrashReportDetail<String>() {
                                        public String call() {
                                            return Particle.toString();
                                        }
                                    });
                                    crashreportcategory.func_189529_a("Particle Type", (ICrashReportDetail)new ICrashReportDetail<String>() {
                                        public String call() {
                                            return "ENTITY_PARTICLE_TEXTURE";
                                        }
                                    });
                                    throw new ReportedException(crashreport);
                                }
                            }
                        }
                    }
                }
            }
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179112_b(770, 771);
            GlStateManager.func_179084_k();
            GlStateManager.func_179092_a(516, 0.1f);
            GL11.glPopMatrix();
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderWorldLast(final RenderWorldLastEvent event) {
        final float frame = event.getPartialTicks();
        final Entity entity = (Entity)Minecraft.func_71410_x().field_71439_g;
        final TextureManager renderer = Minecraft.func_71410_x().field_71446_o;
        final int dim = Minecraft.func_71410_x().field_71441_e.field_73011_w.getDimension();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179147_l();
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.003921569f);
        renderer.func_110577_a(ParticleEngine.particleTexture);
        GlStateManager.func_179132_a(false);
        for (int layer = 3; layer >= 0; --layer) {
            if (ParticleEngine.particles[layer].containsKey(dim)) {
                final ArrayList<Particle> parts = ParticleEngine.particles[layer].get(dim);
                if (parts.size() != 0) {
                    switch (layer) {
                        case 0: {
                            GlStateManager.func_179112_b(770, 1);
                            break;
                        }
                        case 1: {
                            GlStateManager.func_179112_b(770, 771);
                            break;
                        }
                        case 2: {
                            GlStateManager.func_179112_b(770, 1);
                            GlStateManager.func_179097_i();
                            break;
                        }
                        case 3: {
                            GlStateManager.func_179112_b(770, 771);
                            GlStateManager.func_179097_i();
                            break;
                        }
                    }
                    final float f1 = ActiveRenderInfo.func_178808_b();
                    final float f2 = ActiveRenderInfo.func_178803_d();
                    final float f3 = ActiveRenderInfo.func_178805_e();
                    final float f4 = ActiveRenderInfo.func_178807_f();
                    final float f5 = ActiveRenderInfo.func_178809_c();
                    Particle.field_70556_an = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * frame;
                    Particle.field_70554_ao = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * frame;
                    Particle.field_70555_ap = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * frame;
                    final Tessellator tessellator = Tessellator.func_178181_a();
                    final BufferBuilder buffer = tessellator.func_178180_c();
                    buffer.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                    for (int j = 0; j < parts.size(); ++j) {
                        final Particle Particle = parts.get(j);
                        if (Particle != null) {
                            try {
                                Particle.func_180434_a(buffer, entity, frame, f1, f5, f2, f3, f4);
                            }
                            catch (Throwable throwable) {
                                final CrashReport crashreport = CrashReport.func_85055_a(throwable, "Rendering Particle");
                                final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Particle being rendered");
                                crashreportcategory.func_189529_a("Particle", (ICrashReportDetail)new ICrashReportDetail<String>() {
                                    public String call() {
                                        return Particle.toString();
                                    }
                                });
                                crashreportcategory.func_189529_a("Particle Type", (ICrashReportDetail)new ICrashReportDetail<String>() {
                                    public String call() {
                                        return "ENTITY_PARTICLE_TEXTURE";
                                    }
                                });
                                throw new ReportedException(crashreport);
                            }
                        }
                    }
                    tessellator.func_78381_a();
                    switch (layer) {
                        case 2:
                        case 3: {
                            GlStateManager.func_179126_j();
                            break;
                        }
                    }
                }
            }
        }
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179112_b(770, 771);
        GlStateManager.func_179084_k();
        GlStateManager.func_179092_a(516, 0.1f);
        GL11.glPopMatrix();
    }
    
    public static void addEffect(final World world, final Particle fx) {
        addEffect(world.field_73011_w.getDimension(), fx);
    }
    
    private static int getParticleLimit() {
        return (FMLClientHandler.instance().getClient().field_71474_y.field_74362_aa == 2) ? 500 : ((FMLClientHandler.instance().getClient().field_71474_y.field_74362_aa == 1) ? 1000 : 2000);
    }
    
    public static void addEffect(final int dim, final Particle fx) {
        if (Minecraft.func_71410_x().field_71441_e == null) {
            return;
        }
        int ps = FMLClientHandler.instance().getClient().field_71474_y.field_74362_aa;
        Minecraft.func_71410_x();
        if (Minecraft.func_175610_ah() < 30) {
            ++ps;
        }
        if (Minecraft.func_71410_x().field_71441_e.field_73012_v.nextInt(3) < ps) {
            return;
        }
        if (!ParticleEngine.particles[fx.func_70537_b()].containsKey(dim)) {
            ParticleEngine.particles[fx.func_70537_b()].put(dim, new ArrayList<Particle>());
        }
        final ArrayList<Particle> parts = ParticleEngine.particles[fx.func_70537_b()].get(dim);
        if (parts.size() >= getParticleLimit()) {
            parts.remove(0);
        }
        parts.add(fx);
        ParticleEngine.particles[fx.func_70537_b()].put(dim, parts);
    }
    
    public static void addEffectWithDelay(final World world, final Particle fx, final int delay) {
        ParticleEngine.particlesDelayed.add(new ParticleDelay(fx, world.field_73011_w.getDimension(), delay));
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void updateParticles(final TickEvent.ClientTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final World world = (World)mc.field_71441_e;
        if (mc.field_71441_e == null) {
            return;
        }
        final int dim = world.field_73011_w.getDimension();
        if (event.phase == TickEvent.Phase.START) {
            final Iterator<ParticleDelay> i = ParticleEngine.particlesDelayed.iterator();
            while (i.hasNext()) {
                final ParticleDelay pd = i.next();
                --pd.delay;
                if (pd.delay <= 0) {
                    if (pd.dim == dim) {
                        addEffect(pd.dim, pd.particle);
                    }
                    i.remove();
                }
            }
            for (int layer = 0; layer < 6; ++layer) {
                if (ParticleEngine.particles[layer].containsKey(dim)) {
                    final ArrayList<Particle> parts = ParticleEngine.particles[layer].get(dim);
                    for (int j = 0; j < parts.size(); ++j) {
                        final Particle Particle = parts.get(j);
                        try {
                            if (Particle != null) {
                                Particle.func_189213_a();
                            }
                        }
                        catch (Exception e) {
                            try {
                                final CrashReport crashreport = CrashReport.func_85055_a((Throwable)e, "Ticking Particle");
                                final CrashReportCategory crashreportcategory = crashreport.func_85058_a("Particle being ticked");
                                crashreportcategory.func_71507_a("Particle", (Object)new Callable() {
                                    @Override
                                    public String call() {
                                        return Particle.toString();
                                    }
                                });
                                crashreportcategory.func_71507_a("Particle Type", (Object)new Callable() {
                                    @Override
                                    public String call() {
                                        return "ENTITY_PARTICLE_TEXTURE";
                                    }
                                });
                                Particle.func_187112_i();
                            }
                            catch (Exception ex) {}
                        }
                        if (Particle == null || !Particle.func_187113_k()) {
                            parts.remove(j--);
                            ParticleEngine.particles[layer].put(dim, parts);
                        }
                    }
                }
            }
        }
    }
    
    static {
        particleTexture = new ResourceLocation("thaumcraft", "textures/misc/particles.png");
        ParticleEngine.particles = (HashMap<Integer, ArrayList<Particle>>[])new HashMap[] { new HashMap(), new HashMap(), new HashMap(), new HashMap(), new HashMap(), new HashMap() };
        ParticleEngine.particlesDelayed = new ArrayList<ParticleDelay>();
    }
    
    private static class ParticleDelay
    {
        Particle particle;
        int dim;
        int level;
        int delay;
        
        public ParticleDelay(final Particle particle, final int dim, final int delay) {
            this.dim = dim;
            this.particle = particle;
            this.delay = delay;
        }
    }
}
