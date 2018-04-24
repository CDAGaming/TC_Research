package thaumcraft.common.entities.construct.golem.seals;

import thaumcraft.common.items.*;
import thaumcraft.api.golems.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.golems.seals.*;
import net.minecraft.world.*;

public class ItemSealPlacer extends ItemTCBase implements ISealDisplayer
{
    public ItemSealPlacer() {
        super("seal", new String[] { "blank" });
        this.func_77625_d(64);
        this.func_77627_a(true);
        this.func_77656_e(0);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final String[] vn = this.getVariantNames();
            for (int a = 0; a < vn.length; ++a) {
                items.add((Object)new ItemStack((Item)this, 1, a));
            }
        }
    }
    
    @Override
    public String[] getVariantNames() {
        if (SealHandler.types.size() + 1 != this.VARIANTS.length) {
            final String[] rs = SealHandler.getRegisteredSeals();
            final String[] out = new String[rs.length + 1];
            out[0] = "blank";
            int indx = 1;
            for (final String s : rs) {
                final String[] sp = s.split(":");
                out[indx] = ((sp.length > 1) ? sp[1] : sp[0]);
                ++indx;
            }
            this.VARIANTS = out;
            this.VARIANTS_META = new int[this.VARIANTS.length];
            for (int m = 0; m < this.VARIANTS.length; ++m) {
                this.VARIANTS_META[m] = m;
            }
        }
        return this.VARIANTS;
    }
    
    public static ItemStack getSealStack(final String sealKey) {
        final String[] rs = SealHandler.getRegisteredSeals();
        int indx = 1;
        for (final String s : rs) {
            if (s.equals(sealKey)) {
                return new ItemStack(ItemsTC.seals, 1, indx);
            }
            ++indx;
        }
        return null;
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        if (world.field_72995_K || player.func_184586_b(hand).func_77952_i() == 0 || player.func_70093_af()) {
            return EnumActionResult.PASS;
        }
        if (!player.func_175151_a(pos, side, player.func_184586_b(hand))) {
            return EnumActionResult.FAIL;
        }
        final String[] rs = SealHandler.getRegisteredSeals();
        ISeal seal = null;
        try {
            seal = (ISeal)SealHandler.getSeal(rs[player.func_184586_b(hand).func_77952_i() - 1]).getClass().newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (seal == null || !seal.canPlaceAt(world, pos, side)) {
            return EnumActionResult.FAIL;
        }
        if (SealHandler.addSealEntity(world, pos, side, seal, player) && !player.field_71075_bZ.field_75098_d) {
            player.func_184586_b(hand).func_190918_g(1);
        }
        return EnumActionResult.SUCCESS;
    }
    
    public boolean doesSneakBypassUse(final ItemStack stack, final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
}
