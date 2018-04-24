package thaumcraft.common.entities.construct.golem.ai;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class FlightMoveHelper extends EntityMoveHelper
{
    private static final String __OBFID = "CL_00002209";
    
    public FlightMoveHelper(final EntityLiving entity) {
        super(entity);
    }
    
    public void func_75641_c() {
        if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.field_75648_a.func_70661_as().func_75500_f()) {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
            final double d0 = this.field_75646_b - this.field_75648_a.field_70165_t;
            double d2 = this.field_75647_c - this.field_75648_a.field_70163_u;
            final double d3 = this.field_75644_d - this.field_75648_a.field_70161_v;
            double d4 = d0 * d0 + d2 * d2 + d3 * d3;
            d4 = MathHelper.func_76133_a(d4);
            d2 /= d4;
            final float f = (float)(Math.atan2(d3, d0) * 180.0 / 3.141592653589793) - 90.0f;
            this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, f, 30.0f);
            this.field_75648_a.field_70761_aq = this.field_75648_a.field_70177_z;
            final float f2 = (float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            this.field_75648_a.func_70659_e(this.field_75648_a.func_70689_ay() + (f2 - this.field_75648_a.func_70689_ay()) * 0.125f);
            double d5 = Math.sin((this.field_75648_a.field_70173_aa + this.field_75648_a.func_145782_y()) * 0.5) * 0.05;
            final double d6 = Math.cos(this.field_75648_a.field_70177_z * 3.1415927f / 180.0f);
            final double d7 = Math.sin(this.field_75648_a.field_70177_z * 3.1415927f / 180.0f);
            final EntityLiving field_75648_a = this.field_75648_a;
            field_75648_a.field_70159_w += d5 * d6;
            final EntityLiving field_75648_a2 = this.field_75648_a;
            field_75648_a2.field_70179_y += d5 * d7;
            d5 = Math.sin((this.field_75648_a.field_70173_aa + this.field_75648_a.func_145782_y()) * 0.75) * 0.05;
            final EntityLiving field_75648_a3 = this.field_75648_a;
            field_75648_a3.field_70181_x += d5 * (d7 + d6) * 0.25;
            final EntityLiving field_75648_a4 = this.field_75648_a;
            field_75648_a4.field_70181_x += this.field_75648_a.func_70689_ay() * d2 * 0.1;
            final EntityLookHelper entitylookhelper = this.field_75648_a.func_70671_ap();
            final double d8 = this.field_75648_a.field_70165_t + d0 / d4 * 2.0;
            final double d9 = this.field_75648_a.func_70047_e() + this.field_75648_a.field_70163_u + d2 / d4 * 1.0;
            final double d10 = this.field_75648_a.field_70161_v + d3 / d4 * 2.0;
            double d11 = entitylookhelper.func_180423_e();
            double d12 = entitylookhelper.func_180422_f();
            double d13 = entitylookhelper.func_180421_g();
            if (!entitylookhelper.func_180424_b()) {
                d11 = d8;
                d12 = d9;
                d13 = d10;
            }
            this.field_75648_a.func_70671_ap().func_75650_a(d11 + (d8 - d11) * 0.125, d12 + (d9 - d12) * 0.125, d13 + (d10 - d13) * 0.125, 10.0f, 40.0f);
        }
        else {
            this.field_75648_a.func_70659_e(0.0f);
        }
    }
}
