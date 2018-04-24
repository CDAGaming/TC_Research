package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.*;
import net.minecraft.util.math.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraftforge.common.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

public class PacketNote implements IMessage, IMessageHandler<PacketNote, IMessage>
{
    private int x;
    private int y;
    private int z;
    private int dim;
    private byte note;
    
    public PacketNote() {
    }
    
    public PacketNote(final int x, final int y, final int z, final int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
        this.note = -1;
    }
    
    public PacketNote(final int x, final int y, final int z, final int dim, final byte note) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
        this.note = note;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.dim);
        buffer.writeByte((int)this.note);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.dim = buffer.readInt();
        this.note = buffer.readByte();
    }
    
    public IMessage onMessage(final PacketNote message, final MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            if (message.note >= 0) {
                final TileEntity tile = Thaumcraft.proxy.getClientWorld().func_175625_s(new BlockPos(message.x, message.y, message.z));
                if (tile != null && tile instanceof TileEntityNote) {
                    ((TileEntityNote)tile).field_145879_a = message.note;
                }
                else if (tile != null && tile instanceof TileArcaneEar) {
                    ((TileArcaneEar)tile).note = message.note;
                }
            }
        }
        else if (message.note == -1) {
            final World world = (World)DimensionManager.getWorld(message.dim);
            if (world == null) {
                return null;
            }
            final TileEntity tile2 = world.func_175625_s(new BlockPos(message.x, message.y, message.z));
            byte note = -1;
            if (tile2 != null && tile2 instanceof TileEntityNote) {
                note = ((TileEntityNote)tile2).field_145879_a;
            }
            else if (tile2 != null && tile2 instanceof TileArcaneEar) {
                note = ((TileArcaneEar)tile2).note;
            }
            if (note >= 0) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketNote(message.x, message.y, message.z, message.dim, note), new NetworkRegistry.TargetPoint(message.dim, (double)message.x, (double)message.y, (double)message.z, 8.0));
            }
        }
        return null;
    }
}
