package thaumcraft.common.items.casters;

import net.minecraft.entity.player.*;
import baubles.api.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import thaumcraft.api.potions.*;
import thaumcraft.common.lib.potions.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import java.util.*;
import thaumcraft.api.casters.*;
import net.minecraft.entity.*;

public class CasterManager
{
    static HashMap<Integer, Long> cooldownServer;
    static HashMap<Integer, Long> cooldownClient;
    
    public static float getTotalVisDiscount(final EntityPlayer player) {
        int total = 0;
        if (player == null) {
            return 0.0f;
        }
        final IInventory baubles = BaublesApi.getBaubles(player);
        for (int a = 0; a < baubles.func_70302_i_(); ++a) {
            if (baubles.func_70301_a(a) != null && baubles.func_70301_a(a).func_77973_b() instanceof IVisDiscountGear) {
                total += ((IVisDiscountGear)baubles.func_70301_a(a).func_77973_b()).getVisDiscount(baubles.func_70301_a(a), player);
            }
        }
        for (int a = 0; a < 4; ++a) {
            if (((ItemStack)player.field_71071_by.field_70460_b.get(a)).func_77973_b() instanceof IVisDiscountGear) {
                total += ((IVisDiscountGear)((ItemStack)player.field_71071_by.field_70460_b.get(a)).func_77973_b()).getVisDiscount((ItemStack)player.field_71071_by.field_70460_b.get(a), player);
            }
        }
        if (player.func_70644_a(PotionVisExhaust.instance) || player.func_70644_a(PotionInfectiousVisExhaust.instance)) {
            int level1 = 0;
            int level2 = 0;
            if (player.func_70644_a(PotionVisExhaust.instance)) {
                level1 = player.func_70660_b(PotionVisExhaust.instance).func_76458_c();
            }
            if (player.func_70644_a(PotionInfectiousVisExhaust.instance)) {
                level2 = player.func_70660_b(PotionInfectiousVisExhaust.instance).func_76458_c();
            }
            total -= (Math.max(level1, level2) + 1) * 10;
        }
        return total / 100.0f;
    }
    
