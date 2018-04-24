package thaumcraft.client.renderers.entity.construct;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import thaumcraft.client.lib.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;

public class RenderArcaneBore extends RenderLiving
{
    private static final ResourceLocation rl;
    ResourceLocation beam;
    
    public RenderArcaneBore(final RenderManager rm) {
        super(rm, (ModelBase)new ModelArcaneBore(), 0.5f);
        this.beam = new ResourceLocation("thaumcraft", "textures/misc/beam1.png");
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderArcaneBore.rl;
    }
    
    protected float func_77040_d(final EntityLivingBase livingBase, final float partialTickTime) {
        livingBase.field_70761_aq = 0.0f;
        livingBase.field_70760_ar = 0.0f;
        return super.func_77040_d(livingBase, partialTickTime);
    }
    
    protected void func_77041_b(final EntityLivingBase entitylivingbaseIn, final float partialTickTime) {
        entitylivingbaseIn.field_70761_aq = 0.0f;
        entitylivingbaseIn.field_70760_ar = 0.0f;
        super.func_77041_b(entitylivingbaseIn, partialTickTime);
    }
    
    public void func_76986_a(final EntityLiving entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        super.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
        final EntityArcaneBore bore = (EntityArcaneBore)entity;
        if (bore.clientDigging && bore.isActive() && bore.validInventory()) {
            final Tessellator tessellator = Tessellator.func_178181_a();
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            Minecraft.func_71410_x().field_71446_o.func_110577_a(UtilsFX.nodeTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.66f);
            final int part = entity.field_70173_aa % 32;
            Vec3d lv2 = new Vec3d(0.5, 0.075, 0.0);
            Vec3d cv = new Vec3d(x, y + bore.func_70047_e(), z);
            lv2 = Utils.rotateAroundZ(lv2, bore.field_70125_A / 180.0f * 3.1415927f);
            lv2 = Utils.rotateAroundY(lv2, -((bore.field_70759_as + 90.0f) / 180.0f * 3.1415927f));
            cv = cv.func_178787_e(lv2);
            final double beamLength = 5.0;
            GL11.glTranslated(cv.field_72450_a, cv.field_72448_b, cv.field_72449_c);
            GL11.glPushMatrix();
            UtilsFX.renderBillboardQuad(0.5, 32, 32, 96 + part, 0.0f, 1.0f, 0.4f, 0.8f, 210);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            final float var9 = 1.0f;
            final float rot = bore.field_70170_p.field_73011_w.getWorldTime() % 72L * 5L + 5.0f * partialTicks;
            final float size = 1.0f;
            final float op = 0.4f;
            Minecraft.func_71410_x().field_71446_o.func_110577_a(this.beam);
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GL11.glDisable(2884);
            float var10 = entity.field_70173_aa + partialTicks;
            var10 *= -1.0f;
            final float var11 = -var10 * 0.2f - MathHelper.func_76141_d(-var10 * 0.1f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDepthMask(false);
            final float ry = bore.field_70126_B + (bore.field_70177_z - bore.field_70126_B) * partialTicks;
            final float rp = bore.field_70127_C + (bore.field_70125_A - bore.field_70127_C) * partialTicks;
            GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
            GL11.glRotatef(180.0f + ry, 0.0f, 0.0f, -1.0f);
            GL11.glRotatef(rp, -1.0f, 0.0f, 0.0f);
            final double var12 = -0.15 * size;
            final double var13 = 0.15 * size;
            final double var44b = 0.0;
            final double var17b = 0.0;
            final int i = 200;
            final int j = i >> 16 & 0xFFFF;
            final int k = i & 0xFFFF;
            GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
            for (int t = 0; t < 3; ++t) {
                final double var14 = beamLength * size * var9;
                final double var15 = 0.0;
                final double var16 = 1.0;
                final double var17 = -1.0f + var11 + t / 3.0f;
                final double var18 = beamLength * size * var9 + var17;
                GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
                tessellator.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181704_d);
                tessellator.func_178180_c().func_181662_b(var44b, var14, 0.0).func_187315_a(var16, var18).func_181666_a(0.0f, 1.0f, 0.4f, op).func_187314_a(j, k).func_181675_d();
                tessellator.func_178180_c().func_181662_b(var12, 0.0, 0.0).func_187315_a(var16, var17).func_181666_a(0.0f, 1.0f, 0.4f, op).func_187314_a(j, k).func_181675_d();
                tessellator.func_178180_c().func_181662_b(var13, 0.0, 0.0).func_187315_a(var15, var17).func_181666_a(0.0f, 1.0f, 0.4f, op).func_187314_a(j, k).func_181675_d();
                tessellator.func_178180_c().func_181662_b(var17b, var14, 0.0).func_187315_a(var15, var18).func_181666_a(0.0f, 1.0f, 0.4f, op).func_187314_a(j, k).func_181675_d();
                Tessellator.func_178181_a().func_78381_a();
            }
            GL11.glPopMatrix();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/arcanebore.png");
    }
}
