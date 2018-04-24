package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.client.gui.plugins.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.client.*;
import org.lwjgl.opengl.*;
import thaumcraft.api.research.theorycraft.*;
import net.minecraft.util.text.translation.*;
import thaumcraft.client.lib.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.text.*;
import thaumcraft.api.research.*;
import net.minecraft.client.gui.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.io.*;
import thaumcraft.common.lib.*;

@SideOnly(Side.CLIENT)
public class GuiResearchTable extends GuiContainer
{
    private float xSize_lo;
    private float ySize_lo;
    private TileResearchTable table;
    private FontRenderer galFontRenderer;
    private String username;
    EntityPlayer player;
    ResourceLocation txBackground;
    ResourceLocation txBase;
    ResourceLocation txPaper;
    ResourceLocation txPaperGilded;
    ResourceLocation txQuestion;
    ResearchTableData.CardChoice lastDraw;
    float[] cardHover;
    float[] cardZoomOut;
    float[] cardZoomIn;
    boolean[] cardActive;
    boolean cardSelected;
    public HashMap<String, Integer> tempCatTotals;
    long nexCatCheck;
    long nextCheck;
    int dummyInspirationStart;
    Set<String> currentAids;
    Set<String> selectedAids;
    GuiImageButton buttonCreate;
    GuiImageButton buttonComplete;
    GuiImageButton buttonScrap;
    public ArrayList<ResearchTableData.CardChoice> cardChoices;
    
    public GuiResearchTable(final EntityPlayer player, final TileResearchTable e) {
        super((Container)new ContainerResearchTable(player.field_71071_by, e));
        this.txBackground = new ResourceLocation("thaumcraft", "textures/gui/gui_research_table.png");
        this.txBase = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
        this.txPaper = new ResourceLocation("thaumcraft", "textures/gui/paper.png");
        this.txPaperGilded = new ResourceLocation("thaumcraft", "textures/gui/papergilded.png");
        this.txQuestion = new ResourceLocation("thaumcraft", "textures/aspects/_unknown.png");
        this.cardHover = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardZoomOut = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardZoomIn = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardActive = new boolean[] { true, true, true };
        this.cardSelected = false;
        this.tempCatTotals = new HashMap<String, Integer>();
        this.nexCatCheck = 0L;
        this.nextCheck = 0L;
        this.dummyInspirationStart = 0;
        this.currentAids = new HashSet<String>();
        this.selectedAids = new HashSet<String>();
        this.buttonCreate = new GuiImageButton((GuiScreen)this, 1, this.field_147003_i + 128, this.field_147009_r + 22, 49, 11, "button.create.theory", null, this.txBase, 37, 66, 51, 13, 8978346);
        this.buttonComplete = new GuiImageButton((GuiScreen)this, 7, this.field_147003_i + 191, this.field_147009_r + 96, 49, 11, "button.complete.theory", null, this.txBase, 37, 66, 51, 13, 8978346);
        this.buttonScrap = new GuiImageButton((GuiScreen)this, 9, this.field_147003_i + 128, this.field_147009_r + 168, 49, 11, "button.scrap.theory", null, this.txBase, 37, 66, 51, 13, 16720418);
        this.cardChoices = new ArrayList<ResearchTableData.CardChoice>();
        this.table = e;
        this.field_146999_f = 255;
        this.field_147000_g = 255;
        this.galFontRenderer = FMLClientHandler.instance().getClient().field_71464_q;
        this.username = player.func_70005_c_();
        this.player = player;
        if (this.table.data != null) {
            for (final String cat : this.table.data.categoryTotals.keySet()) {
                this.tempCatTotals.put(cat, this.table.data.categoryTotals.get(cat));
            }
            this.syncFromTableChoices();
            this.lastDraw = this.table.data.lastDraw;
        }
    }
    
    private void syncFromTableChoices() {
        this.cardChoices.clear();
        for (final ResearchTableData.CardChoice cc : this.table.data.cardChoices) {
            this.cardChoices.add(cc);
        }
    }
    
    protected void func_146979_b(final int mx, final int my) {
    }
    
