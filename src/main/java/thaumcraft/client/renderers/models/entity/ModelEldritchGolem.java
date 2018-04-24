package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.monster.boss.*;
import net.minecraft.entity.*;

public class ModelEldritchGolem extends ModelBase
{
    ModelRenderer Frontcloth1;
    ModelRenderer CollarL;
    ModelRenderer Cloak1;
    ModelRenderer CloakCL;
    ModelRenderer CloakCR;
    ModelRenderer Cloak3;
    ModelRenderer Cloak2;
    ModelRenderer Head;
    ModelRenderer Head2;
    ModelRenderer Frontcloth0;
    ModelRenderer CollarB;
    ModelRenderer Torso;
    ModelRenderer CollarR;
    ModelRenderer CollarF;
    ModelRenderer CollarBlack;
    ModelRenderer ShoulderR1;
    ModelRenderer ArmL;
    ModelRenderer ShoulderR;
    ModelRenderer ShoulderR2;
    ModelRenderer ShoulderR0;
    ModelRenderer ArmR;
    ModelRenderer ShoulderL1;
    ModelRenderer ShoulderL0;
    ModelRenderer ShoulderL;
    ModelRenderer ShoulderL2;
    ModelRenderer BackpanelR1;
    ModelRenderer WaistR1;
    ModelRenderer WaistR2;
    ModelRenderer WaistR3;
    ModelRenderer LegR;
    ModelRenderer WaistL1;
    ModelRenderer WaistL2;
    ModelRenderer WaistL3;
    ModelRenderer Frontcloth2;
    ModelRenderer BackpanelL1;
    ModelRenderer LegL;
    
