package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class PacketFXInfusionSource implements IMessage, IMessageHandler<PacketFXInfusionSource, IMessage>
{
    private int x;
    private int y;
    private int z;
    private byte dx;
    private byte dy;
    private byte dz;
    private int color;
    
    public PacketFXInfusionSource() {
    }
    
    public PacketFXInfusionSource(final BlockPos pos, final byte dx, final byte dy, final byte dz, final int color) {
        this.x = pos.func_177958_n();
        this.y = pos.func_177956_o();
        this.z = pos.func_177952_p();
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.color = color;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.color);
        buffer.writeByte((int)this.dx);
        buffer.writeByte((int)this.dy);
        buffer.writeByte((int)this.dz);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.color = buffer.readInt();
        this.dx = buffer.readByte();
        this.dy = buffer.readByte();
        this.dz = buffer.readByte();
    }
    
    public IMessage onMessage(final PacketFXInfusionSource message, final MessageContext ctx) {
        final int tx = message.x - message.dx;
        final int ty = message.y - message.dy;
        final int tz = message.z - message.dz;
        final String key = tx + ":" + ty + ":" + tz + ":" + message.color;
        final TileEntity tile = Thaumcraft.proxy.getClientWorld().func_175625_s(new BlockPos(message.x, message.y, message.z));
        if (tile != null && tile instanceof TileInfusionMatrix) {
            int count = 15;
            if (Thaumcraft.proxy.getClientWorld().func_175625_s(new BlockPos(tx, ty, tz)) != null && Thaumcraft.proxy.getClientWorld().func_175625_s(new BlockPos(tx, ty, tz)) instanceof TilePedestal) {
                count = 60;
            }
            final TileInfusionMatrix is = (TileInfusionMatrix)tile;
            if (is.sourceFX.containsKey(key)) {
                final TileInfusionMatrix.SourceFX sf = is.sourceFX.get(key);
                sf.ticks = count;
                is.sourceFX.put(key, sf);
            }
            else {
                final HashMap<String, TileInfusionMatrix.SourceFX> sourceFX = is.sourceFX;
                final String s = key;
                final TileInfusionMatrix this$0 = is;
                this$0.getClass();
                sourceFX.put(s, this$0.new SourceFX(new BlockPos(tx, ty, tz), count, message.color));
            }
        }
        return null;
    }
}
