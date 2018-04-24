package thaumcraft.common.entities.monster.cult;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.monster.boss.*;
import thaumcraft.client.fx.*;
import net.minecraft.world.storage.loot.*;

public class EntityCultist extends EntityMob
{
    public static final ResourceLocation LOOT;
    
    public EntityCultist(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.func_70105_a(0.6f, 1.8f);
        this.field_70728_aV = 10;
        ((PathNavigateGround)this.func_70661_as()).func_179688_b(true);
        this.func_184642_a(EntityEquipmentSlot.CHEST, 0.05f);
        this.func_184642_a(EntityEquipmentSlot.FEET, 0.05f);
        this.func_184642_a(EntityEquipmentSlot.HEAD, 0.05f);
        this.func_184642_a(EntityEquipmentSlot.LEGS, 0.05f);
    }
    
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
    }
    
    public boolean func_98052_bS() {
        return false;
    }
    
    protected boolean func_70814_o() {
        return true;
    }
    
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    protected ResourceLocation func_184647_J() {
        return EntityCultist.LOOT;
    }
    
    protected void setLoot(final DifficultyInstance diff) {
    }
    
    protected void func_180483_b(final DifficultyInstance diff) {
    }
    
    public IEntityLivingData func_180482_a(final DifficultyInstance diff, final IEntityLivingData data) {
        this.setLoot(diff);
        this.func_180483_b(diff);
        return super.func_180482_a(diff, data);
    }
    
    protected boolean func_70692_ba() {
        return true;
    }
    
    public void func_70037_a(final NBTTagCompound nbt) {
        super.func_70037_a(nbt);
        if (nbt.func_74764_b("HomeD")) {
            this.func_175449_a(new BlockPos(nbt.func_74762_e("HomeX"), nbt.func_74762_e("HomeY"), nbt.func_74762_e("HomeZ")), nbt.func_74762_e("HomeD"));
        }
    }
    
    public void func_70014_b(final NBTTagCompound nbt) {
        super.func_70014_b(nbt);
        if (this.func_180486_cf() != null && this.func_110174_bM() > 0.0f) {
            nbt.func_74768_a("HomeD", (int)this.func_110174_bM());
            nbt.func_74768_a("HomeX", this.func_180486_cf().func_177958_n());
            nbt.func_74768_a("HomeY", this.func_180486_cf().func_177956_o());
            nbt.func_74768_a("HomeZ", this.func_180486_cf().func_177952_p());
        }
    }
    
    public boolean func_184191_r(final Entity el) {
        return el instanceof EntityCultist || el instanceof EntityCultistLeader;
    }
    
    public boolean func_70686_a(final Class clazz) {
        return clazz != EntityCultistCleric.class && clazz != EntityCultistLeader.class && clazz != EntityCultistKnight.class && super.func_70686_a(clazz);
    }
    
    public void func_70656_aK() {
        if (this.field_70170_p.field_72995_K) {
            for (int i = 0; i < 20; ++i) {
                final double d0 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d2 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d3 = this.field_70146_Z.nextGaussian() * 0.05;
                final double d4 = 2.0;
                FXDispatcher.INSTANCE.cultistSpawn(this.field_70165_t + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N + d0 * d4, this.field_70163_u + this.field_70146_Z.nextFloat() * this.field_70131_O + d2 * d4, this.field_70161_v + this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0f - this.field_70130_N + d3 * d4, d0, d2, d3);
            }
        }
        else {
            this.field_70170_p.func_72960_a((Entity)this, (byte)20);
        }
    }
    
    static {
        LOOT = LootTableList.func_186375_a(new ResourceLocation("thaumcraft", "cultist"));
    }
}
