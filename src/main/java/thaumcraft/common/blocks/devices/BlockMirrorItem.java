package thaumcraft.common.blocks.devices;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.blocks.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockMirrorItem extends ItemBlock
{
    public BlockMirrorItem(final Block par1) {
        super(par1);
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        if (world.func_180495_p(pos).func_177230_c() instanceof BlockMirror) {
            if (world.field_72995_K) {
                player.func_184609_a(hand);
                return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
            }
            if (this.field_150939_a == BlocksTC.mirror) {
                final TileEntity tm = world.func_175625_s(pos);
                if (tm != null && tm instanceof TileMirror && !((TileMirror)tm).isLinkValid()) {
                    final ItemStack st = player.func_184586_b(hand).func_77946_l();
                    st.func_190920_e(1);
                    st.func_77964_b(1);
                    st.func_77983_a("linkX", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177958_n()));
                    st.func_77983_a("linkY", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177956_o()));
                    st.func_77983_a("linkZ", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177952_p()));
                    st.func_77983_a("linkDim", (NBTBase)new NBTTagInt(world.field_73011_w.getDimension()));
                    world.func_184133_a((EntityPlayer)null, pos, SoundsTC.jar, SoundCategory.BLOCKS, 1.0f, 2.0f);
                    if (!player.field_71071_by.func_70441_a(st) && !world.field_72995_K) {
                        world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, st));
                    }
                    if (!player.field_71075_bZ.field_75098_d) {
                        player.func_184586_b(hand).func_190918_g(1);
                    }
                    player.field_71069_bz.func_75142_b();
                }
                else if (tm != null && tm instanceof TileMirror) {
                    player.func_145747_a((ITextComponent)new TextComponentTranslation("§5§oThat mirror is already linked to a valid destination.", new Object[0]));
                }
            }
            else {
                final TileEntity tm = world.func_175625_s(pos);
                if (tm != null && tm instanceof TileMirrorEssentia && !((TileMirrorEssentia)tm).isLinkValid()) {
                    final ItemStack st = player.func_184586_b(hand).func_77946_l();
                    st.func_190920_e(1);
                    st.func_77964_b(1);
                    st.func_77983_a("linkX", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177958_n()));
                    st.func_77983_a("linkY", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177956_o()));
                    st.func_77983_a("linkZ", (NBTBase)new NBTTagInt(tm.func_174877_v().func_177952_p()));
                    st.func_77983_a("linkDim", (NBTBase)new NBTTagInt(world.field_73011_w.getDimension()));
                    world.func_184133_a((EntityPlayer)null, pos, SoundsTC.jar, SoundCategory.BLOCKS, 1.0f, 2.0f);
                    if (!player.field_71071_by.func_70441_a(st) && !world.field_72995_K) {
                        world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, st));
                    }
                    if (!player.field_71075_bZ.field_75098_d) {
                        player.func_184586_b(hand).func_190918_g(1);
                    }
                    player.field_71069_bz.func_75142_b();
                }
                else if (tm != null && tm instanceof TileMirrorEssentia) {
                    player.func_145747_a((ITextComponent)new TextComponentTranslation("§5§oThat mirror is already linked to a valid destination.", new Object[0]));
                }
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final boolean ret = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if (ret && !world.field_72995_K) {
            if (this.field_150939_a == BlocksTC.mirror) {
                final TileEntity te = world.func_175625_s(pos);
                if (te != null && te instanceof TileMirror && stack.func_77942_o()) {
                    ((TileMirror)te).linkX = stack.func_77978_p().func_74762_e("linkX");
                    ((TileMirror)te).linkY = stack.func_77978_p().func_74762_e("linkY");
                    ((TileMirror)te).linkZ = stack.func_77978_p().func_74762_e("linkZ");
                    ((TileMirror)te).linkDim = stack.func_77978_p().func_74762_e("linkDim");
                    ((TileMirror)te).restoreLink();
                }
            }
            else {
                final TileEntity te = world.func_175625_s(pos);
                if (te != null && te instanceof TileMirrorEssentia && stack.func_77942_o()) {
                    ((TileMirrorEssentia)te).linkX = stack.func_77978_p().func_74762_e("linkX");
                    ((TileMirrorEssentia)te).linkY = stack.func_77978_p().func_74762_e("linkY");
                    ((TileMirrorEssentia)te).linkZ = stack.func_77978_p().func_74762_e("linkZ");
                    ((TileMirrorEssentia)te).linkDim = stack.func_77978_p().func_74762_e("linkDim");
                    ((TileMirrorEssentia)te).restoreLink();
                }
            }
        }
        return ret;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack item, final World worldIn, final List<String> list, final ITooltipFlag flagIn) {
        if (item.func_77942_o()) {
            final int lx = item.func_77978_p().func_74762_e("linkX");
            final int ly = item.func_77978_p().func_74762_e("linkY");
            final int lz = item.func_77978_p().func_74762_e("linkZ");
            final int ldim = item.func_77978_p().func_74762_e("linkDim");
            String desc = "" + ldim;
            final World world = (World)DimensionManager.getWorld(ldim);
            if (world != null) {
                desc = world.field_73011_w.func_186058_p().func_186065_b();
            }
            list.add("Linked to " + lx + "," + ly + "," + lz + " in " + desc);
        }
    }
}
