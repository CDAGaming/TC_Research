package thaumcraft;

import thaumcraft.proxies.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import thaumcraft.common.lib.*;
import net.minecraft.command.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.client.event.*;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.apache.logging.log4j.*;

@Mod(modid = "thaumcraft", name = "Thaumcraft", version = "6.1.BETA10", dependencies = "required-after:forge@[14.23.2.2637,);required-after:baubles@[1.5.2,)", acceptedMinecraftVersions = "[1.12.2]")
public class Thaumcraft
{
    public static final String MODID = "thaumcraft";
    public static final String MODNAME = "Thaumcraft";
    public static final String VERSION = "6.1.BETA10";
    @SidedProxy(clientSide = "thaumcraft.proxies.ClientProxy", serverSide = "thaumcraft.proxies.ServerProxy")
    public static IProxy proxy;
    @Mod.Instance("thaumcraft")
    public static Thaumcraft instance;
    public File modDir;
    public static final Logger log;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Thaumcraft.proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Thaumcraft.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Thaumcraft.proxy.postInit(event);
    }
    
    @Mod.EventHandler
    public void serverLoad(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new CommandThaumcraft());
    }
    
    @Mod.EventHandler
    public void interModComs(final FMLInterModComms.IMCEvent event) {
        Thaumcraft.proxy.checkInterModComs(event);
    }
    
    @SubscribeEvent
    public void onConfigChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("thaumcraft")) {
            ConfigManager.sync("thaumcraft", Config.Type.INSTANCE);
        }
    }
    
    static {
        log = LogManager.getLogger("thaumcraft".toUpperCase());
    }
}
