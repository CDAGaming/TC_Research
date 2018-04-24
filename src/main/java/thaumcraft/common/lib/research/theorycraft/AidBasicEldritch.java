package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBasicEldritch implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.eldritch;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardRealization.class, CardRevelation.class, CardTruth.class };
    }
}
