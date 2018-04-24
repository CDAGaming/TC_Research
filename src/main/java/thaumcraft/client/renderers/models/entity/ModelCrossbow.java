package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.entity.item.*;

public class ModelCrossbow extends ModelBase
{
    ModelRenderer crossl3;
    ModelRenderer crossr3;
    ModelRenderer loadbarcross;
    ModelRenderer loadbarl;
    ModelRenderer loadbarr;
    ModelRenderer barrel;
    ModelRenderer basebarcross;
    ModelRenderer ammobox;
    ModelRenderer crossbow;
    ModelRenderer basebarr;
    ModelRenderer basebarl;
    ModelRenderer crossl1;
    ModelRenderer crossl2;
    ModelRenderer crossr1;
    ModelRenderer crossr2;
    ModelRenderer tripod;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer leg1;
    ModelRenderer leg2;
    
    public ModelCrossbow() {
        this.field_78090_t = 64;
        this.field_78089_u = 32;
        (this.crossbow = new ModelRenderer((ModelBase)this, 28, 14)).func_78789_a(-2.0f, 0.0f, -7.0f, 4, 2, 14);
        this.crossbow.func_78793_a(0.0f, 10.0f, 0.0f);
        this.crossbow.func_78787_b(64, 32);
        this.crossbow.field_78809_i = true;
        this.setRotation(this.crossbow, 0.0f, 0.0f, 0.0f);
        (this.basebarr = new ModelRenderer((ModelBase)this, 40, 23)).func_78789_a(-1.0f, 0.0f, 7.0f, 1, 2, 5);
        this.basebarr.func_78793_a(0.0f, 0.0f, 0.0f);
        this.basebarr.func_78787_b(64, 32);
        this.basebarr.field_78809_i = true;
        this.setRotation(this.basebarr, 0.0f, -0.1396263f, 0.0f);
        (this.basebarl = new ModelRenderer((ModelBase)this, 40, 23)).func_78789_a(0.0f, 0.0f, 7.0f, 1, 2, 5);
        this.basebarl.func_78793_a(0.0f, 0.0f, 0.0f);
        this.basebarl.func_78787_b(64, 32);
        this.basebarl.field_78809_i = true;
        this.setRotation(this.basebarl, 0.0f, 0.1396263f, 0.0f);
        (this.barrel = new ModelRenderer((ModelBase)this, 20, 28)).func_78789_a(-1.0f, -1.0f, -8.0f, 2, 2, 2);
        this.barrel.func_78793_a(0.0f, 0.0f, 0.0f);
        this.barrel.func_78787_b(64, 32);
        this.barrel.field_78809_i = true;
        this.setRotation(this.barrel, 0.0f, 0.0f, 0.0f);
        (this.basebarcross = new ModelRenderer((ModelBase)this, 0, 13)).func_78789_a(-2.0f, 0.5f, 10.0f, 4, 1, 1);
        this.basebarcross.func_78793_a(0.0f, 0.0f, 0.0f);
        this.basebarcross.func_78787_b(64, 32);
        this.basebarcross.field_78809_i = true;
        this.setRotation(this.basebarcross, 0.0f, 0.0f, 0.0f);
        (this.ammobox = new ModelRenderer((ModelBase)this, 38, 0)).func_78789_a(-2.0f, -5.0f, -6.0f, 4, 5, 9);
        this.ammobox.func_78793_a(0.0f, 0.0f, 0.0f);
        this.ammobox.func_78787_b(64, 32);
        this.ammobox.field_78809_i = true;
        this.setRotation(this.ammobox, 0.0f, 0.0f, 0.0f);
        (this.loadbarcross = new ModelRenderer((ModelBase)this, 0, 13)).func_78789_a(-2.0f, -8.5f, -0.5f, 4, 1, 1);
        this.loadbarcross.func_78793_a(0.0f, 0.0f, 0.0f);
        this.loadbarcross.func_78787_b(64, 32);
        this.loadbarcross.field_78809_i = true;
        this.setRotation(this.loadbarcross, -0.5585054f, 0.0f, 0.0f);
        (this.loadbarl = new ModelRenderer((ModelBase)this, 0, 15)).func_78789_a(2.0f, -9.0f, -1.0f, 1, 11, 2);
        this.loadbarl.func_78793_a(0.0f, 0.0f, 0.0f);
        this.loadbarl.func_78787_b(64, 32);
        this.loadbarl.field_78809_i = true;
        this.setRotation(this.loadbarl, -0.5585054f, 0.0f, 0.0f);
        (this.loadbarr = new ModelRenderer((ModelBase)this, 0, 15)).func_78789_a(-3.0f, -9.0f, -1.0f, 1, 11, 2);
        this.loadbarr.func_78793_a(0.0f, 0.0f, 0.0f);
        this.loadbarr.func_78787_b(64, 32);
        this.loadbarr.field_78809_i = true;
        this.setRotation(this.loadbarr, -0.5585054f, 0.0f, 0.0f);
        (this.crossl1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(0.0f, 0.0f, -6.0f, 5, 2, 1);
        this.crossl1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossl1.func_78787_b(64, 32);
        this.crossl1.field_78809_i = true;
        this.setRotation(this.crossl1, 0.0f, -0.2443461f, 0.0f);
        (this.crossl2 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(4.0f, 0.0f, -5.0f, 3, 2, 1);
        this.crossl2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossl2.func_78787_b(64, 32);
        this.crossl2.field_78809_i = true;
        this.setRotation(this.crossl2, 0.0f, -0.2443461f, 0.0f);
        (this.crossl3 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(6.0f, 0.0f, -4.0f, 2, 2, 1);
        this.crossl3.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossl3.func_78787_b(64, 32);
        this.crossl3.field_78809_i = true;
        this.setRotation(this.crossl3, 0.0f, -0.2443461f, 0.0f);
        (this.crossr1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-5.0f, 0.0f, -6.0f, 5, 2, 1);
        this.crossr1.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossr1.func_78787_b(64, 32);
        this.crossr1.field_78809_i = true;
        this.setRotation(this.crossr1, 0.0f, 0.2443461f, 0.0f);
        (this.crossr2 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-7.0f, 0.0f, -5.0f, 3, 2, 1);
        this.crossr2.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossr2.func_78787_b(64, 32);
        this.crossr2.field_78809_i = true;
        this.setRotation(this.crossr2, 0.0f, 0.2443461f, 0.0f);
        (this.crossr3 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-8.0f, 0.0f, -4.0f, 2, 2, 1);
        this.crossr3.func_78793_a(0.0f, 0.0f, 0.0f);
        this.crossr3.func_78787_b(64, 32);
        this.crossr3.field_78809_i = true;
        this.setRotation(this.crossr3, 0.0f, 0.2443461f, 0.0f);
        (this.leg2 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg2.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg2.func_78787_b(64, 32);
        this.leg2.field_78809_i = true;
        this.setRotation(this.leg2, 0.5235988f, 1.570796f, 0.0f);
        (this.tripod = new ModelRenderer((ModelBase)this, 13, 0)).func_78789_a(-1.5f, 0.0f, -1.5f, 3, 2, 3);
        this.tripod.func_78793_a(0.0f, 12.0f, 0.0f);
        this.tripod.func_78787_b(64, 32);
        this.tripod.field_78809_i = true;
        this.setRotation(this.tripod, 0.0f, 0.0f, 0.0f);
        (this.leg3 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg3.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg3.func_78787_b(64, 32);
        this.leg3.field_78809_i = true;
        this.setRotation(this.leg3, 0.5235988f, 3.141593f, 0.0f);
        (this.leg4 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg4.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg4.func_78787_b(64, 32);
        this.leg4.field_78809_i = true;
        this.setRotation(this.leg4, 0.5235988f, 4.712389f, 0.0f);
        (this.leg1 = new ModelRenderer((ModelBase)this, 20, 10)).func_78789_a(-1.0f, 1.0f, -1.0f, 2, 13, 2);
        this.leg1.func_78793_a(0.0f, 12.0f, 0.0f);
        this.leg1.func_78787_b(64, 32);
        this.leg1.field_78809_i = true;
        this.setRotation(this.leg1, 0.5235988f, 0.0f, 0.0f);
        this.crossbow.func_78792_a(this.ammobox);
        this.crossbow.func_78792_a(this.barrel);
        this.crossbow.func_78792_a(this.basebarcross);
        this.crossbow.func_78792_a(this.basebarr);
        this.crossbow.func_78792_a(this.basebarl);
        this.crossbow.func_78792_a(this.loadbarcross);
        this.crossbow.func_78792_a(this.loadbarl);
        this.crossbow.func_78792_a(this.loadbarr);
        this.crossbow.func_78792_a(this.crossl1);
        this.crossbow.func_78792_a(this.crossl2);
        this.crossbow.func_78792_a(this.crossl3);
        this.crossbow.func_78792_a(this.crossr1);
        this.crossbow.func_78792_a(this.crossr2);
        this.crossbow.func_78792_a(this.crossr3);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        this.crossbow.func_78785_a(f5);
        this.leg2.func_78785_a(f5);
        this.tripod.func_78785_a(f5);
        this.leg3.func_78785_a(f5);
        this.leg4.func_78785_a(f5);
        this.leg1.func_78785_a(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
    
    public void func_78087_a(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float headpitch, final float headyaw, final float p_78087_6_, final Entity entity) {
        this.crossbow.field_78796_g = headpitch / 57.295776f;
        this.crossbow.field_78795_f = headyaw / 57.295776f;
        if (this.field_78095_p > -9990.0f) {
            final float f6 = this.field_78095_p;
            final ModelRenderer crossl1 = this.crossl1;
            final ModelRenderer crossl2 = this.crossl2;
            final ModelRenderer crossl3 = this.crossl3;
            final float field_78796_g = -0.2f + MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927f * 2.0f) * 0.2f;
            crossl3.field_78796_g = field_78796_g;
            crossl2.field_78796_g = field_78796_g;
            crossl1.field_78796_g = field_78796_g;
            final ModelRenderer crossr1 = this.crossr1;
            final ModelRenderer crossr2 = this.crossr2;
            final ModelRenderer crossr3 = this.crossr3;
            final float field_78796_g2 = 0.2f - MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927f * 2.0f) * 0.2f;
            crossr3.field_78796_g = field_78796_g2;
            crossr2.field_78796_g = field_78796_g2;
            crossr1.field_78796_g = field_78796_g2;
        }
        final float lp = ((EntityTurretCrossbow)entity).loadProgressForRender;
        final ModelRenderer loadbarcross = this.loadbarcross;
        final ModelRenderer loadbarl = this.loadbarl;
        final ModelRenderer loadbarr = this.loadbarr;
        final float field_78795_f = -0.5f + MathHelper.func_76126_a(MathHelper.func_76129_c(lp) * 3.1415927f * 2.0f) * 0.5f;
        loadbarr.field_78795_f = field_78795_f;
        loadbarl.field_78795_f = field_78795_f;
        loadbarcross.field_78795_f = field_78795_f;
        if (entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx() instanceof EntityMinecart) {
            this.leg1.field_82908_p = -0.5f;
            this.leg2.field_82908_p = -0.5f;
            this.leg3.field_82908_p = -0.5f;
            this.leg4.field_82908_p = -0.5f;
            this.leg1.field_78795_f = 0.1f;
            this.leg2.field_78795_f = 0.1f;
            this.leg3.field_78795_f = 0.1f;
            this.leg4.field_78795_f = 0.1f;
        }
        else {
            this.leg1.field_82908_p = 0.0f;
            this.leg2.field_82908_p = 0.0f;
            this.leg3.field_82908_p = 0.0f;
            this.leg4.field_82908_p = 0.0f;
            this.leg1.field_78795_f = 0.5f;
            this.leg2.field_78795_f = 0.5f;
            this.leg3.field_78795_f = 0.5f;
            this.leg4.field_78795_f = 0.5f;
        }
    }
}
