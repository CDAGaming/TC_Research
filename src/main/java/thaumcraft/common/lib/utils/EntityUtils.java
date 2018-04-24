package thaumcraft.common.lib.utils;

import net.minecraftforge.fml.common.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import baubles.api.*;
import baubles.api.cap.*;
import thaumcraft.api.items.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import java.util.stream.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.entities.*;
import thaumcraft.api.*;
import thaumcraft.common.entities.monster.mods.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.entities.monster.boss.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;

public class EntityUtils
{
    public static final AttributeModifier CHAMPION_HEALTH;
    public static final AttributeModifier CHAMPION_DAMAGE;
    public static final AttributeModifier BOLDBUFF;
    public static final AttributeModifier MIGHTYBUFF;
    public static final AttributeModifier[] HPBUFF;
    public static final AttributeModifier[] DMGBUFF;
    
    public static boolean isFriendly(final Entity source, final Entity target) {
        return source != null && target != null && (source.func_145782_y() == target.func_145782_y() || source.func_184215_y(target) || source.func_184191_r(target) || (target instanceof IEntityOwnable && ((IEntityOwnable)target).func_70902_q() != null && ((IEntityOwnable)target).func_70902_q().equals((Object)source)) || (target instanceof EntityPlayer && !FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W()));
    }
    
    public static Vec3d posToHand(final Entity e, final EnumHand hand) {
        double px = e.field_70165_t;
        double py = e.func_174813_aQ().field_72338_b + e.field_70131_O / 2.0f + 0.25;
        double pz = e.field_70161_v;
        final float m = (hand == EnumHand.MAIN_HAND) ? 0.0f : 180.0f;
        px += -MathHelper.func_76134_b((e.field_70177_z + m) / 180.0f * 3.141593f) * 0.3f;
        pz += -MathHelper.func_76126_a((e.field_70177_z + m) / 180.0f * 3.141593f) * 0.3f;
        final Vec3d vec3d = e.func_70676_i(1.0f);
        px += vec3d.field_72450_a * 0.3;
        py += vec3d.field_72448_b * 0.3;
        pz += vec3d.field_72449_c * 0.3;
        return new Vec3d(px, py, pz);
    }
    
