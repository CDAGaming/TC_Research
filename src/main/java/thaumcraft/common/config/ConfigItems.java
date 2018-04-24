package thaumcraft.common.config;

import net.minecraft.creativetab.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.nbt.*;
import thaumcraft.api.items.*;
import net.minecraftforge.registries.*;
import thaumcraft.common.items.*;
import thaumcraft.common.items.resources.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.api.*;
import thaumcraft.common.items.tools.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.common.items.armor.*;
import thaumcraft.common.items.baubles.*;
import thaumcraft.common.items.misc.*;
import thaumcraft.common.items.curios.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.common.entities.construct.golem.*;
import net.minecraft.util.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.items.casters.foci.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.seals.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.*;
import java.util.*;

public class ConfigItems
{
    public static ItemStack startBook;
    public static CreativeTabs TABTC;
    public static final List<IThaumcraftItems> ITEM_VARIANT_HOLDERS;
    public static ItemStack AIR_CRYSTAL;
    public static ItemStack FIRE_CRYSTAL;
    public static ItemStack WATER_CRYSTAL;
    public static ItemStack EARTH_CRYSTAL;
    public static ItemStack ORDER_CRYSTAL;
    public static ItemStack ENTROPY_CRYSTAL;
    public static ItemStack FLUX_CRYSTAL;
    
