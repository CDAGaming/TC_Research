package thaumcraft.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import thaumcraft.api.blocks.*;
import java.util.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;
import net.minecraft.block.state.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.items.casters.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import thaumcraft.common.blocks.world.ore.*;
import thaumcraft.common.blocks.essentia.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.*;

@SideOnly(Side.CLIENT)
public class ColorHandler
{
    public static void registerColourHandlers() {
        final BlockColors blockColors = Minecraft.func_71410_x().func_184125_al();
        final ItemColors itemColors = Minecraft.func_71410_x().getItemColors();
        registerBlockColourHandlers(blockColors);
        registerItemColourHandlers(blockColors, itemColors);
    }
    
    private static void registerBlockColourHandlers(final BlockColors blockColors) {
        final IBlockColor basicColourHandler = (state, blockAccess, pos, tintIndex) -> state.func_177230_c().func_180659_g(state, blockAccess, pos).field_76291_p;
        final Block[] basicBlocks = new Block[BlocksTC.candles.size() + BlocksTC.banners.size() + BlocksTC.nitor.size()];
        int i = 0;
        for (final Block b : BlocksTC.candles.values()) {
            basicBlocks[i] = b;
            ++i;
        }
        for (final Block b : BlocksTC.banners.values()) {
            basicBlocks[i] = b;
            ++i;
        }
        for (final Block b : BlocksTC.nitor.values()) {
            basicBlocks[i] = b;
            ++i;
        }
        blockColors.func_186722_a(basicColourHandler, basicBlocks);
        final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> (blockAccess != null && pos != null) ? BiomeColorHelper.func_180286_a(blockAccess, pos) : ColorizerGrass.func_77480_a(0.5, 1.0);
        blockColors.func_186722_a(grassColourHandler, new Block[] { BlocksTC.grassAmbient });
        final IBlockColor leafColourHandler = (state, blockAccess, pos, tintIndex) -> {
            if (state.func_177230_c() == BlocksTC.leafSilverwood) {
                return 16777215;
            }
            if (blockAccess != null && pos != null) {
                return BiomeColorHelper.func_180287_b(blockAccess, pos);
            }
            return ColorizerFoliage.func_77468_c();
        };
        blockColors.func_186722_a(leafColourHandler, new Block[] { BlocksTC.leafGreatwood, BlocksTC.leafSilverwood });
        final IBlockColor crystalColourHandler = (state, blockAccess, pos, tintIndex) -> {
            if (state.func_177230_c() instanceof BlockCrystal) {
                return ((BlockCrystal)state.func_177230_c()).aspect.getColor();
            }
            return 16777215;
        };
        blockColors.func_186722_a(crystalColourHandler, new Block[] { BlocksTC.crystalAir, BlocksTC.crystalEarth, BlocksTC.crystalFire, BlocksTC.crystalWater, BlocksTC.crystalEntropy, BlocksTC.crystalOrder, BlocksTC.crystalTaint });
        final IBlockColor tubeFilterColourHandler = (state, blockAccess, pos, tintIndex) -> {
            if (state.func_177230_c() instanceof BlockTube && tintIndex == 1) {
                final TileEntity te = blockAccess.func_175625_s(pos);
                if (te != null && te instanceof TileTubeFilter && ((TileTubeFilter)te).aspectFilter != null) {
                    return ((TileTubeFilter)te).aspectFilter.getColor();
                }
            }
            return 16777215;
        };
        blockColors.func_186722_a(tubeFilterColourHandler, new Block[] { BlocksTC.tubeFilter });
    }
    
