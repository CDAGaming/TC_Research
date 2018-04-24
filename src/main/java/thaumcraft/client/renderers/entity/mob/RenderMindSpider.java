package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderMindSpider extends RenderSpider
{
    public RenderMindSpider(final RenderManager rm) {
        super(rm);
        this.field_76989_e = 0.5f;
        this.func_177094_a((LayerRenderer)new LayerSpiderEyes((RenderSpider)this));
    }
    
    protected void func_77041_b(final EntityLivingBase par1EntityLiving, final float par2) {
        GL11.glScalef(0.6f, 0.6f, 0.6f);
    }
    
    public void func_76986_a(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        if (((EntityMindSpider)p_76986_1_).getViewer().length() == 0 || ((EntityMindSpider)p_76986_1_).getViewer().equals(this.field_76990_c.field_78734_h.func_70005_c_())) {
            super.func_76986_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }
    
    protected void func_77036_a(final EntityLivingBase entity, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        this.func_180548_c((Entity)entity);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, Math.min(0.1f, entity.field_70173_aa / 100.0f));
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.003921569f);
        this.field_77045_g.func_78088_a((Entity)entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
    }
}
