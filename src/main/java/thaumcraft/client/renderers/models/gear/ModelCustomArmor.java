package thaumcraft.client.renderers.models.gear;

import net.minecraft.entity.*;
import thaumcraft.client.lib.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;

public class ModelCustomArmor extends ModelBiped
{
    public ModelCustomArmor(final float f, final int i, final int j, final int k) {
        super(f, (float)i, j, k);
    }
    
    public void func_78087_a(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            this.field_78095_p = ((EntityLivingBase)entityIn).func_70678_g(UtilsFX.sysPartialTicks);
        }
        if (entityIn instanceof EntityArmorStand) {
            this.setRotationAnglesStand(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        }
        else if (entityIn instanceof EntitySkeleton || entityIn instanceof EntityZombie) {
            this.setRotationAnglesZombie(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        }
        else {
            final boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).func_184599_cB() > 4;
            this.field_78116_c.field_78796_g = netHeadYaw * 0.017453292f;
            if (flag) {
                this.field_78116_c.field_78795_f = -0.7853982f;
            }
            else {
                this.field_78116_c.field_78795_f = headPitch * 0.017453292f;
            }
            this.field_78115_e.field_78796_g = 0.0f;
            this.field_178723_h.field_78798_e = 0.0f;
            this.field_178723_h.field_78800_c = -5.0f;
            this.field_178724_i.field_78798_e = 0.0f;
            this.field_178724_i.field_78800_c = 5.0f;
            float f = 1.0f;
            if (flag) {
                f = (float)(entityIn.field_70159_w * entityIn.field_70159_w + entityIn.field_70181_x * entityIn.field_70181_x + entityIn.field_70179_y * entityIn.field_70179_y);
                f /= 0.2f;
                f *= f * f;
            }
            if (f < 1.0f) {
                f = 1.0f;
            }
            this.field_178723_h.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662f + 3.1415927f) * 2.0f * limbSwingAmount * 0.5f / f;
            this.field_178724_i.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / f;
            this.field_178723_h.field_78808_h = 0.0f;
            this.field_178724_i.field_78808_h = 0.0f;
            this.field_178721_j.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
            this.field_178722_k.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount / f;
            this.field_178721_j.field_78796_g = 0.0f;
            this.field_178722_k.field_78796_g = 0.0f;
            this.field_178721_j.field_78808_h = 0.0f;
            this.field_178722_k.field_78808_h = 0.0f;
            if (this.field_78093_q) {
                final ModelRenderer field_178723_h = this.field_178723_h;
                field_178723_h.field_78795_f -= 0.62831855f;
                final ModelRenderer field_178724_i = this.field_178724_i;
                field_178724_i.field_78795_f -= 0.62831855f;
                this.field_178721_j.field_78795_f = -1.4137167f;
                this.field_178721_j.field_78796_g = 0.31415927f;
                this.field_178721_j.field_78808_h = 0.07853982f;
                this.field_178722_k.field_78795_f = -1.4137167f;
                this.field_178722_k.field_78796_g = -0.31415927f;
                this.field_178722_k.field_78808_h = -0.07853982f;
            }
            this.field_178723_h.field_78796_g = 0.0f;
            this.field_178723_h.field_78808_h = 0.0f;
            switch (this.field_187075_l) {
                case EMPTY: {
                    this.field_178724_i.field_78796_g = 0.0f;
                    break;
                }
                case BLOCK: {
                    this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5f - 0.9424779f;
                    this.field_178724_i.field_78796_g = 0.5235988f;
                    break;
                }
                case ITEM: {
                    this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5f - 0.31415927f;
                    this.field_178724_i.field_78796_g = 0.0f;
                    break;
                }
            }
            switch (this.field_187076_m) {
                case EMPTY: {
                    this.field_178723_h.field_78796_g = 0.0f;
                    break;
                }
                case BLOCK: {
                    this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5f - 0.9424779f;
                    this.field_178723_h.field_78796_g = -0.5235988f;
                    break;
                }
                case ITEM: {
                    this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5f - 0.31415927f;
                    this.field_178723_h.field_78796_g = 0.0f;
                    break;
                }
            }
            if (this.field_78095_p > 0.0f) {
                final EnumHandSide enumhandside = this.func_187072_a(entityIn);
                final ModelRenderer modelrenderer = this.func_187074_a(enumhandside);
                float f2 = this.field_78095_p;
                this.field_78115_e.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 6.2831855f) * 0.2f;
                if (enumhandside == EnumHandSide.LEFT) {
                    final ModelRenderer field_78115_e = this.field_78115_e;
                    field_78115_e.field_78796_g *= -1.0f;
                }
                this.field_178723_h.field_78798_e = MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0f;
                this.field_178723_h.field_78800_c = -MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0f;
                this.field_178724_i.field_78798_e = -MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * 5.0f;
                this.field_178724_i.field_78800_c = MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * 5.0f;
                final ModelRenderer field_178723_h2 = this.field_178723_h;
                field_178723_h2.field_78796_g += this.field_78115_e.field_78796_g;
                final ModelRenderer field_178724_i2 = this.field_178724_i;
                field_178724_i2.field_78796_g += this.field_78115_e.field_78796_g;
                final ModelRenderer field_178724_i3 = this.field_178724_i;
                field_178724_i3.field_78795_f += this.field_78115_e.field_78796_g;
                f2 = 1.0f - this.field_78095_p;
                f2 *= f2;
                f2 *= f2;
                f2 = 1.0f - f2;
                final float f3 = MathHelper.func_76126_a(f2 * 3.1415927f);
                final float f4 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927f) * -(this.field_78116_c.field_78795_f - 0.7f) * 0.75f;
                modelrenderer.field_78795_f -= (float)(f3 * 1.2 + f4);
                final ModelRenderer modelRenderer = modelrenderer;
                modelRenderer.field_78796_g += this.field_78115_e.field_78796_g * 2.0f;
                final ModelRenderer modelRenderer2 = modelrenderer;
                modelRenderer2.field_78808_h += MathHelper.func_76126_a(this.field_78095_p * 3.1415927f) * -0.4f;
            }
            if (this.field_78117_n) {
                this.field_78115_e.field_78795_f = 0.5f;
                final ModelRenderer field_178723_h3 = this.field_178723_h;
                field_178723_h3.field_78795_f += 0.4f;
                final ModelRenderer field_178724_i4 = this.field_178724_i;
                field_178724_i4.field_78795_f += 0.4f;
                this.field_178721_j.field_78798_e = 4.0f;
                this.field_178722_k.field_78798_e = 4.0f;
                this.field_178721_j.field_78797_d = 13.0f;
                this.field_178722_k.field_78797_d = 13.0f;
                this.field_78116_c.field_78797_d = 4.5f;
                this.field_78115_e.field_78797_d = 4.5f;
                this.field_178723_h.field_78797_d = 5.0f;
                this.field_178724_i.field_78797_d = 5.0f;
            }
            else {
                this.field_78115_e.field_78795_f = 0.0f;
                this.field_178721_j.field_78798_e = 0.1f;
                this.field_178722_k.field_78798_e = 0.1f;
                this.field_178721_j.field_78797_d = 12.0f;
                this.field_178722_k.field_78797_d = 12.0f;
                this.field_78116_c.field_78797_d = 0.0f;
                this.field_78115_e.field_78797_d = 0.0f;
                this.field_178723_h.field_78797_d = 2.0f;
                this.field_178724_i.field_78797_d = 2.0f;
            }
            final ModelRenderer field_178723_h4 = this.field_178723_h;
            field_178723_h4.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer field_178724_i5 = this.field_178724_i;
            field_178724_i5.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer field_178723_h5 = this.field_178723_h;
            field_178723_h5.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067f) * 0.05f;
            final ModelRenderer field_178724_i6 = this.field_178724_i;
            field_178724_i6.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067f) * 0.05f;
            if (this.field_187076_m == ModelBiped.ArmPose.BOW_AND_ARROW) {
                this.field_178723_h.field_78796_g = -0.1f + this.field_78116_c.field_78796_g;
                this.field_178724_i.field_78796_g = 0.1f + this.field_78116_c.field_78796_g + 0.4f;
                this.field_178723_h.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
                this.field_178724_i.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
            }
            else if (this.field_187075_l == ModelBiped.ArmPose.BOW_AND_ARROW) {
                this.field_178723_h.field_78796_g = -0.1f + this.field_78116_c.field_78796_g - 0.4f;
                this.field_178724_i.field_78796_g = 0.1f + this.field_78116_c.field_78796_g;
                this.field_178723_h.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
                this.field_178724_i.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
            }
            func_178685_a(this.field_78116_c, this.field_178720_f);
        }
    }
    
    public void setRotationAnglesZombie(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        final boolean flag = entityIn instanceof EntityZombie && ((EntityZombie)entityIn).func_184734_db();
        final float f = MathHelper.func_76126_a(this.field_78095_p * 3.1415927f);
        final float f2 = MathHelper.func_76126_a((1.0f - (1.0f - this.field_78095_p) * (1.0f - this.field_78095_p)) * 3.1415927f);
        this.field_178723_h.field_78808_h = 0.0f;
        this.field_178724_i.field_78808_h = 0.0f;
        this.field_178723_h.field_78796_g = -(0.1f - f * 0.6f);
        this.field_178724_i.field_78796_g = 0.1f - f * 0.6f;
        final float f3 = -3.1415927f / (flag ? 1.5f : 2.25f);
        this.field_178723_h.field_78795_f = f3;
        this.field_178724_i.field_78795_f = f3;
        final ModelRenderer field_178723_h = this.field_178723_h;
        field_178723_h.field_78795_f += f * 1.2f - f2 * 0.4f;
        final ModelRenderer field_178724_i = this.field_178724_i;
        field_178724_i.field_78795_f += f * 1.2f - f2 * 0.4f;
        final ModelRenderer field_178723_h2 = this.field_178723_h;
        field_178723_h2.field_78808_h += MathHelper.func_76134_b(ageInTicks * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer field_178724_i2 = this.field_178724_i;
        field_178724_i2.field_78808_h -= MathHelper.func_76134_b(ageInTicks * 0.09f) * 0.05f + 0.05f;
        final ModelRenderer field_178723_h3 = this.field_178723_h;
        field_178723_h3.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.067f) * 0.05f;
        final ModelRenderer field_178724_i3 = this.field_178724_i;
        field_178724_i3.field_78795_f -= MathHelper.func_76126_a(ageInTicks * 0.067f) * 0.05f;
    }
    
    public void setRotationAnglesStand(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        if (entityIn instanceof EntityArmorStand) {
            final EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
            this.field_78116_c.field_78795_f = 0.017453292f * entityarmorstand.func_175418_s().func_179415_b();
            this.field_78116_c.field_78796_g = 0.017453292f * entityarmorstand.func_175418_s().func_179416_c();
            this.field_78116_c.field_78808_h = 0.017453292f * entityarmorstand.func_175418_s().func_179413_d();
            this.field_78116_c.func_78793_a(0.0f, 1.0f, 0.0f);
            this.field_78115_e.field_78795_f = 0.017453292f * entityarmorstand.func_175408_t().func_179415_b();
            this.field_78115_e.field_78796_g = 0.017453292f * entityarmorstand.func_175408_t().func_179416_c();
            this.field_78115_e.field_78808_h = 0.017453292f * entityarmorstand.func_175408_t().func_179413_d();
            this.field_178724_i.field_78795_f = 0.017453292f * entityarmorstand.func_175404_u().func_179415_b();
            this.field_178724_i.field_78796_g = 0.017453292f * entityarmorstand.func_175404_u().func_179416_c();
            this.field_178724_i.field_78808_h = 0.017453292f * entityarmorstand.func_175404_u().func_179413_d();
            this.field_178723_h.field_78795_f = 0.017453292f * entityarmorstand.func_175411_v().func_179415_b();
            this.field_178723_h.field_78796_g = 0.017453292f * entityarmorstand.func_175411_v().func_179416_c();
            this.field_178723_h.field_78808_h = 0.017453292f * entityarmorstand.func_175411_v().func_179413_d();
            this.field_178722_k.field_78795_f = 0.017453292f * entityarmorstand.func_175403_w().func_179415_b();
            this.field_178722_k.field_78796_g = 0.017453292f * entityarmorstand.func_175403_w().func_179416_c();
            this.field_178722_k.field_78808_h = 0.017453292f * entityarmorstand.func_175403_w().func_179413_d();
            this.field_178722_k.func_78793_a(1.9f, 11.0f, 0.0f);
            this.field_178721_j.field_78795_f = 0.017453292f * entityarmorstand.func_175407_x().func_179415_b();
            this.field_178721_j.field_78796_g = 0.017453292f * entityarmorstand.func_175407_x().func_179416_c();
            this.field_178721_j.field_78808_h = 0.017453292f * entityarmorstand.func_175407_x().func_179413_d();
            this.field_178721_j.func_78793_a(-1.9f, 11.0f, 0.0f);
            func_178685_a(this.field_78116_c, this.field_178720_f);
        }
    }
}
