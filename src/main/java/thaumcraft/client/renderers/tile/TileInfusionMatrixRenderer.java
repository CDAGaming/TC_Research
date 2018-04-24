package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.client.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;

@SideOnly(Side.CLIENT)
public class TileInfusionMatrixRenderer extends TileEntitySpecialRenderer<TileInfusionMatrix>
{
    private ModelCube model;
    private ModelCube model_over;
    private static final ResourceLocation tex1;
    private static final ResourceLocation tex2;
    private static final ResourceLocation tex3;
    
    public TileInfusionMatrixRenderer() {
        this.model = new ModelCube(0);
        this.model_over = new ModelCube(32);
    }
    
    private void drawHalo(final TileEntity is, final double x, final double y, final double z, final float par8, final int count) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        final int q = FMLClientHandler.instance().getClient().field_71474_y.field_74347_j ? 20 : 10;
        final Tessellator tessellator = Tessellator.func_178181_a();
        RenderHelper.func_74518_a();
        final float f1 = count / 500.0f;
        final float f2 = 0.9f;
        final float f3 = 0.0f;
        final Random random = new Random(245L);
        GL11.glDisable(3553);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glDisable(3008);
        GL11.glEnable(2884);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        for (int i = 0; i < q; ++i) {
            GL11.glRotatef(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(random.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(random.nextFloat() * 360.0f + f1 * 360.0f, 0.0f, 0.0f, 1.0f);
            tessellator.func_178180_c().func_181668_a(6, DefaultVertexFormats.field_181706_f);
            float fa = random.nextFloat() * 20.0f + 5.0f + f3 * 10.0f;
            float f4 = random.nextFloat() * 2.0f + 1.0f + f3 * 2.0f;
            fa /= 20.0f / (Math.min(count, 50) / 50.0f);
            f4 /= 20.0f / (Math.min(count, 50) / 50.0f);
            tessellator.func_178180_c().func_181662_b(0.0, 0.0, 0.0).func_181669_b(255, 255, 255, (int)(255.0f * (1.0f - f1))).func_181675_d();
            tessellator.func_178180_c().func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181669_b(255, 0, 255, 0).func_181675_d();
            tessellator.func_178180_c().func_181662_b(0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181669_b(255, 0, 255, 0).func_181675_d();
            tessellator.func_178180_c().func_181662_b(0.0, (double)fa, (double)(1.0f * f4)).func_181669_b(255, 0, 255, 0).func_181675_d();
            tessellator.func_178180_c().func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181669_b(255, 0, 255, 0).func_181675_d();
            tessellator.func_78381_a();
        }
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(2884);
        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        RenderHelper.func_74519_b();
        GL11.glBlendFunc(770, 771);
        GL11.glPopMatrix();
    }
    
    public void renderInfusionMatrix(final TileInfusionMatrix is, final double par2, final double par4, final double par6, final float par8, final int destroyStage) {
        GL11.glPushMatrix();
        ResourceLocation t = TileInfusionMatrixRenderer.tex1;
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4 + 0.5f, (float)par6 + 0.5f);
        final float ticks = Minecraft.func_71410_x().func_175606_aa().field_70173_aa + par8;
        int inst = 0;
        int craftcount = 0;
        float startup = 0.0f;
        boolean active = false;
        boolean crafting = false;
        if (is != null && is.func_145831_w() != null) {
            GL11.glRotatef(ticks % 360.0f * is.startUp, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(35.0f * is.startUp, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f * is.startUp, 0.0f, 0.0f, 1.0f);
            final IBlockState bs = is.func_145831_w().func_180495_p(is.func_174877_v().func_177982_a(-1, -2, -1));
            if (bs.func_177230_c() == BlocksTC.pillarAncient) {
                t = TileInfusionMatrixRenderer.tex2;
            }
            if (bs.func_177230_c() == BlocksTC.pillarEldritch) {
                t = TileInfusionMatrixRenderer.tex3;
            }
            inst = is.instability;
            craftcount = is.craftCount;
            startup = is.startUp;
            active = is.active;
            crafting = is.crafting;
        }
        this.func_147499_a(t);
        if (destroyStage >= 0) {
            this.func_147499_a(TileInfusionMatrixRenderer.field_178460_a[destroyStage]);
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(4.0f, 4.0f, 1.0f);
            GlStateManager.func_179109_b(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.func_179128_n(5888);
        }
        final float instability = Math.min(6.0f, 1.0f + inst * 0.66f * (Math.min(craftcount, 50) / 50.0f));
        float b1 = 0.0f;
        float b2 = 0.0f;
        float b3 = 0.0f;
        int aa = 0;
        int bb = 0;
        int cc = 0;
        for (int a = 0; a < 2; ++a) {
            for (int b4 = 0; b4 < 2; ++b4) {
                for (int c = 0; c < 2; ++c) {
                    if (active) {
                        b1 = MathHelper.func_76126_a((ticks + a * 10) / (15.0f - instability / 2.0f)) * 0.01f * startup * instability;
                        b2 = MathHelper.func_76126_a((ticks + b4 * 10) / (14.0f - instability / 2.0f)) * 0.01f * startup * instability;
                        b3 = MathHelper.func_76126_a((ticks + c * 10) / (13.0f - instability / 2.0f)) * 0.01f * startup * instability;
                    }
                    aa = ((a == 0) ? -1 : 1);
                    bb = ((b4 == 0) ? -1 : 1);
                    cc = ((c == 0) ? -1 : 1);
                    GL11.glPushMatrix();
                    GL11.glTranslatef(b1 + aa * 0.25f, b2 + bb * 0.25f, b3 + cc * 0.25f);
                    if (a > 0) {
                        GL11.glRotatef(90.0f, (float)a, 0.0f, 0.0f);
                    }
                    if (b4 > 0) {
                        GL11.glRotatef(90.0f, 0.0f, (float)b4, 0.0f);
                    }
                    if (c > 0) {
                        GL11.glRotatef(90.0f, 0.0f, 0.0f, (float)c);
                    }
                    GL11.glScaled(0.45, 0.45, 0.45);
                    this.model.render();
                    GL11.glPopMatrix();
                }
            }
        }
        if (active) {
            GL11.glPushMatrix();
            GL11.glAlphaFunc(516, 0.003921569f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            for (int a = 0; a < 2; ++a) {
                for (int b4 = 0; b4 < 2; ++b4) {
                    for (int c = 0; c < 2; ++c) {
                        b1 = MathHelper.func_76126_a((ticks + a * 10) / (15.0f - instability / 2.0f)) * 0.01f * startup * instability;
                        b2 = MathHelper.func_76126_a((ticks + b4 * 10) / (14.0f - instability / 2.0f)) * 0.01f * startup * instability;
                        b3 = MathHelper.func_76126_a((ticks + c * 10) / (13.0f - instability / 2.0f)) * 0.01f * startup * instability;
                        aa = ((a == 0) ? -1 : 1);
                        bb = ((b4 == 0) ? -1 : 1);
                        cc = ((c == 0) ? -1 : 1);
                        GL11.glPushMatrix();
                        GL11.glTranslatef(b1 + aa * 0.25f, b2 + bb * 0.25f, b3 + cc * 0.25f);
                        if (a > 0) {
                            GL11.glRotatef(90.0f, (float)a, 0.0f, 0.0f);
                        }
                        if (b4 > 0) {
                            GL11.glRotatef(90.0f, 0.0f, (float)b4, 0.0f);
                        }
                        if (c > 0) {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, (float)c);
                        }
                        GL11.glScaled(0.45, 0.45, 0.45);
                        final int j = 15728880;
                        final int k = j % 65536;
                        final int l = j / 65536;
                        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, k / 1.0f, l / 1.0f);
                        GL11.glColor4f(0.8f, 0.1f, 1.0f, (MathHelper.func_76126_a((ticks + a * 2 + b4 * 3 + c * 4) / 4.0f) * 0.1f + 0.2f) * startup);
                        this.model_over.render();
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glPopMatrix();
                    }
                }
            }
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1f);
            GL11.glPopMatrix();
        }
        if (destroyStage >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
        }
        GL11.glPopMatrix();
        if (crafting) {
            this.drawHalo(is, par2, par4, par6, par8, craftcount);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void render(final TileInfusionMatrix te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a((TileEntity)te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderInfusionMatrix(te, x, y, z, partialTicks, destroyStage);
    }
    
    static {
        tex1 = new ResourceLocation("thaumcraft", "textures/blocks/infuser_normal.png");
        tex2 = new ResourceLocation("thaumcraft", "textures/blocks/infuser_ancient.png");
        tex3 = new ResourceLocation("thaumcraft", "textures/blocks/infuser_eldritch.png");
    }
}
