package thaumcraft.api.research.theorycraft;

import java.util.*;

public class TheorycraftManager
{
    public static HashMap<String, ITheorycraftAid> aids;
    public static HashMap<String, Class<TheorycraftCard>> cards;
    
    public static void registerAid(final ITheorycraftAid aid) {
        final String key = aid.getClass().getName();
        if (!TheorycraftManager.aids.containsKey(key)) {
            TheorycraftManager.aids.put(key, aid);
        }
    }
    
    public static void registerCard(final Class cardClass) {
        final String key = cardClass.getName();
        if (!TheorycraftManager.cards.containsKey(key)) {
            TheorycraftManager.cards.put(key, cardClass);
        }
    }
    
    static {
        TheorycraftManager.aids = new HashMap<String, ITheorycraftAid>();
        TheorycraftManager.cards = new HashMap<String, Class<TheorycraftCard>>();
    }
}
