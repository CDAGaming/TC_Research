package thaumcraft.api.research;

import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.item.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

public class ScanningManager
{
    static ArrayList<IScanThing> things;
    
    public static void addScannableThing(final IScanThing obj) {
        ScanningManager.things.add(obj);
    }
    
    public static void scanTheThing(final EntityPlayer player, final Object object) {
        boolean found = false;
        boolean suppress = false;
        for (final IScanThing thing : ScanningManager.things) {
            if (thing.checkThing(player, object) && (thing.getResearchKey(player, object) == null || thing.getResearchKey(player, object).isEmpty() || ThaumcraftApi.internalMethods.progressResearch(player, thing.getResearchKey(player, object)))) {
                if (thing.getResearchKey(player, object) == null || thing.getResearchKey(player, object).isEmpty()) {
                    suppress = true;
                }
                found = true;
                thing.onSuccess(player, object);
            }
        }
        if (!suppress) {
            if (!found) {
                player.func_146105_b((ITextComponent)new TextComponentString("§5§o" + I18n.func_74838_a("tc.unknownobject")), true);
            }
            else {
                player.func_146105_b((ITextComponent)new TextComponentString("§a§o" + I18n.func_74838_a("tc.knownobject")), true);
            }
        }
        if (object instanceof BlockPos && player.func_130014_f_().func_175625_s((BlockPos)object) instanceof IInventory) {
            final IInventory inv = (IInventory)player.func_130014_f_().func_175625_s((BlockPos)object);
            for (int slot = 0; slot < inv.func_70302_i_(); ++slot) {
                final ItemStack stack = inv.func_70301_a(slot);
                if (stack != null && !stack.func_190926_b()) {
                    scanTheThing(player, stack);
                }
            }
        }
    }
    
    public static boolean isThingStillScannable(final EntityPlayer player, final Object object) {
        for (final IScanThing thing : ScanningManager.things) {
            if (thing.checkThing(player, object)) {
                try {
                    if (!ThaumcraftCapabilities.knowsResearch(player, thing.getResearchKey(player, object))) {
                        return true;
                    }
                    continue;
                }
                catch (Exception ex) {}
            }
        }
        return false;
    }
    
    public static ItemStack getItemFromParms(final EntityPlayer player, final Object obj) {
        ItemStack is = ItemStack.field_190927_a;
        if (obj instanceof ItemStack) {
            is = (ItemStack)obj;
        }
        if (obj instanceof EntityItem && ((EntityItem)obj).func_92059_d() != null) {
            is = ((EntityItem)obj).func_92059_d();
        }
        if (obj instanceof BlockPos) {
            final IBlockState state = player.field_70170_p.func_180495_p((BlockPos)obj);
            is = state.func_177230_c().func_185473_a(player.field_70170_p, (BlockPos)obj, state);
            try {
                if (is == null || is.func_190926_b()) {
                    is = state.func_177230_c().getPickBlock(state, rayTrace(player), player.field_70170_p, (BlockPos)obj, player);
                }
            }
            catch (Exception ex) {}
            try {
                if ((is == null || is.func_190926_b()) && state.func_185904_a() == Material.field_151586_h) {
                    is = new ItemStack(Items.field_151131_as);
                }
                if ((is == null || is.func_190926_b()) && state.func_185904_a() == Material.field_151587_i) {
                    is = new ItemStack(Items.field_151129_at);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return is;
    }
    
    private static RayTraceResult rayTrace(final EntityPlayer player) {
        final Vec3d vec3d = player.func_174824_e(0.0f);
        final Vec3d vec3d2 = player.func_70676_i(0.0f);
        final Vec3d vec3d3 = vec3d.func_72441_c(vec3d2.field_72450_a * 4.0, vec3d2.field_72448_b * 4.0, vec3d2.field_72449_c * 4.0);
        return player.field_70170_p.func_147447_a(vec3d, vec3d3, true, false, true);
    }
    
    static {
        ScanningManager.things = new ArrayList<IScanThing>();
    }
}
