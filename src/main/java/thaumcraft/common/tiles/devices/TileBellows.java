package thaumcraft.common.tiles.devices;

import thaumcraft.common.tiles.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class TileBellows extends TileThaumcraft implements ITickable
{
    public float inflation;
    boolean direction;
    boolean firstrun;
    public int delay;
    
    public TileBellows() {
        this.inflation = 1.0f;
        this.direction = false;
        this.firstrun = true;
        this.delay = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 0.3, this.func_174877_v().func_177956_o() - 0.3, this.func_174877_v().func_177952_p() - 0.3, this.func_174877_v().func_177958_n() + 1.3, this.func_174877_v().func_177956_o() + 1.3, this.func_174877_v().func_177952_p() + 1.3);
    }
    
    public void func_73660_a() {
        if (this.field_145850_b.field_72995_K) {
            if (BlockStateUtils.isEnabled(this.func_145832_p())) {
                if (this.firstrun) {
                    this.inflation = 0.35f + this.field_145850_b.field_73012_v.nextFloat() * 0.55f;
                }
                this.firstrun = false;
                if (this.inflation > 0.35f && !this.direction) {
                    this.inflation -= 0.075f;
                }
                if (this.inflation <= 0.35f && !this.direction) {
                    this.direction = true;
                }
                if (this.inflation < 1.0f && this.direction) {
                    this.inflation += 0.025f;
                }
                if (this.inflation >= 1.0f && this.direction) {
                    this.direction = false;
                    this.field_145850_b.func_184134_a((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), SoundEvents.field_187557_bK, SoundCategory.AMBIENT, 0.01f, 0.5f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.2f, false);
                }
            }
        }
        else if (BlockStateUtils.isEnabled(this.func_145832_p())) {
            ++this.delay;
            if (this.delay >= 2) {
                this.delay = 0;
                final TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(BlockStateUtils.getFacing(this.func_145832_p())));
                if (tile != null && tile instanceof TileEntityFurnace) {
                    final TileEntityFurnace tf = (TileEntityFurnace)tile;
                    final int ct = this.getCooktime(tf);
                    if (ct > 0 && ct < 199) {
                        this.setCooktime(tf, ct + 1);
                    }
                }
            }
        }
    }
    
    public void setCooktime(final TileEntityFurnace ent, final int hit) {
        ent.field_174906_k = hit;
    }
    
    public int getCooktime(final TileEntityFurnace ent) {
        return ent.field_174906_k;
    }
    
    public static int getBellows(final World world, final BlockPos pos, final EnumFacing[] directions) {
        int bellows = 0;
        for (final EnumFacing dir : directions) {
            final TileEntity tile = world.func_175625_s(pos.func_177972_a(dir));
            try {
                if (tile != null && tile instanceof TileBellows && BlockStateUtils.getFacing(tile.func_145832_p()) == dir.func_176734_d() && BlockStateUtils.isEnabled(tile.func_145832_p())) {
                    ++bellows;
                }
            }
            catch (Exception ex) {}
        }
        return bellows;
    }
    
    public boolean canRenderBreaking() {
        return true;
    }
}
