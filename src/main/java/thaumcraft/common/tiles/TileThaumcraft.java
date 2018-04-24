package thaumcraft.common.tiles;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.server.*;
import javax.annotation.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class TileThaumcraft extends TileEntity
{
    public void func_145839_a(final NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.readSyncNBT(nbt);
    }
    
    public void readSyncNBT(final NBTTagCompound nbt) {
    }
    
    public NBTTagCompound func_189515_b(final NBTTagCompound nbt) {
        return this.writeSyncNBT(super.func_189515_b(nbt));
    }
    
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        return nbt;
    }
    
    public void syncTile(final boolean rerender) {
        final IBlockState state = this.field_145850_b.func_180495_p(this.field_174879_c);
        this.field_145850_b.func_184138_a(this.field_174879_c, state, state, 2 + (rerender ? 4 : 0));
    }
    
    @Nullable
    public SPacketUpdateTileEntity func_189518_D_() {
        return new SPacketUpdateTileEntity(this.field_174879_c, -9, this.func_189517_E_());
    }
    
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        this.readSyncNBT(pkt.func_148857_g());
    }
    
    public NBTTagCompound func_189517_E_() {
        return this.writeSyncNBT(this.setupNbt());
    }
    
    private NBTTagCompound setupNbt() {
        final NBTTagCompound nbt = super.func_189515_b(new NBTTagCompound());
        nbt.func_82580_o("ForgeData");
        nbt.func_82580_o("ForgeCaps");
        return nbt;
    }
    
    public boolean shouldRefresh(final World world, final BlockPos pos, final IBlockState oldState, final IBlockState newState) {
        return oldState.func_177230_c() != newState.func_177230_c();
    }
    
    public EnumFacing getFacing() {
        try {
            return EnumFacing.func_82600_a(this.func_145832_p() & 0x7);
        }
        catch (Exception ex) {
            return EnumFacing.UP;
        }
    }
    
    public boolean gettingPower() {
        return this.field_145850_b.func_175640_z(this.func_174877_v());
    }
}
