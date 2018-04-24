package thaumcraft.client.renderers.models.gear;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;

public class ModelLeaderArmor extends ModelCustomArmor
{
    ModelRenderer Helmet;
    ModelRenderer CollarF;
    ModelRenderer CollarB;
    ModelRenderer CollarR;
    ModelRenderer CollarL;
    ModelRenderer BeltR;
    ModelRenderer Mbelt;
    ModelRenderer MbeltL;
    ModelRenderer MbeltR;
    ModelRenderer BeltL;
    ModelRenderer CloakTL;
    ModelRenderer Cloak3;
    ModelRenderer CloakTR;
    ModelRenderer Cloak1;
    ModelRenderer Cloak2;
    ModelRenderer Chestplate;
    ModelRenderer ChestOrnament;
    ModelRenderer ChestClothL;
    ModelRenderer ChestClothR;
    ModelRenderer Backplate;
    ModelRenderer GauntletstrapR1;
    ModelRenderer GauntletstrapR2;
    ModelRenderer ShoulderR;
    ModelRenderer ShoulderR1;
    ModelRenderer ShoulderR2;
    ModelRenderer ShoulderR5;
    ModelRenderer ShoulderR3;
    ModelRenderer ShoulderR4;
    ModelRenderer GauntletR2;
    ModelRenderer GauntletR;
    ModelRenderer GauntletL2;
    ModelRenderer GauntletstrapL1;
    ModelRenderer GauntletstrapL2;
    ModelRenderer ShoulderL;
    ModelRenderer ShoulderL1;
    ModelRenderer ShoulderL2;
    ModelRenderer ShoulderL3;
    ModelRenderer ShoulderL5;
    ModelRenderer ShoulderL4;
    ModelRenderer GauntletL;
    ModelRenderer LegClothR;
    ModelRenderer BackpanelR2;
    ModelRenderer BackpanelR3;
    ModelRenderer BackpanelR4;
    ModelRenderer LegClothL;
    ModelRenderer BackpanelL4;
    ModelRenderer BackpanelL2;
    ModelRenderer BackpanelL3;
    ModelRenderer BackpanelL1;
    ModelRenderer BackpanelR1;
    
