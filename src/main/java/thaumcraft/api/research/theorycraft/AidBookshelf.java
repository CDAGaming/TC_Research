package thaumcraft.api.research.theorycraft;

import net.minecraft.init.*;

public class AidBookshelf implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return Blocks.field_150342_X;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardBalance.class, CardNotation.class, CardNotation.class, CardStudy.class, CardStudy.class, CardStudy.class };
    }
}
