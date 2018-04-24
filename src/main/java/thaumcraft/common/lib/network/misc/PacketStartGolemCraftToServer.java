package thaumcraft.common.lib.network.misc;

import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class PacketStartGolemCraftToServer implements IMessage, IMessageHandler<PacketStartGolemCraftToServer, IMessage>
{
    private long pos;
    private long golem;
    
    public PacketStartGolemCraftToServer() {
    }
    
    public PacketStartGolemCraftToServer(final EntityPlayer player, final BlockPos pos, final long golem) {
        this.pos = pos.func_177986_g();
        this.golem = golem;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.pos);
        buffer.writeLong(this.golem);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.pos = buffer.readLong();
        this.golem = buffer.readLong();
    }
    
    public IMessage onMessage(final PacketStartGolemCraftToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final World world = (World)ctx.getServerHandler().field_147369_b.func_71121_q();
                final Entity player = (Entity)ctx.getServerHandler().field_147369_b;
                final BlockPos bp = BlockPos.func_177969_a(message.pos);
                if (world != null && player != null && player instanceof EntityPlayer && bp != null) {
                    final TileEntity te = world.func_175625_s(bp);
                    if (te != null && te instanceof TileGolemBuilder) {
                        ((TileGolemBuilder)te).startCraft(message.golem, (EntityPlayer)player);
                    }
                }
            }
        });
        return null;
    }
}
