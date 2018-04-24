package thaumcraft.common.blocks.world;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import thaumcraft.common.lib.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import java.util.*;
import thaumcraft.common.lib.utils.*;

public class BlockLoot extends BlockTC
{
    LootType type;
    Random rand;
    
    public BlockLoot(final Material mat, final String name, final LootType type) {
        super(mat, name);
        this.rand = new Random();
        this.func_149711_c(0.15f);
        this.func_149752_b(0.0f);
        this.type = type;
    }
    
    public SoundType func_185467_w() {
        return (this.field_149764_J == Material.field_151575_d) ? SoundType.field_185848_a : SoundsTC.URN;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    protected boolean func_149700_E() {
        return true;
    }
    
    public boolean canHarvestBlock(final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        if (this.func_149688_o(state) == Material.field_151576_e) {
            return new AxisAlignedBB(0.125, 0.0625, 0.125, 0.875, 0.8125, 0.875);
        }
        return new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
    }
    
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (int q = 1 + this.type.ordinal() + this.rand.nextInt(3), a = 0; a < q; ++a) {
            final ItemStack is = Utils.generateLoot(this.type.ordinal(), this.rand);
            if (is != null && !is.func_190926_b()) {
                ret.add(is.func_77946_l());
            }
        }
        return ret;
    }
    
    public enum LootType
    {
        COMMON, 
        UNCOMMON, 
        RARE;
    }
}
