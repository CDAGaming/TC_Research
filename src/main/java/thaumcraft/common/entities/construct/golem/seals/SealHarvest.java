package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.api.golems.seals.*;
import thaumcraft.common.entities.construct.golem.tasks.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.util.math.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.*;
import net.minecraftforge.common.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraftforge.common.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.golems.*;

public class SealHarvest implements ISeal, ISealGui, ISealConfigArea, ISealConfigToggles
{
    int delay;
    int count;
    HashMap<Long, ReplantInfo> replantTasks;
    ResourceLocation icon;
    protected SealToggle[] props;
    
    public SealHarvest() {
        this.delay = new Random(System.nanoTime()).nextInt(33);
        this.count = 0;
        this.replantTasks = new HashMap<Long, ReplantInfo>();
        this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_harvest");
        this.props = new SealToggle[] { new SealToggle(true, "prep", "golem.prop.replant"), new SealToggle(false, "ppro", "golem.prop.provision") };
    }
    
    @Override
    public String getKey() {
        return "thaumcraft:harvest";
    }
    
    @Override
    public void tickSeal(final World world, final ISealEntity seal) {
        if (this.delay % 100 == 0) {
            final AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
            final Iterator<Long> rt = this.replantTasks.keySet().iterator();
            while (rt.hasNext()) {
                final BlockPos pp = BlockPos.func_177969_a((long)rt.next());
                if (!area.func_72318_a(new Vec3d(pp.func_177958_n() + 0.5, pp.func_177956_o() + 0.5, pp.func_177952_p() + 0.5))) {
                    if (this.replantTasks.get(rt) != null) {
                        final Task tt = TaskHandler.getTask(world.field_73011_w.getDimension(), this.replantTasks.get(rt).taskid);
                        if (tt != null) {
                            tt.setSuspended(true);
                        }
                    }
                    rt.remove();
                }
            }
        }
        if (this.delay++ % 5 != 0) {
            return;
        }
        final BlockPos p = GolemHelper.getPosInArea(seal, this.count++);
        if (CropUtils.isGrownCrop(world, p)) {
            final Task task = new Task(seal.getSealPos(), p);
            task.setPriority(seal.getPriority());
            TaskHandler.addTask(world.field_73011_w.getDimension(), task);
        }
        else if (this.getToggles()[0].value && this.replantTasks.containsKey(p.func_177986_g()) && world.func_175623_d(p)) {
            final Task t = TaskHandler.getTask(world.field_73011_w.getDimension(), this.replantTasks.get(p.func_177986_g()).taskid);
            if (t == null) {
                final Task tt2 = new Task(seal.getSealPos(), this.replantTasks.get(p.func_177986_g()).pos);
                tt2.setPriority(seal.getPriority());
                TaskHandler.addTask(world.field_73011_w.getDimension(), tt2);
                this.replantTasks.get(p.func_177986_g()).taskid = tt2.getId();
            }
        }
    }
    
    @Override
    public boolean onTaskCompletion(final World world, final IGolemAPI golem, final Task task) {
        if (CropUtils.isGrownCrop(world, task.getPos())) {
            final FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
            fp.func_70107_b(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u, golem.getGolemEntity().field_70161_v);
            final EnumFacing face = EnumFacing.func_190914_a(task.getPos(), golem.getGolemEntity());
            final IBlockState bs = world.func_180495_p(task.getPos());
            if (CropUtils.clickableCrops.contains(bs.func_177230_c().func_149739_a() + bs.func_177230_c().func_176201_c(bs))) {
                bs.func_177230_c().func_180639_a(world, task.getPos(), bs, (EntityPlayer)fp, EnumHand.MAIN_HAND, face, 0.0f, 0.0f, 0.0f);
                golem.addRankXp(1);
                golem.swingArm();
            }
            else {
                BlockUtils.harvestBlock(world, (EntityPlayer)fp, task.getPos(), true, false, 0, true);
                golem.addRankXp(1);
                golem.swingArm();
                if (this.getToggles()[0].value) {
                    final ItemStack seed = ThaumcraftApi.getSeed(bs.func_177230_c());
                    if (seed != null && !seed.func_190926_b()) {
                        final IBlockState bb = world.func_180495_p(task.getPos().func_177977_b());
                        EnumFacing rf = null;
                        if (seed.func_77973_b() instanceof IPlantable && bb.func_177230_c().canSustainPlant(bb, (IBlockAccess)world, task.getPos().func_177977_b(), EnumFacing.UP, (IPlantable)seed.func_77973_b())) {
                            rf = EnumFacing.DOWN;
                        }
                        else if (!(seed.func_77973_b() instanceof IPlantable) && bs.func_177230_c() instanceof BlockDirectional) {
                            rf = (EnumFacing)bs.func_177229_b((IProperty)BlockDirectional.field_176387_N);
                        }
                        if (rf != null) {
                            final Task tt = new Task(task.getSealPos(), task.getPos());
                            tt.setPriority(task.getPriority());
                            tt.setLifespan((short)300);
                            this.replantTasks.put(tt.getPos().func_177986_g(), new ReplantInfo(tt.getPos(), rf, tt.getId(), seed.func_77946_l(), bb.func_177230_c() instanceof BlockFarmland));
                            TaskHandler.addTask(world.field_73011_w.getDimension(), tt);
                        }
                    }
                }
            }
        }
        else if (this.replantTasks.containsKey(task.getPos().func_177986_g()) && this.replantTasks.get(task.getPos().func_177986_g()).taskid == task.getId() && world.func_175623_d(task.getPos()) && golem.isCarrying(this.replantTasks.get(task.getPos().func_177986_g()).stack)) {
            final FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
            fp.func_70107_b(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u, golem.getGolemEntity().field_70161_v);
            final IBlockState bb2 = world.func_180495_p(task.getPos().func_177977_b());
            final ReplantInfo ri = this.replantTasks.get(task.getPos().func_177986_g());
            if ((bb2.func_177230_c() instanceof BlockDirt || bb2.func_177230_c() instanceof BlockGrass) && ri.farmland) {
                Items.field_151012_L.func_180614_a((EntityPlayer)fp, world, task.getPos().func_177977_b(), EnumHand.MAIN_HAND, EnumFacing.UP, 0.5f, 0.5f, 0.5f);
            }
            final ItemStack seed = ri.stack.func_77946_l();
            seed.func_190920_e(1);
            if (seed.func_77973_b().func_180614_a((EntityPlayer)fp, world, task.getPos().func_177972_a(ri.face), EnumHand.MAIN_HAND, ri.face.func_176734_d(), 0.5f, 0.5f, 0.5f) == EnumActionResult.SUCCESS) {
                world.func_175669_a(2001, task.getPos(), Block.func_176210_f(world.func_180495_p(task.getPos())));
                golem.dropItem(seed);
                golem.addRankXp(1);
                golem.swingArm();
            }
        }
        task.setSuspended(true);
        return true;
    }
    
