package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.essentia.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class TileCentrifugeRenderer extends TileEntitySpecialRenderer
{
    private ModelCentrifuge model;
    private static final ResourceLocation TEX;
    
    public TileCentrifugeRenderer() {
        this.model = new ModelCentrifuge();
    }
    
    public void renderEntityAt(final TileCentrifuge cf, final double x, final double y, final double z, final float fq, final int destroyStage) {
        this.func_147499_a(TileCentrifugeRenderer.TEX);
        GL11.glPushMatrix();
        if (destroyStage >= 0) {
            this.func_147499_a(TileCentrifugeRenderer.field_178460_a[destroyStage]);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(4.0f, 4.0f, 1.0f);
            GlStateManager.func_179109_b(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.func_179128_n(5888);
        }
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        this.model.renderBoxes();
        GL11.glRotated((double)cf.rotation, 0.0, 1.0, 0.0);
        this.model.renderSpinnyBit();
        if (destroyStage >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
        }
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderEntityAt((TileCentrifuge)te, x, y, z, partialTicks, destroyStage);
    }
    
    static {
        TEX = new ResourceLocation("thaumcraft", "textures/models/centrifuge.png");
    }
}
