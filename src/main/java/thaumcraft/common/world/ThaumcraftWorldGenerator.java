package thaumcraft.common.world;

import net.minecraftforge.fml.common.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import java.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.common.config.*;
import thaumcraft.common.world.biomes.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.entity.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.biome.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.*;
import thaumcraft.common.blocks.world.ore.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import thaumcraft.common.world.objects.*;

public class ThaumcraftWorldGenerator implements IWorldGenerator
{
    public static ThaumcraftWorldGenerator INSTANCE;
    private final Predicate<IBlockState> predicate;
    
    public ThaumcraftWorldGenerator() {
        this.predicate = (Predicate<IBlockState>)BlockMatcher.func_177642_a(Blocks.field_150348_b);
    }
    
    public void initialize() {
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        this.worldGeneration(random, chunkX, chunkZ, world, true);
        AuraHandler.generateAura(chunkProvider.func_186025_d(chunkX, chunkZ), random);
    }
    
    public void worldGeneration(final Random random, final int chunkX, final int chunkZ, final World world, final boolean newGen) {
        if (world.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId) {
            world.func_72964_e(chunkX, chunkZ).func_76630_e();
        }
        else {
            this.generateAll(world, random, chunkX, chunkZ, newGen);
            if (world.field_73011_w.getDimension() == -1) {
                this.generateNether(world, random, chunkX, chunkZ, newGen);
            }
            else if (world.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.overworldDim) {
                this.generateSurface(world, random, chunkX, chunkZ, newGen);
            }
            if (!newGen) {
                world.func_72964_e(chunkX, chunkZ).func_76630_e();
            }
        }
    }
    
    private void generateSurface(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen) {
        final int blacklist = BiomeHandler.getDimBlacklist(world.field_73011_w.getDimension());
        if (blacklist == -1 && ModConfig.CONFIG_WORLD.generateStructure && world.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.overworldDim && !world.func_72912_H().func_76067_t().func_77127_a().startsWith("flat") && (newGen || ModConfig.CONFIG_WORLD.regenStructure)) {
            final int randPosX = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
            final int randPosZ = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
            final BlockPos p = world.func_175725_q(new BlockPos(randPosX, 0, randPosZ)).func_177979_c(9);
            if (p.func_177956_o() < world.func_72940_L()) {
                if (random.nextInt(100) == 0) {
                    final WorldGenerator mound = new WorldGenMound();
                    mound.func_180709_b(world, random, p);
                }
                else if (random.nextInt(500) == 0) {
                    final BlockPos p2 = p.func_177981_b(8);
                    final IBlockState bs = world.func_180495_p(p2);
                    if (bs.func_185904_a() == Material.field_151578_c || bs.func_185904_a() == Material.field_151576_e || bs.func_185904_a() == Material.field_151595_p || bs.func_185904_a() == Material.field_151597_y) {
                        final EntityCultistPortalLesser eg = new EntityCultistPortalLesser(world);
                        eg.func_70107_b(p2.func_177958_n() + 0.5, (double)(p2.func_177956_o() + 1), p2.func_177952_p() + 0.5);
                        eg.func_180482_a(world.func_175649_E(new BlockPos((Entity)eg)), (IEntityLivingData)null);
                        world.func_72838_d((Entity)eg);
                    }
                }
            }
        }
    }
    
