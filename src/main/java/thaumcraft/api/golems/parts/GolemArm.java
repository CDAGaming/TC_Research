package thaumcraft.api.golems.parts;

import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.api.golems.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class GolemArm
{
    protected static GolemArm[] arms;
    public byte id;
    public String key;
    public String[] research;
    public ResourceLocation icon;
    public Object[] components;
    public EnumGolemTrait[] traits;
    public IArmFunction function;
    public PartModel model;
    private static byte lastID;
    
    public GolemArm(final String key, final String[] research, final ResourceLocation icon, final PartModel model, final Object[] comp, final EnumGolemTrait[] tags) {
        this.key = key;
        this.research = research;
        this.icon = icon;
        this.components = comp;
        this.traits = tags;
        this.model = model;
        this.function = null;
    }
    
    public GolemArm(final String key, final String[] research, final ResourceLocation icon, final PartModel model, final Object[] comp, final IArmFunction function, final EnumGolemTrait[] tags) {
        this(key, research, icon, model, comp, tags);
        this.function = function;
    }
    
    public static void register(final GolemArm thing) {
        thing.id = GolemArm.lastID;
        ++GolemArm.lastID;
        if (thing.id >= GolemArm.arms.length) {
            final GolemArm[] temp = new GolemArm[thing.id + 1];
            System.arraycopy(GolemArm.arms, 0, temp, 0, GolemArm.arms.length);
            GolemArm.arms = temp;
        }
        GolemArm.arms[thing.id] = thing;
    }
    
    public String getLocalizedName() {
        return I18n.func_74838_a("golem.arm." + this.key.toLowerCase());
    }
    
    public String getLocalizedDescription() {
        return I18n.func_74838_a("golem.arm.text." + this.key.toLowerCase());
    }
    
    public static GolemArm[] getArms() {
        return GolemArm.arms;
    }
    
    static {
        GolemArm.arms = new GolemArm[1];
        GolemArm.lastID = 0;
    }
    
    public interface IArmFunction extends IGenericFunction
    {
        void onMeleeAttack(final IGolemAPI p0, final Entity p1);
        
        void onRangedAttack(final IGolemAPI p0, final EntityLivingBase p1, final float p2);
        
        EntityAIAttackRanged getRangedAttackAI(final IRangedAttackMob p0);
    }
}
