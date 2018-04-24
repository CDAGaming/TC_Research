package thaumcraft.common.entities.monster;

import thaumcraft.api.entities.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.potion.*;
import thaumcraft.common.lib.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.network.datasync.*;

public class EntityEldritchCrab extends EntityMob implements IEldritchMob
{
    private static final DataParameter<Boolean> HELM;
    private int attackTime;
    
    public EntityEldritchCrab(final World par1World) {
        super(par1World);
        this.attackTime = 0;
        this.func_70105_a(0.8f, 0.6f);
        this.field_70728_aV = 6;
        ((PathNavigateGround)this.func_70661_as()).func_179688_b(true);
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.63f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityCultist.class, true));
    }
    
    public double func_70033_W() {
        return this.func_184218_aH() ? 0.5 : 0.0;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(this.hasHelm() ? 0.275 : 0.3);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityEldritchCrab.HELM, (Object)false);
    }
    
    public boolean func_98052_bS() {
        return false;
    }
    
    public int func_70658_aO() {
        return this.hasHelm() ? 5 : 0;
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, IEntityLivingData data) {
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
            this.setHelm(true);
        }
        else {
            this.setHelm(this.field_70146_Z.nextFloat() < 0.33f);
        }
        if (data == null) {
            data = (IEntityLivingData)new EntitySpider.GroupData();
            if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70170_p.field_73012_v.nextFloat() < 0.1f * diff.func_180170_c()) {
                ((EntitySpider.GroupData)data).func_111104_a(this.field_70170_p.field_73012_v);
            }
        }
        if (data instanceof EntitySpider.GroupData) {
            final Potion potion = ((EntitySpider.GroupData)data).field_188478_a;
            if (potion != null) {
                this.func_70690_d(new PotionEffect(potion, Integer.MAX_VALUE));
            }
        }
        return super.func_180482_a(diff, data);
    }
    
    public boolean hasHelm() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityEldritchCrab.HELM);
    }
    
    public void setHelm(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityEldritchCrab.HELM, (Object)par1);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        --this.attackTime;
        if (this.field_70173_aa < 20) {
            this.field_70143_R = 0.0f;
        }
        if (this.func_184187_bx() == null && this.func_70638_az() != null && this.func_70638_az().func_184187_bx() == null && !this.field_70122_E && !this.hasHelm() && !this.func_70638_az().field_70128_L && this.field_70163_u - this.func_70638_az().field_70163_u >= this.func_70638_az().field_70131_O / 2.0f && this.func_70068_e((Entity)this.func_70638_az()) < 4.0) {
            this.func_184205_a((Entity)this.func_70638_az(), true);
        }
        if (!this.field_70170_p.field_72995_K && this.func_184187_bx() != null && this.attackTime <= 0) {
            this.attackTime = 10 + this.field_70146_Z.nextInt(10);
            this.func_70652_k(this.func_184187_bx());
            if (this.func_184187_bx() != null && this.field_70146_Z.nextFloat() < 0.2) {
                this.func_110145_l(this.func_184187_bx());
            }
        }
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
        super.func_70628_a(p_70628_1_, p_70628_2_);
        if (p_70628_1_ && (this.field_70146_Z.nextInt(3) == 0 || this.field_70146_Z.nextInt(1 + p_70628_2_) > 0)) {
            this.func_145779_a(Items.field_151079_bi, 1);
        }
    }
    
    public boolean func_70652_k(final Entity p_70652_1_) {
        if (super.func_70652_k(p_70652_1_)) {
            this.func_184185_a(SoundsTC.crabclaw, 1.0f, 0.9f + this.field_70170_p.field_73012_v.nextFloat() * 0.2f);
            return true;
        }
        return false;
    }
    
    public boolean func_70097_a(final DamageSource source, final float damage) {
        final boolean b = super.func_70097_a(source, damage);
        if (this.hasHelm() && this.func_110143_aJ() / this.func_110138_aP() <= 0.5f) {
            this.setHelm(false);
            this.func_70669_a(new ItemStack(ItemsTC.crimsonPlateChest));
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3);
        }
        return b;
    }
    
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        this.setHelm(par1NBTTagCompound.func_74767_n("helm"));
        if (!this.hasHelm()) {
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3);
        }
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74757_a("helm", this.hasHelm());
    }
    
    public int func_70627_aG() {
        return 160;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.crabtalk;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187543_bD;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.crabdeath;
    }
    
    protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.func_184185_a(SoundEvents.field_187823_fN, 0.15f, 1.0f);
    }
    
    public EnumCreatureAttribute func_70668_bt() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean func_70687_e(final PotionEffect p_70687_1_) {
        return !p_70687_1_.func_188419_a().equals(MobEffects.field_76436_u) && super.func_70687_e(p_70687_1_);
    }
    
    public boolean func_184191_r(final Entity el) {
        return el instanceof EntityEldritchCrab;
    }
    
    static {
        HELM = EntityDataManager.func_187226_a((Class)EntityEldritchCrab.class, DataSerializers.field_187198_h);
    }
}
