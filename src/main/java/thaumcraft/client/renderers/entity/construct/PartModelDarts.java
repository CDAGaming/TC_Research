package thaumcraft.client.renderers.entity.construct;

import thaumcraft.api.golems.parts.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;

public class PartModelDarts extends PartModel
{
    public PartModelDarts(final ResourceLocation objModel, final ResourceLocation objTexture, final EnumAttachPoint attachPoint) {
        super(objModel, objTexture, attachPoint);
    }
    
    @Override
    public float preRenderArmRotationX(final IGolemAPI golem, final float partialTicks, final EnumLimbSide side, float inputRot) {
        if (golem.isInCombat()) {
            inputRot = 90.0f - golem.getGolemEntity().field_70127_C + inputRot / 10.0f;
        }
        return inputRot;
    }
    
    @Override
    public float preRenderArmRotationY(final IGolemAPI golem, final float partialTicks, final EnumLimbSide side, float inputRot) {
        if (golem.isInCombat()) {
            inputRot /= 10.0f;
        }
        return inputRot;
    }
    
    @Override
    public float preRenderArmRotationZ(final IGolemAPI golem, final float partialTicks, final EnumLimbSide side, float inputRot) {
        if (golem.isInCombat()) {
            inputRot /= 10.0f;
        }
        return inputRot;
    }
}
