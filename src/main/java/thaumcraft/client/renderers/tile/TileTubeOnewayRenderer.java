package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.api.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;

public class TileTubeOnewayRenderer extends TileEntitySpecialRenderer
{
    private ModelTubeValve model;
    private static final ResourceLocation TEX_VALVE;
    EnumFacing fd;
    
    public TileTubeOnewayRenderer() {
        this.fd = null;
        this.model = new ModelTubeValve();
    }
    
    public void renderEntityAt(final TileTubeOneway valve, final double x, final double y, final double z, final float fq) {
        this.func_147499_a(TileTubeOnewayRenderer.TEX_VALVE);
        if (valve.func_145831_w() != null && ThaumcraftApiHelper.getConnectableTile(valve.func_145831_w(), valve.func_174877_v(), valve.facing.func_176734_d()) == null) {
            return;
        }
        GL11.glPushMatrix();
        this.fd = valve.facing;
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        if (this.fd.func_96559_d() == 0) {
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
            GL11.glRotatef(90.0f, (float)this.fd.func_96559_d(), 0.0f, 0.0f);
        }
        GL11.glRotatef(90.0f, (float)this.fd.func_82601_c(), (float)this.fd.func_96559_d(), (float)this.fd.func_82599_e());
        GL11.glPushMatrix();
        GL11.glColor3f(0.45f, 0.5f, 1.0f);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glTranslated(0.0, -0.3199999928474426, 0.0);
        this.model.renderRod();
        GL11.glPopMatrix();
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderEntityAt((TileTubeOneway)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_VALVE = new ResourceLocation("thaumcraft", "textures/models/valve.png");
    }
}
