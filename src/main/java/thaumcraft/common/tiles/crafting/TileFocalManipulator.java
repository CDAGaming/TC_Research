package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import thaumcraft.api.blocks.*;
import thaumcraft.common.world.aura.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.client.fx.*;
import thaumcraft.api.casters.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;

public class TileFocalManipulator extends TileThaumcraftInventory
{
    public float vis;
    public HashMap<Integer, FocusElementNode> data;
    public String focusName;
    int ticks;
    public boolean doGather;
    public float visCost;
    public int xpCost;
    private AspectList crystals;
    public AspectList crystalsSync;
    public boolean doGuiReset;
    
    public TileFocalManipulator() {
        super(1);
        this.vis = 0.0f;
        this.data = new HashMap<Integer, FocusElementNode>();
        this.focusName = "";
        this.ticks = 0;
        this.visCost = 0.0f;
        this.xpCost = 0;
        this.crystals = new AspectList();
        this.crystalsSync = new AspectList();
        this.doGuiReset = false;
        this.syncedSlots = new int[] { 0 };
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbt) {
        super.readSyncNBT(nbt);
        this.vis = nbt.func_74760_g("vis");
        this.focusName = nbt.func_74779_i("focusName");
        (this.crystalsSync = new AspectList()).readFromNBT(nbt, "crystals");
        final NBTTagList nodelist = nbt.func_150295_c("nodes", 10);
        this.data.clear();
        for (int x = 0; x < nodelist.func_74745_c(); ++x) {
            final NBTTagCompound nodenbt = nodelist.func_150305_b(x);
            final FocusElementNode node = new FocusElementNode();
            node.deserialize(nodenbt);
            this.data.put(node.id, node);
        }
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbt) {
        super.writeSyncNBT(nbt);
        nbt.func_74776_a("vis", this.vis);
        nbt.func_74778_a("focusName", this.focusName);
        this.crystalsSync.writeToNBT(nbt, "crystals");
        final NBTTagList nodelist = new NBTTagList();
        for (final FocusElementNode node : this.data.values()) {
            nodelist.func_74742_a((NBTBase)node.serialize());
        }
        nbt.func_74782_a("nodes", (NBTBase)nodelist);
        return nbt;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1));
    }
    
    @Override
    public void func_70299_a(final int par1, final ItemStack stack) {
        final ItemStack prev = this.func_70301_a(par1);
        super.func_70299_a(par1, stack);
        if (this.field_145850_b.field_72995_K) {
            if (!ItemStack.func_77989_b(stack, prev)) {
                this.data.clear();
                this.doGuiReset = true;
            }
        }
        else {
            this.vis = 0.0f;
        }
    }
    
    public float spendAura(final float vis) {
        if (this.field_145850_b.func_180495_p(this.func_174877_v().func_177984_a()).func_177230_c() == BlocksTC.arcaneWorkbenchCharger) {
            float q = vis;
            float z = vis / 9.0f;
        Label_0109:
            for (int xx = -1; xx <= 1; ++xx) {
                for (int zz = -1; zz <= 1; ++zz) {
                    if (z > q) {
                        z = q;
                    }
                    q -= AuraHandler.drainVis(this.func_145831_w(), this.func_174877_v().func_177982_a(xx * 16, 0, zz * 16), z, false);
                    if (q <= 0.0f) {
                        break Label_0109;
                    }
                }
            }
            return vis - q;
        }
        return AuraHandler.drainVis(this.func_145831_w(), this.func_174877_v(), vis, false);
    }
    
    @Override
    public void func_73660_a() {
        super.func_73660_a();
        boolean complete = false;
        ++this.ticks;
        if (!this.field_145850_b.field_72995_K) {
            if (this.ticks % 20 == 0) {
                if (this.vis > 0.0f && (this.func_70301_a(0) == null || this.func_70301_a(0).func_190926_b() || !(this.func_70301_a(0).func_77973_b() instanceof ItemFocus))) {
                    complete = true;
                    this.vis = 0.0f;
                    this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.wandfail, SoundCategory.BLOCKS, 0.33f, 1.0f);
                }
                if (!complete && this.vis > 0.0f) {
                    final float amt = this.spendAura(Math.min(20.0f, this.vis));
                    if (amt > 0.0f) {
                        this.field_145850_b.func_175641_c(this.field_174879_c, this.func_145838_q(), 5, 1);
                        this.vis -= amt;
                        this.syncTile(false);
                        this.func_70296_d();
                    }
                    if (this.vis <= 0.0f && this.func_70301_a(0) != null && this.func_70301_a(0).func_77973_b() instanceof ItemFocus) {
                        complete = true;
                        this.endCraft();
                    }
                }
            }
        }
        else if (this.vis > 0.0f) {
            FXDispatcher.INSTANCE.drawGenericParticles(this.field_174879_c.func_177958_n() + 0.5 + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.3f, this.field_174879_c.func_177956_o() + 1.4 + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.3f, this.field_174879_c.func_177952_p() + 0.5 + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.3f, 0.0, 0.0, 0.0, 0.5f + this.field_145850_b.field_73012_v.nextFloat() * 0.4f, 1.0f - this.field_145850_b.field_73012_v.nextFloat() * 0.4f, 1.0f - this.field_145850_b.field_73012_v.nextFloat() * 0.4f, 0.8f, false, 448, 9, 1, 6 + this.field_145850_b.field_73012_v.nextInt(5), 0, 0.3f + this.field_145850_b.field_73012_v.nextFloat() * 0.3f, 0.0f, 0);
        }
        if (complete) {
            this.vis = 0.0f;
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    private FocusPackage generateFocus() {
        if (this.data != null) {
            final FocusPackage core = new FocusPackage();
            int totalComplexity = 0;
            final HashMap<String, Integer> compCount = new HashMap<String, Integer>();
            for (final FocusElementNode node : this.data.values()) {
                if (node.node != null) {
                    int a = 0;
                    if (compCount.containsKey(node.node.getKey())) {
                        a = compCount.get(node.node.getKey());
                    }
                    ++a;
                    node.complexityMultiplier = 0.5f * (a + 1);
                    compCount.put(node.node.getKey(), a);
                    totalComplexity += (int)(node.node.getComplexity() * node.complexityMultiplier);
                }
            }
            core.setComplexity(totalComplexity);
            final FocusElementNode root = this.data.get(0);
            this.traverseChildren(core, root);
            return core;
        }
        return null;
    }
    
    private void traverseChildren(final FocusPackage currentPackage, final FocusElementNode currentNode) {
        currentPackage.addNode(currentNode.node);
        if (currentNode.children == null || currentNode.children.length == 0) {
            return;
        }
        if (currentNode.children.length == 1) {
            this.traverseChildren(currentPackage, this.data.get(currentNode.children[0]));
        }
        else {
            final FocusModSplit splitNode = (FocusModSplit)currentNode.node;
            splitNode.getSplitPackages().clear();
            for (final int c : currentNode.children) {
                final FocusPackage splitPackage = new FocusPackage();
                this.traverseChildren(splitPackage, this.data.get(c));
                splitNode.getSplitPackages().add(splitPackage);
            }
        }
    }
    
    public void endCraft() {
        this.vis = 0.0f;
        if (this.func_70301_a(0) != null && this.func_70301_a(0).func_77973_b() instanceof ItemFocus) {
            final FocusPackage core = this.generateFocus();
            if (core != null) {
                this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.wand, SoundCategory.BLOCKS, 1.0f, 1.0f);
                final ItemStack focus = this.func_70301_a(0);
                if (focus.func_77978_p() != null) {
                    focus.func_77978_p().func_82580_o("color");
                }
                focus.func_151001_c(this.focusName);
                ItemFocus.setPackage(focus, core);
                this.func_70299_a(0, focus);
                this.crystalsSync = new AspectList();
                this.syncTile(false);
                this.func_70296_d();
            }
        }
    }
    
    public boolean startCraft(final int id, final EntityPlayer p) {
        if (this.data == null || this.vis > 0.0f || this.func_70301_a(0) == null || this.func_70301_a(0).func_190926_b() || !(this.func_70301_a(0).func_77973_b() instanceof ItemFocus)) {
            return false;
        }
        final int maxComplexity = ((ItemFocus)this.func_70301_a(0).func_77973_b()).getMaxComplexity();
        int totalComplexity = 0;
        this.crystals = new AspectList();
        final HashMap<String, Integer> compCount = new HashMap<String, Integer>();
        for (final FocusElementNode node : this.data.values()) {
            if (node.node == null) {
                return false;
            }
            if (!ThaumcraftCapabilities.knowsResearchStrict(p, node.node.getResearch())) {
                return false;
            }
            int a = 0;
            if (compCount.containsKey(node.node.getKey())) {
                a = compCount.get(node.node.getKey());
            }
            ++a;
            node.complexityMultiplier = 0.5f * (a + 1);
            compCount.put(node.node.getKey(), a);
            totalComplexity += (int)(node.node.getComplexity() * node.complexityMultiplier);
            if (node.node.getAspect() == null) {
                continue;
            }
            this.crystals.add(node.node.getAspect(), 1);
        }
        this.vis = totalComplexity * 10 + maxComplexity / 5;
        this.xpCost = (int)Math.min(1L, Math.round(Math.sqrt(totalComplexity)));
        if (!p.field_71075_bZ.field_75098_d && p.field_71068_ca < this.xpCost) {
            return false;
        }
        if (!p.field_71075_bZ.field_75098_d) {
            p.func_82242_a(-this.xpCost);
        }
        if (this.crystals.getAspects().length > 0) {
            final ItemStack[] components = new ItemStack[this.crystals.getAspects().length];
            int r = 0;
            for (final Aspect as : this.crystals.getAspects()) {
                components[r] = ThaumcraftApiHelper.makeCrystal(as, this.crystals.getAmount(as));
                ++r;
            }
            if (components.length >= 0) {
                for (int a = 0; a < components.length; ++a) {
                    if (!InventoryUtils.isPlayerCarryingAmount(p, components[a], false)) {
                        return false;
                    }
                }
                for (int a = 0; a < components.length; ++a) {
                    InventoryUtils.consumePlayerItem(p, components[a], true, false);
                }
                this.crystalsSync = this.crystals.copy();
            }
            this.func_70296_d();
            this.syncTile(false);
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.craftstart, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean func_94041_b(final int par1, final ItemStack stack) {
        return stack.func_77973_b() instanceof ItemFocus;
    }
    
    public boolean func_145842_c(final int i, final int j) {
        if (i == 1) {
            this.doGuiReset = true;
        }
        if (i == 5) {
            if (this.field_145850_b.field_72995_K) {
                FXDispatcher.INSTANCE.visSparkle(this.field_174879_c.func_177958_n() + this.func_145831_w().field_73012_v.nextInt(3) - this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177956_o() + this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177952_p() + this.func_145831_w().field_73012_v.nextInt(3) - this.func_145831_w().field_73012_v.nextInt(3), this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o() + 1, this.field_174879_c.func_177952_p(), j);
            }
            return true;
        }
        return super.func_145842_c(i, j);
    }
}
