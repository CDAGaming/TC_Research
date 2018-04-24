package thaumcraft.common.world.biomes;

import net.minecraft.world.biome.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.config.*;
import thaumcraft.common.entities.monster.*;
import java.util.*;
import thaumcraft.common.world.objects.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import thaumcraft.api.blocks.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class BiomeGenMagicalForest extends Biome
{
    protected WorldGenBigMagicTree bigTree;
    private static final WorldGenBlockBlob blobs;
    
    public BiomeGenMagicalForest(final Biome.BiomeProperties par1) {
        super(par1);
        this.setRegistryName("thaumcraft", "magical_forest");
        this.bigTree = new WorldGenBigMagicTree(false);
        this.field_76762_K.add(new Biome.SpawnListEntry((Class)EntityWolf.class, 2, 1, 3));
        this.field_76762_K.add(new Biome.SpawnListEntry((Class)EntityHorse.class, 2, 1, 3));
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityWitch.class, 3, 1, 1));
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityEnderman.class, 3, 1, 1));
        if (ModConfig.CONFIG_WORLD.allowSpawnPech) {
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityPech.class, 10, 1, 2));
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnWisp) {
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityWisp.class, 10, 1, 2));
        }
        this.field_76760_I.field_76832_z = 2;
        this.field_76760_I.field_76802_A = 10;
        this.field_76760_I.field_76803_B = 12;
        this.field_76760_I.field_76833_y = 6;
        this.field_76760_I.field_76798_D = 6;
    }
    
    public WorldGenAbstractTree func_150567_a(final Random par1Random) {
        return (par1Random.nextInt(14) == 0) ? new WorldGenSilverwoodTrees(false, 8, 5) : ((par1Random.nextInt(10) == 0) ? new WorldGenGreatwoodTrees(false, par1Random.nextInt(8) == 0) : this.bigTree);
    }
    
    public WorldGenerator func_76730_b(final Random par1Random) {
        return (WorldGenerator)((par1Random.nextInt(4) == 0) ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS));
    }
    
    @SideOnly(Side.CLIENT)
    public int func_180627_b(final BlockPos p_180627_1_) {
        return ModConfig.CONFIG_GRAPHICS.blueBiome ? 6728396 : 5635969;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_180625_c(final BlockPos p_180625_1_) {
        return ModConfig.CONFIG_GRAPHICS.blueBiome ? 7851246 : 6750149;
    }
    
    public int getWaterColorMultiplier() {
        return 30702;
    }
    
    public void func_180624_a(final World world, final Random random, final BlockPos pos) {
        for (int a = 0; a < 3; ++a) {
            BlockPos pp;
            for (pp = new BlockPos((Vec3i)pos), pp = pp.func_177982_a(4 + random.nextInt(8), 0, 4 + random.nextInt(8)), pp = world.func_175645_m(pp); pp.func_177956_o() > 30 && world.func_180495_p(pp).func_177230_c() != Blocks.field_150349_c; pp = pp.func_177977_b()) {}
            final Block l1 = world.func_180495_p(pp).func_177230_c();
            if (l1 == Blocks.field_150349_c) {
                world.func_180501_a(pp, BlocksTC.grassAmbient.func_176223_P(), 2);
                break;
            }
        }
        for (int k = random.nextInt(3), i = 0; i < k; ++i) {
            BlockPos p2 = new BlockPos((Vec3i)pos);
            p2 = p2.func_177982_a(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);
            p2 = world.func_175645_m(p2);
            BiomeGenMagicalForest.blobs.func_180709_b(world, random, p2);
        }
        for (int k = 0; k < 4; ++k) {
            for (int i = 0; i < 4; ++i) {
                if (random.nextInt(40) == 0) {
                    BlockPos p2 = new BlockPos((Vec3i)pos);
                    p2 = p2.func_177982_a(k * 4 + 1 + 8 + random.nextInt(3), 0, i * 4 + 1 + 8 + random.nextInt(3));
                    p2 = world.func_175645_m(p2);
                    final WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
                    worldgenbigmushroom.func_180709_b(world, random, p2);
                }
            }
        }
        try {
            super.func_180624_a(world, random, pos);
        }
        catch (Exception ex) {}
        for (int a = 0; a < 8; ++a) {
            BlockPos p3;
            for (p3 = new BlockPos((Vec3i)pos), p3 = p3.func_177982_a(random.nextInt(16), 0, random.nextInt(16)), p3 = world.func_175645_m(p3); p3.func_177956_o() > 50 && world.func_180495_p(p3).func_177230_c() != Blocks.field_150349_c; p3 = p3.func_177977_b()) {}
            final Block l2 = world.func_180495_p(p3).func_177230_c();
            if (l2 == Blocks.field_150349_c && world.func_180495_p(p3.func_177984_a()).func_177230_c().func_176200_f((IBlockAccess)world, p3.func_177984_a()) && this.isBlockAdjacentToWood((IBlockAccess)world, p3.func_177984_a())) {
                world.func_180501_a(p3.func_177984_a(), BlocksTC.vishroom.func_176223_P(), 2);
            }
        }
    }
    
    private boolean isBlockAdjacentToWood(final IBlockAccess world, final BlockPos pos) {
        final int count = 0;
        for (int xx = -1; xx <= 1; ++xx) {
            for (int yy = -1; yy <= 1; ++yy) {
                for (int zz = -1; zz <= 1; ++zz) {
                    if (xx != 0 || yy != 0 || zz != 0) {
                        if (Utils.isWoodLog(world, pos.func_177982_a(xx, yy, zz))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public BlockFlower.EnumFlowerType func_180623_a(final Random rand, final BlockPos pos) {
        final double d0 = MathHelper.func_151237_a((1.0 + BiomeGenMagicalForest.field_180281_af.func_151601_a(pos.func_177958_n() / 48.0, pos.func_177952_p() / 48.0)) / 2.0, 0.0, 0.9999);
        return BlockFlower.EnumFlowerType.values()[(int)(d0 * BlockFlower.EnumFlowerType.values().length)];
    }
    
    static {
        blobs = new WorldGenBlockBlob(Blocks.field_150341_Y, 0);
    }
}
