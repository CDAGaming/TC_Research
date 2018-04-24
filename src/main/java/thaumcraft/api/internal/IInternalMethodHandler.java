package thaumcraft.api.internal;

import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;

public interface IInternalMethodHandler
{
    boolean addKnowledge(final EntityPlayer p0, final IPlayerKnowledge.EnumKnowledgeType p1, final ResearchCategory p2, final int p3);
    
    boolean progressResearch(final EntityPlayer p0, final String p1);
    
    boolean completeResearch(final EntityPlayer p0, final String p1);
    
    boolean doesPlayerHaveRequisites(final EntityPlayer p0, final String p1);
    
    void addWarpToPlayer(final EntityPlayer p0, final int p1, final IPlayerWarp.EnumWarpType p2);
    
    int getActualWarp(final EntityPlayer p0);
    
    AspectList getObjectAspects(final ItemStack p0);
    
    AspectList generateTags(final ItemStack p0);
    
    float drainVis(final World p0, final BlockPos p1, final float p2, final boolean p3);
    
    float drainFlux(final World p0, final BlockPos p1, final float p2, final boolean p3);
    
    void addVis(final World p0, final BlockPos p1, final float p2);
    
    void addFlux(final World p0, final BlockPos p1, final float p2, final boolean p3);
    
    float getTotalAura(final World p0, final BlockPos p1);
    
    float getVis(final World p0, final BlockPos p1);
    
    float getFlux(final World p0, final BlockPos p1);
    
    int getAuraBase(final World p0, final BlockPos p1);
    
    void registerSeal(final ISeal p0);
    
    ISeal getSeal(final String p0);
    
    ISealEntity getSealEntity(final int p0, final SealPos p1);
    
    void addGolemTask(final int p0, final Task p1);
    
    boolean shouldPreserveAura(final World p0, final EntityPlayer p1, final BlockPos p2);
    
    ItemStack getSealStack(final String p0);
}
