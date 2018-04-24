package thaumcraft.client.renderers.entity.construct;

import thaumcraft.api.golems.parts.*;
import java.util.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.client.renderer.*;

public class PartModelBreakers extends PartModel
{
    private HashMap<Integer, Float[]> ani;
    
    public PartModelBreakers(final ResourceLocation objModel, final ResourceLocation objTexture, final EnumAttachPoint attachPoint) {
        super(objModel, objTexture, attachPoint);
        this.ani = new HashMap<Integer, Float[]>();
    }
    
    @Override
    public void preRenderObjectPart(final String partName, final IGolemAPI golem, final float partialTicks, final EnumLimbSide side) {
        if (partName.equals("grinder")) {
            float lastSpeed = 0.0f;
            float lastRot = 0.0f;
            if (this.ani.containsKey(golem.getGolemEntity().func_145782_y())) {
                lastSpeed = this.ani.get(golem.getGolemEntity().func_145782_y())[0];
                lastRot = this.ani.get(golem.getGolemEntity().func_145782_y())[1];
            }
            final float f = Math.max(lastSpeed, golem.getGolemEntity().func_70678_g(partialTicks) * 20.0f);
            final float rot = lastRot + f;
            lastSpeed = f * 0.99f;
            this.ani.put(golem.getGolemEntity().func_145782_y(), new Float[] { lastSpeed, rot });
            GlStateManager.func_179137_b(0.0, -0.34, 0.0);
            GlStateManager.func_179114_b((golem.getGolemEntity().field_70173_aa + partialTicks) / 2.0f + rot + ((side == EnumLimbSide.LEFT) ? 22 : 0), (side == EnumLimbSide.LEFT) ? -1.0f : 1.0f, 0.0f, 0.0f);
        }
    }
}
