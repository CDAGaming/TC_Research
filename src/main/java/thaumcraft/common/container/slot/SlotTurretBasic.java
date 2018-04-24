package thaumcraft.common.container.slot;

import thaumcraft.common.entities.construct.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class SlotTurretBasic extends SlotMobEquipment
{
    public SlotTurretBasic(final EntityTurretCrossbow turret, final int par3, final int par4, final int par5) {
        super((EntityLiving)turret, par3, par4, par5);
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return stack != null && !stack.func_190926_b() && stack.func_77973_b() != null && stack.func_77973_b() instanceof ItemArrow;
    }
    
    @Override
    public void func_75218_e() {
    }
}
