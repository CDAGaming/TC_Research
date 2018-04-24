package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.client.fx.*;

public class PacketFXPollute implements IMessage, IMessageHandler<PacketFXPollute, IMessage>
{
    private int x;
    private int y;
    private int z;
    private byte amount;
    
    public PacketFXPollute() {
    }
    
    public PacketFXPollute(final BlockPos pos, final float amt) {
        this.x = pos.func_177958_n();
        this.y = pos.func_177956_o();
        this.z = pos.func_177952_p();
        this.amount = (byte)amt;
    }
    
    public PacketFXPollute(final BlockPos pos, final float amt, final boolean vary) {
        this(pos, amt);
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeByte((int)this.amount);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.amount = buffer.readByte();
    }
    
    public IMessage onMessage(final PacketFXPollute message, final MessageContext ctx) {
        for (int a = 0; a < Math.min(40, message.amount); ++a) {
            FXDispatcher.INSTANCE.drawPollutionParticles(new BlockPos(message.x, message.y, message.z));
        }
        return null;
    }
}
