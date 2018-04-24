package thaumcraft.common.lib.crafting;

import thaumcraft.api.crafting.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.oredict.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.common.lib.events.*;

public class DustTriggerOre implements IDustTrigger
{
    String target;
    ItemStack result;
    String research;
    
    public DustTriggerOre(final String research, final String target, final ItemStack result) {
        this.target = target;
        this.result = result;
        this.research = research;
    }
    
    @Override
    public Placement getValidFace(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing face) {
        final IBlockState bs = world.func_180495_p(pos);
        boolean b = false;
        try {
            final int[] oreIDs;
            final int[] ods = oreIDs = OreDictionary.getOreIDs(new ItemStack(bs.func_177230_c(), 1, bs.func_177230_c().func_180651_a(bs)));
            for (final int q : oreIDs) {
                if (q == OreDictionary.getOreID(this.target)) {
                    b = true;
                    break;
                }
            }
        }
        catch (Exception ex) {}
        return (b && (this.research == null || ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research))) ? new Placement(0, 0, 0, null) : null;
    }
    
    @Override
    public void execute(final World world, final EntityPlayer player, final BlockPos pos, final Placement placement, final EnumFacing side) {
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, this.result, (IInventory)new InventoryFake(1));
        final IBlockState state = world.func_180495_p(pos);
        ServerEvents.addRunnableServer(world, new Runnable() {
            @Override
            public void run() {
                ServerEvents.addSwapper(world, pos, state, DustTriggerOre.this.result, false, 0, player, true, true, -9999, false, false, 0, ServerEvents.DEFAULT_PREDICATE, 0.0f);
            }
        }, 50);
    }
}
