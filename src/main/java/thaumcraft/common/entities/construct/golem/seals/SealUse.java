package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraftforge.common.util.*;
import net.minecraft.network.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.*;

public class SealUse extends SealFiltered implements ISealConfigToggles
{
    int delay;
    FakePlayer fp;
    int watchedTask;
    ResourceLocation icon;
    protected SealToggle[] props;
    
    public SealUse() {
        this.delay = new Random(System.nanoTime()).nextInt(49);
        this.watchedTask = Integer.MIN_VALUE;
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_use");
        this.props = new SealToggle[] { new SealToggle(true, "pmeta", "golem.prop.meta"), new SealToggle(true, "pnbt", "golem.prop.nbt"), new SealToggle(false, "pore", "golem.prop.ore"), new SealToggle(false, "pmod", "golem.prop.mod"), new SealToggle(false, "pleft", "golem.prop.left"), new SealToggle(false, "pempty", "golem.prop.empty"), new SealToggle(false, "pemptyhand", "golem.prop.emptyhand"), new SealToggle(false, "psneak", "golem.prop.sneak"), new SealToggle(false, "ppro", "golem.prop.provision.wl") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:use";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay++ % 5 != 0) {
            return;
        }
        final Task oldTask = TaskHandler.getTask(world.field_73011_w.getDimension(), this.watchedTask);
        if (oldTask == null || oldTask.isSuspended() || oldTask.isCompleted()) {
            if (this.getToggles()[5].value != world.func_175623_d(seal.getSealPos().pos)) {
                return;
            }
            final Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
            task.setPriority(seal.getPriority());
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
            this.watchedTask = task.getId();
        }
    }
    
