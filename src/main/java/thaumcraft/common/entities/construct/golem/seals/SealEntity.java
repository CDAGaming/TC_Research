package thaumcraft.common.entities.construct.golem.seals;

import net.minecraft.util.math.*;
import net.minecraft.world.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class SealEntity implements ISealEntity
{
    SealPos sealPos;
    ISeal seal;
    byte priority;
    byte color;
    boolean locked;
    boolean redstone;
    String owner;
    boolean stopped;
    private BlockPos area;
    
    public SealEntity() {
        this.priority = 0;
        this.color = 0;
        this.locked = false;
        this.redstone = false;
        this.owner = "";
        this.stopped = false;
        this.area = new BlockPos(1, 1, 1);
    }
    
    public SealEntity(final World world, final SealPos sealPos, final ISeal seal) {
        this.priority = 0;
        this.color = 0;
        this.locked = false;
        this.redstone = false;
        this.owner = "";
        this.stopped = false;
        this.area = new BlockPos(1, 1, 1);
        this.sealPos = sealPos;
        this.seal = seal;
        if (seal instanceof ISealConfigArea) {
            final int x = (sealPos.face.func_82601_c() == 0) ? 3 : 1;
            final int y = (sealPos.face.func_96559_d() == 0) ? 3 : 1;
            final int z = (sealPos.face.func_82599_e() == 0) ? 3 : 1;
            this.area = new BlockPos(x, y, z);
        }
    }
    
    @Override
    public void tickSealEntity(final World world) {
        if (this.seal != null) {
            if (this.isStoppedByRedstone(world)) {
                if (!this.stopped) {
                    for (final Task t : TaskHandler.getTasks(world.field_73011_w.getDimension()).values()) {
                        if (t.getSealPos() != null && t.getSealPos().equals(this.sealPos)) {
                            t.setSuspended(true);
                        }
                    }
                }
                this.stopped = true;
                return;
            }
            this.stopped = false;
            this.seal.tickSeal(world, this);
        }
    }
    
    @Override
    public boolean isStoppedByRedstone(final World world) {
        return this.isRedstoneSensitive() && (world.func_175640_z(this.getSealPos().pos) || world.func_175640_z(this.getSealPos().pos.func_177972_a(this.getSealPos().face)));
    }
    
    @Override
    public ISeal getSeal() {
        return this.seal;
    }
    
    @Override
    public SealPos getSealPos() {
        return this.sealPos;
    }
    
    @Override
    public byte getPriority() {
        return this.priority;
    }
    
    @Override
    public void setPriority(final byte priority) {
        this.priority = priority;
    }
    
    @Override
    public byte getColor() {
        return this.color;
    }
    
    @Override
    public void setColor(final byte color) {
        this.color = color;
    }
    
    @Override
    public String getOwner() {
        return this.owner;
    }
    
    @Override
    public void setOwner(final String owner) {
        this.owner = owner;
    }
    
    @Override
    public boolean isLocked() {
        return this.locked;
    }
    
    @Override
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }
    
    @Override
    public boolean isRedstoneSensitive() {
        return this.redstone;
    }
    
    @Override
    public void setRedstoneSensitive(final boolean redstone) {
        this.redstone = redstone;
    }
    
    @Override
    public void readNBT(final NBTTagCompound nbt) {
        final BlockPos p = BlockPos.func_177969_a(nbt.func_74763_f("pos"));
        final EnumFacing face = EnumFacing.field_82609_l[nbt.func_74771_c("face")];
        this.sealPos = new SealPos(p, face);
        this.setPriority(nbt.func_74771_c("priority"));
        this.setColor(nbt.func_74771_c("color"));
        this.setLocked(nbt.func_74767_n("locked"));
        this.setRedstoneSensitive(nbt.func_74767_n("redstone"));
        this.setOwner(nbt.func_74779_i("owner"));
        try {
            this.seal = (ISeal)SealHandler.getSeal(nbt.func_74779_i("type")).getClass().newInstance();
        }
        catch (Exception ex) {}
        if (this.seal != null) {
            this.seal.readCustomNBT(nbt);
            if (this.seal instanceof ISealConfigArea) {
                this.area = BlockPos.func_177969_a(nbt.func_74763_f("area"));
            }
            if (this.seal instanceof ISealConfigToggles) {
                for (final ISealConfigToggles.SealToggle prop : ((ISealConfigToggles)this.seal).getToggles()) {
                    if (nbt.func_74764_b(prop.getKey())) {
                        prop.setValue(nbt.func_74767_n(prop.getKey()));
                    }
                }
            }
        }
    }
    
    @Override
    public NBTTagCompound writeNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.func_74772_a("pos", this.sealPos.pos.func_177986_g());
        nbt.func_74774_a("face", (byte)this.sealPos.face.ordinal());
        nbt.func_74778_a("type", this.seal.getKey());
        nbt.func_74774_a("priority", this.getPriority());
        nbt.func_74774_a("color", this.getColor());
        nbt.func_74757_a("locked", this.isLocked());
        nbt.func_74757_a("redstone", this.isRedstoneSensitive());
        nbt.func_74778_a("owner", this.getOwner());
        if (this.seal != null) {
            this.seal.writeCustomNBT(nbt);
            if (this.seal instanceof ISealConfigArea) {
                nbt.func_74772_a("area", this.area.func_177986_g());
            }
            if (this.seal instanceof ISealConfigToggles) {
                for (final ISealConfigToggles.SealToggle prop : ((ISealConfigToggles)this.seal).getToggles()) {
                    nbt.func_74757_a(prop.getKey(), prop.getValue());
                }
            }
        }
        return nbt;
    }
    
    @Override
    public void syncToClient(final World world) {
        if (!world.field_72995_K) {
            PacketHandler.INSTANCE.sendToDimension((IMessage)new PacketSealToClient(this), world.field_73011_w.getDimension());
        }
    }
    
    @Override
    public BlockPos getArea() {
        return this.area;
    }
    
    @Override
    public void setArea(final BlockPos v) {
        this.area = v;
    }
}
