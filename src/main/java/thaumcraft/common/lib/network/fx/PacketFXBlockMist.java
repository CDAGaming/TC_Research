package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.client.fx.*;

public class PacketFXBlockMist implements IMessage, IMessageHandler<PacketFXBlockMist, IMessage>
{
    private long loc;
    private int color;
    
    public PacketFXBlockMist() {
    }
    
    public PacketFXBlockMist(final BlockPos pos, final int color) {
        this.loc = pos.func_177986_g();
        this.color = color;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.loc);
        buffer.writeInt(this.color);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.loc = buffer.readLong();
        this.color = buffer.readInt();
    }
    
    public IMessage onMessage(final PacketFXBlockMist message, final MessageContext ctx) {
        FXDispatcher.INSTANCE.drawBlockMistParticles(BlockPos.func_177969_a(message.loc), message.color);
        return null;
    }
}
