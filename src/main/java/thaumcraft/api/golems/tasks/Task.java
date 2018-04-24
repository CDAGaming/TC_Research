package thaumcraft.api.golems.tasks;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import thaumcraft.api.golems.*;
import thaumcraft.api.golems.seals.*;

public class Task
{
    private UUID golemUUID;
    private int id;
    private byte type;
    private SealPos sealPos;
    private BlockPos pos;
    private Entity entity;
    private boolean reserved;
    private boolean suspended;
    private boolean completed;
    private int data;
    private short lifespan;
    private byte priority;
    
    private Task() {
        this.priority = 0;
    }
    
    public Task(final SealPos sealPos, final BlockPos pos) {
        this.priority = 0;
        this.sealPos = sealPos;
        this.pos = pos;
        if (sealPos == null) {
            this.id = (System.currentTimeMillis() + "/BNPOS/" + pos.toString()).hashCode();
        }
        else {
            this.id = (System.currentTimeMillis() + "/B/" + sealPos.face.toString() + "/" + sealPos.pos.toString() + "/" + pos.toString()).hashCode();
        }
        this.type = 0;
        this.lifespan = 300;
    }
    
    public Task(final SealPos sealPos, final Entity entity) {
        this.priority = 0;
        this.sealPos = sealPos;
        this.entity = entity;
        if (sealPos == null) {
            this.id = (System.currentTimeMillis() + "/ENPOS/" + entity.func_145782_y()).hashCode();
        }
        else {
            this.id = (System.currentTimeMillis() + "/E/" + sealPos.face.toString() + "/" + sealPos.pos.toString() + "/" + entity.func_145782_y()).hashCode();
        }
        this.type = 1;
        this.lifespan = 300;
    }
    
    public byte getPriority() {
        return this.priority;
    }
    
    public void setPriority(final byte priority) {
        this.priority = priority;
    }
    
    public boolean isCompleted() {
        return this.completed;
    }
    
    public void setCompletion(final boolean fulfilled) {
        this.completed = fulfilled;
        ++this.lifespan;
    }
    
    public UUID getGolemUUID() {
        return this.golemUUID;
    }
    
    public void setGolemUUID(final UUID golemUUID) {
        this.golemUUID = golemUUID;
    }
    
    public BlockPos getPos() {
        return (this.type == 1) ? this.entity.func_180425_c() : this.pos;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean isReserved() {
        return this.reserved;
    }
    
    public void setReserved(final boolean res) {
        this.reserved = res;
        this.lifespan += 120;
    }
    
    public boolean isSuspended() {
        return this.suspended;
    }
    
    public void setSuspended(final boolean suspended) {
        this.suspended = suspended;
    }
    
    public SealPos getSealPos() {
        return this.sealPos;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Task)) {
            return false;
        }
        final Task t = (Task)o;
        return t.id == this.id;
    }
    
    public long getLifespan() {
        return this.lifespan;
    }
    
    public void setLifespan(final short ls) {
        this.lifespan = ls;
    }
    
    public boolean canGolemPerformTask(final IGolemAPI golem) {
        final ISealEntity se = GolemHelper.getSealEntity(golem.getGolemWorld().field_73011_w.getDimension(), this.sealPos);
        return se == null || ((golem.getGolemColor() <= 0 || se.getColor() <= 0 || golem.getGolemColor() == se.getColor()) && se.getSeal().canGolemPerformTask(golem, this));
    }
    
    public int getData() {
        return this.data;
    }
    
    public void setData(final int data) {
        this.data = data;
    }
}
