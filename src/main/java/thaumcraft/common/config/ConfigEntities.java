package thaumcraft.common.config;

import net.minecraftforge.registries.*;
import net.minecraft.util.*;
import thaumcraft.*;
import net.minecraftforge.fml.common.registry.*;
import thaumcraft.common.entities.*;
import thaumcraft.common.entities.projectile.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.common.entities.monster.boss.*;
import thaumcraft.common.entities.monster.cult.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.collect.*;
import java.util.*;

public class ConfigEntities
{
    public static HashMap<Class, Integer> championModWhitelist;
    
    public static void initEntities(final IForgeRegistry<EntityEntry> iForgeRegistry) {
        int id = 0;
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "CultistPortalGreater"), (Class)EntityCultistPortalGreater.class, "CultistPortalGreater", id++, (Object)Thaumcraft.instance, 64, 20, false, 6842578, 32896);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "CultistPortalLesser"), (Class)EntityCultistPortalLesser.class, "CultistPortalLesser", id++, (Object)Thaumcraft.instance, 64, 20, false, 9438728, 6316242);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "SpecialItem"), (Class)EntitySpecialItem.class, "SpecialItem", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "FollowItem"), (Class)EntityFollowingItem.class, "FollowItem", id++, (Object)Thaumcraft.instance, 64, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "FallingTaint"), (Class)EntityFallingTaint.class, "FallingTaint", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Alumentum"), (Class)EntityAlumentum.class, "Alumentum", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "GolemDart"), (Class)EntityGolemDart.class, "GolemDart", id++, (Object)Thaumcraft.instance, 64, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "EldritchOrb"), (Class)EntityEldritchOrb.class, "EldritchOrb", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "BottleTaint"), (Class)EntityBottleTaint.class, "BottleTaint", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "GolemOrb"), (Class)EntityGolemOrb.class, "GolemOrb", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Grapple"), (Class)EntityGrapple.class, "Grapple", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "FocusProjectile"), (Class)EntityFocusProjectile.class, "FocusProjectile", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "FocusCloud"), (Class)EntityFocusCloud.class, "FocusCloud", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Focusmine"), (Class)EntityFocusMine.class, "Focusmine", id++, (Object)Thaumcraft.instance, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TurretBasic"), (Class)EntityTurretCrossbow.class, "TurretBasic", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TurretAdvanced"), (Class)EntityTurretCrossbowAdvanced.class, "TurretAdvanced", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "ArcaneBore"), (Class)EntityArcaneBore.class, "ArcaneBore", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Golem"), (Class)EntityThaumcraftGolem.class, "Golem", id++, (Object)Thaumcraft.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "EldritchWarden"), (Class)EntityEldritchWarden.class, "EldritchWarden", id++, (Object)Thaumcraft.instance, 64, 3, true, 6842578, 8421504);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "EldritchGolem"), (Class)EntityEldritchGolem.class, "EldritchGolem", id++, (Object)Thaumcraft.instance, 64, 3, true, 6842578, 8947848);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "CultistLeader"), (Class)EntityCultistLeader.class, "CultistLeader", id++, (Object)Thaumcraft.instance, 64, 3, true, 6842578, 9438728);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintacleGiant"), (Class)EntityTaintacleGiant.class, "TaintacleGiant", id++, (Object)Thaumcraft.instance, 96, 3, false, 6842578, 10618530);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "BrainyZombie"), (Class)EntityBrainyZombie.class, "BrainyZombie", id++, (Object)Thaumcraft.instance, 64, 3, true, -16129, -16744448);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "GiantBrainyZombie"), (Class)EntityGiantBrainyZombie.class, "GiantBrainyZombie", id++, (Object)Thaumcraft.instance, 64, 3, true, -16129, -16760832);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Wisp"), (Class)EntityWisp.class, "Wisp", id++, (Object)Thaumcraft.instance, 64, 3, false, -16129, -1);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Firebat"), (Class)EntityFireBat.class, "Firebat", id++, (Object)Thaumcraft.instance, 64, 3, false, -16129, -806354944);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Spellbat"), (Class)EntitySpellBat.class, "Spellbat", id++, (Object)Thaumcraft.instance, 64, 3, false, -16129, -806354944);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Pech"), (Class)EntityPech.class, "Pech", id++, (Object)Thaumcraft.instance, 64, 3, true, -16129, -12582848);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "MindSpider"), (Class)EntityMindSpider.class, "MindSpider", id++, (Object)Thaumcraft.instance, 64, 3, true, 4996656, 4473924);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "EldritchGuardian"), (Class)EntityEldritchGuardian.class, "EldritchGuardian", id++, (Object)Thaumcraft.instance, 64, 3, true, 8421504, 0);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "CultistKnight"), (Class)EntityCultistKnight.class, "CultistKnight", id++, (Object)Thaumcraft.instance, 64, 3, true, 9438728, 128);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "CultistCleric"), (Class)EntityCultistCleric.class, "CultistCleric", id++, (Object)Thaumcraft.instance, 64, 3, true, 9438728, 8388608);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "EldritchCrab"), (Class)EntityEldritchCrab.class, "EldritchCrab", id++, (Object)Thaumcraft.instance, 64, 3, true, 8421504, 5570560);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "InhabitedZombie"), (Class)EntityInhabitedZombie.class, "InhabitedZombie", id++, (Object)Thaumcraft.instance, 64, 3, true, 8421504, 5570560);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "ThaumSlime"), (Class)EntityThaumicSlime.class, "ThaumSlime", id++, (Object)Thaumcraft.instance, 64, 3, true, 10618530, -32513);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintCrawler"), (Class)EntityTaintCrawler.class, "TaintCrawler", id++, (Object)Thaumcraft.instance, 64, 3, true, 10618530, 3158064);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "Taintacle"), (Class)EntityTaintacle.class, "Taintacle", id++, (Object)Thaumcraft.instance, 64, 3, false, 10618530, 4469572);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintacleTiny"), (Class)EntityTaintacleSmall.class, "TaintacleTiny", id++, (Object)Thaumcraft.instance, 64, 3, false);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintSwarm"), (Class)EntityTaintSwarm.class, "TaintSwarm", id++, (Object)Thaumcraft.instance, 64, 3, false, 10618530, 16744576);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintSeed"), (Class)EntityTaintSeed.class, "TaintSeed", id++, (Object)Thaumcraft.instance, 64, 20, false, 10618530, 4465237);
        EntityRegistry.registerModEntity(new ResourceLocation("thaumcraft", "TaintSeedPrime"), (Class)EntityTaintSeedPrime.class, "TaintSeedPrime", id++, (Object)Thaumcraft.instance, 64, 20, false, 10618530, 5583718);
    }
    
    public static void postInitEntitySpawns() {
        final ArrayList<Biome> biomes = new ArrayList<Biome>();
        for (final BiomeManager.BiomeEntry be : BiomeManager.getBiomes(BiomeManager.BiomeType.WARM)) {
            biomes.add(be.biome);
        }
        for (final BiomeManager.BiomeEntry be : BiomeManager.getBiomes(BiomeManager.BiomeType.COOL)) {
            biomes.add(be.biome);
        }
        for (final BiomeManager.BiomeEntry be : BiomeManager.getBiomes(BiomeManager.BiomeType.ICY)) {
            biomes.add(be.biome);
        }
        for (final BiomeManager.BiomeEntry be : BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT)) {
            biomes.add(be.biome);
        }
        final Biome[] allBiomes = biomes.toArray(new Biome[] { null });
        if (ModConfig.CONFIG_WORLD.allowSpawnAngryZombie) {
            for (final Biome bgb : biomes) {
                if (bgb != null && (bgb.func_76747_a(EnumCreatureType.MONSTER) != null & bgb.func_76747_a(EnumCreatureType.MONSTER).size() > 0)) {
                    EntityRegistry.addSpawn((Class)EntityBrainyZombie.class, 10, 1, 1, EnumCreatureType.MONSTER, new Biome[] { bgb });
                }
            }
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnPech) {
            for (final Biome bgb : BiomeDictionary.getBiomes(BiomeDictionary.Type.MAGICAL)) {
                if (bgb != null && (bgb.func_76747_a(EnumCreatureType.MONSTER) != null & bgb.func_76747_a(EnumCreatureType.MONSTER).size() > 0)) {
                    EntityRegistry.addSpawn((Class)EntityPech.class, 10, 1, 1, EnumCreatureType.MONSTER, new Biome[] { bgb });
                }
            }
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnFireBat) {
            EntityRegistry.addSpawn((Class)EntityFireBat.class, 10, 1, 2, EnumCreatureType.MONSTER, (Biome[])BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).toArray(new Biome[0]));
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
                EntityRegistry.addSpawn((Class)EntityFireBat.class, 5, 1, 2, EnumCreatureType.MONSTER, (Biome[])biomes.toArray(allBiomes));
            }
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnWisp) {
            EntityRegistry.addSpawn((Class)EntityWisp.class, 5, 1, 1, EnumCreatureType.MONSTER, (Biome[])BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER).toArray(new Biome[0]));
        }
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Zombie:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Spider:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Blaze:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Enderman:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Skeleton:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Witch:1");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Thaumcraft.EldritchCrab:0");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Thaumcraft.Taintacle:2");
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "Thaumcraft.InhabitedZombie:3");
    }
    
    static {
        ConfigEntities.championModWhitelist = new HashMap<Class, Integer>();
    }
}
