package thaumcraft.common.entities.monster.boss;

import thaumcraft.api.entities.*;
import net.minecraft.util.text.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraft.nbt.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.lib.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;

public class EntityEldritchWarden extends EntityThaumcraftBoss implements IRangedAttackMob, IEldritchMob
{
    protected final BossInfoServer bossInfo2;
    String[] titles;
    private static final DataParameter<Byte> NAME;
    boolean fieldFrenzy;
    int fieldFrenzyCounter;
    boolean lastBlast;
    public float armLiftL;
    public float armLiftR;
    
    public EntityEldritchWarden(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.bossInfo2 = new BossInfoServer((ITextComponent)new TextComponentString(""), BossInfo.Color.BLUE, BossInfo.Overlay.NOTCHED_10);
        this.titles = new String[] { "Aphoom-Zhah", "Basatan", "Chaugnar Faugn", "Mnomquah", "Nyogtha", "Oorn", "Shaikorth", "Rhan-Tegoth", "Rhogog", "Shudde M'ell", "Vulthoom", "Yag-Kosha", "Yibb-Tstll", "Zathog", "Zushakon" };
        this.fieldFrenzy = false;
        this.fieldFrenzyCounter = 0;
        this.lastBlast = false;
        this.armLiftL = 0.0f;
        this.armLiftR = 0.0f;
        this.func_70105_a(1.5f, 3.5f);
    }
    
    @Override
    public void func_184203_c(final EntityPlayerMP player) {
        super.func_184203_c(player);
        this.bossInfo2.func_186761_b(player);
    }
    
