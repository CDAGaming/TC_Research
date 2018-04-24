package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import thaumcraft.common.tiles.devices.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import thaumcraft.client.lib.*;
import java.awt.*;

public class TileDioptraRenderer extends TileEntitySpecialRenderer
{
    private final ResourceLocation gridTexture;
    private final ResourceLocation sideTexture;
    private final float[] alphas;
    
    public TileDioptraRenderer() {
        this.gridTexture = new ResourceLocation("thaumcraft", "textures/misc/gridblock.png");
        this.sideTexture = new ResourceLocation("thaumcraft", "textures/models/dioptra_side.png");
        this.alphas = new float[] { 0.9f, 0.9f, 0.9f, 0.9f };
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        final TileDioptra tco = (TileDioptra)te;
        final Tessellator tessellator = Tessellator.func_178181_a();
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final float t = (this.field_147501_a.field_147551_g != null) ? (this.field_147501_a.field_147551_g.field_70173_aa + partialTicks) : 0.0f;
        float rc = 1.0f;
        float gc = 1.0f;
        float bc = 1.0f;
        if (BlockStateUtils.isEnabled(tco.func_145832_p())) {
            rc = MathHelper.func_76126_a(t / 12.0f) * 0.05f + 0.85f;
            gc = MathHelper.func_76126_a(t / 11.0f) * 0.05f + 0.9f;
            bc = MathHelper.func_76126_a(t / 10.0f) * 0.05f + 0.95f;
        }
        else {
            rc = MathHelper.func_76126_a(t / 12.0f) * 0.05f + 0.85f;
            gc = MathHelper.func_76126_a(t / 11.0f) * 0.05f + 0.45f;
            bc = MathHelper.func_76126_a(t / 10.0f) * 0.05f + 0.95f;
        }
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 1);
        GL11.glPushMatrix();
        GL11.glTranslated(-0.495, 0.501, -0.495);
        this.func_147499_a(this.gridTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glScaled(0.99, 1.0, 0.99);
        for (int a = 0; a < 12; ++a) {
            for (int b = 0; b < 12; ++b) {
                final int[] colors = this.calcColorMap(new float[] { 0.0f, 0.0f, 0.0f, 0.0f }, rc, gc, bc);
                final double d3 = a - 6;
                final double d4 = b - 6;
                final double dis = Math.sqrt(d3 * d3 + d4 * d4);
                final float s = MathHelper.func_76126_a((float)((tco.counter - dis * 10.0) / 8.0));
                TexturedQuadTC quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(a / 12.0f, tco.grid_amt[a + b * 13] / 96.0f, b / 12.0f, 0.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, tco.grid_amt[a + 1 + b * 13] / 96.0f, b / 12.0f, 1.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, tco.grid_amt[a + 1 + (b + 1) * 13] / 96.0f, (b + 1) / 12.0f, 1.0f, 0.0f), new PositionTextureVertex(a / 12.0f, tco.grid_amt[a + (b + 1) * 13] / 96.0f, (b + 1) / 12.0f, 0.0f, 0.0f) });
                quad.flipFace();
                quad.draw(tessellator.func_178180_c(), 1.0f, (int)(200.0f + s * 15.0f), colors, this.alphas);
                if (a == 0) {
                    quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(0.0f, 0.0f, b / 12.0f, 0.0f, 1.0f), new PositionTextureVertex(0.0f, tco.grid_amt[b * 13] / 96.0f, b / 12.0f, 1.0f, 1.0f), new PositionTextureVertex(0.0f, tco.grid_amt[(b + 1) * 13] / 96.0f, (b + 1) / 12.0f, 1.0f, 0.0f), new PositionTextureVertex(0.0f, 0.0f, (b + 1) / 12.0f, 0.0f, 0.0f) });
                    quad.flipFace();
                    quad.draw(tessellator.func_178180_c(), 1.0f, (int)(200.0f + s * 15.0f), colors, new float[] { 0.0f, 0.9f, 0.9f, 0.0f });
                }
                if (a == 11) {
                    quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(1.0f, 0.0f, b / 12.0f, 0.0f, 1.0f), new PositionTextureVertex(1.0f, tco.grid_amt[a + 1 + b * 13] / 96.0f, b / 12.0f, 1.0f, 1.0f), new PositionTextureVertex(1.0f, tco.grid_amt[a + 1 + (b + 1) * 13] / 96.0f, (b + 1) / 12.0f, 1.0f, 0.0f), new PositionTextureVertex(1.0f, 0.0f, (b + 1) / 12.0f, 0.0f, 0.0f) });
                    quad.draw(tessellator.func_178180_c(), 1.0f, (int)(200.0f + s * 15.0f), colors, new float[] { 0.0f, 0.9f, 0.9f, 0.0f });
                }
                if (b == 0) {
                    quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(a / 12.0f, 0.0f, 0.0f, 0.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, 0.0f, 0.0f, 1.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, tco.grid_amt[a + 1] / 96.0f, 0.0f, 1.0f, 0.0f), new PositionTextureVertex(a / 12.0f, tco.grid_amt[a] / 96.0f, 0.0f, 0.0f, 0.0f) });
                    quad.flipFace();
                    quad.draw(tessellator.func_178180_c(), 1.0f, (int)(200.0f + s * 15.0f), colors, new float[] { 0.0f, 0.0f, 0.9f, 0.9f });
                }
                if (b == 11) {
                    quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(a / 12.0f, 0.0f, 1.0f, 0.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, 0.0f, 1.0f, 1.0f, 1.0f), new PositionTextureVertex((a + 1) / 12.0f, tco.grid_amt[a + 1 + (b + 1) * 13] / 96.0f, 1.0f, 1.0f, 0.0f), new PositionTextureVertex(a / 12.0f, tco.grid_amt[a + (b + 1) * 13] / 96.0f, 1.0f, 0.0f, 0.0f) });
                    quad.draw(tessellator.func_178180_c(), 1.0f, (int)(200.0f + s * 15.0f), colors, new float[] { 0.0f, 0.0f, 0.9f, 0.9f });
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GlStateManager.func_179129_p();
        GL11.glTranslated(0.0, 1.0, 0.0);
        GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
        for (int q = 0; q < 4; ++q) {
            GL11.glPushMatrix();
            GL11.glRotatef(90.0f * q, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(0.0, 0.0, -0.5);
            UtilsFX.renderQuadCentered(this.sideTexture, 1.0f, rc, gc, bc, 220, 1, 0.8f);
            GL11.glPopMatrix();
        }
        GlStateManager.func_179089_o();
        GL11.glPopMatrix();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    int[] calcColorMap(final float[] fs, final float r, final float g, final float b) {
        final int[] colors = { 0, 0, 0, 0 };
        for (int a = 0; a < 4; ++a) {
            float g2 = g;
            if (fs[a] > 0.0f) {
                final float ll = 1.0f - fs[a];
                g2 *= ll;
            }
            g2 = MathHelper.func_76131_a(g2, 0.0f, 1.0f);
            final Color color1 = new Color(r * 0.8f, g2, b);
            colors[a] = color1.getRGB();
        }
        return colors;
    }
}
