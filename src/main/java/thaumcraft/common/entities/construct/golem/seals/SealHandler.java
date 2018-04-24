package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.*;
import net.minecraft.world.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.util.math.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import thaumcraft.api.golems.seals.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.world.aura.*;

public class SealHandler
{
    public static LinkedHashMap<String, ISeal> types;
    private static int lastID;
    public static ConcurrentHashMap<Integer, ConcurrentHashMap<SealPos, SealEntity>> sealEntities;
    static int count;
    
    public static void registerSeal(final ISeal seal) {
        if (SealHandler.types.containsKey(seal.getKey())) {
            Thaumcraft.log.error("Attempting to register Seal [" + seal.getKey() + "] twice. Ignoring.");
        }
        else {
            SealHandler.types.put(seal.getKey(), seal);
        }
    }
    
    public static String[] getRegisteredSeals() {
        return SealHandler.types.keySet().toArray(new String[0]);
    }
    
    public static ISeal getSeal(final String key) {
        return SealHandler.types.get(key);
    }
    
    public static CopyOnWriteArrayList<SealEntity> getSealsInRange(final World world, final BlockPos source, final int range) {
        final CopyOnWriteArrayList<SealEntity> out = new CopyOnWriteArrayList<SealEntity>();
        final ConcurrentHashMap<SealPos, SealEntity> list = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        if (list != null && list.size() > 0) {
            for (final SealEntity se : list.values()) {
                if (se.getSeal() != null) {
                    if (se.getSealPos() == null) {
                        continue;
                    }
                    if (se.sealPos.pos.func_177951_i((Vec3i)source) > range * range) {
                        continue;
                    }
                    out.add(se);
                }
            }
        }
        return out;
    }
    
    public static CopyOnWriteArrayList<SealEntity> getSealsInChunk(final World world, final ChunkPos chunk) {
        final CopyOnWriteArrayList<SealEntity> out = new CopyOnWriteArrayList<SealEntity>();
        final ConcurrentHashMap<SealPos, SealEntity> list = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        if (list != null && list.size() > 0) {
            for (final SealEntity se : list.values()) {
                if (se.getSeal() != null) {
                    if (se.getSealPos() == null) {
                        continue;
                    }
                    final ChunkPos cc = new ChunkPos(se.sealPos.pos);
                    if (!cc.equals((Object)chunk)) {
                        continue;
                    }
                    out.add(se);
                }
            }
        }
        return out;
    }
    
    public static void removeSealEntity(final World world, final SealPos pos, final boolean quiet) {
        if (!SealHandler.sealEntities.containsKey(world.field_73011_w.getDimension())) {
            SealHandler.sealEntities.put(world.field_73011_w.getDimension(), new ConcurrentHashMap<SealPos, SealEntity>());
        }
        final ConcurrentHashMap<SealPos, SealEntity> se = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        if (se != null) {
            final SealEntity seal = se.remove(pos);
            try {
                if (!world.field_72995_K && seal != null && seal.seal != null) {
                    seal.seal.onRemoval(world, pos.pos, pos.face);
                }
                if (!quiet && seal != null && !world.field_72995_K) {
                    final String[] rs = getRegisteredSeals();
                    int indx = 1;
                    for (final String s : rs) {
                        if (s.equals(seal.getSeal().getKey())) {
                            world.func_72838_d((Entity)new EntityItem(world, pos.pos.func_177958_n() + 0.5 + pos.face.func_82601_c() / 1.7f, pos.pos.func_177956_o() + 0.5 + pos.face.func_96559_d() / 1.7f, pos.pos.func_177952_p() + 0.5 + pos.face.func_82599_e() / 1.7f, new ItemStack(ItemsTC.seals, 1, indx)));
                            break;
                        }
                        ++indx;
                    }
                }
            }
            catch (Exception e) {
                Thaumcraft.log.warn("Removing invalid seal at " + pos.pos);
            }
            final ConcurrentHashMap<Integer, Task> ts = TaskHandler.getTasks(world.field_73011_w.getDimension());
            for (final Task task : ts.values()) {
                if (task.getSealPos() != null && task.getSealPos().equals(pos)) {
                    task.setSuspended(true);
                }
            }
            if (!world.field_72995_K) {
                PacketHandler.INSTANCE.sendToDimension((IMessage)new PacketSealToClient(new SealEntity(world, pos, null)), world.field_73011_w.getDimension());
            }
            if (!quiet) {
                markChunkAsDirty(world.field_73011_w.getDimension(), pos.pos);
            }
        }
    }
    
