package thaumcraft.api.research;

import net.minecraft.entity.player.*;

public interface IScanThing
{
    boolean checkThing(final EntityPlayer p0, final Object p1);
    
    String getResearchKey(final EntityPlayer p0, final Object p1);
    
    default void onSuccess(final EntityPlayer player, final Object object) {
    }
}