    public ModelLeaderArmor(final float f) {
        super(f, 0, 128, 64);
        this.field_78090_t = 128;
        this.field_78089_u = 64;
        (this.Helmet = new ModelRenderer((ModelBase)this, 41, 8)).func_78789_a(-4.5f, -9.0f, -4.5f, 9, 9, 9);
        this.Helmet.func_78787_b(128, 64);
        this.setRotation(this.Helmet, 0.0f, 0.0f, 0.0f);
        (this.CollarF = new ModelRenderer((ModelBase)this, 17, 31)).func_78789_a(-4.5f, -1.5f, -3.0f, 9, 4, 1);
        this.CollarF.func_78793_a(0.0f, 0.0f, -2.5f);
        this.setRotation(this.CollarF, 0.2268928f, 0.0f, 0.0f);
        (this.CollarB = new ModelRenderer((ModelBase)this, 17, 26)).func_78789_a(-4.5f, -1.5f, 7.0f, 9, 4, 1);
        this.CollarB.func_78793_a(0.0f, 0.0f, -2.5f);
        this.setRotation(this.CollarB, 0.2268928f, 0.0f, 0.0f);
        (this.CollarR = new ModelRenderer((ModelBase)this, 17, 11)).func_78789_a(-5.5f, -1.5f, -3.0f, 1, 4, 11);
        this.CollarR.func_78793_a(0.0f, 0.0f, -2.5f);
        this.setRotation(this.CollarR, 0.2268928f, 0.0f, 0.0f);
        (this.CollarL = new ModelRenderer((ModelBase)this, 17, 11)).func_78789_a(4.5f, -1.5f, -3.0f, 1, 4, 11);
        this.CollarL.func_78793_a(0.0f, 0.0f, -2.5f);
        this.setRotation(this.CollarL, 0.2268928f, 0.0f, 0.0f);
        (this.BeltR = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(-5.0f, 4.0f, -3.0f, 1, 3, 6);
        (this.Mbelt = new ModelRenderer((ModelBase)this, 56, 55)).func_78789_a(-4.0f, 8.0f, -3.0f, 8, 4, 1);
        (this.MbeltL = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(4.0f, 8.0f, -3.0f, 1, 3, 6);
        (this.MbeltR = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(-5.0f, 8.0f, -3.0f, 1, 3, 6);
        (this.BeltL = new ModelRenderer((ModelBase)this, 76, 44)).func_78789_a(4.0f, 4.0f, -3.0f, 1, 3, 6);
        (this.CloakTL = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(2.5f, 1.0f, -1.0f, 2, 1, 3);
        this.CloakTL.func_78793_a(0.0f, 0.0f, 3.0f);
        this.setRotation(this.CloakTL, 0.1396263f, 0.0f, 0.0f);
        (this.Cloak3 = new ModelRenderer((ModelBase)this, 0, 59)).func_78789_a(-4.5f, 17.0f, -3.7f, 9, 4, 1);
        this.Cloak3.func_78793_a(0.0f, 0.0f, 3.0f);
        this.setRotation(this.Cloak3, 0.4465716f, 0.0f, 0.0f);
        (this.CloakTR = new ModelRenderer((ModelBase)this, 0, 43)).func_78789_a(-4.5f, 1.0f, -1.0f, 2, 1, 3);
        this.CloakTR.func_78793_a(0.0f, 0.0f, 3.0f);
        this.setRotation(this.CloakTR, 0.1396263f, 0.0f, 0.0f);
        (this.Cloak1 = new ModelRenderer((ModelBase)this, 0, 47)).func_78789_a(-4.5f, 2.0f, 1.0f, 9, 12, 1);
        this.Cloak1.func_78793_a(0.0f, 0.0f, 3.0f);
        this.setRotation(this.Cloak1, 0.1396263f, 0.0f, 0.0f);
        (this.Cloak2 = new ModelRenderer((ModelBase)this, 0, 59)).func_78789_a(-4.5f, 14.0f, -1.3f, 9, 4, 1);
        this.Cloak2.func_78793_a(0.0f, 0.0f, 3.0f);
        this.setRotation(this.Cloak2, 0.3069452f, 0.0f, 0.0f);
        (this.Chestplate = new ModelRenderer((ModelBase)this, 56, 45)).func_78789_a(-4.0f, 1.0f, -3.8f, 8, 7, 2);
        (this.ChestOrnament = new ModelRenderer((ModelBase)this, 76, 53)).func_78789_a(-2.5f, 3.0f, -4.8f, 5, 5, 1);
        this.ChestClothL = new ModelRenderer((ModelBase)this, 20, 47);
        this.ChestClothL.field_78809_i = true;
        this.ChestClothL.func_78789_a(1.5f, 1.2f, -4.5f, 3, 9, 1);
        this.setRotation(this.ChestClothL, 0.0663225f, 0.0f, 0.0f);
        (this.ChestClothR = new ModelRenderer((ModelBase)this, 20, 47)).func_78789_a(-4.5f, 1.2f, -4.5f, 3, 9, 1);
        this.setRotation(this.ChestClothR, 0.0663225f, 0.0f, 0.0f);
        (this.Backplate = new ModelRenderer((ModelBase)this, 36, 45)).func_78789_a(-4.0f, 1.0f, 2.0f, 8, 11, 2);
        (this.GauntletR = new ModelRenderer((ModelBase)this, 100, 26)).func_78789_a(-3.5f, 3.5f, -2.5f, 2, 6, 5);
        (this.GauntletL = new ModelRenderer((ModelBase)this, 114, 26)).func_78789_a(1.5f, 3.5f, -2.5f, 2, 6, 5);
        this.GauntletstrapL1 = new ModelRenderer((ModelBase)this, 84, 31);
        this.GauntletstrapL1.field_78809_i = true;
        this.GauntletstrapL1.func_78789_a(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        this.GauntletstrapL2 = new ModelRenderer((ModelBase)this, 84, 31);
        this.GauntletstrapL2.field_78809_i = true;
        this.GauntletstrapL2.func_78789_a(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        (this.GauntletstrapR1 = new ModelRenderer((ModelBase)this, 84, 31)).func_78789_a(-1.5f, 3.5f, -2.5f, 3, 1, 5);
        (this.GauntletstrapR2 = new ModelRenderer((ModelBase)this, 84, 31)).func_78789_a(-1.5f, 6.5f, -2.5f, 3, 1, 5);
        (this.GauntletR2 = new ModelRenderer((ModelBase)this, 102, 37)).func_78789_a(-5.0f, 3.5f, -2.0f, 1, 5, 4);
        this.setRotation(this.GauntletR2, 0.0f, 0.0f, -0.1675516f);
        (this.GauntletL2 = new ModelRenderer((ModelBase)this, 102, 37)).func_78789_a(4.0f, 3.5f, -2.0f, 1, 5, 4);
        this.setRotation(this.GauntletL2, 0.0f, 0.0f, 0.1675516f);
        (this.ShoulderR = new ModelRenderer((ModelBase)this, 56, 35)).func_78789_a(-3.5f, -2.5f, -2.5f, 5, 5, 5);
        (this.ShoulderR1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-4.3f, -1.5f, -3.0f, 3, 5, 6);
        this.setRotation(this.ShoulderR1, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderR2 = new ModelRenderer((ModelBase)this, 0, 19)).func_78789_a(-3.3f, 3.5f, -2.5f, 1, 1, 5);
        this.setRotation(this.ShoulderR2, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderR5 = new ModelRenderer((ModelBase)this, 18, 4)).func_78789_a(-2.3f, -1.5f, 3.0f, 1, 6, 1);
        this.setRotation(this.ShoulderR5, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderR3 = new ModelRenderer((ModelBase)this, 0, 11)).func_78789_a(-2.3f, 3.5f, -3.0f, 1, 2, 6);
        this.setRotation(this.ShoulderR3, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderR4 = new ModelRenderer((ModelBase)this, 18, 4)).func_78789_a(-2.3f, -1.5f, -4.0f, 1, 6, 1);
        this.setRotation(this.ShoulderR4, 0.0f, 0.0f, 0.7853982f);
        (this.ShoulderL = new ModelRenderer((ModelBase)this, 56, 35)).func_78789_a(-1.5f, -2.5f, -2.5f, 5, 5, 5);
        (this.ShoulderL1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(1.3f, -1.5f, -3.0f, 3, 5, 6);
        this.setRotation(this.ShoulderL1, 0.0f, 0.0f, -0.7853982f);
        this.ShoulderL2 = new ModelRenderer((ModelBase)this, 0, 19);
        this.ShoulderL2.field_78809_i = true;
        this.ShoulderL2.func_78789_a(2.3f, 3.5f, -2.5f, 1, 1, 5);
        this.setRotation(this.ShoulderL2, 0.0f, 0.0f, -0.7853982f);
        (this.ShoulderL3 = new ModelRenderer((ModelBase)this, 0, 11)).func_78789_a(1.3f, 3.5f, -3.0f, 1, 2, 6);
        this.setRotation(this.ShoulderL3, 0.0f, 0.0f, -0.7853982f);
        (this.ShoulderL5 = new ModelRenderer((ModelBase)this, 18, 4)).func_78789_a(1.3f, -1.5f, 3.0f, 1, 6, 1);
        this.ShoulderL5.func_78787_b(128, 64);
        this.setRotation(this.ShoulderL5, 0.0f, 0.0f, -0.7853982f);
        (this.ShoulderL4 = new ModelRenderer((ModelBase)this, 18, 4)).func_78789_a(1.3f, -1.5f, -4.0f, 1, 6, 1);
        this.setRotation(this.ShoulderL4, 0.0f, 0.0f, -0.7853982f);
        (this.LegClothR = new ModelRenderer((ModelBase)this, 20, 55)).func_78789_a(0.0f, 0.0f, 0.0f, 3, 8, 1);
        this.LegClothR.func_78793_a(-4.5f, 10.4f, -3.9f);
        this.setRotation(this.LegClothR, -0.0349066f, 0.0f, 0.0f);
        this.LegClothL = new ModelRenderer((ModelBase)this, 20, 55);
        this.LegClothL.field_78809_i = true;
        this.LegClothL.func_78789_a(0.0f, 0.0f, 0.0f, 3, 8, 1);
        this.LegClothL.func_78793_a(1.5f, 10.4f, -3.9f);
        this.setRotation(this.LegClothL, -0.0349066f, 0.0f, 0.0f);
        (this.BackpanelR1 = new ModelRenderer((ModelBase)this, 0, 25)).func_78789_a(-3.0f, -0.5f, 2.5f, 5, 7, 1);
        this.setRotation(this.BackpanelR1, 0.0698132f, 0.0f, 0.0f);
        (this.BackpanelR2 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-3.0f, -0.5f, -2.5f, 5, 3, 5);
        this.setRotation(this.BackpanelR2, 0.0f, 0.0f, 0.1396263f);
        (this.BackpanelR3 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(-3.0f, 2.5f, -2.5f, 1, 4, 5);
        this.setRotation(this.BackpanelR3, 0.0f, 0.0f, 0.1396263f);
        this.BackpanelR4 = new ModelRenderer((ModelBase)this, 0, 25);
        this.BackpanelR4.field_78809_i = true;
        this.BackpanelR4.func_78789_a(-3.0f, -0.5f, -3.5f, 5, 7, 1);
        this.setRotation(this.BackpanelR4, -0.0349066f, 0.0f, 0.0f);
        (this.BackpanelL1 = new ModelRenderer((ModelBase)this, 0, 25)).func_78789_a(-2.0f, -0.5f, 2.5f, 5, 7, 1);
        this.setRotation(this.BackpanelL1, 0.0698132f, 0.0f, 0.0f);
        (this.BackpanelL4 = new ModelRenderer((ModelBase)this, 0, 25)).func_78789_a(-2.0f, -0.5f, -3.5f, 5, 7, 1);
        this.setRotation(this.BackpanelL4, -0.0349066f, 0.0f, 0.0f);
        (this.BackpanelL2 = new ModelRenderer((ModelBase)this, 96, 14)).func_78789_a(-2.0f, -0.5f, -2.5f, 5, 3, 5);
        this.setRotation(this.BackpanelL2, 0.0f, 0.0f, -0.1396263f);
        (this.BackpanelL3 = new ModelRenderer((ModelBase)this, 116, 13)).func_78789_a(2.0f, 2.5f, -2.5f, 1, 4, 5);
        this.setRotation(this.BackpanelL3, 0.0f, 0.0f, -0.1396263f);
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
            this.field_78115_e.func_78792_a(this.BeltL);
            this.field_78115_e.func_78792_a(this.BeltR);
            this.field_78115_e.func_78792_a(this.Chestplate);
            this.field_78115_e.func_78792_a(this.ChestOrnament);
            this.field_78115_e.func_78792_a(this.ChestClothR);
            this.field_78115_e.func_78792_a(this.ChestClothL);
            this.field_78115_e.func_78792_a(this.LegClothR);
            this.field_78115_e.func_78792_a(this.LegClothL);
            this.field_78115_e.func_78792_a(this.Backplate);
            this.field_78115_e.func_78792_a(this.CollarB);
            this.field_78115_e.func_78792_a(this.CollarR);
            this.field_78115_e.func_78792_a(this.CollarL);
            this.field_78115_e.func_78792_a(this.CollarF);
            this.field_78115_e.func_78792_a(this.Cloak1);
            this.field_78115_e.func_78792_a(this.Cloak2);
            this.field_78115_e.func_78792_a(this.Cloak3);
            this.field_78115_e.func_78792_a(this.CloakTL);
            this.field_78115_e.func_78792_a(this.CloakTR);
        }
        this.field_178723_h.field_78804_l.clear();
        this.field_178723_h.func_78792_a(this.ShoulderR);
        this.field_178723_h.func_78792_a(this.ShoulderR1);
        this.field_178723_h.func_78792_a(this.ShoulderR2);
        this.field_178723_h.func_78792_a(this.ShoulderR3);
        this.field_178723_h.func_78792_a(this.ShoulderR4);
        this.field_178723_h.func_78792_a(this.ShoulderR5);
        this.field_178723_h.func_78792_a(this.GauntletR);
        this.field_178723_h.func_78792_a(this.GauntletR2);
        this.field_178723_h.func_78792_a(this.GauntletstrapR1);
        this.field_178723_h.func_78792_a(this.GauntletstrapR2);
        this.field_178724_i.field_78804_l.clear();
        this.field_178724_i.func_78792_a(this.ShoulderL);
        this.field_178724_i.func_78792_a(this.ShoulderL1);
        this.field_178724_i.func_78792_a(this.ShoulderL2);
        this.field_178724_i.func_78792_a(this.ShoulderL3);
        this.field_178724_i.func_78792_a(this.ShoulderL4);
        this.field_178724_i.func_78792_a(this.ShoulderL5);
        this.field_178724_i.func_78792_a(this.GauntletL);
        this.field_178724_i.func_78792_a(this.GauntletL2);
        this.field_178724_i.func_78792_a(this.GauntletstrapL1);
        this.field_178724_i.func_78792_a(this.GauntletstrapL2);
        this.field_178721_j.func_78792_a(this.BackpanelR1);
        this.field_178721_j.func_78792_a(this.BackpanelR2);
        this.field_178721_j.func_78792_a(this.BackpanelR3);
        this.field_178721_j.func_78792_a(this.BackpanelR4);
        this.field_178722_k.func_78792_a(this.BackpanelL1);
        this.field_178722_k.func_78792_a(this.BackpanelL2);
        this.field_178722_k.func_78792_a(this.BackpanelL3);
        this.field_178722_k.func_78792_a(this.BackpanelL4);
    }
    
    public void func_78088_a(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
        final float a = MathHelper.func_76134_b(f * 0.6662f) * 1.4f * f1;
        final float b = MathHelper.func_76134_b(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        final float c = Math.min(a, b);
        this.LegClothR.field_78795_f = a - 0.1047198f;
        this.LegClothL.field_78795_f = b - 0.1047198f;
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
