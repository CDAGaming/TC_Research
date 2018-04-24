package thaumcraft.common.blocks;

import net.minecraft.block.material.*;
import thaumcraft.common.config.*;
import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;

public class BlockTC extends Block
{
    public BlockTC(final Material material, final String name) {
        super(material);
        this.func_149663_c(name);
        this.setRegistryName("thaumcraft", name);
        this.func_149647_a(ConfigItems.TABTC);
        this.func_149752_b(2.0f);
        this.func_149711_c(1.5f);
    }
    
    public BlockTC(final Material mat, final String name, final SoundType st) {
        this(mat, name);
        this.func_149672_a(st);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        list.add((Object)new ItemStack((Block)this, 1, 0));
    }
    
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
}
