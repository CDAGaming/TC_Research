package thaumcraft.client.renderers.models;

import net.minecraft.client.model.*;

public class ModelCube extends ModelBase
{
    ModelRenderer cube;
    
    public ModelCube() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.cube = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.cube.func_78793_a(8.0f, 8.0f, 8.0f);
        this.cube.func_78787_b(64, 32);
        this.cube.field_78809_i = true;
    }
    
    public ModelCube(final int shift) {
        this.field_78090_t = 64;
        this.field_78089_u = 64;
        (this.cube = new ModelRenderer((ModelBase)this, 0, shift)).func_78789_a(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.cube.func_78793_a(0.0f, 0.0f, 0.0f);
        this.cube.func_78787_b(64, 64);
        this.cube.field_78809_i = true;
    }
    
    public void render() {
        this.cube.func_78785_a(0.0625f);
    }
    
    public void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
