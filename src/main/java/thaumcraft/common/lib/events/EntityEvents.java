package thaumcraft.common.lib.events;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.event.entity.item.*;
import thaumcraft.common.items.consumables.*;
import thaumcraft.api.blocks.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;
import thaumcraft.api.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import thaumcraft.common.items.armor.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.entities.construct.*;
import thaumcraft.api.potions.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.entities.*;
import thaumcraft.common.lib.*;
import thaumcraft.api.capabilities.*;
import net.minecraftforge.event.entity.player.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.util.*;
import thaumcraft.common.entities.monster.boss.*;
import net.minecraft.util.math.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.cult.*;
import thaumcraft.api.damagesource.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.common.*;
import thaumcraft.common.config.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import java.util.*;
import net.minecraft.world.*;

@Mod.EventBusSubscriber
public class EntityEvents
{
    @SubscribeEvent
    public static void itemExpire(final ItemExpireEvent event) {
        if (event.getEntityItem().func_92059_d() != null && event.getEntityItem().func_92059_d().func_77973_b() != null && event.getEntityItem().func_92059_d().func_77973_b() instanceof ItemBathSalts) {
            final BlockPos bp = new BlockPos((Entity)event.getEntityItem());
            final IBlockState bs = event.getEntityItem().field_70170_p.func_180495_p(bp);
            if (bs.func_177230_c() == Blocks.field_150355_j && bs.func_177230_c().func_176201_c(bs) == 0) {
                event.getEntityItem().field_70170_p.func_175656_a(bp, BlocksTC.purifyingFluid.func_176223_P());
            }
        }
    }
    
