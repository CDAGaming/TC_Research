package thaumcraft.api.items;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class ItemGenericEssentiaContainer extends Item implements IEssentiaContainerItem
{
    protected int base;
    
    public ItemGenericEssentiaContainer(final int base) {
        this.base = 1;
        this.base = base;
        this.func_77625_d(64);
        this.func_77627_a(true);
        this.func_77656_e(0);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        for (final Aspect tag : Aspect.aspects.values()) {
            final ItemStack i = new ItemStack((Item)this);
            this.setAspects(i, new AspectList().add(tag, this.base));
            items.add((Object)i);
        }
    }
    
    public AspectList getAspects(final ItemStack itemstack) {
        if (itemstack.func_77942_o()) {
            final AspectList aspects = new AspectList();
            aspects.readFromNBT(itemstack.func_77978_p());
            return (aspects.size() > 0) ? aspects : null;
        }
        return null;
    }
    
    public void setAspects(final ItemStack itemstack, final AspectList aspects) {
        if (!itemstack.func_77942_o()) {
            itemstack.func_77982_d(new NBTTagCompound());
        }
        aspects.writeToNBT(itemstack.func_77978_p());
    }
    
    public boolean ignoreContainedAspects() {
        return false;
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (!world.field_72995_K && !stack.func_77942_o()) {
            final Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
            this.setAspects(stack, new AspectList().add(displayAspects[world.field_73012_v.nextInt(displayAspects.length)], this.base));
        }
        super.func_77663_a(stack, world, entity, par4, par5);
    }
    
    public void func_77622_d(final ItemStack stack, final World world, final EntityPlayer player) {
        if (!world.field_72995_K && !stack.func_77942_o()) {
            final Aspect[] displayAspects = Aspect.aspects.values().toArray(new Aspect[0]);
            this.setAspects(stack, new AspectList().add(displayAspects[world.field_73012_v.nextInt(displayAspects.length)], this.base));
        }
    }
}
