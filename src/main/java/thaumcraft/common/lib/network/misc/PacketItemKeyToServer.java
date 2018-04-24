package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.common.items.tools.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PacketItemKeyToServer implements IMessage, IMessageHandler<PacketItemKeyToServer, IMessage>
{
    private byte key;
    private byte mod;
    
    public PacketItemKeyToServer() {
    }
    
    public PacketItemKeyToServer(final int key) {
        this.key = (byte)key;
        this.mod = 0;
    }
    
    public PacketItemKeyToServer(final int key, final int mod) {
        this.key = (byte)key;
        this.mod = (byte)mod;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeByte((int)this.key);
        buffer.writeByte((int)this.mod);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.key = buffer.readByte();
        this.mod = buffer.readByte();
    }
    
    public IMessage onMessage(final PacketItemKeyToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final World world = (World)ctx.getServerHandler().field_147369_b.func_71121_q();
                if (world == null) {
                    return;
                }
                final Entity player = (Entity)ctx.getServerHandler().field_147369_b;
                if (player != null && player instanceof EntityPlayer && ((EntityPlayer)player).func_184614_ca() != null) {
                    if (message.key == 1 && ((EntityPlayer)player).func_184614_ca().func_77973_b() instanceof ICaster) {
                        CasterManager.toggleMisc(((EntityPlayer)player).func_184614_ca(), world, (EntityPlayer)player, message.mod);
                    }
                    if (message.key == 1 && ((EntityPlayer)player).func_184614_ca().func_77973_b() instanceof ItemElementalShovel) {
                        final ItemElementalShovel itemElementalShovel = (ItemElementalShovel)((EntityPlayer)player).func_184614_ca().func_77973_b();
                        final byte b = ItemElementalShovel.getOrientation(((EntityPlayer)player).func_184614_ca());
                        final ItemElementalShovel itemElementalShovel2 = (ItemElementalShovel)((EntityPlayer)player).func_184614_ca().func_77973_b();
                        ItemElementalShovel.setOrientation(((EntityPlayer)player).func_184614_ca(), (byte)(b + 1));
                    }
                }
            }
        });
        return null;
    }
}
