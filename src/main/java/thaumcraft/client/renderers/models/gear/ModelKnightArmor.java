package thaumcraft.client.renderers.models.gear;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;

public class ModelKnightArmor extends ModelCustomArmor
{
    ModelRenderer Frontcloth1;
    ModelRenderer Helmet;
    ModelRenderer BeltR;
    ModelRenderer Mbelt;
    ModelRenderer MbeltL;
    ModelRenderer MbeltR;
    ModelRenderer BeltL;
    ModelRenderer Chestplate;
    ModelRenderer CloakAtL;
    ModelRenderer Backplate;
    ModelRenderer Cloak3;
    ModelRenderer CloakAtR;
    ModelRenderer Tabbard;
    ModelRenderer Cloak1;
    ModelRenderer Cloak2;
    ModelRenderer ShoulderR1;
    ModelRenderer GauntletR;
    ModelRenderer GauntletstrapR1;
    ModelRenderer GauntletstrapR2;
    ModelRenderer ShoulderR;
    ModelRenderer ShoulderR0;
    ModelRenderer ShoulderR2;
    ModelRenderer ShoulderL1;
    ModelRenderer GauntletL;
    ModelRenderer GauntletstrapL1;
    ModelRenderer GauntletstrapL2;
    ModelRenderer ShoulderL;
    ModelRenderer ShoulderL0;
    ModelRenderer ShoulderL2;
    ModelRenderer SidepanelR3;
    ModelRenderer SidepanelR2;
    ModelRenderer SidepanelL2;
    ModelRenderer SidepanelR0;
    ModelRenderer SidepanelL0;
    ModelRenderer SidepanelR1;
    ModelRenderer SidepanelL3;
    ModelRenderer Frontcloth2;
    ModelRenderer SidepanelL1;
    
