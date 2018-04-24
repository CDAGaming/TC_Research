package thaumcraft.api.internal;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.research.*;

public class DummyInternalMethodHandler implements IInternalMethodHandler
{
    @Override
    public boolean completeResearch(final EntityPlayer player, final String researchkey) {
        return false;
    }
    
    @Override
    public void addWarpToPlayer(final EntityPlayer player, final int amount, final IPlayerWarp.EnumWarpType type) {
    }
    
    @Override
    public AspectList getObjectAspects(final ItemStack is) {
        return null;
    }
    
    @Override
    public AspectList generateTags(final ItemStack is) {
        return null;
    }
    
    @Override
    public float drainVis(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return 0.0f;
    }
    
    @Override
    public float drainFlux(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return 0.0f;
    }
    
    @Override
    public void addVis(final World world, final BlockPos pos, final float amount) {
    }
    
    @Override
    public void addFlux(final World world, final BlockPos pos, final float amount, final boolean showEffect) {
    }
    
    @Override
    public float getTotalAura(final World world, final BlockPos pos) {
        return 0.0f;
    }
    
    @Override
    public float getVis(final World world, final BlockPos pos) {
        return 0.0f;
    }
    
    @Override
    public float getFlux(final World world, final BlockPos pos) {
        return 0.0f;
    }
    
    @Override
    public int getAuraBase(final World world, final BlockPos pos) {
        return 0;
    }
    
    @Override
    public void registerSeal(final ISeal seal) {
    }
    
    @Override
    public ISeal getSeal(final String key) {
        return null;
    }
    
    @Override
    public ISealEntity getSealEntity(final int dim, final SealPos pos) {
        return null;
    }
    
    @Override
    public void addGolemTask(final int dim, final Task task) {
    }
    
    @Override
    public boolean shouldPreserveAura(final World world, final EntityPlayer player, final BlockPos pos) {
        return false;
    }
    
    @Override
    public ItemStack getSealStack(final String key) {
        return null;
    }
    
    @Override
    public boolean doesPlayerHaveRequisites(final EntityPlayer player, final String researchkey) {
        return false;
    }
    
    @Override
    public boolean addKnowledge(final EntityPlayer player, final IPlayerKnowledge.EnumKnowledgeType type, final ResearchCategory field, final int amount) {
        return false;
    }
    
    @Override
    public boolean progressResearch(final EntityPlayer player, final String researchkey) {
        return false;
    }
    
    @Override
    public int getActualWarp(final EntityPlayer player) {
        return 0;
    }
}
