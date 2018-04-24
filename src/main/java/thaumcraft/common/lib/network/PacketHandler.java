package thaumcraft.common.lib.network;

import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.network.misc.*;
import thaumcraft.common.lib.network.playerdata.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE;
    
    public static void preInit() {
        int idx = 0;
        PacketHandler.INSTANCE.registerMessage((Class)PacketBiomeChange.class, (Class)PacketBiomeChange.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketMiscEvent.class, (Class)PacketMiscEvent.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketKnowledgeGain.class, (Class)PacketKnowledgeGain.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketStartGolemCraftToServer.class, (Class)PacketStartGolemCraftToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketStartTheoryToServer.class, (Class)PacketStartTheoryToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketLogisticsRequestToServer.class, (Class)PacketLogisticsRequestToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketMiscStringToServer.class, (Class)PacketMiscStringToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFocusNodesToServer.class, (Class)PacketFocusNodesToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFocusNameToServer.class, (Class)PacketFocusNameToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketAuraToClient.class, (Class)PacketAuraToClient.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSealToClient.class, (Class)PacketSealToClient.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketNote.class, (Class)PacketNote.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSyncWarp.class, (Class)PacketSyncWarp.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSyncKnowledge.class, (Class)PacketSyncKnowledge.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketWarpMessage.class, (Class)PacketWarpMessage.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketNote.class, (Class)PacketNote.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketItemKeyToServer.class, (Class)PacketItemKeyToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFocusChangeToServer.class, (Class)PacketFocusChangeToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSyncProgressToServer.class, (Class)PacketSyncProgressToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketSyncResearchFlagsToServer.class, (Class)PacketSyncResearchFlagsToServer.class, idx++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXPollute.class, (Class)PacketFXPollute.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXBlockBamf.class, (Class)PacketFXBlockBamf.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXFocusEffect.class, (Class)PacketFXFocusEffect.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXFocusPartImpact.class, (Class)PacketFXFocusPartImpact.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXFocusPartImpactBurst.class, (Class)PacketFXFocusPartImpactBurst.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXBlockMist.class, (Class)PacketFXBlockMist.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXBlockArc.class, (Class)PacketFXBlockArc.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXEssentiaSource.class, (Class)PacketFXEssentiaSource.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXInfusionSource.class, (Class)PacketFXInfusionSource.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXShield.class, (Class)PacketFXShield.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXSonic.class, (Class)PacketFXSonic.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXWispZap.class, (Class)PacketFXWispZap.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXZap.class, (Class)PacketFXZap.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXSlash.class, (Class)PacketFXSlash.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXScanSource.class, (Class)PacketFXScanSource.class, idx++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage((Class)PacketFXBoreDig.class, (Class)PacketFXBoreDig.class, idx++, Side.CLIENT);
    }
    
    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("thaumcraft".toLowerCase());
    }
}
