package thaumcraft.common.lib.research;

import thaumcraft.api.research.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.util.text.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.items.*;
import thaumcraft.api.*;
import net.minecraft.util.*;
import java.util.*;

public class ScanSky implements IScanThing
{
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj != null || player.field_70125_A > 0.0f || !player.field_70170_p.func_175678_i(player.func_180425_c().func_177984_a()) || player.field_70170_p.field_73011_w.func_186058_p() != DimensionType.OVERWORLD || !ThaumcraftCapabilities.knowsResearchStrict(player, "CELESTIALSCANNING")) {
            return false;
        }
        final int yaw = (int)(player.field_70177_z + 90.0f) % 360;
        final int pitch = (int)Math.abs(player.field_70125_A);
        int ca = (int)((player.field_70170_p.func_72826_c(0.0f) + 0.25) * 360.0) % 360;
        final boolean night = ca > 180;
        boolean inRangeYaw = false;
        boolean inRangePitch = false;
        if (night) {
            ca -= 180;
        }
        if (ca > 90) {
            inRangeYaw = (Math.abs(Math.abs(yaw) - 180) < 10);
            inRangePitch = (Math.abs(180 - ca - pitch) < 7);
        }
        else {
            inRangeYaw = (Math.abs(yaw) < 10);
            inRangePitch = (Math.abs(ca - pitch) < 7);
        }
        return (inRangeYaw && inRangePitch) || night;
    }
    
    @Override
    public void onSuccess(final EntityPlayer player, final Object object) {
        if (object != null || player.field_70125_A > 0.0f || !player.field_70170_p.func_175678_i(player.func_180425_c().func_177984_a()) || !ThaumcraftCapabilities.knowsResearchStrict(player, "CELESTIALSCANNING")) {
            return;
        }
        final int yaw = (int)(player.field_70177_z + 90.0f) % 360;
        final int pitch = (int)Math.abs(player.field_70125_A);
        int ca = (int)((player.field_70170_p.func_72826_c(0.0f) + 0.25) * 360.0) % 360;
        final boolean night = ca > 180;
        boolean inRangeYaw = false;
        boolean inRangePitch = false;
        if (night) {
            ca -= 180;
        }
        if (ca > 90) {
            inRangeYaw = (Math.abs(Math.abs(yaw) - 180) < 10);
            inRangePitch = (Math.abs(180 - ca - pitch) < 7);
        }
        else {
            inRangeYaw = (Math.abs(yaw) < 10);
            inRangePitch = (Math.abs(ca - pitch) < 7);
        }
        final int worldDay = (int)(player.field_70170_p.func_82737_E() / 24000L);
        if (inRangeYaw && inRangePitch) {
            final String pk = "CEL_" + worldDay + "_";
            final String key = pk + (night ? ("Moon" + player.field_70170_p.field_73011_w.func_76559_b(player.field_70170_p.func_72820_D())) : "Sun");
            System.out.println(key);
            if (ThaumcraftCapabilities.knowsResearch(player, key)) {
                player.func_146105_b((ITextComponent)new TextComponentTranslation("tc.celestial.fail.1", new Object[] { "" }), true);
                return;
            }
            if (InventoryUtils.isPlayerCarryingAmount(player, new ItemStack(Items.field_151121_aF), true) && InventoryUtils.isPlayerCarryingAmount(player, new ItemStack(ItemsTC.scribingTools, 1, 32767), true)) {
                InventoryUtils.consumePlayerItem(player, new ItemStack(Items.field_151121_aF), true, true);
                final ItemStack stack = new ItemStack(ItemsTC.celestialNotes, 1, night ? (5 + player.field_70170_p.field_73011_w.func_76559_b(player.field_70170_p.func_72820_D())) : 0);
                if (!player.field_71071_by.func_70441_a(stack)) {
                    player.func_71019_a(stack, false);
                }
                ThaumcraftApi.internalMethods.progressResearch(player, key);
            }
            else {
                player.func_146105_b((ITextComponent)new TextComponentTranslation("tc.celestial.fail.2", new Object[] { "" }), true);
            }
            this.cleanResearch(player, pk);
        }
        else {
            if (!night) {
                return;
            }
            final EnumFacing face = player.func_184172_bi();
            final int num = face.func_176745_a() - 2;
            final String pk2 = "CEL_" + worldDay + "_";
            final String key2 = pk2 + "Star" + num;
            if (ThaumcraftCapabilities.knowsResearch(player, key2)) {
                player.func_146105_b((ITextComponent)new TextComponentTranslation("tc.celestial.fail.1", new Object[] { "" }), true);
                return;
            }
            if (InventoryUtils.isPlayerCarryingAmount(player, new ItemStack(Items.field_151121_aF), true) && InventoryUtils.isPlayerCarryingAmount(player, new ItemStack(ItemsTC.scribingTools, 1, 32767), true)) {
                InventoryUtils.consumePlayerItem(player, new ItemStack(Items.field_151121_aF), true, true);
                final ItemStack stack2 = new ItemStack(ItemsTC.celestialNotes, 1, 1 + num);
                if (!player.field_71071_by.func_70441_a(stack2)) {
                    player.func_71019_a(stack2, false);
                }
                ThaumcraftApi.internalMethods.progressResearch(player, key2);
            }
            else {
                player.func_146105_b((ITextComponent)new TextComponentTranslation("tc.celestial.fail.2", new Object[] { "" }), true);
            }
            this.cleanResearch(player, pk2);
        }
    }
    
    private void cleanResearch(final EntityPlayer player, final String pk) {
        final ArrayList<String> list = new ArrayList<String>();
        for (final String key : ThaumcraftCapabilities.getKnowledge(player).getResearchList()) {
            if (key.startsWith("CEL_") && !key.startsWith(pk)) {
                list.add(key);
            }
        }
        for (final String key : list) {
            ThaumcraftCapabilities.getKnowledge(player).removeResearch(key);
        }
        ResearchManager.syncList.put(player.func_70005_c_(), true);
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return "";
    }
}
