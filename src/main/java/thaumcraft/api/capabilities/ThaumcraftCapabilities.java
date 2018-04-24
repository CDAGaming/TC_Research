package thaumcraft.api.capabilities;

import net.minecraftforge.common.capabilities.*;
import net.minecraft.entity.player.*;
import javax.annotation.*;
import net.minecraft.util.*;

public class ThaumcraftCapabilities
{
    @CapabilityInject(IPlayerKnowledge.class)
    public static final Capability<IPlayerKnowledge> KNOWLEDGE;
    @CapabilityInject(IPlayerWarp.class)
    public static final Capability<IPlayerWarp> WARP;
    
    public static IPlayerKnowledge getKnowledge(@Nonnull final EntityPlayer player) {
        return (IPlayerKnowledge)player.getCapability((Capability)ThaumcraftCapabilities.KNOWLEDGE, (EnumFacing)null);
    }
    
    public static boolean knowsResearch(@Nonnull final EntityPlayer player, @Nonnull final String... research) {
        for (final String r : research) {
            if (r.contains("&&")) {
                final String[] rr = r.split("&&");
                if (!knowsResearch(player, rr)) {
                    return false;
                }
            }
            else if (r.contains("||")) {
                final String[] split;
                final String[] rr = split = r.split("||");
                for (final String str : split) {
                    if (knowsResearch(player, str)) {
                        return true;
                    }
                }
            }
            else if (!getKnowledge(player).isResearchKnown(r)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean knowsResearchStrict(@Nonnull final EntityPlayer player, @Nonnull final String... research) {
        for (final String r : research) {
            if (r.contains("&&")) {
                final String[] rr = r.split("&&");
                if (!knowsResearchStrict(player, rr)) {
                    return false;
                }
            }
            else if (r.contains("||")) {
                final String[] split;
                final String[] rr = split = r.split("||");
                for (final String str : split) {
                    if (knowsResearchStrict(player, str)) {
                        return true;
                    }
                }
            }
            else if (r.contains("@")) {
                if (!getKnowledge(player).isResearchKnown(r)) {
                    return false;
                }
            }
            else if (!getKnowledge(player).isResearchComplete(r)) {
                return false;
            }
        }
        return true;
    }
    
    public static IPlayerWarp getWarp(@Nonnull final EntityPlayer player) {
        return (IPlayerWarp)player.getCapability((Capability)ThaumcraftCapabilities.WARP, (EnumFacing)null);
    }
    
    static {
        KNOWLEDGE = null;
        WARP = null;
    }
}
