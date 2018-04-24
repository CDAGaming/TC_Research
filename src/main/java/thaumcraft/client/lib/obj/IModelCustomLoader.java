package thaumcraft.client.lib.obj;

import net.minecraft.util.*;

public interface IModelCustomLoader
{
    String getType();
    
    String[] getSuffixes();
    
    IModelCustom loadInstance(final ResourceLocation p0) throws WavefrontObject.ModelFormatException;
}
