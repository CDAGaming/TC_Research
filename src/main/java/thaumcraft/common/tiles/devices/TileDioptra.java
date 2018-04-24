package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.world.aura.*;
import net.minecraft.nbt.*;

public class TileDioptra extends TileThaumcraft implements ITickable
{
    public int counter;
    public byte[] grid_amt;
    private byte[] grid_amt_p;
    
    public TileDioptra() {
        this.counter = 0;
        this.grid_amt = new byte[169];
        this.grid_amt_p = new byte[169];
        Arrays.fill(this.grid_amt, (byte)0);
        Arrays.fill(this.grid_amt_p, (byte)0);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 0.3, this.func_174877_v().func_177956_o() - 0.3, this.func_174877_v().func_177952_p() - 0.3, this.func_174877_v().func_177958_n() + 1.3, this.func_174877_v().func_177956_o() + 2.3, this.func_174877_v().func_177952_p() + 1.3);
    }
    
    public void func_73660_a() {
        ++this.counter;
        if (!this.field_145850_b.field_72995_K) {
            if (this.counter % 20 == 0) {
                Arrays.fill(this.grid_amt, (byte)0);
                for (int xx = 0; xx < 13; ++xx) {
                    for (int zz = 0; zz < 13; ++zz) {
                        final AuraChunk ac = AuraHandler.getAuraChunk(this.field_145850_b.field_73011_w.getDimension(), (this.field_174879_c.func_177958_n() >> 4) + xx - 6, (this.field_174879_c.func_177952_p() >> 4) + zz - 6);
                        if (ac != null) {
                            if (BlockStateUtils.isEnabled(this.func_145832_p())) {
                                this.grid_amt[xx + zz * 13] = (byte)Math.min(64.0f, ac.getVis() / 500.0f * 64.0f);
                            }
                            else {
                                this.grid_amt[xx + zz * 13] = (byte)Math.min(64.0f, ac.getFlux() / 500.0f * 64.0f);
                            }
                        }
                    }
                }
                this.func_70296_d();
                this.syncTile(false);
            }
        }
        else {
            this.counter = 0;
        }
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbt) {
        if (nbt.func_74764_b("grid_a")) {
            this.grid_amt = nbt.func_74770_j("grid_a");
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        nbt.func_74773_a("grid_a", this.grid_amt);
        return nbt;
    }
}
