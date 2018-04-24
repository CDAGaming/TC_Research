package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.client.lib.events.*;
import net.minecraft.entity.player.*;

public class PacketMiscEvent implements IMessage, IMessageHandler<PacketMiscEvent, IMessage>
{
    private byte type;
    private int value;
    public static final byte WARP_EVENT = 0;
    public static final byte MIST_EVENT = 1;
    public static final byte MIST_EVENT_SHORT = 2;
    
    public PacketMiscEvent() {
        this.value = 0;
    }
    
    public PacketMiscEvent(final byte type) {
        this.value = 0;
        this.type = type;
    }
    
    public PacketMiscEvent(final byte type, final int value) {
        this.value = 0;
        this.type = type;
        this.value = value;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeByte((int)this.type);
        if (this.value != 0) {
            buffer.writeInt(this.value);
        }
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.type = buffer.readByte();
        if (buffer.isReadable()) {
            this.value = buffer.readInt();
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketMiscEvent message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketMiscEvent.this.processMessage(message);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketMiscEvent message) {
        final EntityPlayer p = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        switch (message.type) {
            case 0: {
                if (!ModConfig.CONFIG_GRAPHICS.nostress) {
                    p.field_70170_p.func_184134_a(p.field_70165_t, p.field_70163_u, p.field_70161_v, SoundsTC.heartbeat, SoundCategory.AMBIENT, 1.0f, 1.0f, false);
                    break;
                }
                break;
            }
            case 1: {
                RenderEventHandler.fogFiddled = true;
                RenderEventHandler.fogDuration = 2400;
                break;
            }
            case 2: {
                RenderEventHandler.fogFiddled = true;
                if (RenderEventHandler.fogDuration < 200) {
                    RenderEventHandler.fogDuration = 200;
                    break;
                }
                break;
            }
        }
    }
}
