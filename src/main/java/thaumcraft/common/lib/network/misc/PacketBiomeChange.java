package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import thaumcraft.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketBiomeChange implements IMessage, IMessageHandler<PacketBiomeChange, IMessage>
{
    private int x;
    private int z;
    private short biome;
    
    public PacketBiomeChange() {
    }
    
    public PacketBiomeChange(final int x, final int z, final short biome) {
        this.x = x;
        this.z = z;
        this.biome = biome;
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.z);
        buffer.writeShort((int)this.biome);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.z = buffer.readInt();
        this.biome = buffer.readShort();
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketBiomeChange message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                Utils.setBiomeAt(Thaumcraft.proxy.getClientWorld(), new BlockPos(message.x, 0, message.z), Biome.func_150568_d((int)message.biome));
            }
        });
        return null;
    }
}
