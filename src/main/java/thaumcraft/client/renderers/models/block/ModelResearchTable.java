package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class ModelResearchTable extends ModelBase
{
    ModelRenderer Inkwell;
    ModelRenderer ScrollTube;
    ModelRenderer ScrollRibbon;
    
    public ModelResearchTable() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.Inkwell = new ModelRenderer((ModelBase)this, 0, 16)).func_78789_a(0.0f, 0.0f, 0.0f, 3, 2, 3);
        this.Inkwell.func_78793_a(-6.0f, -2.0f, 3.0f);
        this.Inkwell.field_78809_i = true;
        this.setRotation(this.Inkwell, 0.0f, 0.0f, 0.0f);
        (this.ScrollTube = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-8.0f, -0.5f, 0.0f, 8, 2, 2);
        this.ScrollTube.func_78793_a(-2.0f, -2.0f, 2.0f);
        this.ScrollTube.field_78809_i = true;
        this.setRotation(this.ScrollTube, 0.0f, 10.0f, 0.0f);
        (this.ScrollRibbon = new ModelRenderer((ModelBase)this, 0, 4)).func_78789_a(-4.25f, -0.275f, 0.0f, 1, 2, 2);
        this.ScrollRibbon.func_78793_a(-2.0f, -2.0f, 2.0f);
        this.ScrollRibbon.field_78809_i = true;
        this.setRotation(this.ScrollRibbon, 0.0f, 10.0f, 0.0f);
    }
    
    public void renderInkwell() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.Inkwell.func_78785_a(0.0625f);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void renderScroll(final int color) {
        GL11.glPushMatrix();
        this.ScrollTube.func_78785_a(0.0625f);
        final Color c = new Color(color);
        GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        this.ScrollRibbon.func_78785_a(0.0625f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
