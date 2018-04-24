package thaumcraft.api.aura;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import thaumcraft.api.*;
import net.minecraft.entity.player.*;

public class AuraHelper
{
    public static float drainVis(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return ThaumcraftApi.internalMethods.drainVis(world, pos, amount, simulate);
    }
    
    public static float drainFlux(final World world, final BlockPos pos, final float amount, final boolean simulate) {
        return ThaumcraftApi.internalMethods.drainFlux(world, pos, amount, simulate);
    }
    
    public static void addVis(final World world, final BlockPos pos, final float amount) {
        ThaumcraftApi.internalMethods.addVis(world, pos, amount);
    }
    
    public static float getVis(final World world, final BlockPos pos) {
        return ThaumcraftApi.internalMethods.getVis(world, pos);
    }
    
    public static void polluteAura(final World world, final BlockPos pos, final float amount, final boolean showEffect) {
        ThaumcraftApi.internalMethods.addFlux(world, pos, amount, showEffect);
    }
    
    public static float getFlux(final World world, final BlockPos pos) {
        return ThaumcraftApi.internalMethods.getFlux(world, pos);
    }
    
    public static int getAuraBase(final World world, final BlockPos pos) {
        return ThaumcraftApi.internalMethods.getAuraBase(world, pos);
    }
    
    public static boolean shouldPreserveAura(final World world, final EntityPlayer player, final BlockPos pos) {
        return ThaumcraftApi.internalMethods.shouldPreserveAura(world, player, pos);
    }
}
