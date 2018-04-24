package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraftforge.fml.client.*;
import thaumcraft.common.lib.utils.*;
import org.lwjgl.opengl.*;
import net.minecraft.world.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;

public class TilePatternCrafterRenderer extends TileEntitySpecialRenderer
{
    private ModelBoreBase model;
    private static final ResourceLocation TEX;
    private static final ResourceLocation ICON;
    
    public TilePatternCrafterRenderer() {
        this.model = new ModelBoreBase();
    }
    
    public void renderTileEntityAt(final TilePatternCrafter pc, final double x, final double y, final double z, final float fq) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        int f = 3;
        if (pc.func_145831_w() != null) {
            f = BlockStateUtils.getFacing(pc.func_145832_p()).ordinal();
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f, (float)z + 0.5f);
        switch (f) {
            case 5: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 4: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 2: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        GL11.glPushMatrix();
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(0.0f, 0.0f, -0.5f);
        UtilsFX.renderQuadCentered(TilePatternCrafterRenderer.TEX, 10, 1, pc.type, 0.5f, 1.0f, 1.0f, 1.0f, pc.func_145838_q().func_185484_c(pc.func_145831_w().func_180495_p(pc.func_174877_v()), (IBlockAccess)pc.func_145831_w(), pc.func_174877_v()), 771, 1.0f);
        GL11.glPopMatrix();
        mc.field_71446_o.func_110577_a(TilePatternCrafterRenderer.ICON);
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.2f, -0.40625f, 0.05f);
        GL11.glRotatef(-pc.rot % 360.0f, 0.0f, 0.0f, 1.0f);
        GL11.glScaled(0.5, 0.5, 1.0);
        GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
        UtilsFX.renderTextureIn3D(1.0f, 0.0f, 0.0f, 1.0f, 16, 16, 0.1f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.2f, -0.40625f, 0.05f);
        GL11.glRotatef(pc.rot % 360.0f, 0.0f, 0.0f, 1.0f);
        GL11.glScaled(0.5, 0.5, 1.0);
        GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
        UtilsFX.renderTextureIn3D(1.0f, 0.0f, 0.0f, 1.0f, 16, 16, 0.1f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TilePatternCrafter)te, x, y, z, partialTicks);
    }
    
    static {
        TEX = new ResourceLocation("thaumcraft", "textures/blocks/pattern_crafter_modes.png");
        ICON = new ResourceLocation("thaumcraft", "textures/misc/gear_brass.png");
    }
}
