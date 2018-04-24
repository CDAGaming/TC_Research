package thaumcraft.common.lib.network.misc;

import net.minecraft.util.math.*;
import net.minecraft.item.*;
import io.netty.buffer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.api.golems.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class PacketLogisticsRequestToServer implements IMessage, IMessageHandler<PacketLogisticsRequestToServer, IMessage>
{
    private BlockPos pos;
    private ItemStack stack;
    private EnumFacing side;
    private int stacksize;
    
    public PacketLogisticsRequestToServer() {
    }
    
    public PacketLogisticsRequestToServer(final BlockPos pos, final EnumFacing side, final ItemStack stack, final int size) {
        this.pos = pos;
        this.stack = stack;
        this.side = side;
        this.stacksize = size;
    }
    
    public void toBytes(final ByteBuf buffer) {
        if (this.pos == null || this.side == null) {
            buffer.writeBoolean(false);
        }
        else {
            buffer.writeBoolean(true);
            buffer.writeLong(this.pos.func_177986_g());
            buffer.writeByte(this.side.func_176745_a());
        }
        Utils.writeItemStackToBuffer(buffer, this.stack);
        buffer.writeInt(this.stacksize);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        if (buffer.readBoolean()) {
            this.pos = BlockPos.func_177969_a(buffer.readLong());
            this.side = EnumFacing.values()[buffer.readByte()];
        }
        this.stack = Utils.readItemStackFromBuffer(buffer);
        this.stacksize = buffer.readInt();
    }
    
    public IMessage onMessage(final PacketLogisticsRequestToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final World world = (World)ctx.getServerHandler().field_147369_b.func_71121_q();
                final Entity player = (Entity)ctx.getServerHandler().field_147369_b;
                while (message.stacksize > 0) {
                    final ItemStack s = message.stack.func_77946_l();
                    s.func_190920_e(Math.min(message.stacksize, s.func_77976_d()));
                    final PacketLogisticsRequestToServer val$message = message;
                    val$message.stacksize -= s.func_190916_E();
                    if (message.pos != null) {
                        GolemHelper.requestProvisioning(world, message.pos, message.side, s);
                    }
                    else {
                        GolemHelper.requestProvisioning(world, (Entity)ctx.getServerHandler().field_147369_b, s);
                    }
                }
            }
        });
        return null;
    }
}
