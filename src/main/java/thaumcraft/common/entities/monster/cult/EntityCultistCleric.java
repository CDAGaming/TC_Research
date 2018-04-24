package thaumcraft.common.entities.monster.cult;

import net.minecraftforge.fml.common.registry.*;
import thaumcraft.common.entities.ai.misc.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.ai.*;
import net.minecraft.inventory.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.lib.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.datasync.*;

public class EntityCultistCleric extends EntityCultist implements IRangedAttackMob, IEntityAdditionalSpawnData
{
    public int rage;
    private static final DataParameter<Boolean> RITUALIST;
    
    public EntityCultistCleric(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.rage = 0;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new AIAltarFocus(this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AILongRangeAttack((IRangedAttackMob)this, 2.0, 1.0, 20, 40, 24.0f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new AICultistHurtByTarget((EntityCreature)this, true));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0);
    }
    
    @Override
    protected void setLoot(final DifficultyInstance diff) {
        this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ItemsTC.crimsonRobeHelm));
        this.func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(ItemsTC.crimsonRobeChest));
        this.func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(ItemsTC.crimsonRobeLegs));
        if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.3f : 0.1f)) {
            this.func_184201_a(EntityEquipmentSlot.FEET, new ItemStack(ItemsTC.crimsonBoots));
        }
    }
    
    public void func_82196_d(final EntityLivingBase entitylivingbase, final float f) {
        final double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
        final double d2 = entitylivingbase.func_174813_aQ().field_72338_b + entitylivingbase.field_70131_O / 2.0f - (this.field_70163_u + this.field_70131_O / 2.0f);
        final double d3 = entitylivingbase.field_70161_v - this.field_70161_v;
        this.func_184609_a(this.func_184600_cs());
        if (this.field_70146_Z.nextFloat() > 0.66f) {
            final EntityGolemOrb entityGolemOrb;
            final EntityGolemOrb blast = entityGolemOrb = new EntityGolemOrb(this.field_70170_p, (EntityLivingBase)this, entitylivingbase, true);
            entityGolemOrb.field_70165_t += blast.field_70159_w / 2.0;
            final EntityGolemOrb entityGolemOrb2 = blast;
            entityGolemOrb2.field_70161_v += blast.field_70179_y / 2.0;
            blast.func_70107_b(blast.field_70165_t, blast.field_70163_u, blast.field_70161_v);
            blast.func_70186_c(d0, d2 + 2.0, d3, 0.66f, 3.0f);
            this.func_184185_a(SoundsTC.egattack, 1.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
            this.field_70170_p.func_72838_d((Entity)blast);
        }
        else {
            final float f2 = MathHelper.func_76129_c(f) * 0.5f;
            this.field_70170_p.func_180498_a((EntityPlayer)null, 1009, this.func_180425_c(), 0);
            for (int i = 0; i < 3; ++i) {
                final EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.field_70170_p, (EntityLivingBase)this, d0 + this.field_70146_Z.nextGaussian() * f2, d2, d3 + this.field_70146_Z.nextGaussian() * f2);
                entitysmallfireball.field_70163_u = this.field_70163_u + this.field_70131_O / 2.0f + 0.5;
                this.field_70170_p.func_72838_d((Entity)entitysmallfireball);
            }
        }
    }
    
    @Override
    protected boolean func_70692_ba() {
        return !this.getIsRitualist();
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityCultistCleric.RITUALIST, (Object)false);
    }
    
    public boolean getIsRitualist() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityCultistCleric.RITUALIST);
    }
    
    public void setIsRitualist(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityCultistCleric.RITUALIST, (Object)par1);
    }
    
    public boolean func_70097_a(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.func_180431_b(p_70097_1_)) {
            return false;
        }
        this.setIsRitualist(false);
        return super.func_70097_a(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        this.setIsRitualist(Boolean.valueOf(par1NBTTagCompound.func_74767_n("ritualist")));
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74757_a("ritualist", this.getIsRitualist());
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeInt(this.func_180486_cf().func_177958_n());
        data.writeInt(this.func_180486_cf().func_177956_o());
        data.writeInt(this.func_180486_cf().func_177952_p());
    }
    
    public void readSpawnData(final ByteBuf data) {
        this.func_175449_a(new BlockPos(data.readInt(), data.readInt(), data.readInt()), 8);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70170_p.field_72995_K && this.getIsRitualist()) {
            final double d0 = this.func_180486_cf().func_177958_n() + 0.5 - this.field_70165_t;
            final double d2 = this.func_180486_cf().func_177956_o() + 1.5 - (this.field_70163_u + this.func_70047_e());
            final double d3 = this.func_180486_cf().func_177952_p() + 0.5 - this.field_70161_v;
            final double d4 = MathHelper.func_76133_a(d0 * d0 + d3 * d3);
            final float f = (float)(Math.atan2(d3, d0) * 180.0 / 3.141592653589793) - 90.0f;
            final float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            this.field_70125_A = this.updateRotation(this.field_70125_A, f2, 10.0f);
            this.field_70759_as = this.updateRotation(this.field_70759_as, f, this.func_70646_bf());
        }
        if (!this.field_70170_p.field_72995_K && this.getIsRitualist() && this.rage >= 5) {
            this.setIsRitualist(false);
        }
    }
    
    private float updateRotation(final float p_75652_1_, final float p_75652_2_, final float p_75652_3_) {
        float f3 = MathHelper.func_76142_g(p_75652_2_ - p_75652_1_);
        if (f3 > p_75652_3_) {
            f3 = p_75652_3_;
        }
        if (f3 < -p_75652_3_) {
            f3 = -p_75652_3_;
        }
        return p_75652_1_ + f3;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.chant;
    }
    
    public int func_70627_aG() {
        return 500;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 19) {
            for (int i = 0; i < 3; ++i) {
                final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
                this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_ANGRY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + 0.5 + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d0, d2, d3, new int[0]);
            }
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        RITUALIST = EntityDataManager.func_187226_a((Class)EntityCultistCleric.class, DataSerializers.field_187198_h);
    }
}