    @Override
    public boolean canGolemPerformTask(final IGolemAPI golem, final Task task) {
        if (this.replantTasks.containsKey(task.getPos().func_177986_g()) && this.replantTasks.get(task.getPos().func_177986_g()).taskid == task.getId()) {
            final boolean carry = golem.isCarrying(this.replantTasks.get(task.getPos().func_177986_g()).stack);
            if (!carry && this.getToggles()[1].value) {
                final ISealEntity se = SealHandler.getSealEntity(golem.getGolemWorld().field_73011_w.getDimension(), task.getSealPos());
                if (se != null) {
                    GolemHelper.requestProvisioning(golem.getGolemWorld(), se, this.replantTasks.get(task.getPos().func_177986_g()).stack);
                }
            }
            return carry;
        }
        return true;
    }
    
    @Override
    public void onTaskSuspension(final World world, final Task task) {
    }
    
    @Override
    public void readCustomNBT(final NBTTagCompound nbt) {
        final NBTTagList nbttaglist = nbt.func_150295_c("replant", 10);
        for (int i = 0; i < nbttaglist.func_74745_c(); ++i) {
            final NBTTagCompound nbttagcompound1 = nbttaglist.func_150305_b(i);
            final long loc = nbttagcompound1.func_74763_f("taskloc");
            final byte face = nbttagcompound1.func_74771_c("taskface");
            final boolean farmland = nbttagcompound1.func_74767_n("farmland");
            final ItemStack stack = new ItemStack(nbttagcompound1);
            this.replantTasks.put(loc, new ReplantInfo(BlockPos.func_177969_a(loc), EnumFacing.field_82609_l[face], 0, stack, farmland));
        }
    }
    
    @Override
    public void writeCustomNBT(final NBTTagCompound nbt) {
        if (this.getToggles()[0].value) {
            final NBTTagList nbttaglist = new NBTTagList();
            for (final Long key : this.replantTasks.keySet()) {
                final ReplantInfo info = this.replantTasks.get(key);
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74772_a("taskloc", info.pos.func_177986_g());
                nbttagcompound1.func_74774_a("taskface", (byte)info.face.ordinal());
                nbttagcompound1.func_74757_a("farmland", info.farmland);
                info.stack.func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
            }
            nbt.func_74782_a("replant", (NBTBase)nbttaglist);
        }
    }
    
    @Override
    public boolean canPlaceAt(final World world, final BlockPos pos, final EnumFacing side) {
        return !world.func_175623_d(pos);
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
        return new int[] { 2, 3, 0, 4 };
    }
    
    @Override
    public SealToggle[] getToggles() {
        return this.props;
    }
    
    @Override
    public void setToggle(final int indx, final boolean value) {
        this.props[indx].setValue(value);
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
    public void onTaskStarted(final World world, final IGolemAPI golem, final Task task) {
    }
    
    private class ReplantInfo
    {
        EnumFacing face;
        BlockPos pos;
        int taskid;
        ItemStack stack;
        boolean farmland;
        
        public ReplantInfo(final BlockPos pos, final EnumFacing face, final int taskid, final ItemStack stack, final boolean farmland) {
            this.pos = pos;
            this.face = face;
            this.taskid = taskid;
            this.stack = stack;
            this.farmland = farmland;
        }
    }
}