    @Override
    public void func_184178_b(final EntityPlayerMP player) {
        super.func_184178_b(player);
        this.bossInfo2.func_186760_a(player);
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AILongRangeAttack((IRangedAttackMob)this, 3.0, 1.0, 20, 40, 24.0f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.1, false));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityCultist.class, true));
    }
    
    @Override
    public void generateName() {
        final int t = (int)this.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
        if (t >= 0) {
            this.func_96094_a(String.format(I18n.func_74838_a("entity.Thaumcraft.EldritchWarden.name.custom"), this.getTitle(), ChampionModifier.mods[t].getModNameLocalized()));
        }
    }
    
    private String getTitle() {
        return this.titles[(byte)this.func_184212_Q().func_187225_a((DataParameter)EntityEldritchWarden.NAME)];
    }
    
    private void setTitle(final int title) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityEldritchWarden.NAME, (Object)(byte)title);
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(400.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.33);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0);
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityEldritchWarden.NAME, (Object)(byte)0);
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74774_a("title", (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityEldritchWarden.NAME));
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setTitle(nbt.func_74771_c("title"));
    }
    
    public int func_70658_aO() {
        return super.func_70658_aO() + 4;
    }
    
    @Override
    protected void func_70619_bc() {
        if (this.fieldFrenzyCounter == 0) {
            super.func_70619_bc();
        }
        final int bh = (int)(this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() * 0.66);
        if (this.field_70172_ad <= 0 && this.field_70173_aa % 25 == 0 && this.func_110139_bj() < bh) {
            this.func_110149_m(this.func_110139_bj() + 1.0f);
        }
        this.bossInfo2.func_186735_a(this.func_110139_bj() / bh);
    }
    
    @Override
    public void func_70071_h_() {
        if (this.getSpawnTimer() == 150) {
            this.field_70170_p.func_72960_a((Entity)this, (byte)18);
        }
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
            FXDispatcher.INSTANCE.wispFXEG(x, (float)(this.field_70163_u + 0.25 * this.field_70131_O), z, (Entity)this);
            if (this.spawnTimer > 0) {
                final float he = Math.max(1.0f, this.field_70131_O * ((150 - this.spawnTimer) / 150.0f));
                for (int a = 0; a < 33; ++a) {
                    FXDispatcher.INSTANCE.smokeSpiral(this.field_70165_t, this.func_174813_aQ().field_72338_b + he / 2.0f, this.field_70161_v, he, this.field_70146_Z.nextInt(360), MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) - 1, 2232623);
                }
            }
        }
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70163_u);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        for (int l = 0; l < 4; ++l) {
            i = MathHelper.func_76128_c(this.field_70165_t + (l % 2 * 2 - 1) * 0.25f);
            j = MathHelper.func_76128_c(this.field_70163_u);
            k = MathHelper.func_76128_c(this.field_70161_v + (l / 2 % 2 * 2 - 1) * 0.25f);
            final BlockPos bp = new BlockPos(i, j, k);
            if (this.field_70170_p.func_175623_d(bp) && BlocksTC.effectSap != null) {
                this.field_70170_p.func_175656_a(bp, BlocksTC.effectSap.func_176223_P());
            }
        }
        if (!this.field_70170_p.field_72995_K && this.fieldFrenzyCounter > 0) {
            if (this.fieldFrenzyCounter == 150) {
                this.teleportHome();
            }
            this.performFieldFrenzy();
        }
    }
    
    private void performFieldFrenzy() {
        if (this.fieldFrenzyCounter < 121 && this.fieldFrenzyCounter % 10 == 0) {
            this.field_70170_p.func_72960_a((Entity)this, (byte)17);
            final double radius = (150 - this.fieldFrenzyCounter) / 8.0;
            final int d = 1 + this.fieldFrenzyCounter / 8;
            final int i = MathHelper.func_76128_c(this.field_70165_t);
            final int j = MathHelper.func_76128_c(this.field_70163_u);
            final int k = MathHelper.func_76128_c(this.field_70161_v);
            for (int q = 0; q < 180 / d; ++q) {
                final double radians = Math.toRadians(q * 2 * d);
                final int deltaX = (int)(radius * Math.cos(radians));
                final int deltaZ = (int)(radius * Math.sin(radians));
                final BlockPos bp = new BlockPos(i + deltaX, j, k + deltaZ);
                if (this.field_70170_p.func_175623_d(bp) && this.field_70170_p.func_175677_d(bp.func_177977_b(), false)) {
                    this.field_70170_p.func_175656_a(bp, BlocksTC.effectSap.func_176223_P());
                    this.field_70170_p.func_175684_a(bp, BlocksTC.effectSap, 250 + this.field_70146_Z.nextInt(150));
                    if (this.field_70146_Z.nextFloat() < 0.3f) {
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockArc(bp, (Entity)this, 0.5f + this.field_70146_Z.nextFloat() * 0.2f, 0.0f, 0.5f + this.field_70146_Z.nextFloat() * 0.2f), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
                    }
                }
            }
            this.func_184185_a(SoundsTC.zap, 1.0f, 0.9f + this.field_70146_Z.nextFloat() * 0.1f);
        }
        --this.fieldFrenzyCounter;
    }
    
    protected void teleportHome() {
        final EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, (double)this.func_180486_cf().func_177958_n(), (double)this.func_180486_cf().func_177956_o(), (double)this.func_180486_cf().func_177952_p(), 0.0f);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return;
        }
        final double d3 = this.field_70165_t;
        final double d4 = this.field_70163_u;
        final double d5 = this.field_70161_v;
        this.field_70165_t = event.getTargetX();
        this.field_70163_u = event.getTargetY();
        this.field_70161_v = event.getTargetZ();
        boolean flag = false;
        int i = MathHelper.func_76128_c(this.field_70165_t);
        final int j = MathHelper.func_76128_c(this.field_70163_u);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        BlockPos bp = new BlockPos(i, j, k);
        if (this.field_70170_p.func_175667_e(bp)) {
            bp = new BlockPos(i, j, k);
            boolean flag2 = false;
            int tries = 20;
            while (!flag2 && tries > 0) {
                final IBlockState block = this.field_70170_p.func_180495_p(bp.func_177977_b());
                final IBlockState block2 = this.field_70170_p.func_180495_p(bp);
                if (block.func_185904_a().func_76230_c() && !block2.func_185904_a().func_76230_c()) {
                    flag2 = true;
                }
                else {
                    i = MathHelper.func_76128_c(this.field_70165_t) + this.field_70146_Z.nextInt(8) - this.field_70146_Z.nextInt(8);
                    k = MathHelper.func_76128_c(this.field_70161_v) + this.field_70146_Z.nextInt(8) - this.field_70146_Z.nextInt(8);
                    --tries;
                }
            }
            if (flag2) {
                this.func_70107_b(i + 0.5, j + 0.1, k + 0.5);
                if (this.field_70170_p.func_184144_a((Entity)this, this.func_174813_aQ()).isEmpty()) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            this.func_70107_b(d3, d4, d5);
            return;
        }
        final short short1 = 128;
        for (int l = 0; l < short1; ++l) {
            final double d6 = l / (short1 - 1.0);
            final float f = (this.field_70146_Z.nextFloat() - 0.5f) * 0.2f;
            final float f2 = (this.field_70146_Z.nextFloat() - 0.5f) * 0.2f;
            final float f3 = (this.field_70146_Z.nextFloat() - 0.5f) * 0.2f;
            final double d7 = d3 + (this.field_70165_t - d3) * d6 + (this.field_70146_Z.nextDouble() - 0.5) * this.field_70130_N * 2.0;
            final double d8 = d4 + (this.field_70163_u - d4) * d6 + this.field_70146_Z.nextDouble() * this.field_70131_O;
            final double d9 = d5 + (this.field_70161_v - d5) * d6 + (this.field_70146_Z.nextDouble() - 0.5) * this.field_70130_N * 2.0;
            this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, d7, d8, d9, (double)f, (double)f2, (double)f3, new int[0]);
        }
        this.func_184185_a(SoundEvents.field_187534_aX, 1.0f, 1.0f);
    }
    
    @Override
    public boolean func_180431_b(final DamageSource ds) {
        return this.fieldFrenzyCounter > 0 || super.func_180431_b(ds);
    }
    
    @Override
    public boolean func_70097_a(final DamageSource source, final float damage) {
        if (this.func_180431_b(source) || source == DamageSource.field_76369_e || source == DamageSource.field_82727_n) {
            return false;
        }
        final boolean aef = super.func_70097_a(source, damage);
        if (!this.field_70170_p.field_72995_K && aef && !this.fieldFrenzy && this.func_110139_bj() <= 0.0f) {
            this.fieldFrenzy = true;
            this.fieldFrenzyCounter = 150;
        }
        return aef;
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        this.spawnTimer = 150;
        this.setTitle(this.field_70146_Z.nextInt(this.titles.length));
        this.func_110149_m((float)(this.func_110139_bj() + this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() * 0.66));
        return super.func_180482_a(diff, data);
    }
    
    public float func_70047_e() {
        return 3.1f;
    }
    
    public void func_82196_d(final EntityLivingBase entitylivingbase, final float f) {
        if (this.field_70146_Z.nextFloat() > 0.2f) {
            final EntityEldritchOrb blast = new EntityEldritchOrb(this.field_70170_p, (EntityLivingBase)this);
            this.lastBlast = !this.lastBlast;
            this.field_70170_p.func_72960_a((Entity)this, (byte)(this.lastBlast ? 16 : 15));
            final int rr = this.lastBlast ? 90 : 180;
            final double xx = MathHelper.func_76134_b((this.field_70177_z + rr) % 360.0f / 180.0f * 3.1415927f) * 0.5f;
            final double yy = 0.13;
            final double zz = MathHelper.func_76126_a((this.field_70177_z + rr) % 360.0f / 180.0f * 3.1415927f) * 0.5f;
            blast.func_70107_b(blast.field_70165_t - xx, blast.field_70163_u - yy, blast.field_70161_v - zz);
            final double d0 = entitylivingbase.field_70165_t + entitylivingbase.field_70159_w - this.field_70165_t;
            final double d2 = entitylivingbase.field_70163_u - this.field_70163_u - entitylivingbase.field_70131_O / 2.0f;
            final double d3 = entitylivingbase.field_70161_v + entitylivingbase.field_70179_y - this.field_70161_v;
            blast.func_70186_c(d0, d2, d3, 1.0f, 2.0f);
            this.func_184185_a(SoundsTC.egattack, 2.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
            this.field_70170_p.func_72838_d((Entity)blast);
        }
        else if (this.func_70685_l((Entity)entitylivingbase)) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXSonic(this.func_145782_y()), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
            entitylivingbase.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927f / 180.0f) * 1.5f), 0.1, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927f / 180.0f) * 1.5f));
            try {
                entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 400, 0));
                entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_76437_t, 400, 0));
            }
            catch (Exception ex) {}
            if (entitylivingbase instanceof EntityPlayer) {
                ThaumcraftApi.internalMethods.addWarpToPlayer((EntityPlayer)entitylivingbase, 3 + this.field_70170_p.field_73012_v.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);
            }
            this.func_184185_a(SoundsTC.egscreech, 4.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
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
        else if (p_70103_1_ == 18) {
            this.spawnTimer = 150;
        }
        else {
            super.func_70103_a(p_70103_1_);
        }
    }
    
    public boolean func_70686_a(final Class clazz) {
        return clazz != EntityEldritchGuardian.class && super.func_70686_a(clazz);
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
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        NAME = EntityDataManager.func_187226_a((Class)EntityEldritchWarden.class, DataSerializers.field_187191_a);
    }
}
