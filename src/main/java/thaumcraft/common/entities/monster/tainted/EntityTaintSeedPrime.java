package thaumcraft.common.entities.monster.tainted;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import thaumcraft.common.config.*;

public class EntityTaintSeedPrime extends EntityTaintSeed
{
    public EntityTaintSeedPrime(final World par1World) {
        super(par1World);
        this.func_70105_a(2.0f, 2.0f);
        this.field_70728_aV = 12;
    }
    
    @Override
    protected int getArea() {
        return 2;
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(150.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0);
    }
    
    @Override
    protected void func_70628_a(final boolean flag, final int i) {
        this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        if (this.field_70146_Z.nextBoolean()) {
            this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        }
        if (this.field_70146_Z.nextBoolean()) {
            this.func_70099_a(ConfigItems.FLUX_CRYSTAL.func_77946_l(), this.field_70131_O / 2.0f);
        }
    }
}
