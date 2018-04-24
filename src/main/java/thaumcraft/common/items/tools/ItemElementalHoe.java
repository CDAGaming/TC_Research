package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import thaumcraft.client.fx.*;
import thaumcraft.common.lib.utils.*;
import net.minecraft.entity.*;

public class ItemElementalHoe extends ItemHoe implements IThaumcraftItems
{
    public ItemElementalHoe(final Item.ToolMaterial enumtoolmaterial) {
        super(enumtoolmaterial);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("elemental_hoe");
        this.func_77655_b("elemental_hoe");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public Item getItem() {
        return (Item)this;
    }
    
    public String[] getVariantNames() {
        return new String[] { "normal" };
    }
    
    public int[] getVariantMeta() {
        return new int[] { 0 };
    }
    
    public ItemMeshDefinition getCustomMesh() {
        return null;
    }
    
    public ModelResourceLocation getCustomModelResourceLocation(final String variant) {
        return new ModelResourceLocation("thaumcraft:" + variant);
    }
    
    public int func_77619_b() {
        return 5;
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 0)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumActionResult func_180614_a(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (player.func_70093_af()) {
            return super.func_180614_a(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }
        boolean did = false;
        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                if (super.func_180614_a(player, world, pos.func_177982_a(xx, 0, zz), hand, facing, hitX, hitY, hitZ) == EnumActionResult.SUCCESS) {
                    if (world.field_72995_K) {
                        final BlockPos pp = pos.func_177982_a(xx, 0, zz);
                        FXDispatcher.INSTANCE.drawBamf(pp.func_177958_n() + 0.5, pp.func_177956_o() + 1.01, pp.func_177952_p() + 0.5, 0.3f, 0.12f, 0.1f, xx == 0 && zz == 0, false, EnumFacing.UP);
                    }
                    if (!did) {
                        did = true;
                    }
                }
            }
        }
        if (!did) {
            did = Utils.useBonemealAtLoc(world, player, pos);
            if (did) {
                player.func_184586_b(hand).func_77972_a(3, (EntityLivingBase)player);
                if (!world.field_72995_K) {
                    world.func_175669_a(2005, pos, 0);
                }
                else {
                    FXDispatcher.INSTANCE.drawBlockMistParticles(pos, 4259648);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