    public ModelEldritchGolem() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Cloak1 = new ModelRenderer((ModelBase)this, 0, 47)).func_78789_a(-5.0f, 1.5f, 4.0f, 10, 12, 1);
        this.Cloak1.func_78793_a(0.0f, 0.0f, -2.5f);
        this.Cloak1.func_78787_b(128, 64);
        this.setRotation(this.Cloak1, 0.1396263f, 0.0f, 0.0f);
        (this.Cloak3 = new ModelRenderer((ModelBase)this, 0, 37)).func_78789_a(-5.0f, 17.5f, -0.8f, 10, 4, 1);
        this.Cloak3.func_78793_a(0.0f, 0.0f, -2.5f);
        this.Cloak3.func_78787_b(128, 64);
        this.setRotation(this.Cloak3, 0.4465716f, 0.0f, 0.0f);
        (this.Cloak2 = new ModelRenderer((ModelBase)this, 0, 59)).func_78789_a(-5.0f, 13.5f, 1.7f, 10, 4, 1);
        this.Cloak2.func_78793_a(0.0f, 0.0f, -2.5f);
        this.Cloak2.func_78787_b(128, 64);
        this.setRotation(this.Cloak2, 0.3069452f, 0.0f, 0.0f);
        (this.CloakCL = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(3.0f, 0.5f, 2.0f, 2, 1, 3);
        this.CloakCL.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CloakCL.func_78787_b(128, 64);
        this.setRotation(this.CloakCL, 0.1396263f, 0.0f, 0.0f);
        (this.CloakCR = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(-5.0f, 0.5f, 2.0f, 2, 1, 3);
        this.CloakCR.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CloakCR.func_78787_b(128, 64);
        this.setRotation(this.CloakCR, 0.1396263f, 0.0f, 0.0f);
        (this.Head = new ModelRenderer((ModelBase)this, 47, 12)).func_78789_a(-3.5f, -6.0f, -2.5f, 7, 7, 5);
        this.Head.func_78793_a(0.0f, 4.5f, -3.8f);
        this.Head.func_78787_b(128, 64);
        this.setRotation(this.Head, -0.1047198f, 0.0f, 0.0f);
        (this.Head2 = new ModelRenderer((ModelBase)this, 26, 16)).func_78789_a(-2.0f, -2.0f, -2.0f, 4, 4, 4);
        this.Head2.func_78793_a(0.0f, 0.0f, -5.0f);
        this.Head2.func_78787_b(128, 64);
        this.setRotation(this.Head2, -0.1047198f, 0.0f, 0.0f);
        (this.CollarL = new ModelRenderer((ModelBase)this, 75, 50)).func_78789_a(3.5f, -0.5f, -7.0f, 1, 4, 10);
        this.CollarL.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CollarL.func_78787_b(128, 64);
        this.setRotation(this.CollarL, 0.837758f, 0.0f, 0.0f);
        (this.CollarR = new ModelRenderer((ModelBase)this, 67, 50)).func_78789_a(-4.5f, -0.5f, -7.0f, 1, 4, 10);
        this.CollarR.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CollarR.func_78787_b(128, 64);
        this.setRotation(this.CollarR, 0.837758f, 0.0f, 0.0f);
        (this.CollarB = new ModelRenderer((ModelBase)this, 77, 59)).func_78789_a(-3.5f, -0.5f, 2.0f, 7, 4, 1);
        this.CollarB.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CollarB.func_78787_b(128, 64);
        this.setRotation(this.CollarB, 0.837758f, 0.0f, 0.0f);
        (this.CollarF = new ModelRenderer((ModelBase)this, 77, 59)).func_78789_a(-3.5f, -0.5f, -7.0f, 7, 4, 1);
        this.CollarF.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CollarF.func_78787_b(128, 64);
        this.setRotation(this.CollarF, 0.837758f, 0.0f, 0.0f);
        (this.CollarBlack = new ModelRenderer((ModelBase)this, 22, 0)).func_78789_a(-3.5f, 0.0f, -6.0f, 7, 1, 8);
        this.CollarBlack.func_78793_a(0.0f, 0.0f, -2.5f);
        this.CollarBlack.func_78787_b(128, 64);
        this.setRotation(this.CollarBlack, 0.837758f, 0.0f, 0.0f);
        (this.Frontcloth0 = new ModelRenderer((ModelBase)this, 114, 52)).func_78789_a(-3.0f, 3.2f, -3.5f, 6, 10, 1);
        this.Frontcloth0.func_78793_a(0.0f, 0.0f, -2.5f);
        this.Frontcloth0.func_78787_b(114, 64);
        this.setRotation(this.Frontcloth0, 0.1745329f, 0.0f, 0.0f);
        (this.Frontcloth1 = new ModelRenderer((ModelBase)this, 114, 39)).func_78789_a(-1.0f, 1.5f, -3.5f, 6, 6, 1);
        this.Frontcloth1.func_78793_a(-2.0f, 12.0f, 0.0f);
        this.Frontcloth1.func_78787_b(114, 64);
        this.setRotation(this.Frontcloth1, -0.1047198f, 0.0f, 0.0f);
        (this.Frontcloth2 = new ModelRenderer((ModelBase)this, 114, 47)).func_78789_a(-1.0f, 8.5f, -1.5f, 6, 3, 1);
        this.Frontcloth2.func_78793_a(-2.0f, 11.0f, 0.0f);
        this.Frontcloth2.func_78787_b(114, 64);
        this.setRotation(this.Frontcloth2, -0.3316126f, 0.0f, 0.0f);
        (this.Torso = new ModelRenderer((ModelBase)this, 34, 45)).func_78789_a(-5.0f, 2.5f, -3.0f, 10, 10, 6);
        this.Torso.func_78793_a(0.0f, 0.0f, -2.5f);
        this.Torso.func_78787_b(128, 64);
        this.Torso.field_78809_i = true;
        this.setRotation(this.Torso, 0.1745329f, 0.0f, 0.0f);
        (this.ArmR = new ModelRenderer((ModelBase)this, 78, 32)).func_78789_a(-3.5f, 1.5f, -2.0f, 4, 13, 5);
        this.ArmR.func_78793_a(-5.0f, 3.0f, -2.0f);
        this.ArmR.func_78787_b(128, 64);
        this.setRotation(this.ArmR, 0.0f, 0.0f, 0.1047198f);
        (this.ShoulderR1 = new ModelRenderer((ModelBase)this, 0, 23)).func_78789_a(-3.3f, 4.0f, -2.5f, 1, 2, 6);
        this.ShoulderR1.func_78787_b(128, 64);
        this.setRotation(this.ShoulderR1, 0.0f, 0.0f, 1.186824f);
        (this.ShoulderR = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-4.3f, -1.0f, -3.0f, 4, 5, 7);
        this.ShoulderR.func_78787_b(128, 64);
        this.setRotation(this.ShoulderR, 0.0f, 0.0f, 1.186824f);
        (this.ShoulderR2 = new ModelRenderer((ModelBase)this, 0, 12)).func_78789_a(-2.3f, 4.0f, -3.0f, 2, 3, 7);
        this.ShoulderR2.func_78787_b(128, 64);
        this.setRotation(this.ShoulderR2, 0.0f, 0.0f, 1.186824f);
        (this.ShoulderR0 = new ModelRenderer((ModelBase)this, 56, 31)).func_78789_a(-4.5f, -1.5f, -2.5f, 5, 6, 6);
        this.ShoulderR0.func_78787_b(128, 64);
        this.setRotation(this.ShoulderR0, 0.0f, 0.0f, 0.0f);
        this.ArmL = new ModelRenderer((ModelBase)this, 78, 32);
        this.ArmL.field_78809_i = true;
        this.ArmL.func_78789_a(-0.5f, 1.5f, -2.0f, 4, 13, 5);
        this.ArmL.func_78793_a(5.0f, 3.0f, -2.0f);
        this.ArmL.func_78787_b(128, 64);
        this.setRotation(this.ArmL, 0.0f, 0.0f, -0.1047198f);
        this.ShoulderL1 = new ModelRenderer((ModelBase)this, 0, 23);
        this.ShoulderL1.field_78809_i = true;
        this.ShoulderL1.func_78789_a(2.3f, 4.0f, -2.5f, 1, 2, 6);
        this.ShoulderL1.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL1, 0.0f, 0.0f, -1.186824f);
        this.ShoulderL0 = new ModelRenderer((ModelBase)this, 56, 31);
        this.ShoulderL0.field_78809_i = true;
        this.ShoulderL0.func_78789_a(-0.5f, -1.5f, -2.5f, 5, 6, 6);
        this.ShoulderL0.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL0, 0.0f, 0.0f, 0.0f);
        this.ShoulderL = new ModelRenderer((ModelBase)this, 0, 0);
        this.ShoulderL.field_78809_i = true;
        this.ShoulderL.func_78789_a(0.3f, -1.0f, -3.0f, 4, 5, 7);
        this.ShoulderL.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL, 0.0f, 0.0f, -1.186824f);
        this.ShoulderL2 = new ModelRenderer((ModelBase)this, 0, 12);
        this.ShoulderL2.field_78809_i = true;
        this.ShoulderL2.func_78789_a(0.3f, 4.0f, -3.0f, 2, 3, 7);
        this.ShoulderL2.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL2, 0.0f, 0.0f, -1.186824f);
        (this.BackpanelR1 = new ModelRenderer((ModelBase)this, 96, 7)).func_78789_a(0.0f, 2.5f, -2.5f, 2, 2, 5);
        this.BackpanelR1.func_78793_a(-2.0f, 12.0f, 0.0f);
        this.BackpanelR1.func_78787_b(128, 64);
        this.setRotation(this.BackpanelR1, 0.0f, 0.0f, 0.1396263f);
        (this.WaistR1 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-3.0f, -0.5f, -2.5f, 5, 3, 5);
        this.WaistR1.func_78793_a(-2.0f, 12.0f, 0.0f);
        this.WaistR1.func_78787_b(128, 64);
        this.setRotation(this.WaistR1, 0.0f, 0.0f, 0.1396263f);
        (this.WaistR2 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(-3.0f, 2.5f, -2.5f, 1, 4, 5);
        this.WaistR2.func_78793_a(-2.0f, 12.0f, 0.0f);
        this.WaistR2.func_78787_b(128, 64);
        this.setRotation(this.WaistR2, 0.0f, 0.0f, 0.1396263f);
        this.WaistR3 = new ModelRenderer((ModelBase)this, 114, 5);
        this.WaistR3.field_78809_i = true;
        this.WaistR3.func_78789_a(-2.0f, 2.5f, -2.5f, 2, 3, 5);
        this.WaistR3.func_78793_a(-2.0f, 12.0f, 0.0f);
        this.WaistR3.func_78787_b(128, 64);
        this.setRotation(this.WaistR3, 0.0f, 0.0f, 0.1396263f);
        (this.LegR = new ModelRenderer((ModelBase)this, 79, 19)).func_78789_a(-2.5f, 2.5f, -2.0f, 4, 9, 4);
        this.LegR.func_78793_a(-2.0f, 12.5f, 0.0f);
        this.LegR.func_78787_b(128, 64);
        this.setRotation(this.LegR, 0.0f, 0.0f, 0.0f);
        (this.WaistL1 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-2.0f, -0.5f, -2.5f, 5, 3, 5);
        this.WaistL1.func_78793_a(2.0f, 12.0f, 0.0f);
        this.WaistL1.func_78787_b(128, 64);
        this.WaistL1.field_78809_i = true;
        this.setRotation(this.WaistL1, 0.0f, 0.0f, -0.1396263f);
        (this.WaistL2 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(2.0f, 2.5f, -2.5f, 1, 4, 5);
        this.WaistL2.func_78793_a(2.0f, 12.0f, 0.0f);
        this.WaistL2.func_78787_b(128, 64);
        this.WaistL2.field_78809_i = true;
        this.setRotation(this.WaistL2, 0.0f, 0.0f, -0.1396263f);
        (this.WaistL3 = new ModelRenderer((ModelBase)this, 114, 5)).func_78789_a(0.0f, 2.5f, -2.5f, 2, 3, 5);
        this.WaistL3.func_78793_a(2.0f, 12.0f, 0.0f);
        this.WaistL3.func_78787_b(128, 64);
        this.WaistL3.field_78809_i = true;
        this.setRotation(this.WaistL3, 0.0f, 0.0f, -0.1396263f);
        (this.BackpanelL1 = new ModelRenderer((ModelBase)this, 96, 7)).func_78789_a(-2.0f, 2.5f, -2.5f, 2, 2, 5);
        this.BackpanelL1.func_78793_a(2.0f, 12.0f, 0.0f);
        this.BackpanelL1.func_78787_b(128, 64);
        this.BackpanelL1.field_78809_i = true;
        this.setRotation(this.BackpanelL1, 0.0f, 0.0f, -0.1396263f);
        (this.LegL = new ModelRenderer((ModelBase)this, 79, 19)).func_78789_a(-1.5f, 2.5f, -2.0f, 4, 9, 4);
        this.LegL.func_78793_a(2.0f, 12.5f, 0.0f);
        this.LegL.func_78787_b(128, 64);
        this.LegL.field_78809_i = true;
        this.setRotation(this.LegL, 0.0f, 0.0f, 0.0f);
        this.ArmL.func_78792_a(this.ShoulderL);
        this.ArmL.func_78792_a(this.ShoulderL0);
        this.ArmL.func_78792_a(this.ShoulderL1);
        this.ArmL.func_78792_a(this.ShoulderL2);
        this.ArmR.func_78792_a(this.ShoulderR);
        this.ArmR.func_78792_a(this.ShoulderR0);
        this.ArmR.func_78792_a(this.ShoulderR1);
        this.ArmR.func_78792_a(this.ShoulderR2);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        final float a = MathHelper.func_76134_b(f * 0.44f) * 1.4f * f1;
        final float b = MathHelper.func_76134_b(f * 0.44f + 3.1415927f) * 1.4f * f1;
        final float c = Math.min(a, b);
        this.Frontcloth1.field_78795_f = c - 0.1047198f;
        this.Frontcloth2.field_78795_f = c - 0.3316126f;
        this.Cloak1.field_78795_f = -c / 3.0f + 0.1396263f;
        this.Cloak2.field_78795_f = -c / 3.0f + 0.3069452f;
        this.Cloak3.field_78795_f = -c / 3.0f + 0.4465716f;
        this.Frontcloth1.func_78785_a(f5);
        this.CollarL.func_78785_a(f5);
        this.CollarBlack.func_78785_a(f5);
        this.Cloak1.func_78785_a(f5);
        this.CloakCL.func_78785_a(f5);
        this.CloakCR.func_78785_a(f5);
        this.Cloak3.func_78785_a(f5);
        this.Cloak2.func_78785_a(f5);
        if (entity instanceof EntityEldritchGolem && !((EntityEldritchGolem)entity).isHeadless()) {
            this.Head.func_78785_a(f5);
        }
        else {
            this.Head2.func_78785_a(f5);
        }
        this.Frontcloth0.func_78785_a(f5);
        this.CollarB.func_78785_a(f5);
        this.Torso.func_78785_a(f5);
        this.CollarR.func_78785_a(f5);
        this.CollarF.func_78785_a(f5);
        this.Frontcloth1.func_78785_a(f5);
        this.ArmL.func_78785_a(f5);
        this.ArmR.func_78785_a(f5);
        this.BackpanelR1.func_78785_a(f5);
        this.WaistR1.func_78785_a(f5);
        this.WaistR2.func_78785_a(f5);
        this.WaistR3.func_78785_a(f5);
        this.LegR.func_78785_a(f5);
        this.WaistL1.func_78785_a(f5);
        this.WaistL2.func_78785_a(f5);
        this.WaistL3.func_78785_a(f5);
        this.Frontcloth2.func_78785_a(f5);
        this.BackpanelL1.func_78785_a(f5);
        this.LegL.func_78785_a(f5);
    }
    
    public void func_78087_a(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        super.func_78087_a(par1, par2, par3, par4, par5, par6, entity);
        if (entity instanceof EntityEldritchGolem && ((EntityEldritchGolem)entity).getSpawnTimer() > 0) {
            this.Head.field_78796_g = 0.0f;
            this.Head.field_78795_f = ((EntityEldritchGolem)entity).getSpawnTimer() / 2 / 57.295776f;
        }
        else {
            this.Head.field_78796_g = par4 / 4.0f / 57.295776f;
            this.Head.field_78795_f = par5 / 2.0f / 57.295776f;
            this.Head2.field_78796_g = par4 / 57.295776f;
            this.Head2.field_78795_f = par5 / 57.295776f;
        }
        this.LegR.field_78795_f = MathHelper.func_76134_b(par1 * 0.4662f) * 1.4f * par2;
        this.LegL.field_78795_f = MathHelper.func_76134_b(par1 * 0.4662f + 3.1415927f) * 1.4f * par2;
    }
    
    public void func_78086_a(final EntityLivingBase p_78086_1_, final float par1, final float par2, final float par3) {
        final EntityEldritchGolem golem = (EntityEldritchGolem)p_78086_1_;
        final int i = golem.getAttackTimer();
        if (i > 0) {
            this.ArmR.field_78795_f = -2.0f + 1.5f * this.doAbs(i - par3, 10.0f);
            this.ArmL.field_78795_f = -2.0f + 1.5f * this.doAbs(i - par3, 10.0f);
        }
        else {
            this.ArmR.field_78795_f = MathHelper.func_76134_b(par1 * 0.4f + 3.1415927f) * 2.0f * par2 * 0.5f;
            this.ArmL.field_78795_f = MathHelper.func_76134_b(par1 * 0.4f) * 2.0f * par2 * 0.5f;
        }
    }
    
    private float doAbs(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
