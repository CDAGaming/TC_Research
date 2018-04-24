package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderFireBat extends RenderLiving
{
    private int renderedBatSize;
    private static final ResourceLocation rl;
    
    public RenderFireBat(final RenderManager rm) {
        super(rm, (ModelBase)new ModelFireBat(), 0.25f);
        this.renderedBatSize = ((ModelFireBat)this.field_77045_g).getBatSize();
    }
    
    public void func_82443_a(final EntityFireBat par1EntityBat, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final int var10 = ((ModelFireBat)this.field_77045_g).getBatSize();
        if (var10 != this.renderedBatSize) {
            this.renderedBatSize = var10;
            this.field_77045_g = (ModelBase)new ModelBat();
        }
        super.func_76986_a((EntityLiving)par1EntityBat, par2, par4, par6, par8, par9);
    }
    
    protected void func_82442_a(final EntityFireBat par1EntityBat, final float par2) {
        GL11.glScalef(0.35f, 0.35f, 0.35f);
    }
    
    protected void func_82445_a(final EntityFireBat par1EntityBat, final double par2, final double par4, final double par6) {
        super.func_77039_a((EntityLivingBase)par1EntityBat, par2, par4, par6);
    }
    
    protected void func_82444_a(final EntityFireBat par1EntityBat, final float par2, final float par3, final float par4) {
        if (!par1EntityBat.getIsBatHanging()) {
            GL11.glTranslatef(0.0f, MathHelper.func_76134_b(par2 * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GL11.glTranslatef(0.0f, -0.1f, 0.0f);
        }
        super.func_77043_a((EntityLivingBase)par1EntityBat, par2, par3, par4);
    }
    
    protected void func_77041_b(final EntityLivingBase par1EntityLiving, final float par2) {
        this.func_82442_a((EntityFireBat)par1EntityLiving, par2);
    }
    
    protected void func_77043_a(final EntityLivingBase par1EntityLiving, final float par2, final float par3, final float par4) {
        this.func_82444_a((EntityFireBat)par1EntityLiving, par2, par3, par4);
    }
    
    protected void func_77039_a(final EntityLivingBase par1EntityLiving, final double par2, final double par4, final double par6) {
        this.func_82445_a((EntityFireBat)par1EntityLiving, par2, par4, par6);
    }
    
    public void func_76986_a(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82443_a((EntityFireBat)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderFireBat.rl;
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/firebat.png");
    }
}
