package thaumcraft.proxies;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.event.*;

public interface IProxy
{
    void preInit(final FMLPreInitializationEvent p0);
    
    void init(final FMLInitializationEvent p0);
    
    void postInit(final FMLPostInitializationEvent p0);
    
    World getClientWorld();
    
    boolean isShiftKeyDown();
    
    void registerModel(final ItemBlock p0);
    
    void checkInterModComs(final FMLInterModComms.IMCEvent p0);
}
