package thaumcraft.common.lib.research;

import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import thaumcraft.api.research.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import java.util.*;
import net.minecraft.entity.*;

public class ScanGeneric implements IScanThing
{
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj == null) {
            return false;
        }
        AspectList al = null;
        if (obj instanceof Entity && !(obj instanceof EntityItem)) {
            al = AspectHelper.getEntityAspects((Entity)obj);
        }
        else {
            final ItemStack is = ScanningManager.getItemFromParms(player, obj);
            if (is != null && !is.func_190926_b()) {
                al = AspectHelper.getObjectAspects(is);
            }
        }
        return al != null && al.size() > 0;
    }
    
    @Override
    public void onSuccess(final EntityPlayer player, final Object obj) {
        if (obj == null) {
            return;
        }
        AspectList al = null;
        if (obj instanceof Entity && !(obj instanceof EntityItem)) {
            al = AspectHelper.getEntityAspects((Entity)obj);
        }
        else {
            final ItemStack is = ScanningManager.getItemFromParms(player, obj);
            if (is != null && !is.func_190926_b()) {
                al = AspectHelper.getObjectAspects(is);
            }
        }
        if (al != null) {
            for (final ResearchCategory category : ResearchCategories.researchCategories.values()) {
                ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, category, category.applyFormula(al));
            }
        }
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object obj) {
        if (obj instanceof Entity && !(obj instanceof EntityItem)) {
            final String s = EntityList.func_75621_b((Entity)obj);
            return "!" + s;
        }
        final ItemStack is = ScanningManager.getItemFromParms(player, obj);
        if (is != null && !is.func_190926_b()) {
            String s2 = "!" + is.func_77973_b().getRegistryName();
            if (!is.func_77984_f()) {
                s2 += is.func_77952_i();
            }
            return s2;
        }
        return null;
    }
}
