package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;

public class TileCentrifuge extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ITickable
{
    public Aspect aspectOut;
    public Aspect aspectIn;
    int count;
    int process;
    float rotationSpeed;
    public float rotation;
    
    public TileCentrifuge() {
        this.aspectOut = null;
        this.aspectIn = null;
        this.count = 0;
        this.process = 0;
        this.rotationSpeed = 0.0f;
        this.rotation = 0.0f;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.aspectIn = Aspect.getAspect(nbttagcompound.func_74779_i("aspectIn"));
        this.aspectOut = Aspect.getAspect(nbttagcompound.func_74779_i("aspectOut"));
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        if (this.aspectIn != null) {
            nbttagcompound.func_74778_a("aspectIn", this.aspectIn.getTag());
        }
        if (this.aspectOut != null) {
            nbttagcompound.func_74778_a("aspectOut", this.aspectOut.getTag());
        }
        return nbttagcompound;
    }
    
    @Override
    public AspectList getAspects() {
        final AspectList al = new AspectList();
        if (this.aspectOut != null) {
            al.add(this.aspectOut, 1);
        }
        return al;
    }
    
    @Override
    public int addToContainer(final Aspect tt, int am) {
        if (am > 0 && this.aspectOut == null) {
            this.aspectOut = tt;
            this.func_70296_d();
            this.field_145850_b.markAndNotifyBlock(this.func_174877_v(), this.field_145850_b.func_175726_f(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), 3);
            --am;
        }
        return am;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tt, final int am) {
        if (this.aspectOut != null && tt == this.aspectOut) {
            this.aspectOut = null;
            this.func_70296_d();
            this.field_145850_b.markAndNotifyBlock(this.func_174877_v(), this.field_145850_b.func_175726_f(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), 3);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amt) {
        return amt == 1 && tag == this.aspectOut;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        for (final Aspect tt : ot.getAspects()) {
            if (tt == this.aspectOut) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int containerContains(final Aspect tag) {
        return (tag == this.aspectOut) ? 1 : 0;
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face == EnumFacing.UP || face == EnumFacing.DOWN;
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face == EnumFacing.DOWN;
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return face == EnumFacing.UP;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing face) {
        return null;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing face) {
        return (face == EnumFacing.DOWN) ? (this.gettingPower() ? 0 : ((this.aspectIn == null) ? 128 : 64)) : 0;
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return this.aspectOut;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return (this.aspectOut != null) ? 1 : 0;
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return (this.canOutputTo(face) && this.takeFromContainer(aspect, amount)) ? amount : 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        if (this.aspectIn == null && !aspect.isPrimal()) {
            this.aspectIn = aspect;
            this.process = 39;
            this.func_70296_d();
            this.field_145850_b.markAndNotifyBlock(this.func_174877_v(), this.field_145850_b.func_175726_f(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), 3);
            return 1;
        }
        return 0;
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K) {
            if (!this.gettingPower()) {
                if (this.aspectOut == null && this.aspectIn == null && ++this.count % 5 == 0) {
                    this.drawEssentia();
                }
                if (this.process > 0) {
                    --this.process;
                }
                if (this.aspectOut == null && this.aspectIn != null && this.process == 0) {
                    this.processEssentia();
                }
            }
        }
        else {
            if (this.aspectIn != null && !this.gettingPower() && this.rotationSpeed < 20.0f) {
                this.rotationSpeed += 2.0f;
            }
            if ((this.aspectIn == null || this.gettingPower()) && this.rotationSpeed > 0.0f) {
                this.rotationSpeed -= 0.5f;
            }
            final int pr = (int)this.rotation;
            this.rotation += this.rotationSpeed;
            if (this.rotation % 180.0f <= 20.0f && pr % 180 >= 160 && this.rotationSpeed > 0.0f) {
                this.field_145850_b.func_184134_a(this.func_174877_v().func_177958_n() + 0.5, this.func_174877_v().func_177956_o() + 0.5, this.func_174877_v().func_177952_p() + 0.5, SoundsTC.pump, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            }
        }
    }
    
    void processEssentia() {
        final Aspect[] comps = this.aspectIn.getComponents();
        this.aspectOut = comps[this.field_145850_b.field_73012_v.nextInt(2)];
        this.aspectIn = null;
        this.func_70296_d();
        this.field_145850_b.markAndNotifyBlock(this.func_174877_v(), this.field_145850_b.func_175726_f(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), 3);
    }
    
    void drawEssentia() {
        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.func_174877_v(), EnumFacing.DOWN);
        if (te != null) {
            final IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(EnumFacing.UP)) {
                return;
            }
            Aspect ta = null;
            if (ic.getEssentiaAmount(EnumFacing.UP) > 0 && ic.getSuctionAmount(EnumFacing.UP) < this.getSuctionAmount(EnumFacing.DOWN) && this.getSuctionAmount(EnumFacing.DOWN) >= ic.getMinimumSuction()) {
                ta = ic.getEssentiaType(EnumFacing.UP);
            }
            if (ta != null && !ta.isPrimal() && ic.getSuctionAmount(EnumFacing.UP) < this.getSuctionAmount(EnumFacing.DOWN) && ic.takeEssentia(ta, 1, EnumFacing.UP) == 1) {
                this.aspectIn = ta;
                this.process = 39;
                this.func_70296_d();
                this.field_145850_b.markAndNotifyBlock(this.func_174877_v(), this.field_145850_b.func_175726_f(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), this.field_145850_b.func_180495_p(this.func_174877_v()), 3);
            }
        }
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
    }
    
    public boolean canRenderBreaking() {
        return true;
    }
}
