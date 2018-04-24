package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.blocks.*;
import thaumcraft.api.research.theorycraft.*;

public class AidBasicArtifice implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return BlocksTC.infusionMatrix;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardCalibrate.class, CardTinker.class, CardMindOverMatter.class };
    }
}
