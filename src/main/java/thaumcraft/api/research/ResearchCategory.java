package thaumcraft.api.research;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;

public class ResearchCategory
{
    public int minDisplayColumn;
    public int minDisplayRow;
    public int maxDisplayColumn;
    public int maxDisplayRow;
    public ResourceLocation icon;
    public ResourceLocation background;
    public ResourceLocation background2;
    public String researchKey;
    public String key;
    public AspectList formula;
    public Map<String, ResearchEntry> research;
    
    public ResearchCategory(final String key, final String researchkey, final AspectList formula, final ResourceLocation icon, final ResourceLocation background) {
        this.research = new HashMap<String, ResearchEntry>();
        this.key = key;
        this.researchKey = researchkey;
        this.icon = icon;
        this.background = background;
        this.background2 = null;
        this.formula = formula;
    }
    
    public ResearchCategory(final String key, final String researchKey, final AspectList formula, final ResourceLocation icon, final ResourceLocation background, final ResourceLocation background2) {
        this.research = new HashMap<String, ResearchEntry>();
        this.key = key;
        this.researchKey = researchKey;
        this.icon = icon;
        this.background = background;
        this.background2 = background2;
        this.formula = formula;
    }
    
    public int applyFormula(final AspectList as) {
        return this.applyFormula(as, 1.0);
    }
    
    public int applyFormula(final AspectList as, final double mod) {
        if (this.formula == null) {
            return 0;
        }
        double total = 0.0;
        for (final Aspect aspect : this.formula.getAspects()) {
            total += mod * mod * as.getAmount(aspect) * (this.formula.getAmount(aspect) / 10.0);
        }
        if (total > 0.0) {
            total = Math.sqrt(total);
        }
        return MathHelper.func_76143_f(total);
    }
}
