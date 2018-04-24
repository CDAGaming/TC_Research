package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.essentia.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;

public class TileTubeValveRenderer extends TileEntitySpecialRenderer
{
    private ModelTubeValve model;
    private static final ResourceLocation TEX_VALVE;
    
    public TileTubeValveRenderer() {
        this.model = new ModelTubeValve();
    }
    
    public void renderEntityAt(final TileTubeValve valve, final double x, final double y, final double z, final float fq) {
        this.func_147499_a(TileTubeValveRenderer.TEX_VALVE);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        if (valve.facing.func_96559_d() == 0) {
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
            GL11.glRotatef(90.0f, (float)valve.facing.func_96559_d(), 0.0f, 0.0f);
        }
        GL11.glRotatef(90.0f, (float)valve.facing.func_82601_c(), (float)valve.facing.func_96559_d(), (float)valve.facing.func_82599_e());
        GL11.glRotated(-valve.rotation * 1.5, 0.0, 1.0, 0.0);
        GL11.glTranslated(0.0, (double)(-0.03f - valve.rotation / 360.0f * 0.09f), 0.0);
        GL11.glPushMatrix();
        this.model.renderRing();
        GL11.glScaled(0.75, 1.0, 0.75);
        this.model.renderRod();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderEntityAt((TileTubeValve)te, x, y, z, partialTicks);
    }
    
    static {
        TEX_VALVE = new ResourceLocation("thaumcraft", "textures/models/valve.png");
    }
}
