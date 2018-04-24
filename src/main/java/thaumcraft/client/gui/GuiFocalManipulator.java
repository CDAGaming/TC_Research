package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import java.text.*;
import net.minecraft.item.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import net.minecraft.util.text.translation.*;
import java.io.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.text.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import thaumcraft.client.lib.*;
import thaumcraft.api.casters.*;
import thaumcraft.client.gui.plugins.*;
import java.util.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.api.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.lib.network.playerdata.*;
import thaumcraft.api.aspects.*;

@SideOnly(Side.CLIENT)
public class GuiFocalManipulator extends GuiContainer
{
    private TileFocalManipulator table;
    private float xSize_lo;
    private float ySize_lo;
    private int isMouseButtonDown;
    protected int mouseX;
    protected int mouseY;
    protected double curMouseX;
    protected double curMouseY;
    ResourceLocation tex;
    ResourceLocation tex2;
    ResourceLocation tex3;
    ResourceLocation texbase;
    GuiImageButton buttonConfirm;
    GuiSliderTC scrollbarParts;
    GuiSliderTC scrollbarMainSide;
    GuiSliderTC scrollbarMainBottom;
    private GuiTextField nameField;
    int totalComplexity;
    int maxComplexity;
    int lastNodeHover;
    DecimalFormat myFormatter;
    ArrayList<String> shownParts;
    int partsStart;
    ItemStack[] components;
    boolean valid;
    static ResourceLocation iMedium;
    static ResourceLocation iEffect;
    private int nodeID;
    int sMinX;
    int sMinY;
    int sMaxX;
    int sMaxY;
    int selectedNode;
    float costCast;
    int costXp;
    int costVis;
    int scrollX;
    int scrollY;
    
