package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.client.renderers.models.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.tiles.essentia.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;

public class TileBellowsRenderer extends TileEntitySpecialRenderer<TileBellows>
{
    private ModelBellows model;
    private ModelBoreBase model2;
    private static final ResourceLocation TEX;
    private static final ResourceLocation TEX_BORE;
    
    public TileBellowsRenderer() {
        this.model = new ModelBellows();
        this.model2 = new ModelBoreBase();
    }
    
    public void render(final TileBellows bellows, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a((TileEntity)bellows, x, y, z, partialTicks, destroyStage, alpha);
        float scale = 0.0f;
        EnumFacing dir = EnumFacing.WEST;
        boolean extension = false;
        if (bellows == null) {
            final EntityPlayer p = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
            scale = MathHelper.func_76126_a(p.field_70173_aa / 8.0f) * 0.3f + 0.7f;
        }
        else {
            scale = bellows.inflation;
            dir = BlockStateUtils.getFacing(bellows.func_145832_p());
            final TileEntity te = bellows.func_145831_w().func_175625_s(bellows.func_174877_v().func_177972_a(BlockStateUtils.getFacing(bellows.func_145832_p())));
            if (te != null && te instanceof TileTubeBuffer) {
                extension = true;
            }
        }
        final float tscale = 0.125f + scale * 0.875f;
        if (extension) {
            this.func_147499_a(TileBellowsRenderer.TEX_BORE);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 0.5f + dir.func_82601_c(), (float)y + dir.func_96559_d(), (float)z + 0.5f + dir.func_82599_e());
            switch (dir.func_176734_d().ordinal()) {
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
            this.model2.renderNozzle();
            GL11.glPopMatrix();
        }
        this.func_147499_a(TileBellowsRenderer.TEX);
        GL11.glPushMatrix();
        GL11.glEnable(2977);
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.translateFromOrientation((float)x, (float)y, (float)z, dir.ordinal());
        if (destroyStage >= 0) {
            this.func_147499_a(TileBellowsRenderer.field_178460_a[destroyStage]);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(4.0f, 4.0f, 1.0f);
            GlStateManager.func_179109_b(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.func_179128_n(5888);
        }
        GL11.glTranslatef(0.0f, 1.0f, 0.0f);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, (scale + 0.1f) / 2.0f, 0.5f);
        this.model.Bag.func_78793_a(0.0f, 0.5f, 0.0f);
        this.model.Bag.func_78785_a(0.0625f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -tscale / 2.0f + 0.5f, 0.0f);
        this.model.TopPlank.func_78785_a(0.0625f);
        GL11.glTranslatef(0.0f, tscale / 2.0f - 0.5f, 0.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, tscale / 2.0f - 0.5f, 0.0f);
        this.model.BottomPlank.func_78785_a(0.0625f);
        GL11.glTranslatef(0.0f, -tscale / 2.0f + 0.5f, 0.0f);
        GL11.glPopMatrix();
        this.model.render();
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        if (destroyStage >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
        }
    }
    
    private void translateFromOrientation(final double x, final double y, final double z, final int orientation) {
        GL11.glTranslatef((float)x + 0.5f, (float)y - 0.5f, (float)z + 0.5f);
        if (orientation == 0) {
            GL11.glTranslatef(0.0f, 1.0f, -1.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 1) {
            GL11.glTranslatef(0.0f, 1.0f, 1.0f);
            GL11.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 2) {
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 4) {
            GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 5) {
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        }
    }
    
    static {
        TEX = new ResourceLocation("thaumcraft", "textures/blocks/bellows.png");
        TEX_BORE = new ResourceLocation("thaumcraft", "textures/models/bore.png");
    }
}
