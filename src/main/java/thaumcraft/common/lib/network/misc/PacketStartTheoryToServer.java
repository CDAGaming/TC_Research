package thaumcraft.common.lib.network.misc;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import java.util.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class PacketStartTheoryToServer implements IMessage, IMessageHandler<PacketStartTheoryToServer, IMessage>
{
    private long pos;
    private Set<String> aids;
    
    public PacketStartTheoryToServer() {
        this.aids = new HashSet<String>();
    }
    
    public PacketStartTheoryToServer(final BlockPos pos, final Set<String> aids) {
        this.aids = new HashSet<String>();
        this.pos = pos.func_177986_g();
        this.aids = aids;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeLong(this.pos);
        buffer.writeByte(this.aids.size());
        for (final String aid : this.aids) {
            ByteBufUtils.writeUTF8String(buffer, aid);
        }
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.pos = buffer.readLong();
        for (int s = buffer.readByte(), a = 0; a < s; ++a) {
            this.aids.add(ByteBufUtils.readUTF8String(buffer));
        }
    }
    
    public IMessage onMessage(final PacketStartTheoryToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final World world = (World)ctx.getServerHandler().field_147369_b.func_71121_q();
                final Entity player = (Entity)ctx.getServerHandler().field_147369_b;
                final BlockPos bp = BlockPos.func_177969_a(message.pos);
                if (world != null && player != null && player instanceof EntityPlayer && bp != null) {
                    final TileEntity te = world.func_175625_s(bp);
                    if (te != null && te instanceof TileResearchTable) {
                        ((TileResearchTable)te).startNewTheory((EntityPlayer)player, message.aids);
                    }
                }
            }
        });
        return null;
    }
}
