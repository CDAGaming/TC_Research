package thaumcraft.common.entities.construct.golem.ai;

import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import net.minecraft.entity.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.config.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.pathfinding.*;

public class AIGotoEntity extends AIGoto
{
    public AIGotoEntity(final EntityThaumcraftGolem g) {
        super(g, (byte)1);
    }
    
    @Override
    public void func_75246_d() {
        super.func_75246_d();
        if (this.golem.func_70671_ap() != null) {
            this.golem.func_70671_ap().func_75651_a(this.golem.getTask().getEntity(), 10.0f, (float)this.golem.func_70646_bf());
        }
    }
    
    @Override
    protected void moveTo() {
        this.golem.func_70661_as().func_75497_a(this.golem.getTask().getEntity(), (double)this.golem.getGolemMoveSpeed());
    }
    
    @Override
    protected boolean findDestination() {
        final ArrayList<Task> list = TaskHandler.getEntityTasksSorted(this.golem.field_70170_p.field_73011_w.getDimension(), this.golem.func_110124_au(), (Entity)this.golem);
        for (final Task ticket : list) {
            if (this.areGolemTagsValidForTask(ticket) && ticket.canGolemPerformTask(this.golem) && this.golem.func_180485_d(ticket.getEntity().func_180425_c()) && this.isValidDestination(this.golem.field_70170_p, ticket.getEntity().func_180425_c()) && this.canEasilyReach(ticket.getEntity())) {
                this.golem.setTask(ticket);
                this.golem.getTask().setReserved(true);
                this.minDist = 3.5 + this.golem.getTask().getEntity().field_70130_N / 2.0f * (this.golem.getTask().getEntity().field_70130_N / 2.0f);
                if (ModConfig.CONFIG_GRAPHICS.showGolemEmotes) {
                    this.golem.field_70170_p.func_72960_a((Entity)this.golem, (byte)5);
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean canEasilyReach(final Entity e) {
        if (this.golem.func_70068_e(e) < this.minDist) {
            return true;
        }
        final Path pathentity = this.golem.func_70661_as().func_75494_a(e);
        if (pathentity == null) {
            return false;
        }
        final PathPoint pathpoint = pathentity.func_75870_c();
        if (pathpoint == null) {
            return false;
        }
        final int i = pathpoint.field_75839_a - MathHelper.func_76128_c(e.field_70165_t);
        final int j = pathpoint.field_75838_c - MathHelper.func_76128_c(e.field_70161_v);
        return i * i + j * j < this.minDist;
    }
}
