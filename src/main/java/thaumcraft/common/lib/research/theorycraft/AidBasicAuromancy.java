package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBasicAuromancy implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.wandWorkbench;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardFocus.class, CardAwareness.class, CardSpellbinding.class };
    }
}
