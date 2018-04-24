package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.common.tiles.crafting.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;

public class TileCrucibleRenderer extends TileEntitySpecialRenderer
{
    public void renderEntityAt(final TileCrucible cr, final double x, final double y, final double z, final float fq) {
        if (cr.tank.getFluidAmount() > 0) {
            this.renderFluid(cr, x, y, z);
        }
    }
    
    public void renderFluid(final TileCrucible cr, final double x, final double y, final double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + cr.getFluidHeight(), z + 1.0);
        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
        if (cr.tank.getFluidAmount() > 0) {
            final TextureAtlasSprite icon = Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(Blocks.field_150355_j.func_176223_P());
            final float n = cr.aspects.visSize();
            cr.getClass();
            float recolor = n / 1000.0f;
            if (recolor > 0.0f) {
                recolor = 0.5f + recolor / 2.0f;
            }
            if (recolor > 1.0f) {
                recolor = 1.0f;
            }
            UtilsFX.renderQuadFromIcon(icon, 1.0f, 1.0f - recolor / 3.0f, 1.0f - recolor, 1.0f - recolor / 2.0f, BlocksTC.crucible.func_185484_c(cr.func_145831_w().func_180495_p(cr.func_174877_v()), (IBlockAccess)cr.func_145831_w(), cr.func_174877_v()), 771, 1.0f);
        }
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderEntityAt((TileCrucible)te, x, y, z, partialTicks);
    }
}
