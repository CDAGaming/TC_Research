package thaumcraft.common.lib.crafting;

import thaumcraft.api.crafting.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import java.util.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.lib.events.*;

public class DustTriggerMultiblock implements IDustTrigger
{
    Part[][][] blueprint;
    String research;
    int ySize;
    int xSize;
    int zSize;
    
    public DustTriggerMultiblock(final String research, final Part[][][] blueprint) {
        this.blueprint = blueprint;
        this.research = research;
        this.ySize = this.blueprint.length;
        this.xSize = this.blueprint[0].length;
        this.zSize = this.blueprint[0][0].length;
    }
    
    @Override
    public Placement getValidFace(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing face) {
        if (this.research != null && !ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research)) {
            return null;
        }
        for (int yy = -this.ySize; yy <= 0; ++yy) {
            for (int xx = -this.xSize; xx <= 0; ++xx) {
                for (int zz = -this.zSize; zz <= 0; ++zz) {
                    final BlockPos p2 = pos.func_177982_a(xx, yy, zz);
                    final EnumFacing f = this.fitMultiblock(world, p2);
                    if (f != null) {
                        return new Placement(xx, yy, zz, f);
                    }
                }
            }
        }
        return null;
    }
    
    private EnumFacing fitMultiblock(final World world, final BlockPos pos) {
        final EnumFacing[] field_176754_o = EnumFacing.field_176754_o;
        final int length = field_176754_o.length;
        int i = 0;
    Label_0011:
        while (i < length) {
            final EnumFacing face = field_176754_o[i];
            for (int y = 0; y < this.ySize; ++y) {
                final Matrix matrix = new Matrix(this.blueprint[y]);
                matrix.Rotate90DegRight(3 - face.func_176736_b());
                for (int x = 0; x < matrix.rows; ++x) {
                    for (int z = 0; z < matrix.cols; ++z) {
                        if (matrix.matrix[x][z] != null) {
                            final IBlockState bsWo = world.func_180495_p(pos.func_177982_a(x, -y + (this.ySize - 1), z));
                            Label_0382: {
                                if (!(matrix.matrix[x][z].getSource() instanceof Block) || bsWo.func_177230_c() == matrix.matrix[x][z].getSource()) {
                                    if (!(matrix.matrix[x][z].getSource() instanceof Material) || bsWo.func_185904_a() == matrix.matrix[x][z].getSource()) {
                                        if (matrix.matrix[x][z].getSource() instanceof ItemStack) {
                                            if (bsWo.func_177230_c() != Block.func_149634_a(((ItemStack)matrix.matrix[x][z].getSource()).func_77973_b())) {
                                                break Label_0382;
                                            }
                                            if (bsWo.func_177230_c().func_176201_c(bsWo) != ((ItemStack)matrix.matrix[x][z].getSource()).func_77952_i()) {
                                                break Label_0382;
                                            }
                                        }
                                        if (!(matrix.matrix[x][z].getSource() instanceof IBlockState) || bsWo == matrix.matrix[x][z].getSource()) {
                                            continue;
                                        }
                                    }
                                }
                            }
                            ++i;
                            continue Label_0011;
                        }
                    }
                }
            }
            return face;
        }
        return null;
    }
    
    @Override
    public List<BlockPos> sparkle(final World world, final EntityPlayer player, final BlockPos pos, final Placement placement) {
        final BlockPos p2 = pos.func_177982_a(placement.xOffset, placement.yOffset, placement.zOffset);
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        for (int y = 0; y < this.ySize; ++y) {
            final Matrix matrix = new Matrix(this.blueprint[y]);
            matrix.Rotate90DegRight(3 - placement.facing.func_176736_b());
            for (int x = 0; x < matrix.rows; ++x) {
                for (int z = 0; z < matrix.cols; ++z) {
                    if (matrix.matrix[x][z] != null) {
                        final BlockPos p3 = p2.func_177982_a(x, -y + (this.ySize - 1), z);
                        if (matrix.matrix[x][z].getSource() != null && BlockUtils.isBlockExposed(world, p3)) {
                            list.add(p3);
                        }
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public void execute(final World world, final EntityPlayer player, final BlockPos pos, final Placement placement, final EnumFacing side) {
        if (!world.field_72995_K) {
            FMLCommonHandler.instance().firePlayerCraftingEvent(player, new ItemStack(BlocksTC.infernalFurnace), (IInventory)new InventoryFake(1));
            final BlockPos p2 = pos.func_177982_a(placement.xOffset, placement.yOffset, placement.zOffset);
            for (int y = 0; y < this.ySize; ++y) {
                final Matrix matrix = new Matrix(this.blueprint[y]);
                matrix.Rotate90DegRight(3 - placement.facing.func_176736_b());
                for (int x = 0; x < matrix.rows; ++x) {
                    for (int z = 0; z < matrix.cols; ++z) {
                        if (matrix.matrix[x][z] != null && matrix.matrix[x][z].getTarget() != null) {
                            ItemStack targetObject;
                            if (matrix.matrix[x][z].getTarget() instanceof Block) {
                                int meta = 0;
                                if (((Block)matrix.matrix[x][z].getTarget()) instanceof IBlockFacingHorizontal) {
                                    final IBlockState state = ((Block)matrix.matrix[x][z].getTarget()).func_176223_P().func_177226_a((IProperty)IBlockFacingHorizontal.FACING, (Comparable)(matrix.matrix[x][z].getApplyPlayerFacing() ? side : (matrix.matrix[x][z].isOpp() ? placement.facing.func_176734_d() : placement.facing)));
                                    meta = ((Block)matrix.matrix[x][z].getTarget()).func_176201_c(state);
                                }
                                targetObject = new ItemStack((Block)matrix.matrix[x][z].getTarget(), 1, meta);
                            }
                            else if (matrix.matrix[x][z].getTarget() instanceof ItemStack) {
                                targetObject = ((ItemStack)matrix.matrix[x][z].getTarget()).func_77946_l();
                            }
                            else {
                                targetObject = null;
                            }
                            final BlockPos p3 = p2.func_177982_a(x, -y + (this.ySize - 1), z);
                            Object sourceObject;
                            if (matrix.matrix[x][z].getSource() instanceof Block) {
                                sourceObject = world.func_180495_p(p3);
                            }
                            else if (matrix.matrix[x][z].getSource() instanceof Material) {
                                sourceObject = matrix.matrix[x][z].getSource();
                            }
                            else if (matrix.matrix[x][z].getSource() instanceof IBlockState) {
                                sourceObject = matrix.matrix[x][z].getSource();
                            }
                            else {
                                sourceObject = null;
                            }
                            ToolEvents.addBlockedBlock(world, p3);
                            ServerEvents.addRunnableServer(world, new Runnable() {
                                @Override
                                public void run() {
                                    ServerEvents.addSwapper(world, p3, sourceObject, targetObject, false, 0, player, true, false, -9999, false, false, 0, ServerEvents.DEFAULT_PREDICATE, 0.0f);
                                    ToolEvents.clearBlockedBlock(world, p3);
                                }
                            }, matrix.matrix[x][z].getPriority());
                        }
                    }
                }
            }
        }
    }
}
