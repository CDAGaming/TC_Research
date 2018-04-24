package thaumcraft.common.lib.events;

import thaumcraft.api.internal.*;
import net.minecraft.tileentity.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class EssentiaHandler
{
    static final int DELAY = 10000;
    private static HashMap<WorldCoordinates, ArrayList<WorldCoordinates>> sources;
    private static HashMap<WorldCoordinates, Long> sourcesDelay;
    private static TileEntity lat;
    private static TileEntity las;
    private static Aspect lasp;
    private static int lext;
    public static ConcurrentHashMap<String, EssentiaSourceFX> sourceFX;
    
    public static boolean drainEssentia(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final int ext) {
        return drainEssentia(tile, aspect, direction, range, false, ext);
    }
    
    public static boolean drainEssentia(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final boolean ignoreMirror, final int ext) {
        final WorldCoordinates tileLoc = new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension());
        if (!EssentiaHandler.sources.containsKey(tileLoc)) {
            getSources(tile.func_145831_w(), tileLoc, direction, range);
            return EssentiaHandler.sources.containsKey(tileLoc) && drainEssentia(tile, aspect, direction, range, ignoreMirror, ext);
        }
        final ArrayList<WorldCoordinates> es = EssentiaHandler.sources.get(tileLoc);
        for (final WorldCoordinates source : es) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (as.isBlocked()) {
                continue;
            }
            if (ignoreMirror && sourceTile instanceof TileMirrorEssentia) {
                continue;
            }
            if (as.takeFromContainer(aspect, 1)) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXEssentiaSource(tile.func_174877_v(), (byte)(tile.func_174877_v().func_177958_n() - source.pos.func_177958_n()), (byte)(tile.func_174877_v().func_177956_o() - source.pos.func_177956_o()), (byte)(tile.func_174877_v().func_177952_p() - source.pos.func_177952_p()), aspect.getColor(), ext), new NetworkRegistry.TargetPoint(tile.func_145831_w().field_73011_w.getDimension(), (double)tile.func_174877_v().func_177958_n(), (double)tile.func_174877_v().func_177956_o(), (double)tile.func_174877_v().func_177952_p(), 32.0));
                return true;
            }
        }
        EssentiaHandler.sources.remove(tileLoc);
        EssentiaHandler.sourcesDelay.put(tileLoc, System.currentTimeMillis() + 10000L);
        return false;
    }
    
    public static boolean drainEssentiaWithConfirmation(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final boolean ignoreMirror, final int ext) {
        final WorldCoordinates tileLoc = new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension());
        if (!EssentiaHandler.sources.containsKey(tileLoc)) {
            getSources(tile.func_145831_w(), tileLoc, direction, range);
            return EssentiaHandler.sources.containsKey(tileLoc) && drainEssentiaWithConfirmation(tile, aspect, direction, range, ignoreMirror, ext);
        }
        final ArrayList<WorldCoordinates> es = EssentiaHandler.sources.get(tileLoc);
        for (final WorldCoordinates source : es) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (as.isBlocked()) {
                continue;
            }
            if (ignoreMirror && sourceTile instanceof TileMirrorEssentia) {
                continue;
            }
            if (as.doesContainerContainAmount(aspect, 1)) {
                EssentiaHandler.las = sourceTile;
                EssentiaHandler.lasp = aspect;
                EssentiaHandler.lat = tile;
                EssentiaHandler.lext = ext;
                return true;
            }
        }
        EssentiaHandler.sources.remove(tileLoc);
        EssentiaHandler.sourcesDelay.put(tileLoc, System.currentTimeMillis() + 10000L);
        return false;
    }
    
    public static void confirmDrain() {
        if (EssentiaHandler.las != null && EssentiaHandler.lasp != null && EssentiaHandler.lat != null) {
            final IAspectSource as = (IAspectSource)EssentiaHandler.las;
            if (as.takeFromContainer(EssentiaHandler.lasp, 1)) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXEssentiaSource(EssentiaHandler.lat.func_174877_v(), (byte)(EssentiaHandler.lat.func_174877_v().func_177958_n() - EssentiaHandler.las.func_174877_v().func_177958_n()), (byte)(EssentiaHandler.lat.func_174877_v().func_177956_o() - EssentiaHandler.las.func_174877_v().func_177956_o()), (byte)(EssentiaHandler.lat.func_174877_v().func_177952_p() - EssentiaHandler.las.func_174877_v().func_177952_p()), EssentiaHandler.lasp.getColor(), EssentiaHandler.lext), new NetworkRegistry.TargetPoint(EssentiaHandler.lat.func_145831_w().field_73011_w.getDimension(), (double)EssentiaHandler.lat.func_174877_v().func_177958_n(), (double)EssentiaHandler.lat.func_174877_v().func_177956_o(), (double)EssentiaHandler.lat.func_174877_v().func_177952_p(), 32.0));
            }
        }
        EssentiaHandler.las = null;
        EssentiaHandler.lasp = null;
        EssentiaHandler.lat = null;
    }
    
    public static boolean addEssentia(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final boolean ignoreMirror, final int ext) {
        final WorldCoordinates tileLoc = new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension());
        if (!EssentiaHandler.sources.containsKey(tileLoc)) {
            getSources(tile.func_145831_w(), tileLoc, direction, range);
            return EssentiaHandler.sources.containsKey(tileLoc) && addEssentia(tile, aspect, direction, range, ignoreMirror, ext);
        }
        final ArrayList<WorldCoordinates> es = EssentiaHandler.sources.get(tileLoc);
        final ArrayList<WorldCoordinates> empties = new ArrayList<WorldCoordinates>();
        for (final WorldCoordinates source : es) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (as.isBlocked()) {
                continue;
            }
            if (ignoreMirror && sourceTile instanceof TileMirrorEssentia) {
                continue;
            }
            if (as.doesContainerAccept(aspect) && (as.getAspects() == null || as.getAspects().visSize() == 0)) {
                empties.add(source);
            }
            else {
                if (as.doesContainerAccept(aspect) && as.addToContainer(aspect, 1) <= 0) {
                    PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXEssentiaSource(source.pos, (byte)(source.pos.func_177958_n() - tile.func_174877_v().func_177958_n()), (byte)(source.pos.func_177956_o() - tile.func_174877_v().func_177956_o()), (byte)(source.pos.func_177952_p() - tile.func_174877_v().func_177952_p()), aspect.getColor(), ext), new NetworkRegistry.TargetPoint(tile.func_145831_w().field_73011_w.getDimension(), (double)tile.func_174877_v().func_177958_n(), (double)tile.func_174877_v().func_177956_o(), (double)tile.func_174877_v().func_177952_p(), 32.0));
                    return true;
                }
                continue;
            }
        }
        for (final WorldCoordinates source : empties) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (as.doesContainerAccept(aspect) && as.addToContainer(aspect, 1) <= 0) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXEssentiaSource(source.pos, (byte)(source.pos.func_177958_n() - tile.func_174877_v().func_177958_n()), (byte)(source.pos.func_177956_o() - tile.func_174877_v().func_177956_o()), (byte)(source.pos.func_177952_p() - tile.func_174877_v().func_177952_p()), aspect.getColor(), ext), new NetworkRegistry.TargetPoint(tile.func_145831_w().field_73011_w.getDimension(), (double)tile.func_174877_v().func_177958_n(), (double)tile.func_174877_v().func_177956_o(), (double)tile.func_174877_v().func_177952_p(), 32.0));
                return true;
            }
        }
        EssentiaHandler.sources.remove(tileLoc);
        EssentiaHandler.sourcesDelay.put(tileLoc, System.currentTimeMillis() + 10000L);
        return false;
    }
    
    public static boolean findEssentia(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final boolean ignoreMirror) {
        final WorldCoordinates tileLoc = new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension());
        if (!EssentiaHandler.sources.containsKey(tileLoc)) {
            getSources(tile.func_145831_w(), tileLoc, direction, range);
            return EssentiaHandler.sources.containsKey(tileLoc) && findEssentia(tile, aspect, direction, range, ignoreMirror);
        }
        final ArrayList<WorldCoordinates> es = EssentiaHandler.sources.get(tileLoc);
        for (final WorldCoordinates source : es) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (as.isBlocked()) {
                continue;
            }
            if (ignoreMirror && sourceTile instanceof TileMirrorEssentia) {
                continue;
            }
            if (as.doesContainerContainAmount(aspect, 1)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canAcceptEssentia(final TileEntity tile, final Aspect aspect, final EnumFacing direction, final int range, final boolean ignoreMirror) {
        final WorldCoordinates tileLoc = new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension());
        if (!EssentiaHandler.sources.containsKey(tileLoc)) {
            getSources(tile.func_145831_w(), tileLoc, direction, range);
            return EssentiaHandler.sources.containsKey(tileLoc) && findEssentia(tile, aspect, direction, range, ignoreMirror);
        }
        final ArrayList<WorldCoordinates> es = EssentiaHandler.sources.get(tileLoc);
        for (final WorldCoordinates source : es) {
            final TileEntity sourceTile = tile.func_145831_w().func_175625_s(source.pos);
            if (sourceTile == null || !(sourceTile instanceof IAspectSource)) {
                break;
            }
            if (ignoreMirror && sourceTile instanceof TileMirrorEssentia) {
                continue;
            }
            final IAspectSource as = (IAspectSource)sourceTile;
            if (!as.isBlocked() && as.doesContainerAccept(aspect)) {
                return true;
            }
        }
        return false;
    }
    
    private static void getSources(final World world, final WorldCoordinates tileLoc, EnumFacing direction, final int range) {
        if (EssentiaHandler.sourcesDelay.containsKey(tileLoc)) {
            final long d = EssentiaHandler.sourcesDelay.get(tileLoc);
            if (d > System.currentTimeMillis()) {
                return;
            }
            EssentiaHandler.sourcesDelay.remove(tileLoc);
        }
        final TileEntity sourceTile = world.func_175625_s(tileLoc.pos);
        final ArrayList<WorldCoordinates> sourceList = new ArrayList<WorldCoordinates>();
        int start = 0;
        if (direction == null) {
            start = -range;
            direction = EnumFacing.UP;
        }
        int xx = 0;
        int yy = 0;
        int zz = 0;
        for (int aa = -range; aa <= range; ++aa) {
            for (int bb = -range; bb <= range; ++bb) {
                for (int cc = start; cc < range; ++cc) {
                    if (aa != 0 || bb != 0 || cc != 0) {
                        xx = tileLoc.pos.func_177958_n();
                        yy = tileLoc.pos.func_177956_o();
                        zz = tileLoc.pos.func_177952_p();
                        if (direction.func_96559_d() != 0) {
                            xx += aa;
                            yy += cc * direction.func_96559_d();
                            zz += bb;
                        }
                        else if (direction.func_82601_c() == 0) {
                            xx += aa;
                            yy += bb;
                            zz += cc * direction.func_82599_e();
                        }
                        else {
                            xx += cc * direction.func_82601_c();
                            yy += aa;
                            zz += bb;
                        }
                        final TileEntity te = world.func_175625_s(new BlockPos(xx, yy, zz));
                        if (te != null && te instanceof IAspectSource) {
                            if (!(sourceTile instanceof TileMirrorEssentia) || !(te instanceof TileMirrorEssentia) || sourceTile.func_174877_v().func_177958_n() != ((TileMirrorEssentia)te).linkX || sourceTile.func_174877_v().func_177956_o() != ((TileMirrorEssentia)te).linkY || sourceTile.func_174877_v().func_177952_p() != ((TileMirrorEssentia)te).linkZ || sourceTile.func_145831_w().field_73011_w.getDimension() != ((TileMirrorEssentia)te).linkDim) {
                                sourceList.add(new WorldCoordinates(new BlockPos(xx, yy, zz), world.field_73011_w.getDimension()));
                            }
                        }
                    }
                }
            }
        }
        if (sourceList.size() > 0) {
            final ArrayList<WorldCoordinates> sourceList2 = new ArrayList<WorldCoordinates>();
        Label_0467:
            for (final WorldCoordinates wc : sourceList) {
                final double dist = wc.getDistanceSquaredToWorldCoordinates(tileLoc);
                if (!sourceList2.isEmpty()) {
                    for (int a = 0; a < sourceList2.size(); ++a) {
                        final double d2 = sourceList2.get(a).getDistanceSquaredToWorldCoordinates(tileLoc);
                        if (dist < d2) {
                            sourceList2.add(a, wc);
                            continue Label_0467;
                        }
                    }
                }
                sourceList2.add(wc);
            }
            EssentiaHandler.sources.put(tileLoc, sourceList2);
        }
        else {
            EssentiaHandler.sourcesDelay.put(tileLoc, System.currentTimeMillis() + 10000L);
        }
    }
    
    public static void refreshSources(final TileEntity tile) {
        EssentiaHandler.sources.remove(new WorldCoordinates(tile.func_174877_v(), tile.func_145831_w().field_73011_w.getDimension()));
    }
    
    static {
        EssentiaHandler.sources = new HashMap<WorldCoordinates, ArrayList<WorldCoordinates>>();
        EssentiaHandler.sourcesDelay = new HashMap<WorldCoordinates, Long>();
        EssentiaHandler.lat = null;
        EssentiaHandler.las = null;
        EssentiaHandler.lasp = null;
        EssentiaHandler.lext = 0;
        EssentiaHandler.sourceFX = new ConcurrentHashMap<String, EssentiaSourceFX>();
    }
    
    public static class EssentiaSourceFX
    {
        public BlockPos start;
        public BlockPos end;
        public int color;
        public int ext;
        
        public EssentiaSourceFX(final BlockPos start, final BlockPos end, final int color, final int ext) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.ext = ext;
        }
    }
}
