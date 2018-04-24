package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import thaumcraft.client.fx.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import thaumcraft.common.config.*;

public class EntityThaumicSlime extends EntitySlime implements ITaintedMob
{
    int launched;
    int spitCounter;
    
    public EntityThaumicSlime(final World par1World) {
        super(par1World);
        this.launched = 10;
        this.spitCounter = 100;
        final int i = 1 << 1 + this.field_70146_Z.nextInt(3);
        this.func_70799_a(i, true);
    }
    
    public EntityThaumicSlime(final World par1World, final EntityLivingBase par2EntityLiving, final EntityLivingBase par3EntityLiving) {
        super(par1World);
        this.launched = 10;
        this.spitCounter = 100;
        this.func_70799_a(1, true);
        this.field_70163_u = (par2EntityLiving.func_174813_aQ().field_72338_b + par2EntityLiving.func_174813_aQ().field_72337_e) / 2.0;
        final double var6 = par3EntityLiving.field_70165_t - par2EntityLiving.field_70165_t;
        final double var7 = par3EntityLiving.func_174813_aQ().field_72338_b + par3EntityLiving.field_70131_O / 3.0f - this.field_70163_u;
        final double var8 = par3EntityLiving.field_70161_v - par2EntityLiving.field_70161_v;
        final double var9 = MathHelper.func_76133_a(var6 * var6 + var8 * var8);
        if (var9 >= 1.0E-7) {
            final float var10 = (float)(Math.atan2(var8, var6) * 180.0 / 3.141592653589793) - 90.0f;
            final float var11 = (float)(-(Math.atan2(var7, var9) * 180.0 / 3.141592653589793));
            final double var12 = var6 / var9;
            final double var13 = var8 / var9;
            this.func_70012_b(par2EntityLiving.field_70165_t + var12, this.field_70163_u, par2EntityLiving.field_70161_v + var13, var10, var11);
            final float var14 = (float)var9 * 0.2f;
            this.shoot(var6, var7 + var14, var8, 1.5f, 1.0f);
        }
    }
    
    public void shoot(double par1, double par3, double par5, final float par7, final float par8) {
        final float var9 = MathHelper.func_76133_a(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += this.field_70146_Z.nextGaussian() * 0.007499999832361937 * par8;
        par3 += this.field_70146_Z.nextGaussian() * 0.007499999832361937 * par8;
        par5 += this.field_70146_Z.nextGaussian() * 0.007499999832361937 * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        this.field_70159_w = par1;
        this.field_70181_x = par3;
        this.field_70179_y = par5;
        final float var10 = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
        final float n = (float)(Math.atan2(par1, par5) * 180.0 / 3.141592653589793);
        this.field_70177_z = n;
        this.field_70126_B = n;
        final float n2 = (float)(Math.atan2(par3, var10) * 180.0 / 3.141592653589793);
        this.field_70125_A = n2;
        this.field_70127_C = n2;
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        int i = this.field_70146_Z.nextInt(3);
        if (i < 2 && this.field_70146_Z.nextFloat() < 0.5f * p_180482_1_.func_180170_c()) {
            ++i;
        }
        final int j = 1 << i;
        this.func_70799_a(j, true);
        return super.func_180482_a(p_180482_1_, p_180482_2_);
    }
    
    public void func_70799_a(final int par1, final boolean t) {
        super.func_70799_a(par1, t);
        this.field_70728_aV = par1 + 2;
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
    }
    
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
    }
    
