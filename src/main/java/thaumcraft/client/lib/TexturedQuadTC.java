package thaumcraft.client.lib;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.vertex.*;
import java.awt.*;
import net.minecraft.client.renderer.*;

public class TexturedQuadTC
{
    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    private boolean flipped;
    
    public TexturedQuadTC(final PositionTextureVertex[] vertices) {
        this.flipped = false;
        this.vertexPositions = vertices;
        this.nVertices = vertices.length;
    }
    
    public TexturedQuadTC(final PositionTextureVertex[] vertices, final int texcoordU1, final int texcoordV1, final int texcoordU2, final int texcoordV2, final float textureWidth, final float textureHeight) {
        this(vertices);
        final float f2 = 0.0f / textureWidth;
        final float f3 = 0.0f / textureHeight;
        vertices[0] = vertices[0].func_78240_a(texcoordU2 / textureWidth - f2, texcoordV1 / textureHeight + f3);
        vertices[1] = vertices[1].func_78240_a(texcoordU1 / textureWidth + f2, texcoordV1 / textureHeight + f3);
        vertices[2] = vertices[2].func_78240_a(texcoordU1 / textureWidth + f2, texcoordV2 / textureHeight - f3);
        vertices[3] = vertices[3].func_78240_a(texcoordU2 / textureWidth - f2, texcoordV2 / textureHeight - f3);
    }
    
    public void flipFace() {
        this.flipped = true;
        final PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];
        for (int i = 0; i < this.vertexPositions.length; ++i) {
            apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }
        this.vertexPositions = apositiontexturevertex;
    }
    
    public void draw(final BufferBuilder renderer, final float scale, final int bright, final int color, final float alpha) {
        if (bright != -99) {
            renderer.func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        }
        else {
            renderer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
        }
        final Color c = new Color(color);
        final int j = bright >> 16 & 0xFFFF;
        final int k = bright & 0xFFFF;
        for (int i = 0; i < 4; ++i) {
            final PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
            if (bright != -99) {
                renderer.func_181662_b(positiontexturevertex.field_78243_a.field_72450_a * scale, positiontexturevertex.field_78243_a.field_72448_b * scale, positiontexturevertex.field_78243_a.field_72449_c * scale).func_187315_a((double)positiontexturevertex.field_78241_b, (double)positiontexturevertex.field_78242_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255.0f)).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
            }
            else {
                renderer.func_181662_b(positiontexturevertex.field_78243_a.field_72450_a * scale, positiontexturevertex.field_78243_a.field_72448_b * scale, positiontexturevertex.field_78243_a.field_72449_c * scale).func_187315_a((double)positiontexturevertex.field_78241_b, (double)positiontexturevertex.field_78242_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255.0f)).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
            }
        }
        Tessellator.func_178181_a().func_78381_a();
    }
    
    public void draw(final BufferBuilder renderer, final float scale, final int bright, final int[] color, final float[] alpha) {
        if (bright != -99) {
            renderer.func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
        }
        else {
            renderer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
        }
        final int j = bright >> 16 & 0xFFFF;
        final int k = bright & 0xFFFF;
        for (int i = 0; i < 4; ++i) {
            final int idx = this.flipped ? (3 - i) : i;
            final Color c = new Color(color[idx]);
            final PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
            if (bright != -99) {
                renderer.func_181662_b(positiontexturevertex.field_78243_a.field_72450_a * scale, positiontexturevertex.field_78243_a.field_72448_b * scale, positiontexturevertex.field_78243_a.field_72449_c * scale).func_187315_a((double)positiontexturevertex.field_78241_b, (double)positiontexturevertex.field_78242_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha[idx] * 255.0f)).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
            }
            else {
                renderer.func_181662_b(positiontexturevertex.field_78243_a.field_72450_a * scale, positiontexturevertex.field_78243_a.field_72448_b * scale, positiontexturevertex.field_78243_a.field_72449_c * scale).func_187315_a((double)positiontexturevertex.field_78241_b, (double)positiontexturevertex.field_78242_c).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha[idx] * 255.0f)).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
            }
        }
        Tessellator.func_178181_a().func_78381_a();
    }
}
