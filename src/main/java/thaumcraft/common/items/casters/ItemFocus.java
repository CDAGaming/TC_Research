package thaumcraft.common.items.casters;

import thaumcraft.common.items.*;
import java.awt.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.client.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.text.translation.*;
import java.util.*;
import net.minecraft.util.text.*;
import thaumcraft.api.casters.*;
import net.minecraft.item.*;

public class ItemFocus extends ItemTCBase
{
    private int maxComplexity;
    
    public ItemFocus(final String name, final int complexity) {
        super(name, new String[0]);
        this.field_77777_bU = 1;
        this.func_77656_e(0);
        this.maxComplexity = complexity;
    }
    
    public int getFocusColor(final ItemStack focusstack) {
        if (focusstack == null || focusstack.func_190926_b() || focusstack.func_77978_p() == null) {
            return 16777215;
        }
        int color = 16777215;
        if (!focusstack.func_77978_p().func_74764_b("color")) {
            final FocusPackage core = getPackage(focusstack);
            if (core != null) {
                final FocusEffect[] fe = core.getFocusEffects();
                int r = 0;
                int g = 0;
                int b = 0;
                for (final FocusEffect ef : fe) {
                    final Color c = new Color(FocusEngine.getElementColor(ef.getKey()));
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }
                r /= fe.length;
                g /= fe.length;
                b /= fe.length;
                final Color c2 = new Color(r, g, b);
                color = c2.getRGB();
                focusstack.func_77983_a("color", (NBTBase)new NBTTagInt(color));
            }
        }
        else {
            color = focusstack.func_77978_p().func_74762_e("color");
        }
        return color;
    }
    
    public String getSortingHelper(final ItemStack focusstack) {
        if (focusstack == null || focusstack.func_190926_b() || !focusstack.func_77942_o()) {
            return null;
        }
        int sh = focusstack.func_77978_p().func_74762_e("sort");
        if (sh == 0) {
            sh = getPackage(focusstack).getSortingHelper();
            focusstack.func_77983_a("sort", (NBTBase)new NBTTagInt(sh));
        }
        return focusstack.func_82833_r() + sh;
    }
    
    public static void setPackage(final ItemStack focusstack, final FocusPackage core) {
        final NBTTagCompound tag = core.serialize();
        focusstack.func_77983_a("package", (NBTBase)tag);
    }
    
    public static FocusPackage getPackage(final ItemStack focusstack) {
        final NBTTagCompound tag = focusstack.func_179543_a("package");
        if (tag != null) {
            final FocusPackage p = new FocusPackage();
            p.deserialize(tag);
            return p;
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        this.addFocusInformation(stack, worldIn, tooltip, flagIn);
    }
    
    @SideOnly(Side.CLIENT)
    public void addFocusInformation(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final FocusPackage p = getPackage(stack);
        if (p != null) {
            final float al = this.getVisCost(stack);
            final String amount = ItemStack.field_111284_a.format(al);
            tooltip.add(amount + " " + I18n.func_74838_a("item.Focus.cost1"));
            for (final IFocusElement fe : p.nodes) {
                if (fe instanceof FocusNode && !(fe instanceof FocusMediumRoot)) {
                    this.buildInfo(tooltip, (FocusNode)fe, 0);
                }
            }
        }
    }
    
    private void buildInfo(final List list, final FocusNode node, final int depth) {
        if (node instanceof FocusNode && !(node instanceof FocusMediumRoot)) {
            String t0 = "";
            for (int a = 0; a < depth; ++a) {
                t0 += "  ";
            }
            t0 = t0 + TextFormatting.DARK_PURPLE + I18n.func_74838_a(node.getUnlocalizedName());
            if (!node.getSettingList().isEmpty()) {
                t0 = t0 + TextFormatting.DARK_AQUA + " [";
                boolean q = false;
                for (final String st : node.getSettingList()) {
                    final NodeSetting ns = node.getSetting(st);
                    t0 = t0 + (q ? ", " : "") + ns.getLocalizedName() + " " + ns.getValueText();
                    q = true;
                }
                t0 += "]";
            }
            list.add(t0);
            if (node instanceof FocusModSplit) {
                final FocusModSplit split = (FocusModSplit)node;
                for (final FocusPackage p : split.getSplitPackages()) {
                    for (final IFocusElement fe : p.nodes) {
                        if (fe instanceof FocusNode && !(fe instanceof FocusMediumRoot)) {
                            this.buildInfo(list, (FocusNode)fe, depth + 1);
                        }
                    }
                }
            }
        }
    }
    
    public EnumRarity func_77613_e(final ItemStack focusstack) {
        return EnumRarity.RARE;
    }
    
    public float getVisCost(final ItemStack focusstack) {
        final FocusPackage p = getPackage(focusstack);
        return (p == null) ? 0.0f : (p.getComplexity() / 5.0f);
    }
    
    public int getActivationTime(final ItemStack focusstack) {
        final FocusPackage p = getPackage(focusstack);
        return (p == null) ? 0 : Math.max(5, p.getComplexity() / 4 * (p.getComplexity() / 4));
    }
    
    public int getMaxComplexity() {
        return this.maxComplexity;
    }
}
