package thaumcraft.common.entities.monster.tainted;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.client.particle.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.blocks.world.taint.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import thaumcraft.common.config.*;
import net.minecraft.network.datasync.*;

public class EntityTaintSwarm extends EntityMob implements ITaintedMob
{
    private BlockPos currentFlightTarget;
    private static final DataParameter<Boolean> SUMMONED;
    public int damBonus;
    public ArrayList swarm;
    private int attackTime;
    
    public EntityTaintSwarm(final World par1World) {
        super(par1World);
        this.damBonus = 0;
        this.swarm = new ArrayList();
        this.func_70105_a(2.0f, 2.0f);
    }
    
    public boolean func_70686_a(final Class clazz) {
        return !ITaintedMob.class.isAssignableFrom(clazz);
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        return otherEntity instanceof ITaintedMob || super.func_184191_r(otherEntity);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityTaintSwarm.SUMMONED, (Object)false);
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b() {
        return 15728880;
    }
    
    protected boolean func_70692_ba() {
        return true;
    }
    
    public float func_70013_c() {
        return 1.0f;
    }
    
    protected float func_70599_aP() {
        return 0.1f;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.swarm;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.swarmattack;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.swarmattack;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a((double)(2 + this.damBonus));
    }
    
    public boolean getIsSummoned() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityTaintSwarm.SUMMONED);
    }
    
    public void setIsSummoned(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityTaintSwarm.SUMMONED, (Object)par1);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        this.field_70181_x *= 0.6000000238418579;
        if (this.field_70170_p.field_72995_K) {
            for (int a = 0; a < this.swarm.size(); ++a) {
                if (this.swarm.get(a) == null || !this.swarm.get(a).func_187113_k()) {
                    this.swarm.remove(a);
                    break;
                }
            }
            if (this.swarm.size() < 30) {
                this.swarm.add(FXDispatcher.INSTANCE.swarmParticleFX((Entity)this, 0.22f, 15.0f, 0.08f));
            }
        }
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        if (this.func_70638_az() == null) {
            if (this.getIsSummoned()) {
                this.func_70097_a(DamageSource.field_76377_j, 5.0f);
            }
            if (this.currentFlightTarget != null && (!this.field_70170_p.func_175623_d(this.currentFlightTarget) || this.currentFlightTarget.func_177956_o() < 1 || this.currentFlightTarget.func_177956_o() > this.field_70170_p.func_175725_q(this.currentFlightTarget).func_177981_b(2).func_177956_o() || !TaintHelper.isNearTaintSeed(this.field_70170_p, this.currentFlightTarget))) {
                this.currentFlightTarget = null;
            }
            if (this.currentFlightTarget == null || this.field_70146_Z.nextInt(30) == 0 || this.func_174831_c(this.currentFlightTarget) < 4.0) {
                this.currentFlightTarget = new BlockPos((int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7), (int)this.field_70163_u + this.field_70146_Z.nextInt(6) - 2, (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7));
            }
            final double var1 = this.currentFlightTarget.func_177958_n() + 0.5 - this.field_70165_t;
            final double var2 = this.currentFlightTarget.func_177956_o() + 0.1 - this.field_70163_u;
            final double var3 = this.currentFlightTarget.func_177952_p() + 0.5 - this.field_70161_v;
            this.field_70159_w += (Math.signum(var1) * 0.5 - this.field_70159_w) * 0.015000000014901161;
            this.field_70181_x += (Math.signum(var2) * 0.699999988079071 - this.field_70181_x) * 0.10000000149011612;
            this.field_70179_y += (Math.signum(var3) * 0.5 - this.field_70179_y) * 0.015000000014901161;
            final float var4 = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0 / 3.141592653589793) - 90.0f;
            final float var5 = MathHelper.func_76142_g(var4 - this.field_70177_z);
            this.field_191988_bg = 0.1f;
            this.field_70177_z += var5;
        }
        else if (this.func_70638_az() != null) {
            final double var1 = this.func_70638_az().field_70165_t - this.field_70165_t;
            final double var2 = this.func_70638_az().field_70163_u + this.func_70638_az().func_70047_e() - this.field_70163_u;
            final double var3 = this.func_70638_az().field_70161_v - this.field_70161_v;
            this.field_70159_w += (Math.signum(var1) * 0.5 - this.field_70159_w) * 0.025000000149011613;
            this.field_70181_x += (Math.signum(var2) * 0.699999988079071 - this.field_70181_x) * 0.10000000149011612;
            this.field_70179_y += (Math.signum(var3) * 0.5 - this.field_70179_y) * 0.02500000001490116;
            final float var4 = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0 / 3.141592653589793) - 90.0f;
            final float var5 = MathHelper.func_76142_g(var4 - this.field_70177_z);
            this.field_191988_bg = 0.1f;
            this.field_70177_z += var5;
        }
        if (this.func_70638_az() == null) {
            this.func_70624_b((EntityLivingBase)this.findPlayerToAttack());
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
    
    protected void func_70619_bc() {
        super.func_70619_bc();
    }
    
    protected boolean func_70041_e_() {
        return false;
    }
    
    public void func_180430_e(final float distance, final float damageMultiplier) {
    }
    
    public boolean func_145773_az() {
        return true;
    }
    
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        return !this.func_180431_b(par1DamageSource) && super.func_70097_a(par1DamageSource, par2);
    }
    
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (this.attackTime <= 0 && par2 < 3.0f && par1Entity.func_174813_aQ().field_72337_e > this.func_174813_aQ().field_72338_b && par1Entity.func_174813_aQ().field_72338_b < this.func_174813_aQ().field_72337_e) {
            if (this.getIsSummoned()) {
                ((EntityLivingBase)par1Entity).field_70718_bc = 100;
            }
            this.attackTime = 10 + this.field_70146_Z.nextInt(5);
            final double mx = par1Entity.field_70159_w;
            final double my = par1Entity.field_70181_x;
            final double mz = par1Entity.field_70179_y;
            if (this.func_70652_k(par1Entity) && !this.field_70170_p.field_72995_K && par1Entity instanceof EntityLivingBase) {
                ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76437_t, 100, 0));
            }
            par1Entity.field_70160_al = false;
            par1Entity.field_70159_w = mx;
            par1Entity.field_70181_x = my;
            par1Entity.field_70179_y = mz;
            this.func_184185_a(SoundsTC.swarmattack, 0.3f, 0.9f + this.field_70170_p.field_73012_v.nextFloat() * 0.2f);
        }
    }
    
    protected Entity findPlayerToAttack() {
        final double var1 = 8.0;
        return (Entity)(this.getIsSummoned() ? null : this.field_70170_p.func_72890_a((Entity)this, var1));
    }
    
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        this.setIsSummoned(par1NBTTagCompound.func_74767_n("summoned"));
        this.damBonus = par1NBTTagCompound.func_74771_c("damBonus");
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74757_a("summoned", this.getIsSummoned());
        par1NBTTagCompound.func_74774_a("damBonus", (byte)this.damBonus);
    }
    
    public boolean func_70601_bi() {
        final int var1 = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
        final int var2 = MathHelper.func_76128_c(this.field_70165_t);
        final int var3 = MathHelper.func_76128_c(this.field_70161_v);
        final int var4 = this.field_70170_p.func_175699_k(new BlockPos(var2, var1, var3));
        final byte var5 = 7;
        return var4 <= this.field_70146_Z.nextInt(var5) && super.func_70601_bi();
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        if (this.field_70170_p.field_73012_v.nextBoolean()) {
            this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        }
    }
    
    static {
        SUMMONED = EntityDataManager.func_187226_a((Class)EntityTaintSwarm.class, DataSerializers.field_187198_h);
    }
}
