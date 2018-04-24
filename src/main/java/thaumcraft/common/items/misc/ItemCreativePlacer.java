package thaumcraft.common.items.misc;

import thaumcraft.common.items.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;

public class ItemCreativePlacer extends ItemTCBase
{
    public ItemCreativePlacer() {
        super("creative_placer", new String[] { "obelisk", "node", "caster" });
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        tooltip.add(TextFormatting.DARK_PURPLE + "Creative only");
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        IBlockState bs = world.func_180495_p(pos);
        if (!bs.func_185904_a().func_76220_a()) {
            return EnumActionResult.FAIL;
        }
        if (world.field_72995_K) {
            return EnumActionResult.PASS;
        }
        pos = pos.func_177972_a(side);
        bs = world.func_180495_p(pos);
        if (!player.func_175151_a(pos, side, player.func_184586_b(hand))) {
            return EnumActionResult.FAIL;
        }
        if (!bs.func_177230_c().func_176200_f((IBlockAccess)world, pos)) {
            return EnumActionResult.FAIL;
        }
        if (player.func_184586_b(hand).func_77952_i() == 0 && !world.func_180495_p(pos.func_177977_b()).func_185904_a().func_76220_a()) {
            return EnumActionResult.FAIL;
        }
        world.func_175698_g(pos);
        player.func_184586_b(hand).func_77952_i();
        return EnumActionResult.SUCCESS;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.EPIC;
    }
}
