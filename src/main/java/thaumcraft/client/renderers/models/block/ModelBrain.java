package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBrain extends ModelBase
{
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    
    public ModelBrain() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Shape1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(0.0f, 0.0f, 0.0f, 12, 10, 16);
        this.Shape1.func_78793_a(-6.0f, 8.0f, -8.0f);
        this.Shape1.func_78787_b(128, 64);
        this.Shape1.field_78809_i = true;
        this.setRotation(this.Shape1, 0.0f, 0.0f, 0.0f);
        (this.Shape2 = new ModelRenderer((ModelBase)this, 64, 0)).func_78789_a(0.0f, 0.0f, 0.0f, 8, 3, 7);
        this.Shape2.func_78793_a(-4.0f, 18.0f, 0.0f);
        this.Shape2.func_78787_b(128, 64);
        this.Shape2.field_78809_i = true;
        this.setRotation(this.Shape2, 0.0f, 0.0f, 0.0f);
        (this.Shape3 = new ModelRenderer((ModelBase)this, 0, 32)).func_78789_a(0.0f, 0.0f, 0.0f, 2, 6, 2);
        this.Shape3.func_78793_a(-1.0f, 18.0f, -2.0f);
        this.Shape3.func_78787_b(128, 64);
        this.Shape3.field_78809_i = true;
        this.setRotation(this.Shape3, 0.4089647f, 0.0f, 0.0f);
    }
    
    public void render() {
        this.Shape1.func_78785_a(0.0625f);
        this.Shape2.func_78785_a(0.0625f);
        this.Shape3.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
