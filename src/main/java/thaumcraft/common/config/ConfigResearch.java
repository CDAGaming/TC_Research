package thaumcraft.common.config;

import net.minecraft.util.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import thaumcraft.common.entities.monster.cult.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.tainted.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.entity.passive.*;
import thaumcraft.api.entities.*;
import thaumcraft.common.entities.monster.boss.*;
import net.minecraft.block.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import thaumcraft.api.research.*;
import thaumcraft.common.lib.research.*;
import java.util.*;
import thaumcraft.api.research.theorycraft.*;
import thaumcraft.common.lib.research.theorycraft.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.world.biome.*;
import net.minecraft.stats.*;

public class ConfigResearch
{
    public static String[] TCCategories;
    private static final ResourceLocation BACK_OVER;
    
    public static void init() {
        initCategories();
        initScannables();
        initTheorycraft();
        initWarp();
        for (final String cat : ConfigResearch.TCCategories) {
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumcraft", "research/" + cat.toLowerCase()));
        }
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumcraft", "research/scans"));
    }
    
    public static void postInit() {
        ResearchManager.parseAllResearch();
    }
    
    private static void initCategories() {
        ResearchCategories.registerCategory("BASICS", null, new AspectList().add(Aspect.PLANT, 5).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5).add(Aspect.AIR, 5).add(Aspect.FIRE, 5).add(Aspect.EARTH, 3).add(Aspect.WATER, 5), new ResourceLocation("thaumcraft", "textures/items/thaumonomicon_cheat.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("AUROMANCY", "UNLOCKAUROMANCY", new AspectList().add(Aspect.AURA, 20).add(Aspect.MAGIC, 20).add(Aspect.FLUX, 15).add(Aspect.CRYSTAL, 5).add(Aspect.COLD, 5).add(Aspect.AIR, 5), new ResourceLocation("thaumcraft", "textures/research/cat_auromancy.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_2.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("ALCHEMY", "UNLOCKALCHEMY", new AspectList().add(Aspect.ALCHEMY, 30).add(Aspect.FLUX, 10).add(Aspect.MAGIC, 10).add(Aspect.LIFE, 5).add(Aspect.AVERSION, 5).add(Aspect.DESIRE, 5).add(Aspect.WATER, 5), new ResourceLocation("thaumcraft", "textures/research/cat_alchemy.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_3.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("ARTIFICE", "UNLOCKARTIFICE", new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.CRAFT, 10).add(Aspect.METAL, 10).add(Aspect.TOOL, 10).add(Aspect.ENERGY, 10).add(Aspect.LIGHT, 5).add(Aspect.FLIGHT, 5).add(Aspect.TRAP, 5).add(Aspect.FIRE, 5), new ResourceLocation("thaumcraft", "textures/research/cat_artifice.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_4.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("INFUSION", "UNLOCKINFUSION", new AspectList().add(Aspect.MAGIC, 30).add(Aspect.PROTECT, 10).add(Aspect.TOOL, 10).add(Aspect.FLUX, 5).add(Aspect.CRAFT, 5).add(Aspect.SOUL, 5).add(Aspect.EARTH, 3), new ResourceLocation("thaumcraft", "textures/research/cat_infusion.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_7.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("GOLEMANCY", "UNLOCKGOLEMANCY", new AspectList().add(Aspect.MAN, 20).add(Aspect.MOTION, 10).add(Aspect.MIND, 10).add(Aspect.MECHANISM, 10).add(Aspect.EXCHANGE, 5).add(Aspect.SENSES, 5).add(Aspect.BEAST, 5).add(Aspect.ORDER, 5), new ResourceLocation("thaumcraft", "textures/research/cat_golemancy.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_5.jpg"), ConfigResearch.BACK_OVER);
        ResearchCategories.registerCategory("ELDRITCH", "UNLOCKELDRITCH", new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.DARKNESS, 10).add(Aspect.MAGIC, 5).add(Aspect.MIND, 5).add(Aspect.VOID, 5).add(Aspect.DEATH, 5).add(Aspect.UNDEAD, 5).add(Aspect.ENTROPY, 5), new ResourceLocation("thaumcraft", "textures/research/cat_eldritch.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_6.jpg"), ConfigResearch.BACK_OVER);
    }
    
    private static void initScannables() {
        ScanningManager.addScannableThing(new ScanGeneric());
        for (final ResourceLocation loc : Enchantment.field_185264_b.func_148742_b()) {
            final Enchantment ench = (Enchantment)Enchantment.field_185264_b.func_82594_a((Object)loc);
            ScanningManager.addScannableThing(new ScanEnchantment(ench));
        }
        for (final ResourceLocation loc : Potion.field_188414_b.func_148742_b()) {
            final Potion pot = (Potion)Potion.field_188414_b.func_82594_a((Object)loc);
            ScanningManager.addScannableThing(new ScanPotion(pot));
        }
        ScanningManager.addScannableThing(new ScanEntity("!Wisp", EntityWisp.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!ThaumSlime", EntityThaumicSlime.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!Firebat", EntityFireBat.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!Pech", EntityPech.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!BrainyZombie", EntityBrainyZombie.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!EldritchCrab", EntityEldritchCrab.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!EldritchCrab", EntityInhabitedZombie.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!CrimsonCultist", EntityCultist.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!EldritchGuardian", EntityEldritchGuardian.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!TaintCrawler", EntityTaintCrawler.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!Taintacle", EntityTaintacle.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!TaintSeed", EntityTaintSeed.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!TaintSwarm", EntityTaintSwarm.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_golem", EntityGolem.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_golem", EntityOwnedConstruct.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_SPIDER", EntitySpider.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_BAT", EntityBat.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_BAT", EntityFireBat.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityBat.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityFireBat.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityTaintSwarm.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityWisp.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityGhast.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_FLY", EntityBlaze.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!ORMOB", IEldritchMob.class, true));
        ScanningManager.addScannableThing(new ScanEntity("!ORBOSS", EntityThaumcraftBoss.class, true));
        ScanningManager.addScannableThing(new ScanBlock("!ORBLOCK1", new Block[] { BlocksTC.stoneAncient, BlocksTC.stoneAncientTile }));
        ScanningManager.addScannableThing(new ScanBlock("!ORBLOCK2", new Block[] { BlocksTC.stoneEldritchTile }));
        ScanningManager.addScannableThing(new ScanBlock("!ORBLOCK3", new Block[] { BlocksTC.stoneAncientGlyphed }));
        ScanningManager.addScannableThing(new ScanBlock("ORE", new Block[] { BlocksTC.oreAmber, BlocksTC.oreCinnabar, BlocksTC.crystalAir, BlocksTC.crystalFire, BlocksTC.crystalWater, BlocksTC.crystalEarth, BlocksTC.crystalOrder, BlocksTC.crystalEntropy, BlocksTC.crystalTaint }));
        ScanningManager.addScannableThing(new ScanBlock("!OREAMBER", new Block[] { BlocksTC.oreAmber }));
        ScanningManager.addScannableThing(new ScanBlock("!ORECINNABAR", new Block[] { BlocksTC.oreCinnabar }));
        ScanningManager.addScannableThing(new ScanBlock("!ORECRYSTAL", new Block[] { BlocksTC.crystalAir, BlocksTC.crystalFire, BlocksTC.crystalWater, BlocksTC.crystalEarth, BlocksTC.crystalOrder, BlocksTC.crystalEntropy, BlocksTC.crystalTaint }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTWOOD", new Block[] { BlocksTC.logGreatwood }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTWOOD", new Block[] { BlocksTC.logSilverwood }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTWOOD", new Block[] { BlocksTC.saplingGreatwood }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTWOOD", new Block[] { BlocksTC.saplingSilverwood }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTCINDERPEARL", new Block[] { BlocksTC.cinderpearl }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTSHIMMERLEAF", new Block[] { BlocksTC.shimmerleaf }));
        ScanningManager.addScannableThing(new ScanBlock("!PLANTVISHROOM", new Block[] { BlocksTC.vishroom }));
        ScanningManager.addScannableThing(new ScanItem("PRIMPEARL", new ItemStack(ItemsTC.primordialPearl, 1, 32767)));
        ScanningManager.addScannableThing(new ScanItem("!DRAGONBREATH", new ItemStack(Items.field_185157_bK)));
        ScanningManager.addScannableThing(new ScanItem("!TOTEMUNDYING", new ItemStack(Items.field_190929_cY)));
        ScanningManager.addScannableThing(new ScanBlock("f_TELEPORT", new Block[] { Blocks.field_150427_aO, Blocks.field_150384_bq, Blocks.field_150378_br }));
        ScanningManager.addScannableThing(new ScanItem("f_TELEPORT", new ItemStack(Items.field_151079_bi)));
        ScanningManager.addScannableThing(new ScanEntity("f_TELEPORT", EntityEnderman.class, true));
        ScanningManager.addScannableThing(new ScanEntity("f_BRAIN", EntityBrainyZombie.class, true));
        ScanningManager.addScannableThing(new ScanItem("f_BRAIN", new ItemStack(ItemsTC.brain)));
        ScanningManager.addScannableThing(new ScanBlock("f_DISPENSER", new Block[] { Blocks.field_150367_z }));
        ScanningManager.addScannableThing(new ScanItem("f_DISPENSER", new ItemStack(Blocks.field_150367_z)));
        ScanningManager.addScannableThing(new ScanItem("f_MATCLAY", new ItemStack(Items.field_151119_aD)));
        ScanningManager.addScannableThing(new ScanBlock("f_MATCLAY", new Block[] { Blocks.field_150405_ch, Blocks.field_150406_ce }));
        ScanningManager.addScannableThing(new ScanMaterial("f_MATCLAY", new Material[] { Material.field_151571_B }));
        ScanningManager.addScannableThing(new ScanOreDictionary("f_MATIRON", new String[] { "oreIron", "ingotIron", "blockIron", "plateIron" }));
        ScanningManager.addScannableThing(new ScanOreDictionary("f_MATBRASS", new String[] { "ingotBrass", "blockBrass", "plateBrass" }));
        ScanningManager.addScannableThing(new ScanOreDictionary("f_MATTHAUMIUM", new String[] { "ingotThaumium", "blockThaumium", "plateThaumium" }));
        ScanningManager.addScannableThing(new ScanOreDictionary("f_MATVOID", new String[] { "ingotVoid", "blockVoid", "plateVoid" }));
        ScanningManager.addScannableThing(new ScanSky());
    }
    
    private static void initTheorycraft() {
        TheorycraftManager.registerAid(new AidBookshelf());
        TheorycraftManager.registerAid(new AidBrainInAJar());
        TheorycraftManager.registerAid(new AidGlyphedStone());
        TheorycraftManager.registerAid(new AidPortal.AidPortalEnd());
        TheorycraftManager.registerAid(new AidPortal.AidPortalNether());
        TheorycraftManager.registerAid(new AidPortal.AidPortalCrimson());
        TheorycraftManager.registerAid(new AidBasicAlchemy());
        TheorycraftManager.registerAid(new AidBasicArtifice());
        TheorycraftManager.registerAid(new AidBasicAuromancy());
        TheorycraftManager.registerAid(new AidBasicGolemancy());
        TheorycraftManager.registerAid(new AidBasicEldritch());
        TheorycraftManager.registerAid(new AidEnchantmentTable());
        TheorycraftManager.registerAid(new AidBeacon());
        TheorycraftManager.registerCard(CardStudy.class);
        TheorycraftManager.registerCard(CardAnalyze.class);
        TheorycraftManager.registerCard(CardBalance.class);
        TheorycraftManager.registerCard(CardNotation.class);
        TheorycraftManager.registerCard(CardPonder.class);
        TheorycraftManager.registerCard(CardRethink.class);
        TheorycraftManager.registerCard(CardReject.class);
        TheorycraftManager.registerCard(CardExperimentation.class);
        TheorycraftManager.registerCard(CardCurio.class);
        TheorycraftManager.registerCard(CardInspired.class);
        TheorycraftManager.registerCard(CardEnchantment.class);
        TheorycraftManager.registerCard(CardBeacon.class);
        TheorycraftManager.registerCard(CardCelestial.class);
        TheorycraftManager.registerCard(CardConcentrate.class);
        TheorycraftManager.registerCard(CardReactions.class);
        TheorycraftManager.registerCard(CardSynthesis.class);
        TheorycraftManager.registerCard(CardCalibrate.class);
        TheorycraftManager.registerCard(CardMindOverMatter.class);
        TheorycraftManager.registerCard(CardTinker.class);
        TheorycraftManager.registerCard(CardMeasure.class);
        TheorycraftManager.registerCard(CardChannel.class);
        TheorycraftManager.registerCard(CardInfuse.class);
        TheorycraftManager.registerCard(CardFocus.class);
        TheorycraftManager.registerCard(CardAwareness.class);
        TheorycraftManager.registerCard(CardSpellbinding.class);
        TheorycraftManager.registerCard(CardSculpting.class);
        TheorycraftManager.registerCard(CardScripting.class);
        TheorycraftManager.registerCard(CardSynergy.class);
        TheorycraftManager.registerCard(CardDarkWhispers.class);
        TheorycraftManager.registerCard(CardGlyphs.class);
        TheorycraftManager.registerCard(CardPortal.class);
        TheorycraftManager.registerCard(CardRevelation.class);
        TheorycraftManager.registerCard(CardRealization.class);
    }
    
    private static void initWarp() {
        ThaumcraftApi.addWarpToItem(new ItemStack(BlocksTC.jarBrain), 1);
    }
    
    private static void initGolemancyResearch() {
    }
    
    private static void initEldritchResearch() {
    }
    
    public static void checkPeriodicStuff(final EntityPlayer player) {
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        final Biome biome = player.field_70170_p.func_180494_b(player.func_180425_c());
        if (!knowledge.isResearchKnown("m_hellandback") && BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
            knowledge.addResearch("m_hellandback");
            knowledge.sync((EntityPlayerMP)player);
            player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.hellandback")), true);
        }
        if (!knowledge.isResearchKnown("m_endoftheworld") && BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
            knowledge.addResearch("m_endoftheworld");
            knowledge.sync((EntityPlayerMP)player);
            player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.endoftheworld")), true);
        }
        if (knowledge.isResearchKnown("UNLOCKAUROMANCY@1") && !knowledge.isResearchKnown("UNLOCKAUROMANCY@2")) {
            if (player.field_70163_u < 10.0 && !knowledge.isResearchKnown("m_deepdown")) {
                knowledge.addResearch("m_deepdown");
                knowledge.sync((EntityPlayerMP)player);
                player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.deepdown")), true);
            }
            if (player.field_70163_u > player.func_130014_f_().func_72940_L() * 0.4 && !knowledge.isResearchKnown("m_uphigh")) {
                knowledge.addResearch("m_uphigh");
                knowledge.sync((EntityPlayerMP)player);
                player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.uphigh")), true);
            }
        }
        final StatisticsManagerServer sms = player.func_184102_h().func_184103_al().func_152602_a(player);
        if (sms != null) {
            if (!knowledge.isResearchKnown("m_walker") && sms.func_77444_a(StatList.field_188100_j) > 160000) {
                knowledge.addResearch("m_walker");
                knowledge.sync((EntityPlayerMP)player);
            }
            if (!knowledge.isResearchKnown("m_runner") && sms.func_77444_a(StatList.field_188102_l) > 80000) {
                knowledge.addResearch("m_runner");
                knowledge.sync((EntityPlayerMP)player);
            }
            if (!knowledge.isResearchKnown("m_jumper") && sms.func_77444_a(StatList.field_75953_u) > 500) {
                knowledge.addResearch("m_jumper");
                knowledge.sync((EntityPlayerMP)player);
            }
            if (!knowledge.isResearchKnown("m_swimmer") && sms.func_77444_a(StatList.field_75946_m) > 8000) {
                knowledge.addResearch("m_swimmer");
                knowledge.sync((EntityPlayerMP)player);
            }
        }
    }
    
    static {
        ConfigResearch.TCCategories = new String[] { "BASICS", "ALCHEMY", "AUROMANCY", "ARTIFICE", "INFUSION", "GOLEMANCY", "ELDRITCH" };
        BACK_OVER = new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png");
    }
}
