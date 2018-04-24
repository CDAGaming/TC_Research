package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import net.minecraft.block.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import thaumcraft.client.fx.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.enchantment.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;

public class ItemElementalShovel extends ItemSpade implements IArchitect, IThaumcraftItems
{
    private static final Block[] isEffective;
    EnumFacing side;
    
    public ItemElementalShovel(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.side = EnumFacing.DOWN;
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("elemental_shovel");
        this.func_77655_b("elemental_shovel");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public Item getItem() {
        return (Item)this;
    }
    
    public String[] getVariantNames() {
        return new String[] { "normal" };
    }
    
    public int[] getVariantMeta() {
        return new int[] { 0 };
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        return new ModelResourceLocation("thaumcraft:" + variant);
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        return (Set<String>)ImmutableSet.of((Object)"shovel");
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumActionResult func_180614_a(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing side, final float par8, final float par9, final float par10) {
        final IBlockState bs = world.func_180495_p(pos);
        final TileEntity te = world.func_175625_s(pos);
        if (te == null) {
            for (int aa = -1; aa <= 1; ++aa) {
                for (int bb = -1; bb <= 1; ++bb) {
                    int xx = 0;
                    int yy = 0;
                    int zz = 0;
                    final byte o = getOrientation(player.func_184586_b(hand));
                    if (o == 1) {
                        yy = bb;
                        if (side.ordinal() <= 1) {
                            final int l = MathHelper.func_76128_c(player.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
                            if (l == 0 || l == 2) {
                                xx = aa;
                            }
                            else {
                                zz = aa;
                            }
                        }
                        else if (side.ordinal() <= 3) {
                            zz = aa;
                        }
                        else {
                            xx = aa;
                        }
                    }
                    else if (o == 2) {
                        if (side.ordinal() <= 1) {
                            final int l = MathHelper.func_76128_c(player.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
                            yy = bb;
                            if (l == 0 || l == 2) {
                                xx = aa;
                            }
                            else {
                                zz = aa;
                            }
                        }
                        else {
                            zz = bb;
                            xx = aa;
                        }
                    }
                    else if (side.ordinal() <= 1) {
                        xx = aa;
                        zz = bb;
                    }
                    else if (side.ordinal() <= 3) {
                        xx = aa;
                        yy = bb;
                    }
                    else {
                        zz = aa;
                        yy = bb;
                    }
                    final BlockPos p2 = pos.func_177972_a(side).func_177982_a(xx, yy, zz);
                    final IBlockState b2 = world.func_180495_p(p2);
                    if (world.func_175623_d(p2) || b2 == Blocks.field_150395_bd || b2 == Blocks.field_150329_H || b2.func_185904_a() == Material.field_151586_h || b2 == Blocks.field_150330_I || b2.func_177230_c().func_176200_f((IBlockAccess)world, p2)) {
                        if (player.field_71075_bZ.field_75098_d || InventoryUtils.consumePlayerItem(player, Item.func_150898_a(bs.func_177230_c()), bs.func_177230_c().func_176201_c(bs))) {
                            world.func_184134_a((double)p2.func_177958_n(), (double)p2.func_177956_o(), (double)p2.func_177952_p(), bs.func_177230_c().func_185467_w().func_185845_c(), SoundCategory.BLOCKS, 0.6f, 0.9f + world.field_73012_v.nextFloat() * 0.2f, false);
                            world.func_175656_a(p2, bs);
                            player.func_184586_b(hand).func_77972_a(1, (EntityLivingBase)player);
                            if (world.field_72995_K) {
                                FXDispatcher.INSTANCE.drawBamf(p2, 8401408, false, false, side);
                            }
                            player.func_184609_a(hand);
                        }
                        else if (bs.func_177230_c() == Blocks.field_150349_c && (player.field_71075_bZ.field_75098_d || InventoryUtils.consumePlayerItem(player, Item.func_150898_a(Blocks.field_150346_d), 0))) {
                            world.func_184134_a((double)p2.func_177958_n(), (double)p2.func_177956_o(), (double)p2.func_177952_p(), bs.func_177230_c().func_185467_w().func_185845_c(), SoundCategory.BLOCKS, 0.6f, 0.9f + world.field_73012_v.nextFloat() * 0.2f, false);
                            world.func_175656_a(p2, Blocks.field_150346_d.func_176223_P());
                            player.func_184586_b(hand).func_77972_a(1, (EntityLivingBase)player);
                            if (world.field_72995_K) {
                                FXDispatcher.INSTANCE.drawBamf(p2, 8401408, false, false, side);
                            }
                            player.func_184609_a(hand);
                        }
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }
    
    private boolean isEffectiveAgainst(final Block block) {
        for (int var3 = 0; var3 < ItemElementalShovel.isEffective.length; ++var3) {
            if (ItemElementalShovel.isEffective[var3] == block) {
                return true;
            }
        }
        return false;
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack w1 = new ItemStack((Item)this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.DESTRUCTIVE, 1);
            items.add((Object)w1);
        }
    }
    
    public ArrayList<BlockPos> getArchitectBlocks(final ItemStack focusstack, final World world, final BlockPos pos, final EnumFacing side, final EntityPlayer player) {
        final ArrayList<BlockPos> b = new ArrayList<BlockPos>();
        if (!player.func_70093_af()) {
            return b;
        }
        for (int aa = -1; aa <= 1; ++aa) {
            for (int bb = -1; bb <= 1; ++bb) {
                int xx = 0;
                int yy = 0;
                int zz = 0;
                final byte o = getOrientation(focusstack);
                if (o == 1) {
                    yy = bb;
                    if (side.ordinal() <= 1) {
                        final int l = MathHelper.func_76128_c(player.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
                        if (l == 0 || l == 2) {
                            xx = aa;
                        }
                        else {
                            zz = aa;
                        }
                    }
                    else if (side.ordinal() <= 3) {
                        zz = aa;
                    }
                    else {
                        xx = aa;
                    }
                }
                else if (o == 2) {
                    if (side.ordinal() <= 1) {
                        final int l = MathHelper.func_76128_c(player.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3;
                        yy = bb;
                        if (l == 0 || l == 2) {
                            xx = aa;
                        }
                        else {
                            zz = aa;
                        }
                    }
                    else {
                        zz = bb;
                        xx = aa;
                    }
                }
                else if (side.ordinal() <= 1) {
                    xx = aa;
                    zz = bb;
                }
                else if (side.ordinal() <= 3) {
                    xx = aa;
                    yy = bb;
                }
                else {
                    zz = aa;
                    yy = bb;
                }
                final BlockPos p2 = pos.func_177972_a(side).func_177982_a(xx, yy, zz);
                final IBlockState b2 = world.func_180495_p(p2);
                if (world.func_175623_d(p2) || b2 == Blocks.field_150395_bd || b2 == Blocks.field_150329_H || b2.func_185904_a() == Material.field_151586_h || b2 == Blocks.field_150330_I || b2.func_177230_c().func_176200_f((IBlockAccess)world, p2)) {
                    b.add(p2);
                }
            }
        }
        return b;
    }
    
    public boolean showAxis(final ItemStack stack, final World world, final EntityPlayer player, final EnumFacing side, final EnumAxis axis) {
        return false;
    }
    
    public static byte getOrientation(final ItemStack stack) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("or")) {
            return stack.func_77978_p().func_74771_c("or");
        }
        return 0;
    }
    
    public static void setOrientation(final ItemStack stack, final byte o) {
        if (!stack.func_77942_o()) {
            stack.func_77982_d(new NBTTagCompound());
        }
        if (stack.func_77942_o()) {
            stack.func_77978_p().func_74774_a("or", (byte)(o % 3));
        }
    }
    
    public RayTraceResult getArchitectMOP(final ItemStack stack, final World world, final EntityLivingBase player) {
        return Utils.rayTrace(world, (Entity)player, false);
    }
    
    public boolean useBlockHighlight(final ItemStack stack) {
        return true;
    }
    
    static {
        isEffective = new Block[] { Blocks.field_150349_c, Blocks.field_150346_d, Blocks.field_150354_m, Blocks.field_150351_n, Blocks.field_150431_aC, Blocks.field_150433_aE, Blocks.field_150435_aG, Blocks.field_150458_ak, Blocks.field_150425_aM, Blocks.field_150391_bh };
    }
}
