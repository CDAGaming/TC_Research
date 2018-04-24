package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.block.*;
import thaumcraft.common.tiles.crafting.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.items.*;
import thaumcraft.client.lib.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class TileResearchTableRenderer extends TileEntitySpecialRenderer
{
    private ModelResearchTable tableModel;
    private static final ResourceLocation TEX;
    
    public TileResearchTableRenderer() {
        this.tableModel = new ModelResearchTable();
    }
    
    public void renderTileEntityAt(final TileResearchTable table, final double par2, final double par4, final double par6, final float par8) {
        GL11.glPushMatrix();
        this.func_147499_a(TileResearchTableRenderer.TEX);
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 1.0f, (float)par6 + 0.5f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        switch (BlockStateUtils.getFacing(table.func_145832_p())) {
            case EAST: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case WEST: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case SOUTH: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
        }
        if (table.data != null) {
            this.tableModel.renderScroll(Aspect.ALCHEMY.getColor());
        }
        if (table.func_70301_a(0) != null && table.func_70301_a(0).func_77973_b() instanceof IScribeTools) {
            this.tableModel.renderInkwell();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(-0.5, 0.1, 0.125);
            GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScaled(0.5, 0.5, 0.5);
            UtilsFX.renderItemIn2D("thaumcraft:research/quill", 0.0625f);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }
    
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8, final int q) {
        this.renderTileEntityAt((TileResearchTable)par1TileEntity, par2, par4, par6, par8);
    }
    
    static {
        TEX = new ResourceLocation("thaumcraft", "textures/blocks/research_table_model.png");
    }
}
