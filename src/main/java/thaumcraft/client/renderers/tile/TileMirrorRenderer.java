package thaumcraft.client.renderers.tile;

import java.nio.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.tileentity.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import thaumcraft.client.lib.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.util.*;

public class TileMirrorRenderer extends TileEntitySpecialRenderer
{
    FloatBuffer fBuffer;
    private static final ResourceLocation t1;
    private static final ResourceLocation t2;
    private static ResourceLocation mp;
    private static ResourceLocation mpt;
    
    public TileMirrorRenderer() {
        this.fBuffer = GLAllocation.func_74529_h(16);
    }
    
    public void drawPlaneYPos(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float px = (float)TileEntityRendererDispatcher.field_147554_b;
        final float py = (float)TileEntityRendererDispatcher.field_147555_c;
        final float pz = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.99f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f2 = 16 - i;
            float f3 = 0.0625f;
            float f4 = 1.0f / (f2 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f4 = 0.1f;
                f2 = 65.0f;
                f3 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f3 = 0.5f;
            }
            final float f5 = (float)(y + offset);
            final float f6 = (float)(f5 - ActiveRenderInfo.getCameraPosition().field_72448_b);
            final float f7 = (float)(f5 + f2 - ActiveRenderInfo.getCameraPosition().field_72448_b);
            float f8 = f6 / f7;
            f8 += (float)(y + offset);
            GL11.glTranslatef(px, f8, pz);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f3, f3, f3);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-px, -pz, -py);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72450_a * f2 / f6, ActiveRenderInfo.getCameraPosition().field_72449_c * f2 / f6, (double)(-py));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f8 = random.nextFloat() * 0.5f + 0.1f;
            float f9 = random.nextFloat() * 0.5f + 0.4f;
            float f10 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f9 = (f8 = (f10 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + p, y + offset, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + p, y + offset, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + offset, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + offset, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    public void drawPlaneYNeg(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float f2 = (float)TileEntityRendererDispatcher.field_147554_b;
        final float f3 = (float)TileEntityRendererDispatcher.field_147555_c;
        final float f4 = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.01f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f5 = 16 - i;
            float f6 = 0.0625f;
            float f7 = 1.0f / (f5 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f7 = 0.1f;
                f5 = 65.0f;
                f6 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f6 = 0.5f;
            }
            final float f8 = (float)(-(y + offset));
            final float f9 = (float)(f8 + ActiveRenderInfo.getCameraPosition().field_72448_b);
            final float f10 = (float)(f8 + f5 + ActiveRenderInfo.getCameraPosition().field_72448_b);
            float f11 = f9 / f10;
            f11 += (float)(y + offset);
            GL11.glTranslatef(f2, f11, f4);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-f2, -f4, -f3);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72450_a * f5 / f9, ActiveRenderInfo.getCameraPosition().field_72449_c * f5 / f9, (double)(-f3));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f11 = random.nextFloat() * 0.5f + 0.1f;
            float f12 = random.nextFloat() * 0.5f + 0.4f;
            float f13 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f12 = (f11 = (f13 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + p, y + offset, z + p).func_181666_a(f11 * f7, f12 * f7, f13 * f7, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + p, y + offset, z + 1.0 - p).func_181666_a(f11 * f7, f12 * f7, f13 * f7, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + offset, z + 1.0 - p).func_181666_a(f11 * f7, f12 * f7, f13 * f7, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + offset, z + p).func_181666_a(f11 * f7, f12 * f7, f13 * f7, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    public void drawPlaneZNeg(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float px = (float)TileEntityRendererDispatcher.field_147554_b;
        final float py = (float)TileEntityRendererDispatcher.field_147555_c;
        final float pz = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.01f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f2 = 16 - i;
            float f3 = 0.0625f;
            float f4 = 1.0f / (f2 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f4 = 0.1f;
                f2 = 65.0f;
                f3 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f3 = 0.5f;
            }
            final float f5 = (float)(-(z + offset));
            final float f6 = (float)(f5 + ActiveRenderInfo.getCameraPosition().field_72449_c);
            final float f7 = (float)(f5 + f2 + ActiveRenderInfo.getCameraPosition().field_72449_c);
            float f8 = f6 / f7;
            f8 += (float)(z + offset);
            GL11.glTranslatef(px, py, f8);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f3, f3, f3);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-px, -py, -pz);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72450_a * f2 / f6, ActiveRenderInfo.getCameraPosition().field_72448_b * f2 / f6, (double)(-pz));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f8 = random.nextFloat() * 0.5f + 0.1f;
            float f9 = random.nextFloat() * 0.5f + 0.4f;
            float f10 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f9 = (f8 = (f10 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + p, y + 1.0 - p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + p, y + p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + 1.0 - p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    public void drawPlaneZPos(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float px = (float)TileEntityRendererDispatcher.field_147554_b;
        final float py = (float)TileEntityRendererDispatcher.field_147555_c;
        final float pz = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.99f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f2 = 16 - i;
            float f3 = 0.0625f;
            float f4 = 1.0f / (f2 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f4 = 0.1f;
                f2 = 65.0f;
                f3 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f3 = 0.5f;
            }
            final float f5 = (float)(z + offset);
            final float f6 = (float)(f5 - ActiveRenderInfo.getCameraPosition().field_72449_c);
            final float f7 = (float)(f5 + f2 - ActiveRenderInfo.getCameraPosition().field_72449_c);
            float f8 = f6 / f7;
            f8 += (float)(z + offset);
            GL11.glTranslatef(px, py, f8);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f3, f3, f3);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-px, -py, -pz);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72450_a * f2 / f6, ActiveRenderInfo.getCameraPosition().field_72448_b * f2 / f6, (double)(-pz));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f8 = random.nextFloat() * 0.5f + 0.1f;
            float f9 = random.nextFloat() * 0.5f + 0.4f;
            float f10 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f9 = (f8 = (f10 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + p, y + p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + p, y + 1.0 - p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + 1.0 - p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + 1.0 - p, y + p, z + offset).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    public void drawPlaneXNeg(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float px = (float)TileEntityRendererDispatcher.field_147554_b;
        final float py = (float)TileEntityRendererDispatcher.field_147555_c;
        final float pz = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.01f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f2 = 16 - i;
            float f3 = 0.0625f;
            float f4 = 1.0f / (f2 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f4 = 0.1f;
                f2 = 65.0f;
                f3 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f3 = 0.5f;
            }
            final float f5 = (float)(-(x + offset));
            final float f6 = (float)(f5 + ActiveRenderInfo.getCameraPosition().field_72450_a);
            final float f7 = (float)(f5 + f2 + ActiveRenderInfo.getCameraPosition().field_72450_a);
            float f8 = f6 / f7;
            f8 += (float)(x + offset);
            GL11.glTranslatef(f8, py, pz);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f3, f3, f3);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-pz, -py, -px);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72449_c * f2 / f6, ActiveRenderInfo.getCameraPosition().field_72448_b * f2 / f6, (double)(-px));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f8 = random.nextFloat() * 0.5f + 0.1f;
            float f9 = random.nextFloat() * 0.5f + 0.4f;
            float f10 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f9 = (f8 = (f10 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + offset, y + 1.0 - p, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + 1.0 - p, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + p, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + p, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    public void drawPlaneXPos(final TileEntity tileentityendportal, final double x, final double y, final double z, final float f) {
        final float px = (float)TileEntityRendererDispatcher.field_147554_b;
        final float py = (float)TileEntityRendererDispatcher.field_147555_c;
        final float pz = (float)TileEntityRendererDispatcher.field_147552_d;
        GL11.glDisable(2896);
        final Random random = new Random(31100L);
        final float offset = 0.99f;
        final float p = 0.1875f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f2 = 16 - i;
            float f3 = 0.0625f;
            float f4 = 1.0f / (f2 + 1.0f);
            if (i == 0) {
                this.func_147499_a(TileMirrorRenderer.t1);
                f4 = 0.1f;
                f2 = 65.0f;
                f3 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
                this.func_147499_a(TileMirrorRenderer.t2);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                f3 = 0.5f;
            }
            final float f5 = (float)(x + offset);
            final float f6 = (float)(f5 - ActiveRenderInfo.getCameraPosition().field_72450_a);
            final float f7 = (float)(f5 + f2 - ActiveRenderInfo.getCameraPosition().field_72450_a);
            float f8 = f6 / f7;
            f8 += (float)(x + offset);
            GL11.glTranslatef(f8, py, pz);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 250000.0f, 0.0f);
            GL11.glScalef(f3, f3, f3);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-pz, -py, -px);
            GL11.glTranslated(ActiveRenderInfo.getCameraPosition().field_72449_c * f2 / f6, ActiveRenderInfo.getCameraPosition().field_72448_b * f2 / f6, (double)(-px));
            final Tessellator tessellator = Tessellator.func_178181_a();
            tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181706_f);
            f8 = random.nextFloat() * 0.5f + 0.1f;
            float f9 = random.nextFloat() * 0.5f + 0.4f;
            float f10 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
                f9 = (f8 = (f10 = 1.0f));
            }
            tessellator.func_178180_c().func_181662_b(x + offset, y + p, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + p, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + 1.0 - p, z + 1.0 - p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(x + offset, y + 1.0 - p, z + p).func_181666_a(f8 * f4, f9 * f4, f10 * f4, 1.0f).func_181675_d();
            tessellator.func_78381_a();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    private FloatBuffer calcFloatBuffer(final float f, final float f1, final float f2, final float f3) {
        this.fBuffer.clear();
        this.fBuffer.put(f).put(f1).put(f2).put(f3);
        this.fBuffer.flip();
        return this.fBuffer;
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        final EnumFacing dir = BlockStateUtils.getFacing(te.func_145832_p());
        boolean linked = false;
        if (te instanceof TileMirror) {
            linked = ((TileMirror)te).linked;
        }
        if (te instanceof TileMirrorEssentia) {
            linked = ((TileMirrorEssentia)te).linked;
        }
        final int b = te.func_145838_q().func_185484_c(te.func_145831_w().func_180495_p(te.func_174877_v()), (IBlockAccess)te.func_145831_w(), te.func_174877_v());
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.translateFromOrientation((float)x, (float)y, (float)z, dir.ordinal(), 0.01f);
        UtilsFX.renderItemIn2D((te.func_145838_q() == BlocksTC.mirror) ? "thaumcraft:blocks/mirrorframe" : "thaumcraft:blocks/mirrorframe2", 0.0625f);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        if (linked && FMLClientHandler.instance().getClient().field_71439_g.func_174831_c(te.func_174877_v()) < 1024.0) {
            GL11.glPushMatrix();
            switch (dir) {
                case DOWN: {
                    this.drawPlaneYPos(te, x, y, z, partialTicks);
                    break;
                }
                case UP: {
                    this.drawPlaneYNeg(te, x, y, z, partialTicks);
                    break;
                }
                case WEST: {
                    this.drawPlaneXPos(te, x, y, z, partialTicks);
                    break;
                }
                case EAST: {
                    this.drawPlaneXNeg(te, x, y, z, partialTicks);
                    break;
                }
                case NORTH: {
                    this.drawPlaneZPos(te, x, y, z, partialTicks);
                    break;
                }
                case SOUTH: {
                    this.drawPlaneZNeg(te, x, y, z, partialTicks);
                    break;
                }
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.translateFromOrientation((float)x, (float)y, (float)z, dir.ordinal(), 0.02f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslated(0.5, -0.5, 0.0);
            UtilsFX.renderQuadCentered(TileMirrorRenderer.mpt, 1.0f, 1.0f, 1.0f, 1.0f, b, 771, 1.0f);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        else {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.translateFromOrientation((float)x, (float)y, (float)z, dir.ordinal(), 0.02f);
            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslated(0.5, -0.5, 0.0);
            UtilsFX.renderQuadCentered(TileMirrorRenderer.mp, 1.0f, 1.0f, 1.0f, 1.0f, b, 771, 1.0f);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
    
    private void translateFromOrientation(final float x, final float y, final float z, final int orientation, final float off) {
        if (orientation == 0) {
            GL11.glTranslatef(x, y + 1.0f, z + 1.0f);
            GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 1) {
            GL11.glTranslatef(x, y, z);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == 2) {
            GL11.glTranslatef(x, y, z + 1.0f);
        }
        else if (orientation == 3) {
            GL11.glTranslatef(x + 1.0f, y, z);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 4) {
            GL11.glTranslatef(x + 1.0f, y, z + 1.0f);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        }
        else if (orientation == 5) {
            GL11.glTranslatef(x, y, z);
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, 0.0f, -off);
    }
    
    static {
        t1 = new ResourceLocation("thaumcraft", "textures/misc/tunnel.png");
        t2 = new ResourceLocation("thaumcraft", "textures/misc/particlefield.png");
        TileMirrorRenderer.mp = new ResourceLocation("thaumcraft", "textures/blocks/mirrorpane.png");
        TileMirrorRenderer.mpt = new ResourceLocation("thaumcraft", "textures/blocks/mirrorpanetrans.png");
    }
}
