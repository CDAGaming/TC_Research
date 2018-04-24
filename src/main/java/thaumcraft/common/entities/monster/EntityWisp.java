package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import thaumcraft.client.fx.*;
import thaumcraft.api.aspects.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.api.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;

public class EntityWisp extends EntityFlying implements IMob
{
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;
    private BlockPos currentFlightTarget;
    private static final DataParameter<String> TYPE;
    
    public EntityWisp(final World world) {
        super(world);
        this.courseChangeCooldown = 0;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.func_70105_a(0.9f, 0.9f);
        this.field_70728_aV = 5;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(22.0);
        this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0);
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public int func_70682_h(final int par1) {
        return par1;
    }
    
    public boolean func_70097_a(final DamageSource damagesource, final float i) {
        if (damagesource.func_76346_g() instanceof EntityLivingBase) {
            this.func_70624_b((EntityLivingBase)damagesource.func_76346_g());
            this.aggroCooldown = 200;
        }
        return super.func_70097_a(damagesource, i);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityWisp.TYPE, (Object)String.valueOf(""));
    }
    
    public void func_70645_a(final DamageSource par1DamageSource) {
        super.func_70645_a(par1DamageSource);
        if (this.field_70170_p.field_72995_K) {
            FXDispatcher.INSTANCE.burst(this.field_70165_t, this.field_70163_u + 0.44999998807907104, this.field_70161_v, 1.0f);
        }
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70170_p.field_72995_K && this.field_70173_aa <= 1) {
            FXDispatcher.INSTANCE.burst(this.field_70165_t, this.field_70163_u, this.field_70161_v, 10.0f);
        }
        if (this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextBoolean() && Aspect.getAspect(this.getType()) != null) {
            FXDispatcher.INSTANCE.drawWispParticles(this.field_70165_t + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.7f, this.field_70163_u + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.7f, this.field_70161_v + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.7f, 0.0, 0.0, 0.0, Aspect.getAspect(this.getType()).getColor(), 0);
        }
        this.field_70181_x *= 0.6000000238418579;
    }
    
    public String getType() {
        return (String)this.func_184212_Q().func_187225_a((DataParameter)EntityWisp.TYPE);
    }
    
    public void setType(final String t) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityWisp.TYPE, (Object)String.valueOf(t));
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        if (this.func_70613_aW()) {
            if (!this.field_70170_p.field_72995_K && Aspect.getAspect(this.getType()) == null) {
                if (this.field_70170_p.field_73012_v.nextInt(10) != 0) {
                    final ArrayList<Aspect> as = Aspect.getPrimalAspects();
                    this.setType(as.get(this.field_70170_p.field_73012_v.nextInt(as.size())).getTag());
                }
                else {
                    final ArrayList<Aspect> as = Aspect.getCompoundAspects();
                    this.setType(as.get(this.field_70170_p.field_73012_v.nextInt(as.size())).getTag());
                }
            }
            if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
                this.func_70106_y();
            }
            this.prevAttackCounter = this.attackCounter;
            final double attackrange = 16.0;
            if (this.func_70638_az() == null || !this.func_70685_l((Entity)this.func_70638_az())) {
                if (this.currentFlightTarget != null && (!this.field_70170_p.func_175623_d(this.currentFlightTarget) || this.currentFlightTarget.func_177956_o() < 1 || this.currentFlightTarget.func_177956_o() > this.field_70170_p.func_175725_q(this.currentFlightTarget).func_177981_b(8).func_177956_o())) {
                    this.currentFlightTarget = null;
                }
                if (this.currentFlightTarget == null || this.field_70146_Z.nextInt(30) == 0 || this.func_174831_c(this.currentFlightTarget) < 4.0) {
                    this.currentFlightTarget = new BlockPos((int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7), (int)this.field_70163_u + this.field_70146_Z.nextInt(6) - 2, (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7));
                }
                final double var1 = this.currentFlightTarget.func_177958_n() + 0.5 - this.field_70165_t;
                final double var2 = this.currentFlightTarget.func_177956_o() + 0.1 - this.field_70163_u;
                final double var3 = this.currentFlightTarget.func_177952_p() + 0.5 - this.field_70161_v;
                this.field_70159_w += (Math.signum(var1) * 0.5 - this.field_70159_w) * 0.10000000149011612;
                this.field_70181_x += (Math.signum(var2) * 0.699999988079071 - this.field_70181_x) * 0.10000000149011612;
                this.field_70179_y += (Math.signum(var3) * 0.5 - this.field_70179_y) * 0.10000000149011612;
                final float var4 = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0 / 3.141592653589793) - 90.0f;
                final float var5 = MathHelper.func_76142_g(var4 - this.field_70177_z);
                this.field_191988_bg = 0.15f;
                this.field_70177_z += var5;
            }
            else if (this.func_70068_e((Entity)this.func_70638_az()) > attackrange * attackrange / 2.0 && this.func_70685_l((Entity)this.func_70638_az())) {
                final double var1 = this.func_70638_az().field_70165_t - this.field_70165_t;
                final double var2 = this.func_70638_az().field_70163_u + this.func_70638_az().func_70047_e() * 0.66f - this.field_70163_u;
                final double var3 = this.func_70638_az().field_70161_v - this.field_70161_v;
                this.field_70159_w += (Math.signum(var1) * 0.5 - this.field_70159_w) * 0.10000000149011612;
                this.field_70181_x += (Math.signum(var2) * 0.699999988079071 - this.field_70181_x) * 0.10000000149011612;
                this.field_70179_y += (Math.signum(var3) * 0.5 - this.field_70179_y) * 0.10000000149011612;
                final float var4 = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0 / 3.141592653589793) - 90.0f;
                final float var5 = MathHelper.func_76142_g(var4 - this.field_70177_z);
                this.field_191988_bg = 0.5f;
                this.field_70177_z += var5;
            }
            if (this.func_70638_az() instanceof EntityPlayer && ((EntityPlayer)this.func_70638_az()).field_71075_bZ.field_75102_a) {
                this.func_70624_b((EntityLivingBase)null);
            }
            if (this.func_70638_az() != null && this.func_70638_az().field_70128_L) {
                this.func_70624_b((EntityLivingBase)null);
            }
            --this.aggroCooldown;
            if (this.field_70170_p.field_73012_v.nextInt(1000) == 0 && (this.func_70638_az() == null || this.aggroCooldown-- <= 0)) {
                this.func_70624_b((EntityLivingBase)this.field_70170_p.func_72890_a((Entity)this, 16.0));
                if (this.func_70638_az() != null) {
                    this.aggroCooldown = 50;
                }
            }
            if (!this.field_70128_L && this.func_70638_az() != null && this.func_70638_az().func_70068_e((Entity)this) < attackrange * attackrange) {
                final double d5 = this.func_70638_az().field_70165_t - this.field_70165_t;
                final double d6 = this.func_70638_az().func_174813_aQ().field_72338_b + this.func_70638_az().field_70131_O / 2.0f - (this.field_70163_u + this.field_70131_O / 2.0f);
                final double d7 = this.func_70638_az().field_70161_v - this.field_70161_v;
                final float n = -(float)Math.atan2(d5, d7) * 180.0f / 3.141593f;
                this.field_70177_z = n;
                this.field_70761_aq = n;
                if (this.func_70685_l((Entity)this.func_70638_az())) {
                    ++this.attackCounter;
                    if (this.attackCounter == 20) {
                        this.func_184185_a(SoundsTC.zap, 1.0f, 1.1f);
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXWispZap(this.func_145782_y(), this.func_70638_az().func_145782_y()), new NetworkRegistry.TargetPoint(this.field_70170_p.field_73011_w.getDimension(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 32.0));
                        final float damage = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
                        if (Math.abs(this.func_70638_az().field_70159_w) > 0.10000000149011612 || Math.abs(this.func_70638_az().field_70181_x) > 0.10000000149011612 || Math.abs(this.func_70638_az().field_70179_y) > 0.10000000149011612) {
                            if (this.field_70170_p.field_73012_v.nextFloat() < 0.4f) {
                                this.func_70638_az().func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), damage);
                            }
                        }
                        else if (this.field_70170_p.field_73012_v.nextFloat() < 0.66f) {
                            this.func_70638_az().func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), damage + 1.0f);
                        }
                        this.attackCounter = -20 + this.field_70170_p.field_73012_v.nextInt(20);
                    }
                }
                else if (this.attackCounter > 0) {
                    --this.attackCounter;
                }
            }
        }
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.wisplive;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187659_cY;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.wispdead;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        if (Aspect.getAspect(this.getType()) != null) {
            this.func_70099_a(ThaumcraftApiHelper.makeCrystal(Aspect.getAspect(this.getType())), 0.0f);
        }
    }
    
    protected float func_70599_aP() {
        return 0.25f;
    }
    
    protected boolean func_70692_ba() {
        return true;
    }
    
    public boolean func_70601_bi() {
        int count = 0;
        try {
            final List l = this.field_70170_p.func_72872_a((Class)EntityWisp.class, this.func_174813_aQ().func_72314_b(16.0, 16.0, 16.0));
            if (l != null) {
                count = l.size();
            }
        }
        catch (Exception ex) {}
        return count < 8 && this.field_70170_p.func_175659_aa() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.func_70601_bi();
    }
    
    protected boolean isValidLightLevel() {
        final BlockPos blockpos = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
        if (this.field_70170_p.func_175642_b(EnumSkyBlock.SKY, blockpos) > this.field_70146_Z.nextInt(32)) {
            return false;
        }
        int i = this.field_70170_p.func_175671_l(blockpos);
        if (this.field_70170_p.func_72911_I()) {
            final int j = this.field_70170_p.func_175657_ab();
            this.field_70170_p.func_175692_b(10);
            i = this.field_70170_p.func_175671_l(blockpos);
            this.field_70170_p.func_175692_b(j);
        }
        return i <= this.field_70146_Z.nextInt(8);
    }
    
    public void func_70014_b(final NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.func_74778_a("Type", this.getType());
    }
    
    public void func_70037_a(final NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        this.setType(nbttagcompound.func_74779_i("Type"));
    }
    
    public int func_70641_bl() {
        return 2;
    }
    
    static {
        TYPE = EntityDataManager.func_187226_a((Class)EntityWisp.class, DataSerializers.field_187194_d);
    }
}
