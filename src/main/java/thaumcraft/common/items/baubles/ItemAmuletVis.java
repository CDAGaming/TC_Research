package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import baubles.api.*;
import thaumcraft.api.items.*;
import net.minecraft.util.*;
import baubles.api.cap.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemAmuletVis extends ItemTCBase implements IBauble
{
    public ItemAmuletVis() {
        super("amulet_vis", new String[] { "found", "crafted" });
        this.field_77777_bU = 1;
        this.func_77656_e(0);
        this.func_77627_a(true);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return (itemstack.func_77952_i() == 0) ? EnumRarity.UNCOMMON : EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.AMULET;
    }
    
    public void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
        if (player instanceof EntityPlayer && !player.field_70170_p.field_72995_K && player.field_70173_aa % ((itemstack.func_77952_i() == 0) ? 40 : 5) == 0) {
            NonNullList<ItemStack> inv = (NonNullList<ItemStack>)((EntityPlayer)player).field_71071_by.field_70462_a;
            int a = 0;
            while (true) {
                final int n = a;
                final InventoryPlayer field_71071_by = ((EntityPlayer)player).field_71071_by;
                if (n >= InventoryPlayer.func_70451_h()) {
                    final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler((EntityPlayer)player);
                    for (int a2 = 0; a2 < baubles.getSlots(); ++a2) {
                        if (RechargeHelper.rechargeItem(player.field_70170_p, baubles.getStackInSlot(a2), player.func_180425_c(), (EntityPlayer)player, 1) > 0.0f) {
                            return;
                        }
                    }
                    inv = (NonNullList<ItemStack>)((EntityPlayer)player).field_71071_by.field_70460_b;
                    for (int a2 = 0; a2 < inv.size(); ++a2) {
                        if (RechargeHelper.rechargeItem(player.field_70170_p, (ItemStack)inv.get(a2), player.func_180425_c(), (EntityPlayer)player, 1) > 0.0f) {
                            return;
                        }
                    }
                    break;
                }
                if (RechargeHelper.rechargeItem(player.field_70170_p, (ItemStack)inv.get(a), player.func_180425_c(), (EntityPlayer)player, 1) > 0.0f) {
                    return;
                }
                ++a;
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.AQUA + I18n.func_74838_a("item.amulet_vis.text"));
    }
}
