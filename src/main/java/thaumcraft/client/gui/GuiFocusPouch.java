package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;

@SideOnly(Side.CLIENT)
public class GuiFocusPouch extends GuiContainer
{
    private int blockSlot;
    ResourceLocation tex;
    
    public GuiFocusPouch(final InventoryPlayer par1InventoryPlayer, final World world, final int x, final int y, final int z) {
        super((Container)new ContainerFocusPouch(par1InventoryPlayer, world, x, y, z));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_focuspouch.png");
        this.blockSlot = par1InventoryPlayer.field_70461_c;
        this.field_146999_f = 175;
        this.field_147000_g = 232;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int par1, final int par2) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        final float t = this.field_73735_i;
        this.field_73735_i = 200.0f;
        GL11.glEnable(3042);
        this.func_73729_b(8 + this.blockSlot * 18, 209, 240, 0, 16, 16);
        GL11.glDisable(3042);
        this.field_73735_i = t;
    }
    
    protected boolean func_146983_a(final int par1) {
        return false;
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        if (((ItemStack)this.field_146297_k.field_71439_g.field_71071_by.field_70462_a.get(this.blockSlot)).func_190926_b()) {
            this.field_146297_k.field_71439_g.func_71053_j();
        }
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int var5 = (this.field_146294_l - this.field_146999_f) / 2;
        final int var6 = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(var5, var6, 0, 0, this.field_146999_f, this.field_147000_g);
        GL11.glDisable(3042);
    }
}
