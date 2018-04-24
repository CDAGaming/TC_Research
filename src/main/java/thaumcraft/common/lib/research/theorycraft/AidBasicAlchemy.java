package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBasicAlchemy implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.crucible;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardConcentrate.class, CardReactions.class, CardSynthesis.class };
    }
}
