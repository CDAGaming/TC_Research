package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import thaumcraft.api.items.*;
import thaumcraft.api.aspects.*;
import net.minecraft.creativetab.*;
import thaumcraft.common.config.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.api.blocks.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import thaumcraft.common.tiles.essentia.*;
import net.minecraft.block.state.*;

public class ItemPhial extends ItemTCEssentiaContainer
{
    public ItemPhial() {
        super("phial", 10, new String[] { "empty", "filled" });
    }
    
    public static ItemStack makePhial(final Aspect aspect, final int amt) {
        final ItemStack i = new ItemStack(ItemsTC.phial, 1, 1);
        ((IEssentiaContainerItem)i.func_77973_b()).setAspects(i, new AspectList().add(aspect, amt));
        return i;
    }
    
    public static ItemStack makeFilledPhial(final Aspect aspect) {
        return makePhial(aspect, 10);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            items.add((Object)new ItemStack((Item)this, 1, 0));
            for (final Aspect tag : Aspect.aspects.values()) {
                final ItemStack i = new ItemStack((Item)this, 1, 1);
                this.setAspects(i, new AspectList().add(tag, this.base));
                items.add((Object)i);
            }
        }
    }
    
    public String func_77667_c(final ItemStack stack) {
        return super.func_77658_a() + "." + this.getVariantNames()[stack.func_77952_i()];
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int par4, final boolean par5) {
        if (!world.field_72995_K && !stack.func_77942_o() && stack.func_77952_i() == 1) {
            stack.func_77964_b(0);
        }
    }
    
    @Override
    public void func_77622_d(final ItemStack stack, final World world, final EntityPlayer player) {
        if (!world.field_72995_K && !stack.func_77942_o() && stack.func_77952_i() == 1) {
            stack.func_77964_b(0);
        }
    }
    
    public boolean doesSneakBypassUse(final ItemStack stack, final IBlockAccess world, final BlockPos pos, final EntityPlayer player) {
        return true;
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float f1, final float f2, final float f3, final EnumHand hand) {
        final IBlockState bi = world.func_180495_p(pos);
        if (player.func_184586_b(hand).func_77952_i() == 0 && bi.func_177230_c() == BlocksTC.alembic) {
            final TileAlembic tile = (TileAlembic)world.func_175625_s(pos);
            if (tile.amount >= this.base) {
                if (world.field_72995_K) {
                    player.func_184609_a(hand);
                    return EnumActionResult.PASS;
                }
                final ItemStack phial = new ItemStack((Item)this, 1, 1);
                this.setAspects(phial, new AspectList().add(tile.aspect, this.base));
                if (tile.takeFromContainer(tile.aspect, this.base)) {
                    player.func_184586_b(hand).func_190918_g(1);
                    if (!player.field_71071_by.func_70441_a(phial)) {
                        world.func_72838_d((Entity)new EntityItem(world, player.field_70165_t, player.field_70163_u, player.field_70161_v, phial));
                    }
                    player.func_184185_a(SoundEvents.field_187615_H, 0.25f, 1.0f);
                    player.field_71069_bz.func_75142_b();
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        if (player.func_184586_b(hand).func_77952_i() == 0 && (bi.func_177230_c() == BlocksTC.jarNormal || bi.func_177230_c() == BlocksTC.jarVoid)) {
            final TileJarFillable tile2 = (TileJarFillable)world.func_175625_s(pos);
            if (tile2.amount >= this.base) {
                if (world.field_72995_K) {
                    player.func_184609_a(hand);
                    return EnumActionResult.PASS;
                }
                final Aspect asp = Aspect.getAspect(tile2.aspect.getTag());
                if (tile2.takeFromContainer(asp, this.base)) {
                    player.func_184586_b(hand).func_190918_g(1);
                    final ItemStack phial2 = new ItemStack((Item)this, 1, 1);
                    this.setAspects(phial2, new AspectList().add(asp, this.base));
                    if (!player.field_71071_by.func_70441_a(phial2)) {
                        world.func_72838_d((Entity)new EntityItem(world, (double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f), phial2));
                    }
                    player.func_184185_a(SoundEvents.field_187615_H, 0.25f, 1.0f);
                    player.field_71069_bz.func_75142_b();
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        final AspectList al = this.getAspects(player.func_184586_b(hand));
        if (al != null && al.size() == 1) {
            final Aspect aspect = al.getAspects()[0];
            if (player.func_184586_b(hand).func_77952_i() != 0 && (bi.func_177230_c() == BlocksTC.jarNormal || bi.func_177230_c() == BlocksTC.jarVoid)) {
                final TileJarFillable tile3 = (TileJarFillable)world.func_175625_s(pos);
                if (tile3.amount <= 250 - this.base && tile3.doesContainerAccept(aspect)) {
                    if (world.field_72995_K) {
                        player.func_184609_a(hand);
                        return EnumActionResult.PASS;
                    }
                    if (tile3.addToContainer(aspect, this.base) == 0) {
                        world.markAndNotifyBlock(pos, world.func_175726_f(pos), bi, bi, 3);
                        tile3.func_70296_d();
                        player.func_184586_b(hand).func_190918_g(1);
                        if (!player.field_71071_by.func_70441_a(new ItemStack((Item)this, 1, 0))) {
                            world.func_72838_d((Entity)new EntityItem(world, (double)(pos.func_177958_n() + 0.5f), (double)(pos.func_177956_o() + 0.5f), (double)(pos.func_177952_p() + 0.5f), new ItemStack((Item)this, 1, 0)));
                        }
                        player.func_184185_a(SoundEvents.field_187615_H, 0.25f, 1.0f);
                        player.field_71069_bz.func_75142_b();
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }
}
