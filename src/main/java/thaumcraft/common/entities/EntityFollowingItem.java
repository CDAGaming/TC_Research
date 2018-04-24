package thaumcraft.common.entities;

import net.minecraftforge.fml.common.registry.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;

public class EntityFollowingItem extends EntitySpecialItem implements IEntityAdditionalSpawnData
{
    double targetX;
    double targetY;
    double targetZ;
    int type;
    public Entity target;
    int field_70292_b;
    public double gravity;
    
    public EntityFollowingItem(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack) {
        super(par1World);
        this.targetX = 0.0;
        this.targetY = 0.0;
        this.targetZ = 0.0;
        this.type = 3;
        this.target = null;
        this.field_70292_b = 20;
        this.gravity = 0.03999999910593033;
        this.func_70105_a(0.25f, 0.25f);
        this.func_70107_b(par2, par4, par6);
        this.func_92058_a(par8ItemStack);
        this.field_70177_z = (float)(Math.random() * 360.0);
    }
    
    public EntityFollowingItem(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack, final Entity target, final int t) {
        this(par1World, par2, par4, par6, par8ItemStack);
        this.target = target;
        this.targetX = target.field_70165_t;
        this.targetY = target.func_174813_aQ().field_72338_b + target.field_70131_O / 2.0f;
        this.targetZ = target.field_70161_v;
        this.type = t;
        this.field_70145_X = true;
    }
    
    public EntityFollowingItem(final World par1World, final double par2, final double par4, final double par6, final ItemStack par8ItemStack, final double tx, final double ty, final double tz) {
        this(par1World, par2, par4, par6, par8ItemStack);
        this.targetX = tx;
        this.targetY = ty;
        this.targetZ = tz;
    }
    
    public EntityFollowingItem(final World par1World) {
        super(par1World);
        this.targetX = 0.0;
        this.targetY = 0.0;
        this.targetZ = 0.0;
        this.type = 3;
        this.target = null;
        this.field_70292_b = 20;
        this.gravity = 0.03999999910593033;
        this.func_70105_a(0.25f, 0.25f);
    }
    
    @Override
    public void func_70071_h_() {
        if (this.target != null) {
            this.targetX = this.target.field_70165_t;
            this.targetY = this.target.func_174813_aQ().field_72338_b + this.target.field_70131_O / 2.0f;
            this.targetZ = this.target.field_70161_v;
        }
        if (this.targetX != 0.0 || this.targetY != 0.0 || this.targetZ != 0.0) {
            final float xd = (float)(this.targetX - this.field_70165_t);
            final float yd = (float)(this.targetY - this.field_70163_u);
            final float zd = (float)(this.targetZ - this.field_70161_v);
            if (this.field_70292_b > 1) {
                --this.field_70292_b;
            }
            double distance = MathHelper.func_76129_c(xd * xd + yd * yd + zd * zd);
            if (distance > 0.5) {
                distance *= this.field_70292_b;
                this.field_70159_w = xd / distance;
                this.field_70181_x = yd / distance;
                this.field_70179_y = zd / distance;
            }
            else {
                this.field_70159_w *= 0.10000000149011612;
                this.field_70181_x *= 0.10000000149011612;
                this.field_70179_y *= 0.10000000149011612;
                this.targetX = 0.0;
                this.targetY = 0.0;
                this.targetZ = 0.0;
                this.target = null;
                this.field_70145_X = false;
            }
            if (this.field_70170_p.field_72995_K) {
                final float h = (float)((this.func_174813_aQ().field_72337_e - this.func_174813_aQ().field_72338_b) / 2.0) + MathHelper.func_76126_a(this.func_174872_o() / 10.0f + this.field_70290_d) * 0.1f + 0.1f;
                if (this.type != 10) {
                    FXDispatcher.INSTANCE.drawNitorCore((float)this.field_70169_q + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, (float)this.field_70167_r + h + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, (float)this.field_70166_s + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, this.field_70146_Z.nextGaussian() * 0.009999999776482582, this.field_70146_Z.nextGaussian() * 0.009999999776482582, this.field_70146_Z.nextGaussian() * 0.009999999776482582);
                }
                else {
                    FXDispatcher.INSTANCE.crucibleBubble((float)this.field_70169_q + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, (float)this.field_70167_r + h + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, (float)this.field_70166_s + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.125f, 0.33f, 0.33f, 1.0f);
                }
            }
        }
        else {
            this.field_70181_x -= this.gravity;
        }
        super.func_70071_h_();
    }
    
    public void func_70014_b(final NBTTagCompound par1NBTTagCompound) {
        super.func_70014_b(par1NBTTagCompound);
        par1NBTTagCompound.func_74777_a("type", (short)this.type);
    }
    
    public void func_70037_a(final NBTTagCompound par1NBTTagCompound) {
        super.func_70037_a(par1NBTTagCompound);
        this.type = par1NBTTagCompound.func_74765_d("type");
    }
    
    public void writeSpawnData(final ByteBuf data) {
        if (this.target != null) {
            data.writeInt((this.target == null) ? -1 : this.target.func_145782_y());
            data.writeDouble(this.targetX);
            data.writeDouble(this.targetY);
            data.writeDouble(this.targetZ);
            data.writeByte(this.type);
        }
    }
    
    public void readSpawnData(final ByteBuf data) {
        try {
            final int ent = data.readInt();
            if (ent > -1) {
                this.target = this.field_70170_p.func_73045_a(ent);
            }
            this.targetX = data.readDouble();
            this.targetY = data.readDouble();
            this.targetZ = data.readDouble();
            this.type = data.readByte();
        }
        catch (Exception ex) {}
    }
}
