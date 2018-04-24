package thaumcraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.common.entities.construct.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.common.container.*;
import net.minecraft.inventory.*;
import thaumcraft.client.gui.plugins.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.io.*;

@SideOnly(Side.CLIENT)
public class GuiTurretAdvanced extends GuiContainer
{
    EntityTurretCrossbowAdvanced turret;
    public static ResourceLocation tex;
    
    public GuiTurretAdvanced(final InventoryPlayer par1InventoryPlayer, final World world, final EntityTurretCrossbowAdvanced t) {
        super((Container)new ContainerTurretAdvanced(par1InventoryPlayer, world, t));
        this.field_146999_f = 175;
        this.field_147000_g = 232;
        this.turret = t;
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(new GuiToggleButton(1, this.field_147003_i + 90, this.field_147009_r + 13, 8, 8, "button.turretfocus.1", new Runnable() {
            @Override
            public void run() {
                GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetAnimal();
            }
        }));
        this.field_146292_n.add(new GuiToggleButton(2, this.field_147003_i + 90, this.field_147009_r + 27, 8, 8, "button.turretfocus.2", new Runnable() {
            @Override
            public void run() {
                GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetMob();
            }
        }));
        this.field_146292_n.add(new GuiToggleButton(3, this.field_147003_i + 90, this.field_147009_r + 41, 8, 8, "button.turretfocus.3", new Runnable() {
            @Override
            public void run() {
                GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetPlayer();
            }
        }));
        this.field_146292_n.add(new GuiToggleButton(4, this.field_147003_i + 90, this.field_147009_r + 55, 8, 8, "button.turretfocus.4", new Runnable() {
            @Override
            public void run() {
                GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetFriendly();
            }
        }));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }
    
    protected void func_146979_b(final int par1, final int par2) {
    }
    
    protected void func_146976_a(final float par1, final int par2, final int par3) {
        this.field_146297_k.field_71446_o.func_110577_a(GuiTurretAdvanced.tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int k = (this.field_146294_l - this.field_146999_f) / 2;
        final int l = (this.field_146295_m - this.field_147000_g) / 2;
        GL11.glEnable(3042);
        this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
        final int h = (int)(39.0f * (this.turret.func_110143_aJ() / this.turret.func_110138_aP()));
        this.func_73729_b(k + 30, l + 59, 192, 48, h, 6);
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button.field_146127_k == 1) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 1);
        }
        else if (button.field_146127_k == 2) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 2);
        }
        else if (button.field_146127_k == 3) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 3);
        }
        else if (button.field_146127_k == 4) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147002_h.field_75152_c, 4);
        }
        else {
            super.func_146284_a(button);
        }
    }
    
    static {
        GuiTurretAdvanced.tex = new ResourceLocation("thaumcraft", "textures/gui/gui_turret_advanced.png");
    }
}
