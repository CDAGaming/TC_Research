package thaumcraft.api.aspects;

import net.minecraft.util.*;
import thaumcraft.api.research.*;
import org.apache.commons.lang3.text.*;
import net.minecraft.util.text.translation.*;
import java.util.*;

public class Aspect
{
    String tag;
    Aspect[] components;
    int color;
    private String chatcolor;
    ResourceLocation image;
    int blend;
    public static HashMap<Integer, Aspect> mixList;
    private static ArrayList<Aspect> primals;
    private static ArrayList<Aspect> compounds;
    public static LinkedHashMap<String, Aspect> aspects;
    public static final Aspect AIR;
    public static final Aspect EARTH;
    public static final Aspect FIRE;
    public static final Aspect WATER;
    public static final Aspect ORDER;
    public static final Aspect ENTROPY;
    public static final Aspect VOID;
    public static final Aspect LIGHT;
    public static final Aspect MOTION;
    public static final Aspect COLD;
    public static final Aspect CRYSTAL;
    public static final Aspect METAL;
    public static final Aspect LIFE;
    public static final Aspect DEATH;
    public static final Aspect ENERGY;
    public static final Aspect EXCHANGE;
    public static final Aspect MAGIC;
    public static final Aspect AURA;
    public static final Aspect ALCHEMY;
    public static final Aspect FLUX;
    public static final Aspect DARKNESS;
    public static final Aspect ELDRITCH;
    public static final Aspect FLIGHT;
    public static final Aspect PLANT;
    public static final Aspect TOOL;
    public static final Aspect CRAFT;
    public static final Aspect MECHANISM;
    public static final Aspect TRAP;
    public static final Aspect SOUL;
    public static final Aspect MIND;
    public static final Aspect SENSES;
    public static final Aspect AVERSION;
    public static final Aspect PROTECT;
    public static final Aspect DESIRE;
    public static final Aspect UNDEAD;
    public static final Aspect BEAST;
    public static final Aspect MAN;
    
    public Aspect(final String tag, final int color, final Aspect[] components, final ResourceLocation image, final int blend) {
        if (Aspect.aspects.containsKey(tag)) {
            throw new IllegalArgumentException(tag + " already registered!");
        }
        this.tag = tag;
        this.components = components;
        this.color = color;
        this.image = image;
        this.blend = blend;
        Aspect.aspects.put(tag, this);
        ScanningManager.addScannableThing(new ScanAspect("!" + tag, this));
        if (components != null) {
            final int h = (components[0].getTag() + components[1].getTag()).hashCode();
            Aspect.mixList.put(h, this);
        }
    }
    
