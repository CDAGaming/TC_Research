package thaumcraft.client.gui.plugins;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import java.awt.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraft.client.renderer.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiHoverButton extends GuiButton
{
    String description;
    GuiScreen screen;
    int color;
    Object tex;
    
    public GuiHoverButton(final GuiScreen screen, final int buttonId, final int x, final int y, final int width, final int height, final String buttonText, final String description, final Object tex) {
        super(buttonId, x, y, width, height, buttonText);
        this.tex = null;
        this.description = description;
        this.tex = tex;
        this.screen = screen;
        this.color = 16777215;
    }
    
    public GuiHoverButton(final GuiScreen screen, final int buttonId, final int x, final int y, final int width, final int height, final String buttonText, final String description, final Object tex, final int color) {
        super(buttonId, x, y, width, height, buttonText);
        this.tex = null;
        this.description = description;
        this.tex = tex;
        this.screen = screen;
        this.color = color;
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float pt) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            final Color c = new Color(this.color);
            GlStateManager.func_179131_c(0.9f * (c.getRed() / 255.0f), 0.9f * (c.getGreen() / 255.0f), 0.9f * (c.getBlue() / 255.0f), 0.9f);
            this.field_146123_n = (xx >= this.field_146128_h - this.field_146120_f / 2 && yy >= this.field_146129_i - this.field_146121_g / 2 && xx < this.field_146128_h - this.field_146120_f / 2 + this.field_146120_f && yy < this.field_146129_i - this.field_146121_g / 2 + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            if (k == 2) {
                GlStateManager.func_179131_c(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
            }
            if (this.tex instanceof Aspect) {
                mc.func_110434_K().func_110577_a(((Aspect)this.tex).getImage());
                final Color c2 = new Color(((Aspect)this.tex).getColor());
                if (k != 2) {
                    GlStateManager.func_179131_c(c2.getRed() / 290.0f, c2.getGreen() / 290.0f, c2.getBlue() / 290.0f, 0.9f);
                }
                else {
                    GlStateManager.func_179131_c(c2.getRed() / 255.0f, c2.getGreen() / 255.0f, c2.getBlue() / 255.0f, 1.0f);
                }
                func_146110_a(this.field_146128_h - this.field_146120_f / 2, this.field_146129_i - this.field_146121_g / 2, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
            }
            if (this.tex instanceof ResourceLocation) {
                mc.func_110434_K().func_110577_a((ResourceLocation)this.tex);
                func_146110_a(this.field_146128_h - this.field_146120_f / 2, this.field_146129_i - this.field_146121_g / 2, 0.0f, 0.0f, 16, 16, 16.0f, 16.0f);
            }
            if (this.tex instanceof TextureAtlasSprite) {
                this.func_175175_a(this.field_146128_h - this.field_146120_f / 2, this.field_146129_i - this.field_146121_g / 2, (TextureAtlasSprite)this.tex, 16, 16);
            }
            if (this.tex instanceof ItemStack) {
                this.field_73735_i -= 90.0f;
                UtilsFX.renderItemStackShaded(mc, (ItemStack)this.tex, this.field_146128_h - this.field_146120_f / 2, this.field_146129_i - this.field_146121_g / 2 - ((k == 2) ? 1 : 0), null, 1.0f);
                this.field_73735_i += 90.0f;
            }
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    public void func_146111_b(final int xx, final int yy) {
        final FontRenderer fontrenderer = Minecraft.func_71410_x().field_71466_p;
        this.field_73735_i += 90.0f;
        List<String> text = new ArrayList<String>();
        if (this.tex instanceof ItemStack) {
            text = (List<String>)((ItemStack)this.tex).func_82840_a((EntityPlayer)Minecraft.func_71410_x().field_71439_g, (ITooltipFlag)(Minecraft.func_71410_x().field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL));
            int qq = 0;
            for (final String s : text) {
                if (s.endsWith(" " + TextFormatting.RESET)) {
                    text = text.subList(0, qq);
                    break;
                }
                ++qq;
            }
        }
        else {
            text.add(this.field_146126_j);
        }
        int m = 8;
        if (this.description != null) {
            m = 0;
            text.add("§o§9" + this.description);
        }
        UtilsFX.drawCustomTooltip(this.screen, fontrenderer, text, xx + 4, yy + m, -99);
        this.field_73735_i -= 90.0f;
        RenderHelper.func_74518_a();
        GlStateManager.func_179140_f();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public boolean func_146116_c(final Minecraft mc, final int mouseX, final int mouseY) {
        return false;
    }
}
