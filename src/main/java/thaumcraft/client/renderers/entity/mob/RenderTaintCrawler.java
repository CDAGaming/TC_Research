package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import thaumcraft.common.entities.monster.tainted.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class RenderTaintCrawler extends RenderLiving
{
    private static final ResourceLocation textures;
    
    public RenderTaintCrawler(final RenderManager p_i46144_1_) {
        super(p_i46144_1_, (ModelBase)new ModelSilverfish(), 0.2f);
    }
    
    protected float func_180584_a(final EntityTaintCrawler p_180584_1_) {
        return 180.0f;
    }
    
    protected ResourceLocation getEntityTexture(final EntityTaintCrawler entity) {
        return RenderTaintCrawler.textures;
    }
    
    protected float func_77037_a(final EntityLivingBase p_77037_1_) {
        return this.func_180584_a((EntityTaintCrawler)p_77037_1_);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return this.getEntityTexture((EntityTaintCrawler)entity);
    }
    
    protected void func_77041_b(final EntityLivingBase par1EntityLiving, final float par2) {
        GL11.glScalef(0.7f, 0.7f, 0.7f);
    }
    
    static {
        textures = new ResourceLocation("thaumcraft", "textures/entity/crawler.png");
    }
}
