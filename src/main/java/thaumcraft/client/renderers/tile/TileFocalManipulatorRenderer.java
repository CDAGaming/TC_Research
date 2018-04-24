package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.client.fx.*;
import java.awt.*;
import thaumcraft.client.lib.*;
import thaumcraft.api.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileFocalManipulatorRenderer extends TileEntitySpecialRenderer
{
    EntityItem entityitem;
    
    public TileFocalManipulatorRenderer() {
        this.entityitem = null;
    }
    
    public void renderTileEntityAt(final TileFocalManipulator table, final double par2, final double par4, final double par6, final float par8) {
        if (table.func_145831_w() != null) {
            final float ticks = Minecraft.func_71410_x().func_175606_aa().field_70173_aa + par8;
            if (table.func_70301_a(0) != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 0.8f, (float)par6 + 0.5f);
                GL11.glRotatef(ticks % 360.0f, 0.0f, 1.0f, 0.0f);
                final ItemStack is = table.func_70301_a(0).func_77946_l();
                this.entityitem = new EntityItem(table.func_145831_w(), 0.0, 0.0, 0.0, is);
                this.entityitem.field_70290_d = MathHelper.func_76126_a(ticks / 14.0f) * 0.2f + 0.2f;
                final RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
                rendermanager.func_188391_a((Entity)this.entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
                GL11.glPopMatrix();
            }
            if (table.crystalsSync.getAspects().length > 0) {
                final int q = table.crystalsSync.getAspects().length;
                final float ang = 360 / q;
                for (int a = 0; a < q; ++a) {
                    final float angle = ticks % 720.0f / 2.0f + ang * a;
                    final float bob = MathHelper.func_76126_a((ticks + a * 10) / 12.0f) * 0.02f + 0.02f;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 1.3f, (float)par6 + 0.5f);
                    GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, bob, 0.4f);
                    GL11.glRotatef(-angle, 0.0f, 1.0f, 0.0f);
                    this.func_147499_a(ParticleEngine.particleTexture);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 1);
                    GL11.glAlphaFunc(516, 0.003921569f);
                    GL11.glDepthMask(false);
                    final Color c = new Color(table.crystalsSync.getAspects()[a].getColor());
                    final float r = c.getRed() / 255.0f;
                    final float g = c.getGreen() / 255.0f;
                    final float b = c.getBlue() / 255.0f;
                    GL11.glColor4f(r, g, b, 0.66f);
                    UtilsFX.renderBillboardQuad(0.17499999701976776, 64, 64, 320 + Minecraft.func_71410_x().func_175606_aa().field_70173_aa % 16);
                    GL11.glDepthMask(true);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3042);
                    GlStateManager.func_179092_a(516, 0.1f);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 1.05f, (float)par6 + 0.5f);
                    GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, bob, 0.4f);
                    GL11.glScaled(0.5, 0.5, 0.5);
                    final ItemStack is2 = ThaumcraftApiHelper.makeCrystal(table.crystalsSync.getAspects()[a]);
                    this.entityitem = new EntityItem(table.func_145831_w(), 0.0, 0.0, 0.0, is2);
                    this.entityitem.field_70290_d = 0.0f;
                    this.renderRay(angle, a, bob, r, g, b, ticks);
                    this.renderRay(angle, (a + 1) * 5, bob, r, g, b, ticks);
                    final RenderManager rendermanager2 = Minecraft.func_71410_x().func_175598_ae();
                    rendermanager2.func_188391_a((Entity)this.entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    private void renderRay(final float angle, final int num, final float lift, final float r, final float g, final float b, final float ticks) {
        final Random random = new Random(187L + num * num);
        GL11.glPushMatrix();
        final float pan = MathHelper.func_76126_a((ticks + num * 10) / 15.0f) * 15.0f;
        final float aparture = MathHelper.func_76126_a((ticks + num * 10) / 14.0f) * 2.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder wr = tessellator.func_178180_c();
        RenderHelper.func_74518_a();
        GL11.glTranslatef(0.0f, 0.475f + lift, 0.0f);
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        GL11.glEnable(2884);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
        GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotated((double)pan, 1.0, 0.0, 0.0);
        wr.func_181668_a(6, DefaultVertexFormats.field_181706_f);
        float fa = random.nextFloat() * 20.0f + 10.0f;
        float f4 = random.nextFloat() * 4.0f + 6.0f + aparture;
        fa /= 30.0f / (Math.min(ticks, 10.0f) / 10.0f);
        f4 /= 30.0f / (Math.min(ticks, 10.0f) / 10.0f);
        wr.func_181662_b(0.0, 0.0, 0.0).func_181666_a(r, g, b, 0.66f).func_181675_d();
        wr.func_181662_b(-0.8 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(r, g, b, 0.0f).func_181675_d();
        wr.func_181662_b(0.8 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(r, g, b, 0.0f).func_181675_d();
        wr.func_181662_b(0.0, (double)fa, (double)(1.0f * f4)).func_181666_a(r, g, b, 0.0f).func_181675_d();
        wr.func_181662_b(-0.8 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(r, g, b, 0.0f).func_181675_d();
        tessellator.func_78381_a();
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(2884);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        RenderHelper.func_74519_b();
        GL11.glPopMatrix();
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileFocalManipulator)te, x, y, z, partialTicks);
    }
}
