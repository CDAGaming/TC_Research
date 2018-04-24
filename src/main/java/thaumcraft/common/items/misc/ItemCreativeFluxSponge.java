package thaumcraft.common.items.misc;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.api.aura.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;

public class ItemCreativeFluxSponge extends ItemTCBase
{
    public ItemCreativeFluxSponge() {
        super("creative_flux_sponge", new String[0]);
        this.func_77625_d(1);
        this.func_77627_a(false);
        this.func_77656_e(0);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        tooltip.add(TextFormatting.GREEN + "Right-click to drain all");
        tooltip.add(TextFormatting.GREEN + "flux from 9x9 chunk area");
        tooltip.add(TextFormatting.DARK_PURPLE + "Creative only");
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.EPIC;
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        if (worldIn.field_72995_K) {
            playerIn.func_184609_a(hand);
            playerIn.field_70170_p.func_184134_a(playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, SoundsTC.craftstart, SoundCategory.PLAYERS, 0.15f, 1.0f, false);
        }
        else {
            int q = 0;
            final BlockPos p = playerIn.func_180425_c();
            for (int x = -4; x <= 4; ++x) {
                for (int z = -4; z <= 4; ++z) {
                    q += (int)AuraHelper.drainFlux(worldIn, p.func_177982_a(16 * x, 0, 16 * z), 500.0f, false);
                }
            }
            playerIn.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.GREEN + "" + q + " flux drained from 81 chunks."));
        }
        return (ActionResult<ItemStack>)super.func_77659_a(worldIn, playerIn, hand);
    }
}
