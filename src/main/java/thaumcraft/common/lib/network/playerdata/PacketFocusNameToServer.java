package thaumcraft.common.lib.network.playerdata;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class PacketFocusNameToServer implements IMessage, IMessageHandler<PacketFocusNameToServer, IMessage>
{
    private long loc;
    private String name;
    
    public PacketFocusNameToServer() {
    }
    
    public PacketFocusNameToServer(final BlockPos pos, final String name) {
        this.loc = pos.func_177986_g();
        this.name = name;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.loc);
        ByteBufUtils.writeUTF8String(buffer, this.name);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.loc = buffer.readLong();
        this.name = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketFocusNameToServer message, final MessageContext ctx) {
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
                    ((TileFocalManipulator)rt).focusName = message.name;
                    ((TileFocalManipulator)rt).func_70296_d();
                }
            }
        });
        return null;
    }
}
