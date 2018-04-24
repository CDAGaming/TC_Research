package thaumcraft.common.lib.network.fx;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.other.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXShield implements IMessage, IMessageHandler<PacketFXShield, IMessage>
{
    private int source;
    private int target;
    
    public PacketFXShield() {
    }
    
    public PacketFXShield(final int source, final int target) {
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
    public IMessage onMessage(final PacketFXShield message, final MessageContext ctx) {
        final Entity p = Thaumcraft.proxy.getClientWorld().func_73045_a(message.source);
        if (p == null) {
            return null;
        }
        float pitch = 0.0f;
        float yaw = 0.0f;
        if (message.target >= 0) {
            final Entity t = Thaumcraft.proxy.getClientWorld().func_73045_a(message.target);
            if (t != null) {
                final double d0 = p.field_70165_t - t.field_70165_t;
                final double d2 = (p.func_174813_aQ().field_72338_b + p.func_174813_aQ().field_72337_e) / 2.0 - (t.func_174813_aQ().field_72338_b + t.func_174813_aQ().field_72337_e) / 2.0;
                final double d3 = p.field_70161_v - t.field_70161_v;
                final double d4 = MathHelper.func_76133_a(d0 * d0 + d3 * d3);
                final float f = (float)(Math.atan2(d3, d0) * 180.0 / 3.141592653589793) - 90.0f;
                final float f2 = pitch = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
                yaw = f;
            }
            else {
                pitch = 90.0f;
                yaw = 0.0f;
            }
            final FXShieldRunes fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 8, yaw, pitch);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb);
        }
        else if (message.target == -1) {
            FXShieldRunes fb2 = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 8, 0.0f, 90.0f);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb2);
            fb2 = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 8, 0.0f, 270.0f);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb2);
        }
        else if (message.target == -2) {
            final FXShieldRunes fb2 = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 8, 0.0f, 270.0f);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb2);
        }
        else if (message.target == -3) {
            final FXShieldRunes fb2 = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.field_70165_t, p.field_70163_u, p.field_70161_v, p, 8, 0.0f, 90.0f);
            FMLClientHandler.instance().getClient().field_71452_i.func_78873_a((Particle)fb2);
        }
        return null;
    }
}
