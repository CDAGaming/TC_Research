package thaumcraft.common.tiles.misc;

import thaumcraft.common.tiles.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import thaumcraft.common.blocks.basic.*;

public class TileBanner extends TileThaumcraft
{
    private byte facing;
    private Aspect aspect;
    private boolean onWall;
    
    public TileBanner() {
        this.facing = 0;
        this.aspect = null;
        this.onWall = false;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)(this.func_174877_v().func_177956_o() - 1), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 2), (double)(this.func_174877_v().func_177952_p() + 1));
    }
    
    public byte getBannerFacing() {
        return this.facing;
    }
    
    public void setBannerFacing(final byte face) {
        this.facing = face;
        this.func_70296_d();
    }
    
    public boolean getWall() {
        return this.onWall;
    }
    
    public void setWall(final boolean b) {
        this.onWall = b;
        this.func_70296_d();
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbttagcompound) {
        this.facing = nbttagcompound.func_74771_c("facing");
        final String as = nbttagcompound.func_74779_i("aspect");
        if (as != null && as.length() > 0) {
            this.setAspect(Aspect.getAspect(as));
        }
        else {
            this.aspect = null;
        }
        this.onWall = nbttagcompound.func_74767_n("wall");
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74774_a("facing", this.facing);
        nbttagcompound.func_74778_a("aspect", (this.getAspect() == null) ? "" : this.getAspect().getTag());
        nbttagcompound.func_74757_a("wall", this.onWall);
        return nbttagcompound;
    }
    
    public Aspect getAspect() {
        return this.aspect;
    }
    
    public void setAspect(final Aspect aspect) {
        this.aspect = aspect;
    }
    
    public int getColor() {
        return (this.func_145838_q() == null || ((BlockBannerTC)this.func_145838_q()).dye == null) ? -1 : ((BlockBannerTC)this.func_145838_q()).dye.func_193350_e();
    }
}