    public static boolean hasGoggles(final Entity e) {
        if (!(e instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer viewer = (EntityPlayer)e;
        if (viewer.func_184614_ca().func_77973_b() instanceof IGoggles && showPopups(viewer.func_184614_ca(), viewer)) {
            return true;
        }
        for (int a = 0; a < 4; ++a) {
            if (((ItemStack)viewer.field_71071_by.field_70460_b.get(a)).func_77973_b() instanceof IGoggles && showPopups((ItemStack)viewer.field_71071_by.field_70460_b.get(a), viewer)) {
                return true;
            }
        }
        final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(viewer);
        for (int a2 = 0; a2 < baubles.getSlots(); ++a2) {
            if (baubles.getStackInSlot(a2).func_77973_b() instanceof IGoggles && showPopups(baubles.getStackInSlot(a2), viewer)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean showPopups(final ItemStack stack, final EntityPlayer player) {
        return ((IGoggles)stack.func_77973_b()).showIngamePopups(stack, (EntityLivingBase)player);
    }
    
    public static boolean hasRevealer(final Entity e) {
        if (!(e instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer viewer = (EntityPlayer)e;
        if (viewer.func_184614_ca().func_77973_b() instanceof IRevealer && reveals(viewer.func_184614_ca(), viewer)) {
            return true;
        }
        if (viewer.func_184592_cb().func_77973_b() instanceof IRevealer && reveals(viewer.func_184592_cb(), viewer)) {
            return true;
        }
        for (int a = 0; a < 4; ++a) {
            if (((ItemStack)viewer.field_71071_by.field_70460_b.get(a)).func_77973_b() instanceof IRevealer && reveals((ItemStack)viewer.field_71071_by.field_70460_b.get(a), viewer)) {
                return true;
            }
        }
        final IInventory baubles = BaublesApi.getBaubles(viewer);
        for (int a2 = 0; a2 < baubles.func_70302_i_(); ++a2) {
            if (baubles.func_70301_a(a2).func_77973_b() instanceof IRevealer && reveals(baubles.func_70301_a(a2), viewer)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean reveals(final ItemStack stack, final EntityPlayer player) {
        return ((IRevealer)stack.func_77973_b()).showNodes(stack, (EntityLivingBase)player);
    }
    
    public static Entity getPointedEntity(final World world, final Entity entity, final double minrange, final double range, final float padding, final boolean nonCollide) {
        return getPointedEntity(world, new RayTraceResult(entity, entity.func_174791_d().func_72441_c(0.0, (double)entity.func_70047_e(), 0.0)), entity.func_70040_Z(), minrange, range, padding, nonCollide);
    }
    
    public static Entity getPointedEntity(final World world, final Entity entity, final Vec3d lookVec, final double minrange, final double range, final float padding) {
        return getPointedEntity(world, new RayTraceResult(entity, entity.func_174791_d().func_72441_c(0.0, (double)entity.func_70047_e(), 0.0)), lookVec, minrange, range, padding, false);
    }
    
    public static Entity getPointedEntity(final World world, final RayTraceResult ray, final Vec3d lookVec, final double minrange, final double range, final float padding) {
        return getPointedEntity(world, ray, lookVec, minrange, range, padding, false);
    }
    
    public static Entity getPointedEntity(final World world, final RayTraceResult ray, final Vec3d lookVec, final double minrange, final double range, final float padding, final boolean nonCollide) {
        Entity pointedEntity = null;
        final Vec3d entityVec = new Vec3d(ray.field_72307_f.field_72450_a, ray.field_72307_f.field_72448_b, ray.field_72307_f.field_72449_c);
        final Vec3d vec3d2 = entityVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
        final AxisAlignedBB bb = (ray.field_72308_g != null) ? ray.field_72308_g.func_174813_aQ() : new AxisAlignedBB(ray.field_72307_f, ray.field_72307_f).func_186662_g(0.5);
        final List list = world.func_72839_b(ray.field_72308_g, bb.func_72321_a(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range).func_72314_b((double)padding, (double)padding, (double)padding));
        double d2 = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (ray.field_72307_f.func_72438_d(entity.func_174791_d()) >= minrange) {
                if (entity.func_70067_L() || nonCollide) {
                    if (world.func_147447_a(ray.field_72307_f, new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v), false, true, false) == null) {
                        final float f2 = Math.max(0.8f, entity.func_70111_Y());
                        final AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b((double)f2, (double)f2, (double)f2);
                        final RayTraceResult RayTraceResult = axisalignedbb.func_72327_a(entityVec, vec3d2);
                        if (axisalignedbb.func_72318_a(entityVec)) {
                            if (0.0 < d2 || d2 == 0.0) {
                                pointedEntity = entity;
                                d2 = 0.0;
                            }
                        }
                        else if (RayTraceResult != null) {
                            final double d3 = entityVec.func_72438_d(RayTraceResult.field_72307_f);
                            if (d3 < d2 || d2 == 0.0) {
                                pointedEntity = entity;
                                d2 = d3;
                            }
                        }
                    }
                }
            }
        }
        return pointedEntity;
    }
    
    public static RayTraceResult getPointedEntityRay(final World world, final Entity ignoreEntity, final Vec3d startVec, final Vec3d lookVec, final double minrange, final double range, final float padding, final boolean nonCollide) {
        RayTraceResult pointedEntityRay = null;
        final Vec3d vec3d2 = startVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
        final AxisAlignedBB bb = (ignoreEntity != null) ? ignoreEntity.func_174813_aQ() : new AxisAlignedBB(startVec, startVec).func_186662_g(0.5);
        final List list = world.func_72839_b(ignoreEntity, bb.func_72321_a(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range).func_72314_b((double)padding, (double)padding, (double)padding));
        double d2 = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (startVec.func_72438_d(entity.func_174791_d()) >= minrange) {
                if (entity.func_70067_L() || nonCollide) {
                    if (world.func_147447_a(startVec, new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v), false, true, false) == null) {
                        final float f2 = Math.max(0.8f, entity.func_70111_Y());
                        final AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b((double)f2, (double)f2, (double)f2);
                        final RayTraceResult rayTraceResult = axisalignedbb.func_72327_a(startVec, vec3d2);
                        if (axisalignedbb.func_72318_a(startVec)) {
                            if (0.0 < d2 || d2 == 0.0) {
                                pointedEntityRay = new RayTraceResult(entity, rayTraceResult.field_72307_f);
                                d2 = 0.0;
                            }
                        }
                        else if (rayTraceResult != null) {
                            final double d3 = startVec.func_72438_d(rayTraceResult.field_72307_f);
                            if (d3 < d2 || d2 == 0.0) {
                                pointedEntityRay = new RayTraceResult(entity, rayTraceResult.field_72307_f);
                                d2 = d3;
                            }
                        }
                    }
                }
            }
        }
        return pointedEntityRay;
    }
    
    public static Entity getPointedEntity(final World world, final EntityLivingBase player, final double range, final Class<?> clazz) {
        Entity pointedEntity = null;
        final Vec3d vec3d = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
        final Vec3d vec3d2 = player.func_70040_Z();
        final Vec3d vec3d3 = vec3d.func_72441_c(vec3d2.field_72450_a * range, vec3d2.field_72448_b * range, vec3d2.field_72449_c * range);
        final float f1 = 1.1f;
        final List list = world.func_72839_b((Entity)player, player.func_174813_aQ().func_72321_a(vec3d2.field_72450_a * range, vec3d2.field_72448_b * range, vec3d2.field_72449_c * range).func_72314_b((double)f1, (double)f1, (double)f1));
        double d2 = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = list.get(i);
            if (entity.func_70067_L() && world.func_147447_a(new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v), new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v), false, true, false) == null) {
                if (!clazz.isInstance(entity)) {
                    final float f2 = Math.max(0.8f, entity.func_70111_Y());
                    final AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b((double)f2, (double)f2, (double)f2);
                    final RayTraceResult RayTraceResult = axisalignedbb.func_72327_a(vec3d, vec3d3);
                    if (axisalignedbb.func_72318_a(vec3d)) {
                        if (0.0 < d2 || d2 == 0.0) {
                            pointedEntity = entity;
                            d2 = 0.0;
                        }
                    }
                    else if (RayTraceResult != null) {
                        final double d3 = vec3d.func_72438_d(RayTraceResult.field_72307_f);
                        if (d3 < d2 || d2 == 0.0) {
                            pointedEntity = entity;
                            d2 = d3;
                        }
                    }
                }
            }
        }
        return pointedEntity;
    }
    
    public static boolean canEntityBeSeen(final Entity entity, final TileEntity te) {
        return te.func_145831_w().func_147447_a(new Vec3d(te.func_174877_v().func_177958_n() + 0.5, te.func_174877_v().func_177956_o() + 1.25, te.func_174877_v().func_177952_p() + 0.5), new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), false, true, false) == null;
    }
    
    public static boolean canEntityBeSeen(final Entity entity, final double x, final double y, final double z) {
        return entity.field_70170_p.func_147447_a(new Vec3d(x, y, z), new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), false, true, false) == null;
    }
    
    public static boolean canEntityBeSeen(final Entity entity, final Entity entity2) {
        return entity.field_70170_p.func_147447_a(new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.field_70131_O / 2.0f, entity.field_70161_v), new Vec3d(entity2.field_70165_t, entity2.field_70163_u + entity2.field_70131_O / 2.0f, entity2.field_70161_v), false, true, false) == null;
    }
    
