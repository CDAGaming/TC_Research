package thaumcraft.common.items.curios;

import thaumcraft.common.items.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import thaumcraft.common.lib.*;
import net.minecraft.util.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import thaumcraft.api.research.*;
import net.minecraft.util.math.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;

public class ItemPechWand extends ItemTCBase
{
    public ItemPechWand() {
        super("pech_wand", new String[0]);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add(I18n.func_74838_a("item.curio.text"));
    }
    
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer player, final EnumHand hand) {
        final IPlayerKnowledge knowledge = ThaumcraftCapabilities.getKnowledge(player);
        if (!knowledge.isResearchKnown("BASEAUROMANCY")) {
            if (!worldIn.field_72995_K) {
                player.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.RED + I18n.func_74838_a("not.pechwand")));
            }
            return (ActionResult<ItemStack>)super.func_77659_a(worldIn, player, hand);
        }
        if (!player.field_71075_bZ.field_75098_d) {
            player.func_184586_b(hand).func_190918_g(1);
        }
        worldIn.func_184148_a((EntityPlayer)null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundsTC.learn, SoundCategory.NEUTRAL, 0.5f, 0.4f / (ItemPechWand.field_77697_d.nextFloat() * 0.4f + 0.8f));
        if (!worldIn.field_72995_K) {
            if (!knowledge.isResearchKnown("FOCUSPECH")) {
                ThaumcraftApi.internalMethods.progressResearch(player, "FOCUSPECH");
                player.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.DARK_PURPLE + I18n.func_74838_a("got.pechwand")));
            }
            final int oProg = IPlayerKnowledge.EnumKnowledgeType.OBSERVATION.getProgression();
            final ResearchCategory[] rc = ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);
            ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[player.func_70681_au().nextInt(rc.length)], MathHelper.func_76136_a(player.func_70681_au(), oProg / 3, oProg / 2));
            final int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();
            ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[player.func_70681_au().nextInt(rc.length)], MathHelper.func_76136_a(player.func_70681_au(), tProg / 5, tProg / 4));
        }
        player.func_71029_a(StatList.func_188057_b((Item)this));
        return (ActionResult<ItemStack>)super.func_77659_a(worldIn, player, hand);
    }
}
