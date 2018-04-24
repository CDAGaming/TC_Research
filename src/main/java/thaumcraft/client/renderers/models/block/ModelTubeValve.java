package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelTubeValve extends ModelBase
{
    ModelRenderer ValveRod;
    ModelRenderer ValveRing;
    
    public ModelTubeValve() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.ValveRod = new ModelRenderer((ModelBase)this, 0, 10)).func_78789_a(-1.0f, 2.0f, -1.0f, 2, 2, 2);
        this.ValveRod.func_78793_a(0.0f, 0.0f, 0.0f);
        this.ValveRod.func_78787_b(64, 32);
        this.ValveRod.field_78809_i = true;
        this.setRotation(this.ValveRod, 0.0f, 0.0f, 0.0f);
        (this.ValveRing = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-2.0f, 4.0f, -2.0f, 4, 1, 4);
        this.ValveRing.func_78793_a(0.0f, 0.0f, 0.0f);
        this.ValveRing.func_78787_b(64, 32);
        this.ValveRing.field_78809_i = true;
        this.setRotation(this.ValveRing, 0.0f, 0.0f, 0.0f);
    }
    
    public void renderRod() {
        this.ValveRod.func_78785_a(0.0625f);
    }
    
    public void renderRing() {
        this.ValveRing.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
