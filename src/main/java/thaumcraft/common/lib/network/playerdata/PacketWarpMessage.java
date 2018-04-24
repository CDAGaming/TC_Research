package thaumcraft.common.lib.network.playerdata;

import net.minecraft.entity.player.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.text.*;

public class PacketWarpMessage implements IMessage, IMessageHandler<PacketWarpMessage, IMessage>
{
    protected int data;
    protected byte type;
    
    public PacketWarpMessage() {
        this.data = 0;
        this.type = 0;
    }
    
    public PacketWarpMessage(final EntityPlayer player, final byte type, final int change) {
        this.data = 0;
        this.type = 0;
        this.data = change;
        this.type = type;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.data);
        buffer.writeByte((int)this.type);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.data = buffer.readInt();
        this.type = buffer.readByte();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketWarpMessage message, final MessageContext ctx) {
        if (message.data != 0) {
            Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
                @Override
                public void run() {
                    PacketWarpMessage.this.processMessage(message);
                }
            });
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketWarpMessage message) {
        if (message.type == 0 && message.data > 0) {
            String text = I18n.func_74838_a("tc.addwarp");
            if (message.data < 0) {
                text = I18n.func_74838_a("tc.removewarp");
            }
            else {
                Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.whispers, 0.5f, 1.0f);
            }
        }
        else if (message.type == 1) {
            String text = I18n.func_74838_a("tc.addwarpsticky");
            if (message.data < 0) {
                text = I18n.func_74838_a("tc.removewarpsticky");
            }
            else {
                Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.whispers, 0.5f, 1.0f);
            }
            Minecraft.func_71410_x().field_71439_g.func_146105_b((ITextComponent)new TextComponentString(text), true);
        }
        else if (message.data > 0) {
            String text = I18n.func_74838_a("tc.addwarptemp");
            if (message.data < 0) {
                text = I18n.func_74838_a("tc.removewarptemp");
            }
            Minecraft.func_71410_x().field_71439_g.func_146105_b((ITextComponent)new TextComponentString(text), true);
        }
    }
}
