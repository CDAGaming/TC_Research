package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class GuiHandMirror extends GuiContainer
{
    int ci;
    ResourceLocation tex;
    
    public GuiHandMirror(final InventoryPlayer par1InventoryPlayer, final World world, final int x, final int y, final int z) {
        super((Container)new ContainerHandMirror(par1InventoryPlayer, world, x, y, z));
        this.ci = 0;
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_handmirror.png");
        this.ci = par1InventoryPlayer.field_70461_c;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void drawGuiContainerForegroundLayer() {
    }
    
    protected boolean func_146983_a(final int par1) {
        return false;
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int var5 = (this.field_146294_l - this.field_146999_f) / 2;
        final int var6 = (this.field_146295_m - this.field_147000_g) / 2;
        this.func_73729_b(var5, var6, 0, 0, this.field_146999_f, this.field_147000_g);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.func_73729_b(var5 + 8 + this.ci * 18, var6 + 142, 240, 0, 16, 16);
    }
}
