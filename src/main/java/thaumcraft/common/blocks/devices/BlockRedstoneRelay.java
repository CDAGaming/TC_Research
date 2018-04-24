package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;
import java.util.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class BlockRedstoneRelay extends BlockTCDevice implements IBlockFacingHorizontal, IBlockEnabled
{
    private RayTracer rayTracer;
    
    public BlockRedstoneRelay() {
        super(Material.field_151594_q, TileRedstoneRelay.class, "redstone_relay");
        this.rayTracer = new RayTracer();
        this.func_149711_c(0.0f);
        this.func_149752_b(0.0f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149649_H();
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_176196_c(final World worldIn, final BlockPos pos) {
        return worldIn.func_180495_p(pos.func_177977_b()).func_185917_h() && super.func_176196_c(worldIn, pos);
    }
    
    public boolean canBlockStay(final World worldIn, final BlockPos pos) {
        return worldIn.func_180495_p(pos.func_177977_b()).func_185917_h();
    }
    
    public void func_180645_a(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!player.field_71075_bZ.field_75099_e) {
            return false;
        }
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, pos);
        if (hit == null) {
            return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileRedstoneRelay) {
            if (hit.subHit == 0) {
                ((TileRedstoneRelay)tile).increaseOut();
                world.func_184133_a((EntityPlayer)null, pos, SoundsTC.key, SoundCategory.BLOCKS, 0.5f, 1.0f);
                this.updateState(world, pos, state);
                this.notifyNeighbors(world, pos, state);
            }
            if (hit.subHit == 1) {
                ((TileRedstoneRelay)tile).increaseIn();
                world.func_184133_a((EntityPlayer)null, pos, SoundsTC.key, SoundCategory.BLOCKS, 0.5f, 1.0f);
                this.updateState(world, pos, state);
                this.notifyNeighbors(world, pos, state);
            }
            return true;
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
    
    public void func_180650_b(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final boolean flag = this.shouldBePowered(worldIn, pos, state);
        if (this.isPowered(state) && !flag) {
            worldIn.func_180501_a(pos, this.getUnpoweredState(state), 2);
            this.notifyNeighbors(worldIn, pos, state);
        }
        else if (!this.isPowered(state)) {
            worldIn.func_180501_a(pos, this.getPoweredState(state), 2);
            this.notifyNeighbors(worldIn, pos, state);
            if (!flag) {
                worldIn.func_175654_a(pos, this.getPoweredState(state).func_177230_c(), this.getTickDelay(state), -1);
            }
        }
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.func_180663_b(worldIn, pos, state);
        this.notifyNeighbors(worldIn, pos, state);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_176225_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side.func_176740_k() != EnumFacing.Axis.Y;
    }
    
    protected boolean isPowered(final IBlockState state) {
        return BlockStateUtils.isEnabled(state);
    }
    
    public int func_176211_b(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return this.func_180656_a(state, worldIn, pos, side);
    }
    
    public int func_180656_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return this.isPowered(state) ? ((state.func_177229_b((IProperty)BlockRedstoneRelay.FACING) == side) ? this.getActiveSignal(worldIn, pos, state) : 0) : 0;
    }
    
    @Override
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        if (this.canBlockStay(worldIn, pos)) {
            this.updateState(worldIn, pos, state);
        }
        else {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
            for (final EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.func_175685_c(pos.func_177972_a(enumfacing), (Block)this, false);
            }
        }
    }
    
    @Override
    protected void updateState(final World worldIn, final BlockPos pos, final IBlockState state) {
        final boolean flag = this.shouldBePowered(worldIn, pos, state);
        if (((this.isPowered(state) && !flag) || (!this.isPowered(state) && flag)) && !worldIn.func_175691_a(pos, (Block)this)) {
            byte b0 = -1;
            if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
                b0 = -3;
            }
            else if (this.isPowered(state)) {
                b0 = -2;
            }
            worldIn.func_175654_a(pos, (Block)this, this.getTickDelay(state), (int)b0);
        }
    }
    
    protected boolean shouldBePowered(final World worldIn, final BlockPos pos, final IBlockState state) {
        int pr = 1;
        final TileEntity tile = worldIn.func_175625_s(pos);
        if (tile != null && tile instanceof TileRedstoneRelay) {
            pr = ((TileRedstoneRelay)tile).getIn();
        }
        return this.calculateInputStrength(worldIn, pos, state) >= pr;
    }
    
    protected int calculateInputStrength(final World worldIn, final BlockPos pos, final IBlockState state) {
        final EnumFacing enumfacing = (EnumFacing)state.func_177229_b((IProperty)BlockRedstoneRelay.FACING);
        final BlockPos blockpos1 = pos.func_177972_a(enumfacing);
        final int i = worldIn.func_175651_c(blockpos1, enumfacing);
        if (i >= 15) {
            return i;
        }
        final IBlockState iblockstate1 = worldIn.func_180495_p(blockpos1);
        return Math.max(i, (iblockstate1.func_177230_c() == Blocks.field_150488_af) ? ((int)iblockstate1.func_177229_b((IProperty)BlockRedstoneWire.field_176351_O)) : 0);
    }
    
    protected int getPowerOnSides(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state) {
        final EnumFacing enumfacing = (EnumFacing)state.func_177229_b((IProperty)BlockRedstoneRelay.FACING);
        final EnumFacing enumfacing2 = enumfacing.func_176746_e();
        final EnumFacing enumfacing3 = enumfacing.func_176735_f();
        return Math.max(this.getPowerOnSide(worldIn, pos.func_177972_a(enumfacing2), enumfacing2), this.getPowerOnSide(worldIn, pos.func_177972_a(enumfacing3), enumfacing3));
    }
    
    protected int getPowerOnSide(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState iblockstate = worldIn.func_180495_p(pos);
        final Block block = iblockstate.func_177230_c();
        return (int)(this.canPowerSide(block, iblockstate) ? ((block == Blocks.field_150488_af) ? iblockstate.func_177229_b((IProperty)BlockRedstoneWire.field_176351_O) : worldIn.func_175627_a(pos, side)) : 0);
    }
    
    public boolean func_149744_f(final IBlockState state) {
        return true;
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        if (this.shouldBePowered(worldIn, pos, state)) {
            worldIn.func_175684_a(pos, (Block)this, 1);
        }
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)BlockRedstoneRelay.FACING, (Comparable)(placer.func_70093_af() ? placer.func_174811_aO() : placer.func_174811_aO().func_176734_d()));
        bs = bs.func_177226_a((IProperty)BlockRedstoneRelay.ENABLED, (Comparable)false);
        return bs;
    }
    
    @Override
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.notifyNeighbors(worldIn, pos, state);
    }
    
    protected void notifyNeighbors(final World worldIn, final BlockPos pos, final IBlockState state) {
        final EnumFacing enumfacing = (EnumFacing)state.func_177229_b((IProperty)BlockRedstoneRelay.FACING);
        final BlockPos blockpos1 = pos.func_177972_a(enumfacing.func_176734_d());
        if (ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.func_180495_p(pos), (EnumSet)EnumSet.of(enumfacing.func_176734_d()), false).isCanceled()) {
            return;
        }
        worldIn.func_190524_a(blockpos1, (Block)this, pos);
        worldIn.func_175695_a(blockpos1, (Block)this, enumfacing);
    }
    
    public void func_176206_d(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.isPowered(state)) {
            for (final EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.func_175685_c(pos.func_177972_a(enumfacing), (Block)this, false);
            }
        }
        super.func_176206_d(worldIn, pos, state);
    }
    
    protected boolean canPowerSide(final Block blockIn, final IBlockState iblockstate) {
        return blockIn.func_149744_f(iblockstate);
    }
    
    protected int getActiveSignal(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity tile = worldIn.func_175625_s(pos);
        if (tile != null && tile instanceof TileRedstoneRelay) {
            return ((TileRedstoneRelay)tile).getOut();
        }
        return 0;
    }
    
    public static boolean isRedstoneRepeaterBlockID(final Block blockIn) {
        return Blocks.field_150413_aR.func_149667_c(blockIn) || Blocks.field_150441_bU.func_149667_c(blockIn);
    }
    
    public boolean isAssociated(final Block other) {
        return other == this.getPoweredState(this.func_176223_P()).func_177230_c() || other == this.getUnpoweredState(this.func_176223_P()).func_177230_c();
    }
    
    public boolean isFacingTowardsRepeater(final World worldIn, final BlockPos pos, final IBlockState state) {
        final EnumFacing enumfacing = ((EnumFacing)state.func_177229_b((IProperty)BlockRedstoneRelay.FACING)).func_176734_d();
        final BlockPos blockpos1 = pos.func_177972_a(enumfacing);
        return isRedstoneRepeaterBlockID(worldIn.func_180495_p(blockpos1).func_177230_c()) && worldIn.func_180495_p(blockpos1).func_177229_b((IProperty)BlockRedstoneRelay.FACING) != enumfacing;
    }
    
    protected int getTickDelay(final IBlockState state) {
        return 2;
    }
    
    protected IBlockState getPoweredState(final IBlockState unpoweredState) {
        final EnumFacing enumfacing = (EnumFacing)unpoweredState.func_177229_b((IProperty)BlockRedstoneRelay.FACING);
        return this.func_176223_P().func_177226_a((IProperty)BlockRedstoneRelay.FACING, (Comparable)enumfacing).func_177226_a((IProperty)BlockRedstoneRelay.ENABLED, (Comparable)true);
    }
    
    protected IBlockState getUnpoweredState(final IBlockState poweredState) {
        final EnumFacing enumfacing = (EnumFacing)poweredState.func_177229_b((IProperty)BlockRedstoneRelay.FACING);
        return this.func_176223_P().func_177226_a((IProperty)BlockRedstoneRelay.FACING, (Comparable)enumfacing).func_177226_a((IProperty)BlockRedstoneRelay.ENABLED, (Comparable)false);
    }
    
    public boolean func_149667_c(final Block other) {
        return this.isAssociated(other);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB func_180640_a(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileRedstoneRelay) {
            final RayTraceResult hit = RayTracer.retraceBlock(world, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, pos);
            if (hit != null && hit.subHit == 0) {
                final Cuboid6 cubeoid = ((TileRedstoneRelay)tile).getCuboid0(BlockStateUtils.getFacing(tile.func_145832_p()));
                final Vector3 v = new Vector3(pos);
                final Cuboid6 c = cubeoid.sub(v);
                return new AxisAlignedBB((double)(float)c.min.x, (double)(float)c.min.y, (double)(float)c.min.z, (double)(float)c.max.x, (double)(float)c.max.y, (double)(float)c.max.z).func_186670_a(pos);
            }
            if (hit != null && hit.subHit == 1) {
                final Cuboid6 cubeoid = ((TileRedstoneRelay)tile).getCuboid1(BlockStateUtils.getFacing(tile.func_145832_p()));
                final Vector3 v = new Vector3(pos);
                final Cuboid6 c = cubeoid.sub(v);
                return new AxisAlignedBB((double)(float)c.min.x, (double)(float)c.min.y, (double)(float)c.min.z, (double)(float)c.max.x, (double)(float)c.max.y, (double)(float)c.max.z).func_186670_a(pos);
            }
        }
        return super.func_180640_a(state, world, pos);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBlockHighlight(final DrawBlockHighlightEvent event) {
        if (event.getTarget().field_72313_a == RayTraceResult.Type.BLOCK && event.getPlayer().field_70170_p.func_180495_p(event.getTarget().func_178782_a()).func_177230_c() == this) {
            RayTracer.retraceBlock(event.getPlayer().field_70170_p, event.getPlayer(), event.getTarget().func_178782_a());
        }
    }
    
    public RayTraceResult func_180636_a(final IBlockState state, final World world, final BlockPos pos, final Vec3d start, final Vec3d end) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile == null || !(tile instanceof TileRedstoneRelay)) {
            return super.func_180636_a(state, world, pos, start, end);
        }
        final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        if (tile instanceof TileRedstoneRelay) {
            ((TileRedstoneRelay)tile).addTraceableCuboids(cuboids);
        }
        final ArrayList<ExtendedMOP> list = new ArrayList<ExtendedMOP>();
        this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
        return (list.size() > 0) ? ((ExtendedMOP)list.get(0)) : super.func_180636_a(state, world, pos, start, end);
    }
}
