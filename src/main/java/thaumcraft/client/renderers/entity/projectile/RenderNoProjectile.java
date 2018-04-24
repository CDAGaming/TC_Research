package thaumcraft.client.renderers.entity.projectile;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.projectile.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderNoProjectile extends Render
{
    public RenderNoProjectile(final RenderManager rm) {
        super(rm);
        this.field_76989_e = 0.1f;
    }
    
    public void renderEntityAt(final EntityThrowable tg, final double x, final double y, final double z, final float fq) {
        if (tg instanceof EntityFocusProjectile) {
            final EntityFocusProjectile gp = (EntityFocusProjectile)tg;
            float qq = fq - gp.lastRenderTick;
            if (qq < 0.0f) {
                ++qq;
            }
            if (qq > 0.2) {
                gp.renderParticle(fq);
            }
        }
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt((EntityThrowable)entity, d, d1, d2, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
