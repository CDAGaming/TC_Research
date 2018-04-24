package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import net.minecraft.item.*;
import thaumcraft.api.research.theorycraft.*;

public class AidGlyphedStone implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return new ItemStack(BlocksTC.stoneAncientGlyphed);
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardGlyphs.class };
    }
}
