package thaumcraft.client.renderers.entity.construct;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.common.entities.monster.cult.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import thaumcraft.client.lib.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderCultistPortalLesser extends Render
{
    public static final ResourceLocation portaltex;
    
    public RenderCultistPortalLesser(final RenderManager renderManager) {
        super(renderManager);
        this.field_76989_e = 0.0f;
        this.field_76987_f = 0.0f;
    }
    
    public void renderPortal(final EntityCultistPortalLesser portal, final double px, double py, final double pz, final float par8, final float f) {
        if (portal.isActive()) {
            final long nt = System.nanoTime();
            final long time = nt / 50000000L;
            float scaley = 1.4f;
            int e = (int)Math.min(50.0f, portal.activeCounter + f);
            if (portal.field_70737_aN > 0) {
                final double d = Math.sin(portal.field_70737_aN * 72 * 3.141592653589793 / 180.0);
                scaley -= (float)(d / 4.0);
                e += (int)(6.0 * d);
            }
            if (portal.pulse > 0) {
                final double d = Math.sin(portal.pulse * 36 * 3.141592653589793 / 180.0);
                scaley += (float)(d / 4.0);
                e += (int)(12.0 * d);
            }
            float scale = e / 50.0f * 1.25f;
            py += portal.field_70131_O / 2.0f;
            final float m = (1.0f - portal.func_110143_aJ() / portal.func_110138_aP()) / 3.0f;
            final float bob = MathHelper.func_76126_a(portal.activeCounter / (5.0f - 12.0f * m)) * m + m;
            final float bob2 = MathHelper.func_76126_a(portal.activeCounter / (6.0f - 15.0f * m)) * m + m;
            final float alpha = 1.0f - bob;
            scaley -= bob / 4.0f;
            scale -= bob2 / 3.0f;
            this.func_110776_a(RenderCultistPortalLesser.portaltex);
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
            if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
                final Tessellator tessellator = Tessellator.func_178181_a();
                final float arX = ActiveRenderInfo.func_178808_b();
                final float arZ = ActiveRenderInfo.func_178803_d();
                final float arYZ = ActiveRenderInfo.func_178805_e();
                final float arXY = ActiveRenderInfo.func_178807_f();
                final float arXZ = ActiveRenderInfo.func_178809_c();
                tessellator.func_178180_c().func_181668_a(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
                final Vec3d v1 = new Vec3d((double)(-arX - arYZ), (double)(-arXZ), (double)(-arZ - arXY));
                final Vec3d v2 = new Vec3d((double)(-arX + arYZ), (double)arXZ, (double)(-arZ + arXY));
                final Vec3d v3 = new Vec3d((double)(arX + arYZ), (double)arXZ, (double)(arZ + arXY));
                final Vec3d v4 = new Vec3d((double)(arX - arYZ), (double)(-arXZ), (double)(arZ - arXY));
                final int frame = 15 - (int)time % 16;
                final float f2 = frame / 16.0f;
                final float f3 = f2 + 0.0625f;
                final float f4 = 0.0f;
                final float f5 = 1.0f;
                final int i = 220;
                final int j = i >> 16 & 0xFFFF;
                final int k = i & 0xFFFF;
                tessellator.func_178180_c().func_181662_b(px + v1.field_72450_a * scale, py + v1.field_72448_b * scaley, pz + v1.field_72449_c * scale).func_187315_a((double)f3, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, alpha).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                tessellator.func_178180_c().func_181662_b(px + v2.field_72450_a * scale, py + v2.field_72448_b * scaley, pz + v2.field_72449_c * scale).func_187315_a((double)f3, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, alpha).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                tessellator.func_178180_c().func_181662_b(px + v3.field_72450_a * scale, py + v3.field_72448_b * scaley, pz + v3.field_72449_c * scale).func_187315_a((double)f2, (double)f5).func_181666_a(1.0f, 1.0f, 1.0f, alpha).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                tessellator.func_178180_c().func_181662_b(px + v4.field_72450_a * scale, py + v4.field_72448_b * scaley, pz + v4.field_72449_c * scale).func_187315_a((double)f2, (double)f4).func_181666_a(1.0f, 1.0f, 1.0f, alpha).func_187314_a(j, k).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                tessellator.func_78381_a();
            }
            GL11.glDisable(32826);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
    
    public void func_76986_a(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderPortal((EntityCultistPortalLesser)par1Entity, par2, par4, par6, par8, par9);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderCultistPortalLesser.portaltex;
    }
    
    static {
        portaltex = new ResourceLocation("thaumcraft", "textures/misc/cultist_portal.png");
    }
}