    public static ISealEntity getSealEntity(final int dim, final SealPos pos) {
        if (!SealHandler.sealEntities.containsKey(dim)) {
            SealHandler.sealEntities.put(dim, new ConcurrentHashMap<SealPos, SealEntity>());
        }
        if (pos == null) {
            return null;
        }
        final ConcurrentHashMap<SealPos, SealEntity> se = SealHandler.sealEntities.get(dim);
        if (se != null) {
            return se.get(pos);
        }
        return null;
    }
    
    public static boolean addSealEntity(final World world, final BlockPos pos, final EnumFacing face, final ISeal seal, final EntityPlayer player) {
        if (!SealHandler.sealEntities.containsKey(world.field_73011_w.getDimension())) {
            SealHandler.sealEntities.put(world.field_73011_w.getDimension(), new ConcurrentHashMap<SealPos, SealEntity>());
        }
        final ConcurrentHashMap<SealPos, SealEntity> se = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        final SealPos sp = new SealPos(pos, face);
        if (se.containsKey(sp)) {
            return false;
        }
        final SealEntity sealent = new SealEntity(world, sp, seal);
        sealent.setOwner(player.func_110124_au().toString());
        se.put(sp, sealent);
        if (!world.field_72995_K) {
            sealent.syncToClient(world);
            markChunkAsDirty(world.field_73011_w.getDimension(), pos);
        }
        return true;
    }
    
    public static boolean addSealEntity(final World world, final SealEntity seal) {
        if (world == null || SealHandler.sealEntities == null) {
            return false;
        }
        if (!SealHandler.sealEntities.containsKey(world.field_73011_w.getDimension())) {
            SealHandler.sealEntities.put(world.field_73011_w.getDimension(), new ConcurrentHashMap<SealPos, SealEntity>());
        }
        final ConcurrentHashMap<SealPos, SealEntity> se = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        if (se.containsKey(seal.getSealPos())) {
            return false;
        }
        se.put(seal.getSealPos(), seal);
        if (!world.field_72995_K) {
            seal.syncToClient(world);
            markChunkAsDirty(world.field_73011_w.getDimension(), seal.getSealPos().pos);
        }
        return true;
    }
    
    public static void tickSealEntities(final World world) {
        if (!SealHandler.sealEntities.containsKey(world.field_73011_w.getDimension())) {
            SealHandler.sealEntities.put(world.field_73011_w.getDimension(), new ConcurrentHashMap<SealPos, SealEntity>());
        }
        final ConcurrentHashMap<SealPos, SealEntity> se = SealHandler.sealEntities.get(world.field_73011_w.getDimension());
        ++SealHandler.count;
        for (final SealEntity sealEntity : se.values()) {
            if (world.func_175667_e(sealEntity.sealPos.pos)) {
                try {
                    boolean tick = true;
                    if (SealHandler.count % 20 == 0 && !sealEntity.seal.canPlaceAt(world, sealEntity.sealPos.pos, sealEntity.sealPos.face)) {
                        removeSealEntity(world, sealEntity.sealPos, false);
                        tick = false;
                    }
                    if (!tick) {
                        continue;
                    }
                    sealEntity.tickSealEntity(world);
                }
                catch (Exception e) {
                    removeSealEntity(world, sealEntity.sealPos, false);
                }
            }
        }
    }
    
    public static void markChunkAsDirty(final int dim, final BlockPos bp) {
        final ChunkPos pos = new ChunkPos(bp);
        if (!AuraHandler.dirtyChunks.containsKey(dim)) {
            AuraHandler.dirtyChunks.put(dim, new CopyOnWriteArrayList<ChunkPos>());
        }
        final CopyOnWriteArrayList<ChunkPos> dc = AuraHandler.dirtyChunks.get(dim);
        if (!dc.contains(pos)) {
            dc.add(pos);
        }
    }
    
    static {
        SealHandler.types = new LinkedHashMap<String, ISeal>();
        SealHandler.lastID = 0;
        SealHandler.sealEntities = new ConcurrentHashMap<Integer, ConcurrentHashMap<SealPos, SealEntity>>();
        SealHandler.count = 0;
    }
}
