package thaumcraft.client.renderers.entity.mob;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import net.minecraft.client.*;

@SideOnly(Side.CLIENT)
public class LayerHeldItemPech extends LayerHeldItem
{
    public LayerHeldItemPech(final RenderLivingBase<?> livingEntityRendererIn) {
        super((RenderLivingBase)livingEntityRendererIn);
    }
    
    public void func_177141_a(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final boolean flag = entitylivingbaseIn.func_184591_cq() == EnumHandSide.RIGHT;
        final ItemStack itemstack = flag ? entitylivingbaseIn.func_184592_cb() : entitylivingbaseIn.func_184614_ca();
        final ItemStack itemstack2 = flag ? entitylivingbaseIn.func_184614_ca() : entitylivingbaseIn.func_184592_cb();
        if ((itemstack != null && !itemstack.func_190926_b()) || (itemstack2 != null && !itemstack2.func_190926_b())) {
            GlStateManager.func_179094_E();
            if (this.field_177206_a.func_177087_b().field_78091_s) {
                final float f = 0.5f;
                GlStateManager.func_179109_b(0.0f, 0.625f, 0.0f);
                GlStateManager.func_179114_b(-20.0f, -1.0f, 0.0f, 0.0f);
                GlStateManager.func_179152_a(f, f, f);
            }
            this.renderHeldItem(entitylivingbaseIn, itemstack2, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.func_179121_F();
        }
    }
    
    private void renderHeldItem(final EntityLivingBase entity, final ItemStack stack, final ItemCameraTransforms.TransformType p_188358_3_, final EnumHandSide p_188358_4_) {
        if (stack != null && !stack.func_190926_b()) {
            GlStateManager.func_179094_E();
            ((ModelBiped)this.field_177206_a.func_177087_b()).func_187073_a(0.0625f, p_188358_4_);
            GlStateManager.func_179109_b(0.0f, -0.1f, 0.0625f);
            if (stack.func_77973_b() instanceof ItemBow) {
                GlStateManager.func_179137_b(-0.07500000298023224, -0.1, 0.0);
            }
            if (entity.func_70093_af()) {
                GlStateManager.func_179109_b(0.0f, 0.2f, 0.0f);
            }
            GlStateManager.func_179114_b(-90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
            final boolean flag = p_188358_4_ == EnumHandSide.LEFT;
            GlStateManager.func_179109_b(flag ? -0.0625f : 0.0625f, 0.125f, -0.625f);
            Minecraft.func_71410_x().func_175597_ag().func_187462_a(entity, stack, p_188358_3_, flag);
            GlStateManager.func_179121_F();
        }
    }
    
    public boolean func_177142_b() {
        return false;
    }
}
