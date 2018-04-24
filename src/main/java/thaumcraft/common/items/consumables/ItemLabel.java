package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;

public class ItemLabel extends ItemTCEssentiaContainer
{
    public ItemLabel() {
        super("label", 1, new String[] { "blank", "filled" });
    }
    
    public String func_77667_c(final ItemStack stack) {
        return super.func_77658_a() + "." + this.getVariantNames()[stack.func_77952_i()];
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            items.add((Object)new ItemStack((Item)this, 1, 0));
        }
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        if (world.field_72995_K) {
            return EnumActionResult.PASS;
        }
        final IBlockState bs = world.func_180495_p(pos);
        if (bs.func_177230_c() instanceof ILabelable) {
            if (((ILabelable)bs.func_177230_c()).applyLabel(player, pos, side, player.func_184586_b(hand))) {
                player.func_184586_b(hand).func_190918_g(1);
                player.field_71069_bz.func_75142_b();
            }
            return EnumActionResult.SUCCESS;
        }
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof ILabelable) {
            if (((ILabelable)te).applyLabel(player, pos, side, player.func_184586_b(hand))) {
                player.func_184586_b(hand).func_190918_g(1);
                player.field_71069_bz.func_75142_b();
            }
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int par4, final boolean par5) {
    }
    
    @Override
    public void func_77622_d(final ItemStack stack, final World world, final EntityPlayer player) {
    }
    
    @Override
    public boolean ignoreContainedAspects() {
        return true;
    }
}
