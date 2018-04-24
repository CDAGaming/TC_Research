package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import thaumcraft.common.config.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import thaumcraft.api.capabilities.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public class ItemZombieBrain extends ItemFood implements IThaumcraftItems
{
    public ItemZombieBrain() {
        super(4, 0.2f, true);
        this.func_185070_a(new PotionEffect(MobEffects.field_76438_s, 30, 0), 0.8f);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("brain");
        this.func_77655_b("brain");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
    }
    
    public void func_77849_c(final ItemStack stack, final World world, final EntityPlayer player) {
        if (!world.field_72995_K) {
            if (world.field_73012_v.nextFloat() < 0.1f) {
                ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.NORMAL);
            }
            else {
                ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1 + world.field_73012_v.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);
            }
        }
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
}
