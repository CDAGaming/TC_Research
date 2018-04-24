package thaumcraft.client.lib;

import java.text.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.item.*;
import java.awt.*;
import net.minecraft.client.renderer.texture.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.config.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class UtilsFX
{
    public static final ResourceLocation nodeTexture;
    public static final VertexFormat VERTEXFORMAT_POS_TEX_CO_LM_NO;
    public static final String[] colorNames;
    public static final String[] colorCodes;
    public static final int[] colors;
    public static float sysPartialTicks;
    static DecimalFormat myFormatter;
    public static boolean hideStackOverlay;
    
    public static void renderFacingQuad(final double px, final double py, final double pz, final int gridX, final int gridY, final int frame, final float scale, final int color, final float alpha, final int blend, final float partialTicks) {
        if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
            final Tessellator tessellator = Tessellator.func_178181_a();
            final BufferBuilder wr = tessellator.func_178180_c();
            final float arX = ActiveRenderInfo.func_178808_b();
            final float arZ = ActiveRenderInfo.func_178803_d();
            final float arYZ = ActiveRenderInfo.func_178805_e();
            final float arXY = ActiveRenderInfo.func_178807_f();
            final float arXZ = ActiveRenderInfo.func_178809_c();
            final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().func_175606_aa();
            final double iPX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * partialTicks;
            final double iPY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * partialTicks;
            final double iPZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * partialTicks;
            GlStateManager.func_179094_E();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, blend);
            GlStateManager.func_179092_a(516, 0.003921569f);
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslated(-iPX, -iPY, -iPZ);
            final Vec3d v1 = new Vec3d((double)(-arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(-arZ * scale - arXY * scale));
            final Vec3d v2 = new Vec3d((double)(-arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(-arZ * scale + arXY * scale));
            final Vec3d v3 = new Vec3d((double)(arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(arZ * scale + arXY * scale));
            final Vec3d v4 = new Vec3d((double)(arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(arZ * scale - arXY * scale));
            final int xm = frame % gridX;
            final int ym = frame / gridY;
            final float f1 = xm / gridX;
            final float f2 = f1 + 1.0f / gridX;
            final float f3 = ym / gridY;
            final float f4 = f3 + 1.0f / gridY;
            final TexturedQuadTC quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex((float)px + (float)v1.field_72450_a, (float)py + (float)v1.field_72448_b, (float)pz + (float)v1.field_72449_c, f2, f4), new PositionTextureVertex((float)px + (float)v2.field_72450_a, (float)py + (float)v2.field_72448_b, (float)pz + (float)v2.field_72449_c, f2, f3), new PositionTextureVertex((float)px + (float)v3.field_72450_a, (float)py + (float)v3.field_72448_b, (float)pz + (float)v3.field_72449_c, f1, f3), new PositionTextureVertex((float)px + (float)v4.field_72450_a, (float)py + (float)v4.field_72448_b, (float)pz + (float)v4.field_72449_c, f1, f4) });
            quad.draw(tessellator.func_178180_c(), 1.0f, 220, color, alpha);
            GlStateManager.func_179132_a(true);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3042);
            GlStateManager.func_179092_a(516, 0.1f);
            GlStateManager.func_179121_F();
        }
    }
    
    public static void drawTexturedQuad(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final double zLevel) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.func_178181_a();
        var9.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + par6), zLevel).func_187315_a((double)((par3 + 0.0f) * var7), (double)((par4 + par6) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + par5), (double)(par2 + par6), zLevel).func_187315_a((double)((par3 + par5) * var7), (double)((par4 + par6) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + par5), (double)(par2 + 0.0f), zLevel).func_187315_a((double)((par3 + par5) * var7), (double)((par4 + 0.0f) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + 0.0f), zLevel).func_187315_a((double)((par3 + 0.0f) * var7), (double)((par4 + 0.0f) * var8)).func_181675_d();
        var9.func_78381_a();
    }
    
    public static void drawTexturedQuadF(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final double zLevel) {
        final float d = 0.0625f;
        final Tessellator var9 = Tessellator.func_178181_a();
        var9.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + 16.0f), zLevel).func_187315_a((double)((par3 + 0.0f) * d), (double)((par4 + par6) * d)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 16.0f), (double)(par2 + 16.0f), zLevel).func_187315_a((double)((par3 + par5) * d), (double)((par4 + par6) * d)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 16.0f), (double)(par2 + 0.0f), zLevel).func_187315_a((double)((par3 + par5) * d), (double)((par4 + 0.0f) * d)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + 0.0f), zLevel).func_187315_a((double)((par3 + 0.0f) * d), (double)((par4 + 0.0f) * d)).func_181675_d();
        var9.func_78381_a();
    }
    
    public static void drawTexturedQuadFull(final float par1, final float par2, final double zLevel) {
        final Tessellator var9 = Tessellator.func_178181_a();
        var9.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + 16.0f), zLevel).func_187315_a(0.0, 1.0).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 16.0f), (double)(par2 + 16.0f), zLevel).func_187315_a(1.0, 1.0).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 16.0f), (double)(par2 + 0.0f), zLevel).func_187315_a(1.0, 0.0).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par1 + 0.0f), (double)(par2 + 0.0f), zLevel).func_187315_a(0.0, 0.0).func_181675_d();
        var9.func_78381_a();
    }
    
    public static void renderItemInGUI(final int x, final int y, final int z, final ItemStack stack) {
        final Minecraft mc = Minecraft.func_71410_x();
        try {
            GlStateManager.func_179094_E();
            RenderHelper.func_74520_c();
            GlStateManager.func_179140_f();
            GlStateManager.func_179091_B();
            GlStateManager.func_179142_g();
            GlStateManager.func_179145_e();
            mc.func_175599_af().field_77023_b = z;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            mc.func_175599_af().func_180450_b(stack, x, y);
            mc.func_175599_af().field_77023_b = 0.0f;
            GlStateManager.func_179140_f();
            GlStateManager.func_179121_F();
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            RenderHelper.func_74519_b();
        }
        catch (Exception ex) {}
    }
    
    public static void renderQuadCentered(final ResourceLocation texture, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(texture);
        renderQuadCentered(1, 1, 0, scale, red, green, blue, brightness, blend, opacity);
    }
    
    public static void renderQuadCentered(final ResourceLocation texture, final int gridX, final int gridY, final int frame, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(texture);
        renderQuadCentered(gridX, gridY, frame, scale, red, green, blue, brightness, blend, opacity);
    }
    
    public static void renderQuadCentered() {
        renderQuadCentered(1, 1, 0, 1.0f, 1.0f, 1.0f, 1.0f, 200, 771, 1.0f);
    }
    
    public static void renderQuadCentered(final int gridX, final int gridY, final int frame, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final boolean blendon = GL11.glIsEnabled(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        final int xm = frame % gridX;
        final int ym = frame / gridY;
        final float f1 = xm / gridX;
        final float f2 = f1 + 1.0f / gridX;
        final float f3 = ym / gridY;
        final float f4 = f3 + 1.0f / gridY;
        final Color c = new Color(red, green, blue);
        final TexturedQuadTC quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(-0.5f, 0.5f, 0.0f, f2, f4), new PositionTextureVertex(0.5f, 0.5f, 0.0f, f2, f3), new PositionTextureVertex(0.5f, -0.5f, 0.0f, f1, f3), new PositionTextureVertex(-0.5f, -0.5f, 0.0f, f1, f4) });
        quad.draw(tessellator.func_178180_c(), scale, brightness, c.getRGB(), opacity);
        GL11.glBlendFunc(770, 771);
        if (!blendon) {
            GL11.glDisable(3042);
        }
    }
    
    public static void renderQuadFromIcon(final TextureAtlasSprite icon, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        final boolean blendon = GL11.glIsEnabled(3042);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final float f1 = icon.func_94212_f();
        final float f2 = icon.func_94206_g();
        final float f3 = icon.func_94209_e();
        final float f4 = icon.func_94210_h();
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(red, green, blue, opacity);
        if (brightness > -1) {
            tessellator.func_178180_c().func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        }
        else {
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181712_l);
        }
        final int j = brightness >> 16 & 0xFFFF;
        final int k = brightness & 0xFFFF;
        tessellator.func_178180_c().func_181662_b(0.0, 0.0, 0.0).func_187315_a((double)f1, (double)f4).func_181666_a(red, green, blue, opacity);
        if (brightness > -1) {
            tessellator.func_178180_c().func_187314_a(j, k);
        }
        tessellator.func_178180_c().func_181663_c(0.0f, 0.0f, 1.0f);
        tessellator.func_178180_c().func_181675_d();
        tessellator.func_178180_c().func_181662_b(1.0, 0.0, 0.0).func_187315_a((double)f3, (double)f4).func_181666_a(red, green, blue, opacity);
        if (brightness > -1) {
            tessellator.func_178180_c().func_187314_a(j, k);
        }
        tessellator.func_178180_c().func_181663_c(0.0f, 0.0f, 1.0f);
        tessellator.func_178180_c().func_181675_d();
        tessellator.func_178180_c().func_181662_b(1.0, 1.0, 0.0).func_187315_a((double)f3, (double)f2).func_181666_a(red, green, blue, opacity);
        if (brightness > -1) {
            tessellator.func_178180_c().func_187314_a(j, k);
        }
        tessellator.func_178180_c().func_181663_c(0.0f, 0.0f, 1.0f);
        tessellator.func_178180_c().func_181675_d();
        tessellator.func_178180_c().func_181662_b(0.0, 1.0, 0.0).func_187315_a((double)f1, (double)f2).func_181666_a(red, green, blue, opacity);
        if (brightness > -1) {
            tessellator.func_178180_c().func_187314_a(j, k);
        }
        tessellator.func_178180_c().func_181663_c(0.0f, 0.0f, 1.0f);
        tessellator.func_178180_c().func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179112_b(770, 771);
        if (!blendon) {
            GL11.glDisable(3042);
        }
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amount, final int bonus, final double z, final int blend, final float alpha) {
        drawTag(x, y, aspect, amount, bonus, z, blend, alpha, false);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amt, final int bonus, final double z) {
        drawTag(x, y, aspect, amt, bonus, z, 771, 1.0f, false);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect) {
        drawTag(x, y, aspect, 0.0f, 0, 0.0, 771, 1.0f, true);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amount, final int bonus, final double z, final int blend, final float alpha, final boolean bw) {
        drawTag(x, (double)y, aspect, amount, bonus, z, blend, alpha, bw);
    }
    
    public static void drawTag(final double x, final double y, final Aspect aspect, final float amount, final int bonus, final double z, final int blend, final float alpha, final boolean bw) {
        if (aspect == null) {
            return;
        }
        final boolean blendon = GL11.glIsEnabled(3042);
        final Minecraft mc = Minecraft.func_71410_x();
        final boolean isLightingEnabled = GL11.glIsEnabled(2896);
        final Color color = new Color(aspect.getColor());
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glPushMatrix();
        mc.field_71446_o.func_110577_a(aspect.getImage());
        if (!bw) {
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha);
        }
        else {
            GL11.glColor4f(0.1f, 0.1f, 0.1f, alpha * 0.8f);
        }
        final Tessellator var9 = Tessellator.func_178181_a();
        var9.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181709_i);
        if (!bw) {
            var9.func_178180_c().func_181662_b(x + 0.0, y + 16.0, z).func_187315_a(0.0, 1.0).func_181666_a(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 16.0, y + 16.0, z).func_187315_a(1.0, 1.0).func_181666_a(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 16.0, y + 0.0, z).func_187315_a(1.0, 0.0).func_181666_a(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 0.0, y + 0.0, z).func_187315_a(0.0, 0.0).func_181666_a(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, alpha).func_181675_d();
        }
        else {
            var9.func_178180_c().func_181662_b(x + 0.0, y + 16.0, z).func_187315_a(0.0, 1.0).func_181666_a(0.1f, 0.1f, 0.1f, alpha * 0.8f).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 16.0, y + 16.0, z).func_187315_a(1.0, 1.0).func_181666_a(0.1f, 0.1f, 0.1f, alpha * 0.8f).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 16.0, y + 0.0, z).func_187315_a(1.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, alpha * 0.8f).func_181675_d();
            var9.func_178180_c().func_181662_b(x + 0.0, y + 0.0, z).func_187315_a(0.0, 0.0).func_181666_a(0.1f, 0.1f, 0.1f, alpha * 0.8f).func_181675_d();
        }
        var9.func_78381_a();
        GL11.glPopMatrix();
        if (amount > 0.0f) {
            GL11.glPushMatrix();
            float q = 0.5f;
            if (!ModConfig.CONFIG_GRAPHICS.largeTagText) {
                GL11.glScalef(0.5f, 0.5f, 0.5f);
                q = 1.0f;
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final String am = UtilsFX.myFormatter.format(amount);
            final int sw = mc.field_71466_p.func_78256_a(am);
            for (final EnumFacing e : EnumFacing.field_176754_o) {
                mc.field_71466_p.func_175065_a(am, (32 - sw + (int)x * 2) * q + e.func_82601_c(), (32 - mc.field_71466_p.field_78288_b + (int)y * 2) * q + e.func_82599_e(), 0, false);
            }
            mc.field_71466_p.func_175065_a(am, (32 - sw + (int)x * 2) * q, (32 - mc.field_71466_p.field_78288_b + (int)y * 2) * q, 16777215, false);
            GL11.glPopMatrix();
        }
        if (bonus > 0) {
            GL11.glPushMatrix();
            mc.field_71446_o.func_110577_a(ParticleEngine.particleTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int px = 16 * (mc.field_71439_g.field_70173_aa % 16);
            drawTexturedQuad((int)x - 4, (int)y - 4, px, 80.0f, 16.0f, 16.0f, z);
            if (bonus > 1) {
                float q2 = 0.5f;
                if (!ModConfig.CONFIG_GRAPHICS.largeTagText) {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    q2 = 1.0f;
                }
                final String am2 = "" + bonus;
                final int sw2 = mc.field_71466_p.func_78256_a(am2) / 2;
                GL11.glTranslated(0.0, 0.0, -1.0);
                mc.field_71466_p.func_175063_a(am2, (8 - sw2 + (int)x * 2) * q2, (15 - mc.field_71466_p.field_78288_b + (int)y * 2) * q2, 16777215);
            }
            GL11.glPopMatrix();
        }
        GlStateManager.func_179112_b(770, 771);
        if (!blendon) {
            GL11.glDisable(3042);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glAlphaFunc(516, 0.1f);
        if (isLightingEnabled) {
            GL11.glEnable(2896);
        }
        GL11.glPopMatrix();
    }
    
    public static void drawCustomTooltip(final GuiScreen gui, final FontRenderer fr, final List textList, final int x, final int y, final int subTipColor) {
        drawCustomTooltip(gui, fr, textList, x, y, subTipColor, false);
    }
    
    public static void drawCustomTooltip(final GuiScreen gui, final FontRenderer fr, List textList, final int x, final int y, final int subTipColor, final boolean ignoremouse) {
        if (!textList.isEmpty()) {
            final Minecraft mc = Minecraft.func_71410_x();
            final ScaledResolution scaledresolution = new ScaledResolution(mc);
            final int sf = scaledresolution.func_78325_e();
            GlStateManager.func_179101_C();
            RenderHelper.func_74518_a();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            int max = 240;
            final int mx = Mouse.getEventX();
            boolean flip = false;
            if (!ignoremouse && (max + 24) * sf + mx > mc.field_71443_c) {
                max = (mc.field_71443_c - mx) / sf - 24;
                if (max < 120) {
                    max = 240;
                    flip = true;
                }
            }
            int widestLineWidth = 0;
            final Iterator textLineEntry = textList.iterator();
            boolean b = false;
            while (textLineEntry.hasNext()) {
                final String textLine = textLineEntry.next();
                if (fr.func_78256_a(textLine) > max) {
                    b = true;
                    break;
                }
            }
            if (b) {
                final List tl = new ArrayList();
                for (final Object o : textList) {
                    final String textLine2 = (String)o;
                    final List tl2 = fr.func_78271_c(textLine2, textLine2.startsWith("@@") ? (max * 2) : max);
                    for (final Object o2 : tl2) {
                        String textLine3 = ((String)o2).trim();
                        if (textLine2.startsWith("@@")) {
                            textLine3 = "@@" + textLine3;
                        }
                        tl.add(textLine3);
                    }
                }
                textList = tl;
            }
            final Iterator textLines = textList.iterator();
            int totalHeight = -2;
            while (textLines.hasNext()) {
                final String textLine4 = textLines.next();
                int lineWidth = fr.func_78256_a(textLine4);
                if (textLine4.startsWith("@@") && !fr.func_82883_a()) {
                    lineWidth /= 2;
                }
                if (lineWidth > widestLineWidth) {
                    widestLineWidth = lineWidth;
                }
                totalHeight += ((textLine4.startsWith("@@") && !fr.func_82883_a()) ? 7 : 10);
            }
            int sX = x + 12;
            int sY = y - 12;
            if (textList.size() > 1) {
                totalHeight += 2;
            }
            if (sY + totalHeight > scaledresolution.func_78328_b()) {
                sY = scaledresolution.func_78328_b() - totalHeight - 5;
            }
            if (flip) {
                sX -= widestLineWidth + 24;
            }
            Minecraft.func_71410_x().func_175599_af().field_77023_b = 300.0f;
            final int var10 = -267386864;
            drawGradientRect(sX - 3, sY - 4, sX + widestLineWidth + 3, sY - 3, var10, var10);
            drawGradientRect(sX - 3, sY + totalHeight + 3, sX + widestLineWidth + 3, sY + totalHeight + 4, var10, var10);
            drawGradientRect(sX - 3, sY - 3, sX + widestLineWidth + 3, sY + totalHeight + 3, var10, var10);
            drawGradientRect(sX - 4, sY - 3, sX - 3, sY + totalHeight + 3, var10, var10);
            drawGradientRect(sX + widestLineWidth + 3, sY - 3, sX + widestLineWidth + 4, sY + totalHeight + 3, var10, var10);
            final int var11 = 1347420415;
            final int var12 = (var11 & 0xFEFEFE) >> 1 | (var11 & 0xFF000000);
            drawGradientRect(sX - 3, sY - 3 + 1, sX - 3 + 1, sY + totalHeight + 3 - 1, var11, var12);
            drawGradientRect(sX + widestLineWidth + 2, sY - 3 + 1, sX + widestLineWidth + 3, sY + totalHeight + 3 - 1, var11, var12);
            drawGradientRect(sX - 3, sY - 3, sX + widestLineWidth + 3, sY - 3 + 1, var11, var11);
            drawGradientRect(sX - 3, sY + totalHeight + 2, sX + widestLineWidth + 3, sY + totalHeight + 3, var12, var12);
            for (int i = 0; i < textList.size(); ++i) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)sX, (float)sY, 0.0f);
                String tl3 = textList.get(i);
                boolean shift = false;
                GL11.glPushMatrix();
                if (tl3.startsWith("@@") && !fr.func_82883_a()) {
                    sY += 7;
                    GL11.glScalef(0.5f, 0.5f, 1.0f);
                    shift = true;
                }
                else {
                    sY += 10;
                }
                tl3 = tl3.replaceAll("@@", "");
                if (subTipColor != -99) {
                    if (i == 0) {
                        tl3 = "§" + Integer.toHexString(subTipColor) + tl3;
                    }
                    else {
                        tl3 = "§7" + tl3;
                    }
                }
                GL11.glTranslated(0.0, 0.0, 301.0);
                fr.func_175063_a(tl3, 0.0f, shift ? 3.0f : 0.0f, -1);
                GL11.glPopMatrix();
                if (i == 0) {
                    sY += 2;
                }
                GL11.glPopMatrix();
            }
            Minecraft.func_71410_x().func_175599_af().field_77023_b = 0.0f;
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            RenderHelper.func_74519_b();
            GlStateManager.func_179091_B();
        }
    }
    
    public static void drawGradientRect(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final boolean blendon = GL11.glIsEnabled(3042);
        final float var7 = (par5 >> 24 & 0xFF) / 255.0f;
        final float var8 = (par5 >> 16 & 0xFF) / 255.0f;
        final float var9 = (par5 >> 8 & 0xFF) / 255.0f;
        final float var10 = (par5 & 0xFF) / 255.0f;
        final float var11 = (par6 >> 24 & 0xFF) / 255.0f;
        final float var12 = (par6 >> 16 & 0xFF) / 255.0f;
        final float var13 = (par6 >> 8 & 0xFF) / 255.0f;
        final float var14 = (par6 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        final Tessellator var15 = Tessellator.func_178181_a();
        var15.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
        var15.func_178180_c().func_181662_b((double)par3, (double)par2, 300.0).func_181666_a(var8, var9, var10, var7).func_181675_d();
        var15.func_178180_c().func_181662_b((double)par1, (double)par2, 300.0).func_181666_a(var8, var9, var10, var7).func_181675_d();
        var15.func_178180_c().func_181662_b((double)par1, (double)par4, 300.0).func_181666_a(var12, var13, var14, var11).func_181675_d();
        var15.func_178180_c().func_181662_b((double)par3, (double)par4, 300.0).func_181666_a(var12, var13, var14, var11).func_181675_d();
        var15.func_78381_a();
        GL11.glShadeModel(7424);
        GlStateManager.func_179112_b(770, 771);
        if (!blendon) {
            GL11.glDisable(3042);
        }
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }
    
    public static void renderBillboardQuad(final double scale) {
        GL11.glPushMatrix();
        rotateToPlayer();
        final Tessellator tessellator = Tessellator.func_178181_a();
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181710_j);
        tessellator.func_178180_c().func_181662_b(-scale, -scale, 0.0).func_187315_a(0.0, 0.0).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-scale, scale, 0.0).func_187315_a(0.0, 1.0).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, scale, 0.0).func_187315_a(1.0, 1.0).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, -scale, 0.0).func_187315_a(1.0, 0.0).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glPopMatrix();
    }
    
    public static void renderBillboardQuad(final double scale, final int gridX, final int gridY, final int frame) {
        GL11.glPushMatrix();
        rotateToPlayer();
        final int xm = frame % gridX;
        final int ym = frame / gridY;
        final float f1 = xm / gridX;
        final float f2 = f1 + 1.0f / gridX;
        final float f3 = ym / gridY;
        final float f4 = f3 + 1.0f / gridY;
        final Tessellator tessellator = Tessellator.func_178181_a();
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181710_j);
        tessellator.func_178180_c().func_181662_b(-scale, -scale, 0.0).func_187315_a((double)f2, (double)f4).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-scale, scale, 0.0).func_187315_a((double)f2, (double)f3).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, scale, 0.0).func_187315_a((double)f1, (double)f3).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, -scale, 0.0).func_187315_a((double)f1, (double)f4).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glPopMatrix();
    }
    
    public static void renderBillboardQuad(final double scale, final int gridX, final int gridY, final int frame, final float r, final float g, final float b, final float a, final int bright) {
        GL11.glPushMatrix();
        rotateToPlayer();
        final int xm = frame % gridX;
        final int ym = frame / gridY;
        final float f1 = xm / gridX;
        final float f2 = f1 + 1.0f / gridX;
        final float f3 = ym / gridY;
        final float f4 = f3 + 1.0f / gridY;
        final int j = bright >> 16 & 0xFFFF;
        final int k = bright & 0xFFFF;
        final Tessellator tessellator = Tessellator.func_178181_a();
        tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181704_d);
        tessellator.func_178180_c().func_181662_b(-scale, -scale, 0.0).func_187315_a((double)f2, (double)f4).func_181666_a(r, g, b, a).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b(-scale, scale, 0.0).func_187315_a((double)f2, (double)f3).func_181666_a(r, g, b, a).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, scale, 0.0).func_187315_a((double)f1, (double)f3).func_181666_a(r, g, b, a).func_187314_a(j, k).func_181675_d();
        tessellator.func_178180_c().func_181662_b(scale, -scale, 0.0).func_187315_a((double)f1, (double)f4).func_181666_a(r, g, b, a).func_187314_a(j, k).func_181675_d();
        tessellator.func_78381_a();
        GL11.glPopMatrix();
    }
    
    public static void rotateToPlayer() {
        GL11.glRotatef(-Minecraft.func_71410_x().func_175598_ae().field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(Minecraft.func_71410_x().func_175598_ae().field_78732_j, 1.0f, 0.0f, 0.0f);
    }
    
    public static boolean renderItemStack(final Minecraft mc, final ItemStack itm, final int x, final int y, final String txt) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final RenderItem itemRender = mc.func_175599_af();
        final boolean isLightingEnabled = GL11.glIsEnabled(2896);
        boolean rc = false;
        if (itm != null && !itm.func_190926_b()) {
            rc = true;
            final boolean isRescaleNormalEnabled = GL11.glIsEnabled(32826);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 32.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(32826);
            GL11.glEnable(2896);
            final short short1 = 240;
            final short short2 = 240;
            RenderHelper.func_74520_c();
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, short1 / 1.0f, short2 / 1.0f);
            itemRender.func_180450_b(itm, x, y);
            if (!UtilsFX.hideStackOverlay) {
                itemRender.func_180453_a(mc.field_71466_p, itm, x, y, txt);
            }
            GL11.glPopMatrix();
            if (isRescaleNormalEnabled) {
                GL11.glEnable(32826);
            }
            else {
                GL11.glDisable(32826);
            }
        }
        if (isLightingEnabled) {
            GL11.glEnable(2896);
        }
        else {
            GL11.glDisable(2896);
        }
        return rc;
    }
    
    public static boolean renderItemStackShaded(final Minecraft mc, final ItemStack itm, final int x, final int y, final String txt, final float shade) {
        GlStateManager.func_179131_c(shade, shade, shade, shade);
        final RenderItem itemRender = mc.func_175599_af();
        final boolean isLightingEnabled = GL11.glIsEnabled(2896);
        boolean rc = false;
        if (itm != null && !itm.func_190926_b()) {
            rc = true;
            final boolean isRescaleNormalEnabled = GL11.glIsEnabled(32826);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 32.0f);
            GlStateManager.func_179131_c(shade, shade, shade, shade);
            GL11.glEnable(32826);
            GL11.glEnable(2896);
            final short short1 = 240;
            final short short2 = 240;
            RenderHelper.func_74520_c();
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, short1 / 1.0f, short2 / 1.0f);
            itemRender.func_180450_b(itm, x, y);
            itemRender.func_180453_a(mc.field_71466_p, itm, x, y, txt);
            GL11.glPopMatrix();
            if (isRescaleNormalEnabled) {
                GL11.glEnable(32826);
            }
            else {
                GL11.glDisable(32826);
            }
        }
        if (isLightingEnabled) {
            GL11.glEnable(2896);
        }
        else {
            GL11.glDisable(2896);
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        return rc;
    }
    
    public static void drawBeam(final Vector S, final Vector E, final Vector P, final float width, final int bright) {
        drawBeam(S, E, P, width, bright, 1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawBeam(final Vector S, final Vector E, final Vector P, final float width, final int bright, final float r, final float g, final float b, final float a) {
        final Vector PS = Sub(S, P);
        final Vector SE = Sub(E, S);
        Vector normal = Cross(PS, SE);
        normal = normal.normalize();
        final Vector half = Mul(normal, width);
        final Vector p1 = Add(S, half);
        final Vector p2 = Sub(S, half);
        final Vector p3 = Add(E, half);
        final Vector p4 = Sub(E, half);
        drawQuad(Tessellator.func_178181_a(), p1, p3, p4, p2, bright, r, g, b, a);
    }
    
    public static void drawQuad(final Tessellator tessellator, final Vector p1, final Vector p2, final Vector p3, final Vector p4, final int bright, final float r, final float g, final float b, final float a) {
        final int j = bright >> 16 & 0xFFFF;
        final int k = bright & 0xFFFF;
        tessellator.func_178180_c().func_181662_b((double)p1.getX(), (double)p1.getY(), (double)p1.getZ()).func_187315_a(0.0, 0.0).func_187314_a(j, k).func_181666_a(r, g, b, a).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)p2.getX(), (double)p2.getY(), (double)p2.getZ()).func_187315_a(1.0, 0.0).func_187314_a(j, k).func_181666_a(r, g, b, a).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)p3.getX(), (double)p3.getY(), (double)p3.getZ()).func_187315_a(1.0, 1.0).func_187314_a(j, k).func_181666_a(r, g, b, a).func_181675_d();
        tessellator.func_178180_c().func_181662_b((double)p4.getX(), (double)p4.getY(), (double)p4.getZ()).func_187315_a(0.0, 1.0).func_187314_a(j, k).func_181666_a(r, g, b, a).func_181675_d();
    }
    
    private static Vector Cross(final Vector a, final Vector b) {
        final float x = a.y * b.z - a.z * b.y;
        final float y = a.z * b.x - a.x * b.z;
        final float z = a.x * b.y - a.y * b.x;
        return new Vector(x, y, z);
    }
    
    public static Vector Sub(final Vector a, final Vector b) {
        return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    private static Vector Add(final Vector a, final Vector b) {
        return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    private static Vector Mul(final Vector a, final float f) {
        return new Vector(a.x * f, a.y * f, a.z * f);
    }
    
    public static void renderItemIn2D(final String sprite, final float thickness) {
        renderItemIn2D(Minecraft.func_71410_x().func_147117_R().func_110572_b(sprite), thickness);
    }
    
    public static void renderItemIn2D(final TextureAtlasSprite icon, final float thickness) {
        GL11.glPushMatrix();
        final float f1 = icon.func_94212_f();
        final float f2 = icon.func_94206_g();
        final float f3 = icon.func_94209_e();
        final float f4 = icon.func_94210_h();
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
        renderTextureIn3D(f1, f2, f3, f4, 16, 16, thickness);
        GL11.glPopMatrix();
    }
    
    public static void renderTextureIn3D(final float maxu, final float maxv, final float minu, final float minv, final int width, final int height, final float thickness) {
        final Tessellator tess = Tessellator.func_178181_a();
        final BufferBuilder wr = tess.func_178180_c();
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        wr.func_181662_b(0.0, 0.0, 0.0).func_187315_a((double)maxu, (double)minv).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
        wr.func_181662_b(1.0, 0.0, 0.0).func_187315_a((double)minu, (double)minv).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
        wr.func_181662_b(1.0, 1.0, 0.0).func_187315_a((double)minu, (double)maxv).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
        wr.func_181662_b(0.0, 1.0, 0.0).func_187315_a((double)maxu, (double)maxv).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
        tess.func_78381_a();
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        wr.func_181662_b(0.0, 1.0, (double)(0.0f - thickness)).func_187315_a((double)maxu, (double)maxv).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
        wr.func_181662_b(1.0, 1.0, (double)(0.0f - thickness)).func_187315_a((double)minu, (double)maxv).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
        wr.func_181662_b(1.0, 0.0, (double)(0.0f - thickness)).func_187315_a((double)minu, (double)minv).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
        wr.func_181662_b(0.0, 0.0, (double)(0.0f - thickness)).func_187315_a((double)maxu, (double)minv).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
        tess.func_78381_a();
        final float f5 = 0.5f * (maxu - minu) / width;
        final float f6 = 0.5f * (minv - maxv) / height;
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        for (int k = 0; k < width; ++k) {
            final float f7 = k / width;
            final float f8 = maxu + (minu - maxu) * f7 - f5;
            wr.func_181662_b((double)f7, 0.0, (double)(0.0f - thickness)).func_187315_a((double)f8, (double)minv).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f7, 0.0, 0.0).func_187315_a((double)f8, (double)minv).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f7, 1.0, 0.0).func_187315_a((double)f8, (double)maxv).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f7, 1.0, (double)(0.0f - thickness)).func_187315_a((double)f8, (double)maxv).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
        }
        tess.func_78381_a();
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        for (int k = 0; k < width; ++k) {
            final float f7 = k / width;
            final float f8 = maxu + (minu - maxu) * f7 - f5;
            final float f9 = f7 + 1.0f / width;
            wr.func_181662_b((double)f9, 1.0, (double)(0.0f - thickness)).func_187315_a((double)f8, (double)maxv).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f9, 1.0, 0.0).func_187315_a((double)f8, (double)maxv).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f9, 0.0, 0.0).func_187315_a((double)f8, (double)minv).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
            wr.func_181662_b((double)f9, 0.0, (double)(0.0f - thickness)).func_187315_a((double)f8, (double)minv).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
        }
        tess.func_78381_a();
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        for (int k = 0; k < height; ++k) {
            final float f7 = k / height;
            final float f8 = minv + (maxv - minv) * f7 - f6;
            final float f9 = f7 + 1.0f / height;
            wr.func_181662_b(0.0, (double)f9, 0.0).func_187315_a((double)maxu, (double)f8).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(1.0, (double)f9, 0.0).func_187315_a((double)minu, (double)f8).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(1.0, (double)f9, (double)(0.0f - thickness)).func_187315_a((double)minu, (double)f8).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(0.0, (double)f9, (double)(0.0f - thickness)).func_187315_a((double)maxu, (double)f8).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
        }
        tess.func_78381_a();
        wr.func_181668_a(7, DefaultVertexFormats.field_181710_j);
        for (int k = 0; k < height; ++k) {
            final float f7 = k / height;
            final float f8 = minv + (maxv - minv) * f7 - f6;
            wr.func_181662_b(1.0, (double)f7, 0.0).func_187315_a((double)minu, (double)f8).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(0.0, (double)f7, 0.0).func_187315_a((double)maxu, (double)f8).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(0.0, (double)f7, (double)(0.0f - thickness)).func_187315_a((double)maxu, (double)f8).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(1.0, (double)f7, (double)(0.0f - thickness)).func_187315_a((double)minu, (double)f8).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
        }
        tess.func_78381_a();
    }
    
    static {
        nodeTexture = new ResourceLocation("thaumcraft", "textures/misc/auranodes.png");
        VERTEXFORMAT_POS_TEX_CO_LM_NO = new VertexFormat().func_181721_a(DefaultVertexFormats.field_181713_m).func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n).func_181721_a(DefaultVertexFormats.field_181716_p).func_181721_a(DefaultVertexFormats.field_181717_q).func_181721_a(DefaultVertexFormats.field_181718_r);
        colorNames = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
        colorCodes = new String[] { "§f", "§6", "§d", "§9", "§e", "§a", "§d", "§8", "§7", "§b", "§5", "§9", "§4", "§2", "§c", "§8" };
        colors = new int[] { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131, 10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
        UtilsFX.sysPartialTicks = 0.0f;
        UtilsFX.myFormatter = new DecimalFormat("#######.##");
        UtilsFX.hideStackOverlay = false;
    }
    
    public static class Vector
    {
        public final float x;
        public final float y;
        public final float z;
        
        public Vector(final float x, final float y, final float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public float getZ() {
            return this.z;
        }
        
        public float norm() {
            return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        }
        
        public Vector normalize() {
            final float n = this.norm();
            return new Vector(this.x / n, this.y / n, this.z / n);
        }
    }
}
