package thaumcraft.common.blocks.basic;

import thaumcraft.common.blocks.*;
import thaumcraft.api.internal.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import thaumcraft.api.items.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.config.*;
import net.minecraft.init.*;
import java.util.*;

public class BlockStonePorous extends BlockTC
{
    static Random r;
    static ArrayList<WeightedRandomLoot> pdrops;
    
    public BlockStonePorous() {
        super(Material.field_151576_e, "stone_porous");
        this.func_149711_c(1.0f);
        this.func_149752_b(5.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, final IBlockState state, final int fortune) {
        final List<ItemStack> ret = new ArrayList<ItemStack>();
        final int rr = BlockStonePorous.r.nextInt(15) + fortune;
        if (rr > 13) {
            if (BlockStonePorous.pdrops == null || BlockStonePorous.pdrops.size() <= 0) {
                this.createDrops();
            }
            final ItemStack s = ((WeightedRandomLoot)WeightedRandom.func_76271_a(BlockStonePorous.r, (List)BlockStonePorous.pdrops)).item.func_77946_l();
            ret.add(s);
        }
        else {
            ret.add(new ItemStack(Blocks.field_150351_n));
        }
        return ret;
    }
    
    private void createDrops() {
        BlockStonePorous.pdrops = new ArrayList<WeightedRandomLoot>();
        for (final Aspect aspect : Aspect.getCompoundAspects()) {
            final ItemStack is = new ItemStack(ItemsTC.crystalEssence);
            ((ItemGenericEssentiaContainer)ItemsTC.crystalEssence).setAspects(is, new AspectList().add(aspect, (aspect == Aspect.FLUX) ? 100 : (aspect.isPrimal() ? 20 : 1)));
            BlockStonePorous.pdrops.add(new WeightedRandomLoot(is, 1));
        }
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.amber), 20));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 0), 20));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 1), 10));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 6), 10));
        if (ModConfig.foundCopperIngot) {
            BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 2), 10));
        }
        if (ModConfig.foundTinIngot) {
            BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 3), 10));
        }
        if (ModConfig.foundSilverIngot) {
            BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 4), 8));
        }
        if (ModConfig.foundLeadIngot) {
            BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 5), 10));
        }
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_151045_i), 2));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_151166_bC), 4));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_151137_ax), 8));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_179563_cD), 3));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_179562_cC), 3));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_151119_aD), 30));
        BlockStonePorous.pdrops.add(new WeightedRandomLoot(new ItemStack(Items.field_151128_bU), 15));
    }
    
    static {
        BlockStonePorous.r = new Random(System.currentTimeMillis());
        BlockStonePorous.pdrops = null;
    }
}
