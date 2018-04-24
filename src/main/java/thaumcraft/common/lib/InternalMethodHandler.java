package thaumcraft.common.lib;

import thaumcraft.api.internal.*;
import thaumcraft.api.research.*;
import thaumcraft.common.lib.research.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.common.entities.construct.golem.seals.*;

public class InternalMethodHandler implements IInternalMethodHandler
{
    @Override
    public boolean addKnowledge(final EntityPlayer player, final IPlayerKnowledge.EnumKnowledgeType type, final ResearchCategory field, final int amount) {
        return amount != 0 && !player.field_70170_p.field_72995_K && ResearchManager.addKnowledge(player, type, field, amount);
    }
    
    @Override
    public void addWarpToPlayer(final EntityPlayer player, int amount, final IPlayerWarp.EnumWarpType type) {
        if (amount == 0 || player.field_70170_p.field_72995_K) {
            return;
        }
        final IPlayerWarp pw = ThaumcraftCapabilities.getWarp(player);
        final int cur = pw.get(type);
        if (amount < 0 && cur + amount < 0) {
            amount = cur;
        }
        pw.add(type, amount);
        if (type == IPlayerWarp.EnumWarpType.PERMANENT) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketWarpMessage(player, (byte)0, amount), (EntityPlayerMP)player);
        }
        if (type == IPlayerWarp.EnumWarpType.NORMAL) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketWarpMessage(player, (byte)1, amount), (EntityPlayerMP)player);
        }
        if (type == IPlayerWarp.EnumWarpType.TEMPORARY) {
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketWarpMessage(player, (byte)2, amount), (EntityPlayerMP)player);
        }
        if (amount > 0) {
            pw.setCounter(pw.get(IPlayerWarp.EnumWarpType.TEMPORARY) + pw.get(IPlayerWarp.EnumWarpType.PERMANENT) + pw.get(IPlayerWarp.EnumWarpType.NORMAL));
        }
        if (type != IPlayerWarp.EnumWarpType.TEMPORARY && ThaumcraftCapabilities.knowsResearchStrict(player, "FIRSTSTEPS") && !ThaumcraftCapabilities.knowsResearchStrict(player, "WARP")) {
            this.completeResearch(player, "WARP");
            player.func_146105_b((ITextComponent)new TextComponentTranslation("research.WARP.warn", new Object[0]), true);
        }
        pw.sync((EntityPlayerMP)player);
    }
    
    @Override
    public boolean progressResearch(final EntityPlayer player, final String researchkey) {
        return researchkey != null && !player.field_70170_p.field_72995_K && ResearchManager.progressResearch(player, researchkey);
    }
    
    @Override
    public boolean completeResearch(final EntityPlayer player, final String researchkey) {
        return researchkey != null && !player.field_70170_p.field_72995_K && ResearchManager.completeResearch(player, researchkey);
    }
    
    @Override
    public boolean doesPlayerHaveRequisites(final EntityPlayer player, final String researchkey) {
        return ResearchManager.doesPlayerHaveRequisites(player, researchkey);
    }
    
    @Override
    public AspectList getObjectAspects(final ItemStack is) {
        return ThaumcraftCraftingManager.getObjectTags(is);
    }
    
    @Override
    public AspectList generateTags(final ItemStack is) {
        return ThaumcraftCraftingManager.generateTags(is);
    }
    
    @Override
    public float drainFlux(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return AuraHandler.drainFlux(world, pos, amount, simulate);
    }
    
    @Override
    public float getFlux(final World world, final BlockPos pos) {
        return AuraHandler.getFlux(world, pos);
    }
    
    @Override
    public float drainVis(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return AuraHandler.drainVis(world, pos, amount, simulate);
    }
    
    @Override
    public void addVis(final World world, final BlockPos pos, final float amount) {
        AuraHandler.addVis(world, pos, amount);
    }
    
    @Override
    public float getTotalAura(final World world, final BlockPos pos) {
        return AuraHandler.getTotalAura(world, pos);
    }
    
    @Override
    public float getVis(final World world, final BlockPos pos) {
        return AuraHandler.getVis(world, pos);
    }
    
    @Override
    public int getAuraBase(final World world, final BlockPos pos) {
        return AuraHandler.getAuraBase(world, pos);
    }
    
    @Override
    public void addFlux(final World world, final BlockPos pos, final float amount, final boolean showEffect) {
        if (world.field_72995_K) {
            return;
        }
        AuraHandler.addFlux(world, pos, amount);
        if (showEffect && amount > 0.0f) {
            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXPollute(pos, amount), new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), 32.0));
        }
    }
    
    @Override
    public void registerSeal(final ISeal seal) {
        SealHandler.registerSeal(seal);
    }
    
    @Override
    public ISeal getSeal(final String key) {
        return SealHandler.getSeal(key);
    }
    
    @Override
    public ISealEntity getSealEntity(final int dim, final SealPos pos) {
        return SealHandler.getSealEntity(dim, pos);
    }
    
    @Override
    public void addGolemTask(final int dim, final Task task) {
        TaskHandler.addTask(dim, task);
    }
    
    @Override
    public boolean shouldPreserveAura(final World world, final EntityPlayer player, final BlockPos pos) {
        return AuraHandler.shouldPreserveAura(world, player, pos);
    }
    
    @Override
    public ItemStack getSealStack(final String key) {
        return ItemSealPlacer.getSealStack(key);
    }
    
    @Override
    public int getActualWarp(final EntityPlayer player) {
        final IPlayerWarp wc = ThaumcraftCapabilities.getWarp(player);
        return wc.get(IPlayerWarp.EnumWarpType.NORMAL) + wc.get(IPlayerWarp.EnumWarpType.PERMANENT);
    }
}