    public static boolean consumeVisFromInventory(final EntityPlayer player, final float cost) {
        for (int a = player.field_71071_by.field_70462_a.size() - 1; a >= 0; --a) {
            final ItemStack item = (ItemStack)player.field_71071_by.field_70462_a.get(a);
            if (item.func_77973_b() instanceof ICaster) {
                final boolean done = ((ICaster)item.func_77973_b()).consumeVis(item, player, cost, true, false);
                if (done) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void changeFocus(final ItemStack is, final World w, final EntityPlayer player, final String focus) {
        final ICaster wand = (ICaster)is.func_77973_b();
        final TreeMap<String, Integer> foci = new TreeMap<String, Integer>();
        final HashMap<Integer, Integer> pouches = new HashMap<Integer, Integer>();
        int pouchcount = 0;
        ItemStack item = ItemStack.field_190927_a;
        final IInventory baubles = BaublesApi.getBaubles(player);
        for (int a = 0; a < baubles.func_70302_i_(); ++a) {
            if (baubles.func_70301_a(a).func_77973_b() instanceof ItemFocusPouch) {
                ++pouchcount;
                item = baubles.func_70301_a(a);
                pouches.put(pouchcount, a - 4);
                final NonNullList<ItemStack> inv = ((ItemFocusPouch)item.func_77973_b()).getInventory(item);
                for (int q = 0; q < inv.size(); ++q) {
                    item = (ItemStack)inv.get(q);
                    if (item.func_77973_b() instanceof ItemFocus) {
                        final String sh = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                        if (sh != null) {
                            foci.put(sh, q + pouchcount * 1000);
                        }
                    }
                }
            }
        }
        for (int a = 0; a < 36; ++a) {
            item = (ItemStack)player.field_71071_by.field_70462_a.get(a);
            if (item.func_77973_b() instanceof ItemFocus) {
                final String sh2 = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                if (sh2 == null) {
                    continue;
                }
                foci.put(sh2, a);
            }
            if (item.func_77973_b() instanceof ItemFocusPouch) {
                ++pouchcount;
                pouches.put(pouchcount, a);
                final NonNullList<ItemStack> inv = ((ItemFocusPouch)item.func_77973_b()).getInventory(item);
                for (int q = 0; q < inv.size(); ++q) {
                    item = (ItemStack)inv.get(q);
                    if (item.func_77973_b() instanceof ItemFocus) {
                        final String sh = ((ItemFocus)item.func_77973_b()).getSortingHelper(item);
                        if (sh != null) {
                            foci.put(sh, q + pouchcount * 1000);
                        }
                    }
                }
            }
        }
        if (focus.equals("REMOVE") || foci.size() == 0) {
            if (wand.getFocus(is) != null && (addFocusToPouch(player, wand.getFocusStack(is).func_77946_l(), pouches) || player.field_71071_by.func_70441_a(wand.getFocusStack(is).func_77946_l()))) {
                wand.setFocus(is, null);
                player.func_184185_a(SoundsTC.ticks, 0.3f, 0.9f);
            }
            return;
        }
        if (foci != null && foci.size() > 0 && focus != null) {
            String newkey = focus;
            if (foci.get(newkey) == null) {
                newkey = foci.higherKey(newkey);
            }
            if (newkey == null || foci.get(newkey) == null) {
                newkey = foci.firstKey();
            }
            if (foci.get(newkey) < 1000 && foci.get(newkey) >= 0) {
                item = ((ItemStack)player.field_71071_by.field_70462_a.get((int)foci.get(newkey))).func_77946_l();
            }
            else {
                final int pid = foci.get(newkey) / 1000;
                if (pouches.containsKey(pid)) {
                    final int pouchslot = pouches.get(pid);
                    final int focusslot = foci.get(newkey) - pid * 1000;
                    ItemStack tmp = ItemStack.field_190927_a;
                    if (pouchslot >= 0) {
                        tmp = ((ItemStack)player.field_71071_by.field_70462_a.get(pouchslot)).func_77946_l();
                    }
                    else {
                        tmp = baubles.func_70301_a(pouchslot + 4).func_77946_l();
                    }
                    item = fetchFocusFromPouch(player, focusslot, tmp, pouchslot);
                }
            }
            if (item == null || item.func_190926_b()) {
                return;
            }
            if (foci.get(newkey) < 1000 && foci.get(newkey) >= 0) {
                player.field_71071_by.func_70299_a((int)foci.get(newkey), ItemStack.field_190927_a);
            }
            player.func_184185_a(SoundsTC.ticks, 0.3f, 1.0f);
            if (wand.getFocus(is) != null && (addFocusToPouch(player, wand.getFocusStack(is).func_77946_l(), pouches) || player.field_71071_by.func_70441_a(wand.getFocusStack(is).func_77946_l()))) {
                wand.setFocus(is, ItemStack.field_190927_a);
            }
            if (wand.getFocus(is) == null) {
                wand.setFocus(is, item);
            }
            else if (!addFocusToPouch(player, item, pouches)) {
                player.field_71071_by.func_70441_a(item);
            }
        }
    }
    
    private static ItemStack fetchFocusFromPouch(final EntityPlayer player, final int focusid, final ItemStack pouch, final int pouchslot) {
        ItemStack focus = ItemStack.field_190927_a;
        final NonNullList<ItemStack> inv = ((ItemFocusPouch)pouch.func_77973_b()).getInventory(pouch);
        final ItemStack contents = (ItemStack)inv.get(focusid);
        if (contents.func_77973_b() instanceof ItemFocus) {
            focus = contents.func_77946_l();
            inv.set(focusid, (Object)ItemStack.field_190927_a);
            ((ItemFocusPouch)pouch.func_77973_b()).setInventory(pouch, inv);
            if (pouchslot >= 0) {
                player.field_71071_by.func_70299_a(pouchslot, pouch);
                player.field_71071_by.func_70296_d();
            }
            else {
                final IInventory baubles = BaublesApi.getBaubles(player);
                baubles.func_70299_a(pouchslot + 4, pouch);
                BaublesApi.getBaublesHandler(player).setChanged(pouchslot + 4, true);
                baubles.func_70296_d();
            }
        }
        return focus;
    }
    
    private static boolean addFocusToPouch(final EntityPlayer player, final ItemStack focus, final HashMap<Integer, Integer> pouches) {
        final IInventory baubles = BaublesApi.getBaubles(player);
        for (final Integer pouchslot : pouches.values()) {
            ItemStack pouch;
            if (pouchslot >= 0) {
                pouch = (ItemStack)player.field_71071_by.field_70462_a.get((int)pouchslot);
            }
            else {
                pouch = baubles.func_70301_a(pouchslot + 4);
            }
            final NonNullList<ItemStack> inv = ((ItemFocusPouch)pouch.func_77973_b()).getInventory(pouch);
            for (int q = 0; q < inv.size(); ++q) {
                final ItemStack contents = (ItemStack)inv.get(q);
                if (contents.func_190926_b()) {
                    inv.set(q, (Object)focus.func_77946_l());
                    ((ItemFocusPouch)pouch.func_77973_b()).setInventory(pouch, inv);
                    if (pouchslot >= 0) {
                        player.field_71071_by.func_70299_a((int)pouchslot, pouch);
                        player.field_71071_by.func_70296_d();
                    }
                    else {
                        baubles.func_70299_a(pouchslot + 4, pouch);
                        BaublesApi.getBaublesHandler(player).setChanged(pouchslot + 4, true);
                        baubles.func_70296_d();
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void toggleMisc(final ItemStack itemstack, final World world, final EntityPlayer player, final int mod) {
        if (!(itemstack.func_77973_b() instanceof ICaster)) {
            return;
        }
        final ICaster caster = (ICaster)itemstack.func_77973_b();
        final ItemFocus focus = (ItemFocus)caster.getFocus(itemstack);
        final FocusPackage fp = ItemFocus.getPackage(caster.getFocusStack(itemstack));
        if (fp != null && FocusEngine.doesPackageContainElement(fp, "thaumcraft.PLAN")) {
            int dim = getAreaDim(itemstack);
            if (mod == 0) {
                int areax = getAreaX(itemstack);
                int areay = getAreaY(itemstack);
                int areaz = getAreaZ(itemstack);
                final int max = getAreaSize(itemstack);
                if (dim == 0) {
                    ++areax;
                    ++areaz;
                    ++areay;
                }
                else if (dim == 1) {
                    ++areax;
                }
                else if (dim == 2) {
                    ++areaz;
                }
                else if (dim == 3) {
                    ++areay;
                }
                if (areax > max) {
                    areax = 0;
                }
                if (areaz > max) {
                    areaz = 0;
                }
                if (areay > max) {
                    areay = 0;
                }
                setAreaX(itemstack, areax);
                setAreaY(itemstack, areay);
                setAreaZ(itemstack, areaz);
            }
            if (mod == 1) {
                if (++dim > 3) {
                    dim = 0;
                }
                setAreaDim(itemstack, dim);
            }
        }
    }
    
    private static int getAreaSize(final ItemStack itemstack) {
        final boolean pot = false;
        if (!(itemstack.func_77973_b() instanceof ICaster)) {
            return 0;
        }
        final ICaster caster = (ICaster)itemstack.func_77973_b();
        final ItemFocus focus = (ItemFocus)caster.getFocus(itemstack);
        return pot ? 6 : 3;
    }
    
    public static int getAreaDim(final ItemStack stack) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("aread")) {
            return stack.func_77978_p().func_74762_e("aread");
        }
        return 0;
    }
    
    public static int getAreaX(final ItemStack stack) {
        final ICaster wand = (ICaster)stack.func_77973_b();
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("areax")) {
            int a = stack.func_77978_p().func_74762_e("areax");
            if (a > getAreaSize(stack)) {
                a = getAreaSize(stack);
            }
            return a;
        }
        return getAreaSize(stack);
    }
    
    public static int getAreaY(final ItemStack stack) {
        final ICaster wand = (ICaster)stack.func_77973_b();
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("areay")) {
            int a = stack.func_77978_p().func_74762_e("areay");
            if (a > getAreaSize(stack)) {
                a = getAreaSize(stack);
            }
            return a;
        }
        return getAreaSize(stack);
    }
    
    public static int getAreaZ(final ItemStack stack) {
        final ICaster wand = (ICaster)stack.func_77973_b();
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("areaz")) {
            int a = stack.func_77978_p().func_74762_e("areaz");
            if (a > getAreaSize(stack)) {
                a = getAreaSize(stack);
            }
            return a;
        }
        return getAreaSize(stack);
    }
    
    public static void setAreaX(final ItemStack stack, final int area) {
        if (stack.func_77942_o()) {
            stack.func_77978_p().func_74768_a("areax", area);
        }
    }
    
    public static void setAreaY(final ItemStack stack, final int area) {
        if (stack.func_77942_o()) {
            stack.func_77978_p().func_74768_a("areay", area);
        }
    }
    
    public static void setAreaZ(final ItemStack stack, final int area) {
        if (stack.func_77942_o()) {
            stack.func_77978_p().func_74768_a("areaz", area);
        }
    }
    
    public static void setAreaDim(final ItemStack stack, final int dim) {
        if (stack.func_77942_o()) {
            stack.func_77978_p().func_74768_a("aread", dim);
        }
    }
    
    static boolean isOnCooldown(final EntityLivingBase entityLiving) {
        if (entityLiving.field_70170_p.field_72995_K && CasterManager.cooldownClient.containsKey(entityLiving.func_145782_y())) {
            return CasterManager.cooldownClient.get(entityLiving.func_145782_y()) > System.currentTimeMillis();
        }
        return !entityLiving.field_70170_p.field_72995_K && CasterManager.cooldownServer.containsKey(entityLiving.func_145782_y()) && CasterManager.cooldownServer.get(entityLiving.func_145782_y()) > System.currentTimeMillis();
    }
    
    public static float getCooldown(final EntityLivingBase entityLiving) {
        if (entityLiving.field_70170_p.field_72995_K && CasterManager.cooldownClient.containsKey(entityLiving.func_145782_y())) {
            return (CasterManager.cooldownClient.get(entityLiving.func_145782_y()) - System.currentTimeMillis()) / 1000.0f;
        }
        return 0.0f;
    }
    
    public static void setCooldown(final EntityLivingBase entityLiving, final int cd) {
        if (cd == 0) {
            CasterManager.cooldownClient.remove(entityLiving.func_145782_y());
            CasterManager.cooldownServer.remove(entityLiving.func_145782_y());
        }
        else if (entityLiving.field_70170_p.field_72995_K) {
            CasterManager.cooldownClient.put(entityLiving.func_145782_y(), System.currentTimeMillis() + cd * 50);
        }
        else {
            CasterManager.cooldownServer.put(entityLiving.func_145782_y(), System.currentTimeMillis() + cd * 50);
        }
    }
    
    static {
        CasterManager.cooldownServer = new HashMap<Integer, Long>();
        CasterManager.cooldownClient = new HashMap<Integer, Long>();
    }
}
