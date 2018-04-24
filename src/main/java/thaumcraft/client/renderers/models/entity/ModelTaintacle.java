package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.monster.tainted.*;
import org.lwjgl.opengl.*;

public class ModelTaintacle extends ModelBase
{
    public ModelRenderer tentacle;
    public ModelRenderer[] tents;
    public ModelRenderer orb;
    private int length;
    private boolean seed;
    
    public ModelTaintacle(final int length, final boolean seed) {
        this.tentacle = new ModelRendererTaintacle(this);
        this.orb = new ModelRendererTaintacle(this);
        this.length = 10;
        this.seed = false;
        this.seed = seed;
        final int var3 = 0;
        this.length = length;
        this.field_78089_u = 64;
        this.field_78090_t = 64;
        (this.tentacle = new ModelRendererTaintacle(this, 0, 0)).func_78789_a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.tentacle.field_78800_c = 0.0f;
        this.tentacle.field_78798_e = 0.0f;
        this.tentacle.field_78797_d = 12.0f;
        this.tents = new ModelRendererTaintacle[length];
        for (int k = 0; k < length - 1; ++k) {
            (this.tents[k] = new ModelRendererTaintacle(this, 0, 16)).func_78789_a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
            this.tents[k].field_78797_d = -8.0f;
            if (k == 0) {
                this.tentacle.func_78792_a(this.tents[k]);
            }
            else {
                this.tents[k - 1].func_78792_a(this.tents[k]);
            }
        }
        if (!seed) {
            (this.orb = new ModelRendererTaintacle(this, 0, 56)).func_78789_a(-2.0f, -2.0f, -2.0f, 4, 4, 4);
            this.orb.field_78797_d = -8.0f;
            this.tents[length - 2].func_78792_a(this.orb);
            (this.tents[length - 1] = new ModelRendererTaintacle(this, 0, 32)).func_78789_a(-6.0f, -6.0f, -6.0f, 12, 12, 12);
            this.tents[length - 1].field_78797_d = -8.0f;
            this.tents[length - 2].func_78792_a(this.tents[length - 1]);
        }
    }
    
    public void func_78087_a(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        float flail = 0.0f;
        float ht = 0.0f;
        final int at = 0;
        if (entity instanceof EntityTaintacle) {
            final EntityTaintacle tentacle = (EntityTaintacle)entity;
            flail = tentacle.flailIntensity;
            ht = tentacle.field_70737_aN;
            final float mod = par6 * 0.2f;
            final float fs = (flail > 1.0f) ? 3.0f : (1.0f + ((flail > 1.0f) ? mod : (-mod)));
            final float fi = flail + ((ht > 0.0f || at > 0) ? mod : (-mod));
            this.tentacle.field_78795_f = 0.0f;
            for (int k = 0; k < this.length - 1; ++k) {
                this.tents[k].field_78795_f = 0.15f * fi * MathHelper.func_76126_a(par3 * 0.1f * fs - k / 2.0f);
                this.tents[k].field_78808_h = 0.1f / fi * MathHelper.func_76126_a(par3 * 0.15f - k / 2.0f);
            }
        }
        if (entity instanceof EntityTaintSeed) {
            final EntityTaintSeed seed = (EntityTaintSeed)entity;
            ht = seed.field_70737_aN / 200.0f;
            flail = 0.1f;
            final float mod = par6 * 0.2f;
            final float fs = (flail > 1.0f) ? 3.0f : (1.0f + ((flail > 1.0f) ? mod : (-mod)));
            float fi = flail + ((ht > 0.0f || at > 0) ? mod : (-mod));
            fi *= 3.0f;
            this.tentacle.field_78795_f = 0.0f;
            for (int k = 0; k < this.length - 1; ++k) {
                this.tents[k].field_78795_f = 0.2f + 0.01f * k * k + ht + seed.attackAnim;
                this.tents[k].field_78808_h = 0.1f / fi * MathHelper.func_76126_a(par3 * 0.05f - k / 2.0f) / 5.0f;
            }
        }
    }
    
    public void func_78088_a(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.func_78087_a(par2, par3, par4, par5, par6, par7, par1Entity);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (par1Entity instanceof EntityTaintSeed) {
            GL11.glTranslatef(0.0f, 1.0f, -0.2f);
            GL11.glScalef(par1Entity.field_70130_N * 0.6f, par1Entity.field_70131_O, par1Entity.field_70130_N * 0.6f);
            ((ModelRendererTaintacle)this.tentacle).render(par7, this.seed ? 0.82f : 0.85f);
        }
        else {
            float height = 0.0f;
            final float hc = par1Entity.field_70131_O * 10.0f;
            if (par1Entity.field_70173_aa < hc) {
                height = (hc - par1Entity.field_70173_aa) / hc * par1Entity.field_70131_O;
            }
            GL11.glTranslatef(0.0f, ((par1Entity.field_70131_O == 3.0f) ? 0.6f : 1.2f) + height, 0.0f);
            GL11.glScalef(par1Entity.field_70131_O / 3.0f, par1Entity.field_70131_O / 3.0f, par1Entity.field_70131_O / 3.0f);
            ((ModelRendererTaintacle)this.tentacle).render(par7, 0.88f);
        }
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
