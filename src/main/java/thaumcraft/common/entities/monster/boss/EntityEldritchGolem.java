package thaumcraft.common.entities.monster.boss;

import thaumcraft.api.entities.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import thaumcraft.api.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import thaumcraft.common.blocks.world.*;
import net.minecraft.block.state.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.fx.*;
import net.minecraft.network.datasync.*;

public class EntityEldritchGolem extends EntityThaumcraftBoss implements IEldritchMob, IRangedAttackMob
{
    private static final DataParameter<Boolean> HEADLESS;
    int beamCharge;
    boolean chargingBeam;
    int arcing;
    int ax;
    int ay;
    int az;
    private int attackTimer;
    
    public EntityEldritchGolem(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.beamCharge = 0;
        this.chargingBeam = false;
        this.arcing = 0;
        this.ax = 0;
        this.ay = 0;
        this.az = 0;
        this.func_70105_a(1.75f, 3.5f);
        this.field_70178_ae = true;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.1, false));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    @Override
    public void generateName() {
        final int t = (int)this.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
        if (t >= 0) {
            this.func_96094_a(String.format(I18n.func_74838_a("entity.Thaumcraft.EldritchGolem.name.custom"), ChampionModifier.mods[t].getModNameLocalized()));
        }
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityEldritchGolem.HEADLESS, (Object)false);
    }
    
    public boolean isHeadless() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityEldritchGolem.HEADLESS);
    }
    
    public void setHeadless(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityEldritchGolem.HEADLESS, (Object)par1);
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74757_a("headless", this.isHeadless());
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setHeadless(nbt.func_74767_n("headless"));
        if (this.isHeadless()) {
            this.makeHeadless();
        }
    }
    
    public float func_70047_e() {
        return this.isHeadless() ? 3.33f : 3.0f;
    }
    
    public int func_70658_aO() {
        return super.func_70658_aO() + 6;
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(400.0);
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187602_cF;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187599_cE;
    }
    
    protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.func_184185_a(SoundEvents.field_187605_cG, 1.0f, 1.0f);
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        this.spawnTimer = 100;
        return super.func_180482_a(diff, data);
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 2.500000277905201E-7 && this.field_70146_Z.nextInt(5) == 0) {
            final IBlockState bs = this.field_70170_p.func_180495_p(this.func_180425_c());
            if (bs.func_185904_a() != Material.field_151579_a) {
                this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + (this.field_70146_Z.nextFloat() - 0.5) * this.field_70130_N, this.func_174813_aQ().field_72338_b + 0.1, this.field_70161_v + (this.field_70146_Z.nextFloat() - 0.5) * this.field_70130_N, 4.0 * (this.field_70146_Z.nextFloat() - 0.5), 0.5, (this.field_70146_Z.nextFloat() - 0.5) * 4.0, new int[] { Block.func_176210_f(bs) });
            }
            if (!this.field_70170_p.field_72995_K && bs.func_177230_c() instanceof BlockLoot) {
                this.field_70170_p.func_175655_b(this.func_180425_c(), true);
            }
        }
        if (!this.field_70170_p.field_72995_K) {
            final IBlockState bs = this.field_70170_p.func_180495_p(this.func_180425_c());
            final float h = bs.func_185887_b(this.field_70170_p, this.func_180425_c());
            if (h >= 0.0f && h <= 0.15f) {
                this.field_70170_p.func_175655_b(this.func_180425_c(), true);
            }
        }
    }
    
    @Override
    public boolean func_70097_a(final DamageSource source, final float damage) {
        if (!this.field_70170_p.field_72995_K && damage > this.func_110143_aJ() && !this.isHeadless()) {
            this.setHeadless(true);
            this.spawnTimer = 100;
            final double xx = MathHelper.func_76134_b(this.field_70177_z % 360.0f / 180.0f * 3.1415927f) * 0.75f;
            final double zz = MathHelper.func_76126_a(this.field_70177_z % 360.0f / 180.0f * 3.1415927f) * 0.75f;
            this.field_70170_p.func_72876_a((Entity)this, this.field_70165_t + xx, this.field_70163_u + this.func_70047_e(), this.field_70161_v + zz, 2.0f, false);
            this.makeHeadless();
            return false;
        }
        return super.func_70097_a(source, damage);
    }
    
    void makeHeadless() {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AILongRangeAttack((IRangedAttackMob)this, 3.0, 1.0, 5, 5, 24.0f));
    }
    
    public boolean func_70652_k(final Entity target) {
        if (this.attackTimer > 0) {
            return false;
        }
        this.attackTimer = 10;
        this.field_70170_p.func_72960_a((Entity)this, (byte)4);
        final boolean flag = target.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * 0.75f);
        if (flag) {
            target.field_70181_x += 0.2000000059604645;
            if (this.isHeadless()) {
                target.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927f / 180.0f) * 1.5f), 0.1, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927f / 180.0f) * 1.5f));
            }
        }
        return flag;
    }
    
    public void func_82196_d(final EntityLivingBase entitylivingbase, final float f) {
        if (this.func_70685_l((Entity)entitylivingbase) && !this.chargingBeam && this.beamCharge > 0) {
            this.beamCharge -= 15 + this.field_70146_Z.nextInt(5);
            this.func_70671_ap().func_75650_a(entitylivingbase.field_70165_t, entitylivingbase.func_174813_aQ().field_72338_b + entitylivingbase.field_70131_O / 2.0f, entitylivingbase.field_70161_v, 30.0f, 30.0f);
            final Vec3d v = this.func_70676_i(1.0f);
            final EntityGolemOrb entityGolemOrb;
            final EntityGolemOrb blast = entityGolemOrb = new EntityGolemOrb(this.field_70170_p, (EntityLivingBase)this, entitylivingbase, false);
            entityGolemOrb.field_70165_t += v.field_72450_a;
            final EntityGolemOrb entityGolemOrb2 = blast;
            entityGolemOrb2.field_70161_v += v.field_72449_c;
            blast.func_70107_b(blast.field_70165_t, blast.field_70163_u, blast.field_70161_v);
            final double d0 = entitylivingbase.field_70165_t + entitylivingbase.field_70159_w - this.field_70165_t;
            final double d2 = entitylivingbase.field_70163_u - this.field_70163_u - entitylivingbase.field_70131_O / 2.0f;
            final double d3 = entitylivingbase.field_70161_v + entitylivingbase.field_70179_y - this.field_70161_v;
            blast.func_70186_c(d0, d2, d3, 0.66f, 5.0f);
            this.func_184185_a(SoundsTC.egattack, 1.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
            this.field_70170_p.func_72838_d((Entity)blast);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte p_70103_1_) {
        if (p_70103_1_ == 4) {
            this.attackTimer = 10;
            this.func_184185_a(SoundEvents.field_187596_cD, 1.0f, 1.0f);
        }
        else if (p_70103_1_ == 18) {
            this.spawnTimer = 150;
        }
        else if (p_70103_1_ == 19) {
            if (this.arcing == 0) {
                final float radius = 2.0f + this.field_70146_Z.nextFloat() * 2.0f;
                final double radians = Math.toRadians(this.field_70146_Z.nextInt(360));
                final double deltaX = radius * Math.cos(radians);
                final double deltaZ = radius * Math.sin(radians);
                final int bx = MathHelper.func_76128_c(this.field_70165_t + deltaX);
                int by = MathHelper.func_76128_c(this.field_70163_u);
                final int bz = MathHelper.func_76128_c(this.field_70161_v + deltaZ);
                final BlockPos bp = new BlockPos(bx, by, bz);
                for (int c = 0; c < 5 && this.field_70170_p.func_175623_d(bp); ++c, --by) {}
                if (this.field_70170_p.func_175623_d(bp.func_177984_a()) && !this.field_70170_p.func_175623_d(bp)) {
                    this.ax = bx;
                    this.ay = by;
                    this.az = bz;
                    this.arcing = 8 + this.field_70146_Z.nextInt(5);
                    this.func_184185_a(SoundsTC.jacobs, 0.8f, 1.0f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.05f);
                }
            }
        }
        else {
            super.func_70103_a(p_70103_1_);
        }
    }
    
    @Override
    public void func_70071_h_() {
        if (this.getSpawnTimer() == 150) {
            this.field_70170_p.func_72960_a((Entity)this, (byte)18);
        }
        if (this.getSpawnTimer() > 0) {
            this.func_70691_i(2.0f);
        }
        super.func_70071_h_();
        if (this.field_70170_p.field_72995_K) {
            if (this.isHeadless()) {
                this.field_70125_A = 0.0f;
                final float f1 = MathHelper.func_76134_b(-this.field_70761_aq * 0.017453292f - 3.1415927f);
                final float f2 = MathHelper.func_76126_a(-this.field_70761_aq * 0.017453292f - 3.1415927f);
                final float f3 = -MathHelper.func_76134_b(-this.field_70125_A * 0.017453292f);
                final float f4 = MathHelper.func_76126_a(-this.field_70125_A * 0.017453292f);
                final Vec3d v = new Vec3d((double)(f2 * f3), (double)f4, (double)(f1 * f3));
                if (this.field_70146_Z.nextInt(20) == 0) {
                    final float a = (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) / 3.0f;
                    final float b = (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) / 3.0f;
                    FXDispatcher.INSTANCE.spark((float)(this.field_70165_t + v.field_72450_a + a), (float)this.field_70163_u + this.func_70047_e() - 0.75f, (float)(this.field_70161_v + v.field_72449_c + b), 3.0f, 0.65f + this.field_70146_Z.nextFloat() * 0.1f, 1.0f, 1.0f, 0.8f);
                }
                FXDispatcher.INSTANCE.drawVentParticles((float)this.field_70165_t + v.field_72450_a * 0.4, (float)this.field_70163_u + this.func_70047_e() - 1.25f, (float)this.field_70161_v + v.field_72449_c * 0.4, 0.0, 0.001, 0.0, 5592405, 4.0f);
                if (this.arcing > 0) {
                    FXDispatcher.INSTANCE.arcLightning(this.field_70165_t, this.field_70163_u + this.field_70131_O / 2.0f, this.field_70161_v, this.ax + 0.5, this.ay + 1, this.az + 0.5, 0.65f + this.field_70146_Z.nextFloat() * 0.1f, 1.0f, 1.0f, 1.0f - this.arcing / 10.0f);
                    --this.arcing;
                }
            }
        }
        else {
            if (this.isHeadless() && this.beamCharge <= 0) {
                this.chargingBeam = true;
            }
            if (this.isHeadless() && this.chargingBeam) {
                ++this.beamCharge;
                this.field_70170_p.func_72960_a((Entity)this, (byte)19);
                if (this.beamCharge == 150) {
                    this.chargingBeam = false;
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        HEADLESS = EntityDataManager.func_187226_a((Class)EntityEldritchGolem.class, DataSerializers.field_187198_h);
    }
}
