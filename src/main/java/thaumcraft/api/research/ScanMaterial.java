package thaumcraft.api.research;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class ScanMaterial implements IScanThing
{
    String research;
    Material[] mats;
    
    public ScanMaterial(final Material mat) {
        this.research = "!" + mat.getClass().getTypeName();
        this.mats = new Material[] { mat };
    }
    
    public ScanMaterial(final String research, final Material... mats) {
        this.research = research;
        this.mats = mats;
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj != null && obj instanceof BlockPos) {
            for (final Material mat : this.mats) {
                if (player.field_70170_p.func_180495_p((BlockPos)obj).func_185904_a() == mat) {
                    return true;
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
