package thaumcraft.common.lib;

import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import thaumcraft.common.entities.projectile.*;

public class BehaviorDispenseAlumetum extends BehaviorProjectileDispense
{
    protected IProjectile func_82499_a(final World worldIn, final IPosition position, final ItemStack stackIn) {
        return (IProjectile)new EntityAlumentum(worldIn, position.func_82615_a(), position.func_82617_b(), position.func_82616_c());
    }
}