    public void func_70071_h_() {
        final int i = this.func_70809_q();
        if (this.field_70122_E && !this.field_175452_bi) {
            this.field_175452_bi = true;
            final float sa = this.field_70813_a;
            super.func_70071_h_();
            this.field_70813_a = sa;
            if (this.field_70170_p.field_72995_K) {
                for (int j = 0; j < i * 2; ++j) {
                    FXDispatcher.INSTANCE.slimeJumpFX((Entity)this, i);
                }
            }
            if (this.func_70807_r()) {
                this.func_184185_a(this.func_184710_cZ(), this.func_70599_aP(), ((this.func_70681_au().nextFloat() - this.func_70681_au().nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.field_70813_a = -0.5f;
            this.field_175452_bi = this.field_70122_E;
            this.func_70808_l();
        }
        else {
            super.func_70071_h_();
        }
        if (this.field_70170_p.field_72995_K) {
            if (this.launched > 0) {
                --this.launched;
                for (int k = 0; k < i * (this.launched + 1); ++k) {
                    FXDispatcher.INSTANCE.slimeJumpFX((Entity)this, i);
                }
            }
            final float ff = this.func_70809_q();
            this.func_70105_a(0.6f * ff, 0.6f * ff);
            this.func_70105_a(0.51000005f * ff, 0.51000005f * ff);
        }
        else if (!this.field_70128_L) {
            final EntityPlayer entityplayer = this.field_70170_p.func_72890_a((Entity)this, 16.0);
            if (entityplayer != null) {
                if (this.spitCounter > 0) {
                    --this.spitCounter;
                }
                this.func_70625_a((Entity)entityplayer, 10.0f, 20.0f);
                if (this.func_70032_d((Entity)entityplayer) > 4.0f && this.spitCounter <= 0 && this.func_70809_q() > 2) {
                    this.spitCounter = 101;
                    if (!this.field_70170_p.field_72995_K) {
                        final EntityThaumicSlime flyslime = new EntityThaumicSlime(this.field_70170_p, (EntityLivingBase)this, (EntityLivingBase)entityplayer);
                        this.field_70170_p.func_72838_d((Entity)flyslime);
                    }
                    this.func_184185_a(SoundsTC.gore, 1.0f, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f + 1.0f) * 0.8f);
                    this.func_70799_a(this.func_70809_q() - 1, true);
                }
            }
        }
    }
    
    protected EntityThaumicSlime createInstance() {
        return new EntityThaumicSlime(this.field_70170_p);
    }
    
    public void func_70106_y() {
        final int i = this.func_70809_q();
        if (!this.field_70170_p.field_72995_K && i > 1 && this.func_110143_aJ() <= 0.0f) {
            for (int k = 0; k < i; ++k) {
                final float f = (k % 2 - 0.5f) * i / 4.0f;
                final float f2 = (k / 2 - 0.5f) * i / 4.0f;
                final EntityThaumicSlime entityslime = this.createInstance();
                entityslime.func_70799_a(1, true);
                entityslime.func_70012_b(this.field_70165_t + f, this.field_70163_u + 0.5, this.field_70161_v + f2, this.field_70146_Z.nextFloat() * 360.0f, 0.0f);
                this.field_70170_p.func_72838_d((Entity)entityslime);
            }
        }
        this.field_70128_L = true;
    }
    
    public boolean func_70601_bi() {
        return false;
    }
    
    protected int func_70805_n() {
        return this.func_70809_q() + 1;
    }
    
    protected boolean func_70800_m() {
        return true;
    }
    
    protected void func_175451_e(final EntityLivingBase p_175451_1_) {
        int i = this.func_70809_q();
        if (this.launched > 0) {
            i += 2;
        }
        if (this.func_70685_l((Entity)p_175451_1_) && this.func_70068_e((Entity)p_175451_1_) < 0.6 * i * 0.6 * i && p_175451_1_.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), (float)this.func_70805_n())) {
            this.func_184185_a(SoundEvents.field_187870_fk, 1.0f, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f + 1.0f);
            this.func_174815_a((EntityLivingBase)this, (Entity)p_175451_1_);
        }
    }
    
    protected Item func_146068_u() {
        return (this.func_70809_q() > 1) ? ItemsTC.crystalEssence : Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        if (this.func_70809_q() > 1) {
            this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        }
    }
}
