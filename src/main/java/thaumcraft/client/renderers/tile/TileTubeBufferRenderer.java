package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.client.renderers.models.block.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;

public class TileTubeBufferRenderer extends TileEntitySpecialRenderer
{
    private ModelTubeValve model;
    private static final ResourceLocation TEX_VALVE;
    
    public TileTubeBufferRenderer() {
        this.model = new ModelTubeValve();
    }
    
    public void renderEntityAt(final TileTubeBuffer buffer, final double x, final double y, final double z, final float fq) {
        this.func_147499_a(TileTubeBufferRenderer.TEX_VALVE);
        if (buffer.func_145831_w() != null) {
            for (final EnumFacing dir : EnumFacing.field_82609_l) {
                if (buffer.chokedSides[dir.ordinal()] != 0 && buffer.openSides[dir.ordinal()]) {
                    if (ThaumcraftApiHelper.getConnectableTile(buffer.func_145831_w(), buffer.func_174877_v(), dir) != null) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
                        if (dir.func_176734_d().func_96559_d() == 0) {
                            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                        }
                        else {
                            GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
                            GL11.glRotatef(90.0f, (float)dir.func_176734_d().func_96559_d(), 0.0f, 0.0f);
                        }
                        GL11.glRotatef(90.0f, (float)dir.func_176734_d().func_82601_c(), (float)dir.func_176734_d().func_96559_d(), (float)dir.func_176734_d().func_82599_e());
                        GL11.glPushMatrix();
                        if (buffer.chokedSides[dir.ordinal()] == 2) {
                            GL11.glColor3f(1.0f, 0.3f, 0.3f);
                        }
                        else {
                            GL11.glColor3f(0.3f, 0.3f, 1.0f);
                        }
                        GL11.glScaled(2.0, 1.0, 2.0);
                        GL11.glTranslated(0.0, -0.5, 0.0);
                        this.model.renderRod();
                        GL11.glPopMatrix();
                        GL11.glColor3f(1.0f, 1.0f, 1.0f);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderEntityAt((TileTubeBuffer)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_VALVE = new ResourceLocation("thaumcraft", "textures/models/valve.png");
    }
}
