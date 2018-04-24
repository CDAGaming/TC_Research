package thaumcraft.api.aspects;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.internal.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import thaumcraft.api.*;
import java.util.*;

public class AspectHelper
{
    public static AspectList cullTags(final AspectList temp) {
        return cullTags(temp, 7);
    }
    
    public static AspectList cullTags(final AspectList temp, final int cap) {
        final AspectList temp2 = new AspectList();
        for (final Aspect tag : temp.getAspects()) {
            if (tag != null) {
                temp2.add(tag, temp.getAmount(tag));
            }
        }
        while (temp2 != null && temp2.size() > cap) {
            Aspect lowest = null;
            float low = 32767.0f;
            for (final Aspect tag2 : temp2.getAspects()) {
                if (tag2 != null) {
                    float ta = temp2.getAmount(tag2);
                    if (tag2.isPrimal()) {
                        ta *= 0.9f;
                    }
                    else {
                        if (!tag2.getComponents()[0].isPrimal()) {
                            ta *= 1.1f;
                            if (!tag2.getComponents()[0].getComponents()[0].isPrimal()) {
                                ta *= 1.05f;
                            }
                            if (!tag2.getComponents()[0].getComponents()[1].isPrimal()) {
                                ta *= 1.05f;
                            }
                        }
                        if (!tag2.getComponents()[1].isPrimal()) {
                            ta *= 1.1f;
                            if (!tag2.getComponents()[1].getComponents()[0].isPrimal()) {
                                ta *= 1.05f;
                            }
                            if (!tag2.getComponents()[1].getComponents()[1].isPrimal()) {
                                ta *= 1.05f;
                            }
                        }
                    }
                    if (ta < low) {
                        low = ta;
                        lowest = tag2;
                    }
                }
            }
            temp2.aspects.remove(lowest);
        }
        return temp2;
    }
    
    public static AspectList getObjectAspects(final ItemStack is) {
        return ThaumcraftApi.internalMethods.getObjectAspects(is);
    }
    
    public static AspectList generateTags(final ItemStack is) {
        return ThaumcraftApi.internalMethods.generateTags(is);
    }
    
    public static AspectList getEntityAspects(final Entity entity) {
        AspectList tags = null;
        if (entity instanceof EntityPlayer) {
            tags = new AspectList();
            tags.add(Aspect.MAN, 4);
            final Random rand = new Random(((EntityPlayer)entity).func_70005_c_().hashCode());
            final Aspect[] posa = Aspect.aspects.values().toArray(new Aspect[0]);
            tags.add(posa[rand.nextInt(posa.length)], 15);
            tags.add(posa[rand.nextInt(posa.length)], 15);
            tags.add(posa[rand.nextInt(posa.length)], 15);
        }
        else {
        Label_0119:
            for (final ThaumcraftApi.EntityTags et : CommonInternals.scanEntities) {
                if (!et.entityName.equals(EntityList.func_75621_b(entity))) {
                    continue;
                }
                if (et.nbts == null || et.nbts.length == 0) {
                    tags = et.aspects;
                }
                else {
                    final NBTTagCompound tc = new NBTTagCompound();
                    entity.func_189511_e(tc);
                    for (final ThaumcraftApi.EntityTagsNBT nbt : et.nbts) {
                        if (!tc.func_74764_b(nbt.name)) {
                            continue Label_0119;
                        }
                        if (!ThaumcraftApiHelper.getNBTDataFromId(tc, tc.func_150299_b(nbt.name), nbt.name).equals(nbt.value)) {
                            continue Label_0119;
                        }
                    }
                    tags = et.aspects;
                }
            }
        }
        return tags;
    }
    
    public static Aspect getCombinationResult(final Aspect aspect1, final Aspect aspect2) {
        final Collection<Aspect> aspects = Aspect.aspects.values();
        for (final Aspect aspect3 : aspects) {
            if (aspect3.getComponents() != null && ((aspect3.getComponents()[0] == aspect1 && aspect3.getComponents()[1] == aspect2) || (aspect3.getComponents()[0] == aspect2 && aspect3.getComponents()[1] == aspect1))) {
                return aspect3;
            }
        }
        return null;
    }
    
    public static Aspect getRandomPrimal(final Random rand, final Aspect aspect) {
        final ArrayList<Aspect> list = new ArrayList<Aspect>();
        if (aspect != null) {
            final AspectList temp = new AspectList();
            temp.add(aspect, 1);
            final AspectList temp2 = reduceToPrimals(temp);
            for (final Aspect a : temp2.getAspects()) {
                for (int b = 0; b < temp2.getAmount(a); ++b) {
                    list.add(a);
                }
            }
        }
        return (list.size() > 0) ? list.get(rand.nextInt(list.size())) : null;
    }
    
    public static AspectList reduceToPrimals(final AspectList in) {
        final AspectList out = new AspectList();
        for (final Aspect aspect : in.getAspects()) {
            if (aspect != null) {
                if (aspect.isPrimal()) {
                    out.add(aspect, in.getAmount(aspect));
                }
                else {
                    final AspectList temp = new AspectList();
                    temp.add(aspect.getComponents()[0], in.getAmount(aspect));
                    temp.add(aspect.getComponents()[1], in.getAmount(aspect));
                    final AspectList temp2 = reduceToPrimals(temp);
                    for (final Aspect a : temp2.getAspects()) {
                        out.add(a, temp2.getAmount(a));
                    }
                }
            }
        }
        return out;
    }
    
    public static AspectList getPrimalAspects(final AspectList in) {
        final AspectList t = new AspectList();
        t.add(Aspect.AIR, in.getAmount(Aspect.AIR));
        t.add(Aspect.FIRE, in.getAmount(Aspect.FIRE));
        t.add(Aspect.WATER, in.getAmount(Aspect.WATER));
        t.add(Aspect.EARTH, in.getAmount(Aspect.EARTH));
        t.add(Aspect.ORDER, in.getAmount(Aspect.ORDER));
        t.add(Aspect.ENTROPY, in.getAmount(Aspect.ENTROPY));
        return t;
    }
    
    public static AspectList getAuraAspects(final AspectList in) {
        final AspectList t = new AspectList();
        t.add(Aspect.AIR, in.getAmount(Aspect.AIR));
        t.add(Aspect.FIRE, in.getAmount(Aspect.FIRE));
        t.add(Aspect.WATER, in.getAmount(Aspect.WATER));
        t.add(Aspect.EARTH, in.getAmount(Aspect.EARTH));
        t.add(Aspect.ORDER, in.getAmount(Aspect.ORDER));
        t.add(Aspect.ENTROPY, in.getAmount(Aspect.ENTROPY));
        t.add(Aspect.FLUX, in.getAmount(Aspect.FLUX));
        return t;
    }
}
