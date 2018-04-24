package thaumcraft.common.lib.events;

import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.item.*;
import thaumcraft.common.items.armor.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.config.*;
import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import thaumcraft.api.potions.*;
import net.minecraft.potion.*;
import thaumcraft.api.items.*;
import net.minecraft.init.*;
import thaumcraft.common.lib.potions.*;
import thaumcraft.common.entities.monster.cult.*;
import net.minecraft.util.math.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.lib.utils.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.entity.*;
import java.util.*;
import baubles.api.*;
import net.minecraft.inventory.*;

public class WarpEvents
{
    public static void checkWarpEvent(final EntityPlayer player) {
        final IPlayerWarp wc = ThaumcraftCapabilities.getWarp(player);
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, -1, IPlayerWarp.EnumWarpType.TEMPORARY);
        final int tw = wc.get(IPlayerWarp.EnumWarpType.TEMPORARY);
        final int nw = wc.get(IPlayerWarp.EnumWarpType.NORMAL);
        final int pw = wc.get(IPlayerWarp.EnumWarpType.PERMANENT);
        int warp = tw + nw + pw;
        final int actualwarp = pw + nw;
        final int gearWarp = getWarpFromGear(player);
        warp += gearWarp;
        int warpCounter = wc.getCounter();
        final int r = player.field_70170_p.field_73012_v.nextInt(100);
        if (warpCounter > 0 && warp > 0 && r <= Math.sqrt(warpCounter)) {
            warp = Math.min(100, (warp + warp + warpCounter) / 3);
            warpCounter -= (int)Math.max(5.0, Math.sqrt(warpCounter) * 2.0 - gearWarp * 2);
            wc.setCounter(warpCounter);
            int eff = player.field_70170_p.field_73012_v.nextInt(warp) + gearWarp;
            final ItemStack helm = (ItemStack)player.field_71071_by.field_70460_b.get(3);
            if (helm.func_77973_b() instanceof ItemFortressArmor && helm.func_77942_o() && helm.func_77978_p().func_74764_b("mask") && helm.func_77978_p().func_74762_e("mask") == 0) {
                eff -= 2 + player.field_70170_p.field_73012_v.nextInt(4);
            }
            PacketHandler.INSTANCE.sendTo((IMessage)new PacketMiscEvent((byte)0), (EntityPlayerMP)player);
            if (eff > 0) {
                if (eff <= 4) {
                    if (!ModConfig.CONFIG_GRAPHICS.nostress) {
                        player.field_70170_p.func_184133_a(player, player.func_180425_c(), SoundEvents.field_187572_ar, SoundCategory.AMBIENT, 1.0f, 0.5f);
                    }
                }
                else if (eff <= 8) {
                    if (!ModConfig.CONFIG_GRAPHICS.nostress) {
                        player.field_70170_p.func_184148_a(player, player.field_70165_t + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 10.0f, player.field_70163_u + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 10.0f, player.field_70161_v + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 10.0f, SoundEvents.field_187539_bB, SoundCategory.AMBIENT, 4.0f, (1.0f + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
                    }
                }
                else if (eff <= 12) {
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.11")), true);
                }
                else if (eff <= 16) {
                    final PotionEffect pe = new PotionEffect(PotionVisExhaust.instance, 5000, Math.min(3, warp / 15), true, true);
                    pe.getCurativeItems().clear();
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.1")), true);
                }
                else if (eff <= 20) {
                    final PotionEffect pe = new PotionEffect(PotionThaumarhia.instance, Math.min(32000, 10 * warp), 0, true, true);
                    pe.getCurativeItems().clear();
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.15")), true);
                }
                else if (eff <= 24) {
                    final PotionEffect pe = new PotionEffect(PotionUnnaturalHunger.instance, 5000, Math.min(3, warp / 15), true, true);
                    pe.getCurativeItems().clear();
                    pe.addCurativeItem(new ItemStack(Items.field_151078_bh));
                    pe.addCurativeItem(new ItemStack(ItemsTC.brain));
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.2")), true);
                }
                else if (eff <= 28) {
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.12")), true);
                }
                else if (eff <= 32) {
                    spawnMist(player, warp, 1);
                }
                else if (eff <= 36) {
                    try {
                        player.func_70690_d(new PotionEffect(PotionBlurredVision.instance, Math.min(32000, 10 * warp), 0, true, true));
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                else if (eff <= 40) {
                    final PotionEffect pe = new PotionEffect(PotionSunScorned.instance, 5000, Math.min(3, warp / 15), true, true);
                    pe.getCurativeItems().clear();
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.5")), true);
                }
                else if (eff <= 44) {
                    try {
                        player.func_70690_d(new PotionEffect(MobEffects.field_76419_f, 1200, Math.min(3, warp / 15), true, true));
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.9")), true);
                }
                else if (eff <= 48) {
                    final PotionEffect pe = new PotionEffect(PotionInfectiousVisExhaust.instance, 6000, Math.min(3, warp / 15));
                    pe.getCurativeItems().clear();
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.1")), true);
                }
                else if (eff <= 52) {
                    player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, Math.min(40 * warp, 6000), 0, true, true));
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.10")), true);
                }
                else if (eff <= 56) {
                    final PotionEffect pe = new PotionEffect(PotionDeathGaze.instance, 6000, Math.min(3, warp / 15), true, true);
                    pe.getCurativeItems().clear();
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.4")), true);
                }
                else if (eff <= 60) {
                    suddenlySpiders(player, warp, false);
                }
                else if (eff <= 64) {
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.13")), true);
                }
                else if (eff <= 68) {
                    spawnMist(player, warp, warp / 30);
                }
                else if (eff <= 72) {
                    try {
                        player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, Math.min(32000, 5 * warp), 0, true, true));
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                else if (eff == 76) {
                    if (nw > 0) {
                        ThaumcraftApi.internalMethods.addWarpToPlayer(player, -1, IPlayerWarp.EnumWarpType.NORMAL);
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.14")), true);
                }
                else if (eff <= 80) {
                    final PotionEffect pe = new PotionEffect(PotionUnnaturalHunger.instance, 6000, Math.min(3, warp / 15), true, true);
                    pe.getCurativeItems().clear();
                    pe.addCurativeItem(new ItemStack(Items.field_151078_bh));
                    pe.addCurativeItem(new ItemStack(ItemsTC.brain));
                    try {
                        player.func_70690_d(pe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.2")), true);
                }
                else if (eff <= 88) {
                    spawnPortal(player);
                }
                else if (eff <= 92) {
                    suddenlySpiders(player, warp, true);
                }
                else {
                    spawnMist(player, warp, warp / 15);
                }
            }
            if (actualwarp > 10 && !ThaumcraftCapabilities.knowsResearch(player, "BATHSALTS") && !ThaumcraftCapabilities.knowsResearch(player, "!BATHSALTS")) {
                player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.8")), true);
                ThaumcraftApi.internalMethods.completeResearch(player, "!BATHSALTS");
            }
            if (actualwarp > 25 && !ThaumcraftCapabilities.knowsResearch(player, "ELDRITCHMINOR")) {
                ThaumcraftApi.internalMethods.completeResearch(player, "ELDRITCHMINOR");
            }
            if (actualwarp > 50 && !ThaumcraftCapabilities.knowsResearch(player, "ELDRITCHMAJOR")) {
                ThaumcraftApi.internalMethods.completeResearch(player, "ELDRITCHMAJOR");
            }
        }
    }
    
    private static void spawnMist(final EntityPlayer player, final int warp, int guardian) {
        PacketHandler.INSTANCE.sendTo((IMessage)new PacketMiscEvent((byte)1), (EntityPlayerMP)player);
        if (guardian > 0) {
            guardian = Math.min(8, guardian);
            for (int a = 0; a < guardian; ++a) {
                spawnGuardian(player);
            }
        }
        player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.6")), true);
    }
    
    private static void spawnPortal(final EntityPlayer player) {
        final EntityCultistPortalLesser eg = new EntityCultistPortalLesser(player.field_70170_p);
        final int i = MathHelper.func_76128_c(player.field_70165_t);
        final int j = MathHelper.func_76128_c(player.field_70163_u);
        final int k = MathHelper.func_76128_c(player.field_70161_v);
        for (int l = 0; l < 50; ++l) {
            final int i2 = i + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            final int j2 = j + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            final int k2 = k + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            eg.func_70107_b(i2 + 0.5, j2 + 1.0, k2 + 0.5);
            if (player.field_70170_p.func_180495_p(new BlockPos(i2, j2 - 1, k2)).func_185914_p() && player.field_70170_p.func_72855_b(eg.func_174813_aQ()) && player.field_70170_p.func_184144_a((Entity)eg, eg.func_174813_aQ()).isEmpty() && !player.field_70170_p.func_72953_d(eg.func_174813_aQ())) {
                eg.func_180482_a(player.field_70170_p.func_175649_E(new BlockPos((Entity)eg)), (IEntityLivingData)null);
                player.field_70170_p.func_72838_d((Entity)eg);
                player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.16")), true);
                break;
            }
        }
    }
    
    private static void spawnGuardian(final EntityPlayer player) {
        final EntityEldritchGuardian eg = new EntityEldritchGuardian(player.field_70170_p);
        final int i = MathHelper.func_76128_c(player.field_70165_t);
        final int j = MathHelper.func_76128_c(player.field_70163_u);
        final int k = MathHelper.func_76128_c(player.field_70161_v);
        for (int l = 0; l < 50; ++l) {
            final int i2 = i + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            final int j2 = j + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            final int k2 = k + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
            if (player.field_70170_p.func_180495_p(new BlockPos(i2, j2 - 1, k2)).func_185917_h()) {
                eg.func_70107_b((double)i2, (double)j2, (double)k2);
                if (player.field_70170_p.func_72855_b(eg.func_174813_aQ()) && player.field_70170_p.func_184144_a((Entity)eg, eg.func_174813_aQ()).isEmpty() && !player.field_70170_p.func_72953_d(eg.func_174813_aQ())) {
                    eg.func_70624_b((EntityLivingBase)player);
                    player.field_70170_p.func_72838_d((Entity)eg);
                    break;
                }
            }
        }
    }
    
    private static void suddenlySpiders(final EntityPlayer player, final int warp, final boolean real) {
        for (int spawns = Math.min(50, warp), a = 0; a < spawns; ++a) {
            final EntityMindSpider spider = new EntityMindSpider(player.field_70170_p);
            final int i = MathHelper.func_76128_c(player.field_70165_t);
            final int j = MathHelper.func_76128_c(player.field_70163_u);
            final int k = MathHelper.func_76128_c(player.field_70161_v);
            boolean success = false;
            for (int l = 0; l < 50; ++l) {
                final int i2 = i + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
                final int j2 = j + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
                final int k2 = k + MathHelper.func_76136_a(player.field_70170_p.field_73012_v, 7, 24) * MathHelper.func_76136_a(player.field_70170_p.field_73012_v, -1, 1);
                if (player.field_70170_p.func_180495_p(new BlockPos(i2, j2 - 1, k2)).func_185917_h()) {
                    spider.func_70107_b((double)i2, (double)j2, (double)k2);
                    if (player.field_70170_p.func_72855_b(spider.func_174813_aQ()) && player.field_70170_p.func_184144_a((Entity)spider, spider.func_174813_aQ()).isEmpty() && !player.field_70170_p.func_72953_d(spider.func_174813_aQ())) {
                        success = true;
                        break;
                    }
                }
            }
            if (success) {
                spider.func_70624_b((EntityLivingBase)player);
                if (!real) {
                    spider.setViewer(player.func_70005_c_());
                    spider.setHarmless(true);
                }
                player.field_70170_p.func_72838_d((Entity)spider);
            }
        }
        player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("warp.text.7")), true);
    }
    
    public static void checkDeathGaze(final EntityPlayer player) {
        final PotionEffect pe = player.func_70660_b(PotionDeathGaze.instance);
        if (pe == null) {
            return;
        }
        final int level = pe.func_76458_c();
        final int range = Math.min(8 + level * 3, 24);
        final List list = player.field_70170_p.func_72839_b((Entity)player, player.func_174813_aQ().func_72314_b((double)range, (double)range, (double)range));
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.func_70067_L() && entity instanceof EntityLivingBase) {
                if (((EntityLivingBase)entity).func_70089_S()) {
                    if (EntityUtils.isVisibleTo(0.75f, (Entity)player, entity, range)) {
                        if (entity != null && player.func_70685_l(entity) && (!(entity instanceof EntityPlayer) || FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W()) && !((EntityLivingBase)entity).func_70644_a(MobEffects.field_82731_v)) {
                            ((EntityLivingBase)entity).func_70604_c((EntityLivingBase)player);
                            ((EntityLivingBase)entity).func_130011_c((Entity)player);
                            if (entity instanceof EntityCreature) {
                                ((EntityCreature)entity).func_70624_b((EntityLivingBase)player);
                            }
                            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 80));
                        }
                    }
                }
            }
        }
    }
    
    private static int getWarpFromGear(final EntityPlayer player) {
        int w = PlayerEvents.getFinalWarp(player.func_184614_ca(), player);
        for (int a = 0; a < 4; ++a) {
            w += PlayerEvents.getFinalWarp((ItemStack)player.field_71071_by.field_70460_b.get(a), player);
        }
        final IInventory baubles = BaublesApi.getBaubles(player);
        for (int a2 = 0; a2 < baubles.func_70302_i_(); ++a2) {
            w += PlayerEvents.getFinalWarp(baubles.func_70301_a(a2), player);
        }
        return w;
    }
}
