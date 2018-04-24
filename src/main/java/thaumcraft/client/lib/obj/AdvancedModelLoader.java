package thaumcraft.client.lib.obj;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.*;
import java.util.*;
import com.google.common.collect.*;

@SideOnly(Side.CLIENT)
public class AdvancedModelLoader
{
    private static Map<String, IModelCustomLoader> instances;
    
    public static void registerModelHandler(final IModelCustomLoader modelHandler) {
        for (final String suffix : modelHandler.getSuffixes()) {
            AdvancedModelLoader.instances.put(suffix, modelHandler);
        }
    }
    
    public static IModelCustom loadModel(final ResourceLocation resource) throws IllegalArgumentException, WavefrontObject.ModelFormatException {
        final String name = resource.func_110623_a();
        final int i = name.lastIndexOf(46);
        if (i == -1) {
            FMLLog.severe("The resource name %s is not valid", new Object[] { resource });
            throw new IllegalArgumentException("The resource name is not valid");
        }
        final String suffix = name.substring(i + 1);
        final IModelCustomLoader loader = AdvancedModelLoader.instances.get(suffix);
        if (loader == null) {
            FMLLog.severe("The resource name %s is not supported", new Object[] { resource });
            throw new IllegalArgumentException("The resource name is not supported");
        }
        return loader.loadInstance(resource);
    }
    
    public static Collection<String> getSupportedSuffixes() {
        return AdvancedModelLoader.instances.keySet();
    }
    
    static {
        AdvancedModelLoader.instances = (Map<String, IModelCustomLoader>)Maps.newHashMap();
        registerModelHandler(new ObjModelLoader());
    }
}
