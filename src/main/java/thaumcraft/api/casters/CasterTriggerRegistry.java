package thaumcraft.api.casters;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class CasterTriggerRegistry
{
    private static HashMap<String, LinkedHashMap<IBlockState, List<Trigger>>> triggers;
    private static final String DEFAULT = "default";
    
    public static void registerWandBlockTrigger(final ICasterTriggerManager manager, final int event, final IBlockState state, final String modid) {
        if (!CasterTriggerRegistry.triggers.containsKey(modid)) {
            CasterTriggerRegistry.triggers.put(modid, new LinkedHashMap<IBlockState, List<Trigger>>());
        }
        final LinkedHashMap<IBlockState, List<Trigger>> temp = CasterTriggerRegistry.triggers.get(modid);
        List<Trigger> ts = temp.get(state);
        if (ts == null) {
            ts = new ArrayList<Trigger>();
        }
        ts.add(new Trigger(manager, event));
        temp.put(state, ts);
        CasterTriggerRegistry.triggers.put(modid, temp);
    }
    
    public static void registerCasterBlockTrigger(final ICasterTriggerManager manager, final int event, final IBlockState state) {
        registerWandBlockTrigger(manager, event, state, "default");
    }
    
    public static boolean hasTrigger(final IBlockState state) {
        for (final String modid : CasterTriggerRegistry.triggers.keySet()) {
            final LinkedHashMap<IBlockState, List<Trigger>> temp = CasterTriggerRegistry.triggers.get(modid);
            if (temp.containsKey(state)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasTrigger(final IBlockState state, final String modid) {
        if (!CasterTriggerRegistry.triggers.containsKey(modid)) {
            return false;
        }
        final LinkedHashMap<IBlockState, List<Trigger>> temp = CasterTriggerRegistry.triggers.get(modid);
        return temp.containsKey(state);
    }
    
    public static boolean performTrigger(final World world, final ItemStack casterStack, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final IBlockState state) {
        for (final String modid : CasterTriggerRegistry.triggers.keySet()) {
            final LinkedHashMap<IBlockState, List<Trigger>> temp = CasterTriggerRegistry.triggers.get(modid);
            final List<Trigger> l = temp.get(state);
            if (l != null) {
                if (l.size() == 0) {
                    continue;
                }
                for (final Trigger trig : l) {
                    final boolean result = trig.manager.performTrigger(world, casterStack, player, pos, side, trig.event);
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean performTrigger(final World world, final ItemStack casterStack, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final IBlockState state, final String modid) {
        if (!CasterTriggerRegistry.triggers.containsKey(modid)) {
            return false;
        }
        final LinkedHashMap<IBlockState, List<Trigger>> temp = CasterTriggerRegistry.triggers.get(modid);
        final List<Trigger> l = temp.get(state);
        if (l == null || l.size() == 0) {
            return false;
        }
        for (final Trigger trig : l) {
            final boolean result = trig.manager.performTrigger(world, casterStack, player, pos, side, trig.event);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    static {
        CasterTriggerRegistry.triggers = new HashMap<String, LinkedHashMap<IBlockState, List<Trigger>>>();
    }
    
    private static class Trigger
    {
        ICasterTriggerManager manager;
        int event;
        
        public Trigger(final ICasterTriggerManager manager, final int event) {
            this.manager = manager;
            this.event = event;
        }
    }
}