    @Override
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
    }
    
    public boolean mayPlace(final World world, final Block blockIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState block = world.func_180495_p(pos);
        final AxisAlignedBB axisalignedbb = blockIn.func_185496_a(blockIn.func_176223_P(), (IBlockAccess)world, pos);
        return axisalignedbb == null || world.func_72917_a(axisalignedbb, (Entity)null);
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        if (this.getToggles()[5].value == world.func_175623_d(task.getPos())) {
            if (this.fp == null) {
                this.fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
                this.fp.field_71135_a = new FakeNetHandlerPlayServer(this.fp.field_71133_b, new NetworkManager(EnumPacketDirection.CLIENTBOUND), (EntityPlayerMP)this.fp);
            }
            this.fp.func_70080_a(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u, golem.getGolemEntity().field_70161_v, golem.getGolemEntity().field_70177_z, golem.getGolemEntity().field_70125_A);
            final IBlockState bs = world.func_180495_p(task.getPos());
            ItemStack clickStack = (ItemStack)golem.getCarrying().get(0);
            if (!((ItemStack)this.filter.get(0)).func_190926_b()) {
                clickStack = InventoryUtils.findFirstMatchFromFilter(this.filter, this.blacklist, golem.getCarrying(), new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value));
            }
            if (!clickStack.func_190926_b() || this.props[6].value) {
                ItemStack ss = ItemStack.field_190927_a;
                if (!clickStack.func_190926_b()) {
                    ss = clickStack.func_77946_l();
                    golem.dropItem(clickStack.func_77946_l());
                }
                this.fp.func_184611_a(EnumHand.MAIN_HAND, ss);
                this.fp.func_70095_a(this.props[6].value);
                if (this.getToggles()[4].value) {
                    try {
                        this.fp.field_71134_c.func_180784_a(task.getPos(), task.getSealPos().face);
                    }
                    catch (Exception ex) {}
                }
                else {
                    if (this.fp.func_184614_ca().func_77973_b() instanceof ItemBlock && !this.mayPlace(world, ((ItemBlock)this.fp.func_184614_ca().func_77973_b()).func_179223_d(), task.getPos(), task.getSealPos().face)) {
                        golem.getGolemEntity().func_70107_b(golem.getGolemEntity().field_70165_t + task.getSealPos().face.func_82601_c(), golem.getGolemEntity().field_70163_u + task.getSealPos().face.func_96559_d(), golem.getGolemEntity().field_70161_v + task.getSealPos().face.func_82599_e());
                    }
                    try {
                        this.fp.field_71134_c.func_187251_a((EntityPlayer)this.fp, world, this.fp.func_184614_ca(), EnumHand.MAIN_HAND, task.getPos(), task.getSealPos().face, 0.5f, 0.5f, 0.5f);
                    }
                    catch (Exception ex2) {}
                }
                golem.addRankXp(1);
                if (!this.fp.func_184614_ca().func_190926_b() && this.fp.func_184614_ca().func_190916_E() <= 0) {
                    this.fp.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
                }
                this.dropSomeItems(this.fp, golem);
                golem.swingArm();
            }
        }
        task.setSuspended(true);
        return true;
    }
    
    private void dropSomeItems(final FakePlayer fp2, final IGolemAPI golem) {
        for (int i = 0; i < fp2.field_71071_by.field_70462_a.size(); ++i) {
            if (!((ItemStack)fp2.field_71071_by.field_70462_a.get(i)).func_190926_b()) {
                if (golem.canCarry((ItemStack)fp2.field_71071_by.field_70462_a.get(i), true)) {
                    fp2.field_71071_by.field_70462_a.set(i, (Object)golem.holdItem((ItemStack)fp2.field_71071_by.field_70462_a.get(i)));
                }
                if (!((ItemStack)fp2.field_71071_by.field_70462_a.get(i)).func_190926_b() && ((ItemStack)fp2.field_71071_by.field_70462_a.get(i)).func_190916_E() > 0) {
                    InventoryUtils.dropItemAtEntity(golem.getGolemWorld(), (ItemStack)fp2.field_71071_by.field_70462_a.get(i), (Entity)golem.getGolemEntity());
                }
                fp2.field_71071_by.field_70462_a.set(i, (Object)ItemStack.field_190927_a);
            }
        }
        for (int i = 0; i < fp2.field_71071_by.field_70460_b.size(); ++i) {
            if (!((ItemStack)fp2.field_71071_by.field_70460_b.get(i)).func_190926_b()) {
                if (golem.canCarry((ItemStack)fp2.field_71071_by.field_70460_b.get(i), true)) {
                    fp2.field_71071_by.field_70460_b.set(i, (Object)golem.holdItem((ItemStack)fp2.field_71071_by.field_70460_b.get(i)));
                }
                if (!((ItemStack)fp2.field_71071_by.field_70462_a.get(i)).func_190926_b() && ((ItemStack)fp2.field_71071_by.field_70460_b.get(i)).func_190916_E() > 0) {
                    InventoryUtils.dropItemAtEntity(golem.getGolemWorld(), (ItemStack)fp2.field_71071_by.field_70460_b.get(i), (Entity)golem.getGolemEntity());
                }
                fp2.field_71071_by.field_70460_b.set(i, (Object)ItemStack.field_190927_a);
            }
        }
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        if (!this.props[6].value) {
            final boolean found = !InventoryUtils.findFirstMatchFromFilter(this.filter, this.blacklist, golem.getCarrying(), new InventoryUtils.InvFilter(!this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value)).func_190926_b();
            if (!found && this.getToggles()[8].value && !this.blacklist && this.getInv().get(0) != null) {
                final ISealEntity se = SealHandler.getSealEntity(golem.getGolemWorld().field_73011_w.getDimension(), task.getSealPos());
                if (se != null) {
                    final ItemStack stack = ((ItemStack)this.getInv().get(0)).func_77946_l();
                    if (!this.props[0].value) {
                        stack.func_77964_b(32767);
                    }
                    GolemHelper.requestProvisioning(golem.getGolemWorld(), se, stack);
                }
            }
            return found;
        }
        return true;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
    }
    
    @Override
    public boolean canPlaceAt(final World world, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public ResourceLocation getSealIcon() {
        return this.icon;
    }
    
    @Override
    public void onRemoval(final World world, final BlockPos pos, final EnumFacing side) {
    }
    
    @Override
    public Object returnContainer(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseContainer(player.field_71071_by, world, seal);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Object returnGui(final World world, final EntityPlayer player, final BlockPos pos, final EnumFacing side, final ISealEntity seal) {
        return new SealBaseGUI(player.field_71071_by, world, seal);
    }
    
    @Override
    public int[] getGuiCategories() {
        return new int[] { 1, 3, 0, 4 };
    }
    
    @Override
    public EnumGolemTrait[] getRequiredTags() {
        return new EnumGolemTrait[] { EnumGolemTrait.DEFT, EnumGolemTrait.SMART };
    }
    
    @Override
    public EnumGolemTrait[] getForbiddenTags() {
        return null;
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
    }
}
