package thaumcraft.common.lib.events;

import thaumcraft.common.lib.utils.*;
import net.minecraft.world.*;
import thaumcraft.common.blocks.world.taint.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.common.entities.monster.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import thaumcraft.common.lib.potions.*;
import net.minecraft.potion.*;
import thaumcraft.api.*;
import java.util.*;
import thaumcraft.api.capabilities.*;
import com.google.common.base.*;

public class TaintEvents
{
    static ArrayList<RandomItemChooser.Item> events;
    
    protected static boolean taintEvent(final World world, BlockPos pos) {
        final RandomItemChooser ric = new RandomItemChooser();
        final TaintItem ei = (TaintItem)ric.chooseOnWeight(TaintEvents.events);
        if (ei == null) {
            return false;
        }
        pos = pos.func_177982_a(world.field_73012_v.nextInt(16), 0, world.field_73012_v.nextInt(16));
        BlockPos p2 = world.func_175725_q(pos);
        if (!world.field_73011_w.func_191066_m()) {
            for (p2 = new BlockPos(p2.func_177958_n(), 10, p2.func_177952_p()); !world.func_175623_d(p2.func_177984_a()) && !world.func_175623_d(p2.func_177981_b(2)); p2 = p2.func_177984_a()) {
                if (p2.func_177956_o() > world.func_72940_L()) {
                    return false;
                }
            }
        }
        if (!ei.nearTaintAllowed && TaintHelper.isNearTaintSeed(world, p2)) {
            return false;
        }
        if (AuraHandler.getFlux(world, p2) < ei.cost) {
            return false;
        }
        boolean didit = false;
        switch (ei.event) {
            case 0: {
                if (p2.func_177956_o() + 5 < world.func_72940_L()) {
                    final EntityWisp wisp = new EntityWisp(world);
                    wisp.func_70012_b((double)p2.func_177958_n(), (double)(p2.func_177956_o() + 5), (double)p2.func_177952_p(), 0.0f, 0.0f);
                    if (world.field_73012_v.nextInt(3) == 0) {
                        wisp.setType(Aspect.FLUX.getTag());
                    }
                    if (world.func_72838_d((Entity)wisp)) {
                        didit = true;
                    }
                    break;
                }
                break;
            }
            case 1: {
                if (p2.func_177956_o() + 1 < world.func_72940_L()) {
                    final EntityTaintCrawler crawler = new EntityTaintCrawler(world);
                    crawler.func_70012_b((double)p2.func_177958_n(), (double)(p2.func_177956_o() + 1), (double)p2.func_177952_p(), 0.0f, 0.0f);
                    if (world.func_72838_d((Entity)crawler)) {
                        didit = true;
                    }
                    break;
                }
                break;
            }
            case 2: {
                if (p2.func_177956_o() + 1 < world.func_72940_L()) {
                    final EntityTaintSeedPrime seed = new EntityTaintSeedPrime(world);
                    seed.func_70012_b((double)p2.func_177958_n(), (double)(p2.func_177956_o() + 1), (double)p2.func_177952_p(), (float)world.field_73012_v.nextInt(360), 0.0f);
                    if (world.func_72838_d((Entity)seed)) {
                        didit = true;
                    }
                    break;
                }
                break;
            }
            case 3: {
                final List<EntityLivingBase> targets2 = (List<EntityLivingBase>)world.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB((double)p2.func_177958_n(), (double)p2.func_177956_o(), (double)p2.func_177952_p(), (double)(p2.func_177958_n() + 1), (double)(p2.func_177956_o() + 1), (double)(p2.func_177952_p() + 1)).func_72314_b(16.0, 16.0, 16.0));
                if (targets2 != null && targets2.size() > 0) {
                    for (final EntityLivingBase target : targets2) {
                        didit = true;
                        if (target instanceof EntityPlayer) {
                            ((EntityPlayer)target).func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("tc.fluxevent.2")), true);
                        }
                        final PotionEffect pe = new PotionEffect(PotionInfectiousVisExhaust.instance, 3000, 2);
                        pe.getCurativeItems().clear();
                        try {
                            target.func_70690_d(pe);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                break;
            }
        }
        if (didit) {
            AuraHandler.drainFlux(world, p2, ei.cost, false);
            final List<EntityPlayer> targets3 = (List<EntityPlayer>)world.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB((double)p2.func_177958_n(), (double)p2.func_177956_o(), (double)p2.func_177952_p(), (double)(p2.func_177958_n() + 1), (double)(p2.func_177956_o() + 1), (double)(p2.func_177952_p() + 1)).func_72314_b(32.0, 32.0, 32.0));
            if (targets3 != null && targets3.size() > 0) {
                for (final EntityPlayer target2 : targets3) {
                    final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(target2);
                    if (!knowledge.isResearchKnown("f_toomuchflux")) {
                        target2.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("tc.fluxevent.3")), true);
                        ThaumcraftApi.internalMethods.completeResearch(target2, "f_toomuchflux");
                    }
                }
            }
        }
        return true;
    }
    
    private static BlockPos findSpotToStrike(final World world, final BlockPos pos) {
        final BlockPos blockpos1 = world.func_175725_q(pos);
        final AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockpos1, new BlockPos(blockpos1.func_177958_n(), world.func_72800_K(), blockpos1.func_177952_p())).func_72314_b(4.0, 4.0, 4.0);
        final List list = world.func_175647_a((Class)EntityLivingBase.class, axisalignedbb, (Predicate)new Predicate() {
            public boolean applyLiving(final EntityLivingBase living) {
                return living != null && living.func_70089_S() && world.func_175678_i(living.func_180425_c());
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.applyLiving((EntityLivingBase)p_apply_1_);
            }
        });
        return list.isEmpty() ? blockpos1 : list.get(world.field_73012_v.nextInt(list.size())).func_180425_c();
    }
    
    static {
        (TaintEvents.events = new ArrayList<RandomItemChooser.Item>()).add(new TaintItem(0, 1, 5, true));
        TaintEvents.events.add(new TaintItem(1, 5, 10, true));
        TaintEvents.events.add(new TaintItem(2, 15, 25, false));
        TaintEvents.events.add(new TaintItem(3, 5, 15, false));
    }
    
    static class TaintItem implements RandomItemChooser.Item
    {
        int weight;
        int event;
        int cost;
        boolean nearTaintAllowed;
        
        protected TaintItem(final int event, final int weight, final int cost, final boolean nearTaintAllowed) {
            this.weight = weight;
            this.event = event;
            this.cost = cost;
            this.nearTaintAllowed = nearTaintAllowed;
        }
        
        @Override
        public double getWeight() {
            return this.weight;
        }
    }
}
