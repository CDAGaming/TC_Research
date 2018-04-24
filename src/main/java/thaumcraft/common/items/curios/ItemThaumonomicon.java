package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.relauncher.*;
import thaumcraft.api.research.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.common.lib.research.*;
import net.minecraft.entity.player.*;
import thaumcraft.common.lib.*;
import thaumcraft.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;

public class ItemThaumonomicon extends ItemTCBase
{
    public ItemThaumonomicon() {
        super("thaumonomicon", new String[] { "normal", "cheat" });
        this.func_77627_a(true);
        this.func_77625_d(1);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            items.add((Object)new ItemStack((Item)this, 1, 0));
            if (ModConfig.CONFIG_MISC.allowCheatSheet) {
                items.add((Object)new ItemStack((Item)this, 1, 1));
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        if (stack.func_77952_i() == 1) {
            tooltip.add(TextFormatting.DARK_PURPLE + "Creative only");
        }
    }
    
    public ActionResult<ItemStack> func_77659_a(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.field_72995_K) {
            if (ModConfig.CONFIG_MISC.allowCheatSheet && player.func_184586_b(hand).func_77952_i() == 1) {
                final Collection<ResearchCategory> rc = ResearchCategories.researchCategories.values();
                for (final ResearchCategory cat : rc) {
                    final Collection<ResearchEntry> rl = cat.research.values();
                    for (final ResearchEntry ri : rl) {
                        CommandThaumcraft.giveRecursiveResearch(player, ri.getKey());
                    }
                }
            }
            else {
                final Collection<ResearchCategory> rc = ResearchCategories.researchCategories.values();
                for (final ResearchCategory cat : rc) {
                    final Collection<ResearchEntry> rl = cat.research.values();
                    for (final ResearchEntry ri : rl) {
                        if (ThaumcraftCapabilities.knowsResearch(player, ri.getKey()) && ri.getSiblings() != null) {
                            for (final String sib : ri.getSiblings()) {
                                if (!ThaumcraftCapabilities.knowsResearch(player, sib)) {
                                    ResearchManager.completeResearch(player, sib);
                                }
                            }
                        }
                    }
                }
            }
            ThaumcraftCapabilities.getKnowledge(player).sync((EntityPlayerMP)player);
        }
        else {
            world.func_184134_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundsTC.page, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        }
        player.openGui((Object)Thaumcraft.instance, 12, world, 0, 0, 0);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)player.func_184586_b(hand));
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return (itemstack.func_77952_i() != 1) ? EnumRarity.UNCOMMON : EnumRarity.EPIC;
    }
}