    public static void initMisc() {
        OreDictionaryEntries.initializeOreDictionary();
        ConfigItems.AIR_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.AIR);
        ConfigItems.FIRE_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.FIRE);
        ConfigItems.WATER_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.WATER);
        ConfigItems.EARTH_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.EARTH);
        ConfigItems.ORDER_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.ORDER);
        ConfigItems.ENTROPY_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY);
        ConfigItems.FLUX_CRYSTAL = ThaumcraftApiHelper.makeCrystal(Aspect.FLUX);
        final NBTTagCompound contents = new NBTTagCompound();
        contents.func_74768_a("generation", 3);
        contents.func_74778_a("title", I18n.func_74838_a("book.start.title"));
        final NBTTagList pages = new NBTTagList();
        pages.func_74742_a((NBTBase)new NBTTagString(I18n.func_74838_a("book.start.1")));
        pages.func_74742_a((NBTBase)new NBTTagString(I18n.func_74838_a("book.start.2")));
        pages.func_74742_a((NBTBase)new NBTTagString(I18n.func_74838_a("book.start.3")));
        contents.func_74782_a("pages", (NBTBase)pages);
        ConfigItems.startBook.func_77982_d(contents);
    }
    
    public static void initItems(final IForgeRegistry<Item> iForgeRegistry) {
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumonomicon = new ItemThaumonomicon()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.curio = new ItemCurio()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.lootBag = new ItemLootBag()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.primordialPearl = new ItemPrimordialPearl()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.pechWand = new ItemPechWand()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.celestialNotes = new ItemCelestialNotes()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.amber = new ItemTCBase("amber", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.quicksilver = new ItemTCBase("quicksilver", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.ingots = new ItemTCBase("ingot", new String[] { "thaumium", "void", "brass" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.nuggets = new ItemTCBase("nugget", new String[] { "iron", "copper", "tin", "silver", "lead", "quicksilver", "thaumium", "void", "brass", "quartz", "rareearth" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.clusters = new ItemTCBase("cluster", new String[] { "iron", "gold", "copper", "tin", "silver", "lead", "cinnabar", "quartz" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.fabric = new ItemTCBase("fabric", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.visResonator = new ItemTCBase("vis_resonator", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.tallow = new ItemTCBase("tallow", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.mechanismSimple = new ItemTCBase("mechanism_simple", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.mechanismComplex = new ItemTCBase("mechanism_complex", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.plate = new ItemTCBase("plate", new String[] { "brass", "iron", "thaumium", "void" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.filter = new ItemTCBase("filter", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.morphicResonator = new ItemTCBase("morphic_resonator", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.salisMundus = new ItemMagicDust()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.mirroredGlass = new ItemTCBase("mirrored_glass", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidSeed = new ItemTCBase("void_seed", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.mind = new ItemTCBase("mind", new String[] { "clockwork", "biothaumic" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.modules = new ItemTCBase("module", new String[] { "vision", "aggression" })));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crystalEssence = new ItemCrystalEssence()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.chunks = (Item)new ItemChunksEdible()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.tripleMeatTreat = (Item)new ItemTripleMeatTreat()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.brain = (Item)new ItemZombieBrain()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.label = new ItemLabel()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.phial = new ItemPhial()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.alumentum = new ItemAlumentum()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.jarBrace = new ItemTCBase("jar_brace", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.bucketDeath = new ItemBucketDeath()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.bucketPure = new ItemBucketPure()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.bottleTaint = new ItemBottleTaint()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.sanitySoap = new ItemSanitySoap()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.bathSalts = new ItemBathSalts()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.turretPlacer = new ItemTurretPlacer()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.scribingTools = new ItemScribingTools()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumometer = new ItemThaumometer()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.resonator = new ItemResonator()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.sanityChecker = new ItemSanityChecker()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.handMirror = new ItemHandMirror()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumAxe = (Item)new ItemThaumiumAxe(ThaumcraftMaterials.TOOLMAT_THAUMIUM)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumSword = (Item)new ItemThaumiumSword(ThaumcraftMaterials.TOOLMAT_THAUMIUM)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumShovel = (Item)new ItemThaumiumShovel(ThaumcraftMaterials.TOOLMAT_THAUMIUM)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumPick = (Item)new ItemThaumiumPickaxe(ThaumcraftMaterials.TOOLMAT_THAUMIUM)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumHoe = (Item)new ItemThaumiumHoe(ThaumcraftMaterials.TOOLMAT_THAUMIUM)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidAxe = (Item)new ItemVoidAxe(ThaumcraftMaterials.TOOLMAT_VOID)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidSword = (Item)new ItemVoidSword(ThaumcraftMaterials.TOOLMAT_VOID)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidShovel = (Item)new ItemVoidShovel(ThaumcraftMaterials.TOOLMAT_VOID)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidPick = (Item)new ItemVoidPickaxe(ThaumcraftMaterials.TOOLMAT_VOID)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidHoe = (Item)new ItemVoidHoe(ThaumcraftMaterials.TOOLMAT_VOID)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.elementalAxe = (Item)new ItemElementalAxe(ThaumcraftMaterials.TOOLMAT_ELEMENTAL)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.elementalSword = (Item)new ItemElementalSword(ThaumcraftMaterials.TOOLMAT_ELEMENTAL)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.elementalShovel = (Item)new ItemElementalShovel(ThaumcraftMaterials.TOOLMAT_ELEMENTAL)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.elementalPick = (Item)new ItemElementalPickaxe(ThaumcraftMaterials.TOOLMAT_ELEMENTAL)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.elementalHoe = (Item)new ItemElementalHoe(ThaumcraftMaterials.TOOLMAT_ELEMENTAL)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.primalCrusher = (Item)new ItemPrimalCrusher()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonBlade = (Item)new ItemCrimsonBlade()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.grappleGun = new ItemGrappleGun()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.grappleGunTip = new ItemTCBase("grapple_gun_tip", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.grappleGunSpool = new ItemTCBase("grapple_gun_spool", new String[0])));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.goggles = (Item)new ItemGoggles()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumHelm = (Item)new ItemThaumiumArmor("thaumium_helm", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 2, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumChest = (Item)new ItemThaumiumArmor("thaumium_chest", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 2, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumLegs = (Item)new ItemThaumiumArmor("thaumium_legs", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 2, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.thaumiumBoots = (Item)new ItemThaumiumArmor("thaumium_boots", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 2, EntityEquipmentSlot.FEET)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.clothChest = (Item)new ItemRobeArmor("cloth_chest", ThaumcraftMaterials.ARMORMAT_SPECIAL, 1, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.clothLegs = (Item)new ItemRobeArmor("cloth_legs", ThaumcraftMaterials.ARMORMAT_SPECIAL, 2, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.clothBoots = (Item)new ItemRobeArmor("cloth_boots", ThaumcraftMaterials.ARMORMAT_SPECIAL, 1, EntityEquipmentSlot.FEET)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.travellerBoots = (Item)new ItemBootsTraveller()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.fortressHelm = (Item)new ItemFortressArmor("fortress_helm", ThaumcraftMaterials.ARMORMAT_FORTRESS, 4, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.fortressChest = (Item)new ItemFortressArmor("fortress_chest", ThaumcraftMaterials.ARMORMAT_FORTRESS, 4, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.fortressLegs = (Item)new ItemFortressArmor("fortress_legs", ThaumcraftMaterials.ARMORMAT_FORTRESS, 4, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidHelm = (Item)new ItemVoidArmor("void_helm", ThaumcraftMaterials.ARMORMAT_VOID, 2, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidChest = (Item)new ItemVoidArmor("void_chest", ThaumcraftMaterials.ARMORMAT_VOID, 2, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidLegs = (Item)new ItemVoidArmor("void_legs", ThaumcraftMaterials.ARMORMAT_VOID, 2, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidBoots = (Item)new ItemVoidArmor("void_boots", ThaumcraftMaterials.ARMORMAT_VOID, 2, EntityEquipmentSlot.FEET)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidRobeHelm = (Item)new ItemVoidRobeArmor("void_robe_helm", ThaumcraftMaterials.ARMORMAT_VOID, 4, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidRobeChest = (Item)new ItemVoidRobeArmor("void_robe_chest", ThaumcraftMaterials.ARMORMAT_VOID, 4, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.voidRobeLegs = (Item)new ItemVoidRobeArmor("void_robe_legs", ThaumcraftMaterials.ARMORMAT_VOID, 4, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPlateHelm = (Item)new ItemCultistPlateArmor("crimson_plate_helm", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPlateChest = (Item)new ItemCultistPlateArmor("crimson_plate_chest", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPlateLegs = (Item)new ItemCultistPlateArmor("crimson_plate_legs", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonBoots = (Item)new ItemCultistBoots()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonRobeHelm = (Item)new ItemCultistRobeArmor("crimson_robe_helm", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonRobeChest = (Item)new ItemCultistRobeArmor("crimson_robe_chest", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonRobeLegs = (Item)new ItemCultistRobeArmor("crimson_robe_legs", ItemArmor.ArmorMaterial.IRON, 4, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPraetorHelm = (Item)new ItemCultistLeaderArmor("crimson_praetor_helm", 4, EntityEquipmentSlot.HEAD)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPraetorChest = (Item)new ItemCultistLeaderArmor("crimson_praetor_chest", 4, EntityEquipmentSlot.CHEST)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.crimsonPraetorLegs = (Item)new ItemCultistLeaderArmor("crimson_praetor_legs", 4, EntityEquipmentSlot.LEGS)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.baubles = new ItemBaubles()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.amuletVis = new ItemAmuletVis()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.charmVerdant = new ItemVerdantCharm()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.bandCuriosity = new ItemCuriosityBand()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.charmVoidseer = new ItemVoidseerCharm()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.ringCloud = new ItemCloudRing()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.charmUndying = new ItemCharmUndying()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.creativeFluxSponge = new ItemCreativeFluxSponge()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.enchantedPlaceholder = new ItemEnchantmentPlaceholder()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.casterBasic = new ItemCaster("caster_basic", 0)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.focus1 = new ItemFocus("focus_1", 15)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.focus2 = new ItemFocus("focus_2", 25)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.focus3 = new ItemFocus("focus_3", 50)));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.focusPouch = new ItemFocusPouch()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.golemBell = new ItemGolemBell()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.golemPlacer = new ItemGolemPlacer()));
        iForgeRegistry.register((IForgeRegistryEntry)(ItemsTC.seals = new ItemSealPlacer()));
    }
    
    public static void init() {
        FocusEngine.registerElement(FocusMediumRoot.class, new ResourceLocation("thaumcraft", "textures/foci/root.png"), 10066329);
        FocusEngine.registerElement(FocusMediumTouch.class, new ResourceLocation("thaumcraft", "textures/foci/touch.png"), 11371909);
        FocusEngine.registerElement(FocusMediumBolt.class, new ResourceLocation("thaumcraft", "textures/foci/bolt.png"), 11377029);
        FocusEngine.registerElement(FocusMediumProjectile.class, new ResourceLocation("thaumcraft", "textures/foci/projectile.png"), 11382149);
        FocusEngine.registerElement(FocusMediumCloud.class, new ResourceLocation("thaumcraft", "textures/foci/cloud.png"), 10071429);
        FocusEngine.registerElement(FocusMediumMine.class, new ResourceLocation("thaumcraft", "textures/foci/mine.png"), 8760709);
        FocusEngine.registerElement(FocusMediumPlan.class, new ResourceLocation("thaumcraft", "textures/foci/plan.png"), 8760728);
        FocusEngine.registerElement(FocusMediumSpellBat.class, new ResourceLocation("thaumcraft", "textures/foci/spellbat.png"), 8760748);
        FocusEngine.registerElement(FocusEffectFire.class, new ResourceLocation("thaumcraft", "textures/foci/fire.png"), 16734721);
        FocusEngine.registerElement(FocusEffectFrost.class, new ResourceLocation("thaumcraft", "textures/foci/frost.png"), 14811135);
        FocusEngine.registerElement(FocusEffectAir.class, new ResourceLocation("thaumcraft", "textures/foci/air.png"), 16777086);
        FocusEngine.registerElement(FocusEffectEarth.class, new ResourceLocation("thaumcraft", "textures/foci/earth.png"), 5685248);
        FocusEngine.registerElement(FocusEffectFlux.class, new ResourceLocation("thaumcraft", "textures/foci/flux.png"), 8388736);
        FocusEngine.registerElement(FocusEffectBreak.class, new ResourceLocation("thaumcraft", "textures/foci/break.png"), 9063176);
        FocusEngine.registerElement(FocusEffectRift.class, new ResourceLocation("thaumcraft", "textures/foci/rift.png"), 3084645);
        FocusEngine.registerElement(FocusEffectExchange.class, new ResourceLocation("thaumcraft", "textures/foci/exchange.png"), 5735255);
        FocusEngine.registerElement(FocusEffectCurse.class, new ResourceLocation("thaumcraft", "textures/foci/curse.png"), 6946821);
        FocusEngine.registerElement(FocusEffectHeal.class, new ResourceLocation("thaumcraft", "textures/foci/heal.png"), 14548997);
        FocusEngine.registerElement(FocusModScatter.class, new ResourceLocation("thaumcraft", "textures/foci/scatter.png"), 10066329);
        FocusEngine.registerElement(FocusModSplitTarget.class, new ResourceLocation("thaumcraft", "textures/foci/split_target.png"), 10066329);
        FocusEngine.registerElement(FocusModSplitTrajectory.class, new ResourceLocation("thaumcraft", "textures/foci/split_trajectory.png"), 10066329);
    }
    
    public static void preInitSeals() {
        SealHandler.registerSeal(new SealPickup());
        SealHandler.registerSeal(new SealPickupAdvanced());
        SealHandler.registerSeal(new SealFill());
        SealHandler.registerSeal(new SealFillAdvanced());
        SealHandler.registerSeal(new SealEmpty());
        SealHandler.registerSeal(new SealEmptyAdvanced());
        SealHandler.registerSeal(new SealHarvest());
        SealHandler.registerSeal(new SealButcher());
        SealHandler.registerSeal(new SealGuard());
        SealHandler.registerSeal(new SealGuardAdvanced());
        SealHandler.registerSeal(new SealLumber());
        SealHandler.registerSeal(new SealBreaker());
        SealHandler.registerSeal(new SealUse());
        SealHandler.registerSeal(new SealProvide());
    }
    
    @SideOnly(Side.CLIENT)
    public static void initModelsAndVariants() {
        for (final IThaumcraftItems itemVariantHolder : ConfigItems.ITEM_VARIANT_HOLDERS) {
            initModelAndVariants(itemVariantHolder);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static void initModelAndVariants(final IThaumcraftItems item) {
        if (item.getCustomMesh() != null) {
            ModelLoader.setCustomMeshDefinition(item.getItem(), item.getCustomMesh());
            for (int i = 0; i < item.getVariantNames().length; ++i) {
                ModelBakery.registerItemVariants(item.getItem(), new ResourceLocation[] { item.getCustomModelResourceLocation(item.getVariantNames()[i]) });
            }
        }
        else if (item.getItem() == ItemsTC.seals) {
            for (int i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], new ModelResourceLocation(item.getItem().getRegistryName() + "_" + item.getVariantNames()[i], (String)null));
            }
        }
        else if (!item.getItem().func_77614_k()) {
            ModelLoader.setCustomModelResourceLocation(item.getItem(), 0, new ModelResourceLocation(item.getItem().getRegistryName(), (String)null));
        }
        else {
            for (int i = 0; i < item.getVariantNames().length; ++i) {
                ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getVariantMeta()[i], item.getCustomModelResourceLocation(item.getVariantNames()[i]));
            }
        }
    }
    
    static {
        ConfigItems.startBook = new ItemStack(Items.field_151164_bB);
        ConfigItems.TABTC = new CreativeTabThaumcraft(CreativeTabs.getNextID(), "thaumcraft");
        ITEM_VARIANT_HOLDERS = new ArrayList<IThaumcraftItems>();
    }
}
