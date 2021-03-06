package thaumcraft.common.config;

import thaumcraft.api.items.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import thaumcraft.common.blocks.basic.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import thaumcraft.api.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.api.golems.*;
import java.util.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.registry.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import thaumcraft.api.internal.*;
import thaumcraft.api.crafting.*;

public class ConfigRecipes
{
    static ResourceLocation defaultGroup;
    public static HashMap<String, ArrayList<ResourceLocation>> recipeGroups;
    
    public static void initializeCompoundRecipes() {
        IDustTrigger.registerDustTrigger(new DustTriggerSimple("!gotdream", Blocks.field_150342_X, new ItemStack(ItemsTC.thaumonomicon)));
        IDustTrigger.registerDustTrigger(new DustTriggerOre("!gotdream", "bookshelf", new ItemStack(ItemsTC.thaumonomicon)));
        IDustTrigger.registerDustTrigger(new DustTriggerSimple("FIRSTSTEPS@1", Blocks.field_150462_ai, new ItemStack(BlocksTC.arcaneWorkbench)));
        IDustTrigger.registerDustTrigger(new DustTriggerOre("FIRSTSTEPS@1", "workbench", new ItemStack(BlocksTC.arcaneWorkbench)));
        IDustTrigger.registerDustTrigger(new DustTriggerSimple("UNLOCKALCHEMY@1", (Block)Blocks.field_150383_bp, new ItemStack(BlocksTC.crucible)));
        final Part NB = new Part(Blocks.field_150385_bj, new ItemStack(BlocksTC.placeholderNetherbrick));
        final Part OB = new Part(Blocks.field_150343_Z, new ItemStack(BlocksTC.placeholderObsidian));
        final Part IB = new Part(Blocks.field_150411_aY, "AIR");
        final Part LA = new Part(Material.field_151587_i, BlocksTC.infernalFurnace, true);
        final Part[][][] infernalFurnaceBlueprint = { { { NB, OB, NB }, { OB, null, OB }, { NB, OB, NB } }, { { NB, OB, NB }, { OB, LA, OB }, { NB, IB, NB } }, { { NB, OB, NB }, { OB, OB, OB }, { NB, OB, NB } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("INFERNALFURNACE", infernalFurnaceBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:infernalfurnace"), new ThaumcraftApi.BluePrint("INFERNALFURNACE", infernalFurnaceBlueprint, new ItemStack[] { new ItemStack(Blocks.field_150385_bj, 12), new ItemStack(Blocks.field_150343_Z, 12), new ItemStack(Blocks.field_150411_aY), new ItemStack(Items.field_151129_at) }));
        final Part IM = new Part(BlocksTC.infusionMatrix, null);
        final Part SNT = new Part(BlocksTC.stoneArcane, "AIR");
        final Part SNB1 = new Part(BlocksTC.stoneArcane, new ItemStack(BlocksTC.pillarArcane, 1, BlockPillar.calcMeta(EnumFacing.EAST)));
        final Part SNB2 = new Part(BlocksTC.stoneArcane, new ItemStack(BlocksTC.pillarArcane, 1, BlockPillar.calcMeta(EnumFacing.NORTH)));
        final Part SNB3 = new Part(BlocksTC.stoneArcane, new ItemStack(BlocksTC.pillarArcane, 1, BlockPillar.calcMeta(EnumFacing.SOUTH)));
        final Part SNB4 = new Part(BlocksTC.stoneArcane, new ItemStack(BlocksTC.pillarArcane, 1, BlockPillar.calcMeta(EnumFacing.WEST)));
        final Part PN = new Part(BlocksTC.pedestalArcane.func_176223_P(), null);
        final Part[][][] infusionAltarNormalBlueprint = { { { null, null, null }, { null, IM, null }, { null, null, null } }, { { SNT, null, SNT }, { null, null, null }, { SNT, null, SNT } }, { { SNB1, null, SNB2 }, { null, PN, null }, { SNB3, null, SNB4 } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("INFUSION", infusionAltarNormalBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:infusionaltar"), new ThaumcraftApi.BluePrint("INFUSION", infusionAltarNormalBlueprint, new ItemStack[] { new ItemStack(BlocksTC.stoneArcane, 8), new ItemStack(BlocksTC.pedestalArcane), new ItemStack(BlocksTC.infusionMatrix) }));
        final Part SAT = new Part(BlocksTC.stoneAncient, "AIR");
        final Part SAB1 = new Part(BlocksTC.stoneAncient, new ItemStack(BlocksTC.pillarAncient, 1, BlockPillar.calcMeta(EnumFacing.EAST)));
        final Part SAB2 = new Part(BlocksTC.stoneAncient, new ItemStack(BlocksTC.pillarAncient, 1, BlockPillar.calcMeta(EnumFacing.NORTH)));
        final Part SAB3 = new Part(BlocksTC.stoneAncient, new ItemStack(BlocksTC.pillarAncient, 1, BlockPillar.calcMeta(EnumFacing.SOUTH)));
        final Part SAB4 = new Part(BlocksTC.stoneAncient, new ItemStack(BlocksTC.pillarAncient, 1, BlockPillar.calcMeta(EnumFacing.WEST)));
        final Part PA = new Part(BlocksTC.pedestalAncient.func_176203_a(2), null);
        final Part[][][] infusionAltarAncientBlueprint = { { { null, null, null }, { null, IM, null }, { null, null, null } }, { { SAT, null, SAT }, { null, null, null }, { SAT, null, SAT } }, { { SAB1, null, SAB2 }, { null, PA, null }, { SAB3, null, SAB4 } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("INFUSIONANCIENT", infusionAltarAncientBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:infusionaltarancient"), new ThaumcraftApi.BluePrint("INFUSIONANCIENT", infusionAltarAncientBlueprint, new ItemStack[] { new ItemStack(BlocksTC.stoneAncient, 8), new ItemStack(BlocksTC.pedestalAncient), new ItemStack(BlocksTC.infusionMatrix) }));
        final Part SET = new Part(BlocksTC.stoneEldritchTile, "AIR");
        final Part SEB1 = new Part(BlocksTC.stoneEldritchTile, new ItemStack(BlocksTC.pillarEldritch, 1, BlockPillar.calcMeta(EnumFacing.EAST)));
        final Part SEB2 = new Part(BlocksTC.stoneEldritchTile, new ItemStack(BlocksTC.pillarEldritch, 1, BlockPillar.calcMeta(EnumFacing.NORTH)));
        final Part SEB3 = new Part(BlocksTC.stoneEldritchTile, new ItemStack(BlocksTC.pillarEldritch, 1, BlockPillar.calcMeta(EnumFacing.SOUTH)));
        final Part SEB4 = new Part(BlocksTC.stoneEldritchTile, new ItemStack(BlocksTC.pillarEldritch, 1, BlockPillar.calcMeta(EnumFacing.WEST)));
        final Part PE = new Part(BlocksTC.pedestalEldritch.func_176203_a(1), null);
        final Part[][][] infusionAltarEldritchBlueprint = { { { null, null, null }, { null, IM, null }, { null, null, null } }, { { SET, null, SET }, { null, null, null }, { SET, null, SET } }, { { SEB1, null, SEB2 }, { null, PE, null }, { SEB3, null, SEB4 } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("INFUSIONELDRITCH", infusionAltarEldritchBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:infusionaltareldritch"), new ThaumcraftApi.BluePrint("INFUSIONELDRITCH", infusionAltarEldritchBlueprint, new ItemStack[] { new ItemStack(BlocksTC.stoneEldritchTile, 8), new ItemStack(BlocksTC.pedestalEldritch), new ItemStack(BlocksTC.infusionMatrix) }));
        final Part TH1 = new Part(BlocksTC.metalAlchemical.func_176223_P(), BlocksTC.thaumatoriumTop).setApplyPlayerFacing(true);
        final Part TH2 = new Part(BlocksTC.metalAlchemical.func_176223_P(), BlocksTC.thaumatorium).setApplyPlayerFacing(true);
        final Part TH3 = new Part(BlocksTC.crucible, null);
        final Part[][][] thaumotoriumBlueprint = { { { TH1 } }, { { TH2 } }, { { TH3 } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("THAUMATORIUM", thaumotoriumBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:Thaumatorium"), new ThaumcraftApi.BluePrint("THAUMATORIUM", thaumotoriumBlueprint, new ItemStack[] { new ItemStack(BlocksTC.metalAlchemical, 2), new ItemStack(BlocksTC.crucible) }));
        final Part GP1 = new Part(Blocks.field_150411_aY, new ItemStack(BlocksTC.placeholderBars));
        final Part GP2 = new Part(Blocks.field_150383_bp, new ItemStack(BlocksTC.placeholderCauldron));
        final Part GP3 = new Part(Blocks.field_150331_J.func_176223_P().func_177226_a((IProperty)BlockPistonBase.field_176387_N, (Comparable)EnumFacing.UP), BlocksTC.golemBuilder);
        final Part GP4 = new Part(Blocks.field_150467_bQ, new ItemStack(BlocksTC.placeholderAnvil));
        final Part GP5 = new Part(BlocksTC.tableStone, new ItemStack(BlocksTC.placeholderTable));
        final Part[][][] golempressBlueprint = { { { null, null }, { GP1, null } }, { { GP2, GP4 }, { GP3, GP5 } } };
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("MINDCLOCKWORK", golempressBlueprint));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumcraft:GolemPress"), new ThaumcraftApi.BluePrint("MINDCLOCKWORK", new ItemStack(BlocksTC.golemBuilder), golempressBlueprint, new ItemStack[] { new ItemStack(Blocks.field_150411_aY), new ItemStack(Items.field_151066_bu), new ItemStack((Block)Blocks.field_150331_J), new ItemStack(Blocks.field_150467_bQ), new ItemStack(BlocksTC.tableStone) }));
    }
    
    public static void initializeAlchemyRecipes() {
        final ResourceLocation visCrystalGroup = new ResourceLocation("thaumcraft:viscrystalgroup");
        final CrucibleRecipe[] cre = new CrucibleRecipe[Aspect.aspects.size()];
        for (final Aspect aspect : Aspect.aspects.values()) {
            ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:vis_crystal_" + aspect.getTag()), new CrucibleRecipe("BASEALCHEMY", ThaumcraftApiHelper.makeCrystal(aspect), "nuggetQuartz", new AspectList().add(aspect, 2)).setGroup(visCrystalGroup));
        }
        final ResourceLocation nitorGroup = new ResourceLocation("thaumcraft", "nitorgroup");
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:nitor"), new CrucibleRecipe("UNLOCKALCHEMY@3", new ItemStack((Block)BlocksTC.nitor.get(EnumDyeColor.YELLOW)), "dustGlowstone", new AspectList().merge(Aspect.ENERGY, 10).merge(Aspect.FIRE, 10).merge(Aspect.LIGHT, 10)));
        final String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
        int a = 0;
        for (final EnumDyeColor d : EnumDyeColor.values()) {
            shapelessOreDictRecipe("NitorDye" + d.func_176762_d().toLowerCase(), nitorGroup, new ItemStack((Block)BlocksTC.nitor.get(d)), new Object[] { ConfigAspects.dyes[15 - a], "nitor" });
            ++a;
        }
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:alumentum"), new CrucibleRecipe("ALUMENTUM", new ItemStack(ItemsTC.alumentum), new ItemStack(Items.field_151044_h, 1, 32767), new AspectList().merge(Aspect.ENERGY, 10).merge(Aspect.FIRE, 10).merge(Aspect.ENTROPY, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:brassingot"), new CrucibleRecipe("METALLURGY@1", new ItemStack(ItemsTC.ingots, 1, 2), "ingotIron", new AspectList().merge(Aspect.TOOL, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:thaumiumingot"), new CrucibleRecipe("METALLURGY@2", new ItemStack(ItemsTC.ingots, 1, 0), "ingotIron", new AspectList().merge(Aspect.MAGIC, 5).merge(Aspect.EARTH, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:voidingot"), new CrucibleRecipe("METALLURGY@4", new ItemStack(ItemsTC.ingots, 1, 1), new ItemStack(ItemsTC.voidSeed), new AspectList().merge(Aspect.METAL, 10).merge(Aspect.FLUX, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:voidseed"), new CrucibleRecipe("METALLURGY@4", new ItemStack(ItemsTC.voidSeed), new ItemStack(Items.field_151014_N), new AspectList().merge(Aspect.DARKNESS, 5).merge(Aspect.VOID, 5).merge(Aspect.ELDRITCH, 5).merge(Aspect.MAGIC, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_tallow"), new CrucibleRecipe("HEDGEALCHEMY@1", new ItemStack(ItemsTC.tallow), new ItemStack(Items.field_151078_bh), new AspectList().merge(Aspect.FIRE, 1)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_leather"), new CrucibleRecipe("HEDGEALCHEMY@1", new ItemStack(Items.field_151116_aA), new ItemStack(Items.field_151078_bh), new AspectList().merge(Aspect.AIR, 3).merge(Aspect.BEAST, 3)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:focus_1"), new CrucibleRecipe("UNLOCKAUROMANCY", new ItemStack(ItemsTC.focus1), ConfigItems.ORDER_CRYSTAL, new AspectList().merge(Aspect.CRYSTAL, 20).merge(Aspect.MAGIC, 10).merge(Aspect.AURA, 5)));
        final ArrayList<CrucibleRecipe> rl = new ArrayList<CrucibleRecipe>();
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_iron"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 0), "oreIron", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_gold"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 1), "oreGold", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_cinnabar"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 6), "oreCinnabar", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        if (ModConfig.foundCopperOre) {
            ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_copper"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 2), "oreCopper", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        }
        if (ModConfig.foundTinOre) {
            ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_tin"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 3), "oreTin", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        }
        if (ModConfig.foundSilverOre) {
            ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_silver"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 4), "oreSilver", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        }
        if (ModConfig.foundLeadOre) {
            ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:metal_purification_lead"), new CrucibleRecipe("METALPURIFICATION", new ItemStack(ItemsTC.clusters, 1, 5), "oreLead", new AspectList().merge(Aspect.METAL, 5).merge(Aspect.ORDER, 5)));
        }
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:LiquidDeath"), new CrucibleRecipe("LIQUIDDEATH", new ItemStack(ItemsTC.bucketDeath), new ItemStack(Items.field_151133_ar), new AspectList().add(Aspect.DEATH, 100).add(Aspect.ALCHEMY, 20).add(Aspect.ENTROPY, 50)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:BottleTaint"), new CrucibleRecipe("BOTTLETAINT", new ItemStack(ItemsTC.bottleTaint), ItemPhial.makeFilledPhial(Aspect.FLUX), new AspectList().add(Aspect.FLUX, 30).add(Aspect.WATER, 30)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:BathSalts"), new CrucibleRecipe("BATHSALTS", new ItemStack(ItemsTC.bathSalts), new ItemStack(ItemsTC.salisMundus), new AspectList().add(Aspect.MIND, 40).add(Aspect.AIR, 40).add(Aspect.ORDER, 40).add(Aspect.LIFE, 40)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SaneSoap"), new CrucibleRecipe("SANESOAP", new ItemStack(ItemsTC.sanitySoap), new ItemStack(BlocksTC.fleshBlock), new AspectList().add(Aspect.MIND, 75).add(Aspect.ELDRITCH, 50).add(Aspect.ORDER, 75).add(Aspect.LIFE, 50)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealCollect"), new CrucibleRecipe("SEALCOLLECT", GolemHelper.getSealStack("thaumcraft:pickup"), new ItemStack(ItemsTC.seals), new AspectList().add(Aspect.DESIRE, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealCollectAdv"), new CrucibleRecipe("SEALCOLLECT&&MINDBIOTHAUMIC", GolemHelper.getSealStack("thaumcraft:pickup_advanced"), GolemHelper.getSealStack("thaumcraft:pickup"), new AspectList().add(Aspect.SENSES, 10).add(Aspect.MIND, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealStore"), new CrucibleRecipe("SEALSTORE", GolemHelper.getSealStack("thaumcraft:fill"), new ItemStack(ItemsTC.seals), new AspectList().add(Aspect.AVERSION, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealStoreAdv"), new CrucibleRecipe("SEALSTORE&&MINDBIOTHAUMIC", GolemHelper.getSealStack("thaumcraft:fill_advanced"), GolemHelper.getSealStack("thaumcraft:fill"), new AspectList().add(Aspect.SENSES, 10).add(Aspect.MIND, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealEmpty"), new CrucibleRecipe("SEALEMPTY", GolemHelper.getSealStack("thaumcraft:empty"), new ItemStack(ItemsTC.seals), new AspectList().add(Aspect.VOID, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealEmptyAdv"), new CrucibleRecipe("SEALEMPTY&&MINDBIOTHAUMIC", GolemHelper.getSealStack("thaumcraft:empty_advanced"), GolemHelper.getSealStack("thaumcraft:empty"), new AspectList().add(Aspect.SENSES, 10).add(Aspect.MIND, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealProvide"), new CrucibleRecipe("SEALPROVIDE", GolemHelper.getSealStack("thaumcraft:provider"), GolemHelper.getSealStack("thaumcraft:empty_advanced"), new AspectList().add(Aspect.EXCHANGE, 10).add(Aspect.DESIRE, 10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealGuard"), new CrucibleRecipe("SEALGUARD", GolemHelper.getSealStack("thaumcraft:guard"), new ItemStack(ItemsTC.seals), new AspectList().add(Aspect.AVERSION, 20).add(Aspect.PROTECT, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealGuardAdv"), new CrucibleRecipe("SEALGUARD&&MINDBIOTHAUMIC", GolemHelper.getSealStack("thaumcraft:guard_advanced"), GolemHelper.getSealStack("thaumcraft:guard"), new AspectList().add(Aspect.SENSES, 20).add(Aspect.MIND, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealLumber"), new CrucibleRecipe("SEALLUMBER", GolemHelper.getSealStack("thaumcraft:lumber"), GolemHelper.getSealStack("thaumcraft:breaker"), new AspectList().add(Aspect.PLANT, 40).add(Aspect.SENSES, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:SealUse"), new CrucibleRecipe("SEALUSE", GolemHelper.getSealStack("thaumcraft:use"), new ItemStack(ItemsTC.seals), new AspectList().add(Aspect.CRAFT, 20).add(Aspect.SENSES, 10).add(Aspect.MIND, 20)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:EverfullUrn"), new CrucibleRecipe("EVERFULLURN", new ItemStack(BlocksTC.everfullUrn), new ItemStack(Items.field_151162_bE), new AspectList().add(Aspect.WATER, 30).add(Aspect.CRAFT, 10).add(Aspect.EARTH, 10)));
    }
    
    public static void initializeArcaneRecipes(final IForgeRegistry<IRecipe> iForgeRegistry) {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:mechanism_simple"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASEARTIFICE", 10, new AspectList().add(Aspect.FIRE, 1).add(Aspect.WATER, 1), ItemsTC.mechanismSimple, new Object[] { "BBB", "BSI", "III", 'B', "plateBrass", 'I', "plateIron", 'S', "stickWood" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:mechanism_complex"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASEARTIFICE", 50, new AspectList().add(Aspect.FIRE, 1).add(Aspect.WATER, 1), ItemsTC.mechanismComplex, new Object[] { " M ", "TQT", " M ", 'T', "plateThaumium", 'Q', Blocks.field_150331_J, 'M', new ItemStack(ItemsTC.mechanismSimple) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:vis_resonator"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKAUROMANCY@2", 50, new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1), ItemsTC.visResonator, new Object[] { "plateIron", "gemQuartz" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:activatorrail"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "FIRSTSTEPS", 10, null, BlocksTC.activatorRail, new Object[] { new ItemStack(Blocks.field_150408_cc) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:thaumometer"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "FIRSTSTEPS@2", 20, new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), ItemsTC.thaumometer, new Object[] { " I ", "IGI", " I ", 'I', "ingotGold", 'G', new ItemStack(Blocks.field_150410_aZ) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:sanitychecker"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "WARP", 20, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), ItemsTC.sanityChecker, new Object[] { "BN ", "M N", "BN ", 'N', "nuggetBrass", 'B', new ItemStack(ItemsTC.brain), 'M', new ItemStack(ItemsTC.mirroredGlass) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:rechargepedestal"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "RECHARGEPEDESTAL", 100, new AspectList().add(Aspect.AIR, 1).add(Aspect.ORDER, 1), BlocksTC.rechargePedestal, new Object[] { " R ", "DID", "SSS", 'I', "ingotGold", 'D', "gemDiamond", 'R', new ItemStack(ItemsTC.visResonator), 'S', "stone" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:workbenchcharger"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "WORKBENCHCHARGER", 200, new AspectList().add(Aspect.AIR, 2).add(Aspect.ORDER, 2), new ItemStack(BlocksTC.arcaneWorkbenchCharger), new Object[] { " R ", "W W", "I I", 'I', "ingotIron", 'R', new ItemStack(ItemsTC.visResonator), 'W', new ItemStack(BlocksTC.plankGreatwood) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:wand_workbench"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASEAUROMANCY@2", 100, new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1), new ItemStack(BlocksTC.wandWorkbench), new Object[] { "ISI", "BRB", "GTG", 'S', new ItemStack((Block)BlocksTC.slabArcaneStone), 'T', new ItemStack(BlocksTC.tableStone), 'R', new ItemStack(ItemsTC.visResonator), 'B', new ItemStack(BlocksTC.stoneArcane), 'G', "ingotGold", 'I', "plateIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:caster_basic"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKAUROMANCY@2", 100, new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(ItemsTC.casterBasic), new Object[] { "III", "LRL", "LTL", 'T', new ItemStack(ItemsTC.thaumometer), 'R', new ItemStack(ItemsTC.visResonator), 'L', "leather", 'I', "ingotIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EnchantedFabric"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKINFUSION", 5, null, new ItemStack(ItemsTC.fabric), new Object[] { " S ", "SCS", " S ", 'S', "string", 'C', new ItemStack(Blocks.field_150325_L, 1, 32767) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:RobeChest"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKINFUSION", 100, null, new ItemStack(ItemsTC.clothChest, 1), new Object[] { "I I", "III", "III", 'I', new ItemStack(ItemsTC.fabric) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:RobeLegs"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKINFUSION", 100, null, new ItemStack(ItemsTC.clothLegs, 1), new Object[] { "III", "I I", "I I", 'I', new ItemStack(ItemsTC.fabric) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:RobeBoots"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKINFUSION", 100, null, new ItemStack(ItemsTC.clothBoots, 1), new Object[] { "I I", "I I", 'I', new ItemStack(ItemsTC.fabric) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Goggles"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "UNLOCKARTIFICE", 50, null, new ItemStack(ItemsTC.goggles), new Object[] { "LGL", "L L", "TGT", 'T', new ItemStack(ItemsTC.thaumometer), 'G', "ingotBrass", 'L', "leather" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:SealBlank"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "CONTROLSEALS", 20, new AspectList().add(Aspect.AIR, 1), new ItemStack(ItemsTC.seals, 3), new Object[] { new ItemStack(Items.field_151119_aD), new ItemStack(ItemsTC.tallow), "dyeRed", "nitor" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:modvision"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "GOLEMVISION", 50, new AspectList().add(Aspect.WATER, 1), new ItemStack(ItemsTC.modules, 1, 0), new Object[] { "B B", "E E", "PGP", 'B', new ItemStack(Items.field_151069_bo), 'E', new ItemStack(Items.field_151071_bq), 'P', "plateBrass", 'G', new ItemStack(ItemsTC.mechanismSimple) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:modaggression"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "SEALGUARD", 50, new AspectList().add(Aspect.FIRE, 1), new ItemStack(ItemsTC.modules, 1, 1), new Object[] { " R ", "RTR", "PGP", 'R', "paneGlass", 'T', new ItemStack(Items.field_151065_br), 'P', "plateBrass", 'G', new ItemStack(ItemsTC.mechanismSimple) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:mirrorglass"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "BASEARTIFICE", 50, new AspectList().add(Aspect.WATER, 1).add(Aspect.ORDER, 1), new ItemStack(ItemsTC.mirroredGlass), new Object[] { new ItemStack(ItemsTC.quicksilver), "paneGlass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:ArcaneSpa"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ARCANESPA", 50, new AspectList().add(Aspect.WATER, 1), new ItemStack(BlocksTC.spa), new Object[] { "QIQ", "SJS", "SPS", 'P', new ItemStack(ItemsTC.mechanismSimple), 'J', new ItemStack(BlocksTC.jarNormal), 'S', new ItemStack(BlocksTC.stoneArcane), 'Q', new ItemStack(Blocks.field_150371_ca), 'I', new ItemStack(Blocks.field_150411_aY) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Tube"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 10, null, new ItemStack(BlocksTC.tube, 8, 0), new Object[] { " Q ", "IGI", " B ", 'I', "plateIron", 'B', "nuggetBrass", 'G', "blockGlass", 'Q', "nuggetQuicksilver" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Resonator"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 50, null, new ItemStack(ItemsTC.resonator), new Object[] { "I I", "INI", " S ", 'I', "plateIron", 'N', Items.field_151128_bU, 'S', "stickWood" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:TubeValve"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 10, null, new ItemStack(BlocksTC.tubeValve), new Object[] { new ItemStack(BlocksTC.tube), new ItemStack(Blocks.field_150442_at) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:TubeFilter"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 10, null, new ItemStack(BlocksTC.tubeFilter), new Object[] { new ItemStack(BlocksTC.tube, 1, 0), new ItemStack(ItemsTC.filter) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:TubeRestrict"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 10, new AspectList().add(Aspect.EARTH, 1), new ItemStack(BlocksTC.tubeRestrict), new Object[] { new ItemStack(BlocksTC.tube) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:TubeOneway"), new ShapelessArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 10, new AspectList().add(Aspect.WATER, 1), new ItemStack(BlocksTC.tubeOneway), new Object[] { new ItemStack(BlocksTC.tube) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:TubeBuffer"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 25, null, new ItemStack(BlocksTC.tubeBuffer), new Object[] { "PVP", "TWT", "PRP", 'T', new ItemStack(BlocksTC.tube), 'V', new ItemStack(BlocksTC.tubeValve), 'W', "plateIron", 'R', new ItemStack(BlocksTC.tubeRestrict), 'P', new ItemStack(ItemsTC.phial) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:WardedJar"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "WARDEDJARS", 5, null, new ItemStack(BlocksTC.jarNormal), new Object[] { "GWG", "G G", "GGG", 'W', "slabWood", 'G', "paneGlass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:JarVoid"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "WARDEDJARS", 50, new AspectList().add(Aspect.ENTROPY, 1), new ItemStack(BlocksTC.jarVoid), new Object[] { "J", 'J', new ItemStack(BlocksTC.jarNormal) }));
        final ResourceLocation bannerGroup = new ResourceLocation("thaumcraft", "banners");
        int a = 0;
        for (final EnumDyeColor d : EnumDyeColor.values()) {
            final ItemStack banner = new ItemStack((Block)BlocksTC.banners.get(d));
            ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Banner" + d.func_176762_d().toLowerCase()), new ShapedArcaneRecipe(bannerGroup, "BASEINFUSION", 10, null, banner, new Object[] { "WS", "WS", "WB", 'W', new ItemStack(Blocks.field_150325_L, 1, 15 - a), 'S', "stickWood", 'B', "slabWood" }));
            ++a;
        }
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:PaveBarrier"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "PAVINGSTONES", 50, new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1), new ItemStack(BlocksTC.pavingStoneBarrier, 4), new Object[] { "SS", "SS", 'S', new ItemStack(BlocksTC.stoneArcaneBrick) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:PaveTravel"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "PAVINGSTONES", 50, new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1), new ItemStack(BlocksTC.pavingStoneTravel, 4), new Object[] { "SS", "SS", 'S', new ItemStack(BlocksTC.stoneArcaneBrick) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:ArcaneLamp"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ARCANELAMP", 50, new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1), new ItemStack(BlocksTC.lampArcane), new Object[] { " I ", "IAI", " I ", 'A', new ItemStack(BlocksTC.amberBlock), 'I', "plateIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Levitator"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "LEVITATOR", 35, new AspectList().add(Aspect.AIR, 1), new ItemStack(BlocksTC.levitator), new Object[] { "WIW", "BNB", "WGW", 'I', "plateThaumium", 'N', "nitor", 'W', "plankWood", 'B', "plateIron", 'G', new ItemStack(ItemsTC.mechanismSimple) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:RedstoneRelay"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "REDSTONERELAY", 10, new AspectList().add(Aspect.ORDER, 1), new ItemStack(BlocksTC.redstoneRelay), new Object[] { "   ", "TGT", "SSS", 'T', new ItemStack(Blocks.field_150429_aA), 'G', new ItemStack(ItemsTC.mechanismSimple), 'S', new ItemStack((Block)Blocks.field_150333_U) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:ArcaneEar"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ARCANEEAR", 15, new AspectList().add(Aspect.AIR, 1), new ItemStack(BlocksTC.arcaneEar), new Object[] { "P P", " G ", "WRW", 'W', "slabWood", 'R', Items.field_151137_ax, 'G', new ItemStack(ItemsTC.mechanismSimple), 'P', "plateBrass" }));
        shapelessOreDictRecipe("ArcaneEarToggle", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.arcaneEarToggle), new Object[] { new ItemStack(BlocksTC.arcaneEar), new ItemStack(Blocks.field_150442_at) });
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:InfusionMatrix"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSION@2", 150, new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(BlocksTC.infusionMatrix), new Object[] { "S S", " N ", "S S", 'S', new ItemStack(BlocksTC.stoneArcaneBrick), 'N', "nitor" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:MatrixMotion"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSIONBOOST", 500, new AspectList().add(Aspect.AIR, 1).add(Aspect.ORDER, 1), new ItemStack(BlocksTC.matrixSpeed), new Object[] { "SNS", "NGN", "SNS", 'S', new ItemStack(BlocksTC.stoneArcane), 'N', "nitor", 'G', new ItemStack(Blocks.field_150484_ah) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:MatrixCost"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSIONBOOST", 500, new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1).add(Aspect.ENTROPY, 1), new ItemStack(BlocksTC.matrixCost), new Object[] { "SAS", "AGA", "SAS", 'S', new ItemStack(BlocksTC.stoneArcane), 'A', new ItemStack(ItemsTC.alumentum), 'G', new ItemStack(Blocks.field_150484_ah) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:ArcanePedestal"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSION", 10, null, new ItemStack(BlocksTC.pedestalArcane), new Object[] { "SSS", " B ", "SSS", 'S', new ItemStack((Block)BlocksTC.slabArcaneStone), 'B', new ItemStack(BlocksTC.stoneArcane) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:AncientPedestal"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSIONANCIENT", 150, null, new ItemStack(BlocksTC.pedestalAncient), new Object[] { "SSS", " B ", "SSS", 'S', new ItemStack((Block)BlocksTC.slabAncient), 'B', new ItemStack(BlocksTC.stoneAncient) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EldritchPedestal"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "INFUSIONELDRITCH", 150, null, new ItemStack(BlocksTC.pedestalEldritch), new Object[] { "SSS", " B ", "SSS", 'S', new ItemStack((Block)BlocksTC.slabEldritch), 'B', new ItemStack(BlocksTC.stoneEldritchTile) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:FocusPouch"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "FOCUSPOUCH", 25, null, new ItemStack(ItemsTC.focusPouch), new Object[] { "LGL", "LBL", "LLL", 'B', new ItemStack(ItemsTC.baubles, 1, 2), 'L', "leather", 'G', Items.field_151043_k }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:dioptra"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "DIOPTRA", 50, new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1), new ItemStack(BlocksTC.dioptra), new Object[] { "APA", "IGI", "AAA", 'A', new ItemStack(BlocksTC.stoneArcane), 'G', new ItemStack(ItemsTC.thaumometer), 'P', new ItemStack(ItemsTC.visResonator), 'I', "plateIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:HungryChest"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "HUNGRYCHEST", 15, new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1), new ItemStack(BlocksTC.hungryChest), new Object[] { "WTW", "W W", "WWW", 'W', new ItemStack(BlocksTC.plankGreatwood), 'T', new ItemStack(Blocks.field_150415_aT) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Filter"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASEALCHEMY", 15, new AspectList().add(Aspect.WATER, 1), new ItemStack(ItemsTC.filter, 2, 0), new Object[] { "GWG", 'G', Items.field_151043_k, 'W', new ItemStack(BlocksTC.plankSilverwood) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:MorphicResonator"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASEALCHEMY", 50, new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1), new ItemStack(ItemsTC.morphicResonator), new Object[] { " G ", "BSB", " G ", 'G', "paneGlass", 'B', "plateBrass", 'S', new ItemStack(ItemsTC.nuggets, 1, 10) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Alembic"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIASMELTER", 50, new AspectList().add(Aspect.WATER, 1), new ItemStack(BlocksTC.alembic), new Object[] { "WFW", "SBS", "WFW", 'W', new ItemStack(BlocksTC.plankGreatwood), 'B', Items.field_151133_ar, 'F', new ItemStack(ItemsTC.filter), 'S', "plateBrass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EssentiaSmelter"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIASMELTER@2", 50, new AspectList().add(Aspect.FIRE, 1), new ItemStack(BlocksTC.smelterBasic), new Object[] { "BCB", "SFS", "SSS", 'C', new ItemStack(BlocksTC.crucible), 'F', new ItemStack(Blocks.field_150460_al), 'S', "cobblestone", 'B', "plateBrass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EssentiaSmelterThaumium"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIASMELTERTHAUMIUM", 250, new AspectList().add(Aspect.FIRE, 2), new ItemStack(BlocksTC.smelterThaumium), new Object[] { "BFB", "IGI", "III", 'F', new ItemStack(BlocksTC.smelterBasic), 'G', new ItemStack(BlocksTC.metalAlchemical), 'I', "plateThaumium", 'B', "plateBrass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EssentiaSmelterVoid"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIASMELTERVOID", 750, new AspectList().add(Aspect.FIRE, 3), new ItemStack(BlocksTC.smelterVoid), new Object[] { "BFB", "IGI", "III", 'F', new ItemStack(BlocksTC.smelterBasic), 'G', new ItemStack(BlocksTC.metalAlchemicalAdvanced), 'I', "plateVoid", 'B', "plateBrass" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:AlchemicalConstruct"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "TUBES", 75, new AspectList().add(Aspect.WATER, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(BlocksTC.metalAlchemical), new Object[] { "IVI", "TWT", "IVI", 'W', new ItemStack(BlocksTC.plankGreatwood), 'V', new ItemStack(BlocksTC.tubeValve), 'T', new ItemStack(BlocksTC.tube), 'I', "plateIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:AdvAlchemyConstruct"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIASMELTERVOID@1", 200, new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1), new ItemStack(BlocksTC.metalAlchemicalAdvanced), new Object[] { "VAV", "APA", "VAV", 'A', new ItemStack(BlocksTC.metalAlchemical), 'V', "plateVoid", 'P', Ingredient.func_193367_a(ItemsTC.primordialPearl) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:PotionSprayer"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "POTIONSPRAYER", 75, new AspectList().add(Aspect.WATER, 1).add(Aspect.FIRE, 1), new ItemStack(BlocksTC.potionSprayer), new Object[] { "BDB", "IAI", "ICI", 'B', "plateBrass", 'I', "plateIron", 'A', new ItemStack(Items.field_151067_bt), 'D', new ItemStack(Blocks.field_150367_z), 'C', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:SmelterAux"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "IMPROVEDSMELTING", 100, new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1), new ItemStack(BlocksTC.smelterAux), new Object[] { "WTW", "RGR", "IBI", 'W', new ItemStack(BlocksTC.plankGreatwood), 'B', new ItemStack(BlocksTC.bellows), 'R', "plateBrass", 'T', new ItemStack(BlocksTC.tubeFilter), 'I', "plateIron", 'G', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:SmelterVent"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "IMPROVEDSMELTING2", 150, new AspectList().add(Aspect.AIR, 1), new ItemStack(BlocksTC.smelterVent), new Object[] { "IBI", "MGF", "IBI", 'I', "plateIron", 'B', "plateBrass", 'F', new ItemStack(ItemsTC.filter), 'M', new ItemStack(ItemsTC.filter), 'G', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EssentiaTransportIn"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIATRANSPORT", 100, new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1), new ItemStack(BlocksTC.essentiaTransportInput), new Object[] { "   ", "BQB", "IGI", 'I', "plateIron", 'B', "plateBrass", 'Q', new ItemStack(Blocks.field_150367_z), 'G', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:EssentiaTransportOut"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ESSENTIATRANSPORT", 100, new AspectList().add(Aspect.AIR, 1).add(Aspect.WATER, 1), new ItemStack(BlocksTC.essentiaTransportOutput), new Object[] { "   ", "BQB", "IGI", 'I', "plateIron", 'B', "plateBrass", 'Q', new ItemStack((Block)Blocks.field_150438_bZ), 'G', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Bellows"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BELLOWS", 25, new AspectList().add(Aspect.AIR, 1), new ItemStack(BlocksTC.bellows), new Object[] { "WW ", "LLI", "WW ", 'W', "plankWood", 'I', "ingotIron", 'L', "leather" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:Centrifuge"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "CENTRIFUGE", 100, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(BlocksTC.centrifuge), new Object[] { " T ", "RCP", " T ", 'T', new ItemStack(BlocksTC.tube), 'P', new ItemStack(ItemsTC.mechanismSimple), 'R', new ItemStack(ItemsTC.morphicResonator), 'C', new ItemStack(BlocksTC.metalAlchemical) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:MnemonicMatrix"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "THAUMATORIUM", 50, new AspectList().add(Aspect.EARTH, 1).add(Aspect.ORDER, 1), new ItemStack(BlocksTC.brainBox), new Object[] { "IAI", "ABA", "IAI", 'B', new ItemStack(ItemsTC.mind, 1, 0), 'A', "gemAmber", 'I', "plateIron" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:MindClockwork"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "MINDCLOCKWORK@2", 25, new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1), new ItemStack(ItemsTC.mind, 1, 0), new Object[] { " P ", "PGP", "BCB", 'G', new ItemStack(ItemsTC.mechanismSimple), 'B', "plateBrass", 'P', "paneGlass", 'C', new ItemStack(Items.field_151132_bS) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:AutomatedCrossbow"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "BASICTURRET", 100, new AspectList().add(Aspect.AIR, 1), new ItemStack(ItemsTC.turretPlacer, 1, 0), new Object[] { "BGI", "WMW", "S S", 'G', new ItemStack(ItemsTC.mechanismSimple), 'I', "plateIron", 'S', "stickWood", 'M', new ItemStack(ItemsTC.mind), 'B', new ItemStack((Item)Items.field_151031_f), 'W', new ItemStack(BlocksTC.plankGreatwood) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:AdvancedCrossbow"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ADVANCEDTURRET", 150, new AspectList().add(Aspect.AIR, 2), new ItemStack(ItemsTC.turretPlacer, 1, 1), new Object[] { "PMP", "PTP", "   ", 'T', new ItemStack(ItemsTC.turretPlacer, 1, 0), 'P', "plateIron", 'M', new ItemStack(ItemsTC.mind, 1, 1) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:patterncrafter"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "ARCANEPATTERNCRAFTER", 50, new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1), new ItemStack(BlocksTC.patternCrafter), new Object[] { "VH ", "GCG", " W ", 'H', new ItemStack((Block)Blocks.field_150438_bZ), 'W', new ItemStack(BlocksTC.plankGreatwood), 'G', new ItemStack(ItemsTC.mechanismSimple), 'V', new ItemStack(ItemsTC.visResonator), 'C', "workbench" }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:GrappleGunTip"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "GRAPPLEGUN", 25, new AspectList().add(Aspect.EARTH, 1), new ItemStack(ItemsTC.grappleGunTip), new Object[] { "BRB", "RHR", "BRB", 'B', "plateBrass", 'R', new ItemStack(ItemsTC.nuggets, 1, 10), 'H', new ItemStack((Block)Blocks.field_150479_bC) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:GrappleGunSpool"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "GRAPPLEGUN", 25, new AspectList().add(Aspect.WATER, 1), new ItemStack(ItemsTC.grappleGunSpool), new Object[] { "SHS", "SGS", "SSS", 'G', new ItemStack(ItemsTC.mechanismSimple), 'S', "string", 'H', new ItemStack((Block)Blocks.field_150479_bC) }));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumcraft:GrappleGun"), new ShapedArcaneRecipe(ConfigRecipes.defaultGroup, "GRAPPLEGUN", 75, new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1), new ItemStack(ItemsTC.grappleGun), new Object[] { "  S", "TII", " BW", 'B', "plateBrass", 'I', "plateIron", 'T', new ItemStack(ItemsTC.grappleGunTip), 'W', "plankWood", 'S', new ItemStack(ItemsTC.grappleGunSpool) }));
    }
    
    public static void initializeInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:SealHarvest"), new InfusionRecipe("SEALHARVEST", GolemHelper.getSealStack("thaumcraft:harvest"), 0, new AspectList().add(Aspect.PLANT, 10).add(Aspect.SENSES, 10).add(Aspect.MAN, 10), new ItemStack(ItemsTC.seals), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(Items.field_151080_bb), new ItemStack(Items.field_151081_bc), new ItemStack(Items.field_151100_aR, 1, 3), new ItemStack(Items.field_151120_aE), new ItemStack((Block)Blocks.field_150434_aF) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:SealButcher"), new InfusionRecipe("SEALBUTCHER", GolemHelper.getSealStack("thaumcraft:butcher"), 0, new AspectList().add(Aspect.BEAST, 10).add(Aspect.SENSES, 10).add(Aspect.MAN, 10), GolemHelper.getSealStack("thaumcraft:guard"), new Object[] { "leather", new ItemStack(Blocks.field_150325_L, 1, 32767), new ItemStack(Items.field_179555_bs), new ItemStack(Items.field_151147_al), new ItemStack(Items.field_179561_bm), new ItemStack(Items.field_151082_bd) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:SealBreak"), new InfusionRecipe("SEALBREAK", GolemHelper.getSealStack("thaumcraft:breaker"), 1, new AspectList().add(Aspect.TOOL, 10).add(Aspect.ENTROPY, 10).add(Aspect.MAN, 10), new ItemStack(ItemsTC.seals), new Object[] { new ItemStack(Items.field_151006_E), new ItemStack(Items.field_151005_D), new ItemStack(Items.field_151011_C) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterAir"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalAir), 0, new AspectList().add(Aspect.AIR, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.AIR), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterFire"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalFire), 0, new AspectList().add(Aspect.FIRE, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.FIRE), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterWater"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalWater), 0, new AspectList().add(Aspect.WATER, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.WATER), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterEarth"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalEarth), 0, new AspectList().add(Aspect.EARTH, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.EARTH), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterOrder"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalOrder), 0, new AspectList().add(Aspect.ORDER, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.ORDER), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterEntropy"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalEntropy), 0, new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CrystalClusterFlux"), new InfusionRecipe("CRYSTALFARMER", new ItemStack(BlocksTC.crystalTaint), 4, new AspectList().add(Aspect.FLUX, 10).add(Aspect.CRYSTAL, 10).add(Aspect.TRAP, 5), ThaumcraftApiHelper.makeCrystal(Aspect.FLUX), new Object[] { new ItemStack(Items.field_151014_N), new ItemStack(ItemsTC.salisMundus) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:focus_2"), new InfusionRecipe("FOCUSADVANCED@1", new ItemStack(ItemsTC.focus2), 3, new AspectList().add(Aspect.MAGIC, 25).add(Aspect.ORDER, 50), new ItemStack(ItemsTC.focus1), new Object[] { new ItemStack(ItemsTC.quicksilver), "gemDiamond", new ItemStack(ItemsTC.quicksilver), new ItemStack(Items.field_151079_bi) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:focus_3"), new InfusionRecipe("FOCUSGREATER@1", new ItemStack(ItemsTC.focus3), 5, new AspectList().add(Aspect.MAGIC, 25).add(Aspect.ORDER, 50).add(Aspect.VOID, 100), new ItemStack(ItemsTC.focus2), new Object[] { new ItemStack(ItemsTC.quicksilver), Ingredient.func_193367_a(ItemsTC.primordialPearl), new ItemStack(ItemsTC.quicksilver), new ItemStack(Items.field_151156_bN) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:JarBrain"), new InfusionRecipe("JARBRAIN", new ItemStack(BlocksTC.jarBrain), 4, new AspectList().add(Aspect.MIND, 25).add(Aspect.SENSES, 25).add(Aspect.UNDEAD, 25), new ItemStack(BlocksTC.jarNormal), new Object[] { new ItemStack(ItemsTC.brain), new ItemStack(Items.field_151070_bp), new ItemStack(Items.field_151131_as), new ItemStack(Items.field_151070_bp) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:VisAmulet"), new InfusionRecipe("VISAMULET", new ItemStack(ItemsTC.amuletVis, 1, 1), 6, new AspectList().add(Aspect.AURA, 50).add(Aspect.ENERGY, 100).add(Aspect.VOID, 50), new ItemStack(ItemsTC.baubles, 1, 0), new Object[] { new ItemStack(ItemsTC.visResonator), ThaumcraftApiHelper.makeCrystal(Aspect.AIR), ThaumcraftApiHelper.makeCrystal(Aspect.FIRE), ThaumcraftApiHelper.makeCrystal(Aspect.WATER), ThaumcraftApiHelper.makeCrystal(Aspect.EARTH), ThaumcraftApiHelper.makeCrystal(Aspect.ORDER) }));
        final InfusionRunicAugmentRecipe ra = new InfusionRunicAugmentRecipe();
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:RunicArmor"), ra);
        for (int a = 0; a < 3; ++a) {
            final ItemStack in = new ItemStack(ItemsTC.baubles, 1, 1);
            if (a > 0) {
                in.func_77983_a("TC.RUNIC", (NBTBase)new NBTTagByte((byte)a));
            }
            ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:RunicArmorFake" + a), new InfusionRunicAugmentRecipe(in));
        }
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:Mirror"), new InfusionRecipe("MIRROR", new ItemStack(BlocksTC.mirror), 1, new AspectList().add(Aspect.MOTION, 25).add(Aspect.DARKNESS, 25).add(Aspect.EXCHANGE, 25), new ItemStack(ItemsTC.mirroredGlass), new Object[] { "ingotGold", "ingotGold", "ingotGold", new ItemStack(Items.field_151079_bi) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MirrorHand"), new InfusionRecipe("MIRRORHAND", new ItemStack(ItemsTC.handMirror), 5, new AspectList().add(Aspect.TOOL, 50).add(Aspect.MOTION, 50), new ItemStack(BlocksTC.mirror), new Object[] { "stickWood", new ItemStack(Items.field_151111_aL), new ItemStack((Item)Items.field_151148_bJ) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MirrorEssentia"), new InfusionRecipe("MIRRORESSENTIA", new ItemStack(BlocksTC.mirrorEssentia), 2, new AspectList().add(Aspect.MOTION, 25).add(Aspect.WATER, 25).add(Aspect.EXCHANGE, 25), new ItemStack(ItemsTC.mirroredGlass), new Object[] { "ingotIron", "ingotIron", "ingotIron", new ItemStack(Items.field_151079_bi) }));
        final ItemStack isEA = new ItemStack(ItemsTC.elementalAxe);
        EnumInfusionEnchantment.addInfusionEnchantment(isEA, EnumInfusionEnchantment.COLLECTOR, 1);
        EnumInfusionEnchantment.addInfusionEnchantment(isEA, EnumInfusionEnchantment.BURROWING, 1);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ElementalAxe"), new InfusionRecipe("ELEMENTALTOOLS", isEA, 1, new AspectList().add(Aspect.WATER, 60).add(Aspect.PLANT, 30), new ItemStack(ItemsTC.thaumiumAxe), new Object[] { ConfigItems.WATER_CRYSTAL, ConfigItems.WATER_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(BlocksTC.plankGreatwood) }));
        final ItemStack isEP = new ItemStack(ItemsTC.elementalPick);
        EnumInfusionEnchantment.addInfusionEnchantment(isEP, EnumInfusionEnchantment.REFINING, 1);
        EnumInfusionEnchantment.addInfusionEnchantment(isEP, EnumInfusionEnchantment.SOUNDING, 2);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ElementalPick"), new InfusionRecipe("ELEMENTALTOOLS", isEP, 1, new AspectList().add(Aspect.FIRE, 30).add(Aspect.METAL, 30).add(Aspect.SENSES, 30), new ItemStack(ItemsTC.thaumiumPick), new Object[] { ConfigItems.FIRE_CRYSTAL, ConfigItems.FIRE_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(BlocksTC.plankGreatwood) }));
        final ItemStack isESW = new ItemStack(ItemsTC.elementalSword);
        EnumInfusionEnchantment.addInfusionEnchantment(isESW, EnumInfusionEnchantment.ARCING, 2);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ElementalSword"), new InfusionRecipe("ELEMENTALTOOLS", isESW, 1, new AspectList().add(Aspect.AIR, 30).add(Aspect.MOTION, 30).add(Aspect.AVERSION, 30), new ItemStack(ItemsTC.thaumiumSword), new Object[] { ConfigItems.AIR_CRYSTAL, ConfigItems.AIR_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(BlocksTC.plankGreatwood) }));
        final ItemStack isES = new ItemStack(ItemsTC.elementalShovel);
        EnumInfusionEnchantment.addInfusionEnchantment(isES, EnumInfusionEnchantment.DESTRUCTIVE, 1);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ElementalShovel"), new InfusionRecipe("ELEMENTALTOOLS", isES, 1, new AspectList().add(Aspect.EARTH, 60).add(Aspect.CRAFT, 30), new ItemStack(ItemsTC.thaumiumShovel), new Object[] { ConfigItems.EARTH_CRYSTAL, ConfigItems.EARTH_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(BlocksTC.plankGreatwood) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ElementalHoe"), new InfusionRecipe("ELEMENTALTOOLS", new ItemStack(ItemsTC.elementalHoe), 1, new AspectList().add(Aspect.ORDER, 30).add(Aspect.PLANT, 30).add(Aspect.ENTROPY, 30), new ItemStack(ItemsTC.thaumiumHoe), new Object[] { ConfigItems.ORDER_CRYSTAL, ConfigItems.ENTROPY_CRYSTAL, new ItemStack(ItemsTC.nuggets, 1, 10), new ItemStack(BlocksTC.plankGreatwood) }));
        final InfusionEnchantmentRecipe IEBURROWING = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.BURROWING, new AspectList().add(Aspect.SENSES, 80).add(Aspect.EARTH, 150), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(Items.field_179556_br) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IEBURROWING"), IEBURROWING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IEBURROWINGFAKE"), new InfusionEnchantmentRecipe(IEBURROWING, new ItemStack(Items.field_151039_o)));
        final InfusionEnchantmentRecipe IECOLLECTOR = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.COLLECTOR, new AspectList().add(Aspect.DESIRE, 80).add(Aspect.WATER, 100), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(Items.field_151058_ca) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IECOLLECTOR"), IECOLLECTOR);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IECOLLECTORFAKE"), new InfusionEnchantmentRecipe(IECOLLECTOR, new ItemStack(Items.field_151049_t)));
        final InfusionEnchantmentRecipe IEDESTRUCTIVE = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.DESTRUCTIVE, new AspectList().add(Aspect.AVERSION, 200).add(Aspect.ENTROPY, 250), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(Blocks.field_150335_W) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IEDESTRUCTIVE"), IEDESTRUCTIVE);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IEDESTRUCTIVEFAKE"), new InfusionEnchantmentRecipe(IEDESTRUCTIVE, new ItemStack(Items.field_151050_s)));
        final InfusionEnchantmentRecipe IEREFINING = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.REFINING, new AspectList().add(Aspect.ORDER, 80).add(Aspect.EXCHANGE, 60), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(ItemsTC.salisMundus) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IEREFINING"), IEREFINING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IEREFININGFAKE"), new InfusionEnchantmentRecipe(IEREFINING, new ItemStack(Items.field_151035_b)));
        final InfusionEnchantmentRecipe IESOUNDING = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.SOUNDING, new AspectList().add(Aspect.SENSES, 40).add(Aspect.FIRE, 60), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack((Item)Items.field_151148_bJ) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IESOUNDING"), IESOUNDING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IESOUNDINGFAKE"), new InfusionEnchantmentRecipe(IESOUNDING, new ItemStack(Items.field_151005_D)));
        final InfusionEnchantmentRecipe IEARCING = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.ARCING, new AspectList().add(Aspect.ENERGY, 40).add(Aspect.AIR, 60), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(Blocks.field_150451_bX) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IEARCING"), IEARCING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IEARCINGFAKE"), new InfusionEnchantmentRecipe(IEARCING, new ItemStack(Items.field_151041_m)));
        final InfusionEnchantmentRecipe IEESSENCE = new InfusionEnchantmentRecipe(EnumInfusionEnchantment.ESSENCE, new AspectList().add(Aspect.BEAST, 40).add(Aspect.FLUX, 60), new Object[] { new ItemStack(Items.field_151134_bR), new ItemStack(ItemsTC.crystalEssence) });
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:IEESSENCE"), IEESSENCE);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:IEESSENCEFAKE"), new InfusionEnchantmentRecipe(IEESSENCE, new ItemStack(Items.field_151052_q)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:BootsTraveller"), new InfusionRecipe("BOOTSTRAVELLER", new ItemStack(ItemsTC.travellerBoots), 1, new AspectList().add(Aspect.FLIGHT, 100).add(Aspect.MOTION, 100), new ItemStack((Item)Items.field_151021_T), new Object[] { ConfigItems.AIR_CRYSTAL, ConfigItems.AIR_CRYSTAL, new ItemStack(ItemsTC.fabric), new ItemStack(ItemsTC.fabric), new ItemStack(Items.field_151008_G), new ItemStack(Items.field_151115_aP, 1, 32767) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MindBiothaumic"), new InfusionRecipe("MINDBIOTHAUMIC", new ItemStack(ItemsTC.mind, 1, 1), 4, new AspectList().add(Aspect.MIND, 50).add(Aspect.MECHANISM, 25), new ItemStack(ItemsTC.mind, 1, 0), new Object[] { new ItemStack(ItemsTC.brain), new ItemStack(ItemsTC.mechanismComplex) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ArcaneBore"), new InfusionRecipe("ARCANEBORE", new ItemStack(ItemsTC.turretPlacer, 1, 2), 4, new AspectList().add(Aspect.ENERGY, 25).add(Aspect.EARTH, 25).add(Aspect.MECHANISM, 100).add(Aspect.VOID, 25).add(Aspect.MOTION, 25), new ItemStack(ItemsTC.turretPlacer), new Object[] { new ItemStack(BlocksTC.plankGreatwood), new ItemStack(BlocksTC.plankGreatwood), new ItemStack(ItemsTC.mechanismComplex), "plateBrass", new ItemStack(Items.field_151046_w), new ItemStack(Items.field_151047_v), new ItemStack(ItemsTC.morphicResonator), new ItemStack(ItemsTC.nuggets, 1, 10) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:LampGrowth"), new InfusionRecipe("LAMPGROWTH", new ItemStack(BlocksTC.lampGrowth), 4, new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIGHT, 15).add(Aspect.LIFE, 15).add(Aspect.TOOL, 15), new ItemStack(BlocksTC.lampArcane), new Object[] { new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151100_aR, 1, 15), ConfigItems.EARTH_CRYSTAL, new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151100_aR, 1, 15), ConfigItems.EARTH_CRYSTAL }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:LampFertility"), new InfusionRecipe("LAMPFERTILITY", new ItemStack(BlocksTC.lampFertility), 4, new AspectList().add(Aspect.BEAST, 20).add(Aspect.LIGHT, 15).add(Aspect.LIFE, 15).add(Aspect.DESIRE, 15), new ItemStack(BlocksTC.lampArcane), new Object[] { new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151015_O), ConfigItems.FIRE_CRYSTAL, new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151172_bF), ConfigItems.FIRE_CRYSTAL }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ThaumiumFortressHelm"), new InfusionRecipe("ARMORFORTRESS", new ItemStack(ItemsTC.fortressHelm), 3, new AspectList().add(Aspect.METAL, 50).add(Aspect.PROTECT, 20).add(Aspect.ENERGY, 25), new ItemStack(ItemsTC.thaumiumHelm), new Object[] { "plateThaumium", "plateThaumium", new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151166_bC) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ThaumiumFortressChest"), new InfusionRecipe("ARMORFORTRESS", new ItemStack(ItemsTC.fortressChest), 3, new AspectList().add(Aspect.METAL, 50).add(Aspect.PROTECT, 30).add(Aspect.ENERGY, 25), new ItemStack(ItemsTC.thaumiumChest), new Object[] { "plateThaumium", "plateThaumium", "plateThaumium", "plateThaumium", new ItemStack(Items.field_151043_k), "leather" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:ThaumiumFortressLegs"), new InfusionRecipe("ARMORFORTRESS", new ItemStack(ItemsTC.fortressLegs), 3, new AspectList().add(Aspect.METAL, 50).add(Aspect.PROTECT, 25).add(Aspect.ENERGY, 25), new ItemStack(ItemsTC.thaumiumLegs), new Object[] { "plateThaumium", "plateThaumium", "plateThaumium", new ItemStack(Items.field_151043_k), "leather" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:HelmGoggles"), new InfusionRecipe("FORTRESSMASK", new Object[] { "goggles", new NBTTagByte((byte)1) }, 5, new AspectList().add(Aspect.SENSES, 40).add(Aspect.AURA, 20).add(Aspect.PROTECT, 20), new ItemStack(ItemsTC.fortressHelm, 1, 32767), new Object[] { new ItemStack(Items.field_151123_aH), new ItemStack(ItemsTC.goggles, 1, 32767) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MaskGrinningDevil"), new InfusionRecipe("FORTRESSMASK", new Object[] { "mask", new NBTTagInt(0) }, 8, new AspectList().add(Aspect.MIND, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20), new ItemStack(ItemsTC.fortressHelm, 1, 32767), new Object[] { new ItemStack(Items.field_151100_aR, 1, 0), "plateIron", "leather", new ItemStack(BlocksTC.shimmerleaf), new ItemStack(ItemsTC.brain), "plateIron" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MaskAngryGhost"), new InfusionRecipe("FORTRESSMASK", new Object[] { "mask", new NBTTagInt(1) }, 8, new AspectList().add(Aspect.ENTROPY, 80).add(Aspect.DEATH, 80).add(Aspect.PROTECT, 20), new ItemStack(ItemsTC.fortressHelm, 1, 32767), new Object[] { new ItemStack(Items.field_151100_aR, 1, 15), "plateIron", "leather", new ItemStack(Items.field_151170_bI), new ItemStack(Items.field_151144_bL, 1, 1), "plateIron" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:MaskSippingFiend"), new InfusionRecipe("FORTRESSMASK", new Object[] { "mask", new NBTTagInt(2) }, 8, new AspectList().add(Aspect.UNDEAD, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20), new ItemStack(ItemsTC.fortressHelm, 1, 32767), new Object[] { new ItemStack(Items.field_151100_aR, 1, 1), "plateIron", "leather", new ItemStack(Items.field_151073_bk), new ItemStack(Items.field_151117_aB), "plateIron" }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:VerdantHeart"), new InfusionRecipe("VERDANTCHARMS", new ItemStack(ItemsTC.charmVerdant), 5, new AspectList().add(Aspect.LIFE, 60).add(Aspect.ORDER, 30).add(Aspect.PLANT, 60), new ItemStack(ItemsTC.baubles, 1, 4), new Object[] { new ItemStack(ItemsTC.nuggets, 1, 10), ThaumcraftApiHelper.makeCrystal(Aspect.LIFE), new ItemStack(Items.field_151117_aB), ThaumcraftApiHelper.makeCrystal(Aspect.PLANT) }));
        final ItemStack pis1 = new ItemStack((Item)Items.field_151068_bn);
        pis1.func_77983_a("Potion", (NBTBase)new NBTTagString("minecraft:strong_healing"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:VerdantHeartLife"), new InfusionRecipe("VERDANTCHARMS", new Object[] { "type", new NBTTagByte((byte)1) }, 5, new AspectList().add(Aspect.LIFE, 80).add(Aspect.MAN, 80), new ItemStack(ItemsTC.charmVerdant), new Object[] { new ItemStack(Items.field_151153_ao), ThaumcraftApiHelper.makeCrystal(Aspect.LIFE), pis1, ThaumcraftApiHelper.makeCrystal(Aspect.MAN) }));
        final ItemStack pis2 = new ItemStack((Item)Items.field_151068_bn);
        pis2.func_77983_a("Potion", (NBTBase)new NBTTagString("minecraft:strong_regeneration"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:VerdantHeartSustain"), new InfusionRecipe("VERDANTCHARMS", new Object[] { "type", new NBTTagByte((byte)2) }, 5, new AspectList().add(Aspect.DESIRE, 80).add(Aspect.AIR, 80), new ItemStack(ItemsTC.charmVerdant), new Object[] { new ItemStack(ItemsTC.tripleMeatTreat), ThaumcraftApiHelper.makeCrystal(Aspect.DESIRE), pis2, ThaumcraftApiHelper.makeCrystal(Aspect.AIR) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CLOUDRING"), new InfusionRecipe("CLOUDRING", new ItemStack(ItemsTC.ringCloud), 1, new AspectList().add(Aspect.AIR, 50), new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { ConfigItems.AIR_CRYSTAL, new ItemStack(Items.field_151008_G) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CuriosityBand"), new InfusionRecipe("CURIOSITYBAND", new ItemStack(ItemsTC.bandCuriosity), 5, new AspectList().add(Aspect.MIND, 150).add(Aspect.VOID, 50).add(Aspect.TRAP, 100), new ItemStack(ItemsTC.baubles, 1, 6), new Object[] { new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151099_bA), new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151099_bA), new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151099_bA), new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151099_bA) }));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumcraft:CHARMUNDYING"), new InfusionRecipe("CHARMUNDYING", new ItemStack(ItemsTC.charmUndying), 2, new AspectList().add(Aspect.LIFE, 25), new ItemStack(Items.field_190929_cY), new Object[] { "plateBrass" }));
    }
    
    public static void initializeNormalRecipes(final IForgeRegistry<IRecipe> iForgeRegistry) {
        final ResourceLocation brassGroup = new ResourceLocation("thaumcraft", "brass_stuff");
        final ResourceLocation thaumiumGroup = new ResourceLocation("thaumcraft", "thaumium_stuff");
        final ResourceLocation voidGroup = new ResourceLocation("thaumcraft", "void_stuff");
        final ResourceLocation baublesGroup = new ResourceLocation("thaumcraft", "baubles_stuff");
        iForgeRegistry.register(new RecipesRobeArmorDyes().setRegistryName("robedye"));
        iForgeRegistry.register(new RecipesVoidRobeArmorDyes().setRegistryName("voidarmordye"));
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "thaumiumtonuggets"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.nuggets, 9, 6), new Object[] { "#", '#', new ItemStack(ItemsTC.ingots, 1, 0) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "voidtonuggets"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.nuggets, 9, 7), new Object[] { "#", '#', new ItemStack(ItemsTC.ingots, 1, 1) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "brasstonuggets"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.nuggets, 9, 8), new Object[] { "#", '#', new ItemStack(ItemsTC.ingots, 1, 2) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "quartztonuggets"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.nuggets, 9, 9), new Object[] { "#", '#', new ItemStack(Items.field_151128_bU) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "quicksilvertonuggets"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.nuggets, 9, 5), new Object[] { "#", '#', new ItemStack(ItemsTC.quicksilver) });
        oreDictRecipe("nuggetstothaumium", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.ingots, 1, 0), new Object[] { "###", "###", "###", '#', "nuggetThaumium" });
        oreDictRecipe("nuggetstovoid", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.ingots, 1, 1), new Object[] { "###", "###", "###", '#', "nuggetVoid" });
        oreDictRecipe("nuggetstobrass", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.ingots, 1, 2), new Object[] { "###", "###", "###", '#', "nuggetBrass" });
        oreDictRecipe("nuggetstoquicksilver", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.quicksilver), new Object[] { "###", "###", "###", '#', "nuggetQuicksilver" });
        oreDictRecipe("thaumiumingotstoblock", thaumiumGroup, new ItemStack(BlocksTC.metalBlockThaumium), new Object[] { "###", "###", "###", '#', new ItemStack(ItemsTC.ingots, 1, 0) });
        oreDictRecipe("thaumiumblocktoingots", thaumiumGroup, new ItemStack(ItemsTC.ingots, 9, 0), new Object[] { "#", '#', new ItemStack(BlocksTC.metalBlockThaumium) });
        oreDictRecipe("voidingotstoblock", voidGroup, new ItemStack(BlocksTC.metalBlockVoid), new Object[] { "###", "###", "###", '#', new ItemStack(ItemsTC.ingots, 1, 1) });
        oreDictRecipe("voidblocktoingots", voidGroup, new ItemStack(ItemsTC.ingots, 9, 1), new Object[] { "#", '#', new ItemStack(BlocksTC.metalBlockVoid) });
        oreDictRecipe("brassingotstoblock", brassGroup, new ItemStack(BlocksTC.metalBlockBrass), new Object[] { "###", "###", "###", '#', new ItemStack(ItemsTC.ingots, 1, 2) });
        oreDictRecipe("brassblocktoingots", brassGroup, new ItemStack(ItemsTC.ingots, 9, 2), new Object[] { "#", '#', new ItemStack(BlocksTC.metalBlockBrass) });
        oreDictRecipe("fleshtoblock", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.fleshBlock), new Object[] { "###", "###", "###", '#', Items.field_151078_bh });
        oreDictRecipe("blocktoflesh", ConfigRecipes.defaultGroup, new ItemStack(Items.field_151078_bh, 9, 0), new Object[] { "#", '#', BlocksTC.fleshBlock });
        oreDictRecipe("ambertoblock", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.amberBlock), new Object[] { "###", "###", "###", '#', "gemAmber" });
        oreDictRecipe("amberblocktobrick", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.amberBrick), new Object[] { "##", "##", '#', new ItemStack(BlocksTC.amberBlock) });
        oreDictRecipe("amberbricktoblock", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.amberBlock, 4), new Object[] { "##", "##", '#', new ItemStack(BlocksTC.amberBrick) });
        oreDictRecipe("amberblocktoamber", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.amber, 9), new Object[] { "#", '#', new ItemStack(BlocksTC.amberBlock) });
        oreDictRecipe("ironplate", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.plate, 3, 1), new Object[] { "BBB", 'B', "ingotIron" });
        oreDictRecipe("brassplate", brassGroup, new ItemStack(ItemsTC.plate, 3, 0), new Object[] { "BBB", 'B', "ingotBrass" });
        oreDictRecipe("thaumiumplate", thaumiumGroup, new ItemStack(ItemsTC.plate, 3, 2), new Object[] { "BBB", 'B', "ingotThaumium" });
        oreDictRecipe("thaumiumhelm", thaumiumGroup, new ItemStack(ItemsTC.thaumiumHelm, 1), new Object[] { "III", "I I", 'I', "ingotThaumium" });
        oreDictRecipe("thaumiumchest", thaumiumGroup, new ItemStack(ItemsTC.thaumiumChest, 1), new Object[] { "I I", "III", "III", 'I', "ingotThaumium" });
        oreDictRecipe("thaumiumlegs", thaumiumGroup, new ItemStack(ItemsTC.thaumiumLegs, 1), new Object[] { "III", "I I", "I I", 'I', "ingotThaumium" });
        oreDictRecipe("thaumiumboots", thaumiumGroup, new ItemStack(ItemsTC.thaumiumBoots, 1), new Object[] { "I I", "I I", 'I', "ingotThaumium" });
        oreDictRecipe("thaumiumshovel", thaumiumGroup, new ItemStack(ItemsTC.thaumiumShovel, 1), new Object[] { "I", "S", "S", 'I', "ingotThaumium", 'S', "stickWood" });
        oreDictRecipe("thaumiumpick", thaumiumGroup, new ItemStack(ItemsTC.thaumiumPick, 1), new Object[] { "III", " S ", " S ", 'I', "ingotThaumium", 'S', "stickWood" });
        oreDictRecipe("thaumiumaxe", thaumiumGroup, new ItemStack(ItemsTC.thaumiumAxe, 1), new Object[] { "II", "SI", "S ", 'I', "ingotThaumium", 'S', "stickWood" });
        oreDictRecipe("thaumiumhoe", thaumiumGroup, new ItemStack(ItemsTC.thaumiumHoe, 1), new Object[] { "II", "S ", "S ", 'I', "ingotThaumium", 'S', "stickWood" });
        oreDictRecipe("thaumiumsword", thaumiumGroup, new ItemStack(ItemsTC.thaumiumSword, 1), new Object[] { "I", "I", "S", 'I', "ingotThaumium", 'S', "stickWood" });
        oreDictRecipe("voidplate", voidGroup, new ItemStack(ItemsTC.plate, 3, 3), new Object[] { "BBB", 'B', "ingotVoid" });
        oreDictRecipe("voidhelm", voidGroup, new ItemStack(ItemsTC.voidHelm, 1), new Object[] { "III", "I I", 'I', "ingotVoid" });
        oreDictRecipe("voidchest", voidGroup, new ItemStack(ItemsTC.voidChest, 1), new Object[] { "I I", "III", "III", 'I', "ingotVoid" });
        oreDictRecipe("voidlegs", voidGroup, new ItemStack(ItemsTC.voidLegs, 1), new Object[] { "III", "I I", "I I", 'I', "ingotVoid" });
        oreDictRecipe("voidboots", voidGroup, new ItemStack(ItemsTC.voidBoots, 1), new Object[] { "I I", "I I", 'I', "ingotVoid" });
        oreDictRecipe("voidshovel", voidGroup, new ItemStack(ItemsTC.voidShovel, 1), new Object[] { "I", "S", "S", 'I', "ingotVoid", 'S', "stickWood" });
        oreDictRecipe("voidpick", voidGroup, new ItemStack(ItemsTC.voidPick, 1), new Object[] { "III", " S ", " S ", 'I', "ingotVoid", 'S', "stickWood" });
        oreDictRecipe("voidaxe", voidGroup, new ItemStack(ItemsTC.voidAxe, 1), new Object[] { "II", "SI", "S ", 'I', "ingotVoid", 'S', "stickWood" });
        oreDictRecipe("voidhoe", voidGroup, new ItemStack(ItemsTC.voidHoe, 1), new Object[] { "II", "S ", "S ", 'I', "ingotVoid", 'S', "stickWood" });
        oreDictRecipe("voidsword", voidGroup, new ItemStack(ItemsTC.voidSword, 1), new Object[] { "I", "I", "S", 'I', "ingotVoid", 'S', "stickWood" });
        oreDictRecipe("babuleamulet", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 0), new Object[] { " S ", "S S", " I ", 'S', "string", 'I', "ingotBrass" });
        oreDictRecipe("babulering", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 1), new Object[] { "NNN", "N N", "NNN", 'N', "nuggetBrass" });
        oreDictRecipe("babulegirdle", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 2), new Object[] { " L ", "L L", " I ", 'L', "leather", 'I', "ingotBrass" });
        oreDictRecipe("babuleamuletfancy", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 4), new Object[] { " S ", "SGS", " I ", 'S', "string", 'G', "gemDiamond", 'I', "ingotGold" });
        oreDictRecipe("babuleringfancy", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 5), new Object[] { "NGN", "N N", "NNN", 'G', "gemDiamond", 'N', "nuggetGold" });
        oreDictRecipe("babulegirdlefancy", baublesGroup, new ItemStack(ItemsTC.baubles, 1, 6), new Object[] { " L ", "LGL", " I ", 'L', "leather", 'G', "gemDiamond", 'I', "ingotGold" });
        iForgeRegistry.register(new RecipeTripleMeatTreat().setRegistryName("triplemeattreat"));
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:triplemeattreatfake"), new ShapelessOreRecipe(ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.tripleMeatTreat), new Object[] { "nuggetMeat", "nuggetMeat", "nuggetMeat", new ItemStack(Items.field_151102_aT) }));
        iForgeRegistry.register(new RecipeMagicDust().setRegistryName("salismundus"));
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("thaumcraft:salismundus"), new ShapelessOreRecipe(ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.salisMundus), new Object[] { Items.field_151145_ak, Items.field_151054_z, Items.field_151137_ax, new ItemStack(ItemsTC.crystalEssence, 1, 32767), new ItemStack(ItemsTC.crystalEssence, 1, 32767), new ItemStack(ItemsTC.crystalEssence, 1, 32767) }));
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "shimmerleaftoquicksilver"), ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.quicksilver), new Object[] { "#", '#', BlocksTC.shimmerleaf });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "cinderpearltoblazepowder"), ConfigRecipes.defaultGroup, new ItemStack(Items.field_151065_br), new Object[] { "#", '#', BlocksTC.cinderpearl });
        final ResourceLocation labelsGroup = new ResourceLocation("thaumcraft", "jarlabels");
        shapelessOreDictRecipe("JarLabel", labelsGroup, new ItemStack(ItemsTC.label, 4, 0), new Object[] { "dyeBlack", "slimeball", Items.field_151121_aF, Items.field_151121_aF, Items.field_151121_aF, Items.field_151121_aF });
        final int count = 0;
        for (final Aspect aspect : Aspect.aspects.values()) {
            final ItemStack output = new ItemStack(ItemsTC.label, 1, 1);
            ((IEssentiaContainerItem)output.func_77973_b()).setAspects(output, new AspectList().add(aspect, 1));
            shapelessOreDictRecipe("label_" + aspect.getTag(), labelsGroup, output, new Object[] { new ItemStack(ItemsTC.label), ItemPhial.makeFilledPhial(aspect) });
        }
        shapelessOreDictRecipe("JarLabelNull", labelsGroup, new ItemStack(ItemsTC.label), new Object[] { new ItemStack(ItemsTC.label, 1, 1) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "PlankGreatwood"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.plankGreatwood, 4), new Object[] { "W", 'W', new ItemStack(BlocksTC.logGreatwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "PlankSilverwood"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.plankSilverwood, 4), new Object[] { "W", 'W', new ItemStack(BlocksTC.logSilverwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "StairsGreatwood"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stairsGreatwood, 4, 0), new Object[] { "K  ", "KK ", "KKK", 'K', new ItemStack(BlocksTC.plankGreatwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "StairsSilverwood"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stairsSilverwood, 4, 0), new Object[] { "K  ", "KK ", "KKK", 'K', new ItemStack(BlocksTC.plankSilverwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "StairsArcane"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stairsArcane, 4, 0), new Object[] { "K  ", "KK ", "KKK", 'K', new ItemStack(BlocksTC.stoneArcane) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "StairsArcaneBrick"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stairsArcaneBrick, 4, 0), new Object[] { "K  ", "KK ", "KKK", 'K', new ItemStack(BlocksTC.stoneArcaneBrick) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "StairsAncient"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stairsAncient, 4, 0), new Object[] { "K  ", "KK ", "KKK", 'K', new ItemStack(BlocksTC.stoneAncient) });
        oreDictRecipe("StoneArcane", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stoneArcane, 9), new Object[] { "KKK", "KCK", "KKK", 'K', "stone", 'C', new ItemStack(ItemsTC.crystalEssence) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "BrickArcane"), ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.stoneArcaneBrick, 4), new Object[] { "KK", "KK", 'K', new ItemStack(BlocksTC.stoneArcane) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabGreatwood"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabGreatwood, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.plankGreatwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabSilverwood"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabSilverwood, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.plankSilverwood) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabArcaneStone"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabArcaneStone, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.stoneArcane) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabArcaneBrick"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabArcaneBrick, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.stoneArcaneBrick) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabAncient"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabAncient, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.stoneAncient) });
        GameRegistry.addShapedRecipe(new ResourceLocation("thaumcraft", "SlabEldritch"), ConfigRecipes.defaultGroup, new ItemStack((Block)BlocksTC.slabEldritch, 6), new Object[] { "KKK", 'K', new ItemStack(BlocksTC.stoneEldritchTile) });
        oreDictRecipe("phail", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.phial, 8, 0), new Object[] { " C ", "G G", " G ", 'G', "blockGlass", 'C', Items.field_151119_aD });
        oreDictRecipe("tablewood", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.tableWood), new Object[] { "SSS", "W W", 'S', "slabWood", 'W', "plankWood" });
        oreDictRecipe("tablestone", ConfigRecipes.defaultGroup, new ItemStack(BlocksTC.tableStone), new Object[] { "SSS", "W W", 'S', new ItemStack((Block)Blocks.field_150333_U), 'W', "stone" });
        final ResourceLocation inkwellGroup = new ResourceLocation("thaumcraft", "inkwell");
        shapelessOreDictRecipe("scribingtoolscraft1", inkwellGroup, new ItemStack(ItemsTC.scribingTools), new Object[] { new ItemStack(ItemsTC.phial, 1, 0), Items.field_151008_G, "dyeBlack" });
        shapelessOreDictRecipe("scribingtoolscraft2", inkwellGroup, new ItemStack(ItemsTC.scribingTools), new Object[] { Items.field_151069_bo, Items.field_151008_G, "dyeBlack" });
        shapelessOreDictRecipe("scribingtoolsrefill", inkwellGroup, new ItemStack(ItemsTC.scribingTools), new Object[] { new ItemStack(ItemsTC.scribingTools, 1, 32767), "dyeBlack" });
        oreDictRecipe("GolemBell", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.golemBell), new Object[] { " QQ", " QQ", "S  ", 'S', "stickWood", 'Q', "gemQuartz" });
        final ResourceLocation candlesGroup = new ResourceLocation("thaumcraft", "tallowcandles");
        oreDictRecipe("TallowCandle", candlesGroup, new ItemStack((Block)BlocksTC.candles.get(EnumDyeColor.WHITE), 3), new Object[] { " S ", " T ", " T ", 'S', "string", 'T', new ItemStack(ItemsTC.tallow) });
        final IRecipe[] trs = new IRecipe[16];
        int a = 0;
        for (final EnumDyeColor d : EnumDyeColor.values()) {
            trs[a] = shapelessOreDictRecipe("TallowCandle" + d.func_176762_d().toLowerCase(), candlesGroup, new ItemStack((Block)BlocksTC.candles.get(d)), new Object[] { ConfigAspects.dyes[15 - a], ingredientsFromBlocks((Block[])BlocksTC.candles.values().toArray(new Block[0])) });
            ++a;
        }
        oreDictRecipe("BrassBrace", ConfigRecipes.defaultGroup, new ItemStack(ItemsTC.jarBrace, 2), new Object[] { "NSN", "S S", "NSN", 'N', "nuggetBrass", 'S', "stickWood" });
    }
    
    public static Ingredient ingredientsFromBlocks(final Block... blocks) {
        final ItemStack[] aitemstack = new ItemStack[blocks.length];
        for (int i = 0; i < blocks.length; ++i) {
            aitemstack[i] = new ItemStack(blocks[i]);
        }
        return Ingredient.func_193369_a(aitemstack);
    }
    
    public static void initializeSmelting() {
        GameRegistry.addSmelting(BlocksTC.oreCinnabar, new ItemStack(ItemsTC.quicksilver), 1.0f);
        GameRegistry.addSmelting(BlocksTC.oreAmber, new ItemStack(ItemsTC.amber), 1.0f);
        GameRegistry.addSmelting(BlocksTC.oreQuartz, new ItemStack(Items.field_151128_bU), 1.0f);
        GameRegistry.addSmelting(BlocksTC.logGreatwood, new ItemStack(Items.field_151044_h, 1, 1), 0.5f);
        GameRegistry.addSmelting(BlocksTC.logSilverwood, new ItemStack(Items.field_151044_h, 1, 1), 0.5f);
        GameRegistry.addSmelting(new ItemStack(ItemsTC.clusters, 1, 0), new ItemStack(Items.field_151042_j, 2, 0), 1.0f);
        GameRegistry.addSmelting(new ItemStack(ItemsTC.clusters, 1, 1), new ItemStack(Items.field_151043_k, 2, 0), 1.0f);
        GameRegistry.addSmelting(new ItemStack(ItemsTC.clusters, 1, 6), new ItemStack(ItemsTC.quicksilver, 2, 0), 1.0f);
        GameRegistry.addSmelting(new ItemStack(ItemsTC.clusters, 1, 7), new ItemStack(Items.field_151128_bU, 2, 0), 1.0f);
        ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(Items.field_151074_bl));
        ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(Items.field_191525_da));
        ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(ItemsTC.nuggets, 1, 5));
        ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(ItemsTC.nuggets, 1, 1));
        ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(ItemsTC.nuggets, 1, 2));
        ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(ItemsTC.nuggets, 1, 3));
        ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(ItemsTC.nuggets, 1, 4));
        ThaumcraftApi.addSmeltingBonus("oreQuartz", new ItemStack(ItemsTC.nuggets, 1, 9));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 0), new ItemStack(Items.field_191525_da));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 1), new ItemStack(Items.field_151074_bl));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 6), new ItemStack(ItemsTC.nuggets, 1, 5));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 2), new ItemStack(ItemsTC.nuggets, 1, 1));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 3), new ItemStack(ItemsTC.nuggets, 1, 2));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 4), new ItemStack(ItemsTC.nuggets, 1, 3));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 5), new ItemStack(ItemsTC.nuggets, 1, 4));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 7), new ItemStack(ItemsTC.nuggets, 1, 9));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_151082_bd), new ItemStack(ItemsTC.chunks, 1, 0));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_151076_bf), new ItemStack(ItemsTC.chunks, 1, 1));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_151147_al), new ItemStack(ItemsTC.chunks, 1, 2));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_151115_aP, 1, 32767), new ItemStack(ItemsTC.chunks, 1, 3));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_179558_bo), new ItemStack(ItemsTC.chunks, 1, 4));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(Items.field_179561_bm), new ItemStack(ItemsTC.chunks, 1, 5));
        ThaumcraftApi.addSmeltingBonus("oreDiamond", new ItemStack(ItemsTC.nuggets, 1, 10), 0.025f);
        ThaumcraftApi.addSmeltingBonus("oreRedstone", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreLapis", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreEmerald", new ItemStack(ItemsTC.nuggets, 1, 10), 0.025f);
        ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(ItemsTC.nuggets, 1, 10), 0.02f);
        ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(ItemsTC.nuggets, 1, 10), 0.025f);
        ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(ItemsTC.nuggets, 1, 10), 0.02f);
        ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus("oreQuartz", new ItemStack(ItemsTC.nuggets, 1, 10), 0.01f);
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ItemsTC.clusters, 1, 32767), new ItemStack(ItemsTC.nuggets, 1, 10), 0.02f);
    }
    
    static IRecipe oreDictRecipe(final String name, final ResourceLocation optionalGroup, final ItemStack res, final Object[] params) {
        final IRecipe rec = (IRecipe)new ShapedOreRecipe(optionalGroup, res, params);
        rec.setRegistryName(new ResourceLocation("thaumcraft", name));
        GameData.register_impl((IForgeRegistryEntry)rec);
        return rec;
    }
    
    static IRecipe shapelessOreDictRecipe(final String name, final ResourceLocation optionalGroup, final ItemStack res, final Object[] params) {
        final IRecipe rec = (IRecipe)new ShapelessOreRecipe(optionalGroup, res, params);
        rec.setRegistryName(new ResourceLocation("thaumcraft", name));
        GameData.register_impl((IForgeRegistryEntry)rec);
        return rec;
    }
    
    public static void postAspects() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_gunpowder"), new CrucibleRecipe("HEDGEALCHEMY@2", new ItemStack(Items.field_151016_H, 2, 0), new ItemStack(Items.field_151016_H), new AspectList(new ItemStack(Items.field_151016_H))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_slime"), new CrucibleRecipe("HEDGEALCHEMY@2", new ItemStack(Items.field_151123_aH, 2, 0), new ItemStack(Items.field_151123_aH), new AspectList(new ItemStack(Items.field_151123_aH))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_glowstone"), new CrucibleRecipe("HEDGEALCHEMY@2", new ItemStack(Items.field_151114_aO, 2, 0), "dustGlowstone", new AspectList(new ItemStack(Items.field_151114_aO))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_dye"), new CrucibleRecipe("HEDGEALCHEMY@2", new ItemStack(Items.field_151100_aR, 2, 0), new ItemStack(Items.field_151100_aR, 1, 0), new AspectList(new ItemStack(Items.field_151100_aR))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_clay"), new CrucibleRecipe("HEDGEALCHEMY@3", new ItemStack(Items.field_151119_aD, 1, 0), new ItemStack(Blocks.field_150346_d), new AspectList(new ItemStack(Items.field_151119_aD, 1, 0)).remove(new AspectList(new ItemStack(Blocks.field_150346_d)))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_string"), new CrucibleRecipe("HEDGEALCHEMY@3", new ItemStack(Items.field_151007_F), new ItemStack(Items.field_151015_O), new AspectList(new ItemStack(Items.field_151007_F)).remove(new AspectList(new ItemStack(Items.field_151015_O)))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_web"), new CrucibleRecipe("HEDGEALCHEMY@3", new ItemStack(Blocks.field_150321_G), new ItemStack(Items.field_151007_F), new AspectList(new ItemStack(Blocks.field_150321_G)).remove(new AspectList(new ItemStack(Items.field_151007_F)))));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumcraft:hedge_lava"), new CrucibleRecipe("HEDGEALCHEMY@3", new ItemStack(Items.field_151129_at), new ItemStack(Items.field_151133_ar), new AspectList().add(Aspect.FIRE, 15).add(Aspect.EARTH, 5)));
    }
    
    public static void compileGroups() {
        for (final ResourceLocation reg : CraftingManager.field_193380_a.func_148742_b()) {
            final IRecipe recipe = CraftingManager.func_193373_a(reg);
            if (recipe != null) {
                final String group = recipe.func_193358_e();
                if (group.trim().isEmpty()) {
                    continue;
                }
                if (group.startsWith("minecraft")) {
                    continue;
                }
                if (!ConfigRecipes.recipeGroups.containsKey(group)) {
                    ConfigRecipes.recipeGroups.put(group, new ArrayList<ResourceLocation>());
                }
                final ArrayList list = ConfigRecipes.recipeGroups.get(group);
                list.add(reg);
            }
        }
        for (final ResourceLocation reg : CommonInternals.craftingRecipeCatalog.keySet()) {
            final IThaumcraftRecipe recipe2 = CommonInternals.craftingRecipeCatalog.get(reg);
            if (recipe2 != null) {
                final String group = recipe2.getGroup();
                if (group == null) {
                    continue;
                }
                if (group.trim().isEmpty()) {
                    continue;
                }
                if (!ConfigRecipes.recipeGroups.containsKey(group)) {
                    ConfigRecipes.recipeGroups.put(group, new ArrayList<ResourceLocation>());
                }
                final ArrayList list = ConfigRecipes.recipeGroups.get(group);
                list.add(reg);
            }
        }
        for (final ResourceLocation reg : CommonInternals.craftingRecipeCatalogFake.keySet()) {
            Object recipe3 = CommonInternals.craftingRecipeCatalogFake.get(reg);
            if (recipe3 != null) {
                final String group = "";
                if (recipe3 instanceof IRecipe) {
                    recipe3 = ((IRecipe)recipe3).func_193358_e();
                }
                else if (recipe3 instanceof IThaumcraftRecipe) {
                    recipe3 = ((IThaumcraftRecipe)recipe3).getGroup();
                }
                if (group == null) {
                    continue;
                }
                if (group.trim().isEmpty()) {
                    continue;
                }
                if (!ConfigRecipes.recipeGroups.containsKey(group)) {
                    ConfigRecipes.recipeGroups.put(group, new ArrayList<ResourceLocation>());
                }
                final ArrayList list = ConfigRecipes.recipeGroups.get(group);
                list.add(reg);
            }
        }
    }
    
    static {
        ConfigRecipes.defaultGroup = new ResourceLocation("");
        ConfigRecipes.recipeGroups = new HashMap<String, ArrayList<ResourceLocation>>();
    }
}
