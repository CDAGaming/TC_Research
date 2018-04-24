package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import thaumcraft.common.tiles.crafting.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.entity.*;

@SideOnly(Side.CLIENT)
public class TilePedestalRenderer extends TileEntitySpecialRenderer<TilePedestal>
{
    public void render(final TilePedestal ped, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a((TileEntity)ped, x, y, z, partialTicks, destroyStage, alpha);
        if (ped != null && !ped.func_70301_a(0).func_190926_b()) {
            EntityItem entityitem = null;
            final float ticks = Minecraft.func_71410_x().func_175606_aa().field_70173_aa + partialTicks;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 0.5f, (float)y + 0.75f, (float)z + 0.5f);
            GL11.glScaled(1.75, 1.75, 1.75);
            GL11.glRotatef(ticks % 360.0f, 0.0f, 1.0f, 0.0f);
            final ItemStack is = ped.func_70301_a(0).func_77946_l();
            is.func_190920_e(1);
            entityitem = new EntityItem((World)Minecraft.func_71410_x().field_71441_e, 0.0, 0.0, 0.0, is);
            entityitem.field_70290_d = 0.0f;
            final RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
            rendermanager.func_188391_a((Entity)entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
            GL11.glPopMatrix();
        }
    }
}
