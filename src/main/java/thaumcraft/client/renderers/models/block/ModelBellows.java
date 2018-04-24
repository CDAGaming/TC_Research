package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBellows extends ModelBase
{
    public ModelRenderer BottomPlank;
    public ModelRenderer MiddlePlank;
    public ModelRenderer TopPlank;
    public ModelRenderer Bag;
    public ModelRenderer Nozzle;
    
    public ModelBellows() {
        this.field_78090_t = 128;
        this.field_78089_u = 128;
        (this.BottomPlank = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-6.0f, 0.0f, -6.0f, 12, 2, 12);
        this.BottomPlank.func_78793_a(0.0f, 22.0f, 0.0f);
        this.BottomPlank.func_78787_b(128, 128);
        this.BottomPlank.field_78809_i = true;
        this.setRotation(this.BottomPlank, 0.0f, 0.0f, 0.0f);
        (this.MiddlePlank = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-6.0f, -1.0f, -6.0f, 12, 2, 12);
        this.MiddlePlank.func_78793_a(0.0f, 16.0f, 0.0f);
        this.MiddlePlank.func_78787_b(128, 128);
        this.MiddlePlank.field_78809_i = true;
        this.setRotation(this.MiddlePlank, 0.0f, 0.0f, 0.0f);
        (this.TopPlank = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-6.0f, 0.0f, -6.0f, 12, 2, 12);
        this.TopPlank.func_78793_a(0.0f, 8.0f, 0.0f);
        this.TopPlank.func_78787_b(128, 128);
        this.TopPlank.field_78809_i = true;
        this.setRotation(this.TopPlank, 0.0f, 0.0f, 0.0f);
        (this.Bag = new ModelRenderer((ModelBase)this, 48, 0)).func_78789_a(-10.0f, -12.03333f, -10.0f, 20, 24, 20);
        this.Bag.func_78793_a(0.0f, 16.0f, 0.0f);
        this.Bag.func_78787_b(64, 32);
        this.Bag.field_78809_i = true;
        this.setRotation(this.Bag, 0.0f, 0.0f, 0.0f);
        (this.Nozzle = new ModelRenderer((ModelBase)this, 0, 36)).func_78789_a(-2.0f, -2.0f, 0.0f, 4, 4, 2);
        this.Nozzle.func_78793_a(0.0f, 16.0f, 6.0f);
        this.Nozzle.func_78787_b(128, 128);
        this.Nozzle.field_78809_i = true;
        this.setRotation(this.Nozzle, 0.0f, 0.0f, 0.0f);
    }
    
    public void render() {
        this.MiddlePlank.func_78785_a(0.0625f);
        this.Nozzle.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
