package thaumcraft.common.tiles.crafting;

import thaumcraft.common.tiles.*;
import thaumcraft.api.casters.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import thaumcraft.common.blocks.devices.*;
import thaumcraft.common.blocks.basic.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.crafting.*;
import thaumcraft.common.lib.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.aspects.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.common.lib.events.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.network.fx.*;
import net.minecraft.potion.*;
import thaumcraft.api.potions.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.blocks.*;
import thaumcraft.api.aura.*;
import net.minecraft.enchantment.*;
import net.minecraftforge.fml.common.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.init.*;
import thaumcraft.api.crafting.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;
import net.minecraft.item.*;

public class TileInfusionMatrix extends TileThaumcraft implements IInteractWithCaster, IAspectContainer, ITickable
{
    private ArrayList<BlockPos> pedestals;
    private int dangerCount;
    public boolean active;
    public boolean crafting;
    public boolean checkSurroundings;
    public int symmetryInstability;
    public float costMult;
    public int instability;
    private int cycleTime;
    private AspectList recipeEssentia;
    private ArrayList<ItemStack> recipeIngredients;
    private Object recipeOutput;
    private String recipePlayer;
    private String recipeOutputLabel;
    private ItemStack recipeInput;
    private int recipeInstability;
    private int recipeXP;
    private int recipeType;
    public HashMap<String, SourceFX> sourceFX;
    public int count;
    public int craftCount;
    public float startUp;
    private int countDelay;
    ArrayList<ItemStack> ingredients;
    int itemCount;
    
