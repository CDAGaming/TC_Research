package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import thaumcraft.common.config.*;
import net.minecraft.nbt.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import net.minecraft.util.math.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.api.golems.seals.*;
import net.minecraftforge.fml.common.eventhandler.*;
import thaumcraft.*;
import java.util.*;
import net.minecraftforge.event.world.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

@Mod.EventBusSubscriber
public class ChunkEvents
{
    @SubscribeEvent
    public static void chunkSave(final ChunkDataEvent.Save event) {
        final int dim = event.getWorld().field_73011_w.getDimension();
        final ChunkPos loc = event.getChunk().func_76632_l();
        final NBTTagCompound nbt = new NBTTagCompound();
        event.getData().func_74782_a("Thaumcraft", (NBTBase)nbt);
        nbt.func_74757_a(ModConfig.CONFIG_WORLD.regenKey, true);
        final AuraChunk ac = AuraHandler.getAuraChunk(dim, loc.field_77276_a, loc.field_77275_b);
        if (ac != null) {
            nbt.func_74777_a("base", ac.getBase());
            nbt.func_74776_a("flux", ac.getFlux());
            nbt.func_74776_a("vis", ac.getVis());
            if (!event.getChunk().func_177410_o()) {
                AuraHandler.removeAuraChunk(dim, loc.field_77276_a, loc.field_77275_b);
            }
        }
        final NBTTagList tagList = new NBTTagList();
        for (final ISealEntity seal : SealHandler.getSealsInChunk(event.getWorld(), loc)) {
            final NBTTagCompound sealnbt = seal.writeNBT();
            tagList.func_74742_a((NBTBase)sealnbt);
            if (!event.getChunk().func_177410_o()) {
                SealHandler.removeSealEntity(event.getWorld(), seal.getSealPos(), true);
            }
        }
        nbt.func_74782_a("seals", (NBTBase)tagList);
    }
    
    @SubscribeEvent
    public static void chunkLoad(final ChunkDataEvent.Load event) {
        final int dim = event.getWorld().field_73011_w.getDimension();
        final ChunkPos loc = event.getChunk().func_76632_l();
        if (event.getData().func_74775_l("Thaumcraft").func_74764_b("base")) {
            final NBTTagCompound nbt = event.getData().func_74775_l("Thaumcraft");
            final short base = nbt.func_74765_d("base");
            final float flux = nbt.func_74760_g("flux");
            final float vis = nbt.func_74760_g("vis");
            AuraHandler.addAuraChunk(dim, event.getChunk(), base, vis, flux);
        }
        else {
            AuraHandler.generateAura(event.getChunk(), event.getWorld().field_73012_v);
        }
        if (event.getData().func_74775_l("Thaumcraft").func_74764_b("seals")) {
            final NBTTagCompound nbt = event.getData().func_74775_l("Thaumcraft");
            final NBTTagList tagList = nbt.func_150295_c("seals", 10);
            for (int a = 0; a < tagList.func_74745_c(); ++a) {
                final NBTTagCompound tasknbt = tagList.func_150305_b(a);
                final SealEntity seal = new SealEntity();
                seal.readNBT(tasknbt);
                SealHandler.addSealEntity(event.getWorld(), seal);
            }
        }
        if (!event.getData().func_74775_l("Thaumcraft").func_74764_b(ModConfig.CONFIG_WORLD.regenKey) && (ModConfig.CONFIG_WORLD.regenAmber || ModConfig.CONFIG_WORLD.regenAura || ModConfig.CONFIG_WORLD.regenCinnabar || ModConfig.CONFIG_WORLD.regenCrystals || ModConfig.CONFIG_WORLD.regenStructure || ModConfig.CONFIG_WORLD.regenTrees)) {
            Thaumcraft.log.warn("World gen was never run for chunk at " + event.getChunk().func_76632_l() + ". Adding to queue for regeneration.");
            ArrayList<ChunkPos> chunks = ServerEvents.chunksToGenerate.get(dim);
            if (chunks == null) {
                ServerEvents.chunksToGenerate.put(dim, new ArrayList<ChunkPos>());
                chunks = ServerEvents.chunksToGenerate.get(dim);
            }
            if (chunks != null) {
                chunks.add(new ChunkPos(loc.field_77276_a, loc.field_77275_b));
                ServerEvents.chunksToGenerate.put(dim, chunks);
            }
        }
    }
    
    @SubscribeEvent
    public static void chunkWatch(final ChunkWatchEvent.Watch event) {
        for (final ISealEntity seal : SealHandler.getSealsInChunk(event.getPlayer().field_70170_p, event.getChunk())) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketSealToClient(seal), event.getPlayer());
        }
    }
}
