package thaumcraft.client.renderers.entity.construct;

import thaumcraft.api.golems.parts.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import thaumcraft.common.entities.construct.golem.parts.*;
import net.minecraft.client.renderer.*;

public class PartModelWheel extends PartModel
{
    public PartModelWheel(final ResourceLocation objModel, final ResourceLocation objTexture, final EnumAttachPoint attachPoint) {
        super(objModel, objTexture, attachPoint);
    }
    
    @Override
    public void preRenderObjectPart(final String partName, final IGolemAPI golem, final float partialTicks, final EnumLimbSide side) {
        if (partName.equals("wheel")) {
            float lastRot = 0.0f;
            if (GolemLegWheels.ani.containsKey(golem.getGolemEntity().func_145782_y())) {
                lastRot = GolemLegWheels.ani.get(golem.getGolemEntity().func_145782_y());
            }
            GlStateManager.func_179137_b(0.0, -0.375, 0.0);
            GlStateManager.func_179114_b(lastRot, -1.0f, 0.0f, 0.0f);
        }
    }
}
