package thaumcraft.common.world.biomes;

import net.minecraft.world.biome.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.config.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;

public class BiomeGenEerie extends Biome
{
    public BiomeGenEerie(final Biome.BiomeProperties par1) {
        super(par1);
        this.setRegistryName("thaumcraft", "eerie");
        this.field_76762_K.clear();
        this.field_76762_K.add(new Biome.SpawnListEntry((Class)EntityBat.class, 3, 1, 1));
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityWitch.class, 8, 1, 1));
        this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityEnderman.class, 4, 1, 1));
        if (ModConfig.CONFIG_WORLD.allowSpawnAngryZombie) {
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityBrainyZombie.class, 32, 1, 1));
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityGiantBrainyZombie.class, 8, 1, 1));
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnWisp) {
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityWisp.class, 3, 1, 1));
        }
        if (ModConfig.CONFIG_WORLD.allowSpawnElder) {
            this.field_76761_J.add(new Biome.SpawnListEntry((Class)EntityEldritchGuardian.class, 1, 1, 1));
        }
        this.field_76760_I.field_76832_z = 2;
        this.field_76760_I.field_76802_A = 1;
        this.field_76760_I.field_76803_B = 2;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_180627_b(final BlockPos p_180627_1_) {
        return 4212800;
    }
    
    @SideOnly(Side.CLIENT)
    public int func_180625_c(final BlockPos p_180625_1_) {
        return 4212800;
    }
    
    public int func_76731_a(final float par1) {
        return 2237081;
    }
    
    public int getWaterColorMultiplier() {
        return 3035999;
    }
}
