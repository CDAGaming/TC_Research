package thaumcraft.common.lib.crafting;

import thaumcraft.api.crafting.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.capabilities.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import thaumcraft.common.lib.events.*;

public class DustTriggerSimple implements IDustTrigger
{
    Block target;
    ItemStack result;
    String research;
    
    public DustTriggerSimple(final String research, final Block target, final ItemStack result) {
        this.target = target;
        this.result = result;
        this.research = research;
    }
    
    @Override
    public Placement getValidFace(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing face) {
        return (world.func_180495_p(pos).func_177230_c() == this.target && (this.research == null || ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research))) ? new Placement(0, 0, 0, null) : null;
    }
    
    @Override
    public void execute(final World world, final EntityPlayer player, final BlockPos pos, final Placement placement, final EnumFacing side) {
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, this.result, (IInventory)new InventoryFake(1));
        final IBlockState state = world.func_180495_p(pos);
        ServerEvents.addRunnableServer(world, new Runnable() {
            @Override
            public void run() {
                ServerEvents.addSwapper(world, pos, state, DustTriggerSimple.this.result, false, 0, player, true, true, -9999, false, false, 0, ServerEvents.DEFAULT_PREDICATE, 0.0f);
            }
        }, 50);
    }
}
