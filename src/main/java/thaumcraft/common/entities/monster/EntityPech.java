package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.ai.pech.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import thaumcraft.common.items.casters.foci.*;
import thaumcraft.api.casters.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;
import thaumcraft.common.config.*;
import thaumcraft.common.world.biomes.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.api.aspects.*;
import net.minecraft.network.datasync.*;
import net.minecraft.world.storage.loot.*;
import java.util.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

public class EntityPech extends EntityMob implements IRangedAttackMob
{
    public NonNullList<ItemStack> loot;
    public boolean trading;
    private final EntityAIAttackRanged aiArrowAttack;
    private final EntityAIAttackRanged aiBlastAttack;
    private final EntityAIAttackMelee aiMeleeAttack;
    private final EntityAIAvoidEntity aiAvoidPlayer;
    private static final DataParameter<Byte> TYPE;
    private static final DataParameter<Integer> ANGER;
    private static final DataParameter<Boolean> TAMED;
    public static final ResourceLocation LOOT;
    public float mumble;
    int chargecount;
    static HashMap<Integer, Integer> valuedItems;
    public static HashMap<Integer, ArrayList<List>> tradeInventory;
    
    public String func_70005_c_() {
        if (this.func_145818_k_()) {
            return this.func_95999_t();
        }
        switch (this.getPechType()) {
            case 0: {
                return I18n.func_74838_a("entity.Thaumcraft.Pech.name");
            }
            case 1: {
                return I18n.func_74838_a("entity.Thaumcraft.Pech.1.name");
            }
            case 2: {
                return I18n.func_74838_a("entity.Thaumcraft.Pech.2.name");
            }
            default: {
                return I18n.func_74838_a("entity.Thaumcraft.Pech.name");
            }
        }
    }
    
