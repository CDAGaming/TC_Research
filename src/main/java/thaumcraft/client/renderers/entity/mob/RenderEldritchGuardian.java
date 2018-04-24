package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import thaumcraft.common.entities.monster.boss.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import thaumcraft.common.config.*;

@SideOnly(Side.CLIENT)
public class RenderEldritchGuardian extends RenderLiving
{
    protected ModelEldritchGuardian modelMain;
    private static final ResourceLocation[] skin;
    
    public RenderEldritchGuardian(final RenderManager rm, final ModelEldritchGuardian par1ModelBiped, final float par2) {
        super(rm, (ModelBase)par1ModelBiped, par2);
        this.modelMain = par1ModelBiped;
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return (entity instanceof EntityEldritchWarden) ? RenderEldritchGuardian.skin[1] : RenderEldritchGuardian.skin[0];
    }
    
    public void doRenderLiving(final EntityLiving guardian, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.003921569f);
        GL11.glBlendFunc(770, 771);
        float base = 1.0f;
        double d3 = par4;
        if (guardian instanceof EntityEldritchWarden) {
            d3 -= guardian.field_70131_O * (((EntityEldritchWarden)guardian).getSpawnTimer() / 150.0f);
        }
        else {
            final Entity e = Minecraft.func_71410_x().func_175606_aa();
            final float d4 = (e.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 576.0f : 1024.0f;
            final float d5 = 256.0f;
            if (guardian.field_70170_p != null && guardian.field_70170_p.field_73011_w.getDimension() == ModConfig.CONFIG_WORLD.dimensionOuterId) {
                base = 1.0f;
            }
            else {
                final double d6 = guardian.func_70092_e(e.field_70165_t, e.field_70163_u, e.field_70161_v);
                if (d6 < 256.0) {
                    base = 0.6f;
                }
                else {
                    base = (float)(1.0 - Math.min(d4 - d5, d6 - d5) / (d4 - d5)) * 0.6f;
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, base);
        super.func_76986_a(guardian, par2, d3, par6, par8, par9);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1f);
    }
    
    public void func_76986_a(final EntityLiving par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderLiving(par1Entity, par2, par4, par6, par8, par9);
    }
    
    static {
        skin = new ResourceLocation[] { new ResourceLocation("thaumcraft", "textures/entity/eldritch_guardian.png"), new ResourceLocation("thaumcraft", "textures/entity/eldritch_warden.png") };
    }
}
