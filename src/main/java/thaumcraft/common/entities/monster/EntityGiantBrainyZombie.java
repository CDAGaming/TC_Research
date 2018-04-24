package thaumcraft.common.entities.monster;

import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.datasync.*;

public class EntityGiantBrainyZombie extends EntityBrainyZombie
{
    private static final DataParameter<Float> ANGER;
    
    public EntityGiantBrainyZombie(final World world) {
        super(world);
        this.field_70728_aV = 15;
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4f));
    }
    
    public void func_70636_d() {
        super.func_70636_d();
        if (this.getAnger() > 1.0f) {
            this.setAnger(this.getAnger() - 0.002f);
            this.func_70105_a(0.6f + this.getAnger() * 0.6f, 1.95f + this.getAnger() * 1.95f);
            this.func_146069_a(1.0f);
        }
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a((double)(7.0f + (this.getAnger() - 1.0f) * 5.0f));
    }
    
    public float func_70047_e() {
        float f = 1.74f + this.getAnger() * 1.74f;
        if (this.func_70631_g_()) {
            f -= 0.81;
        }
        return f;
    }
    
    protected void func_70088_a() {
        super.func_70088_a();
        this.func_184212_Q().func_187214_a((DataParameter)EntityGiantBrainyZombie.ANGER, (Object)0.0f);
    }
    
    public float getAnger() {
        return (float)this.func_184212_Q().func_187225_a((DataParameter)EntityGiantBrainyZombie.ANGER);
    }
    
    public void setAnger(final float par1) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityGiantBrainyZombie.ANGER, (Object)par1);
    }
    
    public boolean func_70097_a(final DamageSource par1DamageSource, final float par2) {
        this.setAnger(Math.min(2.0f, this.getAnger() + 0.1f));
        return super.func_70097_a(par1DamageSource, par2);
    }
    
    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0);
    }
    
    protected void func_70628_a(final boolean flag, final int i) {
        for (int a = 0; a < 6; ++a) {
            if (this.field_70170_p.field_73012_v.nextBoolean()) {
                this.func_145779_a(Items.field_151078_bh, 2);
            }
        }
        for (int a = 0; a < 6; ++a) {
            if (this.field_70170_p.field_73012_v.nextBoolean()) {
                this.func_145779_a(Items.field_151078_bh, 2);
            }
        }
    }
    
    static {
        ANGER = EntityDataManager.func_187226_a((Class)EntityGiantBrainyZombie.class, DataSerializers.field_187193_c);
    }
}
