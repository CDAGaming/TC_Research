package thaumcraft.common.items.tools;

import thaumcraft.api.items.*;
import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class ItemVoidPickaxe extends ItemPickaxe implements IWarpingGear, IThaumcraftItems
{
    public ItemVoidPickaxe(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("void_pick");
        this.func_77655_b("void_pick");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public Item getItem() {
        return (Item)this;
    }
    
    public String[] getVariantNames() {
        return new String[] { "normal" };
    }
    
    public int[] getVariantMeta() {
        return new int[] { 0 };
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        return new ModelResourceLocation("thaumcraft:" + variant);
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        return (Set<String>)ImmutableSet.of((Object)"pickaxe");
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
        if (stack.func_77951_h() && entity != null && entity.field_70173_aa % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.func_77972_a(-1, (EntityLivingBase)entity);
        }
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (!player.field_70170_p.field_72995_K && entity instanceof EntityLivingBase) {
            if (!(entity instanceof EntityPlayer) || FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W()) {
                ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76437_t, 80));
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 1;
    }
}
