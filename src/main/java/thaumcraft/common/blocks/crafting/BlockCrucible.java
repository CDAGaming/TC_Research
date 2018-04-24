package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.entities.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.capability.*;
import thaumcraft.api.casters.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockCrucible extends BlockTCTile
{
    private int delay;
    protected static final AxisAlignedBB AABB_LEGS;
    protected static final AxisAlignedBB AABB_WALL_NORTH;
    protected static final AxisAlignedBB AABB_WALL_SOUTH;
    protected static final AxisAlignedBB AABB_WALL_EAST;
    protected static final AxisAlignedBB AABB_WALL_WEST;
    
    public BlockCrucible() {
        super(Material.field_151573_f, TileCrucible.class, "crucible");
        this.delay = 0;
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    public void func_180634_a(final World world, final BlockPos pos, final IBlockState state, final Entity entity) {
        if (!world.field_72995_K) {
            final TileCrucible tile = (TileCrucible)world.func_175625_s(pos);
            if (tile != null && entity instanceof EntityItem && !(entity instanceof EntitySpecialItem) && tile.heat > 150 && tile.tank.getFluidAmount() > 0) {
                tile.attemptSmelt((EntityItem)entity);
            }
            else {
                ++this.delay;
                if (this.delay < 10) {
                    return;
                }
                this.delay = 0;
                if (entity instanceof EntityLivingBase && tile != null && tile.heat > 150 && tile.tank.getFluidAmount() > 0) {
                    entity.func_70097_a(DamageSource.field_76372_a, 1.0f);
                    world.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.4f, 2.0f + world.field_73012_v.nextFloat() * 0.4f);
                }
            }
        }
        super.func_180634_a(world, pos, state, entity);
    }
    
    public void func_185477_a(final IBlockState state, final World worldIn, final BlockPos pos, final AxisAlignedBB AABB, final List<AxisAlignedBB> list, final Entity p_185477_6_, final boolean isActualState) {
        func_185492_a(pos, AABB, (List)list, BlockCrucible.AABB_LEGS);
        func_185492_a(pos, AABB, (List)list, BlockCrucible.AABB_WALL_WEST);
        func_185492_a(pos, AABB, (List)list, BlockCrucible.AABB_WALL_NORTH);
        func_185492_a(pos, AABB, (List)list, BlockCrucible.AABB_WALL_EAST);
        func_185492_a(pos, AABB, (List)list, BlockCrucible.AABB_WALL_SOUTH);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return BlockCrucible.field_185505_j;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te != null && te instanceof TileCrucible) {
            ((TileCrucible)te).spillRemnants();
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!world.field_72995_K) {
            final FluidStack fs = FluidUtil.getFluidContained(player.func_184586_b(hand));
            if (fs != null) {
                final FluidStack fluidStack = fs;
                final Fluid water = FluidRegistry.WATER;
                final Fluid water2 = FluidRegistry.WATER;
                if (fluidStack.containsFluid(new FluidStack(water, 1000))) {
                    final TileEntity te = world.func_175625_s(pos);
                    if (te != null && te instanceof TileCrucible) {
                        final TileCrucible tile = (TileCrucible)te;
                        if (tile.tank.getFluidAmount() < tile.tank.getCapacity()) {
                            if (FluidUtil.interactWithFluidHandler(player, hand, (IFluidHandler)tile.tank)) {
                                player.field_71069_bz.func_75142_b();
                                te.func_70296_d();
                                world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                                world.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.33f, 1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.3f);
                            }
                            return true;
                        }
                    }
                    return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
                }
            }
            if (!player.func_70093_af() && !(player.func_184586_b(hand).func_77973_b() instanceof ICaster) && side == EnumFacing.UP) {
                final TileEntity te = world.func_175625_s(pos);
                if (te != null && te instanceof TileCrucible) {
                    final TileCrucible tile = (TileCrucible)te;
                    final ItemStack ti = player.func_184586_b(hand).func_77946_l();
                    ti.func_190920_e(1);
                    if (tile.heat > 150 && tile.tank.getFluidAmount() > 0 && tile.attemptSmelt(ti, player.func_70005_c_()) == null) {
                        player.field_71071_by.func_70298_a(player.field_71071_by.field_70461_c, 1);
                        return true;
                    }
                }
            }
            else if (player.func_184586_b(hand).func_190926_b() && player.func_70093_af()) {
                final TileEntity te = world.func_175625_s(pos);
                if (te != null && te instanceof TileCrucible) {
                    final TileCrucible tile = (TileCrucible)te;
                    tile.spillRemnants();
                    return true;
                }
            }
            return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
        return true;
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileCrucible) {
            final float n = ((TileCrucible)te).aspects.visSize();
            ((TileCrucible)te).getClass();
            final float r = n / 1000.0f;
            return MathHelper.func_76141_d(r * 14.0f) + ((((TileCrucible)te).aspects.visSize() > 0) ? 1 : 0);
        }
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World w, final BlockPos pos, final Random r) {
        if (r.nextInt(10) == 0) {
            final TileEntity te = w.func_175625_s(pos);
            if (te != null && te instanceof TileCrucible && ((TileCrucible)te).tank.getFluidAmount() > 0 && ((TileCrucible)te).heat > 150) {
                w.func_184134_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundEvents.field_187662_cZ, SoundCategory.BLOCKS, 0.1f + r.nextFloat() * 0.1f, 1.2f + r.nextFloat() * 0.2f, false);
            }
        }
    }
    
    static {
        AABB_LEGS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.3125, 1.0);
        AABB_WALL_NORTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.125);
        AABB_WALL_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.875, 1.0, 1.0, 1.0);
        AABB_WALL_EAST = new AxisAlignedBB(0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
        AABB_WALL_WEST = new AxisAlignedBB(0.0, 0.0, 0.0, 0.125, 1.0, 1.0);
    }
}
