package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.util.math.*;

public class ModelPech extends ModelBiped
{
    ModelRenderer Jowls;
    ModelRenderer LowerPack;
    ModelRenderer UpperPack;
    
    public ModelPech() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.field_78115_e = new ModelRenderer((ModelBase)this, 34, 12)).func_78789_a(-3.0f, 0.0f, 0.0f, 6, 10, 6);
        this.field_78115_e.func_78793_a(0.0f, 9.0f, -3.0f);
        this.field_78115_e.func_78787_b(128, 64);
        this.field_78115_e.field_78809_i = true;
        this.setRotation(this.field_78115_e, 0.3129957f, 0.0f, 0.0f);
        this.field_178721_j = new ModelRenderer((ModelBase)this, 35, 1);
        this.field_178721_j.field_78809_i = true;
        this.field_178721_j.func_78789_a(-2.9f, 0.0f, 0.0f, 3, 6, 3);
        this.field_178721_j.func_78793_a(0.0f, 18.0f, 0.0f);
        this.field_178721_j.func_78787_b(128, 64);
        this.field_178721_j.field_78809_i = true;
        this.setRotation(this.field_178721_j, 0.0f, 0.0f, 0.0f);
        this.field_178721_j.field_78809_i = false;
        (this.field_178722_k = new ModelRenderer((ModelBase)this, 35, 1)).func_78789_a(-0.1f, 0.0f, 0.0f, 3, 6, 3);
        this.field_178722_k.func_78793_a(0.0f, 18.0f, 0.0f);
        this.field_178722_k.func_78787_b(128, 64);
        this.field_178722_k.field_78809_i = true;
        this.setRotation(this.field_178722_k, 0.0f, 0.0f, 0.0f);
        (this.field_78116_c = new ModelRenderer((ModelBase)this, 2, 11)).func_78789_a(-3.5f, -5.0f, -5.0f, 7, 5, 5);
        this.field_78116_c.func_78793_a(0.0f, 8.0f, 0.0f);
        this.field_78116_c.func_78787_b(128, 64);
        this.field_78116_c.field_78809_i = true;
        this.setRotation(this.field_78116_c, 0.0f, 0.0f, 0.0f);
        (this.Jowls = new ModelRenderer((ModelBase)this, 1, 21)).func_78789_a(-4.0f, -1.0f, -6.0f, 8, 3, 5);
        this.Jowls.func_78793_a(0.0f, 8.0f, 0.0f);
        this.Jowls.func_78787_b(128, 64);
        this.Jowls.field_78809_i = true;
        this.setRotation(this.Jowls, 0.0f, 0.0f, 0.0f);
        (this.LowerPack = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-5.0f, 0.0f, 0.0f, 10, 5, 5);
        this.LowerPack.func_78793_a(0.0f, 10.0f, 3.5f);
        this.LowerPack.func_78787_b(128, 64);
        this.LowerPack.field_78809_i = true;
        this.setRotation(this.LowerPack, 0.3013602f, 0.0f, 0.0f);
        (this.UpperPack = new ModelRenderer((ModelBase)this, 64, 1)).func_78789_a(-7.5f, -14.0f, 0.0f, 15, 14, 11);
        this.UpperPack.func_78793_a(0.0f, 10.0f, 3.0f);
        this.UpperPack.func_78787_b(128, 64);
        this.UpperPack.field_78809_i = true;
        this.setRotation(this.UpperPack, 0.4537856f, 0.0f, 0.0f);
        this.field_178723_h = new ModelRenderer((ModelBase)this, 52, 2);
        this.field_178723_h.field_78809_i = true;
        this.field_178723_h.func_78789_a(-2.0f, 0.0f, -1.0f, 2, 6, 2);
        this.field_178723_h.func_78793_a(-3.0f, 10.0f, -1.0f);
        this.field_178723_h.func_78787_b(128, 64);
        this.field_178723_h.field_78809_i = true;
        this.setRotation(this.field_178723_h, 0.0f, 0.0f, 0.0f);
        this.field_178723_h.field_78809_i = false;
        (this.field_178724_i = new ModelRenderer((ModelBase)this, 52, 2)).func_78789_a(0.0f, 0.0f, -1.0f, 2, 6, 2);
        this.field_178724_i.func_78793_a(3.0f, 10.0f, -1.0f);
        this.field_178724_i.func_78787_b(128, 64);
        this.field_178724_i.field_78809_i = true;
        this.setRotation(this.field_178724_i, 0.0f, 0.0f, 0.0f);
    }
    
    public void func_78088_a(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.func_78087_a(par2, par3, par4, par5, par6, par7, par1Entity);
        this.field_78115_e.func_78785_a(par7);
        this.field_178721_j.func_78785_a(par7);
        this.field_178722_k.func_78785_a(par7);
        this.field_78116_c.func_78785_a(par7);
        this.Jowls.func_78785_a(par7);
        this.LowerPack.func_78785_a(par7);
        this.UpperPack.func_78785_a(par7);
        this.field_178723_h.func_78785_a(par7);
        this.field_178724_i.func_78785_a(par7);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
    
    public void func_78087_a(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        this.field_78116_c.field_78796_g = par4 / 57.295776f;
        this.field_78116_c.field_78795_f = par5 / 57.295776f;
        float mumble = 0.0f;
        if (entity instanceof EntityPech) {
            mumble = ((EntityPech)entity).mumble;
        }
        this.Jowls.field_78796_g = this.field_78116_c.field_78796_g;
        this.Jowls.field_78795_f = this.field_78116_c.field_78795_f + (0.2617994f + MathHelper.func_76134_b(par1 * 0.6662f) * par2 * 0.25f) + 0.34906587f * Math.abs(MathHelper.func_76126_a(mumble / 8.0f));
        this.field_178723_h.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662f + 3.1415927f) * 2.0f * par2 * 0.5f;
        this.field_178724_i.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662f) * 2.0f * par2 * 0.5f;
        this.field_178723_h.field_78808_h = 0.0f;
        this.field_178724_i.field_78808_h = 0.0f;
        this.field_178721_j.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662f) * 1.4f * par2;
        this.field_178722_k.field_78795_f = MathHelper.func_76134_b(par1 * 0.6662f + 3.1415927f) * 1.4f * par2;
        this.field_178721_j.field_78796_g = 0.0f;
        this.field_178722_k.field_78796_g = 0.0f;
        this.LowerPack.field_78796_g = MathHelper.func_76134_b(par1 * 0.6662f) * 2.0f * par2 * 0.125f;
        this.LowerPack.field_78808_h = MathHelper.func_76134_b(par1 * 0.6662f) * 2.0f * par2 * 0.125f;
        if (this.field_78093_q) {
            final ModelRenderer field_178723_h = this.field_178723_h;
            field_178723_h.field_78795_f -= 0.62831855f;
            final ModelRenderer field_178724_i = this.field_178724_i;
            field_178724_i.field_78795_f -= 0.62831855f;
            this.field_178721_j.field_78795_f = -1.2566371f;
            this.field_178722_k.field_78795_f = -1.2566371f;
            this.field_178721_j.field_78796_g = 0.31415927f;
            this.field_178722_k.field_78796_g = -0.31415927f;
        }
        this.field_178723_h.field_78796_g = 0.0f;
        this.field_178724_i.field_78796_g = 0.0f;
        if (this.field_78095_p > -9990.0f) {
            float f6 = this.field_78095_p;
            final ModelRenderer field_178723_h2 = this.field_178723_h;
            field_178723_h2.field_78796_g += this.field_78115_e.field_78796_g;
            final ModelRenderer field_178724_i2 = this.field_178724_i;
            field_178724_i2.field_78796_g += this.field_78115_e.field_78796_g;
            final ModelRenderer field_178724_i3 = this.field_178724_i;
            field_178724_i3.field_78795_f += this.field_78115_e.field_78796_g;
            f6 = 1.0f - this.field_78095_p;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            final float f7 = MathHelper.func_76126_a(f6 * 3.1415927f);
            final float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927f) * -(this.field_78116_c.field_78795_f - 0.7f) * 0.75f;
            this.field_178723_h.field_78795_f -= (float)(f7 * 1.2 + f8);
            final ModelRenderer field_178723_h3 = this.field_178723_h;
            field_178723_h3.field_78796_g += this.field_78115_e.field_78796_g * 2.0f;
            this.field_178723_h.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927f) * -0.4f;
        }
        if (entity.func_70093_af()) {
            final ModelRenderer field_178723_h4 = this.field_178723_h;
            field_178723_h4.field_78795_f += 0.4f;
            final ModelRenderer field_178724_i4 = this.field_178724_i;
            field_178724_i4.field_78795_f += 0.4f;
        }
        final ModelRenderer field_178723_h5 = this.field_178723_h;
        field_178723_h5.field_78808_h += MathHelper.func_76134_b(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer field_178724_i5 = this.field_178724_i;
        field_178724_i5.field_78808_h -= MathHelper.func_76134_b(par3 * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer field_178723_h6 = this.field_178723_h;
        field_178723_h6.field_78795_f += MathHelper.func_76126_a(par3 * 0.067f) * 0.05f;
        final ModelRenderer field_178724_i6 = this.field_178724_i;
        field_178724_i6.field_78795_f -= MathHelper.func_76126_a(par3 * 0.067f) * 0.05f;
    }
}
