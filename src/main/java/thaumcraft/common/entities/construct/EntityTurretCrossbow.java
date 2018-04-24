package thaumcraft.common.entities.construct;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.*;
import net.minecraft.tileentity.*;
import net.minecraft.scoreboard.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.items.*;
import thaumcraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public class EntityTurretCrossbow extends EntityOwnedConstruct implements IRangedAttackMob
{
    int loadProgressInt;
    boolean isLoadInProgress;
    float loadProgress;
    float prevLoadProgress;
    public float loadProgressForRender;
    boolean attackedLastTick;
    int attackCount;
    
    public EntityTurretCrossbow(final World worldIn) {
        super(worldIn);
        this.loadProgressInt = 0;
        this.isLoadInProgress = false;
        this.loadProgress = 0.0f;
        this.prevLoadProgress = 0.0f;
        this.loadProgressForRender = 0.0f;
        this.attackedLastTick = false;
        this.attackCount = 0;
        this.func_70105_a(0.95f, 1.25f);
        this.field_70138_W = 0.0f;
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAttackRanged((IRangedAttackMob)this, 0.0, 20, 60, 24.0f));
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIWatchTarget((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestValidTarget(this, EntityLiving.class, 5, true, false, IMob.field_82192_a));
    }
    
    public EntityTurretCrossbow(final World worldIn, final BlockPos pos) {
        this(worldIn);
        this.func_70080_a(pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), pos.func_177952_p() + 0.5, 0.0f, 0.0f);
    }
    
    public void func_82196_d(final EntityLivingBase target, final float range) {
        if (this.func_184614_ca() != null && this.func_184614_ca().func_190916_E() > 0) {
            final EntityTippedArrow entityarrow = new EntityTippedArrow(this.field_70170_p, (EntityLivingBase)this);
            entityarrow.func_70239_b(2.25 + range * 2.0f + this.field_70146_Z.nextGaussian() * 0.25);
            entityarrow.func_184555_a(this.func_184614_ca());
            final Vec3d vec3d = this.func_70676_i(1.0f);
            if (!this.func_184218_aH()) {
                final EntityTippedArrow entityTippedArrow = entityarrow;
                entityTippedArrow.field_70165_t -= vec3d.field_72450_a * 0.8999999761581421;
                final EntityTippedArrow entityTippedArrow2 = entityarrow;
                entityTippedArrow2.field_70163_u -= vec3d.field_72448_b * 0.8999999761581421;
                final EntityTippedArrow entityTippedArrow3 = entityarrow;
                entityTippedArrow3.field_70161_v -= vec3d.field_72449_c * 0.8999999761581421;
            }
            else {
                final EntityTippedArrow entityTippedArrow4 = entityarrow;
                entityTippedArrow4.field_70165_t += vec3d.field_72450_a * 1.75;
                final EntityTippedArrow entityTippedArrow5 = entityarrow;
                entityTippedArrow5.field_70163_u += vec3d.field_72448_b * 1.75;
                final EntityTippedArrow entityTippedArrow6 = entityarrow;
                entityTippedArrow6.field_70161_v += vec3d.field_72449_c * 1.75;
            }
            entityarrow.field_70251_a = EntityArrow.PickupStatus.DISALLOWED;
            final double d0 = target.field_70165_t - this.field_70165_t;
            final double d2 = target.func_174813_aQ().field_72338_b + target.func_70047_e() + range * range * 3.0f - entityarrow.field_70163_u;
            final double d3 = target.field_70161_v - this.field_70161_v;
            entityarrow.func_70186_c(d0, d2, d3, 2.0f, 2.0f);
            this.field_70170_p.func_72838_d((Entity)entityarrow);
            this.field_70170_p.func_72960_a((Entity)this, (byte)16);
            this.func_184185_a(SoundEvents.field_187737_v, 1.0f, 1.0f / (this.func_70681_au().nextFloat() * 0.4f + 0.8f));
            this.func_184614_ca().func_190918_g(1);
            if (this.func_184614_ca().func_190916_E() <= 0) {
                this.func_184611_a(EnumHand.MAIN_HAND, (ItemStack)null);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte par1) {
        if (par1 == 16) {
            if (!this.field_82175_bq) {
                this.field_110158_av = -1;
                this.field_82175_bq = true;
            }
        }
        else if (par1 == 17) {
            if (!this.isLoadInProgress) {
                this.loadProgressInt = -1;
                this.isLoadInProgress = true;
            }
        }
        else {
            super.func_70103_a(par1);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public float getLoadProgress(final float pt) {
        float f1 = this.loadProgress - this.prevLoadProgress;
        if (f1 < 0.0f) {
            ++f1;
        }
        return this.prevLoadProgress + f1 * pt;
    }
    
    protected void func_82168_bl() {
        if (this.field_82175_bq) {
            ++this.field_110158_av;
            if (this.field_110158_av >= 6) {
                this.field_110158_av = 0;
                this.field_82175_bq = false;
            }
        }
        else {
            this.field_110158_av = 0;
        }
        this.field_70733_aJ = this.field_110158_av / 6.0f;
        if (this.isLoadInProgress) {
            ++this.loadProgressInt;
            if (this.loadProgressInt >= 10) {
                this.loadProgressInt = 0;
                this.isLoadInProgress = false;
            }
        }
        else {
            this.loadProgressInt = 0;
        }
        this.loadProgress = this.loadProgressInt / 10.0f;
    }
    
    public void func_70030_z() {
        this.prevLoadProgress = this.loadProgress;
        if (!this.field_70170_p.field_72995_K && (this.func_184614_ca() == null || this.func_184614_ca().func_190916_E() <= 0)) {
            final BlockPos p = this.func_180425_c().func_177977_b();
            final TileEntity t = this.field_70170_p.func_175625_s(p);
            if (t != null && t instanceof TileEntityDispenser && EnumFacing.func_82600_a(t.func_145832_p() & 0x7) == EnumFacing.UP) {
                final TileEntityDispenser d = (TileEntityDispenser)t;
                for (int a = 0; a < d.func_70302_i_(); ++a) {
                    if (d.func_70301_a(a) != null && d.func_70301_a(a).func_77973_b() instanceof ItemArrow) {
                        this.func_184611_a(EnumHand.MAIN_HAND, d.func_70298_a(a, d.func_70301_a(a).func_190916_E()));
                        this.func_184185_a(SoundsTC.ticks, 1.0f, 1.0f);
                        this.field_70170_p.func_72960_a((Entity)this, (byte)17);
                        break;
                    }
                }
            }
        }
        super.func_70030_z();
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
    
    public float func_70047_e() {
        return this.field_70131_O * 0.66f;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0);
    }
    
    public int func_70658_aO() {
        return 2;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.func_70638_az() != null && this.func_184191_r((Entity)this.func_70638_az())) {
            this.func_70624_b((EntityLivingBase)null);
        }
        if (!this.field_70170_p.field_72995_K) {
            this.field_70177_z = this.field_70759_as;
            if (this.field_70173_aa % 80 == 0) {
                this.func_70691_i(1.0f);
            }
            final int k = MathHelper.func_76128_c(this.field_70165_t);
            int l = MathHelper.func_76128_c(this.field_70163_u);
            final int i1 = MathHelper.func_76128_c(this.field_70161_v);
            if (BlockRailBase.func_176562_d(this.field_70170_p, new BlockPos(k, l - 1, i1))) {
                --l;
            }
            final BlockPos blockpos = new BlockPos(k, l, i1);
            final IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            if (BlockRailBase.func_176563_d(iblockstate) && iblockstate.func_177230_c() == BlocksTC.activatorRail) {
                final boolean ac = (boolean)iblockstate.func_177229_b((IProperty)BlockRailPowered.field_176569_M);
                this.func_94061_f(ac);
            }
        }
        else {
            this.func_82168_bl();
        }
    }
    
    public boolean func_70104_M() {
        return true;
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    @Override
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
    }
    
    @Override
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
    }
    
    public boolean func_70097_a(final DamageSource source, final float amount) {
        this.field_70177_z += (float)(this.func_70681_au().nextGaussian() * 45.0);
        this.field_70125_A += (float)(this.func_70681_au().nextGaussian() * 20.0);
        return super.func_70097_a(source, amount);
    }
    
    public void func_70653_a(final Entity p_70653_1_, final float p_70653_2_, final double p_70653_3_, final double p_70653_5_) {
        super.func_70653_a(p_70653_1_, p_70653_2_, p_70653_3_ / 10.0, p_70653_5_ / 10.0);
        if (this.field_70181_x > 0.1) {
            this.field_70181_x = 0.1;
        }
    }
    
    @Override
    protected boolean func_184645_a(final EntityPlayer player, final EnumHand hand) {
        if (!this.field_70170_p.field_72995_K && this.isOwner((EntityLivingBase)player) && !this.field_70128_L) {
            if (player.func_70093_af()) {
                this.func_184185_a(SoundsTC.zap, 1.0f, 1.0f);
                this.dropAmmo();
                this.func_70099_a(new ItemStack(ItemsTC.turretPlacer, 1, 0), 0.5f);
                this.func_70106_y();
                player.func_184609_a(hand);
            }
            else {
                player.openGui((Object)Thaumcraft.instance, 16, this.field_70170_p, this.func_145782_y(), 0, 0);
            }
            return true;
        }
        return super.func_184645_a(player, hand);
    }
    
    public void func_70091_d(final MoverType mt, final double x, final double y, final double z) {
        super.func_70091_d(mt, x / 20.0, y, z / 20.0);
    }
    
    @Override
    public void func_70645_a(final DamageSource cause) {
        super.func_70645_a(cause);
        if (!this.field_70170_p.field_72995_K) {
            this.dropAmmo();
        }
    }
    
    protected void dropAmmo() {
        if (this.func_184614_ca() != null) {
            this.func_70099_a(this.func_184614_ca(), 0.5f);
        }
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
        final float b = p_70628_2_ * 0.15f;
        if (this.field_70146_Z.nextFloat() < 0.2f + b) {
            this.func_70099_a(new ItemStack(ItemsTC.mind), 0.5f);
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
    }
    
    protected RayTraceResult getRayTraceResult() {
        final float f = this.field_70127_C + (this.field_70125_A - this.field_70127_C);
        final float f2 = this.field_70126_B + (this.field_70177_z - this.field_70126_B);
        final double d0 = this.field_70169_q + (this.field_70165_t - this.field_70169_q);
        final double d2 = this.field_70167_r + (this.field_70163_u - this.field_70167_r) + this.func_70047_e();
        final double d3 = this.field_70166_s + (this.field_70161_v - this.field_70166_s);
        final Vec3d vec3 = new Vec3d(d0, d2, d3);
        final float f3 = MathHelper.func_76134_b(-f2 * 0.017453292f - 3.1415927f);
        final float f4 = MathHelper.func_76126_a(-f2 * 0.017453292f - 3.1415927f);
        final float f5 = -MathHelper.func_76134_b(-f * 0.017453292f);
        final float f6 = MathHelper.func_76126_a(-f * 0.017453292f);
        final float f7 = f4 * f5;
        final float f8 = f3 * f5;
        final double d4 = 5.0;
        final Vec3d vec4 = vec3.func_72441_c(f7 * d4, f6 * d4, f8 * d4);
        return this.field_70170_p.func_147447_a(vec3, vec4, true, false, false);
    }
    
    public int func_70646_bf() {
        return 20;
    }
    
    public void func_184724_a(final boolean swingingArms) {
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
        
        public EntityAINearestValidTarget(final EntityTurretCrossbow this$0, final EntityCreature p_i45878_1_, final Class p_i45878_2_, final boolean p_i45878_3_) {
            this(this$0, p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
        }
        
        public EntityAINearestValidTarget(final EntityTurretCrossbow this$0, final EntityCreature p_i45879_1_, final Class p_i45879_2_, final boolean p_i45879_3_, final boolean p_i45879_4_) {
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
        
        protected boolean func_75296_a(final EntityLivingBase p_75296_1_, final boolean p_75296_2_) {
            return func_179445_a((EntityLiving)this.field_75299_d, p_75296_1_, p_75296_2_, this.field_75297_f) && this.field_75299_d.func_180485_d(new BlockPos((Entity)p_75296_1_));
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
