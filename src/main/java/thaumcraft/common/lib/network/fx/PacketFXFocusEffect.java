package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import thaumcraft.api.casters.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXFocusEffect implements IMessage, IMessageHandler<PacketFXFocusEffect, IMessage>
{
    float x;
    float y;
    float z;
    float mx;
    float my;
    float mz;
    String parts;
    
    public PacketFXFocusEffect() {
    }
    
    public PacketFXFocusEffect(final float x, final float y, final float z, final float mx, final float my, final float mz, final String[] parts) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.parts = "";
        for (int a = 0; a < parts.length; ++a) {
            if (a > 0) {
                this.parts += "%";
            }
            this.parts += parts[a];
        }
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeFloat(this.x);
        buffer.writeFloat(this.y);
        buffer.writeFloat(this.z);
        buffer.writeFloat(this.mx);
        buffer.writeFloat(this.my);
        buffer.writeFloat(this.mz);
        ByteBufUtils.writeUTF8String(buffer, this.parts);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readFloat();
        this.y = buffer.readFloat();
        this.z = buffer.readFloat();
        this.mx = buffer.readFloat();
        this.my = buffer.readFloat();
        this.mz = buffer.readFloat();
        this.parts = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketFXFocusEffect message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketFXFocusEffect.this.processMessage(message);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketFXFocusEffect message) {
        final String[] partKeys = message.parts.split("%");
        final int amt = Math.max(1, 10 / partKeys.length);
        for (final String k : partKeys) {
            final IFocusElement part = FocusEngine.getElement(k);
            if (part != null && part instanceof FocusEffect) {
                for (int a = 0; a < amt; ++a) {
                    ((FocusEffect)part).renderParticleFX((World)Minecraft.func_71410_x().field_71441_e, message.x, message.y, message.z, message.mx + Minecraft.func_71410_x().field_71441_e.field_73012_v.nextGaussian() / 20.0, message.my + Minecraft.func_71410_x().field_71441_e.field_73012_v.nextGaussian() / 20.0, message.mz + Minecraft.func_71410_x().field_71441_e.field_73012_v.nextGaussian() / 20.0);
                }
            }
        }
    }
}
