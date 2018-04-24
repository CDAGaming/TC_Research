package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.awt.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXZap implements IMessage, IMessageHandler<PacketFXZap, IMessage>
{
    private Vec3d source;
    private Vec3d target;
    private int color;
    private float width;
    
    public PacketFXZap() {
    }
    
    public PacketFXZap(final Vec3d source, final Vec3d target, final int color, final float width) {
        this.source = source;
        this.target = target;
        this.color = color;
        this.width = width;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeDouble(this.source.field_72450_a);
        buffer.writeDouble(this.source.field_72448_b);
        buffer.writeDouble(this.source.field_72449_c);
        buffer.writeDouble(this.target.field_72450_a);
        buffer.writeDouble(this.target.field_72448_b);
        buffer.writeDouble(this.target.field_72449_c);
        buffer.writeInt(this.color);
        buffer.writeFloat(this.width);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.source = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        this.target = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        this.color = buffer.readInt();
        this.width = buffer.readFloat();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketFXZap message, final MessageContext ctx) {
        final Color c = new Color(message.color);
        FXDispatcher.INSTANCE.arcBolt(message.source.field_72450_a, message.source.field_72448_b, message.source.field_72449_c, message.target.field_72450_a, message.target.field_72448_b, message.target.field_72449_c, c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, message.width);
        return null;
    }
}
