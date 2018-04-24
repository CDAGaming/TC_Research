package thaumcraft.common.entities.construct.golem.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.math.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.client.gui.plugins.*;
import thaumcraft.api.golems.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;
import java.io.*;

@SideOnly(Side.CLIENT)
public class SealBaseGUI extends GuiContainer
{
    ISealEntity seal;
    int middleX;
    int middleY;
    int category;
    int[] categories;
    ResourceLocation tex;
    
    public SealBaseGUI(final InventoryPlayer player, final World world, final ISealEntity seal) {
        super((Container)new SealBaseContainer(player, world, seal));
        this.category = -1;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
        this.seal = seal;
        this.field_146999_f = 176;
        this.field_147000_g = 232;
        this.middleX = this.field_146999_f / 2;
        this.middleY = (this.field_147000_g - 72) / 2 - 8;
        if (seal.getSeal() instanceof ISealGui) {
            this.categories = ((ISealGui)seal.getSeal()).getGuiCategories();
        }
        else {
            this.categories = new int[] { 0, 4 };
        }
    }
    
    private ModelManager getModelmanager() {
        return Minecraft.func_71410_x().field_175617_aL;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146296_j = new CustomRenderItem();
        this.setupCategories();
    }
    
    private void setupCategories() {
        this.field_146292_n.clear();
        int c = 0;
        float slice = 60.0f / this.categories.length;
        final float start = -180.0f + (this.categories.length - 1) * slice / 2.0f;
        if (slice > 24.0f) {
            slice = 24.0f;
        }
        if (slice < 12.0f) {
            slice = 12.0f;
        }
        for (final int cat : this.categories) {
            if (this.category < 0) {
                this.category = cat;
            }
            if (this.categories.length > 1) {
                final int xx = (int)(MathHelper.func_76134_b((start - c * slice) / 180.0f * 3.1415927f) * 86.0f);
                final int yy = (int)(MathHelper.func_76126_a((start - c * slice) / 180.0f * 3.1415927f) * 86.0f);
                this.field_146292_n.add(new GuiGolemCategoryButton(c, this.field_147003_i + this.middleX + xx, this.field_147009_r + this.middleY + yy, 16, 16, "button.category." + cat, cat, this.category == cat));
            }
            ++c;
        }
        final int xx2 = (int)(MathHelper.func_76134_b((start - c * slice) / 180.0f * 3.1415927f) * 86.0f);
        final int yy2 = (int)(MathHelper.func_76126_a((start - c * slice) / 180.0f * 3.1415927f) * 86.0f);
        this.field_146292_n.add(new GuiGolemRedstoneButton(27, this.field_147003_i + this.middleX + xx2 - 8, this.field_147009_r + this.middleY + yy2 - 8, 16, 16, this.seal));
        switch (this.category) {
            case 0: {
                this.field_146292_n.add(new GuiPlusMinusButton(80, this.field_147003_i + this.middleX - 5 - 14, this.field_147009_r + this.middleY - 17, 10, 10, true));
                this.field_146292_n.add(new GuiPlusMinusButton(81, this.field_147003_i + this.middleX - 5 + 14, this.field_147009_r + this.middleY - 17, 10, 10, false));
                this.field_146292_n.add(new GuiPlusMinusButton(82, this.field_147003_i + this.middleX + 18 - 12, this.field_147009_r + this.middleY + 4, 10, 10, true));
                this.field_146292_n.add(new GuiPlusMinusButton(83, this.field_147003_i + this.middleX + 18 + 11, this.field_147009_r + this.middleY + 4, 10, 10, false));
                this.field_146292_n.add(new GuiGolemLockButton(25, this.field_147003_i + this.middleX - 32, this.field_147009_r + this.middleY, 16, 16, this.seal));
                break;
            }
            case 1: {
                if (this.seal.getSeal() instanceof ISealConfigFilter) {
                    final int s = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
                    final int sy = 16 + (s - 1) / 3 * 12;
                    this.field_146292_n.add(new GuiGolemBWListButton(20, this.field_147003_i + this.middleX - 8, this.field_147009_r + this.middleY + (s - 1) / 3 * 24 - sy + 27, 16, 16, (ISealConfigFilter)this.seal.getSeal()));
                    break;
                }
                break;
            }
            case 2: {
                this.field_146292_n.add(new GuiPlusMinusButton(90, this.field_147003_i + this.middleX - 5 - 14, this.field_147009_r + this.middleY - 25, 10, 10, true));
                this.field_146292_n.add(new GuiPlusMinusButton(91, this.field_147003_i + this.middleX - 5 + 14, this.field_147009_r + this.middleY - 25, 10, 10, false));
                this.field_146292_n.add(new GuiPlusMinusButton(92, this.field_147003_i + this.middleX - 5 - 14, this.field_147009_r + this.middleY, 10, 10, true));
                this.field_146292_n.add(new GuiPlusMinusButton(93, this.field_147003_i + this.middleX - 5 + 14, this.field_147009_r + this.middleY, 10, 10, false));
                this.field_146292_n.add(new GuiPlusMinusButton(94, this.field_147003_i + this.middleX - 5 - 14, this.field_147009_r + this.middleY + 25, 10, 10, true));
                this.field_146292_n.add(new GuiPlusMinusButton(95, this.field_147003_i + this.middleX - 5 + 14, this.field_147009_r + this.middleY + 25, 10, 10, false));
                break;
            }
            case 3: {
                if (this.seal.getSeal() instanceof ISealConfigToggles) {
                    final ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
                    final int s2 = (cp.getToggles().length < 4) ? 8 : ((cp.getToggles().length < 6) ? 7 : ((cp.getToggles().length < 9) ? 6 : 5));
                    final int h = (cp.getToggles().length - 1) * s2;
                    int w = 12;
                    for (final ISealConfigToggles.SealToggle prop : cp.getToggles()) {
                        int ww = 12 + Math.min(100, this.field_146289_q.func_78256_a(I18n.func_74838_a(prop.getName())));
                        ww /= 2;
                        if (ww > w) {
                            w = ww;
                        }
                    }
                    int p = 0;
                    for (final ISealConfigToggles.SealToggle prop2 : cp.getToggles()) {
                        this.field_146292_n.add(new GuiGolemPropButton(30 + p, this.field_147003_i + this.middleX - w, this.field_147009_r + this.middleY - 5 - h + p * (s2 * 2), 8, 8, prop2.getName(), prop2));
                        ++p;
                    }
                    break;
                }
                break;
            }
            case 4: {
                EnumGolemTrait[] tags = this.seal.getSeal().getRequiredTags();
                if (tags != null && tags.length > 0) {
                    int p2 = 0;
                    for (final EnumGolemTrait tag : tags) {
                        this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 500 + p2, this.field_147003_i + this.middleX + p2 * 18 - (tags.length - 1) * 9, this.field_147009_r + this.middleY - 8, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
                        ++p2;
                    }
                }
                tags = this.seal.getSeal().getForbiddenTags();
                if (tags != null && tags.length > 0) {
                    int p2 = 0;
                    for (final EnumGolemTrait tag : tags) {
                        this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 600 + p2, this.field_147003_i + this.middleX + p2 * 18 - (tags.length - 1) * 9, this.field_147009_r + this.middleY + 24, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
                        ++p2;
                    }
                    break;
                }
                break;
            }
        }
    }
    
