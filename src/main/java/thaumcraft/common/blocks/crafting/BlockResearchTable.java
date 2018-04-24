package thaumcraft.common.blocks.crafting;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import java.util.*;
import thaumcraft.client.fx.particles.*;
import thaumcraft.client.fx.*;
import net.minecraft.client.particle.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockResearchTable extends BlockTCDevice implements IBlockFacingHorizontal
{
    public BlockResearchTable() {
        super(Material.field_151575_d, TileResearchTable.class, "research_table");
        this.func_149672_a(SoundType.field_185848_a);
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
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        player.openGui((Object)Thaumcraft.instance, 10, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        return true;
    }
    
    @Override
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState bs = this.func_176223_P();
        bs = bs.func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)placer.func_174811_aO());
        return bs;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        final TileEntity te = world.func_175625_s(pos);
        if (rand.nextInt(5) == 0 && te != null && ((TileResearchTable)te).data != null) {
            final double xx = rand.nextGaussian() / 2.0;
            final double zz = rand.nextGaussian() / 2.0;
            final double yy = 1.5 + rand.nextFloat();
            final int a = 40 + rand.nextInt(20);
            final FXGeneric fb = new FXGeneric(world, pos.func_177958_n() + 0.5 + xx, pos.func_177956_o() + yy, pos.func_177952_p() + 0.5 + zz, -xx / a, -(yy - 0.85) / a, -zz / a);
            fb.func_187114_a(a);
            fb.func_70538_b(0.5f + rand.nextFloat() * 0.5f, 0.5f + rand.nextFloat() * 0.5f, 0.5f + rand.nextFloat() * 0.5f);
            fb.setAlphaF(0.0f, 0.25f, 0.5f, 0.75f, 0.0f);
            fb.setParticles(384 + rand.nextInt(16), 1, 1);
            fb.setScale(0.8f + rand.nextFloat() * 0.3f, 0.3f);
            fb.setLayer(0);
            ParticleEngine.addEffect(world, fb);
        }
    }
}
