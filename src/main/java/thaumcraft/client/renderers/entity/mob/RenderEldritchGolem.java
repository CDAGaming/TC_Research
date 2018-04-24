package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderEldritchGolem extends RenderLiving
{
    protected ModelEldritchGolem modelMain;
    private static final ResourceLocation skin;
    
    public RenderEldritchGolem(final RenderManager rm, final ModelEldritchGolem par1ModelBiped, final float par2) {
        super(rm, (ModelBase)par1ModelBiped, par2);
        this.modelMain = par1ModelBiped;
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderEldritchGolem.skin;
    }
    
    protected void func_77041_b(final EntityLivingBase par1EntityLiving, final float par2) {
        GL11.glScalef(1.7f, 1.7f, 1.7f);
    }
    
    public void doRenderLiving(final EntityLiving golem, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glBlendFunc(770, 771);
        super.func_76986_a(golem, par2, par4, par6, par8, par9);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }
    
    public void func_76986_a(final EntityLiving par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLiving(par1Entity, par2, par4, par6, par8, par9);
    }
    
    static {
        skin = new ResourceLocation("thaumcraft", "textures/entity/eldritch_golem.png");
    }
}
