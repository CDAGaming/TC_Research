package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;

@SideOnly(Side.CLIENT)
public class GuiArcaneBore extends GuiContainer
{
    EntityArcaneBore turret;
    ResourceLocation tex;
    
    public GuiArcaneBore(final InventoryPlayer par1InventoryPlayer, final World world, final EntityArcaneBore t) {
        super((Container)new ContainerArcaneBore(par1InventoryPlayer, world, t));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_arcanebore.png");
        this.field_146999_f = 175;
        this.field_147000_g = 232;
        this.turret = t;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int par1, final int par2) {
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        final int h = (int)(39.0f * (this.turret.func_110143_aJ() / this.turret.func_110138_aP()));
        this.func_73729_b(k + 68, l + 59, 192, 48, h, 6);
        if (this.turret.func_184614_ca() != null && this.turret.func_184614_ca().func_77952_i() + 1 >= this.turret.func_184614_ca().func_77958_k()) {
            this.func_73729_b(k + 80, l + 29, 240, 0, 16, 16);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(k + 124), (float)(l + 18), 505.0f);
        GL11.glScalef(0.5f, 0.5f, 0.0f);
        String text = "Width: " + (1 + this.turret.getDigRadius() * 2);
        this.field_146289_q.func_175063_a(text, 0.0f, 0.0f, 16777215);
        text = "Depth: " + this.turret.getDigDepth();
        this.field_146289_q.func_175063_a(text, 64.0f, 0.0f, 16777215);
        text = "Speed: +" + this.turret.getDigSpeed(Blocks.field_150348_b.func_176223_P());
        this.field_146289_q.func_175063_a(text, 0.0f, 10.0f, 16777215);
        int base = 0;
        final int refining = this.turret.getRefining();
        final int fortune = this.turret.getFortune();
        if (this.turret.hasSilkTouch() || refining > 0 || fortune > 0) {
            text = "Other properties:";
            this.field_146289_q.func_175063_a(text, 0.0f, 24.0f, 16777215);
        }
        if (refining > 0) {
            text = "Refining " + refining;
            this.field_146289_q.func_175063_a(text, 4.0f, (float)(34 + base), 12632256);
            base += 9;
        }
        if (fortune > 0) {
            text = "Fortune " + fortune;
            this.field_146289_q.func_175063_a(text, 4.0f, (float)(34 + base), 15648330);
            base += 9;
        }
        if (this.turret.hasSilkTouch()) {
            text = "Silk Touch";
            this.field_146289_q.func_175063_a(text, 4.0f, (float)(34 + base), 8421631);
            base += 9;
        }
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glDisable(3042);
    }
}
