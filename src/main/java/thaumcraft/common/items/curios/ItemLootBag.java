package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;

public class ItemLootBag extends ItemTCBase
{
    public ItemLootBag() {
        super("loot_bag", new String[] { "common", "uncommon", "rare" });
        this.func_77625_d(16);
    }
    
    public EnumRarity func_77613_e(final ItemStack stack) {
        switch (stack.func_77952_i()) {
            case 1: {
                return EnumRarity.UNCOMMON;
            }
            case 2: {
                return EnumRarity.RARE;
            }
            default: {
                return EnumRarity.COMMON;
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        tooltip.add(I18n.func_74838_a("tc.lootbag"));
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.field_72995_K) {
            for (int q = 8 + world.field_73012_v.nextInt(5), a = 0; a < q; ++a) {
                final ItemStack is = Utils.generateLoot(player.func_184586_b(hand).func_77952_i(), world.field_73012_v);
                if (is != null && !is.func_190926_b()) {
                    world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, is.func_77946_l()));
                }
            }
            player.func_184185_a(SoundsTC.coins, 0.75f, 1.0f);
        }
        player.func_184586_b(hand).func_190918_g(1);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
}
