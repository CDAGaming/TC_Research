package thaumcraft.api.casters;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.common.*;
import net.minecraft.nbt.*;

public class FocusPackage implements IFocusElement
{
    public World world;
    private EntityLivingBase caster;
    private UUID casterUUID;
    private float power;
    private int complexity;
    int index;
    UUID uid;
    public List<IFocusElement> nodes;
    
    @Override
    public String getResearch() {
        return null;
    }
    
    public FocusPackage() {
        this.power = 1.0f;
        this.complexity = 0;
        this.nodes = Collections.synchronizedList(new ArrayList<IFocusElement>());
    }
    
    public FocusPackage(final EntityLivingBase caster) {
        this.power = 1.0f;
        this.complexity = 0;
        this.nodes = Collections.synchronizedList(new ArrayList<IFocusElement>());
        this.world = caster.field_70170_p;
        this.caster = caster;
        this.casterUUID = caster.func_110124_au();
    }
    
    @Override
    public String getKey() {
        return "thaumcraft.PACKAGE";
    }
    
    @Override
    public EnumUnitType getType() {
        return EnumUnitType.PACKAGE;
    }
    
    public int getComplexity() {
        return this.complexity;
    }
    
    public void setComplexity(final int complexity) {
        this.complexity = complexity;
    }
    
    public UUID getUniqueID() {
        return this.uid;
    }
    
    public void setUniqueID(final UUID id) {
        this.uid = id;
    }
    
    public int getExecutionIndex() {
        return this.index;
    }
    
    public void setExecutionIndex(final int idx) {
        this.index = idx;
    }
    
    public void addNode(final IFocusElement e) {
        this.nodes.add(e);
    }
    
    public UUID getCasterUUID() {
        if (this.caster != null) {
            this.casterUUID = this.caster.func_110124_au();
        }
        return this.casterUUID;
    }
    
    public void setCasterUUID(final UUID casterUUID) {
        this.casterUUID = casterUUID;
    }
    
