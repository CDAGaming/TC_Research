package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.container.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import thaumcraft.api.crafting.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import java.awt.*;
import thaumcraft.client.lib.*;
import thaumcraft.api.aspects.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import thaumcraft.common.lib.*;

@SideOnly(Side.CLIENT)
public class GuiThaumatorium extends GuiContainer
{
    private TileThaumatorium inventory;
    private ContainerThaumatorium container;
    private int index;
    private int lastSize;
    private EntityPlayer player;
    ResourceLocation tex;
    int startAspect;
    
    public GuiThaumatorium(final InventoryPlayer par1InventoryPlayer, final TileThaumatorium par2TileEntityFurnace) {
        super((Container)new ContainerThaumatorium(par1InventoryPlayer, par2TileEntityFurnace));
        this.container = null;
        this.index = 0;
        this.lastSize = 0;
        this.player = null;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_thaumatorium.png");
        this.startAspect = 0;
        this.inventory = par2TileEntityFurnace;
        (this.container = (ContainerThaumatorium)this.field_147002_h).updateRecipes();
        this.lastSize = this.container.recipes.size();
        this.player = par1InventoryPlayer.field_70458_d;
        this.refreshIndex();
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    void refreshIndex() {
        if (this.inventory.recipeHash != null && this.container.recipes.size() > 0) {
            for (int a = 0; a < this.container.recipes.size(); ++a) {
                if (this.inventory.recipeHash.contains(this.container.recipes.get(a).hash)) {
                    this.index = a;
                    break;
                }
            }
        }
        this.startAspect = 0;
    }
    
    protected void func_146976_a(final float par1, final int mx, final int my) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.index >= this.container.recipes.size()) {
            this.index = this.container.recipes.size() - 1;
        }
        if (this.container.recipes.size() > 0) {
            if (this.lastSize != this.container.recipes.size()) {
                this.lastSize = this.container.recipes.size();
                this.refreshIndex();
            }
            if (this.index < 0) {
                this.index = 0;
            }
            if (this.container.recipes.size() > 1) {
                if (this.index > 0) {
                    this.func_73729_b(k + 128, l + 16, 192, 16, 16, 8);
                }
                else {
                    this.func_73729_b(k + 128, l + 16, 176, 16, 16, 8);
                }
                if (this.index < this.container.recipes.size() - 1) {
                    this.func_73729_b(k + 128, l + 24, 192, 24, 16, 8);
                }
                else {
                    this.func_73729_b(k + 128, l + 24, 176, 24, 16, 8);
                }
            }
            if (this.container.recipes.get(this.index).getAspects().size() > 6) {
                if (this.startAspect > 0) {
                    this.func_73729_b(k + 32, l + 40, 192, 32, 8, 16);
                }
                else {
                    this.func_73729_b(k + 32, l + 40, 176, 32, 8, 16);
                }
                if (this.startAspect < this.container.recipes.get(this.index).getAspects().size() - 1) {
                    this.func_73729_b(k + 136, l + 40, 200, 32, 8, 16);
                }
                else {
                    this.func_73729_b(k + 136, l + 40, 184, 32, 8, 16);
                }
            }
            else {
                this.startAspect = 0;
            }
            if (this.inventory.recipeHash != null && this.inventory.recipeHash.size() > 0) {
                final int x = mx - (k + 112);
                final int y = my - (l + 16);
                if ((x >= 0 && y >= 0 && x < 16 && y < 16) || this.inventory.recipeHash.contains(this.container.recipes.get(this.index).hash)) {
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    this.func_73729_b(k + 104, l + 8, 176, 96, 48, 48);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                }
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                final float alpha = 0.6f + MathHelper.func_76126_a(this.field_146297_k.field_71439_g.field_70173_aa / 5.0f) * 0.4f + 0.4f;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                this.func_73729_b(k + 88, l + 16, 176, 56, 24, 24);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
            this.drawAspects(k, l);
            this.drawOutput(k, l, mx, my);
            if (this.inventory.maxRecipes > 1) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(k + 136), (float)(l + 33), 0.0f);
                GL11.glScalef(0.5f, 0.5f, 0.0f);
                final String text = this.inventory.recipeHash.size() + "/" + this.inventory.maxRecipes;
                final int ll = this.field_146289_q.func_78256_a(text) / 2;
                this.field_146289_q.func_78276_b(text, -ll, 0, 16777215);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
    }
    