    public TileInfusionMatrix() {
        this.pedestals = new ArrayList<BlockPos>();
        this.dangerCount = 0;
        this.active = false;
        this.crafting = false;
        this.checkSurroundings = true;
        this.symmetryInstability = 0;
        this.costMult = 0.0f;
        this.instability = 0;
        this.cycleTime = 20;
        this.recipeEssentia = new AspectList();
        this.recipeIngredients = null;
        this.recipeOutput = null;
        this.recipePlayer = null;
        this.recipeOutputLabel = null;
        this.recipeInput = null;
        this.recipeInstability = 0;
        this.recipeXP = 0;
        this.recipeType = 0;
        this.sourceFX = new HashMap<String, SourceFX>();
        this.count = 0;
        this.craftCount = 0;
        this.countDelay = this.cycleTime / 2;
        this.ingredients = new ArrayList<ItemStack>();
        this.itemCount = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.func_174877_v().func_177958_n() - 0.1, this.func_174877_v().func_177956_o() - 0.1, this.func_174877_v().func_177952_p() - 0.1, this.func_174877_v().func_177958_n() + 1.1, this.func_174877_v().func_177956_o() + 1.1, this.func_174877_v().func_177952_p() + 1.1);
    }
    
    @Override
    public void readSyncNBT(final NBTTagCompound nbtCompound) {
        this.active = nbtCompound.func_74767_n("active");
        this.crafting = nbtCompound.func_74767_n("crafting");
        this.instability = nbtCompound.func_74765_d("instability");
        this.recipeEssentia.readFromNBT(nbtCompound);
    }
    
    @Override
    public NBTTagCompound writeSyncNBT(final NBTTagCompound nbtCompound) {
        nbtCompound.func_74757_a("active", this.active);
        nbtCompound.func_74757_a("crafting", this.crafting);
        nbtCompound.func_74777_a("instability", (short)this.instability);
        this.recipeEssentia.writeToNBT(nbtCompound);
        return nbtCompound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound nbtCompound) {
        super.func_145839_a(nbtCompound);
        final NBTTagList nbttaglist = nbtCompound.func_150295_c("recipein", 10);
        this.recipeIngredients = new ArrayList<ItemStack>();
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
            this.recipeIngredients.add(new ItemStack(nbttagcompound1));
        }
        final String rot = nbtCompound.func_74779_i("rotype");
        if (rot != null && rot.equals("@")) {
            this.recipeOutput = new ItemStack(nbtCompound.func_74775_l("recipeout"));
        }
        else if (rot != null) {
            this.recipeOutputLabel = rot;
            this.recipeOutput = nbtCompound.func_74781_a("recipeout");
        }
        this.recipeInput = new ItemStack(nbtCompound.func_74775_l("recipeinput"));
        this.recipeInstability = nbtCompound.func_74762_e("recipeinst");
        this.recipeType = nbtCompound.func_74762_e("recipetype");
        this.recipeXP = nbtCompound.func_74762_e("recipexp");
        this.recipePlayer = nbtCompound.func_74779_i("recipeplayer");
        if (this.recipePlayer.isEmpty()) {
            this.recipePlayer = null;
        }
    }
    
    @Override
    public NBTTagCompound func_189515_b(final NBTTagCompound nbtCompound) {
        super.func_189515_b(nbtCompound);
        if (this.recipeIngredients != null && this.recipeIngredients.size() > 0) {
            final NBTTagList nbttaglist = new NBTTagList();
            for (final ItemStack stack : this.recipeIngredients) {
                if (!stack.func_190926_b()) {
                    final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.func_74774_a("item", (byte)this.count);
                    stack.func_77955_b(nbttagcompound1);
                    nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
                    ++this.count;
                }
            }
            nbtCompound.func_74782_a("recipein", (NBTBase)nbttaglist);
        }
        if (this.recipeOutput != null && this.recipeOutput instanceof ItemStack) {
            nbtCompound.func_74778_a("rotype", "@");
        }
        if (this.recipeOutput != null && this.recipeOutput instanceof NBTBase) {
            nbtCompound.func_74778_a("rotype", this.recipeOutputLabel);
        }
        if (this.recipeOutput != null && this.recipeOutput instanceof ItemStack) {
            nbtCompound.func_74782_a("recipeout", (NBTBase)((ItemStack)this.recipeOutput).func_77955_b(new NBTTagCompound()));
        }
        if (this.recipeOutput != null && this.recipeOutput instanceof NBTBase) {
            nbtCompound.func_74782_a("recipeout", (NBTBase)this.recipeOutput);
        }
        if (this.recipeInput != null) {
            nbtCompound.func_74782_a("recipeinput", (NBTBase)this.recipeInput.func_77955_b(new NBTTagCompound()));
        }
        nbtCompound.func_74768_a("recipeinst", this.recipeInstability);
        nbtCompound.func_74768_a("recipetype", this.recipeType);
        nbtCompound.func_74768_a("recipexp", this.recipeXP);
        if (this.recipePlayer == null) {
            nbtCompound.func_74778_a("recipeplayer", "");
        }
        else {
            nbtCompound.func_74778_a("recipeplayer", this.recipePlayer);
        }
        return nbtCompound;
    }
    
    public void func_73660_a() {
        ++this.count;
        if (this.checkSurroundings) {
            this.checkSurroundings = false;
            this.getSurroundings();
        }
        if (this.field_145850_b.field_72995_K) {
            this.doEffects();
        }
        else {
            if (this.count % (this.crafting ? 20 : 100) == 0 && !this.validLocation()) {
                this.active = false;
                this.func_70296_d();
                this.syncTile(false);
                return;
            }
            if (this.active && this.crafting && this.count % this.countDelay == 0) {
                this.craftCycle();
                this.func_70296_d();
            }
        }
    }
    
    public boolean validLocation() {
        return this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(0, -2, 0)).func_177230_c() instanceof BlockPedestal && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, 1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, 1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, -1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, -1)).func_177230_c() instanceof BlockPillar;
    }
    
    public void craftingStart(final EntityPlayer player) {
        if (!this.validLocation()) {
            this.active = false;
            this.func_70296_d();
            this.syncTile(false);
            return;
        }
        this.getSurroundings();
        TileEntity te = null;
        this.recipeInput = null;
        te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177979_c(2));
        if (te != null && te instanceof TilePedestal) {
            final TilePedestal ped = (TilePedestal)te;
            if (!ped.func_70301_a(0).func_190926_b()) {
                this.recipeInput = ped.func_70301_a(0).func_77946_l();
            }
        }
        if (this.recipeInput == null || this.recipeInput.func_190926_b()) {
            return;
        }
        final ArrayList<ItemStack> components = new ArrayList<ItemStack>();
        for (final BlockPos cc : this.pedestals) {
            te = this.field_145850_b.func_175625_s(cc);
            if (te != null && te instanceof TilePedestal) {
                final TilePedestal ped2 = (TilePedestal)te;
                if (ped2.func_70301_a(0).func_190926_b()) {
                    continue;
                }
                components.add(ped2.func_70301_a(0).func_77946_l());
            }
        }
        if (components.size() == 0) {
            return;
        }
        final InfusionRecipe recipe = ThaumcraftCraftingManager.findMatchingInfusionRecipe(components, this.recipeInput, player);
        if (this.costMult < 0.5) {
            this.costMult = 0.5f;
        }
        if (recipe != null) {
            this.recipeType = 0;
            this.recipeIngredients = components;
            if (recipe.getRecipeOutput(player, this.recipeInput, components) instanceof Object[]) {
                final Object[] obj = (Object[])recipe.getRecipeOutput(player, this.recipeInput, components);
                this.recipeOutputLabel = (String)obj[0];
                this.recipeOutput = obj[1];
            }
            else {
                this.recipeOutput = recipe.getRecipeOutput(player, this.recipeInput, components);
            }
            this.recipeInstability = recipe.getInstability(player, this.recipeInput, components);
            final AspectList al = recipe.getAspects(player, this.recipeInput, components);
            final AspectList al2 = new AspectList();
            for (final Aspect as : al.getAspects()) {
                if ((int)(al.getAmount(as) * this.costMult) > 0) {
                    al2.add(as, (int)(al.getAmount(as) * this.costMult));
                }
            }
            this.recipeEssentia = al2;
            this.recipePlayer = player.func_70005_c_();
            this.instability = this.symmetryInstability + this.recipeInstability;
            this.crafting = true;
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.craftstart, SoundCategory.BLOCKS, 0.5f, 1.0f);
            this.syncTile(false);
            this.func_70296_d();
        }
    }
    
    public void craftCycle() {
        boolean valid = false;
        TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177979_c(2));
        if (te != null && te instanceof TilePedestal) {
            final TilePedestal ped = (TilePedestal)te;
            if (!ped.func_70301_a(0).func_190926_b()) {
                final ItemStack i2 = ped.func_70301_a(0).func_77946_l();
                if (this.recipeInput.func_77952_i() == 32767) {
                    i2.func_77964_b(32767);
                }
                if (ThaumcraftApiHelper.areItemStacksEqualForCrafting(i2, this.recipeInput)) {
                    valid = true;
                }
            }
        }
        if (!valid || (this.instability > 0 && this.field_145850_b.field_73012_v.nextInt(500) <= this.instability)) {
            switch (this.field_145850_b.field_73012_v.nextInt(21)) {
                case 0:
                case 2:
                case 10:
                case 13: {
                    this.inEvEjectItem(0);
                    break;
                }
                case 6:
                case 17: {
                    this.inEvEjectItem(1);
                    break;
                }
                case 1:
                case 11: {
                    this.inEvEjectItem(2);
                    break;
                }
                case 3:
                case 8:
                case 14: {
                    this.inEvZap(false);
                    break;
                }
                case 5:
                case 16: {
                    this.inEvHarm(false);
                    break;
                }
                case 12: {
                    this.inEvZap(true);
                    break;
                }
                case 19: {
                    this.inEvEjectItem(3);
                    break;
                }
                case 7: {
                    this.inEvEjectItem(4);
                    break;
                }
                case 4:
                case 15: {
                    this.inEvEjectItem(5);
                    break;
                }
                case 18: {
                    this.inEvHarm(true);
                    break;
                }
                case 9: {
                    this.field_145850_b.func_72876_a((Entity)null, this.field_174879_c.func_177958_n() + 0.5, this.field_174879_c.func_177956_o() + 0.5, this.field_174879_c.func_177952_p() + 0.5, 1.5f + this.field_145850_b.field_73012_v.nextFloat(), false);
                    break;
                }
                case 20: {
                    this.inEvWarp();
                    break;
                }
            }
            if (valid) {
                return;
            }
        }
        if (!valid) {
            this.instability = 0;
            this.crafting = false;
            this.recipeEssentia = new AspectList();
            this.syncTile(false);
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.craftfail, SoundCategory.BLOCKS, 1.0f, 0.6f);
            this.func_70296_d();
            return;
        }
        if (this.recipeType == 1 && this.recipeXP > 0) {
            final List<EntityPlayer> targets = (List<EntityPlayer>)this.field_145850_b.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1)).func_72314_b(10.0, 10.0, 10.0));
            if (targets != null && targets.size() > 0) {
                for (final EntityPlayer target : targets) {
                    if (target.field_71075_bZ.field_75098_d || target.field_71068_ca > 0) {
                        if (!target.field_71075_bZ.field_75098_d) {
                            target.func_82242_a(-1);
                        }
                        --this.recipeXP;
                        target.func_70097_a(DamageSource.field_76376_m, (float)this.field_145850_b.field_73012_v.nextInt(2));
                        PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXInfusionSource(this.field_174879_c, (byte)0, (byte)0, (byte)0, target.func_145782_y()), new NetworkRegistry.TargetPoint(this.func_145831_w().field_73011_w.getDimension(), (double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), 32.0));
                        target.func_184185_a(SoundEvents.field_187659_cY, 1.0f, 2.0f + this.field_145850_b.field_73012_v.nextFloat() * 0.4f);
                        this.countDelay = this.cycleTime;
                        return;
                    }
                }
                final Aspect[] ingEss = this.recipeEssentia.getAspects();
                if (ingEss != null && ingEss.length > 0 && this.field_145850_b.field_73012_v.nextInt(3) == 0) {
                    final Aspect as = ingEss[this.field_145850_b.field_73012_v.nextInt(ingEss.length)];
                    this.recipeEssentia.add(as, 1);
                    if (this.field_145850_b.field_73012_v.nextInt(50 - this.recipeInstability * 2) == 0) {
                        ++this.instability;
                    }
                    if (this.instability > 25) {
                        this.instability = 25;
                    }
                    this.syncTile(false);
                    this.func_70296_d();
                }
            }
            return;
        }
        if (this.recipeType == 1 && this.recipeXP == 0) {
            this.countDelay = this.cycleTime / 2;
        }
        if (this.countDelay < 1) {
            this.countDelay = 1;
        }
        if (this.recipeEssentia.visSize() > 0) {
            for (final Aspect aspect : this.recipeEssentia.getAspects()) {
                final int na = this.recipeEssentia.getAmount(aspect);
                if (na > 0) {
                    if (EssentiaHandler.drainEssentia(this, aspect, null, 12, (na > 1) ? this.countDelay : 0)) {
                        this.recipeEssentia.reduce(aspect, 1);
                        this.syncTile(false);
                        this.func_70296_d();
                        return;
                    }
                    if (this.field_145850_b.field_73012_v.nextInt(100 - this.recipeInstability * 3) == 0) {
                        ++this.instability;
                    }
                    if (this.instability > 25) {
                        this.instability = 25;
                    }
                    this.syncTile(false);
                    this.func_70296_d();
                }
            }
            this.checkSurroundings = true;
            return;
        }
        if (this.recipeIngredients.size() > 0) {
            for (int a = 0; a < this.recipeIngredients.size(); ++a) {
                for (final BlockPos cc : this.pedestals) {
                    te = this.field_145850_b.func_175625_s(cc);
                    if (te != null && te instanceof TilePedestal && ((TilePedestal)te).func_70301_a(0) != null && ThaumcraftApiHelper.areItemStacksEqualForCrafting(((TilePedestal)te).func_70301_a(0), this.recipeIngredients.get(a))) {
                        if (this.itemCount == 0) {
                            this.itemCount = 5;
                            PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXInfusionSource(this.field_174879_c, (byte)(this.field_174879_c.func_177958_n() - cc.func_177958_n()), (byte)(this.field_174879_c.func_177956_o() - cc.func_177956_o()), (byte)(this.field_174879_c.func_177952_p() - cc.func_177952_p()), 0), new NetworkRegistry.TargetPoint(this.func_145831_w().field_73011_w.getDimension(), (double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), 32.0));
                        }
                        else if (this.itemCount-- <= 1) {
                            final ItemStack is = ((TilePedestal)te).func_70301_a(0).func_77973_b().getContainerItem(((TilePedestal)te).func_70301_a(0));
                            ((TilePedestal)te).func_70299_a(0, (is == null) ? null : is.func_77946_l());
                            ((TilePedestal)te).func_70296_d();
                            this.recipeIngredients.remove(a);
                            this.func_70296_d();
                        }
                        return;
                    }
                }
                final Aspect[] ingEss = this.recipeEssentia.getAspects();
                if (ingEss != null && ingEss.length > 0 && this.field_145850_b.field_73012_v.nextInt(1 + a) == 0) {
                    final Aspect as = ingEss[this.field_145850_b.field_73012_v.nextInt(ingEss.length)];
                    this.recipeEssentia.add(as, 1);
                    if (this.field_145850_b.field_73012_v.nextInt(50 - this.recipeInstability * 2) == 0) {
                        ++this.instability;
                    }
                    if (this.instability > 25) {
                        this.instability = 25;
                    }
                    this.syncTile(false);
                    this.func_70296_d();
                }
            }
            return;
        }
        this.instability = 0;
        this.crafting = false;
        this.craftingFinish(this.recipeOutput, this.recipeOutputLabel);
        this.recipeOutput = null;
        this.syncTile(false);
        this.func_70296_d();
    }
    
    private void inEvZap(final boolean all) {
        final List<EntityLivingBase> targets = (List<EntityLivingBase>)this.field_145850_b.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1)).func_72314_b(10.0, 10.0, 10.0));
        if (targets != null && targets.size() > 0) {
            for (final EntityLivingBase target : targets) {
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockArc(this.field_174879_c, (Entity)target, 0.3f - this.field_145850_b.field_73012_v.nextFloat() * 0.1f, 0.0f, 0.3f - this.field_145850_b.field_73012_v.nextFloat() * 0.1f), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.getDimension(), (double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), 32.0));
                target.func_70097_a(DamageSource.field_76376_m, (float)(4 + this.field_145850_b.field_73012_v.nextInt(4)));
                if (!all) {
                    break;
                }
            }
        }
    }
    
    private void inEvHarm(final boolean all) {
        final List<EntityLivingBase> targets = (List<EntityLivingBase>)this.field_145850_b.func_72872_a((Class)EntityLivingBase.class, new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1)).func_72314_b(10.0, 10.0, 10.0));
        if (targets != null && targets.size() > 0) {
            for (final EntityLivingBase target : targets) {
                if (this.field_145850_b.field_73012_v.nextBoolean()) {
                    target.func_70690_d(new PotionEffect(PotionFluxTaint.instance, 120, 0, false, true));
                }
                else {
                    final PotionEffect pe = new PotionEffect(PotionVisExhaust.instance, 2400, 0, true, true);
                    pe.getCurativeItems().clear();
                    target.func_70690_d(pe);
                }
                if (!all) {
                    break;
                }
            }
        }
    }
    
    private void inEvWarp() {
        final List<EntityPlayer> targets = (List<EntityPlayer>)this.field_145850_b.func_72872_a((Class)EntityPlayer.class, new AxisAlignedBB((double)this.func_174877_v().func_177958_n(), (double)this.func_174877_v().func_177956_o(), (double)this.func_174877_v().func_177952_p(), (double)(this.func_174877_v().func_177958_n() + 1), (double)(this.func_174877_v().func_177956_o() + 1), (double)(this.func_174877_v().func_177952_p() + 1)).func_72314_b(10.0, 10.0, 10.0));
        if (targets != null && targets.size() > 0) {
            final EntityPlayer target = targets.get(this.field_145850_b.field_73012_v.nextInt(targets.size()));
            if (this.field_145850_b.field_73012_v.nextFloat() < 0.25f) {
                ThaumcraftApi.internalMethods.addWarpToPlayer(target, 1, IPlayerWarp.EnumWarpType.NORMAL);
            }
            else {
                ThaumcraftApi.internalMethods.addWarpToPlayer(target, 2 + this.field_145850_b.field_73012_v.nextInt(4), IPlayerWarp.EnumWarpType.TEMPORARY);
            }
        }
    }
    
    private void inEvEjectItem(final int type) {
        for (int q = 0; q < 50 && this.pedestals.size() > 0; ++q) {
            final BlockPos cc = this.pedestals.get(this.field_145850_b.field_73012_v.nextInt(this.pedestals.size()));
            final TileEntity te = this.field_145850_b.func_175625_s(cc);
            if (te != null && te instanceof TilePedestal && ((TilePedestal)te).func_70301_a(0) != null) {
                if (type < 3 || type == 5) {
                    InventoryUtils.dropItems(this.field_145850_b, cc);
                }
                else {
                    ((TilePedestal)te).func_70299_a(0, null);
                }
                if (type == 1 || type == 3) {
                    this.field_145850_b.func_175656_a(cc.func_177984_a(), BlocksTC.fluxGoo.func_176223_P());
                    this.field_145850_b.func_184133_a((EntityPlayer)null, cc, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 0.3f, 1.0f);
                }
                else if (type == 2 || type == 4) {
                    final int a = 5 + this.field_145850_b.field_73012_v.nextInt(5);
                    AuraHelper.polluteAura(this.field_145850_b, cc, a, true);
                }
                else if (type == 5) {
                    this.field_145850_b.func_72876_a((Entity)null, (double)(cc.func_177958_n() + 0.5f), (double)(cc.func_177956_o() + 0.5f), (double)(cc.func_177952_p() + 0.5f), 1.0f, false);
                }
                this.field_145850_b.func_175641_c(cc, this.field_145850_b.func_180495_p(cc).func_177230_c(), 11, 0);
                PacketHandler.INSTANCE.sendToAllAround((IMessage)new PacketFXBlockArc(this.field_174879_c, cc.func_177984_a(), 0.3f - this.field_145850_b.field_73012_v.nextFloat() * 0.1f, 0.0f, 0.3f - this.field_145850_b.field_73012_v.nextFloat() * 0.1f), new NetworkRegistry.TargetPoint(this.field_145850_b.field_73011_w.getDimension(), (double)cc.func_177958_n(), (double)cc.func_177956_o(), (double)cc.func_177952_p(), 32.0));
                return;
            }
        }
    }
    
    public void craftingFinish(final Object out, final String label) {
        final TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177979_c(2));
        if (te != null && te instanceof TilePedestal) {
            if (out instanceof ItemStack) {
                ((TilePedestal)te).setInventorySlotContentsFromInfusion(0, ((ItemStack)out).func_77946_l());
            }
            else if (out instanceof NBTBase) {
                final ItemStack temp = ((TilePedestal)te).func_70301_a(0);
                final NBTBase tag = (NBTBase)out;
                temp.func_77983_a(label, tag);
                this.syncTile(false);
                te.func_70296_d();
            }
            else if (out instanceof Enchantment) {
                final ItemStack temp = ((TilePedestal)te).func_70301_a(0);
                final Map enchantments = EnchantmentHelper.func_82781_a(temp);
                enchantments.put(out, EnchantmentHelper.func_77506_a((Enchantment)out, temp) + 1);
                EnchantmentHelper.func_82782_a(enchantments, temp);
                this.syncTile(false);
                te.func_70296_d();
            }
            if (this.recipePlayer != null) {
                final EntityPlayer p = this.field_145850_b.func_72924_a(this.recipePlayer);
                if (p != null) {
                    FMLCommonHandler.instance().firePlayerCraftingEvent(p, ((TilePedestal)te).func_70301_a(0), (IInventory)new InventoryFake(this.recipeIngredients));
                }
            }
            this.recipeEssentia = new AspectList();
            this.syncTile(false);
            this.func_70296_d();
            this.field_145850_b.func_175641_c(this.field_174879_c.func_177979_c(2), this.field_145850_b.func_180495_p(this.field_174879_c.func_177979_c(2)).func_177230_c(), 12, 0);
            this.field_145850_b.func_184133_a((EntityPlayer)null, this.field_174879_c, SoundsTC.wand, SoundCategory.BLOCKS, 0.5f, 1.0f);
        }
    }
    
    private void getSurroundings() {
        final ArrayList<BlockPos> stuff = new ArrayList<BlockPos>();
        this.pedestals.clear();
        try {
            for (int xx = -12; xx <= 12; ++xx) {
                for (int zz = -12; zz <= 12; ++zz) {
                    boolean skip = false;
                    for (int yy = -5; yy <= 10; ++yy) {
                        if (xx != 0 || zz != 0) {
                            final int x = this.field_174879_c.func_177958_n() + xx;
                            final int y = this.field_174879_c.func_177956_o() - yy;
                            final int z = this.field_174879_c.func_177952_p() + zz;
                            final BlockPos bp = new BlockPos(x, y, z);
                            final TileEntity te = this.field_145850_b.func_175625_s(bp);
                            if (!skip && yy > 0 && Math.abs(xx) <= 8 && Math.abs(zz) <= 8 && te != null && te instanceof TilePedestal) {
                                this.pedestals.add(bp);
                                skip = true;
                            }
                            else {
                                final Block bi = this.field_145850_b.func_180495_p(bp).func_177230_c();
                                try {
                                    if (bi != null && (bi == Blocks.field_150465_bP || (bi instanceof IInfusionStabiliser && ((IInfusionStabiliser)bi).canStabaliseInfusion(this.func_145831_w(), bp)))) {
                                        stuff.add(bp);
                                    }
                                }
                                catch (Exception ex) {}
                            }
                        }
                    }
                }
            }
            this.cycleTime = 10;
            this.symmetryInstability = 0;
            this.costMult = 1.0f;
            if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, -1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, -1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, 1)).func_177230_c() instanceof BlockPillar && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, 1)).func_177230_c() instanceof BlockPillar) {
                if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, -1)).func_177230_c() == BlocksTC.pillarAncient && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, -1)).func_177230_c() == BlocksTC.pillarAncient && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, 1)).func_177230_c() == BlocksTC.pillarAncient && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, 1)).func_177230_c() == BlocksTC.pillarAncient) {
                    --this.cycleTime;
                    this.costMult -= 0.1f;
                    this.symmetryInstability += 2;
                }
                if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, -1)).func_177230_c() == BlocksTC.pillarEldritch && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, -1)).func_177230_c() == BlocksTC.pillarEldritch && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(1, -2, 1)).func_177230_c() == BlocksTC.pillarEldritch && this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(-1, -2, 1)).func_177230_c() == BlocksTC.pillarEldritch) {
                    this.cycleTime -= 3;
                    this.costMult += 0.05f;
                    this.symmetryInstability -= 4;
                }
            }
            final int[] xm = { -1, 1, 1, -1 };
            final int[] zm = { -1, -1, 1, 1 };
            for (int a = 0; a < 4; ++a) {
                final Block b = this.field_145850_b.func_180495_p(this.field_174879_c.func_177982_a(xm[a], -3, zm[a])).func_177230_c();
                if (b == BlocksTC.matrixSpeed) {
                    --this.cycleTime;
                    this.costMult += 0.01f;
                }
                if (b == BlocksTC.matrixCost) {
                    ++this.cycleTime;
                    this.costMult -= 0.02f;
                }
            }
            this.countDelay = this.cycleTime / 2;
            int apc = 0;
            for (final BlockPos cc : this.pedestals) {
                boolean items = false;
                final int x2 = this.field_174879_c.func_177958_n() - cc.func_177958_n();
                final int z2 = this.field_174879_c.func_177952_p() - cc.func_177952_p();
                TileEntity te = this.field_145850_b.func_175625_s(cc);
                if (te != null && te instanceof TilePedestal) {
                    this.symmetryInstability += 2;
                    if (((TilePedestal)te).func_70301_a(0) != null) {
                        ++this.symmetryInstability;
                        if (this.field_145850_b.func_180495_p(cc).func_177230_c() == BlocksTC.pedestalEldritch) {
                            ++this.symmetryInstability;
                        }
                        items = true;
                    }
                    if (this.field_145850_b.func_180495_p(cc).func_177230_c() == BlocksTC.pedestalEldritch) {
                        this.costMult += 0.0025f;
                    }
                    if (this.field_145850_b.func_180495_p(cc).func_177230_c() == BlocksTC.pedestalAncient) {
                        this.costMult -= 0.01f;
                        ++apc;
                    }
                }
                final int xx2 = this.field_174879_c.func_177958_n() + x2;
                final int zz2 = this.field_174879_c.func_177952_p() + z2;
                final BlockPos cc2 = new BlockPos(xx2, cc.func_177956_o(), zz2);
                te = this.field_145850_b.func_175625_s(cc2);
                if (te != null && te instanceof TilePedestal && this.field_145850_b.func_180495_p(cc2) == this.field_145850_b.func_180495_p(cc)) {
                    this.symmetryInstability -= 2;
                    if (((TilePedestal)te).func_70301_a(0) == null || !items) {
                        continue;
                    }
                    --this.symmetryInstability;
                    if (this.field_145850_b.func_180495_p(cc2).func_177230_c() != BlocksTC.pedestalEldritch) {
                        continue;
                    }
                    this.symmetryInstability -= 2;
                }
            }
            this.symmetryInstability += apc / 4;
            float sym = 0.0f;
            for (final BlockPos cc3 : stuff) {
                final int x2 = this.field_174879_c.func_177958_n() - cc3.func_177958_n();
                final int z2 = this.field_174879_c.func_177952_p() - cc3.func_177952_p();
                Block bi2 = this.field_145850_b.func_180495_p(cc3).func_177230_c();
                try {
                    if (bi2 == Blocks.field_150465_bP || (bi2 instanceof IInfusionStabiliser && ((IInfusionStabiliser)bi2).canStabaliseInfusion(this.func_145831_w(), cc3))) {
                        sym += 0.1f;
                    }
                }
                catch (Exception ex2) {}
                final int xx2 = this.field_174879_c.func_177958_n() + x2;
                final int zz2 = this.field_174879_c.func_177952_p() + z2;
                bi2 = this.field_145850_b.func_180495_p(new BlockPos(xx2, cc3.func_177956_o(), zz2)).func_177230_c();
                try {
                    if (bi2 != Blocks.field_150465_bP && (!(bi2 instanceof IInfusionStabiliser) || !((IInfusionStabiliser)bi2).canStabaliseInfusion(this.func_145831_w(), cc3))) {
                        continue;
                    }
                    sym -= 0.2f;
                }
                catch (Exception ex3) {}
            }
            this.symmetryInstability += (int)sym;
        }
        catch (Exception ex4) {}
    }
    
    @Override
    public boolean onCasterRightClick(final World world, final ItemStack wandstack, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final EnumHand hand) {
        if (!world.field_72995_K && this.active && !this.crafting) {
            this.craftingStart(player);
            return false;
        }
        if (!world.field_72995_K && !this.active && this.validLocation()) {
            world.func_184133_a((EntityPlayer)null, pos, SoundsTC.craftstart, SoundCategory.BLOCKS, 0.5f, 1.0f);
            this.active = true;
            this.syncTile(false);
            this.func_70296_d();
            return false;
        }
        return false;
    }
    
    private void doEffects() {
        if (this.crafting) {
            if (this.craftCount == 0) {
                this.field_145850_b.func_184134_a((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), SoundsTC.infuserstart, SoundCategory.BLOCKS, 0.5f, 1.0f, false);
            }
            else if (this.craftCount == 0 || this.craftCount % 65 == 0) {
                this.field_145850_b.func_184134_a((double)this.field_174879_c.func_177958_n(), (double)this.field_174879_c.func_177956_o(), (double)this.field_174879_c.func_177952_p(), SoundsTC.infuser, SoundCategory.BLOCKS, 0.5f, 1.0f, false);
            }
            ++this.craftCount;
            FXDispatcher.INSTANCE.blockRunes(this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o() - 2, this.field_174879_c.func_177952_p(), 0.5f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f, 0.1f, 0.7f + this.field_145850_b.field_73012_v.nextFloat() * 0.3f, 25, -0.03f);
        }
        else if (this.craftCount > 0) {
            this.craftCount -= 2;
            if (this.craftCount < 0) {
                this.craftCount = 0;
            }
            if (this.craftCount > 50) {
                this.craftCount = 50;
            }
        }
        if (this.active && this.startUp != 1.0f) {
            if (this.startUp < 1.0f) {
                this.startUp += Math.max(this.startUp / 10.0f, 0.001f);
            }
            if (this.startUp > 0.999) {
                this.startUp = 1.0f;
            }
        }
        if (!this.active && this.startUp > 0.0f) {
            if (this.startUp > 0.0f) {
                this.startUp -= this.startUp / 10.0f;
            }
            if (this.startUp < 0.001) {
                this.startUp = 0.0f;
            }
        }
        for (final String fxk : this.sourceFX.keySet().toArray(new String[0])) {
            final SourceFX fx = this.sourceFX.get(fxk);
            if (fx.ticks <= 0) {
                this.sourceFX.remove(fxk);
            }
            else {
                if (fx.loc.equals((Object)this.field_174879_c)) {
                    final Entity player = this.field_145850_b.func_73045_a(fx.color);
                    if (player != null) {
                        for (int a = 0; a < 4; ++a) {
                            FXDispatcher.INSTANCE.drawInfusionParticles4(player.field_70165_t + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * player.field_70130_N, player.func_174813_aQ().field_72338_b + this.field_145850_b.field_73012_v.nextFloat() * player.field_70131_O, player.field_70161_v + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * player.field_70130_N, this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p());
                        }
                    }
                }
                else {
                    final TileEntity tile = this.field_145850_b.func_175625_s(fx.loc);
                    if (tile instanceof TilePedestal) {
                        final ItemStack is = ((TilePedestal)tile).func_70301_a(0);
                        if (is != null && !is.func_190926_b()) {
                            if (this.field_145850_b.field_73012_v.nextInt(3) == 0) {
                                FXDispatcher.INSTANCE.drawInfusionParticles3(fx.loc.func_177958_n() + this.field_145850_b.field_73012_v.nextFloat(), fx.loc.func_177956_o() + this.field_145850_b.field_73012_v.nextFloat() + 1.0f, fx.loc.func_177952_p() + this.field_145850_b.field_73012_v.nextFloat(), this.field_174879_c.func_177958_n(), this.field_174879_c.func_177956_o(), this.field_174879_c.func_177952_p());
                            }
                            else {
                                final Item bi = is.func_77973_b();
                                final int md = is.func_77952_i();
                                if (bi instanceof ItemBlock) {
                                    for (int a2 = 0; a2 < 4; ++a2) {
                                        FXDispatcher.INSTANCE.drawInfusionParticles2(fx.loc.func_177958_n() + this.field_145850_b.field_73012_v.nextFloat(), fx.loc.func_177956_o() + this.field_145850_b.field_73012_v.nextFloat() + 1.0f, fx.loc.func_177952_p() + this.field_145850_b.field_73012_v.nextFloat(), this.field_174879_c, Block.func_149634_a(bi).func_176223_P(), md);
                                    }
                                }
                                else {
                                    for (int a2 = 0; a2 < 4; ++a2) {
                                        FXDispatcher.INSTANCE.drawInfusionParticles1(fx.loc.func_177958_n() + 0.4f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f, fx.loc.func_177956_o() + 1.23f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f, fx.loc.func_177952_p() + 0.4f + this.field_145850_b.field_73012_v.nextFloat() * 0.2f, this.field_174879_c, bi, md);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        fx.ticks = 0;
                    }
                }
                final SourceFX sourceFX = fx;
                --sourceFX.ticks;
                this.sourceFX.put(fxk, fx);
            }
        }
        if (this.crafting && this.instability > 0 && this.field_145850_b.field_73012_v.nextInt(200) <= this.instability) {
            float xx;
            float zz;
            int yy;
            for (xx = this.field_174879_c.func_177958_n() + 0.5f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 4.0f, zz = this.field_174879_c.func_177952_p() + 0.5f + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 4.0f, yy = this.field_174879_c.func_177956_o() - 2; !this.field_145850_b.func_175623_d(new BlockPos((double)xx, (double)yy, (double)zz)); ++yy) {}
            FXDispatcher.INSTANCE.arcLightning(this.field_174879_c.func_177958_n() + 0.5f, this.field_174879_c.func_177956_o() + 0.5f, this.field_174879_c.func_177952_p() + 0.5f, xx, yy, zz, 0.8f, 0.1f, 1.0f, 0.0f);
        }
    }
    
    @Override
    public AspectList getAspects() {
        return this.recipeEssentia;
    }
    
    @Override
    public void setAspects(final AspectList aspects) {
    }
    
    @Override
    public int addToContainer(final Aspect tag, final int amount) {
        return 0;
    }
    
    @Override
    public boolean takeFromContainer(final Aspect tag, final int amount) {
        return false;
    }
    
    @Override
    public boolean takeFromContainer(final AspectList ot) {
        return false;
    }
    
    @Override
    public boolean doesContainerContainAmount(final Aspect tag, final int amount) {
        return false;
    }
    
    @Override
    public boolean doesContainerContain(final AspectList ot) {
        return false;
    }
    
    @Override
    public int containerContains(final Aspect tag) {
        return 0;
    }
    
    @Override
    public boolean doesContainerAccept(final Aspect tag) {
        return true;
    }
    
    public boolean canRenderBreaking() {
        return true;
    }
    
    public class SourceFX
    {
        public BlockPos loc;
        public int ticks;
        public int color;
        public int entity;
        
        public SourceFX(final BlockPos loc, final int ticks, final int color) {
            this.loc = loc;
            this.ticks = ticks;
            this.color = color;
        }
    }
}
