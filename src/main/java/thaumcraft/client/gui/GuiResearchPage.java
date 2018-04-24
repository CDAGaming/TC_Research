package thaumcraft.client.gui;

import net.minecraft.client.gui.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.text.translation.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.lib.*;
import thaumcraft.client.lib.events.*;
import java.awt.*;
import net.minecraft.item.*;
import thaumcraft.api.research.*;
import thaumcraft.api.internal.*;
import thaumcraft.common.config.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.*;
import thaumcraft.api.crafting.*;
import net.minecraftforge.common.crafting.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.common.lib.*;
import java.io.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.playerdata.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import java.util.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;

@SideOnly(Side.CLIENT)
public class GuiResearchPage extends GuiScreen
{
    public static LinkedList<ResourceLocation> history;
    protected int paneWidth;
    protected int paneHeight;
    protected double guiMapX;
    protected double guiMapY;
    protected int mouseX;
    protected int mouseY;
    private ResearchEntry research;
    private int currentStage;
    int lastStage;
    boolean hold;
    private int page;
    private int maxPages;
    private int maxAspectPages;
    AspectList knownPlayerAspects;
    IPlayerKnowledge playerKnowledge;
    int rhash;
    float transX;
    float transY;
    float rotX;
    float rotY;
    float rotZ;
    long lastCheck;
    float pt;
    ResourceLocation tex1;
    ResourceLocation tex2;
    ResourceLocation tex3;
    ResourceLocation tex4;
    ResourceLocation dummyResearch;
    ResourceLocation dummyMap;
    ResourceLocation dummyFlask;
    ResourceLocation dummyChest;
    int hrx;
    int hry;
    static ResourceLocation shownRecipe;
    int recipePage;
    int recipePageMax;
    private long lastCycle;
    private boolean showingAspects;
    private boolean showingKnowledge;
    private static int aspectsPage;
    LinkedHashMap<ResourceLocation, ArrayList> recipeLists;
    LinkedHashMap<ResourceLocation, ArrayList> recipeOutputs;
    LinkedHashMap<ResourceLocation, ArrayList> drilldownLists;
    boolean hasRecipePages;
    boolean renderingCompound;
    static boolean cycleMultiblockLines;
    BlueprintBlockAccess blockAccess;
    HashMap<ResourceLocation, BlueprintBlockAccess> blockAccessIcons;
    ArrayList<List> reference;
    private int cycle;
    boolean allowWithPagePopup;
    List tipText;
    private static final int PAGEWIDTH = 140;
    private static final int PAGEHEIGHT = 210;
    private static final PageImage PILINE;
    private static final PageImage PIDIV;
    private ArrayList<Page> pages;
    boolean isComplete;
    boolean hasAllRequisites;
    boolean[] hasItem;
    boolean[] hasCraft;
    boolean[] hasResearch;
    boolean[] hasKnow;
    boolean[] hasStats;
    public HashMap<Integer, String> keyCache;
    
    public GuiResearchPage(final ResearchEntry research, final ResourceLocation recipe, final double x, final double y) {
        this.paneWidth = 256;
        this.paneHeight = 181;
        this.mouseX = 0;
        this.mouseY = 0;
        this.currentStage = 0;
        this.lastStage = 0;
        this.hold = false;
        this.page = 0;
        this.maxPages = 0;
        this.maxAspectPages = 0;
        this.rhash = 0;
        this.transX = 0.0f;
        this.transY = 0.0f;
        this.rotX = 0.0f;
        this.rotY = 0.0f;
        this.rotZ = 0.0f;
        this.lastCheck = 0L;
        this.tex1 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchbook.png");
        this.tex2 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchbook_overlay.png");
        this.tex3 = new ResourceLocation("thaumcraft", "textures/aspects/_back.png");
        this.tex4 = new ResourceLocation("thaumcraft", "textures/gui/paper.png");
        this.dummyResearch = new ResourceLocation("thaumcraft", "textures/aspects/_unknown.png");
        this.dummyMap = new ResourceLocation("thaumcraft", "textures/research/rd_map.png");
        this.dummyFlask = new ResourceLocation("thaumcraft", "textures/research/rd_flask.png");
        this.dummyChest = new ResourceLocation("thaumcraft", "textures/research/rd_chest.png");
        this.hrx = 0;
        this.hry = 0;
        this.recipePage = 0;
        this.recipePageMax = 0;
        this.lastCycle = 0L;
        this.showingAspects = false;
        this.showingKnowledge = false;
        this.recipeLists = new LinkedHashMap<ResourceLocation, ArrayList>();
        this.recipeOutputs = new LinkedHashMap<ResourceLocation, ArrayList>();
        this.drilldownLists = new LinkedHashMap<ResourceLocation, ArrayList>();
        this.renderingCompound = false;
        this.blockAccess = null;
        this.blockAccessIcons = new HashMap<ResourceLocation, BlueprintBlockAccess>();
        this.reference = new ArrayList<List>();
        this.cycle = -1;
        this.allowWithPagePopup = false;
        this.tipText = null;
        this.pages = new ArrayList<Page>();
        this.isComplete = false;
        this.hasAllRequisites = false;
        this.hasItem = null;
        this.hasCraft = null;
        this.hasResearch = null;
        this.hasKnow = null;
        this.hasStats = null;
        this.keyCache = new HashMap<Integer, String>();
        this.research = research;
        this.guiMapX = x;
        this.guiMapY = y;
        this.field_146297_k = Minecraft.func_71410_x();
        this.playerKnowledge = ThaumcraftCapabilities.getKnowledge((EntityPlayer)this.field_146297_k.field_71439_g);
        this.parsePages();
        this.knownPlayerAspects = new AspectList();
        for (final Aspect a : Aspect.aspects.values()) {
            if (ThaumcraftCapabilities.knowsResearch((EntityPlayer)this.field_146297_k.field_71439_g, "!" + a.getTag().toLowerCase())) {
                this.knownPlayerAspects.add(a, 1);
            }
        }
        this.maxAspectPages = ((this.knownPlayerAspects != null) ? MathHelper.func_76123_f(this.knownPlayerAspects.size() / 5.0f) : 0);
        this.page = 0;
        if (recipe != null) {
            GuiResearchPage.shownRecipe = recipe;
        }
    }
    
    public void func_73866_w_() {
        this.rotX = 25.0f;
        this.rotY = -45.0f;
    }
    
    public void func_73863_a(final int par1, final int par2, final float par3) {
        this.hasRecipePages = false;
        final long nano = System.nanoTime();
        if (nano > this.lastCheck) {
            this.parsePages();
            if (this.hold) {
                this.lastCheck = nano + 250000000L;
            }
            else {
                this.lastCheck = nano + 2000000000L;
            }
            if (this.currentStage > this.lastStage) {
                this.hold = false;
            }
        }
        this.pt = par3;
        this.func_146276_q_();
        this.genResearchBackground(par1, par2, par3);
        final int sw = (this.field_146294_l - this.paneWidth) / 2;
        final int sh = (this.field_146295_m - this.paneHeight) / 2;
        if (!GuiResearchPage.history.isEmpty()) {
            final int mx = par1 - (sw + 118);
            final int my = par2 - (sh + 190);
            if (mx >= 0 && my >= 0 && mx < 20 && my < 12) {
                this.field_146297_k.field_71466_p.func_175063_a(I18n.func_74838_a("recipe.return"), (float)par1, (float)par2, 16777215);
            }
        }
    }
    
