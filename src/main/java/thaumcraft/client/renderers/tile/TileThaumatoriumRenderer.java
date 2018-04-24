package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.tiles.crafting.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.client.*;
import thaumcraft.api.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import thaumcraft.api.crafting.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.tileentity.*;

@SideOnly(Side.CLIENT)
public class TileThaumatoriumRenderer extends TileEntitySpecialRenderer
{
    EntityItem entityitem;
    
    public TileThaumatoriumRenderer() {
        this.entityitem = null;
    }
    
    public void renderTileEntityAt(final TileThaumatorium tile, final double par2, final double par4, final double par6, final float par8) {
        final EnumFacing facing = BlockStateUtils.getFacing(tile.func_145832_p());
        if (tile != null && tile.func_145831_w() != null && tile.recipeHash != null && tile.recipeHash.size() > 0) {
            final int stack = Minecraft.func_71410_x().func_175606_aa().field_70173_aa / 40 % tile.recipeHash.size();
            final CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(tile.recipeHash.get(stack));
            if (recipe != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)par2 + 0.5f + facing.func_82601_c() / 1.99f, (float)par4 + 1.125f, (float)par6 + 0.5f + facing.func_82599_e() / 1.99f);
                switch (facing.ordinal()) {
                    case 5: {
                        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                    case 4: {
                        GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                    case 2: {
                        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                        break;
                    }
                }
                GL11.glScaled(0.75, 0.75, 0.75);
                final ItemStack is = recipe.getRecipeOutput().func_77946_l();
                is.func_190920_e(1);
                this.entityitem = new EntityItem(tile.func_145831_w(), 0.0, 0.0, 0.0, is);
                this.entityitem.field_70290_d = 0.0f;
                final RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
                rendermanager.func_188391_a((Entity)this.entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f, false);
                GL11.glPopMatrix();
            }
        }
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        this.renderTileEntityAt((TileThaumatorium)te, x, y, z, partialTicks);
    }
}
