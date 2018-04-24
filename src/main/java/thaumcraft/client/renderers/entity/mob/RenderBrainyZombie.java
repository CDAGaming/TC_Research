package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import thaumcraft.common.entities.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderBrainyZombie extends RenderZombie
{
    private static final ResourceLocation field_110865_p;
    private static final ResourceLocation field_110864_q;
    private ModelBiped field_82434_o;
    private ModelZombieVillager field_82432_p;
    private int field_82431_q;
    
    public RenderBrainyZombie(final RenderManager rm) {
        super(rm);
        this.field_82431_q = 1;
    }
    
    protected ResourceLocation func_110775_a(final EntityZombie entity) {
        return RenderBrainyZombie.field_110865_p;
    }
    
    protected void preRenderScale(final EntityGiantBrainyZombie z, final float par2) {
        GL11.glScalef(1.0f + z.getAnger(), 1.0f + z.getAnger(), 1.0f + z.getAnger());
        final float q = Math.min(1.0f, z.getAnger()) / 2.0f;
        GL11.glColor3f(1.0f, 1.0f - q, 1.0f - q);
    }
    
    protected void preRenderCallback(final EntityZombie par1EntityLiving, final float par2) {
        if (par1EntityLiving instanceof EntityGiantBrainyZombie) {
            this.preRenderScale((EntityGiantBrainyZombie)par1EntityLiving, par2);
        }
    }
    
    static {
        field_110865_p = new ResourceLocation("thaumcraft", "textures/entity/bzombie.png");
        field_110864_q = new ResourceLocation("thaumcraft", "textures/entity/bzombievil.png");
    }
}
