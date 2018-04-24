package thaumcraft.common.entities.monster.boss;

import thaumcraft.common.entities.monster.tainted.*;
import thaumcraft.api.entities.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.datasync.*;

public class EntityTaintacleGiant extends EntityTaintacle implements ITaintedMob, IEldritchMob
{
    protected final BossInfoServer bossInfo;
    private static final DataParameter<Integer> AGGRO;
    
    public EntityTaintacleGiant(final World par1World) {
        super(par1World);
        this.bossInfo = (BossInfoServer)new BossInfoServer(this.func_145748_c_(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS).func_186741_a(true);
        this.func_70105_a(1.1f, 6.0f);
        this.field_70728_aV = 20;
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(175.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(9.0);
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        EntityUtils.makeChampion(this, true);
        return data;
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.getAnger() > 0) {
            this.setAnger(this.getAnger() - 1);
        }
        if (this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(15) == 0 && this.getAnger() > 0) {
            final double d0 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p.func_175688_a(EnumParticleTypes.VILLAGER_ANGRY, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N - this.field_70130_N / 2.0, this.func_174813_aQ().field_72338_b + this.field_70131_O + this.field_70146_Z.nextFloat() * 0.5, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N - this.field_70130_N / 2.0, d0, d2, d3, new int[0]);
        }
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 30 == 0) {
            this.func_70691_i(1.0f);
        }
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityTaintacleGiant.AGGRO, (Object)0);
    }
    
    public int getAnger() {
        return (int)this.func_184212_Q().func_187225_a((DataParameter)EntityTaintacleGiant.AGGRO);
    }
    
    public void setAnger(final int par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityTaintacleGiant.AGGRO, (Object)par1);
    }
    
    @Override
    public boolean func_70601_bi() {
        return false;
    }
    
    @Override
    protected void func_70628_a(final boolean flag, final int i) {
        final List<EntityTaintacleGiant> ents = EntityUtils.getEntitiesInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (Entity)this, (Class<? extends EntityTaintacleGiant>)EntityTaintacleGiant.class, 48.0);
        if (ents == null || ents.size() <= 0) {
            EntityUtils.entityDropSpecialItem((Entity)this, new ItemStack(ItemsTC.primordialPearl), this.field_70131_O / 2.0f);
        }
    }
    
    protected boolean func_70692_ba() {
        return false;
    }
    
    public boolean func_70648_aU() {
        return true;
    }
    
    protected int func_70682_h(final int air) {
        return air;
    }
    
    public boolean func_70097_a(final DamageSource source, float damage) {
        if (!this.field_70170_p.field_72995_K && damage > 35.0f) {
            if (this.getAnger() == 0) {
                try {
                    this.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200, (int)(damage / 15.0f)));
                    this.func_70690_d(new PotionEffect(MobEffects.field_76420_g, 200, (int)(damage / 10.0f)));
                    this.func_70690_d(new PotionEffect(MobEffects.field_76422_e, 200, (int)(damage / 40.0f)));
                    this.setAnger(200);
                }
                catch (Exception ex) {}
                if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityPlayer) {
                    ((EntityPlayer)source.func_76346_g()).func_146105_b((ITextComponent)new TextComponentTranslation(this.func_70005_c_() + " " + I18n.func_74838_a("tc.boss.enrage"), new Object[0]), true);
                }
            }
            damage = 35.0f;
        }
        return super.func_70097_a(source, damage);
    }
    
    protected void func_70619_bc() {
        super.func_70619_bc();
        this.bossInfo.func_186735_a(this.func_110143_aJ() / this.func_110138_aP());
    }
    
    public void func_184203_c(final EntityPlayerMP player) {
        super.func_184203_c(player);
        this.bossInfo.func_186761_b(player);
    }
    
    public void func_184178_b(final EntityPlayerMP player) {
        super.func_184178_b(player);
        this.bossInfo.func_186760_a(player);
    }
    
    public boolean func_184222_aU() {
        return false;
    }
    
    static {
        AGGRO = EntityDataManager.func_187226_a((Class)EntityThaumcraftBoss.class, DataSerializers.field_187192_b);
    }
}
