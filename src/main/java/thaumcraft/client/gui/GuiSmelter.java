package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class GuiSmelter extends GuiContainer
{
    private TileSmelter furnaceInventory;
    ResourceLocation tex;
    
    public GuiSmelter(final InventoryPlayer par1InventoryPlayer, final TileSmelter par2TileEntityFurnace) {
        super((Container)new ContainerSmelter(par1InventoryPlayer, par2TileEntityFurnace));
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_smelter.png");
        this.furnaceInventory = par2TileEntityFurnace;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int par1, final int par2) {
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.furnaceInventory.getBurnTimeRemainingScaled(20) > 0) {
            final int i1 = this.furnaceInventory.getBurnTimeRemainingScaled(20);
            this.func_73729_b(k + 80, l + 26 + 20 - i1, 176, 20 - i1, 16, i1);
        }
        int i1 = this.furnaceInventory.getCookProgressScaled(46);
        this.func_73729_b(k + 106, l + 13 + 46 - i1, 216, 46 - i1, 9, i1);
        i1 = this.furnaceInventory.getVisScaled(48);
        this.func_73729_b(k + 61, l + 12 + 48 - i1, 200, 48 - i1, 8, i1);
        this.func_73729_b(k + 60, l + 8, 232, 0, 10, 55);
    }
}
