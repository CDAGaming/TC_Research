package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import thaumcraft.codechicken.lib.raytracer.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import thaumcraft.api.aspects.*;

public class ItemResonator extends ItemTCBase
{
    public ItemResonator() {
        super("resonator", new String[0]);
        this.func_77625_d(1);
        this.func_77625_d(1);
        this.func_77637_a(ConfigItems.TABTC);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_77636_d(final ItemStack stack1) {
        return stack1.func_77942_o();
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        final TileEntity tile = world.func_175625_s(pos);
        if (tile == null || !(tile instanceof IEssentiaTransport)) {
            return EnumActionResult.FAIL;
        }
        if (world.field_72995_K) {
            player.func_184609_a(hand);
            return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
        }
        final IEssentiaTransport et = (IEssentiaTransport)tile;
        final RayTraceResult hit = RayTracer.retraceBlock(world, player, pos);
        if (hit != null && hit.subHit >= 0 && hit.subHit < 6) {
            side = EnumFacing.field_82609_l[hit.subHit];
        }
        if (!(tile instanceof TileTubeBuffer) && et.getEssentiaType(side) != null) {
            player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.resonator1", new Object[] { "" + et.getEssentiaAmount(side), et.getEssentiaType(side).getName() }));
        }
        else if (tile instanceof TileTubeBuffer && ((IAspectContainer)tile).getAspects().size() > 0) {
            for (final Aspect aspect : ((IAspectContainer)tile).getAspects().getAspectsSortedByName()) {
                player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.resonator1", new Object[] { "" + ((IAspectContainer)tile).getAspects().getAmount(aspect), aspect.getName() }));
            }
        }
        String s = I18n.func_74838_a("tc.resonator3");
        if (et.getSuctionType(side) != null) {
            s = et.getSuctionType(side).getName();
        }
        player.func_145747_a((ITextComponent)new TextComponentTranslation("tc.resonator2", new Object[] { "" + et.getSuctionAmount(side), s }));
        world.func_184148_a((EntityPlayer)null, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), SoundEvents.field_187767_eL, SoundCategory.BLOCKS, 0.5f, 1.9f + world.field_73012_v.nextFloat() * 0.1f);
        return EnumActionResult.SUCCESS;
    }
}
