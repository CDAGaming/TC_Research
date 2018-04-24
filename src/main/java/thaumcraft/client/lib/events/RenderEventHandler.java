package thaumcraft.client.lib.events;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.shader.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.client.*;
import thaumcraft.common.lib.events.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import thaumcraft.client.lib.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.items.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraft.util.text.*;
import org.lwjgl.opengl.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.api.golems.*;
import java.util.concurrent.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import net.minecraft.item.*;
import java.awt.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.util.math.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import thaumcraft.api.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraftforge.client.event.*;
import java.util.*;
import java.io.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class RenderEventHandler
{
    public static RenderEventHandler INSTANCE;
    @SideOnly(Side.CLIENT)
    public static HudHandler hudHandler;
    @SideOnly(Side.CLIENT)
    public static WandRenderingHandler wandHandler;
    @SideOnly(Side.CLIENT)
    static ShaderHandler shaderhandler;
    public static List blockTags;
    public static float tagscale;
    public static int tickCount;
    static boolean checkedDate;
    private Random random;
    public static boolean resetShaders;
    private static int oldDisplayWidth;
    private static int oldDisplayHeight;
    static final ResourceLocation CFRAME;
    static final ResourceLocation MIDDLE;
    static EnumFacing[][] rotfaces;
    static int[][] rotmat;
    public static HashMap<Integer, ShaderGroup> shaderGroups;
    public static boolean fogFiddled;
    public static float fogTarget;
    public static int fogDuration;
    public static float prevVignetteBrightness;
    public static float targetBrightness;
    protected static final ResourceLocation vignetteTexPath;
    
    public RenderEventHandler() {
        this.random = new Random();
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void playerTick(final TickEvent.PlayerTickEvent event) {
        final Minecraft mc = Minecraft.func_71410_x();
        if (event.side == Side.SERVER || event.player.func_145782_y() != mc.field_71439_g.func_145782_y()) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            try {
                RenderEventHandler.shaderhandler.checkShaders(event, mc);
                if (ShaderHandler.warpVignette > 0) {
                    --ShaderHandler.warpVignette;
                    RenderEventHandler.targetBrightness = 0.0f;
                }
                else {
                    RenderEventHandler.targetBrightness = 1.0f;
                }
                if (RenderEventHandler.fogFiddled) {
                    if (RenderEventHandler.fogDuration < 100) {
                        RenderEventHandler.fogTarget = 0.1f * (RenderEventHandler.fogDuration / 100.0f);
                    }
                    else if (RenderEventHandler.fogTarget < 0.1f) {
                        RenderEventHandler.fogTarget += 0.001f;
                    }
                    --RenderEventHandler.fogDuration;
                    if (RenderEventHandler.fogDuration < 0) {
                        RenderEventHandler.fogFiddled = false;
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void clientWorldTick(final TickEvent.ClientTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final World world = (World)mc.field_71441_e;
        if (event.phase == TickEvent.Phase.START) {
            ++RenderEventHandler.tickCount;
            for (final String fxk : EssentiaHandler.sourceFX.keySet().toArray(new String[0])) {
                final EssentiaHandler.EssentiaSourceFX fx = EssentiaHandler.sourceFX.get(fxk);
                if (world != null) {
                    int mod = 0;
                    final TileEntity tile = world.func_175625_s(fx.start);
                    if (tile != null && tile instanceof TileInfusionMatrix) {
                        mod = -1;
                    }
                    FXDispatcher.INSTANCE.essentiaTrailFx(fx.end, fx.start.func_177981_b(mod), RenderEventHandler.tickCount, fx.color, 0.1f, fx.ext);
                    EssentiaHandler.sourceFX.remove(fxk);
                }
            }
        }
        else {
            final LinkedBlockingQueue<HudHandler.KnowledgeGainTracker> temp = new LinkedBlockingQueue<HudHandler.KnowledgeGainTracker>();
            if (RenderEventHandler.hudHandler.knowledgeGainTrackers.isEmpty()) {
                if (RenderEventHandler.hudHandler.kgFade > 0.0f) {
                    final HudHandler hudHandler = RenderEventHandler.hudHandler;
                    --hudHandler.kgFade;
                }
            }
            else {
                final HudHandler hudHandler2 = RenderEventHandler.hudHandler;
                hudHandler2.kgFade += 10.0f;
                if (RenderEventHandler.hudHandler.kgFade > 40.0f) {
                    RenderEventHandler.hudHandler.kgFade = 40.0f;
                }
                while (!RenderEventHandler.hudHandler.knowledgeGainTrackers.isEmpty()) {
                    final HudHandler.KnowledgeGainTracker current = RenderEventHandler.hudHandler.knowledgeGainTrackers.poll();
                    if (current != null && current.progress > 0) {
                        final HudHandler.KnowledgeGainTracker knowledgeGainTracker = current;
                        --knowledgeGainTracker.progress;
                        temp.offer(current);
                    }
                }
                while (!temp.isEmpty()) {
                    RenderEventHandler.hudHandler.knowledgeGainTrackers.offer(temp.poll());
                }
            }
            if (mc.field_71441_e != null && !RenderEventHandler.checkedDate) {
                RenderEventHandler.checkedDate = true;
                final Calendar calendar = mc.field_71441_e.func_83015_S();
                if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
                    ModConfig.isHalloween = true;
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            UtilsFX.sysPartialTicks = event.renderTickTime;
        }
        else {
            final Minecraft mc = FMLClientHandler.instance().getClient();
            if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
                final long time = System.currentTimeMillis();
                if (player != null) {
                    RenderEventHandler.hudHandler.renderHuds(mc, event.renderTickTime, player, time);
                }
                if ((player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof IArchitect) || (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() instanceof IArchitect)) {
                    final ItemStack stack = (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof IArchitect) ? player.func_184614_ca() : player.func_184592_cb();
                    if (!((IArchitect)stack.func_77973_b()).useBlockHighlight(stack)) {
                        final RayTraceResult target2 = ((IArchitect)stack.func_77973_b()).getArchitectMOP(stack, player.field_70170_p, (EntityLivingBase)player);
                        if (target2 != null) {
                            RenderEventHandler.wandHandler.handleArchitectOverlay(stack, player, event.renderTickTime, player.field_70173_aa, target2);
                        }
                    }
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void tooltipEvent(final ItemTooltipEvent event) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final GuiScreen gui = mc.field_71462_r;
        if (gui instanceof GuiContainer && GuiScreen.func_146272_n() != ModConfig.CONFIG_GRAPHICS.showTags && !Mouse.isGrabbed()) {
            final AspectList tags = ThaumcraftCraftingManager.getObjectTags(event.getItemStack());
            int index = 0;
            if (tags.size() > 0) {
                for (final Aspect tag : tags.getAspects()) {
                    if (tag != null) {
                        ++index;
                    }
                }
            }
            final int width = index * 18;
            if (width > 0) {
                final double sw = mc.field_71466_p.func_78256_a(" ");
                final int t = MathHelper.func_76143_f(width / sw);
                final int l = MathHelper.func_76143_f(18.0 / mc.field_71466_p.field_78288_b);
                final String is = "\u00ef申l                                                                                                                 ";
                final StringBuilder sb = new StringBuilder(is);
                sb.setLength(t);
                sb.append(TextFormatting.RESET);
                for (int a = 0; a < l; ++a) {
                    event.getToolTip().add(sb.toString());
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void tooltipEvent(final RenderTooltipEvent.PostText event) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final GuiScreen gui = mc.field_71462_r;
        if (gui instanceof GuiContainer && GuiScreen.func_146272_n() != ModConfig.CONFIG_GRAPHICS.showTags && !Mouse.isGrabbed()) {
            int bot = event.getHeight();
            if (event.getLines().size() > 0) {
                for (int a = event.getLines().size() - 1; a >= 0; --a) {
                    if (event.getLines().get(a) != null && (!event.getLines().get(a).startsWith("\u00ef申7\u00ef申l  ") || !event.getLines().get(a).endsWith("\u00ef申r"))) {
                        bot -= 10;
                    }
                    else if (a > 0 && event.getLines().get(a - 1) != null && event.getLines().get(a - 1).startsWith("\u00ef申7\u00ef申l  ") && event.getLines().get(a).endsWith("\u00ef申r")) {
                        break;
                    }
                }
            }
            RenderEventHandler.hudHandler.renderAspectsInGui((GuiContainer)gui, (EntityPlayer)mc.field_71439_g, event.getStack(), bot, event.getX(), event.getY());
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderOverlay(final RenderGameOverlayEvent event) {
        final Minecraft mc = Minecraft.func_71410_x();
        final long time = System.nanoTime() / 1000000L;
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            RenderEventHandler.wandHandler.handleFociRadial(mc, time, event);
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
            renderVignette(RenderEventHandler.targetBrightness, event.getResolution().func_78327_c(), event.getResolution().func_78324_d());
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderShaders(final RenderGameOverlayEvent.Pre event) {
        if (!ModConfig.CONFIG_GRAPHICS.disableShaders && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            final Minecraft mc = Minecraft.func_71410_x();
            if (OpenGlHelper.field_148824_g && RenderEventHandler.shaderGroups.size() > 0) {
                updateShaderFrameBuffers(mc);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                for (final ShaderGroup sg : RenderEventHandler.shaderGroups.values()) {
                    GL11.glPushMatrix();
                    try {
                        sg.func_148018_a(event.getPartialTicks());
                    }
                    catch (Exception ex) {}
                    GL11.glPopMatrix();
                }
                mc.func_147110_a().func_147610_a(true);
            }
        }
    }
    
    private static void updateShaderFrameBuffers(final Minecraft mc) {
        if (RenderEventHandler.resetShaders || mc.field_71443_c != RenderEventHandler.oldDisplayWidth || RenderEventHandler.oldDisplayHeight != mc.field_71440_d) {
            for (final ShaderGroup sg : RenderEventHandler.shaderGroups.values()) {
                sg.func_148026_a(mc.field_71443_c, mc.field_71440_d);
            }
            RenderEventHandler.oldDisplayWidth = mc.field_71443_c;
            RenderEventHandler.oldDisplayHeight = mc.field_71440_d;
            RenderEventHandler.resetShaders = false;
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void blockHighlight(final DrawBlockHighlightEvent event) {
        final int ticks = event.getPlayer().field_70173_aa;
        final RayTraceResult target = event.getTarget();
        if (RenderEventHandler.blockTags.size() > 0) {
            final int x = RenderEventHandler.blockTags.get(0);
            final int y = RenderEventHandler.blockTags.get(1);
            final int z = RenderEventHandler.blockTags.get(2);
            final AspectList ot = RenderEventHandler.blockTags.get(3);
            final EnumFacing dir = EnumFacing.field_82609_l[RenderEventHandler.blockTags.get(4)];
            if (x == target.func_178782_a().func_177958_n() && y == target.func_178782_a().func_177956_o() && z == target.func_178782_a().func_177952_p()) {
                if (RenderEventHandler.tagscale < 0.5f) {
                    RenderEventHandler.tagscale += 0.031f - RenderEventHandler.tagscale / 10.0f;
                }
                drawTagsOnContainer(target.func_178782_a().func_177958_n() + dir.func_82601_c() / 2.0f, target.func_178782_a().func_177956_o() + dir.func_96559_d() / 2.0f, target.func_178782_a().func_177952_p() + dir.func_82599_e() / 2.0f, ot, 220, dir, event.getPartialTicks());
            }
        }
        if (target != null && target.func_178782_a() != null) {
            final TileEntity te = event.getPlayer().field_70170_p.func_175625_s(target.func_178782_a());
            if (te != null && te instanceof TileRedstoneRelay) {
                final RayTraceResult hit = RayTracer.retraceBlock(event.getPlayer().field_70170_p, event.getPlayer(), target.func_178782_a());
                if (hit != null) {
                    if (hit.subHit == 0) {
                        drawTextInAir(target.func_178782_a().func_177958_n(), target.func_178782_a().func_177956_o() + 0.3, target.func_178782_a().func_177952_p(), event.getPartialTicks(), "Out: " + ((TileRedstoneRelay)te).getOut());
                    }
                    else if (hit.subHit == 1) {
                        drawTextInAir(target.func_178782_a().func_177958_n(), target.func_178782_a().func_177956_o() + 0.3, target.func_178782_a().func_177952_p(), event.getPartialTicks(), "In: " + ((TileRedstoneRelay)te).getIn());
                    }
                }
            }
            if (EntityUtils.hasGoggles((Entity)event.getPlayer())) {
                final boolean spaceAbove = event.getPlayer().field_70170_p.func_175623_d(target.func_178782_a().func_177984_a());
                if (te != null) {
                    int note = -1;
                    if (te instanceof TileEntityNote) {
                        note = ((TileEntityNote)te).field_145879_a;
                    }
                    else if (te instanceof TileArcaneEar) {
                        note = ((TileArcaneEar)te).note;
                    }
                    else if (te instanceof IAspectContainer && ((IAspectContainer)te).getAspects() != null && ((IAspectContainer)te).getAspects().size() > 0) {
                        final float shift = 0.0f;
                        if (RenderEventHandler.tagscale < 0.3f) {
                            RenderEventHandler.tagscale += 0.031f - RenderEventHandler.tagscale / 10.0f;
                        }
                        drawTagsOnContainer(target.func_178782_a().func_177958_n(), target.func_178782_a().func_177956_o() + (spaceAbove ? 0.4f : 0.0f) + shift, target.func_178782_a().func_177952_p(), ((IAspectContainer)te).getAspects(), 220, spaceAbove ? EnumFacing.UP : event.getTarget().field_178784_b, event.getPartialTicks());
                    }
                    if (note >= 0) {
                        if (ticks % 5 == 0) {
                            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketNote(target.func_178782_a().func_177958_n(), target.func_178782_a().func_177956_o(), target.func_178782_a().func_177952_p(), event.getPlayer().field_70170_p.field_73011_w.getDimension()));
                        }
                        drawTextInAir(target.func_178782_a().func_177958_n(), target.func_178782_a().func_177956_o() + 1, target.func_178782_a().func_177952_p(), event.getPartialTicks(), "Note: " + note);
                    }
                }
            }
        }
        if (target.field_72313_a == RayTraceResult.Type.BLOCK && ((event.getPlayer().func_184614_ca() != null && event.getPlayer().func_184614_ca().func_77973_b() instanceof IArchitect) || (event.getPlayer().func_184592_cb() != null && event.getPlayer().func_184592_cb().func_77973_b() instanceof IArchitect))) {
            final ItemStack stack = (event.getPlayer().func_184614_ca() != null && event.getPlayer().func_184614_ca().func_77973_b() instanceof IArchitect) ? event.getPlayer().func_184614_ca() : event.getPlayer().func_184592_cb();
            if (((IArchitect)stack.func_77973_b()).useBlockHighlight(stack)) {
                final RayTraceResult target2 = ((IArchitect)stack.func_77973_b()).getArchitectMOP(stack, event.getPlayer().field_70170_p, (EntityLivingBase)event.getPlayer());
                if (target2 != null && RenderEventHandler.wandHandler.handleArchitectOverlay(stack, event.getPlayer(), event.getPartialTicks(), event.getPlayer().field_70173_aa, target2)) {
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderLast(final RenderWorldLastEvent event) {
        if (RenderEventHandler.tagscale > 0.0f) {
            RenderEventHandler.tagscale -= 0.005f;
        }
        final float partialTicks = event.getPartialTicks();
        final Minecraft mc = Minecraft.func_71410_x();
        if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)mc.func_175606_aa();
            if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof ISealDisplayer) {
                drawSeals(partialTicks, player);
            }
            else if (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() instanceof ISealDisplayer) {
                drawSeals(partialTicks, player);
            }
            if (player.func_184614_ca() != null && player.func_184614_ca().func_77973_b() instanceof IArchitect) {
                final RayTraceResult target = ((IArchitect)player.func_184614_ca().func_77973_b()).getArchitectMOP(player.func_184614_ca(), player.field_70170_p, (EntityLivingBase)player);
                RenderEventHandler.wandHandler.handleArchitectOverlay(player.func_184614_ca(), player, partialTicks, player.field_70173_aa, target);
            }
            else if (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() instanceof IArchitect) {
                final RayTraceResult target = ((IArchitect)player.func_184592_cb().func_77973_b()).getArchitectMOP(player.func_184592_cb(), player.field_70170_p, (EntityLivingBase)player);
                RenderEventHandler.wandHandler.handleArchitectOverlay(player.func_184592_cb(), player, partialTicks, player.field_70173_aa, target);
            }
        }
    }
    
    private static void drawSeals(final float partialTicks, final EntityPlayer player) {
        final ConcurrentHashMap<SealPos, SealEntity> seals = SealHandler.sealEntities.get(player.field_70170_p.field_73011_w.getDimension());
        if (seals != null && seals.size() > 0) {
            GL11.glPushMatrix();
            if (player.func_70093_af()) {
                GL11.glDisable(2929);
            }
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2884);
            final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
            final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
            final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
            GL11.glTranslated(-iPX, -iPY, -iPZ);
            for (final ISealEntity seal : seals.values()) {
                final double dis = player.func_174831_c(seal.getSealPos().pos);
                if (dis <= 256.0) {
                    final float alpha = 1.0f - (float)(dis / 256.0);
                    boolean ia = false;
                    if (seal.isStoppedByRedstone(player.field_70170_p)) {
                        ia = true;
                        if (player.field_70170_p.field_73012_v.nextFloat() < partialTicks / 12.0f) {
                            FXDispatcher.INSTANCE.spark(seal.getSealPos().pos.func_177958_n() + 0.5f + seal.getSealPos().face.func_82601_c() * 0.66f, seal.getSealPos().pos.func_177956_o() + 0.5f + seal.getSealPos().face.func_96559_d() * 0.66f, seal.getSealPos().pos.func_177952_p() + 0.5f + seal.getSealPos().face.func_82599_e() * 0.66f, 2.0f, 0.8f - player.field_70170_p.field_73012_v.nextFloat() * 0.2f, 0.0f, 0.0f, 1.0f);
                            ia = false;
                        }
                    }
                    renderSeal(seal.getSealPos().pos.func_177958_n(), seal.getSealPos().pos.func_177956_o(), seal.getSealPos().pos.func_177952_p(), alpha, seal.getSealPos().face, seal.getSeal().getSealIcon(), ia);
                    drawSealArea(player, seal, alpha, partialTicks);
                }
            }
            GL11.glDisable(3042);
            GL11.glEnable(2884);
            if (player.func_70093_af()) {
                GL11.glEnable(2929);
            }
            GL11.glPopMatrix();
        }
    }
    
    private static void drawSealArea(final EntityPlayer player, final ISealEntity seal, final float alpha, final float partialTicks) {
        GL11.glPushMatrix();
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        if (seal.getColor() > 0) {
            final Color c = new Color(EnumDyeColor.func_176764_b(seal.getColor() - 1).func_193350_e());
            r = c.getRed() / 255.0f;
            g = c.getGreen() / 255.0f;
            b = c.getBlue() / 255.0f;
        }
        else {
            r = 0.7f + MathHelper.func_76126_a((player.field_70173_aa + partialTicks + seal.getSealPos().pos.func_177958_n()) / 4.0f) * 0.1f;
            g = 0.7f + MathHelper.func_76126_a((player.field_70173_aa + partialTicks + seal.getSealPos().pos.func_177956_o()) / 5.0f) * 0.1f;
            b = 0.7f + MathHelper.func_76126_a((player.field_70173_aa + partialTicks + seal.getSealPos().pos.func_177952_p()) / 6.0f) * 0.1f;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(seal.getSealPos().pos.func_177958_n() + 0.5, seal.getSealPos().pos.func_177956_o() + 0.5, seal.getSealPos().pos.func_177952_p() + 0.5);
        GL11.glRotatef(90.0f, (float)(-seal.getSealPos().face.func_96559_d()), (float)seal.getSealPos().face.func_82601_c(), (float)(-seal.getSealPos().face.func_82599_e()));
        if (seal.getSealPos().face.func_82599_e() < 0) {
            GL11.glTranslated(0.0, 0.0, -0.5099999904632568);
        }
        else {
            GL11.glTranslated(0.0, 0.0, 0.5099999904632568);
        }
        GL11.glRotatef(player.field_70173_aa % 360 + partialTicks, 0.0f, 0.0f, 1.0f);
        UtilsFX.renderQuadCentered(RenderEventHandler.MIDDLE, 0.9f, r, g, b, 200, 771, alpha * 0.8f);
        GL11.glPopMatrix();
        if (seal.getSeal() instanceof ISealConfigArea) {
            GL11.glDepthMask(false);
            final AxisAlignedBB area = new AxisAlignedBB((double)seal.getSealPos().pos.func_177958_n(), (double)seal.getSealPos().pos.func_177956_o(), (double)seal.getSealPos().pos.func_177952_p(), (double)(seal.getSealPos().pos.func_177958_n() + 1), (double)(seal.getSealPos().pos.func_177956_o() + 1), (double)(seal.getSealPos().pos.func_177952_p() + 1)).func_72317_d((double)seal.getSealPos().face.func_82601_c(), (double)seal.getSealPos().face.func_96559_d(), (double)seal.getSealPos().face.func_82599_e()).func_72321_a((seal.getSealPos().face.func_82601_c() != 0) ? ((double)((seal.getArea().func_177958_n() - 1) * seal.getSealPos().face.func_82601_c())) : 0.0, (seal.getSealPos().face.func_96559_d() != 0) ? ((double)((seal.getArea().func_177956_o() - 1) * seal.getSealPos().face.func_96559_d())) : 0.0, (seal.getSealPos().face.func_82599_e() != 0) ? ((double)((seal.getArea().func_177952_p() - 1) * seal.getSealPos().face.func_82599_e())) : 0.0).func_72314_b((seal.getSealPos().face.func_82601_c() == 0) ? ((double)(seal.getArea().func_177958_n() - 1)) : 0.0, (seal.getSealPos().face.func_96559_d() == 0) ? ((double)(seal.getArea().func_177956_o() - 1)) : 0.0, (seal.getSealPos().face.func_82599_e() == 0) ? ((double)(seal.getArea().func_177952_p() - 1)) : 0.0);
            final double[][] locs = { { area.field_72340_a, area.field_72338_b, area.field_72339_c }, { area.field_72340_a, area.field_72337_e - 1.0, area.field_72339_c }, { area.field_72336_d - 1.0, area.field_72338_b, area.field_72339_c }, { area.field_72336_d - 1.0, area.field_72337_e - 1.0, area.field_72339_c }, { area.field_72336_d - 1.0, area.field_72338_b, area.field_72334_f - 1.0 }, { area.field_72336_d - 1.0, area.field_72337_e - 1.0, area.field_72334_f - 1.0 }, { area.field_72340_a, area.field_72338_b, area.field_72334_f - 1.0 }, { area.field_72340_a, area.field_72337_e - 1.0, area.field_72334_f - 1.0 } };
            int q = 0;
            for (final double[] loc : locs) {
                GL11.glPushMatrix();
                GL11.glTranslated(loc[0] + 0.5, loc[1] + 0.5, loc[2] + 0.5);
                int w = 0;
                for (final EnumFacing face : RenderEventHandler.rotfaces[q]) {
                    GL11.glPushMatrix();
                    GL11.glRotatef(90.0f, (float)(-face.func_96559_d()), (float)face.func_82601_c(), (float)(-face.func_82599_e()));
                    if (face.func_82599_e() < 0) {
                        GL11.glTranslated(0.0, 0.0, -0.49000000953674316);
                    }
                    else {
                        GL11.glTranslated(0.0, 0.0, 0.49000000953674316);
                    }
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
                    GL11.glRotatef((float)RenderEventHandler.rotmat[q][w], 0.0f, 0.0f, 1.0f);
                    UtilsFX.renderQuadCentered(RenderEventHandler.CFRAME, 1.0f, r, g, b, 200, 771, alpha * 0.7f);
                    GL11.glPopMatrix();
                    ++w;
                }
                GL11.glPopMatrix();
                ++q;
            }
            GL11.glDepthMask(true);
        }
        GL11.glPopMatrix();
    }
    
    static void renderSeal(final int x, final int y, final int z, final float alpha, final EnumFacing face, final ResourceLocation resourceLocation, final boolean ia) {
        GL11.glPushMatrix();
        GL11.glColor4f(ia ? 0.5f : 1.0f, ia ? 0.5f : 1.0f, ia ? 0.5f : 1.0f, alpha);
        translateSeal(x, y, z, face.ordinal(), -0.05f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        UtilsFX.renderItemIn2D(resourceLocation.toString(), Minecraft.func_71410_x().func_175606_aa().func_70093_af() ? 0.0f : 0.1f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private static void translateSeal(final float x, final float y, final float z, final int orientation, final float off) {
        if (orientation == 1) {
            GL11.glTranslatef(x + 0.25f, y + 1.0f, z + 0.75f);
            GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 0) {
            GL11.glTranslatef(x + 0.25f, y, z + 0.25f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 3) {
            GL11.glTranslatef(x + 0.25f, y + 0.25f, z + 1.0f);
        }
        else if (orientation == 2) {
            GL11.glTranslatef(x + 0.75f, y + 0.25f, z);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 5) {
            GL11.glTranslatef(x + 1.0f, y + 0.25f, z + 0.75f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 4) {
            GL11.glTranslatef(x, y + 0.25f, z + 0.25f);
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, 0.0f, -off);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void fogDensityEvent(final EntityViewRenderEvent.RenderFogEvent event) {
        if (RenderEventHandler.fogFiddled && RenderEventHandler.fogTarget > 0.0f) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, RenderEventHandler.fogTarget);
        }
    }
    
    @SubscribeEvent
    public static void livingTick(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().field_70170_p.field_72995_K && event.getEntity() instanceof EntityCreature && !event.getEntity().field_70128_L) {
            final EntityCreature mob = (EntityCreature)event.getEntity();
            if (mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD) != null) {
                final Integer t = (int)mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
                if (t != null && t >= 0 && t < ChampionModifier.mods.length) {
                    ChampionModifier.mods[t].effect.showFX((EntityLivingBase)mob);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void renderLivingPre(final RenderLivingEvent.Pre event) {
        if (event.getEntity().field_70170_p.field_72995_K && event.getEntity() instanceof EntityCreature && !event.getEntity().field_70128_L) {
            final EntityCreature mob = (EntityCreature)event.getEntity();
            if (mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD) != null) {
                final Integer t = (int)mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
                if (t != null && t >= 0 && t < ChampionModifier.mods.length) {
                    ChampionModifier.mods[t].effect.preRender((EntityLivingBase)mob, event.getRenderer());
                }
            }
        }
    }
    
    public static void drawTagsOnContainer(final double x, final double y, final double z, final AspectList tags, final int bright, final EnumFacing dir, final float partialTicks) {
        if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer && tags != null && tags.size() > 0 && dir != null) {
            final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
            final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
            final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
            final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
            final int rowsize = 5;
            int current = 0;
            float shifty = 0.0f;
            int left = tags.size();
            for (final Aspect tag : tags.getAspects()) {
                int div = Math.min(left, rowsize);
                if (current >= rowsize) {
                    current = 0;
                    shifty -= RenderEventHandler.tagscale * 1.05f;
                    left -= rowsize;
                    if (left < rowsize) {
                        div = left % rowsize;
                    }
                }
                float shift = (current - div / 2.0f + 0.5f) * RenderEventHandler.tagscale * 4.0f;
                shift *= RenderEventHandler.tagscale;
                final Color color = new Color(tag.getColor());
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                GL11.glTranslated(-iPX + x + 0.5 + RenderEventHandler.tagscale * 2.0f * dir.func_82601_c(), -iPY + y - shifty + 0.5 + RenderEventHandler.tagscale * 2.0f * dir.func_96559_d(), -iPZ + z + 0.5 + RenderEventHandler.tagscale * 2.0f * dir.func_82599_e());
                final float xd = (float)(iPX - (x + 0.5));
                final float zd = (float)(iPZ - (z + 0.5));
                final float rotYaw = (float)(Math.atan2(xd, zd) * 180.0 / 3.141592653589793);
                GL11.glRotatef(rotYaw + 180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glTranslated((double)shift, 0.0, 0.0);
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glScalef(RenderEventHandler.tagscale, RenderEventHandler.tagscale, RenderEventHandler.tagscale);
                UtilsFX.renderQuadCentered(tag.getImage(), 1.0f, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, bright, 771, 0.75f);
                if (tags.getAmount(tag) >= 0) {
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                    final String am = "" + tags.getAmount(tag);
                    GL11.glScalef(0.04f, 0.04f, 0.04f);
                    GL11.glTranslated(0.0, 6.0, -0.1);
                    final int sw = Minecraft.func_71410_x().field_71466_p.func_78256_a(am);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    Minecraft.func_71410_x().field_71466_p.func_78276_b(am, 14 - sw, 1, 1118481);
                    GL11.glTranslated(0.0, 0.0, -0.1);
                    Minecraft.func_71410_x().field_71466_p.func_78276_b(am, 13 - sw, 0, 16777215);
                }
                GL11.glEnable(2929);
                GL11.glPopMatrix();
                ++current;
            }
        }
    }
    
    public static void drawTextInAir(final double x, final double y, final double z, final float partialTicks, final String text) {
        if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
            final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
            final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
            final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslated(-iPX + x + 0.5, -iPY + y + 0.5, -iPZ + z + 0.5);
            final float xd = (float)(iPX - (x + 0.5));
            final float zd = (float)(iPZ - (z + 0.5));
            final float rotYaw = (float)(Math.atan2(xd, zd) * 180.0 / 3.141592653589793);
            GL11.glRotatef(rotYaw + 180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(0.02f, 0.02f, 0.02f);
            final int sw = Minecraft.func_71410_x().field_71466_p.func_78256_a(text);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            Minecraft.func_71410_x().field_71466_p.func_78276_b(text, 1 - sw / 2, 1, 1118481);
            GL11.glTranslated(0.0, 0.0, -0.1);
            Minecraft.func_71410_x().field_71466_p.func_78276_b(text, -sw / 2, 0, 16777215);
            GL11.glPopMatrix();
        }
    }
    
    protected static void renderVignette(float brightness, final double sw, final double sh) {
        final int k = (int)sw;
        final int l = (int)sh;
        brightness = 1.0f - brightness;
        RenderEventHandler.prevVignetteBrightness += (float)((brightness - RenderEventHandler.prevVignetteBrightness) * 0.01);
        if (RenderEventHandler.prevVignetteBrightness > 0.0f) {
            final float b = RenderEventHandler.prevVignetteBrightness * (1.0f + MathHelper.func_76126_a(Minecraft.func_71410_x().field_71439_g.field_70173_aa / 2.0f) * 0.1f);
            GL11.glPushMatrix();
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, sw, sh, 0.0, 1000.0, 3000.0);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(RenderEventHandler.vignetteTexPath);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            OpenGlHelper.func_148821_a(0, 769, 1, 0);
            GL11.glColor4f(b, b, b, 1.0f);
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
            tessellator.func_178180_c().func_181662_b(0.0, (double)l, -90.0).func_187315_a(0.0, 1.0).func_181675_d();
            tessellator.func_178180_c().func_181662_b((double)k, (double)l, -90.0).func_187315_a(1.0, 1.0).func_181675_d();
            tessellator.func_178180_c().func_181662_b((double)k, 0.0, -90.0).func_187315_a(1.0, 0.0).func_181675_d();
            tessellator.func_178180_c().func_181662_b(0.0, 0.0, -90.0).func_187315_a(0.0, 0.0).func_181675_d();
            tessellator.func_78381_a();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            GL11.glPopMatrix();
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void textureStitchEventPre(final TextureStitchEvent.Pre event) {
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "research/quill"));
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "blocks/crystal"));
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "blocks/taint_growth_1"));
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "blocks/taint_growth_2"));
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "blocks/taint_growth_3"));
        event.getMap().func_174942_a(new ResourceLocation("thaumcraft", "blocks/taint_growth_4"));
    }
    
    static {
        RenderEventHandler.INSTANCE = new RenderEventHandler();
        RenderEventHandler.hudHandler = new HudHandler();
        RenderEventHandler.wandHandler = new WandRenderingHandler();
        RenderEventHandler.shaderhandler = new ShaderHandler();
        RenderEventHandler.blockTags = new ArrayList();
        RenderEventHandler.tagscale = 0.0f;
        RenderEventHandler.tickCount = 0;
        RenderEventHandler.checkedDate = false;
        RenderEventHandler.resetShaders = false;
        RenderEventHandler.oldDisplayWidth = 0;
        RenderEventHandler.oldDisplayHeight = 0;
        CFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_corner.png");
        MIDDLE = new ResourceLocation("thaumcraft", "textures/misc/seal_area.png");
        RenderEventHandler.rotfaces = new EnumFacing[][] { { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.WEST }, { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST }, { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST }, { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST }, { EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.EAST }, { EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST }, { EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.WEST }, { EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.WEST } };
        RenderEventHandler.rotmat = new int[][] { { 0, 270, 0 }, { 270, 180, 270 }, { 90, 0, 90 }, { 180, 90, 180 }, { 180, 180, 0 }, { 90, 270, 270 }, { 270, 90, 90 }, { 0, 0, 180 } };
        RenderEventHandler.shaderGroups = new HashMap<Integer, ShaderGroup>();
        RenderEventHandler.fogFiddled = false;
        RenderEventHandler.fogTarget = 0.0f;
        RenderEventHandler.fogDuration = 0;
        RenderEventHandler.prevVignetteBrightness = 0.0f;
        RenderEventHandler.targetBrightness = 1.0f;
        vignetteTexPath = new ResourceLocation("thaumcraft", "textures/misc/vignette.png");
    }
    
    public static class ChargeEntry
    {
        public long time;
        public long tickTime;
        public ItemStack item;
        float charge;
        byte diff;
        
        public ChargeEntry(final long time, final ItemStack item, final float charge) {
            this.charge = 0.0f;
            this.diff = 0;
            this.time = time;
            this.item = item;
            this.charge = charge;
        }
    }
}