    public void func_73863_a(final int mx, final int my, final float par3) {
        this.func_146276_q_();
        super.func_73863_a(mx, my, par3);
        this.xSize_lo = mx;
        this.ySize_lo = my;
        final int xx = this.field_147003_i;
        final int yy = this.field_147009_r;
        RenderHelper.func_74518_a();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.table.data == null) {
            if (!this.currentAids.isEmpty()) {
                final int side = Math.min(this.currentAids.size(), 6);
                int c = 0;
                int r = 0;
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
                this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
                for (final String key : this.currentAids) {
                    final ITheorycraftAid mutator = TheorycraftManager.aids.get(key);
                    if (mutator == null) {
                        continue;
                    }
                    final int x = xx + 128 + 20 * c - side * 10;
                    final int y = yy + 85 + 35 * r;
                    if (this.func_146978_c(x - xx, y - yy, 16, 16, mx, my) && !this.selectedAids.contains(key)) {
                        this.func_73729_b(x, y, 0, 96, 16, 16);
                    }
                    if (++c < side) {
                        continue;
                    }
                    ++r;
                    c = 0;
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
        else {
            int sx = 128;
            final int cw = 110;
            final int sz = this.cardChoices.size();
            int a = 0;
            if (!this.cardSelected) {
                for (final ResearchTableData.CardChoice cardChoice : this.cardChoices) {
                    if (this.cardZoomOut[a] >= 1.0f) {
                        final float dx = 55 + sx - 55 * sz + cw * a - 65;
                        float fx = 65.0f + dx * this.cardZoomOut[a];
                        final float qx = 191.0f - fx;
                        if (this.cardActive[a]) {
                            fx += qx * this.cardZoomIn[a];
                        }
                        this.drawSheetOverlay(fx, 100.0, cardChoice, mx, my);
                        ++a;
                    }
                }
            }
            int qq = 0;
            if (this.table.func_70301_a(0) == null || this.table.func_70301_a(0).func_77952_i() == this.table.func_70301_a(0).func_77958_k()) {
                sx = Math.max(this.field_146289_q.func_78256_a(I18n.func_74838_a("tile.researchtable.noink.0")), this.field_146289_q.func_78256_a(I18n.func_74838_a("tile.researchtable.noink.1"))) / 2;
                UtilsFX.drawCustomTooltip((GuiScreen)this, this.field_146289_q, Arrays.asList(I18n.func_74838_a("tile.researchtable.noink.0"), I18n.func_74838_a("tile.researchtable.noink.1")), xx - sx + 116, yy + 60 + qq, 11, true);
                qq += 40;
            }
            if (this.table.func_70301_a(1) == null) {
                sx = this.field_146289_q.func_78256_a(I18n.func_74838_a("tile.researchtable.nopaper.0")) / 2;
                UtilsFX.drawCustomTooltip((GuiScreen)this, this.field_146289_q, Arrays.asList(I18n.func_74838_a("tile.researchtable.nopaper.0")), xx - sx + 116, yy + 60 + qq, 11, true);
            }
        }
        this.func_191948_b(mx, my);
    }
    
    protected void func_146976_a(final float partialTicks, final int mx, final int my) {
        this.checkButtons();
        final int xx = this.field_147003_i;
        final int yy = this.field_147009_r;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.field_146297_k.field_71446_o.func_110577_a(this.txBackground);
        this.func_73729_b(xx, yy, 0, 0, 255, 255);
        this.field_146289_q.func_78276_b(" ", 0, 0, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.table.data == null) {
            if (this.nextCheck < this.player.field_70173_aa) {
                this.currentAids = this.table.checkSurroundingAids();
                this.dummyInspirationStart = ResearchTableData.getAvailableInspiration(this.player);
                this.nextCheck = this.player.field_70173_aa + 100;
            }
            this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(xx + 128 - this.dummyInspirationStart * 5), (double)(yy + 55), 0.0);
            GL11.glScaled(0.5, 0.5, 0.0);
            for (int a = 0; a < this.dummyInspirationStart; ++a) {
                this.func_73729_b(20 * a, 0, (this.dummyInspirationStart - this.selectedAids.size() <= a) ? 48 : 32, 96, 16, 16);
            }
            GL11.glPopMatrix();
            if (!this.currentAids.isEmpty()) {
                final int side = Math.min(this.currentAids.size(), 6);
                int c = 0;
                int r = 0;
                for (final String key : this.currentAids) {
                    final ITheorycraftAid mutator = TheorycraftManager.aids.get(key);
                    if (mutator == null) {
                        continue;
                    }
                    final int x = xx + 128 + 20 * c - side * 10;
                    final int y = yy + 85 + 35 * r;
                    if (this.selectedAids.contains(key)) {
                        this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
                        this.func_73729_b(x, y, 0, 96, 16, 16);
                    }
                    GL11.glPushMatrix();
                    RenderHelper.func_74520_c();
                    GlStateManager.func_179140_f();
                    GlStateManager.func_179091_B();
                    GlStateManager.func_179142_g();
                    GlStateManager.func_179145_e();
                    final ItemStack s = (ItemStack)((mutator.getAidObject() instanceof ItemStack) ? mutator.getAidObject() : new ItemStack((Block)mutator.getAidObject()));
                    this.field_146296_j.func_180450_b(s, x, y);
                    GlStateManager.func_179140_f();
                    GlStateManager.func_179132_a(true);
                    GlStateManager.func_179126_j();
                    GL11.glPopMatrix();
                    if (++c < side) {
                        continue;
                    }
                    ++r;
                    c = 0;
                }
            }
        }
        else {
            this.checkCards();
            this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
            GL11.glPushMatrix();
            GL11.glTranslated((double)(xx + 15), (double)(yy + 150), 0.0);
            if (this.table.data != null) {
                for (int a = 0; a < this.table.data.bonusDraws; ++a) {
                    this.func_73729_b(a * 2, a, 64, 96, 16, 16);
                }
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)(xx + 128 - this.table.data.inspirationStart * 5), (double)(yy + 16), 0.0);
            GL11.glScaled(0.5, 0.5, 0.0);
            for (int a = 0; a < this.table.data.inspirationStart; ++a) {
                this.func_73729_b(20 * a, 0, (this.table.data.inspiration <= a) ? 48 : 32, 96, 16, 16);
            }
            GL11.glPopMatrix();
            int sheets = 0;
            if (this.table.func_70301_a(1) != null) {
                sheets = 1 + this.table.func_70301_a(1).func_190916_E() / 4;
            }
            Random r2 = new Random(55L);
            if (sheets > 0 && !this.table.data.isComplete()) {
                for (int a2 = 0; a2 < sheets; ++a2) {
                    this.drawSheet(xx + 65, yy + 100, 6.0, r2, 1.0f, 1.0f, null);
                }
                boolean highlight = false;
                final int var7 = mx - (25 + xx);
                final int var8 = my - (55 + yy);
                if (this.cardChoices.isEmpty() && var7 >= 0 && var8 >= 0 && var7 < 75 && var8 < 90) {
                    highlight = true;
                }
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, highlight ? 1.0f : 0.5f);
                GlStateManager.func_179147_l();
                this.field_146297_k.field_71446_o.func_110577_a(this.txQuestion);
                GL11.glTranslated((double)(xx + 65), (double)(yy + 100), 0.0);
                GL11.glScaled(highlight ? 1.75 : 1.5, highlight ? 1.75 : 1.5, 0.0);
                UtilsFX.drawTexturedQuadFull(-8.0f, -8.0f, 0.0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
            for (final Long seed : this.table.data.savedCards) {
                r2 = new Random(seed);
                this.drawSheet(xx + 191, yy + 100, 6.0, r2, 1.0f, 1.0f, null);
            }
            if (this.lastDraw != null) {
                r2 = new Random(this.lastDraw.card.getSeed());
                this.drawSheet(xx + 191, yy + 100, 6.0, r2, 1.0f, 1.0f, this.lastDraw);
            }
            final ArrayList<String> sparkle = new ArrayList<String>();
            if (this.nexCatCheck < this.player.field_70173_aa) {
                for (final String cat : ResearchCategories.researchCategories.keySet()) {
                    int t0 = 0;
                    if (this.table.data.categoryTotals.containsKey(cat)) {
                        t0 = this.table.data.categoryTotals.get(cat);
                    }
                    int t2 = 0;
                    if (this.tempCatTotals.containsKey(cat)) {
                        t2 = this.tempCatTotals.get(cat);
                    }
                    if (t0 == 0 && t2 == 0) {
                        this.tempCatTotals.remove(cat);
                    }
                    else {
                        if (t2 > t0) {
                            --t2;
                        }
                        if (t2 < t0) {
                            ++t2;
                            sparkle.add(cat);
                        }
                        this.tempCatTotals.put(cat, t2);
                    }
                }
                this.nexCatCheck = this.player.field_70173_aa + 1;
            }
            final HashMap<String, Integer> unsortedMap = new HashMap<String, Integer>();
            String hf = null;
            int lf = 0;
            for (final String cat2 : this.tempCatTotals.keySet()) {
                final int cf = this.tempCatTotals.get(cat2);
                if (cf == 0) {
                    continue;
                }
                if (cf > lf) {
                    lf = cf;
                    hf = cat2;
                }
                unsortedMap.put(cat2, cf);
            }
            if (hf != null) {
                unsortedMap.put(hf, unsortedMap.get(hf));
            }
            final Comparator<Map.Entry<String, Integer>> valueComparator = (e1, e2) -> e2.getValue().compareTo(e1.getValue());
            final Map<String, Integer> sortedMap = unsortedMap.entrySet().stream().sorted((Comparator<? super Object>)valueComparator).collect((Collector<? super Object, ?, Map<String, Integer>>)Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue, (e1, e2) -> e1, (Supplier<R>)LinkedHashMap::new));
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            int i = 0;
            for (final String field : sortedMap.keySet()) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslatef((float)(xx + 253), (float)(yy + 16 + i * 18 + ((i > 0) ? 4 : 0)), 0.0f);
                GL11.glScaled(0.0625, 0.0625, 0.0625);
                this.field_146297_k.field_71446_o.func_110577_a(ResearchCategories.getResearchCategory(field).icon);
                this.func_73729_b(0, 0, 0, 0, 255, 255);
                GL11.glPopMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 5.0f);
                String s2 = sortedMap.get(field) + "%";
                if (i > this.table.data.penaltyStart) {
                    final int q = sortedMap.get(field) / 3;
                    s2 = s2 + " (-" + q + ")";
                }
                this.field_146297_k.field_71466_p.func_175063_a(s2, (float)(xx + 276), (float)(yy + 20 + i * 18 + ((i > this.table.data.penaltyStart) ? 4 : 0)), this.table.data.categoriesBlocked.contains(field) ? 6316128 : ((i <= this.table.data.penaltyStart) ? 57536 : 16777215));
                if (sparkle.contains(field)) {
                    for (int q = 0; q < 2; ++q) {
                        final float rr = MathHelper.func_76136_a(this.player.func_70681_au(), 255, 255) / 255.0f;
                        final float gg = MathHelper.func_76136_a(this.player.func_70681_au(), 189, 255) / 255.0f;
                        final float bb = MathHelper.func_76136_a(this.player.func_70681_au(), 64, 255) / 255.0f;
                        FXDispatcher.INSTANCE.drawSimpleSparkleGui(this.player.func_70681_au(), xx + 276 + this.player.func_70681_au().nextFloat() * this.field_146289_q.func_78256_a(s2), yy + 20 + this.player.func_70681_au().nextFloat() * 8.0f + i * 18 + ((i > this.table.data.penaltyStart) ? 4 : 0), this.player.field_70170_p.field_73012_v.nextGaussian() * 0.5, this.player.field_70170_p.field_73012_v.nextGaussian() * 0.5, 24.0f, rr, gg, bb, 0, 0.9f, -1.0f);
                    }
                }
                final int var9 = mx - (xx + 256);
                final int var10 = my - (yy + 16 + i * 18 + ((i > this.table.data.penaltyStart) ? 4 : 0));
                if (var9 >= 0 && var10 >= 0 && var9 < 16 && var10 < 16) {
                    GL11.glPushMatrix();
                    UtilsFX.drawCustomTooltip((GuiScreen)this, this.field_146289_q, Arrays.asList(ResearchCategories.getCategoryName(field)), mx + 8, my + 8, 11);
                    GL11.glPopMatrix();
                    RenderHelper.func_74518_a();
                }
                ++i;
            }
            final int sx = 128;
            final int cw = 110;
            final int sz = this.cardChoices.size();
            int a3 = 0;
            for (final ResearchTableData.CardChoice cardChoice : this.cardChoices) {
                r2 = new Random(cardChoice.card.getSeed());
                final int var11 = mx - (5 + sx - 55 * sz + xx + cw * a3);
                final int var12 = my - (100 + yy - 60);
                if (this.cardZoomOut[a3] >= 0.95 && !this.cardSelected) {
                    if (var11 >= 0 && var12 >= 0 && var11 < 100 && var12 < 120) {
                        final float[] cardHover = this.cardHover;
                        final int n = a3;
                        cardHover[n] += Math.max((0.25f - this.cardHover[a3]) / 3.0f * partialTicks, 0.0025f);
                    }
                    else {
                        final float[] cardHover2 = this.cardHover;
                        final int n2 = a3;
                        cardHover2[n2] -= 0.1f * partialTicks;
                    }
                }
                if (a3 == sz - 1 || this.cardZoomOut[a3 + 1] > 0.6) {
                    final float f = this.cardZoomOut[a3];
                    final float[] cardZoomOut = this.cardZoomOut;
                    final int n3 = a3;
                    cardZoomOut[n3] += Math.max((1.0f - this.cardZoomOut[a3]) / 5.0f * partialTicks, 0.0025f);
                    if (this.cardZoomOut[a3] > 0.0f && f == 0.0f) {
                        this.playButtonPageFlip();
                    }
                }
                final float prevZoomIn = this.cardZoomIn[a3];
                if (this.cardSelected) {
                    final float[] cardZoomIn = this.cardZoomIn;
                    final int n4 = a3;
                    cardZoomIn[n4] += (float)(this.cardActive[a3] ? Math.max((1.0f - this.cardZoomIn[a3]) / 3.0f * partialTicks, 0.0025) : (0.3f * partialTicks));
                    this.cardHover[a3] = 1.0f - this.cardZoomIn[a3];
                }
                this.cardZoomIn[a3] = MathHelper.func_76131_a(this.cardZoomIn[a3], 0.0f, 1.0f);
                this.cardHover[a3] = MathHelper.func_76131_a(this.cardHover[a3], 0.0f, 0.25f);
                this.cardZoomOut[a3] = MathHelper.func_76131_a(this.cardZoomOut[a3], 0.0f, 1.0f);
                final float dx = 55 + sx - 55 * sz + xx + cw * a3 - (xx + 65);
                float fx = xx + 65 + dx * this.cardZoomOut[a3];
                final float qx = xx + 191 - fx;
                if (this.cardActive[a3]) {
                    fx += qx * this.cardZoomIn[a3];
                }
                this.drawSheet(fx, yy + 100, 6.0f + this.cardZoomOut[a3] * 2.0f - this.cardZoomIn[a3] * 2.0f + this.cardHover[a3], r2, this.cardActive[a3] ? 1.0f : (1.0f - this.cardZoomIn[a3]), Math.max(1.0f - this.cardZoomOut[a3], this.cardZoomIn[a3]), cardChoice);
                if (this.cardSelected && this.cardActive[a3] && this.cardZoomIn[a3] >= 1.0f && prevZoomIn < 1.0f) {
                    this.playButtonWrite();
                    this.cardChoices.clear();
                    this.cardSelected = false;
                    this.lastDraw = this.table.data.lastDraw;
                    break;
                }
                ++a3;
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.func_74518_a();
    }
    
