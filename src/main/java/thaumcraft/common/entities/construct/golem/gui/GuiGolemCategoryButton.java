package thaumcraft.common.entities.construct.golem.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.client.gui.*;

class GuiGolemCategoryButton extends GuiButton
{
    int icon;
    boolean active;
    static ResourceLocation tex;
    
    public GuiGolemCategoryButton(final int buttonId, final int x, final int y, final int width, final int height, final String buttonText, final int i, final boolean act) {
        super(buttonId, x, y, width, height, buttonText);
        this.icon = i;
        this.active = act;
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float partialTicks) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(GuiGolemCategoryButton.tex);
            GlStateManager.func_179131_c(0.9f, 0.9f, 0.9f, 0.9f);
            this.field_146123_n = (xx >= this.field_146128_h - 8 && yy >= this.field_146129_i - 8 && xx < this.field_146128_h - 8 + this.field_146120_f && yy < this.field_146129_i - 8 + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            if (this.active) {
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            }
            else if (k != 2) {
                GlStateManager.func_179131_c(0.7f, 0.7f, 0.7f, 0.7f);
            }
            this.func_73729_b(this.field_146128_h - 8, this.field_146129_i - 8, this.icon * 16, 120, 16, 16);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (k == 2) {
                this.field_73735_i += 90.0f;
                final String s = I18n.func_74838_a(this.field_146126_j);
                this.func_73731_b(fontrenderer, s, this.field_146128_h - 10 - fontrenderer.func_78256_a(s), this.field_146129_i - 4, 16777215);
                this.field_73735_i -= 90.0f;
            }
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    public boolean func_146116_c(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h - 8 && mouseY >= this.field_146129_i - 8 && mouseX < this.field_146128_h - 8 + this.field_146120_f && mouseY < this.field_146129_i - 8 + this.field_146121_g;
    }
    
    static {
        GuiGolemCategoryButton.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
    }
}
