package thaumcraft.client.renderers.entity.construct;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.model.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.entity.*;

public class RenderTurretCrossbow extends RenderLiving
{
    private static final ResourceLocation rl;
    
    public RenderTurretCrossbow(final RenderManager rm) {
        super(rm, (ModelBase)new ModelCrossbow(), 0.5f);
    }
    
    protected float func_77040_d(final EntityLivingBase e, final float p_77040_2_) {
        ((EntityTurretCrossbow)e).loadProgressForRender = ((EntityTurretCrossbow)e).getLoadProgress(p_77040_2_);
        e.field_70761_aq = 0.0f;
        e.field_70760_ar = 0.0f;
        return super.func_77040_d(e, p_77040_2_);
    }
    
    protected void func_77041_b(final EntityLivingBase entitylivingbaseIn, final float partialTickTime) {
        entitylivingbaseIn.field_70761_aq = 0.0f;
        entitylivingbaseIn.field_70760_ar = 0.0f;
        super.func_77041_b(entitylivingbaseIn, partialTickTime);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderTurretCrossbow.rl;
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/crossbow.png");
    }
}
