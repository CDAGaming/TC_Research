package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.util.math.*;

public class ModelEldritchCrab extends ModelBase
{
    ModelRenderer TailHelm;
    ModelRenderer TailBare;
    ModelRenderer RFLeg1;
    ModelRenderer RClaw1;
    ModelRenderer Head1;
    ModelRenderer RClaw0;
    ModelRenderer RClaw2;
    ModelRenderer LClaw2;
    ModelRenderer LClaw1;
    ModelRenderer RArm;
    ModelRenderer Torso;
    ModelRenderer RRLeg1;
    ModelRenderer Head0;
    ModelRenderer LRLeg1;
    ModelRenderer LFLeg1;
    ModelRenderer RRLeg0;
    ModelRenderer RFLeg0;
    ModelRenderer LFLeg0;
    ModelRenderer LRLeg0;
    ModelRenderer LClaw0;
    ModelRenderer LArm;
    
    public ModelEldritchCrab() {
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.TailHelm = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-4.5f, -4.5f, -0.4f, 9, 9, 9);
        this.TailHelm.func_78793_a(0.0f, 18.0f, 0.0f);
        this.setRotation(this.TailHelm, 0.1047198f, 0.0f, 0.0f);
        (this.TailBare = new ModelRenderer((ModelBase)this, 64, 0)).func_78789_a(-4.0f, -4.0f, -0.4f, 8, 8, 8);
        this.TailBare.func_78793_a(0.0f, 18.0f, 0.0f);
        this.setRotation(this.TailBare, 0.1047198f, 0.0f, 0.0f);
        (this.RClaw1 = new ModelRenderer((ModelBase)this, 0, 47)).func_78789_a(-2.0f, -1.0f, -5.066667f, 4, 3, 5);
        this.RClaw1.func_78793_a(-6.0f, 15.5f, -10.0f);
        (this.Head1 = new ModelRenderer((ModelBase)this, 0, 38)).func_78789_a(-2.0f, -1.5f, -9.066667f, 4, 4, 1);
        this.Head1.func_78793_a(0.0f, 18.0f, 0.0f);
        (this.RClaw0 = new ModelRenderer((ModelBase)this, 0, 55)).func_78789_a(-2.0f, -2.5f, -3.066667f, 4, 5, 3);
        this.RClaw0.func_78793_a(-6.0f, 17.0f, -7.0f);
        (this.RClaw2 = new ModelRenderer((ModelBase)this, 14, 54)).func_78789_a(-1.5f, -1.0f, -4.066667f, 3, 2, 5);
        this.RClaw2.func_78793_a(-6.0f, 18.5f, -10.0f);
        this.setRotation(this.RClaw2, 0.3141593f, 0.0f, 0.0f);
        (this.RArm = new ModelRenderer((ModelBase)this, 44, 4)).func_78789_a(-1.0f, -1.0f, -5.066667f, 2, 2, 6);
        this.RArm.func_78793_a(-3.0f, 17.0f, -4.0f);
        this.setRotation(this.RArm, 0.0f, 0.7504916f, 0.0f);
        (this.LClaw2 = new ModelRenderer((ModelBase)this, 14, 54)).func_78789_a(-1.5f, -1.0f, -4.066667f, 3, 2, 5);
        this.LClaw2.func_78793_a(6.0f, 18.5f, -10.0f);
        this.setRotation(this.LClaw2, 0.3141593f, 0.0f, 0.0f);
        this.LClaw1 = new ModelRenderer((ModelBase)this, 0, 47);
        this.LClaw1.field_78809_i = true;
        this.LClaw1.func_78789_a(-2.0f, -1.0f, -5.066667f, 4, 3, 5);
        this.LClaw1.func_78793_a(6.0f, 15.5f, -10.0f);
        this.LClaw0 = new ModelRenderer((ModelBase)this, 0, 55);
        this.LClaw0.field_78809_i = true;
        this.LClaw0.func_78789_a(-2.0f, -2.5f, -3.066667f, 4, 5, 3);
        this.LClaw0.func_78793_a(6.0f, 17.0f, -7.0f);
        (this.LArm = new ModelRenderer((ModelBase)this, 44, 4)).func_78789_a(-1.0f, -1.0f, -4.066667f, 2, 2, 6);
        this.LArm.func_78793_a(4.0f, 17.0f, -5.0f);
        this.setRotation(this.LArm, 0.0f, -0.7504916f, 0.0f);
        (this.Torso = new ModelRenderer((ModelBase)this, 0, 18)).func_78789_a(-3.5f, -3.5f, -6.066667f, 7, 7, 6);
        this.Torso.func_78793_a(0.0f, 18.0f, 0.0f);
        this.setRotation(this.Torso, 0.0523599f, 0.0f, 0.0f);
        (this.Head0 = new ModelRenderer((ModelBase)this, 0, 31)).func_78789_a(-2.5f, -2.0f, -8.066667f, 5, 5, 2);
        this.Head0.func_78793_a(0.0f, 18.0f, 0.0f);
        (this.RRLeg1 = new ModelRenderer((ModelBase)this, 36, 4)).func_78789_a(-4.5f, 1.0f, -0.9f, 2, 5, 2);
        this.RRLeg1.func_78793_a(-4.0f, 20.0f, -1.5f);
        (this.RFLeg1 = new ModelRenderer((ModelBase)this, 36, 4)).func_78789_a(-5.0f, 1.0f, -1.066667f, 2, 5, 2);
        this.RFLeg1.func_78793_a(-4.0f, 20.0f, -3.5f);
        (this.LRLeg1 = new ModelRenderer((ModelBase)this, 36, 4)).func_78789_a(2.5f, 1.0f, -0.9f, 2, 5, 2);
        this.LRLeg1.func_78793_a(4.0f, 20.0f, -1.5f);
        (this.LFLeg1 = new ModelRenderer((ModelBase)this, 36, 4)).func_78789_a(3.0f, 1.0f, -1.066667f, 2, 5, 2);
        this.LFLeg1.func_78793_a(4.0f, 20.0f, -3.5f);
        (this.RRLeg0 = new ModelRenderer((ModelBase)this, 36, 0)).func_78789_a(-4.5f, -1.0f, -0.9f, 6, 2, 2);
        this.RRLeg0.func_78793_a(-4.0f, 20.0f, -1.5f);
        (this.RFLeg0 = new ModelRenderer((ModelBase)this, 36, 0)).func_78789_a(-5.0f, -1.0f, -1.066667f, 6, 2, 2);
        this.RFLeg0.func_78793_a(-4.0f, 20.0f, -3.5f);
        (this.LFLeg0 = new ModelRenderer((ModelBase)this, 36, 0)).func_78789_a(-1.0f, -1.0f, -1.066667f, 6, 2, 2);
        this.LFLeg0.func_78793_a(4.0f, 20.0f, -3.5f);
        (this.LRLeg0 = new ModelRenderer((ModelBase)this, 36, 0)).func_78789_a(-1.5f, -1.0f, -0.9f, 6, 2, 2);
        this.LRLeg0.func_78793_a(4.0f, 20.0f, -1.5f);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        if (entity instanceof EntityEldritchCrab && ((EntityEldritchCrab)entity).hasHelm()) {
            this.TailHelm.func_78785_a(f5);
        }
        else {
            this.TailBare.func_78785_a(f5);
        }
        this.RFLeg1.func_78785_a(f5);
        this.RClaw1.func_78785_a(f5);
        this.Head1.func_78785_a(f5);
        this.RClaw0.func_78785_a(f5);
        this.RClaw2.func_78785_a(f5);
        this.LClaw2.func_78785_a(f5);
        this.LClaw1.func_78785_a(f5);
        this.RArm.func_78785_a(f5);
        this.Torso.func_78785_a(f5);
        this.RRLeg1.func_78785_a(f5);
        this.Head0.func_78785_a(f5);
        this.LRLeg1.func_78785_a(f5);
        this.LFLeg1.func_78785_a(f5);
        this.RRLeg0.func_78785_a(f5);
        this.RFLeg0.func_78785_a(f5);
        this.LFLeg0.func_78785_a(f5);
        this.LRLeg0.func_78785_a(f5);
        this.LClaw0.func_78785_a(f5);
        this.LArm.func_78785_a(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
    
    public void func_78087_a(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        this.setRotation(this.RRLeg1, 0.0f, 0.2094395f, 0.4363323f);
        this.setRotation(this.RFLeg1, 0.0f, -0.2094395f, 0.4363323f);
        this.setRotation(this.LRLeg1, 0.0f, -0.2094395f, -0.4363323f);
        this.setRotation(this.LFLeg1, 0.0f, 0.2094395f, -0.4363323f);
        this.setRotation(this.RRLeg0, 0.0f, 0.2094395f, 0.4363323f);
        this.setRotation(this.RFLeg0, 0.0f, -0.2094395f, 0.4363323f);
        this.setRotation(this.LFLeg0, 0.0f, 0.2094395f, -0.4363323f);
        this.setRotation(this.LRLeg0, 0.0f, -0.2094395f, -0.4363323f);
        final float f9 = -(MathHelper.func_76134_b(par1 * 0.6662f * 2.0f + 0.0f) * 0.4f) * par2;
        final float f10 = -(MathHelper.func_76134_b(par1 * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * par2;
        final ModelRenderer rrLeg1 = this.RRLeg1;
        rrLeg1.field_78796_g += f9;
        final ModelRenderer rrLeg2 = this.RRLeg0;
        rrLeg2.field_78796_g += f9;
        final ModelRenderer lrLeg1 = this.LRLeg1;
        lrLeg1.field_78796_g += -f9;
        final ModelRenderer lrLeg2 = this.LRLeg0;
        lrLeg2.field_78796_g += -f9;
        final ModelRenderer rfLeg1 = this.RFLeg1;
        rfLeg1.field_78796_g += f10;
        final ModelRenderer rfLeg2 = this.RFLeg0;
        rfLeg2.field_78796_g += f10;
        final ModelRenderer lfLeg1 = this.LFLeg1;
        lfLeg1.field_78796_g += -f10;
        final ModelRenderer lfLeg2 = this.LFLeg0;
        lfLeg2.field_78796_g += -f10;
        final ModelRenderer rrLeg3 = this.RRLeg1;
        rrLeg3.field_78808_h += f9;
        final ModelRenderer rrLeg4 = this.RRLeg0;
        rrLeg4.field_78808_h += f9;
        final ModelRenderer lrLeg3 = this.LRLeg1;
        lrLeg3.field_78808_h += -f9;
        final ModelRenderer lrLeg4 = this.LRLeg0;
        lrLeg4.field_78808_h += -f9;
        final ModelRenderer rfLeg3 = this.RFLeg1;
        rfLeg3.field_78808_h += f10;
        final ModelRenderer rfLeg4 = this.RFLeg0;
        rfLeg4.field_78808_h += f10;
        final ModelRenderer lfLeg3 = this.LFLeg1;
        lfLeg3.field_78808_h += -f10;
        final ModelRenderer lfLeg4 = this.LFLeg0;
        lfLeg4.field_78808_h += -f10;
        final ModelRenderer tailBare = this.TailBare;
        final ModelRenderer tailHelm = this.TailHelm;
        final float n = MathHelper.func_76134_b(par1 * 0.6662f) * 2.0f * par2 * 0.125f;
        tailHelm.field_78796_g = n;
        tailBare.field_78796_g = n;
        final ModelRenderer tailBare2 = this.TailBare;
        final ModelRenderer tailHelm2 = this.TailHelm;
        final float n2 = MathHelper.func_76134_b(par1 * 0.6662f) * par2 * 0.125f;
        tailHelm2.field_78808_h = n2;
        tailBare2.field_78808_h = n2;
        this.RClaw2.field_78795_f = 0.3141593f - MathHelper.func_76126_a(entity.field_70173_aa / 4.0f) * 0.25f;
        this.LClaw2.field_78795_f = 0.3141593f + MathHelper.func_76126_a(entity.field_70173_aa / 4.1f) * 0.25f;
        this.RClaw1.field_78795_f = MathHelper.func_76126_a(entity.field_70173_aa / 4.0f) * 0.125f;
        this.LClaw1.field_78795_f = -MathHelper.func_76126_a(entity.field_70173_aa / 4.1f) * 0.125f;
    }
}
