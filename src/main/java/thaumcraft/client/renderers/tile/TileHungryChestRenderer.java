package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileHungryChestRenderer extends TileEntitySpecialRenderer
{
    private ModelChest chestModel;
    private static final ResourceLocation textureNormal;
    
    public TileHungryChestRenderer() {
        this.chestModel = new ModelChest();
    }
    
    public void renderTileEntityChestAt(final TileHungryChest chest, final double par2, final double par4, final double par6, final float par8, final int bp) {
        int var9 = 0;
        if (!chest.func_145830_o()) {
            var9 = 0;
        }
        else {
            var9 = chest.func_145832_p();
        }
        final ModelChest var10 = this.chestModel;
        if (bp >= 0) {
            this.func_147499_a(TileHungryChestRenderer.field_178460_a[bp]);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(4.0f, 4.0f, 1.0f);
            GlStateManager.func_179109_b(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.func_179128_n(5888);
        }
        else {
            this.func_147499_a(TileHungryChestRenderer.textureNormal);
        }
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        if (bp < 0) {
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GL11.glTranslatef((float)par2, (float)par4 + 1.0f, (float)par6 + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        short var11 = 0;
        if (var9 == 2) {
            var11 = 180;
        }
        if (var9 == 3) {
            var11 = 0;
        }
        if (var9 == 4) {
            var11 = 90;
        }
        if (var9 == 5) {
            var11 = -90;
        }
        GL11.glRotatef((float)var11, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float var12 = chest.field_145986_n + (chest.field_145989_m - chest.field_145986_n) * par8;
        var12 = 1.0f - var12;
        var12 = 1.0f - var12 * var12 * var12;
        var10.field_78234_a.field_78795_f = -(var12 * 3.1415927f / 2.0f);
        var10.func_78231_a();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (bp >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
        }
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityChestAt((TileHungryChest)te, x, y, z, partialTicks, destroyStage);
    }
    
    static {
        textureNormal = new ResourceLocation("thaumcraft", "textures/models/chesthungry.png");
    }
}
