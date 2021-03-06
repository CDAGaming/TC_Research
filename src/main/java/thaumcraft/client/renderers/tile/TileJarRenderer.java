package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.block.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import thaumcraft.common.config.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import java.awt.*;
import net.minecraft.client.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileJarRenderer extends TileEntitySpecialRenderer
{
    private ModelJar model;
    private ModelBrain brain;
    private static final ResourceLocation TEX_LABEL;
    private static final ResourceLocation TEX_BRAIN;
    private static final ResourceLocation TEX_BRINE;
    
    public TileJarRenderer() {
        this.model = new ModelJar();
        this.brain = new ModelBrain();
    }
    
    public void renderTileEntityAt(final TileJar tile, final double x, final double y, final double z, final float f) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)x + 0.5f, (float)y + 0.01f, (float)z + 0.5f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (tile instanceof TileJarBrain) {
            this.renderBrain((TileJarBrain)tile, x, y, z, f);
        }
        else if (tile instanceof TileJarFillable) {
            GL11.glDisable(2896);
            if (((TileJarFillable)tile).blocked) {
                GL11.glPushMatrix();
                this.func_147499_a(TileJarRenderer.TEX_BRINE);
                GL11.glScaled(1.001, 1.001, 1.001);
                this.model.renderLidBrace();
                GL11.glPopMatrix();
            }
            if (ThaumcraftApiHelper.getConnectableTile(tile.func_145831_w(), tile.func_174877_v(), EnumFacing.UP) != null) {
                GL11.glPushMatrix();
                this.func_147499_a(TileJarRenderer.TEX_BRINE);
                GL11.glScaled(0.9, 1.0, 0.9);
                this.model.renderLidExtension();
                GL11.glPopMatrix();
            }
            if (((TileJarFillable)tile).aspectFilter != null) {
                GL11.glPushMatrix();
                GL11.glBlendFunc(770, 771);
                switch (((TileJarFillable)tile).facing) {
                    case 3: {
                        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                    case 5: {
                        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                    case 4: {
                        GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                }
                final float rot = (((TileJarFillable)tile).aspectFilter.getTag().hashCode() + tile.func_174877_v().func_177958_n() + ((TileJarFillable)tile).facing) % 4 - 2;
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, -0.4f, 0.315f);
                if (ModConfig.CONFIG_GRAPHICS.crooked) {
                    GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
                }
                UtilsFX.renderQuadCentered(TileJarRenderer.TEX_LABEL, 0.5f, 1.0f, 1.0f, 1.0f, -99, 771, 1.0f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, -0.4f, 0.316f);
                if (ModConfig.CONFIG_GRAPHICS.crooked) {
                    GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
                }
                GL11.glScaled(0.021, 0.021, 0.021);
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                UtilsFX.drawTag(-8, -8, ((TileJarFillable)tile).aspectFilter);
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
            if (((TileJarFillable)tile).amount > 0) {
                this.renderLiquid((TileJarFillable)tile, x, y, z, f);
            }
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    public void renderLiquid(final TileJarFillable te, final double x, final double y, final double z, final float f) {
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        final World world = te.func_145831_w();
        final RenderCubes renderBlocks = new RenderCubes();
        GL11.glDisable(2896);
        final float level = te.amount / 250.0f * 0.625f;
        final Tessellator t = Tessellator.func_178181_a();
        renderBlocks.setRenderBounds(0.25, 0.0625, 0.25, 0.75, 0.0625 + level, 0.75);
        t.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181711_k);
        Color co = new Color(0);
        if (te.aspect != null) {
            co = new Color(te.aspect.getColor());
        }
        final TextureAtlasSprite icon = Minecraft.func_71410_x().func_147117_R().func_110572_b("thaumcraft:blocks/animatedglow");
        this.func_147499_a(TextureMap.field_110575_b);
        renderBlocks.renderFaceYNeg(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        renderBlocks.renderFaceYPos(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        renderBlocks.renderFaceZNeg(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        renderBlocks.renderFaceZPos(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        renderBlocks.renderFaceXNeg(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        renderBlocks.renderFaceXPos(BlocksTC.jarNormal, -0.5, 0.0, -0.5, icon, co.getRed() / 255.0f, co.getGreen() / 255.0f, co.getBlue() / 255.0f, 200);
        t.func_78381_a();
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void renderBrain(final TileJarBrain te, final double x, final double y, final double z, final float f) {
        final float bob = MathHelper.func_76126_a(Minecraft.func_71410_x().field_71439_g.field_70173_aa / 14.0f) * 0.03f + 0.03f;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -0.8f + bob, 0.0f);
        float f2;
        for (f2 = te.rota - te.rotb; f2 >= 3.141593f; f2 -= 6.283185f) {}
        while (f2 < -3.141593f) {
            f2 += 6.283185f;
        }
        final float f3 = te.rotb + f2 * f;
        GL11.glRotatef(f3 * 180.0f / 3.141593f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        this.func_147499_a(TileJarRenderer.TEX_BRAIN);
        GL11.glScalef(0.4f, 0.4f, 0.4f);
        this.brain.render();
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.func_147499_a(TileJarRenderer.TEX_BRINE);
        this.model.renderBrine();
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileJar)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_LABEL = new ResourceLocation("thaumcraft", "textures/models/label.png");
        TEX_BRAIN = new ResourceLocation("thaumcraft", "textures/models/brain2.png");
        TEX_BRINE = new ResourceLocation("thaumcraft", "textures/models/jarbrine.png");
    }
}
