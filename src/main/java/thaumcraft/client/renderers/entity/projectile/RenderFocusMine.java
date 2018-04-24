package thaumcraft.client.renderers.entity.projectile;

import net.minecraft.util.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import thaumcraft.common.entities.projectile.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFocusMine extends Render
{
    ResourceLocation beam;
    private ModelGrappler model;
    
    public RenderFocusMine(final RenderManager rm) {
        super(rm);
        this.beam = new ResourceLocation("thaumcraft", "textures/entity/mine.png");
        this.field_76989_e = 0.0f;
        this.model = new ModelGrappler();
    }
    
    public void renderEntityAt(final Entity entity, final double x, final double y, final double z, final float fq, final float pticks) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(x, y, z);
        final EntityFocusMine mine = (EntityFocusMine)entity;
        final float f = (mine.counter + pticks) % 8.0f / 8.0f;
        final int i = 61680;
        final int j = i % 65536;
        final int k = i / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)j, (float)k);
        GL11.glColor4f(1.0f, 1.0f - f, 1.0f - f, 1.0f);
        this.func_110776_a(this.beam);
        GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * pticks - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * pticks, 0.0f, 0.0f, 1.0f);
        this.model.render();
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void func_76986_a(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        this.renderEntityAt(entity, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return TextureMap.field_110575_b;
    }
}
