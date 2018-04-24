package thaumcraft.common.blocks.devices;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.config.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;

public class BlockHungryChest extends BlockContainer
{
    public static final PropertyDirection FACING;
    private final Random rand;
    
    public BlockHungryChest() {
        super(Material.field_151575_d);
        this.rand = new Random();
        this.func_149663_c("hungry_chest");
        this.setRegistryName("thaumcraft", "hungry_chest");
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockHungryChest.FACING, (Comparable)EnumFacing.NORTH));
        this.func_149711_c(2.5f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149647_a(ConfigItems.TABTC);
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.func_176223_P().func_177226_a((IProperty)BlockHungryChest.FACING, (Comparable)placer.func_174811_aO());
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final EnumFacing enumfacing = EnumFacing.func_176731_b(MathHelper.func_76128_c(placer.field_70177_z * 4.0f / 360.0f + 0.5) & 0x3).func_176734_d();
        state = state.func_177226_a((IProperty)BlockHungryChest.FACING, (Comparable)enumfacing);
        worldIn.func_180501_a(pos, state, 3);
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (tileentity instanceof IInventory) {
            InventoryHelper.func_180175_a(worldIn, pos, (IInventory)tileentity);
            worldIn.func_175666_e(pos, (Block)this);
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        final TileEntity tileentity = world.func_175625_s(pos);
        if (tileentity instanceof IInventory) {
            player.func_71007_a((IInventory)tileentity);
        }
        return true;
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        final Object var10 = world.func_175625_s(pos);
        if (var10 == null) {
            return;
        }
        if (world.field_72995_K) {
            return;
        }
        if (entity instanceof EntityItem && !entity.field_70128_L) {
            final ItemStack leftovers = InventoryUtils.insertStackAt(world, pos, EnumFacing.UP, ((EntityItem)entity).func_92059_d(), false);
            if (leftovers == null || leftovers.func_190926_b() || leftovers.func_190916_E() != ((EntityItem)entity).func_92059_d().func_190916_E()) {
                entity.func_184185_a(SoundEvents.field_187537_bA, 0.25f, (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2f + 1.0f);
            }
            if (leftovers != null && !leftovers.func_190926_b()) {
                ((EntityItem)entity).func_92058_a(leftovers);
            }
            else {
                entity.func_70106_y();
            }
            ((TileHungryChest)var10).func_70296_d();
        }
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World worldIn, final BlockPos pos) {
        final Object var10 = worldIn.func_175625_s(pos);
        if (var10 instanceof TileHungryChest) {
            return Container.func_94526_b((IInventory)var10);
        }
        return 0;
    }
    
    public IBlockState func_176203_a(final int meta) {
        EnumFacing enumfacing = EnumFacing.func_82600_a(meta);
        if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockHungryChest.FACING, (Comparable)enumfacing);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockHungryChest.FACING)).func_176745_a();
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockHungryChest.FACING });
    }
    
    public TileEntity func_149915_a(final World par1World, final int m) {
        return (TileEntity)new TileHungryChest();
    }
    
    static {
        FACING = PropertyDirection.func_177712_a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
}