    private static void registerItemColourHandlers(final BlockColors blockColors, final ItemColors itemColors) {
        final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
            final IBlockState state = ((ItemBlock)stack.func_77973_b()).func_179223_d().func_176203_a(stack.func_77960_j());
            return blockColors.func_186724_a(state, (IBlockAccess)null, (BlockPos)null, tintIndex);
        };
        final Block[] basicBlocks = new Block[BlocksTC.candles.size() + BlocksTC.nitor.size() + 3];
        int i = 0;
        for (final Block b : BlocksTC.candles.values()) {
            basicBlocks[i] = b;
            ++i;
        }
        for (final Block b : BlocksTC.nitor.values()) {
            basicBlocks[i] = b;
            ++i;
        }
        basicBlocks[i] = BlocksTC.leafGreatwood;
        ++i;
        basicBlocks[i] = BlocksTC.leafSilverwood;
        ++i;
        basicBlocks[i] = BlocksTC.grassAmbient;
        ++i;
        itemColors.func_186731_a(itemBlockColourHandler, basicBlocks);
        final IItemColor itemEssentiaColourHandler = (stack, tintIndex) -> {
            final ItemGenericEssentiaContainer item = (ItemGenericEssentiaContainer)stack.func_77973_b();
            if (item.getAspects(stack) != null) {
                return item.getAspects(stack).getAspects()[0].getColor();
            }
            return 16777215;
        };
        itemColors.func_186730_a(itemEssentiaColourHandler, new Item[] { ItemsTC.crystalEssence });
        final IItemColor itemCrystalPlanterColourHandler = (stack, tintIndex) -> {
            final Item item = stack.func_77973_b();
            if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d() instanceof BlockCrystal) {
                return ((BlockCrystal)((ItemBlock)item).func_179223_d()).aspect.getColor();
            }
            return 16777215;
        };
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalAir });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalEarth });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalFire });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalWater });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalEntropy });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalOrder });
        itemColors.func_186731_a(itemCrystalPlanterColourHandler, new Block[] { BlocksTC.crystalTaint });
        final IItemColor itemEssentiaAltColourHandler = (stack, tintIndex) -> {
            final ItemGenericEssentiaContainer item = (ItemGenericEssentiaContainer)stack.func_77973_b();
            if (stack.func_77952_i() == 1 && item.getAspects(stack) != null && tintIndex == 1) {
                return item.getAspects(stack).getAspects()[0].getColor();
            }
            return 16777215;
        };
        itemColors.func_186730_a(itemEssentiaAltColourHandler, new Item[] { ItemsTC.phial, ItemsTC.label });
        final IItemColor itemArmorColourHandler = (stack, tintIndex) -> {
            final ItemArmor item = (ItemArmor)stack.func_77973_b();
            return (tintIndex > 0) ? -1 : item.func_82814_b(stack);
        };
        itemColors.func_186730_a(itemArmorColourHandler, new Item[] { ItemsTC.voidRobeChest, ItemsTC.voidRobeHelm, ItemsTC.voidRobeLegs, ItemsTC.clothChest, ItemsTC.clothLegs, ItemsTC.clothBoots });
        final IItemColor itemCasterColourHandler = (stack, tintIndex) -> {
            final ItemCaster item = (ItemCaster)stack.func_77973_b();
            final ItemFocus focus = item.getFocus(stack);
            return (tintIndex > 0 && focus != null) ? focus.getFocusColor(item.getFocusStack(stack)) : -1;
        };
        itemColors.func_186730_a(itemCasterColourHandler, new Item[] { ItemsTC.casterBasic });
        final IItemColor itemFocusColourHandler = (stack, tintIndex) -> {
            final ItemFocus item = (ItemFocus)stack.func_77973_b();
            final int color = item.getFocusColor(stack);
            return color;
        };
        itemColors.func_186730_a(itemFocusColourHandler, new Item[] { ItemsTC.focus1 });
        itemColors.func_186730_a(itemFocusColourHandler, new Item[] { ItemsTC.focus2 });
        itemColors.func_186730_a(itemFocusColourHandler, new Item[] { ItemsTC.focus3 });
        final IItemColor itemGolemColourHandler = (stack, tintIndex) -> {
            if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("props")) {
                final IGolemProperties props = GolemProperties.fromLong(stack.func_77978_p().func_74763_f("props"));
                return props.getMaterial().itemColor;
            }
            return 16777215;
        };
        itemColors.func_186730_a(itemGolemColourHandler, new Item[] { ItemsTC.golemPlacer });
        final IItemColor itemBannerColourHandler = (stack, tintIndex) -> {
            if (tintIndex == 1) {
                final IBlockState state = ((ItemBlock)stack.func_77973_b()).func_179223_d().func_176203_a(stack.func_77960_j());
                return blockColors.func_186724_a(state, (IBlockAccess)null, (BlockPos)null, tintIndex);
            }
            if (tintIndex != 2) {
                return 16777215;
            }
            if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("aspect") && stack.func_77978_p().func_74779_i("aspect") != null) {
                return Aspect.getAspect(stack.func_77978_p().func_74779_i("aspect")).getColor();
            }
            final IBlockState state = ((ItemBlock)stack.func_77973_b()).func_179223_d().func_176203_a(stack.func_77960_j());
            return blockColors.func_186724_a(state, (IBlockAccess)null, (BlockPos)null, tintIndex);
        };
        final Block[] bannerBlocks = new Block[BlocksTC.banners.size()];
        i = 0;
        for (final Block b2 : BlocksTC.banners.values()) {
            bannerBlocks[i] = b2;
            ++i;
        }
        itemColors.func_186731_a(itemBannerColourHandler, bannerBlocks);
    }
}