    public Aspect(final String tag, final int color, final Aspect[] components) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }
    
    public Aspect(final String tag, final int color, final Aspect[] components, final int blend) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }
    
    public Aspect(final String tag, final int color, final String chatcolor, final int blend) {
        this(tag, color, (Aspect[])null, blend);
        this.setChatcolor(chatcolor);
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getName() {
        return WordUtils.capitalizeFully(this.tag);
    }
    
    public String getLocalizedDescription() {
        return I18n.func_74838_a("tc.aspect." + this.tag);
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public Aspect[] getComponents() {
        return this.components;
    }
    
    public void setComponents(final Aspect[] components) {
        this.components = components;
    }
    
    public ResourceLocation getImage() {
        return this.image;
    }
    
    public static Aspect getAspect(final String tag) {
        return Aspect.aspects.get(tag);
    }
    
    public int getBlend() {
        return this.blend;
    }
    
    public void setBlend(final int blend) {
        this.blend = blend;
    }
    
    public boolean isPrimal() {
        return this.getComponents() == null || this.getComponents().length != 2;
    }
    
    public static ArrayList<Aspect> getPrimalAspects() {
        if (Aspect.primals.isEmpty()) {
            final Collection<Aspect> pa = Aspect.aspects.values();
            for (final Aspect aspect : pa) {
                if (aspect.isPrimal()) {
                    Aspect.primals.add(aspect);
                }
            }
        }
        return Aspect.primals;
    }
    
    public static ArrayList<Aspect> getCompoundAspects() {
        if (Aspect.compounds.isEmpty()) {
            final Collection<Aspect> pa = Aspect.aspects.values();
            for (final Aspect aspect : pa) {
                if (!aspect.isPrimal()) {
                    Aspect.compounds.add(aspect);
                }
            }
        }
        return Aspect.compounds;
    }
    
    public String getChatcolor() {
        return this.chatcolor;
    }
    
    public void setChatcolor(final String chatcolor) {
        this.chatcolor = chatcolor;
    }
    
    static {
        Aspect.mixList = new HashMap<Integer, Aspect>();
        Aspect.primals = new ArrayList<Aspect>();
        Aspect.compounds = new ArrayList<Aspect>();
        Aspect.aspects = new LinkedHashMap<String, Aspect>();
        AIR = new Aspect("aer", 16777086, "e", 1);
        EARTH = new Aspect("terra", 5685248, "2", 1);
        FIRE = new Aspect("ignis", 16734721, "c", 1);
        WATER = new Aspect("aqua", 3986684, "3", 1);
        ORDER = new Aspect("ordo", 14013676, "7", 1);
        ENTROPY = new Aspect("perditio", 4210752, "8", 771);
        VOID = new Aspect("vacuos", 8947848, new Aspect[] { Aspect.AIR, Aspect.ENTROPY }, 771);
        LIGHT = new Aspect("lux", 16777152, new Aspect[] { Aspect.AIR, Aspect.FIRE });
        MOTION = new Aspect("motus", 13487348, new Aspect[] { Aspect.AIR, Aspect.ORDER });
        COLD = new Aspect("gelum", 14811135, new Aspect[] { Aspect.FIRE, Aspect.ENTROPY });
        CRYSTAL = new Aspect("vitreus", 8454143, new Aspect[] { Aspect.EARTH, Aspect.AIR });
        METAL = new Aspect("metallum", 11908557, new Aspect[] { Aspect.EARTH, Aspect.ORDER });
        LIFE = new Aspect("victus", 14548997, new Aspect[] { Aspect.EARTH, Aspect.WATER });
        DEATH = new Aspect("mortuus", 6946821, new Aspect[] { Aspect.WATER, Aspect.ENTROPY });
        ENERGY = new Aspect("potentia", 12648447, new Aspect[] { Aspect.ORDER, Aspect.FIRE });
        EXCHANGE = new Aspect("permutatio", 5735255, new Aspect[] { Aspect.ENTROPY, Aspect.ORDER });
        MAGIC = new Aspect("praecantatio", 13566207, new Aspect[] { Aspect.ENERGY, Aspect.AIR });
        AURA = new Aspect("auram", 16761087, new Aspect[] { Aspect.MAGIC, Aspect.AIR });
        ALCHEMY = new Aspect("alkimia", 2337949, new Aspect[] { Aspect.MAGIC, Aspect.WATER });
        FLUX = new Aspect("vitium", 8388736, new Aspect[] { Aspect.ENTROPY, Aspect.MAGIC });
        DARKNESS = new Aspect("tenebrae", 2236962, new Aspect[] { Aspect.VOID, Aspect.LIGHT });
        ELDRITCH = new Aspect("alienis", 8409216, new Aspect[] { Aspect.VOID, Aspect.DARKNESS });
        FLIGHT = new Aspect("volatus", 15198167, new Aspect[] { Aspect.AIR, Aspect.MOTION });
        PLANT = new Aspect("herba", 109568, new Aspect[] { Aspect.LIFE, Aspect.EARTH });
        TOOL = new Aspect("instrumentum", 4210926, new Aspect[] { Aspect.METAL, Aspect.ENERGY });
        CRAFT = new Aspect("fabrico", 8428928, new Aspect[] { Aspect.EXCHANGE, Aspect.TOOL });
        MECHANISM = new Aspect("machina", 8421536, new Aspect[] { Aspect.MOTION, Aspect.TOOL });
        TRAP = new Aspect("vinculum", 10125440, new Aspect[] { Aspect.MOTION, Aspect.ENTROPY });
        SOUL = new Aspect("spiritus", 15461371, new Aspect[] { Aspect.LIFE, Aspect.DEATH });
        MIND = new Aspect("cognitio", 16356991, new Aspect[] { Aspect.FIRE, Aspect.SOUL });
        SENSES = new Aspect("sensus", 12648384, new Aspect[] { Aspect.AIR, Aspect.SOUL });
        AVERSION = new Aspect("aversio", 12603472, new Aspect[] { Aspect.SOUL, Aspect.ENTROPY });
        PROTECT = new Aspect("praemunio", 49344, new Aspect[] { Aspect.SOUL, Aspect.EARTH });
        DESIRE = new Aspect("desiderium", 15121988, new Aspect[] { Aspect.SOUL, Aspect.VOID });
        UNDEAD = new Aspect("exanimis", 3817472, new Aspect[] { Aspect.MOTION, Aspect.DEATH });
        BEAST = new Aspect("bestia", 10445833, new Aspect[] { Aspect.MOTION, Aspect.LIFE });
        MAN = new Aspect("humanus", 16766912, new Aspect[] { Aspect.SOUL, Aspect.LIFE });
    }
}
