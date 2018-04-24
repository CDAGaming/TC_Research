package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBoreEmit extends ModelBase
{
    ModelRenderer Knob;
    ModelRenderer Cross1;
    ModelRenderer Cross3;
    ModelRenderer Cross2;
    ModelRenderer Rod;
    
    public ModelBoreEmit() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Knob = new ModelRenderer((ModelBase)this, 66, 0)).func_78789_a(-2.0f, 12.0f, -2.0f, 4, 4, 4);
        this.Knob.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Knob.func_78787_b(128, 64);
        this.Knob.field_78809_i = true;
        this.setRotation(this.Knob, 0.0f, 0.0f, 0.0f);
        (this.Cross1 = new ModelRenderer((ModelBase)this, 56, 16)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.Cross1.func_78793_a(0.0f, 8.0f, 0.0f);
        this.Cross1.func_78787_b(128, 64);
        this.Cross1.field_78809_i = true;
        this.setRotation(this.Cross1, 0.0f, 0.0f, 0.0f);
        (this.Cross3 = new ModelRenderer((ModelBase)this, 56, 16)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 1, 4);
        this.Cross3.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Cross3.func_78787_b(128, 64);
        this.Cross3.field_78809_i = true;
        this.setRotation(this.Cross3, 0.0f, 0.0f, 0.0f);
        (this.Cross2 = new ModelRenderer((ModelBase)this, 56, 24)).func_78789_a(-3.0f, 4.0f, -3.0f, 6, 1, 6);
        this.Cross2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Cross2.func_78787_b(128, 64);
        this.Cross2.field_78809_i = true;
        this.setRotation(this.Cross2, 0.0f, 0.0f, 0.0f);
        (this.Rod = new ModelRenderer((ModelBase)this, 56, 0)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 11, 2);
        this.Rod.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Rod.func_78787_b(128, 64);
        this.Rod.field_78809_i = true;
        this.setRotation(this.Rod, 0.0f, 0.0f, 0.0f);
    }
    
    public void render(final boolean focus) {
        final float f5 = 0.0625f;
        if (focus) {
            this.Knob.func_78785_a(f5);
        }
        this.Cross1.func_78785_a(f5);
        this.Cross3.func_78785_a(f5);
        this.Cross2.func_78785_a(f5);
        this.Rod.func_78785_a(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
