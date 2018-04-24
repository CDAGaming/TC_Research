package thaumcraft.common.blocks.misc;

import thaumcraft.common.blocks.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import thaumcraft.api.blocks.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.*;
import thaumcraft.common.blocks.devices.*;
import thaumcraft.common.blocks.crafting.*;

public class BlockPlaceholder extends BlockTC
{
    private final Random rand;
    
    public BlockPlaceholder(final String name) {
        super(Material.field_151576_e, name);
        this.rand = new Random();
        this.func_149711_c(2.5f);
        this.func_149672_a(SoundType.field_185851_d);
        this.func_149647_a((CreativeTabs)null);
    }
    
    protected boolean func_149700_E() {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        if (state.func_177230_c() == BlocksTC.placeholderNetherbrick) {
            return Item.func_150898_a(Blocks.field_150385_bj);
        }
        if (state.func_177230_c() == BlocksTC.placeholderObsidian) {
            return Item.func_150898_a(Blocks.field_150343_Z);
        }
        if (state.func_177230_c() == BlocksTC.placeholderBars) {
            return Item.func_150898_a(Blocks.field_150411_aY);
        }
        if (state.func_177230_c() == BlocksTC.placeholderAnvil) {
            return Item.func_150898_a(Blocks.field_150467_bQ);
        }
        if (state.func_177230_c() == BlocksTC.placeholderCauldron) {
            return Item.func_150898_a((Block)Blocks.field_150383_bp);
        }
        if (state.func_177230_c() == BlocksTC.placeholderTable) {
            return Item.func_150898_a(BlocksTC.tableStone);
        }
        return Item.func_150899_d(0);
    }
    
    @Override
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (world.field_72995_K) {
            return true;
        }
        if (state.func_177230_c() != BlocksTC.placeholderNetherbrick && state.func_177230_c() != BlocksTC.placeholderObsidian) {
            for (int a = -1; a <= 1; ++a) {
                for (int b = -1; b <= 1; ++b) {
                    for (int c = -1; c <= 1; ++c) {
                        final IBlockState s = world.func_180495_p(pos.func_177982_a(a, b, c));
                        if (s.func_177230_c() == BlocksTC.golemBuilder) {
                            player.openGui((Object)Thaumcraft.instance, 19, world, pos.func_177982_a(a, b, c).func_177958_n(), pos.func_177982_a(a, b, c).func_177956_o(), pos.func_177982_a(a, b, c).func_177952_p());
                            return true;
                        }
                    }
                }
            }
        }
        return super.func_180639_a(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        Label_0265: {
            if ((state.func_177230_c() == BlocksTC.placeholderNetherbrick || state.func_177230_c() == BlocksTC.placeholderObsidian) && !BlockInfernalFurnace.ignore && !worldIn.field_72995_K) {
                for (int a = -1; a <= 1; ++a) {
                    for (int b = -1; b <= 1; ++b) {
                        for (int c = -1; c <= 1; ++c) {
                            final IBlockState s = worldIn.func_180495_p(pos.func_177982_a(a, b, c));
                            if (s.func_177230_c() == BlocksTC.infernalFurnace) {
                                BlockInfernalFurnace.destroyFurnace(worldIn, pos.func_177982_a(a, b, c), s, pos);
                                break Label_0265;
                            }
                        }
                    }
                }
            }
            else if (state.func_177230_c() != BlocksTC.placeholderNetherbrick && state.func_177230_c() != BlocksTC.placeholderObsidian && !BlockGolemBuilder.ignore && !worldIn.field_72995_K) {
                for (int a = -1; a <= 1; ++a) {
                    for (int b = -1; b <= 1; ++b) {
                        for (int c = -1; c <= 1; ++c) {
                            final IBlockState s = worldIn.func_180495_p(pos.func_177982_a(a, b, c));
                            if (s.func_177230_c() == BlocksTC.golemBuilder) {
                                BlockGolemBuilder.destroy(worldIn, pos.func_177982_a(a, b, c), s, pos);
                                break Label_0265;
                            }
                        }
                    }
                }
            }
        }
        super.func_180663_b(worldIn, pos, state);
    }
}
