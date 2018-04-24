package thaumcraft.common.blocks.world.plants;

import net.minecraft.block.properties.*;
import thaumcraft.common.config.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.material.*;
import thaumcraft.api.blocks.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.*;

public class BlockLeavesTC extends BlockLeaves
{
    public BlockLeavesTC(final String name) {
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockLeavesTC.field_176236_b, (Comparable)true).func_177226_a((IProperty)BlockLeavesTC.field_176237_a, (Comparable)true));
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 60;
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 30;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return Blocks.field_150362_t.func_180664_k();
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return Blocks.field_150362_t.func_149662_c(state);
    }
    
    public boolean func_176225_a(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side) {
        this.func_150122_b(!this.func_149662_c(blockState));
        return super.func_176225_a(blockState, blockAccess, pos, side);
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return (state.func_177230_c() == BlocksTC.leafSilverwood) ? MapColor.field_151674_s : super.func_180659_g(state, worldIn, pos);
    }
    
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        list.add((Object)new ItemStack((Block)this));
    }
    
    protected ItemStack func_180643_i(final IBlockState state) {
        return new ItemStack((Block)this);
    }
    
    public void func_180650_b(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.field_72995_K && state.func_177230_c() == BlocksTC.leafSilverwood && (boolean)state.func_177229_b((IProperty)BlockLeavesTC.field_176237_a) && AuraHandler.getVis(worldIn, pos) < AuraHandler.getAuraBase(worldIn, pos)) {
            AuraHandler.addVis(worldIn, pos, 0.01f);
        }
        super.func_180650_b(worldIn, pos, state, rand);
    }
    
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockLeavesTC.field_176237_a, (Comparable)((meta & 0x4) == 0x0)).func_177226_a((IProperty)BlockLeavesTC.field_176236_b, (Comparable)((meta & 0x8) > 0));
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        if (!(boolean)state.func_177229_b((IProperty)BlockLeavesTC.field_176237_a)) {
            i |= 0x4;
        }
        if (state.func_177229_b((IProperty)BlockLeavesTC.field_176236_b)) {
            i |= 0x8;
        }
        return i;
    }
    
    protected int func_176232_d(final IBlockState state) {
        return (state.func_177230_c() == BlocksTC.leafSilverwood) ? 44 : 200;
    }
    
    protected void func_176234_a(final World worldIn, final BlockPos pos, final IBlockState state, final int chance) {
        if (state.func_177230_c() == BlocksTC.leafSilverwood && worldIn.field_73012_v.nextInt((int)(chance * 0.75)) == 0) {
            func_180635_a(worldIn, pos, new ItemStack(ItemsTC.nuggets, 1, 5));
        }
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return (state.func_177230_c() == BlocksTC.leafSilverwood) ? Item.func_150898_a(BlocksTC.saplingSilverwood) : Item.func_150898_a(BlocksTC.saplingGreatwood);
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockLeavesTC.field_176236_b, BlockLeavesTC.field_176237_a });
    }
    
    public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
        final IBlockState state = world.func_180495_p(pos);
        return new ArrayList<ItemStack>(Arrays.asList(new ItemStack((Block)this)));
    }
    
    public BlockPlanks.EnumType func_176233_b(final int meta) {
        return null;
    }
}
