package thaumcraft.client.renderers.entity.construct;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import thaumcraft.client.lib.obj.*;
import thaumcraft.common.entities.construct.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class RenderTurretCrossbowAdvanced extends RenderLiving
{
    private IModelCustom model;
    private static final ResourceLocation TURMODEL;
    private static final ResourceLocation rl;
    
    public RenderTurretCrossbowAdvanced(final RenderManager rm) {
        super(rm, (ModelBase)null, 0.5f);
        this.model = AdvancedModelLoader.loadModel(RenderTurretCrossbowAdvanced.TURMODEL);
    }
    
    public void renderTurret(final EntityTurretCrossbow turret, final double x, final double y, final double z, final float par8, final float pTicks) {
        this.func_180548_c((Entity)turret);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(x, y + 0.75, z);
        GL11.glPushMatrix();
        if (turret.func_184218_aH() && turret.func_184187_bx() != null && turret.func_184187_bx() instanceof EntityMinecart) {
            GL11.glScaled(0.66, 0.75, 0.66);
        }
        this.model.renderPart("legs");
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        if (turret.field_70737_aN > 0) {
            GlStateManager.func_179131_c(1.0f, 0.5f, 0.5f, 1.0f);
            final float jiggle = turret.field_70737_aN / 500.0f;
            GL11.glTranslated(turret.func_70681_au().nextGaussian() * jiggle, turret.func_70681_au().nextGaussian() * jiggle, turret.func_70681_au().nextGaussian() * jiggle);
        }
        GL11.glRotatef(turret.field_70758_at + (turret.field_70759_as - turret.field_70758_at) * pTicks, 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(turret.field_70127_C + (turret.field_70125_A - turret.field_70127_C) * pTicks, 1.0f, 0.0f, 0.0f);
        this.model.renderPart("mech");
        this.model.renderPart("box");
        this.model.renderPart("shield");
        this.model.renderPart("brain");
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, 0.0, (double)(MathHelper.func_76126_a(MathHelper.func_76129_c(turret.loadProgressForRender) * 3.1415927f * 2.0f) / 12.0f));
        this.model.renderPart("loader");
        GL11.glPopMatrix();
        float sp = 0.0f;
        if (this.func_77040_d((EntityLivingBase)turret, pTicks) > -9990.0f) {
            sp = MathHelper.func_76126_a(MathHelper.func_76129_c(this.func_77040_d((EntityLivingBase)turret, pTicks)) * 3.1415927f * 2.0f) * 20.0f;
        }
        GL11.glTranslated(0.0, 0.0, 0.375);
        GL11.glPushMatrix();
        GL11.glRotatef(sp, 0.0f, 1.0f, 0.0f);
        this.model.renderPart("bow1");
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef(sp, 0.0f, -1.0f, 0.0f);
        this.model.renderPart("bow2");
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    protected float func_77040_d(final EntityLivingBase e, final float p_77040_2_) {
        ((EntityTurretCrossbow)e).loadProgressForRender = ((EntityTurretCrossbow)e).getLoadProgress(p_77040_2_);
        return super.func_77040_d(e, p_77040_2_);
    }
    
    private void translateFromOrientation(final int orientation) {
        GL11.glTranslated(0.0, 0.5, 0.0);
        if (orientation == 0) {
            GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation != 1) {
            if (orientation == 2) {
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (orientation == 3) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (orientation == 4) {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            }
            else if (orientation == 5) {
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        GL11.glTranslated(0.0, -0.5, 0.0);
    }
    
    public void func_76986_a(final EntityLiving par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderTurret((EntityTurretCrossbow)par1Entity, par2, par4, par6, par8, par9);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderTurretCrossbowAdvanced.rl;
    }
    
    static {
        TURMODEL = new ResourceLocation("thaumcraft", "models/obj/crossbow_advanced.obj");
        rl = new ResourceLocation("thaumcraft", "textures/entity/crossbow_advanced.png");
    }
}
