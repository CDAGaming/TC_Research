package thaumcraft.client.lib.events;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import thaumcraft.common.lib.events.*;
import baubles.api.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.inventory.*;
import java.util.*;
import thaumcraft.api.casters.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.util.*;
import net.minecraft.util.math.*;
import thaumcraft.api.items.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class WandRenderingHandler
{
    static float radialHudScale;
    TreeMap<String, Integer> foci;
    HashMap<String, ItemStack> fociItem;
    HashMap<String, Boolean> fociHover;
    HashMap<String, Float> fociScale;
    long lastTime;
    boolean lastState;
    final ResourceLocation R1;
    final ResourceLocation R2;
    int lastArcHash;
    ArrayList<BlockPos> architectBlocks;
    HashMap<BlockPos, boolean[]> bmCache;
    final ResourceLocation CFRAME;
    final ResourceLocation SFRAME;
    int[][] mos;
    int[][] rotmat;
    ResourceLocation tex;
    
    public WandRenderingHandler() {
        this.foci = new TreeMap<String, Integer>();
        this.fociItem = new HashMap<String, ItemStack>();
        this.fociHover = new HashMap<String, Boolean>();
        this.fociScale = new HashMap<String, Float>();
        this.lastTime = 0L;
        this.lastState = false;
        this.R1 = new ResourceLocation("thaumcraft", "textures/misc/radial.png");
        this.R2 = new ResourceLocation("thaumcraft", "textures/misc/radial2.png");
        this.lastArcHash = 0;
        this.architectBlocks = new ArrayList<BlockPos>();
        this.bmCache = new HashMap<BlockPos, boolean[]>();
        this.CFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_corner.png");
        this.SFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_side.png");
        this.mos = new int[][] { { 4, 5, 6, 7 }, { 0, 1, 2, 3 }, { 0, 1, 4, 5 }, { 2, 3, 6, 7 }, { 0, 2, 4, 6 }, { 1, 3, 5, 7 } };
        this.rotmat = new int[][] { { 0, 90, 270, 180 }, { 270, 180, 0, 90 }, { 180, 90, 270, 0 }, { 0, 270, 90, 180 }, { 270, 180, 0, 90 }, { 180, 270, 90, 0 } };
        this.tex = new ResourceLocation("thaumcraft", "textures/misc/architect_arrows.png");
    }
    
    @SideOnly(Side.CLIENT)
    public void handleFociRadial(final Minecraft mc, final long time, final RenderGameOverlayEvent event) {
        if (KeyHandler.radialActive || WandRenderingHandler.radialHudScale > 0.0f) {
            if (KeyHandler.radialActive) {
                if (mc.field_71462_r != null) {
                    KeyHandler.radialActive = false;
                    KeyHandler.radialLock = true;
                    mc.func_71381_h();
                    mc.func_71364_i();
                    return;
                }
                if (WandRenderingHandler.radialHudScale == 0.0f) {
                    this.foci.clear();
                    this.fociItem.clear();
                    this.fociHover.clear();
                    this.fociScale.clear();
                    int pouchcount = 0;
                    ItemStack item = null;
                    final IInventory baubles = BaublesApi.getBaubles((EntityPlayer)mc.field_71439_g);
                    for (int a = 0; a < baubles.func_70302_i_(); ++a) {
                        if (baubles.func_70301_a(a) != null && !baubles.func_70301_a(a).func_190926_b() && baubles.func_70301_a(a).func_77973_b() instanceof ItemFocusPouch) {
                            ++pouchcount;
                            item = baubles.func_70301_a(a);
                            final NonNullList<ItemStack> inv = ((ItemFocusPouch)item.func_77973_b()).getInventory(item);
                            for (int q = 0; q < inv.size(); ++q) {
                                item = (ItemStack)inv.get(q);
                                if (item.func_77973_b() instanceof ItemFocus) {
                                    final String sh = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                                    if (sh != null) {
                                        this.foci.put(sh, q + pouchcount * 1000);
                                        this.fociItem.put(sh, item.func_77946_l());
                                        this.fociScale.put(sh, 1.0f);
                                        this.fociHover.put(sh, false);
                                    }
                                }
                            }
                        }
                    }
                    for (int a = 0; a < 36; ++a) {
                        item = (ItemStack)mc.field_71439_g.field_71071_by.field_70462_a.get(a);
                        if (item.func_77973_b() instanceof ItemFocus) {
                            final String sh2 = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                            if (sh2 == null) {
                                continue;
                            }
                            this.foci.put(sh2, a);
                            this.fociItem.put(sh2, item.func_77946_l());
                            this.fociScale.put(sh2, 1.0f);
                            this.fociHover.put(sh2, false);
                        }
                        if (item.func_77973_b() instanceof ItemFocusPouch) {
                            ++pouchcount;
                            final NonNullList<ItemStack> inv = ((ItemFocusPouch)item.func_77973_b()).getInventory(item);
                            for (int q = 0; q < inv.size(); ++q) {
                                item = (ItemStack)inv.get(q);
                                if (item.func_77973_b() instanceof ItemFocus) {
                                    final String sh = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                                    if (sh != null) {
                                        this.foci.put(sh, q + pouchcount * 1000);
                                        this.fociItem.put(sh, item.func_77946_l());
                                        this.fociScale.put(sh, 1.0f);
                                        this.fociHover.put(sh, false);
                                    }
                                }
                            }
                        }
                    }
                    if (this.foci.size() > 0 && mc.field_71415_G) {
                        mc.field_71415_G = false;
                        mc.field_71417_B.func_74373_b();
                    }
                }
            }
            else if (mc.field_71462_r == null && this.lastState) {
                if (Display.isActive() && !mc.field_71415_G) {
                    mc.field_71415_G = true;
                    mc.field_71417_B.func_74372_a();
                }
                this.lastState = false;
            }
            this.renderFocusRadialHUD(event.getResolution().func_78327_c(), event.getResolution().func_78324_d(), time, event.getPartialTicks());
            if (time > this.lastTime) {
                for (final String key : this.fociHover.keySet()) {
                    if (this.fociHover.get(key)) {
                        if (!KeyHandler.radialActive && !KeyHandler.radialLock) {
                            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketFocusChangeToServer(key));
                            KeyHandler.radialLock = true;
                        }
                        if (this.fociScale.get(key) >= 1.3f) {
                            continue;
                        }
                        this.fociScale.put(key, this.fociScale.get(key) + 0.025f);
                    }
                    else {
                        if (this.fociScale.get(key) <= 1.0f) {
                            continue;
                        }
                        this.fociScale.put(key, this.fociScale.get(key) - 0.025f);
                    }
                }
                if (!KeyHandler.radialActive) {
                    WandRenderingHandler.radialHudScale -= 0.05f;
                }
                else if (KeyHandler.radialActive && WandRenderingHandler.radialHudScale < 1.0f) {
                    WandRenderingHandler.radialHudScale += 0.05f;
                }
                if (WandRenderingHandler.radialHudScale > 1.0f) {
                    WandRenderingHandler.radialHudScale = 1.0f;
                }
                if (WandRenderingHandler.radialHudScale < 0.0f) {
                    WandRenderingHandler.radialHudScale = 0.0f;
                    KeyHandler.radialLock = false;
                }
                this.lastTime = time + 5L;
                this.lastState = KeyHandler.radialActive;
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void renderFocusRadialHUD(final double sw, final double sh, final long time, final float partialTicks) {
        final Minecraft mc = Minecraft.func_71410_x();
        ItemStack s = mc.field_71439_g.func_184614_ca();
        if (!(s.func_77973_b() instanceof ICaster)) {
            s = mc.field_71439_g.func_184592_cb();
        }
        if (!(s.func_77973_b() instanceof ICaster)) {
            return;
        }
        final ICaster wand = (ICaster)s.func_77973_b();
        final ItemFocus focus = (ItemFocus)wand.getFocus(s);
        final int i = (int)(Mouse.getEventX() * sw / mc.field_71443_c);
        final int j = (int)(sh - Mouse.getEventY() * sh / mc.field_71440_d - 1.0);
        final int k = Mouse.getEventButton();
        if (this.fociItem.size() == 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, sw, sh, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glTranslated(sw / 2.0, sh / 2.0, 0.0);
        ItemStack tt = null;
        final float width = 16.0f + this.fociItem.size() * 2.5f;
        mc.field_71446_o.func_110577_a(this.R1);
        GL11.glPushMatrix();
        GL11.glRotatef(partialTicks + mc.field_71439_g.field_70173_aa % 720 / 2.0f, 0.0f, 0.0f, 1.0f);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        UtilsFX.renderQuadCentered(1, 1, 0, width * 2.75f * WandRenderingHandler.radialHudScale, 0.5f, 0.5f, 0.5f, 200, 771, 0.5f);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glPopMatrix();
        mc.field_71446_o.func_110577_a(this.R2);
        GL11.glPushMatrix();
        GL11.glRotatef(-(partialTicks + mc.field_71439_g.field_70173_aa % 720 / 2.0f), 0.0f, 0.0f, 1.0f);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        UtilsFX.renderQuadCentered(1, 1, 0, width * 2.55f * WandRenderingHandler.radialHudScale, 0.5f, 0.5f, 0.5f, 200, 771, 0.5f);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glPopMatrix();
        if (focus != null) {
            final ItemStack item = wand.getFocusStack(s).func_77946_l();
            UtilsFX.renderItemInGUI(-8, -8, 100, item);
            final int mx = (int)(i - sw / 2.0);
            final int my = (int)(j - sh / 2.0);
            if (mx >= -10 && mx <= 10 && my >= -10 && my <= 10) {
                tt = item;
            }
        }
        GL11.glScaled((double)WandRenderingHandler.radialHudScale, (double)WandRenderingHandler.radialHudScale, (double)WandRenderingHandler.radialHudScale);
        float currentRot = -90.0f * WandRenderingHandler.radialHudScale;
        final float pieSlice = 360.0f / this.fociItem.size();
        String key = this.foci.firstKey();
        for (int a = 0; a < this.fociItem.size(); ++a) {
            final double xx = MathHelper.func_76134_b(currentRot / 180.0f * 3.1415927f) * width;
            final double yy = MathHelper.func_76126_a(currentRot / 180.0f * 3.1415927f) * width;
            currentRot += pieSlice;
            GL11.glPushMatrix();
            GL11.glTranslated(xx, yy, 100.0);
            GL11.glScalef((float)this.fociScale.get(key), (float)this.fociScale.get(key), (float)this.fociScale.get(key));
            GL11.glEnable(32826);
            final ItemStack item2 = this.fociItem.get(key).func_77946_l();
            UtilsFX.renderItemInGUI(-8, -8, 100, item2);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
            if (!KeyHandler.radialLock && KeyHandler.radialActive) {
                final int mx2 = (int)(i - sw / 2.0 - xx);
                final int my2 = (int)(j - sh / 2.0 - yy);
                if (mx2 >= -10 && mx2 <= 10 && my2 >= -10 && my2 <= 10) {
                    this.fociHover.put(key, true);
                    tt = this.fociItem.get(key);
                    if (k == 0) {
                        KeyHandler.radialActive = false;
                        KeyHandler.radialLock = true;
                        PacketHandler.INSTANCE.sendToServer((IMessage)new PacketFocusChangeToServer(key));
                        break;
                    }
                }
                else {
                    this.fociHover.put(key, false);
                }
            }
            key = this.foci.higherKey(key);
        }
        GL11.glPopMatrix();
        if (tt != null) {
            UtilsFX.drawCustomTooltip(mc.field_71462_r, mc.field_71466_p, tt.func_82840_a((EntityPlayer)mc.field_71439_g, (ITooltipFlag)(Minecraft.func_71410_x().field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL)), -4, 20, 11);
        }
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean handleArchitectOverlay(final ItemStack stack, final EntityPlayer player, final float partialTicks, final int playerticks, final RayTraceResult target) {
        if (target == null) {
            return false;
        }
        final Minecraft mc = Minecraft.func_71410_x();
        final IArchitect af = (IArchitect)stack.func_77973_b();
        final String h = target.func_178782_a().func_177958_n() + "" + target.func_178782_a().func_177956_o() + "" + target.func_178782_a().func_177952_p() + "" + target.field_178784_b + "" + playerticks / 5;
        final int hc = h.hashCode();
        if (hc != this.lastArcHash) {
            this.lastArcHash = hc;
            this.bmCache.clear();
            this.architectBlocks = af.getArchitectBlocks(stack, (World)mc.field_71441_e, target.func_178782_a(), target.field_178784_b, player);
        }
        if (this.architectBlocks == null || this.architectBlocks.size() == 0) {
            return false;
        }
        this.drawArchitectAxis(target.func_178782_a(), partialTicks, af.showAxis(stack, (World)mc.field_71441_e, player, target.field_178784_b, IArchitect.EnumAxis.X), af.showAxis(stack, (World)mc.field_71441_e, player, target.field_178784_b, IArchitect.EnumAxis.Y), af.showAxis(stack, (World)mc.field_71441_e, player, target.field_178784_b, IArchitect.EnumAxis.Z));
        for (final BlockPos cc : this.architectBlocks) {
            this.drawOverlayBlock(cc, playerticks, mc, partialTicks);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return true;
    }
    
    private boolean isConnectedBlock(final World world, final BlockPos pos) {
        return this.architectBlocks.contains(pos);
    }
    
    @SideOnly(Side.CLIENT)
    private boolean[] getConnectedSides(final World world, final BlockPos pos) {
        if (this.bmCache.containsKey(pos)) {
            return this.bmCache.get(pos);
        }
        final boolean[] bitMatrix = { !this.isConnectedBlock(world, pos.func_177982_a(-1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, -1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, -1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(-1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, 1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, 1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(-1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, -1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, -1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, -1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, -1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(-1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, 1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, -1, 0)), !this.isConnectedBlock(world, pos.func_177982_a(1, 0, 0)) && !this.isConnectedBlock(world, pos.func_177982_a(0, 0, 1)) && !this.isConnectedBlock(world, pos.func_177982_a(0, -1, 0)) };
        this.bmCache.put(pos, bitMatrix);
        return bitMatrix;
    }
    
    @SideOnly(Side.CLIENT)
    public void drawOverlayBlock(final BlockPos pos, final int ticks, final Minecraft mc, final float partialTicks) {
        final boolean[] bitMatrix = this.getConnectedSides((World)mc.field_71441_e, pos);
        GL11.glPushMatrix();
        GlStateManager.func_179112_b(770, 771);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glDisable(2884);
        final EntityPlayer player = (EntityPlayer)mc.func_175606_aa();
        final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
        final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
        final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
        GL11.glTranslated(-iPX + pos.func_177958_n() + 0.5, -iPY + pos.func_177956_o() + 0.5, -iPZ + pos.func_177952_p() + 0.5);
        for (final EnumFacing face : EnumFacing.values()) {
            if (!this.isConnectedBlock((World)mc.field_71441_e, pos.func_177972_a(face))) {
                GL11.glPushMatrix();
                GL11.glRotatef(90.0f, (float)(-face.func_96559_d()), (float)face.func_82601_c(), (float)(-face.func_82599_e()));
                if (face.func_82599_e() < 0) {
                    GL11.glTranslated(0.0, 0.0, -0.5);
                }
                else {
                    GL11.glTranslated(0.0, 0.0, 0.5);
                }
                GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
                GL11.glPushMatrix();
                UtilsFX.renderQuadCentered(this.SFRAME, 1, 1, 0, 1.0f, 1.0f, 1.0f, 1.0f, 200, 1, 0.1f);
                GL11.glPopMatrix();
                for (int a = 0; a < 4; ++a) {
                    if (bitMatrix[this.mos[face.ordinal()][a]]) {
                        GL11.glPushMatrix();
                        GL11.glRotatef((float)this.rotmat[face.ordinal()][a], 0.0f, 0.0f, 1.0f);
                        UtilsFX.renderQuadCentered(this.CFRAME, 1, 1, 0, 1.0f, 1.0f, 1.0f, 1.0f, 200, 1, 0.66f);
                        GL11.glPopMatrix();
                    }
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GlStateManager.func_179084_k();
        GlStateManager.func_179092_a(516, 0.1f);
        GL11.glPopMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    public void drawArchitectAxis(final BlockPos pos, final float partialTicks, final boolean dx, final boolean dy, final boolean dz) {
        if (!dx && !dy && !dz) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
        final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
        final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
        final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
        final float r = MathHelper.func_76126_a(player.field_70173_aa / 4.0f + pos.func_177958_n()) * 0.2f + 0.3f;
        final float g = MathHelper.func_76126_a(player.field_70173_aa / 3.0f + pos.func_177956_o()) * 0.2f + 0.3f;
        final float b = MathHelper.func_76126_a(player.field_70173_aa / 2.0f + pos.func_177952_p()) * 0.2f + 0.8f;
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glTranslated(-iPX + pos.func_177958_n() + 0.5, -iPY + pos.func_177956_o() + 0.5, -iPZ + pos.func_177952_p() + 0.5);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.33f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        if (dz) {
            GL11.glPushMatrix();
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glPopMatrix();
        }
        if (dx) {
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glPopMatrix();
        }
        if (dy) {
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            UtilsFX.renderQuadCentered(this.tex, 1.0f, r, g, b, 200, 1, 1.0f);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
    
    static {
        WandRenderingHandler.radialHudScale = 0.0f;
    }
}
