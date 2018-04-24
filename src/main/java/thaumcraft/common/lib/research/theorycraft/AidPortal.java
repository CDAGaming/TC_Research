package thaumcraft.common.lib.research.theorycraft;

import thaumcraft.api.research.theorycraft.*;
import net.minecraft.init.*;
import thaumcraft.common.entities.monster.cult.*;

public class AidPortal implements ITheorycraftAid
{
    Object portal;
    
    public AidPortal(final Object o) {
        this.portal = o;
    }
    
    @Override
    public Object getAidObject() {
        return this.portal;
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardPortal.class };
    }
    
    public static class AidPortalEnd extends AidPortal
    {
        public AidPortalEnd() {
            super(Blocks.field_150384_bq);
        }
    }
    
    public static class AidPortalNether extends AidPortal
    {
        public AidPortalNether() {
            super(Blocks.field_150427_aO);
        }
    }
    
    public static class AidPortalCrimson extends AidPortal
    {
        public AidPortalCrimson() {
            super(EntityCultistPortalLesser.class);
        }
    }
}
