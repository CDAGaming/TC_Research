package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.client.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXSlash implements IMessage, IMessageHandler<PacketFXSlash, IMessage>
{
    private int source;
    private int target;
    
    public PacketFXSlash() {
    }
    
    public PacketFXSlash(final int source, final int target) {
        this.source = source;
        this.target = target;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.source);
        buffer.writeInt(this.target);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.source = buffer.readInt();
        this.target = buffer.readInt();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketFXSlash message, final MessageContext ctx) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final WorldClient world = mc.field_71441_e;
        final Entity var2 = this.getEntityByID(message.source, mc, world);
        final Entity var3 = this.getEntityByID(message.target, mc, world);
        if (var2 != null && var3 != null) {
            FXDispatcher.INSTANCE.drawSlash(var2.field_70165_t, var2.func_174813_aQ().field_72338_b + var2.field_70131_O / 2.0f, var2.field_70161_v, var3.field_70165_t, var3.func_174813_aQ().field_72338_b + var3.field_70131_O / 2.0f, var3.field_70161_v, 8);
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    private Entity getEntityByID(final int par1, final Minecraft mc, final WorldClient world) {
        return (Entity)((par1 == mc.field_71439_g.func_145782_y()) ? mc.field_71439_g : world.func_73045_a(par1));
    }
}