    public ModelKnightArmor(final float f) {
        super(f, 0, 128, 64);
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Helmet = new ModelRenderer((ModelBase)this, 41, 8)).func_78789_a(-4.5f, -9.0f, -4.5f, 9, 9, 9);
        this.Helmet.func_78787_b(128, 64);
        this.setRotation(this.Helmet, 0.0f, 0.0f, 0.0f);
        (this.BeltR = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(-5.0f, 4.0f, -3.0f, 1, 3, 6);
        this.BeltR.func_78787_b(128, 64);
        this.setRotation(this.BeltR, 0.0f, 0.0f, 0.0f);
        (this.Mbelt = new ModelRenderer((ModelBase)this, 56, 55)).func_78789_a(-4.0f, 8.0f, -3.0f, 8, 4, 1);
        this.Mbelt.func_78787_b(128, 64);
        this.setRotation(this.Mbelt, 0.0f, 0.0f, 0.0f);
        (this.MbeltL = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(4.0f, 8.0f, -3.0f, 1, 3, 6);
        this.MbeltL.func_78787_b(128, 64);
        this.setRotation(this.MbeltL, 0.0f, 0.0f, 0.0f);
        (this.MbeltR = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(-5.0f, 8.0f, -3.0f, 1, 3, 6);
        this.MbeltR.func_78787_b(128, 64);
        this.setRotation(this.MbeltR, 0.0f, 0.0f, 0.0f);
        (this.BeltL = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(4.0f, 4.0f, -3.0f, 1, 3, 6);
        this.BeltL.func_78787_b(128, 64);
        this.setRotation(this.BeltL, 0.0f, 0.0f, 0.0f);
        (this.Tabbard = new ModelRenderer((ModelBase)this, 114, 52)).func_78789_a(-3.0f, 1.2f, -3.5f, 6, 10, 1);
        this.Tabbard.func_78787_b(128, 64);
        this.setRotation(this.Tabbard, 0.0f, 0.0f, 0.0f);
        (this.CloakAtL = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(2.5f, 1.0f, 2.0f, 2, 1, 3);
        this.CloakAtL.func_78787_b(128, 64);
        this.setRotation(this.CloakAtL, 0.1396263f, 0.0f, 0.0f);
        (this.Backplate = new ModelRenderer((ModelBase)this, 36, 45)).func_78789_a(-4.0f, 1.0f, 2.0f, 8, 11, 2);
        this.Backplate.func_78787_b(128, 64);
        this.setRotation(this.Backplate, 0.0f, 0.0f, 0.0f);
        (this.Cloak1 = new ModelRenderer((ModelBase)this, 0, 47)).func_78789_a(0.0f, 0.0f, 0.0f, 9, 12, 1);
        this.Cloak1.func_78793_a(-4.5f, 1.3f, 4.2f);
        this.Cloak1.func_78787_b(128, 64);
        this.setRotation(this.Cloak1, 0.1396263f, 0.0f, 0.0f);
        (this.Cloak2 = new ModelRenderer((ModelBase)this, 0, 59)).func_78789_a(0.0f, 11.7f, -2.0f, 9, 4, 1);
        this.Cloak2.func_78793_a(-4.5f, 1.3f, 4.2f);
        this.Cloak2.func_78787_b(128, 64);
        this.setRotation(this.Cloak2, 0.3069452f, 0.0f, 0.0f);
        (this.Cloak3 = new ModelRenderer((ModelBase)this, 0, 59)).func_78789_a(0.0f, 15.2f, -4.2f, 9, 4, 1);
        this.Cloak3.func_78793_a(-4.5f, 1.3f, 4.2f);
        this.Cloak3.func_78787_b(128, 64);
        this.setRotation(this.Cloak3, 0.4465716f, 0.0f, 0.0f);
        (this.CloakAtR = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(-4.5f, 1.0f, 2.0f, 2, 1, 3);
        this.CloakAtR.func_78787_b(128, 64);
        this.setRotation(this.CloakAtR, 0.1396263f, 0.0f, 0.0f);
        (this.Chestplate = new ModelRenderer((ModelBase)this, 56, 45)).func_78789_a(-4.0f, 1.0f, -3.0f, 8, 7, 1);
        this.Chestplate.func_78787_b(128, 64);
        this.setRotation(this.Chestplate, 0.0f, 0.0f, 0.0f);
        (this.ShoulderR1 = new ModelRenderer((ModelBase)this, 0, 19)).func_78789_a(-3.3f, 3.5f, -2.5f, 1, 1, 5);
        this.ShoulderR1.func_78787_b(128, 64);
        this.setRotation(this.ShoulderR1, 0.0f, 0.0f, 0.7853982f);
        (this.GauntletR = new ModelRenderer((ModelBase)this, 100, 26)).func_78789_a(-3.5f, 3.5f, -2.5f, 2, 6, 5);
        this.GauntletR.func_78787_b(128, 64);
        this.GauntletR.field_78809_i = true;
        this.setRotation(this.GauntletR, 0.0f, 0.0f, 0.0f);
        (this.GauntletstrapR1 = new ModelRenderer((ModelBase)this, 84, 31)).func_78789_a(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        this.GauntletstrapR1.func_78787_b(128, 64);
        this.GauntletstrapR1.field_78809_i = true;
        this.setRotation(this.GauntletstrapR1, 0.0f, 0.0f, 0.0f);
        (this.GauntletstrapR2 = new ModelRenderer((ModelBase)this, 84, 31)).func_78789_a(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        this.GauntletstrapR2.func_78787_b(128, 64);
        this.GauntletstrapR2.field_78809_i = true;
        this.setRotation(this.GauntletstrapR2, 0.0f, 0.0f, 0.0f);
        (this.ShoulderR = new ModelRenderer((ModelBase)this, 56, 35)).func_78789_a(-3.5f, -2.5f, -2.5f, 5, 5, 5);
        this.ShoulderR.func_78787_b(128, 64);
        this.ShoulderR.field_78809_i = true;
        this.setRotation(this.ShoulderR, 0.0f, 0.0f, 0.0f);
        (this.ShoulderR0 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-4.3f, -1.5f, -3.0f, 3, 5, 6);
        this.ShoulderR0.func_78787_b(128, 64);
        this.ShoulderR0.field_78809_i = true;
        this.setRotation(this.ShoulderR0, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderR2 = new ModelRenderer((ModelBase)this, 0, 11)).func_78789_a(-2.3f, 3.5f, -3.0f, 1, 2, 6);
        this.ShoulderR2.func_78787_b(128, 64);
        this.ShoulderR2.field_78809_i = true;
        this.setRotation(this.ShoulderR2, 0.0f, 0.0f, 0.7853982f);
        this.ShoulderL1 = new ModelRenderer((ModelBase)this, 0, 19);
        this.ShoulderL1.field_78809_i = true;
        this.ShoulderL1.func_78789_a(2.3f, 3.5f, -2.5f, 1, 1, 5);
        this.ShoulderL1.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL1, 0.0f, 0.0f, -0.7853982f);
        (this.GauntletL = new ModelRenderer((ModelBase)this, 114, 26)).func_78789_a(1.5f, 3.5f, -2.5f, 2, 6, 5);
        this.GauntletL.func_78787_b(128, 64);
        this.setRotation(this.GauntletL, 0.0f, 0.0f, 0.0f);
        this.GauntletstrapL1 = new ModelRenderer((ModelBase)this, 84, 31);
        this.GauntletstrapL1.field_78809_i = true;
        this.GauntletstrapL1.func_78789_a(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        this.GauntletstrapL1.func_78787_b(128, 64);
        this.setRotation(this.GauntletstrapL1, 0.0f, 0.0f, 0.0f);
        this.GauntletstrapL2 = new ModelRenderer((ModelBase)this, 84, 31);
        this.GauntletstrapL2.field_78809_i = true;
        this.GauntletstrapL2.func_78789_a(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        this.GauntletstrapL2.func_78787_b(128, 64);
        this.setRotation(this.GauntletstrapL2, 0.0f, 0.0f, 0.0f);
        (this.ShoulderL = new ModelRenderer((ModelBase)this, 56, 35)).func_78789_a(-1.5f, -2.5f, -2.5f, 5, 5, 5);
        this.ShoulderL.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL, 0.0f, 0.0f, 0.0f);
        (this.ShoulderL0 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(1.3f, -1.5f, -3.0f, 3, 5, 6);
        this.ShoulderL0.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL0, 0.0f, 0.0f, -0.7853982f);
        (this.ShoulderL2 = new ModelRenderer((ModelBase)this, 0, 11)).func_78789_a(1.3f, 3.5f, -3.0f, 1, 2, 6);
        this.ShoulderL2.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL2, 0.0f, 0.0f, -0.7853982f);
        (this.SidepanelR3 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(-3.0f, 2.5f, -2.5f, 1, 4, 5);
        this.SidepanelR3.func_78787_b(128, 64);
        this.setRotation(this.SidepanelR3, 0.0f, 0.0f, 0.1396263f);
        this.SidepanelR2 = new ModelRenderer((ModelBase)this, 114, 5);
        this.SidepanelR2.field_78809_i = true;
        this.SidepanelR2.func_78789_a(-2.0f, 2.5f, -2.5f, 2, 3, 5);
        this.SidepanelR2.func_78787_b(128, 64);
        this.setRotation(this.SidepanelR2, 0.0f, 0.0f, 0.1396263f);
        (this.SidepanelL2 = new ModelRenderer((ModelBase)this, 114, 5)).func_78789_a(0.0f, 2.5f, -2.5f, 2, 3, 5);
        this.SidepanelL2.func_78787_b(128, 64);
        this.setRotation(this.SidepanelL2, 0.0f, 0.0f, -0.1396263f);
        (this.SidepanelR0 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-3.0f, -0.5f, -2.5f, 5, 3, 5);
        this.SidepanelR0.func_78787_b(128, 64);
        this.setRotation(this.SidepanelR0, 0.0f, 0.0f, 0.1396263f);
        (this.SidepanelL0 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-2.0f, -0.5f, -2.5f, 5, 3, 5);
        this.SidepanelL0.func_78787_b(128, 64);
        this.setRotation(this.SidepanelL0, 0.0f, 0.0f, -0.1396263f);
        this.SidepanelR1 = new ModelRenderer((ModelBase)this, 96, 7);
        this.SidepanelR1.field_78809_i = true;
        this.SidepanelR1.func_78789_a(0.0f, 2.5f, -2.5f, 2, 2, 5);
        this.SidepanelR1.func_78787_b(128, 64);
        this.setRotation(this.SidepanelR1, 0.0f, 0.0f, 0.1396263f);
        (this.SidepanelL3 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(2.0f, 2.5f, -2.5f, 1, 4, 5);
        this.SidepanelL3.func_78787_b(128, 64);
        this.setRotation(this.SidepanelL3, 0.0f, 0.0f, -0.1396263f);
        (this.SidepanelL1 = new ModelRenderer((ModelBase)this, 96, 7)).func_78789_a(-2.0f, 2.5f, -2.5f, 2, 2, 5);
        this.SidepanelL1.func_78787_b(128, 64);
        this.setRotation(this.SidepanelL1, 0.0f, 0.0f, -0.1396263f);
        (this.Frontcloth1 = new ModelRenderer((ModelBase)this, 120, 39)).func_78789_a(0.0f, 0.0f, 0.0f, 6, 8, 1);
        this.Frontcloth1.func_78793_a(-3.0f, 11.0f, -3.5f);
        this.Frontcloth1.func_78787_b(128, 64);
        this.setRotation(this.Frontcloth1, -0.1047198f, 0.0f, 0.0f);
        (this.Frontcloth2 = new ModelRenderer((ModelBase)this, 100, 37)).func_78789_a(0.0f, 7.5f, 1.8f, 6, 3, 1);
        this.Frontcloth2.func_78793_a(-3.0f, 11.0f, -3.5f);
        this.Frontcloth2.func_78787_b(128, 64);
        this.setRotation(this.Frontcloth2, -0.3316126f, 0.0f, 0.0f);
        this.field_178720_f.field_78804_l.clear();
        this.field_78116_c.field_78804_l.clear();
        this.field_78116_c.func_78792_a(this.Helmet);
        this.field_78115_e.field_78804_l.clear();
        this.field_178721_j.field_78804_l.clear();
        this.field_178722_k.field_78804_l.clear();
        this.field_78115_e.func_78792_a(this.Mbelt);
        this.field_78115_e.func_78792_a(this.MbeltL);
        this.field_78115_e.func_78792_a(this.MbeltR);
        if (f >= 1.0f) {
            this.field_78115_e.func_78792_a(this.Chestplate);
            this.field_78115_e.func_78792_a(this.Frontcloth1);
            this.field_78115_e.func_78792_a(this.Frontcloth2);
            this.field_78115_e.func_78792_a(this.Tabbard);
            this.field_78115_e.func_78792_a(this.Backplate);
            this.field_78115_e.func_78792_a(this.Cloak1);
            this.field_78115_e.func_78792_a(this.Cloak2);
            this.field_78115_e.func_78792_a(this.Cloak3);
            this.field_78115_e.func_78792_a(this.CloakAtL);
            this.field_78115_e.func_78792_a(this.CloakAtR);
        }
        this.field_178723_h.field_78804_l.clear();
        this.field_178723_h.func_78792_a(this.ShoulderR);
        this.field_178723_h.func_78792_a(this.ShoulderR0);
        this.field_178723_h.func_78792_a(this.ShoulderR1);
        this.field_178723_h.func_78792_a(this.ShoulderR2);
        this.field_178723_h.func_78792_a(this.GauntletR);
        this.field_178723_h.func_78792_a(this.GauntletstrapR1);
        this.field_178723_h.func_78792_a(this.GauntletstrapR2);
        this.field_178724_i.field_78804_l.clear();
        this.field_178724_i.func_78792_a(this.ShoulderL);
        this.field_178724_i.func_78792_a(this.ShoulderL0);
        this.field_178724_i.func_78792_a(this.ShoulderL1);
        this.field_178724_i.func_78792_a(this.ShoulderL2);
        this.field_178724_i.func_78792_a(this.GauntletL);
        this.field_178724_i.func_78792_a(this.GauntletstrapL1);
        this.field_178724_i.func_78792_a(this.GauntletstrapL2);
        this.field_178721_j.func_78792_a(this.SidepanelR0);
        this.field_178721_j.func_78792_a(this.SidepanelR1);
        this.field_178721_j.func_78792_a(this.SidepanelR2);
        this.field_178721_j.func_78792_a(this.SidepanelR3);
        this.field_178722_k.func_78792_a(this.SidepanelL0);
        this.field_178722_k.func_78792_a(this.SidepanelL1);
        this.field_178722_k.func_78792_a(this.SidepanelL2);
        this.field_178722_k.func_78792_a(this.SidepanelL3);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        final float a = MathHelper.func_76134_b(f * 0.6662f) * 1.4f * f1;
        final float b = MathHelper.func_76134_b(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        final float c = Math.min(a, b);
        this.Frontcloth1.field_78795_f = c - 0.1047198f;
        this.Frontcloth2.field_78795_f = c - 0.3316126f;
        this.Cloak1.field_78795_f = -c / 2.0f + 0.1396263f;
        this.Cloak2.field_78795_f = -c / 2.0f + 0.3069452f;
        this.Cloak3.field_78795_f = -c / 2.0f + 0.4465716f;
        if (this.field_78091_s) {
            final float f6 = 2.0f;
            GL11.glPushMatrix();
            GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
            GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
            this.field_78116_c.func_78785_a(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
            GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
            this.field_78115_e.func_78785_a(f5);
            this.field_178723_h.func_78785_a(f5);
            this.field_178724_i.func_78785_a(f5);
            this.field_178721_j.func_78785_a(f5);
            this.field_178722_k.func_78785_a(f5);
            this.field_178720_f.func_78785_a(f5);
            GL11.glPopMatrix();
        }
        else {
            GL11.glPushMatrix();
            GL11.glScalef(1.01f, 1.01f, 1.01f);
            this.field_78116_c.func_78785_a(f5);
            GL11.glPopMatrix();
            this.field_78115_e.func_78785_a(f5);
            this.field_178723_h.func_78785_a(f5);
            this.field_178724_i.func_78785_a(f5);
            this.field_178721_j.func_78785_a(f5);
            this.field_178722_k.func_78785_a(f5);
            this.field_178720_f.func_78785_a(f5);
        }
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.field_78795_f = x;
        model.field_78796_g = y;
        model.field_78808_h = z;
    }
}
