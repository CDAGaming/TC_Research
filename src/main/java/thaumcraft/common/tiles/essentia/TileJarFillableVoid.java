package thaumcraft.common.tiles.essentia;

import thaumcraft.api.aspects.*;
import thaumcraft.api.aura.*;
import net.minecraft.util.*;

public class TileJarFillableVoid extends TileJarFillable
{
    int count;
    
    public TileJarFillableVoid() {
        this.count = 0;
    }
    
    @Override
    public int addToContainer(final Aspect tt, int am) {
        final boolean up = this.amount < 250;
        if (am == 0) {
            return am;
        }
        if (tt == this.aspect || this.amount == 0) {
            this.aspect = tt;
            this.amount += am;
            am = 0;
            if (this.amount > 250) {
                if (this.field_145850_b.field_73012_v.nextInt(250) == 0) {
                    AuraHelper.polluteAura(this.func_145831_w(), this.func_174877_v(), 1.0f, true);
                }
                this.amount = 250;
            }
        }
        if (up) {
            this.syncTile(false);
            this.func_70296_d();
        }
        return am;
    }
    
    @Override
    public int getMinimumSuction() {
        return (this.aspectFilter != null) ? 48 : 32;
    }
    
    @Override
    public int getSuctionAmount(final EnumFacing loc) {
        if (this.aspectFilter != null && this.amount < 250) {
            return 48;
        }
        return 32;
    }
    
    @Override
    public void func_73660_a() {
        if (!this.field_145850_b.field_72995_K && ++this.count % 5 == 0) {
            this.fillJar();
        }
    }
}