    public EntityLivingBase getCaster() {
        try {
            if (this.caster == null) {
                this.caster = (EntityLivingBase)this.world.func_152378_a(this.getCasterUUID());
            }
            if (this.caster == null) {
                for (final Entity e : this.world.func_72910_y()) {
                    if (e instanceof EntityLivingBase && this.getCasterUUID().equals(e.func_110124_au())) {
                        this.caster = (EntityLivingBase)e;
                        break;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return this.caster;
    }
    
    public FocusEffect[] getFocusEffects() {
        return this.getFocusEffectsPackage(this);
    }
    
    private FocusEffect[] getFocusEffectsPackage(final FocusPackage fp) {
        final ArrayList<FocusEffect> out = new ArrayList<FocusEffect>();
        for (final IFocusElement el : fp.nodes) {
            if (el instanceof FocusEffect) {
                out.add((FocusEffect)el);
            }
            else if (el instanceof FocusPackage) {
                for (final FocusEffect fep : this.getFocusEffectsPackage((FocusPackage)el)) {
                    out.add(fep);
                }
            }
            else {
                if (!(el instanceof FocusModSplit)) {
                    continue;
                }
                for (final FocusPackage fsp : ((FocusModSplit)el).getSplitPackages()) {
                    for (final FocusEffect fep2 : this.getFocusEffectsPackage(fsp)) {
                        out.add(fep2);
                    }
                }
            }
        }
        return out.toArray(new FocusEffect[0]);
    }
    
    public void deserialize(final NBTTagCompound nbt) {
        this.uid = nbt.func_186857_a("uid");
        this.index = nbt.func_74762_e("index");
        final int dim = nbt.func_74762_e("dim");
        this.world = (World)DimensionManager.getWorld(dim);
        this.setCasterUUID(nbt.func_186857_a("casterUUID"));
        this.power = nbt.func_74760_g("power");
        this.complexity = nbt.func_74762_e("complexity");
        final NBTTagList nodelist = nbt.func_150295_c("nodes", 10);
        this.nodes.clear();
        for (int x = 0; x < nodelist.func_74745_c(); ++x) {
            final NBTTagCompound nodenbt = nodelist.func_150305_b(x);
            final EnumUnitType ut = EnumUnitType.valueOf(nodenbt.func_74779_i("type"));
            if (ut != null) {
                if (ut == EnumUnitType.PACKAGE) {
                    final FocusPackage fp = new FocusPackage();
                    fp.deserialize(nodenbt.func_74775_l("package"));
                    this.nodes.add(fp);
                    break;
                }
                final IFocusElement fn = FocusEngine.getElement(nodenbt.func_74779_i("key"));
                if (fn != null) {
                    if (fn instanceof FocusNode) {
                        ((FocusNode)fn).initialize();
                        if (((FocusNode)fn).getSettingList() != null) {
                            for (final String ns : ((FocusNode)fn).getSettingList()) {
                                ((FocusNode)fn).getSetting(ns).setValue(nodenbt.func_74762_e("setting." + ns));
                            }
                        }
                        if (fn instanceof FocusModSplit) {
                            ((FocusModSplit)fn).deserialize(nodenbt.func_74775_l("packages"));
                        }
                    }
                    this.addNode(fn);
                }
            }
        }
    }
    
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = new NBTTagCompound();
        if (this.uid != null) {
            nbt.func_186854_a("uid", this.uid);
        }
        nbt.func_74768_a("index", this.index);
        if (this.getCasterUUID() != null) {
            nbt.func_186854_a("casterUUID", this.getCasterUUID());
        }
        if (this.world != null) {
            nbt.func_74768_a("dim", this.world.field_73011_w.getDimension());
        }
        nbt.func_74776_a("power", this.power);
        nbt.func_74768_a("complexity", this.complexity);
        final NBTTagList nodelist = new NBTTagList();
        synchronized (this.nodes) {
            for (final IFocusElement node : this.nodes) {
                final NBTTagCompound nodenbt = new NBTTagCompound();
                nodenbt.func_74778_a("type", node.getType().name());
                nodenbt.func_74778_a("key", node.getKey());
                if (node.getType() == EnumUnitType.PACKAGE) {
                    nodenbt.func_74782_a("package", (NBTBase)((FocusPackage)node).serialize());
                    nodelist.func_74742_a((NBTBase)nodenbt);
                    break;
                }
                if (node instanceof FocusNode && ((FocusNode)node).getSettingList() != null) {
                    for (final String ns : ((FocusNode)node).getSettingList()) {
                        nodenbt.func_74768_a("setting." + ns, ((FocusNode)node).getSettingValue(ns));
                    }
                }
                if (node instanceof FocusModSplit) {
                    nodenbt.func_74782_a("packages", (NBTBase)((FocusModSplit)node).serialize());
                }
                nodelist.func_74742_a((NBTBase)nodenbt);
            }
        }
        nbt.func_74782_a("nodes", (NBTBase)nodelist);
        return nbt;
    }
    
    public float getPower() {
        return this.power;
    }
    
    public void multiplyPower(final float pow) {
        this.power *= pow;
    }
    
    public FocusPackage copy(final EntityLivingBase caster) {
        final FocusPackage fp = new FocusPackage(caster);
        fp.deserialize(this.serialize());
        return fp;
    }
    
    public void initialize(final EntityLivingBase caster) {
        final IFocusElement node = this.nodes.get(0);
        if (node instanceof FocusMediumRoot && ((FocusMediumRoot)node).supplyTargets() == null) {
            ((FocusMediumRoot)node).setupFromCaster(caster);
        }
    }
    
    public int getSortingHelper() {
        String s = "";
        for (final IFocusElement k : this.nodes) {
            s += k.getKey();
        }
        return s.hashCode();
    }
}
