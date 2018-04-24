package thaumcraft.common.entities.monster.tainted;

import thaumcraft.api.entities.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class EntityTaintacleSmall extends EntityTaintacle implements ITaintedMob
{
    int lifetime;
    
    public EntityTaintacleSmall(final World par1World) {
        super(par1World);
        this.lifetime = 200;
        this.func_70105_a(0.22f, 1.0f);
        this.field_70728_aV = 0;
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(5.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0);
    }
    
    @Override
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.lifetime-- <= 0) {
            this.func_70665_d(DamageSource.field_76376_m, 10.0f);
        }
    }
    
    @Override
    public boolean func_70601_bi() {
        return false;
    }
    
    @Override
    protected Item func_146068_u() {
        return Item.func_150899_d(0);
    }
    
    @Override
    protected void func_70628_a(final boolean flag, final int i) {
    }
}
