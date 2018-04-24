package thaumcraft.common.items.baubles;

import thaumcraft.common.items.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import baubles.api.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.capabilities.*;

public class ItemVoidseerCharm extends ItemTCBase implements IBauble, IVisDiscountGear, IWarpingGear
{
    public ItemVoidseerCharm() {
        super("voidseer_charm", new String[0]);
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public BaubleType getBaubleType(final ItemStack itemstack) {
        return BaubleType.CHARM;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_BLUE + "" + TextFormatting.ITALIC + I18n.func_74838_a("item.voidseer_charm.text"));
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        int q = 0;
        final IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
        if (warp != null) {
            final int pw = Math.min(100, warp.get(IPlayerWarp.EnumWarpType.PERMANENT));
            q = (int)(pw / 100.0f * 25.0f);
        }
        return q;
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return this.getVisDiscount(itemstack, player) / 5;
    }
}
