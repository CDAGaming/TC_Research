package thaumcraft.common.lib.network.misc;

import thaumcraft.common.world.aura.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.world.chunk.*;
import thaumcraft.client.lib.events.*;

public class PacketAuraToClient implements IMessage, IMessageHandler<PacketAuraToClient, IMessage>
{
    short base;
    float vis;
    float flux;
    
    public PacketAuraToClient() {
    }
    
    public PacketAuraToClient(final AuraChunk ac) {
        this.base = ac.getBase();
        this.vis = ac.getVis();
        this.flux = ac.getFlux();
    }
    
    public void toBytes(final ByteBuf dos) {
        dos.writeShort((int)this.base);
        dos.writeFloat(this.vis);
        dos.writeFloat(this.flux);
    }
    
    public void fromBytes(final ByteBuf dat) {
        this.base = dat.readShort();
        this.vis = dat.readFloat();
        this.flux = dat.readFloat();
    }
    
    public IMessage onMessage(final PacketAuraToClient message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                HudHandler.currentAura = new AuraChunk(null, message.base, message.vis, message.flux);
            }
        });
        return null;
    }
}
