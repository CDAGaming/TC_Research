package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBoreBase extends ModelBase
{
    ModelRenderer Base1;
    ModelRenderer Base2;
    ModelRenderer PillarMid;
    ModelRenderer Pillar2;
    ModelRenderer Pillar3;
    ModelRenderer Pillar4;
    ModelRenderer Pillar1;
    ModelRenderer Nozzle1;
    ModelRenderer Nozzle2;
    
    public ModelBoreBase() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Base1 = new ModelRenderer((ModelBase)this, 64, 24)).func_78789_a(-8.0f, 0.0f, -8.0f, 16, 2, 16);
        this.Base1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Base1.func_78787_b(128, 64);
        this.Base1.field_78809_i = true;
        this.setRotation(this.Base1, 0.0f, 0.0f, 0.0f);
        (this.Base2 = new ModelRenderer((ModelBase)this, 64, 24)).func_78789_a(-8.0f, 0.0f, -8.0f, 16, 2, 16);
        this.Base2.func_78793_a(0.0f, 14.0f, 0.0f);
        this.Base2.func_78787_b(128, 64);
        this.Base2.field_78809_i = true;
        this.setRotation(this.Base2, 0.0f, 0.0f, 0.0f);
        (this.PillarMid = new ModelRenderer((ModelBase)this, 84, 42)).func_78789_a(-2.5f, 0.0f, -2.5f, 5, 12, 5);
        this.PillarMid.func_78793_a(0.0f, 2.0f, 0.0f);
        this.PillarMid.func_78787_b(128, 64);
        this.PillarMid.field_78809_i = true;
        this.setRotation(this.PillarMid, 0.0f, 0.0f, 0.0f);
        (this.Pillar2 = new ModelRenderer((ModelBase)this, 64, 42)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.Pillar2.func_78793_a(-5.0f, 2.0f, -5.0f);
        this.Pillar2.func_78787_b(128, 64);
        this.Pillar2.field_78809_i = true;
        this.setRotation(this.Pillar2, 0.0f, 0.0f, 0.0f);
        (this.Pillar3 = new ModelRenderer((ModelBase)this, 64, 42)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.Pillar3.func_78793_a(-5.0f, 2.0f, 5.0f);
        this.Pillar3.func_78787_b(128, 64);
        this.Pillar3.field_78809_i = true;
        this.setRotation(this.Pillar3, 0.0f, 0.0f, 0.0f);
        (this.Pillar4 = new ModelRenderer((ModelBase)this, 64, 42)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.Pillar4.func_78793_a(5.0f, 2.0f, 5.0f);
        this.Pillar4.func_78787_b(128, 64);
        this.Pillar4.field_78809_i = true;
        this.setRotation(this.Pillar4, 0.0f, 0.0f, 0.0f);
        (this.Pillar1 = new ModelRenderer((ModelBase)this, 64, 42)).func_78789_a(-2.0f, 0.0f, -2.0f, 4, 12, 4);
        this.Pillar1.func_78793_a(5.0f, 2.0f, -5.0f);
        this.Pillar1.func_78787_b(128, 64);
        this.Pillar1.field_78809_i = true;
        this.setRotation(this.Pillar1, 0.0f, 0.0f, 0.0f);
        (this.Nozzle1 = new ModelRenderer((ModelBase)this, 106, 42)).func_78789_a(2.5f, -2.0f, -2.0f, 5, 4, 4);
        this.Nozzle1.func_78793_a(0.0f, 8.0f, 0.0f);
        this.Nozzle1.func_78787_b(128, 64);
        this.Nozzle1.field_78809_i = true;
        this.setRotation(this.Nozzle1, 0.0f, 0.0f, 0.0f);
        (this.Nozzle2 = new ModelRenderer((ModelBase)this, 106, 51)).func_78789_a(7.0f, -2.5f, -2.5f, 1, 5, 5);
        this.Nozzle2.func_78793_a(0.0f, 8.0f, 0.0f);
        this.Nozzle2.func_78787_b(128, 64);
        this.Nozzle2.field_78809_i = true;
        this.setRotation(this.Nozzle2, 0.0f, 0.0f, 0.0f);
    }
    
    public void render() {
        final float f5 = 0.0625f;
        this.Base1.func_78785_a(f5);
        this.Base2.func_78785_a(f5);
        this.PillarMid.func_78785_a(f5);
        this.Pillar2.func_78785_a(f5);
        this.Pillar3.func_78785_a(f5);
        this.Pillar4.func_78785_a(f5);
        this.Pillar1.func_78785_a(f5);
    }
    
    public void renderNozzle() {
        final float f5 = 0.0625f;
        this.Nozzle1.func_78785_a(f5);
        this.Nozzle2.func_78785_a(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
