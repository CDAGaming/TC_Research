package thaumcraft.common.blocks.basic;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import thaumcraft.common.tiles.misc.*;
import net.minecraft.util.math.*;

public class BlockBannerTCItem extends ItemBlock
{
    public BlockBannerTCItem(final BlockBannerTC block) {
        super((Block)block);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74779_i("aspect") != null && Aspect.getAspect(stack.func_77978_p().func_74779_i("aspect")) != null) {
            tooltip.add(Aspect.getAspect(stack.func_77978_p().func_74779_i("aspect")).getName());
        }
    }
    
    public EnumActionResult func_180614_a(final EntityPlayer player, final World worldIn, BlockPos pos, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.DOWN) {
            return EnumActionResult.FAIL;
        }
        if (!worldIn.func_180495_p(pos).func_185904_a().func_76220_a()) {
            return EnumActionResult.FAIL;
        }
        pos = pos.func_177972_a(side);
        if (!player.func_175151_a(pos, side, player.func_184586_b(hand))) {
            return EnumActionResult.FAIL;
        }
        if (!Blocks.field_180393_cK.func_176196_c(worldIn, pos)) {
            return EnumActionResult.FAIL;
        }
        if (worldIn.field_72995_K) {
            return EnumActionResult.FAIL;
        }
        worldIn.func_180501_a(pos, this.field_150939_a.func_176223_P(), 3);
        final TileBanner tile = (TileBanner)worldIn.func_175625_s(pos);
        if (tile != null) {
            if (side == EnumFacing.UP) {
                final int i = MathHelper.func_76128_c((player.field_70177_z + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
                tile.setBannerFacing((byte)i);
            }
            else {
                tile.setWall(true);
                int i = 0;
                if (side == EnumFacing.NORTH) {
                    i = 8;
                }
                if (side == EnumFacing.WEST) {
                    i = 4;
                }
                if (side == EnumFacing.EAST) {
                    i = 12;
                }
                tile.setBannerFacing((byte)i);
            }
            if (player.func_184586_b(hand).func_77942_o() && player.func_184586_b(hand).func_77978_p().func_74779_i("aspect") != null) {
                tile.setAspect(Aspect.getAspect(player.func_184586_b(hand).func_77978_p().func_74779_i("aspect")));
            }
            tile.func_70296_d();
            worldIn.markAndNotifyBlock(pos, worldIn.func_175726_f(pos), this.field_150939_a.func_176223_P(), this.field_150939_a.func_176223_P(), 3);
        }
        player.func_184586_b(hand).func_190918_g(1);
        return EnumActionResult.SUCCESS;
    }
}
