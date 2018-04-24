package thaumcraft.common.entities.projectile;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EntityGolemDart extends EntityArrow
{
    public EntityGolemDart(final World par1World) {
        super(par1World);
        this.func_70105_a(0.2f, 0.2f);
    }
    
    public EntityGolemDart(final World par1World, final double par2, final double par4, final double par6) {
        super(par1World, par2, par4, par6);
        this.func_70105_a(0.2f, 0.2f);
    }
    
    public EntityGolemDart(final World par1World, final EntityLivingBase par2EntityLivingBase) {
        super(par1World, par2EntityLivingBase);
        this.func_70105_a(0.2f, 0.2f);
    }
    
    protected ItemStack func_184550_j() {
        return new ItemStack(Items.field_151032_g);
    }
}
