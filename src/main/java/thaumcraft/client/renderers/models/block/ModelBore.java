package thaumcraft.client.renderers.models.block;

import net.minecraft.client.model.*;

public class ModelBore extends ModelBase
{
    ModelRenderer Base;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer NozCrossbar;
    ModelRenderer NozFront;
    ModelRenderer NozMid;
    
    public ModelBore() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Base = new ModelRenderer((ModelBase)this, 0, 32)).func_78789_a(-6.0f, 0.0f, -6.0f, 12, 2, 12);
        this.Base.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Base.func_78787_b(64, 32);
        this.Base.field_78809_i = true;
        this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
        (this.Side1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-2.0f, 2.0f, -5.5f, 4, 8, 1);
        this.Side1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Side1.func_78787_b(64, 32);
        this.Side1.field_78809_i = true;
        this.setRotation(this.Side1, 0.0f, 0.0f, 0.0f);
        (this.Side2 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-2.0f, 2.0f, 4.5f, 4, 8, 1);
        this.Side2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.Side2.func_78787_b(64, 32);
        this.Side2.field_78809_i = true;
        this.setRotation(this.Side2, 0.0f, 0.0f, 0.0f);
        (this.NozCrossbar = new ModelRenderer((ModelBase)this, 0, 48)).func_78789_a(-1.0f, -1.0f, -6.0f, 2, 2, 12);
        this.NozCrossbar.func_78793_a(0.0f, 8.0f, 0.0f);
        this.NozCrossbar.func_78787_b(64, 32);
        this.NozCrossbar.field_78809_i = true;
        this.setRotation(this.NozCrossbar, 0.0f, 0.0f, 0.0f);
        (this.NozFront = new ModelRenderer((ModelBase)this, 30, 14)).func_78789_a(4.0f, -2.5f, -2.5f, 4, 5, 5);
        this.NozFront.func_78793_a(0.0f, 8.0f, 0.0f);
        this.NozFront.func_78787_b(64, 32);
        this.NozFront.field_78809_i = true;
        this.setRotation(this.NozFront, 0.0f, 0.0f, 0.0f);
        (this.NozMid = new ModelRenderer((ModelBase)this, 0, 14)).func_78789_a(-2.0f, -4.0f, -4.0f, 6, 8, 8);
        this.NozMid.func_78793_a(0.0f, 8.0f, 0.0f);
        this.NozMid.func_78787_b(64, 32);
        this.NozMid.field_78809_i = true;
        this.setRotation(this.NozMid, 0.0f, 0.0f, 0.0f);
    }
    
    public void renderBase() {
        final float f5 = 0.0625f;
        this.Base.func_78785_a(f5);
        this.Side1.func_78785_a(f5);
        this.Side2.func_78785_a(f5);
        this.NozCrossbar.func_78785_a(f5);
    }
    
    public void renderNozzle() {
        final float f5 = 0.0625f;
        this.NozFront.func_78785_a(f5);
        this.NozMid.func_78785_a(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
