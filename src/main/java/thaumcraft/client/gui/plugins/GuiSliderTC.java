package thaumcraft.client.gui.plugins;

import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.relauncher.*;

public class GuiSliderTC extends GuiButton
{
    private float sliderPosition;
    public boolean isMouseDown;
    private final String name;
    private final float min;
    private float max;
    private final boolean vertical;
    static ResourceLocation tex;
    
    public GuiSliderTC(final int idIn, final int x, final int y, final int w, final int h, final String name, final float min, final float max, final float defaultValue, final boolean vertical) {
        super(idIn, x, y, w, h, "");
        this.sliderPosition = 1.0f;
        this.name = name;
        this.min = min;
        this.max = max;
        this.sliderPosition = (defaultValue - min) / (max - min);
        this.vertical = vertical;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public float getMin() {
        return this.min;
    }
    
    public void setMax(final float max) {
        this.max = max;
        this.sliderPosition = 0.0f;
    }
    
    public float getSliderValue() {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }
    
    public void setSliderValue(final float p_175218_1_, final boolean p_175218_2_) {
        this.sliderPosition = (p_175218_1_ - this.min) / (this.max - this.min);
    }
    
    public float getSliderPosition() {
        return this.sliderPosition;
    }
    
    protected int func_146114_a(final boolean mouseOver) {
        return 0;
    }
    
    protected void func_146119_b(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.field_146125_m) {
            if (this.isMouseDown) {
                if (this.vertical) {
                    this.sliderPosition = (mouseY - (this.field_146129_i + 4)) / (this.field_146121_g - 8);
                }
                else {
                    this.sliderPosition = (mouseX - (this.field_146128_h + 4)) / (this.field_146120_f - 8);
                }
                if (this.sliderPosition < 0.0f) {
                    this.sliderPosition = 0.0f;
                }
                if (this.sliderPosition > 1.0f) {
                    this.sliderPosition = 1.0f;
                }
            }
            mc.func_110434_K().func_110577_a(GuiSliderTC.tex);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.vertical) {
                this.func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.sliderPosition * (this.field_146121_g - 8)), 20, 20, 8, 8);
            }
            else {
                this.func_73729_b(this.field_146128_h + (int)(this.sliderPosition * (this.field_146120_f - 8)), this.field_146129_i, 20, 20, 8, 8);
            }
        }
    }
    
    public void setSliderPosition(final float p_175219_1_) {
        this.sliderPosition = p_175219_1_;
    }
    
    public boolean func_146116_c(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.func_146116_c(mc, mouseX, mouseY)) {
            if (this.vertical) {
                this.sliderPosition = (mouseY - (this.field_146129_i + 4)) / (this.field_146121_g - 8);
            }
            else {
                this.sliderPosition = (mouseX - (this.field_146128_h + 4)) / (this.field_146120_f - 8);
            }
            if (this.sliderPosition < 0.0f) {
                this.sliderPosition = 0.0f;
            }
            if (this.sliderPosition > 1.0f) {
                this.sliderPosition = 1.0f;
            }
            return this.isMouseDown = true;
        }
        return false;
    }
    
    public void func_146118_a(final int mouseX, final int mouseY) {
        this.isMouseDown = false;
    }
    
    public void func_191745_a(final Minecraft mc, final int mouseX, final int mouseY, final float pt) {
        if (this.field_146125_m) {
            mc.func_110434_K().func_110577_a(GuiSliderTC.tex);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
            final int i = this.func_146114_a(this.field_146123_n);
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.func_179094_E();
            if (this.vertical) {
                GlStateManager.func_179109_b((float)(this.field_146128_h + 2), (float)this.field_146129_i, 0.0f);
                GlStateManager.func_179152_a(1.0f, this.field_146121_g / 32.0f, 1.0f);
                this.func_73729_b(0, 0, 240, 176, 4, 32);
            }
            else {
                GlStateManager.func_179109_b((float)this.field_146128_h, (float)(this.field_146129_i + 2), 0.0f);
                GlStateManager.func_179152_a(this.field_146120_f / 32.0f, 1.0f, 1.0f);
                this.func_73729_b(0, 0, 208, 176, 32, 4);
            }
            GlStateManager.func_179121_F();
            this.func_146119_b(mc, mouseX, mouseY);
        }
    }
    
    static {
        GuiSliderTC.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
    }
    
    @SideOnly(Side.CLIENT)
    public interface FormatHelper
    {
        String getText(final int p0, final String p1, final float p2);
    }
}
