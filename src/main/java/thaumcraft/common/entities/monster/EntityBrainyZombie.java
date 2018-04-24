package thaumcraft.common.entities.monster;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;

public class EntityBrainyZombie extends EntityZombie
{
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0);
        this.func_110148_a(EntityBrainyZombie.field_110186_bp).func_111128_a(0.0);
    }
    
    public EntityBrainyZombie(final World world) {
        super(world);
        this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
    }
    
    public int func_70658_aO() {
        return super.func_70658_aO() + 1;
    }
    
    protected void func_184610_a(final boolean wasRecentlyHit, final int lootingModifier, final DamageSource source) {
        if (this.field_70170_p.field_73012_v.nextInt(10) - lootingModifier <= 4) {
            this.func_70099_a(new ItemStack(ItemsTC.brain), 1.5f);
        }
        super.func_184610_a(wasRecentlyHit, lootingModifier, source);
    }
}
