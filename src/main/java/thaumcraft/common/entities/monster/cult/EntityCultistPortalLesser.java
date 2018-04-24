package thaumcraft.common.entities.monster.cult;

import net.minecraft.entity.monster.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.network.datasync.*;

public class EntityCultistPortalLesser extends EntityMob
{
    private static final DataParameter<Boolean> ACTIVE;
    int stagecounter;
    public int activeCounter;
    public int pulse;
    
    public EntityCultistPortalLesser(final World par1World) {
        super(par1World);
        this.stagecounter = 100;
        this.activeCounter = 0;
        this.pulse = 0;
        this.field_70178_ae = true;
        this.field_70728_aV = 10;
        this.func_70105_a(1.5f, 3.0f);
    }
    
    public int func_70658_aO() {
        return 4;
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityCultistPortalLesser.ACTIVE, (Object)false);
    }
    
    public boolean isActive() {
        return (boolean)this.func_184212_Q().func_187225_a((DataParameter)EntityCultistPortalLesser.ACTIVE);
    }
    
    public void setActive(final boolean active) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityCultistPortalLesser.ACTIVE, (Object)active);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(0.0);
        this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0);
    }
    
    protected boolean func_70692_ba() {
        return false;
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        nbt.func_74757_a("active", this.isActive());
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        this.setActive(nbt.func_74767_n("active"));
    }
    
    public boolean func_70067_L() {
        return true;
    }
    
    public boolean func_70104_M() {
        return false;
    }
    
    public void func_70091_d(final MoverType mt, final double par1, final double par3, final double par5) {
    }
    
    public void func_70636_d() {
    }
    
    public boolean func_70112_a(final double par1) {
        return par1 < 4096.0;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_70070_b() {
        return 15728880;
    }
    
    public float func_70013_c() {
        return 1.0f;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.isActive()) {
            ++this.activeCounter;
        }
        if (!this.field_70170_p.field_72995_K) {
            if (!this.isActive()) {
                if (this.field_70173_aa % 10 == 0) {
                    final EntityPlayer p = this.field_70170_p.func_72890_a((Entity)this, 32.0);
                    if (p != null) {
                        this.setActive(true);
                        this.func_184185_a(SoundsTC.craftstart, 1.0f, 1.0f);
                    }
                }
            }
            else if (this.stagecounter-- <= 0) {
                final EntityPlayer p = this.field_70170_p.func_72890_a((Entity)this, 32.0);
                if (p != null && this.func_70685_l((Entity)p)) {
                    int count = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 6 : ((this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) ? 4 : 2);
                    try {
                        final List l = this.field_70170_p.func_72872_a((Class)EntityCultist.class, this.func_174813_aQ().func_72314_b(32.0, 32.0, 32.0));
                        if (l != null) {
                            count -= l.size();
                        }
                    }
                    catch (Exception ex) {}
                    if (count > 0) {
                        this.field_70170_p.func_72960_a((Entity)this, (byte)16);
                        this.spawnMinions();
                    }
                }
                this.stagecounter = 50 + this.field_70146_Z.nextInt(50);
            }
        }
        if (this.pulse > 0) {
            --this.pulse;
        }
    }
    
    int getTiming() {
        final List<Entity> l = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends Entity>)EntityCultist.class, 32.0);
        return l.size() * 20;
    }
    
    void spawnMinions() {
        EntityCultist cultist = null;
        if (this.field_70146_Z.nextFloat() > 0.33) {
            cultist = new EntityCultistKnight(this.field_70170_p);
        }
        else {
            cultist = new EntityCultistCleric(this.field_70170_p);
        }
        cultist.func_70107_b(this.field_70165_t + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat(), this.field_70163_u + 0.25, this.field_70161_v + this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat());
        cultist.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Vec3i)cultist.func_180425_c())), null);
        this.field_70170_p.func_72838_d((Entity)cultist);
        cultist.func_70656_aK();
        cultist.func_184185_a(SoundsTC.wandfail, 1.0f, 1.0f);
        this.func_70097_a(DamageSource.field_76380_i, (float)(5 + this.field_70146_Z.nextInt(5)));
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    public void func_70100_b_(final EntityPlayer p) {
        if (this.func_70068_e((Entity)p) < 3.0 && p.func_70097_a(DamageSource.func_76354_b((Entity)this, (Entity)this), 4.0f)) {
            this.func_184185_a(SoundsTC.zap, 1.0f, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1f + 1.0f);
        }
    }
    
    protected float func_70599_aP() {
        return 0.75f;
    }
    
    public int func_70627_aG() {
        return 540;
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.monolith;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundsTC.zap;
    }
    
    protected SoundEvent func_184615_bR() {
        return SoundsTC.shock;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean flag, final int fortune) {
    }
    
    @SideOnly(Side.CLIENT)
    public void func_70103_a(final byte msg) {
        if (msg == 16) {
            this.pulse = 10;
        }
        else {
            super.func_70103_a(msg);
        }
    }
    
    public void func_70690_d(final PotionEffect p_70690_1_) {
    }
    
    public void func_180430_e(final float distance, final float damageMultiplier) {
    }
    
    public void func_70645_a(final DamageSource p_70645_1_) {
        if (!this.field_70170_p.field_72995_K) {
            this.field_70170_p.func_72885_a((Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.5f, false, false);
        }
        super.func_70645_a(p_70645_1_);
    }
    
    static {
        ACTIVE = EntityDataManager.func_187226_a((Class)EntityCultistPortalLesser.class, DataSerializers.field_187198_h);
    }
}
