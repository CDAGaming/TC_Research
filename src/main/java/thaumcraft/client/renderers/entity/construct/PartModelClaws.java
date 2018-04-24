package thaumcraft.client.renderers.entity.construct;

import thaumcraft.api.golems.parts.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.client.renderer.*;

public class PartModelClaws extends PartModel
{
    float f;
    
    public PartModelClaws(final ResourceLocation objModel, final ResourceLocation objTexture, final EnumAttachPoint attachPoint) {
        super(objModel, objTexture, attachPoint);
        this.f = 0.0f;
    }
    
    @Override
    public void preRenderObjectPart(final String partName, final IGolemAPI golem, final float partialTicks, final EnumLimbSide side) {
        if (partName.startsWith("claw")) {
            this.f = 0.0f;
            this.f = golem.getGolemEntity().func_70678_g(partialTicks) * 4.1f;
            this.f *= this.f;
            GlStateManager.func_179137_b(0.0, -0.2, 0.0);
            GlStateManager.func_179114_b(this.f, partName.endsWith("1") ? 1.0f : -1.0f, 0.0f, 0.0f);
        }
    }
}
