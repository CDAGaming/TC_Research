package thaumcraft.common.lib.network.playerdata;

import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import io.netty.buffer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketSyncWarp implements IMessage, IMessageHandler<PacketSyncWarp, IMessage>
{
    protected NBTTagCompound data;
    
    public PacketSyncWarp() {
    }
    
    public PacketSyncWarp(final EntityPlayer player) {
        final IPlayerWarp pk = ThaumcraftCapabilities.getWarp(player);
        this.data = (NBTTagCompound)pk.serializeNBT();
    }
    
    public void toBytes(final ByteBuf buffer) {
        Utils.writeNBTTagCompoundToBuffer(buffer, this.data);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.data = Utils.readNBTTagCompoundFromBuffer(buffer);
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketSyncWarp message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
                final IPlayerWarp pk = ThaumcraftCapabilities.getWarp(player);
                pk.deserializeNBT((NBTBase)message.data);
            }
        });
        return null;
    }
}
