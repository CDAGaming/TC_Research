package thaumcraft;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import thaumcraft.proxies.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.item.crafting.*;
import net.minecraft.potion.*;
import thaumcraft.api.potions.*;
import thaumcraft.common.lib.potions.*;
import net.minecraftforge.registries.*;
import net.minecraft.world.biome.*;
import thaumcraft.common.world.biomes.*;
import thaumcraft.common.config.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.*;

@Mod.EventBusSubscriber
public final class Registrar
{
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        ConfigBlocks.initBlocks((IForgeRegistry<Block>)event.getRegistry());
        ConfigBlocks.initTileEntities();
        ConfigBlocks.initMisc();
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerBlocksClient(final RegistryEvent.Register<Block> event) {
        ProxyBlock.setupBlocksClient((IForgeRegistry<Block>)event.getRegistry());
    }
    
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ConfigItems.preInitSeals();
        ConfigItems.initItems((IForgeRegistry<Item>)event.getRegistry());
        ConfigItems.initMisc();
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerItemsClient(final RegistryEvent.Register<Item> event) {
        ConfigItems.initModelsAndVariants();
    }
    
    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
        ConfigEntities.initEntities((IForgeRegistry<EntityEntry>)event.getRegistry());
    }
    
    @SubscribeEvent
    public static void registerVanillaRecipes(final RegistryEvent.Register<IRecipe> event) {
        ConfigRecipes.initializeSmelting();
        ConfigRecipes.initializeNormalRecipes((IForgeRegistry<IRecipe>)event.getRegistry());
        ConfigRecipes.initializeArcaneRecipes((IForgeRegistry<IRecipe>)event.getRegistry());
        ConfigRecipes.initializeInfusionRecipes();
        ConfigRecipes.initializeAlchemyRecipes();
        ConfigRecipes.initializeCompoundRecipes();
    }
    
    @SubscribeEvent
    public static void registerPotions(final RegistryEvent.Register<Potion> event) {
        PotionFluxTaint.instance = (Potion)new PotionFluxTaint(true, 6697847).setRegistryName("fluxTaint");
        PotionVisExhaust.instance = (Potion)new PotionVisExhaust(true, 6702199).setRegistryName("visExhaust");
        PotionInfectiousVisExhaust.instance = (Potion)new PotionInfectiousVisExhaust(true, 6706551).setRegistryName("infectiousVisExhaust");
        PotionUnnaturalHunger.instance = (Potion)new PotionUnnaturalHunger(true, 4482611).setRegistryName("unnaturalHunger");
        PotionWarpWard.instance = (Potion)new PotionWarpWard(false, 14742263).setRegistryName("warpWard");
        PotionDeathGaze.instance = (Potion)new PotionDeathGaze(true, 6702131).setRegistryName("deathGaze");
        PotionBlurredVision.instance = (Potion)new PotionBlurredVision(true, 8421504).setRegistryName("blurredVision");
        PotionSunScorned.instance = (Potion)new PotionSunScorned(true, 16308330).setRegistryName("sunScorned");
        PotionThaumarhia.instance = (Potion)new PotionThaumarhia(true, 6702199).setRegistryName("thaumarhia");
        event.getRegistry().register(PotionFluxTaint.instance);
        event.getRegistry().register(PotionVisExhaust.instance);
        event.getRegistry().register(PotionInfectiousVisExhaust.instance);
        event.getRegistry().register(PotionUnnaturalHunger.instance);
        event.getRegistry().register(PotionWarpWard.instance);
        event.getRegistry().register(PotionDeathGaze.instance);
        event.getRegistry().register(PotionBlurredVision.instance);
        event.getRegistry().register(PotionSunScorned.instance);
        event.getRegistry().register(PotionThaumarhia.instance);
    }
    
    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        BiomeHandler.MAGICAL_FOREST = new BiomeGenMagicalForest(new Biome.BiomeProperties("Magical Forest").setBaseHeight(0.2f).setHeightVariation(0.3f).setTemperature(0.8f).setRainfall(0.4f));
        event.getRegistry().register(BiomeHandler.MAGICAL_FOREST);
        BiomeHandler.EERIE = new BiomeGenEerie(new Biome.BiomeProperties("Eerie").setBaseHeight(0.125f).setHeightVariation(0.4f).setTemperature(0.8f).setRainDisabled());
        event.getRegistry().register(BiomeHandler.EERIE);
        BiomeHandler.ELDRITCH = new BiomeGenEldritch(new Biome.BiomeProperties("Outer Lands").setBaseHeight(0.125f).setHeightVariation(0.15f).setTemperature(0.8f).setRainfall(0.2f));
        event.getRegistry().register(BiomeHandler.ELDRITCH);
        BiomeHandler.registerBiomes();
        if (ModConfig.CONFIG_WORLD.generateMagicForest) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BiomeHandler.MAGICAL_FOREST, ModConfig.CONFIG_WORLD.biomeMagicalForestWeight));
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(BiomeHandler.MAGICAL_FOREST, ModConfig.CONFIG_WORLD.biomeMagicalForestWeight));
        }
    }
    
    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
        SoundsTC.registerSounds(event);
        SoundsTC.registerSoundTypes();
    }
}
