package thaumcraft.common.blocks.world.taint;

import net.minecraftforge.fml.common.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.config.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import thaumcraft.api.aura.*;
import net.minecraft.entity.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.blocks.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.codechicken.lib.vec.*;
import java.util.*;
import net.minecraft.client.*;
import javax.annotation.*;
import net.minecraft.block.state.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class BlockTaintFibre extends Block implements ITaintBlock
{
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    public static final PropertyBool SOUTH;
    public static final PropertyBool WEST;
    public static final PropertyBool UP;
    public static final PropertyBool DOWN;
    public static final PropertyBool GROWTH1;
    public static final PropertyBool GROWTH2;
    public static final PropertyBool GROWTH3;
    public static final PropertyBool GROWTH4;
    private RayTracer rayTracer;
    protected static final AxisAlignedBB AABB_EMPTY;
    protected static final AxisAlignedBB AABB_UP;
    protected static final AxisAlignedBB AABB_DOWN;
    protected static final AxisAlignedBB AABB_EAST;
    protected static final AxisAlignedBB AABB_WEST;
    protected static final AxisAlignedBB AABB_SOUTH;
    protected static final AxisAlignedBB AABB_NORTH;
    
    public BlockTaintFibre() {
        super(ThaumcraftMaterials.MATERIAL_TAINT);
        this.rayTracer = new RayTracer();
        this.func_149663_c("taint_fibre");
        this.setRegistryName("thaumcraft", "taint_fibre");
        this.func_149711_c(1.0f);
        this.func_149672_a(SoundsTC.GORE);
        this.func_149675_a(true);
        this.func_149647_a(ConfigItems.TABTC);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockTaintFibre.NORTH, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.EAST, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.SOUTH, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.WEST, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.UP, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.DOWN, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.GROWTH1, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.GROWTH2, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.GROWTH3, (Comparable)false).func_177226_a((IProperty)BlockTaintFibre.GROWTH4, (Comparable)false));
    }
    
    public SoundType func_185467_w() {
        return SoundsTC.GORE;
    }
    
    public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 3;
    }
    
    public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
        return 3;
    }
    
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return MapColor.field_151678_z;
    }
    
    public void die(final World world, final BlockPos pos, final IBlockState blockState) {
        world.func_175698_g(pos);
    }
    
    protected boolean func_149700_E() {
        return false;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150899_d(0);
    }
    
    public void func_180653_a(final World worldIn, final BlockPos pos, IBlockState state, final float chance, final int fortune) {
        state = this.func_176221_a(state, (IBlockAccess)worldIn, pos);
        if (state instanceof IBlockState && (boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH3)) {
            if (worldIn.field_73012_v.nextInt(5) <= fortune) {
                func_180635_a(worldIn, pos, ConfigItems.FLUX_CRYSTAL.func_77946_l());
            }
            AuraHelper.polluteAura(worldIn, pos, 1.0f, true);
        }
    }
    
    public void func_180650_b(final World world, final BlockPos pos, IBlockState state, final Random random) {
        if (!world.field_72995_K) {
            state = this.func_176221_a(state, (IBlockAccess)world, pos);
            if (state instanceof IBlockState) {
                if (!(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH1) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH2) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH3) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH4) && isOnlyAdjacentToTaint(world, pos)) {
                    this.die(world, pos, state);
                }
                else if (!TaintHelper.isNearTaintSeed(world, pos)) {
                    this.die(world, pos, state);
                }
                else {
                    TaintHelper.spreadFibres(world, pos);
                }
            }
        }
    }
    
    public void func_189540_a(IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos pos2) {
        state = this.func_176221_a(state, (IBlockAccess)worldIn, pos);
        if (state instanceof IBlockState && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH1) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH2) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH3) && !(boolean)state.func_177229_b((IProperty)BlockTaintFibre.GROWTH4) && isOnlyAdjacentToTaint(worldIn, pos)) {
            worldIn.func_175698_g(pos);
        }
    }
    
    public static int getAdjacentTaint(final IBlockAccess world, final BlockPos pos) {
        int count = 0;
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            if (world.func_180495_p(pos.func_177972_a(dir)).func_185904_a() != ThaumcraftMaterials.MATERIAL_TAINT) {
                ++count;
            }
        }
        return count;
    }
    
    public static boolean isOnlyAdjacentToTaint(final World world, final BlockPos pos) {
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            if (!world.func_175623_d(pos.func_177972_a(dir)) && world.func_180495_p(pos.func_177972_a(dir)).func_185904_a() != ThaumcraftMaterials.MATERIAL_TAINT && world.func_180495_p(pos.func_177972_a(dir)).func_177230_c().isSideSolid(world.func_180495_p(pos.func_177972_a(dir)), (IBlockAccess)world, pos.func_177972_a(dir), dir.func_176734_d())) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isHemmedByTaint(final World world, final BlockPos pos) {
        int c = 0;
        for (final EnumFacing dir : EnumFacing.field_82609_l) {
            final IBlockState block = world.func_180495_p(pos.func_177972_a(dir));
            if (block.func_185904_a() == ThaumcraftMaterials.MATERIAL_TAINT) {
                ++c;
            }
            else if (world.func_175623_d(pos.func_177972_a(dir))) {
                --c;
            }
            else if (!block.func_185904_a().func_76224_d() && !block.isSideSolid((IBlockAccess)world, pos.func_177972_a(dir), dir.func_176734_d())) {
                --c;
            }
        }
        return c > 0;
    }
    
    public void func_176199_a(final World world, final BlockPos pos, final Entity entity) {
        if (!world.field_72995_K && entity instanceof EntityLivingBase && !((EntityLivingBase)entity).func_70662_br() && world.field_73012_v.nextInt(750) == 0) {
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(PotionFluxTaint.instance, 200, 0, false, true));
        }
    }
    
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int eventID, final int eventParam) {
        if (eventID == 1) {
            if (worldIn.field_72995_K) {
                worldIn.func_184133_a((EntityPlayer)null, pos, SoundEvents.field_187540_ab, SoundCategory.BLOCKS, 0.1f, 0.9f + worldIn.field_73012_v.nextFloat() * 0.2f);
            }
            return true;
        }
        return super.func_189539_a(state, worldIn, pos, eventID, eventParam);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean isSideSolid(final IBlockState base_state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    private boolean drawAt(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState b = worldIn.func_180495_p(pos);
        return b.func_177230_c() != BlocksTC.taintFibre && b.func_177230_c() != BlocksTC.taintFeature && b.isSideSolid(worldIn, pos, side.func_176734_d());
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBlockHighlight(final DrawBlockHighlightEvent event) {
        if (event.getTarget().field_72313_a == RayTraceResult.Type.BLOCK && event.getPlayer().field_70170_p.func_180495_p(event.getTarget().func_178782_a()).func_177230_c() == this) {
            RayTracer.retraceBlock(event.getPlayer().field_70170_p, event.getPlayer(), event.getTarget().func_178782_a());
        }
    }
    
    public RayTraceResult func_180636_a(final IBlockState state, final World world, final BlockPos pos, final Vec3d start, final Vec3d end) {
        final List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        if (this.drawAt((IBlockAccess)world, pos.func_177984_a(), EnumFacing.UP)) {
            cuboids.add(new IndexedCuboid6(0, new Cuboid6(BlockTaintFibre.AABB_UP.func_186670_a(pos))));
        }
        if (this.drawAt((IBlockAccess)world, pos.func_177977_b(), EnumFacing.DOWN)) {
            cuboids.add(new IndexedCuboid6(1, new Cuboid6(BlockTaintFibre.AABB_DOWN.func_186670_a(pos))));
        }
        if (this.drawAt((IBlockAccess)world, pos.func_177974_f(), EnumFacing.EAST)) {
            cuboids.add(new IndexedCuboid6(2, new Cuboid6(BlockTaintFibre.AABB_EAST.func_186670_a(pos))));
        }
        if (this.drawAt((IBlockAccess)world, pos.func_177976_e(), EnumFacing.WEST)) {
            cuboids.add(new IndexedCuboid6(3, new Cuboid6(BlockTaintFibre.AABB_WEST.func_186670_a(pos))));
        }
        if (this.drawAt((IBlockAccess)world, pos.func_177968_d(), EnumFacing.SOUTH)) {
            cuboids.add(new IndexedCuboid6(4, new Cuboid6(BlockTaintFibre.AABB_SOUTH.func_186670_a(pos))));
        }
        if (this.drawAt((IBlockAccess)world, pos.func_177978_c(), EnumFacing.NORTH)) {
            cuboids.add(new IndexedCuboid6(5, new Cuboid6(BlockTaintFibre.AABB_NORTH.func_186670_a(pos))));
        }
        final IBlockState ss = this.func_176221_a(world.func_180495_p(pos), (IBlockAccess)world, pos);
        if (ss.func_177230_c() == this && ss instanceof IBlockState) {
            if (ss.func_177229_b((IProperty)BlockTaintFibre.GROWTH1)) {
                cuboids.add(new IndexedCuboid6(6, new Cuboid6(new AxisAlignedBB(0.10000000149011612, 0.0, 0.10000000149011612, 0.8999999761581421, 0.4000000059604645, 0.8999999761581421).func_186670_a(pos))));
            }
            else if (ss.func_177229_b((IProperty)BlockTaintFibre.GROWTH2)) {
                cuboids.add(new IndexedCuboid6(6, new Cuboid6(new AxisAlignedBB(0.20000000298023224, 0.0, 0.20000000298023224, 0.800000011920929, 1.0, 0.800000011920929).func_186670_a(pos))));
            }
            else if (ss.func_177229_b((IProperty)BlockTaintFibre.GROWTH3)) {
                cuboids.add(new IndexedCuboid6(6, new Cuboid6(new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.3125, 0.75).func_186670_a(pos))));
            }
            else if (ss.func_177229_b((IProperty)BlockTaintFibre.GROWTH4)) {
                cuboids.add(new IndexedCuboid6(6, new Cuboid6(new AxisAlignedBB(0.10000000149011612, 0.30000001192092896, 0.10000000149011612, 0.8999999761581421, 1.0, 0.8999999761581421).func_186670_a(pos))));
            }
        }
        final ArrayList<ExtendedMOP> list = new ArrayList<ExtendedMOP>();
        this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
        return (list.size() > 0) ? ((ExtendedMOP)list.get(0)) : super.func_180636_a(state, world, pos, start, end);
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState s, final IBlockAccess source, final BlockPos pos) {
        return BlockTaintFibre.AABB_EMPTY;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB func_180640_a(final IBlockState s, final World world, final BlockPos pos) {
        final IBlockState state = this.func_176221_a(world.func_180495_p(pos), (IBlockAccess)world, pos);
        if (state.func_177230_c() == this && state instanceof IBlockState) {
            if (state.func_177229_b((IProperty)BlockTaintFibre.GROWTH1)) {
                return new AxisAlignedBB(0.10000000149011612, 0.0, 0.10000000149011612, 0.8999999761581421, 0.4000000059604645, 0.8999999761581421).func_186670_a(pos);
            }
            if (state.func_177229_b((IProperty)BlockTaintFibre.GROWTH2)) {
                return new AxisAlignedBB(0.20000000298023224, 0.0, 0.20000000298023224, 0.800000011920929, 1.0, 0.800000011920929).func_186670_a(pos);
            }
            if (state.func_177229_b((IProperty)BlockTaintFibre.GROWTH3)) {
                return new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.3125, 0.75).func_186670_a(pos);
            }
            if (state.func_177229_b((IProperty)BlockTaintFibre.GROWTH4)) {
                return new AxisAlignedBB(0.10000000149011612, 0.30000001192092896, 0.10000000149011612, 0.8999999761581421, 1.0, 0.8999999761581421).func_186670_a(pos);
            }
        }
        final RayTraceResult hit = RayTracer.retraceBlock(world, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, pos);
        if (hit != null) {
            switch (hit.subHit) {
                case 0: {
                    return BlockTaintFibre.AABB_UP.func_186670_a(pos);
                }
                case 1: {
                    return BlockTaintFibre.AABB_DOWN.func_186670_a(pos);
                }
                case 2: {
                    return BlockTaintFibre.AABB_EAST.func_186670_a(pos);
                }
                case 3: {
                    return BlockTaintFibre.AABB_WEST.func_186670_a(pos);
                }
                case 4: {
                    return BlockTaintFibre.AABB_SOUTH.func_186670_a(pos);
                }
                case 5: {
                    return BlockTaintFibre.AABB_NORTH.func_186670_a(pos);
                }
            }
        }
        return BlockTaintFibre.AABB_EMPTY;
    }
    
    public void func_185477_a(final IBlockState state, final World worldIn, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes, @Nullable final Entity entityIn, final boolean isActualState) {
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177984_a(), EnumFacing.UP)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_UP);
        }
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177977_b(), EnumFacing.DOWN)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_DOWN);
        }
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177974_f(), EnumFacing.EAST)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_EAST);
        }
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177976_e(), EnumFacing.WEST)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_WEST);
        }
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177968_d(), EnumFacing.SOUTH)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_SOUTH);
        }
        if (this.drawAt((IBlockAccess)worldIn, pos.func_177978_c(), EnumFacing.NORTH)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockTaintFibre.AABB_NORTH);
        }
    }
    
    public boolean func_176200_f(final IBlockAccess worldIn, final BlockPos pos) {
        return true;
    }
    
    public boolean func_176205_b(final IBlockAccess worldIn, final BlockPos pos) {
        return true;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public int func_176201_c(final IBlockState state) {
        return 0;
    }
    
    public int getLightValue(final IBlockState state2, final IBlockAccess world, final BlockPos pos) {
        final IBlockState state3 = this.func_176221_a(world.func_180495_p(pos), world, pos);
        if (state3.func_177230_c() == this && state3 instanceof IBlockState) {
            return state3.func_177229_b((IProperty)BlockTaintFibre.GROWTH3) ? 12 : (((boolean)state3.func_177229_b((IProperty)BlockTaintFibre.GROWTH2) || (boolean)state3.func_177229_b((IProperty)BlockTaintFibre.GROWTH4)) ? 6 : super.getLightValue(state2, world, pos));
        }
        return super.getLightValue(state2, world, pos);
    }
    
    private Boolean[] makeConnections(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final Boolean[] cons = { false, false, false, false, false, false };
        int a = 0;
        for (final EnumFacing face : EnumFacing.field_82609_l) {
            if (this.drawAt(world, pos.func_177972_a(face), face)) {
                cons[a] = true;
            }
            ++a;
        }
        return cons;
    }
    
    public IBlockState func_176221_a(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final Boolean[] cons = this.makeConnections(state, worldIn, pos);
        final boolean d = this.drawAt(worldIn, pos.func_177977_b(), EnumFacing.DOWN);
        final boolean u = this.drawAt(worldIn, pos.func_177984_a(), EnumFacing.UP);
        int growth = 0;
        final Random rand = new Random(pos.func_177986_g());
        final int q = rand.nextInt(50);
        if (d) {
            if (q < 4) {
                growth = 1;
            }
            else if (q == 4 || q == 5) {
                growth = 2;
            }
            else if (q == 6) {
                growth = 3;
            }
        }
        if (u && q > 47) {
            growth = 4;
        }
        try {
            return state.func_177226_a((IProperty)BlockTaintFibre.DOWN, (Comparable)cons[0]).func_177226_a((IProperty)BlockTaintFibre.UP, (Comparable)cons[1]).func_177226_a((IProperty)BlockTaintFibre.NORTH, (Comparable)cons[2]).func_177226_a((IProperty)BlockTaintFibre.SOUTH, (Comparable)cons[3]).func_177226_a((IProperty)BlockTaintFibre.WEST, (Comparable)cons[4]).func_177226_a((IProperty)BlockTaintFibre.EAST, (Comparable)cons[5]).func_177226_a((IProperty)BlockTaintFibre.GROWTH1, (Comparable)(growth == 1)).func_177226_a((IProperty)BlockTaintFibre.GROWTH2, (Comparable)(growth == 2)).func_177226_a((IProperty)BlockTaintFibre.GROWTH3, (Comparable)(growth == 3)).func_177226_a((IProperty)BlockTaintFibre.GROWTH4, (Comparable)(growth == 4));
        }
        catch (Exception e) {
            return state;
        }
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { BlockTaintFibre.NORTH, BlockTaintFibre.EAST, BlockTaintFibre.SOUTH, BlockTaintFibre.WEST, BlockTaintFibre.UP, BlockTaintFibre.DOWN, BlockTaintFibre.GROWTH1, BlockTaintFibre.GROWTH2, BlockTaintFibre.GROWTH3, BlockTaintFibre.GROWTH4 });
    }
    
    static {
        NORTH = PropertyBool.func_177716_a("north");
        EAST = PropertyBool.func_177716_a("east");
        SOUTH = PropertyBool.func_177716_a("south");
        WEST = PropertyBool.func_177716_a("west");
        UP = PropertyBool.func_177716_a("up");
        DOWN = PropertyBool.func_177716_a("down");
        GROWTH1 = PropertyBool.func_177716_a("growth1");
        GROWTH2 = PropertyBool.func_177716_a("growth2");
        GROWTH3 = PropertyBool.func_177716_a("growth3");
        GROWTH4 = PropertyBool.func_177716_a("growth4");
        AABB_EMPTY = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        AABB_UP = new AxisAlignedBB(0.0, 0.949999988079071, 0.0, 1.0, 1.0, 1.0);
        AABB_DOWN = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.05000000074505806, 1.0);
        AABB_EAST = new AxisAlignedBB(0.949999988079071, 0.0, 0.0, 1.0, 1.0, 1.0);
        AABB_WEST = new AxisAlignedBB(0.0, 0.0, 0.0, 0.05000000074505806, 1.0, 1.0);
        AABB_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.949999988079071, 1.0, 1.0, 1.0);
        AABB_NORTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.05000000074505806);
    }
}
