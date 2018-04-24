package thaumcraft.api.casters;

import net.minecraft.nbt.*;
import java.util.*;

public abstract class FocusModSplit extends FocusMod
{
    private ArrayList<FocusPackage> packages;
    
    public FocusModSplit() {
        this.packages = new ArrayList<FocusPackage>();
    }
    
    public final ArrayList<FocusPackage> getSplitPackages() {
        return this.packages;
    }
    
    public void deserialize(final NBTTagCompound nbt) {
        final NBTTagList nodelist = nbt.func_150295_c("packages", 10);
        this.packages.clear();
        for (int x = 0; x < nodelist.func_74745_c(); ++x) {
            final NBTTagCompound nodenbt = nodelist.func_150305_b(x);
            final FocusPackage fp = new FocusPackage();
            fp.deserialize(nodenbt);
            this.packages.add(fp);
        }
    }
    
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = new NBTTagCompound();
        final NBTTagList nodelist = new NBTTagList();
        for (final FocusPackage node : this.packages) {
            nodelist.func_74742_a((NBTBase)node.serialize());
        }
        nbt.func_74782_a("packages", (NBTBase)nodelist);
        return nbt;
    }
    
    @Override
    public float getPowerMultiplier() {
        return 0.75f;
    }
}
