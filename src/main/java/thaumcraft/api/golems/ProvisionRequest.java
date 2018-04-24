package thaumcraft.api.golems;

import thaumcraft.api.golems.seals.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class ProvisionRequest
{
    private ISealEntity seal;
    private Entity entity;
    private BlockPos pos;
    private EnumFacing side;
    private ItemStack stack;
    private int id;
    
    ProvisionRequest(final ISealEntity seal, final ItemStack stack) {
        this.seal = seal;
        this.stack = stack.func_77946_l();
        String s = seal.getSealPos().pos.toString() + seal.getSealPos().face.name() + stack.toString();
        if (stack.func_77942_o()) {
            s += stack.func_77978_p().toString();
        }
        this.id = s.hashCode();
    }
    
    ProvisionRequest(final BlockPos pos, final EnumFacing side, final ItemStack stack) {
        this.pos = pos;
        this.side = side;
        this.stack = stack.func_77946_l();
        String s = pos.toString() + side.name() + stack.toString();
        if (stack.func_77942_o()) {
            s += stack.func_77978_p().toString();
        }
        this.id = s.hashCode();
    }
    
    ProvisionRequest(final Entity entity, final ItemStack stack) {
        this.entity = entity;
        this.stack = stack.func_77946_l();
        String s = entity.func_145782_y() + stack.toString();
        if (stack.func_77942_o()) {
            s += stack.func_77978_p().toString();
        }
        this.id = s.hashCode();
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public ISealEntity getSeal() {
        return this.seal;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public EnumFacing getSide() {
        return this.side;
    }
    
    public void setSide(final EnumFacing side) {
        this.side = side;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ProvisionRequest)) {
            return false;
        }
        final ProvisionRequest pr = (ProvisionRequest)p_equals_1_;
        return this.id == pr.id;
    }
    
    private boolean isItemStackEqual(final ItemStack first, final ItemStack other) {
        return first.func_190916_E() == other.func_190916_E() && first.func_77973_b() == other.func_77973_b() && first.func_77952_i() == other.func_77952_i() && (first.func_77978_p() != null || other.func_77978_p() == null) && (first.func_77978_p() == null || first.func_77978_p().equals((Object)other.func_77978_p()));
    }
}