    protected boolean func_146983_a(final int par1) {
        return false;
    }
    
    protected void func_146976_a(final float par1, final int mouseX, final int mouseY) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_73729_b(this.field_147003_i + this.middleX - 80, this.field_147009_r + this.middleY - 80, 96, 0, 160, 160);
        this.func_73729_b(this.field_147003_i, this.field_147009_r + 143, 0, 167, 176, 89);
        this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.category." + this.category), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 64, 16777215);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        switch (this.category) {
            case 0: {
                this.func_73729_b(this.field_147003_i + this.middleX + 17, this.field_147009_r + this.middleY + 3, 2, 18, 12, 12);
                if (this.seal.getColor() >= 1 && this.seal.getColor() <= 16) {
                    final Color c = new Color(EnumDyeColor.func_176764_b(this.seal.getColor() - 1).func_193350_e());
                    GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 1.0f);
                    this.func_73729_b(this.field_147003_i + this.middleX + 20, this.field_147009_r + this.middleY + 6, 74, 31, 6, 6);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                final int mx = mouseX - this.field_147003_i;
                final int my = mouseY - this.field_147009_r;
                if (mx >= this.middleX + 5 && mx <= this.middleX + 41 && my >= this.middleY + 3 && my <= this.middleY + 15) {
                    if (this.seal.getColor() >= 1 && this.seal.getColor() <= 16) {
                        final String s = "color." + EnumDyeColor.func_176764_b(this.seal.getColor() - 1).func_176610_l();
                        String s2 = I18n.func_74838_a("golem.prop.color");
                        s2 = s2.replace("%s", I18n.func_74838_a(s));
                        this.func_73732_a(this.field_146289_q, s2, this.field_147003_i + this.middleX + 23, this.field_147009_r + this.middleY + 17, 16777215);
                    }
                    else {
                        this.func_73732_a(this.field_146289_q, I18n.func_74838_a("golem.prop.colorall"), this.field_147003_i + this.middleX + 23, this.field_147009_r + this.middleY + 17, 16777215);
                    }
                }
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("golem.prop.priority"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 28, 12299007);
                this.func_73732_a(this.field_146289_q, "" + this.seal.getPriority(), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 16, 16777215);
                if (this.seal.getOwner().equals(this.field_146297_k.field_71439_g.func_110124_au().toString())) {
                    this.func_73732_a(this.field_146289_q, I18n.func_74838_a("golem.prop.owner"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY + 32, 12299007);
                    break;
                }
                break;
            }
            case 1: {
                if (this.seal.getSeal() instanceof ISealConfigFilter) {
                    final int s3 = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
                    final int sx = 16 + (s3 - 1) % 3 * 12;
                    final int sy = 16 + (s3 - 1) / 3 * 12;
                    for (int a = 0; a < s3; ++a) {
                        final int x = a % 3;
                        final int y = a / 3;
                        this.func_73729_b(this.field_147003_i + this.middleX + x * 24 - sx, this.field_147009_r + this.middleY + y * 24 - sy, 0, 56, 32, 32);
                    }
                    break;
                }
                break;
            }
            case 2: {
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.caption.y"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 24 - 9, 14540253);
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.caption.x"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 9, 14540253);
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.caption.z"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY + 24 - 9, 14540253);
                this.func_73732_a(this.field_146289_q, "" + this.seal.getArea().func_177956_o(), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 24, 16777215);
                this.func_73732_a(this.field_146289_q, "" + this.seal.getArea().func_177958_n(), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY, 16777215);
                this.func_73732_a(this.field_146289_q, "" + this.seal.getArea().func_177952_p(), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY + 24, 16777215);
                break;
            }
            case 4: {
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.caption.required"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY - 26, 14540253);
                this.func_73732_a(this.field_146289_q, I18n.func_74838_a("button.caption.forbidden"), this.field_147003_i + this.middleX, this.field_147009_r + this.middleY + 6, 14540253);
                break;
            }
        }
    }
    
    protected void func_146979_b(final int mouseX, final int mouseY) {
        RenderHelper.func_74518_a();
        for (final GuiButton guibutton : this.field_146292_n) {
            if (guibutton.func_146115_a()) {
                guibutton.func_146111_b(mouseX - this.field_147003_i, mouseY - this.field_147009_r);
                break;
            }
        }
        RenderHelper.func_74520_c();
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k < this.categories.length && this.categories[button.field_146127_k] != this.category) {
            this.category = this.categories[button.field_146127_k];
            ((SealBaseContainer)this.field_147002_h).category = this.category;
            ((SealBaseContainer)this.field_147002_h).setupCategories();
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, button.field_146127_k);
            this.setupCategories();
        }
        else if (this.category == 0 && button.field_146127_k == 25 && this.seal.getOwner().equals(this.field_146297_k.field_71439_g.func_110124_au().toString())) {
            this.seal.setLocked(!this.seal.isLocked());
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, this.seal.isLocked() ? 25 : 26);
        }
        else if (this.category == 1 && this.seal.getSeal() instanceof ISealConfigFilter && button.field_146127_k == 20) {
            final ISealConfigFilter cp = (ISealConfigFilter)this.seal.getSeal();
            cp.setBlacklist(!cp.isBlacklist());
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, cp.isBlacklist() ? 20 : 21);
        }
        else if (this.category == 3 && this.seal.getSeal() instanceof ISealConfigToggles && button.field_146127_k >= 30 && button.field_146127_k < 30 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length) {
            final ISealConfigToggles cp2 = (ISealConfigToggles)this.seal.getSeal();
            cp2.setToggle(button.field_146127_k - 30, !cp2.getToggles()[button.field_146127_k - 30].getValue());
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, cp2.getToggles()[button.field_146127_k - 30].getValue() ? button.field_146127_k : (button.field_146127_k + 30));
        }
        else if (button.field_146127_k == 27 && this.seal.getOwner().equals(this.field_146297_k.field_71439_g.func_110124_au().toString())) {
            this.seal.setRedstoneSensitive(!this.seal.isRedstoneSensitive());
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, this.seal.isRedstoneSensitive() ? 27 : 28);
        }
        else {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, button.field_146127_k);
        }
    }
}
