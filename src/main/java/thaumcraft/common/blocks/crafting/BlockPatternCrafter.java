package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;
import net.minecraft.block.*;
import java.util.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class BlockPatternCrafter extends BlockTCDevice implements IBlockFacingHorizontal, IBlockEnabled
{
    private RayTracer rayTracer;
    
    public BlockPatternCrafter() {
        super(Material.field_151573_f, TilePatternCrafter.class, "pattern_crafter");
        this.rayTracer = new RayTracer();
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean isSideSolid(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)placer.func_174811_aO());
        return bs;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, pos);
        if (hit == null) {
            return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
        }
        final TileEntity tile = world.func_175625_s(pos);
        if (hit.subHit == 0 && tile instanceof TilePatternCrafter) {
            if (!world.field_72995_K) {
                ((TilePatternCrafter)tile).cycle();
                world.func_184148_a((EntityPlayer)null, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundsTC.key, SoundCategory.BLOCKS, 0.5f, 1.0f);
            }
            return true;
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB func_180640_a(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TilePatternCrafter) {
            final RayTraceResult hit = RayTracer.retraceBlock(world, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, pos);
            if (hit != null && hit.subHit == 0) {
                final Cuboid6 cubeoid = ((TilePatternCrafter)tile).getCuboidByFacing(BlockStateUtils.getFacing(tile.func_145832_p()));
                final Vector3 v = new Vector3(pos);
                final Cuboid6 c = cubeoid.sub(v);
                return new AxisAlignedBB((double)(float)c.min.x, (double)(float)c.min.y, (double)(float)c.min.z, (double)(float)c.max.x, (double)(float)c.max.y, (double)(float)c.max.z).func_186670_a(pos);
            }
        }
        return super.func_180640_a(state, world, pos);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return BlockPatternCrafter.field_185505_j;
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
        if (tile == null || !(tile instanceof TilePatternCrafter)) {
            return super.func_180636_a(state, world, pos, start, end);
        }
        final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        if (tile instanceof TilePatternCrafter) {
            ((TilePatternCrafter)tile).addTraceableCuboids(cuboids);
        }
        final ArrayList<ExtendedMOP> list = new ArrayList<ExtendedMOP>();
        this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
        return (list.size() > 0) ? ((ExtendedMOP)list.get(0)) : super.func_180636_a(state, world, pos, start, end);
    }
}
