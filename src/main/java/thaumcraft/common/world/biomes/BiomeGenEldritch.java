package thaumcraft.common.world.biomes;

import net.minecraft.world.biome.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.math.*;

public class BiomeGenEldritch extends Biome
{
    public BiomeGenEldritch(final Biome.BiomeProperties p_i1990_1_) {
        super(p_i1990_1_);
        this.setRegistryName("thaumcraft", "eldritch");
        this.field_76761_J.clear();
        this.field_76762_K.clear();
        this.field_76755_L.clear();
        this.field_82914_M.clear();
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityInhabitedZombie.class, 1, 1, 1));
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityEldritchGuardian.class, 1, 1, 1));
        this.field_76752_A = Blocks.field_150346_d.func_176223_P();
        this.field_76753_B = Blocks.field_150346_d.func_176223_P();
    }
    
    @SideOnly(Side.CLIENT)
    public int func_76731_a(final float p_76731_1_) {
        return 0;
    }
    
    public void func_180624_a(final World world, final Random random, final BlockPos pos) {
    }
}
