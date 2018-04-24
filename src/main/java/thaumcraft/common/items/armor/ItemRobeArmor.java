package thaumcraft.common.items.armor;

import thaumcraft.common.items.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import thaumcraft.api.items.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemRobeArmor extends ItemArmor implements IVisDiscountGear, IThaumcraftItems
{
    public ItemRobeArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
        super(enumarmormaterial, j, k);
        this.setRegistryName(name);
        this.func_77655_b(name);
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
    
    public boolean func_82816_b_(final ItemStack stack1) {
        return true;
    }
    
    public int func_82814_b(final ItemStack stack1) {
        final NBTTagCompound nbttagcompound = stack1.func_77978_p();
        if (nbttagcompound == null) {
            return 6961280;
        }
        final NBTTagCompound nbttagcompound2 = nbttagcompound.func_74775_l("display");
        return (nbttagcompound2 == null) ? 6961280 : (nbttagcompound2.func_74764_b("color") ? nbttagcompound2.func_74762_e("color") : 6961280);
    }
    
    public void func_82815_c(final ItemStack stack1) {
        final NBTTagCompound nbttagcompound = stack1.func_77978_p();
        if (nbttagcompound != null) {
            final NBTTagCompound nbttagcompound2 = nbttagcompound.func_74775_l("display");
            if (nbttagcompound2.func_74764_b("color")) {
                nbttagcompound2.func_82580_o("color");
            }
        }
    }
    
    public void func_82813_b(final ItemStack stack1, final int par2) {
        NBTTagCompound nbttagcompound = stack1.func_77978_p();
        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            stack1.func_77982_d(nbttagcompound);
        }
        final NBTTagCompound nbttagcompound2 = nbttagcompound.func_74775_l("display");
        if (!nbttagcompound.func_74764_b("display")) {
            nbttagcompound.func_74782_a("display", (NBTBase)nbttagcompound2);
        }
        nbttagcompound2.func_74768_a("color", par2);
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        if (stack.func_77973_b() == ItemsTC.clothChest || stack.func_77973_b() == ItemsTC.clothBoots) {
            return (type == null) ? "thaumcraft:textures/entity/armor/robes_1.png" : "thaumcraft:textures/entity/armor/robes_1_overlay.png";
        }
        if (stack.func_77973_b() == ItemsTC.clothLegs) {
            return (type == null) ? "thaumcraft:textures/entity/armor/robes_2.png" : "thaumcraft:textures/entity/armor/robes_2_overlay.png";
        }
        return (type == null) ? "thaumcraft:textures/entity/armor/robes_1.png" : "thaumcraft:textures/entity/armor/robes_1_overlay.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.fabric)) || super.func_82789_a(stack1, stack2);
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        return (this.field_77881_a == EntityEquipmentSlot.FEET) ? 2 : 3;
    }
    
    public EnumActionResult onItemUseFirst(final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EnumHand hand) {
        final IBlockState bs = world.func_180495_p(pos);
        if (bs.func_177230_c() == Blocks.field_150383_bp) {
            final IBlockState blockState = bs;
            final BlockCauldron field_150383_bp = Blocks.field_150383_bp;
            final int i = (int)blockState.func_177229_b((IProperty)BlockCauldron.field_176591_a);
            if (!world.field_72995_K && i > 0) {
                this.func_82815_c(player.func_184586_b(hand));
                Blocks.field_150383_bp.func_176590_a(world, pos, bs, i - 1);
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }
}
