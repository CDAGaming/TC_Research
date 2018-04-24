package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBrainInAJar implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.jarBrain;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardDarkWhispers.class };
    }
}
