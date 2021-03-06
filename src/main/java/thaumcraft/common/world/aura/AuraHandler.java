package thaumcraft.common.world.aura;

import java.util.concurrent.*;
import thaumcraft.*;
import net.minecraft.world.chunk.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;
import java.util.*;
import net.minecraft.world.biome.*;
import thaumcraft.common.world.biomes.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class AuraHandler
{
    public static final int AURA_CEILING = 500;
    static ConcurrentHashMap<Integer, AuraWorld> auras;
    public static ConcurrentHashMap<Integer, CopyOnWriteArrayList<ChunkPos>> dirtyChunks;
    public static ConcurrentHashMap<Integer, BlockPos> taintTrigger;
    
    public static AuraWorld getAuraWorld(final int dim) {
        return AuraHandler.auras.get(dim);
    }
    
    public static AuraChunk getAuraChunk(final int dim, final int x, final int y) {
        if (AuraHandler.auras.containsKey(dim)) {
            return AuraHandler.auras.get(dim).getAuraChunkAt(x, y);
        }
        return null;
    }
    
    public static void addAuraWorld(final int dim) {
        if (!AuraHandler.auras.containsKey(dim)) {
            AuraHandler.auras.put(dim, new AuraWorld(dim));
            Thaumcraft.log.info("Creating aura cache for world " + dim);
        }
    }
    
    public static void removeAuraWorld(final int dim) {
        AuraHandler.auras.remove(dim);
        Thaumcraft.log.info("Removing aura cache for world " + dim);
    }
    
    public static void addAuraChunk(final int dim, final Chunk chunk, final short base, final float vis, final float flux) {
        AuraWorld aw = AuraHandler.auras.get(dim);
        if (aw == null) {
            aw = new AuraWorld(dim);
        }
        aw.getAuraChunks().put(new PosXY(chunk.field_76635_g, chunk.field_76647_h), new AuraChunk(chunk, base, vis, flux));
        AuraHandler.auras.put(dim, aw);
    }
    
    public static void removeAuraChunk(final int dim, final int x, final int y) {
        final AuraWorld aw = AuraHandler.auras.get(dim);
        if (aw != null) {
            aw.getAuraChunks().remove(new PosXY(x, y));
        }
    }
    
    public static float getTotalAura(final World world, final BlockPos pos) {
        final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
        return (ac != null) ? (ac.getVis() + ac.getFlux()) : 0.0f;
    }
    
    public static float getFluxSaturation(final World world, final BlockPos pos) {
        final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
        return (ac != null) ? (ac.getFlux() / ac.getBase()) : 0.0f;
    }
    
    public static float getVis(final World world, final BlockPos pos) {
        final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
        return (ac != null) ? ac.getVis() : 0.0f;
    }
    
    public static float getFlux(final World world, final BlockPos pos) {
        final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
        return (ac != null) ? ac.getFlux() : 0.0f;
    }
    
    public static int getAuraBase(final World world, final BlockPos pos) {
        final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
        return (ac != null) ? ac.getBase() : 0;
    }
    
    public static boolean shouldPreserveAura(final World world, final EntityPlayer player, final BlockPos pos) {
        return (player == null || ThaumcraftCapabilities.getKnowledge(player).isResearchComplete("AURAPRESERVE")) && getVis(world, pos) / getAuraBase(world, pos) < 0.1;
    }
    
    public static void addVis(final World world, final BlockPos pos, final float amount) {
        if (amount < 0.0f) {
            return;
        }
        try {
            final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
            modifyVisInChunk(ac, amount, true);
        }
        catch (Exception ex) {}
    }
    
    public static void addFlux(final World world, final BlockPos pos, final float amount) {
        if (amount < 0.0f) {
            return;
        }
        try {
            final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
            modifyFluxInChunk(ac, amount, true);
        }
        catch (Exception ex) {}
    }
    
    public static float drainVis(final World world, final BlockPos pos, float amount, final boolean simulate) {
        boolean didit = false;
        try {
            final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
            if (amount > ac.getVis()) {
                amount = ac.getVis();
            }
            didit = modifyVisInChunk(ac, -amount, !simulate);
        }
        catch (Exception ex) {}
        return didit ? amount : 0.0f;
    }
    
    public static float drainFlux(final World world, final BlockPos pos, float amount, final boolean simulate) {
        boolean didit = false;
        try {
            final AuraChunk ac = getAuraChunk(world.field_73011_w.getDimension(), pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
            if (amount > ac.getFlux()) {
                amount = ac.getFlux();
            }
            didit = modifyFluxInChunk(ac, -amount, !simulate);
        }
        catch (Exception ex) {}
        return didit ? amount : 0.0f;
    }
    
    public static boolean modifyVisInChunk(final AuraChunk ac, final float amount, final boolean doit) {
        if (ac != null) {
            if (doit) {
                ac.setVis(Math.max(0.0f, ac.getVis() + amount));
            }
            return true;
        }
        return false;
    }
    
    private static boolean modifyFluxInChunk(final AuraChunk ac, final float amount, final boolean doit) {
        if (ac != null) {
            if (doit) {
                ac.setFlux(Math.max(0.0f, ac.getFlux() + amount));
            }
            return true;
        }
        return false;
    }
    
    public static void generateAura(final Chunk chunk, final Random rand) {
        final Biome bgb = chunk.func_177412_p().func_180494_b(new BlockPos(chunk.field_76635_g * 16 + 8, 50, chunk.field_76647_h * 16 + 8));
        if (BiomeHandler.getBiomeBlacklist(Biome.func_185362_a(bgb)) != -1) {
            return;
        }
        float life = BiomeHandler.getBiomeAuraModifier(bgb);
        for (int a = 0; a < 4; ++a) {
            final EnumFacing dir = EnumFacing.func_176731_b(a);
            final Biome bgb2 = chunk.func_177412_p().func_180494_b(new BlockPos((chunk.field_76635_g + dir.func_82601_c()) * 16 + 8, 50, (chunk.field_76647_h + dir.func_82599_e()) * 16 + 8));
            life += BiomeHandler.getBiomeAuraModifier(bgb2);
        }
        life /= 5.0f;
        final float noise = (float)(1.0 + rand.nextGaussian() * 0.10000000149011612);
        short base = (short)(life * 500.0f * noise);
        base = (short)MathHelper.func_76125_a((int)base, 0, 500);
        addAuraChunk(chunk.func_177412_p().field_73011_w.getDimension(), chunk, base, base, 0.0f);
    }
    
    static {
        AuraHandler.auras = new ConcurrentHashMap<Integer, AuraWorld>();
        AuraHandler.dirtyChunks = new ConcurrentHashMap<Integer, CopyOnWriteArrayList<ChunkPos>>();
        AuraHandler.taintTrigger = new ConcurrentHashMap<Integer, BlockPos>();
    }
}
