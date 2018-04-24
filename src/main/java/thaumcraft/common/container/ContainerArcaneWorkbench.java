package thaumcraft.common.container;

import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.blocks.world.ore.*;
import thaumcraft.common.container.slot.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.common.items.casters.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.*;
import thaumcraft.api.aspects.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.crafting.*;
import net.minecraft.item.*;

public class ContainerArcaneWorkbench extends Container
{
    private TileArcaneWorkbench tileEntity;
    private InventoryPlayer ip;
    public InventoryCraftResult craftResult;
    public static int[] xx;
    public static int[] yy;
    private int lastVis;
    private long lastCheck;
    
    public ContainerArcaneWorkbench(final InventoryPlayer par1InventoryPlayer, final TileArcaneWorkbench e) {
        this.craftResult = new InventoryCraftResult();
        this.lastVis = -1;
        this.lastCheck = 0L;
        this.tileEntity = e;
        this.tileEntity.inventoryCraft.field_70465_c = this;
        this.ip = par1InventoryPlayer;
        this.func_75146_a((Slot)new SlotCraftingArcaneWorkbench(this.tileEntity, par1InventoryPlayer.field_70458_d, this.tileEntity.inventoryCraft, (IInventory)this.craftResult, 15, 160, 64));
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 3; ++var7) {
                this.func_75146_a(new Slot((IInventory)this.tileEntity.inventoryCraft, var7 + var6 * 3, 40 + var7 * 24, 40 + var6 * 24));
            }
        }
        for (final ShardType st : ShardType.values()) {
            if (st.getMetadata() < 6) {
                this.func_75146_a((Slot)new SlotCrystal(st.getAspect(), (IInventory)this.tileEntity.inventoryCraft, st.getMetadata() + 9, ContainerArcaneWorkbench.xx[st.getMetadata()], ContainerArcaneWorkbench.yy[st.getMetadata()]));
            }
        }
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, var7 + var6 * 9 + 9, 16 + var7 * 18, 151 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.func_75146_a(new Slot((IInventory)par1InventoryPlayer, var6, 16 + var6 * 18, 209));
        }
        this.func_75130_a((IInventory)this.tileEntity.inventoryCraft);
    }
    
    public void func_75132_a(final IContainerListener par1ICrafting) {
        super.func_75132_a(par1ICrafting);
        this.tileEntity.getAura();
        par1ICrafting.func_71112_a((Container)this, 0, this.tileEntity.auraVisServer);
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        final long t = System.currentTimeMillis();
        if (t > this.lastCheck) {
            this.lastCheck = t + 500L;
            this.tileEntity.getAura();
        }
        if (this.lastVis != this.tileEntity.auraVisServer) {
            this.func_75130_a((IInventory)this.tileEntity.inventoryCraft);
        }
        for (int i = 0; i < this.field_75149_d.size(); ++i) {
            final IContainerListener icrafting = this.field_75149_d.get(i);
            if (this.lastVis != this.tileEntity.auraVisServer) {
                icrafting.func_71112_a((Container)this, 0, this.tileEntity.auraVisServer);
            }
        }
        this.lastVis = this.tileEntity.auraVisServer;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75137_b(final int par1, final int par2) {
        if (par1 == 0) {
            this.tileEntity.auraVisClient = par2;
        }
    }
    
    public void func_75130_a(final IInventory par1IInventory) {
        final IArcaneRecipe recipe = ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventoryCraft, this.ip.field_70458_d);
        int vis = 0;
        AspectList crystals = null;
        if (recipe != null) {
            vis = recipe.getVis();
            vis *= (int)(1.0f - CasterManager.getTotalVisDiscount(this.ip.field_70458_d));
            crystals = recipe.getCrystals();
        }
        final boolean hasVis = this.tileEntity.func_145831_w().field_72995_K ? (this.tileEntity.auraVisClient >= vis) : (this.tileEntity.auraVisServer >= vis);
        boolean hasCrystals = true;
        if (crystals != null && crystals.size() > 0) {
            for (final Aspect aspect : crystals.getAspects()) {
                if (InventoryUtils.countStackIn(InventoryUtils.wrapInventory((IInventory)this.tileEntity.inventoryCraft, EnumFacing.UP), ThaumcraftApiHelper.makeCrystal(aspect, crystals.getAmount(aspect)), InventoryUtils.InvFilter.STRICT) < crystals.getAmount(aspect)) {
                    hasCrystals = false;
                    break;
                }
            }
        }
        if (hasVis && hasCrystals) {
            this.func_192389_a(this.tileEntity.func_145831_w(), this.ip.field_70458_d, (InventoryCrafting)this.tileEntity.inventoryCraft, this.craftResult);
        }
    }
    
    public void func_75134_a(final EntityPlayer par1EntityPlayer) {
        super.func_75134_a(par1EntityPlayer);
        if (!this.tileEntity.func_145831_w().field_72995_K) {
            this.tileEntity.inventoryCraft.field_70465_c = new ContainerDummy();
        }
    }
    
    public boolean func_75145_c(final EntityPlayer par1EntityPlayer) {
        return this.tileEntity.func_145831_w().func_175625_s(this.tileEntity.func_174877_v()) == this.tileEntity && par1EntityPlayer.func_174831_c(this.tileEntity.func_174877_v()) <= 64.0;
    }
    
    public ItemStack func_82846_b(final EntityPlayer par1EntityPlayer, final int par1) {
        ItemStack var2 = ItemStack.field_190927_a;
        final Slot var3 = this.field_75151_b.get(par1);
        if (var3 != null && var3.func_75216_d()) {
            final ItemStack var4 = var3.func_75211_c();
            var2 = var4.func_77946_l();
            if (par1 == 0) {
                if (!this.func_75135_a(var4, 16, 52, true)) {
                    return ItemStack.field_190927_a;
                }
                var3.func_75220_a(var4, var2);
            }
            else if (par1 >= 16 && par1 < 52) {
                for (final ShardType st : ShardType.values()) {
                    if (st.getMetadata() < 6) {
                        if (SlotCrystal.isValidCrystal(var4, st.getAspect())) {
                            if (!this.func_75135_a(var4, 10 + st.getMetadata(), 11 + st.getMetadata(), false)) {
                                return ItemStack.field_190927_a;
                            }
                            if (var4.func_190916_E() == 0) {
                                break;
                            }
                        }
                    }
                }
                if (var4.func_190916_E() != 0) {
                    if (par1 >= 16 && par1 < 43) {
                        if (!this.func_75135_a(var4, 43, 52, false)) {
                            return ItemStack.field_190927_a;
                        }
                    }
                    else if (par1 >= 43 && par1 < 52 && !this.func_75135_a(var4, 16, 43, false)) {
                        return ItemStack.field_190927_a;
                    }
                }
            }
            else if (!this.func_75135_a(var4, 16, 52, false)) {
                return ItemStack.field_190927_a;
            }
            if (var4.func_190916_E() == 0) {
                var3.func_75215_d(ItemStack.field_190927_a);
            }
            else {
                var3.func_75218_e();
            }
            if (var4.func_190916_E() == var2.func_190916_E()) {
                return ItemStack.field_190927_a;
            }
            var3.func_190901_a(this.ip.field_70458_d, var4);
        }
        return var2;
    }
    
    public boolean func_94530_a(final ItemStack stack, final Slot slot) {
        return slot.field_75224_c != this.craftResult && super.func_94530_a(stack, slot);
    }
    
    static {
        ContainerArcaneWorkbench.xx = new int[] { 64, 17, 112, 17, 112, 64 };
        ContainerArcaneWorkbench.yy = new int[] { 13, 35, 35, 93, 93, 115 };
    }
}
