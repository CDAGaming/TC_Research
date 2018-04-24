package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import java.util.*;
import thaumcraft.api.casters.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXFocusPartImpactBurst implements IMessage, IMessageHandler<PacketFXFocusPartImpactBurst, IMessage>
{
    private double x;
    private double y;
    private double z;
    private String parts;
    
    public PacketFXFocusPartImpactBurst() {
    }
    
    public PacketFXFocusPartImpactBurst(final double x, final double y, final double z, final String[] parts) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.parts = "";
        for (int a = 0; a < parts.length; ++a) {
            if (a > 0) {
                this.parts += "%";
            }
            this.parts += parts[a];
        }
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeFloat((float)this.x);
        buffer.writeFloat((float)this.y);
        buffer.writeFloat((float)this.z);
        ByteBufUtils.writeUTF8String(buffer, this.parts);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readFloat();
        this.y = buffer.readFloat();
        this.z = buffer.readFloat();
        this.parts = ByteBufUtils.readUTF8String(buffer);
    }
    
    public IMessage onMessage(final PacketFXFocusPartImpactBurst message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketFXFocusPartImpactBurst.this.processMessage(message);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketFXFocusPartImpactBurst message) {
        final String[] partKeys = message.parts.split("%");
        final int amt = Math.max(1, 20 / partKeys.length);
        final Random r = Minecraft.func_71410_x().field_71441_e.field_73012_v;
        for (final String k : partKeys) {
            final IFocusElement part = FocusEngine.getElement(k);
            if (part != null && part instanceof FocusEffect) {
                for (int a = 0; a < amt; ++a) {
                    ((FocusEffect)part).renderParticleFX((World)Minecraft.func_71410_x().field_71441_e, message.x, message.y, message.z, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4, r.nextGaussian() * 0.4);
                }
            }
        }
    }
}
