package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.enchantment.*;

public class ItemElementalPickaxe extends ItemPickaxe implements IThaumcraftItems
{
    public ItemElementalPickaxe(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("elemental_pick");
        this.func_77655_b("elemental_pick");
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
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (!player.field_70170_p.field_72995_K) {
            if (!(entity instanceof EntityPlayer) || FMLCommonHandler.instance().getMinecraftServerInstance().func_71219_W()) {
                entity.func_70015_d(2);
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack w1 = new ItemStack((Item)this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.REFINING, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.SOUNDING, 2);
            items.add((Object)w1);
        }
    }
}
