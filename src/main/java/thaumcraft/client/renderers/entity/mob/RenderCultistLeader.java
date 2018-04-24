package thaumcraft.client.renderers.entity.mob;

import thaumcraft.common.entities.monster.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderCultistLeader extends RenderBiped<EntityCultistLeader>
{
    private static final ResourceLocation skin;
    private static final ResourceLocation fl;
    
    public RenderCultistLeader(final RenderManager p_i46127_1_) {
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
    
    protected ResourceLocation getEntityTexture(final EntityCultistLeader p_110775_1_) {
        return RenderCultistLeader.skin;
    }
    
    protected void preRenderCallback(final EntityCultistLeader entitylivingbaseIn, final float partialTickTime) {
        super.func_77041_b((EntityLivingBase)entitylivingbaseIn, partialTickTime);
        GL11.glScalef(1.15f, 1.15f, 1.15f);
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
        this.func_110776_a(RenderCultistLeader.fl);
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
