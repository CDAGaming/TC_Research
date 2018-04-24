package thaumcraft.common.entities.construct.golem;

import thaumcraft.common.items.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import thaumcraft.api.golems.parts.*;
import net.minecraft.world.*;
import net.minecraft.client.util.*;
import thaumcraft.api.golems.*;
import net.minecraft.util.text.translation.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class ItemGolemPlacer extends ItemTCBase implements ISealDisplayer
{
    public ItemGolemPlacer() {
        super("golem", new String[0]);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack is = new ItemStack((Item)this, 1, 0);
            is.func_77983_a("props", (NBTBase)new NBTTagLong(0L));
            items.add((Object)is.func_77946_l());
            IGolemProperties props = new GolemProperties();
            props.setHead(GolemHead.getHeads()[1]);
            props.setArms(GolemArm.getArms()[1]);
            is.func_77983_a("props", (NBTBase)new NBTTagLong(props.toLong()));
            items.add((Object)is.func_77946_l());
            props = new GolemProperties();
            props.setMaterial(GolemMaterial.getMaterials()[1]);
            props.setHead(GolemHead.getHeads()[1]);
            props.setArms(GolemArm.getArms()[2]);
            is.func_77983_a("props", (NBTBase)new NBTTagLong(props.toLong()));
            items.add((Object)is.func_77946_l());
            props = new GolemProperties();
            props.setMaterial(GolemMaterial.getMaterials()[4]);
            props.setHead(GolemHead.getHeads()[1]);
            props.setArms(GolemArm.getArms()[3]);
            is.func_77983_a("props", (NBTBase)new NBTTagLong(props.toLong()));
            items.add((Object)is.func_77946_l());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("props")) {
            final IGolemProperties props = GolemProperties.fromLong(stack.func_77978_p().func_74763_f("props"));
            if (props.hasTrait(EnumGolemTrait.SMART)) {
                if (props.getRank() >= 10) {
                    tooltip.add("§6" + I18n.func_74838_a("golem.rank") + " " + props.getRank());
                }
                else {
                    final int rx = stack.func_77978_p().func_74762_e("xp");
                    final int xn = (props.getRank() + 1) * (props.getRank() + 1) * 1000;
                    tooltip.add("§6" + I18n.func_74838_a("golem.rank") + " " + props.getRank() + " §2(" + rx + "/" + xn + ")");
                }
            }
            tooltip.add("§a" + props.getMaterial().getLocalizedName());
            for (final EnumGolemTrait tag : props.getTraits()) {
                tooltip.add("§9-" + tag.getLocalizedName());
            }
        }
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        IBlockState bs = world.func_180495_p(pos);
        if (!bs.func_185904_a().func_76220_a()) {
            return EnumActionResult.FAIL;
        }
        if (world.field_72995_K) {
            return EnumActionResult.PASS;
        }
        pos = pos.func_177972_a(side);
        bs = world.func_180495_p(pos);
        if (!player.func_175151_a(pos, side, player.func_184586_b(hand))) {
            return EnumActionResult.FAIL;
        }
        final EntityThaumcraftGolem golem = new EntityThaumcraftGolem(world);
        golem.func_70080_a(pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), pos.func_177952_p() + 0.5, 0.0f, 0.0f);
        if (golem != null && world.func_72838_d((Entity)golem)) {
            golem.setOwned(true);
            golem.setValidSpawn();
            golem.setOwnerId(player.func_110124_au());
            if (player.func_184586_b(hand).func_77942_o() && player.func_184586_b(hand).func_77978_p().func_74764_b("props")) {
                golem.setProperties(GolemProperties.fromLong(player.func_184586_b(hand).func_77978_p().func_74763_f("props")));
            }
            if (player.func_184586_b(hand).func_77942_o() && player.func_184586_b(hand).func_77978_p().func_74764_b("xp")) {
                golem.rankXp = player.func_184586_b(hand).func_77978_p().func_74762_e("xp");
            }
            golem.func_180482_a(world.func_175649_E(pos), null);
            if (!player.field_71075_bZ.field_75098_d) {
                player.func_184586_b(hand).func_190918_g(1);
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
