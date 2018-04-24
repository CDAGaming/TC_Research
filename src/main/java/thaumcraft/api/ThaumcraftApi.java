package thaumcraft.api;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.registries.*;
import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.block.*;
import thaumcraft.api.internal.*;
import thaumcraft.api.crafting.*;

public class ThaumcraftApi
{
    public static IInternalMethodHandler internalMethods;
    
    public static void registerResearchLocation(final ResourceLocation loc) {
        if (!CommonInternals.jsonLocs.containsKey(loc.toString())) {
            CommonInternals.jsonLocs.put(loc.toString(), loc);
        }
    }
    
    public static void addSmeltingBonus(final Object in, final ItemStack out, final float chance) {
        if (in instanceof ItemStack || in instanceof String) {
            CommonInternals.smeltingBonus.add(new SmeltBonus(in, out, chance));
        }
    }
    
    public static void addSmeltingBonus(final Object in, final ItemStack out) {
        if (in instanceof ItemStack || in instanceof String) {
            CommonInternals.smeltingBonus.add(new SmeltBonus(in, out, 0.33f));
        }
    }
    
    public static HashMap<ResourceLocation, IThaumcraftRecipe> getCraftingRecipes() {
        return CommonInternals.craftingRecipeCatalog;
    }
    
    public static HashMap<ResourceLocation, Object> getCraftingRecipesFake() {
        return CommonInternals.craftingRecipeCatalogFake;
    }
    
    public static void addFakeCraftingRecipe(final ResourceLocation registry, final Object recipe) {
        getCraftingRecipesFake().put(registry, recipe);
    }
    
    public static void addMultiblockRecipeToCatalog(final ResourceLocation registry, final BluePrint recipe) {
        getCraftingRecipes().put(registry, recipe);
    }
    
    public static void addArcaneCraftingRecipe(final ResourceLocation registry, final IArcaneRecipe recipe) {
        recipe.setRegistryName(registry);
        GameData.register_impl((IForgeRegistryEntry)recipe);
    }
    
    public static void addInfusionCraftingRecipe(final ResourceLocation registry, final InfusionRecipe recipe) {
        getCraftingRecipes().put(registry, recipe);
    }
    
    public static InfusionRecipe getInfusionRecipe(final ItemStack res) {
        for (final Object r : getCraftingRecipes().values()) {
            if (r instanceof InfusionRecipe && ((InfusionRecipe)r).getRecipeOutput() instanceof ItemStack && ((ItemStack)((InfusionRecipe)r).getRecipeOutput()).func_77969_a(res)) {
                return (InfusionRecipe)r;
            }
        }
        return null;
    }
    
    public static void addCrucibleRecipe(final ResourceLocation registry, final CrucibleRecipe recipe) {
        getCraftingRecipes().put(registry, recipe);
    }
    
    public static CrucibleRecipe getCrucibleRecipe(final ItemStack stack) {
        for (final Object r : getCraftingRecipes().values()) {
            if (r instanceof CrucibleRecipe && ((CrucibleRecipe)r).getRecipeOutput().func_77969_a(stack)) {
                return (CrucibleRecipe)r;
            }
        }
        return null;
    }
    
