package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.lib.enchantment.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import thaumcraft.client.fx.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.*;
import java.util.*;

public class ItemElementalSword extends ItemSword implements IThaumcraftItems
{
    public ItemElementalSword(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("elemental_sword");
        this.func_77655_b("elemental_sword");
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
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack w1 = new ItemStack((Item)this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.ARCING, 2);
            items.add((Object)w1);
        }
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.NONE;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 72000;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        playerIn.func_184598_c(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)playerIn.func_184586_b(hand));
    }
    
    public void onUsingTick(final ItemStack stack, final EntityLivingBase player, final int count) {
        super.onUsingTick(stack, player, count);
        final int ticks = this.func_77626_a(stack) - count;
        if (player.field_70181_x < 0.0) {
            player.field_70181_x /= 1.2000000476837158;
            player.field_70143_R /= 1.2f;
        }
        player.field_70181_x += 0.07999999821186066;
        if (player.field_70181_x > 0.5) {
            player.field_70181_x = 0.20000000298023224;
        }
        if (player instanceof EntityPlayerMP) {
            EntityUtils.resetFloatCounter((EntityPlayerMP)player);
        }
        final List targets = player.field_70170_p.func_72839_b((Entity)player, player.func_174813_aQ().func_72314_b(2.5, 2.5, 2.5));
        if (targets.size() > 0) {
            for (int var9 = 0; var9 < targets.size(); ++var9) {
                final Entity entity = targets.get(var9);
                if (!(entity instanceof EntityPlayer)) {
                    if (entity instanceof EntityLivingBase) {
                        if (!entity.field_70128_L) {
                            if (player.func_184187_bx() == null || player.func_184187_bx() != entity) {
                                final Vec3d p = new Vec3d(player.field_70165_t, player.field_70163_u, player.field_70161_v);
                                final Vec3d t = new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
                                final double distance = p.func_72438_d(t) + 0.1;
                                final Vec3d r = new Vec3d(t.field_72450_a - p.field_72450_a, t.field_72448_b - p.field_72448_b, t.field_72449_c - p.field_72449_c);
                                final Entity entity2 = entity;
                                entity2.field_70159_w += r.field_72450_a / 2.5 / distance;
                                final Entity entity3 = entity;
                                entity3.field_70181_x += r.field_72448_b / 2.5 / distance;
                                final Entity entity4 = entity;
                                entity4.field_70179_y += r.field_72449_c / 2.5 / distance;
                            }
                        }
                    }
                }
            }
        }
        if (player.field_70170_p.field_72995_K) {
            int miny = (int)(player.func_174813_aQ().field_72338_b - 2.0);
            if (player.field_70122_E) {
                miny = MathHelper.func_76128_c(player.func_174813_aQ().field_72338_b);
            }
            for (int a = 0; a < 5; ++a) {
                FXDispatcher.INSTANCE.smokeSpiral(player.field_70165_t, player.func_174813_aQ().field_72338_b + player.field_70131_O / 2.0f, player.field_70161_v, 1.5f, player.field_70170_p.field_73012_v.nextInt(360), miny, 14540253);
            }
            if (player.field_70122_E) {
                final float r2 = player.field_70170_p.field_73012_v.nextFloat() * 360.0f;
                final float mx = -MathHelper.func_76126_a(r2 / 180.0f * 3.1415927f) / 5.0f;
                final float mz = MathHelper.func_76134_b(r2 / 180.0f * 3.1415927f) / 5.0f;
                player.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, player.field_70165_t, player.func_174813_aQ().field_72338_b + 0.10000000149011612, player.field_70161_v, (double)mx, 0.0, (double)mz, new int[0]);
            }
        }
        else if (ticks == 0 || ticks % 20 == 0) {
            player.func_184185_a(SoundsTC.wind, 0.5f, 0.9f + player.field_70170_p.field_73012_v.nextFloat() * 0.2f);
        }
        if (ticks % 20 == 0) {
            stack.func_77972_a(1, player);
        }
    }
}
