package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.material.*;

public class ItemBucketPure extends ItemTCBase
{
    public ItemBucketPure() {
        super("bucket_pure", new String[0]);
        this.func_77627_a(false);
        this.func_77625_d(1);
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer player, final EnumHand hand) {
        final boolean flag = false;
        final RayTraceResult rayTraceResult = this.func_77621_a(worldIn, player, flag);
        if (rayTraceResult == null) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
        }
        final ActionResult<ItemStack> ret = (ActionResult<ItemStack>)ForgeEventFactory.onBucketUse(player, worldIn, player.func_184586_b(hand), rayTraceResult);
        if (ret != null) {
            return ret;
        }
        if (rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = rayTraceResult.func_178782_a();
            if (!worldIn.func_175660_a(player, blockpos)) {
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
            }
            final BlockPos blockpos2 = blockpos.func_177972_a(rayTraceResult.field_178784_b);
            if (!player.func_175151_a(blockpos2, rayTraceResult.field_178784_b, player.func_184586_b(hand))) {
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
            }
            if (this.tryPlaceContainedLiquid(worldIn, blockpos2) && !player.field_71075_bZ.field_75098_d) {
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)new ItemStack(Items.field_151133_ar));
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
    
    public boolean tryPlaceContainedLiquid(final World world, final BlockPos pos) {
        final Material material = world.func_180495_p(pos).func_185904_a();
        final boolean flag = !material.func_76220_a();
        if (!world.func_175623_d(pos) && !flag) {
            return false;
        }
        if (!world.field_72995_K && flag && !material.func_76224_d()) {
            world.func_175655_b(pos, true);
        }
        world.func_175656_a(pos, BlocksTC.purifyingFluid.func_176223_P());
        return true;
    }
}
