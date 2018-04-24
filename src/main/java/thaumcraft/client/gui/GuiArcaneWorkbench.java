package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import thaumcraft.common.container.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.lib.crafting.*;
import net.minecraft.inventory.*;
import thaumcraft.common.items.casters.*;
import thaumcraft.common.blocks.world.ore.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import thaumcraft.api.crafting.*;
import thaumcraft.api.aspects.*;

@SideOnly(Side.CLIENT)
public class GuiArcaneWorkbench extends GuiContainer
{
    private TileArcaneWorkbench tileEntity;
    private InventoryPlayer ip;
    private int[][] aspectLocs;
    ResourceLocation tex;
    
    public GuiArcaneWorkbench(final InventoryPlayer par1InventoryPlayer, final TileArcaneWorkbench e) {
        super((Container)new ContainerArcaneWorkbench(par1InventoryPlayer, e));
        this.aspectLocs = new int[][] { { 72, 21 }, { 24, 43 }, { 24, 102 }, { 72, 124 }, { 120, 102 }, { 120, 43 } };
        this.tex = new ResourceLocation("thaumcraft", "textures/gui/arcaneworkbench.png");
        this.tileEntity = e;
        this.ip = par1InventoryPlayer;
        this.field_147000_g = 234;
        this.field_146999_f = 190;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(this.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        final int var5 = (this.field_146294_l - this.field_146999_f) / 2;
        final int var6 = (this.field_146295_m - this.field_147000_g) / 2;
        this.func_73729_b(var5, var6, 0, 0, this.field_146999_f, this.field_147000_g);
        int cost = 0;
        int discount = 0;
        final IArcaneRecipe result = ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventoryCraft, this.ip.field_70458_d);
        AspectList crystals = null;
        final float df = CasterManager.getTotalVisDiscount(this.ip.field_70458_d);
        if (result != null) {
            cost = result.getVis();
            cost *= (int)(1.0f - df);
            discount = (int)(df * 100.0f);
            crystals = result.getCrystals();
        }
        if (crystals != null) {
            GlStateManager.func_179112_b(770, 1);
            for (final Aspect a : crystals.getAspects()) {
                final int id = ShardType.getMetaByAspect(a);
                final Color col = new Color(a.getColor());
                GL11.glColor4f(col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f, 0.33f);
                GL11.glPushMatrix();
                GL11.glTranslatef(var5 + ContainerArcaneWorkbench.xx[id] + 7.5f, var6 + ContainerArcaneWorkbench.yy[id] + 8.0f, 0.0f);
                GL11.glRotatef(id * 60 + this.field_146297_k.func_175606_aa().field_70173_aa % 360 + par1, 0.0f, 0.0f, 1.0f);
                GL11.glScalef(0.5f, 0.5f, 0.0f);
                this.func_73729_b(-32, -32, 192, 0, 64, 64);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179112_b(770, 771);
        }
        GL11.glDisable(3042);
        if (cost > 0) {
            if (this.tileEntity.auraVisClient < cost) {
                GL11.glPushMatrix();
                final float var7 = 0.33f;
                GL11.glColor4f(var7, var7, var7, 0.66f);
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                GL11.glEnable(3042);
                this.field_146296_j.func_180450_b(result.func_77572_b((InventoryCrafting)this.tileEntity.inventoryCraft), var5 + 160, var6 + 64);
                this.field_146296_j.func_180453_a(this.field_146297_k.field_71466_p, result.func_77572_b((InventoryCrafting)this.tileEntity.inventoryCraft), var5 + 160, var6 + 64, "");
                GlStateManager.func_179145_e();
                GlStateManager.func_179126_j();
                RenderHelper.func_74519_b();
                GL11.glPopMatrix();
                RenderHelper.func_74518_a();
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(var5 + 168), (float)(var6 + 46), 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.0f);
            String text = this.tileEntity.auraVisClient + " available";
            int ll = this.field_146289_q.func_78256_a(text) / 2;
            this.field_146289_q.func_78276_b(text, -ll, 0, (this.tileEntity.auraVisClient < cost) ? 15625838 : 7237358);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(var5 + 168), (float)(var6 + 38), 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.0f);
            text = cost + " vis";
            if (discount > 0) {
                text = text + " (" + discount + "% discount)";
            }
            ll = this.field_146289_q.func_78256_a(text) / 2;
            this.field_146289_q.func_78276_b(text, -ll, 0, 12648447);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
}
