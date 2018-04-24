package thaumcraft.common.lib.network.playerdata;

import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.utils.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;

public class PacketSyncResearchFlagsToServer implements IMessage, IMessageHandler<PacketSyncResearchFlagsToServer, IMessage>
{
    String key;
    byte flags;
    
    public PacketSyncResearchFlagsToServer() {
    }
    
    public PacketSyncResearchFlagsToServer(final EntityPlayer player, final String key) {
        this.key = key;
        this.flags = Utils.pack(ThaumcraftCapabilities.getKnowledge(player).hasResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.PAGE), ThaumcraftCapabilities.getKnowledge(player).hasResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.POPUP), ThaumcraftCapabilities.getKnowledge(player).hasResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.RESEARCH));
    }
    
    public void toBytes(final ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.key);
        buffer.writeByte((int)this.flags);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.key = ByteBufUtils.readUTF8String(buffer);
        this.flags = buffer.readByte();
    }
    
    public IMessage onMessage(final PacketSyncResearchFlagsToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final boolean[] b = Utils.unpack(message.flags);
                if (ctx.getServerHandler().field_147369_b != null) {
                    final EntityPlayer player = (EntityPlayer)ctx.getServerHandler().field_147369_b;
                    if (b[0]) {
                        ThaumcraftCapabilities.getKnowledge(player).setResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.PAGE);
                    }
                    else {
                        ThaumcraftCapabilities.getKnowledge(player).clearResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.PAGE);
                    }
                    if (b[1]) {
                        ThaumcraftCapabilities.getKnowledge(player).setResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.POPUP);
                    }
                    else {
                        ThaumcraftCapabilities.getKnowledge(player).clearResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.POPUP);
                    }
                    if (b[2]) {
                        ThaumcraftCapabilities.getKnowledge(player).setResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.RESEARCH);
                    }
                    else {
                        ThaumcraftCapabilities.getKnowledge(player).clearResearchFlag(message.key, IPlayerKnowledge.EnumResearchFlag.RESEARCH);
                    }
                }
            }
        });
        return null;
    }
}
