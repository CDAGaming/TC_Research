package thaumcraft.api.casters;

import thaumcraft.api.aspects.*;
import net.minecraft.util.math.*;
import java.util.*;

public abstract class FocusNode implements IFocusElement
{
    FocusPackage pack;
    private FocusNode parent;
    final HashMap<String, NodeSetting> settings;
    
    public FocusNode() {
        this.settings = new HashMap<String, NodeSetting>();
        this.initialize();
    }
    
    public String getUnlocalizedName() {
        return this.getKey() + ".name";
    }
    
    public String getUnlocalizedText() {
        return this.getKey() + ".text";
    }
    
    public abstract int getComplexity();
    
    public abstract Aspect getAspect();
    
    public abstract EnumSupplyType[] mustBeSupplied();
    
    public abstract EnumSupplyType[] willSupply();
    
    public boolean canSupply(final EnumSupplyType type) {
        if (this.willSupply() != null) {
            for (final EnumSupplyType st : this.willSupply()) {
                if (st == type) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public RayTraceResult[] supplyTargets() {
        return null;
    }
    
    public Trajectory[] supplyTrajectories() {
        return null;
    }
    
    public final void setPackage(final FocusPackage pack) {
        this.pack = pack;
    }
    
    public final FocusPackage getPackage() {
        return this.pack;
    }
    
    public final FocusPackage getRemainingPackage() {
        final FocusPackage p = this.getPackage();
        final List<IFocusElement> l = p.nodes.subList(p.index + 1, p.nodes.size());
        final List<IFocusElement> l2 = Collections.synchronizedList(new ArrayList<IFocusElement>());
        for (final IFocusElement fe : l) {
            l2.add(fe);
        }
        final FocusPackage p2 = new FocusPackage();
        p2.setUniqueID(p.getUniqueID());
        p2.world = p.world;
        p2.multiplyPower(p.getPower());
        p2.nodes = l2;
        p2.setCasterUUID(p.getCasterUUID());
        return l2.isEmpty() ? null : p2;
    }
    
    public final FocusNode getParent() {
        return this.parent;
    }
    
    public final Set<String> getSettingList() {
        return this.settings.keySet();
    }
    
    public final NodeSetting getSetting(final String key) {
        return this.settings.get(key);
    }
    
    public final int getSettingValue(final String key) {
        return this.settings.containsKey(key) ? this.settings.get(key).getValue() : 0;
    }
    
    public NodeSetting[] createSettings() {
        return null;
    }
    
    public final void initialize() {
        final NodeSetting[] set = this.createSettings();
        if (set != null) {
            for (final NodeSetting setting : set) {
                this.settings.put(setting.key, setting);
            }
        }
    }
    
    public void setParent(final FocusNode parent) {
        this.parent = parent;
    }
    
    public float getPowerMultiplier() {
        return 1.0f;
    }
    
    public enum EnumSupplyType
    {
        TARGET, 
        TRAJECTORY;
    }
}
