package thaumcraft.common.lib.network.playerdata;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.network.*;
import java.util.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class PacketFocusNodesToServer implements IMessage, IMessageHandler<PacketFocusNodesToServer, IMessage>
{
    private long loc;
    private HashMap<Integer, FocusElementNode> data;
    private String name;
    
    public PacketFocusNodesToServer() {
        this.data = new HashMap<Integer, FocusElementNode>();
    }
    
    public PacketFocusNodesToServer(final BlockPos pos, final HashMap<Integer, FocusElementNode> data, final String name) {
        this.data = new HashMap<Integer, FocusElementNode>();
        this.loc = pos.func_177986_g();
        this.data = data;
        this.name = name;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.loc);
        buffer.writeByte(this.data.size());
        for (final FocusElementNode node : this.data.values()) {
            Utils.writeNBTTagCompoundToBuffer(buffer, node.serialize());
        }
        ByteBufUtils.writeUTF8String(buffer, this.name);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.loc = buffer.readLong();
        for (int m = buffer.readByte(), a = 0; a < m; ++a) {
            final FocusElementNode node = new FocusElementNode();
            node.deserialize(Utils.readNBTTagCompoundFromBuffer(buffer));
            this.data.put(node.id, node);
        }
        this.name = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketFocusNodesToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ctx.getServerHandler().field_147369_b == null) {
                    return;
                }
                final BlockPos pos = BlockPos.func_177969_a(message.loc);
                final TileEntity rt = ctx.getServerHandler().field_147369_b.field_70170_p.func_175625_s(pos);
                if (rt != null && rt instanceof TileFocalManipulator) {
                    ((TileFocalManipulator)rt).data.clear();
                    ((TileFocalManipulator)rt).data = message.data;
                    ((TileFocalManipulator)rt).focusName = message.name;
                    ((TileFocalManipulator)rt).func_70296_d();
                }
            }
        });
        return null;
    }
}
