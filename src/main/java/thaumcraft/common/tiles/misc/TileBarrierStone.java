package thaumcraft.common.tiles.misc;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import java.util.*;

public class TileBarrierStone extends TileEntity implements ITickable
{
    int count;
    
    public TileBarrierStone() {
        this.count = 0;
    }
    
    public boolean gettingPower() {
        return this.field_145850_b.func_175687_A(this.field_174879_c) > 0;
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            if (this.count == 0) {
                this.count = this.field_145850_b.field_73012_v.nextInt(100);
            }
            if (this.count % 5 == 0 && !this.gettingPower()) {
                final List<EntityLivingBase> targets = (List<EntityLivingBase>)this.field_145850_b.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 3), (double)(this.field_174879_c.func_177952_p() + 1)).func_72314_b(0.1, 0.1, 0.1));
                if (targets.size() > 0) {
                    for (final EntityLivingBase e : targets) {
                        if (!e.field_70122_E && !(e instanceof EntityPlayer)) {
                            e.func_70024_g((double)(-MathHelper.func_76126_a((e.field_70177_z + 180.0f) * 3.1415927f / 180.0f) * 0.2f), -0.1, (double)(MathHelper.func_76134_b((e.field_70177_z + 180.0f) * 3.1415927f / 180.0f) * 0.2f));
                        }
                    }
                }
            }
            if (++this.count % 100 == 0) {
                if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177981_b(1)) != BlocksTC.barrier.func_176223_P() && this.field_145850_b.func_175623_d(this.field_174879_c.func_177981_b(1))) {
                    this.field_145850_b.func_180501_a(this.field_174879_c.func_177981_b(1), BlocksTC.barrier.func_176223_P(), 3);
                }
                if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177981_b(2)) != BlocksTC.barrier.func_176223_P() && this.field_145850_b.func_175623_d(this.field_174879_c.func_177981_b(2))) {
                    this.field_145850_b.func_180501_a(this.field_174879_c.func_177981_b(2), BlocksTC.barrier.func_176223_P(), 3);
                }
            }
        }
    }
}
