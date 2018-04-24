package thaumcraft.common.lib;

import net.minecraft.server.*;
import net.minecraft.util.text.*;
import thaumcraft.common.lib.research.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.util.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.research.*;

public class CommandThaumcraft extends CommandBase
{
    private List aliases;
    
    public CommandThaumcraft() {
        (this.aliases = new ArrayList()).add("thaumcraft");
        this.aliases.add("thaum");
        this.aliases.add("tc");
    }
    
    public String func_71517_b() {
        return "thaumcraft";
    }
    
    public List<String> func_71514_a() {
        return (List<String>)this.aliases;
    }
    
    public String func_71518_a(final ICommandSender icommandsender) {
        return "/thaumcraft <action> [<player> [<params>]]";
    }
    
    public int func_82362_a() {
        return 2;
    }
    
    public boolean func_82358_a(final String[] astring, final int i) {
        return i == 1;
    }
    
    public void func_184881_a(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length == 0) {
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cUse /thaumcraft help to get help", new Object[0]));
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            for (final ResearchCategory rc : ResearchCategories.researchCategories.values()) {
                rc.research.clear();
            }
            ResearchManager.parseAllResearch();
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
        }
        else if (args[0].equalsIgnoreCase("help")) {
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§3You can also use /thaum or /tc instead of /thaumcraft.", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§3Use this to give research to a player.", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("  /thaumcraft research <list|player> <list|all|reset|<research>>", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§3Use this to give set a players warp level.", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("  /thaumcraft warp <player> <add|set> <amount> <PERM|TEMP>", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("  not specifying perm or temp will just add normal warp", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§3Use this to reload json research data", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("  /thaumcraft reload", new Object[0]));
        }
        else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("research") && args[1].equalsIgnoreCase("list")) {
                this.listResearch(sender);
            }
            else {
                final EntityPlayerMP entityplayermp = func_184888_a(server, sender, args[1]);
                if (args[0].equalsIgnoreCase("research")) {
                    if (args.length == 3) {
                        if (args[2].equalsIgnoreCase("list")) {
                            this.listAllResearch(sender, entityplayermp);
                        }
                        else if (args[2].equalsIgnoreCase("all")) {
                            this.giveAllResearch(sender, entityplayermp);
                        }
                        else if (args[2].equalsIgnoreCase("reset")) {
                            this.resetResearch(sender, entityplayermp);
                        }
                        else {
                            this.giveResearch(sender, entityplayermp, args[2]);
                        }
                    }
                    else {
                        sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
                        sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cUse /thaumcraft research <list|player> <list|all|reset|<research>>", new Object[0]));
                    }
                }
                else if (args[0].equalsIgnoreCase("warp")) {
                    if (args.length >= 4 && args[2].equalsIgnoreCase("set")) {
                        final int i = func_180528_a(args[3], 0);
                        this.setWarp(sender, entityplayermp, i, (args.length == 5) ? args[4] : "");
                    }
                    else if (args.length >= 4 && args[2].equalsIgnoreCase("add")) {
                        final int i = func_175764_a(args[3], -100, 100);
                        this.addWarp(sender, entityplayermp, i, (args.length == 5) ? args[4] : "");
                    }
                    else {
                        sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
                        sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cUse /thaumcraft warp <player> <add|set> <amount> <PERM|TEMP>", new Object[0]));
                    }
                }
                else {
                    sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
                    sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cUse /thaumcraft help to get help", new Object[0]));
                }
            }
        }
        else {
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cInvalid arguments", new Object[0]));
            sender.func_145747_a((ITextComponent)new TextComponentTranslation("§cUse /thaumcraft help to get help", new Object[0]));
        }
    }
    
    private void setWarp(final ICommandSender icommandsender, final EntityPlayerMP player, final int i, final String type) {
        if (type.equalsIgnoreCase("PERM")) {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).set(IPlayerWarp.EnumWarpType.PERMANENT, i);
        }
        else if (type.equalsIgnoreCase("TEMP")) {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).set(IPlayerWarp.EnumWarpType.TEMPORARY, i);
        }
        else {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).set(IPlayerWarp.EnumWarpType.NORMAL, i);
        }
        ThaumcraftCapabilities.getWarp((EntityPlayer)player).sync(player);
        player.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + icommandsender.func_70005_c_() + " set your warp to " + i, new Object[0]));
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
    }
    
    private void addWarp(final ICommandSender icommandsender, final EntityPlayerMP player, final int i, final String type) {
        if (type.equalsIgnoreCase("PERM")) {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).add(IPlayerWarp.EnumWarpType.PERMANENT, i);
        }
        else if (type.equalsIgnoreCase("TEMP")) {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).add(IPlayerWarp.EnumWarpType.TEMPORARY, i);
        }
        else {
            ThaumcraftCapabilities.getWarp((EntityPlayer)player).add(IPlayerWarp.EnumWarpType.NORMAL, i);
        }
        ThaumcraftCapabilities.getWarp((EntityPlayer)player).sync(player);
        PacketHandler.INSTANCE.sendTo((IMessage)new PacketWarpMessage((EntityPlayer)player, (byte)0, i), player);
        player.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + icommandsender.func_70005_c_() + " added " + i + " warp to your total.", new Object[0]));
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
    }
    
    private void listResearch(final ICommandSender icommandsender) {
        final Collection<ResearchCategory> rc = ResearchCategories.researchCategories.values();
        for (final ResearchCategory cat : rc) {
            final Collection<ResearchEntry> rl = cat.research.values();
            for (final ResearchEntry ri : rl) {
                icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + ri.getKey(), new Object[0]));
            }
        }
    }
    
    void giveResearch(final ICommandSender icommandsender, final EntityPlayerMP player, final String research) {
        if (ResearchCategories.getResearch(research) != null) {
            giveRecursiveResearch((EntityPlayer)player, research);
            ThaumcraftCapabilities.getKnowledge((EntityPlayer)player).sync(player);
            player.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + icommandsender.func_70005_c_() + " gave you " + research + " research and its requisites.", new Object[0]));
            icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
        }
        else {
            icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§cResearch does not exist.", new Object[0]));
        }
    }
    
    public static void giveRecursiveResearch(final EntityPlayer player, String research) {
        if (research.contains("@")) {
            final int i = research.indexOf("@");
            research = research.substring(0, i);
        }
        final ResearchEntry res = ResearchCategories.getResearch(research);
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        if (!knowledge.isResearchComplete(research)) {
            if (res != null && res.getParents() != null) {
                for (final String rsi : res.getParentsStripped()) {
                    giveRecursiveResearch(player, rsi);
                }
            }
            if (res != null && res.getStages() != null) {
                for (final ResearchStage page : res.getStages()) {
                    if (page.getResearch() != null) {
                        for (final String gr : page.getResearch()) {
                            ResearchManager.completeResearch(player, gr);
                        }
                    }
                }
            }
            ResearchManager.completeResearch(player, research);
            for (final String rc : ResearchCategories.researchCategories.keySet()) {
                for (final ResearchEntry ri : ResearchCategories.getResearchCategory(rc).research.values()) {
                    if (ri.getStages() != null) {
                        for (final ResearchStage stage : ri.getStages()) {
                            if (stage.getResearch() != null && Arrays.asList(stage.getResearch()).contains(research)) {
                                ThaumcraftCapabilities.getKnowledge(player).setResearchFlag(ri.getKey(), IPlayerKnowledge.EnumResearchFlag.PAGE);
                                break;
                            }
                        }
                    }
                }
            }
            if (res != null && res.getSiblings() != null) {
                for (final String rsi : res.getSiblings()) {
                    giveRecursiveResearch(player, rsi);
                }
            }
        }
    }
    
    void listAllResearch(final ICommandSender icommandsender, final EntityPlayerMP player) {
        String ss = "";
        for (final String key : ThaumcraftCapabilities.getKnowledge((EntityPlayer)player).getResearchList()) {
            if (ss.length() != 0) {
                ss += ", ";
            }
            ss += key;
        }
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Research for " + player.func_70005_c_(), new Object[0]));
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + ss, new Object[0]));
    }
    
    void giveAllResearch(final ICommandSender icommandsender, final EntityPlayerMP player) {
        final Collection<ResearchCategory> rc = ResearchCategories.researchCategories.values();
        for (final ResearchCategory cat : rc) {
            final Collection<ResearchEntry> rl = cat.research.values();
            for (final ResearchEntry ri : rl) {
                giveRecursiveResearch((EntityPlayer)player, ri.getKey());
            }
        }
        player.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + icommandsender.func_70005_c_() + " has given you all research.", new Object[0]));
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
    }
    
    void resetResearch(final ICommandSender icommandsender, final EntityPlayerMP player) {
        ThaumcraftCapabilities.getKnowledge((EntityPlayer)player).clear();
        final Collection<ResearchCategory> rc = ResearchCategories.researchCategories.values();
        for (final ResearchCategory cat : rc) {
            final Collection<ResearchEntry> res = cat.research.values();
            for (final ResearchEntry ri : res) {
                if (ri.hasMeta(ResearchEntry.EnumResearchMeta.AUTOUNLOCK)) {
                    ResearchManager.completeResearch((EntityPlayer)player, ri.getKey(), false);
                }
            }
        }
        player.func_145747_a((ITextComponent)new TextComponentTranslation("§5" + icommandsender.func_70005_c_() + " has reset all your research.", new Object[0]));
        icommandsender.func_145747_a((ITextComponent)new TextComponentTranslation("§5Success!", new Object[0]));
        ThaumcraftCapabilities.getKnowledge((EntityPlayer)player).sync(player);
    }
}
