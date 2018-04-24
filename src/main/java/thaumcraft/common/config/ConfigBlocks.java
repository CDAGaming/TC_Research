package thaumcraft.common.config;

import thaumcraft.api.blocks.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.blocks.world.ore.*;
import thaumcraft.common.blocks.*;
import net.minecraftforge.registries.*;
import thaumcraft.common.blocks.world.plants.*;
import net.minecraft.block.material.*;
import thaumcraft.common.blocks.world.*;
import thaumcraft.common.blocks.basic.*;
import thaumcraft.common.blocks.essentia.*;
import thaumcraft.common.blocks.crafting.*;
import thaumcraft.common.blocks.devices.*;
import net.minecraft.block.*;
import net.minecraftforge.fluids.*;
import thaumcraft.common.blocks.world.taint.*;
import thaumcraft.common.blocks.misc.*;
import net.minecraftforge.fml.common.registry.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.tiles.misc.*;
import thaumcraft.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class ConfigBlocks
{
    public static void initMisc() {
        BlocksTC.oreAmber.setHarvestLevel("pickaxe", 1);
        BlocksTC.oreCinnabar.setHarvestLevel("pickaxe", 2);
        BlockUtils.portableHoleBlackList.add("minecraft:bed");
        BlockUtils.portableHoleBlackList.add("minecraft:piston");
        BlockUtils.portableHoleBlackList.add("minecraft:piston_head");
        BlockUtils.portableHoleBlackList.add("minecraft:sticky_piston");
        BlockUtils.portableHoleBlackList.add("minecraft:piston_extension");
        BlockUtils.portableHoleBlackList.add("minecraft:wooden_door");
        BlockUtils.portableHoleBlackList.add("minecraft:spruce_door");
        BlockUtils.portableHoleBlackList.add("minecraft:birch_door");
        BlockUtils.portableHoleBlackList.add("minecraft:jungle_door");
        BlockUtils.portableHoleBlackList.add("minecraft:acacia_door");
        BlockUtils.portableHoleBlackList.add("minecraft:dark_oak_door");
        BlockUtils.portableHoleBlackList.add("minecraft:iron_door");
        BlockUtils.portableHoleBlackList.add("thaumcraft:infernal_furnace");
    }
    
    public static void initBlocks(final IForgeRegistry<Block> iForgeRegistry) {
        BlocksTC.oreAmber = registerBlock(new BlockOreTC("ore_amber").func_149711_c(1.5f));
        BlocksTC.oreCinnabar = registerBlock(new BlockOreTC("ore_cinnabar").func_149711_c(2.0f));
        BlocksTC.oreQuartz = registerBlock(new BlockOreTC("ore_quartz").func_149711_c(3.0f));
        BlocksTC.crystalAir = registerBlock(new BlockCrystal("crystal_aer", Aspect.AIR));
        BlocksTC.crystalFire = registerBlock(new BlockCrystal("crystal_ignis", Aspect.FIRE));
        BlocksTC.crystalWater = registerBlock(new BlockCrystal("crystal_aqua", Aspect.WATER));
        BlocksTC.crystalEarth = registerBlock(new BlockCrystal("crystal_terra", Aspect.EARTH));
        BlocksTC.crystalOrder = registerBlock(new BlockCrystal("crystal_ordo", Aspect.ORDER));
        BlocksTC.crystalEntropy = registerBlock(new BlockCrystal("crystal_perditio", Aspect.ENTROPY));
        BlocksTC.crystalTaint = registerBlock(new BlockCrystal("crystal_vitium", Aspect.FLUX));
        ShardType.AIR.setOre(BlocksTC.crystalAir);
        ShardType.FIRE.setOre(BlocksTC.crystalFire);
        ShardType.WATER.setOre(BlocksTC.crystalWater);
        ShardType.EARTH.setOre(BlocksTC.crystalEarth);
        ShardType.ORDER.setOre(BlocksTC.crystalOrder);
        ShardType.ENTROPY.setOre(BlocksTC.crystalEntropy);
        ShardType.FLUX.setOre(BlocksTC.crystalTaint);
        BlocksTC.stoneArcane = registerBlock(new BlockStoneTC("stone_arcane", true));
        BlocksTC.stoneArcaneBrick = registerBlock(new BlockStoneTC("stone_arcane_brick", true));
        BlocksTC.stoneAncient = registerBlock(new BlockStoneTC("stone_ancient", true));
        BlocksTC.stoneAncientTile = registerBlock(new BlockStoneTC("stone_ancient_tile", false));
        BlocksTC.stoneAncientRock = registerBlock(new BlockStoneTC("stone_ancient_rock", false).func_149711_c(-1.0f));
        BlocksTC.stoneAncientGlyphed = registerBlock(new BlockStoneTC("stone_ancient_glyphed", false));
        BlocksTC.stoneAncientDoorway = registerBlock(new BlockStoneTC("stone_ancient_doorway", false).func_149711_c(-1.0f));
        BlocksTC.stoneEldritchTile = registerBlock(new BlockStoneTC("stone_eldritch_tile", true).func_149711_c(15.0f).func_149752_b(1000.0f));
        BlocksTC.stonePorous = registerBlock(new BlockStonePorous());
        BlocksTC.stairsArcane = registerBlock((Block)new BlockStairsTC("stairs_arcane", BlocksTC.stoneArcane.func_176223_P()));
        BlocksTC.stairsArcaneBrick = registerBlock((Block)new BlockStairsTC("stairs_arcane_brick", BlocksTC.stoneArcaneBrick.func_176223_P()));
        BlocksTC.stairsAncient = registerBlock((Block)new BlockStairsTC("stairs_ancient", BlocksTC.stoneAncient.func_176223_P()));
        BlocksTC.slabArcaneStone = (BlockSlab)new BlockSlabTC.Half("slab_arcane_stone", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.doubleSlabArcaneStone = (BlockSlab)new BlockSlabTC.Double("slab_double_arcane_stone", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.slabArcaneBrick = (BlockSlab)new BlockSlabTC.Half("slab_arcane_brick", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.doubleSlabArcaneBrick = (BlockSlab)new BlockSlabTC.Double("slab_double_arcane_brick", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.slabAncient = (BlockSlab)new BlockSlabTC.Half("slab_ancient", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.doubleSlabAncient = (BlockSlab)new BlockSlabTC.Double("slab_double_ancient", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.slabEldritch = (BlockSlab)new BlockSlabTC.Half("slab_eldritch", false).func_149711_c(2.0f).func_149752_b(10.0f);
        BlocksTC.doubleSlabEldritch = (BlockSlab)new BlockSlabTC.Double("slab_double_eldritch", false).func_149711_c(2.0f).func_149752_b(10.0f);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabArcaneStone);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabArcaneStone);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabArcaneBrick);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabArcaneBrick);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabAncient);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabAncient);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabEldritch);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabEldritch);
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabArcaneStone, BlocksTC.slabArcaneStone, BlocksTC.doubleSlabArcaneStone).setRegistryName(BlocksTC.slabArcaneStone.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabArcaneBrick, BlocksTC.slabArcaneBrick, BlocksTC.doubleSlabArcaneBrick).setRegistryName(BlocksTC.slabArcaneBrick.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabAncient, BlocksTC.slabAncient, BlocksTC.doubleSlabAncient).setRegistryName(BlocksTC.slabAncient.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabEldritch, BlocksTC.slabEldritch, BlocksTC.doubleSlabEldritch).setRegistryName(BlocksTC.slabEldritch.getRegistryName()));
        BlocksTC.saplingGreatwood = registerBlock((Block)new BlockSaplingTC("sapling_greatwood"));
        BlocksTC.saplingSilverwood = registerBlock((Block)new BlockSaplingTC("sapling_silverwood"));
        BlocksTC.logGreatwood = registerBlock(new BlockLogsTC("log_greatwood"));
        BlocksTC.logSilverwood = registerBlock(new BlockLogsTC("log_silverwood"));
        BlocksTC.leafGreatwood = registerBlock((Block)new BlockLeavesTC("leaves_greatwood"));
        BlocksTC.leafSilverwood = registerBlock((Block)new BlockLeavesTC("leaves_silverwood"));
        BlocksTC.shimmerleaf = registerBlock((Block)new BlockPlantShimmerleaf());
        BlocksTC.cinderpearl = registerBlock((Block)new BlockPlantCinderpearl());
        BlocksTC.vishroom = registerBlock((Block)new BlockPlantVishroom());
        BlocksTC.plankGreatwood = registerBlock(new BlockPlanksTC("plank_greatwood"));
        BlocksTC.plankSilverwood = registerBlock(new BlockPlanksTC("plank_silverwood"));
        BlocksTC.stairsGreatwood = registerBlock((Block)new BlockStairsTC("stairs_greatwood", BlocksTC.plankGreatwood.func_176223_P()));
        BlocksTC.stairsSilverwood = registerBlock((Block)new BlockStairsTC("stairs_silverwood", BlocksTC.plankSilverwood.func_176223_P()));
        BlocksTC.slabGreatwood = (BlockSlab)new BlockSlabTC.Half("slab_greatwood", true).func_149711_c(1.2f).func_149752_b(2.0f);
        BlocksTC.doubleSlabGreatwood = (BlockSlab)new BlockSlabTC.Double("slab_double_greatwood", true).func_149711_c(1.2f).func_149752_b(2.0f);
        BlocksTC.slabSilverwood = (BlockSlab)new BlockSlabTC.Half("slab_silverwood", true).func_149711_c(1.0f).func_149752_b(2.0f);
        BlocksTC.doubleSlabSilverwood = (BlockSlab)new BlockSlabTC.Double("slab_double_silverwood", true).func_149711_c(1.0f).func_149752_b(2.0f);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabGreatwood);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabGreatwood);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.slabSilverwood);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.doubleSlabSilverwood);
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabGreatwood, BlocksTC.slabGreatwood, BlocksTC.doubleSlabGreatwood).setRegistryName(BlocksTC.slabGreatwood.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemSlab((Block)BlocksTC.slabSilverwood, BlocksTC.slabSilverwood, BlocksTC.doubleSlabSilverwood).setRegistryName(BlocksTC.slabSilverwood.getRegistryName()));
        BlocksTC.amberBlock = registerBlock(new BlockTranslucent("amber_block"));
        BlocksTC.amberBrick = registerBlock(new BlockTranslucent("amber_brick"));
        BlocksTC.fleshBlock = registerBlock(new BlockFlesh());
        BlocksTC.lootCrateCommon = registerBlock(new BlockLoot(Material.field_151575_d, "loot_crate_common", BlockLoot.LootType.COMMON));
        BlocksTC.lootCrateUncommon = registerBlock(new BlockLoot(Material.field_151575_d, "loot_crate_uncommon", BlockLoot.LootType.UNCOMMON));
        BlocksTC.lootCrateRare = registerBlock(new BlockLoot(Material.field_151575_d, "loot_crate_rare", BlockLoot.LootType.RARE));
        BlocksTC.lootUrnCommon = registerBlock(new BlockLoot(Material.field_151576_e, "loot_urn_common", BlockLoot.LootType.COMMON));
        BlocksTC.lootUrnUncommon = registerBlock(new BlockLoot(Material.field_151576_e, "loot_urn_uncommon", BlockLoot.LootType.UNCOMMON));
        BlocksTC.lootUrnRare = registerBlock(new BlockLoot(Material.field_151576_e, "loot_urn_rare", BlockLoot.LootType.RARE));
        BlocksTC.taintFibre = registerBlock(new BlockTaintFibre());
        BlocksTC.taintCrust = registerBlock(new BlockTaint("taint_crust"));
        BlocksTC.taintSoil = registerBlock(new BlockTaint("taint_soil"));
        BlocksTC.taintRock = registerBlock(new BlockTaint("taint_rock"));
        BlocksTC.taintGeyser = registerBlock(new BlockTaint("taint_geyser"));
        BlocksTC.taintFeature = registerBlock(new BlockTaintFeature());
        BlocksTC.taintLog = registerBlock(new BlockTaintLog());
        BlocksTC.grassAmbient = registerBlock((Block)new BlockGrassAmbient());
        BlocksTC.tableWood = registerBlock(new BlockTable(Material.field_151575_d, "table_wood", SoundType.field_185848_a).func_149711_c(2.0f));
        BlocksTC.tableStone = registerBlock(new BlockTable(Material.field_151576_e, "table_stone", SoundType.field_185851_d).func_149711_c(2.5f));
        BlocksTC.pedestalArcane = registerBlock(new BlockPedestal("pedestal_arcane"));
        BlocksTC.pedestalAncient = registerBlock(new BlockPedestal("pedestal_ancient"));
        BlocksTC.pedestalEldritch = registerBlock(new BlockPedestal("pedestal_eldritch"));
        BlocksTC.metalBlockBrass = registerBlock(new BlockMetalTC("metal_brass"));
        BlocksTC.metalBlockThaumium = registerBlock(new BlockMetalTC("metal_thaumium"));
        BlocksTC.metalBlockVoid = registerBlock(new BlockMetalTC("metal_void"));
        BlocksTC.metalAlchemical = registerBlock(new BlockMetalTC("metal_alchemical"));
        BlocksTC.metalAlchemicalAdvanced = registerBlock(new BlockMetalTC("metal_alchemical_advanced"));
        BlocksTC.pavingStoneTravel = registerBlock(new BlockPavingStone("paving_stone_travel"));
        BlocksTC.pavingStoneBarrier = registerBlock(new BlockPavingStone("paving_stone_barrier"));
        BlocksTC.pillarArcane = registerBlock(new BlockPillar("pillar_arcane"));
        BlocksTC.pillarAncient = registerBlock(new BlockPillar("pillar_ancient"));
        BlocksTC.pillarEldritch = registerBlock(new BlockPillar("pillar_eldritch"));
        BlocksTC.matrixSpeed = registerBlock(new BlockStoneTC("matrix_speed", false));
        BlocksTC.matrixCost = registerBlock(new BlockStoneTC("matrix_cost", false));
        for (final EnumDyeColor dye : EnumDyeColor.values()) {
            BlocksTC.candles.put(dye, registerBlock(new BlockCandle("candle_" + dye.func_176762_d().toLowerCase(), dye)));
        }
        for (final EnumDyeColor dye : EnumDyeColor.values()) {
            final BlockBannerTC block = new BlockBannerTC("banner_" + dye.func_176762_d().toLowerCase(), dye);
            ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)block);
            ForgeRegistries.ITEMS.register(new BlockBannerTCItem(block).setRegistryName(block.getRegistryName()));
            BlocksTC.banners.put(dye, block);
        }
        BlocksTC.bannerCrimsonCult = new BlockBannerTC("banner_crimson_cult", null);
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)BlocksTC.bannerCrimsonCult);
        ForgeRegistries.ITEMS.register(new BlockBannerTCItem((BlockBannerTC)BlocksTC.bannerCrimsonCult).setRegistryName(BlocksTC.bannerCrimsonCult.getRegistryName()));
        for (final EnumDyeColor dye : EnumDyeColor.values()) {
            BlocksTC.nitor.put(dye, registerBlock(new BlockNitor("nitor_" + dye.func_176762_d().toLowerCase(), dye)));
        }
        BlocksTC.arcaneWorkbench = registerBlock(new BlockArcaneWorkbench());
        BlocksTC.arcaneWorkbenchCharger = registerBlock(new BlockArcaneWorkbenchCharger());
        BlocksTC.dioptra = registerBlock(new BlockDioptra());
        BlocksTC.researchTable = registerBlock(new BlockResearchTable());
        BlocksTC.crucible = registerBlock(new BlockCrucible());
        BlocksTC.arcaneEar = registerBlock(new BlockArcaneEar("arcane_ear"));
        BlocksTC.arcaneEarToggle = registerBlock(new BlockArcaneEarToggle());
        BlocksTC.lampArcane = registerBlock(new BlockLamp(TileLampArcane.class, "lamp_arcane"));
        BlocksTC.lampFertility = registerBlock(new BlockLamp(TileLampFertility.class, "lamp_fertility"));
        BlocksTC.lampGrowth = registerBlock(new BlockLamp(TileLampGrowth.class, "lamp_growth"));
        BlocksTC.levitator = registerBlock(new BlockLevitator());
        BlocksTC.centrifuge = registerBlock(new BlockCentrifuge());
        BlocksTC.bellows = registerBlock(new BlockBellows());
        BlocksTC.smelterBasic = registerBlock(new BlockSmelter("smelter_basic"));
        BlocksTC.smelterThaumium = registerBlock(new BlockSmelter("smelter_thaumium"));
        BlocksTC.smelterVoid = registerBlock(new BlockSmelter("smelter_void"));
        BlocksTC.smelterAux = registerBlock(new BlockSmelterAux());
        BlocksTC.smelterVent = registerBlock(new BlockSmelterVent());
        BlocksTC.alembic = registerBlock(new BlockAlembic());
        BlocksTC.rechargePedestal = registerBlock(new BlockRechargePedestal());
        BlocksTC.wandWorkbench = registerBlock(new BlockFocalManipulator());
        BlocksTC.hungryChest = registerBlock((Block)new BlockHungryChest());
        BlocksTC.tube = registerBlock(new BlockTube(TileTube.class, "tube"));
        BlocksTC.tubeValve = registerBlock(new BlockTube(TileTubeValve.class, "tube_valve"));
        BlocksTC.tubeRestrict = registerBlock(new BlockTube(TileTubeRestrict.class, "tube_restrict"));
        BlocksTC.tubeOneway = registerBlock(new BlockTube(TileTubeOneway.class, "tube_oneway"));
        BlocksTC.tubeFilter = registerBlock(new BlockTube(TileTubeFilter.class, "tube_filter"));
        BlocksTC.tubeBuffer = registerBlock(new BlockTube(TileTubeBuffer.class, "tube_buffer"));
        BlocksTC.jarNormal = registerBlock(new BlockJar(TileJarFillable.class, "jar_normal"), BlockJarItem.class);
        BlocksTC.jarVoid = registerBlock(new BlockJar(TileJarFillableVoid.class, "jar_void"), BlockJarItem.class);
        BlocksTC.jarBrain = registerBlock(new BlockJar(TileJarBrain.class, "jar_brain"), BlockJarBrainItem.class);
        BlocksTC.infusionMatrix = registerBlock(new BlockInfusionMatrix());
        BlocksTC.infernalFurnace = registerBlock(new BlockInfernalFurnace());
        BlocksTC.everfullUrn = registerBlock(new BlockWaterJug());
        BlocksTC.thaumatorium = registerBlock(new BlockThaumatorium(false));
        BlocksTC.thaumatoriumTop = registerBlock(new BlockThaumatorium(true));
        BlocksTC.brainBox = registerBlock(new BlockBrainBox());
        BlocksTC.spa = registerBlock(new BlockSpa());
        BlocksTC.golemBuilder = registerBlock(new BlockGolemBuilder());
        BlocksTC.mirror = registerBlock(new BlockMirror(TileMirror.class, "mirror"), BlockMirrorItem.class);
        BlocksTC.mirrorEssentia = registerBlock(new BlockMirror(TileMirrorEssentia.class, "mirror_essentia"), BlockMirrorItem.class);
        BlocksTC.essentiaTransportInput = registerBlock(new BlockEssentiaTransport(TileEssentiaInput.class, "essentia_input"));
        BlocksTC.essentiaTransportOutput = registerBlock(new BlockEssentiaTransport(TileEssentiaOutput.class, "essentia_output"));
        BlocksTC.redstoneRelay = registerBlock(new BlockRedstoneRelay());
        BlocksTC.patternCrafter = registerBlock(new BlockPatternCrafter());
        BlocksTC.potionSprayer = registerBlock(new BlockPotionSprayer());
        BlocksTC.activatorRail = registerBlock(((Block)new BlockRailPowered().func_149711_c(0.7f).func_149647_a(ConfigItems.TABTC).setRegistryName("thaumcraft", "activator_rail")).func_149663_c("activator_rail"));
        FluidRegistry.registerFluid((Fluid)FluidFluxGoo.instance);
        iForgeRegistry.register((IForgeRegistryEntry)(BlocksTC.fluxGoo = (Block)new BlockFluxGoo()));
        FluidRegistry.registerFluid((Fluid)FluidDeath.instance);
        iForgeRegistry.register((IForgeRegistryEntry)(BlocksTC.liquidDeath = (Block)new BlockFluidDeath()));
        FluidRegistry.registerFluid((Fluid)FluidPure.instance);
        iForgeRegistry.register((IForgeRegistryEntry)(BlocksTC.purifyingFluid = (Block)new BlockFluidPure()));
        BlocksTC.hole = registerBlock((Block)new BlockHole());
        BlocksTC.effectShock = registerBlock(new BlockEffect("effect_shock"));
        BlocksTC.effectSap = registerBlock(new BlockEffect("effect_sap"));
        BlocksTC.effectGlimmer = registerBlock(new BlockEffect("effect_glimmer"));
        BlocksTC.placeholderNetherbrick = registerBlock(new BlockPlaceholder("placeholder_brick"));
        BlocksTC.placeholderObsidian = registerBlock(new BlockPlaceholder("placeholder_obsidian"));
        BlocksTC.placeholderBars = registerBlock(new BlockPlaceholder("placeholder_bars"));
        BlocksTC.placeholderAnvil = registerBlock(new BlockPlaceholder("placeholder_anvil"));
        BlocksTC.placeholderCauldron = registerBlock(new BlockPlaceholder("placeholder_cauldron"));
        BlocksTC.placeholderTable = registerBlock(new BlockPlaceholder("placeholder_table"));
        BlocksTC.empty = registerBlock(new BlockTranslucent("empty"));
        BlocksTC.barrier = registerBlock(new BlockBarrier());
    }
    
    public static void initTileEntities() {
        GameRegistry.registerTileEntity((Class)TileArcaneWorkbench.class, "thaumcraft:TileArcaneWorkbench");
        GameRegistry.registerTileEntity((Class)TileDioptra.class, "thaumcraft:TileDioptra");
        GameRegistry.registerTileEntity((Class)TileArcaneEar.class, "thaumcraft:TileArcaneEar");
        GameRegistry.registerTileEntity((Class)TileLevitator.class, "thaumcraft:TileLevitator");
        GameRegistry.registerTileEntity((Class)TileCrucible.class, "thaumcraft:TileCrucible");
        GameRegistry.registerTileEntity((Class)TileNitor.class, "thaumcraft:TileNitor");
        GameRegistry.registerTileEntity((Class)TileFocalManipulator.class, "thaumcraft:TileFocalManipulator");
        GameRegistry.registerTileEntity((Class)TilePedestal.class, "thaumcraft:TilePedestal");
        GameRegistry.registerTileEntity((Class)TileRechargePedestal.class, "thaumcraft:TileRechargePedestal");
        GameRegistry.registerTileEntity((Class)TileResearchTable.class, "thaumcraft:TileResearchTable");
        GameRegistry.registerTileEntity((Class)TileTube.class, "thaumcraft:TileTube");
        GameRegistry.registerTileEntity((Class)TileTubeValve.class, "thaumcraft:TileTubeValve");
        GameRegistry.registerTileEntity((Class)TileTubeFilter.class, "thaumcraft:TileTubeFilter");
        GameRegistry.registerTileEntity((Class)TileTubeRestrict.class, "thaumcraft:TileTubeRestrict");
        GameRegistry.registerTileEntity((Class)TileTubeOneway.class, "thaumcraft:TileTubeOneway");
        GameRegistry.registerTileEntity((Class)TileTubeBuffer.class, "thaumcraft:TileTubeBuffer");
        GameRegistry.registerTileEntity((Class)TileHungryChest.class, "thaumcraft:TileChestHungry");
        GameRegistry.registerTileEntity((Class)TileCentrifuge.class, "thaumcraft:TileCentrifuge");
        GameRegistry.registerTileEntity((Class)TileJarFillable.class, "thaumcraft:TileJar");
        GameRegistry.registerTileEntity((Class)TileJarFillableVoid.class, "thaumcraft:TileJarVoid");
        GameRegistry.registerTileEntity((Class)TileJarBrain.class, "thaumcraft:TileJarBrain");
        GameRegistry.registerTileEntity((Class)TileBellows.class, "thaumcraft:TileBellows");
        GameRegistry.registerTileEntity((Class)TileSmelter.class, "thaumcraft:TileSmelter");
        GameRegistry.registerTileEntity((Class)TileAlembic.class, "thaumcraft:TileAlembic");
        GameRegistry.registerTileEntity((Class)TileInfusionMatrix.class, "thaumcraft:TileInfusionMatrix");
        GameRegistry.registerTileEntity((Class)TileWaterJug.class, "thaumcraft:TileWaterJug");
        GameRegistry.registerTileEntity((Class)TileInfernalFurnace.class, "thaumcraft:TileInfernalFurnace");
        GameRegistry.registerTileEntity((Class)TileThaumatorium.class, "thaumcraft:TileThaumatorium");
        GameRegistry.registerTileEntity((Class)TileThaumatoriumTop.class, "thaumcraft:TileThaumatoriumTop");
        GameRegistry.registerTileEntity((Class)TileSpa.class, "thaumcraft:TileSpa");
        GameRegistry.registerTileEntity((Class)TileLampGrowth.class, "thaumcraft:TileLampGrowth");
        GameRegistry.registerTileEntity((Class)TileLampArcane.class, "thaumcraft:TileLampArcane");
        GameRegistry.registerTileEntity((Class)TileLampFertility.class, "thaumcraft:TileLampFertility");
        GameRegistry.registerTileEntity((Class)TileMirror.class, "thaumcraft:TileMirror");
        GameRegistry.registerTileEntity((Class)TileMirrorEssentia.class, "thaumcraft:TileMirrorEssentia");
        GameRegistry.registerTileEntity((Class)TileRedstoneRelay.class, "thaumcraft:TileRedstoneRelay");
        GameRegistry.registerTileEntity((Class)TileGolemBuilder.class, "thaumcraft:TileGolemBuilder");
        GameRegistry.registerTileEntity((Class)TileEssentiaInput.class, "thaumcraft:TileEssentiaInput");
        GameRegistry.registerTileEntity((Class)TileEssentiaOutput.class, "thaumcraft:TileEssentiaOutput");
        GameRegistry.registerTileEntity((Class)TilePatternCrafter.class, "thaumcraft:TilePatternCrafter");
        GameRegistry.registerTileEntity((Class)TilePotionSprayer.class, "thaumcraft:TilePotionSprayer");
        GameRegistry.registerTileEntity((Class)TileBanner.class, "thaumcraft:TileBanner");
        GameRegistry.registerTileEntity((Class)TileHole.class, "thaumcraft:TileHole");
        GameRegistry.registerTileEntity((Class)TileBarrierStone.class, "thaumcraft:TileBarrierStone");
    }
    
    private static Block registerBlock(final Block block) {
        return registerBlock(block, new ItemBlock(block));
    }
    
    private static Block registerBlock(final Block block, final ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register((IForgeRegistryEntry)itemBlock);
        Thaumcraft.proxy.registerModel(itemBlock);
        return block;
    }
    
    private static Block registerBlock(final Block block, final Class clazz) {
        ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)block);
        try {
            final ItemBlock itemBlock = (ItemBlock)clazz.getConstructors()[0].newInstance(block);
            itemBlock.setRegistryName(block.getRegistryName());
            ForgeRegistries.ITEMS.register((IForgeRegistryEntry)itemBlock);
            Thaumcraft.proxy.registerModel(itemBlock);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return block;
    }
    
    public static final class FluidPure extends Fluid
    {
        public static final String name = "purifying_fluid";
        public static final FluidPure instance;
        
        private FluidPure() {
            super("purifying_fluid", new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"));
            this.setLuminosity(5);
            this.setRarity(EnumRarity.RARE);
        }
        
        public int getColor() {
            return 2013252778;
        }
        
        static {
            instance = new FluidPure();
        }
    }
    
    public static final class FluidDeath extends Fluid
    {
        public static final String name = "liquid_death";
        public static final FluidDeath instance;
        
        private FluidDeath() {
            super("liquid_death", new ResourceLocation("thaumcraft:blocks/animatedglow"), new ResourceLocation("thaumcraft:blocks/animatedglow"));
            this.setViscosity(1500);
            this.setRarity(EnumRarity.RARE);
        }
        
        public int getColor() {
            return -263978855;
        }
        
        static {
            instance = new FluidDeath();
        }
    }
    
    public static final class FluidFluxGoo extends Fluid
    {
        public static final String name = "flux_goo";
        public static final FluidFluxGoo instance;
        
        private FluidFluxGoo() {
            super("flux_goo", new ResourceLocation("thaumcraft:blocks/flux_goo"), new ResourceLocation("thaumcraft:blocks/flux_goo"));
            this.setViscosity(6000);
            this.setDensity(8);
        }
        
        static {
            instance = new FluidFluxGoo();
        }
    }
    
    public static final class FluidTaintDust extends Fluid
    {
        public static final String name = "taint_dust";
        public static final FluidTaintDust instance;
        
        private FluidTaintDust() {
            super("taint_dust", new ResourceLocation("thaumcraft:blocks/taint_dust"), new ResourceLocation("thaumcraft:blocks/taint_dust"));
            this.setViscosity(8000);
            this.setDensity(2000);
        }
        
        static {
            instance = new FluidTaintDust();
        }
    }
}
