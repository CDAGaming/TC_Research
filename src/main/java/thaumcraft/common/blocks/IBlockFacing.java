package thaumcraft.common.blocks;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public interface IBlockFacing
{
    public static final PropertyDirection FACING = PropertyDirection.func_177712_a("facing", (Predicate)new Predicate() {
        public boolean apply(final EnumFacing facing) {
            return true;
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.apply((EnumFacing)p_apply_1_);
        }
    });
}
