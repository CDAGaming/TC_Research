package thaumcraft.common.tiles.devices;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.blocks.devices.*;

public class TileHungryChest extends TileEntityChest
{
    public void func_145979_i() {
        if (!this.field_145984_a) {
            this.field_145984_a = true;
        }
    }
    
    public boolean canRenderBreaking() {
        return true;
    }
    
    public void func_174886_c(final EntityPlayer player) {
        if (!player.func_175149_v() && this.func_145838_q() instanceof BlockHungryChest) {
            --this.field_145987_o;
            this.field_145850_b.func_175641_c(this.field_174879_c, this.func_145838_q(), 1, this.field_145987_o);
            this.field_145850_b.func_175685_c(this.field_174879_c, this.func_145838_q(), true);
            this.field_145850_b.func_175685_c(this.field_174879_c.func_177977_b(), this.func_145838_q(), true);
        }
    }
}