    protected void genResearchBackground(final int par1, final int par2, final float par3) {
        final int sw = (this.field_146294_l - this.paneWidth) / 2;
        final int sh = (this.field_146295_m - this.paneHeight) / 2;
        final float var10 = (this.field_146294_l - this.paneWidth * 1.3f) / 2.0f;
        final float var11 = (this.field_146295_m - this.paneHeight * 1.3f) / 2.0f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glPushMatrix();
        GL11.glTranslatef(var10, var11, 0.0f);
        GL11.glScalef(1.3f, 1.3f, 1.0f);
        this.func_73729_b(0, 0, 0, 0, this.paneWidth, this.paneHeight);
        GL11.glPopMatrix();
        this.reference.clear();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        int current = 0;
        for (int a = 0; a < this.pages.size(); ++a) {
            if ((current == this.page || current == this.page + 1) && current < this.maxPages) {
                this.drawPage(this.pages.get(a), current % 2, sw, sh - 10, par1, par2);
            }
            if (++current > this.page + 1) {
                break;
            }
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
        final float bob = MathHelper.func_76126_a(this.field_146297_k.field_71439_g.field_70173_aa / 3.0f) * 0.2f + 0.1f;
        if (!GuiResearchPage.history.isEmpty()) {
            this.drawTexturedModalRectScaled(sw + 118, sh + 190, 38, 202, 20, 12, bob);
        }
        if (this.page > 0 && GuiResearchPage.shownRecipe == null) {
            this.drawTexturedModalRectScaled(sw - 16, sh + 190, 0, 184, 12, 8, bob);
        }
        if (this.page < this.maxPages - 2 && GuiResearchPage.shownRecipe == null) {
            this.drawTexturedModalRectScaled(sw + 262, sh + 190, 12, 184, 12, 8, bob);
        }
        if (this.tipText != null) {
            UtilsFX.drawCustomTooltip(this, this.field_146297_k.field_71466_p, this.tipText, par1, par2 + 12, 11);
            this.tipText = null;
        }
    }
    
    private void drawPage(final Page pageParm, final int side, final int x, int y, final int mx, final int my) {
        if (this.lastCycle < System.currentTimeMillis()) {
            ++this.cycle;
            this.lastCycle = System.currentTimeMillis() + 1000L;
            if (GuiResearchPage.cycleMultiblockLines && this.blockAccess != null) {
                final BlueprintBlockAccess blockAccess = this.blockAccess;
                ++blockAccess.sliceLine;
            }
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        if (this.page == 0 && side == 0) {
            this.func_73729_b(x + 4, y - 7, 24, 184, 96, 4);
            this.func_73729_b(x + 4, y + 10, 24, 184, 96, 4);
            final int offset = this.field_146297_k.field_71466_p.func_78256_a(this.research.getLocalizedName());
            if (offset <= 140) {
                this.field_146297_k.field_71466_p.func_78276_b(this.research.getLocalizedName(), x - 15 + 140 / 2 - offset / 2, y, 2105376);
            }
            else {
                final float vv = 140.0f / offset;
                GL11.glPushMatrix();
                GL11.glTranslatef(x - 15 + 140 / 2 - offset / 2 * vv, y + 1.0f * vv, 0.0f);
                GL11.glScalef(vv, vv, vv);
                this.field_146297_k.field_71466_p.func_78276_b(this.research.getLocalizedName(), 0, 0, 2105376);
                GL11.glPopMatrix();
            }
            y += 28;
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        for (final Object content : pageParm.contents) {
            if (content instanceof String) {
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                final String ss = ((String)content).replace("~B", "");
                this.field_146297_k.field_71466_p.func_78276_b(ss, x - 15 + side * 152, y - 6, 0);
                y += this.field_146297_k.field_71466_p.field_78288_b;
                if (!((String)content).endsWith("~B")) {
                    continue;
                }
                y += (int)(this.field_146297_k.field_71466_p.field_78288_b * 0.66);
            }
            else {
                if (!(content instanceof PageImage)) {
                    continue;
                }
                final PageImage pi = (PageImage)content;
                GL11.glPushMatrix();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                this.field_146297_k.field_71446_o.func_110577_a(pi.loc);
                final int pad = (140 - pi.aw) / 2;
                GL11.glTranslatef((float)(x - 15 + side * 152 + pad), (float)(y - 5), 0.0f);
                GL11.glScalef(pi.scale, pi.scale, pi.scale);
                this.func_73729_b(0, 0, pi.x, pi.y, pi.w, pi.h);
                GL11.glPopMatrix();
                y += pi.ah + 2;
            }
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        if (this.playerKnowledge.isResearchComplete("FIRSTSTEPS")) {
            y = (this.field_146295_m - this.paneHeight) / 2 + 9;
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            final int le = this.mouseInside(x - 48, y, 25, 16, mx, my) ? 0 : 3;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawPopupAt(x - 48, y, 25, 16, mx, my, "tc.aspect.name");
            this.func_73729_b(x - 48 + le, y, 76, 232, 24 - le, 16);
            this.func_73729_b(x - 28, y, 100, 232, 4, 16);
        }
        if (this.playerKnowledge.isResearchComplete("KNOWLEDGETYPES") && !this.research.getKey().equals("KNOWLEDGETYPES")) {
            y = (this.field_146295_m - this.paneHeight) / 2 + 32;
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            final int le = this.mouseInside(x - 48, y, 25, 16, mx, my) ? 0 : 3;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawPopupAt(x - 48, y, 25, 16, mx, my, "tc.knowledge.name");
            this.func_73729_b(x - 49 + le, y, 44, 232, 24 - le, 16);
            this.func_73729_b(x - 29, y, 100, 232, 4, 16);
        }
        final ResearchStage stage = this.research.getStages()[this.currentStage];
        if (stage.getRecipes() != null) {
            this.drawRecipeBookmarks(x, mx, my, stage);
        }
        if (this.page == 0 && side == 0 && !this.isComplete) {
            this.drawRequirements(x, mx, my, stage);
        }
        if (this.playerKnowledge.isResearchComplete("KNOWLEDGETYPES") && this.research.getKey().equals("KNOWLEDGETYPES")) {
            this.drawKnowledges(x, (this.field_146295_m - this.paneHeight) / 2 - 16 + 210, mx, my, true);
        }
        this.renderingCompound = false;
        if (this.showingAspects) {
            this.drawAspectsInsert(mx, my);
        }
        else if (this.showingKnowledge) {
            this.drawKnowledgesInsert(mx, my);
        }
        else if (GuiResearchPage.shownRecipe != null) {
            this.drawRecipe(mx, my);
        }
        else if (stage.getWarp() > 0 && !this.isComplete) {
            int warp = stage.getWarp();
            if (warp > 5) {
                warp = 5;
            }
            GuiResearchBrowser.drawForbidden(x - 45, y - 20);
            final String s = I18n.func_74838_a("tc.forbidden.level." + warp);
            this.field_146297_k.field_71466_p.func_78276_b(s, x - 44 - this.field_146297_k.field_71466_p.func_78256_a(s) / 2, y - 23, 11180543);
            final String text = I18n.func_74838_a("tc.warp.warn");
            this.drawPopupAt(x - 55, y - 30, 20, 20, mx, my, text.replaceAll("%n", s));
        }
    }
    
    private void drawKnowledgesInsert(final int mx, final int my) {
        this.allowWithPagePopup = true;
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex4);
        final int x = (this.field_146294_l - 256) / 2;
        final int y = (this.field_146295_m - 256) / 2;
        GlStateManager.func_179097_i();
        this.func_73729_b(x, y, 0, 0, 255, 255);
        GlStateManager.func_179126_j();
        GL11.glPushMatrix();
        this.drawKnowledges(x + 60, (this.field_146295_m - this.paneHeight) / 2 + 75, mx, my, false);
        GL11.glPopMatrix();
        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
        this.allowWithPagePopup = false;
    }
    
    private void drawKnowledges(final int x, int y, final int mx, final int my, final boolean inpage) {
        y -= 18;
        boolean drewSomething = false;
        int amt = 0;
        int par = 0;
        int tc = 0;
        for (final IPlayerKnowledge.EnumKnowledgeType type : IPlayerKnowledge.EnumKnowledgeType.values()) {
            int fc = 0;
            boolean b = false;
            for (final ResearchCategory category : ResearchCategories.researchCategories.values()) {
                if (!type.hasFields() && category != null) {
                    continue;
                }
                amt = this.playerKnowledge.getKnowledge(type, category);
                par = this.playerKnowledge.getKnowledgeRaw(type, category) % type.getProgression();
                if (amt <= 0 && par <= 0) {
                    continue;
                }
                drewSomething = true;
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPushMatrix();
                this.field_146297_k.field_71446_o.func_110577_a(HudHandler.KNOW_TYPE[type.ordinal()]);
                GL11.glTranslatef((float)(x - 10 + (inpage ? 18 : 23) * fc), (float)(y - tc * (inpage ? 20 : 28)), 0.0f);
                GL11.glScaled(0.0625, 0.0625, 0.0625);
                this.func_73729_b(0, 0, 0, 0, 255, 255);
                if (type.hasFields() && category != null) {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.75f);
                    this.field_146297_k.field_71446_o.func_110577_a(category.icon);
                    GL11.glTranslatef(0.0f, 0.0f, 1.0f);
                    GL11.glScaled(0.66, 0.66, 0.66);
                    this.func_73729_b(66, 66, 0, 0, 255, 255);
                }
                GL11.glPopMatrix();
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslatef(0.0f, 0.0f, 5.0f);
                String s = "" + amt;
                final int m = this.field_146297_k.field_71466_p.func_78256_a(s);
                this.field_146297_k.field_71466_p.func_175063_a(s, (float)(x - 10 + 16 - m + (inpage ? 18 : 23) * fc), (float)(y - tc * (inpage ? 20 : 28) + 8), 16777215);
                s = I18n.func_74838_a("tc.type." + type.toString().toLowerCase());
                if (type.hasFields() && category != null) {
                    s = s + ": " + ResearchCategories.getCategoryName(category.key);
                }
                this.drawPopupAt(x - 10 + (inpage ? 18 : 23) * fc, y - tc * (inpage ? 20 : 28), mx, my, s);
                if (par > 0) {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.75f);
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                    final int l = (int)(par / type.getProgression() * 16.0f);
                    this.func_73729_b(x - 10 + (inpage ? 18 : 23) * fc, y + 17 - tc * (inpage ? 20 : 28), 0, 232, l, 2);
                    this.func_73729_b(x - 10 + (inpage ? 18 : 23) * fc + l, y + 17 - tc * (inpage ? 20 : 28), l, 234, 16 - l, 2);
                }
                GL11.glTranslatef(0.0f, 0.0f, -5.0f);
                ++fc;
                b = true;
            }
            if (b) {
                ++tc;
            }
        }
        if (inpage && drewSomething) {
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x + 4, y - tc * (inpage ? 20 : 28) + 12, 24, 184, 96, 8);
        }
    }
    
