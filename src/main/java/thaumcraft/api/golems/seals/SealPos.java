package thaumcraft.api.golems.seals;

import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class SealPos
{
    public BlockPos pos;
    public EnumFacing face;
    
    public SealPos(final BlockPos pos, final EnumFacing face) {
        this.pos = pos;
        this.face = face;
    }
    
    @Override
    public int hashCode() {
        final byte b0 = (byte)(this.face.ordinal() + 1);
        int i = 31 * b0 + this.pos.func_177958_n();
        i = 31 * i + this.pos.func_177956_o();
        i = 31 * i + this.pos.func_177952_p();
        return i;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof SealPos)) {
            return false;
        }
        final SealPos sp = (SealPos)p_equals_1_;
        return this.pos.equals((Object)sp.pos) && this.face.equals((Object)sp.face);
    }
}
