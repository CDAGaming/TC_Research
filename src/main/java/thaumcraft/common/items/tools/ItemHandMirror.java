package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.text.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraft.nbt.*;
import thaumcraft.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;

public class ItemHandMirror extends ItemTCBase
{
    public ItemHandMirror() {
        super("hand_mirror", new String[0]);
        this.func_77625_d(1);
    }
    
    public boolean func_77651_p() {
        return true;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_77636_d(final ItemStack stack1) {
        return stack1.func_77942_o();
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float par8, final float par9, final float par10, final EnumHand hand) {
        final Block bi = world.func_180495_p(pos).func_177230_c();
        if (bi != BlocksTC.mirror) {
            return EnumActionResult.FAIL;
        }
        if (world.field_72995_K) {
            player.func_184609_a(hand);
            return super.onItemUseFirst(player, world, pos, side, par8, par9, par10, hand);
        }
        final TileEntity tm = world.func_175625_s(pos);
        if (tm != null && tm instanceof TileMirror) {
            player.func_184586_b(hand).func_77983_a("linkX", (NBTBase)new NBTTagInt(pos.func_177958_n()));
            player.func_184586_b(hand).func_77983_a("linkY", (NBTBase)new NBTTagInt(pos.func_177956_o()));
            player.func_184586_b(hand).func_77983_a("linkZ", (NBTBase)new NBTTagInt(pos.func_177952_p()));
            player.func_184586_b(hand).func_77983_a("linkDim", (NBTBase)new NBTTagInt(world.field_73011_w.getDimension()));
            world.func_184148_a((EntityPlayer)null, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundsTC.jar, SoundCategory.BLOCKS, 1.0f, 2.0f);
            player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.handmirrorlinked", new Object[0]));
            player.field_71069_bz.func_75142_b();
        }
        return EnumActionResult.PASS;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.field_72995_K && player.func_184586_b(hand).func_77942_o()) {
            final int lx = player.func_184586_b(hand).func_77978_p().func_74762_e("linkX");
            final int ly = player.func_184586_b(hand).func_77978_p().func_74762_e("linkY");
            final int lz = player.func_184586_b(hand).func_77978_p().func_74762_e("linkZ");
            final int ldim = player.func_184586_b(hand).func_77978_p().func_74762_e("linkDim");
            final World targetWorld = (World)DimensionManager.getWorld(ldim);
            if (targetWorld == null) {
                return (ActionResult<ItemStack>)super.func_77659_a(world, player, hand);
            }
            final TileEntity te = targetWorld.func_175625_s(new BlockPos(lx, ly, lz));
            if (te == null || !(te instanceof TileMirror)) {
                player.func_184586_b(hand).func_77982_d((NBTTagCompound)null);
                player.func_184185_a(SoundsTC.zap, 1.0f, 0.8f);
                player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.handmirrorerror", new Object[0]));
                return (ActionResult<ItemStack>)super.func_77659_a(world, player, hand);
            }
            player.openGui((Object)Thaumcraft.instance, 4, world, MathHelper.func_76128_c(player.field_70165_t), MathHelper.func_76128_c(player.field_70163_u), MathHelper.func_76128_c(player.field_70161_v));
        }
        return (ActionResult<ItemStack>)super.func_77659_a(world, player, hand);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o()) {
            final int lx = stack.func_77978_p().func_74762_e("linkX");
            final int ly = stack.func_77978_p().func_74762_e("linkY");
            final int lz = stack.func_77978_p().func_74762_e("linkZ");
            final int ldim = stack.func_77978_p().func_74762_e("linkDim");
            tooltip.add(I18n.func_74838_a("tc.handmirrorlinkedto") + " " + lx + "," + ly + "," + lz + " in " + ldim);
        }
    }
    
    public static boolean transport(final ItemStack mirror, ItemStack items, final EntityPlayer player, final World worldObj) {
        if (!mirror.func_77942_o()) {
            return false;
        }
        final int lx = mirror.func_77978_p().func_74762_e("linkX");
        final int ly = mirror.func_77978_p().func_74762_e("linkY");
        final int lz = mirror.func_77978_p().func_74762_e("linkZ");
        final int ldim = mirror.func_77978_p().func_74762_e("linkDim");
        final World targetWorld = (World)DimensionManager.getWorld(ldim);
        if (targetWorld == null) {
            return false;
        }
        final TileEntity te = targetWorld.func_175625_s(new BlockPos(lx, ly, lz));
        if (te == null || !(te instanceof TileMirror)) {
            mirror.func_77982_d((NBTTagCompound)null);
            player.func_184185_a(SoundsTC.zap, 1.0f, 0.8f);
            player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.handmirrorerror", new Object[0]));
            return false;
        }
        final TileMirror tm = (TileMirror)te;
        if (tm.transportDirect(items)) {
            items = null;
            player.func_184185_a(SoundEvents.field_187534_aX, 0.1f, 1.0f);
        }
        return true;
    }
}
