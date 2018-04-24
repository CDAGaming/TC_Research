package thaumcraft.api.research;

import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.oredict.*;
import net.minecraft.block.state.*;

public class ScanOreDictionary implements IScanThing
{
    String research;
    String[] entries;
    
    public ScanOreDictionary(final String research, final String... entries) {
        this.research = research;
        this.entries = entries;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        ItemStack stack = null;
        if (obj != null && obj instanceof BlockPos) {
            final IBlockState state = player.field_70170_p.func_180495_p((BlockPos)obj);
            stack = state.func_177230_c().func_185473_a(player.field_70170_p, (BlockPos)obj, state);
        }
        if (obj != null && obj instanceof ItemStack) {
            stack = (ItemStack)obj;
        }
        if (obj != null && obj instanceof EntityItem && ((EntityItem)obj).func_92059_d() != null) {
            stack = ((EntityItem)obj).func_92059_d();
        }
        if (stack != null && !stack.func_190926_b()) {
            final int[] ids = OreDictionary.getOreIDs(stack);
            for (final String entry : this.entries) {
                for (final int id : ids) {
                    if (OreDictionary.getOreName(id).equals(entry)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
