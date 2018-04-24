package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import thaumcraft.api.entities.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import thaumcraft.client.fx.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;

public class EntityBottleTaint extends EntityThrowable
{
    public EntityBottleTaint(final World p_i1788_1_) {
        super(p_i1788_1_);
    }
    
    public EntityBottleTaint(final World p_i1790_1_, final EntityLivingBase p_i1790_2) {
        super(p_i1790_1_, p_i1790_2);
    }
    
    protected void func_70184_a(final RayTraceResult p_70184_1_) {
        if (!this.field_70170_p.field_72995_K) {
            final List ents = this.field_70170_p.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70165_t, this.field_70163_u, this.field_70161_v).func_72314_b(5.0, 5.0, 5.0));
            if (ents.size() > 0) {
                for (final Object ent : ents) {
                    final EntityLivingBase el = (EntityLivingBase)ent;
                    if (!(el instanceof ITaintedMob) && !el.func_70662_br()) {
                        el.func_70690_d(new PotionEffect(PotionFluxTaint.instance, 100, 0, false, true));
                    }
                }
            }
            for (int a = 0; a < 10; ++a) {
                final int xx = (int)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 4.0f);
                final int zz = (int)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 4.0f);
                BlockPos p = this.func_180425_c().func_177982_a(xx, 0, zz);
                if (this.field_70170_p.field_73012_v.nextBoolean()) {
                    if (this.field_70170_p.func_175677_d(p.func_177977_b(), false) && this.field_70170_p.func_180495_p(p).func_177230_c().func_176200_f((IBlockAccess)this.field_70170_p, p)) {
                        this.field_70170_p.func_175656_a(p, BlocksTC.fluxGoo.func_176223_P());
                    }
                    else {
                        p = p.func_177977_b();
                        if (this.field_70170_p.func_175677_d(p.func_177977_b(), false) && this.field_70170_p.func_180495_p(p).func_177230_c().func_176200_f((IBlockAccess)this.field_70170_p, p)) {
                            this.field_70170_p.func_175656_a(p, BlocksTC.fluxGoo.func_176223_P());
                        }
                    }
                }
            }
            this.func_70106_y();
        }
        else {
            for (int a2 = 0; a2 < 200; ++a2) {
                FXDispatcher.INSTANCE.taintsplosionFX((Entity)this);
            }
            FXDispatcher.INSTANCE.bottleTaintBreak(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        }
    }
}
