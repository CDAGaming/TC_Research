package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class RenderPech extends RenderLiving
{
    protected ModelPech modelMain;
    protected ModelPech modelOverlay;
    private static final ResourceLocation[] skin;
    
    public RenderPech(final RenderManager rm, final ModelPech par1ModelBiped, final float par2) {
        super(rm, (ModelBase)par1ModelBiped, par2);
        this.modelMain = par1ModelBiped;
        this.modelOverlay = new ModelPech();
        this.func_177094_a((LayerRenderer)new LayerHeldItemPech((RenderLivingBase<?>)this));
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderPech.skin[((EntityPech)entity).getPechType()];
    }
    
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final float f2 = 1.0f;
        GL11.glColor3f(f2, f2, f2);
        double d3 = par4 - par1EntityLiving.func_70033_W();
        if (par1EntityLiving.func_70093_af()) {
            d3 -= 0.125;
        }
        super.func_76986_a(par1EntityLiving, par2, d3, par6, par8, par9);
    }
    
    public void func_76986_a(final EntityLiving par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLiving(par1Entity, par2, par4, par6, par8, par9);
    }
    
    static {
        skin = new ResourceLocation[] { new ResourceLocation("thaumcraft", "textures/entity/pech_forage.png"), new ResourceLocation("thaumcraft", "textures/entity/pech_thaum.png"), new ResourceLocation("thaumcraft", "textures/entity/pech_stalker.png") };
    }
}
