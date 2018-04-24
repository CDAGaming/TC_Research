package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.container.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.util.text.*;
import java.io.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import thaumcraft.common.lib.*;
import thaumcraft.common.lib.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import thaumcraft.client.gui.plugins.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import thaumcraft.common.lib.network.misc.*;

@SideOnly(Side.CLIENT)
public class GuiLogistics extends GuiContainer
{
    int selectedSlot;
    ContainerLogistics con;
    World world;
    EntityPlayer player;
    BlockPos target;
    EnumFacing side;
    ResourceLocation tex;
    long lu;
    int lastStackSize;
    int stackSize;
    boolean stacksizeUpdated;
    ItemStack selectedStack;
    int lastScrollPos;
    GuiSliderTC scrollbar;
    GuiSliderTC countbar;
    GuiPlusMinusButton countbutton1;
    GuiPlusMinusButton countbutton2;
    GuiImageButton requestbutton;
    GuiTextField searchField;
    
    public GuiLogistics(final InventoryPlayer par1InventoryPlayer, final World world, final BlockPos pos, final EnumFacing side) {
        super((Container)new ContainerLogistics(par1InventoryPlayer, world));
        this.selectedSlot = -1;
        this.con = null;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_logistics.png");
        this.lu = 0L;
        this.lastStackSize = 1;
        this.stackSize = 1;
        this.stacksizeUpdated = false;
        this.selectedStack = null;
        this.lastScrollPos = 0;
        this.world = world;
        this.player = par1InventoryPlayer.field_70458_d;
        this.field_146999_f = 215;
        this.field_147000_g = 215;
        this.con = (ContainerLogistics)this.field_147002_h;
        this.target = pos;
        this.side = side;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int mouseX, final int mouseY) {
        final long ct = System.currentTimeMillis();
        if (ct > this.lu) {
            this.lu = ct + 1000L;
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 22);
        }
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glEnable(3042);
        if (this.scrollbar != null) {
            if (this.scrollbar.getMax() != this.con.end) {
                this.scrollbar.setMax(this.con.end);
            }
            final int sv = Math.round(this.scrollbar.getSliderValue());
            if (sv != this.lastScrollPos) {
                this.lastScrollPos = sv;
                this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 100 + this.lastScrollPos);
            }
            else if (this.con.updated && sv != this.con.start) {
                this.scrollbar.setSliderValue(this.con.start, false);
                this.con.updated = false;
            }
        }
        this.countbar.field_146125_m = (this.selectedSlot >= 0);
        this.countbutton1.field_146125_m = (this.selectedSlot >= 0);
        this.countbutton2.field_146125_m = (this.selectedSlot >= 0);
        this.requestbutton.field_146125_m = (this.selectedSlot >= 0);
        if (this.selectedSlot >= 0 && this.selectedStack != null && !this.selectedStack.func_190926_b() && (!this.selectedStack.func_77969_a(this.field_147002_h.func_75139_a(this.selectedSlot).func_75211_c()) || !ItemStack.func_77970_a(this.selectedStack, this.field_147002_h.func_75139_a(this.selectedSlot).func_75211_c()))) {
            this.selectedSlot = -1;
            for (final Slot slot : this.field_147002_h.field_75151_b) {
                if (this.selectedStack.func_77969_a(slot.func_75211_c()) && ItemStack.func_77970_a(this.selectedStack, slot.func_75211_c())) {
                    this.selectedSlot = slot.field_75222_d;
                    break;
                }
            }
        }
        if (this.selectedSlot >= 0 && !this.field_147002_h.func_75139_a(this.selectedSlot).func_75216_d()) {
            this.selectedSlot = -1;
        }
        if (this.selectedSlot >= 0 && this.field_147002_h.func_75139_a(this.selectedSlot) != null && this.field_147002_h.func_75139_a(this.selectedSlot).func_75216_d()) {
            final ItemStack stack = this.field_147002_h.func_75139_a(this.selectedSlot).func_75211_c();
            if (this.countbar.getMax() != stack.func_190916_E()) {
                this.countbar.setMax(stack.func_190916_E());
            }
            final int sv2 = Math.round(this.countbar.getSliderValue());
            if (sv2 != this.lastStackSize) {
                this.lastStackSize = sv2;
                this.stackSize = this.lastStackSize;
            }
            else if (this.stacksizeUpdated && sv2 != this.stackSize) {
                this.countbar.setSliderValue(this.stackSize, false);
                this.stacksizeUpdated = false;
            }
            final String s = "" + this.stackSize;
            this.field_146289_q.func_78276_b(s, 83 - this.field_146289_q.func_78256_a(s) / 2, 196, 3355443);
        }
        GL11.glDisable(3042);
    }
    
    protected boolean func_146983_a(final int par1) {
        return false;
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.selectedSlot >= 0) {
            this.func_73729_b(this.field_147003_i + 17 + this.selectedSlot % 9 * 19, this.field_147009_r + 17 + this.selectedSlot / 9 * 19, 222, 46, 20, 20);
        }
        GL11.glDisable(3042);
        this.searchField.func_146194_f();
        if (!this.searchField.func_146206_l() && this.searchField.func_146179_b().isEmpty()) {
            this.field_146289_q.func_78276_b(new TextComponentTranslation("tc.logistics.search", new Object[0]).func_150254_d(), this.field_147003_i + 146, this.field_147009_r + 197, 2236962);
        }
    }
    
    public void func_146274_d() throws IOException {
        super.func_146274_d();
        final int k = Mouse.getDWheel();
        if (k < 0) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 0);
        }
        else if (k > 0) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 1);
        }
    }
    
    protected void func_184098_a(final Slot slotIn, final int slotId, final int mouseButton, final ClickType type) {
        super.func_184098_a(slotIn, slotId, mouseButton, type);
        if (slotIn != null && slotId < 81 && slotIn.func_75216_d()) {
            Minecraft.func_71410_x().field_71439_g.func_184185_a(SoundsTC.clack, 0.66f, 1.0f);
            this.selectedSlot = slotId;
            this.selectedStack = slotIn.func_75211_c();
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.searchField.func_146192_a(mouseX, mouseY, mouseButton);
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k == 1) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 0);
        }
        if (button.field_146127_k == 0) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 1);
        }
        if (this.selectedSlot >= 0 && this.field_147002_h.func_75139_a(this.selectedSlot) != null && this.field_147002_h.func_75139_a(this.selectedSlot).func_75216_d()) {
            final ItemStack stack = this.field_147002_h.func_75139_a(this.selectedSlot).func_75211_c();
            if (button.field_146127_k == 2) {
                --this.stackSize;
                if (this.stackSize < 1) {
                    this.stackSize = 1;
                }
                this.stacksizeUpdated = true;
            }
            if (button.field_146127_k == 3) {
                ++this.stackSize;
                if (this.stackSize > stack.func_190916_E()) {
                    this.stackSize = stack.func_190916_E();
                }
                this.stacksizeUpdated = true;
            }
            if (button.field_146127_k == 7) {
                final ItemStack s2 = stack.func_77946_l();
                s2.func_190920_e(1);
                PacketHandler.INSTANCE.sendToServer((IMessage)new PacketLogisticsRequestToServer(this.target, this.side, s2, this.stackSize));
            }
        }
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.clear();
        this.field_146292_n.add(new GuiScrollButton(0, this.field_147003_i + 195, this.field_147009_r + 16, 10, 10, true, true));
        this.field_146292_n.add(new GuiScrollButton(1, this.field_147003_i + 195, this.field_147009_r + 180, 10, 10, false, true));
        this.countbutton1 = new GuiPlusMinusButton(2, this.field_147003_i + 13, this.field_147009_r + 195, 10, 10, true);
        this.countbutton2 = new GuiPlusMinusButton(3, this.field_147003_i + 57, this.field_147009_r + 195, 10, 10, false);
        this.field_146292_n.add(this.countbutton1);
        this.field_146292_n.add(this.countbutton2);
        this.scrollbar = new GuiSliderTC(5, this.field_147003_i + 196, this.field_147009_r + 28, 8, 149, "logistics.scrollbar", 0.0f, this.con.end, this.con.start, true);
        this.countbar = new GuiSliderTC(6, this.field_147003_i + 24, this.field_147009_r + 196, 32, 8, "logistics.countbar", 1.0f, 64.0f, 1.0f, false);
        this.field_146292_n.add(this.scrollbar);
        this.field_146292_n.add(this.countbar);
        this.requestbutton = new GuiImageButton((GuiScreen)this, 7, this.field_147003_i + 116, this.field_147009_r + 200, 40, 13, "tc.logistics.request", "logistics.request", new ResourceLocation("thaumcraft", "textures/gui/gui_base.png"), 37, 82, 40, 13);
        this.field_146292_n.add(this.requestbutton);
        (this.searchField = new GuiTextField(8, this.field_146289_q, this.field_147003_i + 143, this.field_147009_r + 196, 55, this.field_146289_q.field_78288_b)).func_146203_f(10);
        this.searchField.func_146185_a(true);
        this.searchField.func_146189_e(false);
        this.searchField.func_146193_g(16777215);
        this.searchField.func_146189_e(true);
        this.searchField.func_146205_d(true);
        this.searchField.func_146180_a("");
        Keyboard.enableRepeatEvents(true);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (this.searchField.func_146201_a(typedChar, keyCode)) {
            PacketHandler.INSTANCE.sendToServer((IMessage)new PacketMiscStringToServer(0, this.searchField.func_146179_b()));
        }
        else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
}
