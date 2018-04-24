package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.common.registry.*;
import io.netty.buffer.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.utils.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;

public class EntitySpellBat extends EntityMob implements IEntityAdditionalSpawnData
{
    private BlockPos currentFlightTarget;
    public EntityLivingBase owner;
    FocusPackage focusPackage;
    private UUID ownerUniqueId;
    private static final DataParameter<Boolean> FRIENDLY;
    public int damBonus;
    private int attackTime;
    FocusEffect[] effects;
    public int color;
    
    public EntitySpellBat(final World world) {
        super(world);
        this.owner = null;
        this.damBonus = 0;
        this.effects = null;
        this.color = 16777215;
        this.func_70105_a(0.5f, 0.9f);
    }
    
    public EntitySpellBat(final FocusPackage pac, final boolean friendly) {
        super(pac.world);
        this.owner = null;
        this.damBonus = 0;
        this.effects = null;
        this.color = 16777215;
        this.func_70105_a(0.5f, 0.9f);
        this.focusPackage = pac;
        this.setOwner(pac.getCaster());
        this.setIsFriendly(friendly);
    }
    
    public void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntitySpellBat.FRIENDLY, (Object)false);
    }
    
    public boolean getIsFriendly() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntitySpellBat.FRIENDLY);
    }
    
    public void setIsFriendly(final boolean par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntitySpellBat.FRIENDLY, (Object)par1);
    }
    
    public void writeSpawnData(final ByteBuf data) {
        Utils.writeNBTTagCompoundToBuffer(data, this.focusPackage.serialize());
    }
    
    public void readSpawnData(final ByteBuf data) {
        try {
            (this.focusPackage = new FocusPackage()).deserialize(Utils.readNBTTagCompoundFromBuffer(data));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setOwner(@Nullable final EntityLivingBase ownerIn) {
        this.owner = ownerIn;
        this.ownerUniqueId = ((ownerIn == null) ? null : ownerIn.func_110124_au());
    }
    
    @Nullable
    public EntityLivingBase getOwner() {
        if (this.owner == null && this.ownerUniqueId != null && this.field_70170_p instanceof WorldServer) {
            final Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.ownerUniqueId);
            if (entity instanceof EntityLivingBase) {
                this.owner = (EntityLivingBase)entity;
            }
        }
        return this.owner;
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
        return SoundEvents.field_187740_w;
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
    
    public Team func_96124_cp() {
        final EntityLivingBase entitylivingbase = this.getOwner();
        if (entitylivingbase != null) {
            return entitylivingbase.func_96124_cp();
        }
        return super.func_96124_cp();
    }
    
    public boolean func_184191_r(final Entity otherEntity) {
        final EntityLivingBase owner = this.getOwner();
        if (otherEntity == owner) {
            return true;
        }
        if (owner != null) {
            return owner.func_184191_r(otherEntity) || otherEntity.func_184191_r((Entity)owner);
        }
        return super.func_184191_r(otherEntity);
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (!this.field_70170_p.field_72995_K && (this.field_70173_aa > 600 || this.getOwner() == null)) {
            this.func_70106_y();
        }
        this.field_70181_x *= 0.6000000238418579;
        if (this.func_70089_S() && this.field_70170_p.field_72995_K) {
            if (this.effects == null) {
                this.effects = this.focusPackage.getFocusEffects();
                int r = 0;
                int g = 0;
                int b = 0;
                for (final FocusEffect ef : this.effects) {
                    final Color c = new Color(FocusEngine.getElementColor(ef.getKey()));
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }
                r /= this.effects.length;
                g /= this.effects.length;
                b /= this.effects.length;
                final Color c2 = new Color(r, g, b);
                this.color = c2.getRGB();
            }
            if (this.effects != null && this.effects.length > 0) {
                final FocusEffect eff = this.effects[this.field_70146_Z.nextInt(this.effects.length)];
                eff.renderParticleFX(this.field_70170_p, this.field_70165_t + this.field_70170_p.field_73012_v.nextGaussian() * 0.125, this.field_70163_u + this.field_70131_O / 2.0f + this.field_70170_p.field_73012_v.nextGaussian() * 0.125, this.field_70161_v + this.field_70170_p.field_73012_v.nextGaussian() * 0.125, 0.0, 0.0, 0.0);
            }
        }
    }
    
    protected void func_70619_bc() {
        super.func_70619_bc();
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        final BlockPos blockpos = new BlockPos((Entity)this);
        final BlockPos blockpos2 = blockpos.func_177984_a();
        if (this.func_70638_az() == null) {
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
            this.func_70624_b(this.findTargetToAttack());
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
        if (!this.getIsFriendly() && this.func_70638_az() instanceof EntityPlayer && ((EntityPlayer)this.func_70638_az()).field_71075_bZ.field_75102_a) {
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
        return super.func_70097_a(par1DamageSource, par2);
    }
    
    protected void attackEntity(final Entity target, final float par2) {
        if (this.attackTime <= 0 && par2 < Math.max(2.5f, target.field_70130_N * 1.1f) && target.func_174813_aQ().field_72337_e > this.func_174813_aQ().field_72338_b && target.func_174813_aQ().field_72338_b < this.func_174813_aQ().field_72337_e) {
            this.attackTime = 40;
            if (!this.field_70170_p.field_72995_K) {
                final RayTraceResult ray = new RayTraceResult(target);
                ray.field_72307_f = target.func_174791_d().func_72441_c(0.0, (double)(target.field_70131_O / 2.0f), 0.0);
                final Trajectory tra = new Trajectory(this.func_174791_d(), this.func_174791_d().func_72444_a(ray.field_72307_f));
                FocusEngine.runFocusPackage(this.focusPackage.copy(this.getOwner()), new Trajectory[] { tra }, new RayTraceResult[] { ray });
                this.func_70606_j(this.func_110143_aJ() - 1.0f);
            }
            this.func_184185_a(SoundEvents.field_187743_y, 0.5f, 0.9f + this.field_70170_p.field_73012_v.nextFloat() * 0.2f);
        }
    }
    
    protected void func_82167_n(final Entity entityIn) {
        if (this.getIsFriendly()) {
            return;
        }
        super.func_82167_n(entityIn);
    }
    
    protected EntityLivingBase findTargetToAttack() {
        final double var1 = 12.0;
        final List<EntityLivingBase> list = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends EntityLivingBase>)EntityLivingBase.class, var1);
        double d = Double.MAX_VALUE;
        EntityLivingBase ret = null;
        for (final EntityLivingBase e : list) {
            if (e.field_70128_L) {
                continue;
            }
            if (this.getIsFriendly()) {
                if (!EntityUtils.isFriendly((Entity)this.getOwner(), (Entity)e)) {
                    System.out.println(e);
                    continue;
                }
            }
            else {
                if (EntityUtils.isFriendly((Entity)this.getOwner(), (Entity)e)) {
                    continue;
                }
                if (this.func_184191_r((Entity)e)) {
                    continue;
                }
            }
            final double ed = this.func_70068_e((Entity)e);
            if (ed >= d) {
                continue;
            }
            d = ed;
            ret = e;
        }
        return ret;
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.ownerUniqueId = nbt.func_186857_a("OwnerUUID");
        this.setIsFriendly(nbt.func_74767_n("friendly"));
        try {
            (this.focusPackage = new FocusPackage()).deserialize(nbt.func_74775_l("pack"));
        }
        catch (Exception ex) {}
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        if (this.ownerUniqueId != null) {
            nbt.func_186854_a("OwnerUUID", this.ownerUniqueId);
        }
        nbt.func_74782_a("pack", (NBTBase)this.focusPackage.serialize());
        nbt.func_74757_a("friendly", this.getIsFriendly());
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
    
    protected boolean func_146066_aG() {
        return false;
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    static {
        FRIENDLY = EntityDataManager.func_187226_a((Class)EntitySpellBat.class, DataSerializers.field_187198_h);
    }
}
