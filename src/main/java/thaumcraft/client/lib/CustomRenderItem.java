package thaumcraft.client.lib;

import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.resources.*;

public class CustomRenderItem extends RenderItem
{
    public CustomRenderItem() {
        super((TextureManager)null, Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178083_a(), (ItemColors)null);
    }
    
    public void func_175041_b() {
    }
    
    public void func_180453_a(final FontRenderer fr, final ItemStack stack, final int xPosition, final int yPosition, String text) {
        if (stack != null && !stack.func_190926_b() && stack.func_190916_E() <= 0) {
            text = TextFormatting.GOLD + "*";
        }
        Minecraft.func_71410_x().func_175599_af().func_180453_a(fr, stack, xPosition, yPosition, text);
    }
    
    protected void func_175048_a(final Item itm, final int subType, final String identifier) {
    }
    
    protected void func_175029_a(final Block blk, final int subType, final String identifier) {
    }
    
    public ItemModelMesher func_175037_a() {
        return Minecraft.func_71410_x().func_175599_af().func_175037_a();
    }
    
    public void func_180454_a(final ItemStack stack, final IBakedModel model) {
        Minecraft.func_71410_x().func_175599_af().func_180454_a(stack, model);
    }
    
    public boolean func_175050_a(final ItemStack stack) {
        return Minecraft.func_71410_x().func_175599_af().func_175050_a(stack);
    }
    
    public void func_181564_a(final ItemStack p_181564_1_, final ItemCameraTransforms.TransformType p_181564_2_) {
        Minecraft.func_71410_x().func_175599_af().func_181564_a(p_181564_1_, p_181564_2_);
    }
    
    public IBakedModel func_184393_a(final ItemStack p_184393_1_, final World p_184393_2_, final EntityLivingBase p_184393_3_) {
        return Minecraft.func_71410_x().func_175599_af().func_184393_a(p_184393_1_, p_184393_2_, p_184393_3_);
    }
    
    public void func_184392_a(final ItemStack p_184392_1_, final EntityLivingBase p_184392_2_, final ItemCameraTransforms.TransformType p_184392_3_, final boolean p_184392_4_) {
        Minecraft.func_71410_x().func_175599_af().func_184392_a(p_184392_1_, p_184392_2_, p_184392_3_, p_184392_4_);
    }
    
    public void func_184391_a(final EntityLivingBase p_184391_1_, final ItemStack p_184391_2_, final int p_184391_3_, final int p_184391_4_) {
        Minecraft.func_71410_x().func_175599_af().func_184391_a(p_184391_1_, p_184391_2_, p_184391_3_, p_184391_4_);
    }
    
    public void func_175042_a(final ItemStack stack, final int x, final int y) {
        Minecraft.func_71410_x().func_175599_af().func_175042_a(stack, x, y);
    }
    
    public void func_180450_b(final ItemStack stack, final int xPosition, final int yPosition) {
        Minecraft.func_71410_x().func_175599_af().func_180450_b(stack, xPosition, yPosition);
    }
    
    public void func_175030_a(final FontRenderer fr, final ItemStack stack, final int xPosition, final int yPosition) {
        Minecraft.func_71410_x().func_175599_af().func_175030_a(fr, stack, xPosition, yPosition);
    }
    
    public void func_110549_a(final IResourceManager resourceManager) {
        Minecraft.func_71410_x().func_175599_af().func_110549_a(resourceManager);
    }
}
