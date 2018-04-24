package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelCentrifuge extends ModelBase
{
    ModelRenderer Crossbar;
    ModelRenderer Dingus1;
    ModelRenderer Dingus2;
    ModelRenderer Core;
    ModelRenderer Top;
    ModelRenderer Bottom;
    
    public ModelCentrifuge() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.Crossbar = new ModelRenderer((ModelBase)this, 16, 0)).func_78789_a(-4.0f, -1.0f, -1.0f, 8, 2, 2);
        this.Crossbar.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Crossbar.func_78787_b(64, 32);
        this.Crossbar.field_78809_i = true;
        this.setRotation(this.Crossbar, 0.0f, 0.0f, 0.0f);
        (this.Dingus1 = new ModelRenderer((ModelBase)this, 0, 16)).func_78789_a(4.0f, -3.0f, -2.0f, 4, 6, 4);
        this.Dingus1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Dingus1.func_78787_b(64, 32);
        this.Dingus1.field_78809_i = true;
        this.setRotation(this.Dingus1, 0.0f, 0.0f, 0.0f);
        (this.Dingus2 = new ModelRenderer((ModelBase)this, 0, 16)).func_78789_a(-8.0f, -3.0f, -2.0f, 4, 6, 4);
        this.Dingus2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Dingus2.func_78787_b(64, 32);
        this.Dingus2.field_78809_i = true;
        this.setRotation(this.Dingus2, 0.0f, 0.0f, 0.0f);
        (this.Core = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-1.5f, -4.0f, -1.5f, 3, 8, 3);
        this.Core.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Core.func_78787_b(64, 32);
        this.Core.field_78809_i = true;
        this.setRotation(this.Core, 0.0f, 0.0f, 0.0f);
        (this.Top = new ModelRenderer((ModelBase)this, 20, 16)).func_78789_a(-4.0f, -8.0f, -4.0f, 8, 4, 8);
        this.Top.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Top.func_78787_b(64, 32);
        this.Top.field_78809_i = true;
        this.setRotation(this.Top, 0.0f, 0.0f, 0.0f);
        (this.Bottom = new ModelRenderer((ModelBase)this, 20, 16)).func_78789_a(-4.0f, 4.0f, -4.0f, 8, 4, 8);
        this.Bottom.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Bottom.func_78787_b(64, 32);
        this.Bottom.field_78809_i = true;
        this.setRotation(this.Bottom, 0.0f, 0.0f, 0.0f);
    }
    
    public void renderBoxes() {
        this.Top.func_78785_a(0.0625f);
        this.Bottom.func_78785_a(0.0625f);
    }
    
    public void renderSpinnyBit() {
        this.Crossbar.func_78785_a(0.0625f);
        this.Dingus1.func_78785_a(0.0625f);
        this.Dingus2.func_78785_a(0.0625f);
        this.Core.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
