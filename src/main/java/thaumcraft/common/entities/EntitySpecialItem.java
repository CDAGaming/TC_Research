package thaumcraft.common.entities;

import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EntitySpecialItem extends EntityItem
{
    public EntitySpecialItem(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack) {
        super(par1World);
        this.func_70105_a(0.25f, 0.25f);
        this.func_70107_b(par2, par4, par6);
        this.func_92058_a(par8ItemStack);
        this.field_70177_z = (float)(Math.random() * 360.0);
        this.field_70159_w = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.field_70181_x = 0.20000000298023224;
        this.field_70179_y = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntitySpecialItem(final World par1World) {
        super(par1World);
        this.func_70105_a(0.25f, 0.25f);
    }
    
    public void func_70071_h_() {
        if (this.field_70173_aa > 1) {
            if (this.field_70181_x > 0.0) {
                this.field_70181_x *= 0.8999999761581421;
            }
            this.field_70181_x += 0.03999999910593033;
            super.func_70071_h_();
        }
    }
    
    public boolean func_70097_a(final DamageSource source, final float damage) {
        return !source.func_94541_c() && super.func_70097_a(source, damage);
    }
}
