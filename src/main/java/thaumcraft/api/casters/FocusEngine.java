package thaumcraft.api.casters;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class FocusEngine
{
    public static HashMap<String, Class<IFocusElement>> elements;
    private static HashMap<String, ResourceLocation> elementIcons;
    private static HashMap<String, Integer> elementColor;
    private static ArrayList<String> damageResistList;
    
    public static void registerElement(final Class element, final ResourceLocation icon, final int color) {
        try {
            final IFocusElement fe = element.newInstance();
            FocusEngine.elements.put(fe.getKey(), element);
            FocusEngine.elementIcons.put(fe.getKey(), icon);
            FocusEngine.elementColor.put(fe.getKey(), color);
        }
        catch (Exception ex) {}
    }
    
    public static IFocusElement getElement(final String key) {
        try {
            return FocusEngine.elements.get(key).newInstance();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static ResourceLocation getElementIcon(final String key) {
        return FocusEngine.elementIcons.get(key);
    }
    
    public static int getElementColor(final String key) {
        return FocusEngine.elementColor.get(key);
    }
    
    public static boolean doesPackageContainElement(final FocusPackage focusPackage, final String key) {
        for (final IFocusElement node : focusPackage.nodes) {
            if (node.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    public static void castFocusPackage(final EntityLivingBase caster, final FocusPackage focusPackage, final boolean nocopy) {
        FocusPackage focusPackageCopy;
        if (nocopy) {
            focusPackageCopy = focusPackage;
        }
        else {
            focusPackageCopy = focusPackage.copy(caster);
        }
        focusPackageCopy.initialize(caster);
        focusPackageCopy.setUniqueID(UUID.randomUUID());
        for (final FocusEffect effect : focusPackageCopy.getFocusEffects()) {
            effect.onCast((Entity)caster);
        }
        runFocusPackage(focusPackageCopy, null, null);
    }
    
    public static void castFocusPackage(final EntityLivingBase caster, final FocusPackage focusPackage) {
        castFocusPackage(caster, focusPackage, false);
    }
    
    public static void runFocusPackage(final FocusPackage focusPackage, final Trajectory[] trajectories, final RayTraceResult[] targets) {
        Trajectory[] prevTrajectories = trajectories;
        RayTraceResult[] prevTargets = targets;
        synchronized (focusPackage.nodes) {
            if (!(focusPackage.nodes.get(0) instanceof FocusMediumRoot)) {
                focusPackage.nodes.add(0, new FocusMediumRoot(trajectories, targets));
            }
            for (int idx = 0; idx < focusPackage.nodes.size(); ++idx) {
                focusPackage.setExecutionIndex(idx);
                final IFocusElement node = focusPackage.nodes.get(idx);
                if (idx > 0 && ((FocusNode)node).getParent() == null) {
                    final IFocusElement nodePrev = focusPackage.nodes.get(idx - 1);
                    if (node instanceof FocusNode && nodePrev instanceof FocusNode) {
                        ((FocusNode)node).setParent((FocusNode)nodePrev);
                    }
                }
                if (node instanceof FocusNode && ((FocusNode)node).getPackage() == null) {
                    ((FocusNode)node).setPackage(focusPackage);
                }
                if (node instanceof FocusNode) {
                    focusPackage.multiplyPower(((FocusNode)node).getPowerMultiplier());
                }
                if (node instanceof FocusPackage) {
                    runFocusPackage((FocusPackage)node, prevTrajectories, prevTargets);
                    break;
                }
                if (node instanceof FocusMedium) {
                    final FocusMedium medium = (FocusMedium)node;
                    if (prevTrajectories != null) {
                        for (final Trajectory trajectory : prevTrajectories) {
                            medium.execute(trajectory);
                        }
                    }
                    if (medium.hasIntermediary()) {
                        break;
                    }
                }
                else if (node instanceof FocusMod) {
                    if (node instanceof FocusModSplit) {
                        final FocusModSplit split = (FocusModSplit)node;
                        for (final FocusPackage sp : split.getSplitPackages()) {
                            split.setPackage(sp);
                            sp.multiplyPower(focusPackage.getPower());
                            split.execute();
                            runFocusPackage(sp, split.supplyTrajectories(), split.supplyTargets());
                        }
                        break;
                    }
                    ((FocusMod)node).execute();
                }
                else if (node instanceof FocusEffect) {
                    final FocusEffect effect = (FocusEffect)node;
                    if (prevTargets != null) {
                        int num = 0;
                        for (final RayTraceResult target : prevTargets) {
                            if (target.field_72308_g != null) {
                                final String k = target.field_72308_g.func_145782_y() + focusPackage.getUniqueID().toString();
                                if (FocusEngine.damageResistList.contains(k) && target.field_72308_g.field_70172_ad > 0) {
                                    target.field_72308_g.field_70172_ad = 0;
                                }
                                else {
                                    if (FocusEngine.damageResistList.size() > 10) {
                                        FocusEngine.damageResistList.remove(0);
                                    }
                                    FocusEngine.damageResistList.add(k);
                                }
                            }
                            final Trajectory tra = (prevTrajectories != null) ? ((prevTrajectories.length == prevTargets.length) ? prevTrajectories[num] : prevTrajectories[0]) : null;
                            effect.execute(target, tra, focusPackage.getPower(), num);
                            ++num;
                        }
                    }
                }
                if (node instanceof FocusNode) {
                    prevTrajectories = ((FocusNode)node).supplyTrajectories();
                    prevTargets = ((FocusNode)node).supplyTargets();
                }
            }
        }
    }
    
    static {
        FocusEngine.elements = new HashMap<String, Class<IFocusElement>>();
        FocusEngine.elementIcons = new HashMap<String, ResourceLocation>();
        FocusEngine.elementColor = new HashMap<String, Integer>();
        FocusEngine.damageResistList = new ArrayList<String>();
    }
}
