package thaumcraft.api.internal;

import net.minecraft.util.*;
import java.util.*;
import thaumcraft.api.*;
import thaumcraft.api.crafting.*;
import java.util.concurrent.*;
import thaumcraft.api.aspects.*;
import net.minecraft.item.*;

public class CommonInternals
{
    public static HashMap<String, ResourceLocation> jsonLocs;
    public static ArrayList<ThaumcraftApi.EntityTags> scanEntities;
    public static HashMap<ResourceLocation, IThaumcraftRecipe> craftingRecipeCatalog;
    public static HashMap<ResourceLocation, Object> craftingRecipeCatalogFake;
    public static ArrayList<ThaumcraftApi.SmeltBonus> smeltingBonus;
    public static ConcurrentHashMap<String, AspectList> objectTags;
    public static ConcurrentHashMap<String, int[]> groupedObjectTags;
    public static HashMap<Object, Integer> warpMap;
    public static HashMap<String, ItemStack> seedList;
    
    public static IThaumcraftRecipe getCatalogRecipe(final ResourceLocation key) {
        return CommonInternals.craftingRecipeCatalog.get(key);
    }
    
    public static Object getCatalogRecipeFake(final ResourceLocation key) {
        return CommonInternals.craftingRecipeCatalogFake.get(key);
    }
    
    static {
        CommonInternals.jsonLocs = new HashMap<String, ResourceLocation>();
        CommonInternals.scanEntities = new ArrayList<ThaumcraftApi.EntityTags>();
        CommonInternals.craftingRecipeCatalog = new HashMap<ResourceLocation, IThaumcraftRecipe>();
        CommonInternals.craftingRecipeCatalogFake = new HashMap<ResourceLocation, Object>();
        CommonInternals.smeltingBonus = new ArrayList<ThaumcraftApi.SmeltBonus>();
        CommonInternals.objectTags = new ConcurrentHashMap<String, AspectList>();
        CommonInternals.groupedObjectTags = new ConcurrentHashMap<String, int[]>();
        CommonInternals.warpMap = new HashMap<Object, Integer>();
        CommonInternals.seedList = new HashMap<String, ItemStack>();
    }
}