    public static CrucibleRecipe getCrucibleRecipeFromHash(final int hash) {
        for (final Object r : getCraftingRecipes().values()) {
            if (r instanceof CrucibleRecipe[]) {
                for (final CrucibleRecipe recipe : (CrucibleRecipe[])r) {
                    if (recipe.hash == hash) {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }
    
    public static boolean exists(final ItemStack item) {
        final ItemStack stack = item.func_77946_l();
        stack.func_190920_e(1);
        AspectList tmp = CommonInternals.objectTags.get(stack.serializeNBT().toString());
        if (tmp == null) {
            try {
                stack.func_77964_b(32767);
                tmp = CommonInternals.objectTags.get(stack.serializeNBT().toString());
                if (item.func_77952_i() == 32767 && tmp == null) {
                    int index = 0;
                    do {
                        stack.func_77964_b(index);
                        tmp = CommonInternals.objectTags.get(stack.serializeNBT().toString());
                    } while (++index < 16 && tmp == null);
                }
                if (tmp == null) {
                    return false;
                }
            }
            catch (Exception ex) {}
        }
        return true;
    }
    
    public static void registerObjectTag(final ItemStack item, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        try {
            final ItemStack tmp = item.func_77946_l();
            tmp.func_190920_e(1);
            final NBTTagCompound nbt = new NBTTagCompound();
            aspects.writeToNBT(nbt);
            CommonInternals.objectTags.put(tmp.serializeNBT().toString(), aspects);
        }
        catch (Exception ex) {}
    }
    
    public static void registerObjectTag(final ItemStack item, final int[] meta, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        try {
            final ItemStack tmp = item.func_77946_l();
            tmp.func_190920_e(1);
            final String s = tmp.serializeNBT().toString();
            CommonInternals.objectTags.put(s, aspects);
            for (final int m : meta) {
                CommonInternals.groupedObjectTags.put(m + ":" + s, meta);
            }
        }
        catch (Exception ex) {}
    }
    
    public static void registerObjectTag(final String oreDict, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        final List<ItemStack> ores = ThaumcraftApiHelper.getOresWithWildCards(oreDict);
        if (ores != null && ores.size() > 0) {
            for (final ItemStack ore : ores) {
                try {
                    final ItemStack oc = ore.func_77946_l();
                    oc.func_190920_e(1);
                    registerObjectTag(oc, aspects.copy());
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static void registerComplexObjectTag(final ItemStack item, final AspectList aspects) {
        if (!exists(item)) {
            final AspectList tmp = AspectHelper.generateTags(item);
            if (tmp != null && tmp.size() > 0) {
                for (final Aspect tag : tmp.getAspects()) {
                    aspects.add(tag, tmp.getAmount(tag));
                }
            }
            registerObjectTag(item, aspects);
        }
        else {
            final AspectList tmp = AspectHelper.getObjectAspects(item);
            for (final Aspect tag : aspects.getAspects()) {
                tmp.merge(tag, tmp.getAmount(tag));
            }
            registerObjectTag(item, tmp);
        }
    }
    
    public static void registerComplexObjectTag(final String oreDict, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        final List<ItemStack> ores = ThaumcraftApiHelper.getOresWithWildCards(oreDict);
        if (ores != null && ores.size() > 0) {
            for (final ItemStack ore : ores) {
                try {
                    final ItemStack oc = ore.func_77946_l();
                    oc.func_190920_e(1);
                    registerComplexObjectTag(oc, aspects.copy());
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static void registerEntityTag(final String entityName, final AspectList aspects, final EntityTagsNBT... nbt) {
        CommonInternals.scanEntities.add(new EntityTags(entityName, aspects, nbt));
    }
    
    public static void addWarpToItem(final ItemStack craftresult, final int amount) {
        CommonInternals.warpMap.put(Arrays.asList(craftresult.func_77973_b(), craftresult.func_77952_i()), amount);
    }
    
    public static int getWarp(final ItemStack in) {
        if (in == null) {
            return 0;
        }
        if (in instanceof ItemStack && CommonInternals.warpMap.containsKey(Arrays.asList(in.func_77973_b(), in.func_77952_i()))) {
            return CommonInternals.warpMap.get(Arrays.asList(in.func_77973_b(), in.func_77952_i()));
        }
        return 0;
    }
    
    public static void addLootBagItem(final ItemStack item, final int weight, final int... bagTypes) {
        if (bagTypes == null || bagTypes.length == 0) {
            WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
        }
        else {
            for (final int rarity : bagTypes) {
                switch (rarity) {
                    case 0: {
                        WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                    case 1: {
                        WeightedRandomLoot.lootBagUncommon.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                    case 2: {
                        WeightedRandomLoot.lootBagRare.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                }
            }
        }
    }
    
    public static void registerSeed(final Block block, final ItemStack seed) {
        CommonInternals.seedList.put(block.func_149739_a(), seed);
    }
    
    public static ItemStack getSeed(final Block block) {
        return CommonInternals.seedList.get(block.func_149739_a());
    }
    
    static {
        ThaumcraftApi.internalMethods = new DummyInternalMethodHandler();
    }
    
    public static class SmeltBonus
    {
        public Object in;
        public ItemStack out;
        public float chance;
        
        public SmeltBonus(final Object in, final ItemStack out, final float chance) {
            this.in = in;
            this.out = out;
            this.chance = chance;
        }
    }
    
    public static class BluePrint implements IThaumcraftRecipe
    {
        Part[][][] parts;
        String research;
        ItemStack displayStack;
        ItemStack[] ingredientList;
        private String group;
        
        public BluePrint(final String research, final Part[][][] parts, final ItemStack... ingredientList) {
            this.parts = parts;
            this.research = research;
            this.ingredientList = ingredientList;
        }
        
        public BluePrint(final String research, final ItemStack display, final Part[][][] parts, final ItemStack... ingredientList) {
            this.parts = parts;
            this.research = research;
            this.displayStack = display;
            this.ingredientList = ingredientList;
        }
        
        public Part[][][] getParts() {
            return this.parts;
        }
        
        @Override
        public String getResearch() {
            return this.research;
        }
        
        public ItemStack[] getIngredientList() {
            return this.ingredientList;
        }
        
        public ItemStack getDisplayStack() {
            return this.displayStack;
        }
        
        @Override
        public String getGroup() {
            return this.group;
        }
        
        public BluePrint setGroup(final ResourceLocation loc) {
            this.group = loc.toString();
            return this;
        }
    }
    
    public static class EntityTagsNBT
    {
        public String name;
        public Object value;
        
        public EntityTagsNBT(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }
    }
    
    public static class EntityTags
    {
        public String entityName;
        public EntityTagsNBT[] nbts;
        public AspectList aspects;
        
        public EntityTags(final String entityName, final AspectList aspects, final EntityTagsNBT... nbts) {
            this.entityName = entityName;
            this.nbts = nbts;
            this.aspects = aspects;
        }
    }
}
