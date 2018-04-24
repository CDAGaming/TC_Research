package thaumcraft.common.tiles.devices;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.blocks.*;
import net.minecraft.block.properties.*;
import thaumcraft.common.blocks.devices.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class TileArcaneEar extends TileEntity implements ITickable
{
    public byte note;
    public byte tone;
    public int redstoneSignal;
    public static WeakHashMap<Integer, ArrayList<Integer[]>> noteBlockEvents;
    
    public TileArcaneEar() {
        this.note = 0;
        this.tone = 0;
        this.redstoneSignal = 0;
    }
    
    public NBTTagCompound func_189515_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_189515_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74774_a("note", this.note);
        par1NBTTagCompound.func_74774_a("tone", this.tone);
        return par1NBTTagCompound;
    }
    
    public void func_145839_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_145839_a(par1NBTTagCompound);
        this.note = par1NBTTagCompound.func_74771_c("note");
        this.tone = par1NBTTagCompound.func_74771_c("tone");
        if (this.note < 0) {
            this.note = 0;
        }
        if (this.note > 24) {
            this.note = 24;
        }
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            if (this.redstoneSignal > 0) {
                --this.redstoneSignal;
                if (this.redstoneSignal == 0) {
                    final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d();
                    final TileEntity tileentity = this.field_145850_b.func_175625_s(this.field_174879_c);
                    this.field_145850_b.func_180501_a(this.field_174879_c, this.field_145850_b.func_180495_p(this.field_174879_c).func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)false), 3);
                    if (tileentity != null) {
                        tileentity.func_145829_t();
                        this.field_145850_b.func_175690_a(this.field_174879_c, tileentity);
                    }
                    this.field_145850_b.func_175685_c(this.field_174879_c, this.func_145838_q(), true);
                    this.field_145850_b.func_175685_c(this.field_174879_c.func_177972_a(facing), this.func_145838_q(), true);
                    final IBlockState state = this.field_145850_b.func_180495_p(this.field_174879_c);
                    this.field_145850_b.markAndNotifyBlock(this.field_174879_c, this.field_145850_b.func_175726_f(this.field_174879_c), state, state, 3);
                }
            }
            final ArrayList<Integer[]> nbe = TileArcaneEar.noteBlockEvents.get(this.field_145850_b.field_73011_w.getDimension());
            if (nbe != null) {
                for (final Integer[] dat : nbe) {
                    if (dat[3] == this.tone && dat[4] == this.note && this.func_145835_a(dat[0] + 0.5, dat[1] + 0.5, dat[2] + 0.5) <= 4096.0) {
                        final EnumFacing facing2 = BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d();
                        this.triggerNote(this.field_145850_b, this.field_174879_c, false);
                        final TileEntity tileentity2 = this.field_145850_b.func_175625_s(this.field_174879_c);
                        final IBlockState state2 = this.field_145850_b.func_180495_p(this.field_174879_c);
                        if (this.func_145838_q() instanceof BlockArcaneEarToggle) {
                            this.field_145850_b.func_180501_a(this.field_174879_c, state2.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)!BlockStateUtils.isEnabled(state2)), 3);
                        }
                        else {
                            this.redstoneSignal = 10;
                            this.field_145850_b.func_180501_a(this.field_174879_c, state2.func_177226_a((IProperty)IBlockEnabled.ENABLED, (Comparable)true), 3);
                        }
                        if (tileentity2 != null) {
                            tileentity2.func_145829_t();
                            this.field_145850_b.func_175690_a(this.field_174879_c, tileentity2);
                        }
                        this.field_145850_b.func_175685_c(this.field_174879_c, this.func_145838_q(), true);
                        this.field_145850_b.func_175685_c(this.field_174879_c.func_177972_a(facing2), this.func_145838_q(), true);
                        final IBlockState state3 = this.field_145850_b.func_180495_p(this.field_174879_c);
                        this.field_145850_b.markAndNotifyBlock(this.field_174879_c, this.field_145850_b.func_175726_f(this.field_174879_c), state3, state3, 3);
                        break;
                    }
                }
            }
        }
    }
    
    public void updateTone() {
        try {
            final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d();
            final Material var5 = this.field_145850_b.func_180495_p(this.field_174879_c.func_177972_a(facing)).func_185904_a();
            this.tone = 0;
            if (var5 == Material.field_151576_e) {
                this.tone = 1;
            }
            if (var5 == Material.field_151595_p) {
                this.tone = 2;
            }
            if (var5 == Material.field_151592_s) {
                this.tone = 3;
            }
            if (var5 == Material.field_151575_d) {
                this.tone = 4;
            }
            this.func_70296_d();
        }
        catch (Exception ex) {}
    }
    
    public void changePitch() {
        this.note = (byte)((this.note + 1) % 25);
        this.func_70296_d();
    }
    
    public void triggerNote(final World world, final BlockPos pos, final boolean sound) {
        byte var6 = -1;
        if (sound) {
            final EnumFacing facing = BlockStateUtils.getFacing(this.func_145832_p()).func_176734_d();
            final Material var7 = world.func_180495_p(pos.func_177972_a(facing)).func_185904_a();
            var6 = 0;
            if (var7 == Material.field_151576_e) {
                var6 = 1;
            }
            if (var7 == Material.field_151595_p) {
                var6 = 2;
            }
            if (var7 == Material.field_151592_s) {
                var6 = 3;
            }
            if (var7 == Material.field_151575_d) {
                var6 = 4;
            }
        }
        world.func_175641_c(pos, this.func_145838_q(), (int)var6, (int)this.note);
    }
    
    static {
        TileArcaneEar.noteBlockEvents = new WeakHashMap<Integer, ArrayList<Integer[]>>();
    }
}
