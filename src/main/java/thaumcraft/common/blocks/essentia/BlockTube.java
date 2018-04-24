package thaumcraft.common.blocks.essentia;

import thaumcraft.common.blocks.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import thaumcraft.api.*;
import net.minecraft.client.*;
import thaumcraft.api.casters.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.items.tools.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.aura.*;
import net.minecraft.init.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.tiles.essentia.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import java.util.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class BlockTube extends BlockTCDevice
{
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    public static final PropertyBool SOUTH;
    public static final PropertyBool WEST;
    public static final PropertyBool UP;
    public static final PropertyBool DOWN;
    private RayTracer rayTracer;
    
    public BlockTube(final Class tile, final String name) {
        super(Material.field_151573_f, tile, name);
        this.rayTracer = new RayTracer();
        this.func_149711_c(0.5f);
        this.func_149752_b(5.0f);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockTube.NORTH, (Comparable)false).func_177226_a((IProperty)BlockTube.EAST, (Comparable)false).func_177226_a((IProperty)BlockTube.SOUTH, (Comparable)false).func_177226_a((IProperty)BlockTube.WEST, (Comparable)false).func_177226_a((IProperty)BlockTube.UP, (Comparable)false).func_177226_a((IProperty)BlockTube.DOWN, (Comparable)false));
    }
    
    @Override
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockTube.NORTH, BlockTube.EAST, BlockTube.SOUTH, BlockTube.WEST, BlockTube.UP, BlockTube.DOWN });
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Override
    public int func_176201_c(final IBlockState state) {
        return 0;
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final TileEntity tile = worldIn.func_175625_s(pos);
        if (tile != null && tile instanceof TileTube) {
            ((TileTube)tile).facing = EnumFacing.func_190914_a(pos, placer);
            tile.func_70296_d();
        }
        super.func_180633_a(worldIn, pos, state, placer, stack);
    }
    
    public IBlockState func_176221_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final Boolean[] cons = this.makeConnections(state, worldIn, pos);
        return state.func_177226_a((IProperty)BlockTube.DOWN, (Comparable)cons[0]).func_177226_a((IProperty)BlockTube.UP, (Comparable)cons[1]).func_177226_a((IProperty)BlockTube.NORTH, (Comparable)cons[2]).func_177226_a((IProperty)BlockTube.SOUTH, (Comparable)cons[3]).func_177226_a((IProperty)BlockTube.WEST, (Comparable)cons[4]).func_177226_a((IProperty)BlockTube.EAST, (Comparable)cons[5]);
    }
    
    private Boolean[] makeConnections(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final Boolean[] cons = { false, false, false, false, false, false };
        final TileEntity t = world.func_175625_s(pos);
        if (t != null && t instanceof IEssentiaTransport) {
            final IEssentiaTransport tube = (IEssentiaTransport)t;
            int a = 0;
            for (final EnumFacing face : EnumFacing.field_82609_l) {
                if (tube.isConnectable(face) && ThaumcraftApiHelper.getConnectableTile(world, pos, face) != null) {
                    cons[a] = true;
                }
                ++a;
            }
        }
        return cons;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB func_180640_a(final IBlockState state, final World world, final BlockPos pos) {
        final boolean noDoodads = InventoryUtils.isHoldingItem((EntityPlayer)Minecraft.func_71410_x().field_71439_g, ICaster.class) == null && InventoryUtils.isHoldingItem((EntityPlayer)Minecraft.func_71410_x().field_71439_g, ItemResonator.class) == null;
        final TileEntity tile = world.func_175625_s(pos);
        if (tile != null && tile instanceof TileTube) {
            final RayTraceResult hit = RayTracer.retraceBlock(world, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, pos);
            final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
            ((TileTube)tile).addTraceableCuboids(cuboids);
            if (hit != null && hit.subHit >= 0 && hit.subHit <= 6 && !noDoodads) {
                for (final IndexedCuboid6 cc : cuboids) {
                    if ((int)cc.data == hit.subHit) {
                        final Vector3 v = new Vector3(pos);
                        final Cuboid6 c = cc.sub(v);
                        return new AxisAlignedBB((double)(float)c.min.x, (double)(float)c.min.y, (double)(float)c.min.z, (double)(float)c.max.x, (double)(float)c.max.y, (double)(float)c.max.z).func_186670_a(pos);
                    }
                }
            }
        }
        return super.func_180640_a(state, world, pos);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        float minx = 0.3125f;
        float maxx = 0.6875f;
        float miny = 0.3125f;
        float maxy = 0.6875f;
        float minz = 0.3125f;
        float maxz = 0.6875f;
        EnumFacing fd = null;
        for (int side = 0; side < 6; ++side) {
            fd = EnumFacing.field_82609_l[side];
            final TileEntity te = ThaumcraftApiHelper.getConnectableTile(source, pos, fd);
            if (te != null) {
                switch (side) {
                    case 0: {
                        miny = 0.0f;
                        break;
                    }
                    case 1: {
                        maxy = 1.0f;
                        break;
                    }
                    case 2: {
                        minz = 0.0f;
                        break;
                    }
                    case 3: {
                        maxz = 1.0f;
                        break;
                    }
                    case 4: {
                        minx = 0.0f;
                        break;
                    }
                    case 5: {
                        maxx = 1.0f;
                        break;
                    }
                }
            }
        }
        return new AxisAlignedBB((double)minx, (double)miny, (double)minz, (double)maxx, (double)maxy, (double)maxz);
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState state, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileTubeBuffer) {
            final float n = ((TileTubeBuffer)te).aspects.visSize();
            ((TileTubeBuffer)te).getClass();
            final float r = n / 10.0f;
            return MathHelper.func_76141_d(r * 14.0f) + ((((TileTubeBuffer)te).aspects.visSize() > 0) ? 1 : 0);
        }
        return 0;
    }
    
    @Override
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te != null && te instanceof TileTube && ((TileTube)te).getEssentiaAmount(EnumFacing.UP) > 0) {
            if (!worldIn.field_72995_K) {
                AuraHelper.polluteAura(worldIn, pos, ((TileTube)te).getEssentiaAmount(EnumFacing.UP), true);
            }
            else {
                worldIn.func_184134_a(pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.1f, 1.0f + worldIn.field_73012_v.nextFloat() * 0.1f, false);
                for (int a = 0; a < 5; ++a) {
                    FXDispatcher.INSTANCE.drawVentParticles(pos.func_177958_n() + 0.33 + worldIn.field_73012_v.nextFloat() * 0.33, pos.func_177956_o() + 0.33 + worldIn.field_73012_v.nextFloat() * 0.33, pos.func_177952_p() + 0.33 + worldIn.field_73012_v.nextFloat() * 0.33, 0.0, 0.0, 0.0, Aspect.FLUX.getColor());
                }
            }
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (state.func_177230_c() == BlocksTC.tubeValve) {
            if (player.func_184586_b(hand).func_77973_b() instanceof ICaster || player.func_184586_b(hand).func_77973_b() instanceof ItemResonator || player.func_184586_b(hand).func_77973_b() == Item.func_150898_a((Block)this)) {
                return false;
            }
            final TileEntity te = world.func_175625_s(pos);
            if (te instanceof TileTubeValve) {
                ((TileTubeValve)te).allowFlow = !((TileTubeValve)te).allowFlow;
                world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                te.func_70296_d();
                if (!world.field_72995_K) {
                    world.func_184133_a((EntityPlayer)null, pos, SoundsTC.squeek, SoundCategory.BLOCKS, 0.7f, 0.9f + world.field_73012_v.nextFloat() * 0.2f);
                }
                return true;
            }
        }
        if (state.func_177230_c() == BlocksTC.tubeFilter) {
            final TileEntity te = world.func_175625_s(pos);
            if (te != null && te instanceof TileTubeFilter && player.func_70093_af() && ((TileTubeFilter)te).aspectFilter != null) {
                ((TileTubeFilter)te).aspectFilter = null;
                world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                te.func_70296_d();
                if (world.field_72995_K) {
                    world.func_184134_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundsTC.key, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                }
                return true;
            }
            if (te != null && te instanceof TileTubeFilter && ((TileTubeFilter)te).aspectFilter == null && player.func_184586_b(hand).func_77973_b() instanceof IEssentiaContainerItem) {
                if (((IEssentiaContainerItem)player.func_184586_b(hand).func_77973_b()).getAspects(player.func_184586_b(hand)) != null) {
                    ((TileTubeFilter)te).aspectFilter = ((IEssentiaContainerItem)player.func_184586_b(hand).func_77973_b()).getAspects(player.func_184586_b(hand)).getAspects()[0];
                    world.markAndNotifyBlock(pos, world.func_175726_f(pos), state, state, 3);
                    te.func_70296_d();
                    if (world.field_72995_K) {
                        world.func_184134_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundsTC.key, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
                    }
                }
                return true;
            }
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBlockHighlight(final DrawBlockHighlightEvent event) {
        if (event.getTarget().field_72313_a == RayTraceResult.Type.BLOCK && event.getPlayer().field_70170_p.func_180495_p(event.getTarget().func_178782_a()).func_177230_c() == this && (InventoryUtils.isHoldingItem(event.getPlayer(), ICaster.class) != null || InventoryUtils.isHoldingItem(event.getPlayer(), ItemResonator.class) != null)) {
            RayTracer.retraceBlock(event.getPlayer().field_70170_p, event.getPlayer(), event.getTarget().func_178782_a());
        }
    }
    
    public RayTraceResult func_180636_a(final IBlockState state, final World world, final BlockPos pos, final Vec3d start, final Vec3d end) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile == null || (!(tile instanceof TileTube) && !(tile instanceof TileTubeBuffer))) {
            return super.func_180636_a(state, world, pos, start, end);
        }
        final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        if (tile instanceof TileTube) {
            ((TileTube)tile).addTraceableCuboids(cuboids);
        }
        else if (tile instanceof TileTubeBuffer) {
            ((TileTubeBuffer)tile).addTraceableCuboids(cuboids);
        }
        final ArrayList<ExtendedMOP> list = new ArrayList<ExtendedMOP>();
        this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
        return (list.size() > 0) ? ((ExtendedMOP)list.get(0)) : super.func_180636_a(state, world, pos, start, end);
    }
    
    static {
        NORTH = PropertyBool.func_177716_a("north");
        EAST = PropertyBool.func_177716_a("east");
        SOUTH = PropertyBool.func_177716_a("south");
        WEST = PropertyBool.func_177716_a("west");
        UP = PropertyBool.func_177716_a("up");
        DOWN = PropertyBool.func_177716_a("down");
    }
}
