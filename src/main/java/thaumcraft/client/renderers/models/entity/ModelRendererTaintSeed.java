package thaumcraft.client.renderers.models.entity;

import org.lwjgl.opengl.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;

public class ModelRendererTaintSeed extends ModelRenderer
{
    private int textureOffsetX;
    private int textureOffsetY;
    private boolean compiled;
    private int displayList;
    private ModelBase baseModel;
    static int q;
    
    public ModelRendererTaintSeed(final ModelBase par1ModelBase) {
        super(par1ModelBase);
    }
    
    public ModelRendererTaintSeed(final ModelBase par1ModelBase, final int par2, final int par3) {
        this(par1ModelBase);
        this.func_78784_a(par2, par3);
    }
    
    @SideOnly(Side.CLIENT)
    public void render(final float par1, final float tt, final float height) {
        final float qq = (float)(3.141592653589793 * (ModelRendererTaintSeed.q / 7.0f));
        final float scale = height - (float)Math.sin(qq);
        float pulse = (float)Math.sin(tt / 12.0f - qq) * 0.33f;
        pulse *= pulse;
        ++ModelRendererTaintSeed.q;
        if (ModelRendererTaintSeed.q > 7) {
            ModelRendererTaintSeed.q = 0;
        }
        if (!this.field_78807_k && this.field_78806_j) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            GL11.glTranslatef(this.field_82906_o, this.field_82908_p, this.field_82907_q);
            if (this.field_78795_f == 0.0f && this.field_78796_g == 0.0f && this.field_78808_h == 0.0f) {
                if (this.field_78800_c == 0.0f && this.field_78797_d == 0.0f && this.field_78798_e == 0.0f) {
                    GL11.glCallList(this.displayList);
                    if (this.field_78805_m != null) {
                        for (int i = 0; i < this.field_78805_m.size(); ++i) {
                            GL11.glPushMatrix();
                            GL11.glScalef(scale + pulse, scale * 0.9f, scale + pulse);
                            this.field_78805_m.get(i).render(par1, tt, height);
                            GL11.glPopMatrix();
                        }
                    }
                }
                else {
                    GL11.glTranslatef(this.field_78800_c * par1, this.field_78797_d * par1, this.field_78798_e * par1);
                    GL11.glCallList(this.displayList);
                    if (this.field_78805_m != null) {
                        for (int i = 0; i < this.field_78805_m.size(); ++i) {
                            GL11.glPushMatrix();
                            GL11.glScalef(scale + pulse, scale * 0.9f, scale + pulse);
                            this.field_78805_m.get(i).render(par1, tt, height);
                            GL11.glPopMatrix();
                        }
                    }
                    GL11.glTranslatef(-this.field_78800_c * par1, -this.field_78797_d * par1, -this.field_78798_e * par1);
                }
            }
            else {
                GL11.glPushMatrix();
                GL11.glTranslatef(this.field_78800_c * par1, this.field_78797_d * par1, this.field_78798_e * par1);
                if (this.field_78808_h != 0.0f) {
                    GL11.glRotatef(this.field_78808_h * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.field_78796_g != 0.0f) {
                    GL11.glRotatef(this.field_78796_g * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.field_78795_f != 0.0f) {
                    GL11.glRotatef(this.field_78795_f * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GL11.glCallList(this.displayList);
                if (this.field_78805_m != null) {
                    for (int i = 0; i < this.field_78805_m.size(); ++i) {
                        GL11.glPushMatrix();
                        GL11.glScalef(scale + pulse, scale * 0.9f, scale + pulse);
                        this.field_78805_m.get(i).render(par1, tt, height);
                        GL11.glPopMatrix();
                    }
                }
                GL11.glPopMatrix();
            }
            GL11.glTranslatef(-this.field_82906_o, -this.field_82908_p, -this.field_82907_q);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void compileDisplayList(final float par1) {
        GL11.glNewList(this.displayList = GLAllocation.func_74526_a(1), 4864);
        final Tessellator tessellator = Tessellator.func_178181_a();
        for (int i = 0; i < this.field_78804_l.size(); ++i) {
            this.field_78804_l.get(i).func_178780_a(tessellator.func_178180_c(), par1);
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    static {
        ModelRendererTaintSeed.q = 0;
    }
}
