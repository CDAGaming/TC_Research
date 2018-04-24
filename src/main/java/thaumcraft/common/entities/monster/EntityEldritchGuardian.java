package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import net.minecraft.pathfinding.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.monster.cult.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.util.math.*;

public class EntityEldritchGuardian extends EntityMob implements IRangedAttackMob, IEldritchMob
{
    public float armLiftL;
    public float armLiftR;
    boolean lastBlast;
    
    public EntityEldritchGuardian(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.armLiftL = 0.0f;
        this.armLiftR = 0.0f;
        this.lastBlast = false;
        ((PathNavigateGround)this.func_70661_as()).func_179688_b(true);
        this.func_70105_a(0.8f, 2.25f);
        this.field_70728_aV = 20;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AILongRangeAttack((IRangedAttackMob)this, 8.0, 1.0, 20, 40, 24.0f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityCultist.class, true));
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0);
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.28);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
    }
    
    public int func_70658_aO() {
        return 4;
    }
    
    public boolean func_98052_bS() {
        return false;
    }
    
    public boolean func_70097_a(final DamageSource source, float damage) {
        if (source.func_82725_o()) {
            damage /= 2.0f;
        }
        return super.func_70097_a(source, damage);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70170_p.field_72995_K) {
            if (this.armLiftL > 0.0f) {
                this.armLiftL -= 0.05f;
            }
            if (this.armLiftR > 0.0f) {
                this.armLiftR -= 0.05f;
            }
            final float x = (float)(this.field_70165_t + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f);
            final float z = (float)(this.field_70161_v + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2f);
            FXDispatcher.INSTANCE.wispFXEG(x, (float)(this.field_70163_u + 0.22 * this.field_70131_O), z, (Entity)this);
        }
        else if (this.field_70170_p.field_73011_w.getDimension() != ModConfig.CONFIG_WORLD.dimensionOuterId && (this.field_70173_aa == 0 || this.field_70173_aa % 100 == 0) && this.field_70170_p.func_175659_aa() != EnumDifficulty.EASY) {
            final double d6 = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 576.0 : 256.0;
            for (int i = 0; i < this.field_70170_p.field_73010_i.size(); ++i) {
                final EntityPlayer entityplayer1 = this.field_70170_p.field_73010_i.get(i);
                if (entityplayer1.func_70089_S()) {
                    final double d7 = entityplayer1.func_70092_e(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                    if (d7 < d6) {
                        PacketHandler.INSTANCE.sendTo((IMessage)new PacketMiscEvent((byte)2), (EntityPlayerMP)entityplayer1);
                    }
                }
            }
        }
    }
    
    public boolean func_70652_k(final Entity p_70652_1_) {
        final boolean flag = super.func_70652_k(p_70652_1_);
        if (flag) {
            final int i = this.field_70170_p.func_175659_aa().func_151525_a();
            if (this.func_184614_ca() == null && this.func_70027_ad() && this.field_70146_Z.nextFloat() < i * 0.3f) {
                p_70652_1_.func_70015_d(2 * i);
            }
        }
        return flag;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.egidle;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.egdeath;
    }
    
    public int func_70627_aG() {
        return 500;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        super.func_70628_a(flag, i);
    }
    
    public EnumCreatureAttribute func_70668_bt() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        if (this.func_180486_cf() != null && this.func_110174_bM() > 0.0f) {
            nbt.func_74768_a("HomeD", (int)this.func_110174_bM());
            nbt.func_74768_a("HomeX", this.func_180486_cf().func_177958_n());
            nbt.func_74768_a("HomeY", this.func_180486_cf().func_177956_o());
            nbt.func_74768_a("HomeZ", this.func_180486_cf().func_177952_p());
        }
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        if (nbt.func_74764_b("HomeD")) {
            this.func_175449_a(new BlockPos(nbt.func_74762_e("HomeX"), nbt.func_74762_e("HomeY"), nbt.func_74762_e("HomeZ")), nbt.func_74762_e("HomeD"));
        }
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        final IEntityLivingData dd = super.func_180482_a(diff, data);
        final float f = diff.func_180170_c();
        if (this.field_70170_p.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId) {
            final int bh = (int)this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() / 2;
            this.func_110149_m(this.func_110139_bj() + bh);
        }
        return dd;
    }
    
    protected void func_70619_bc() {
        super.func_70619_bc();
        if (this.field_70170_p.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId && this.field_70172_ad <= 0 && this.field_70173_aa % 25 == 0) {
            final int bh = (int)this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() / 2;
            if (this.func_110139_bj() < bh) {
                this.func_110149_m(this.func_110139_bj() + 1.0f);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte p_70103_1_) {
        if (p_70103_1_ == 15) {
            this.armLiftL = 0.5f;
        }
        else if (p_70103_1_ == 16) {
            this.armLiftR = 0.5f;
        }
        else if (p_70103_1_ == 17) {
            this.armLiftL = 0.9f;
            this.armLiftR = 0.9f;
        }
        else {
            super.func_70103_a(p_70103_1_);
        }
    }
    
    protected boolean func_70692_ba() {
        return !this.func_110175_bO();
    }
    
    public float func_70047_e() {
        return 2.1f;
    }
    
    public boolean func_70601_bi() {
        final List ents = this.field_70170_p.func_72872_a((Class)EntityEldritchGuardian.class, new AxisAlignedBB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70165_t + 1.0, this.field_70163_u + 1.0, this.field_70161_v + 1.0).func_72314_b(32.0, 16.0, 32.0));
        return ents.size() <= 0 && super.func_70601_bi();
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    protected float func_70599_aP() {
        return 1.5f;
    }
    
    public void func_82196_d(final EntityLivingBase entitylivingbase, final float f) {
        if (this.field_70146_Z.nextFloat() > 0.1f) {
            final EntityEldritchOrb blast = new EntityEldritchOrb(this.field_70170_p, (EntityLivingBase)this);
            this.lastBlast = !this.lastBlast;
            this.field_70170_p.func_72960_a((Entity)this, (byte)(this.lastBlast ? 16 : 15));
            final int rr = this.lastBlast ? 90 : 180;
            final double xx = MathHelper.func_76134_b((this.field_70177_z + rr) % 360.0f / 180.0f * 3.1415927f) * 0.5f;
            final double yy = 0.057777777 * this.field_70131_O;
            final double zz = MathHelper.func_76126_a((this.field_70177_z + rr) % 360.0f / 180.0f * 3.1415927f) * 0.5f;
            blast.func_70107_b(blast.field_70165_t - xx, blast.field_70163_u, blast.field_70161_v - zz);
            final Vec3d v = this.func_70040_Z();
            blast.func_70186_c(v.field_72450_a, v.field_72448_b, v.field_72449_c, 1.0f, 2.0f);
            this.func_184185_a(SoundsTC.egattack, 2.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
            this.field_70170_p.func_72838_d((Entity)blast);
        }
        else if (this.func_70685_l((Entity)entitylivingbase)) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXSonic(this.func_145782_y()), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
            try {
                entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 400, 0));
            }
            catch (Exception ex) {}
            if (entitylivingbase instanceof EntityPlayer) {
                ThaumcraftApi.internalMethods.addWarpToPlayer((EntityPlayer)entitylivingbase, 1 + this.field_70170_p.field_73012_v.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);
            }
            this.func_184185_a(SoundsTC.egscreech, 3.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
        }
    }
    
    public boolean func_184191_r(final Entity el) {
        return el instanceof IEldritchMob;
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
}