    public EntityPech(final World world) {
        super(world);
        this.loot = (NonNullList<ItemStack>)NonNullList.func_191197_a(9, (Object)ItemStack.field_190927_a);
        this.trading = false;
        this.aiArrowAttack = new EntityAIAttackRanged((IRangedAttackMob)this, 0.6, 20, 50, 15.0f);
        this.aiBlastAttack = new EntityAIAttackRanged((IRangedAttackMob)this, 0.6, 20, 50, 15.0f);
        this.aiMeleeAttack = new EntityAIAttackMelee((EntityCreature)this, 0.6, false);
        this.aiAvoidPlayer = new EntityAIAvoidEntity((EntityCreature)this, (Class)EntityPlayer.class, 8.0f, 0.5, 0.6);
        this.mumble = 0.0f;
        this.chargecount = 0;
        this.func_70105_a(0.6f, 1.8f);
        ((PathNavigateGround)this.func_70661_as()).func_179693_d(false);
        this.func_184642_a(EntityEquipmentSlot.MAINHAND, 0.2f);
        this.func_184642_a(EntityEquipmentSlot.OFFHAND, 0.2f);
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new AIPechTradePlayer(this));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIPechItemEntityGoto(this));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.5));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(9, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6));
        this.field_70714_bg.func_75776_a(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 3.0f, 1.0f));
        this.field_70714_bg.func_75776_a(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f));
        this.field_70714_bg.func_75776_a(11, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new AINearestAttackableTargetPech((EntityCreature)this, EntityPlayer.class, true));
    }
    
    public void setCombatTask() {
        if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
            this.field_70714_bg.func_85156_a((EntityAIBase)this.aiMeleeAttack);
            this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
            this.field_70714_bg.func_85156_a((EntityAIBase)this.aiBlastAttack);
            final ItemStack itemstack = this.func_184614_ca();
            if (itemstack.func_77973_b() == Items.field_151031_f) {
                this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiArrowAttack);
            }
            else if (itemstack.func_77973_b() == ItemsTC.pechWand) {
                this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiBlastAttack);
            }
            else {
                this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiMeleeAttack);
            }
            if (this.isTamed()) {
                this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAvoidPlayer);
            }
            else {
                this.field_70714_bg.func_75776_a(4, (EntityAIBase)this.aiAvoidPlayer);
            }
        }
    }
    
    public void func_82196_d(final EntityLivingBase target, final float f) {
        if (this.getPechType() == 2) {
            final EntityTippedArrow entityarrow = new EntityTippedArrow(this.field_70170_p, (EntityLivingBase)this);
            if (this.field_70170_p.field_73012_v.nextFloat() < 0.2) {
                final ItemStack itemstack = new ItemStack(Items.field_185167_i);
                PotionUtils.func_185184_a(itemstack, (Collection)Collections.singletonList(new PotionEffect(MobEffects.field_76436_u, 40)));
                entityarrow.func_184555_a(itemstack);
            }
            final double d0 = target.field_70165_t - this.field_70165_t;
            final double d2 = target.func_174813_aQ().field_72338_b + target.field_70131_O / 3.0f - entityarrow.field_70163_u;
            final double d3 = target.field_70161_v - this.field_70161_v;
            final double d4 = MathHelper.func_76133_a(d0 * d0 + d3 * d3);
            entityarrow.func_70186_c(d0, d2 + d4 * 0.20000000298023224, d3, 1.6f, (float)(14 - this.field_70170_p.func_175659_aa().func_151525_a() * 4));
            final int i = EnchantmentHelper.func_185284_a(Enchantments.field_185309_u, (EntityLivingBase)this);
            final int j = EnchantmentHelper.func_185284_a(Enchantments.field_185310_v, (EntityLivingBase)this);
            entityarrow.func_70239_b(f * 2.0f + this.field_70146_Z.nextGaussian() * 0.25 + this.field_70170_p.func_175659_aa().func_151525_a() * 0.11f);
            if (i > 0) {
                entityarrow.func_70239_b(entityarrow.func_70242_d() + i * 0.5 + 0.5);
            }
            if (j > 0) {
                entityarrow.func_70240_a(j);
            }
            if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, (EntityLivingBase)this) > 0) {
                entityarrow.func_70015_d(100);
            }
            this.func_184185_a(SoundEvents.field_187737_v, 1.0f, 1.0f / (this.func_70681_au().nextFloat() * 0.4f + 0.8f));
            this.field_70170_p.func_72838_d((Entity)entityarrow);
        }
        else if (this.getPechType() == 1) {
            final FocusPackage p = new FocusPackage((EntityLivingBase)this);
            final FocusMediumRoot root = new FocusMediumRoot();
            final double off = this.func_70032_d((Entity)target) / 6.0f;
            root.setupFromCasterToTarget((EntityLivingBase)this, (Entity)target, off);
            p.addNode(root);
            final FocusMediumProjectile fp = new FocusMediumProjectile();
            fp.initialize();
            fp.getSetting("speed").setValue(2);
            p.addNode(fp);
            p.addNode(this.field_70146_Z.nextBoolean() ? new FocusEffectCurse() : (this.field_70146_Z.nextBoolean() ? new FocusEffectFlux() : (this.field_70146_Z.nextBoolean() ? new FocusEffectEarth() : (this.field_70146_Z.nextBoolean() ? new FocusEffectAir() : new FocusEffectFire()))));
            FocusEngine.castFocusPackage((EntityLivingBase)this, p, true);
            this.func_184609_a(this.func_184600_cs());
        }
    }
    
    public void func_184201_a(final EntityEquipmentSlot slotIn, @Nullable final ItemStack stack) {
        super.func_184201_a(slotIn, stack);
        if (!this.field_70170_p.field_72995_K && slotIn == EntityEquipmentSlot.MAINHAND) {
            this.setCombatTask();
        }
    }
    
    protected void func_180481_a(final DifficultyInstance difficulty) {
        super.func_180481_a(difficulty);
        switch (this.field_70146_Z.nextInt(20)) {
            case 0:
            case 12: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(ItemsTC.pechWand));
                break;
            }
            case 1: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151052_q));
                break;
            }
            case 3: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151049_t));
                break;
            }
            case 5: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151040_l));
                break;
            }
            case 6: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151036_c));
                break;
            }
            case 7: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack((Item)Items.field_151112_aM));
                break;
            }
            case 8: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151050_s));
                break;
            }
            case 9: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151035_b));
                break;
            }
            case 2:
            case 4:
            case 10:
            case 11:
            case 13: {
                this.func_184611_a(this.func_184600_cs(), new ItemStack((Item)Items.field_151031_f));
                break;
            }
        }
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        this.func_180481_a(diff);
        final ItemStack itemstack = this.func_184586_b(this.func_184600_cs());
        if (itemstack.func_77973_b() == ItemsTC.pechWand) {
            this.setPechType(1);
            this.func_184642_a((this.func_184600_cs() == EnumHand.MAIN_HAND) ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, 0.1f);
        }
        else if (!itemstack.func_190926_b()) {
            if (itemstack.func_77973_b() == Items.field_151031_f) {
                this.setPechType(2);
            }
            this.func_180483_b(diff);
        }
        final float f = diff.func_180170_c();
        this.func_98053_h(this.field_70146_Z.nextFloat() < 0.75f * f);
        this.setCombatTask();
        return super.func_180482_a(diff, data);
    }
    
    public boolean func_70601_bi() {
        final Biome biome = this.field_70170_p.func_180494_b(new BlockPos((Entity)this));
        boolean magicBiome = false;
        if (biome != null) {
            magicBiome = BiomeDictionary.hasType(biome, BiomeDictionary.Type.MAGICAL);
        }
        int count = 0;
        try {
            final List l = this.field_70170_p.func_72872_a((Class)EntityPech.class, this.func_174813_aQ().func_72314_b(16.0, 16.0, 16.0));
            if (l != null) {
                count = l.size();
            }
        }
        catch (Exception ex) {}
        if (this.field_70170_p.field_73011_w.getDimension() != ModConfig.CONFIG_WORLD.overworldDim && biome != BiomeHandler.MAGICAL_FOREST && biome != BiomeHandler.EERIE) {
            magicBiome = false;
        }
        return count < 4 && magicBiome && super.func_70601_bi();
    }
    
    public float func_70047_e() {
        return this.field_70131_O * 0.66f;
    }
    
    public void func_70636_d() {
        super.func_70636_d();
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityPech.TYPE, (Object)(byte)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityPech.ANGER, (Object)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityPech.TAMED, (Object)false);
    }
    
    public int getPechType() {
        return (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityPech.TYPE);
    }
    
    public int getAnger() {
        return (int)this.func_184212_Q().func_187225_a((DataParameter)EntityPech.ANGER);
    }
    
    public boolean isTamed() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityPech.TAMED);
    }
    
    public void setPechType(final int par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityPech.TYPE, (Object)(byte)par1);
    }
    
    public void setAnger(final int par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityPech.ANGER, (Object)par1);
    }
    
    public void setTamed(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityPech.TAMED, (Object)par1);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74774_a("PechType", (byte)this.getPechType());
        nbt.func_74777_a("Anger", (short)this.getAnger());
        nbt.func_74757_a("Tamed", this.isTamed());
        ItemStackHelper.func_191282_a(nbt, (NonNullList)this.loot);
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        if (nbt.func_74764_b("PechType")) {
            final byte b0 = nbt.func_74771_c("PechType");
            this.setPechType(b0);
        }
        this.setAnger(nbt.func_74765_d("Anger"));
        this.setTamed(nbt.func_74767_n("Tamed"));
        ItemStackHelper.func_191283_b(nbt, (NonNullList)(this.loot = (NonNullList<ItemStack>)NonNullList.func_191197_a(9, (Object)ItemStack.field_190927_a)));
        this.setCombatTask();
    }
    
    protected boolean func_70692_ba() {
        try {
            if (this.loot == null) {
                return true;
            }
            int q = 0;
            for (final ItemStack is : this.loot) {
                if (is != null && is.func_190916_E() > 0) {
                    ++q;
                }
            }
            return q < 5;
        }
        catch (Exception e) {
            return true;
        }
    }
    
    public boolean func_184652_a(final EntityPlayer player) {
        return false;
    }
    
    protected ResourceLocation func_184647_J() {
        return EntityPech.LOOT;
    }
    
    protected void func_184610_a(final boolean wasRecentlyHit, final int lootingModifier, final DamageSource source) {
        for (int a = 0; a < this.loot.size(); ++a) {
            if (!((ItemStack)this.loot.get(a)).func_190926_b() && this.field_70170_p.field_73012_v.nextFloat() < 0.33f) {
                this.func_70099_a(((ItemStack)this.loot.get(a)).func_77946_l(), 1.5f);
            }
        }
        super.func_184610_a(wasRecentlyHit, lootingModifier, source);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            this.mumble = 3.1415927f;
        }
        else if (par1 == 17) {
            this.mumble = 6.2831855f;
        }
        else if (par1 == 18) {
            for (int i = 0; i < 5; ++i) {
                final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
                this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_HAPPY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + 0.5 + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d0, d2, d3, new int[0]);
            }
        }
        if (par1 == 19) {
            for (int i = 0; i < 5; ++i) {
                final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
                final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
                this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_ANGRY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + 0.5 + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d0, d2, d3, new int[0]);
            }
            this.mumble = 6.2831855f;
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public void func_70642_aH() {
        if (!this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(3) == 0) {
                final List list = this.field_70170_p.func_72839_b((Entity)this, this.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity1 = list.get(i);
                    if (entity1 instanceof EntityPech) {
                        this.field_70170_p.func_72960_a((Entity)this, (byte)17);
                        this.func_184185_a(SoundsTC.pech_trade, this.func_70599_aP(), this.func_70647_i());
                        return;
                    }
                }
            }
            this.field_70170_p.func_72960_a((Entity)this, (byte)16);
        }
        super.func_70642_aH();
    }
    
    public int func_70627_aG() {
        return 120;
    }
    
    protected float func_70599_aP() {
        return 0.4f;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.pech_idle;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.pech_hit;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.pech_death;
    }
    
    private void becomeAngryAt(final Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_184812_l_()) {
            return;
        }
        if (this.getAnger() <= 0) {
            this.field_70170_p.func_72960_a((Entity)this, (byte)19);
            this.func_184185_a(SoundsTC.pech_charge, this.func_70599_aP(), this.func_70647_i());
        }
        this.func_70624_b((EntityLivingBase)entity);
        this.setAnger(400 + this.field_70146_Z.nextInt(400));
        this.setTamed(false);
        this.setCombatTask();
    }
    
    public int func_70658_aO() {
        int i = super.func_70658_aO() + 2;
        if (i > 20) {
            i = 20;
        }
        return i;
    }
    
    public boolean func_70097_a(final DamageSource damSource, final float par2) {
        if (this.func_180431_b(damSource)) {
            return false;
        }
        final Entity entity = damSource.func_76346_g();
        if (entity instanceof EntityPlayer) {
            final List list = this.field_70170_p.func_72839_b((Entity)this, this.func_174813_aQ().func_72314_b(32.0, 16.0, 32.0));
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity2 = list.get(i);
                if (entity2 instanceof EntityPech) {
                    final EntityPech entitypech = (EntityPech)entity2;
                    entitypech.becomeAngryAt(entity);
                }
            }
            this.becomeAngryAt(entity);
        }
        return super.func_70097_a(damSource, par2);
    }
    
    public void func_70071_h_() {
        if (this.mumble > 0.0f) {
            this.mumble *= 0.75f;
        }
        if (this.getAnger() > 0) {
            this.setAnger(this.getAnger() - 1);
        }
        if (this.getAnger() > 0 && this.func_70638_az() != null) {
            if (this.chargecount > 0) {
                --this.chargecount;
            }
            if (this.chargecount == 0) {
                this.chargecount = 100;
                this.func_184185_a(SoundsTC.pech_charge, this.func_70599_aP(), this.func_70647_i());
            }
            this.field_70170_p.func_72960_a((Entity)this, (byte)17);
        }
        if (this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(15) == 0 && this.getAnger() > 0) {
            final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_ANGRY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + 0.5 + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d0, d2, d3, new int[0]);
        }
        if (this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(25) == 0 && this.isTamed()) {
            final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_HAPPY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + 0.5 + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d0, d2, d3, new int[0]);
        }
        super.func_70071_h_();
    }
    
    public void func_70619_bc() {
        super.func_70619_bc();
        if (this.field_70173_aa % 40 == 0) {
            this.func_70691_i(1.0f);
        }
    }
    
    public boolean canPickup(final ItemStack entityItem) {
        if (entityItem == null) {
            return false;
        }
        if (!this.isTamed() && EntityPech.valuedItems.containsKey(Item.func_150891_b(entityItem.func_77973_b()))) {
            return true;
        }
        for (int a = 0; a < this.loot.size(); ++a) {
            if (!((ItemStack)this.loot.get(a)).func_190926_b() && ((ItemStack)this.loot.get(a)).func_190916_E() <= 0) {
                this.loot.set(a, (Object)ItemStack.field_190927_a);
            }
            if (((ItemStack)this.loot.get(a)).func_190926_b()) {
                return true;
            }
            if (InventoryUtils.areItemStacksEqualStrict(entityItem, (ItemStack)this.loot.get(a)) && entityItem.func_190916_E() + ((ItemStack)this.loot.get(a)).func_190916_E() <= ((ItemStack)this.loot.get(a)).func_77976_d()) {
                return true;
            }
        }
        return false;
    }
    
    public ItemStack pickupItem(ItemStack entityItem) {
        if (entityItem == null || entityItem.func_190926_b()) {
            return ItemStack.field_190927_a;
        }
        if (this.isTamed() || !this.isValued(entityItem)) {
            for (int a = 0; a < this.loot.size(); ++a) {
                if (this.loot.get(a) != null && ((ItemStack)this.loot.get(a)).func_190916_E() <= 0) {
                    this.loot.set(a, (Object)ItemStack.field_190927_a);
                }
                if (entityItem != null && !entityItem.func_190926_b() && entityItem.func_190916_E() > 0 && !((ItemStack)this.loot.get(a)).func_190926_b() && ((ItemStack)this.loot.get(a)).func_190916_E() < ((ItemStack)this.loot.get(a)).func_77976_d() && InventoryUtils.areItemStacksEqualStrict(entityItem, (ItemStack)this.loot.get(a))) {
                    if (entityItem.func_190916_E() + ((ItemStack)this.loot.get(a)).func_190916_E() <= ((ItemStack)this.loot.get(a)).func_77976_d()) {
                        ((ItemStack)this.loot.get(a)).func_190917_f(entityItem.func_190916_E());
                        return ItemStack.field_190927_a;
                    }
                    final int sz = Math.min(entityItem.func_190916_E(), ((ItemStack)this.loot.get(a)).func_77976_d() - ((ItemStack)this.loot.get(a)).func_190916_E());
                    ((ItemStack)this.loot.get(a)).func_190917_f(sz);
                    entityItem.func_190918_g(sz);
                }
                if (entityItem != null && !entityItem.func_190926_b() && entityItem.func_190916_E() <= 0) {
                    entityItem = ItemStack.field_190927_a;
                }
            }
            for (int a = 0; a < this.loot.size(); ++a) {
                if (!((ItemStack)this.loot.get(a)).func_190926_b() && ((ItemStack)this.loot.get(a)).func_190916_E() <= 0) {
                    this.loot.set(a, (Object)ItemStack.field_190927_a);
                }
                if (entityItem != null && entityItem.func_190916_E() > 0 && ((ItemStack)this.loot.get(a)).func_190926_b()) {
                    this.loot.set(a, (Object)entityItem.func_77946_l());
                    return null;
                }
            }
            if (entityItem != null && !entityItem.func_190926_b() && entityItem.func_190916_E() <= 0) {
                entityItem = ItemStack.field_190927_a;
            }
            return entityItem;
        }
        if (this.field_70146_Z.nextInt(10) < this.getValue(entityItem)) {
            this.setTamed(true);
            this.setCombatTask();
            this.field_70170_p.func_72960_a((Entity)this, (byte)18);
        }
        entityItem.func_190918_g(1);
        if (entityItem.func_190916_E() <= 0) {
            return ItemStack.field_190927_a;
        }
        return entityItem;
    }
    
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (player.func_70093_af() || (player.func_184586_b(hand) != null && player.func_184586_b(hand).func_77973_b() instanceof ItemNameTag)) {
            return false;
        }
        if (this.isTamed()) {
            if (!this.field_70170_p.field_72995_K) {
                player.openGui((Object)Thaumcraft.instance, 1, this.field_70170_p, this.func_145782_y(), 0, 0);
            }
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    public boolean isValued(final ItemStack item) {
        if (item == null || item.func_190926_b()) {
            return false;
        }
        boolean value = EntityPech.valuedItems.containsKey(Item.func_150891_b(item.func_77973_b()));
        if (!value) {
            final AspectList al = ThaumcraftCraftingManager.getObjectTags(item);
            if (al.getAmount(Aspect.DESIRE) > 1) {
                value = true;
            }
        }
        return value;
    }
    
    public int getValue(final ItemStack item) {
        if (item == null || item.func_190926_b()) {
            return 0;
        }
        int value = EntityPech.valuedItems.containsKey(Item.func_150891_b(item.func_77973_b())) ? EntityPech.valuedItems.get(Item.func_150891_b(item.func_77973_b())) : 0;
        if (value == 0) {
            final AspectList al = ThaumcraftCraftingManager.getObjectTags(item);
            value = Math.min(32, al.getAmount(Aspect.DESIRE) / 5);
        }
        return value;
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        TYPE = EntityDataManager.func_187226_a((Class)EntityPech.class, DataSerializers.field_187191_a);
        ANGER = EntityDataManager.func_187226_a((Class)EntityPech.class, DataSerializers.field_187192_b);
        TAMED = EntityDataManager.func_187226_a((Class)EntityPech.class, DataSerializers.field_187198_h);
        LOOT = LootTableList.func_186375_a(new ResourceLocation("thaumcraft", "pech"));
        EntityPech.valuedItems = new HashMap<Integer, Integer>();
        EntityPech.tradeInventory = new HashMap<Integer, ArrayList<List>>();
        EntityPech.valuedItems.put(Item.func_150891_b(Items.field_151043_k), 2);
        EntityPech.valuedItems.put(Item.func_150891_b(Items.field_151153_ao), 2);
        EntityPech.valuedItems.put(Item.func_150891_b(Items.field_151079_bi), 3);
        EntityPech.valuedItems.put(Item.func_150891_b(Items.field_151045_i), 4);
        EntityPech.valuedItems.put(Item.func_150891_b(Items.field_151166_bC), 5);
        final ArrayList<List> forInv = new ArrayList<List>();
        forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 0)));
        forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 1)));
        forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 6)));
        if (ModConfig.foundCopperIngot) {
            forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 2)));
        }
        if (ModConfig.foundTinIngot) {
            forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 3)));
        }
        if (ModConfig.foundSilverIngot) {
            forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 4)));
        }
        if (ModConfig.foundLeadIngot) {
            forInv.add(Arrays.asList(1, new ItemStack(ItemsTC.clusters, 1, 5)));
        }
        forInv.add(Arrays.asList(2, new ItemStack(Items.field_151072_bj)));
        forInv.add(Arrays.asList(2, new ItemStack(BlocksTC.saplingGreatwood)));
        forInv.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8201)));
        forInv.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8194)));
        forInv.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forInv.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forInv.add(Arrays.asList(3, new ItemStack(Items.field_151153_ao, 1, 0)));
        forInv.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8265)));
        forInv.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8262)));
        forInv.add(Arrays.asList(4, new ItemStack(ItemsTC.thaumiumPick)));
        forInv.add(Arrays.asList(4, new ItemStack(ItemsTC.thaumiumAxe)));
        forInv.add(Arrays.asList(4, new ItemStack(ItemsTC.thaumiumHoe)));
        forInv.add(Arrays.asList(5, new ItemStack(Items.field_151153_ao, 1, 1)));
        forInv.add(Arrays.asList(5, new ItemStack(BlocksTC.saplingSilverwood)));
        forInv.add(Arrays.asList(5, new ItemStack(ItemsTC.curio, 1, 4)));
        EntityPech.tradeInventory.put(0, forInv);
        final ArrayList<List> forMag = new ArrayList<List>();
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.AIR)));
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.EARTH)));
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.FIRE)));
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.WATER)));
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.ORDER)));
        forMag.add(Arrays.asList(1, ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY)));
        forMag.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8193)));
        forMag.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8261)));
        forMag.add(Arrays.asList(2, ThaumcraftApiHelper.makeCrystal(Aspect.FLUX)));
        forMag.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forMag.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forMag.add(Arrays.asList(3, ThaumcraftApiHelper.makeCrystal(Aspect.AURA)));
        forMag.add(Arrays.asList(3, new ItemStack(Items.field_151153_ao, 1, 0)));
        forMag.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8225)));
        forMag.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8229)));
        forMag.add(Arrays.asList(4, new ItemStack(ItemsTC.clothBoots)));
        forMag.add(Arrays.asList(4, new ItemStack(ItemsTC.clothChest)));
        forMag.add(Arrays.asList(4, new ItemStack(ItemsTC.clothLegs)));
        forMag.add(Arrays.asList(5, new ItemStack(Items.field_151153_ao, 1, 1)));
        forMag.add(Arrays.asList(5, new ItemStack(ItemsTC.pechWand)));
        forMag.add(Arrays.asList(5, new ItemStack(ItemsTC.curio, 1, 4)));
        forMag.add(Arrays.asList(5, new ItemStack(ItemsTC.amuletVis, 1, 0)));
        EntityPech.tradeInventory.put(1, forMag);
        final ArrayList<List> forArc = new ArrayList<List>();
        for (int a = 0; a < 15; ++a) {
            forArc.add(Arrays.asList(1, new ItemStack((Block)BlocksTC.candles.get(EnumDyeColor.func_176766_a(a)))));
        }
        forArc.add(Arrays.asList(2, new ItemStack(Items.field_151073_bk)));
        forArc.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8194)));
        forArc.add(Arrays.asList(2, new ItemStack((Item)Items.field_151068_bn, 1, 8201)));
        forArc.add(Arrays.asList(2, ItemEnchantedBook.func_92111_a(new EnchantmentData(Enchantments.field_185309_u, 1))));
        forArc.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forArc.add(Arrays.asList(3, new ItemStack(Items.field_151062_by)));
        forArc.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8270)));
        forArc.add(Arrays.asList(3, new ItemStack((Item)Items.field_151068_bn, 1, 8225)));
        forArc.add(Arrays.asList(3, new ItemStack(Items.field_151153_ao, 1, 0)));
        forArc.add(Arrays.asList(4, new ItemStack(ItemsTC.eldritchEye)));
        forArc.add(Arrays.asList(4, new ItemStack(Items.field_151153_ao, 1, 1)));
        forArc.add(Arrays.asList(5, new ItemStack(ItemsTC.baubles, 1, 3)));
        forArc.add(Arrays.asList(5, ItemEnchantedBook.func_92111_a(new EnchantmentData(Enchantments.field_185311_w, 1))));
        forArc.add(Arrays.asList(5, ItemEnchantedBook.func_92111_a(new EnchantmentData(Enchantments.field_185312_x, 1))));
        forArc.add(Arrays.asList(5, new ItemStack(ItemsTC.curio, 1, 4)));
        EntityPech.tradeInventory.put(2, forArc);
    }
}
