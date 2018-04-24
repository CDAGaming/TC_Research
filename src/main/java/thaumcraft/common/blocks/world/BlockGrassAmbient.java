package thaumcraft.common.blocks.world;

import thaumcraft.common.config.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import thaumcraft.client.fx.*;
import net.minecraft.world.biome.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockGrassAmbient extends BlockGrass
{
    public BlockGrassAmbient() {
        this.func_149663_c("grass_ambient");
        this.setRegistryName("thaumcraft", "grass_ambient");
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149711_c(0.6f);
        this.func_149672_a(SoundType.field_185849_b);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_180655_c(final IBlockState state, final World worldIn, final BlockPos pos, final Random rand) {
        final Biome biome = worldIn.func_180494_b(pos);
        int i = worldIn.func_175642_b(EnumSkyBlock.SKY, pos.func_177984_a()) - worldIn.func_175657_ab();
        float f = worldIn.func_72929_e(1.0f);
        final float f2 = (f < 3.1415927f) ? 0.0f : 6.2831855f;
        f += (f2 - f) * 0.2f;
        i = Math.round(i * MathHelper.func_76134_b(f));
        i = MathHelper.func_76125_a(i, 0, 15);
        if (4 + i * 2 < 1 + rand.nextInt(13)) {
            final int x = MathHelper.func_76136_a(rand, -8, 8);
            final int z = MathHelper.func_76136_a(rand, -8, 8);
            BlockPos pp = pos.func_177982_a(x, 5, z);
            for (int q = 0; q < 10 && pp.func_177956_o() > 50 && worldIn.func_180495_p(pp).func_177230_c() != Blocks.field_150349_c; pp = pp.func_177977_b(), ++q) {}
            if (worldIn.func_180495_p(pp).func_177230_c() == Blocks.field_150349_c) {
                FXDispatcher.INSTANCE.drawWispyMotesOnBlock(pp.func_177984_a(), 400, -0.01f);
            }
        }
    }
}
