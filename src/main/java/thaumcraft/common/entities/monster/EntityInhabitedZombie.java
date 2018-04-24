package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import thaumcraft.api.entities.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;

public class EntityInhabitedZombie extends EntityZombie implements IEldritchMob
{
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0);
        this.func_110148_a(EntityInhabitedZombie.field_110186_bp).func_111128_a(0.0);
    }
    
    public EntityInhabitedZombie(final World world) {
        super(world);
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityCultist.class, true));
    }
    
    public void func_70074_a(final EntityLivingBase par1EntityLivingBase) {
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        final float d = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.9f : 0.6f;
        this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ItemsTC.crimsonPlateHelm));
        if (this.field_70146_Z.nextFloat() <= d) {
            this.func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(ItemsTC.crimsonPlateChest));
        }
        if (this.field_70146_Z.nextFloat() <= d) {
            this.func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(ItemsTC.crimsonPlateLegs));
        }
        return super.func_180482_a(diff, data);
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected void func_70628_a(final boolean p_70628_1_, final int p_70628_2_) {
    }
    
    protected void func_70609_aI() {
        if (!this.field_70170_p.field_72995_K) {
            final EntityEldritchCrab crab = new EntityEldritchCrab(this.field_70170_p);
            crab.func_70080_a(this.field_70165_t, this.field_70163_u + this.func_70047_e(), this.field_70161_v, this.field_70177_z, this.field_70125_A);
            crab.setHelm(true);
            this.field_70170_p.func_72838_d((Entity)crab);
            if ((this.field_70718_bc > 0 || this.func_70684_aJ()) && this.func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
                int i = this.func_70693_a(this.field_70717_bb);
                while (i > 0) {
                    final int j = EntityXPOrb.func_70527_a(i);
                    i -= j;
                    this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
                }
            }
        }
        for (int k = 0; k < 20; ++k) {
            final double d2 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d3 = this.field_70146_Z.nextGaussian() * 0.02;
            final double d4 = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextFloat() * this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N, d2, d3, d4, new int[0]);
        }
        this.func_70106_y();
    }
    
    public void func_70645_a(final DamageSource p_70645_1_) {
    }
    
    protected SoundEvent func_184639_G() {
        return SoundsTC.crabtalk;
    }
    
    protected SoundEvent func_184601_bQ(final DamageSource damageSourceIn) {
        return SoundEvents.field_187543_bD;
    }
    
    public boolean func_70601_bi() {
        final List ents = this.field_70170_p.func_72872_a((Class)EntityInhabitedZombie.class, new AxisAlignedBB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70165_t + 1.0, this.field_70163_u + 1.0, this.field_70161_v + 1.0).func_72314_b(32.0, 16.0, 32.0));
        return ents.size() <= 0 && super.func_70601_bi();
    }
}
