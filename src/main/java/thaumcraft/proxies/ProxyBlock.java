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
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabAncient), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_ancient"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabArcaneStone), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_arcane_stone"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabArcaneBrick), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_arcane_brick"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabEldritch), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_eldritch"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabGreatwood), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_greatwood"), "half=bottom,variant=default"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.slabSilverwood), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:slab_silverwood"), "half=bottom,variant=default"));
        for (int a = 0; a < ShardType.values().length; ++a) {
            ProxyBlock.crystals[a] = new ModelResourceLocation(iForgeRegistry.getKey(ShardType.values()[a].getOre()), "normal");
            final ModelResourceLocation mrl = ProxyBlock.crystals[a];
            ModelLoader.setCustomStateMapper(ShardType.values()[a].getOre(), new StateMapperBase() {
                protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
                    return mrl;
                }
            });
        }
        for (final Block b : BlocksTC.banners.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:banner"), "inventory"));
        }
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.bannerCrimsonCult), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:banner_crimson_cult"), "inventory"));
        for (final Block b : BlocksTC.nitor.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(new ResourceLocation("thaumcraft:nitor"), "inventory"));
        }
        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksTC.mirror), new ResourceLocation("thaumcraft:mirror"), new ResourceLocation("thaumcraft:mirror_on"));
        ModelBakery.registerItemVariants(Item.getItemFromBlock(BlocksTC.mirrorEssentia), new ResourceLocation("thaumcraft:mirror_essentia"), new ResourceLocation("thaumcraft:mirror_essentia_on"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.mirror), 1, new ModelResourceLocation(new ResourceLocation("thaumcraft:mirror_on"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlocksTC.mirrorEssentia), 1, new ModelResourceLocation(new ResourceLocation("thaumcraft:mirror_essentia_on"), "inventory"));
        final Item fluxGooItem = Item.getItemFromBlock(BlocksTC.fluxGoo);
        ModelBakery.registerItemVariants(fluxGooItem);
        ModelLoader.setCustomMeshDefinition(fluxGooItem, stack -> ProxyBlock.fluidGooLocation);
        ModelLoader.setCustomStateMapper(BlocksTC.fluxGoo, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
                return ProxyBlock.fluidGooLocation;
            }
        });
        final Item liquidDeathItem = Item.getItemFromBlock(BlocksTC.liquidDeath);
        ModelBakery.registerItemVariants(liquidDeathItem);
        ModelLoader.setCustomMeshDefinition(liquidDeathItem, stack -> ProxyBlock.fluidDeathLocation);
        ModelLoader.setCustomStateMapper(BlocksTC.liquidDeath, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
                return ProxyBlock.fluidDeathLocation;
            }
        });
        final Item purifyingFluidItem = Item.getItemFromBlock(BlocksTC.purifyingFluid);
        ModelBakery.registerItemVariants(purifyingFluidItem);
        ModelLoader.setCustomMeshDefinition(purifyingFluidItem, stack -> ProxyBlock.fluidPureLocation);
        ModelLoader.setCustomStateMapper(BlocksTC.purifyingFluid, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
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
            final TextureAtlasSprite crystalTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/crystal");
            final IBakedModel customCrystalModel = new CrystalModel(crystalTexture);
            for (int a = 0; a < ShardType.values().length; ++a) {
                event.getModelRegistry().putObject(ProxyBlock.crystals[a], customCrystalModel);
            }
        }
    }
}
