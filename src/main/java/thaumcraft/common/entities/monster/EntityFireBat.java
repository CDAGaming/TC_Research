package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.network.datasync.*;

public class EntityFireBat extends EntityMob
{
    private BlockPos currentFlightTarget;
    public EntityLivingBase owner;
    private static final DataParameter<Boolean> HANGING;
    public int damBonus;
    private int attackTime;
    
    public EntityFireBat(final World par1World) {
        super(par1World);
        this.owner = null;
        this.damBonus = 0;
        this.func_70105_a(0.5f, 0.9f);
        this.setIsBatHanging(true);
        this.field_70178_ae = true;
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityFireBat.HANGING, (Object)false);
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b() {
        return 15728880;
    }
    
    public float func_70013_c() {
        return 1.0f;
    }
    
    protected float func_70599_aP() {
        return 0.1f;
    }
    
    protected float func_70647_i() {
        return super.func_70647_i() * 0.95f;
    }
    
    protected SoundEvent func_184639_G() {
        return (this.getIsBatHanging() && this.field_70146_Z.nextInt(4) != 0) ? null : SoundEvents.field_187740_w;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187743_y;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187742_x;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(5.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0);
    }
    
    public boolean getIsBatHanging() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityFireBat.HANGING);
    }
    
    public void setIsBatHanging(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityFireBat.HANGING, (Object)par1);
    }
    
    public void func_70636_d() {
        if (this.func_70026_G()) {
            this.func_70097_a(DamageSource.field_76369_e, 1.0f);
        }
        super.func_70636_d();
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getIsBatHanging()) {
            final double field_70159_w = 0.0;
            this.field_70179_y = field_70159_w;
            this.field_70181_x = field_70159_w;
            this.field_70159_w = field_70159_w;
            this.field_70163_u = MathHelper.func_76128_c(this.field_70163_u) + 1.0 - this.field_70131_O;
        }
        else {
            this.field_70181_x *= 0.6000000238418579;
        }
    }
    
    protected void func_70619_bc() {
        super.func_70619_bc();
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        final BlockPos blockpos = new BlockPos((Entity)this);
        final BlockPos blockpos2 = blockpos.func_177984_a();
        if (this.getIsBatHanging()) {
            if (!this.field_70170_p.func_180495_p(blockpos2).func_185915_l()) {
                this.setIsBatHanging(false);
                this.field_70170_p.func_180498_a((EntityPlayer)null, 1025, blockpos, 0);
            }
            else {
                if (this.field_70146_Z.nextInt(200) == 0) {
                    this.field_70759_as = this.field_70146_Z.nextInt(360);
                }
                if (this.field_70170_p.func_72890_a((Entity)this, 4.0) != null) {
                    this.setIsBatHanging(false);
                    this.field_70170_p.func_180498_a((EntityPlayer)null, 1025, blockpos, 0);
                }
            }
        }
        else if (this.func_70638_az() == null) {
            if (this.currentFlightTarget != null && (!this.field_70170_p.func_175623_d(this.currentFlightTarget) || this.currentFlightTarget.func_177956_o() < 1)) {
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
            this.field_191988_bg = 0.5f;
            this.field_70177_z += var5;
            if (this.field_70146_Z.nextInt(100) == 0 && this.field_70170_p.func_180495_p(blockpos2).func_185915_l()) {
                this.setIsBatHanging(true);
            }
        }
        else {
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
        if (this.func_70638_az() == null) {
            this.func_70624_b(this.findPlayerToAttack());
        }
        else if (this.func_70638_az().func_70089_S()) {
            final float f = this.func_70638_az().func_70032_d((Entity)this);
            if (this.func_70685_l((Entity)this.func_70638_az())) {
                this.attackEntity((Entity)this.func_70638_az(), f);
            }
        }
        else {
            this.func_70624_b((EntityLivingBase)null);
        }
        if (this.func_70638_az() instanceof EntityPlayer && ((EntityPlayer)this.func_70638_az()).field_71075_bZ.field_75102_a) {
            this.func_70624_b((EntityLivingBase)null);
        }
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public void func_180430_e(final float par1, final float damageMultiplier) {
    }
    
    protected void func_184231_a(final double p_180433_1_, final boolean p_180433_3_, final IBlockState state, final BlockPos pos) {
    }
    
    public boolean func_145773_az() {
        return true;
    }
    
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        if (this.func_180431_b(par1DamageSource) || par1DamageSource.func_76347_k() || par1DamageSource.func_94541_c()) {
            return false;
        }
        if (!this.field_70170_p.field_72995_K && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }
        return super.func_70097_a(par1DamageSource, par2);
    }
    
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (this.attackTime <= 0 && par2 < Math.max(2.5f, par1Entity.field_70130_N * 1.1f) && par1Entity.func_174813_aQ().field_72337_e > this.func_174813_aQ().field_72338_b && par1Entity.func_174813_aQ().field_72338_b < this.func_174813_aQ().field_72337_e) {
            this.attackTime = 20;
            if (this.field_70170_p.field_73012_v.nextInt(10) == 0 && !this.field_70170_p.field_72995_K) {
                par1Entity.field_70172_ad = 0;
                this.field_70170_p.func_72885_a((Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.5f, false, false);
                this.func_70106_y();
            }
            this.func_184185_a(SoundEvents.field_187743_y, 0.5f, 0.9f + this.field_70170_p.field_73012_v.nextFloat() * 0.2f);
        }
    }
    
    protected EntityLivingBase findPlayerToAttack() {
        final double var1 = 12.0;
        return (EntityLivingBase)this.field_70170_p.func_72890_a((Entity)this, var1);
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setIsBatHanging(nbt.func_74767_n("hang"));
        this.damBonus = nbt.func_74771_c("damBonus");
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74757_a("hang", this.getIsBatHanging());
        nbt.func_74774_a("damBonus", (byte)this.damBonus);
    }
    
    public boolean func_70601_bi() {
        final int i = MathHelper.func_76128_c(this.field_70165_t);
        final int j = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
        final int k = MathHelper.func_76128_c(this.field_70161_v);
        final BlockPos blockpos = new BlockPos(i, j, k);
        final int var4 = this.field_70170_p.func_175699_k(blockpos);
        final byte var5 = 7;
        return var4 <= this.field_70146_Z.nextInt(var5) && super.func_70601_bi();
    }
    
    protected Item func_146068_u() {
        return Items.field_151016_H;
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    static {
        HANGING = EntityDataManager.func_187226_a((Class)EntityFireBat.class, DataSerializers.field_187198_h);
    }
}
