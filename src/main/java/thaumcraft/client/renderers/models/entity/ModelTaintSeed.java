package thaumcraft.client.renderers.models.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class ModelTaintSeed extends ModelBase
{
    public ModelRenderer tentacle;
    public ModelRenderer[] tents;
    public ModelRenderer orb;
    private int length;
    
    public ModelTaintSeed() {
        this.tentacle = new ModelRendererTaintSeed(this);
        this.orb = new ModelRendererTaintSeed(this);
        this.length = 8;
        this.field_78089_u = 64;
        this.field_78090_t = 64;
        (this.tentacle = new ModelRendererTaintSeed(this, 0, 0)).func_78789_a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.tentacle.field_78800_c = 0.0f;
        this.tentacle.field_78798_e = 0.0f;
        this.tentacle.field_78797_d = 12.0f;
        this.tents = new ModelRendererTaintSeed[this.length];
        for (int k = 0; k < this.length - 1; ++k) {
            this.tents[k] = new ModelRendererTaintSeed(this, 0, (k < this.length - 4) ? 16 : ((k == this.length - 4) ? 48 : 56));
            if (k < this.length - 4) {
                this.tents[k].func_78789_a(-4.0f, -4.0f, -4.0f, 8, 8, 8);
                this.tents[k].field_78797_d = -8.0f;
            }
            else {
                this.tents[k].func_78789_a(-2.0f, -2.0f, -2.0f, 4, 4, 4);
                this.tents[k].field_78797_d = ((k == this.length - 4) ? -8.0f : -4.0f);
            }
            if (k == 0) {
                this.tentacle.func_78792_a(this.tents[k]);
            }
            else {
                this.tents[k - 1].func_78792_a(this.tents[k]);
            }
        }
    }
    
    public void func_78087_a(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        float flail = 0.0f;
        float ht = 0.0f;
        final int at = 0;
        final EntityTaintSeed seed = (EntityTaintSeed)entity;
        ht = seed.field_70737_aN / 200.0f;
        flail = 0.1f;
        final float mod = par6 * 0.2f;
        final float fs = (flail > 1.0f) ? 3.0f : (1.0f + ((flail > 1.0f) ? mod : (-mod)));
        float fi = flail + ((ht > 0.0f || at > 0) ? mod : (-mod));
        fi *= 3.0f;
        this.tentacle.field_78795_f = 0.0f;
        for (int k = 0; k < this.length - 1; ++k) {
            this.tents[k].field_78795_f = 0.1f / fi * MathHelper.func_76126_a(par3 * 0.06f - k / 2.0f) / 5.0f + ht + seed.attackAnim;
            this.tents[k].field_78808_h = 0.1f / fi * MathHelper.func_76126_a(par3 * 0.05f - k / 2.0f) / 5.0f;
        }
    }
    
    public void func_78088_a(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.func_78087_a(par2, par3, par4, par5, par6, par7, par1Entity);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float height = 0.0f;
        final float hc = par1Entity.field_70131_O * 10.0f;
        if (par1Entity.field_70173_aa < hc) {
            height = (hc - par1Entity.field_70173_aa) / hc * par1Entity.field_70131_O;
        }
        GL11.glTranslatef(0.0f, ((par1Entity.field_70131_O == 3.0f) ? 0.6f : 1.2f) + height, 0.0f);
        GL11.glScalef(par1Entity.field_70131_O / 2.0f, par1Entity.field_70131_O / 2.0f, par1Entity.field_70131_O / 2.0f);
        ((ModelRendererTaintSeed)this.tentacle).render(par7, par1Entity.field_70173_aa + Minecraft.func_71410_x().func_184121_ak(), 1.6f);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
