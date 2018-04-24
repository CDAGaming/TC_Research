package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.devices.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import net.minecraft.util.text.translation.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;
import java.io.*;
import thaumcraft.common.lib.*;

@SideOnly(Side.CLIENT)
public class GuiSpa extends GuiContainer
{
    private TileSpa spa;
    private float xSize_lo;
    private float ySize_lo;
    ResourceLocation tex;
    
    public GuiSpa(final InventoryPlayer par1InventoryPlayer, final TileSpa teSpa) {
        super((Container)new ContainerSpa(par1InventoryPlayer, teSpa));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_spa.png");
        this.spa = teSpa;
    }
    
    public void func_73863_a(final int par1, final int par2, final float par3) {
        this.func_146276_q_();
        super.func_73863_a(par1, par2, par3);
        this.xSize_lo = par1;
        this.ySize_lo = par2;
        final int baseX = this.field_147003_i;
        final int baseY = this.field_147009_r;
        int mposx = par1 - (baseX + 104);
        int mposy = par2 - (baseY + 10);
        if (mposx >= 0 && mposy >= 0 && mposx < 10 && mposy < 55) {
            final List list = new ArrayList();
            final FluidStack fluid = this.spa.tank.getFluid();
            if (fluid != null) {
                list.add(fluid.getFluid().getLocalizedName(fluid));
                list.add(fluid.amount + " mb");
                this.drawHoveringText(list, par1, par2, this.field_146289_q);
            }
        }
        mposx = par1 - (baseX + 88);
        mposy = par2 - (baseY + 34);
        if (mposx >= 0 && mposy >= 0 && mposx < 10 && mposy < 10) {
            final List list = new ArrayList();
            if (this.spa.getMix()) {
                list.add(I18n.func_74838_a("text.spa.mix.true"));
            }
            else {
                list.add(I18n.func_74838_a("text.spa.mix.false"));
            }
            this.drawHoveringText(list, par1, par2, this.field_146289_q);
        }
        this.func_191948_b(par2, par2);
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.spa.getMix()) {
            this.func_73729_b(k + 89, l + 35, 208, 16, 8, 8);
        }
        else {
            this.func_73729_b(k + 89, l + 35, 208, 32, 8, 8);
        }
        if (this.spa.tank.getFluidAmount() > 0) {
            final FluidStack fluid = this.spa.tank.getFluid();
            if (fluid != null) {
                final TextureAtlasSprite icon = this.func_175371_a(fluid.getFluid().getBlock());
                if (icon != null) {
                    final float bar = this.spa.tank.getFluidAmount() / this.spa.tank.getCapacity();
                    this.renderFluid(icon, fluid.getFluid());
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex);
                    this.func_73729_b(k + 107, l + 15, 107, 15, 10, (int)(48.0f - 48.0f * bar));
                }
            }
        }
        this.func_73729_b(k + 106, l + 11, 232, 0, 10, 55);
        GL11.glDisable(3042);
    }
    
    private TextureAtlasSprite func_175371_a(final Block p_175371_1_) {
        return Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(p_175371_1_.func_176223_P());
    }
    
    public void renderFluid(final TextureAtlasSprite icon, final Fluid fluid) {
        GL11.glPushMatrix();
        this.field_146297_k.field_71446_o.func_110577_a(TextureMap.field_110575_b);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final Color cc = new Color(fluid.getColor());
        GL11.glColor3f(cc.getRed() / 255.0f, cc.getGreen() / 255.0f, cc.getBlue() / 255.0f);
        for (int a = 0; a < 6; ++a) {
            this.func_175175_a((this.field_147003_i + 107) * 2, (this.field_147009_r + 15) * 2 + a * 16, icon, 16, 16);
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    protected void func_73864_a(final int mx, final int my, final int par3) throws IOException {
        super.func_73864_a(mx, my, par3);
        final int gx = (this.field_146294_l - this.field_146999_f) / 2;
        final int gy = (this.field_146295_m - this.field_147000_g) / 2;
        final int var7 = mx - (gx + 89);
        final int var8 = my - (gy + 35);
        if (var7 >= 0 && var8 >= 0 && var7 < 8 && var8 < 8) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 1);
            this.playButtonClick();
        }
    }
    
    private void playButtonClick() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.clack, 0.4f, 1.0f);
    }
}
