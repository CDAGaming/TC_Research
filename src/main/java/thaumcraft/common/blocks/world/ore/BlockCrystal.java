package thaumcraft.common.blocks.world.ore;

import thaumcraft.api.aspects.*;
import net.minecraft.block.material.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.config.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.*;
import thaumcraft.api.*;
import net.minecraft.world.*;
import thaumcraft.api.aura.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.api.blocks.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.properties.*;
import net.minecraftforge.common.property.*;

public class BlockCrystal extends Block
{
    public static final PropertyInteger SIZE;
    public static final PropertyInteger GENERATION;
    public static final IUnlistedProperty<Boolean> NORTH;
    public static final IUnlistedProperty<Boolean> EAST;
    public static final IUnlistedProperty<Boolean> SOUTH;
    public static final IUnlistedProperty<Boolean> WEST;
    public static final IUnlistedProperty<Boolean> UP;
    public static final IUnlistedProperty<Boolean> DOWN;
    public Aspect aspect;
    
    public BlockCrystal(final String name, final Aspect aspect) {
        super(Material.field_151592_s);
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
        this.aspect = aspect;
        this.func_149711_c(0.25f);
        this.func_149672_a(SoundsTC.CRYSTAL);
        this.func_149675_a(true);
        this.func_149647_a(ConfigItems.TABTC);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockCrystal.SIZE, (Comparable)0).func_177226_a((IProperty)BlockCrystal.GENERATION, (Comparable)1));
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.CRYSTAL;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
    
    protected boolean func_149700_E() {
        return false;
    }
    
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        final List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int count = this.getGrowth(state) + 1, i = 0; i < count; ++i) {
            ret.add(ThaumcraftApiHelper.makeCrystal(this.aspect));
        }
        return ret;
    }
    
    public void func_180650_b(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.field_72995_K && rand.nextInt(3 + this.getGeneration(state)) == 0) {
            final int threshold = 10;
            final int growth = this.getGrowth(state);
            int generation = this.getGeneration(state);
            if (this.aspect != Aspect.FLUX && AuraHelper.getVis(worldIn, pos) <= threshold) {
                if (growth > 0) {
                    worldIn.func_175656_a(pos, state.func_177226_a((IProperty)BlockCrystal.SIZE, (Comparable)(growth - 1)));
                    AuraHelper.addVis(worldIn, pos, threshold);
                }
                else if (BlockUtils.isBlockTouching((IBlockAccess)worldIn, pos, this)) {
                    worldIn.func_175698_g(pos);
                    AuraHandler.addVis(worldIn, pos, threshold);
                    AuraHandler.addFlux(worldIn, pos, 1.0f);
                }
            }
            else if (AuraHelper.getVis(worldIn, pos) > AuraHandler.getAuraBase(worldIn, pos) + threshold) {
                if (growth < 3 && growth < 5 - generation + pos.func_177986_g() % 3L) {
                    if (AuraHelper.drainVis(worldIn, pos, threshold, false) > 0.0f) {
                        worldIn.func_175656_a(pos, state.func_177226_a((IProperty)BlockCrystal.SIZE, (Comparable)(growth + 1)));
                    }
                }
                else if (generation < 4) {
                    final BlockPos p2 = spreadCrystal(worldIn, pos);
                    if (p2 != null && AuraHelper.drainVis(worldIn, pos, threshold, false) > 0.0f) {
                        if (rand.nextInt(6) == 0) {
                            --generation;
                        }
                        worldIn.func_175656_a(p2, this.func_176223_P().func_177226_a((IProperty)BlockCrystal.GENERATION, (Comparable)(generation + 1)));
                        AuraHandler.addFlux(worldIn, pos, 1.0f);
                    }
                }
            }
            else if (this.aspect != Aspect.FLUX && AuraHelper.getFlux(worldIn, pos) > threshold && AuraHelper.getFlux(worldIn, pos) > AuraHelper.getVis(worldIn, pos) && AuraHelper.getFlux(worldIn, pos) > AuraHandler.getAuraBase(worldIn, pos) / 2 && AuraHandler.drainFlux(worldIn, pos, threshold, false) > 0.0f) {
                worldIn.func_175656_a(pos, BlocksTC.crystalTaint.func_176203_a(this.func_176201_c(state)));
            }
        }
    }
    
    public static BlockPos spreadCrystal(final World world, final BlockPos pos) {
        final int xx = pos.func_177958_n() + world.field_73012_v.nextInt(3) - 1;
        final int yy = pos.func_177956_o() + world.field_73012_v.nextInt(3) - 1;
        final int zz = pos.func_177952_p() + world.field_73012_v.nextInt(3) - 1;
        final BlockPos t = new BlockPos(xx, yy, zz);
        if (t.equals((Object)pos)) {
            return null;
        }
        final IBlockState bs = world.func_180495_p(t);
        final Material bm = bs.func_185904_a();
        if (!bm.func_76224_d() && (world.func_175623_d(t) || bs.func_177230_c().func_176200_f((IBlockAccess)world, t)) && world.field_73012_v.nextInt(16) == 0 && BlockUtils.isBlockTouching((IBlockAccess)world, t, Material.field_151576_e, true)) {
            return t;
        }
        return null;
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        if (!BlockUtils.isBlockTouching((IBlockAccess)worldIn, pos, Material.field_151576_e, true)) {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing o) {
        return false;
    }
    
    private boolean drawAt(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState fbs = worldIn.func_180495_p(pos);
        return fbs.func_185904_a() == Material.field_151576_e && fbs.func_177230_c().isSideSolid(fbs, worldIn, pos, side.func_176734_d());
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState bs, final IBlockAccess iblockaccess, final BlockPos pos) {
        final IBlockState state = this.getExtendedState(bs, iblockaccess, pos);
        if (state instanceof IExtendedBlockState) {
            final IExtendedBlockState es = (IExtendedBlockState)state;
            int c = 0;
            if (es.getValue((IUnlistedProperty)BlockCrystal.UP)) {
                ++c;
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.DOWN)) {
                ++c;
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.EAST)) {
                ++c;
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.WEST)) {
                ++c;
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.SOUTH)) {
                ++c;
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.NORTH)) {
                ++c;
            }
            if (c > 1) {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.UP)) {
                return new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.DOWN)) {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.EAST)) {
                return new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.WEST)) {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.SOUTH)) {
                return new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
            }
            if (es.getValue((IUnlistedProperty)BlockCrystal.NORTH)) {
                return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
            }
        }
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return null;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public int getLightValue(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final IBlockState st = world.func_180495_p(pos);
        return 2 + (st.func_177230_c().func_176201_c(st) & 0x3) * 3;
    }
    
    public int func_185484_c(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final int i = source.func_175626_b(pos, state.getLightValue(source, pos));
        final int j = 180;
        final int k = i & 0xFF;
        final int l = j & 0xFF;
        final int i2 = i >> 16 & 0xFF;
        final int j2 = j >> 16 & 0xFF;
        return ((k > l) ? k : l) | ((i2 > j2) ? i2 : j2) << 16;
    }
    
    protected BlockStateContainer func_180661_e() {
        final IProperty[] listedProperties = { BlockCrystal.SIZE, BlockCrystal.GENERATION };
        final IUnlistedProperty[] unlistedProperties = { BlockCrystal.UP, BlockCrystal.DOWN, BlockCrystal.NORTH, BlockCrystal.EAST, BlockCrystal.WEST, BlockCrystal.SOUTH };
        return (BlockStateContainer)new ExtendedBlockState((Block)this, listedProperties, unlistedProperties);
    }
    
    public IBlockState getExtendedState(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        if (state instanceof IExtendedBlockState) {
            final IExtendedBlockState retval = (IExtendedBlockState)state;
            return (IBlockState)retval.withProperty((IUnlistedProperty)BlockCrystal.UP, (Object)this.drawAt(world, pos.func_177984_a(), EnumFacing.UP)).withProperty((IUnlistedProperty)BlockCrystal.DOWN, (Object)this.drawAt(world, pos.func_177977_b(), EnumFacing.DOWN)).withProperty((IUnlistedProperty)BlockCrystal.NORTH, (Object)this.drawAt(world, pos.func_177978_c(), EnumFacing.NORTH)).withProperty((IUnlistedProperty)BlockCrystal.EAST, (Object)this.drawAt(world, pos.func_177974_f(), EnumFacing.EAST)).withProperty((IUnlistedProperty)BlockCrystal.SOUTH, (Object)this.drawAt(world, pos.func_177968_d(), EnumFacing.SOUTH)).withProperty((IUnlistedProperty)BlockCrystal.WEST, (Object)this.drawAt(world, pos.func_177976_e(), EnumFacing.WEST));
        }
        return state;
    }
    
    public IBlockState func_176221_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state;
    }
    
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockCrystal.SIZE, (Comparable)(meta & 0x3)).func_177226_a((IProperty)BlockCrystal.GENERATION, (Comparable)(1 + (meta >> 2 & 0x3)));
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        i |= (int)state.func_177229_b((IProperty)BlockCrystal.SIZE);
        i |= (int)state.func_177229_b((IProperty)BlockCrystal.GENERATION) - 1 << 2;
        return i;
    }
    
    public int getGrowth(final IBlockState state) {
        return this.func_176201_c(state) & 0x3;
    }
    
    public int getGeneration(final IBlockState state) {
        return 1 + (this.func_176201_c(state) >> 2);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        list.add((Object)new ItemStack((Block)this, 1, 0));
    }
    
    public boolean canSilkHarvest(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player) {
        return false;
    }
    
    public boolean func_176196_c(final World worldIn, final BlockPos pos) {
        return BlockUtils.isBlockTouching((IBlockAccess)worldIn, pos, Material.field_151576_e, true) && super.func_176196_c(worldIn, pos);
    }
    
    static {
        SIZE = PropertyInteger.func_177719_a("size", 0, 3);
        GENERATION = PropertyInteger.func_177719_a("gen", 1, 4);
        NORTH = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("north"));
        EAST = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("east"));
        SOUTH = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("south"));
        WEST = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("west"));
        UP = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("up"));
        DOWN = (IUnlistedProperty)new Properties.PropertyAdapter((IProperty)PropertyBool.func_177716_a("down"));
    }
}
