package thaumcraft.common.lib.network;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber
public class EventHandlerNetwork
{
    @SubscribeEvent
    public void playerLoggedInEvent(final PlayerEvent.PlayerLoggedInEvent event) {
        final Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            final EntityPlayer p = event.player;
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketSyncWarp(p), (EntityPlayerMP)p);
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketSyncKnowledge(p), (EntityPlayerMP)p);
        }
    }
}
