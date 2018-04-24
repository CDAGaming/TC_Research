package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import thaumcraft.api.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.items.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import thaumcraft.common.lib.events.*;

public class ItemBootsTraveller extends ItemArmor implements IThaumcraftItems, IRechargable
{
    public ItemBootsTraveller() {
        super(ThaumcraftMaterials.ARMORMAT_SPECIAL, 4, EntityEquipmentSlot.FEET);
        this.func_77656_e(350);
        this.setRegistryName("traveller_boots");
        this.func_77655_b("traveller_boots");
        ConfigItems.ITEM_VARIANT_HOLDERS.add(this);
        this.func_77637_a(ConfigItems.TABTC);
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
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        return "thaumcraft:textures/entity/armor/bootstraveler.png";
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(Items.field_151116_aA)) || super.func_82789_a(stack1, stack2);
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.RARE;
    }
    
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
        final boolean hasCharge = RechargeHelper.getCharge(itemStack) > 0;
        if (!world.field_72995_K && player.field_70173_aa % 20 == 0) {
            int e = 0;
            if (itemStack.func_77942_o()) {
                e = itemStack.func_77978_p().func_74762_e("energy");
            }
            if (e > 0) {
                --e;
            }
            else if (e <= 0 && RechargeHelper.consumeCharge(itemStack, (EntityLivingBase)player, 1)) {
                e = 60;
            }
            itemStack.func_77983_a("energy", (NBTBase)new NBTTagInt(e));
        }
        if (hasCharge && !player.field_71075_bZ.field_75100_b && player.field_191988_bg > 0.0f) {
            if (player.field_70170_p.field_72995_K && !player.func_70093_af()) {
                if (!PlayerEvents.prevStep.containsKey(player.func_145782_y())) {
                    PlayerEvents.prevStep.put(player.func_145782_y(), player.field_70138_W);
                }
                player.field_70138_W = 1.0f;
            }
            if (player.field_70122_E) {
                float bonus = 0.05f;
                if (player.func_70090_H()) {
                    bonus /= 4.0f;
                }
                player.func_191958_b(0.0f, 0.0f, bonus, 1.0f);
            }
            else {
                if (player.func_70090_H()) {
                    player.func_191958_b(0.0f, 0.0f, 0.025f, 1.0f);
                }
                player.field_70747_aH = 0.05f;
            }
        }
        if (player.field_70143_R > 0.0f) {
            player.field_70143_R -= 0.25f;
        }
    }
    
    public int getMaxCharge(final ItemStack stack, final EntityLivingBase player) {
        return 240;
    }
    
    public EnumChargeDisplay showInHud(final ItemStack stack, final EntityLivingBase player) {
        return EnumChargeDisplay.PERIODIC;
    }
}
