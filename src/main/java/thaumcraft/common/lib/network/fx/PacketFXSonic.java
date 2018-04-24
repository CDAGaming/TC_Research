package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.*;
import thaumcraft.client.fx.other.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXSonic implements IMessage, IMessageHandler<PacketFXSonic, IMessage>
{
    private int source;
    
    public PacketFXSonic() {
    }
    
    public PacketFXSonic(final int source) {
        this.source = source;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.source);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.source = buffer.readInt();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketFXSonic message, final MessageContext ctx) {
        final Entity p = Thaumcraft.proxy.getClientWorld().func_73045_a(message.source);
        if (p != null) {
            final FXSonic fb = new FXSonic(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 10);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb);
        }
        return null;
    }
}
