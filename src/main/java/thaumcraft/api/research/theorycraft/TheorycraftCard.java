package thaumcraft.api.research.theorycraft;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.nbt.*;

public abstract class TheorycraftCard
{
    private long seed;
    
    public TheorycraftCard() {
        this.seed = -1L;
    }
    
    public long getSeed() {
        if (this.seed < 0L) {
            this.setSeed(System.nanoTime());
        }
        return this.seed;
    }
    
    public boolean initialize(final EntityPlayer player, final ResearchTableData data) {
        return true;
    }
    
    public boolean isAidOnly() {
        return false;
    }
    
    public abstract int getInspirationCost();
    
    public String getResearchCategory() {
        return null;
    }
    
    public abstract String getLocalizedName();
    
    public abstract String getLocalizedText();
    
    public ItemStack[] getRequiredItems() {
        return null;
    }
    
    public boolean[] getRequiredItemsConsumed() {
        if (this.getRequiredItems() != null) {
            final boolean[] b = new boolean[this.getRequiredItems().length];
            Arrays.fill(b, false);
            return b;
        }
        return null;
    }
    
    public abstract boolean activate(final EntityPlayer p0, final ResearchTableData p1);
    
    public void setSeed(final long seed) {
        this.seed = Math.abs(seed);
    }
    
    public NBTTagCompound serialize() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.func_74772_a("seed", this.seed);
        return nbt;
    }
    
    public void deserialize(final NBTTagCompound nbt) {
        if (nbt == null) {
            return;
        }
        this.seed = nbt.func_74763_f("seed");
    }
}
