package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.container.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class PacketMiscStringToServer implements IMessage, IMessageHandler<PacketMiscStringToServer, IMessage>
{
    private int id;
    private String text;
    
    public PacketMiscStringToServer() {
    }
    
    public PacketMiscStringToServer(final int id, final String text) {
        this.id = id;
        this.text = text;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.id);
        ByteBufUtils.writeUTF8String(buffer, this.text);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.id = buffer.readInt();
        this.text = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketMiscStringToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final EntityPlayerMP player = ctx.getServerHandler().field_147369_b;
                if (PacketMiscStringToServer.this.id == 0 && player.field_71070_bA instanceof ContainerLogistics) {
                    final ContainerLogistics container = (ContainerLogistics)player.field_71070_bA;
                    container.searchText = message.text;
                    container.refreshItemList(true);
                }
            }
        });
        return null;
    }
}
