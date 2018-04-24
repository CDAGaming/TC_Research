package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import thaumcraft.api.blocks.*;
import net.minecraftforge.fml.common.gameevent.*;
import thaumcraft.api.*;
import thaumcraft.common.config.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.common.lib.research.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber
public class CraftingEvents implements IFuelHandler
{
    public int getBurnTime(final ItemStack fuel) {
        if (fuel.func_77969_a(new ItemStack(ItemsTC.alumentum))) {
            return 4800;
        }
        if (fuel.func_77969_a(new ItemStack(BlocksTC.logGreatwood))) {
            return 500;
        }
        if (fuel.func_77969_a(new ItemStack(BlocksTC.logSilverwood))) {
            return 400;
        }
        return 0;
    }
    
    @SubscribeEvent
    public static void onCrafting(final PlayerEvent.ItemCraftedEvent event) {
        final int warp = ThaumcraftApi.getWarp(event.crafting);
        if (!ModConfig.CONFIG_MISC.wussMode && warp > 0 && !event.player.field_70170_p.field_72995_K) {
            ThaumcraftApi.internalMethods.addWarpToPlayer(event.player, warp, IPlayerWarp.EnumWarpType.NORMAL);
        }
        if (event.crafting.func_77973_b() == ItemsTC.label && event.crafting.func_77942_o()) {
            for (int var2 = 0; var2 < 9; ++var2) {
                final ItemStack var3 = event.craftMatrix.func_70301_a(var2);
                if (var3 != null && var3.func_77973_b() instanceof ItemPhial) {
                    var3.func_190917_f(1);
                    event.craftMatrix.func_70299_a(var2, var3);
                }
            }
        }
        if (event.player != null && !event.player.field_70170_p.field_72995_K) {
            final int stackHash = ResearchManager.createItemStackHash(event.crafting.func_77946_l());
            if (ResearchManager.craftingReferences.contains(stackHash)) {
                ResearchManager.completeResearch(event.player, "[#]" + stackHash);
            }
        }
    }
}
