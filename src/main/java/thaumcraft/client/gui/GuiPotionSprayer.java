package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.devices.*;
import thaumcraft.common.container.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import thaumcraft.client.lib.*;
import thaumcraft.api.aspects.*;

@SideOnly(Side.CLIENT)
public class GuiPotionSprayer extends GuiContainer
{
    private TilePotionSprayer inventory;
    private ContainerPotionSprayer container;
    private EntityPlayer player;
    ResourceLocation tex;
    int startAspect;
    
    public GuiPotionSprayer(final InventoryPlayer par1InventoryPlayer, final TilePotionSprayer tilePotionSprayer) {
        super((Container)new ContainerPotionSprayer(par1InventoryPlayer, tilePotionSprayer));
        this.container = null;
        this.player = null;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_potion_sprayer.png");
        this.startAspect = 0;
        this.field_146999_f = 192;
        this.field_147000_g = 233;
        this.inventory = tilePotionSprayer;
        this.container = (ContainerPotionSprayer)this.field_147002_h;
        this.player = par1InventoryPlayer.field_70458_d;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146976_a(final float par1, final int mx, final int my) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.inventory.charges > 0) {
            final Color c = new Color(this.inventory.color);
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
            final int scroll = this.player.field_70173_aa % 256;
            this.func_73729_b(k + 128, l + 36 + (8 - this.inventory.charges) * 9, 232, scroll, 8, this.inventory.charges * 9);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.drawAspects(k, l);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        this.func_73729_b(k + 125, l + 28, 205, 28, 14, 88);
    }
    
    private void drawAspects(final int k, final int l) {
        int pos = 0;
        for (final Aspect aspect : this.inventory.recipe.getAspectsSortedByName()) {
            GL11.glPushMatrix();
            GL11.glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
            this.func_73729_b(k + 96 + 22 * (pos % 2), l + 46 + 16 * (pos / 2) - 14, 192, 56, 2, 14);
            final int i1 = (int)(this.inventory.recipeProgress.getAmount(aspect) / this.inventory.recipe.getAmount(aspect) * 14.0f);
            final Color c = new Color(aspect.getColor());
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
            this.func_73729_b(k + 96 + 22 * (pos % 2), l + 46 + 16 * (pos / 2) - i1, 192, 56, 2, i1);
            GL11.glPopMatrix();
            ++pos;
        }
        pos = 0;
        for (final Aspect aspect : this.inventory.recipe.getAspectsSortedByName()) {
            UtilsFX.drawTag(k + 79 + 22 * (pos % 2), l + 31 + 16 * (pos / 2), aspect, this.inventory.recipe.getAmount(aspect), 0, this.field_73735_i);
            ++pos;
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
