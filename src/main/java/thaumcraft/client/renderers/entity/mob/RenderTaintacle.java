package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.client.renderers.models.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderTaintacle extends RenderLiving
{
    private static final ResourceLocation rl;
    
    public RenderTaintacle(final RenderManager rm, final float shadow, final int length) {
        super(rm, (ModelBase)new ModelTaintacle(length, false), shadow);
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return RenderTaintacle.rl;
    }
    
    static {
        rl = new ResourceLocation("thaumcraft", "textures/entity/taintacle.png");
    }
}
