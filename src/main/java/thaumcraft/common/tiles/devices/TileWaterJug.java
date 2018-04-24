package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import thaumcraft.client.fx.*;
import net.minecraftforge.fluids.capability.*;
import vazkii.botania.api.item.*;
import net.minecraftforge.fluids.*;
import thaumcraft.api.aura.*;
import net.minecraft.tileentity.*;

public class TileWaterJug extends TileThaumcraft implements ITickable
{
    public int charge;
    int zone;
    int counter;
    ArrayList<Integer> handlers;
    int zc;
    int tcount;
    
    public TileWaterJug() {
        this.charge = 0;
        this.zone = 0;
        this.counter = 0;
        this.handlers = new ArrayList<Integer>();
        this.zc = 0;
        this.tcount = 0;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.charge = nbt.func_74762_e("charge");
        final NBTTagList nbttaglist = nbt.func_150295_c("handlers", 3);
        this.handlers = new ArrayList<Integer>();
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagInt tag = (NBTTagInt)nbttaglist.func_179238_g(i);
            this.handlers.add(tag.func_150287_d());
        }
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("charge", this.charge);
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.handlers.size(); ++i) {
            final NBTTagInt nbtTagInt = new NBTTagInt((int)this.handlers.get(i));
        }
        nbt.func_74782_a("handlers", (NBTBase)nbttaglist);
        return nbt;
    }
    
    public void func_73660_a() {
        ++this.counter;
        if (this.field_145850_b.field_72995_K) {
            if (this.tcount > 0 || this.field_145850_b.field_73012_v.nextInt(15) == 0) {
                FXDispatcher.INSTANCE.jarSplashFx(this.func_174877_v().func_177958_n() + 0.5, this.func_174877_v().func_177956_o() + 1, this.func_174877_v().func_177952_p() + 0.5);
            }
            if (this.tcount > 0) {
                if (this.tcount % 5 == 0) {
                    final int x = this.zc / 5 % 5;
                    final int y = this.zc / 5 / 5 % 3;
                    final int z = this.zc % 5;
                    FXDispatcher.INSTANCE.waterTrailFx(this.func_174877_v(), this.func_174877_v().func_177982_a(x - 2, y - 1, z - 2), this.counter, 2650102, 0.1f);
                }
                --this.tcount;
            }
        }
        else if (this.counter % 5 == 0) {
            ++this.zone;
            int x = this.zone / 5 % 5;
            int y = this.zone / 5 / 5 % 3;
            int z = this.zone % 5;
            final TileEntity te = this.field_145850_b.func_175625_s(this.func_174877_v().func_177982_a(x - 2, y - 1, z - 2));
            if (te != null && (te instanceof IFluidHandler || te instanceof IPetalApothecary) && !this.handlers.contains(this.zone)) {
                this.handlers.add(this.zone);
                this.func_70296_d();
            }
            int i = 0;
            while (i < this.handlers.size() && this.charge > 0) {
                final int zz = this.handlers.get(i);
                x = zz / 5 % 5;
                y = zz / 5 / 5 % 3;
                z = zz % 5;
                final TileEntity tile = this.field_145850_b.func_175625_s(this.func_174877_v().func_177982_a(x - 2, y - 1, z - 2));
                if (tile != null && tile instanceof IFluidHandler) {
                    final IFluidHandler fh = (IFluidHandler)tile;
                    final int q = fh.fill(new FluidStack(FluidRegistry.WATER, 25), true);
                    if (q > 0) {
                        this.charge -= q;
                        this.func_70296_d();
                        this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_145838_q(), 1, zz);
                        break;
                    }
                }
                else {
                    if (tile == null || !(tile instanceof IPetalApothecary)) {
                        this.handlers.remove(i);
                        this.func_70296_d();
                        continue;
                    }
                    final IPetalApothecary pa = (IPetalApothecary)tile;
                    if (!pa.hasWater()) {
                        pa.setWater(true);
                        this.field_145850_b.func_175641_c(this.func_174877_v(), this.func_145838_q(), 2, zz);
                        this.charge -= 250;
                    }
                }
                ++i;
            }
            if (this.charge <= 0 && AuraHelper.drainVis(this.func_145831_w(), this.func_174877_v(), 1.0f, false) > 0.0f) {
                this.charge += 1000;
                this.func_70296_d();
            }
        }
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 1) {
            if (this.field_145850_b.field_72995_K) {
                this.zc = j;
                this.tcount = 5;
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
}
