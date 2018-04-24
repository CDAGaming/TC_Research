package thaumcraft.api.golems.seals;

public interface ISealGui
{
    public static final int CAT_PRIORITY = 0;
    public static final int CAT_FILTER = 1;
    public static final int CAT_AREA = 2;
    public static final int CAT_TOGGLES = 3;
    public static final int CAT_TAGS = 4;
    
    int[] getGuiCategories();
}
