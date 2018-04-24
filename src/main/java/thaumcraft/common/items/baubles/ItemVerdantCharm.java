package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;
import baubles.api.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import thaumcraft.api.items.*;
import thaumcraft.api.potions.*;

public class ItemVerdantCharm extends ItemTCBase implements IBauble, IRechargable
{
    public ItemVerdantCharm() {
        super("verdant_charm", new String[0]);
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
        this.func_185043_a(new ResourceLocation("type"), (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                if (stack.func_77973_b() instanceof ItemVerdantCharm && stack.func_77942_o()) {
                    return stack.func_77978_p().func_74771_c("type");
                }
                return 0.0f;
            }
        });
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.CHARM;
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            items.add((Object)new ItemStack((Item)this));
            final ItemStack vhbl = new ItemStack((Item)this);
            vhbl.func_77983_a("type", (NBTBase)new NBTTagByte((byte)1));
            items.add((Object)vhbl);
            final ItemStack vhbl2 = new ItemStack((Item)this);
            vhbl2.func_77983_a("type", (NBTBase)new NBTTagByte((byte)2));
            items.add((Object)vhbl2);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74771_c("type") == 1) {
            tooltip.add(TextFormatting.GOLD + I18n.func_74838_a("item.verdant_charm.life.text"));
        }
        if (stack.func_77942_o() && stack.func_77978_p().func_74771_c("type") == 2) {
            tooltip.add(TextFormatting.GOLD + I18n.func_74838_a("item.verdant_charm.sustain.text"));
        }
    }
    
    public void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
        if (!player.field_70170_p.field_72995_K && player.field_70173_aa % 20 == 0 && player instanceof EntityPlayer) {
            if (player.func_70660_b(MobEffects.field_82731_v) != null && RechargeHelper.consumeCharge(itemstack, player, 20)) {
                player.func_184589_d(MobEffects.field_82731_v);
                return;
            }
            if (player.func_70660_b(MobEffects.field_76436_u) != null && RechargeHelper.consumeCharge(itemstack, player, 10)) {
                player.func_184589_d(MobEffects.field_76436_u);
                return;
            }
            if (player.func_70660_b(PotionFluxTaint.instance) != null && RechargeHelper.consumeCharge(itemstack, player, 5)) {
                player.func_184589_d(PotionFluxTaint.instance);
                return;
            }
            if (itemstack.func_77942_o() && itemstack.func_77978_p().func_74771_c("type") == 1 && player.func_110143_aJ() < player.func_110138_aP() && RechargeHelper.consumeCharge(itemstack, player, 5)) {
                player.func_70691_i(1.0f);
                return;
            }
            if (itemstack.func_77942_o() && itemstack.func_77978_p().func_74771_c("type") == 2) {
                if (player.func_70086_ai() < 100 && RechargeHelper.consumeCharge(itemstack, player, 1)) {
                    player.func_70050_g(300);
                    return;
                }
                if (player instanceof EntityPlayer && ((EntityPlayer)player).func_71043_e(false) && RechargeHelper.consumeCharge(itemstack, player, 1)) {
                    ((EntityPlayer)player).func_71024_bL().func_75122_a(1, 0.3f);
                }
            }
        }
    }
    
    public int getMaxCharge(final ItemStack stack, final EntityLivingBase player) {
        return 200;
    }
    
    public EnumChargeDisplay showInHud(final ItemStack stack, final EntityLivingBase player) {
        return EnumChargeDisplay.NORMAL;
    }
    
    public boolean willAutoSync(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
}
