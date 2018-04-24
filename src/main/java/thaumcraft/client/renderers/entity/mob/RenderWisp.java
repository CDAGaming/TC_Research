package thaumcraft.client.renderers.entity.mob;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.api.aspects.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.fx.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderWisp extends Render
{
    public RenderWisp(final RenderManager rm) {
        super(rm);
        this.field_76989_e = 0.0f;
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        if (((EntityLiving)entity).func_110143_aJ() <= 0.0f) {
            return;
        }
        final double xx = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * pticks;
        final double yy = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * pticks;
        final double zz = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * pticks;
        int color = 0;
        if (Aspect.getAspect(((EntityWisp)entity).getType()) != null) {
            color = Aspect.getAspect(((EntityWisp)entity).getType()).getColor();
        }
        GL11.glPushMatrix();
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glDepthMask(false);
        this.func_110776_a(ParticleEngine.particleTexture);
        UtilsFX.renderFacingQuad(xx, yy, zz, 64, 64, 512 + entity.field_70173_aa % 16, 0.4f, 16777215, 1.0f, 1, pticks);
        UtilsFX.renderFacingQuad(xx, yy, zz, 64, 64, 320 + entity.field_70173_aa % 16, 0.75f, 16777215, 0.25f, 1, pticks);
        this.func_110776_a(UtilsFX.nodeTexture);
        UtilsFX.renderFacingQuad(xx, yy, zz, 32, 32, 800 + entity.field_70173_aa % 16, 0.75f, color, 0.5f, 1, pticks);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glPopMatrix();
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
