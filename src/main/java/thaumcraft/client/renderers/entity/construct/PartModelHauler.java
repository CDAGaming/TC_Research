package thaumcraft.client.renderers.entity.construct;

import thaumcraft.api.golems.parts.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;

public class PartModelHauler extends PartModel
{
    public PartModelHauler(final ResourceLocation objModel, final ResourceLocation objTexture, final EnumAttachPoint attachPoint) {
        super(objModel, objTexture, attachPoint);
    }
    
    @Override
    public void postRenderObjectPart(final String partName, final IGolemAPI golem, final float partialTicks, final EnumLimbSide side) {
        if (golem.getCarrying().size() > 1 && golem.getCarrying().get(1) != null) {
            final ItemStack itemstack = (ItemStack)golem.getCarrying().get(1);
            if (itemstack != null && !itemstack.func_190926_b()) {
                GlStateManager.func_179094_E();
                final Item item = itemstack.func_77973_b();
                final Minecraft minecraft = Minecraft.func_71410_x();
                GlStateManager.func_179139_a(0.375, 0.375, 0.375);
                GlStateManager.func_179109_b(0.0f, 0.33f, 0.825f);
                if (!(item instanceof ItemBlock)) {
                    GlStateManager.func_179109_b(0.0f, 0.0f, -0.25f);
                }
                minecraft.func_175597_ag().func_178099_a(golem.getGolemEntity(), itemstack, ItemCameraTransforms.TransformType.HEAD);
                GlStateManager.func_179121_F();
            }
        }
    }
}
