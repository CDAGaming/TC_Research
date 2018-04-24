package thaumcraft.api.golems;

import net.minecraft.util.*;
import net.minecraft.util.text.translation.*;

public enum EnumGolemTrait
{
    SMART(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_smart.png")), 
    DEFT(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_deft.png")), 
    CLUMSY(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_clumsy.png")), 
    FIGHTER(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_fighter.png")), 
    WHEELED(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_wheeled.png")), 
    FLYER(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_flyer.png")), 
    CLIMBER(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_climber.png")), 
    HEAVY(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_heavy.png")), 
    LIGHT(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_light.png")), 
    FRAGILE(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_fragile.png")), 
    REPAIR(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_repair.png")), 
    SCOUT(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_scout.png")), 
    ARMORED(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_armored.png")), 
    BRUTAL(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_brutal.png")), 
    FIREPROOF(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_fireproof.png")), 
    BREAKER(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_breaker.png")), 
    HAULER(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_hauler.png")), 
    RANGED(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_ranged.png")), 
    BLASTPROOF(new ResourceLocation("thaumcraft", "textures/misc/golem/tag_blastproof.png"));
    
    public ResourceLocation icon;
    public EnumGolemTrait opposite;
    
    private EnumGolemTrait(final ResourceLocation icon) {
        this.icon = icon;
    }
    
    public String getLocalizedName() {
        return I18n.func_74838_a("golem.trait." + this.name().toLowerCase());
    }
    
    public String getLocalizedDescription() {
        return I18n.func_74838_a("golem.trait.text." + this.name().toLowerCase());
    }
    
    static {
        EnumGolemTrait.CLUMSY.opposite = EnumGolemTrait.DEFT;
        EnumGolemTrait.DEFT.opposite = EnumGolemTrait.CLUMSY;
        EnumGolemTrait.HEAVY.opposite = EnumGolemTrait.LIGHT;
        EnumGolemTrait.LIGHT.opposite = EnumGolemTrait.HEAVY;
        EnumGolemTrait.FRAGILE.opposite = EnumGolemTrait.ARMORED;
        EnumGolemTrait.ARMORED.opposite = EnumGolemTrait.FRAGILE;
    }
}
