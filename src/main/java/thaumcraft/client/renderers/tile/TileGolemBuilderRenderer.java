package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.item.*;
import thaumcraft.client.lib.obj.*;
import thaumcraft.common.tiles.crafting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileGolemBuilderRenderer extends TileEntitySpecialRenderer
{
    private IModelCustom model;
    private static final ResourceLocation TM;
    private static final ResourceLocation TEX;
    EntityItem entityitem;
    
    public TileGolemBuilderRenderer() {
        this.entityitem = null;
        this.model = AdvancedModelLoader.loadModel(TileGolemBuilderRenderer.TM);
    }
    
    public void renderTileEntityAt(final TileGolemBuilder tile, final double par2, final double par4, final double par6, final float pt, final int destroyStage) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4, (float)par6 + 0.5f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_147499_a(TileGolemBuilderRenderer.TEX);
        if (destroyStage >= 0) {
            this.func_147499_a(TileGolemBuilderRenderer.field_178460_a[destroyStage]);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(5.0f, 5.0f, 2.0f);
            GlStateManager.func_179109_b(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.func_179128_n(5888);
        }
        else {
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glMatrixMode(5888);
        }
        final EnumFacing facing = BlockStateUtils.getFacing(tile.func_145832_p());
        if (tile.func_145831_w() != null) {
            switch (facing.ordinal()) {
                case 5: {
                    GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 4: {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
            }
        }
        this.model.renderAllExcept("press");
        GL11.glPushMatrix();
        final float h = tile.press;
        final double s = Math.sin(Math.toRadians(h)) * 0.625;
        GL11.glTranslated(0.0, -s, 0.0);
        this.model.renderPart("press");
        GL11.glPopMatrix();
        if (destroyStage >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
        }
        else {
            GL11.glMatrixMode(5890);
            GL11.glLoadIdentity();
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glMatrixMode(5888);
        }
        GL11.glTranslatef(-0.3125f, 0.625f, 1.3125f);
        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
        final TextureAtlasSprite icon = Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(Blocks.field_150353_l.func_176223_P());
        UtilsFX.renderQuadFromIcon(icon, 0.625f, 1.0f, 1.0f, 1.0f, 200, 771, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileGolemBuilder)te, x, y, z, partialTicks, destroyStage);
    }
    
    static {
        TM = new ResourceLocation("thaumcraft", "models/block/golembuilder.obj");
        TEX = new ResourceLocation("thaumcraft", "textures/blocks/golembuilder.png");
    }
}
