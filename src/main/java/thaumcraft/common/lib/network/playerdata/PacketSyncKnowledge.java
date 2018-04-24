package thaumcraft.common.lib.network.playerdata;

import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import java.util.*;
import io.netty.buffer.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraft.nbt.*;
import thaumcraft.client.gui.*;
import net.minecraft.client.gui.toasts.*;
import thaumcraft.api.research.*;
import net.minecraftforge.fml.relauncher.*;

public class PacketSyncKnowledge implements IMessage, IMessageHandler<PacketSyncKnowledge, IMessage>
{
    protected NBTTagCompound data;
    
    public PacketSyncKnowledge() {
    }
    
    public PacketSyncKnowledge(final EntityPlayer player) {
        final IPlayerKnowledge pk = ThaumcraftCapabilities.getKnowledge(player);
        this.data = (NBTTagCompound)pk.serializeNBT();
        for (final String key : pk.getResearchList()) {
            pk.clearResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.POPUP);
        }
    }
    
    public void toBytes(final ByteBuf buffer) {
        Utils.writeNBTTagCompoundToBuffer(buffer, this.data);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.data = Utils.readNBTTagCompoundFromBuffer(buffer);
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketSyncKnowledge message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
                final IPlayerKnowledge pk = ThaumcraftCapabilities.getKnowledge(player);
                pk.deserializeNBT((NBTBase)message.data);
                for (final String key : pk.getResearchList()) {
                    if (pk.hasResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.POPUP)) {
                        final ResearchEntry ri = ResearchCategories.getResearch(key);
                        if (ri != null) {
                            Minecraft.func_71410_x().func_193033_an().func_192988_a((IToast)new ResearchToast(ri));
                        }
                    }
                    pk.clearResearchFlag(key, IPlayerKnowledge.EnumResearchFlag.POPUP);
                }
            }
        });
        return null;
    }
}
