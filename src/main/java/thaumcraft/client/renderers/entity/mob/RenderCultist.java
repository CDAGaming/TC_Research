package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.entities.monster.cult.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderCultist extends RenderBiped<EntityCultist>
{
    private static final ResourceLocation skin;
    private static final ResourceLocation fl;
    
    public RenderCultist(final RenderManager p_i46127_1_) {
        super(p_i46127_1_, new ModelBiped(), 0.5f);
        this.func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
        final LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
            protected void func_177177_a() {
                this.field_177189_c = (ModelBase)new ModelBiped();
                this.field_177186_d = (ModelBase)new ModelBiped();
            }
        };
        this.func_177094_a((LayerRenderer)layerbipedarmor);
    }
    
    protected ResourceLocation getEntityTexture(final EntityCultist p_110775_1_) {
        return RenderCultist.skin;
    }
    
    public void doRender(final EntityCultist entity, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        float bob = 0.0f;
        final boolean rit = entity instanceof EntityCultistCleric && ((EntityCultistCleric)entity).getIsRitualist();
        if (rit) {
            final int val = new Random(entity.func_145782_y()).nextInt(1000);
            final float c = ((EntityCultistCleric)entity).field_70173_aa + p_76986_9_ + val;
            bob = MathHelper.func_76126_a(c / 9.0f) * 0.1f + 0.21f;
            GL11.glTranslated(0.0, (double)bob, 0.0);
        }
        super.func_76986_a((EntityLiving)entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        if (rit) {
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            this.drawFloatyLine(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e() * 1.2f, entity.field_70161_v, ((EntityCultistCleric)entity).func_180486_cf().func_177958_n() + 0.5, ((EntityCultistCleric)entity).func_180486_cf().func_177956_o() + 1.5 - bob, ((EntityCultistCleric)entity).func_180486_cf().func_177952_p() + 0.5, p_76986_9_, 1114129, -0.03f, Math.min(((EntityCultistCleric)entity).field_70173_aa, 10) / 10.0f, 0.25f);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
    
    private void drawFloatyLine(final double x, final double y, final double z, final double x2, final double y2, final double z2, final float partialTicks, final int color, final float speed, final float distance, final float width) {
        final Entity player = Minecraft.func_71410_x().func_175606_aa();
        final double iPX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
        final double iPY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
        final double iPZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
        GL11.glTranslated(-iPX + x2, -iPY + y2, -iPZ + z2);
        final float time = System.nanoTime() / 30000000L;
        final Color co = new Color(color);
        final float r = co.getRed() / 255.0f;
        final float g = co.getGreen() / 255.0f;
        final float b = co.getBlue() / 255.0f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final double dc1x = (float)(x - x2);
        final double dc1y = (float)(y - y2);
        final double dc1z = (float)(z - z2);
        this.func_110776_a(RenderCultist.fl);
        tessellator.func_178180_c().func_181668_a(5, DefaultVertexFormats.field_181709_i);
        final double dx2 = 0.0;
        final double dy2 = 0.0;
        final double dz2 = 0.0;
        final double d3 = x - x2;
        final double d4 = y - y2;
        final double d5 = z - z2;
        final float dist = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
        final float blocks = Math.round(dist);
        final float length = blocks * 6.0f;
        final float f9 = 0.0f;
        final float f10 = 1.0f;
        for (int i = 0; i <= length * distance; ++i) {
            final float f11 = i / length;
            float f2a = i * 1.5f / length;
            f2a = Math.min(0.75f, f2a);
            final float f12 = 1.0f - Math.abs(i - length / 2.0f) / (length / 2.0f);
            final double dx3 = dc1x + MathHelper.func_76126_a((float)((z % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 4.0)) * 0.5f * f12;
            final double dy3 = dc1y + MathHelper.func_76126_a((float)((x % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 3.0)) * 0.5f * f12;
            final double dz3 = dc1z + MathHelper.func_76126_a((float)((y % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 2.0)) * 0.5f * f12;
            final float f13 = (1.0f - f11) * dist - time * speed;
            tessellator.func_178180_c().func_181662_b(dx3 * f11, dy3 * f11 - width, dz3 * f11).func_187315_a((double)f13, (double)f10).func_181666_a(r, g, b, 0.8f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(dx3 * f11, dy3 * f11 + width, dz3 * f11).func_187315_a((double)f13, (double)f9).func_181666_a(r, g, b, 0.8f).func_181675_d();
        }
        tessellator.func_78381_a();
        tessellator.func_178180_c().func_181668_a(5, DefaultVertexFormats.field_181709_i);
        for (int i = 0; i <= length * distance; ++i) {
            final float f11 = i / length;
            float f2a = i * 1.5f / length;
            f2a = Math.min(0.75f, f2a);
            final float f12 = 1.0f - Math.abs(i - length / 2.0f) / (length / 2.0f);
            final double dx3 = dc1x + MathHelper.func_76126_a((float)((z % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 4.0)) * 0.5f * f12;
            final double dy3 = dc1y + MathHelper.func_76126_a((float)((x % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 3.0)) * 0.5f * f12;
            final double dz3 = dc1z + MathHelper.func_76126_a((float)((y % 16.0 + dist * (1.0f - f11) * 6.0f - time % 32767.0f / 5.0f) / 2.0)) * 0.5f * f12;
            final float f13 = (1.0f - f11) * dist - time * speed;
            tessellator.func_178180_c().func_181662_b(dx3 * f11 - width, dy3 * f11, dz3 * f11).func_187315_a((double)f13, (double)f10).func_181666_a(r, g, b, 0.8f).func_181675_d();
            tessellator.func_178180_c().func_181662_b(dx3 * f11 + width, dy3 * f11, dz3 * f11).func_187315_a((double)f13, (double)f9).func_181666_a(r, g, b, 0.8f).func_181675_d();
        }
        tessellator.func_78381_a();
        GL11.glDisable(3042);
    }
    
    static {
        skin = new ResourceLocation("thaumcraft", "textures/entity/cultist.png");
        fl = new ResourceLocation("thaumcraft", "textures/misc/wispy.png");
    }
}
