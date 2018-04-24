package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderSpellBat extends RenderLiving
{
    private int renderedBatSize;
    private static final ResourceLocation rl;
    
    public RenderSpellBat(final RenderManager rm) {
        super(rm, (ModelBase)new ModelFireBat(), 0.25f);
        this.renderedBatSize = ((ModelFireBat)this.field_77045_g).getBatSize();
    }
    
    public void func_82443_a(final EntitySpellBat bat, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final int var10 = ((ModelFireBat)this.field_77045_g).getBatSize();
        if (var10 != this.renderedBatSize) {
            this.renderedBatSize = var10;
            this.field_77045_g = (ModelBase)new ModelBat();
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final Color c = new Color(bat.color);
        final float r = c.getRed() / 255.0f;
        final float g = c.getGreen() / 255.0f;
        final float b = c.getBlue() / 255.0f;
        GL11.glColor4f(r, g, b, 0.5f);
        super.func_76986_a((EntityLiving)bat, par2, par4, par6, par8, par9);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    protected void func_82442_a(final EntitySpellBat par1EntityBat, final float par2) {
        GL11.glScalef(0.35f, 0.35f, 0.35f);
    }
    
    protected void func_82445_a(final EntitySpellBat par1EntityBat, final double par2, final double par4, final double par6) {
        super.func_77039_a((EntityLivingBase)par1EntityBat, par2, par4, par6);
    }
    
    protected void func_82444_a(final EntitySpellBat par1EntityBat, final float par2, final float par3, final float par4) {
        GL11.glTranslatef(0.0f, -0.1f, 0.0f);
        super.func_77043_a((EntityLivingBase)par1EntityBat, par2, par3, par4);
    }
    
    protected void func_77041_b(final EntityLivingBase par1EntityLiving, final float par2) {
        this.func_82442_a((EntitySpellBat)par1EntityLiving, par2);
    }
    
    protected void func_77043_a(final EntityLivingBase par1EntityLiving, final float par2, final float par3, final float par4) {
        this.func_82444_a((EntitySpellBat)par1EntityLiving, par2, par3, par4);
    }
    
    protected void func_77039_a(final EntityLivingBase par1EntityLiving, final double par2, final double par4, final double par6) {
        this.func_82445_a((EntitySpellBat)par1EntityLiving, par2, par4, par6);
    }
    
    public void func_76986_a(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82443_a((EntitySpellBat)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderSpellBat.rl;
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/spellbat.png");
    }
}
