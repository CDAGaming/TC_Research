package thaumcraft.common.entities.monster.tainted;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.blocks.world.taint.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import thaumcraft.common.config.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;

public class EntityTaintCrawler extends EntityMob implements ITaintedMob
{
    BlockPos lastPos;
    
    public EntityTaintCrawler(final World par1World) {
        super(par1World);
        this.lastPos = new BlockPos(0, 0, 0);
        this.func_70105_a(0.5f, 0.4f);
        this.field_70728_aV = 3;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    public boolean func_70686_a(final Class clazz) {
        return !ITaintedMob.class.isAssignableFrom(clazz);
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        return otherEntity instanceof ITaintedMob || super.func_184191_r(otherEntity);
    }
    
    public float func_70047_e() {
        return 0.1f;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.275);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0);
    }
    
    protected float func_70647_i() {
        return 0.7f;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187793_eY;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187850_fa;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187795_eZ;
    }
    
    protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.func_184185_a(SoundEvents.field_187852_fb, 0.15f, 1.0f);
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public void func_70071_h_() {
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 40 == 0 && this.lastPos != this.func_180425_c()) {
            this.lastPos = this.func_180425_c();
            final IBlockState bs = this.field_70170_p.func_180495_p(this.func_180425_c());
            final Material bm = bs.func_185904_a();
            if (!bs.func_177230_c().isLeaves(bs, (IBlockAccess)this.field_70170_p, this.func_180425_c()) && !bm.func_76224_d() && bm != ThaumcraftMaterials.MATERIAL_TAINT && (this.field_70170_p.func_175623_d(this.func_180425_c()) || bs.func_177230_c().func_176200_f((IBlockAccess)this.field_70170_p, this.func_180425_c()) || bs.func_177230_c() instanceof BlockFlower || bs.func_177230_c() instanceof IPlantable) && BlockUtils.isAdjacentToSolidBlock(this.field_70170_p, this.func_180425_c()) && !BlockTaintFibre.isOnlyAdjacentToTaint(this.field_70170_p, this.func_180425_c())) {
                this.field_70170_p.func_175656_a(this.func_180425_c(), BlocksTC.taintFibre.func_176223_P());
            }
        }
        super.func_70071_h_();
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    public EnumCreatureAttribute func_70668_bt() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        if (this.field_70170_p.field_73012_v.nextInt(8) == 0) {
            this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        }
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        return p_180482_2_;
    }
    
    public boolean func_70652_k(final Entity victim) {
        if (super.func_70652_k(victim)) {
            if (victim instanceof EntityLivingBase) {
                byte b0 = 0;
                if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
                    b0 = 3;
                }
                else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                    b0 = 6;
                }
                if (b0 > 0 && this.field_70146_Z.nextInt(b0 + 1) > 2) {
                    ((EntityLivingBase)victim).func_70690_d(new PotionEffect(PotionFluxTaint.instance, b0 * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
}
