package thaumcraft.proxies;

import thaumcraft.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.capabilities.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.common.*;
import thaumcraft.common.lib.events.*;
import net.minecraftforge.fml.common.registry.*;
import thaumcraft.common.world.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.*;
import thaumcraft.api.items.*;
import thaumcraft.common.lib.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.common.config.*;
import thaumcraft.common.config.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.world.biomes.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

public class CommonProxy implements IGuiHandler, IProxy
{
    ProxyGUI proxyGUI;
    
    public CommonProxy() {
        this.proxyGUI = new ProxyGUI();
    }
    
    public void preInit(final FMLPreInitializationEvent event) {
        event.getModMetadata().version = "6.1.BETA10";
        Thaumcraft.instance.modDir = event.getModConfigurationDirectory();
        ThaumcraftApi.internalMethods = new InternalMethodHandler();
        PlayerKnowledge.preInit();
        PlayerWarp.preInit();
        PacketHandler.preInit();
        MinecraftForge.TERRAIN_GEN_BUS.register((Object)WorldEvents.INSTANCE);
        GameRegistry.registerFuelHandler((IFuelHandler)new CraftingEvents());
        GameRegistry.registerWorldGenerator((IWorldGenerator)ThaumcraftWorldGenerator.INSTANCE, 0);
        MinecraftForge.EVENT_BUS.register((Object)Thaumcraft.instance);
    }
    
    public void init(final FMLInitializationEvent event) {
        ConfigItems.init();
        BlockDispenser.field_149943_a.func_82595_a((Object)ItemsTC.alumentum, (Object)new BehaviorDispenseAlumetum());
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)Thaumcraft.instance, (IGuiHandler)this);
        ConfigResearch.init();
        ConfigManager.sync("thaumcraft", Config.Type.INSTANCE);
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
        ConfigEntities.postInitEntitySpawns();
        ModConfig.postInitModCompatibility();
        ConfigAspects.postInit();
        ConfigRecipes.postAspects();
        ModConfig.postInitLoot();
        ModConfig.postInitMisc();
        ConfigRecipes.compileGroups();
        ConfigResearch.postInit();
    }
    
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return this.proxyGUI.getClientGuiElement(ID, player, world, x, y, z);
    }
    
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return this.proxyGUI.getServerGuiElement(ID, player, world, x, y, z);
    }
    
    public boolean isShiftKeyDown() {
        return false;
    }
    
    public World getClientWorld() {
        return null;
    }
    
    public void registerModel(final ItemBlock itemBlock) {
    }
    
    public void checkInterModComs(final FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equals("portableHoleBlacklist") && message.isStringMessage()) {
                BlockUtils.portableHoleBlackList.add(message.getStringValue());
            }
            if (message.key.equals("harvestStandardCrop") && message.isItemStackMessage()) {
                final ItemStack crop = message.getItemStackValue();
                CropUtils.addStandardCrop(crop, crop.func_77952_i());
            }
            if (message.key.equals("harvestClickableCrop") && message.isItemStackMessage()) {
                final ItemStack crop = message.getItemStackValue();
                CropUtils.addClickableCrop(crop, crop.func_77952_i());
            }
            if (message.key.equals("harvestStackedCrop") && message.isItemStackMessage()) {
                final ItemStack crop = message.getItemStackValue();
                CropUtils.addStackedCrop(crop, crop.func_77952_i());
            }
            if (message.key.equals("nativeCluster") && message.isStringMessage()) {
                final String[] t = message.getStringValue().split(",");
                if (t != null && t.length == 5) {
                    try {
                        final ItemStack ore = new ItemStack(Item.func_150899_d(Integer.parseInt(t[0])), 1, Integer.parseInt(t[1]));
                        final ItemStack cluster = new ItemStack(Item.func_150899_d(Integer.parseInt(t[2])), 1, Integer.parseInt(t[3]));
                        Utils.addSpecialMiningResult(ore, cluster, Float.parseFloat(t[4]));
                    }
                    catch (Exception ex) {}
                }
            }
            if (message.key.equals("lampBlacklist") && message.isItemStackMessage()) {
                final ItemStack crop = message.getItemStackValue();
                CropUtils.blacklistLamp(crop, crop.func_77952_i());
            }
            if (message.key.equals("dimensionBlacklist") && message.isStringMessage()) {
                final String[] t = message.getStringValue().split(":");
                if (t != null && t.length == 2) {
                    try {
                        BiomeHandler.addDimBlacklist(Integer.parseInt(t[0]), Integer.parseInt(t[1]));
                    }
                    catch (Exception ex2) {}
                }
            }
            if (message.key.equals("biomeBlacklist") && message.isStringMessage()) {
                final String[] t = message.getStringValue().split(":");
                if (t != null && t.length == 2 && Biome.func_150568_d(Integer.parseInt(t[0])) != null) {
                    try {
                        BiomeHandler.addBiomeBlacklist(Integer.parseInt(t[0]), Integer.parseInt(t[1]));
                    }
                    catch (Exception ex3) {}
                }
            }
            if (message.key.equals("championWhiteList") && message.isStringMessage()) {
                try {
                    final String[] t = message.getStringValue().split(":");
                    final Class oclass = EntityList.func_192839_a(t[0]);
                    if (oclass == null) {
                        continue;
                    }
                    ConfigEntities.championModWhitelist.put(oclass, Integer.parseInt(t[1]));
                }
                catch (Exception e) {
                    Thaumcraft.log.error("Failed to Whitelist [" + message.getStringValue() + "] with [ championWhiteList ] message.");
                }
            }
        }
    }
}
