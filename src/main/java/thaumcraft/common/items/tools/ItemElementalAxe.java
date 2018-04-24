package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.*;
import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.enchantment.*;

public class ItemElementalAxe extends ItemAxe implements IThaumcraftItems
{
    public ItemElementalAxe(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial, 8.0f, -3.0f);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("elemental_axe");
        this.func_77655_b("elemental_axe");
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
        return (Set<String>)ImmutableSet.of((Object)"axe");
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumAction func_77661_b(final ItemStack itemstack) {
        return EnumAction.BOW;
    }
    
    public int func_77626_a(final ItemStack p_77626_1_) {
        return 72000;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        playerIn.func_184598_c(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)playerIn.func_184586_b(hand));
    }
    
    public void onUsingTick(final ItemStack stack, final EntityLivingBase player, final int count) {
        final List<EntityItem> stuff = EntityUtils.getEntitiesInRange(player.field_70170_p, player.field_70165_t, player.field_70163_u, player.field_70161_v, (Entity)player, (Class<? extends EntityItem>)EntityItem.class, 10.0);
        if (stuff != null && stuff.size() > 0) {
            for (final EntityItem e : stuff) {
                if (!e.field_70128_L) {
                    double d6 = e.field_70165_t - player.field_70165_t;
                    double d7 = e.field_70163_u - player.field_70163_u + player.field_70131_O / 2.0f;
                    double d8 = e.field_70161_v - player.field_70161_v;
                    final double d9 = MathHelper.func_76133_a(d6 * d6 + d7 * d7 + d8 * d8);
                    d6 /= d9;
                    d7 /= d9;
                    d8 /= d9;
                    final double d10 = 0.3;
                    final EntityItem entityItem = e;
                    entityItem.field_70159_w -= d6 * d10;
                    final EntityItem entityItem2 = e;
                    entityItem2.field_70181_x -= d7 * d10 - 0.1;
                    final EntityItem entityItem3 = e;
                    entityItem3.field_70179_y -= d8 * d10;
                    if (e.field_70159_w > 0.25) {
                        e.field_70159_w = 0.25;
                    }
                    if (e.field_70159_w < -0.25) {
                        e.field_70159_w = -0.25;
                    }
                    if (e.field_70181_x > 0.25) {
                        e.field_70181_x = 0.25;
                    }
                    if (e.field_70181_x < -0.25) {
                        e.field_70181_x = -0.25;
                    }
                    if (e.field_70179_y > 0.25) {
                        e.field_70179_y = 0.25;
                    }
                    if (e.field_70179_y < -0.25) {
                        e.field_70179_y = -0.25;
                    }
                    if (!player.field_70170_p.field_72995_K) {
                        continue;
                    }
                    FXDispatcher.INSTANCE.crucibleBubble((float)e.field_70165_t + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 0.2f, (float)e.field_70163_u + e.field_70131_O + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 0.2f, (float)e.field_70161_v + (player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 0.2f, 0.33f, 0.33f, 1.0f);
                }
            }
        }
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack w1 = new ItemStack((Item)this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.BURROWING, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.COLLECTOR, 1);
            items.add((Object)w1);
        }
    }
}
