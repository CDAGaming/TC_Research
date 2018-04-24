package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderEldritchCrab extends RenderLiving
{
    private static final ResourceLocation skin;
    
    public RenderEldritchCrab(final RenderManager renderManager) {
        super(renderManager, (ModelBase)new ModelEldritchCrab(), 1.0f);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderEldritchCrab.skin;
    }
    
    public void renderCrab(final EntityLiving crab, final double par2, final double par4, final double par6, final float par8, final float par9) {
        super.func_76986_a(crab, par2, par4, par6, par8, par9);
    }
    
    public void func_76986_a(final EntityLiving par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderCrab(par1Entity, par2, par4, par6, par8, par9);
    }
    
    static {
        skin = new ResourceLocation("thaumcraft", "textures/entity/crab.png");
    }
}
