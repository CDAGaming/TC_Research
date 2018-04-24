package thaumcraft.common.blocks.devices;

import thaumcraft.common.blocks.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;
import net.minecraft.block.*;
import java.util.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class BlockLevitator extends BlockTCDevice implements IBlockFacing, IBlockEnabled
{
    private RayTracer rayTracer;
    
    public BlockLevitator() {
        super(Material.field_151575_d, TileLevitator.class, "levitator");
        this.rayTracer = new RayTracer();
        this.func_149672_a(SoundType.field_185848_a);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, pos);
        if (hit == null) {
            return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
        final TileEntity tile = world.func_175625_s(pos);
        if (hit.subHit == 0 && tile instanceof TileLevitator) {
            ((TileLevitator)tile).increaseRange(player);
            world.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.key, SoundCategory.BLOCKS, 0.5f, 1.0f);
            return true;
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB func_180640_a(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileLevitator) {
            final RayTraceResult hit = RayTracer.retraceBlock(world, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, pos);
            if (hit != null && hit.subHit == 0) {
                final Cuboid6 cubeoid = ((TileLevitator)tile).getCuboidByFacing(BlockStateUtils.getFacing(tile.func_145832_p()));
                final Vector3 v = new Vector3(pos);
                final Cuboid6 c = cubeoid.add(v);
                return c.aabb();
            }
        }
        return super.func_180640_a(state, world, pos);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing facing = BlockStateUtils.getFacing(state);
        final float f = 0.125f;
        final float minx = 0.0f + ((facing.func_82601_c() > 0) ? f : 0.0f);
        final float maxx = 1.0f - ((facing.func_82601_c() < 0) ? f : 0.0f);
        final float miny = 0.0f + ((facing.func_96559_d() > 0) ? f : 0.0f);
        final float maxy = 1.0f - ((facing.func_96559_d() < 0) ? f : 0.0f);
        final float minz = 0.0f + ((facing.func_82599_e() > 0) ? f : 0.0f);
        final float maxz = 1.0f - ((facing.func_82599_e() < 0) ? f : 0.0f);
        return new AxisAlignedBB((double)minx, (double)miny, (double)minz, (double)maxx, (double)maxy, (double)maxz);
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
        if (tile == null || !(tile instanceof TileLevitator)) {
            return super.func_180636_a(state, world, pos, start, end);
        }
        final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        if (tile instanceof TileLevitator) {
            ((TileLevitator)tile).addTraceableCuboids(cuboids);
        }
        final ArrayList<ExtendedMOP> list = new ArrayList<ExtendedMOP>();
        this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
        return (list.size() > 0) ? ((ExtendedMOP)list.get(0)) : super.func_180636_a(state, world, pos, start, end);
    }
}
