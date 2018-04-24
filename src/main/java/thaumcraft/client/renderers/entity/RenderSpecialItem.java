package thaumcraft.client.renderers.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSpecialItem extends RenderEntityItem
{
    public RenderSpecialItem(final RenderManager p_i46167_1_, final RenderItem p_i46167_2_) {
        super(p_i46167_1_, p_i46167_2_);
    }
    
    public void func_76986_a(final EntityItem e, final double x, final double y, final double z, final float p_177075_8_, final float pt) {
        final Random random = new Random(187L);
        final float var11 = MathHelper.func_76126_a((e.func_174872_o() + pt) / 10.0f + e.field_70290_d) * 0.1f + 0.1f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + var11 + 0.25f, (float)z);
        final int q = FMLClientHandler.instance().getClient().field_71474_y.field_74347_j ? 10 : 5;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder wr = tessellator.func_178180_c();
        RenderHelper.func_74518_a();
        final float f1 = e.func_174872_o() / 500.0f;
        final float f2 = 0.9f;
        final float f3 = 0.0f;
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
            wr.func_181668_a(6, DefaultVertexFormats.field_181706_f);
            float fa = random.nextFloat() * 20.0f + 5.0f + f3 * 10.0f;
            float f4 = random.nextFloat() * 2.0f + 1.0f + f3 * 2.0f;
            fa /= 30.0f / (Math.min(e.func_174872_o(), 10) / 10.0f);
            f4 /= 30.0f / (Math.min(e.func_174872_o(), 10) / 10.0f);
            wr.func_181662_b(0.0, 0.0, 0.0).func_181666_a(1.0f, 1.0f, 1.0f, 1.0f - f3).func_181675_d();
            wr.func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(1.0f, 0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(1.0f, 0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(0.0, (double)fa, (double)(1.0f * f4)).func_181666_a(1.0f, 0.0f, 1.0f, 0.0f).func_181675_d();
            wr.func_181662_b(-0.866 * f4, (double)fa, (double)(-0.5f * f4)).func_181666_a(1.0f, 0.0f, 1.0f, 0.0f).func_181675_d();
            tessellator.func_78381_a();
        }
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
        super.func_76986_a(e, x, y, z, p_177075_8_, pt);
    }
}