    @SubscribeEvent
    public static void livingTick(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityCreature && !event.getEntity().field_70128_L) {
            final EntityCreature mob = (EntityCreature)event.getEntity();
            if (mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD) != null) {
                final int t = (int)mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
                try {
                    if (t >= 0 && ChampionModifier.mods[t].type == 0) {
                        ChampionModifier.mods[t].effect.performEffect((EntityLivingBase)mob, null, null, 0.0f);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    if (t >= ChampionModifier.mods.length) {
                        mob.func_70106_y();
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void entityHurt(final LivingHurtEvent event) {
        if (event.getSource().func_76347_k() && event.getEntity() instanceof EntityPlayer && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)event.getEntity(), "BASEAUROMANCY@2") && !ThaumcraftCapabilities.knowsResearch((EntityPlayer)event.getEntity(), "f_onfire")) {
            final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge((EntityPlayer)event.getEntity());
            knowledge.addResearch("f_onfire");
            knowledge.sync((EntityPlayerMP)event.getEntity());
            ((EntityPlayer)event.getEntity()).func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.onfire")), true);
        }
        if (event.getSource().func_76364_f() != null && event.getEntity() instanceof EntityPlayer && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)event.getEntity(), "FOCUSPROJECTILE@2")) {
            final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge((EntityPlayer)event.getEntity());
            if (!ThaumcraftCapabilities.knowsResearch((EntityPlayer)event.getEntity(), "f_arrow") && event.getSource().func_76364_f() instanceof EntityArrow) {
                knowledge.addResearch("f_arrow");
                knowledge.sync((EntityPlayerMP)event.getEntity());
                ((EntityPlayer)event.getEntity()).func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.projectile")), true);
            }
            if (!ThaumcraftCapabilities.knowsResearch((EntityPlayer)event.getEntity(), "f_fireball") && event.getSource().func_76364_f() instanceof EntityFireball) {
                knowledge.addResearch("f_fireball");
                knowledge.sync((EntityPlayerMP)event.getEntity());
                ((EntityPlayer)event.getEntity()).func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.projectile")), true);
            }
            if (!ThaumcraftCapabilities.knowsResearch((EntityPlayer)event.getEntity(), "f_spit") && event.getSource().func_76364_f() instanceof EntityLlamaSpit) {
                knowledge.addResearch("f_spit");
                knowledge.sync((EntityPlayerMP)event.getEntity());
                ((EntityPlayer)event.getEntity()).func_146105_b((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.projectile")), true);
            }
        }
        if (event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityPlayer) {
            final EntityPlayer leecher = (EntityPlayer)event.getSource().func_76346_g();
            final ItemStack helm = (ItemStack)leecher.field_71071_by.field_70460_b.get(3);
            if (helm != null && helm.func_77973_b() instanceof ItemFortressArmor && helm.func_77942_o() && helm.func_77978_p().func_74764_b("mask") && helm.func_77978_p().func_74762_e("mask") == 2 && leecher.field_70170_p.field_73012_v.nextFloat() < event.getAmount() / 12.0f) {
                leecher.func_70691_i(1.0f);
            }
        }
        if (event.getEntity() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntity();
            if (event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityLivingBase) {
                final EntityLivingBase attacker = (EntityLivingBase)event.getSource().func_76346_g();
                final ItemStack helm2 = (ItemStack)player.field_71071_by.field_70460_b.get(3);
                if (helm2 != null && helm2.func_77973_b() instanceof ItemFortressArmor && helm2.func_77942_o() && helm2.func_77978_p().func_74764_b("mask") && helm2.func_77978_p().func_74762_e("mask") == 1 && player.field_70170_p.field_73012_v.nextFloat() < event.getAmount() / 10.0f) {
                    try {
                        attacker.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 80));
                    }
                    catch (Exception ex) {}
                }
            }
            final int charge = (int)player.func_110139_bj();
            if (charge > 0 && PlayerEvents.runicInfo.containsKey(player.func_145782_y()) && PlayerEvents.lastMaxCharge.containsKey(player.func_145782_y())) {
                final long time = System.currentTimeMillis();
                int target = -1;
                if (event.getSource().func_76346_g() != null) {
                    target = event.getSource().func_76346_g().func_145782_y();
                }
                if (event.getSource() == DamageSource.field_76379_h) {
                    target = -2;
                }
                if (event.getSource() == DamageSource.field_82729_p) {
                    target = -3;
                }
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXShield(event.getEntity().func_145782_y(), target), new NetworkRegistry.TargetPoint(event.getEntity().field_70170_p.field_73011_w.getDimension(), event.getEntity().field_70165_t, event.getEntity().field_70163_u, event.getEntity().field_70161_v, 32.0));
            }
        }
        else {
            if (!event.getEntityLiving().field_70170_p.field_72995_K && event.getEntityLiving().func_110143_aJ() < 2.0f && !event.getEntityLiving().func_70662_br() && !event.getEntityLiving().field_70128_L && !(event.getEntityLiving() instanceof EntityOwnedConstruct) && !(event.getEntityLiving() instanceof ITaintedMob) && event.getEntityLiving().func_70644_a(PotionFluxTaint.instance) && event.getEntityLiving().func_70681_au().nextBoolean()) {
                EntityUtils.makeTainted(event.getEntityLiving());
                return;
            }
            if (event.getEntity() instanceof EntityMob) {
                final IAttributeInstance cai = ((EntityMob)event.getEntity()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD);
                if ((cai != null && cai.func_111126_e() >= 0.0) || event.getEntity() instanceof IEldritchMob) {
                    final EntityMob mob = (EntityMob)event.getEntity();
                    final int t = (int)cai.func_111126_e();
                    if ((t == 5 || event.getEntity() instanceof IEldritchMob) && mob.func_110139_bj() > 0.0f) {
                        int target2 = -1;
                        if (event.getSource().func_76346_g() != null) {
                            target2 = event.getSource().func_76346_g().func_145782_y();
                        }
                        if (event.getSource() == DamageSource.field_76379_h) {
                            target2 = -2;
                        }
                        if (event.getSource() == DamageSource.field_82729_p) {
                            target2 = -3;
                        }
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXShield(mob.func_145782_y(), target2), new NetworkRegistry.TargetPoint(event.getEntity().field_70170_p.field_73011_w.getDimension(), event.getEntity().field_70165_t, event.getEntity().field_70163_u, event.getEntity().field_70161_v, 32.0));
                        event.getEntity().func_184185_a(SoundsTC.runicShieldCharge, 0.66f, 1.1f + event.getEntity().field_70170_p.field_73012_v.nextFloat() * 0.1f);
                    }
                    else if (t >= 0 && ChampionModifier.mods[t].type == 2 && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityLivingBase) {
                        final EntityLivingBase attacker2 = (EntityLivingBase)event.getSource().func_76346_g();
                        event.setAmount(ChampionModifier.mods[t].effect.performEffect((EntityLivingBase)mob, attacker2, event.getSource(), event.getAmount()));
                    }
                }
                if (event.getAmount() > 0.0f && event.getSource().func_76346_g() != null && event.getEntity() instanceof EntityLivingBase && event.getSource().func_76346_g() instanceof EntityMob && ((EntityMob)event.getSource().func_76346_g()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() >= 0.0) {
                    final EntityMob mob = (EntityMob)event.getSource().func_76346_g();
                    final int t = (int)mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e();
                    if (ChampionModifier.mods[t].type == 1) {
                        event.setAmount(ChampionModifier.mods[t].effect.performEffect((EntityLivingBase)mob, (EntityLivingBase)event.getEntity(), event.getSource(), event.getAmount()));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void itemPickup(final EntityItemPickupEvent event) {
        if (event.getEntityPlayer().func_70005_c_().startsWith("FakeThaumcraft")) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void entityConstuct(final EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityCreature && !(event.getEntity() instanceof EntityOwnedConstruct)) {
            final EntityCreature mob = (EntityCreature)event.getEntity();
            mob.func_110140_aT().func_111150_b(ThaumcraftApiHelper.CHAMPION_MOD).func_111128_a(-2.0);
            mob.func_110140_aT().func_111150_b(ChampionModTainted.TAINTED_MOD).func_111128_a(0.0);
        }
    }
    
    @SubscribeEvent
    public static void livingDrops(final LivingDropsEvent event) {
        final boolean fakeplayer = event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof FakePlayer;
        if (!event.getEntity().field_70170_p.field_72995_K && event.isRecentlyHit() && !fakeplayer && event.getEntity() instanceof EntityMob && !(event.getEntity() instanceof EntityThaumcraftBoss) && ((EntityMob)event.getEntity()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() >= 0.0 && ((EntityMob)event.getEntity()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() != 13.0) {
            int i = 5 + event.getEntity().field_70170_p.field_73012_v.nextInt(3);
            while (i > 0) {
                final int j = EntityXPOrb.func_70527_a(i);
                i -= j;
                event.getEntity().field_70170_p.func_72838_d((Entity)new EntityXPOrb(event.getEntity().field_70170_p, event.getEntity().field_70165_t, event.getEntity().field_70163_u, event.getEntity().field_70161_v, j));
            }
            final int lb = Math.min(2, MathHelper.func_76141_d((event.getEntity().field_70170_p.field_73012_v.nextInt(9) + event.getLootingLevel()) / 5.0f));
            event.getDrops().add(new EntityItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, new ItemStack(ItemsTC.lootBag, 1, lb)));
        }
        if (event.getEntityLiving() instanceof EntityZombie && !(event.getEntityLiving() instanceof EntityBrainyZombie) && event.isRecentlyHit() && event.getEntity().field_70170_p.field_73012_v.nextInt(10) - event.getLootingLevel() < 1) {
            event.getDrops().add(new EntityItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, new ItemStack(ItemsTC.brain)));
        }
        if (event.getEntityLiving() instanceof EntityCultist && !fakeplayer && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer)event.getSource().func_76346_g();
            int c = ThaumcraftCapabilities.getKnowledge(p).isResearchKnown("!CrimsonCultist@2") ? 20 : 4;
            if (p.field_71071_by.func_184429_b(new ItemStack(ItemsTC.curio, 1, 6)) >= 0) {
                c = 50;
            }
            if (event.getEntity().field_70170_p.field_73012_v.nextInt(c) == 0) {
                event.getDrops().add(new EntityItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, new ItemStack(ItemsTC.curio, 1, 6)));
            }
        }
        if (event.getSource() == DamageSourceThaumcraft.dissolve) {
            final AspectList aspects = AspectHelper.getEntityAspects((Entity)event.getEntityLiving());
            if (aspects != null && aspects.size() > 0) {
                final Aspect[] al = aspects.getAspects();
                for (int q = MathHelper.func_76136_a(event.getEntity().func_130014_f_().field_73012_v, 1, 1 + aspects.visSize() / 10), a = 0; a < q; ++a) {
                    final Aspect aspect = al[event.getEntity().func_130014_f_().field_73012_v.nextInt(al.length)];
                    final ItemStack stack = ThaumcraftApiHelper.makeCrystal(aspect);
                    event.getDrops().add(new EntityItem(event.getEntity().field_70170_p, event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u + event.getEntityLiving().func_70047_e(), event.getEntityLiving().field_70161_v, stack));
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void entitySpawns(final EntityJoinWorldEvent event) {
        if (!event.getWorld().field_72995_K) {
            if (event.getEntity() instanceof EntityCreature && ((EntityCreature)event.getEntity()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD) != null && ((EntityCreature)event.getEntity()).func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() == 13.0) {
                final IAttributeInstance modai = ((EntityCreature)event.getEntity()).func_110148_a(ChampionModTainted.TAINTED_MOD);
                modai.func_111124_b(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 1.0, 0));
                modai.func_111121_a(new AttributeModifier(UUID.fromString("2cb22137-a9d8-4417-ae06-de0e70f11b4c"), "istainted", 0.0, 0));
            }
            if (event.getEntity() instanceof EntityMob) {
                final EntityMob mob = (EntityMob)event.getEntity();
                if (mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() < -1.0) {
                    int c = event.getWorld().field_73012_v.nextInt(100);
                    if (event.getWorld().func_175659_aa() == EnumDifficulty.EASY || !ModConfig.CONFIG_WORLD.allowChampionMobs) {
                        c += 2;
                    }
                    if (event.getWorld().func_175659_aa() == EnumDifficulty.HARD) {
                        c -= (ModConfig.CONFIG_WORLD.allowChampionMobs ? 2 : 0);
                    }
                    if (event.getWorld().field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId) {
                        c -= 3;
                    }
                    final Biome bg = mob.field_70170_p.func_180494_b(new BlockPos((Entity)mob));
                    if (BiomeDictionary.hasType(bg, BiomeDictionary.Type.SPOOKY) || BiomeDictionary.hasType(bg, BiomeDictionary.Type.NETHER) || BiomeDictionary.hasType(bg, BiomeDictionary.Type.END)) {
                        c -= (ModConfig.CONFIG_WORLD.allowChampionMobs ? 2 : 1);
                    }
                    if (isDangerousLocation(mob.field_70170_p, MathHelper.func_76143_f(mob.field_70165_t), MathHelper.func_76143_f(mob.field_70163_u), MathHelper.func_76143_f(mob.field_70161_v))) {
                        c -= (ModConfig.CONFIG_WORLD.allowChampionMobs ? 10 : 3);
                    }
                    int cc = 0;
                    boolean whitelisted = false;
                    for (final Class clazz : ConfigEntities.championModWhitelist.keySet()) {
                        if (clazz.isAssignableFrom(event.getEntity().getClass())) {
                            whitelisted = true;
                            if (!ModConfig.CONFIG_WORLD.allowChampionMobs && !(event.getEntity() instanceof EntityThaumcraftBoss)) {
                                continue;
                            }
                            cc = Math.max(cc, ConfigEntities.championModWhitelist.get(clazz) - 1);
                        }
                    }
                    c -= cc;
                    if (whitelisted && c <= 0 && mob.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() >= 10.0) {
                        EntityUtils.makeChampion(mob, false);
                    }
                    else {
                        final IAttributeInstance modai2 = mob.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD);
                        modai2.func_111124_b(ChampionModifier.ATTRIBUTE_MOD_NONE);
                        modai2.func_111121_a(ChampionModifier.ATTRIBUTE_MOD_NONE);
                    }
                }
            }
        }
    }
    
    private static boolean isDangerousLocation(final World world, final int x, final int y, final int z) {
        return false;
    }
}
