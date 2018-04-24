package thaumcraft.common.entities.construct;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.scoreboard.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import thaumcraft.*;
import thaumcraft.api.blocks.*;
import net.minecraft.network.datasync.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public class EntityTurretCrossbowAdvanced extends EntityTurretCrossbow
{
    private static final DataParameter<Byte> FLAGS;
    
    public EntityTurretCrossbowAdvanced(final World worldIn) {
        super(worldIn);
        this.func_70105_a(0.95f, 1.5f);
        this.field_70138_W = 0.0f;
    }
    
    @Override
    protected void func_184651_r() {
        this.field_70714_bg.field_75782_a.clear();
        this.field_70715_bh.field_75782_a.clear();
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAttackRanged((IRangedAttackMob)this, 0.0, 20, 40, 24.0f));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIWatchTarget((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestValidTarget(this, EntityLivingBase.class, 5, true, false, null));
        this.setTargetMob(true);
    }
    
    @Override
    public float func_70047_e() {
        return 1.0f;
    }
    
    public EntityTurretCrossbowAdvanced(final World worldIn, final BlockPos pos) {
        this(worldIn);
        this.func_70080_a(pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), pos.func_177952_p() + 0.5, 0.0f, 0.0f);
    }
    
    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)0);
    }
    
    public boolean func_70686_a(final Class clazz) {
        if (IAnimals.class.isAssignableFrom(clazz) && !IMob.class.isAssignableFrom(clazz) && this.getTargetAnimal()) {
            return true;
        }
        if (IMob.class.isAssignableFrom(clazz) && this.getTargetMob()) {
            return true;
        }
        if (!EntityPlayer.class.isAssignableFrom(clazz) || !this.getTargetPlayer()) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && !FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W() && !this.getTargetFriendly()) {
            this.setTargetPlayer(false);
            return false;
        }
        return true;
    }
    
    public boolean getTargetAnimal() {
        return Utils.getBit((byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS), 0);
    }
    
    public void setTargetAnimal(final boolean par1) {
        final byte var2 = (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS);
        if (par1) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.setBit(var2, 0));
        }
        else {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.clearBit(var2, 0));
        }
        this.func_70624_b((EntityLivingBase)null);
    }
    
    public boolean getTargetMob() {
        return Utils.getBit((byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS), 1);
    }
    
    public void setTargetMob(final boolean par1) {
        final byte var2 = (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS);
        if (par1) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.setBit(var2, 1));
        }
        else {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.clearBit(var2, 1));
        }
        this.func_70624_b((EntityLivingBase)null);
    }
    
    public boolean getTargetPlayer() {
        return Utils.getBit((byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS), 2);
    }
    
    public void setTargetPlayer(final boolean par1) {
        final byte var2 = (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS);
        if (par1) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.setBit(var2, 2));
        }
        else {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.clearBit(var2, 2));
        }
        this.func_70624_b((EntityLivingBase)null);
    }
    
    public boolean getTargetFriendly() {
        return Utils.getBit((byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS), 3);
    }
    
    public void setTargetFriendly(final boolean par1) {
        final byte var2 = (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS);
        if (par1) {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.setBit(var2, 3));
        }
        else {
            this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)(byte)Utils.clearBit(var2, 3));
        }
        this.func_70624_b((EntityLivingBase)null);
    }
    
    @Override
    public Team func_96124_cp() {
        if (this.isOwned()) {
            final EntityLivingBase entitylivingbase = this.getOwnerEntity();
            if (entitylivingbase != null) {
                return entitylivingbase.func_96124_cp();
            }
        }
        return super.func_96124_cp();
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0);
    }
    
    @Override
    public int func_70658_aO() {
        return 8;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K && !FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W() && this.func_70638_az() != null && this.func_70638_az() instanceof EntityPlayer && this.func_70638_az() != this.getOwnerEntity()) {
            this.func_70624_b((EntityLivingBase)null);
        }
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.func_184212_Q().func_187227_b((DataParameter)EntityTurretCrossbowAdvanced.FLAGS, (Object)nbt.func_74771_c("targets"));
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74774_a("targets", (byte)this.func_184212_Q().func_187225_a((DataParameter)EntityTurretCrossbowAdvanced.FLAGS));
    }
    
    @Override
    public void func_70653_a(final Entity p_70653_1_, final float p_70653_2_, final double p_70653_3_, final double p_70653_5_) {
        super.func_70653_a(p_70653_1_, p_70653_2_, p_70653_3_ / 10.0, p_70653_5_ / 10.0);
    }
    
    @Override
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (!this.field_70170_p.field_72995_K && this.isOwner((EntityLivingBase)player) && !this.field_70128_L) {
            if (player.func_70093_af()) {
                this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f);
                this.dropAmmo();
                this.func_70099_a(new ItemStack(ItemsTC.turretPlacer, 1, 1), 0.5f);
                this.func_70106_y();
                player.func_184609_a(hand);
            }
            else {
                player.openGui((Object)Thaumcraft.instance, 17, this.field_70170_p, this.func_145782_y(), 0, 0);
            }
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    @Override
    public void func_70091_d(final MoverType t, final double x, final double y, final double z) {
        super.func_70091_d(t, x / 15.0, y, z / 15.0);
    }
    
    @Override
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
        final float b = p_70628_2_ * 0.15f;
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.mind, 1, 1), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.mechanismSimple), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(BlocksTC.plankGreatwood), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.5f + b) {
            this.func_70099_a(new ItemStack(BlocksTC.plankGreatwood), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.3f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.plate, 1, 0), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.4f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.plate, 1, 1), 0.5f);
        }
        if (this.field_70146_Z.nextFloat() < 0.4f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.plate, 1, 1), 0.5f);
        }
    }
    
    static {
        FLAGS = EntityDataManager.func_187226_a((Class)EntityTurretCrossbowAdvanced.class, DataSerializers.field_187191_a);
    }
    
    protected class EntityAIWatchTarget extends EntityAIBase
    {
        protected EntityLiving theWatcher;
        protected Entity closestEntity;
        private int lookTime;
        
        public EntityAIWatchTarget(final EntityLiving p_i1631_1_) {
            this.theWatcher = p_i1631_1_;
            this.func_75248_a(2);
        }
        
        public boolean func_75250_a() {
            if (this.theWatcher.func_70638_az() != null) {
                this.closestEntity = (Entity)this.theWatcher.func_70638_az();
            }
            return this.closestEntity != null;
        }
        
        public boolean func_75253_b() {
            final float d = (float)this.getTargetDistance();
            return this.closestEntity.func_70089_S() && this.theWatcher.func_70068_e(this.closestEntity) <= d * d && this.lookTime > 0;
        }
        
        public void func_75249_e() {
            this.lookTime = 40 + this.theWatcher.func_70681_au().nextInt(40);
        }
        
        public void func_75251_c() {
            this.closestEntity = null;
        }
        
        public void func_75246_d() {
            this.theWatcher.func_70671_ap().func_75650_a(this.closestEntity.field_70165_t, this.closestEntity.field_70163_u + this.closestEntity.func_70047_e(), this.closestEntity.field_70161_v, 10.0f, (float)this.theWatcher.func_70646_bf());
            --this.lookTime;
        }
        
        protected double getTargetDistance() {
            final IAttributeInstance iattributeinstance = this.theWatcher.func_110148_a(SharedMonsterAttributes.field_111265_b);
            return (iattributeinstance == null) ? 16.0 : iattributeinstance.func_111126_e();
        }
    }
    
    protected class EntityAINearestValidTarget extends EntityAITarget
    {
        protected final Class targetClass;
        private final int targetChance;
        protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
        protected Predicate targetEntitySelector;
        protected EntityLivingBase targetEntity;
        private int targetUnseenTicks;
        
        public EntityAINearestValidTarget(final EntityTurretCrossbowAdvanced this$0, final EntityCreature p_i45878_1_, final Class p_i45878_2_, final boolean p_i45878_3_) {
            this(this$0, p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
        }
        
        public EntityAINearestValidTarget(final EntityTurretCrossbowAdvanced this$0, final EntityCreature p_i45879_1_, final Class p_i45879_2_, final boolean p_i45879_3_, final boolean p_i45879_4_) {
            this(this$0, p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, null);
        }
        
        public EntityAINearestValidTarget(final EntityCreature p_i45880_1_, final Class p_i45880_2_, final int p_i45880_3_, final boolean p_i45880_4_, final boolean p_i45880_5_, final Predicate tselector) {
            super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
            this.targetClass = p_i45880_2_;
            this.targetChance = p_i45880_3_;
            this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter((Entity)p_i45880_1_);
            this.func_75248_a(1);
            this.targetEntitySelector = (Predicate)new Predicate() {
                private static final String __OBFID = "CL_00001621";
                
                public boolean applySelection(final EntityLivingBase entity) {
                    if (tselector != null && !tselector.apply((Object)entity)) {
                        return false;
                    }
                    if (entity instanceof EntityPlayer) {
                        double d0 = EntityAINearestValidTarget.access$000(EntityAINearestValidTarget.this);
                        if (entity.func_70093_af()) {
                            d0 *= 0.800000011920929;
                        }
                        if (entity.func_82150_aj()) {
                            float f = ((EntityPlayer)entity).func_82243_bO();
                            if (f < 0.1f) {
                                f = 0.1f;
                            }
                            d0 *= 0.7f * f;
                        }
                        if (entity.func_70032_d((Entity)EntityAINearestValidTarget.this.field_75299_d) > d0) {
                            return false;
                        }
                    }
                    return EntityAINearestValidTarget.this.func_75296_a(entity, false);
                }
                
                public boolean apply(final Object p_apply_1_) {
                    return this.applySelection((EntityLivingBase)p_apply_1_);
                }
            };
        }
        
        public boolean func_75253_b() {
            final EntityLivingBase entitylivingbase = this.field_75299_d.func_70638_az();
            if (entitylivingbase == null) {
                return false;
            }
            if (!entitylivingbase.func_70089_S()) {
                return false;
            }
            final Team team = this.field_75299_d.func_96124_cp();
            final Team team2 = entitylivingbase.func_96124_cp();
            if (team != null && team2 == team && !((EntityTurretCrossbowAdvanced)this.field_75299_d).getTargetFriendly()) {
                return false;
            }
            if (team != null && team2 != team && ((EntityTurretCrossbowAdvanced)this.field_75299_d).getTargetFriendly()) {
                return false;
            }
            final double d0 = this.func_111175_f();
            if (this.field_75299_d.func_70068_e((Entity)entitylivingbase) > d0 * d0) {
                return false;
            }
            if (this.field_75297_f) {
                if (this.field_75299_d.func_70635_at().func_75522_a((Entity)entitylivingbase)) {
                    this.targetUnseenTicks = 0;
                }
                else if (++this.targetUnseenTicks > 60) {
                    return false;
                }
            }
            return true;
        }
        
        protected boolean func_75296_a(final EntityLivingBase p_75296_1_, final boolean p_75296_2_) {
            return this.isGoodTarget((EntityLiving)this.field_75299_d, p_75296_1_, p_75296_2_, this.field_75297_f) && this.field_75299_d.func_180485_d(new BlockPos((Entity)p_75296_1_));
        }
        
        private boolean isGoodTarget(final EntityLiving attacker, final EntityLivingBase posTar, final boolean p_179445_2_, final boolean checkSight) {
            if (posTar == null) {
                return false;
            }
            if (posTar == attacker) {
                return false;
            }
            if (!posTar.func_70089_S()) {
                return false;
            }
            if (!attacker.func_70686_a((Class)posTar.getClass())) {
                return false;
            }
            final Team team = attacker.func_96124_cp();
            final Team team2 = posTar.func_96124_cp();
            if (team != null && team2 == team && !((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                return false;
            }
            if (team != null && team2 != team && ((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                return false;
            }
            if (attacker instanceof IEntityOwnable && StringUtils.isNotEmpty((CharSequence)((IEntityOwnable)attacker).func_184753_b().toString())) {
                if (posTar instanceof IEntityOwnable && ((IEntityOwnable)attacker).func_184753_b().equals(((IEntityOwnable)posTar).func_184753_b()) && !((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                    return false;
                }
                if (!(posTar instanceof IEntityOwnable) && !(posTar instanceof EntityPlayer) && ((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                    return false;
                }
                if (posTar instanceof IEntityOwnable && !((IEntityOwnable)attacker).func_184753_b().equals(((IEntityOwnable)posTar).func_184753_b()) && ((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                    return false;
                }
                if (posTar == ((IEntityOwnable)attacker).func_70902_q() && !((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                    return false;
                }
            }
            else if (posTar instanceof EntityPlayer && !p_179445_2_ && ((EntityPlayer)posTar).field_71075_bZ.field_75102_a && !((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()) {
                return false;
            }
            return !checkSight || attacker.func_70635_at().func_75522_a((Entity)posTar);
        }
        
        public boolean func_75250_a() {
            if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0) {
                return false;
            }
            final double d0 = this.func_111175_f();
            final List list = this.field_75299_d.field_70170_p.func_175647_a(this.targetClass, this.field_75299_d.func_174813_aQ().func_72314_b(d0, 4.0, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.field_180132_d));
            Collections.sort((List<Object>)list, (Comparator<? super Object>)this.theNearestAttackableTargetSorter);
            if (list.isEmpty()) {
                return false;
            }
            this.targetEntity = list.get(0);
            return true;
        }
        
        public void func_75249_e() {
            this.field_75299_d.func_70624_b(this.targetEntity);
            this.targetUnseenTicks = 0;
            super.func_75249_e();
        }
        
        static /* synthetic */ double access$000(final EntityAINearestValidTarget x0) {
            return x0.func_111175_f();
        }
        
        public class Sorter implements Comparator
        {
            private final Entity theEntity;
            private static final String __OBFID = "CL_00001622";
            
            public Sorter(final Entity p_i1662_1_) {
                this.theEntity = p_i1662_1_;
            }
            
            public int compare(final Entity p_compare_1_, final Entity p_compare_2_) {
                final double d0 = this.theEntity.func_70068_e(p_compare_1_);
                final double d2 = this.theEntity.func_70068_e(p_compare_2_);
                return (d0 < d2) ? -1 : ((d0 > d2) ? 1 : 0);
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
            }
        }
    }
}
