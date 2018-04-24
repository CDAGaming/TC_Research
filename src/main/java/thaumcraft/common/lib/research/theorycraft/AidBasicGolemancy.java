package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBasicGolemancy implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.golemBuilder;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardSculpting.class, CardScripting.class, CardSynergy.class };
    }
}
