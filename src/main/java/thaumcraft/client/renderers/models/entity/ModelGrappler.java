package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;

public class ModelGrappler extends ModelBase
{
    ModelRenderer core;
    ModelRenderer prong1;
    ModelRenderer prong2;
    ModelRenderer prong3;
    
    public ModelGrappler() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.core = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-1.5f, -1.5f, -1.5f, 3, 3, 3);
        this.core.func_78793_a(0.0f, 0.0f, 0.0f);
        this.core.func_78787_b(this.field_78090_t, this.field_78089_u);
        this.setRotation(this.core, 0.0f, 0.0f, 0.0f);
        (this.prong1 = new ModelRenderer((ModelBase)this, 0, 10)).func_78789_a(-0.5f, -0.5f, -2.5f, 1, 1, 5);
        this.prong1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.prong1.func_78787_b(this.field_78090_t, this.field_78089_u);
        this.setRotation(this.prong1, 0.0f, 0.0f, 0.0f);
        (this.prong2 = new ModelRenderer((ModelBase)this, 0, 10)).func_78789_a(-0.5f, -0.5f, -2.5f, 1, 1, 5);
        this.prong2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.prong2.func_78787_b(this.field_78090_t, this.field_78089_u);
        this.setRotation(this.prong2, 0.0f, 1.5707964f, 0.0f);
        (this.prong3 = new ModelRenderer((ModelBase)this, 0, 10)).func_78789_a(-0.5f, -0.5f, -2.5f, 1, 1, 5);
        this.prong3.func_78793_a(0.0f, 0.0f, 0.0f);
        this.prong3.func_78787_b(this.field_78090_t, this.field_78089_u);
        this.setRotation(this.prong3, 1.5707964f, 1.5707964f, 0.0f);
    }
    
    public void render() {
        this.core.func_78785_a(0.0625f);
        this.prong1.func_78785_a(0.0625f);
        this.prong2.func_78785_a(0.0625f);
        this.prong3.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