    private void drawRequirements(final int x, final int mx, final int my, final ResearchStage stage) {
        int y = (this.field_146295_m - this.paneHeight) / 2 - 16 + 210;
        GL11.glPushMatrix();
        boolean b = false;
        if (stage.getResearch() != null) {
            y -= 18;
            b = true;
            int shift = 24;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.25f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x - 12, y - 1, 200, 232, 56, 16);
            this.drawPopupAt(x - 15, y, mx, my, "tc.need.research");
            Object loc = null;
            if (this.hasResearch != null) {
                if (this.hasResearch.length != stage.getResearch().length) {
                    this.hasResearch = new boolean[stage.getResearch().length];
                }
                int ss = 18;
                if (stage.getResearch().length > 6) {
                    ss = 110 / stage.getResearch().length;
                }
                for (int a = 0; a < stage.getResearch().length; ++a) {
                    final String key = stage.getResearch()[a];
                    loc = this.dummyResearch;
                    String text = I18n.func_74838_a("research." + key + ".text");
                    if (key.startsWith("!")) {
                        final String k = key.replaceAll("!", "");
                        final Aspect as = Aspect.aspects.get(k);
                        if (as != null) {
                            loc = as;
                            text = as.getName();
                        }
                    }
                    final ResearchEntry re = ResearchCategories.getResearch(key);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    if (re != null && re.getIcons() != null) {
                        final int idx = (int)(System.currentTimeMillis() / 1000L % re.getIcons().length);
                        loc = re.getIcons()[idx];
                        text = re.getLocalizedName();
                    }
                    else if (key.startsWith("m_")) {
                        loc = this.dummyMap;
                    }
                    else if (key.startsWith("c_")) {
                        loc = this.dummyChest;
                    }
                    else if (key.startsWith("f_")) {
                        loc = this.dummyFlask;
                    }
                    else {
                        GlStateManager.func_179131_c(0.5f, 0.75f, 1.0f, 1.0f);
                    }
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    if (loc instanceof Aspect) {
                        this.field_146297_k.field_71446_o.func_110577_a(((Aspect)loc).getImage());
                        final Color cc = new Color(((Aspect)loc).getColor());
                        GlStateManager.func_179131_c(cc.getRed() / 255.0f, cc.getGreen() / 255.0f, cc.getBlue() / 255.0f, 1.0f);
                        UtilsFX.drawTexturedQuadFull(x - 15 + shift, y, this.field_73735_i);
                    }
                    else if (loc instanceof ResourceLocation) {
                        this.field_146297_k.field_71446_o.func_110577_a((ResourceLocation)loc);
                        UtilsFX.drawTexturedQuadFull(x - 15 + shift, y, this.field_73735_i);
                    }
                    else if (loc instanceof ItemStack) {
                        RenderHelper.func_74520_c();
                        GL11.glDisable(2896);
                        GL11.glEnable(32826);
                        GL11.glEnable(2903);
                        GL11.glEnable(2896);
                        this.field_146296_j.func_180450_b(InventoryUtils.cycleItemStack(loc), x - 15 + shift, y);
                        GL11.glDisable(2896);
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                    }
                    GL11.glPopMatrix();
                    if (this.hasResearch[a]) {
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                        GlStateManager.func_179097_i();
                        this.func_73729_b(x - 15 + shift + 8, y, 159, 207, 10, 10);
                        GlStateManager.func_179126_j();
                    }
                    this.drawPopupAt(x - 15 + shift, y, mx, my, text);
                    shift += ss;
                }
            }
        }
        if (stage.getObtain() != null) {
            y -= 18;
            b = true;
            int shift = 24;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.25f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x - 12, y - 1, 200, 216, 56, 16);
            this.drawPopupAt(x - 15, y, mx, my, "tc.need.obtain");
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.hasItem != null) {
                if (this.hasItem.length != stage.getObtain().length) {
                    this.hasItem = new boolean[stage.getObtain().length];
                }
                int ss2 = 18;
                if (stage.getObtain().length > 6) {
                    ss2 = 110 / stage.getObtain().length;
                }
                for (int idx2 = 0; idx2 < stage.getObtain().length; ++idx2) {
                    final ItemStack stack = stage.getObtain()[idx2];
                    this.drawStackAt(stack, x - 15 + shift, y, mx, my, true);
                    if (this.hasItem[idx2]) {
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                        GlStateManager.func_179097_i();
                        this.func_73729_b(x - 15 + shift + 8, y, 159, 207, 10, 10);
                        GlStateManager.func_179126_j();
                    }
                    shift += ss2;
                }
            }
        }
        if (stage.getCraft() != null) {
            y -= 18;
            b = true;
            int shift = 24;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.25f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x - 12, y - 1, 200, 200, 56, 16);
            this.drawPopupAt(x - 15, y, mx, my, "tc.need.craft");
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.hasCraft != null) {
                if (this.hasCraft.length != stage.getCraft().length) {
                    this.hasCraft = new boolean[stage.getCraft().length];
                }
                int ss2 = 18;
                if (stage.getCraft().length > 6) {
                    ss2 = 110 / stage.getCraft().length;
                }
                for (int idx2 = 0; idx2 < stage.getCraft().length; ++idx2) {
                    final ItemStack stack = stage.getCraft()[idx2];
                    this.drawStackAt(stack, x - 15 + shift, y, mx, my, true);
                    if (this.hasCraft[idx2]) {
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                        GlStateManager.func_179097_i();
                        this.func_73729_b(x - 15 + shift + 8, y, 159, 207, 10, 10);
                        GlStateManager.func_179126_j();
                    }
                    shift += ss2;
                }
            }
        }
        if (stage.getKnow() != null) {
            y -= 18;
            b = true;
            int shift = 24;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.25f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x - 12, y - 1, 200, 184, 56, 16);
            this.drawPopupAt(x - 15, y, mx, my, "tc.need.know");
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.hasKnow != null) {
                if (this.hasKnow.length != stage.getKnow().length) {
                    this.hasKnow = new boolean[stage.getKnow().length];
                }
                int ss2 = 18;
                if (stage.getKnow().length > 6) {
                    ss2 = 110 / stage.getKnow().length;
                }
                for (int idx2 = 0; idx2 < stage.getKnow().length; ++idx2) {
                    final ResearchStage.Knowledge kn = stage.getKnow()[idx2];
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPushMatrix();
                    this.field_146297_k.field_71446_o.func_110577_a(HudHandler.KNOW_TYPE[kn.type.ordinal()]);
                    GL11.glTranslatef((float)(x - 15 + shift), (float)y, 0.0f);
                    GL11.glScaled(0.0625, 0.0625, 0.0625);
                    this.func_73729_b(0, 0, 0, 0, 255, 255);
                    if (kn.type.hasFields() && kn.category != null) {
                        this.field_146297_k.field_71446_o.func_110577_a(kn.category.icon);
                        GL11.glTranslatef(32.0f, 32.0f, 1.0f);
                        GL11.glPushMatrix();
                        GL11.glScaled(0.75, 0.75, 0.75);
                        this.func_73729_b(0, 0, 0, 0, 255, 255);
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();
                    final String am = "" + (this.hasKnow[idx2] ? "" : TextFormatting.RED) + kn.amount;
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)(x - 15 + shift + 16 - this.field_146297_k.field_71466_p.func_78256_a(am) / 2), (float)(y + 12), 5.0f);
                    GL11.glScaled(0.5, 0.5, 0.5);
                    this.field_146297_k.field_71466_p.func_175063_a(am, 0.0f, 0.0f, 16777215);
                    GL11.glPopMatrix();
                    if (this.hasKnow[idx2]) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef(0.0f, 0.0f, 1.0f);
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                        this.func_73729_b(x - 15 + shift + 8, y, 159, 207, 10, 10);
                        GL11.glPopMatrix();
                    }
                    String s = I18n.func_74838_a("tc.type." + kn.type.toString().toLowerCase());
                    if (kn.type.hasFields() && kn.category != null) {
                        s = s + ": " + ResearchCategories.getCategoryName(kn.category.key);
                    }
                    this.drawPopupAt(x - 15 + shift, y, mx, my, s);
                    shift += ss2;
                }
            }
        }
        if (b) {
            y -= 12;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            this.func_73729_b(x + 4, y - 2, 24, 184, 96, 8);
            if (this.hasAllRequisites) {
                this.hrx = x + 20;
                this.hry = y - 6;
                if (this.hold) {
                    final String s2 = I18n.func_74838_a("tc.stage.hold");
                    final int m = this.field_146297_k.field_71466_p.func_78256_a(s2);
                    this.field_146297_k.field_71466_p.func_175063_a(s2, x + 52 - m / 2.0f, (float)(y - 4), 16777215);
                }
                else {
                    if (this.mouseInside(this.hrx, this.hry, 64, 12, mx, my)) {
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    else {
                        GlStateManager.func_179131_c(0.8f, 0.8f, 0.9f, 1.0f);
                    }
                    this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                    this.func_73729_b(this.hrx, this.hry, 84, 216, 64, 12);
                    final String s2 = I18n.func_74838_a("tc.stage.complete");
                    final int m = this.field_146297_k.field_71466_p.func_78256_a(s2);
                    this.field_146297_k.field_71466_p.func_175063_a(s2, x + 52 - m / 2.0f, (float)(y - 4), 16777215);
                }
            }
        }
        GL11.glPopMatrix();
    }
    
    private void drawRecipeBookmarks(final int x, final int mx, final int my, final ResearchStage stage) {
        final Random rng = new Random(this.rhash);
        GL11.glPushMatrix();
        int y = (this.field_146295_m - this.paneHeight) / 2 - 8;
        this.allowWithPagePopup = true;
        final int space = Math.min(25, 200 / this.recipeOutputs.size());
        for (final ResourceLocation rk : this.recipeOutputs.keySet()) {
            final List list = this.recipeOutputs.get(rk);
            if (list != null && list.size() > 0) {
                final int i = this.cycle % list.size();
                if (list.get(i) == null) {
                    continue;
                }
                final int sh = rng.nextInt(3);
                final int le = rng.nextInt(3) + (this.mouseInside(x + 280, y - 1, 30, 16, mx, my) ? 0 : 3);
                this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                if (rk.equals((Object)GuiResearchPage.shownRecipe)) {
                    GlStateManager.func_179131_c(1.0f, 0.5f, 0.5f, 1.0f);
                }
                else {
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                }
                this.func_73729_b(x + 280 + sh, y - 1, 120 + le, 232, 28, 16);
                this.func_73729_b(x + 280 + sh, y - 1, 116, 232, 4, 16);
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                UtilsFX.hideStackOverlay = true;
                if (list.get(i) instanceof ItemStack) {
                    this.drawStackAt(list.get(i), x + 287 + sh - le, y - 1, mx, my, false);
                }
                else if (list.get(i) instanceof Part[][][]) {
                    BlueprintBlockAccess ba = this.blockAccessIcons.get(rk);
                    if (ba == null) {
                        this.blockAccessIcons.put(rk, ba = new BlueprintBlockAccess(list.get(i), true));
                    }
                    final int h = ((Part[][][])list.get(i)).length;
                    this.renderBluePrint(ba, x + 295 + sh - le, y + 6 + h, 4.0f, list.get(i), -5000, -5000, null);
                }
                UtilsFX.hideStackOverlay = false;
                y += space;
            }
        }
        this.allowWithPagePopup = false;
        GL11.glPopMatrix();
    }
    
    private void generateRecipesLists(final ResearchStage stage, final ResearchAddendum[] addenda) {
        this.recipeLists.clear();
        this.recipeOutputs.clear();
        if (stage == null || stage.getRecipes() == null) {
            return;
        }
        for (final ResourceLocation rk : stage.getRecipes()) {
            this.addRecipesToList(rk, this.recipeLists, this.recipeOutputs, rk);
        }
        if (addenda == null) {
            return;
        }
        for (final ResearchAddendum addendum : addenda) {
            if (addendum.getRecipes() != null && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, addendum.getResearch())) {
                for (final ResourceLocation rk2 : addendum.getRecipes()) {
                    this.addRecipesToList(rk2, this.recipeLists, this.recipeOutputs, rk2);
                }
            }
        }
    }
    
    private void addRecipesToList(final ResourceLocation rk, final LinkedHashMap<ResourceLocation, ArrayList> recipeLists2, final LinkedHashMap<ResourceLocation, ArrayList> recipeOutputs2, final ResourceLocation rkey) {
        Object recipe = CommonInternals.getCatalogRecipe(rk);
        if (recipe == null) {
            recipe = CommonInternals.getCatalogRecipeFake(rk);
        }
        if (recipe == null) {
            recipe = CraftingManager.func_193373_a(rk);
        }
        if (recipe == null) {
            recipe = ConfigRecipes.recipeGroups.get(rk.toString());
        }
        if (recipe == null) {
            return;
        }
        if (recipe instanceof ArrayList) {
            for (final ResourceLocation rl : (ArrayList)recipe) {
                this.addRecipesToList(rl, recipeLists2, recipeOutputs2, rk);
            }
        }
        else {
            if (!recipeLists2.containsKey(rkey)) {
                recipeLists2.put(rkey, new ArrayList());
                recipeOutputs2.put(rkey, new ArrayList());
            }
            final ArrayList list = recipeLists2.get(rkey);
            final ArrayList outputs = recipeOutputs2.get(rkey);
            if (recipe instanceof ThaumcraftApi.BluePrint) {
                final ThaumcraftApi.BluePrint r = (ThaumcraftApi.BluePrint)recipe;
                if (ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, r.getResearch())) {
                    list.add(r);
                    if (r.getDisplayStack() != null) {
                        outputs.add(r.getDisplayStack());
                    }
                    else {
                        outputs.add(r.getParts());
                    }
                }
            }
            else if (recipe instanceof CrucibleRecipe) {
                final CrucibleRecipe re = (CrucibleRecipe)recipe;
                final ItemStack is = InventoryUtils.cycleItemStack(re.getCatalyst(), 0);
                if (is != null && !is.func_190926_b() && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, re.getResearch())) {
                    list.add(re);
                    outputs.add(re.getRecipeOutput());
                }
            }
            else if (recipe instanceof InfusionRecipe) {
                final InfusionRecipe re2 = (InfusionRecipe)recipe;
                ItemStack is = null;
                if (re2 instanceof InfusionEnchantmentRecipe) {
                    is = InventoryUtils.cycleItemStack(re2.getRecipeOutput((EntityPlayer)this.field_146297_k.field_71439_g, re2.getRecipeInput().func_193365_a()[0], null), 0);
                }
                else if (re2 instanceof InfusionRunicAugmentRecipe) {
                    final NonNullList<Ingredient> il = ((InfusionRunicAugmentRecipe)re2).getComponents(re2.getRecipeInput().func_193365_a()[0]);
                    final List<ItemStack> cl = new ArrayList<ItemStack>();
                    for (final Ingredient i : il) {
                        cl.add(i.func_193365_a()[0]);
                    }
                    is = InventoryUtils.cycleItemStack(re2.getRecipeOutput((EntityPlayer)this.field_146297_k.field_71439_g, re2.getRecipeInput().func_193365_a()[0], cl), 0);
                }
                else if (re2.getRecipeOutput() instanceof ItemStack) {
                    is = InventoryUtils.cycleItemStack(re2.getRecipeOutput(), 0);
                }
                else {
                    is = InventoryUtils.cycleItemStack(re2.getRecipeInput());
                    if (is != null && !is.func_190926_b()) {
                        is = is.func_77946_l();
                    }
                    try {
                        final Object[] obj = (Object[])re2.getRecipeOutput();
                        final NBTBase tag = (NBTBase)obj[1];
                        is.func_77983_a((String)obj[0], tag);
                    }
                    catch (Exception ex) {}
                }
                if (is != null && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, re2.research)) {
                    list.add(re2);
                    outputs.add(is);
                }
            }
            else if (recipe instanceof IArcaneRecipe) {
                final IArcaneRecipe re3 = (IArcaneRecipe)recipe;
                final ItemStack is = InventoryUtils.cycleItemStack(re3.func_77571_b(), 0);
                if (is != null && !is.func_190926_b() && ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, re3.getResearch())) {
                    list.add(re3);
                    outputs.add(re3.func_77571_b());
                }
            }
            else if (recipe instanceof IRecipe) {
                final IRecipe re4 = (IRecipe)recipe;
                list.add(re4);
                outputs.add(re4.func_77571_b());
            }
            else if (recipe instanceof RecipeMisc) {
                final RecipeMisc re5 = (RecipeMisc)recipe;
                list.add(re5);
                outputs.add(re5.getOutput());
            }
        }
    }
    
    private void drawRecipe(final int mx, final int my) {
        this.allowWithPagePopup = true;
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex4);
        final int x = (this.field_146294_l - 256) / 2;
        final int y = (this.field_146295_m - 256) / 2;
        GlStateManager.func_179097_i();
        this.func_73729_b(x, y, 0, 0, 255, 255);
        GlStateManager.func_179126_j();
        List list = this.recipeLists.get(GuiResearchPage.shownRecipe);
        if (list == null || list.size() == 0) {
            list = this.drilldownLists.get(GuiResearchPage.shownRecipe);
        }
        if (list != null && list.size() > 0) {
            this.hasRecipePages = (list.size() > 1);
            this.recipePageMax = list.size() - 1;
            if (this.recipePage > this.recipePageMax) {
                this.recipePage = this.recipePageMax;
            }
            final Object recipe = list.get(this.recipePage % list.size());
            if (recipe != null) {
                if (recipe instanceof IArcaneRecipe) {
                    this.drawArcaneCraftingPage(x + 128, y + 128, mx, my, (IArcaneRecipe)recipe);
                }
                else if (recipe instanceof IRecipe) {
                    this.drawCraftingPage(x + 128, y + 128, mx, my, (IRecipe)recipe);
                }
                else if (recipe instanceof CrucibleRecipe) {
                    this.drawCruciblePage(x + 128, y + 128, mx, my, (CrucibleRecipe)recipe);
                }
                else if (recipe instanceof InfusionRecipe) {
                    this.drawInfusionPage(x + 128, y + 128, mx, my, (InfusionRecipe)recipe);
                }
                else if (recipe instanceof ThaumcraftApi.BluePrint) {
                    this.drawCompoundCraftingPage(x + 128, y + 128, mx, my, (ThaumcraftApi.BluePrint)recipe);
                    this.renderingCompound = true;
                }
            }
            if (this.hasRecipePages) {
                this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
                final float bob = MathHelper.func_76126_a(this.field_146297_k.field_71439_g.field_70173_aa / 3.0f) * 0.2f + 0.1f;
                if (this.recipePage > 0) {
                    this.drawTexturedModalRectScaled(x + 40, y + 232, 0, 184, 12, 8, bob);
                }
                if (this.recipePage < this.recipePageMax) {
                    this.drawTexturedModalRectScaled(x + 204, y + 232, 12, 184, 12, 8, bob);
                }
            }
        }
        this.allowWithPagePopup = false;
    }
    
    private void drawCompoundCraftingPage(final int x, final int y, final int mx, final int my, final ThaumcraftApi.BluePrint recipe) {
        if (recipe.getParts() == null) {
            return;
        }
        if (this.blockAccess == null) {
            this.blockAccess = new BlueprintBlockAccess(recipe.getParts(), false);
        }
        final int ySize = recipe.getParts().length;
        final int xSize = recipe.getParts()[0].length;
        final int zSize = recipe.getParts()[0][0].length;
        final String text = I18n.func_74838_a("recipe.type.construct");
        final int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
        this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
        final int s = Math.max(Math.max(xSize, zSize), ySize) * 2;
        final float scale = 38 - s;
        this.renderBluePrint(this.blockAccess, x, y, scale, recipe.getParts(), mx, my, recipe.getIngredientList());
        this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, this.mouseInside(x + 80, y + 100, 8, 8, mx, my) ? 1.0f : 0.75f);
        this.func_73729_b(x + 80, y + 100, GuiResearchPage.cycleMultiblockLines ? 168 : 160, 224, 8, 8);
    }
    
    private void renderBluePrint(final BlueprintBlockAccess ba, final int x, final int y, final float scale, final Part[][][] blueprint, final int mx, final int my, final ItemStack[] ingredients) {
        final BlockRendererDispatcher blockRender = Minecraft.func_71410_x().func_175602_ab();
        final int ySize = blueprint.length;
        final int xSize = blueprint[0].length;
        final int zSize = blueprint[0][0].length;
        this.transX = x - xSize / 2;
        this.transY = y - (float)Math.sqrt(ySize * ySize + xSize * xSize + zSize * zSize) / 2.0f;
        GlStateManager.func_179091_B();
        GlStateManager.func_179094_E();
        RenderHelper.func_74518_a();
        GlStateManager.func_179109_b(this.transX, this.transY, (float)Math.max(ySize, Math.max(xSize, zSize)));
        GlStateManager.func_179152_a(scale, -scale, 1.0f);
        GlStateManager.func_179114_b(this.rotX, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(this.rotY, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179109_b(zSize / -2.0f, ySize / -2.0f, xSize / -2.0f);
        GlStateManager.func_179140_f();
        if (Minecraft.func_71379_u()) {
            GlStateManager.func_179103_j(7425);
        }
        else {
            GlStateManager.func_179103_j(7424);
        }
        this.field_146297_k.func_110434_K().func_110577_a(TextureMap.field_110575_b);
        final ArrayList<ItemStack> blocks = new ArrayList<ItemStack>();
        for (int h = 0; h < ySize; ++h) {
            for (int l = 0; l < xSize; ++l) {
                for (int w = 0; w < zSize; ++w) {
                    final BlockPos pos = new BlockPos(l, h, w);
                    if (!ba.func_175623_d(pos)) {
                        GlStateManager.func_179109_b((float)l, (float)h, (float)w);
                        GlStateManager.func_179109_b((float)(-l), (float)(-h), (float)(-w));
                        final IBlockState state = ba.func_180495_p(pos);
                        final Tessellator tessellator = Tessellator.func_178181_a();
                        final BufferBuilder buffer = tessellator.func_178180_c();
                        buffer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
                        final boolean b = blockRender.func_175018_a(state, pos, (IBlockAccess)ba, buffer);
                        tessellator.func_78381_a();
                        try {
                            if (!b && state.func_177230_c().hasTileEntity(state)) {
                                final TileEntity te = state.func_177230_c().createTileEntity((World)this.field_146297_k.field_71441_e, state);
                                RenderHelper.func_74519_b();
                                final int i = 250;
                                final int j = i % 65536;
                                final int k = i / 65536;
                                OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
                                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                                TileEntityRendererDispatcher.field_147556_a.func_147549_a(te, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), this.field_146297_k.func_184121_ak());
                            }
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        }
        GlStateManager.func_179121_F();
        if (ingredients != null) {
            for (int a = 0; a < ingredients.length; ++a) {
                if (ingredients[a] != null && !ingredients[a].func_190926_b()) {
                    if (ingredients[a].func_77973_b() != null) {
                        RenderHelper.func_74520_c();
                        GL11.glDisable(2896);
                        GL11.glEnable(32826);
                        GL11.glEnable(2903);
                        GL11.glEnable(2896);
                        this.drawStackAt(ingredients[a], x - 85 + a * 17, y + 90, mx, my, true);
                        GL11.glDisable(2896);
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                    }
                }
            }
        }
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179147_l();
        RenderHelper.func_74518_a();
    }
    
    private void drawAspectsInsert(final int mx, final int my) {
        this.allowWithPagePopup = true;
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex4);
        final int x = (this.field_146294_l - 256) / 2;
        final int y = (this.field_146295_m - 256) / 2;
        GlStateManager.func_179097_i();
        this.func_73729_b(x, y, 0, 0, 255, 255);
        GlStateManager.func_179126_j();
        this.drawAspectPage(x + 60, y + 24, mx, my);
        this.allowWithPagePopup = false;
    }
    
    private void drawAspectPage(final int x, final int y, final int mx, final int my) {
        if (this.knownPlayerAspects != null && this.knownPlayerAspects.size() > 0) {
            GL11.glPushMatrix();
            int count = -1;
            final int start = GuiResearchPage.aspectsPage * 5;
            for (final Aspect aspect : this.knownPlayerAspects.getAspectsSortedByName()) {
                if (++count >= start) {
                    if (count > start + 4) {
                        break;
                    }
                    if (aspect.getImage() != null) {
                        final int ty = y + count % 5 * 40;
                        if (mx >= x && my >= ty && mx < x + 40 && my < ty + 40) {
                            this.field_146297_k.field_71446_o.func_110577_a(this.tex3);
                            GL11.glPushMatrix();
                            GlStateManager.func_179147_l();
                            GlStateManager.func_179112_b(770, 771);
                            GL11.glTranslated((double)(x - 2), (double)(y + count % 5 * 40 - 2), 0.0);
                            GL11.glScaled(2.0, 2.0, 0.0);
                            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.5f);
                            UtilsFX.drawTexturedQuadFull(0.0f, 0.0f, this.field_73735_i);
                            GL11.glPopMatrix();
                        }
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(x + 2), (double)(y + 2 + count % 5 * 40), 0.0);
                        GL11.glScalef(1.5f, 1.5f, 1.5f);
                        UtilsFX.drawTag(0, 0, aspect, 0.0f, 0, this.field_73735_i);
                        GL11.glPopMatrix();
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(x + 16), (double)(y + 29 + count % 5 * 40), 0.0);
                        GL11.glScalef(0.5f, 0.5f, 0.5f);
                        String text = aspect.getName();
                        int offset = this.field_146297_k.field_71466_p.func_78256_a(text) / 2;
                        this.field_146297_k.field_71466_p.func_78276_b(text, -offset, 0, 5263440);
                        GL11.glPopMatrix();
                        if (aspect.getComponents() != null) {
                            GL11.glPushMatrix();
                            GL11.glTranslated((double)(x + 60), (double)(y + 4 + count % 5 * 40), 0.0);
                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                            if (this.playerKnowledge.isResearchKnown("!" + aspect.getComponents()[0].getTag().toLowerCase())) {
                                UtilsFX.drawTag(0, 0, aspect.getComponents()[0], 0.0f, 0, this.field_73735_i);
                            }
                            else {
                                this.field_146297_k.field_71446_o.func_110577_a(this.dummyResearch);
                                GlStateManager.func_179131_c(0.8f, 0.8f, 0.8f, 1.0f);
                                UtilsFX.drawTexturedQuadFull(0.0f, 0.0f, this.field_73735_i);
                            }
                            GL11.glPopMatrix();
                            GL11.glPushMatrix();
                            GL11.glTranslated((double)(x + 102), (double)(y + 4 + count % 5 * 40), 0.0);
                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                            if (this.playerKnowledge.isResearchKnown("!" + aspect.getComponents()[1].getTag().toLowerCase())) {
                                UtilsFX.drawTag(0, 0, aspect.getComponents()[1], 0.0f, 0, this.field_73735_i);
                            }
                            else {
                                this.field_146297_k.field_71446_o.func_110577_a(this.dummyResearch);
                                GlStateManager.func_179131_c(0.8f, 0.8f, 0.8f, 1.0f);
                                UtilsFX.drawTexturedQuadFull(0.0f, 0.0f, this.field_73735_i);
                            }
                            GL11.glPopMatrix();
                            if (this.playerKnowledge.isResearchKnown("!" + aspect.getComponents()[0].getTag().toLowerCase())) {
                                text = aspect.getComponents()[0].getName();
                                offset = this.field_146297_k.field_71466_p.func_78256_a(text) / 2;
                                GL11.glPushMatrix();
                                GL11.glTranslated((double)(x + 22 + 50), (double)(y + 29 + count % 5 * 40), 0.0);
                                GL11.glScalef(0.5f, 0.5f, 0.5f);
                                this.field_146297_k.field_71466_p.func_78276_b(text, -offset, 0, 5263440);
                                GL11.glPopMatrix();
                            }
                            if (this.playerKnowledge.isResearchKnown("!" + aspect.getComponents()[1].getTag().toLowerCase())) {
                                text = aspect.getComponents()[1].getName();
                                offset = this.field_146297_k.field_71466_p.func_78256_a(text) / 2;
                                GL11.glPushMatrix();
                                GL11.glTranslated((double)(x + 22 + 92), (double)(y + 29 + count % 5 * 40), 0.0);
                                GL11.glScalef(0.5f, 0.5f, 0.5f);
                                this.field_146297_k.field_71466_p.func_78276_b(text, -offset, 0, 5263440);
                                GL11.glPopMatrix();
                            }
                            this.field_146297_k.field_71466_p.func_78276_b("=", x + 9 + 32, y + 12 + count % 5 * 40, 10066329);
                            this.field_146297_k.field_71466_p.func_78276_b("+", x + 10 + 79, y + 12 + count % 5 * 40, 10066329);
                        }
                        else {
                            this.field_146297_k.field_71466_p.func_78276_b(I18n.func_74838_a("tc.aspect.primal"), x + 54, y + 12 + count % 5 * 40, 7829367);
                        }
                    }
                }
            }
            this.field_146297_k.field_71446_o.func_110577_a(this.tex1);
            final float bob = MathHelper.func_76126_a(this.field_146297_k.field_71439_g.field_70173_aa / 3.0f) * 0.2f + 0.1f;
            if (GuiResearchPage.aspectsPage > 0) {
                this.drawTexturedModalRectScaled(x - 20, y + 208, 0, 184, 12, 8, bob);
            }
            if (GuiResearchPage.aspectsPage < this.maxAspectPages - 1) {
                this.drawTexturedModalRectScaled(x + 144, y + 208, 12, 184, 12, 8, bob);
            }
            GL11.glPopMatrix();
        }
    }
    
    private void drawArcaneCraftingPage(final int x, final int y, final int mx, final int my, final IArcaneRecipe recipe) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glPushMatrix();
        this.field_146297_k.field_71446_o.func_110577_a(this.tex2);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 1.0f);
        this.func_73729_b(-26, -26, 112, 15, 52, 52);
        this.func_73729_b(-8, -46, 20, 3, 16, 16);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 1.0f);
        this.func_73729_b(-6, 40, 68, 76, 12, 12);
        GL11.glPopMatrix();
        String text = "" + recipe.getVis();
        int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
        this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y + 90, 5263440);
        this.drawPopupAt(x - offset / 2 - 15, y + 75, 30, 30, mx, my, "wandtable.text1");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslated(0.0, 0.0, 100.0);
        this.drawStackAt(InventoryUtils.cycleItemStack(recipe.func_77571_b(), 0), x - 8, y - 84, mx, my, false);
        final AspectList crystals = recipe.getCrystals();
        if (crystals != null) {
            int a = 0;
            final int sz = crystals.size();
            for (final Aspect aspect : crystals.getAspects()) {
                this.drawStackAt(InventoryUtils.cycleItemStack(ThaumcraftApiHelper.makeCrystal(aspect, crystals.getAmount(aspect)), a), x + 4 - sz * 10 + a * 20, y + 59, mx, my, true);
                ++a;
            }
        }
        if (recipe != null && recipe instanceof ShapedArcaneRecipe) {
            text = I18n.func_74838_a("recipe.type.arcane");
            offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            final int rw = ((ShapedArcaneRecipe)recipe).getRecipeWidth();
            final int rh = ((ShapedArcaneRecipe)recipe).getRecipeHeight();
            final NonNullList<Ingredient> items = (NonNullList<Ingredient>)((ShapedArcaneRecipe)recipe).func_192400_c();
            for (int i = 0; i < rw && i < 3; ++i) {
                for (int j = 0; j < rh && j < 3; ++j) {
                    if (items.get(i + j * rw) != null) {
                        this.drawStackAt(InventoryUtils.cycleItemStack(items.get(i + j * rw), i + j * rw), x - 40 + i * 32, y - 40 + j * 32, mx, my, true);
                    }
                }
            }
        }
        if (recipe != null && recipe instanceof ShapelessArcaneRecipe) {
            text = I18n.func_74838_a("recipe.type.arcane.shapeless");
            offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            final NonNullList<Ingredient> items2 = (NonNullList<Ingredient>)((ShapelessArcaneRecipe)recipe).func_192400_c();
            for (int k = 0; k < items2.size() && k < 9; ++k) {
                if (items2.get(k) != null) {
                    this.drawStackAt(InventoryUtils.cycleItemStack(items2.get(k), k), x - 40 + k % 3 * 32, y - 40 + k / 3 * 32, mx, my, true);
                }
            }
        }
        GL11.glPopMatrix();
    }
    
    private void drawCraftingPage(final int x, final int y, final int mx, final int my, final IRecipe recipe) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        if (recipe == null) {
            return;
        }
        GL11.glPushMatrix();
        this.field_146297_k.field_71446_o.func_110577_a(this.tex2);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        GL11.glTranslatef((float)x, (float)y, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 1.0f);
        this.func_73729_b(-26, -26, 60, 15, 51, 52);
        this.func_73729_b(-8, -46, 20, 3, 16, 16);
        GL11.glPopMatrix();
        this.drawStackAt(InventoryUtils.cycleItemStack(recipe.func_77571_b(), 0), x - 8, y - 84, mx, my, false);
        if (recipe != null && recipe instanceof IShapedRecipe) {
            final String text = I18n.func_74838_a("recipe.type.workbench");
            final int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            final int rw = ((IShapedRecipe)recipe).getRecipeWidth();
            final int rh = ((IShapedRecipe)recipe).getRecipeHeight();
            final NonNullList<Ingredient> items = (NonNullList<Ingredient>)recipe.func_192400_c();
            for (int i = 0; i < rw && i < 3; ++i) {
                for (int j = 0; j < rh && j < 3; ++j) {
                    if (items.get(i + j * rw) != null) {
                        this.drawStackAt(InventoryUtils.cycleItemStack(items.get(i + j * rw), i + j * rw), x - 40 + i * 32, y - 40 + j * 32, mx, my, true);
                    }
                }
            }
        }
        if (recipe != null && (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe)) {
            final String text = I18n.func_74838_a("recipe.type.workbenchshapeless");
            final int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            final NonNullList<Ingredient> items2 = (NonNullList<Ingredient>)recipe.func_192400_c();
            for (int k = 0; k < items2.size() && k < 9; ++k) {
                if (items2.get(k) != null) {
                    this.drawStackAt(InventoryUtils.cycleItemStack(items2.get(k), k), x - 40 + k % 3 * 32, y - 40 + k / 3 * 32, mx, my, true);
                }
            }
        }
        GL11.glPopMatrix();
    }
    
    private void drawCruciblePage(final int x, final int y, final int mx, final int my, final CrucibleRecipe rc) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        if (rc != null) {
            GL11.glPushMatrix();
            final String text = I18n.func_74838_a("recipe.type.crucible");
            final int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex2);
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glScalef(2.0f, 2.0f, 1.0f);
            this.func_73729_b(-28, -29, 0, 3, 56, 17);
            GL11.glTranslatef(0.0f, 32.0f, 0.0f);
            this.func_73729_b(-28, -44, 0, 20, 56, 48);
            GL11.glTranslatef(0.0f, -8.0f, 0.0f);
            this.func_73729_b(-25, -50, 100, 84, 11, 13);
            GL11.glPopMatrix();
            int total = 0;
            final int rows = (rc.getAspects().size() - 1) / 3;
            final int shift = (3 - rc.getAspects().size() % 3) * 10;
            final int sx = x - 28;
            final int sy = y + 8 - 10 * rows;
            for (final Aspect tag : rc.getAspects().getAspectsSortedByName()) {
                int m = 0;
                if (total / 3 >= rows && (rows > 1 || rc.getAspects().size() < 3)) {
                    m = 1;
                }
                final int vx = sx + total % 3 * 20 + shift * m;
                final int vy = sy + total / 3 * 20;
                UtilsFX.drawTag(vx, vy, tag, rc.getAspects().getAmount(tag), 0, this.field_73735_i);
                ++total;
            }
            this.drawStackAt(rc.getRecipeOutput(), x - 8, y - 50, mx, my, false);
            this.drawStackAt(InventoryUtils.cycleItemStack(rc.getCatalyst(), 0), x - 64, y - 56, mx, my, true);
            total = 0;
            for (final Aspect tag : rc.getAspects().getAspectsSortedByName()) {
                int m = 0;
                if (total / 3 >= rows && (rows > 1 || rc.getAspects().size() < 3)) {
                    m = 1;
                }
                final int vx = sx + total % 3 * 20 + shift * m;
                final int vy = sy + total / 3 * 20;
                if (mx >= vx && my >= vy && mx < vx + 16 && my < vy + 16) {
                    this.tipText = Arrays.asList(tag.getName(), tag.getLocalizedDescription());
                }
                ++total;
            }
            GL11.glPopMatrix();
        }
    }
    
    private void drawInfusionPage(final int x, final int y, final int mx, final int my, final InfusionRecipe ri) {
        if (ri != null) {
            NonNullList<Ingredient> components = ri.getComponents();
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GL11.glPushMatrix();
            AspectList aspects = ri.getAspects();
            Object output = ri.getRecipeOutput();
            if (ri instanceof InfusionRunicAugmentRecipe) {
                final NonNullList<Ingredient> c = components = ((InfusionRunicAugmentRecipe)ri).getComponents(ri.getRecipeInput().func_193365_a()[0]);
                final ArrayList<ItemStack> com = new ArrayList<ItemStack>();
                for (final Ingredient s : c) {
                    com.add(s.func_193365_a()[0]);
                }
                aspects = ri.getAspects((EntityPlayer)this.field_146297_k.field_71439_g, ri.getRecipeInput().func_193365_a()[0], com);
                output = ((InfusionRunicAugmentRecipe)ri).getRecipeOutput((EntityPlayer)this.field_146297_k.field_71439_g, ri.getRecipeInput().func_193365_a()[0], com);
            }
            if (ri instanceof InfusionEnchantmentRecipe) {
                final ArrayList<ItemStack> com2 = new ArrayList<ItemStack>();
                for (final Object s2 : components) {
                    if (s2 instanceof ItemStack) {
                        com2.add((ItemStack)s2);
                    }
                }
                aspects = ri.getAspects((EntityPlayer)this.field_146297_k.field_71439_g, ri.getRecipeInput().func_193365_a()[0], com2);
                output = ((InfusionEnchantmentRecipe)ri).getRecipeOutput(null, ri.getRecipeInput().func_193365_a()[0], com2);
            }
            String text = I18n.func_74838_a("recipe.type.infusion");
            int offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y - 104, 5263440);
            this.field_146297_k.field_71446_o.func_110577_a(this.tex2);
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b(770, 771);
            GL11.glTranslatef((float)x, (float)(y + 20), 0.0f);
            GL11.glScalef(2.0f, 2.0f, 1.0f);
            this.func_73729_b(-28, -56, 0, 3, 56, 17);
            GL11.glTranslatef(0.0f, 19.0f, 0.0f);
            this.func_73729_b(-28, -55, 200, 77, 60, 44);
            GL11.glPopMatrix();
            int total = 0;
            int rows = (aspects.size() - 1) / 5;
            int shift = (5 - aspects.size() % 5) * 10;
            int sx = x - 48;
            int sy = y + 50 - 10 * rows;
            for (final Aspect tag : aspects.getAspectsSortedByName()) {
                int m = 0;
                if (total / 5 >= rows && (rows > 1 || aspects.size() < 5)) {
                    m = 1;
                }
                final int vx = sx + total % 5 * 20 + shift * m;
                final int vy = sy + total / 5 * 20;
                UtilsFX.drawTag(vx, vy, tag, aspects.getAmount(tag), 0, this.field_73735_i);
                ++total;
            }
            ItemStack idisp = null;
            if (output instanceof ItemStack) {
                idisp = InventoryUtils.cycleItemStack(output);
            }
            else {
                idisp = InventoryUtils.cycleItemStack(ri.getRecipeInput()).func_77946_l();
                try {
                    final Object[] obj = (Object[])output;
                    final NBTBase tag2 = (NBTBase)obj[1];
                    idisp.func_77983_a((String)obj[0], tag2);
                }
                catch (Exception ex) {}
            }
            this.drawStackAt(idisp, x - 8, y - 85, mx, my, false);
            final ItemStack rinp = InventoryUtils.cycleItemStack(ri.getRecipeInput()).func_77946_l();
            this.drawStackAt(rinp, x - 8, y - 16, mx, my, true);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, 0.0, 100.0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final int le = components.size();
            final ArrayList<PosXY> coords = new ArrayList<PosXY>();
            final float pieSlice = 360 / le;
            float currentRot = -90.0f;
            for (int a = 0; a < le; ++a) {
                final int xx = (int)(MathHelper.func_76134_b(currentRot / 180.0f * 3.1415927f) * 40.0f) - 8;
                final int yy = (int)(MathHelper.func_76126_a(currentRot / 180.0f * 3.1415927f) * 40.0f) - 8;
                currentRot += pieSlice;
                coords.add(new PosXY(xx, yy));
            }
            final ArrayList<ItemStack> cmps = new ArrayList<ItemStack>();
            total = 0;
            sy = y - 8;
            for (final Ingredient ingredient : components) {
                final int vx2 = x + coords.get(total).x;
                final int vy2 = sy + coords.get(total).y;
                final ItemStack is = InventoryUtils.cycleItemStack(ingredient);
                this.drawStackAt(is.func_77946_l().func_77979_a(1), vx2, vy2, mx, my, true);
                ++total;
                cmps.add(is.func_77946_l());
            }
            GL11.glPopMatrix();
            final int inst = Math.min(5, ri.getInstability((EntityPlayer)this.field_146297_k.field_71439_g, rinp, cmps) / 2);
            text = I18n.func_74838_a("tc.inst") + " " + I18n.func_74838_a("tc.inst." + inst);
            offset = this.field_146297_k.field_71466_p.func_78256_a(text);
            this.field_146297_k.field_71466_p.func_78276_b(text, x - offset / 2, y + 94, 5263440);
            total = 0;
            rows = (aspects.size() - 1) / 5;
            shift = (5 - aspects.size() % 5) * 10;
            sx = x - 48;
            sy = y + 50 - 10 * rows;
            for (final Aspect tag3 : aspects.getAspectsSortedByName()) {
                int i = 0;
                if (total / 5 >= rows && (rows > 1 || aspects.size() < 5)) {
                    i = 1;
                }
                final int vx3 = sx + total % 5 * 20 + shift * i;
                final int vy3 = sy + total / 5 * 20;
                if (mx >= vx3 && my >= vy3 && mx < vx3 + 16 && my < vy3 + 16) {
                    this.tipText = Arrays.asList(tag3.getName(), tag3.getLocalizedDescription());
                }
                ++total;
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_73869_a(final char par1, final int par2) throws IOException {
        if (par2 == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i() || par2 == 1) {
            GuiResearchPage.history.clear();
            if (GuiResearchPage.shownRecipe != null || this.showingAspects || this.showingKnowledge) {
                GuiResearchPage.shownRecipe = null;
                this.blockAccess = null;
                this.showingAspects = false;
                this.showingKnowledge = false;
                Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.4f, 1.1f);
            }
            else {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiResearchBrowser(this.guiMapX, this.guiMapY));
            }
        }
        else if (par2 == 203 || par2 == 200 || par2 == 201) {
            this.prevPage();
        }
        else if (par2 == 205 || par2 == 208 || par2 == 209) {
            this.nextPage();
        }
        else if (par2 == 14) {
            this.goBack();
        }
        else {
            super.func_73869_a(par1, par2);
        }
    }
    
    private void nextPage() {
        if (this.page < this.maxPages - 2) {
            this.page += 2;
            this.lastCycle = 0L;
            this.cycle = -1;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.66f, 1.0f);
        }
    }
    
    private void prevPage() {
        if (this.page >= 2) {
            this.page -= 2;
            this.lastCycle = 0L;
            this.cycle = -1;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.66f, 1.0f);
        }
    }
    
    private void goBack() {
        if (!GuiResearchPage.history.isEmpty()) {
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.66f, 1.0f);
            GuiResearchPage.shownRecipe = GuiResearchPage.history.pop();
            this.blockAccess = null;
        }
        else {
            GuiResearchPage.shownRecipe = null;
        }
    }
    
    protected void func_73864_a(final int par1, final int par2, final int par3) {
        this.checkRequisites();
        final int var4 = (this.field_146294_l - this.paneWidth) / 2;
        final int var5 = (this.field_146295_m - this.paneHeight) / 2;
        int mx = par1 - this.hrx;
        int my = par2 - this.hry;
        if (GuiResearchPage.shownRecipe == null && !this.hold && this.hasAllRequisites && mx >= 0 && my >= 0 && mx < 64 && my < 12) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketSyncProgressToServer(this.research.getKey(), false, true, true));
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.write, 0.66f, 1.0f);
            this.lastCheck = 0L;
            this.lastStage = this.currentStage;
            this.hold = true;
            this.keyCache.clear();
            this.drilldownLists.clear();
        }
        if (this.knownPlayerAspects != null && this.playerKnowledge.isResearchComplete("FIRSTSTEPS")) {
            mx = par1 - (var4 - 48);
            my = par2 - (var5 + 8);
            if (mx >= 0 && my >= 0 && mx < 25 && my < 16) {
                GuiResearchPage.shownRecipe = null;
                this.showingKnowledge = false;
                this.showingAspects = !this.showingAspects;
                this.blockAccess = null;
                GuiResearchPage.history.clear();
                if (GuiResearchPage.aspectsPage > this.maxAspectPages) {
                    GuiResearchPage.aspectsPage = 0;
                }
                Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
            }
        }
        if (this.playerKnowledge.isResearchComplete("KNOWLEDGETYPES") && !this.research.getKey().equals("KNOWLEDGETYPES")) {
            mx = par1 - (var4 - 48);
            my = par2 - (var5 + 31);
            if (mx >= 0 && my >= 0 && mx < 25 && my < 16) {
                GuiResearchPage.shownRecipe = null;
                this.showingAspects = false;
                this.showingKnowledge = !this.showingKnowledge;
                this.blockAccess = null;
                GuiResearchPage.history.clear();
                Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
            }
        }
        mx = par1 - (var4 + 205);
        my = par2 - (var5 + 192);
        if (this.showingAspects && GuiResearchPage.aspectsPage < this.maxAspectPages - 1 && mx >= 0 && my >= 0 && mx < 14 && my < 14) {
            ++GuiResearchPage.aspectsPage;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
        }
        mx = par1 - (var4 + 38);
        my = par2 - (var5 + 192);
        if (this.showingAspects && GuiResearchPage.aspectsPage > 0 && mx >= 0 && my >= 0 && mx < 14 && my < 14) {
            --GuiResearchPage.aspectsPage;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
        }
        if (this.recipeLists.size() > 0) {
            int aa = 0;
            final int space = Math.min(25, 200 / this.recipeLists.size());
            for (final ResourceLocation rk : this.recipeLists.keySet()) {
                mx = par1 - (var4 + 280);
                my = par2 - (var5 - 8 + aa * space);
                if (mx >= 0 && my >= 0 && mx < 30 && my < 16) {
                    if (rk.equals((Object)GuiResearchPage.shownRecipe)) {
                        GuiResearchPage.shownRecipe = null;
                    }
                    else {
                        GuiResearchPage.shownRecipe = rk;
                    }
                    this.showingAspects = false;
                    this.showingKnowledge = false;
                    this.blockAccess = null;
                    GuiResearchPage.history.clear();
                    Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
                    break;
                }
                ++aa;
            }
        }
        mx = par1 - (var4 + 205);
        my = par2 - (var5 + 192);
        if (this.hasRecipePages && this.recipePage < this.recipePageMax && mx >= 0 && my >= 0 && mx < 14 && my < 14) {
            ++this.recipePage;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
        }
        mx = par1 - (var4 + 38);
        my = par2 - (var5 + 192);
        if (this.hasRecipePages && this.recipePage > 0 && mx >= 0 && my >= 0 && mx < 14 && my < 14) {
            --this.recipePage;
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.7f, 0.9f);
        }
        mx = par1 - (var4 + 261);
        my = par2 - (var5 + 189);
        if (GuiResearchPage.shownRecipe == null && mx >= 0 && my >= 0 && mx < 14 && my < 10) {
            this.nextPage();
        }
        mx = par1 - (var4 - 17);
        my = par2 - (var5 + 189);
        if (GuiResearchPage.shownRecipe == null && mx >= 0 && my >= 0 && mx < 14 && my < 10) {
            this.prevPage();
        }
        mx = par1 - (var4 + 118);
        my = par2 - (var5 + 190);
        if (mx >= 0 && my >= 0 && mx < 20 && my < 12) {
            this.goBack();
        }
        mx = par1 - (var4 + 210);
        my = par2 - (var5 + 190);
        if (this.renderingCompound && mx >= 0 && my >= 0 && mx < 10 && my < 10) {
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.clack, 0.66f, 1.0f);
            GuiResearchPage.cycleMultiblockLines = !GuiResearchPage.cycleMultiblockLines;
        }
        if (this.reference.size() > 0) {
            for (final List coords : this.reference) {
                if (par1 >= coords.get(0) && par2 >= coords.get(1) && par1 < coords.get(0) + 16 && par2 < coords.get(1) + 16) {
                    Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.page, 0.66f, 1.0f);
                    GuiResearchPage.history.push(new ResourceLocation(GuiResearchPage.shownRecipe.func_110624_b(), GuiResearchPage.shownRecipe.func_110623_a()));
                    GuiResearchPage.shownRecipe = coords.get(2);
                    this.recipePage = Integer.parseInt(coords.get(3));
                    if (!this.drilldownLists.containsKey(GuiResearchPage.shownRecipe)) {
                        this.addRecipesToList(GuiResearchPage.shownRecipe, this.drilldownLists, new LinkedHashMap<ResourceLocation, ArrayList>(), GuiResearchPage.shownRecipe);
                    }
                    this.blockAccess = null;
                    break;
                }
            }
        }
        try {
            super.func_73864_a(par1, par2, par3);
        }
        catch (IOException ex) {}
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    void drawPopupAt(final int x, final int y, final int mx, final int my, final String text) {
        if ((GuiResearchPage.shownRecipe == null || this.allowWithPagePopup) && mx >= x && my >= y && mx < x + 16 && my < y + 16) {
            final ArrayList<String> s = new ArrayList<String>();
            s.add(I18n.func_74838_a(text));
            this.tipText = s;
        }
    }
    
    void drawPopupAt(final int x, final int y, final int w, final int h, final int mx, final int my, final String text) {
        if ((GuiResearchPage.shownRecipe == null || this.allowWithPagePopup) && mx >= x && my >= y && mx < x + w && my < y + h) {
            final ArrayList<String> s = new ArrayList<String>();
            s.add(I18n.func_74838_a(text));
            this.tipText = s;
        }
    }
    
    boolean mouseInside(final int x, final int y, final int w, final int h, final int mx, final int my) {
        return mx >= x && my >= y && mx < x + w && my < y + h;
    }
    
    void drawStackAt(final ItemStack itemstack, final int x, final int y, final int mx, final int my, final boolean clickthrough) {
        UtilsFX.renderItemStack(this.field_146297_k, itemstack, x, y, null);
        if ((GuiResearchPage.shownRecipe == null || this.allowWithPagePopup) && mx >= x && my >= y && mx < x + 16 && my < y + 16 && itemstack != null && !itemstack.func_190926_b() && itemstack.func_77973_b() != null) {
            if (clickthrough) {
                final List addtext = itemstack.func_82840_a((EntityPlayer)this.field_146297_k.field_71439_g, (ITooltipFlag)(Minecraft.func_71410_x().field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL));
                final String ref = this.getCraftingRecipeKey((EntityPlayer)this.field_146297_k.field_71439_g, itemstack);
                if (ref != null) {
                    final String[] sr = ref.split(";", 2);
                    final ResourceLocation res = new ResourceLocation(sr[0]);
                    if (res.func_110623_a().equals("UNKNOWN")) {
                        addtext.add(TextFormatting.DARK_RED + "" + TextFormatting.ITALIC + I18n.func_74838_a("recipe.unknown"));
                    }
                    else {
                        addtext.add(TextFormatting.BLUE + "" + TextFormatting.ITALIC + I18n.func_74838_a("recipe.clickthrough"));
                        this.reference.add(Arrays.asList(mx, my, res, sr[1]));
                    }
                }
                this.tipText = addtext;
            }
            else {
                this.tipText = itemstack.func_82840_a((EntityPlayer)this.field_146297_k.field_71439_g, (ITooltipFlag)(Minecraft.func_71410_x().field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL));
            }
        }
    }
    
    public void drawTexturedModalRectScaled(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6, final float scale) {
        GL11.glPushMatrix();
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.func_178181_a();
        GL11.glTranslatef(par1 + par5 / 2.0f, par2 + par6 / 2.0f, 0.0f);
        GL11.glScalef(1.0f + scale, 1.0f + scale, 1.0f);
        var9.func_178180_c().func_181668_a(7, DefaultVertexFormats.field_181707_g);
        var9.func_178180_c().func_181662_b((double)(-par5 / 2.0f), (double)(par6 / 2.0f), (double)this.field_73735_i).func_187315_a((double)((par3 + 0) * var7), (double)((par4 + par6) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par5 / 2.0f), (double)(par6 / 2.0f), (double)this.field_73735_i).func_187315_a((double)((par3 + par5) * var7), (double)((par4 + par6) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(par5 / 2.0f), (double)(-par6 / 2.0f), (double)this.field_73735_i).func_187315_a((double)((par3 + par5) * var7), (double)((par4 + 0) * var8)).func_181675_d();
        var9.func_178180_c().func_181662_b((double)(-par5 / 2.0f), (double)(-par6 / 2.0f), (double)this.field_73735_i).func_187315_a((double)((par3 + 0) * var7), (double)((par4 + 0) * var8)).func_181675_d();
        var9.func_78381_a();
        GL11.glPopMatrix();
    }
    
    private void parsePages() {
        this.checkRequisites();
        this.pages.clear();
        if (this.research.getStages() == null) {
            return;
        }
        boolean complete = false;
        this.currentStage = ThaumcraftCapabilities.getKnowledge((EntityPlayer)this.field_146297_k.field_71439_g).getResearchStage(this.research.getKey()) - 1;
        while (this.currentStage >= this.research.getStages().length) {
            --this.currentStage;
            complete = true;
        }
        if (this.currentStage < 0) {
            this.currentStage = 0;
        }
        final ResearchStage stage = this.research.getStages()[this.currentStage];
        ResearchAddendum[] addenda = null;
        if (this.research.getAddenda() != null && complete) {
            addenda = this.research.getAddenda();
        }
        this.generateRecipesLists(stage, addenda);
        String rawText = stage.getTextLocalized();
        if (addenda != null) {
            int ac = 0;
            for (final ResearchAddendum addendum : addenda) {
                if (ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, addendum.getResearch())) {
                    ++ac;
                    final ITextComponent text = (ITextComponent)new TextComponentTranslation("tc.addendumtext", new Object[] { ac });
                    rawText = rawText + "<PAGE>" + text.func_150254_d() + "<BR>" + addendum.getTextLocalized();
                }
            }
        }
        rawText = rawText.replaceAll("<BR>", "~B\n\n");
        rawText = rawText.replaceAll("<BR/>", "~B\n\n");
        rawText = rawText.replaceAll("<LINE>", "~L");
        rawText = rawText.replaceAll("<LINE/>", "~L");
        rawText = rawText.replaceAll("<DIV>", "~D");
        rawText = rawText.replaceAll("<DIV/>", "~D");
        rawText = rawText.replaceAll("<PAGE>", "~P");
        rawText = rawText.replaceAll("<PAGE/>", "~P");
        final ArrayList<PageImage> images = new ArrayList<PageImage>();
        final String[] split;
        final String[] imgSplit = split = rawText.split("<IMG>");
        for (final String s : split) {
            final int i = s.indexOf("</IMG>");
            if (i >= 0) {
                final String clean = s.substring(0, i);
                final PageImage pi = PageImage.parse(clean);
                if (pi == null) {
                    rawText = rawText.replaceFirst(clean, "\n");
                }
                else {
                    images.add(pi);
                    rawText = rawText.replaceFirst(clean, "~I");
                }
            }
        }
        rawText = rawText.replaceAll("<IMG>", "");
        rawText = rawText.replaceAll("</IMG>", "");
        final List<String> firstPassText = new ArrayList<String>();
        final String[] temp = rawText.split("~P");
        for (int a = 0; a < temp.length; ++a) {
            final String t = temp[a];
            final String[] temp2 = t.split("~D");
            for (int x = 0; x < temp2.length; ++x) {
                final String t2 = temp2[x];
                final String[] temp3 = t2.split("~L");
                for (int b = 0; b < temp3.length; ++b) {
                    final String t3 = temp3[b];
                    final String[] temp4 = t3.split("~I");
                    for (int c = 0; c < temp4.length; ++c) {
                        final String t4 = temp4[c];
                        firstPassText.add(t4);
                        if (c != temp4.length - 1) {
                            firstPassText.add("~I");
                        }
                    }
                    if (b != temp3.length - 1) {
                        firstPassText.add("~L");
                    }
                }
                if (x != temp2.length - 1) {
                    firstPassText.add("~D");
                }
            }
            if (a != temp.length - 1) {
                firstPassText.add("~P");
            }
        }
        final List<String> parsedText = new ArrayList<String>();
        for (final String s2 : firstPassText) {
            final List<String> pt1 = (List<String>)this.field_146297_k.field_71466_p.func_78271_c(s2, 140);
            for (final String ln : pt1) {
                parsedText.add(ln);
            }
        }
        final int lineHeight = this.field_146297_k.field_71466_p.field_78288_b;
        int heightRemaining = 182;
        int dividerSpace = 0;
        if (this.research.getKey().equals("KNOWLEDGETYPES")) {
            heightRemaining -= 2;
            int tc = 0;
            int amt = 0;
            for (final IPlayerKnowledge.EnumKnowledgeType type : IPlayerKnowledge.EnumKnowledgeType.values()) {
                for (final ResearchCategory category : ResearchCategories.researchCategories.values()) {
                    if (!type.hasFields() && category != null) {
                        continue;
                    }
                    amt = this.playerKnowledge.getKnowledgeRaw(type, category);
                    if (amt > 0) {
                        ++tc;
                        break;
                    }
                }
            }
            heightRemaining -= 20 * tc;
            dividerSpace = 12;
        }
        if (!this.isComplete) {
            if (stage.getCraft() != null) {
                heightRemaining -= 18;
                dividerSpace = 15;
            }
            if (stage.getObtain() != null) {
                heightRemaining -= 18;
                dividerSpace = 15;
            }
            if (stage.getKnow() != null) {
                heightRemaining -= 18;
                dividerSpace = 15;
            }
            if (stage.getResearch() != null) {
                heightRemaining -= 18;
                dividerSpace = 15;
            }
        }
        heightRemaining -= dividerSpace;
        Page page1 = new Page();
        final ArrayList<PageImage> tempImages = new ArrayList<PageImage>();
        for (String line : parsedText) {
            if (line.contains("~I")) {
                if (!images.isEmpty()) {
                    tempImages.add(images.remove(0));
                }
                line = "";
            }
            if (line.contains("~L")) {
                tempImages.add(GuiResearchPage.PILINE);
                line = "";
            }
            if (line.contains("~D")) {
                tempImages.add(GuiResearchPage.PIDIV);
                line = "";
            }
            if (line.contains("~P")) {
                heightRemaining = 210;
                this.pages.add(page1.copy());
                page1 = new Page();
                line = "";
            }
            if (!line.isEmpty()) {
                line = line.trim();
                page1.contents.add(line);
                heightRemaining -= lineHeight;
                if (line.endsWith("~B")) {
                    heightRemaining -= (int)(lineHeight * 0.66);
                }
            }
            while (!tempImages.isEmpty() && heightRemaining >= tempImages.get(0).ah + 2) {
                heightRemaining -= tempImages.get(0).ah + 2;
                page1.contents.add(tempImages.remove(0));
            }
            if (heightRemaining < lineHeight && !page1.contents.isEmpty()) {
                heightRemaining = 210;
                this.pages.add(page1.copy());
                page1 = new Page();
            }
        }
        if (!page1.contents.isEmpty()) {
            this.pages.add(page1.copy());
        }
        page1 = new Page();
        heightRemaining = 210;
        while (!tempImages.isEmpty()) {
            if (heightRemaining < tempImages.get(0).ah + 2) {
                heightRemaining = 210;
                this.pages.add(page1.copy());
                page1 = new Page();
            }
            else {
                heightRemaining -= tempImages.get(0).ah + 2;
                page1.contents.add(tempImages.remove(0));
            }
        }
        if (!page1.contents.isEmpty()) {
            this.pages.add(page1.copy());
        }
        this.rhash = this.research.getKey().hashCode() + this.currentStage * 50;
        this.maxPages = this.pages.size();
    }
    
    private void checkRequisites() {
        if (this.research.getStages() != null) {
            this.isComplete = this.playerKnowledge.isResearchComplete(this.research.getKey());
            while (this.currentStage >= this.research.getStages().length) {
                --this.currentStage;
            }
            if (this.currentStage < 0) {
                return;
            }
            this.hasAllRequisites = true;
            this.hasItem = null;
            this.hasCraft = null;
            this.hasResearch = null;
            this.hasKnow = null;
            final ResearchStage stage = this.research.getStages()[this.currentStage];
            final ItemStack[] o = stage.getObtain();
            if (o != null) {
                this.hasItem = new boolean[o.length];
                for (int a = 0; a < o.length; ++a) {
                    if (!(this.hasItem[a] = InventoryUtils.isPlayerCarryingAmount((EntityPlayer)this.field_146297_k.field_71439_g, o[a], false))) {
                        this.hasAllRequisites = false;
                    }
                }
            }
            final ItemStack[] c = stage.getCraft();
            if (c != null) {
                this.hasCraft = new boolean[c.length];
                for (int a2 = 0; a2 < c.length; ++a2) {
                    if (!this.playerKnowledge.isResearchKnown("[#]" + stage.getCraftReference()[a2])) {
                        this.hasAllRequisites = false;
                        this.hasCraft[a2] = false;
                    }
                    else {
                        this.hasCraft[a2] = true;
                    }
                }
            }
            final String[] r = stage.getResearch();
            if (r != null) {
                this.hasResearch = new boolean[r.length];
                for (int a3 = 0; a3 < r.length; ++a3) {
                    if (!ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, r[a3])) {
                        this.hasAllRequisites = false;
                        this.hasResearch[a3] = false;
                    }
                    else {
                        this.hasResearch[a3] = true;
                    }
                }
            }
            final ResearchStage.Knowledge[] k = stage.getKnow();
            if (k != null) {
                this.hasKnow = new boolean[k.length];
                for (int a4 = 0; a4 < k.length; ++a4) {
                    final int pk = this.playerKnowledge.getKnowledge(k[a4].type, k[a4].category);
                    if (pk < k[a4].amount) {
                        this.hasAllRequisites = false;
                        this.hasKnow[a4] = false;
                    }
                    else {
                        this.hasKnow[a4] = true;
                    }
                }
            }
        }
    }
    
    private int findRecipePage(final ResourceLocation rk, final ItemStack stack, final int start) {
        Object recipe = CommonInternals.getCatalogRecipe(rk);
        if (recipe == null) {
            recipe = CommonInternals.getCatalogRecipeFake(rk);
        }
        if (recipe == null) {
            recipe = CraftingManager.func_193373_a(rk);
        }
        if (recipe == null) {
            recipe = ConfigRecipes.recipeGroups.get(rk.toString());
        }
        if (recipe == null) {
            return -1;
        }
        if (recipe instanceof ArrayList) {
            int g = 0;
            for (final ResourceLocation rl : (ArrayList)recipe) {
                final int q = this.findRecipePage(rl, stack, g);
                if (q >= 0) {
                    return q;
                }
                ++g;
            }
        }
        if (recipe instanceof CrucibleRecipe && ((CrucibleRecipe)recipe).getRecipeOutput().func_77969_a(stack)) {
            if (!ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, ((CrucibleRecipe)recipe).getResearch())) {
                return -99;
            }
            return start;
        }
        else if (recipe instanceof InfusionRecipe && ((InfusionRecipe)recipe).getRecipeOutput() instanceof ItemStack && ((ItemStack)((InfusionRecipe)recipe).getRecipeOutput()).func_77969_a(stack)) {
            if (!ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, ((InfusionRecipe)recipe).getResearch())) {
                return -99;
            }
            return start;
        }
        else if (recipe instanceof IRecipe && ((IRecipe)recipe).func_77571_b().func_77969_a(stack)) {
            if (recipe instanceof IArcaneRecipe && !ThaumcraftCapabilities.knowsResearchStrict((EntityPlayer)this.field_146297_k.field_71439_g, ((IArcaneRecipe)recipe).getResearch())) {
                return -99;
            }
            return start;
        }
        else {
            if (recipe instanceof RecipeMisc && ((RecipeMisc)recipe).getOutput().func_77969_a(stack)) {
                return start;
            }
            return -1;
        }
    }
    
    private String getCraftingRecipeKey(final EntityPlayer player, final ItemStack stack) {
        final int key = stack.serializeNBT().toString().hashCode();
        if (this.keyCache.containsKey(key)) {
            return this.keyCache.get(key);
        }
        for (final ResearchCategory rcl : ResearchCategories.researchCategories.values()) {
            for (final ResearchEntry ri : rcl.research.values()) {
                if (ri.getStages() == null) {
                    continue;
                }
                for (int a = 0; a < ri.getStages().length; ++a) {
                    final ResearchStage stage = ri.getStages()[a];
                    if (stage.getRecipes() != null) {
                        for (final ResourceLocation rec : stage.getRecipes()) {
                            final int result = this.findRecipePage(rec, stack, 0);
                            if (result != -1) {
                                String s = rec.toString();
                                if (result == -99) {
                                    s = new ResourceLocation("UNKNOWN").toString();
                                }
                                else {
                                    s = s + ";" + result;
                                }
                                this.keyCache.put(key, s);
                                return s;
                            }
                        }
                    }
                }
            }
        }
        this.keyCache.put(key, null);
        return null;
    }
    
    static {
        GuiResearchPage.history = new LinkedList<ResourceLocation>();
        GuiResearchPage.aspectsPage = 0;
        GuiResearchPage.cycleMultiblockLines = false;
        PILINE = PageImage.parse("thaumcraft:textures/gui/gui_researchbook.png:24:184:95:6:1");
        PIDIV = PageImage.parse("thaumcraft:textures/gui/gui_researchbook.png:28:192:140:6:1");
    }
    
    private class Page
    {
        ArrayList contents;
        
        private Page() {
            this.contents = new ArrayList();
        }
        
        public Page copy() {
            final Page p = new Page();
            p.contents.addAll(this.contents);
            return p;
        }
    }
    
    private static class PageImage
    {
        int x;
        int y;
        int w;
        int h;
        int aw;
        int ah;
        float scale;
        ResourceLocation loc;
        
        public static PageImage parse(final String text) {
            final String[] s = text.split(":");
            if (s.length != 7) {
                return null;
            }
            try {
                final PageImage pi = new PageImage();
                pi.loc = new ResourceLocation(s[0], s[1]);
                pi.x = Integer.parseInt(s[2]);
                pi.y = Integer.parseInt(s[3]);
                pi.w = Integer.parseInt(s[4]);
                pi.h = Integer.parseInt(s[5]);
                pi.scale = Float.parseFloat(s[6]);
                pi.aw = (int)(pi.w * pi.scale);
                pi.ah = (int)(pi.h * pi.scale);
                if (pi.ah > 208 || pi.aw > 140) {
                    return null;
                }
                return pi;
            }
            catch (Exception ex) {
                return null;
            }
        }
    }
    
    public static class BlueprintBlockAccess implements IBlockAccess
    {
        private final Part[][][] data;
        private IBlockState[][][] structure;
        public int sliceLine;
        
        public BlueprintBlockAccess(final Part[][][] data, final boolean target) {
            this.sliceLine = 0;
            this.data = new Part[data.length][data[0].length][data[0][0].length];
            for (int y = 0; y < data.length; ++y) {
                for (int x = 0; x < data[0].length; ++x) {
                    for (int z = 0; z < data[0][0].length; ++z) {
                        this.data[y][x][z] = data[y][x][z];
                    }
                }
            }
            this.structure = new IBlockState[data.length][data[0].length][data[0][0].length];
            if (target) {
                for (int y = 0; y < this.data.length; ++y) {
                    final Matrix matrix = new Matrix(this.data[y]);
                    matrix.Rotate90DegRight(3);
                    this.data[y] = matrix.getMatrix();
                }
            }
            for (int y = 0; y < data.length; ++y) {
                for (int x = 0; x < data[0].length; ++x) {
                    for (int z = 0; z < data[0][0].length; ++z) {
                        this.structure[data.length - y - 1][x][z] = (target ? this.convertTarget(x, y, z) : this.convert(x, y, z));
                    }
                }
            }
        }
        
        private IBlockState convert(final int x, final int y, final int z) {
            if (this.data[y][x][z] == null || this.data[y][x][z].getSource() == null) {
                return Blocks.field_150350_a.func_176223_P();
            }
            if (this.data[y][x][z].getSource() instanceof ItemStack && Block.func_149634_a(((ItemStack)this.data[y][x][z].getSource()).func_77973_b()) != null) {
                return Block.func_149634_a(((ItemStack)this.data[y][x][z].getSource()).func_77973_b()).func_176203_a(((ItemStack)this.data[y][x][z].getSource()).func_77952_i());
            }
            if (this.data[y][x][z].getSource() instanceof Block) {
                return ((Block)this.data[y][x][z].getSource()).func_176223_P();
            }
            if (this.data[y][x][z].getSource() instanceof IBlockState) {
                return (IBlockState)this.data[y][x][z].getSource();
            }
            if (this.data[y][x][z].getSource() instanceof Material) {
                if (this.data[y][x][z].getSource() == Material.field_151587_i) {
                    return Blocks.field_150353_l.func_176223_P();
                }
                if (this.data[y][x][z].getSource() == Material.field_151586_h) {
                    return Blocks.field_150355_j.func_176223_P();
                }
            }
            return Blocks.field_150350_a.func_176223_P();
        }
        
        private IBlockState convertTarget(final int x, final int y, final int z) {
            if (this.data[y][x][z] == null) {
                return Blocks.field_150350_a.func_176223_P();
            }
            if (this.data[y][x][z].getTarget() == null) {
                return this.convert(x, y, z);
            }
            if (this.data[y][x][z].getTarget() instanceof ItemStack && Block.func_149634_a(((ItemStack)this.data[y][x][z].getTarget()).func_77973_b()) != null) {
                return Block.func_149634_a(((ItemStack)this.data[y][x][z].getTarget()).func_77973_b()).func_176203_a(((ItemStack)this.data[y][x][z].getTarget()).func_77952_i());
            }
            if (this.data[y][x][z].getTarget() instanceof Block) {
                return ((Block)this.data[y][x][z].getTarget()).func_176223_P();
            }
            if (this.data[y][x][z].getTarget() instanceof IBlockState) {
                return (IBlockState)this.data[y][x][z].getTarget();
            }
            if (this.data[y][x][z].getTarget() instanceof Material) {
                if (this.data[y][x][z].getTarget() == Material.field_151587_i) {
                    return Blocks.field_150353_l.func_176223_P();
                }
                if (this.data[y][x][z].getTarget() == Material.field_151586_h) {
                    return Blocks.field_150355_j.func_176223_P();
                }
            }
            return Blocks.field_150350_a.func_176223_P();
        }
        
        @Nullable
        public TileEntity func_175625_s(final BlockPos pos) {
            return null;
        }
        
        public int func_175626_b(final BlockPos pos, final int lightValue) {
            return 15728880;
        }
        
        public IBlockState func_180495_p(final BlockPos pos) {
            final int x = pos.func_177958_n();
            final int y = pos.func_177956_o();
            final int z = pos.func_177952_p();
            if (this.sliceLine > this.structure.length) {
                this.sliceLine = 0;
            }
            if (y >= 0 && y < this.structure.length - this.sliceLine && x >= 0 && x < this.structure[y].length && z >= 0 && z < this.structure[y][x].length) {
                return this.structure[y][x][z];
            }
            return Blocks.field_150350_a.func_176223_P();
        }
        
        public boolean func_175623_d(final BlockPos pos) {
            return this.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a;
        }
        
        public int func_175627_a(final BlockPos pos, final EnumFacing direction) {
            return 0;
        }
        
        public WorldType func_175624_G() {
            return null;
        }
        
        public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
            return false;
        }
        
        public Biome func_180494_b(final BlockPos pos) {
            return null;
        }
    }
}
