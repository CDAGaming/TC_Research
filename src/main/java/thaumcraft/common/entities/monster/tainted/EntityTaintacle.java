package thaumcraft.common.entities.monster.tainted;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import thaumcraft.api.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.world.biomes.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.common.config.*;
import net.minecraftforge.fml.relauncher.*;

public class EntityTaintacle extends EntityMob implements ITaintedMob
{
    public float flailIntensity;
    
    public EntityTaintacle(final World par1World) {
        super(par1World);
        this.flailIntensity = 1.0f;
        this.func_70105_a(0.8f, 3.0f);
        this.field_70728_aV = 8;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 6.0f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    public boolean func_70686_a(final Class clazz) {
        return !ITaintedMob.class.isAssignableFrom(clazz);
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        return otherEntity instanceof ITaintedMob || super.func_184191_r(otherEntity);
    }
    
    public boolean func_70601_bi() {
        final boolean onTaint = this.field_70170_p.func_180495_p(this.func_180425_c()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT || this.field_70170_p.func_180495_p(this.func_180425_c().func_177977_b()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT;
        return onTaint && this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0);
    }
    
    public void func_70091_d(final MoverType mt, double par1, double par3, double par5) {
        par1 = 0.0;
        par5 = 0.0;
        if (par3 > 0.0) {
            par3 = 0.0;
        }
        super.func_70091_d(mt, par1, par3, par5);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 20 == 0) {
            final boolean onTaint = this.field_70170_p.func_180495_p(this.func_180425_c()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT || this.field_70170_p.func_180495_p(this.func_180425_c().func_177977_b()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT;
            if (!onTaint) {
                this.func_70665_d(DamageSource.field_76366_f, 1.0f);
            }
            if (!(this instanceof EntityTaintacleSmall) && this.field_70173_aa % 40 == 0 && this.func_70638_az() != null && this.func_70068_e((Entity)this.func_70638_az()) > 16.0 && this.func_70068_e((Entity)this.func_70638_az()) < 256.0 && this.func_70635_at().func_75522_a((Entity)this.func_70638_az())) {
                this.spawnTentacles((Entity)this.func_70638_az());
            }
        }
        if (this.field_70170_p.field_72995_K) {
            if (this.flailIntensity > 1.0f) {
                this.flailIntensity -= 0.01f;
            }
            if (this.field_70173_aa < this.field_70131_O * 10.0f && this.field_70122_E) {
                FXDispatcher.INSTANCE.tentacleAriseFX((Entity)this);
            }
        }
    }
    
    protected void spawnTentacles(final Entity entity) {
        if (this.field_70170_p.func_180494_b(entity.func_180425_c()) == BiomeHandler.ELDRITCH || this.field_70170_p.func_180495_p(entity.func_180425_c()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT || this.field_70170_p.func_180495_p(entity.func_180425_c().func_177977_b()).func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT) {
            final EntityTaintacleSmall taintlet = new EntityTaintacleSmall(this.field_70170_p);
            taintlet.func_70012_b(entity.field_70165_t + this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat(), entity.field_70163_u, entity.field_70161_v + this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat(), 0.0f, 0.0f);
            this.field_70170_p.func_72838_d((Entity)taintlet);
            this.func_184185_a(SoundsTC.tentacle, this.func_70599_aP(), this.func_70647_i());
            if (this.field_70170_p.func_180494_b(entity.func_180425_c()) == BiomeHandler.ELDRITCH && this.field_70170_p.func_175623_d(entity.func_180425_c()) && BlockUtils.isAdjacentToSolidBlock(this.field_70170_p, entity.func_180425_c())) {
                this.field_70170_p.func_175656_a(entity.func_180425_c(), BlocksTC.taintFibre.func_176223_P());
            }
        }
    }
    
    public void faceEntity(final Entity par1Entity, final float par2) {
        final double d0 = par1Entity.field_70165_t - this.field_70165_t;
        final double d2 = par1Entity.field_70161_v - this.field_70161_v;
        final float f2 = (float)(Math.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        this.field_70177_z = this.func_70663_b(this.field_70177_z, f2, par2);
    }
    
    protected float func_70663_b(final float par1, final float par2, final float par3) {
        float f3 = MathHelper.func_76142_g(par2 - par1);
        if (f3 > par3) {
            f3 = par3;
        }
        if (f3 < -par3) {
            f3 = -par3;
        }
        return par1 + f3;
    }
    
    public int func_70627_aG() {
        return 200;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187540_ab;
    }
    
    protected float func_70647_i() {
        return 1.3f - this.field_70131_O / 10.0f;
    }
    
    protected float func_70599_aP() {
        return this.field_70131_O / 8.0f;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.tentacle;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.tentacle;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            this.flailIntensity = 3.0f;
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public boolean func_70652_k(final Entity p_70652_1_) {
        this.field_70170_p.func_72960_a((Entity)this, (byte)16);
        this.func_184185_a(SoundsTC.tentacle, this.func_70599_aP(), this.func_70647_i());
        return super.func_70652_k(p_70652_1_);
    }
}
