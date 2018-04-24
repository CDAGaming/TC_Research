package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.client.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.api.aspects.*;
import java.awt.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXWispZap implements IMessage, IMessageHandler<PacketFXWispZap, IMessage>
{
    private int source;
    private int target;
    
    public PacketFXWispZap() {
    }
    
    public PacketFXWispZap(final int source, final int target) {
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
    
    public IMessage onMessage(final PacketFXWispZap message, final MessageContext ctx) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final WorldClient world = mc.field_71441_e;
        final Entity var2 = this.getEntityByID(message.source, mc, world);
        final Entity var3 = this.getEntityByID(message.target, mc, world);
        if (var2 != null && var3 != null) {
            float r = 1.0f;
            float g = 1.0f;
            float b = 1.0f;
            if (var2 instanceof EntityWisp) {
                final Color c = new Color(Aspect.getAspect(((EntityWisp)var2).getType()).getColor());
                r = c.getRed() / 255.0f;
                g = c.getGreen() / 255.0f;
                b = c.getBlue() / 255.0f;
            }
            FXDispatcher.INSTANCE.arcBolt(var2.field_70165_t, var2.field_70163_u, var2.field_70161_v, var3.field_70165_t, var3.field_70163_u, var3.field_70161_v, r, g, b, 0.6f);
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    private Entity getEntityByID(final int par1, final Minecraft mc, final WorldClient world) {
        return (Entity)((par1 == mc.field_71439_g.func_145782_y()) ? mc.field_71439_g : world.func_73045_a(par1));
    }
}
