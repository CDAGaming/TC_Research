package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import thaumcraft.common.container.*;
import thaumcraft.api.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.world.aura.*;

public class TileArcaneWorkbench extends TileThaumcraft
{
    public InventoryArcaneWorkbench inventoryCraft;
    public int auraVisServer;
    public int auraVisClient;
    
    public TileArcaneWorkbench() {
        this.auraVisServer = 0;
        this.auraVisClient = 0;
        this.inventoryCraft = new InventoryArcaneWorkbench(this, new ContainerDummy());
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.inventoryCraft.func_70302_i_(), (Object)ItemStack.field_190927_a);
        ItemStackHelper.func_191283_b(nbtCompound, (NonNullList)stacks);
        for (int a = 0; a < stacks.size(); ++a) {
            this.inventoryCraft.func_70299_a(a, (ItemStack)stacks.get(a));
        }
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbtCompound) {
        super.func_189515_b(nbtCompound);
        final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(this.inventoryCraft.func_70302_i_(), (Object)ItemStack.field_190927_a);
        for (int a = 0; a < stacks.size(); ++a) {
            stacks.set(a, (Object)this.inventoryCraft.func_70301_a(a));
        }
        ItemStackHelper.func_191282_a(nbtCompound, (NonNullList)stacks);
        return nbtCompound;
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbtCompound) {
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbtCompound) {
        return nbtCompound;
    }
    
    public void getAura() {
        if (!this.func_145831_w().field_72995_K) {
            int t = 0;
            if (this.field_145850_b.func_180495_p(this.func_174877_v().func_177984_a()).func_177230_c() == BlocksTC.arcaneWorkbenchCharger) {
                final int sx = this.field_174879_c.func_177958_n() >> 4;
                final int sz = this.field_174879_c.func_177952_p() >> 4;
                for (int xx = -1; xx <= 1; ++xx) {
                    for (int zz = -1; zz <= 1; ++zz) {
                        final AuraChunk ac = AuraHandler.getAuraChunk(this.field_145850_b.field_73011_w.getDimension(), sx + xx, sz + zz);
                        if (ac != null) {
                            t += (int)ac.getVis();
                        }
                    }
                }
            }
            else {
                t = (int)AuraHandler.getVis(this.func_145831_w(), this.func_174877_v());
            }
            this.auraVisServer = t;
        }
    }
    
    public void spendAura(final int vis) {
        if (!this.func_145831_w().field_72995_K) {
            if (this.field_145850_b.func_180495_p(this.func_174877_v().func_177984_a()).func_177230_c() == BlocksTC.arcaneWorkbenchCharger) {
                int q = vis;
                int z = Math.max(1, vis / 9);
                int attempts = 0;
            Label_0144:
                while (q > 0) {
                    ++attempts;
                    for (int xx = -1; xx <= 1; ++xx) {
                        for (int zz = -1; zz <= 1; ++zz) {
                            if (z > q) {
                                z = q;
                            }
                            q -= (int)AuraHandler.drainVis(this.func_145831_w(), this.func_174877_v().func_177982_a(xx * 16, 0, zz * 16), z, false);
                            if (q <= 0) {
                                break Label_0144;
                            }
                            if (attempts > 1000) {
                                break Label_0144;
                            }
                        }
                    }
                }
            }
            else {
                AuraHandler.drainVis(this.func_145831_w(), this.func_174877_v(), vis, false);
            }
        }
    }
}