    private void drawSheet(final double x, final double y, final double scale, final Random r, final float alpha, final float tilt, final ResearchTableData.CardChoice cardChoice) {
        GL11.glPushMatrix();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        GL11.glTranslated(x + r.nextGaussian(), y + r.nextGaussian(), 0.0);
        GL11.glScaled(scale, scale, 0.0);
        GL11.glRotated(r.nextGaussian() * tilt, 0.0, 0.0, 1.0);
        GL11.glPushMatrix();
        if (cardChoice != null && cardChoice.fromAid) {
            this.field_146297_k.field_71446_o.func_110577_a(this.txPaperGilded);
        }
        else {
            this.field_146297_k.field_71446_o.func_110577_a(this.txPaper);
        }
        if (r.nextBoolean()) {
            GL11.glRotated(180.0, 0.0, 0.0, 1.0);
        }
        if (r.nextBoolean()) {
            GL11.glRotated(180.0, 0.0, 1.0, 0.0);
        }
        GL11.glDisable(2884);
        UtilsFX.drawTexturedQuadFull(-8.0f, -8.0f, 0.0);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        if (cardChoice != null && alpha == 1.0f) {
            if (cardChoice.card.getResearchCategory() != null) {
                final ResearchCategory rc = ResearchCategories.getResearchCategory(cardChoice.card.getResearchCategory());
                if (rc != null) {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha / 6.0f);
                    GL11.glPushMatrix();
                    GL11.glScaled(0.5, 0.5, 0.0);
                    this.field_146297_k.field_71446_o.func_110577_a(rc.icon);
                    UtilsFX.drawTexturedQuadFull(-8.0f, -8.0f, 0.0);
                    GL11.glPopMatrix();
                }
            }
            GL11.glPushMatrix();
            GL11.glScaled(0.0625, 0.0625, 0.0);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, alpha);
            final String name = TextFormatting.BOLD + cardChoice.card.getLocalizedName() + TextFormatting.RESET;
            final int sz = this.field_146289_q.func_78256_a(name);
            this.field_146289_q.func_78276_b(name, -sz / 2, -65, 0);
            this.field_146289_q.func_78279_b(cardChoice.card.getLocalizedText(), -70, -48, 140, 0);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
            GL11.glScaled(0.0625, 0.0625, 0.0);
            int cc = cardChoice.card.getInspirationCost();
            boolean add = false;
            if (cc < 0) {
                add = true;
                cc = Math.abs(cc) + 1;
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            for (int a = 0; a < cc; ++a) {
                if (a == 0 && add) {
                    this.func_73729_b(-10 * cc + 20 * a, -95, 48, 0, 16, 16);
                }
                else {
                    this.func_73729_b(-10 * cc + 20 * a, -95, 32, 96, 16, 16);
                }
            }
            GL11.glPopMatrix();
            if (cardChoice.card.getRequiredItems() != null) {
                final ItemStack[] items = cardChoice.card.getRequiredItems();
                GL11.glPushMatrix();
                for (int a2 = 0; a2 < items.length; ++a2) {
                    if (items[a2] == null || items[a2].func_190926_b()) {
                        GL11.glPushMatrix();
                        this.field_146297_k.field_71446_o.func_110577_a(this.txQuestion);
                        GL11.glScaled(0.125, 0.125, 0.0);
                        GL11.glColor4f(0.75f, 0.75f, 0.75f, alpha);
                        GL11.glTranslated((double)(-9 * items.length + 18 * a2), 35.0, 0.0);
                        UtilsFX.drawTexturedQuadFull(0.0f, 0.0f, 0.0);
                        GL11.glPopMatrix();
                    }
                    else {
                        GL11.glPushMatrix();
                        GL11.glScaled(0.125, 0.125, 0.0);
                        RenderHelper.func_74520_c();
                        GlStateManager.func_179140_f();
                        GlStateManager.func_179091_B();
                        GlStateManager.func_179142_g();
                        GlStateManager.func_179145_e();
                        this.field_146296_j.func_180450_b(items[a2], -9 * items.length + 18 * a2, 35);
                        GlStateManager.func_179140_f();
                        GlStateManager.func_179132_a(true);
                        GlStateManager.func_179126_j();
                        GL11.glPopMatrix();
                        try {
                            if (cardChoice.card.getRequiredItemsConsumed()[a2]) {
                                GL11.glPushMatrix();
                                this.field_146297_k.field_71446_o.func_110577_a(this.txBase);
                                GL11.glScaled(0.125, 0.125, 0.0);
                                final float s = (float)Math.sin((this.player.field_70173_aa + a2 * 2 + this.field_146297_k.func_184121_ak()) / 2.0f) * 0.03f;
                                GL11.glTranslated((double)(-2 - 9 * items.length + 18 * a2), (double)(45.0f + s * 10.0f), 0.0);
                                GL11.glScaled(0.5, 0.5 + s, 0.0);
                                this.func_73729_b(0, 0, 64, 120, 16, 16);
                                GL11.glPopMatrix();
                            }
                        }
                        catch (Exception ex) {}
                    }
                }
                GL11.glPopMatrix();
            }
        }
        GlStateManager.func_179084_k();
        GL11.glPopMatrix();
    }
    
    private void drawSheetOverlay(final double x, final double y, final ResearchTableData.CardChoice cardChoice, final int mx, final int my) {
        GL11.glPushMatrix();
        if (cardChoice != null && cardChoice.card.getRequiredItems() != null) {
            final ItemStack[] items = cardChoice.card.getRequiredItems();
            for (int a = 0; a < items.length; ++a) {
                if (this.func_146978_c((int)(x - 9 * items.length + 18 * a), (int)(y + 36.0), 15, 15, mx, my)) {
                    if (items[a] == null || items[a].func_190926_b()) {
                        this.func_146283_a((List)Arrays.asList(I18n.func_74838_a("tc.card.unknown")), mx, my);
                    }
                    else {
                        this.func_146285_a(items[a], mx, my);
                    }
                }
            }
        }
        GL11.glPopMatrix();
    }
    
    private void drawCards() {
        this.cardSelected = false;
        this.cardHover = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardZoomOut = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardZoomIn = new float[] { 0.0f, 0.0f, 0.0f };
        this.cardActive = new boolean[] { true, true, true };
        int draw = 2;
        if (this.table.data.bonusDraws > 0) {
            ++draw;
            final ResearchTableData data = this.table.data;
            --data.bonusDraws;
        }
        this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, draw);
        this.cardChoices.clear();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(this.buttonCreate);
        this.buttonCreate.field_146128_h = this.field_147003_i + 128;
        this.buttonCreate.field_146129_i = this.field_147009_r + 22;
        this.field_146292_n.add(this.buttonComplete);
        this.buttonComplete.field_146128_h = this.field_147003_i + 191;
        this.buttonComplete.field_146129_i = this.field_147009_r + 96;
        this.field_146292_n.add(this.buttonScrap);
        this.buttonScrap.field_146128_h = this.field_147003_i + 128;
        this.buttonScrap.field_146129_i = this.field_147009_r + 168;
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k == 1) {
            this.playButtonClick();
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketStartTheoryToServer(this.table.func_174877_v(), this.selectedAids));
        }
        else if (button.field_146127_k == 7) {
            this.playButtonClick();
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 7);
            this.tempCatTotals.clear();
            this.lastDraw = null;
        }
        else if (button.field_146127_k == 9) {
            this.playButtonClick();
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 9);
            this.tempCatTotals.clear();
            this.lastDraw = null;
            this.table.data = null;
            this.cardChoices.clear();
        }
        else {
            super.func_146284_a(button);
        }
    }
    
    private void checkButtons() {
        this.buttonComplete.active = false;
        this.buttonComplete.field_146125_m = false;
        this.buttonScrap.active = false;
        this.buttonScrap.field_146125_m = false;
        if (this.table.data != null) {
            this.buttonCreate.active = false;
            this.buttonCreate.field_146125_m = false;
            if (this.table.data.isComplete()) {
                this.buttonComplete.active = true;
                this.buttonComplete.field_146125_m = true;
            }
            else {
                this.buttonScrap.active = true;
                this.buttonScrap.field_146125_m = true;
            }
        }
        else {
            this.buttonCreate.field_146125_m = true;
            if (this.table.func_70301_a(1) == null || this.table.func_70301_a(0) == null || this.table.func_70301_a(0).func_77952_i() == this.table.func_70301_a(0).func_77958_k()) {
                this.buttonCreate.active = false;
            }
            else {
                this.buttonCreate.active = true;
            }
        }
    }
    
    protected void func_73864_a(final int mx, final int my, final int par3) throws IOException {
        super.func_73864_a(mx, my, par3);
        final int xx = (this.field_146294_l - this.field_146999_f) / 2;
        final int yy = (this.field_146295_m - this.field_147000_g) / 2;
        if (this.table.data == null) {
            if (!this.currentAids.isEmpty()) {
                final int side = Math.min(this.currentAids.size(), 6);
                int c = 0;
                int r = 0;
                for (final String key : this.currentAids) {
                    final ITheorycraftAid mutator = TheorycraftManager.aids.get(key);
                    if (mutator == null) {
                        continue;
                    }
                    final int x = 128 + 20 * c - side * 10;
                    final int y = 85 + 35 * r;
                    if (this.func_146978_c(x, y, 16, 16, mx, my)) {
                        if (this.selectedAids.contains(key)) {
                            this.selectedAids.remove(key);
                        }
                        else if (this.selectedAids.size() + 1 < this.dummyInspirationStart) {
                            this.selectedAids.add(key);
                        }
                    }
                    if (++c < side) {
                        continue;
                    }
                    ++r;
                    c = 0;
                }
            }
        }
        else {
            final int sx = 128;
            final int cw = 110;
            if (this.cardChoices.size() > 0) {
                int pressed = -1;
                for (int a = 0; a < this.cardChoices.size(); ++a) {
                    final int var7 = mx - (5 + sx - 55 * this.cardChoices.size() + xx + cw * a);
                    final int var8 = my - (100 + yy - 60);
                    if (this.cardZoomOut[a] >= 0.95 && !this.cardSelected && var7 >= 0 && var8 >= 0 && var7 < 100 && var8 < 120) {
                        pressed = a;
                        break;
                    }
                }
                if (pressed >= 0 && this.table.func_70301_a(0) != null && this.table.func_70301_a(0).func_77952_i() != this.table.func_70301_a(0).func_77958_k()) {
                    this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 4 + pressed);
                }
            }
            else {
                final int var9 = mx - (25 + xx);
                final int var10 = my - (55 + yy);
                if (var9 >= 0 && var10 >= 0 && var9 < 75 && var10 < 90 && this.table.func_70301_a(1) != null) {
                    this.drawCards();
                }
            }
        }
    }
    
    void checkCards() {
        if (this.table.data.cardChoices.size() > 0 && this.cardChoices.isEmpty()) {
            this.syncFromTableChoices();
        }
        if (!this.cardSelected) {
            for (int a = 0; a < this.cardChoices.size(); ++a) {
                try {
                    if (this.table.data != null && this.table.data.cardChoices.size() > a && this.table.data.cardChoices.get(a).selected) {
                        for (int q = 0; q < this.cardChoices.size(); ++q) {
                            this.cardActive[q] = this.table.data.cardChoices.get(q).selected;
                        }
                        this.cardSelected = true;
                        this.playButtonPageSelect();
                        this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 1);
                        break;
                    }
                }
                catch (Exception ex) {}
            }
        }
    }
    
    private void playButtonPageFlip() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.page, 1.0f, 1.0f);
    }
    
    private void playButtonPageSelect() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.pageturn, 1.0f, 1.0f);
    }
    
    private void playButtonClick() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.clack, 0.4f, 1.0f);
    }
    
    private void playButtonWrite() {
        this.field_146297_k.func_175606_aa().func_184185_a(SoundsTC.write, 0.3f, 1.0f);
    }
}