    private void drawAspects(final int k, final int l) {
        int count = 0;
        int pos = 0;
        if (this.inventory.recipeHash.contains(this.container.recipes.get(this.index).hash)) {
            for (final Aspect aspect : this.container.recipes.get(this.index).getAspects().getAspectsSortedByName()) {
                if (count >= this.startAspect) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.func_73729_b(k + 41 + 16 * pos, l + 57, 176, 8, 14, 6);
                    final int i1 = (int)(this.inventory.essentia.getAmount(aspect) / this.container.recipes.get(this.index).getAspects().getAmount(aspect) * 12.0f);
                    final Color c = new Color(aspect.getColor());
                    GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
                    this.func_73729_b(k + 42 + 16 * pos, l + 58, 176, 0, i1, 4);
                    GL11.glPopMatrix();
                    ++pos;
                }
                if (++count >= 6 + this.startAspect) {
                    break;
                }
            }
        }
        count = 0;
        pos = 0;
        for (final Aspect aspect : this.container.recipes.get(this.index).getAspects().getAspectsSortedByName()) {
            if (count >= this.startAspect) {
                UtilsFX.drawTag(k + 40 + 16 * pos, l + 40, aspect, this.container.recipes.get(this.index).getAspects().getAmount(aspect), 0, this.field_73735_i);
                ++pos;
            }
            if (++count >= 6 + this.startAspect) {
                break;
            }
        }
    }
    
    private void drawOutput(final int x, final int y, final int mx, final int my) {
        GL11.glPushMatrix();
        boolean dull = false;
        if (this.inventory.recipeHash.size() < this.inventory.maxRecipes || this.inventory.recipeHash.contains(this.container.recipes.get(this.index).hash)) {
            dull = true;
            final float alpha = 0.3f + MathHelper.func_76126_a(this.field_146297_k.field_71439_g.field_70173_aa / 4.0f) * 0.3f + 0.3f;
            GL11.glColor4f(0.5f, 0.5f, 0.5f, alpha);
        }
        GlStateManager.func_179094_E();
        RenderHelper.func_74520_c();
        GlStateManager.func_179140_f();
        GlStateManager.func_179091_B();
        GlStateManager.func_179142_g();
        GlStateManager.func_179145_e();
        this.field_146296_j.field_77023_b = 100.0f;
        this.field_146296_j.func_180450_b(this.container.recipes.get(this.index).getRecipeOutput(), x + 112, y + 16);
        this.field_146296_j.func_175030_a(this.field_146289_q, this.container.recipes.get(this.index).getRecipeOutput(), x + 112, y + 16);
        this.field_146296_j.field_77023_b = 0.0f;
        GlStateManager.func_179140_f();
        GlStateManager.func_179121_F();
        final int xx = mx - (x + 112);
        final int yy = my - (y + 16);
        if (xx >= 0 && yy >= 0 && xx < 16 && yy < 16) {
            this.func_146285_a(this.container.recipes.get(this.index).getRecipeOutput(), mx, my);
        }
        GL11.glDisable(3042);
        GL11.glDisable(2896);
        GL11.glPopMatrix();
    }
    
    protected void func_73864_a(final int mx, final int my, final int par3) throws IOException {
        super.func_73864_a(mx, my, par3);
        final int gx = (this.field_146294_l - this.field_146999_f) / 2;
        final int gy = (this.field_146295_m - this.field_147000_g) / 2;
        int x = mx - (gx + 112);
        int y = my - (gy + 16);
        if (this.container.recipes.size() > 0 && this.index >= 0 && this.index < this.container.recipes.size()) {
            if (x >= 0 && y >= 0 && x < 16 && y < 16 && (this.inventory.recipeHash.size() < this.inventory.maxRecipes || this.inventory.recipeHash.contains(this.container.recipes.get(this.index).hash))) {
                this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, this.index);
                this.playButtonSelect();
            }
            if (this.container.recipes.size() > 1) {
                if (this.index > 0) {
                    x = mx - (gx + 128);
                    y = my - (gy + 16);
                    if (x >= 0 && y >= 0 && x < 16 && y < 8) {
                        --this.index;
                        this.playButtonClick();
                    }
                }
                if (this.index < this.container.recipes.size() - 1) {
                    x = mx - (gx + 128);
                    y = my - (gy + 24);
                    if (x >= 0 && y >= 0 && x < 16 && y < 8) {
                        ++this.index;
                        this.playButtonClick();
                    }
                }
            }
            if (this.container.recipes.get(this.index).getAspects().size() > 6) {
                if (this.startAspect > 0) {
                    x = mx - (gx + 32);
                    y = my - (gy + 40);
                    if (x >= 0 && y >= 0 && x < 8 && y < 16) {
                        --this.startAspect;
                        this.playButtonClick();
                    }
                }
                if (this.startAspect < this.container.recipes.get(this.index).getAspects().size() - 1) {
                    x = mx - (gx + 136);
                    y = my - (gy + 40);
                    if (x >= 0 && y >= 0 && x < 8 && y < 16) {
                        ++this.startAspect;
                        this.playButtonClick();
                    }
                }
            }
        }
    }
    
    private void playButtonSelect() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.hhon, 0.3f, 1.0f);
    }
    
    private void playButtonClick() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.clack, 0.4f, 1.0f);
    }
}
