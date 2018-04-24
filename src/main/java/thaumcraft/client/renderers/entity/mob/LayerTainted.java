package thaumcraft.client.renderers.entity.mob;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class LayerTainted implements LayerRenderer<EntityLiving>
{
    private static final ResourceLocation TAINT_TEXTURE;
    private final RenderLivingBase renderer;
    private final ModelBase model;
    public static ArrayList<Integer> taintLayers;
    
    public LayerTainted(final int i, final RenderLivingBase witherRendererIn, final ModelBase model) {
        this.renderer = witherRendererIn;
        this.model = model;
        LayerTainted.taintLayers.add(i);
    }
    
    public void doRenderLayer(final EntityLiving entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!LayerTainted.taintLayers.contains(entitylivingbaseIn.func_145782_y())) {
            return;
        }
        GlStateManager.func_179132_a(!entitylivingbaseIn.func_82150_aj());
        this.renderer.func_110776_a(LayerTainted.TAINT_TEXTURE);
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        final float f = entitylivingbaseIn.field_70173_aa + partialTicks;
        final float f2 = MathHelper.func_76134_b(f * 2.5E-4f);
        final float f3 = f * 0.001f;
        GlStateManager.func_179152_a(8.0f, 4.0f, 4.0f);
        GlStateManager.func_179109_b(f2, f3, 0.0f);
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179147_l();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
        GL11.glBlendFunc(770, 771);
        this.model.func_78086_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
        this.model.func_178686_a(this.renderer.func_177087_b());
        this.model.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179084_k();
    }
    
    public boolean func_177142_b() {
        return false;
    }
    
    static {
        TAINT_TEXTURE = new ResourceLocation("thaumcraft:textures/models/taint_fibres.png");
        LayerTainted.taintLayers = new ArrayList<Integer>();
    }
}
