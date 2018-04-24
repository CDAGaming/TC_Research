package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.parts.*;
import thaumcraft.common.entities.construct.golem.gui.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.common.entities.construct.golem.*;
import thaumcraft.api.capabilities.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.gui.plugins.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.lib.utils.*;
import thaumcraft.api.aspects.*;
import net.minecraft.client.gui.*;
import java.util.*;
import thaumcraft.common.lib.network.*;
import thaumcraft.common.lib.network.misc.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import java.io.*;

@SideOnly(Side.CLIENT)
public class GuiGolemBuilder extends GuiContainer
{
    private TileGolemBuilder builder;
    private EntityPlayer player;
    ResourceLocation tex;
    ArrayList<GolemHead> valHeads;
    ArrayList<GolemMaterial> valMats;
    ArrayList<GolemArm> valArms;
    ArrayList<GolemLeg> valLegs;
    ArrayList<GolemAddon> valAddons;
    static int headIndex;
    static int matIndex;
    static int armIndex;
    static int legIndex;
    static int addonIndex;
    IGolemProperties props;
    float hearts;
    float armor;
    float damage;
    GuiGolemCraftButton craftButton;
    ResourceLocation matIcon;
    int cost;
    boolean allfound;
    ItemStack[] components;
    boolean[] owns;
    boolean disableAll;
    