    private void generateNodes(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen, final int blacklist) {
        if (blacklist != 0 && blacklist != 2 && ModConfig.CONFIG_WORLD.generateAura && (newGen || ModConfig.CONFIG_WORLD.regenAura)) {
            BlockPos var7 = null;
            try {
                var7 = new MapGenScatteredFeature().func_180706_b(world, world.func_175645_m(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8)), true);
            }
            catch (Exception ex) {}
        }
    }
    
    private void generateVegetation(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen) {
        final Biome bgb = world.func_180494_b(new BlockPos(chunkX * 16 + 8, 50, chunkZ * 16 + 8));
        if (BiomeHandler.getBiomeBlacklist(Biome.func_185362_a(bgb)) != -1) {
            return;
        }
        if (random.nextInt(80) == 3) {
            generateSilverwood(world, random, chunkX, chunkZ);
        }
        if (random.nextInt(25) == 7) {
            generateGreatwood(world, random, chunkX, chunkZ);
        }
        final int randPosX = chunkX * 16 + 8;
        final int randPosZ = chunkZ * 16 + 8;
        final BlockPos bp = world.func_175645_m(new BlockPos(randPosX, 0, randPosZ));
        if (world.func_180494_b(bp).field_76752_A.func_177230_c() == Blocks.field_150354_m && world.func_180494_b(bp).func_180626_a(bp) > 1.0f && random.nextInt(30) == 0) {
            generateFlowers(world, random, bp, BlocksTC.cinderpearl, 0);
        }
    }
    
    private void generateOres(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen) {
        final Biome bgb = world.func_180494_b(new BlockPos(chunkX * 16 + 8, 50, chunkZ * 16 + 8));
        if (BiomeHandler.getBiomeBlacklist(Biome.func_185362_a(bgb)) == 0 || BiomeHandler.getBiomeBlacklist(Biome.func_185362_a(bgb)) == 2) {
            return;
        }
        final float density = ModConfig.CONFIG_WORLD.oreDensity / 100.0f;
        if (world.field_73011_w.getDimension() == -1) {
            return;
        }
        if (ModConfig.CONFIG_WORLD.generateCinnabar && (newGen || ModConfig.CONFIG_WORLD.regenCinnabar)) {
            for (int i = 0; i < Math.round(18.0f * density); ++i) {
                final int randPosX = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosY = random.nextInt(world.func_72800_K() / 5);
                final int randPosZ = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
                final IBlockState block = world.func_180495_p(pos);
                if (block.func_177230_c().isReplaceableOreGen(block, (IBlockAccess)world, pos, (Predicate)this.predicate)) {
                    world.func_180501_a(pos, BlocksTC.oreCinnabar.func_176223_P(), 2);
                }
            }
        }
        if (ModConfig.CONFIG_WORLD.generateQuartz && (newGen || ModConfig.CONFIG_WORLD.regenQuartz)) {
            for (int i = 0; i < Math.round(18.0f * density); ++i) {
                final int randPosX = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosY = random.nextInt(world.func_72800_K() / 4);
                final int randPosZ = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
                final IBlockState block = world.func_180495_p(pos);
                if (block.func_177230_c().isReplaceableOreGen(block, (IBlockAccess)world, pos, (Predicate)this.predicate)) {
                    world.func_180501_a(pos, BlocksTC.oreQuartz.func_176223_P(), 2);
                }
            }
        }
        if (ModConfig.CONFIG_WORLD.generateAmber && (newGen || ModConfig.CONFIG_WORLD.regenAmber)) {
            for (int i = 0; i < Math.round(20.0f * density); ++i) {
                final int randPosX = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosZ2 = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosY2 = world.func_175645_m(new BlockPos(randPosX, 0, randPosZ2)).func_177956_o() - random.nextInt(25);
                final BlockPos pos = new BlockPos(randPosX, randPosY2, randPosZ2);
                final IBlockState block = world.func_180495_p(pos);
                if (block.func_177230_c().isReplaceableOreGen(block, (IBlockAccess)world, pos, (Predicate)this.predicate)) {
                    world.func_180501_a(pos, BlocksTC.oreAmber.func_176223_P(), 2);
                }
            }
        }
        if (ModConfig.CONFIG_WORLD.generateCrystals && (newGen || ModConfig.CONFIG_WORLD.regenCrystals)) {
            int t = 8;
            final int maxCrystals = Math.round(64.0f * density);
            int cc = 0;
            if (world.field_73011_w.getDimension() == -1) {
                t = 1;
            }
            for (int j = 0; j < Math.round(t * density); ++j) {
                final int randPosX2 = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosZ3 = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -6, 6);
                final int randPosY3 = random.nextInt(Math.max(5, world.func_175645_m(new BlockPos(randPosX2, 0, randPosZ3)).func_177956_o() - 5));
                final BlockPos bp = new BlockPos(randPosX2, randPosY3, randPosZ3);
                int md = random.nextInt(6);
                if (random.nextInt(3) == 0) {
                    final Aspect tag = BiomeHandler.getRandomBiomeTag(Biome.func_185362_a(world.func_180494_b(bp)), random);
                    if (tag == null) {
                        md = random.nextInt(6);
                    }
                    else {
                        md = ShardType.getMetaByAspect(tag);
                    }
                }
                final Block oreBlock = ShardType.byMetadata(md).getOre();
                for (int xx = -1; xx <= 1; ++xx) {
                    for (int yy = -1; yy <= 1; ++yy) {
                        for (int zz = -1; zz <= 1; ++zz) {
                            if (random.nextInt(3) != 0) {
                                final IBlockState bs = world.func_180495_p(bp.func_177982_a(xx, yy, zz));
                                final Material bm = bs.func_185904_a();
                                if (!bm.func_76224_d() && (world.func_175623_d(bp.func_177982_a(xx, yy, zz)) || bs.func_177230_c().func_176200_f((IBlockAccess)world, bp.func_177982_a(xx, yy, zz))) && BlockUtils.isBlockTouching((IBlockAccess)world, bp.func_177982_a(xx, yy, zz), Material.field_151576_e, true)) {
                                    final int amt = 1 + random.nextInt(3);
                                    world.func_180501_a(bp.func_177982_a(xx, yy, zz), oreBlock.func_176203_a(amt), 0);
                                    cc += amt;
                                }
                            }
                        }
                    }
                }
                if (cc > maxCrystals) {
                    break;
                }
            }
        }
    }
    
    private void generateAll(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen) {
        final boolean auraGen = false;
        final int blacklist = BiomeHandler.getDimBlacklist(world.field_73011_w.getDimension());
        if (blacklist == -1 && ModConfig.CONFIG_WORLD.generateTrees && !world.func_72912_H().func_76067_t().func_77127_a().startsWith("flat") && (newGen || ModConfig.CONFIG_WORLD.regenTrees)) {
            this.generateVegetation(world, random, chunkX, chunkZ, newGen);
        }
        if (blacklist != 0 && blacklist != 2) {
            this.generateOres(world, random, chunkX, chunkZ, newGen);
        }
    }
    
    private void generateNether(final World world, final Random random, final int chunkX, final int chunkZ, final boolean newGen) {
        final boolean auraGen = false;
    }
    
    public static boolean generateFlowers(final World world, final Random random, final BlockPos pos, final Block block, final int md) {
        final WorldGenerator flowers = new WorldGenCustomFlowers(block, md);
        return flowers.func_180709_b(world, random, pos);
    }
    
    public static boolean generateGreatwood(final World world, final Random random, final int chunkX, final int chunkZ) {
        final int x = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
        final int z = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
        final BlockPos bp = world.func_175725_q(new BlockPos(x, 0, z));
        final int bio = Biome.func_185362_a(world.func_180494_b(bp));
        if (BiomeHandler.getBiomeSupportsGreatwood(bio) > random.nextFloat()) {
            final boolean t = new WorldGenGreatwoodTrees(false, random.nextInt(8) == 0).func_180709_b(world, random, bp);
            return t;
        }
        return false;
    }
    
    public static boolean generateSilverwood(final World world, final Random random, final int chunkX, final int chunkZ) {
        final int x = chunkX * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
        final int z = chunkZ * 16 + 8 + MathHelper.func_76136_a(random, -4, 4);
        final BlockPos bp = world.func_175725_q(new BlockPos(x, 0, z));
        final Biome bio = world.func_180494_b(bp);
        final int bi = Biome.func_185362_a(world.func_180494_b(bp));
        if (BiomeHandler.getBiomeSupportsGreatwood(bi) / 2.0f > random.nextFloat() || (!bio.equals(BiomeHandler.MAGICAL_FOREST) && BiomeDictionary.hasType(bio, BiomeDictionary.Type.MAGICAL)) || bio == Biome.func_150568_d(18) || bio == Biome.func_150568_d(28)) {
            final boolean t = new WorldGenSilverwoodTrees(false, 7, 4).func_180709_b(world, random, bp);
            return t;
        }
        return false;
    }
    
    static {
        ThaumcraftWorldGenerator.INSTANCE = new ThaumcraftWorldGenerator();
    }
}
