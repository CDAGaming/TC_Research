package thaumcraft.common.items.resources;

import thaumcraft.common.items.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import java.util.*;

public class ItemCrystalEssence extends ItemTCEssentiaContainer
{
    public ItemCrystalEssence() {
        super("crystal_essence", 1, new String[0]);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            for (final Aspect tag : Aspect.aspects.values()) {
                final ItemStack i = new ItemStack((Item)this);
                this.setAspects(i, new AspectList().add(tag, this.base));
                items.add((Object)i);
            }
        }
    }
}