    public GuiGolemBuilder(final InventoryPlayer par1InventoryPlayer, final TileGolemBuilder table) {
        super((Container)new ContainerGolemBuilder(par1InventoryPlayer, table));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_golembuilder.png");
        this.valHeads = new ArrayList<GolemHead>();
        this.valMats = new ArrayList<GolemMaterial>();
        this.valArms = new ArrayList<GolemArm>();
        this.valLegs = new ArrayList<GolemLeg>();
        this.valAddons = new ArrayList<GolemAddon>();
        this.props = GolemProperties.fromLong(0L);
        this.hearts = 0.0f;
        this.armor = 0.0f;
        this.damage = 0.0f;
        this.craftButton = null;
        this.matIcon = new ResourceLocation("thaumcraft", "textures/items/golem.png");
        this.cost = 0;
        this.allfound = false;
        this.components = null;
        this.owns = null;
        this.disableAll = false;
        this.player = par1InventoryPlayer.field_70458_d;
        this.builder = table;
        this.field_146999_f = 208;
        this.field_147000_g = 224;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.valHeads.clear();
        for (final GolemHead head : GolemHead.getHeads()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(this.player, head.research)) {
                this.valHeads.add(head);
            }
        }
        this.valMats.clear();
        for (final GolemMaterial mat : GolemMaterial.getMaterials()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(this.player, mat.research)) {
                this.valMats.add(mat);
            }
        }
        this.valArms.clear();
        for (final GolemArm arm : GolemArm.getArms()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(this.player, arm.research)) {
                this.valArms.add(arm);
            }
        }
        this.valLegs.clear();
        for (final GolemLeg leg : GolemLeg.getLegs()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(this.player, leg.research)) {
                this.valLegs.add(leg);
            }
        }
        this.valAddons.clear();
        for (final GolemAddon addon : GolemAddon.getAddons()) {
            if (ThaumcraftCapabilities.knowsResearchStrict(this.player, addon.research)) {
                this.valAddons.add(addon);
            }
        }
        if (GuiGolemBuilder.headIndex >= this.valHeads.size()) {
            GuiGolemBuilder.headIndex = 0;
        }
        if (GuiGolemBuilder.matIndex >= this.valMats.size()) {
            GuiGolemBuilder.matIndex = 0;
        }
        if (GuiGolemBuilder.armIndex >= this.valArms.size()) {
            GuiGolemBuilder.armIndex = 0;
        }
        if (GuiGolemBuilder.legIndex >= this.valLegs.size()) {
            GuiGolemBuilder.legIndex = 0;
        }
        if (GuiGolemBuilder.addonIndex >= this.valAddons.size()) {
            GuiGolemBuilder.addonIndex = 0;
        }
        this.gatherInfo();
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.components != null && this.components.length > 0) {
            int i = 1;
            int q = 0;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            for (int a = 0; a < this.components.length; ++a) {
                if (!this.owns[a]) {
                    this.func_73729_b(this.field_147003_i + 144 + q * 16, this.field_147009_r + 16 + 16 * i, 240, 0, 16, 16);
                }
                if (++i > 3) {
                    i = 0;
                    ++q;
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.builder.cost > 0) {
            this.func_73729_b(this.field_147003_i + 145, this.field_147009_r + 89, 209, 89, (int)(46.0f * (1.0f - this.builder.cost / this.builder.maxCost)), 6);
            if (!this.disableAll) {
                this.disableAll = true;
                this.redoComps();
            }
        }
        else if (this.disableAll) {
            this.disableAll = false;
            this.redoComps();
        }
        this.func_73732_a(this.field_146289_q, "" + this.hearts, this.field_147003_i + 48, this.field_147009_r + 108, 16777215);
        this.func_73732_a(this.field_146289_q, "" + this.armor, this.field_147003_i + 72, this.field_147009_r + 108, 16777215);
        this.func_73732_a(this.field_146289_q, "" + this.damage, this.field_147003_i + 97, this.field_147009_r + 108, 16777215);
    }
    
    private void gatherInfo() {
        this.field_146292_n.clear();
        this.craftButton = new GuiGolemCraftButton(99, this.field_147003_i + 120, this.field_147009_r + 104);
        this.field_146292_n.add(this.craftButton);
        if (this.valHeads.size() > 1) {
            this.field_146292_n.add(new GuiScrollButton(0, this.field_147003_i + 112 - 5 - 6, this.field_147009_r - 5 + 16 + 8, 10, 10, true));
            this.field_146292_n.add(new GuiScrollButton(1, this.field_147003_i + 112 - 5 + 22, this.field_147009_r - 5 + 16 + 8, 10, 10, false));
        }
        if (this.valMats.size() > 1) {
            this.field_146292_n.add(new GuiScrollButton(2, this.field_147003_i + 16 - 5 - 6, this.field_147009_r - 5 + 16 + 8, 10, 10, true));
            this.field_146292_n.add(new GuiScrollButton(3, this.field_147003_i + 16 - 5 + 22, this.field_147009_r - 5 + 16 + 8, 10, 10, false));
        }
        if (this.valArms.size() > 1) {
            this.field_146292_n.add(new GuiScrollButton(4, this.field_147003_i + 112 - 5 - 6, this.field_147009_r - 5 + 40 + 8, 10, 10, true));
            this.field_146292_n.add(new GuiScrollButton(5, this.field_147003_i + 112 - 5 + 22, this.field_147009_r - 5 + 40 + 8, 10, 10, false));
        }
        if (this.valLegs.size() > 1) {
            this.field_146292_n.add(new GuiScrollButton(6, this.field_147003_i + 112 - 5 - 6, this.field_147009_r - 5 + 64 + 8, 10, 10, true));
            this.field_146292_n.add(new GuiScrollButton(7, this.field_147003_i + 112 - 5 + 22, this.field_147009_r - 5 + 64 + 8, 10, 10, false));
        }
        if (this.valAddons.size() > 1) {
            this.field_146292_n.add(new GuiScrollButton(8, this.field_147003_i + 16 - 5 - 6, this.field_147009_r - 5 + 64 + 8, 10, 10, true));
            this.field_146292_n.add(new GuiScrollButton(9, this.field_147003_i + 16 - 5 + 22, this.field_147009_r - 5 + 64 + 8, 10, 10, false));
        }
        if (this.valHeads.size() > 0) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 100, this.field_147003_i + 120, this.field_147009_r + 24, 16, 16, this.valHeads.get(GuiGolemBuilder.headIndex).getLocalizedName(), this.valHeads.get(GuiGolemBuilder.headIndex).getLocalizedDescription(), this.valHeads.get(GuiGolemBuilder.headIndex).icon));
        }
        if (this.valMats.size() > 0) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 101, this.field_147003_i + 24, this.field_147009_r + 24, 16, 16, this.valMats.get(GuiGolemBuilder.matIndex).getLocalizedName(), this.valMats.get(GuiGolemBuilder.matIndex).getLocalizedDescription(), this.matIcon, this.valMats.get(GuiGolemBuilder.matIndex).itemColor));
        }
        if (this.valArms.size() > 0) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 102, this.field_147003_i + 120, this.field_147009_r + 48, 16, 16, this.valArms.get(GuiGolemBuilder.armIndex).getLocalizedName(), this.valArms.get(GuiGolemBuilder.armIndex).getLocalizedDescription(), this.valArms.get(GuiGolemBuilder.armIndex).icon));
        }
        if (this.valLegs.size() > 0) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 103, this.field_147003_i + 120, this.field_147009_r + 72, 16, 16, this.valLegs.get(GuiGolemBuilder.legIndex).getLocalizedName(), this.valLegs.get(GuiGolemBuilder.legIndex).getLocalizedDescription(), this.valLegs.get(GuiGolemBuilder.legIndex).icon));
        }
        if (this.valAddons.size() > 0 && !this.valAddons.get(GuiGolemBuilder.addonIndex).key.equalsIgnoreCase("none")) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 103, this.field_147003_i + 24, this.field_147009_r + 72, 16, 16, this.valAddons.get(GuiGolemBuilder.addonIndex).getLocalizedName(), this.valAddons.get(GuiGolemBuilder.addonIndex).getLocalizedDescription(), this.valAddons.get(GuiGolemBuilder.addonIndex).icon));
        }
        (this.props = GolemProperties.fromLong(0L)).setHead(this.valHeads.get(GuiGolemBuilder.headIndex));
        this.props.setMaterial(this.valMats.get(GuiGolemBuilder.matIndex));
        this.props.setArms(this.valArms.get(GuiGolemBuilder.armIndex));
        this.props.setLegs(this.valLegs.get(GuiGolemBuilder.legIndex));
        this.props.setAddon(this.valAddons.get(GuiGolemBuilder.addonIndex));
        this.redoComps();
        final EnumGolemTrait[] tags = this.props.getTraits().toArray(new EnumGolemTrait[0]);
        if (tags != null && tags.length > 0) {
            final int yy = (tags.length <= 4) ? ((tags.length - 1) % 4 * 8) : 24;
            final int xx = (tags.length - 1) / 4 % 4 * 8;
            int i = 0;
            int q = 0;
            int z = 0;
            for (final EnumGolemTrait tag : tags) {
                this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 30 + z, this.field_147003_i + 72 + q * 16 - xx, this.field_147009_r + 48 + 16 * i - yy, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
                if (++i > 3) {
                    i = 0;
                    ++q;
                }
                ++z;
            }
        }
        int hh = 10 + this.props.getMaterial().healthMod;
        if (this.props.hasTrait(EnumGolemTrait.FRAGILE)) {
            hh *= (int)0.75;
        }
        this.hearts = hh / 2.0f;
        int aa = this.props.getMaterial().armor;
        if (this.props.hasTrait(EnumGolemTrait.ARMORED)) {
            aa = (int)Math.max(aa * 1.5, aa + 1);
        }
        if (this.props.hasTrait(EnumGolemTrait.FRAGILE)) {
            aa *= (int)0.75;
        }
        this.armor = aa / 2.0f;
        double dd = this.props.hasTrait(EnumGolemTrait.FIGHTER) ? this.props.getMaterial().damage : 0.0;
        if (this.props.hasTrait(EnumGolemTrait.BRUTAL)) {
            dd = Math.max(dd * 1.5, dd + 1.0);
        }
        this.damage = (float)(dd / 2.0);
    }
    
    private void redoComps() {
        this.allfound = true;
        this.cost = this.props.getTraits().size() * 2;
        this.components = this.props.generateComponents();
        if (this.components.length >= 1) {
            this.owns = new boolean[this.components.length];
            for (int a = 0; a < this.components.length; ++a) {
                this.cost += this.components[a].func_190916_E();
                if (!(this.owns[a] = InventoryUtils.isPlayerCarryingAmount(this.player, this.components[a], true))) {
                    this.allfound = false;
                }
            }
        }
        if (this.components != null && this.components.length > 0) {
            this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 10, this.field_147003_i + 152, this.field_147009_r + 24, 16, 16, Aspect.MECHANISM.getName(), Aspect.MECHANISM.getLocalizedDescription(), Aspect.MECHANISM));
            int i = 1;
            int q = 0;
            int z = 0;
            for (final ItemStack stack : this.components) {
                this.field_146292_n.add(new GuiHoverButton((GuiScreen)this, 11 + z, this.field_147003_i + 152 + q * 16, this.field_147009_r + 24 + 16 * i, 16, 16, stack.func_82833_r(), null, stack));
                if (++i > 3) {
                    i = 0;
                    ++q;
                }
                ++z;
            }
        }
        if (this.field_146292_n != null && this.field_146292_n.size() > 0) {
            for (final Object b : this.field_146292_n) {
                if (b instanceof GuiButton) {
                    ((GuiButton)b).field_146124_l = !this.disableAll;
                    if (this.disableAll || b != this.craftButton) {
                        continue;
                    }
                    this.craftButton.field_146124_l = this.allfound;
                }
            }
        }
    }
    
    protected void func_146979_b(final int mouseX, final int mouseY) {
        if (this.components != null && this.components.length > 0) {
            this.func_73731_b(this.field_146289_q, "" + this.cost, 162 - this.field_146289_q.func_78256_a("" + this.cost), 24, 16777215);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_73729_b(12, 12, 228, 124, 24, 24);
        this.func_73729_b(12, 60, 228, 124, 24, 24);
        this.func_73729_b(108, 12, 228, 124, 24, 24);
        this.func_73729_b(108, 36, 228, 124, 24, 24);
        this.func_73729_b(108, 60, 228, 124, 24, 24);
        for (final GuiButton guibutton : this.field_146292_n) {
            if (guibutton.func_146115_a()) {
                guibutton.func_146111_b(mouseX - this.field_147003_i, mouseY - this.field_147009_r);
                break;
            }
        }
        if (ContainerGolemBuilder.redo) {
            this.redoComps();
            ContainerGolemBuilder.redo = false;
        }
        GL11.glDisable(3042);
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k == 0) {
            --GuiGolemBuilder.headIndex;
            if (GuiGolemBuilder.headIndex < 0) {
                GuiGolemBuilder.headIndex = this.valHeads.size() - 1;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 1) {
            ++GuiGolemBuilder.headIndex;
            if (GuiGolemBuilder.headIndex >= this.valHeads.size()) {
                GuiGolemBuilder.headIndex = 0;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 2) {
            --GuiGolemBuilder.matIndex;
            if (GuiGolemBuilder.matIndex < 0) {
                GuiGolemBuilder.matIndex = this.valMats.size() - 1;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 3) {
            ++GuiGolemBuilder.matIndex;
            if (GuiGolemBuilder.matIndex >= this.valMats.size()) {
                GuiGolemBuilder.matIndex = 0;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 4) {
            --GuiGolemBuilder.armIndex;
            if (GuiGolemBuilder.armIndex < 0) {
                GuiGolemBuilder.armIndex = this.valArms.size() - 1;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 5) {
            ++GuiGolemBuilder.armIndex;
            if (GuiGolemBuilder.armIndex >= this.valArms.size()) {
                GuiGolemBuilder.armIndex = 0;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 6) {
            --GuiGolemBuilder.legIndex;
            if (GuiGolemBuilder.legIndex < 0) {
                GuiGolemBuilder.legIndex = this.valLegs.size() - 1;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 7) {
            ++GuiGolemBuilder.legIndex;
            if (GuiGolemBuilder.legIndex >= this.valLegs.size()) {
                GuiGolemBuilder.legIndex = 0;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 8) {
            --GuiGolemBuilder.addonIndex;
            if (GuiGolemBuilder.addonIndex < 0) {
                GuiGolemBuilder.addonIndex = this.valAddons.size() - 1;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 9) {
            ++GuiGolemBuilder.addonIndex;
            if (GuiGolemBuilder.addonIndex >= this.valAddons.size()) {
                GuiGolemBuilder.addonIndex = 0;
            }
            this.gatherInfo();
        }
        else if (button.field_146127_k == 99 && this.allfound) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketStartGolemCraftToServer(this.player, this.builder.func_174877_v(), this.props.toLong()));
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 99);
            this.disableAll = true;
        }
    }
    
    static {
        GuiGolemBuilder.headIndex = 0;
        GuiGolemBuilder.matIndex = 0;
        GuiGolemBuilder.armIndex = 0;
        GuiGolemBuilder.legIndex = 0;
        GuiGolemBuilder.addonIndex = 0;
    }
}
