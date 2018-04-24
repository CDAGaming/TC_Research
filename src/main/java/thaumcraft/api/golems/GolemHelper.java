package thaumcraft.api.golems;

import java.util.*;
import thaumcraft.api.*;
import net.minecraft.item.*;
import thaumcraft.api.golems.seals.*;
import thaumcraft.api.golems.tasks.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class GolemHelper
{
    public static HashMap<Integer, ArrayList<ProvisionRequest>> provisionRequests;
    
    public static void registerSeal(final ISeal seal) {
        ThaumcraftApi.internalMethods.registerSeal(seal);
    }
    
    public static ISeal getSeal(final String key) {
        return ThaumcraftApi.internalMethods.getSeal(key);
    }
    
    public static ItemStack getSealStack(final String key) {
        return ThaumcraftApi.internalMethods.getSealStack(key);
    }
    
    public static ISealEntity getSealEntity(final int dim, final SealPos pos) {
        return ThaumcraftApi.internalMethods.getSealEntity(dim, pos);
    }
    
    public static void addGolemTask(final int dim, final Task task) {
        ThaumcraftApi.internalMethods.addGolemTask(dim, task);
    }
    
    public static void requestProvisioning(final World world, final ISealEntity seal, final ItemStack stack) {
        if (!GolemHelper.provisionRequests.containsKey(world.field_73011_w.getDimension())) {
            GolemHelper.provisionRequests.put(world.field_73011_w.getDimension(), new ArrayList<ProvisionRequest>());
        }
        final ArrayList<ProvisionRequest> list = GolemHelper.provisionRequests.get(world.field_73011_w.getDimension());
        final ProvisionRequest pr = new ProvisionRequest(seal, stack.func_77946_l());
        if (!list.contains(pr)) {
            list.add(pr);
        }
    }
    
    public static void requestProvisioning(final World world, final BlockPos pos, final EnumFacing side, final ItemStack stack) {
        if (!GolemHelper.provisionRequests.containsKey(world.field_73011_w.getDimension())) {
            GolemHelper.provisionRequests.put(world.field_73011_w.getDimension(), new ArrayList<ProvisionRequest>());
        }
        final ArrayList<ProvisionRequest> list = GolemHelper.provisionRequests.get(world.field_73011_w.getDimension());
        final ProvisionRequest pr = new ProvisionRequest(pos, side, stack.func_77946_l());
        pr.setId(pr.getId() + world.field_73012_v.nextInt());
        if (!list.contains(pr)) {
            list.add(pr);
        }
    }
    
    public static void requestProvisioning(final World world, final Entity entity, final ItemStack stack) {
        if (!GolemHelper.provisionRequests.containsKey(world.field_73011_w.getDimension())) {
            GolemHelper.provisionRequests.put(world.field_73011_w.getDimension(), new ArrayList<ProvisionRequest>());
        }
        final ArrayList<ProvisionRequest> list = GolemHelper.provisionRequests.get(world.field_73011_w.getDimension());
        final ProvisionRequest pr = new ProvisionRequest(entity, stack.func_77946_l());
        pr.setId(pr.getId() + world.field_73012_v.nextInt());
        if (!list.contains(pr)) {
            list.add(pr);
        }
    }
    
    public static BlockPos getPosInArea(final ISealEntity seal, final int count) {
        final int xx = 1 + (seal.getArea().func_177958_n() - 1) * ((seal.getSealPos().face.func_82601_c() == 0) ? 2 : 1);
        final int yy = 1 + (seal.getArea().func_177956_o() - 1) * ((seal.getSealPos().face.func_96559_d() == 0) ? 2 : 1);
        final int zz = 1 + (seal.getArea().func_177952_p() - 1) * ((seal.getSealPos().face.func_82599_e() == 0) ? 2 : 1);
        final int qx = (seal.getSealPos().face.func_82601_c() != 0) ? seal.getSealPos().face.func_82601_c() : 1;
        final int qy = (seal.getSealPos().face.func_96559_d() != 0) ? seal.getSealPos().face.func_96559_d() : 1;
        final int qz = (seal.getSealPos().face.func_82599_e() != 0) ? seal.getSealPos().face.func_82599_e() : 1;
        final int y = qy * (count / zz / xx) % yy + seal.getSealPos().face.func_96559_d();
        final int x = qx * (count / zz) % xx + seal.getSealPos().face.func_82601_c();
        final int z = qz * count % zz + seal.getSealPos().face.func_82599_e();
        final BlockPos p = seal.getSealPos().pos.func_177982_a(x - ((seal.getSealPos().face.func_82601_c() == 0) ? (xx / 2) : 0), y - ((seal.getSealPos().face.func_96559_d() == 0) ? (yy / 2) : 0), z - ((seal.getSealPos().face.func_82599_e() == 0) ? (zz / 2) : 0));
        return p;
    }
    
    public static AxisAlignedBB getBoundsForArea(final ISealEntity seal) {
        return new AxisAlignedBB((double)seal.getSealPos().pos.func_177958_n(), (double)seal.getSealPos().pos.func_177956_o(), (double)seal.getSealPos().pos.func_177952_p(), (double)(seal.getSealPos().pos.func_177958_n() + 1), (double)(seal.getSealPos().pos.func_177956_o() + 1), (double)(seal.getSealPos().pos.func_177952_p() + 1)).func_72317_d((double)seal.getSealPos().face.func_82601_c(), (double)seal.getSealPos().face.func_96559_d(), (double)seal.getSealPos().face.func_82599_e()).func_72321_a((seal.getSealPos().face.func_82601_c() != 0) ? ((double)((seal.getArea().func_177958_n() - 1) * seal.getSealPos().face.func_82601_c())) : 0.0, (seal.getSealPos().face.func_96559_d() != 0) ? ((double)((seal.getArea().func_177956_o() - 1) * seal.getSealPos().face.func_96559_d())) : 0.0, (seal.getSealPos().face.func_82599_e() != 0) ? ((double)((seal.getArea().func_177952_p() - 1) * seal.getSealPos().face.func_82599_e())) : 0.0).func_72314_b((seal.getSealPos().face.func_82601_c() == 0) ? ((double)(seal.getArea().func_177958_n() - 1)) : 0.0, (seal.getSealPos().face.func_96559_d() == 0) ? ((double)(seal.getArea().func_177956_o() - 1)) : 0.0, (seal.getSealPos().face.func_82599_e() == 0) ? ((double)(seal.getArea().func_177952_p() - 1)) : 0.0);
    }
    
    static {
        GolemHelper.provisionRequests = new HashMap<Integer, ArrayList<ProvisionRequest>>();
    }
}
