package thaumcraft.client.renderers.entity;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.entity.*;
import thaumcraft.common.entities.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class RenderFallingTaint extends Render
{
    public RenderFallingTaint(final RenderManager p_i46177_1_) {
        super(p_i46177_1_);
        this.field_76989_e = 0.5f;
    }
    
    public void doRender(final EntityFallingTaint p_180557_1_, final double p_180557_2_, final double p_180557_4_, final double p_180557_6_, final float p_180557_8_, final float p_180557_9_) {
        if (p_180557_1_.getBlock() != null) {
            this.func_110776_a(TextureMap.field_110575_b);
            final IBlockState iblockstate = p_180557_1_.getBlock();
            final Block block = iblockstate.func_177230_c();
            final BlockPos blockpos = new BlockPos((Entity)p_180557_1_);
            final World world = p_180557_1_.getWorld();
            if (iblockstate != world.func_180495_p(blockpos) && block.func_149645_b(iblockstate) != EnumBlockRenderType.INVISIBLE && block.func_149645_b(iblockstate) == EnumBlockRenderType.MODEL) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)p_180557_2_, (float)p_180557_4_, (float)p_180557_6_);
                GlStateManager.func_179140_f();
                final Tessellator tessellator = Tessellator.func_178181_a();
                final BufferBuilder BufferBuilder = tessellator.func_178180_c();
                BufferBuilder.func_181668_a(7, DefaultVertexFormats.field_176600_a);
                final int i = blockpos.func_177958_n();
                final int j = blockpos.func_177956_o();
                final int k = blockpos.func_177952_p();
                BufferBuilder.func_178969_c((double)(-i - 0.5f), (double)(-j), (double)(-k - 0.5f));
                final BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
                final IBakedModel ibakedmodel = blockrendererdispatcher.func_184389_a(iblockstate);
                blockrendererdispatcher.func_175019_b().func_178267_a((IBlockAccess)world, ibakedmodel, iblockstate, blockpos, BufferBuilder, false);
                BufferBuilder.func_178969_c(0.0, 0.0, 0.0);
                tessellator.func_78381_a();
                GlStateManager.func_179145_e();
                GlStateManager.func_179121_F();
                super.func_76986_a((Entity)p_180557_1_, p_180557_2_, p_180557_4_, p_180557_6_, p_180557_8_, p_180557_9_);
            }
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityFallingBlock entity) {
        return TextureMap.field_110575_b;
    }
    
    protected ResourceLocation func_110775_a(final Entity entity) {
        return this.getEntityTexture((EntityFallingBlock)entity);
    }
    
    public void func_76986_a(final Entity entity, final double x, final double y, final double z, final float p_76986_8_, final float partialTicks) {
        this.doRender((EntityFallingTaint)entity, x, y, z, p_76986_8_, partialTicks);
    }
}
