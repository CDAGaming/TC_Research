package thaumcraft.common.entities.monster.boss;

import net.minecraft.entity.player.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.ai.*;
import thaumcraft.api.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import thaumcraft.api.items.*;
import net.minecraft.world.*;
import net.minecraft.enchantment.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.lib.*;
import thaumcraft.client.fx.*;
import net.minecraft.network.datasync.*;

public class EntityCultistLeader extends EntityThaumcraftBoss implements IRangedAttackMob
{
    private static final DataParameter<Byte> NAME;
    String[] titles;
    
    public EntityCultistLeader(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.titles = new String[] { "Alberic", "Anselm", "Bastian", "Beturian", "Chabier", "Chorache", "Chuse", "Dodorol", "Ebardo", "Ferrando", "Fertus", "Guillen", "Larpe", "Obano", "Zelipe" };
        this.func_70105_a(0.75f, 2.25f);
        this.field_70728_aV = 40;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AILongRangeAttack((IRangedAttackMob)this, 16.0, 1.0, 30, 40, 24.0f));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.1, false));
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
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.32);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(150.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0);
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityCultistLeader.NAME, (Object)(byte)0);
    }
    
    @Override
    public void generateName() {
        final int t = (int)this.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
        if (t >= 0) {
            this.func_96094_a(String.format(I18n.func_74838_a("entity.Thaumcraft.CultistLeader.name.custom"), this.getTitle(), ChampionModifier.mods[t].getModNameLocalized()));
        }
    }
    
    private String getTitle() {
        return this.titles[(byte)this.func_184212_Q().func_187225_a((DataParameter)EntityCultistLeader.NAME)];
    }
    
    private void setTitle(final int title) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityCultistLeader.NAME, (Object)(byte)title);
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74774_a("title", (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityCultistLeader.NAME));
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setTitle(nbt.func_74771_c("title"));
    }
    
    protected void func_180481_a(final DifficultyInstance difficulty) {
        this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ItemsTC.crimsonPraetorHelm));
        this.func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(ItemsTC.crimsonPraetorChest));
        this.func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(ItemsTC.crimsonPraetorLegs));
        this.func_184201_a(EntityEquipmentSlot.FEET, new ItemStack(ItemsTC.crimsonBoots));
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY) {
            this.func_184611_a(this.func_184600_cs(), new ItemStack(ItemsTC.voidSword));
        }
        else {
            this.func_184611_a(this.func_184600_cs(), new ItemStack(ItemsTC.crimsonBlade));
        }
    }
    
    @Override
    protected void func_180483_b(final DifficultyInstance diff) {
        final float f = diff.func_180170_c();
        if (this.func_184614_ca() != null && this.field_70146_Z.nextFloat() < 0.5f * f) {
            EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_184614_ca(), (int)(7.0f + f * this.field_70146_Z.nextInt(22)), false);
        }
    }
    
    @Override
    public boolean func_184191_r(final Entity el) {
        return el instanceof EntityCultist || el instanceof EntityCultistLeader;
    }
    
    public boolean func_70686_a(final Class clazz) {
        return clazz != EntityCultistCleric.class && clazz != EntityCultistLeader.class && clazz != EntityCultistKnight.class && super.func_70686_a(clazz);
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    @Override
    protected void func_70628_a(final boolean flag, final int i) {
        this.func_70099_a(new ItemStack(ItemsTC.lootBag, 1, 2), 1.5f);
    }
    
    @Override
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        this.func_180481_a(diff);
        this.func_180483_b(diff);
        this.setTitle(this.field_70146_Z.nextInt(this.titles.length));
        return super.func_180482_a(diff, data);
    }
    
    @Override
    protected void func_70619_bc() {
        super.func_70619_bc();
        final List<Entity> list = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends Entity>)EntityCultist.class, 8.0);
        for (final Entity e : list) {
            try {
                if (!(e instanceof EntityCultist) || ((EntityCultist)e).func_70644_a(MobEffects.field_76428_l)) {
                    continue;
                }
                ((EntityCultist)e).func_70690_d(new PotionEffect(MobEffects.field_76428_l, 60, 1));
            }
            catch (Exception ex) {}
        }
    }
    
    public void func_82196_d(final EntityLivingBase entitylivingbase, final float f) {
        if (this.func_70685_l((Entity)entitylivingbase)) {
            this.func_184609_a(this.func_184600_cs());
            this.func_70671_ap().func_75650_a(entitylivingbase.field_70165_t, entitylivingbase.func_174813_aQ().field_72338_b + entitylivingbase.field_70131_O / 2.0f, entitylivingbase.field_70161_v, 30.0f, 30.0f);
            final EntityGolemOrb entityGolemOrb;
            final EntityGolemOrb blast = entityGolemOrb = new EntityGolemOrb(this.field_70170_p, (EntityLivingBase)this, entitylivingbase, true);
            entityGolemOrb.field_70165_t += blast.field_70159_w / 2.0;
            final EntityGolemOrb entityGolemOrb2 = blast;
            entityGolemOrb2.field_70161_v += blast.field_70179_y / 2.0;
            blast.func_70107_b(blast.field_70165_t, blast.field_70163_u, blast.field_70161_v);
            final double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
            final double d2 = entitylivingbase.func_174813_aQ().field_72338_b + entitylivingbase.field_70131_O / 2.0f - (this.field_70163_u + this.field_70131_O / 2.0f);
            final double d3 = entitylivingbase.field_70161_v - this.field_70161_v;
            blast.func_70186_c(d0, d2 + 2.0, d3, 0.66f, 3.0f);
            this.func_184185_a(SoundsTC.egattack, 1.0f, 1.0f + this.field_70146_Z.nextFloat() * 0.1f);
            this.field_70170_p.func_72838_d((Entity)blast);
        }
    }
    
    public void func_70656_aK() {
        if (this.field_70170_p.field_72995_K) {
            for (int i = 0; i < 20; ++i) {
                final double d0 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d2 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d3 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d4 = 2.0;
                FXDispatcher.INSTANCE.cultistSpawn(this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N + d0 * d4, this.field_70163_u + this.field_70146_Z.nextFloat() * this.field_70131_O + d2 * d4, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N + d3 * d4, d0, d2, d3);
            }
        }
        else {
            this.field_70170_p.func_72960_a((Entity)this, (byte)20);
        }
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        NAME = EntityDataManager.func_187226_a((Class)EntityCultistLeader.class, DataSerializers.field_187191_a);
    }
}
