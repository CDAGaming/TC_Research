package thaumcraft.common.entities.construct.golem.gui;

import thaumcraft.api.golems.seals.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.client.gui.*;

public class GuiGolemRedstoneButton extends GuiButton
{
    ISealEntity seal;
    static ResourceLocation tex;
    
    public GuiGolemRedstoneButton(final int buttonId, final int x, final int y, final int width, final int height, final ISealEntity seal) {
        super(buttonId, x, y, width, height, "");
        this.seal = seal;
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float partialTicks) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(GuiGolemRedstoneButton.tex);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = (xx >= this.field_146128_h && yy >= this.field_146129_i && xx < this.field_146128_h + this.field_146120_f && yy < this.field_146129_i + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            if (k == 2) {
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                GlStateManager.func_179131_c(0.9f, 0.9f, 0.9f, 0.9f);
            }
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            if (this.seal.isRedstoneSensitive()) {
                this.func_73729_b(this.field_146128_h, this.field_146129_i, 64, 136, 16, 16);
            }
            else {
                this.func_73729_b(this.field_146128_h, this.field_146129_i, 80, 136, 16, 16);
            }
            if (k == 2) {
                this.field_73735_i += 90.0f;
                final String s = this.seal.isRedstoneSensitive() ? I18n.func_74838_a("golem.prop.redon") : I18n.func_74838_a("golem.prop.redoff");
                this.func_73731_b(fontrenderer, s, this.field_146128_h - 2 - fontrenderer.func_78256_a(s), this.field_146129_i + 4, 16777215);
                this.field_73735_i -= 90.0f;
            }
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    static {
        GuiGolemRedstoneButton.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
    }
}