    public GuiFocalManipulator(final InventoryPlayer par1InventoryPlayer, final TileFocalManipulator table) {
        super((Container)new ContainerFocalManipulator(par1InventoryPlayer, table));
        this.isMouseButtonDown = 0;
        this.mouseX = 0;
        this.mouseY = 0;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_wandtable.png");
        this.tex2 = new ResourceLocation("thaumcraft", "textures/gui/gui_wandtable2.png");
        this.tex3 = new ResourceLocation("thaumcraft", "textures/gui/gui_wandtable3.png");
        this.texbase = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
        this.buttonConfirm = new GuiImageButton((GuiScreen)this, 0, this.field_147003_i + 232, this.field_147009_r + 7, 24, 16, null, I18n.func_74838_a("wandtable.text3"), this.texbase, 232, 240, 24, 16);
        this.totalComplexity = 0;
        this.maxComplexity = 0;
        this.lastNodeHover = -1;
        this.myFormatter = new DecimalFormat("#######.##");
        this.shownParts = new ArrayList<String>();
        this.partsStart = 0;
        this.components = null;
        this.valid = false;
        this.nodeID = 0;
        this.sMinX = 0;
        this.sMinY = 0;
        this.sMaxX = 0;
        this.sMaxY = 0;
        this.selectedNode = -1;
        this.costCast = 0.0f;
        this.costXp = 0;
        this.costVis = 0;
        this.scrollX = 0;
        this.scrollY = 0;
        this.table = table;
        this.field_146999_f = 231;
        this.field_147000_g = 231;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.gatherInfo(false);
        Keyboard.enableRepeatEvents(true);
        final int i = (this.field_146294_l - this.field_146999_f) / 2;
        final int j = (this.field_146295_m - this.field_147000_g) / 2;
        (this.nameField = new GuiTextField(2, this.field_146289_q, i + 30, j + 11, 170, 12)).func_146193_g(-1);
        this.nameField.func_146204_h(-1);
        this.nameField.func_146185_a(false);
        this.nameField.func_146203_f(50);
        this.nameField.func_146180_a(this.table.focusName);
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k == 0) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 0);
        }
        else if (button.field_146127_k >= 10) {
            this.gatherInfo(true);
        }
        else {
            super.func_146284_a(button);
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (this.nameField.func_146201_a(typedChar, keyCode)) {
            this.table.focusName = this.nameField.func_146179_b();
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketFocusNameToServer(this.table.func_174877_v(), this.table.focusName));
        }
        else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
    
    public void func_73863_a(final int mx, final int my, final float par3) {
        this.func_146276_q_();
        super.func_73863_a(mx, my, par3);
        GL11.glBlendFunc(770, 771);
        this.xSize_lo = mx;
        this.ySize_lo = my;
        final int gx = (this.field_146294_l - this.field_146999_f) / 2;
        final int gy = (this.field_146295_m - this.field_147000_g) / 2;
        if (Mouse.isButtonDown(0)) {
            if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && this.func_146978_c(this.field_147003_i + 63, this.field_147009_r + 31, 136, 160, mx + this.field_147003_i, my + this.field_147009_r)) {
                if (this.isMouseButtonDown == 0) {
                    this.isMouseButtonDown = 1;
                }
                else {
                    final boolean b = this.scrollbarParts != null && this.scrollbarParts.func_146115_a();
                    if (this.scrollbarMainBottom != null && !this.scrollbarMainBottom.func_146115_a() && !b) {
                        this.scrollX -= mx - this.mouseX;
                        this.curMouseX = this.scrollX;
                        if (this.scrollX > this.scrollbarMainBottom.getMax()) {
                            this.scrollX = (int)this.scrollbarMainBottom.getMax();
                        }
                        if (this.scrollX < this.scrollbarMainBottom.getMin()) {
                            this.scrollX = (int)this.scrollbarMainBottom.getMin();
                        }
                        this.scrollbarMainBottom.setSliderValue(this.scrollX, false);
                    }
                    if (this.scrollbarMainSide != null && !this.scrollbarMainSide.func_146115_a() && !b) {
                        this.scrollY -= my - this.mouseY;
                        this.curMouseY = this.scrollY;
                        if (this.scrollY > this.scrollbarMainSide.getMax()) {
                            this.scrollY = (int)this.scrollbarMainSide.getMax();
                        }
                        if (this.scrollY < this.scrollbarMainSide.getMin()) {
                            this.scrollY = (int)this.scrollbarMainSide.getMin();
                        }
                        this.scrollbarMainSide.setSliderValue(this.scrollY, false);
                    }
                }
            }
            this.mouseX = mx;
            this.mouseY = my;
        }
        else {
            this.isMouseButtonDown = 0;
        }
        RenderHelper.func_74518_a();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int count = 0;
        int index = 0;
        for (final String sk : this.shownParts) {
            if (++count - 1 < this.partsStart) {
                continue;
            }
            GL11.glTranslated(0.0, 0.0, 5.0);
            final FocusNode node = (FocusNode)FocusEngine.getElement(sk);
            drawPart(node, gx + 38, 43 + gy + 25 * index, (node.getType() == IFocusElement.EnumUnitType.MOD) ? 24.0f : 32.0f, 220, this.func_146978_c(gx + 38 - 10, 32 + gy + 24 * index, 20, 20, mx + this.field_147003_i, my + this.field_147009_r));
            GL11.glTranslated(0.0, 0.0, -5.0);
            if (++index > 5) {
                break;
            }
        }
        count = 0;
        index = 0;
        for (final String sk : this.shownParts) {
            if (++count - 1 < this.partsStart) {
                continue;
            }
            final FocusNode node = (FocusNode)FocusEngine.getElement(sk);
            if (this.func_146978_c(gx + 38 - 10, 33 + gy + 25 * index, 20, 20, mx + this.field_147003_i, my + this.field_147009_r)) {
                final List list = this.genPartText(node, -1);
                this.drawHoveringTextFixed(list, gx + 44, 46 + gy + 24 * index, this.field_146289_q, this.field_146294_l - (this.field_147003_i + this.field_146999_f - 16));
            }
            if (++index > 5) {
                break;
            }
        }
        if (this.lastNodeHover >= 0) {
            final FocusElementNode fn = this.table.data.get(this.lastNodeHover);
            if (fn.node != null) {
                final int xx = this.field_147003_i + 140 - this.scrollX + fn.x * 24;
                final int yy = this.field_147009_r + 50 - this.scrollY + fn.y * 32;
                final List list = this.genPartText(fn.node, this.lastNodeHover);
                this.drawHoveringTextFixed(list, xx, yy, this.field_146289_q, this.field_146294_l - (this.field_147003_i + this.field_146999_f - 16));
            }
        }
        this.buttonConfirm.active = (this.table.vis <= 0.0f && this.valid);
        GlStateManager.func_179084_k();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.table.data != null && !this.table.data.isEmpty()) {
            this.nameField.func_146194_f();
        }
        this.func_191948_b(this.mouseX, this.mouseY);
    }
    
    private List genPartText(final FocusNode node, final int idx) {
        final List list = new ArrayList();
        if (node != null) {
            FocusElementNode placed = null;
            if (idx >= 0) {
                placed = this.table.data.get(idx);
            }
            list.add(I18n.func_74838_a(node.getUnlocalizedName()));
            list.add(TextFormatting.DARK_PURPLE + I18n.func_74838_a(node.getUnlocalizedText()));
            int c = node.getComplexity();
            if (placed != null) {
                c = (int)(node.getComplexity() * placed.complexityMultiplier);
            }
            list.add(TextFormatting.GOLD + I18n.func_74838_a("focuspart.com") + ((placed != null && placed.complexityMultiplier > 1.0f) ? TextFormatting.RED : "") + " " + c);
            float p = node.getPowerMultiplier();
            if (placed != null) {
                p = placed.getPower(this.table.data);
            }
            if (p != 1.0f) {
                list.add(TextFormatting.GOLD + I18n.func_74838_a("focuspart.eff") + ((p < 1.0f) ? TextFormatting.RED : TextFormatting.GREEN) + " x" + ItemStack.field_111284_a.format(p));
            }
            if (node instanceof FocusEffect) {
                final FocusEffect fe = (FocusEffect)node;
                float d = fe.getDamageForDisplay((placed == null) ? 1.0f : placed.getPower(this.table.data));
                if (d > 0.0f) {
                    list.add(TextFormatting.DARK_RED + "" + I18n.func_74837_a("attribute.modifier.equals.0", new Object[] { ItemStack.field_111284_a.format(d), I18n.func_74838_a("attribute.name.generic.attackDamage") }));
                }
                else if (d < 0.0f) {
                    d *= -1.0f;
                    list.add(TextFormatting.DARK_GREEN + "" + I18n.func_74837_a("attribute.modifier.equals.0", new Object[] { ItemStack.field_111284_a.format(d), I18n.func_74838_a("focus.heal.power") }));
                }
            }
        }
        return list;
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex2);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex3);
        this.func_73729_b(k - 71, l - 3, 0, 0, 71, 239);
        if (this.table.func_70301_a(0) == null || this.table.doGuiReset) {
            if (this.table.doGuiReset) {
                this.resetNodes();
            }
            else {
                this.table.data.clear();
                this.gatherInfo(false);
            }
            this.table.focusName = "";
            if (this.table.func_70301_a(0) != null) {
                this.table.focusName = this.table.func_70301_a(0).func_82833_r();
                this.nameField.func_146180_a(this.table.focusName);
            }
            this.table.doGuiReset = false;
        }
        if (this.table.doGather) {
            this.gatherInfo(false);
            this.table.doGather = false;
        }
        this.drawNodes(this.field_147003_i + 132 - this.scrollX, this.field_147009_r + 48 - this.scrollY, par2, par3);
        GL11.glTranslated(0.0, 0.0, 1.0);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.maxComplexity > 0) {
            this.field_146289_q.func_175063_a(this.totalComplexity + "/" + this.maxComplexity, (float)(k + 242), (float)(l + 36), (this.totalComplexity > this.maxComplexity) ? 16151160 : 16761407);
        }
        this.field_146289_q.func_175063_a("" + this.costXp, (float)(k + 242), (float)(l + 50), (this.costXp > this.field_146297_k.field_71439_g.field_71068_ca) ? 16151160 : 10092429);
        int v = this.costVis;
        if (this.table.vis > 0.0f) {
            v = (int)this.table.vis;
        }
        this.field_146289_q.func_175063_a(TextFormatting.AQUA + "" + v, (float)(k + 242), (float)(l + 64), 10092429);
        if (this.costCast > 0.0f) {
            final String cost = this.myFormatter.format(this.costCast);
            this.field_146289_q.func_175063_a(TextFormatting.AQUA + I18n.func_74838_a("item.Focus.cost1") + ": " + cost, (float)(k + 230), (float)(l + 80), 10092429);
        }
        if (this.components != null && this.components.length > 0) {
            this.field_146289_q.func_175063_a(TextFormatting.GOLD + I18n.func_74838_a("wandtable.text4"), (float)(k + 230), (float)(l + 92), 10092429);
        }
        if (this.table.focusName != null && !this.table.data.isEmpty()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146297_k.field_71446_o.func_110577_a(this.texbase);
            this.func_73729_b(this.field_147003_i + 24, this.field_147009_r + 8, 192, 224, 8, 14);
            int a;
            for (a = 1, a = 1; a < 22; ++a) {
                this.func_73729_b(this.field_147003_i + 24 + a * 8, this.field_147009_r + 8, 200, 224, 8, 14);
            }
            this.func_73729_b(this.field_147003_i + 24 + a * 8, this.field_147009_r + 8, 208, 224, 8, 14);
        }
        GL11.glDisable(3042);
    }
    
    private void drawClippedRect(final int x, final int y, final int sx, final int sy, final int w, final int h) {
        if (this.func_146978_c(48, 26, 166, 174, x + w / 2, y + h / 2)) {
            this.func_73729_b(x, y, sx, sy, w, h);
        }
    }
    
    private void drawNodes(final int x, final int y, final int mx, final int my) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179140_f();
        if (this.table.data != null) {
            int hover = -1;
            for (final FocusElementNode fn : this.table.data.values()) {
                final int xx = x + fn.x * 24;
                final int yy = y + fn.y * 32;
                final boolean mouseover = this.func_146978_c(this.field_147003_i + 63, this.field_147009_r + 31, 136, 160, mx + this.field_147003_i, my + this.field_147009_r) && this.func_146978_c(xx - 10, yy - 10, 20, 20, mx + this.field_147003_i, my + this.field_147009_r);
                if (mouseover && fn.parent >= 0) {
                    hover = fn.id;
                }
                if (fn.node != null) {
                    if (this.func_146978_c(48, 16, 154, 192, xx - 8, yy - 8)) {
                        drawPart(fn.node, xx, yy, 32.0f, 220, mouseover);
                    }
                }
                else {
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.drawClippedRect(xx - 12, yy - 12, 120, 232, 24, 24);
                }
                if (this.selectedNode == fn.id || (mouseover && fn.parent >= 0)) {
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.drawClippedRect(xx - 12, yy - 12, 96, 232, 24, 24);
                }
                final FocusElementNode parent = this.table.data.get(fn.parent);
                if (parent != null) {
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.drawClippedRect(xx - 6, yy - 22, 54, 232, 12, 12);
                    if (parent.node instanceof FocusModSplit) {
                        for (int q = Math.abs(fn.x - parent.x), a = 0; a < q; ++a) {
                            if (fn.x < parent.x) {
                                if (a == 0) {
                                    this.drawClippedRect(xx - 4, yy - 36, 8, 240, 16, 16);
                                }
                                else {
                                    this.drawClippedRect(xx - 12 + a * 24, yy - 36, 72, 240, 24, 16);
                                }
                            }
                            else if (a == 0) {
                                this.drawClippedRect(xx - 12, yy - 36, 24, 240, 16, 16);
                            }
                            else {
                                this.drawClippedRect(xx - 12 - a * 24, yy - 36, 72, 240, 24, 16);
                            }
                        }
                    }
                    if (fn.node != null) {
                        continue;
                    }
                    final int s = (parent.target && parent.trajectory) ? 4 : 0;
                    if (!this.func_146978_c(48, 16, 168, 192, xx - 4, yy - 4)) {
                        continue;
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    if (parent.target) {
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(xx - s), (double)yy, 0.0);
                        GL11.glScaled(0.5, 0.5, 0.5);
                        this.func_73729_b(-8, -8, 152, 240, 16, 16);
                        GL11.glPopMatrix();
                    }
                    if (!parent.trajectory) {
                        continue;
                    }
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(xx + s), (double)yy, 0.0);
                    GL11.glScaled(0.5, 0.5, 0.5);
                    this.func_73729_b(-8, -8, 168, 240, 16, 16);
                    GL11.glPopMatrix();
                }
            }
            if (hover >= 0 && this.lastNodeHover != hover) {
                this.playRollover();
                this.lastNodeHover = hover;
            }
            if (hover < 0) {
                this.lastNodeHover = -1;
            }
            if (this.selectedNode >= 0) {
                this.drawNodeSettings(this.selectedNode);
            }
        }
        GlStateManager.func_179145_e();
        RenderHelper.func_74518_a();
        GL11.glPopMatrix();
    }
    
    private void drawNodeSettings(final int selectedNode2) {
        final FocusElementNode fn = this.table.data.get(selectedNode2);
        if (fn != null && fn.node != null && !fn.node.getSettingList().isEmpty()) {
            int a = 0;
            for (final String sk : fn.node.getSettingList()) {
                final NodeSetting setting = fn.node.getSetting(sk);
                if (setting.getResearch() != null && !ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, setting.getResearch())) {
                    continue;
                }
                final int x = this.field_147003_i + this.field_146999_f;
                final int y = this.field_147009_r + this.field_147000_g - 10 - fn.node.getSettingList().size() * 26 + a * 26;
                this.field_146289_q.func_175063_a(TextFormatting.GOLD + setting.getLocalizedName(), (float)x, (float)y, 16777215);
                ++a;
            }
        }
    }
    
    public void func_146274_d() throws IOException {
        super.func_146274_d();
        final int k = Mouse.getDWheel();
        final int i = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
        final int j = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
        if (this.scrollbarParts != null && this.shownParts.size() > 6 && this.func_146978_c(24, 24, 32, 157, i, j)) {
            if (k > 0) {
                if (this.partsStart > 0) {
                    --this.partsStart;
                    this.scrollbarParts.setSliderValue(this.partsStart, false);
                }
            }
            else if (k < 0 && this.partsStart < this.shownParts.size() - 6) {
                ++this.partsStart;
                this.scrollbarParts.setSliderValue(this.partsStart, false);
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
        if (this.scrollbarMainSide != null) {
            final int sv = Math.round(this.scrollbarMainSide.getSliderValue());
            if (sv != this.scrollY) {
                this.scrollY = sv;
            }
        }
        if (this.scrollbarMainBottom != null) {
            final int sv = Math.round(this.scrollbarMainBottom.getSliderValue());
            if (sv != this.scrollX) {
                this.scrollX = sv;
            }
        }
        if (this.scrollbarParts != null) {
            final int sv = Math.round(this.scrollbarParts.getSliderValue());
            if (sv != this.partsStart) {
                this.partsStart = sv;
            }
        }
    }
    
    protected void func_73864_a(final int mx, final int my, final int par3) {
        try {
            super.func_73864_a(mx, my, par3);
        }
        catch (IOException ex) {}
        final int gx = (this.field_146294_l - this.field_146999_f) / 2;
        final int gy = (this.field_146295_m - this.field_147000_g) / 2;
        if (this.table.vis <= 0.0f && this.table.data != null) {
            this.nameField.func_146192_a(mx, my, par3);
            if (this.lastNodeHover >= 0) {
                this.selectedNode = this.lastNodeHover;
                final boolean r = false;
                if (par3 == 1 && this.table.data.get(this.selectedNode).node != null) {
                    final FocusElementNode fn = this.table.data.get(this.selectedNode);
                    if (this.table.data.get(fn.parent).node != null) {
                        this.addNodeAt(this.table.data.get(fn.parent).node.getClass(), fn.parent, true);
                    }
                }
                this.gatherInfo(false);
                this.playButtonClick();
            }
            int count = 0;
            int index = 0;
            if (this.selectedNode >= 0) {
                for (final String sk : this.shownParts) {
                    if (++count - 1 < this.partsStart) {
                        continue;
                    }
                    if (this.func_146978_c(gx + 28, gy + 32 + 24 * index, 20, 20, mx + this.field_147003_i, my + this.field_147009_r)) {
                        this.addNodeAt(FocusEngine.elements.get(sk), this.selectedNode, true);
                        return;
                    }
                    if (++index > 5) {
                        break;
                    }
                }
            }
        }
    }
    
    private void playButtonClick() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.clack, 0.4f, 1.0f);
    }
    
    private void playRollover() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.clack, 0.4f, 2.0f);
    }
    
    private void playSocketSound(final float pitch) {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.crystal, 0.4f, pitch);
    }
    
    protected void drawHoveringTextFixed(final List listin, final int x, final int y, final FontRenderer font, final int textWidth) {
        if (!listin.isEmpty()) {
            GlStateManager.func_179101_C();
            RenderHelper.func_74518_a();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            final List list = new ArrayList();
            for (final Object o : listin) {
                String s = (String)o;
                s = this.trimStringNewline(s);
                final List list2 = font.func_78271_c(s, textWidth);
                for (final String s2 : list2) {
                    list.add(s2);
                }
            }
            int k = 0;
            final Iterator iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                final String s = iterator2.next();
                final int l = font.func_78256_a(s);
                if (l > k) {
                    k = l;
                }
            }
            final int j2 = x + 12;
            int k2 = y - 12;
            int i1 = 8;
            if (list.size() > 1) {
                i1 += 2 + (list.size() - 1) * 10;
            }
            this.field_73735_i = 300.0f;
            this.field_146296_j.field_77023_b = 300.0f;
            final int j3 = -267386864;
            this.func_73733_a(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j3, j3);
            this.func_73733_a(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j3, j3);
            this.func_73733_a(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j3, j3);
            this.func_73733_a(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j3, j3);
            this.func_73733_a(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j3, j3);
            final int k3 = 1347420415;
            final int l2 = (k3 & 0xFEFEFE) >> 1 | (k3 & 0xFF000000);
            this.func_73733_a(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k3, l2);
            this.func_73733_a(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k3, l2);
            this.func_73733_a(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k3, k3);
            this.func_73733_a(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l2, l2);
            for (int i2 = 0; i2 < list.size(); ++i2) {
                final String s3 = list.get(i2);
                font.func_175063_a(s3, (float)j2, (float)k2, -1);
                if (i2 == 0) {
                    k2 += 2;
                }
                k2 += 10;
            }
            this.field_73735_i = 0.0f;
            this.field_146296_j.field_77023_b = 0.0f;
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            RenderHelper.func_74519_b();
            GlStateManager.func_179091_B();
        }
    }
    
    private String trimStringNewline(String text) {
        while (text != null && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
    
    public static void drawPart(final FocusNode node, final int x, final int y, float scale, final int bright, final boolean mouseover) {
        GL11.glPushMatrix();
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glTranslated((double)x, (double)y, 0.0);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
        final boolean root = node.getType() == IFocusElement.EnumUnitType.MOD || node.getKey().equals("thaumcraft.ROOT");
        if (root) {
            scale *= 2.0f;
        }
        final Color color = new Color(FocusEngine.getElementColor(node.getKey()));
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        switch (node.getType()) {
            case EFFECT: {
                UtilsFX.renderQuadCentered(GuiFocalManipulator.iEffect, (float)(scale * 0.9 + (mouseover ? 2 : 0)), r, g, b, 220, 771, 1.0f);
                break;
            }
            case MEDIUM: {
                if (!root) {
                    UtilsFX.renderQuadCentered(GuiFocalManipulator.iMedium, (float)(scale * 0.9 + (mouseover ? 2 : 0)), r, g, b, 220, 771, 1.0f);
                    break;
                }
                break;
            }
        }
        GL11.glTranslated(0.0, 0.0, 1.0);
        UtilsFX.renderQuadCentered(FocusEngine.getElementIcon(node.getKey()), scale / 2.0f + (mouseover ? 2 : 0), 1.0f, 1.0f, 1.0f, bright, 771, 1.0f);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glPopMatrix();
    }
    
    private int getNextId() {
        while (this.table.data.containsKey(this.nodeID)) {
            ++this.nodeID;
        }
        return this.nodeID;
    }
    
    private void cullChildren(final int idx) {
        if (this.table.data.containsKey(idx)) {
            for (final int i : this.table.data.get(idx).children) {
                this.cullChildren(i);
                this.table.data.remove(i);
            }
        }
    }
    
    private ArrayList<Integer> addNodeAt(final Class nodeClass, final int idx, final boolean gather) {
        final ArrayList<Integer> ret = new ArrayList<Integer>();
        boolean same = false;
        FocusElementNode previous = null;
        if (this.table.data.containsKey(idx)) {
            this.cullChildren(idx);
            if (this.table.data.get(idx).node != null && this.table.data.get(idx).node.getClass() == nodeClass) {
                same = true;
            }
            else {
                previous = this.table.data.remove(idx);
            }
        }
        try {
            FocusElementNode fn = null;
            FocusNode node = null;
            if (!same) {
                fn = new FocusElementNode();
                node = nodeClass.newInstance();
                fn.node = node;
                if (previous != null) {
                    fn.y = previous.y;
                    fn.x = previous.x;
                }
                fn.id = this.getNextId();
                ret.add(fn.id);
                this.selectedNode = fn.id;
                if (previous != null && this.table.data.containsKey(previous.parent)) {
                    fn.parent = previous.parent;
                    final int[] c = this.table.data.get(previous.parent).children;
                    for (int a = 0; a < c.length; ++a) {
                        if (c[a] == previous.id) {
                            this.table.data.get(previous.parent).children[a] = fn.id;
                            break;
                        }
                    }
                }
                fn.target = node.canSupply(FocusNode.EnumSupplyType.TARGET);
                fn.trajectory = node.canSupply(FocusNode.EnumSupplyType.TRAJECTORY);
                this.table.data.put(this.nodeID, fn);
            }
            else {
                fn = this.table.data.get(idx);
                node = fn.node;
            }
            if (fn.target || fn.trajectory) {
                if (node instanceof FocusModSplit) {
                    final FocusElementNode blank1 = new FocusElementNode();
                    blank1.parent = fn.id;
                    blank1.id = this.getNextId();
                    ret.add(this.nodeID);
                    blank1.x = fn.x - 1;
                    blank1.y = fn.y + 1;
                    this.table.data.put(this.nodeID, blank1);
                    this.selectedNode = this.nodeID;
                    final FocusElementNode blank2 = new FocusElementNode();
                    blank2.parent = fn.id;
                    blank2.x = fn.x + 1;
                    blank2.y = fn.y + 1;
                    blank2.id = this.getNextId();
                    ret.add(this.nodeID);
                    this.table.data.put(this.nodeID, blank2);
                    fn.children = new int[] { blank1.id, blank2.id };
                }
                else {
                    final FocusElementNode blank3 = new FocusElementNode();
                    blank3.parent = fn.id;
                    blank3.x = fn.x;
                    blank3.y = fn.y + 1;
                    blank3.id = this.getNextId();
                    ret.add(this.nodeID);
                    this.table.data.put(this.nodeID, blank3);
                    fn.children = new int[] { blank3.id };
                    this.selectedNode = this.nodeID;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (gather) {
            this.calcNodeTreeLayout();
            this.gatherInfo(true);
        }
        return ret;
    }
    
    private void processLeftNodes(final FocusElementNode start, final Bounds bounds) {
        if (start.children.length > 0) {
            this.processLeftNodes(this.table.data.get(start.children[0]), bounds);
        }
        int ox = 0;
        if (start.children.length == 1) {
            ox = bounds.x - 1;
            bounds.x = this.table.data.get(start.children[0]).x;
        }
        start.x = bounds.x;
        if (start.children.length == 1) {
            bounds.x = ox;
        }
        ++bounds.x;
        if (start.children.length > 1) {
            this.processLeftNodes(this.table.data.get(start.children[1]), bounds);
        }
    }
    
    private void centerNodes(final int start, final Bounds bounds) {
        final int x = bounds.x / 2;
        final FocusElementNode sn = this.table.data.get(start);
        this.moveNodes(sn, x);
    }
    
    private void moveNodes(final FocusElementNode start, final int amt) {
        for (final int ci : start.children) {
            this.moveNodes(this.table.data.get(ci), amt);
        }
        start.x -= amt;
    }
    
    private void calcNodeTreeLayout() {
        int fsi = -1;
        for (final FocusElementNode node : this.table.data.values()) {
            if (fsi < 0 && node.node != null && node.node instanceof FocusModSplit) {
                fsi = node.id;
            }
        }
        if (fsi >= 0) {
            final Bounds bounds = new Bounds();
            this.processLeftNodes(this.table.data.get(fsi), bounds);
            this.centerNodes(fsi, bounds);
        }
        for (final FocusElementNode node : this.table.data.values()) {
            if (node.node != null && node.node instanceof FocusModSplit) {
                if (this.table.data.containsKey(node.parent) && this.table.data.get(node.parent).node != null && !(this.table.data.get(node.parent).node instanceof FocusModSplit)) {
                    node.x = this.table.data.get(node.parent).x;
                }
                else {
                    int xx = 0;
                    for (final int a : node.children) {
                        xx += this.table.data.get(a).x;
                    }
                    xx /= node.children.length;
                    node.x = xx;
                }
            }
        }
        if (this.selectedNode >= 0 && !this.table.data.containsKey(this.selectedNode)) {
            this.selectedNode = -1;
        }
    }
    
    private void resetNodes() {
        this.nodeID = 0;
        this.table.data.clear();
        final ArrayList<Integer> r1 = this.addNodeAt(FocusMediumRoot.class, 0, false);
        this.selectedNode = r1.get(1);
        this.calcNodeTreeLayout();
        this.gatherInfo(true);
    }
    
    private void calcScrollBounds() {
        this.sMinX = 0;
        this.sMinY = 0;
        this.sMaxX = 0;
        this.sMaxY = 0;
        for (final FocusElementNode fn : this.table.data.values()) {
            if (fn.x < this.sMinX) {
                this.sMinX = fn.x;
            }
            if (fn.y < this.sMinY) {
                this.sMinY = fn.y;
            }
            if (fn.x > this.sMaxX) {
                this.sMaxX = fn.x;
            }
            if (fn.y > this.sMaxY) {
                this.sMaxY = fn.y;
            }
        }
    }
    
    private void gatherPartsList() {
        this.shownParts.clear();
        if (this.field_146297_k == null) {
            return;
        }
        if (this.selectedNode >= 0 && this.table.data.containsKey(this.selectedNode)) {
            this.partsStart = 0;
            final ArrayList<String> pMed = new ArrayList<String>();
            final ArrayList<String> pEff = new ArrayList<String>();
            final ArrayList<String> pMod = new ArrayList<String>();
            boolean hasExlusive = false;
            boolean hasMedium = false;
            for (final FocusElementNode fn : this.table.data.values()) {
                if (fn.node != null && fn.node instanceof FocusMedium) {
                    hasMedium = !(fn.node instanceof FocusMediumRoot);
                    if (((FocusMedium)fn.node).isExclusive()) {
                        hasExlusive = true;
                        break;
                    }
                    continue;
                }
            }
            final FocusElementNode node = this.table.data.get(this.selectedNode);
            final FocusElementNode parent = this.table.data.get(node.parent);
            if (parent != null && parent.node != null) {
                for (final String key : FocusEngine.elements.keySet()) {
                    final IFocusElement element = FocusEngine.getElement(key);
                    if (!ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, element.getResearch())) {
                        continue;
                    }
                    if (element.getKey().equals("thaumcraft.ROOT")) {
                        continue;
                    }
                    if (!(element instanceof FocusNode)) {
                        continue;
                    }
                    final FocusNode fn2 = (FocusNode)element;
                    if (fn2.mustBeSupplied() == null) {
                        continue;
                    }
                    for (final FocusNode.EnumSupplyType type : fn2.mustBeSupplied()) {
                        if (parent.node.canSupply(type)) {
                            switch (element.getType()) {
                                case EFFECT: {
                                    pEff.add(key);
                                    break;
                                }
                                case MEDIUM: {
                                    if (!hasExlusive && (!((FocusMedium)element).isExclusive() || !hasMedium)) {
                                        pMed.add(key);
                                        break;
                                    }
                                    break;
                                }
                                case MOD: {
                                    pMod.add(key);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            Collections.sort(pMed);
            Collections.sort(pEff);
            Collections.sort(pMod);
            this.shownParts.addAll(pMed);
            this.shownParts.addAll(pEff);
            this.shownParts.addAll(pMod);
        }
    }
    
    private void gatherInfo(final boolean sync) {
        this.field_146292_n.clear();
        this.field_146292_n.add(this.buttonConfirm);
        this.buttonConfirm.field_146128_h = this.field_147003_i + 242;
        this.buttonConfirm.field_146129_i = this.field_147009_r + 18;
        this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 1, this.field_147003_i + 241, this.field_147009_r + 39, 32, 16, I18n.func_74838_a("wandtable.text5"), null, new ResourceLocation("thaumcraft", "textures/gui/complex.png")));
        this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 2, this.field_147003_i + 241, this.field_147009_r + 53, 32, 16, I18n.func_74838_a("wandtable.text2"), null, new ResourceLocation("thaumcraft", "textures/gui/costxp.png")));
        this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 3, this.field_147003_i + 241, this.field_147009_r + 67, 32, 16, I18n.func_74838_a("wandtable.text1"), null, new ResourceLocation("thaumcraft", "textures/gui/costvis.png")));
        final FocusElementNode fn = this.table.data.get(this.selectedNode);
        if (fn != null && fn.node != null && !fn.node.getSettingList().isEmpty()) {
            int a = 0;
            for (final String sk : fn.node.getSettingList()) {
                final NodeSetting setting = fn.node.getSetting(sk);
                if (setting.getResearch() != null && !ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, setting.getResearch())) {
                    continue;
                }
                final int x = this.field_147003_i + this.field_146999_f;
                final int y = this.field_147009_r + this.field_147000_g + 3 - fn.node.getSettingList().size() * 26 + a * 26;
                int w = 32;
                if (setting.getType() instanceof NodeSetting.NodeSettingIntList) {
                    w = 72;
                }
                this.field_146292_n.add(new GuiFocusSettingSpinnerButton(10 + a, x, y, w, setting));
                ++a;
            }
        }
        this.shownParts.clear();
        this.components = null;
        if (this.table.func_70301_a(0) == null) {
            return;
        }
        final HashMap<String, Integer> compCount = new HashMap<String, Integer>();
        this.totalComplexity = 0;
        this.maxComplexity = 0;
        if (this.field_147002_h.func_75139_a(0).func_75216_d()) {
            final ItemStack is = this.field_147002_h.func_75139_a(0).func_75211_c();
            if (is != null && !is.func_190926_b() && is.func_77973_b() instanceof ItemFocus) {
                this.maxComplexity = ((ItemFocus)is.func_77973_b()).getMaxComplexity();
            }
        }
        boolean emptyNodes = false;
        final AspectList crystals = new AspectList();
        if (this.table.data != null) {
            for (final FocusElementNode node : this.table.data.values()) {
                if (node.node != null) {
                    int a2 = 0;
                    if (compCount.containsKey(node.node.getKey())) {
                        a2 = compCount.get(node.node.getKey());
                    }
                    ++a2;
                    node.complexityMultiplier = 0.5f * (a2 + 1);
                    compCount.put(node.node.getKey(), a2);
                    this.totalComplexity += (int)(node.node.getComplexity() * node.complexityMultiplier);
                    if (node.node.getAspect() == null) {
                        continue;
                    }
                    crystals.add(node.node.getAspect(), 1);
                }
                else {
                    emptyNodes = true;
                }
            }
        }
        this.costCast = this.totalComplexity / 5.0f;
        this.costVis = this.totalComplexity * 10 + this.maxComplexity / 5;
        this.costXp = (int)Math.min(1L, Math.round(Math.sqrt(this.totalComplexity)));
        boolean validCrystals = false;
        if (crystals.getAspects().length > 0) {
            validCrystals = true;
            this.components = new ItemStack[crystals.getAspects().length];
            int r = 0;
            for (final Aspect as : crystals.getAspects()) {
                this.components[r] = ThaumcraftApiHelper.makeCrystal(as, crystals.getAmount(as));
                ++r;
            }
            if (this.components.length >= 0) {
                final boolean[] owns = new boolean[this.components.length];
                for (int a3 = 0; a3 < this.components.length; ++a3) {
                    if (!(owns[a3] = InventoryUtils.isPlayerCarryingAmount((EntityPlayer)this.field_146297_k.field_71439_g, this.components[a3], false))) {
                        validCrystals = false;
                    }
                }
            }
            if (this.components != null && this.components.length > 0) {
                int i = 0;
                int q = 0;
                int z = 0;
                for (final ItemStack stack : this.components) {
                    this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 11 + z, this.field_147003_i + 234 + i * 16, this.field_147009_r + 110 + 16 * q, 16, 16, stack.func_82833_r(), null, stack));
                    if (++i > 4) {
                        i = 0;
                        ++q;
                    }
                    ++z;
                }
            }
        }
        this.gatherPartsList();
        this.scrollbarParts = null;
        if (this.shownParts.size() > 6) {
            this.scrollbarParts = new GuiSliderTC(4, this.field_147003_i + 51, this.field_147009_r + 30, 8, 149, "logistics.scrollbar", 0.0f, this.shownParts.size() - 6, 0.0f, true);
            this.field_146292_n.add(this.scrollbarParts);
        }
        this.valid = (this.totalComplexity <= this.maxComplexity && !emptyNodes && validCrystals && this.table.xpCost <= this.field_146297_k.field_71439_g.field_71068_ca);
        this.calcScrollBounds();
        if (this.scrollY > (this.sMaxY - 3) * 32) {
            this.scrollY = (this.sMaxY - 3) * 32;
        }
        if (this.scrollX > this.sMaxX * 24) {
            this.scrollX = this.sMaxX * 24;
        }
        if (this.scrollX < this.sMinX * 24) {
            this.scrollX = this.sMinX * 24;
        }
        this.scrollbarMainSide = null;
        this.scrollbarMainBottom = null;
        if (this.sMaxY * 32 > 130) {
            this.scrollbarMainSide = new GuiSliderTC(5, this.field_147003_i + 203, this.field_147009_r + 32, 8, 156, "logistics.scrollbar", 0.0f, (this.sMaxY - 3) * 32, this.scrollY, true);
            this.field_146292_n.add(this.scrollbarMainSide);
        }
        else {
            this.scrollY = 0;
        }
        if (this.sMinX * 24 < -70 || this.sMaxX * 24 > 70) {
            this.scrollbarMainBottom = new GuiSliderTC(6, this.field_147003_i + 64, this.field_147009_r + 195, 132, 8, "logistics.scrollbar", this.sMinX * 24, this.sMaxX * 24, this.scrollX, false);
            this.field_146292_n.add(this.scrollbarMainBottom);
        }
        else {
            this.scrollX = 0;
        }
        if (sync) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketFocusNodesToServer(this.table.func_174877_v(), this.table.data, this.table.focusName));
        }
    }
    
    static {
        GuiFocalManipulator.iMedium = new ResourceLocation("thaumcraft", "textures/foci/_medium.png");
        GuiFocalManipulator.iEffect = new ResourceLocation("thaumcraft", "textures/foci/_effect.png");
    }
    
    private class Bounds
    {
        int x;
        
        private Bounds() {
            this.x = 0;
        }
    }
}