    public static void resetFloatCounter(final EntityPlayerMP player) {
        player.field_71135_a.field_147365_f = 0;
    }
    
    public static <T extends Entity> List<T> getEntitiesInRange(final World world, final BlockPos pos, final Entity entity, final Class<? extends T> classEntity, final double range) {
        return getEntitiesInRange(world, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, entity, classEntity, range);
    }
    
    public static <T extends Entity> List<T> getEntitiesInRange(final World world, final double x, final double y, final double z, final Entity entity, final Class<? extends T> classEntity, final double range) {
        final ArrayList<T> out = new ArrayList<T>();
        final List list = world.func_72872_a((Class)classEntity, new AxisAlignedBB(x, y, z, x, y, z).func_72314_b(range, range, range));
        if (list.size() > 0) {
            for (final Object e : list) {
                final Entity ent = (Entity)e;
                if (entity != null && entity.func_145782_y() == ent.func_145782_y()) {
                    continue;
                }
                out.add((T)ent);
            }
        }
        return out;
    }
    
    public static <T extends Entity> List<T> getEntitiesInRangeSorted(final World world, final Entity entity, final Class<? extends T> classEntity, final double range) {
        final List<T> list = getEntitiesInRange(world, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity, classEntity, range);
        final List<T> sl = list.stream().sorted((Comparator<? super Object>)new EntityDistComparator(entity)).collect((Collector<? super Object, ?, ArrayList<T>>)Collectors.toList());
        return sl;
    }
    
    public static boolean isVisibleTo(final float fov, final Entity ent, final Entity ent2, final float range) {
        final double[] x = { ent2.field_70165_t, ent2.func_174813_aQ().field_72338_b + ent2.field_70131_O / 2.0f, ent2.field_70161_v };
        final double[] t = { ent.field_70165_t, ent.func_174813_aQ().field_72338_b + ent.func_70047_e(), ent.field_70161_v };
        Vec3d q = ent.func_70040_Z();
        q = new Vec3d(q.field_72450_a * range, q.field_72448_b * range, q.field_72449_c * range);
        final Vec3d l = q.func_72441_c(ent.field_70165_t, ent.func_174813_aQ().field_72338_b + ent.func_70047_e(), ent.field_70161_v);
        final double[] b = { l.field_72450_a, l.field_72448_b, l.field_72449_c };
        return Utils.isLyingInCone(x, t, b, fov);
    }
    
