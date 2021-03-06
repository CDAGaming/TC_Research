package thaumcraft.common.tiles.essentia;

import thaumcraft.common.tiles.*;
import net.minecraft.util.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.events.*;
import net.minecraft.tileentity.*;

public class TileEssentiaInput extends TileThaumcraft implements IEssentiaTransport, ITickable
{
    int count;
    
    public TileEssentiaInput() {
        this.count = 0;
    }
    
    @Override
    public boolean isConnectable(final EnumFacing face) {
        return face == this.getFacing().func_176734_d();
    }
    
    @Override
    public boolean canInputFrom(final EnumFacing face) {
        return face == this.getFacing().func_176734_d();
    }
    
    @Override
    public boolean canOutputTo(final EnumFacing face) {
        return false;
    }
    
    @Override
    public void setSuction(final Aspect aspect, final int amount) {
    }
    
    @Override
    public int getMinimumSuction() {
        return 0;
    }
    
    @Override
    public Aspect getSuctionType(final EnumFacing loc) {
        return null;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        return 128;
    }
    
    @Override
    public Aspect getEssentiaType(final EnumFacing loc) {
        return null;
    }
    
    @Override
    public int getEssentiaAmount(final EnumFacing loc) {
        return 0;
    }
    
    @Override
    public int takeEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return 0;
    }
    
    @Override
    public int addEssentia(final Aspect aspect, final int amount, final EnumFacing face) {
        return amount;
    }
    
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K && ++this.count % 5 == 0) {
            this.fillJar();
        }
    }
    
    void fillJar() {
        final TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.field_145850_b, this.func_174877_v(), this.getFacing().func_176734_d());
        if (te != null) {
            final IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(this.getFacing())) {
                return;
            }
            if (ic.getEssentiaAmount(this.getFacing()) > 0 && ic.getSuctionAmount(this.getFacing()) < this.getSuctionAmount(this.getFacing().func_176734_d()) && this.getSuctionAmount(this.getFacing().func_176734_d()) >= ic.getMinimumSuction()) {
                final Aspect ta = ic.getEssentiaType(this.getFacing());
                if (EssentiaHandler.addEssentia(this, ta, this.getFacing(), 16, false, 5)) {
                    ic.takeEssentia(ta, 1, this.getFacing());
                }
            }
        }
    }
}
