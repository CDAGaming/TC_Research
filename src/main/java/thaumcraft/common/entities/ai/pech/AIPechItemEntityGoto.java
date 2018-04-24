package thaumcraft.common.entities.ai.pech;

import net.minecraft.entity.ai.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.item.*;

public class AIPechItemEntityGoto extends EntityAIBase
{
    private EntityPech pech;
    private Entity targetEntity;
    float maxTargetDistance;
    private int count;
    private int failedPathFindingPenalty;
    
    public AIPechItemEntityGoto(final EntityPech par1EntityCreature) {
        this.maxTargetDistance = 16.0f;
        this.pech = par1EntityCreature;
        this.func_75248_a(3);
    }
    
    public boolean func_75250_a() {
        final int count = this.count - 1;
        this.count = count;
        if (count > 0) {
            return false;
        }
        double range = Double.MAX_VALUE;
        final List<Entity> targets = (List<Entity>)this.pech.field_70170_p.func_72839_b((Entity)this.pech, this.pech.func_174813_aQ().func_72314_b((double)this.maxTargetDistance, (double)this.maxTargetDistance, (double)this.maxTargetDistance));
        if (targets.size() == 0) {
            return false;
        }
        for (final Entity e : targets) {
            if (e instanceof EntityItem && this.pech.canPickup(((EntityItem)e).func_92059_d())) {
                final NBTTagCompound itemData = ((EntityItem)e).getEntityData();
                final String username = ((EntityItem)e).func_145800_j();
                if (username != null && username.equals("PechDrop")) {
                    continue;
                }
                final double distance = e.func_70092_e(this.pech.field_70165_t, this.pech.field_70163_u, this.pech.field_70161_v);
                if (distance >= range || distance > this.maxTargetDistance * this.maxTargetDistance) {
                    continue;
                }
                range = distance;
                this.targetEntity = e;
            }
        }
        return this.targetEntity != null;
    }
    
    public boolean func_75253_b() {
        return this.targetEntity != null && this.targetEntity.func_70089_S() && (!this.pech.func_70661_as().func_75500_f() && this.targetEntity.func_70068_e((Entity)this.pech) < this.maxTargetDistance * this.maxTargetDistance);
    }
    
    public void func_75251_c() {
        this.targetEntity = null;
    }
    
    public void func_75249_e() {
        this.pech.func_70661_as().func_75484_a(this.pech.func_70661_as().func_75494_a(this.targetEntity), this.pech.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * 1.5);
        this.count = 0;
    }
    
    public void func_75246_d() {
        this.pech.func_70671_ap().func_75651_a(this.targetEntity, 30.0f, 30.0f);
        if (this.pech.func_70635_at().func_75522_a(this.targetEntity) && --this.count <= 0) {
            this.count = this.failedPathFindingPenalty + 4 + this.pech.func_70681_au().nextInt(4);
            this.pech.func_70661_as().func_75497_a(this.targetEntity, this.pech.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * 1.5);
            if (this.pech.func_70661_as().func_75505_d() != null) {
                final PathPoint finalPathPoint = this.pech.func_70661_as().func_75505_d().func_75870_c();
                if (finalPathPoint != null && this.targetEntity.func_70092_e((double)finalPathPoint.field_75839_a, (double)finalPathPoint.field_75837_b, (double)finalPathPoint.field_75838_c) < 1.0) {
                    this.failedPathFindingPenalty = 0;
                }
                else {
                    this.failedPathFindingPenalty += 10;
                }
            }
            else {
                this.failedPathFindingPenalty += 10;
            }
        }
        final double distance = this.pech.func_70092_e(this.targetEntity.field_70165_t, this.targetEntity.func_174813_aQ().field_72338_b, this.targetEntity.field_70161_v);
        if (distance <= 1.5) {
            this.count = 0;
            final int am = ((EntityItem)this.targetEntity).func_92059_d().func_190916_E();
            final ItemStack is = this.pech.pickupItem(((EntityItem)this.targetEntity).func_92059_d());
            if (is != null && !is.func_190926_b() && is.func_190916_E() > 0) {
                ((EntityItem)this.targetEntity).func_92058_a(is);
            }
            else {
                this.targetEntity.func_70106_y();
            }
            if (is == null || is.func_190926_b() || is.func_190916_E() != am) {
                this.targetEntity.field_70170_p.func_184148_a((EntityPlayer)null, this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v, SoundEvents.field_187638_cR, SoundCategory.NEUTRAL, 0.2f, ((this.targetEntity.field_70170_p.field_73012_v.nextFloat() - this.targetEntity.field_70170_p.field_73012_v.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
        }
    }
}
