package thaumcraft.common.entities.construct.golem.ai;

import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import net.minecraft.entity.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.config.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.pathfinding.*;

public class AIGotoBlock extends AIGoto
{
    public AIGotoBlock(final EntityThaumcraftGolem g) {
        super(g, (byte)0);
    }
    
    @Override
    public void func_75246_d() {
        super.func_75246_d();
        if (this.golem.func_70671_ap() != null) {
            this.golem.func_70671_ap().func_75650_a(this.golem.getTask().getPos().func_177958_n() + 0.5, this.golem.getTask().getPos().func_177956_o() + 0.5, this.golem.getTask().getPos().func_177952_p() + 0.5, 10.0f, (float)this.golem.func_70646_bf());
        }
    }
    
    @Override
    protected void moveTo() {
        if (this.targetBlock != null) {
            this.golem.func_70661_as().func_75492_a(this.targetBlock.func_177958_n() + 0.5, this.targetBlock.func_177956_o() + 0.5, this.targetBlock.func_177952_p() + 0.5, (double)this.golem.getGolemMoveSpeed());
        }
        else {
            this.golem.func_70661_as().func_75492_a(this.golem.getTask().getPos().func_177958_n() + 0.5, this.golem.getTask().getPos().func_177956_o() + 0.5, this.golem.getTask().getPos().func_177952_p() + 0.5, (double)this.golem.getGolemMoveSpeed());
        }
    }
    
    @Override
    protected boolean findDestination() {
        final ArrayList<Task> list = TaskHandler.getBlockTasksSorted(this.golem.field_70170_p.field_73011_w.getDimension(), this.golem.func_110124_au(), (Entity)this.golem);
        for (final Task ticket : list) {
            if (this.areGolemTagsValidForTask(ticket) && ticket.canGolemPerformTask(this.golem) && this.golem.func_180485_d(ticket.getPos()) && this.isValidDestination(this.golem.field_70170_p, ticket.getPos()) && this.canEasilyReach(ticket.getPos())) {
                this.targetBlock = this.getAdjacentSpace(ticket.getPos());
                this.golem.setTask(ticket);
                this.golem.getTask().setReserved(true);
                if (ModConfig.CONFIG_GRAPHICS.showGolemEmotes) {
                    this.golem.field_70170_p.func_72960_a((Entity)this.golem, (byte)5);
                }
                return true;
            }
        }
        return false;
    }
    
    private BlockPos getAdjacentSpace(final BlockPos pos) {
        double d = Double.MAX_VALUE;
        BlockPos closest = null;
        for (final EnumFacing face : EnumFacing.field_176754_o) {
            final IBlockState block = this.golem.field_70170_p.func_180495_p(pos.func_177972_a(face));
            if (!block.func_185904_a().func_76230_c()) {
                final double dist = pos.func_177972_a(face).func_177957_d(this.golem.field_70165_t, this.golem.field_70163_u, this.golem.field_70161_v);
                if (dist < d) {
                    closest = pos.func_177972_a(face);
                    d = dist;
                }
            }
        }
        return closest;
    }
    
    private boolean canEasilyReach(final BlockPos pos) {
        if (this.golem.func_174831_c(pos) < this.minDist) {
            return true;
        }
        final Path pathentity = this.golem.func_70661_as().func_75488_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5);
        if (pathentity == null) {
            return false;
        }
        final PathPoint pathpoint = pathentity.func_75870_c();
        if (pathpoint == null) {
            return false;
        }
        final int i = pathpoint.field_75839_a - MathHelper.func_76141_d((float)pos.func_177958_n());
        final int j = pathpoint.field_75838_c - MathHelper.func_76141_d((float)pos.func_177952_p());
        int k = pathpoint.field_75837_b - MathHelper.func_76141_d((float)pos.func_177956_o());
        if (i == 0 && j == 0 && k == 2) {
            --k;
        }
        return i * i + j * j + k * k < 2.25;
    }
}
