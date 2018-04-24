package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class ModelArcaneBore extends ModelBase
{
    ModelRenderer crystal;
    ModelRenderer leg2;
    ModelRenderer tripod;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer leg1;
    ModelRenderer magbase;
    ModelRenderer base;
    ModelRenderer domebase;
    ModelRenderer dome;
    ModelRenderer tip;
    
    public ModelArcaneBore() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.leg2 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg2.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg2.func_78787_b(64, 32);
        this.setRotation(this.leg2, 0.5235988f, 1.570796f, 0.0f);
        (this.tripod = new ModelRenderer((ModelBase)this, 13, 0)).func_78789_a(-1.5f, 0.0f, -1.5f, 3, 2, 3);
        this.tripod.func_78793_a(0.0f, 12.0f, 0.0f);
        this.tripod.func_78787_b(64, 32);
        this.setRotation(this.tripod, 0.0f, 0.0f, 0.0f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg3.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg3.func_78787_b(64, 32);
        this.setRotation(this.leg3, 0.5235988f, 3.141593f, 0.0f);
        (this.leg4 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg4.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg4.func_78787_b(64, 32);
        this.setRotation(this.leg4, 0.5235988f, 4.712389f, 0.0f);
        (this.leg1 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg1.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg1.func_78787_b(64, 32);
        this.setRotation(this.leg1, 0.5235988f, 0.0f, 0.0f);
        (this.base = new ModelRenderer((ModelBase)this, 32, 0)).func_78789_a(-3.0f, -6.0f, -3.0f, 6, 6, 6);
        this.base.func_78793_a(0.0f, 13.0f, 0.0f);
        this.base.func_78787_b(64, 32);
        this.setRotation(this.base, 0.0f, 0.0f, 0.0f);
        (this.crystal = new ModelRenderer((ModelBase)this, 32, 25)).func_78789_a(-1.0f, -4.0f, 5.0f, 2, 2, 2);
        this.crystal.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crystal.func_78787_b(64, 32);
        this.setRotation(this.crystal, 0.0f, 0.0f, 0.0f);
        (this.domebase = new ModelRenderer((ModelBase)this, 32, 19)).func_78789_a(-2.0f, -5.0f, 3.0f, 4, 4, 1);
        this.domebase.func_78793_a(0.0f, 0.0f, 0.0f);
        this.domebase.func_78787_b(64, 32);
        this.setRotation(this.domebase, 0.0f, 0.0f, 0.0f);
        (this.dome = new ModelRenderer((ModelBase)this, 44, 16)).func_78789_a(-2.0f, -5.0f, 4.0f, 4, 4, 4);
        this.dome.func_78793_a(0.0f, 0.0f, 0.0f);
        this.dome.func_78787_b(64, 32);
        this.setRotation(this.dome, 0.0f, 0.0f, 0.0f);
        (this.magbase = new ModelRenderer((ModelBase)this, 0, 18)).func_78789_a(-1.0f, -4.0f, -6.0f, 2, 2, 3);
        this.magbase.func_78793_a(0.0f, 0.0f, 0.0f);
        this.magbase.func_78787_b(64, 32);
        this.magbase.field_78809_i = true;
        this.setRotation(this.magbase, 0.0f, 0.0f, 0.0f);
        (this.tip = new ModelRenderer((ModelBase)this, 0, 9)).func_78789_a(-1.5f, 0.0f, -1.5f, 3, 3, 3);
        this.tip.func_78793_a(0.0f, -3.0f, -6.0f);
        this.tip.func_78787_b(64, 32);
        this.tip.field_78809_i = true;
        this.setRotation(this.tip, -1.570796f, 0.0f, 0.0f);
        this.base.func_78792_a(this.crystal);
        this.base.func_78792_a(this.dome);
        this.base.func_78792_a(this.domebase);
        this.base.func_78792_a(this.magbase);
        this.base.func_78792_a(this.tip);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        this.leg2.func_78785_a(f5);
        this.tripod.func_78785_a(f5);
        this.leg3.func_78785_a(f5);
        this.leg4.func_78785_a(f5);
        this.leg1.func_78785_a(f5);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.base.func_78785_a(f5);
        GL11.glDisable(3042);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
    
    public void func_78087_a(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float headpitch, final float headyaw, final float p_78087_6_, final Entity entity) {
        this.base.field_78796_g = headpitch / 57.295776f;
        this.base.field_78795_f = headyaw / 57.295776f;
    }
}
