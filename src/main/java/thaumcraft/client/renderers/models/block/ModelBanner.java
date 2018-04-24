package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBanner extends ModelBase
{
    ModelRenderer B1;
    ModelRenderer B2;
    ModelRenderer Beam;
    public ModelRenderer Banner;
    ModelRenderer Pole;
    
    public ModelBanner() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.B1 = new ModelRenderer((ModelBase)this, 0, 29)).func_78789_a(-5.0f, -7.5f, -1.5f, 2, 3, 3);
        this.B1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.B1.func_78787_b(128, 64);
        this.B1.field_78809_i = true;
        this.setRotation(this.B1, 0.0f, 0.0f, 0.0f);
        (this.B2 = new ModelRenderer((ModelBase)this, 0, 29)).func_78789_a(3.0f, -7.5f, -1.5f, 2, 3, 3);
        this.B2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.B2.func_78787_b(128, 64);
        this.B2.field_78809_i = true;
        this.setRotation(this.B2, 0.0f, 0.0f, 0.0f);
        (this.Beam = new ModelRenderer((ModelBase)this, 30, 0)).func_78789_a(-7.0f, -7.0f, -1.0f, 14, 2, 2);
        this.Beam.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Beam.func_78787_b(128, 64);
        this.Beam.field_78809_i = true;
        this.setRotation(this.Beam, 0.0f, 0.0f, 0.0f);
        (this.Banner = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-7.0f, 0.0f, -0.5f, 14, 28, 1);
        this.Banner.func_78793_a(0.0f, -5.0f, 0.0f);
        this.Banner.func_78787_b(128, 64);
        this.Banner.field_78809_i = true;
        this.setRotation(this.Banner, 0.0f, 0.0f, 0.0f);
        (this.Pole = new ModelRenderer((ModelBase)this, 62, 0)).func_78789_a(0.0f, 0.0f, -1.0f, 2, 31, 2);
        this.Pole.func_78793_a(-1.0f, -7.0f, -2.0f);
        this.Pole.func_78787_b(128, 64);
        this.Pole.field_78809_i = true;
        this.setRotation(this.Pole, 0.0f, 0.0f, 0.0f);
    }
    
    public void renderPole() {
        this.Pole.func_78785_a(0.0625f);
    }
    
    public void renderBeam() {
        this.Beam.func_78785_a(0.0625f);
    }
    
    public void renderTabs() {
        this.B1.func_78785_a(0.0625f);
        this.B2.func_78785_a(0.0625f);
    }
    
    public void renderBanner() {
        this.Banner.func_78785_a(0.0625f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
