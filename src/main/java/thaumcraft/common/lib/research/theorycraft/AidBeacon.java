package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBeacon implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return new ItemStack((Block)Blocks.field_150461_bJ);
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardBeacon.class };
    }
}
