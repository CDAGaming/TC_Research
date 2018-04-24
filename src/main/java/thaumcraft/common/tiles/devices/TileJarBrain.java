package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.essentia.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;

public class TileJarBrain extends TileJar
{
    public float field_40063_b;
    public float field_40061_d;
    public float field_40059_f;
    public float field_40066_q;
    public float rota;
    public float rotb;
    public int xp;
    public int xpMax;
    public int eatDelay;
    long lastsigh;
    
    public TileJarBrain() {
        this.xp = 0;
        this.xpMax = 2000;
        this.eatDelay = 0;
        this.lastsigh = System.currentTimeMillis() + 1500L;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.xp = nbttagcompound.func_74762_e("XP");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74768_a("XP", this.xp);
        return nbttagcompound;
    }
    
    @Override
    public void func_73660_a() {
        Entity entity = null;
        if (this.xp > this.xpMax) {
            this.xp = this.xpMax;
        }
        if (this.xp < this.xpMax) {
            entity = this.getClosestXPOrb();
            if (entity != null && this.eatDelay == 0) {
                final double var3 = (this.field_174879_c.func_177958_n() + 0.5 - entity.field_70165_t) / 25.0;
                final double var4 = (this.field_174879_c.func_177956_o() + 0.5 - entity.field_70163_u) / 25.0;
                final double var5 = (this.field_174879_c.func_177952_p() + 0.5 - entity.field_70161_v) / 25.0;
                final double var6 = Math.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
                double var7 = 1.0 - var6;
                if (var7 > 0.0) {
                    var7 *= var7;
                    final Entity entity2 = entity;
                    entity2.field_70159_w += var3 / var6 * var7 * 0.3;
                    final Entity entity3 = entity;
                    entity3.field_70181_x += var4 / var6 * var7 * 0.5;
                    final Entity entity4 = entity;
                    entity4.field_70179_y += var5 / var6 * var7 * 0.3;
                }
            }
        }
        if (this.field_145850_b.field_72995_K) {
            this.rotb = this.rota;
            if (entity == null) {
                entity = (Entity)this.field_145850_b.func_184137_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, 6.0, false);
                if (entity != null && this.lastsigh < System.currentTimeMillis()) {
                    this.field_145850_b.func_184134_a(this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, SoundsTC.brain, SoundCategory.AMBIENT, 0.15f, 0.8f + this.field_145850_b.field_73012_v.nextFloat() * 0.4f, false);
                    this.lastsigh = System.currentTimeMillis() + 5000L + this.field_145850_b.field_73012_v.nextInt(25000);
                }
            }
            if (entity != null) {
                final double d = entity.field_70165_t - (this.field_174879_c.func_177958_n() + 0.5f);
                final double d2 = entity.field_70161_v - (this.field_174879_c.func_177952_p() + 0.5f);
                this.field_40066_q = (float)Math.atan2(d2, d);
                this.field_40059_f += 0.1f;
                if (this.field_40059_f < 0.5f || TileJarBrain.rand.nextInt(40) == 0) {
                    final float f3 = this.field_40061_d;
                    do {
                        this.field_40061_d += TileJarBrain.rand.nextInt(4) - TileJarBrain.rand.nextInt(4);
                    } while (f3 == this.field_40061_d);
                }
            }
            else {
                this.field_40066_q += 0.01f;
            }
            while (this.rota >= 3.141593f) {
                this.rota -= 6.283185f;
            }
            while (this.rota < -3.141593f) {
                this.rota += 6.283185f;
            }
            while (this.field_40066_q >= 3.141593f) {
                this.field_40066_q -= 6.283185f;
            }
            while (this.field_40066_q < -3.141593f) {
                this.field_40066_q += 6.283185f;
            }
            float f4;
            for (f4 = this.field_40066_q - this.rota; f4 >= 3.141593f; f4 -= 6.283185f) {}
            while (f4 < -3.141593f) {
                f4 += 6.283185f;
            }
            this.rota += f4 * 0.04f;
        }
        if (this.eatDelay > 0) {
            --this.eatDelay;
        }
        else if (this.xp < this.xpMax) {
            final List ents = this.field_145850_b.func_72872_a((Class)EntityXPOrb.class, new AxisAlignedBB(this.field_174879_c.func_177958_n() - 0.1, this.field_174879_c.func_177956_o() - 0.1, this.field_174879_c.func_177952_p() - 0.1, this.field_174879_c.func_177958_n() + 1.1, this.field_174879_c.func_177956_o() + 1.1, this.field_174879_c.func_177952_p() + 1.1));
            if (ents.size() > 0) {
                for (final Object ent : ents) {
                    final EntityXPOrb eo = (EntityXPOrb)ent;
                    this.xp += eo.func_70526_d();
                    eo.func_184185_a(SoundEvents.field_187537_bA, 0.1f, (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.2f + 1.0f);
                    eo.func_70106_y();
                }
                this.syncTile(false);
                this.func_70296_d();
            }
        }
    }
    
    public Entity getClosestXPOrb() {
        double cdist = Double.MAX_VALUE;
        Entity orb = null;
        final List ents = this.field_145850_b.func_72872_a((Class)EntityXPOrb.class, new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1)).func_72314_b(8.0, 8.0, 8.0));
        if (ents.size() > 0) {
            for (final Object ent : ents) {
                final EntityXPOrb eo = (EntityXPOrb)ent;
                final double d = this.func_145835_a(eo.field_70165_t, eo.field_70163_u, eo.field_70161_v);
                if (d < cdist) {
                    orb = (Entity)eo;
                    cdist = d;
                }
            }
        }
        return orb;
    }
}
