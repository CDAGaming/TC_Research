package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class GuiTurretBasic extends GuiContainer
{
    EntityTurretCrossbow turret;
    ResourceLocation tex;
    
    public GuiTurretBasic(final InventoryPlayer par1InventoryPlayer, final World world, final EntityTurretCrossbow t) {
        super((Container)new ContainerTurretBasic(par1InventoryPlayer, world, t));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_turret_basic.png");
        this.field_146999_f = 175;
        this.field_147000_g = 232;
        this.turret = t;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int par1, final int par2) {
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        final int h = (int)(39.0f * (this.turret.func_110143_aJ() / this.turret.func_110138_aP()));
        this.func_73729_b(k + 68, l + 59, 192, 48, h, 6);
    }
}
