package thaumcraft.proxies;

import thaumcraft.common.lib.events.*;
import net.minecraftforge.client.model.obj.*;
import thaumcraft.client.lib.ender.*;
import thaumcraft.client.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.client.model.*;
import net.minecraft.item.*;

public class ClientProxy extends CommonProxy
{
    ProxyEntities proxyEntities;
    ProxyTESR proxyTESR;
    KeyHandler kh;
    
    public ClientProxy() {
        this.proxyEntities = new ProxyEntities();
        this.proxyTESR = new ProxyTESR();
        this.kh = new KeyHandler();
    }
    
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain("thaumcraft".toLowerCase());
        ShaderHelper.initShaders();
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        ColorHandler.registerColourHandlers();
        this.registerKeyBindings();
        this.proxyEntities.setupEntityRenderers();
        this.proxyTESR.setupTESR();
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
    }
    
    public void registerKeyBindings() {
        MinecraftForge.EVENT_BUS.register(this.kh);
    }
    
    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().world;
    }
    
    @Override
    public boolean isShiftKeyDown() {
        return GuiScreen.isShiftKeyDown();
    }
    
    public void setOtherBlockRenderers() {
    }
    
    @Override
    public void registerModel(final ItemBlock itemBlock) {
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
    }
}
