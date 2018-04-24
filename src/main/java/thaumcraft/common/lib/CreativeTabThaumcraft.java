package thaumcraft.common.lib;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraftforge.fml.relauncher.*;

public final class CreativeTabThaumcraft extends CreativeTabs
{
    public CreativeTabThaumcraft(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @SideOnly(Side.CLIENT)
    public ItemStack func_78016_d() {
        return new ItemStack(ItemsTC.goggles);
    }
}
