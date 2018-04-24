package thaumcraft.client.renderers.entity.mob;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderThaumicSlime extends RenderSlime
{
    private static final ResourceLocation slimeTexture;
    
    public RenderThaumicSlime(final RenderManager p_i46141_1_, final ModelBase p_i46141_2_, final float p_i46141_3_) {
        super(p_i46141_1_);
    }
    
    protected ResourceLocation func_110775_a(final EntitySlime entity) {
        return RenderThaumicSlime.slimeTexture;
    }
    
    static {
        slimeTexture = new ResourceLocation("thaumcraft", "textures/entity/tslime.png");
    }
}
