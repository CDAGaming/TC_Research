package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import thaumcraft.common.world.aura.*;
import net.minecraftforge.fml.common.eventhandler.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.world.*;
import thaumcraft.common.tiles.devices.*;
import java.util.*;

@Mod.EventBusSubscriber
public class WorldEvents
{
    public static WorldEvents INSTANCE;
    
    @SubscribeEvent
    public static void worldLoad(final WorldEvent.Load event) {
        if (!event.getWorld().field_72995_K) {
            AuraHandler.addAuraWorld(event.getWorld().field_73011_w.getDimension());
        }
    }
    
    @SubscribeEvent
    public static void worldSave(final WorldEvent.Save event) {
        if (!event.getWorld().field_72995_K) {}
    }
    
    @SubscribeEvent
    public static void worldUnload(final WorldEvent.Unload event) {
        if (event.getWorld().field_72995_K) {
            return;
        }
        SealHandler.sealEntities.remove(event.getWorld().field_73011_w.getDimension());
        AuraHandler.removeAuraWorld(event.getWorld().field_73011_w.getDimension());
    }
    
    @SubscribeEvent
    public static void placeBlockEvent(final BlockEvent.PlaceEvent event) {
        if (isNearActiveBoss(event.getWorld(), event.getPlayer(), event.getPos().func_177958_n(), event.getPos().func_177956_o(), event.getPos().func_177952_p())) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void placeBlockEvent(final BlockEvent.MultiPlaceEvent event) {
        if (isNearActiveBoss(event.getWorld(), event.getPlayer(), event.getPos().func_177958_n(), event.getPos().func_177956_o(), event.getPos().func_177952_p())) {
            event.setCanceled(true);
        }
    }
    
    private static boolean isNearActiveBoss(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        return false;
    }
    
    @SubscribeEvent
    public static void noteEvent(final NoteBlockEvent.Play event) {
        if (event.getWorld().field_72995_K) {
            return;
        }
        if (!TileArcaneEar.noteBlockEvents.containsKey(event.getWorld().field_73011_w.getDimension())) {
            TileArcaneEar.noteBlockEvents.put(event.getWorld().field_73011_w.getDimension(), new ArrayList<Integer[]>());
        }
        final ArrayList<Integer[]> list = TileArcaneEar.noteBlockEvents.get(event.getWorld().field_73011_w.getDimension());
        list.add(new Integer[] { event.getPos().func_177958_n(), event.getPos().func_177956_o(), event.getPos().func_177952_p(), event.getInstrument().ordinal(), event.getVanillaNoteId() });
        TileArcaneEar.noteBlockEvents.put(event.getWorld().field_73011_w.getDimension(), list);
    }
    
    static {
        WorldEvents.INSTANCE = new WorldEvents();
    }
}