    public static boolean isVisibleTo(final float fov, final Entity ent, final double xx, final double yy, final double zz, final float range) {
        final double[] x = { xx, yy, zz };
        final double[] t = { ent.field_70165_t, ent.func_174813_aQ().field_72338_b + ent.func_70047_e(), ent.field_70161_v };
        Vec3d q = ent.func_70040_Z();
        q = new Vec3d(q.field_72450_a * range, q.field_72448_b * range, q.field_72449_c * range);
        final Vec3d l = q.func_72441_c(ent.field_70165_t, ent.func_174813_aQ().field_72338_b + ent.func_70047_e(), ent.field_70161_v);
        final double[] b = { l.field_72450_a, l.field_72448_b, l.field_72449_c };
        return Utils.isLyingInCone(x, t, b, fov);
    }
    
    public static EntityItem entityDropSpecialItem(final Entity entity, final ItemStack stack, final float dropheight) {
        if (stack.func_190916_E() != 0 && stack.func_77973_b() != null) {
            final EntitySpecialItem entityitem = new EntitySpecialItem(entity.field_70170_p, entity.field_70165_t, entity.field_70163_u + dropheight, entity.field_70161_v, stack);
            entityitem.func_174869_p();
            entityitem.field_70181_x = 0.10000000149011612;
            entityitem.field_70159_w = 0.0;
            entityitem.field_70179_y = 0.0;
            if (entity.captureDrops) {
                entity.capturedDrops.add(entityitem);
            }
            else {
                entity.field_70170_p.func_72838_d((Entity)entityitem);
            }
            return entityitem;
        }
        return null;
    }
    
