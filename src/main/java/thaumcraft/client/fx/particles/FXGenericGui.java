package thaumcraft.client.fx.particles;

import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class FXGenericGui extends FXGeneric
{
    boolean inGame;
    
    public FXGenericGui(final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
        this.inGame = false;
        this.inGame = Minecraft.func_71410_x().field_71415_G;
    }
    
    public FXGenericGui(final World world, final double x, final double y, final double z, final double xx, final double yy, final double zz) {
        super(world, x, y, z, xx, yy, zz);
        this.inGame = false;
        this.inGame = Minecraft.func_71410_x().field_71415_G;
    }
    
    @Override
    public void func_189213_a() {
        super.func_189213_a();
        if (!this.inGame && Minecraft.func_71410_x().field_71415_G) {
            this.func_187112_i();
        }
    }
    
    @Override
    public void draw(final BufferBuilder wr, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        float tx1 = this.field_94054_b / this.gridSize;
        float tx2 = tx1 + 1.0f / this.gridSize;
        float ty1 = this.field_94055_c / this.gridSize;
        float ty2 = ty1 + 1.0f / this.gridSize;
        final float ts = 0.1f * this.field_70544_f;
        if (this.field_187119_C != null) {
            tx1 = this.field_187119_C.func_94209_e();
            tx2 = this.field_187119_C.func_94212_f();
            ty1 = this.field_187119_C.func_94206_g();
            ty2 = this.field_187119_C.func_94210_h();
        }
        if (this.flipped) {
            final float t = tx1;
            tx1 = tx2;
            tx2 = t;
        }
        final float fs = MathHelper.func_76131_a((this.field_70546_d + partialTicks) / this.field_70547_e, 0.0f, 1.0f);
        final float pr = this.field_70552_h + (this.dr - this.field_70552_h) * fs;
        final float pg = this.field_70553_i + (this.dg - this.field_70553_i) * fs;
        final float pb = this.field_70551_j + (this.db - this.field_70551_j) * fs;
        final int i = this.func_189214_a(partialTicks);
        final int j = i >> 16 & 0xFFFF;
        final int k = i & 0xFFFF;
        final float f5 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks);
        final float f6 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks);
        final float f7 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks);
        GL11.glPushMatrix();
        GL11.glTranslatef(f5, f6, -90.0f + f7);
        if (this.angled) {
            GL11.glRotatef(-this.angleYaw + 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.anglePitch + 90.0f, 1.0f, 0.0f, 0.0f);
        }
        if (this.field_190014_F != 0.0f) {
            final float f8 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
            GL11.glRotated(f8 * 57.29577951308232, 0.0, 0.0, 1.0);
        }
        wr.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        wr.func_181662_b((double)(-ts), (double)(-ts), 0.0).func_187315_a((double)tx2, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)(-ts), (double)ts, 0.0).func_187315_a((double)tx2, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)ts, (double)ts, 0.0).func_187315_a((double)tx1, (double)ty1).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        wr.func_181662_b((double)ts, (double)(-ts), 0.0).func_187315_a((double)tx1, (double)ty2).func_181666_a(pr, pg, pb, this.field_82339_as).func_187314_a(j, k).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
        GL11.glPopMatrix();
    }
}
