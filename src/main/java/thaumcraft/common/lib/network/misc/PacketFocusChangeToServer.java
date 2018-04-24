package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.items.casters.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PacketFocusChangeToServer implements IMessage, IMessageHandler<PacketFocusChangeToServer, IMessage>
{
    private String focus;
    
    public PacketFocusChangeToServer() {
    }
    
    public PacketFocusChangeToServer(final String focus) {
        this.focus = focus;
    }
    
    public void toBytes(final ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.focus);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.focus = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketFocusChangeToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final World world = (World)ctx.getServerHandler().field_147369_b.func_71121_q();
                if (world == null) {
                    return;
                }
                final Entity player = (Entity)ctx.getServerHandler().field_147369_b;
                if (player != null && player instanceof EntityPlayer && ((EntityPlayer)player).func_184614_ca().func_77973_b() instanceof ICaster) {
                    CasterManager.changeFocus(((EntityPlayer)player).func_184614_ca(), world, (EntityPlayer)player, message.focus);
                }
                else if (player != null && player instanceof EntityPlayer && ((EntityPlayer)player).func_184592_cb().func_77973_b() instanceof ICaster) {
                    CasterManager.changeFocus(((EntityPlayer)player).func_184592_cb(), world, (EntityPlayer)player, message.focus);
                }
            }
        });
        return null;
    }
}
