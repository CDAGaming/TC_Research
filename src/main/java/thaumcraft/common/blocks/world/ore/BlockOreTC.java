package thaumcraft.common.blocks.world.ore;

import thaumcraft.common.blocks.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import thaumcraft.api.blocks.*;
import net.minecraft.init.*;
import thaumcraft.api.items.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class BlockOreTC extends BlockTC
{
    public BlockOreTC(final String name) {
        super(Material.field_151576_e, name);
        this.func_149752_b(5.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return (state.func_177230_c() == BlocksTC.oreQuartz) ? Items.field_151128_bU : ((state.func_177230_c() == BlocksTC.oreAmber) ? ItemsTC.amber : Item.func_150898_a(state.func_177230_c()));
    }
    
    public int func_149745_a(final Random random) {
        return (this == BlocksTC.oreAmber) ? (1 + random.nextInt(2)) : 1;
    }
    
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        final List<ItemStack> drops = (List<ItemStack>)super.getDrops(world, pos, state, fortune);
        if (this == BlocksTC.oreAmber && drops != null) {
            final Random rand = (world instanceof World) ? ((World)world).field_73012_v : BlockOreTC.RANDOM;
            for (int a = 0; a < drops.size(); ++a) {
                final ItemStack is = drops.get(a);
                if (is != null && !is.func_190926_b() && is.func_77973_b() == ItemsTC.amber && rand.nextFloat() < 0.066) {
                    drops.set(a, new ItemStack(ItemsTC.curio, 1, 1));
                }
            }
        }
        return drops;
    }
    
    public int getExpDrop(final IBlockState state, final IBlockAccess world, final BlockPos pos, final int fortune) {
        final Random rand = (world instanceof World) ? ((World)world).field_73012_v : new Random();
        if (this.func_180660_a(state, rand, fortune) != Item.func_150898_a((Block)this)) {
            int j = 0;
            if (this == BlocksTC.oreAmber || this == BlocksTC.oreQuartz) {
                j = MathHelper.func_76136_a(rand, 1, 4);
            }
            return j;
        }
        return 0;
    }
    
    public int func_149679_a(final int fortune, final Random random) {
        if (fortune > 0 && Item.func_150898_a((Block)this) != this.func_180660_a((IBlockState)this.func_176194_O().func_177619_a().iterator().next(), random, fortune)) {
            int j = random.nextInt(fortune + 2) - 1;
            if (j < 0) {
                j = 0;
            }
            return this.func_149745_a(random) * (j + 1);
        }
        return this.func_149745_a(random);
    }
}
