package thaumcraft.common.entities.construct.golem.gui;

import thaumcraft.api.golems.seals.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.client.gui.*;

public class GuiGolemPropButton extends GuiButton
{
    ISealConfigToggles.SealToggle prop;
    static ResourceLocation tex;
    
    public GuiGolemPropButton(final int buttonId, final int x, final int y, final int width, final int height, final String buttonText, final ISealConfigToggles.SealToggle prop) {
        super(buttonId, x, y, width, height, buttonText);
        this.prop = prop;
    }
    
    public void func_191745_a(final Minecraft mc, final int xx, final int yy, final float partialTicks) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = mc.field_71466_p;
            mc.func_110434_K().func_110577_a(GuiGolemPropButton.tex);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = (xx >= this.field_146128_h && yy >= this.field_146129_i && xx < this.field_146128_h + this.field_146120_f && yy < this.field_146129_i + this.field_146121_g);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            GlStateManager.func_179112_b(770, 771);
            this.func_73729_b(this.field_146128_h - 2, this.field_146129_i - 2, 2, 18, 12, 12);
            if (this.prop.getValue()) {
                this.func_73729_b(this.field_146128_h - 2, this.field_146129_i - 2, 18, 18, 12, 12);
            }
            this.func_73731_b(fontrenderer, I18n.func_74838_a(this.field_146126_j), this.field_146128_h + 12, this.field_146129_i, 16777215);
            this.func_146119_b(mc, xx, yy);
        }
    }
    
    static {
        GuiGolemPropButton.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
    }
}
