package thaumcraft.api.research;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;
import net.minecraft.block.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;

public class ScanAspect implements IScanThing
{
    String research;
    Aspect aspect;
    
    public ScanAspect(final String research, final Aspect aspect) {
        this.research = research;
        this.aspect = aspect;
    }
    
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
            ItemStack is = null;
            if (obj instanceof ItemStack) {
                is = (ItemStack)obj;
            }
            if (obj instanceof EntityItem && ((EntityItem)obj).func_92059_d() != null) {
                is = ((EntityItem)obj).func_92059_d();
            }
            if (obj instanceof BlockPos) {
                final Block b = player.field_70170_p.func_180495_p((BlockPos)obj).func_177230_c();
                is = new ItemStack(b, 1, b.func_176201_c(player.field_70170_p.func_180495_p((BlockPos)obj)));
            }
            if (is != null) {
                al = AspectHelper.getObjectAspects(is);
            }
        }
        return al != null && al.getAmount(this.aspect) > 0;
    }
    
    @Override
    public void onSuccess(final EntityPlayer player, final Object obj) {
        ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("AUROMANCY"), 1);
        ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BASICS"), 1);
        ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("ALCHEMY"), 1);
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
