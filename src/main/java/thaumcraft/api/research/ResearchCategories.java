package thaumcraft.api.research;

import net.minecraft.util.text.translation.*;
import java.util.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;

public class ResearchCategories
{
    public static LinkedHashMap<String, ResearchCategory> researchCategories;
    
    public static ResearchCategory getResearchCategory(final String key) {
        return ResearchCategories.researchCategories.get(key);
    }
    
    public static String getCategoryName(final String key) {
        return I18n.func_74838_a("tc.research_category." + key);
    }
    
    public static ResearchEntry getResearch(final String key) {
        final Collection rc = ResearchCategories.researchCategories.values();
        for (final Object cat : rc) {
            final Collection rl = ((ResearchCategory)cat).research.values();
            for (final Object ri : rl) {
                if (((ResearchEntry)ri).key.equals(key)) {
                    return (ResearchEntry)ri;
                }
            }
        }
        return null;
    }
    
    public static ResearchCategory registerCategory(final String key, final String researchkey, final AspectList formula, final ResourceLocation icon, final ResourceLocation background) {
        if (getResearchCategory(key) == null) {
            final ResearchCategory rl = new ResearchCategory(key, researchkey, formula, icon, background);
            ResearchCategories.researchCategories.put(key, rl);
            return rl;
        }
        return null;
    }
    
    public static ResearchCategory registerCategory(final String key, final String researchkey, final AspectList formula, final ResourceLocation icon, final ResourceLocation background, final ResourceLocation background2) {
        if (getResearchCategory(key) == null) {
            final ResearchCategory rl = new ResearchCategory(key, researchkey, formula, icon, background, background2);
            ResearchCategories.researchCategories.put(key, rl);
            return rl;
        }
        return null;
    }
    
    static {
        ResearchCategories.researchCategories = new LinkedHashMap<String, ResearchCategory>();
    }
}
