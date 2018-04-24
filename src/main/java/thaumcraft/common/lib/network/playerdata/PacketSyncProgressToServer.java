package thaumcraft.common.lib.network.playerdata;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.research.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.research.*;
import net.minecraft.item.*;

public class PacketSyncProgressToServer implements IMessage, IMessageHandler<PacketSyncProgressToServer, IMessage>
{
    private String key;
    private boolean first;
    private boolean checks;
    private boolean noFlags;
    
    public PacketSyncProgressToServer() {
    }
    
    public PacketSyncProgressToServer(final String key, final boolean first, final boolean checks, final boolean noFlags) {
        this.key = key;
        this.first = first;
        this.checks = checks;
        this.noFlags = noFlags;
    }
    
    public PacketSyncProgressToServer(final String key, final boolean first) {
        this(key, first, false, true);
    }
    
    public void toBytes(final ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.key);
        buffer.writeBoolean(this.first);
        buffer.writeBoolean(this.checks);
        buffer.writeBoolean(this.noFlags);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.key = ByteBufUtils.readUTF8String(buffer);
        this.first = buffer.readBoolean();
        this.checks = buffer.readBoolean();
        this.noFlags = buffer.readBoolean();
    }
    
    public IMessage onMessage(final PacketSyncProgressToServer message, final MessageContext ctx) {
        final IThreadListener mainThread = (IThreadListener)ctx.getServerHandler().field_147369_b.func_71121_q();
        mainThread.func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                final EntityPlayer player = (EntityPlayer)ctx.getServerHandler().field_147369_b;
                if (player != null && message.first != ThaumcraftCapabilities.knowsResearch(player, message.key)) {
                    if (message.checks && !PacketSyncProgressToServer.this.checkRequisites(player, message.key)) {
                        return;
                    }
                    if (message.noFlags) {
                        ResearchManager.noFlags = true;
                    }
                    ResearchManager.progressResearch(player, message.key);
                }
            }
        });
        return null;
    }
    
    private boolean checkRequisites(final EntityPlayer player, final String key) {
        final ResearchEntry research = ResearchCategories.getResearch(key);
        if (research.getStages() != null) {
            final int currentStage = ThaumcraftCapabilities.getKnowledge(player).getResearchStage(key) - 1;
            if (currentStage < 0) {
                return false;
            }
            if (currentStage >= research.getStages().length) {
                return true;
            }
            final ResearchStage stage = research.getStages()[currentStage];
            final ItemStack[] o = stage.getObtain();
            if (o != null) {
                for (int a = 0; a < o.length; ++a) {
                    if (!InventoryUtils.isPlayerCarryingAmount(player, o[a], false)) {
                        return false;
                    }
                }
                for (int a = 0; a < o.length; ++a) {
                    InventoryUtils.consumePlayerItem(player, o[a], true, false);
                }
            }
            final ItemStack[] c = stage.getCraft();
            if (c != null) {
                for (int a2 = 0; a2 < c.length; ++a2) {
                    if (!ThaumcraftCapabilities.getKnowledge(player).isResearchKnown("[#]" + stage.getCraftReference()[a2])) {
                        return false;
                    }
                }
            }
            final String[] r = stage.getResearch();
            if (r != null) {
                for (int a3 = 0; a3 < r.length; ++a3) {
                    if (!ThaumcraftCapabilities.knowsResearchStrict(player, r[a3])) {
                        return false;
                    }
                }
            }
            final ResearchStage.Knowledge[] k = stage.getKnow();
            if (k != null) {
                for (int a4 = 0; a4 < k.length; ++a4) {
                    final int pk = ThaumcraftCapabilities.getKnowledge(player).getKnowledge(k[a4].type, k[a4].category);
                    if (pk < k[a4].amount) {
                        return false;
                    }
                }
                for (int a4 = 0; a4 < k.length; ++a4) {
                    ResearchManager.addKnowledge(player, k[a4].type, k[a4].category, -k[a4].amount * k[a4].type.getProgression());
                }
            }
        }
        return true;
    }
}
