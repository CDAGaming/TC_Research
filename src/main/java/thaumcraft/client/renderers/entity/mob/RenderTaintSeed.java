package thaumcraft.client.renderers.entity.mob;

import thaumcraft.common.entities.monster.tainted.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.model.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderTaintSeed extends RenderLiving<EntityTaintSeed>
{
    private static final ResourceLocation rl;
    
    public RenderTaintSeed(final RenderManager rm) {
        super(rm, (ModelBase)new ModelTaintSeed(), 0.4f);
    }
    
    public RenderTaintSeed(final RenderManager rm, final ModelBase modelbase, final float sz) {
        super(rm, modelbase, sz);
    }
    
    protected ResourceLocation getEntityTexture(final EntityTaintSeed entity) {
        return RenderTaintSeed.rl;
    }
    
    public void doRender(final EntityTaintSeed entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, (RenderLivingBase)this, x, y, z))) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179129_p();
        this.field_77045_g.field_78095_p = this.func_77040_d((EntityLivingBase)entity, partialTicks);
        final boolean shouldSit = entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit();
        this.field_77045_g.field_78093_q = shouldSit;
        this.field_77045_g.field_78091_s = entity.func_70631_g_();
        try {
            GlStateManager.func_179094_E();
            final float f = 0.0f;
            final float f2 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
            this.func_77039_a((EntityLivingBase)entity, x, y, z);
            final float f3 = this.func_77044_a((EntityLivingBase)entity, partialTicks);
            this.func_77043_a((EntityLivingBase)entity, f3, f, partialTicks);
            final float f4 = this.func_188322_c((EntityLivingBase)entity, partialTicks);
            float f5 = 0.0f;
            float f6 = 0.0f;
            f5 = entity.field_184618_aE + (entity.field_70721_aZ - entity.field_184618_aE) * partialTicks;
            f6 = entity.field_184619_aG - entity.field_70721_aZ * (1.0f - partialTicks);
            if (f5 > 1.0f) {
                f5 = 1.0f;
            }
            GlStateManager.func_179141_d();
            this.field_77045_g.func_78086_a((EntityLivingBase)entity, f6, f5, partialTicks);
            this.field_77045_g.func_78087_a(f6, f5, f3, f, f2, f4, (Entity)entity);
            if (this.field_188301_f) {
                final boolean flag1 = this.func_177088_c((EntityLivingBase)entity);
                GlStateManager.func_179142_g();
                GlStateManager.func_187431_e(this.func_188298_c((Entity)entity));
                if (!this.field_188323_j) {
                    this.func_77036_a((EntityLivingBase)entity, f6, f5, f3, f, f2, f4);
                }
                this.func_177093_a((EntityLivingBase)entity, f6, f5, partialTicks, f3, f, f2, f4);
                GlStateManager.func_187417_n();
                GlStateManager.func_179119_h();
                if (flag1) {
                    this.func_180565_e();
                }
            }
            else {
                final boolean flag2 = this.func_177090_c((EntityLivingBase)entity, partialTicks);
                this.func_77036_a((EntityLivingBase)entity, f6, f5, f3, f, f2, f4);
                if (flag2) {
                    this.func_177091_f();
                }
                GlStateManager.func_179132_a(true);
                this.func_177093_a((EntityLivingBase)entity, f6, f5, partialTicks, f3, f, f2, f4);
            }
            GlStateManager.func_179121_F();
            GlStateManager.func_179101_C();
        }
        catch (Exception ex) {}
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GlStateManager.func_179089_o();
        GlStateManager.func_179121_F();
        if (!this.field_188301_f) {
            this.func_177067_a((EntityLivingBase)entity, x, y, z);
        }
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, (RenderLivingBase)this, x, y, z));
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/taintseed.png");
    }
}
