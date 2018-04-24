package thaumcraft.common.entities.construct.golem.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiGolemCraftButton extends GuiButton
{
    static ResourceLocation tex;
    
    public GuiGolemCraftButton(final int buttonId, final int x, final int y) {
        super(buttonId, x, y, 24, 16, "");
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float partialTicks) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(GuiGolemCraftButton.tex);
            GlStateManager.func_179131_c(0.9f, 0.9f, 0.9f, 0.9f);
            this.field_146123_n = (xx >= this.field_146128_h && yy >= this.field_146129_i && xx < this.field_146128_h + this.field_146120_f && yy < this.field_146129_i + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            if (this.field_146124_l && k == 2) {
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            }
            this.func_73729_b(this.field_146128_h, this.field_146129_i, 216, 64, 24, 16);
            if (!this.field_146124_l) {
                this.func_73729_b(this.field_146128_h, this.field_146129_i, 216, 40, 24, 16);
            }
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    static {
        GuiGolemCraftButton.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_golembuilder.png");
    }
}
