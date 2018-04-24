package thaumcraft.client.renderers.models.entity;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.util.math.*;

@SideOnly(Side.CLIENT)
public class ModelFireBat extends ModelBase
{
    private ModelRenderer batHead;
    private ModelRenderer batBody;
    private ModelRenderer batRightWing;
    private ModelRenderer batLeftWing;
    private ModelRenderer batOuterRightWing;
    private ModelRenderer batOuterLeftWing;
    
    public ModelFireBat() {
        this.field_78090_t = 64;
        this.field_78089_u = 64;
        (this.batHead = new ModelRenderer((ModelBase)this, 0, 0)).func_78789_a(-3.0f, -3.0f, -3.0f, 6, 6, 6);
        final ModelRenderer var1 = new ModelRenderer((ModelBase)this, 24, 0);
        var1.func_78789_a(-4.0f, -6.0f, -2.0f, 3, 4, 1);
        this.batHead.func_78792_a(var1);
        final ModelRenderer var2 = new ModelRenderer((ModelBase)this, 24, 0);
        var2.field_78809_i = true;
        var2.func_78789_a(1.0f, -6.0f, -2.0f, 3, 4, 1);
        this.batHead.func_78792_a(var2);
        (this.batBody = new ModelRenderer((ModelBase)this, 0, 16)).func_78789_a(-3.0f, 4.0f, -3.0f, 6, 12, 6);
        this.batBody.func_78784_a(0, 34).func_78789_a(-5.0f, 16.0f, 0.0f, 10, 6, 1);
        (this.batRightWing = new ModelRenderer((ModelBase)this, 42, 0)).func_78789_a(-12.0f, 1.0f, 1.5f, 10, 16, 1);
        (this.batOuterRightWing = new ModelRenderer((ModelBase)this, 24, 16)).func_78793_a(-12.0f, 1.0f, 1.5f);
        this.batOuterRightWing.func_78789_a(-8.0f, 1.0f, 0.0f, 8, 12, 1);
        this.batLeftWing = new ModelRenderer((ModelBase)this, 42, 0);
        this.batLeftWing.field_78809_i = true;
        this.batLeftWing.func_78789_a(2.0f, 1.0f, 1.5f, 10, 16, 1);
        this.batOuterLeftWing = new ModelRenderer((ModelBase)this, 24, 16);
        this.batOuterLeftWing.field_78809_i = true;
        this.batOuterLeftWing.func_78793_a(12.0f, 1.0f, 1.5f);
        this.batOuterLeftWing.func_78789_a(0.0f, 1.0f, 0.0f, 8, 12, 1);
        this.batBody.func_78792_a(this.batRightWing);
        this.batBody.func_78792_a(this.batLeftWing);
        this.batRightWing.func_78792_a(this.batOuterRightWing);
        this.batLeftWing.func_78792_a(this.batOuterLeftWing);
    }
    
    public int getBatSize() {
        return 36;
    }
    
    public void func_78088_a(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        if (par1Entity instanceof EntityFireBat && ((EntityFireBat)par1Entity).getIsBatHanging()) {
            this.batHead.field_78795_f = par6 / 57.295776f;
            this.batHead.field_78796_g = 3.1415927f - par5 / 57.295776f;
            this.batHead.field_78808_h = 3.1415927f;
            this.batHead.func_78793_a(0.0f, -2.0f, 0.0f);
            this.batRightWing.func_78793_a(-3.0f, 0.0f, 3.0f);
            this.batLeftWing.func_78793_a(3.0f, 0.0f, 3.0f);
            this.batBody.field_78795_f = 3.1415927f;
            this.batRightWing.field_78795_f = -0.15707964f;
            this.batRightWing.field_78796_g = -1.2566371f;
            this.batOuterRightWing.field_78796_g = -1.7278761f;
            this.batLeftWing.field_78795_f = this.batRightWing.field_78795_f;
            this.batLeftWing.field_78796_g = -this.batRightWing.field_78796_g;
            this.batOuterLeftWing.field_78796_g = -this.batOuterRightWing.field_78796_g;
        }
        else {
            this.batHead.field_78795_f = par6 / 57.295776f;
            this.batHead.field_78796_g = par5 / 57.295776f;
            this.batHead.field_78808_h = 0.0f;
            this.batHead.func_78793_a(0.0f, 0.0f, 0.0f);
            this.batRightWing.func_78793_a(0.0f, 0.0f, 0.0f);
            this.batLeftWing.func_78793_a(0.0f, 0.0f, 0.0f);
            this.batBody.field_78795_f = 0.7853982f + MathHelper.func_76134_b(par4 * 0.1f) * 0.15f;
            this.batBody.field_78796_g = 0.0f;
            this.batRightWing.field_78796_g = MathHelper.func_76134_b(par4 * 1.3f) * 3.1415927f * 0.25f;
            this.batLeftWing.field_78796_g = -this.batRightWing.field_78796_g;
            this.batOuterRightWing.field_78796_g = this.batRightWing.field_78796_g * 0.5f;
            this.batOuterLeftWing.field_78796_g = -this.batRightWing.field_78796_g * 0.5f;
        }
        this.batHead.func_78785_a(par7);
        this.batBody.func_78785_a(par7);
    }
}
