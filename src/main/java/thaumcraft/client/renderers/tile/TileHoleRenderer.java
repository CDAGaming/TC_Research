package thaumcraft.client.renderers.tile;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import thaumcraft.client.lib.ender.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import thaumcraft.client.lib.*;
import net.minecraft.block.state.*;

public class TileHoleRenderer extends TileEntitySpecialRenderer
{
    private final ShaderCallback shaderCallback;
    private static final ResourceLocation starsTexture;
    
    public TileHoleRenderer() {
        this.shaderCallback = new ShaderCallback() {
            @Override
            public void call(final int shader) {
                final Minecraft mc = Minecraft.func_71410_x();
                final int x = ARBShaderObjects.glGetUniformLocationARB(shader, (CharSequence)"yaw");
                ARBShaderObjects.glUniform1fARB(x, (float)(mc.field_71439_g.field_70177_z * 2.0f * 3.141592653589793 / 360.0));
                final int z = ARBShaderObjects.glGetUniformLocationARB(shader, (CharSequence)"pitch");
                ARBShaderObjects.glUniform1fARB(z, -(float)(mc.field_71439_g.field_70125_A * 2.0f * 3.141592653589793 / 360.0));
            }
        };
    }
    
    public void func_192841_a(final TileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        super.func_192841_a(te, x, y, z, partialTicks, destroyStage, alpha);
        GL11.glPushMatrix();
        this.func_147499_a(TileHoleRenderer.starsTexture);
        ShaderHelper.useShader(ShaderHelper.endShader, this.shaderCallback);
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        for (final EnumFacing face : EnumFacing.values()) {
            final IBlockState bs = te.func_145831_w().func_180495_p(te.func_174877_v().func_177972_a(face));
            if (bs.func_185914_p() && bs.func_177230_c() != BlocksTC.hole) {
                GL11.glPushMatrix();
                GL11.glRotatef(90.0f, (float)(-face.func_96559_d()), (float)face.func_82601_c(), (float)(-face.func_82599_e()));
                if (face.func_82599_e() < 0) {
                    GL11.glTranslated(0.0, 0.0, -0.49900001287460327);
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                else {
                    GL11.glTranslated(0.0, 0.0, 0.49900001287460327);
                }
                GL11.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
                UtilsFX.renderQuadCentered();
                GL11.glPopMatrix();
            }
        }
        ShaderHelper.releaseShader();
        GL11.glPopMatrix();
    }
    
    static {
        starsTexture = new ResourceLocation("textures/entity/end_portal.png");
    }
}