    public static void makeChampion(final EntityMob entity, final boolean persist) {
        try {
            if (entity.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() > -2.0) {
                return;
            }
        }
        catch (Exception e) {
            return;
        }
        int type = entity.field_70170_p.field_73012_v.nextInt(ChampionModifier.mods.length);
        if (entity instanceof EntityCreeper) {
            type = 0;
        }
        final IAttributeInstance modai = entity.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD);
        modai.func_111124_b(ChampionModifier.mods[type].attributeMod);
        modai.func_111121_a(ChampionModifier.mods[type].attributeMod);
        if (!(entity instanceof EntityThaumcraftBoss)) {
            final IAttributeInstance iattributeinstance = entity.func_110148_a(SharedMonsterAttributes.field_111267_a);
            iattributeinstance.func_111124_b(EntityUtils.CHAMPION_HEALTH);
            iattributeinstance.func_111121_a(EntityUtils.CHAMPION_HEALTH);
            final IAttributeInstance iattributeinstance2 = entity.func_110148_a(SharedMonsterAttributes.field_111264_e);
            iattributeinstance2.func_111124_b(EntityUtils.CHAMPION_DAMAGE);
            iattributeinstance2.func_111121_a(EntityUtils.CHAMPION_DAMAGE);
            entity.func_70691_i(25.0f);
            entity.func_96094_a(ChampionModifier.mods[type].getModNameLocalized() + " " + entity.func_70005_c_());
        }
        else {
            ((EntityThaumcraftBoss)entity).generateName();
        }
        if (persist) {
            entity.func_110163_bv();
        }
        switch (type) {
            case 0: {
                final IAttributeInstance sai = entity.func_110148_a(SharedMonsterAttributes.field_111263_d);
                sai.func_111124_b(EntityUtils.BOLDBUFF);
                sai.func_111121_a(EntityUtils.BOLDBUFF);
                break;
            }
            case 3: {
                final IAttributeInstance mai = entity.func_110148_a(SharedMonsterAttributes.field_111264_e);
                mai.func_111124_b(EntityUtils.MIGHTYBUFF);
                mai.func_111121_a(EntityUtils.MIGHTYBUFF);
                break;
            }
            case 5: {
                final int bh = (int)entity.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() / 2;
                entity.func_110149_m(entity.func_110139_bj() + bh);
                break;
            }
        }
    }
    
    public static void makeTainted(final EntityLivingBase target) {
        try {
            if (target.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD) != null && target.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() > -1.0) {
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        final int type = 13;
        final IAttributeInstance modai = target.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD);
        if (modai == null) {
            return;
        }
        if (target.func_110148_a(ThaumcraftApiHelper.CHAMPION_MOD).func_111126_e() == -1.0) {
            modai.func_111121_a(ChampionModifier.ATTRIBUTE_MINUS_ONE);
        }
        modai.func_111124_b(ChampionModifier.mods[type].attributeMod);
        modai.func_111121_a(ChampionModifier.mods[type].attributeMod);
        if (!(target instanceof EntityThaumcraftBoss)) {
            final IAttributeInstance iattributeinstance = target.func_110148_a(SharedMonsterAttributes.field_111267_a);
            iattributeinstance.func_111124_b(EntityUtils.HPBUFF[5]);
            iattributeinstance.func_111121_a(EntityUtils.HPBUFF[5]);
            final IAttributeInstance iattributeinstance2 = target.func_110148_a(SharedMonsterAttributes.field_111264_e);
            if (iattributeinstance2 == null) {
                target.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
                target.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
            }
            else {
                iattributeinstance2.func_111124_b(EntityUtils.DMGBUFF[0]);
                iattributeinstance2.func_111121_a(EntityUtils.DMGBUFF[0]);
            }
            target.func_70691_i(25.0f);
        }
        else {
            ((EntityThaumcraftBoss)target).generateName();
        }
    }
    
    static {
        CHAMPION_HEALTH = new AttributeModifier(UUID.fromString("a62bef38-48cc-42a6-ac5e-ef913841c4fd"), "Champion health buff", 100.0, 0);
        CHAMPION_DAMAGE = new AttributeModifier(UUID.fromString("a340d2db-d881-4c25-ac62-f0ad14cd63b0"), "Champion damage buff", 2.0, 2);
        BOLDBUFF = new AttributeModifier(UUID.fromString("4b1edd33-caa9-47ae-a702-d86c05701037"), "Bold speed boost", 0.3, 1);
        MIGHTYBUFF = new AttributeModifier(UUID.fromString("7163897f-07f5-49b3-9ce4-b74beb83d2d3"), "Mighty damage boost", 2.0, 2);
        HPBUFF = new AttributeModifier[] { new AttributeModifier(UUID.fromString("54d621c1-dd4d-4b43-8bd2-5531c8875797"), "HEALTH BUFF 1", 50.0, 0), new AttributeModifier(UUID.fromString("f51257dc-b7fa-4f7a-92d7-75d68e8592c4"), "HEALTH BUFF 2", 50.0, 0), new AttributeModifier(UUID.fromString("3d6b2e42-4141-4364-b76d-0e8664bbd0bb"), "HEALTH BUFF 3", 50.0, 0), new AttributeModifier(UUID.fromString("02c97a08-801c-4131-afa2-1427a6151934"), "HEALTH BUFF 4", 50.0, 0), new AttributeModifier(UUID.fromString("0f354f6a-33c5-40be-93be-81b1338567f1"), "HEALTH BUFF 5", 50.0, 0), new AttributeModifier(UUID.fromString("0f354f6a-33c5-40be-93be-81b1338567f1"), "HEALTH BUFF 6", 25.0, 0) };
        DMGBUFF = new AttributeModifier[] { new AttributeModifier(UUID.fromString("534f8c57-929a-48cf-bbd6-0fd851030748"), "DAMAGE BUFF 1", 0.5, 0), new AttributeModifier(UUID.fromString("d317a76e-0e7c-4c61-acfd-9fa286053b32"), "DAMAGE BUFF 2", 0.5, 0), new AttributeModifier(UUID.fromString("ff462d63-26a2-4363-830e-143ed97e2a4f"), "DAMAGE BUFF 3", 0.5, 0), new AttributeModifier(UUID.fromString("cf1eb39e-0c67-495f-887c-0d3080828d2f"), "DAMAGE BUFF 4", 0.5, 0), new AttributeModifier(UUID.fromString("3cfab9da-2701-43d8-ac07-885f16fa4117"), "DAMAGE BUFF 5", 0.5, 0) };
    }
    
    public static class EntityDistComparator implements Comparator<Entity>
    {
        private Entity source;
        
        public EntityDistComparator(final Entity source) {
            this.source = source;
        }
        
        @Override
        public int compare(final Entity a, final Entity b) {
            if (a.equals((Object)b)) {
                return 0;
            }
            final double da = this.source.func_174791_d().func_72436_e(a.func_174791_d());
            final double db = this.source.func_174791_d().func_72436_e(b.func_174791_d());
            return (da < db) ? -1 : ((da > db) ? 1 : 0);
        }
    }
}
