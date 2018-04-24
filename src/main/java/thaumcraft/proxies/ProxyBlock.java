package thaumcraft.proxies;

import net.minecraft.block.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import thaumcraft.common.blocks.world.ore.*;
import net.minecraftforge.registries.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import thaumcraft.client.renderers.block.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ProxyBlock
{
    static ModelResourceLocation[] crystals;
    static ModelResourceLocation[] jars;
    static ModelResourceLocation[] jarsVoid;
    static ModelResourceLocation fibres;
    private static ModelResourceLocation fluidGooLocation;
    private static ModelResourceLocation taintDustLocation;
    private static ModelResourceLocation fluidDeathLocation;
    private static ModelResourceLocation fluidPureLocation;
    
    public static void setupBlocksClient(final IForgeRegistry<Block> iForgeRegistry) {
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabAncient), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_ancient"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabArcaneStone), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_arcane_stone"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabArcaneBrick), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_arcane_brick"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabEldritch), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_eldritch"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabGreatwood), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_greatwood"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a((Block)BlocksTC.slabSilverwood), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_silverwood"), "half=bottom,variant=default"));
        for (int a = 0; a < ShardType.values().length; ++a) {
            ProxyBlock.crystals[a] = new ModelResourceLocation(iForgeRegistry.getKey((IForgeRegistryEntry)ShardType.values()[a].getOre()), "normal");
            final ModelResourceLocation mrl = ProxyBlock.crystals[a];
            ModelLoader.setCustomStateMapper(ShardType.values()[a].getOre(), (IStateMapper)new StateMapperBase() {
                protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
                    return mrl;
                }
            });
        }
        for (final Block b : BlocksTC.banners.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(b), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:banner"), "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(BlocksTC.bannerCrimsonCult), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:banner_crimson_cult"), "inventory"));
        for (final Block b : BlocksTC.nitor.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(b), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:nitor"), "inventory"));
        }
        ModelBakery.registerItemVariants(Item.func_150898_a(BlocksTC.mirror), new ResourceLocation[] { new ResourceLocation("thaumcraft:mirror"), new ResourceLocation("thaumcraft:mirror_on") });
        ModelBakery.registerItemVariants(Item.func_150898_a(BlocksTC.mirrorEssentia), new ResourceLocation[] { new ResourceLocation("thaumcraft:mirror_essentia"), new ResourceLocation("thaumcraft:mirror_essentia_on") });
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(BlocksTC.mirror), 1, new ModelResourceLocation(new ResourceLocation("thaumcraft:mirror_on"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.func_150898_a(BlocksTC.mirrorEssentia), 1, new ModelResourceLocation(new ResourceLocation("thaumcraft:mirror_essentia_on"), "inventory"));
        final Item fluxGooItem = Item.func_150898_a(BlocksTC.fluxGoo);
        ModelBakery.registerItemVariants(fluxGooItem, new ResourceLocation[0]);
        ModelLoader.setCustomMeshDefinition(fluxGooItem, (ItemMeshDefinition)new ItemMeshDefinition() {
            public ModelResourceLocation func_178113_a(final ItemStack stack) {
                return ProxyBlock.fluidGooLocation;
            }
        });
        ModelLoader.setCustomStateMapper(BlocksTC.fluxGoo, (IStateMapper)new StateMapperBase() {
            protected ModelResourceLocation func_178132_a(final IBlockState state) {
                return ProxyBlock.fluidGooLocation;
            }
        });
        final Item liquidDeathItem = Item.func_150898_a(BlocksTC.liquidDeath);
        ModelBakery.registerItemVariants(liquidDeathItem, new ResourceLocation[0]);
        ModelLoader.setCustomMeshDefinition(liquidDeathItem, (ItemMeshDefinition)new ItemMeshDefinition() {
            public ModelResourceLocation func_178113_a(final ItemStack stack) {
                return ProxyBlock.fluidDeathLocation;
            }
        });
        ModelLoader.setCustomStateMapper(BlocksTC.liquidDeath, (IStateMapper)new StateMapperBase() {
            protected ModelResourceLocation func_178132_a(final IBlockState state) {
                return ProxyBlock.fluidDeathLocation;
            }
        });
        final Item purifyingFluidItem = Item.func_150898_a(BlocksTC.purifyingFluid);
        ModelBakery.registerItemVariants(purifyingFluidItem, new ResourceLocation[0]);
        ModelLoader.setCustomMeshDefinition(purifyingFluidItem, (ItemMeshDefinition)new ItemMeshDefinition() {
            public ModelResourceLocation func_178113_a(final ItemStack stack) {
                return ProxyBlock.fluidPureLocation;
            }
        });
        ModelLoader.setCustomStateMapper(BlocksTC.purifyingFluid, (IStateMapper)new StateMapperBase() {
            protected ModelResourceLocation func_178132_a(final IBlockState state) {
                return ProxyBlock.fluidPureLocation;
            }
        });
    }
    
    static {
        ProxyBlock.crystals = new ModelResourceLocation[ShardType.values().length];
        ProxyBlock.jars = new ModelResourceLocation[4];
        ProxyBlock.jarsVoid = new ModelResourceLocation[4];
        ProxyBlock.fluidGooLocation = new ModelResourceLocation("thaumcraft:flux_goo", "fluid");
        ProxyBlock.taintDustLocation = new ModelResourceLocation("thaumcraft:taint_dust", "fluid");
        ProxyBlock.fluidDeathLocation = new ModelResourceLocation("thaumcraft:liquid_death", "fluid");
        ProxyBlock.fluidPureLocation = new ModelResourceLocation("thaumcraft:purifying_fluid", "fluid");
    }
    
    @Mod.EventBusSubscriber({ Side.CLIENT })
    public static class BakeBlockEventHandler
    {
        @SubscribeEvent
        public static void onModelBakeEvent(final ModelBakeEvent event) {
            final TextureAtlasSprite crystalTexture = Minecraft.func_71410_x().func_147117_R().func_110572_b("thaumcraft:blocks/crystal");
            final IBakedModel customCrystalModel = (IBakedModel)new CrystalModel(crystalTexture);
            for (int a = 0; a < ShardType.values().length; ++a) {
                event.getModelRegistry().func_82595_a((Object)ProxyBlock.crystals[a], (Object)customCrystalModel);
            }
        }
    }
}
