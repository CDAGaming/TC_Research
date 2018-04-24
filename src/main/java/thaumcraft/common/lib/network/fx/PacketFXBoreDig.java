package thaumcraft.common.lib.network.fx;

import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.events.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketFXBoreDig implements IMessage, IMessageHandler<PacketFXBoreDig, IMessage>
{
    private int x;
    private int y;
    private int z;
    private int bore;
    private int delay;
    
    public PacketFXBoreDig() {
    }
    
    public PacketFXBoreDig(final BlockPos pos, final Entity bore, final int delay) {
        this.x = pos.func_177958_n();
        this.y = pos.func_177956_o();
        this.z = pos.func_177952_p();
        this.bore = bore.func_145782_y();
        this.delay = delay;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.bore);
        buffer.writeInt(this.delay);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.bore = buffer.readInt();
        this.delay = buffer.readInt();
    }
    
    public IMessage onMessage(final PacketFXBoreDig message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketFXBoreDig.this.processMessage(message);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketFXBoreDig message) {
        try {
            final World world = (World)Minecraft.func_71410_x().field_71441_e;
            final BlockPos pos = new BlockPos(message.x, message.y, message.z);
            final Entity entity = world.func_73045_a(message.bore);
            if (entity == null) {
                return;
            }
            final IBlockState ts = world.func_180495_p(pos);
            if (ts.func_177230_c() == Blocks.field_150350_a) {
                return;
            }
            for (int a = 0; a < message.delay; ++a) {
                ServerEvents.addRunnableClient(world, new Runnable() {
                    @Override
                    public void run() {
                        FXDispatcher.INSTANCE.boreDigFx(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), entity, ts, ts.func_177230_c().func_176201_c(ts) >> 12 & 0xFF, message.delay);
                    }
                }, a);
            }
        }
        catch (Exception ex) {}
    }
}
