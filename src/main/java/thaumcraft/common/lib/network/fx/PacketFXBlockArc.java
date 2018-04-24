package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.client.fx.*;

public class PacketFXBlockArc implements IMessage, IMessageHandler<PacketFXBlockArc, IMessage>
{
    private int x;
    private int y;
    private int z;
    private float tx;
    private float ty;
    private float tz;
    private float r;
    private float g;
    private float b;
    
    public PacketFXBlockArc() {
    }
    
    public PacketFXBlockArc(final BlockPos pos, final Entity source, final float r, final float g, final float b) {
        this.x = pos.func_177958_n();
        this.y = pos.func_177956_o();
        this.z = pos.func_177952_p();
        this.tx = (float)source.field_70165_t;
        this.ty = (float)(source.func_174813_aQ().field_72338_b + source.field_70131_O / 2.0f);
        this.tz = (float)source.field_70161_v;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public PacketFXBlockArc(final BlockPos pos, final BlockPos pos2, final float r, final float g, final float b) {
        this.x = pos.func_177958_n();
        this.y = pos.func_177956_o();
        this.z = pos.func_177952_p();
        this.tx = pos2.func_177958_n() + 0.5f;
        this.ty = pos2.func_177956_o() + 0.5f;
        this.tz = pos2.func_177952_p() + 0.5f;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeFloat(this.tx);
        buffer.writeFloat(this.ty);
        buffer.writeFloat(this.tz);
        buffer.writeFloat(this.r);
        buffer.writeFloat(this.g);
        buffer.writeFloat(this.b);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.tx = buffer.readFloat();
        this.ty = buffer.readFloat();
        this.tz = buffer.readFloat();
        this.r = buffer.readFloat();
        this.g = buffer.readFloat();
        this.b = buffer.readFloat();
    }
    
    public IMessage onMessage(final PacketFXBlockArc message, final MessageContext ctx) {
        FXDispatcher.INSTANCE.arcLightning(message.tx, message.ty, message.tz, message.x + 0.5, message.y + 0.5, message.z + 0.5, message.r, message.g, message.b, 0.5f);
        return null;
    }
}
