package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.block.*;
import thaumcraft.common.tiles.essentia.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileAlembicRenderer extends TileEntitySpecialRenderer
{
    private ModelBoreBase modelBore;
    private static final ResourceLocation TEX_LABEL;
    private static final ResourceLocation TEX_BORE;
    
    public TileAlembicRenderer() {
        this.modelBore = new ModelBoreBase();
    }
    
    public void renderTileEntityAt(final TileAlembic tile, final double x, final double y, final double z, final float f) {
        if (tile.aspectFilter != null) {
            GL11.glPushMatrix();
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef((float)x + 0.5f, (float)y, (float)z + 0.5f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            switch (tile.facing) {
                case 5: {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.5f, -0.376f);
            UtilsFX.renderQuadCentered(TileAlembicRenderer.TEX_LABEL, 0.44f, 1.0f, 1.0f, 1.0f, -99, 771, 1.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.5f, -0.377f);
            GL11.glScaled(0.02, 0.02, 0.02);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            UtilsFX.drawTag(-8, -8, tile.aspectFilter);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
        if (tile.func_145831_w() != null) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_147499_a(TileAlembicRenderer.TEX_BORE);
            for (final EnumFacing dir : EnumFacing.field_176754_o) {
                if (tile.canOutputTo(dir)) {
                    final TileEntity te = ThaumcraftApiHelper.getConnectableTile(tile.func_145831_w(), tile.func_174877_v(), dir);
                    if (te != null && ((IEssentiaTransport)te).canInputFrom(dir.func_176734_d())) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)x + 0.5f, (float)y, (float)z + 0.5f);
                        switch (dir.ordinal()) {
                            case 0: {
                                GL11.glTranslatef(-0.5f, 0.5f, 0.0f);
                                GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
                                break;
                            }
                            case 1: {
                                GL11.glTranslatef(0.5f, 0.5f, 0.0f);
                                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                                break;
                            }
                            case 2: {
                                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                                break;
                            }
                            case 3: {
                                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                                break;
                            }
                            case 4: {
                                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                                break;
                            }
                            case 5: {
                                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
                                break;
                            }
                        }
                        this.modelBore.renderNozzle();
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileAlembic)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_LABEL = new ResourceLocation("thaumcraft", "textures/models/label.png");
        TEX_BORE = new ResourceLocation("thaumcraft", "textures/models/bore.png");
    }
}
