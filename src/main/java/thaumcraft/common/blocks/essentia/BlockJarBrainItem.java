package thaumcraft.common.blocks.essentia;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockJarBrainItem extends ItemBlock
{
    public BlockJarBrainItem(final Block block) {
        super(block);
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final boolean b = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if (b && !world.field_72995_K) {
            final TileEntity te = world.func_175625_s(pos);
            if (te != null && te instanceof TileJarBrain) {
                final TileJarBrain jar = (TileJarBrain)te;
                if (stack.func_77942_o()) {
                    jar.xp = stack.func_77978_p().func_74762_e("xp");
                }
                te.func_70296_d();
                world.markAndNotifyBlock(pos, world.func_175726_f(pos), newState, newState, 3);
            }
        }
        return b;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("xp")) {
            final int tf = stack.func_77978_p().func_74762_e("xp");
            tooltip.add("§a" + tf + " xp");
        }
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
}
