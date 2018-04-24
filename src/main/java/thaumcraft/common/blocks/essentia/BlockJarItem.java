package thaumcraft.common.blocks.essentia;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import thaumcraft.api.aspects.*;
import net.minecraft.block.state.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;

public class BlockJarItem extends ItemBlock implements IEssentiaContainerItem
{
    public BlockJarItem(final Block block) {
        super(block);
    }
    
    public boolean showDurabilityBar(final ItemStack stack) {
        return this.getAspects(stack) != null;
    }
    
    public double getDurabilityForDisplay(final ItemStack stack) {
        final AspectList al = this.getAspects(stack);
        return (al == null) ? 0.0 : (al.visSize() / 250);
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        final Block bi = world.func_180495_p(pos).func_177230_c();
        final ItemStack itemstack = player.func_184586_b(hand);
        if (bi == BlocksTC.alembic && !world.field_72995_K) {
            final TileAlembic tile = (TileAlembic)world.func_175625_s(pos);
            if (tile.amount > 0) {
                if (this.getFilter(itemstack) != null && this.getFilter(itemstack) != tile.aspect) {
                    return EnumActionResult.FAIL;
                }
                if (this.getAspects(itemstack) != null && this.getAspects(itemstack).getAspects()[0] != tile.aspect) {
                    return EnumActionResult.FAIL;
                }
                int amt = tile.amount;
                if (this.getAspects(itemstack) != null && this.getAspects(itemstack).visSize() + amt > 250) {
                    amt = Math.abs(this.getAspects(itemstack).visSize() - 250);
                }
                if (amt <= 0) {
                    return EnumActionResult.FAIL;
                }
                final Aspect a = tile.aspect;
                if (tile.takeFromContainer(tile.aspect, amt)) {
                    final int base = (this.getAspects(itemstack) == null) ? 0 : this.getAspects(itemstack).visSize();
                    if (itemstack.func_190916_E() > 1) {
                        final ItemStack stack = itemstack.func_77946_l();
                        this.setAspects(stack, new AspectList().add(a, base + amt));
                        itemstack.func_190918_g(1);
                        stack.func_190920_e(1);
                        if (!player.field_71071_by.func_70441_a(stack)) {
                            world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, stack));
                        }
                    }
                    else {
                        this.setAspects(itemstack, new AspectList().add(a, base + amt));
                    }
                    player.func_184185_a(SoundEvents.field_187615_H, 0.25f, 1.0f);
                    player.field_71069_bz.func_75142_b();
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final boolean b = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if (b && !world.field_72995_K) {
            final TileEntity te = world.func_175625_s(pos);
            if (te != null && te instanceof TileJarFillable) {
                final TileJarFillable jar = (TileJarFillable)te;
                jar.setAspects(this.getAspects(stack));
                if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("AspectFilter")) {
                    jar.aspectFilter = Aspect.getAspect(stack.func_77978_p().func_74779_i("AspectFilter"));
                }
                te.func_70296_d();
                world.markAndNotifyBlock(pos, world.func_175726_f(pos), newState, newState, 3);
            }
        }
        return b;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("AspectFilter")) {
            final String tf = stack.func_77978_p().func_74779_i("AspectFilter");
            final Aspect tag = Aspect.getAspect(tf);
            tooltip.add("§5" + tag.getName());
        }
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
    
    public AspectList getAspects(final ItemStack itemstack) {
        if (itemstack.func_77942_o()) {
            final AspectList aspects = new AspectList();
            aspects.readFromNBT(itemstack.func_77978_p());
            return (aspects.size() > 0) ? aspects : null;
        }
        return null;
    }
    
    public Aspect getFilter(final ItemStack itemstack) {
        if (itemstack.func_77942_o()) {
            return Aspect.getAspect(itemstack.func_77978_p().func_74779_i("AspectFilter"));
        }
        return null;
    }
    
    public void setAspects(final ItemStack itemstack, final AspectList aspects) {
        if (!itemstack.func_77942_o()) {
            itemstack.func_77982_d(new NBTTagCompound());
        }
        aspects.writeToNBT(itemstack.func_77978_p());
    }
    
    public boolean ignoreContainedAspects() {
        return false;
    }
}
