package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import java.util.*;
import thaumcraft.common.lib.research.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import thaumcraft.common.items.resources.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import thaumcraft.common.items.curios.*;
import thaumcraft.common.config.*;
import thaumcraft.common.lib.enchantment.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.fml.relauncher.*;
import baubles.api.*;
import thaumcraft.common.world.aura.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aura.*;
import net.minecraft.inventory.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.item.*;
import thaumcraft.common.lib.potions.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.common.capabilities.*;
import thaumcraft.common.lib.capabilities.*;
import net.minecraftforge.event.entity.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.nbt.*;
import thaumcraft.*;
import net.minecraftforge.event.entity.player.*;
import thaumcraft.api.research.*;
import thaumcraft.api.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.stats.*;
import net.minecraft.advancements.*;
import net.minecraft.init.*;

@Mod.EventBusSubscriber
public class PlayerEvents
{
    static HashMap<Integer, Long> nextCycle;
    static HashMap<Integer, Integer> lastCharge;
    static HashMap<Integer, Integer> lastMaxCharge;
    static HashMap<Integer, Integer> runicInfo;
    static HashMap<String, Long> upgradeCooldown;
    public static HashMap<Integer, Float> prevStep;
    
    @SubscribeEvent
    public static void livingTick(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntity();
            handleMisc(player);
            handleSpeedMods(player);
            if (!player.field_70170_p.field_72995_K) {
                handleRunicArmor(player);
                handleWarp(player);
                if (player.field_70173_aa % 20 == 0 && ResearchManager.syncList.remove(player.func_70005_c_()) != null) {
                    final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
                    knowledge.sync((EntityPlayerMP)player);
                }
                if (player.field_70173_aa % 200 == 0) {
                    ConfigResearch.checkPeriodicStuff(player);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void pickupItem(final EntityItemPickupEvent event) {
        if (event.getEntityPlayer() != null && !event.getEntityPlayer().field_70170_p.field_72995_K && event.getItem() != null && event.getItem().func_92059_d() != null) {
            final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(event.getEntityPlayer());
            if (event.getItem().func_92059_d().func_77973_b() instanceof ItemCrystalEssence && !knowledge.isResearchKnown("!gotcrystals")) {
                knowledge.addResearch("!gotcrystals");
                knowledge.sync((EntityPlayerMP)event.getEntityPlayer());
                event.getEntityPlayer().func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.crystals")), true);
            }
            if (event.getItem().func_92059_d().func_77973_b() instanceof ItemThaumonomicon && !knowledge.isResearchKnown("!gotthaumonomicon")) {
                knowledge.addResearch("!gotthaumonomicon");
                knowledge.sync((EntityPlayerMP)event.getEntityPlayer());
            }
        }
    }
    
    @SubscribeEvent
    public static void wakeUp(final PlayerWakeUpEvent event) {
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(event.getEntityPlayer());
        if (event.getEntityPlayer() != null && !event.getEntityPlayer().field_70170_p.field_72995_K && knowledge.isResearchKnown("!gotcrystals") && !knowledge.isResearchKnown("!gotdream")) {
            knowledge.addResearch("!gotdream");
            knowledge.sync((EntityPlayerMP)event.getEntityPlayer());
            final ItemStack book = ConfigItems.startBook.func_77946_l();
            book.func_77978_p().func_74778_a("author", event.getEntityPlayer().func_70005_c_());
            if (!event.getEntityPlayer().field_71071_by.func_70441_a(book)) {
                event.getEntityPlayer().func_70099_a(book, 2.0f);
            }
            try {
                event.getEntityPlayer().func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.dream")), true);
            }
            catch (Exception ex) {}
        }
    }
    
    private static void handleMisc(final EntityPlayer player) {
        if (player.field_70170_p.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId && player.field_70173_aa % 20 == 0 && !player.func_175149_v() && !player.field_71075_bZ.field_75098_d && player.field_71075_bZ.field_75100_b) {
            player.field_71075_bZ.field_75100_b = false;
            player.func_146105_b((ITextComponent)new TextComponentString(TextFormatting.ITALIC + "" + TextFormatting.GRAY + I18n.func_74838_a("tc.break.fly")), true);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void tooltipEvent(final ItemTooltipEvent event) {
        try {
            final int charge = getRunicCharge(event.getItemStack());
            if (charge > 0) {
                event.getToolTip().add(TextFormatting.GOLD + I18n.func_74838_a("item.runic.charge") + " +" + charge);
            }
            final int warp = getFinalWarp(event.getItemStack(), event.getEntityPlayer());
            if (warp > 0) {
                event.getToolTip().add(TextFormatting.DARK_PURPLE + I18n.func_74838_a("item.warping") + " " + warp);
            }
            final int al = getFinalDiscount(event.getItemStack(), event.getEntityPlayer());
            if (al > 0) {
                event.getToolTip().add(TextFormatting.DARK_PURPLE + I18n.func_74838_a("tc.visdiscount") + ": " + al + "%");
            }
            if (event.getItemStack() != null) {
                if (event.getItemStack().func_77973_b() instanceof IRechargable) {
                    final int c = Math.round(RechargeHelper.getCharge(event.getItemStack()));
                    if (c >= 0) {
                        event.getToolTip().add(TextFormatting.YELLOW + I18n.func_74838_a("tc.charge") + " " + c);
                    }
                }
                if (event.getItemStack().func_77973_b() instanceof IEssentiaContainerItem) {
                    final AspectList aspects = ((IEssentiaContainerItem)event.getItemStack().func_77973_b()).getAspects(event.getItemStack());
                    if (aspects != null && aspects.size() > 0) {
                        for (final Aspect tag : aspects.getAspectsSortedByName()) {
                            event.getToolTip().add(tag.getName() + " x" + aspects.getAmount(tag));
                        }
                    }
                }
                final NBTTagList nbttaglist = EnumInfusionEnchantment.getInfusionEnchantmentTagList(event.getItemStack());
                if (nbttaglist != null) {
                    for (int j = 0; j < nbttaglist.func_74745_c(); ++j) {
                        final int k = nbttaglist.func_150305_b(j).func_74765_d("id");
                        final int l = nbttaglist.func_150305_b(j).func_74765_d("lvl");
                        if (k >= 0 && k < EnumInfusionEnchantment.values().length) {
                            String s = TextFormatting.GOLD + I18n.func_74838_a("enchantment.infusion." + EnumInfusionEnchantment.values()[k].toString());
                            if (EnumInfusionEnchantment.values()[k].maxLevel > 1) {
                                s = s + " " + I18n.func_74838_a("enchantment.level." + l);
                            }
                            event.getToolTip().add(1, s);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private static void handleRunicArmor(final EntityPlayer player) {
        if (player.field_70173_aa % 20 == 0) {
            int max = 0;
            for (int a = 0; a < 4; ++a) {
                max += getRunicCharge((ItemStack)player.field_71071_by.field_70460_b.get(a));
            }
            final IInventory baubles = BaublesApi.getBaubles(player);
            for (int a2 = 0; a2 < baubles.func_70302_i_(); ++a2) {
                max += getRunicCharge(baubles.func_70301_a(a2));
            }
            if (PlayerEvents.lastMaxCharge.containsKey(player.func_145782_y())) {
                final int charge = PlayerEvents.lastMaxCharge.get(player.func_145782_y());
                if (charge > max) {
                    player.func_110149_m(player.func_110139_bj() - (charge - max));
                }
                if (max <= 0) {
                    PlayerEvents.lastMaxCharge.remove(player.func_145782_y());
                }
            }
            if (max > 0) {
                PlayerEvents.runicInfo.put(player.func_145782_y(), max);
                PlayerEvents.lastMaxCharge.put(player.func_145782_y(), max);
            }
            else {
                PlayerEvents.runicInfo.remove(player.func_145782_y());
            }
        }
        if (PlayerEvents.runicInfo.containsKey(player.func_145782_y())) {
            if (!PlayerEvents.nextCycle.containsKey(player.func_145782_y())) {
                PlayerEvents.nextCycle.put(player.func_145782_y(), 0L);
            }
            final long time = System.currentTimeMillis();
            final int charge = (int)player.func_110139_bj();
            if (charge == 0 && PlayerEvents.lastCharge.containsKey(player.func_145782_y()) && PlayerEvents.lastCharge.get(player.func_145782_y()) > 0) {
                PlayerEvents.nextCycle.put(player.func_145782_y(), time + ModConfig.CONFIG_MISC.shieldWait);
                PlayerEvents.lastCharge.put(player.func_145782_y(), 0);
            }
            if (charge < PlayerEvents.runicInfo.get(player.func_145782_y()) && PlayerEvents.nextCycle.get(player.func_145782_y()) < time && !AuraHandler.shouldPreserveAura(player.field_70170_p, player, player.func_180425_c()) && AuraHelper.getVis(player.field_70170_p, new BlockPos((Entity)player)) >= ModConfig.CONFIG_MISC.shieldCost) {
                AuraHandler.drainVis(player.field_70170_p, new BlockPos((Entity)player), ModConfig.CONFIG_MISC.shieldCost, false);
                PlayerEvents.nextCycle.put(player.func_145782_y(), time + ModConfig.CONFIG_MISC.shieldRecharge);
                player.func_110149_m((float)(charge + 1));
                PlayerEvents.lastCharge.put(player.func_145782_y(), charge + 1);
            }
        }
    }
    
    public static int getRunicCharge(final ItemStack stack) {
        int base = 0;
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("TC.RUNIC")) {
            base += stack.func_77978_p().func_74771_c("TC.RUNIC");
        }
        return base;
    }
    
    public static int getFinalWarp(final ItemStack stack, final EntityPlayer player) {
        if (stack == null || stack.func_190926_b()) {
            return 0;
        }
        int warp = 0;
        if (stack.func_77973_b() instanceof IWarpingGear) {
            final IWarpingGear armor = (IWarpingGear)stack.func_77973_b();
            warp += armor.getWarp(stack, player);
        }
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("TC.WARP")) {
            warp += stack.func_77978_p().func_74771_c("TC.WARP");
        }
        return warp;
    }
    
    public static int getFinalDiscount(final ItemStack stack, final EntityPlayer player) {
        if (stack == null || stack.func_190926_b() || !(stack.func_77973_b() instanceof IVisDiscountGear)) {
            return 0;
        }
        final IVisDiscountGear gear = (IVisDiscountGear)stack.func_77973_b();
        return gear.getVisDiscount(stack, player);
    }
    
    private static void handleSpeedMods(final EntityPlayer player) {
        if (player.field_70170_p.field_72995_K && (player.func_70093_af() || ((ItemStack)player.field_71071_by.field_70460_b.get(0)).func_77973_b() != ItemsTC.travellerBoots) && PlayerEvents.prevStep.containsKey(player.func_145782_y())) {
            player.field_70138_W = PlayerEvents.prevStep.get(player.func_145782_y());
            PlayerEvents.prevStep.remove(player.func_145782_y());
        }
    }
    
    @SubscribeEvent
    public static void playerJumps(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof EntityPlayer && ((ItemStack)((EntityPlayer)event.getEntity()).field_71071_by.field_70460_b.get(0)).func_77973_b() == ItemsTC.travellerBoots) {
            final ItemStack is = (ItemStack)((EntityPlayer)event.getEntity()).field_71071_by.field_70460_b.get(0);
            if (RechargeHelper.getCharge(is) > 0) {
                final EntityLivingBase entityLiving = event.getEntityLiving();
                entityLiving.field_70181_x += 0.2750000059604645;
            }
        }
    }
    
    private static void handleWarp(final EntityPlayer player) {
        if (!ModConfig.CONFIG_MISC.wussMode && player.field_70173_aa > 0 && player.field_70173_aa % 2000 == 0 && !player.func_70644_a(PotionWarpWard.instance)) {
            WarpEvents.checkWarpEvent(player);
        }
        if (player.field_70173_aa % 20 == 0 && player.func_70644_a(PotionDeathGaze.instance)) {
            WarpEvents.checkDeathGaze(player);
        }
    }
    
    @SubscribeEvent
    public static void droppedItem(final ItemTossEvent event) {
        final NBTTagCompound itemData = event.getEntityItem().getEntityData();
        itemData.func_74778_a("thrower", event.getPlayer().func_70005_c_());
    }
    
    @SubscribeEvent
    public static void finishedUsingItem(final LivingEntityUseItemEvent.Finish event) {
        if (!event.getEntity().field_70170_p.field_72995_K && event.getEntityLiving().func_70644_a(PotionUnnaturalHunger.instance)) {
            if (event.getItem().func_77969_a(new ItemStack(Items.field_151078_bh)) || event.getItem().func_77969_a(new ItemStack(ItemsTC.brain))) {
                PotionEffect pe = event.getEntityLiving().func_70660_b(PotionUnnaturalHunger.instance);
                final int amp = pe.func_76458_c() - 1;
                final int duration = pe.func_76459_b() - 600;
                event.getEntityLiving().func_184589_d(PotionUnnaturalHunger.instance);
                if (duration > 0 && amp >= 0) {
                    pe = new PotionEffect(PotionUnnaturalHunger.instance, duration, amp, true, false);
                    pe.getCurativeItems().clear();
                    pe.addCurativeItem(new ItemStack(Items.field_151078_bh));
                    event.getEntityLiving().func_70690_d(pe);
                }
                event.getEntityLiving().func_145747_a((ITextComponent)new TextComponentString("§2§o" + I18n.func_74838_a("warp.text.hunger.2")));
            }
            else if (event.getItem().func_77973_b() instanceof ItemFood) {
                event.getEntityLiving().func_145747_a((ITextComponent)new TextComponentString("§4§o" + I18n.func_74838_a("warp.text.hunger.1")));
            }
        }
    }
    
    @SubscribeEvent
    public static void attachCapabilitiesPlayer(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(PlayerKnowledge.Provider.NAME, (ICapabilityProvider)new PlayerKnowledge.Provider());
            event.addCapability(PlayerWarp.Provider.NAME, (ICapabilityProvider)new PlayerWarp.Provider());
        }
    }
    
    @SubscribeEvent
    public static void playerJoin(final EntityJoinWorldEvent event) {
        if (!event.getWorld().field_72995_K && event.getEntity() instanceof EntityPlayerMP) {
            final EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
            final IPlayerKnowledge pk = ThaumcraftCapabilities.getKnowledge((EntityPlayer)player);
            final IPlayerWarp pw = ThaumcraftCapabilities.getWarp((EntityPlayer)player);
            if (pk != null) {
                pk.sync(player);
            }
            if (pw != null) {
                pw.sync(player);
            }
        }
    }
    
    @SubscribeEvent
    public static void cloneCapabilitiesEvent(final PlayerEvent.Clone event) {
        try {
            final NBTTagCompound nbtKnowledge = (NBTTagCompound)ThaumcraftCapabilities.getKnowledge(event.getOriginal()).serializeNBT();
            ThaumcraftCapabilities.getKnowledge(event.getEntityPlayer()).deserializeNBT((NBTBase)nbtKnowledge);
            final NBTTagCompound nbtWarp = (NBTTagCompound)ThaumcraftCapabilities.getWarp(event.getOriginal()).serializeNBT();
            ThaumcraftCapabilities.getWarp(event.getEntityPlayer()).deserializeNBT((NBTBase)nbtWarp);
        }
        catch (Exception e) {
            Thaumcraft.log.error("Could not clone player [" + event.getOriginal().func_70005_c_() + "] knowledge when changing dimensions");
        }
    }
    
    @SubscribeEvent
    public static void pickupXP(final PlayerPickupXpEvent event) {
        if (event.getEntityPlayer() != null && !event.getEntityPlayer().field_70170_p.field_72995_K && BaublesApi.isBaubleEquipped(event.getEntityPlayer(), ItemsTC.bandCuriosity) >= 0 && event.getOrb().func_70526_d() > 1) {
            final int d = event.getOrb().field_70530_e / 2;
            final EntityXPOrb orb = event.getOrb();
            orb.field_70530_e -= d;
            final float r = event.getEntityPlayer().func_70681_au().nextFloat();
            if (r < 0.05 * d) {
                final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
                final String cat = s[event.getEntityPlayer().func_70681_au().nextInt(s.length)];
                ThaumcraftApi.internalMethods.addKnowledge(event.getEntityPlayer(), IPlayerKnowledge.EnumKnowledgeType.THEORY, ResearchCategories.getResearchCategory(cat), 1);
            }
            else if (r < 0.2 * d) {
                final String[] s = ResearchCategories.researchCategories.keySet().toArray(new String[0]);
                final String cat = s[event.getEntityPlayer().func_70681_au().nextInt(s.length)];
                ThaumcraftApi.internalMethods.addKnowledge(event.getEntityPlayer(), IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory(cat), 1);
            }
        }
    }
    
    @SubscribeEvent
    public static void onDeath(final LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            final int slot = BaublesApi.isBaubleEquipped(player, ItemsTC.charmUndying);
            if (slot >= 0) {
                if (player instanceof EntityPlayerMP) {
                    final EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
                    entityplayermp.func_71029_a(StatList.func_188057_b(Items.field_190929_cY));
                    CriteriaTriggers.field_193130_A.func_193187_a(entityplayermp, BaublesApi.getBaubles(player).func_70301_a(slot));
                }
                BaublesApi.getBaublesHandler(player).extractItem(slot, 1, false);
                player.func_70606_j(1.0f);
                player.func_70674_bp();
                player.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 900, 1));
                player.func_70690_d(new PotionEffect(MobEffects.field_76444_x, 100, 1));
                player.field_70170_p.func_72960_a((Entity)player, (byte)35);
                event.setCanceled(true);
            }
        }
    }
    
    static {
        PlayerEvents.nextCycle = new HashMap<Integer, Long>();
        PlayerEvents.lastCharge = new HashMap<Integer, Integer>();
        PlayerEvents.lastMaxCharge = new HashMap<Integer, Integer>();
        PlayerEvents.runicInfo = new HashMap<Integer, Integer>();
        PlayerEvents.upgradeCooldown = new HashMap<String, Long>();
        PlayerEvents.prevStep = new HashMap<Integer, Float>();
    }
}
