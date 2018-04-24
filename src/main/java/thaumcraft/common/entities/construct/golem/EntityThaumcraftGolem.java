package thaumcraft.common.entities.construct.golem;

import thaumcraft.common.entities.construct.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.entity.player.*;
import java.nio.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.golems.*;
import net.minecraft.entity.ai.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.pathfinding.*;
import thaumcraft.common.entities.construct.golem.ai.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.util.text.*;
import net.minecraftforge.oredict.*;
import thaumcraft.common.config.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.network.datasync.*;

public class EntityThaumcraftGolem extends EntityOwnedConstruct implements IGolemAPI, IRangedAttackMob
{
    int rankXp;
    private static final DataParameter<Integer> PROPS1;
    private static final DataParameter<Integer> PROPS2;
    private static final DataParameter<Integer> PROPS3;
    protected Task task;
    protected int taskID;
    public static final int XPM = 1000;
    
    public EntityThaumcraftGolem(final World worldIn) {
        super(worldIn);
        this.rankXp = 0;
        this.task = null;
        this.taskID = Integer.MAX_VALUE;
        this.func_70105_a(0.4f, 0.9f);
        this.field_70728_aV = 5;
    }
    
    protected void func_184651_r() {
        this.field_70715_bh.field_75782_a.clear();
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new AIGotoEntity(this));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIGotoBlock(this));
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIGotoHome(this));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityThaumcraftGolem.PROPS1, (Object)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityThaumcraftGolem.PROPS2, (Object)0);
        this.func_184212_Q().func_187214_a((DataParameter)EntityThaumcraftGolem.PROPS3, (Object)0);
    }
    
    @Override
    public IGolemProperties getProperties() {
        final ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putInt((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS1));
        bb.putInt((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS2));
        return GolemProperties.fromLong(bb.getLong(0));
    }
    
    @Override
    public void setProperties(final IGolemProperties prop) {
        final ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(prop.toLong());
        bb.rewind();
        this.func_184212_Q().func_187227_b((DataParameter)EntityThaumcraftGolem.PROPS1, (Object)bb.getInt());
        this.func_184212_Q().func_187227_b((DataParameter)EntityThaumcraftGolem.PROPS2, (Object)bb.getInt());
    }
    
    @Override
    public byte getGolemColor() {
        final byte[] ba = Utils.intToByteArray((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS3));
        return ba[0];
    }
    
    public void setGolemColor(final byte b) {
        final byte[] ba = Utils.intToByteArray((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS3));
        ba[0] = b;
        this.func_184212_Q().func_187227_b((DataParameter)EntityThaumcraftGolem.PROPS3, (Object)Utils.byteArraytoInt(ba));
    }
    
    public byte getFlags() {
        final byte[] ba = Utils.intToByteArray((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS3));
        return ba[1];
    }
    
    public void setFlags(final byte b) {
        final byte[] ba = Utils.intToByteArray((int)this.func_184212_Q().func_187225_a((DataParameter)EntityThaumcraftGolem.PROPS3));
        ba[1] = b;
        this.func_184212_Q().func_187227_b((DataParameter)EntityThaumcraftGolem.PROPS3, (Object)Utils.byteArraytoInt(ba));
    }
    
    public float func_70047_e() {
        return 0.7f;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
        this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0);
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0);
    }
    
    private void updateEntityAttributes() {
        int mh = 10 + this.getProperties().getMaterial().healthMod;
        if (this.getProperties().hasTrait(EnumGolemTrait.FRAGILE)) {
            mh *= (int)0.75;
        }
        mh += this.getProperties().getRank();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)mh);
        this.field_70138_W = (this.getProperties().hasTrait(EnumGolemTrait.WHEELED) ? 0.5f : 0.6f);
        this.func_175449_a((this.func_180486_cf() == BlockPos.field_177992_a) ? this.func_180425_c() : this.func_180486_cf(), this.getProperties().hasTrait(EnumGolemTrait.SCOUT) ? 48 : 32);
        this.field_70699_by = this.getGolemNavigator();
        if (this.getProperties().hasTrait(EnumGolemTrait.FIGHTER)) {
            double da = this.getProperties().getMaterial().damage;
            if (this.getProperties().hasTrait(EnumGolemTrait.BRUTAL)) {
                da = Math.max(da * 1.5, da + 1.0);
            }
            da += this.getProperties().getRank() * 0.25;
            this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(da);
        }
        else {
            this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0);
        }
        this.createAI();
    }
    
    private void createAI() {
        this.field_70714_bg.field_75782_a.clear();
        this.field_70715_bh.field_75782_a.clear();
        if (this.isFollowingOwner()) {
            this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIFollowOwner(this, 1.0, 10.0f, 2.0f));
        }
        else {
            this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIGotoEntity(this));
            this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIGotoBlock(this));
            this.field_70714_bg.func_75776_a(5, (EntityAIBase)new AIGotoHome(this));
        }
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        if (this.getProperties().hasTrait(EnumGolemTrait.FIGHTER)) {
            if (this.func_70661_as() instanceof PathNavigateGround) {
                this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
            }
            if (this.getProperties().hasTrait(EnumGolemTrait.RANGED)) {
                EntityAIAttackRanged aa = null;
                if (this.getProperties().getArms().function != null) {
                    aa = this.getProperties().getArms().function.getRangedAttackAI((IRangedAttackMob)this);
                }
                if (aa != null) {
                    this.field_70714_bg.func_75776_a(1, (EntityAIBase)aa);
                }
            }
            this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.15, false));
            if (this.isFollowingOwner()) {
                this.field_70715_bh.func_75776_a(1, (EntityAIBase)new AIOwnerHurtByTarget(this));
                this.field_70715_bh.func_75776_a(2, (EntityAIBase)new AIOwnerHurtTarget(this));
            }
            this.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        }
    }
    
    public boolean func_70617_f_() {
        return this.isBesideClimbableBlock();
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData ld) {
        this.func_175449_a(this.func_180425_c(), 32);
        this.updateEntityAttributes();
        return ld;
    }
    
    public int func_70658_aO() {
        int armor = this.getProperties().getMaterial().armor;
        if (this.getProperties().hasTrait(EnumGolemTrait.ARMORED)) {
            armor = (int)Math.max(armor * 1.5, armor + 1);
        }
        if (this.getProperties().hasTrait(EnumGolemTrait.FRAGILE)) {
            armor *= (int)0.75;
        }
        return armor;
    }
    
    public void func_70636_d() {
        this.func_82168_bl();
        super.func_70636_d();
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K) {
            if (this.task != null && this.task.isSuspended()) {
                this.task = null;
            }
            if (this.func_70638_az() != null && this.func_70638_az().field_70128_L) {
                this.func_70624_b(null);
            }
            if (this.func_70638_az() != null && this.getProperties().hasTrait(EnumGolemTrait.RANGED) && this.func_70068_e((Entity)this.func_70638_az()) > 1024.0) {
                this.func_70624_b(null);
            }
            if (!FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W() && this.func_70638_az() != null && this.func_70638_az() instanceof EntityPlayer) {
                this.func_70624_b(null);
            }
            if (this.field_70173_aa % (this.getProperties().hasTrait(EnumGolemTrait.REPAIR) ? 40 : 100) == 0) {
                this.func_70691_i(1.0f);
            }
            if (this.getProperties().hasTrait(EnumGolemTrait.CLIMBER)) {
                this.setBesideClimbableBlock(!this.field_82175_bq && this.field_70123_F);
            }
        }
        if (this.getProperties().getHead().function != null) {
            this.getProperties().getHead().function.onUpdateTick(this);
        }
        if (this.getProperties().getArms().function != null) {
            this.getProperties().getArms().function.onUpdateTick(this);
        }
        if (this.getProperties().getLegs().function != null) {
            this.getProperties().getLegs().function.onUpdateTick(this);
        }
        if (this.getProperties().getAddon().function != null) {
            this.getProperties().getAddon().function.onUpdateTick(this);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 5) {
            FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t, this.field_70163_u + this.field_70131_O + 0.1, this.field_70161_v, 0.0, 0.0, 0.0, 1.0f, 1.0f, 1.0f, 0.5f, false, 704 + (this.field_70146_Z.nextBoolean() ? 0 : 3), 3, 1, 6, 0, 2.0f, 0.0f, 1);
        }
        else if (par1 == 6) {
            FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t, this.field_70163_u + this.field_70131_O + 0.1, this.field_70161_v, 0.0, 0.025, 0.0, 0.1f, 1.0f, 1.0f, 0.5f, false, 15, 1, 1, 10, 0, 2.0f, 0.0f, 1);
        }
        else if (par1 == 7) {
            FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t, this.field_70163_u + this.field_70131_O + 0.1, this.field_70161_v, 0.0, 0.05, 0.0, 1.0f, 1.0f, 1.0f, 0.5f, false, 640, 10, 1, 10, 0, 2.0f, 0.0f, 1);
        }
        else if (par1 == 8) {
            FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t, this.field_70163_u + this.field_70131_O + 0.1, this.field_70161_v, 0.0, 0.01, 0.0, 1.0f, 1.0f, 0.1f, 0.5f, false, 14, 1, 1, 20, 0, 2.0f, 0.0f, 1);
        }
        else if (par1 == 9) {
            for (int a = 0; a < 5; ++a) {
                FXDispatcher.INSTANCE.drawGenericParticles(this.field_70165_t, this.field_70163_u + this.field_70131_O, this.field_70161_v, this.field_70146_Z.nextGaussian() * 0.009999999776482582, this.field_70146_Z.nextFloat() * 0.02, this.field_70146_Z.nextGaussian() * 0.009999999776482582, 1.0f, 1.0f, 1.0f, 0.5f, false, 13, 1, 1, 20 + this.field_70146_Z.nextInt(20), 0, 0.3f + this.field_70146_Z.nextFloat() * 0.4f, 0.0f, 1);
            }
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    public float getGolemMoveSpeed() {
        return 1.0f + this.getProperties().getRank() * 0.025f + (this.getProperties().hasTrait(EnumGolemTrait.LIGHT) ? 0.2f : 0.0f) + (this.getProperties().hasTrait(EnumGolemTrait.HEAVY) ? -0.175f : 0.0f) + (this.getProperties().hasTrait(EnumGolemTrait.FLYER) ? -0.33f : 0.0f) + (this.getProperties().hasTrait(EnumGolemTrait.WHEELED) ? 0.25f : 0.0f);
    }
    
    public PathNavigate getGolemNavigator() {
        return (PathNavigate)(this.getProperties().hasTrait(EnumGolemTrait.FLYER) ? new PathNavigateGolemAir((EntityLiving)this, this.field_70170_p) : (this.getProperties().hasTrait(EnumGolemTrait.CLIMBER) ? new PathNavigateClimber((EntityLiving)this, this.field_70170_p) : new PathNavigateGolemGround((EntityLiving)this, this.field_70170_p)));
    }
    
    public void func_191986_a(final float strafe, final float vertical, final float forward) {
        if (this.func_70613_aW()) {
            if (this.getProperties().hasTrait(EnumGolemTrait.FLYER)) {
                this.func_191958_b(strafe, vertical, forward, 0.02f);
                this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                this.field_70159_w *= 0.8999999761581421;
                this.field_70181_x *= 0.8999999761581421;
                this.field_70179_y *= 0.8999999761581421;
                if (this.func_70638_az() == null) {
                    this.field_70181_x -= 0.005;
                }
            }
            else {
                super.func_191986_a(strafe, vertical, forward);
            }
        }
        else {
            super.func_191986_a(strafe, vertical, forward);
        }
    }
    
    protected boolean func_70041_e_() {
        return this.getProperties().hasTrait(EnumGolemTrait.HEAVY) && !this.getProperties().hasTrait(EnumGolemTrait.FLYER);
    }
    
    public void func_180430_e(final float distance, final float damageMultiplier) {
        if (!this.getProperties().hasTrait(EnumGolemTrait.FLYER) && !this.getProperties().hasTrait(EnumGolemTrait.CLIMBER)) {
            super.func_180430_e(distance, damageMultiplier);
        }
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setProperties(GolemProperties.fromLong(nbt.func_74763_f("props")));
        this.func_175449_a(BlockPos.func_177969_a(nbt.func_74763_f("homepos")), 32);
        this.setFlags(Byte.valueOf(nbt.func_74771_c("gflags")));
        this.rankXp = nbt.func_74762_e("rankXP");
        this.setGolemColor(nbt.func_74771_c("color"));
        this.updateEntityAttributes();
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74772_a("props", this.getProperties().toLong());
        nbt.func_74772_a("homepos", this.func_180486_cf().func_177986_g());
        nbt.func_74774_a("gflags", this.getFlags());
        nbt.func_74768_a("rankXP", this.rankXp);
        nbt.func_74774_a("color", this.getGolemColor());
    }
    
    protected void func_70665_d(final DamageSource ds, float damage) {
        if (ds.func_76347_k() && this.getProperties().hasTrait(EnumGolemTrait.FIREPROOF)) {
            return;
        }
        if (ds.func_94541_c() && this.getProperties().hasTrait(EnumGolemTrait.BLASTPROOF)) {
            damage = Math.min(this.func_110138_aP() / 2.0f, damage * 0.3f);
        }
        if (ds == DamageSource.field_76367_g) {
            return;
        }
        if (ds == DamageSource.field_76368_d || ds == DamageSource.field_76380_i) {
            this.func_70012_b(this.func_180486_cf().func_177958_n() + 0.5, this.func_180486_cf().func_177956_o() + 0.5, this.func_180486_cf().func_177952_p() + 0.5, 0.0f, 0.0f);
        }
        super.func_70665_d(ds, damage);
    }
    
    @Override
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (player.func_184586_b(hand).func_77973_b() instanceof ItemNameTag) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && this.isOwner((EntityLivingBase)player) && !this.field_70128_L) {
            if (player.func_70093_af()) {
                this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f);
                if (this.task != null) {
                    this.task.setReserved(false);
                }
                this.dropCarried();
                final ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
                placer.func_77983_a("props", (NBTBase)new NBTTagLong(this.getProperties().toLong()));
                placer.func_77983_a("xp", (NBTBase)new NBTTagInt(this.rankXp));
                this.func_70099_a(placer, 0.5f);
                this.func_70106_y();
                player.func_184609_a(hand);
            }
            else if (player.func_184586_b(hand).func_77973_b() instanceof ItemGolemBell && ThaumcraftCapabilities.getKnowledge(player).isResearchKnown("GOLEMDIRECT")) {
                if (this.task != null) {
                    this.task.setReserved(false);
                }
                this.func_184185_a(SoundsTC.scan, 1.0f, 1.0f);
                this.setFollowingOwner(!this.isFollowingOwner());
                if (this.isFollowingOwner()) {
                    player.func_146105_b((ITextComponent)new TextComponentTranslation("golem.follow", new Object[] { "" }), true);
                    if (ModConfig.CONFIG_GRAPHICS.showGolemEmotes) {
                        this.field_70170_p.func_72960_a((Entity)this, (byte)5);
                    }
                }
                else {
                    player.func_146105_b((ITextComponent)new TextComponentTranslation("golem.stay", new Object[] { "" }), true);
                    if (ModConfig.CONFIG_GRAPHICS.showGolemEmotes) {
                        this.field_70170_p.func_72960_a((Entity)this, (byte)8);
                    }
                    this.func_175449_a(this.func_180425_c(), 32);
                }
                this.updateEntityAttributes();
                player.func_184609_a(hand);
            }
            else {
                final int[] ids = OreDictionary.getOreIDs(player.func_184586_b(hand));
                if (ids != null && ids.length > 0) {
                    for (final int id : ids) {
                        final String s = OreDictionary.getOreName(id);
                        if (s.startsWith("dye")) {
                            for (int a = 0; a < ConfigAspects.dyes.length; ++a) {
                                if (s.equals(ConfigAspects.dyes[a])) {
                                    this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f);
                                    this.setGolemColor((byte)(16 - a));
                                    player.func_184586_b(hand).func_190918_g(1);
                                    player.func_184609_a(hand);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    @Override
    public void func_70645_a(final DamageSource cause) {
        if (this.task != null) {
            this.task.setReserved(false);
        }
        super.func_70645_a(cause);
        if (!this.field_70170_p.field_72995_K) {
            this.dropCarried();
        }
    }
    
    protected void dropCarried() {
        for (final ItemStack s : this.getCarrying()) {
            if (s != null && !s.func_190926_b()) {
                this.func_70099_a(s, 0.25f);
            }
        }
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
        final float b = p_70628_2_ * 0.15f;
        for (final ItemStack stack : this.getProperties().generateComponents()) {
            final ItemStack s = stack.func_77946_l();
            if (this.field_70146_Z.nextFloat() < 0.3f + b) {
                if (s.func_190916_E() > 0) {
                    s.func_190918_g(this.field_70146_Z.nextInt(s.func_190916_E()));
                }
                this.func_70099_a(s, 0.25f);
            }
        }
    }
    
    public boolean isBesideClimbableBlock() {
        return Utils.getBit(this.getFlags(), 0);
    }
    
    public void setBesideClimbableBlock(final boolean p_70839_1_) {
        byte b0 = this.getFlags();
        if (p_70839_1_) {
            b0 = (byte)Utils.setBit(b0, 0);
        }
        else {
            b0 = (byte)Utils.clearBit(b0, 0);
        }
        this.setFlags(Byte.valueOf(b0));
    }
    
    public boolean isFollowingOwner() {
        return Utils.getBit(this.getFlags(), 1);
    }
    
    public void setFollowingOwner(final boolean par1) {
        final byte var2 = this.getFlags();
        if (par1) {
            this.setFlags(Byte.valueOf((byte)Utils.setBit(var2, 1)));
        }
        else {
            this.setFlags(Byte.valueOf((byte)Utils.clearBit(var2, 1)));
        }
    }
    
    public void func_70624_b(final EntityLivingBase entitylivingbaseIn) {
        super.func_70624_b(entitylivingbaseIn);
        this.setInCombat(this.func_70638_az() != null);
    }
    
    @Override
    public boolean isInCombat() {
        return Utils.getBit(this.getFlags(), 3);
    }
    
    public void setInCombat(final boolean par1) {
        final byte var2 = this.getFlags();
        if (par1) {
            this.setFlags(Byte.valueOf((byte)Utils.setBit(var2, 3)));
        }
        else {
            this.setFlags(Byte.valueOf((byte)Utils.clearBit(var2, 3)));
        }
    }
    
    public boolean func_70652_k(final Entity ent) {
        float dmg = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
        int kb = 0;
        if (ent instanceof EntityLivingBase) {
            dmg += EnchantmentHelper.func_152377_a(this.func_184614_ca(), ((EntityLivingBase)ent).func_70668_bt());
            kb += EnchantmentHelper.func_77501_a((EntityLivingBase)this);
        }
        final boolean flag = ent.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), dmg);
        if (flag) {
            if (ent instanceof EntityLivingBase && this.getProperties().hasTrait(EnumGolemTrait.DEFT)) {
                ((EntityLivingBase)ent).field_70718_bc = 100;
            }
            if (kb > 0) {
                ent.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927f / 180.0f) * kb * 0.5f), 0.1, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927f / 180.0f) * kb * 0.5f));
                this.field_70159_w *= 0.6;
                this.field_70179_y *= 0.6;
            }
            final int j = EnchantmentHelper.func_90036_a((EntityLivingBase)this);
            if (j > 0) {
                ent.func_70015_d(j * 4);
            }
            this.func_174815_a((EntityLivingBase)this, ent);
            if (this.getProperties().getArms().function != null) {
                this.getProperties().getArms().function.onMeleeAttack(this, ent);
            }
            if (ent instanceof EntityLiving && !((EntityLiving)ent).func_70089_S()) {
                this.addRankXp(8);
            }
        }
        return flag;
    }
    
    public Task getTask() {
        if (this.task == null && this.taskID != Integer.MAX_VALUE) {
            this.task = TaskHandler.getTask(this.field_70170_p.field_73011_w.getDimension(), this.taskID);
            this.taskID = Integer.MAX_VALUE;
        }
        return this.task;
    }
    
    public void setTask(final Task task) {
        this.task = task;
    }
    
    @Override
    public void addRankXp(final int xp) {
        if (!this.getProperties().hasTrait(EnumGolemTrait.SMART) || this.field_70170_p.field_72995_K) {
            return;
        }
        final int rank = this.getProperties().getRank();
        if (rank < 10) {
            this.rankXp += xp;
            final int xn = (rank + 1) * (rank + 1) * 1000;
            if (this.rankXp >= xn) {
                this.rankXp -= xn;
                final IGolemProperties props = this.getProperties();
                props.setRank(rank + 1);
                this.setProperties(props);
                if (ModConfig.CONFIG_GRAPHICS.showGolemEmotes) {
                    this.field_70170_p.func_72960_a((Entity)this, (byte)9);
                    this.func_184185_a(SoundEvents.field_187802_ec, 0.25f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public ItemStack holdItem(ItemStack stack) {
        if (stack == null || stack.func_190926_b() || stack.func_190916_E() <= 0) {
            return stack;
        }
        for (int a = 0; a < (this.getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); ++a) {
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]) == null || this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190926_b()) {
                this.func_184201_a(EntityEquipmentSlot.values()[a], stack);
                return ItemStack.field_190927_a;
            }
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190916_E() < this.func_184582_a(EntityEquipmentSlot.values()[a]).func_77976_d() && ItemStack.func_179545_c(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack) && ItemStack.func_77970_a(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack)) {
                final int d = Math.min(stack.func_190916_E(), this.func_184582_a(EntityEquipmentSlot.values()[a]).func_77976_d() - this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190916_E());
                stack.func_190918_g(d);
                this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190917_f(d);
                if (stack.func_190916_E() <= 0) {
                    stack = ItemStack.field_190927_a;
                }
            }
        }
        return stack;
    }
    
    @Override
    public ItemStack dropItem(final ItemStack stack) {
        ItemStack out = ItemStack.field_190927_a;
        for (int a = 0; a < (this.getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); ++a) {
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]) != null) {
                if (!this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190926_b()) {
                    if (stack == null || stack.func_190926_b()) {
                        out = this.func_184582_a(EntityEquipmentSlot.values()[a]).func_77946_l();
                        this.func_184201_a(EntityEquipmentSlot.values()[a], ItemStack.field_190927_a);
                    }
                    else if (ItemStack.func_179545_c(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack) && ItemStack.func_77970_a(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack)) {
                        out = this.func_184582_a(EntityEquipmentSlot.values()[a]).func_77946_l();
                        out.func_190920_e(Math.min(stack.func_190916_E(), out.func_190916_E()));
                        this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190918_g(stack.func_190916_E());
                        if (this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190916_E() <= 0) {
                            this.func_184201_a(EntityEquipmentSlot.values()[a], ItemStack.field_190927_a);
                        }
                    }
                    if (out != null && !out.func_190926_b()) {
                        break;
                    }
                }
            }
        }
        if (this.getProperties().hasTrait(EnumGolemTrait.HAULER) && (this.func_184582_a(EntityEquipmentSlot.values()[0]) == null || this.func_184582_a(EntityEquipmentSlot.values()[0]).func_190926_b()) && this.func_184582_a(EntityEquipmentSlot.values()[1]) != null && !this.func_184582_a(EntityEquipmentSlot.values()[1]).func_190926_b()) {
            this.func_184201_a(EntityEquipmentSlot.values()[0], this.func_184582_a(EntityEquipmentSlot.values()[1]).func_77946_l());
            this.func_184201_a(EntityEquipmentSlot.values()[1], ItemStack.field_190927_a);
        }
        return out;
    }
    
    @Override
    public boolean canCarry(final ItemStack stack, final boolean partial) {
        for (int a = 0; a < (this.getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); ++a) {
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]) == null || this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190926_b()) {
                return true;
            }
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190916_E() < this.func_184582_a(EntityEquipmentSlot.values()[a]).func_77976_d() && ItemStack.func_179545_c(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack) && ItemStack.func_77970_a(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isCarrying(final ItemStack stack) {
        if (stack == null || stack.func_190926_b()) {
            return false;
        }
        for (int a = 0; a < (this.getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); ++a) {
            if (this.func_184582_a(EntityEquipmentSlot.values()[a]) != null && !this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190926_b() && this.func_184582_a(EntityEquipmentSlot.values()[a]).func_190916_E() > 0 && ItemStack.func_179545_c(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack) && ItemStack.func_77970_a(this.func_184582_a(EntityEquipmentSlot.values()[a]), stack)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public NonNullList<ItemStack> getCarrying() {
        if (this.getProperties().hasTrait(EnumGolemTrait.HAULER)) {
            final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(2, (Object)ItemStack.field_190927_a);
            stacks.set(0, (Object)this.func_184582_a(EntityEquipmentSlot.values()[0]));
            stacks.set(1, (Object)this.func_184582_a(EntityEquipmentSlot.values()[1]));
            return stacks;
        }
        return (NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)this.func_184582_a(EntityEquipmentSlot.values()[0]));
    }
    
    @Override
    public EntityLivingBase getGolemEntity() {
        return (EntityLivingBase)this;
    }
    
    @Override
    public World getGolemWorld() {
        return this.func_130014_f_();
    }
    
    @Override
    public void swingArm() {
        if (!this.field_82175_bq || this.field_110158_av >= 3 || this.field_110158_av < 0) {
            this.field_110158_av = -1;
            this.field_82175_bq = true;
            if (this.field_70170_p instanceof WorldServer) {
                ((WorldServer)this.field_70170_p).func_73039_n().func_151248_b((Entity)this, (Packet)new SPacketAnimation((Entity)this, 0));
            }
        }
    }
    
    public void func_82196_d(final EntityLivingBase target, final float range) {
        if (this.getProperties().getArms().function != null) {
            this.getProperties().getArms().function.onRangedAttack(this, target, range);
        }
    }
    
    public void func_184724_a(final boolean swingingArms) {
    }
    
    static {
        PROPS1 = EntityDataManager.func_187226_a((Class)EntityThaumcraftGolem.class, DataSerializers.field_187192_b);
        PROPS2 = EntityDataManager.func_187226_a((Class)EntityThaumcraftGolem.class, DataSerializers.field_187192_b);
        PROPS3 = EntityDataManager.func_187226_a((Class)EntityThaumcraftGolem.class, DataSerializers.field_187192_b);
    }
}
