package thaumcraft.common.entities.monster.cult;

import net.minecraft.entity.player.*;
import thaumcraft.common.entities.ai.combat.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

public class EntityCultistKnight extends EntityCultist
{
    public EntityCultistKnight(final World p_i1745_1_) {
        super(p_i1745_1_);
    }
    
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
        this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new AICultistHurtByTarget((EntityCreature)this, true));
        this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
    }
    
    @Override
    protected void setLoot(final DifficultyInstance diff) {
        this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ItemsTC.crimsonPlateHelm));
        this.func_184201_a(EntityEquipmentSlot.CHEST, new ItemStack(ItemsTC.crimsonPlateChest));
        this.func_184201_a(EntityEquipmentSlot.LEGS, new ItemStack(ItemsTC.crimsonPlateLegs));
        this.func_184201_a(EntityEquipmentSlot.FEET, new ItemStack(ItemsTC.crimsonBoots));
        if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.05f : 0.01f)) {
            final int i = this.field_70146_Z.nextInt(5);
            if (i == 0) {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(ItemsTC.voidSword));
                this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(ItemsTC.crimsonRobeHelm));
            }
            else {
                this.func_184611_a(this.func_184600_cs(), new ItemStack(ItemsTC.thaumiumSword));
                if (this.field_70146_Z.nextBoolean()) {
                    this.func_184201_a(EntityEquipmentSlot.HEAD, (ItemStack)null);
                }
            }
        }
        else {
            this.func_184611_a(this.func_184600_cs(), new ItemStack(Items.field_151040_l));
        }
    }
    
    @Override
    protected void func_180483_b(final DifficultyInstance diff) {
        final float f = diff.func_180170_c();
        if (this.func_184614_ca() != null && this.field_70146_Z.nextFloat() < 0.25f * f) {
            EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_184614_ca(), (int)(5.0f + f * this.field_70146_Z.nextInt(18)), false);
        }
    }
}
