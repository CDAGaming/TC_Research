package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;

public class ModelJar extends ModelBase
{
    public ModelRenderer Core;
    public ModelRenderer Brine;
    public ModelRenderer Lid;
    public ModelRenderer LidExtension;
    
    public ModelJar() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.Core = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-5.0f, -12.0f, -5.0f, 10, 12, 10);
        this.Core.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Core.func_78787_b(64, 32);
        this.Core.field_78809_i = true;
        this.setRotation(this.Core, 0.0f, 0.0f, 0.0f);
        (this.Brine = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-4.0f, -11.0f, -4.0f, 8, 10, 8);
        this.Brine.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Brine.func_78787_b(64, 32);
        this.Brine.field_78809_i = true;
        this.setRotation(this.Brine, 0.0f, 0.0f, 0.0f);
        (this.Lid = new ModelRenderer((ModelBase)this, 32, 24)).func_78789_a(-3.0f, 0.0f, -3.0f, 6, 2, 6);
        this.Lid.func_78793_a(0.0f, -14.0f, 0.0f);
        this.Lid.func_78787_b(64, 32);
        this.Lid.field_78809_i = true;
        (this.LidExtension = new ModelRenderer((ModelBase)this, 0, 23)).func_78789_a(-2.0f, -16.0f, -2.0f, 4, 2, 4);
        this.LidExtension.func_78793_a(0.0f, 0.0f, 0.0f);
        this.LidExtension.func_78787_b(64, 32);
        this.LidExtension.field_78809_i = true;
    }
    
    public void renderBrine() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.Brine.func_78785_a(0.0625f);
        GL11.glDisable(3042);
    }
    
    public void renderLidExtension() {
        this.LidExtension.func_78785_a(0.0625f);
    }
    
    public void renderLidBrace() {
        this.Lid.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
