package thaumcraft.client.gui.plugins;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiScrollButton extends GuiButton
{
    boolean minus;
    boolean vertical;
    static ResourceLocation tex;
    
    public GuiScrollButton(final int buttonId, final int x, final int y, final int width, final int height, final boolean minus, final boolean vertical) {
        super(buttonId, x, y, width, height, "");
        this.minus = false;
        this.vertical = false;
        this.minus = minus;
        this.vertical = vertical;
    }
    
    public GuiScrollButton(final int buttonId, final int x, final int y, final int width, final int height, final boolean minus) {
        super(buttonId, x, y, width, height, "");
        this.minus = false;
        this.vertical = false;
        this.minus = minus;
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float pt) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(GuiScrollButton.tex);
            GlStateManager.func_179131_c(0.9f, 0.9f, 0.9f, 0.9f);
            this.field_146123_n = (xx >= this.field_146128_h && yy >= this.field_146129_i && xx < this.field_146128_h + this.field_146120_f && yy < this.field_146129_i + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            if (k == 2) {
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            this.func_73729_b(this.field_146128_h, this.field_146129_i, this.vertical ? 67 : (this.minus ? 20 : 30), this.vertical ? (this.minus ? 0 : 10) : 0, 10, 10);
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    static {
        GuiScrollButton.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
    }
}
