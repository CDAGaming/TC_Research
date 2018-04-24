package thaumcraft.common.lib.network.misc;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.client.lib.events.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;

public class PacketKnowledgeGain implements IMessage, IMessageHandler<PacketKnowledgeGain, IMessage>
{
    private byte type;
    private String cat;
    
    public PacketKnowledgeGain() {
    }
    
    public PacketKnowledgeGain(final byte type, final String value) {
        this.type = type;
        this.cat = ((value == null) ? "" : value);
    }
    
    public void toBytes(final ByteBuf buffer) {
        buffer.writeByte((int)this.type);
        ByteBufUtils.writeUTF8String(buffer, this.cat);
    }
    
    public void fromBytes(final ByteBuf buffer) {
        this.type = buffer.readByte();
        this.cat = ByteBufUtils.readUTF8String(buffer);
    }
    
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(final PacketKnowledgeGain message, final MessageContext ctx) {
        Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
            @Override
            public void run() {
                PacketKnowledgeGain.this.processMessage(message);
            }
        });
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    void processMessage(final PacketKnowledgeGain message) {
        final EntityPlayer p = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        final IPlayerKnowledge.EnumKnowledgeType type = IPlayerKnowledge.EnumKnowledgeType.values()[message.type];
        final ResearchCategory cat = (message.cat.length() > 0) ? ResearchCategories.getResearchCategory(message.cat) : null;
        final RenderEventHandler instance = RenderEventHandler.INSTANCE;
        RenderEventHandler.hudHandler.knowledgeGainTrackers.add(new HudHandler.KnowledgeGainTracker(type, cat, 40 + p.field_70170_p.field_73012_v.nextInt(20), p.field_70170_p.field_73012_v.nextLong()));
        p.field_70170_p.func_184134_a(p.field_70165_t, p.field_70163_u, p.field_70161_v, SoundsTC.learn, SoundCategory.AMBIENT, 1.0f, 1.0f, false);
    }
}
