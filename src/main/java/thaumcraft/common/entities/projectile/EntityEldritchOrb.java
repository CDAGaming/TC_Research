package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import java.util.*;

public class EntityEldritchOrb extends EntityThrowable
{
    public EntityEldritchOrb(final World par1World) {
        super(par1World);
    }
    
    public EntityEldritchOrb(final World par1World, final EntityLivingBase p) {
        super(par1World, p);
        this.func_184538_a((Entity)p, p.field_70125_A, p.field_70177_z, -5.0f, 0.75f, 0.0f);
    }
    
    protected float func_70185_h() {
        return 0.0f;
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
        if (this.field_70173_aa > 100) {
            this.func_70106_y();
        }
    }
    
    protected void func_70184_a(final RayTraceResult mop) {
        if (!this.field_70170_p.field_72995_K && this.func_85052_h() != null) {
            final List list = this.field_70170_p.func_72839_b((Entity)this.func_85052_h(), this.func_174813_aQ().func_72314_b(2.0, 2.0, 2.0));
            for (int i = 0; i < list.size(); ++i) {
                final Entity entity1 = list.get(i);
                if (entity1 != null && entity1 instanceof EntityLivingBase && !((EntityLivingBase)entity1).func_70662_br()) {
                    ((EntityLivingBase)entity1).func_70097_a(DamageSource.func_76354_b((Entity)this, (Entity)this.func_85052_h()), (float)this.func_85052_h().func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * 0.666f);
                    try {
                        ((EntityLivingBase)entity1).func_70690_d(new PotionEffect(MobEffects.field_76437_t, 160, 0));
                    }
                    catch (Exception ex) {}
                }
            }
            this.func_184185_a(SoundEvents.field_187659_cY, 0.5f, 2.6f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.8f);
            this.func_70106_y();
        }
    }
}
