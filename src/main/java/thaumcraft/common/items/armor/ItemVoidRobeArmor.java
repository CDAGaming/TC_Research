package thaumcraft.common.items.armor;

import net.minecraftforge.common.*;
import thaumcraft.common.items.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import thaumcraft.api.items.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import thaumcraft.client.renderers.models.gear.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemVoidRobeArmor extends ItemArmor implements IVisDiscountGear, IGoggles, IRevealer, ISpecialArmor, IWarpingGear, IThaumcraftItems
{
    ModelBiped model1;
    ModelBiped model2;
    ModelBiped model;
    
    public ItemVoidRobeArmor(final String name, final ItemArmor.ArmorMaterial enumarmormaterial, final int j, final EntityEquipmentSlot k) {
        super(enumarmormaterial, j, k);
        this.model1 = null;
        this.model2 = null;
        this.model = null;
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName(name);
        this.func_77655_b(name);
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
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final EntityEquipmentSlot slot, final String type) {
        return (type == null) ? "thaumcraft:textures/entity/armor/void_robe_armor_overlay.png" : "thaumcraft:textures/entity/armor/void_robe_armor.png";
    }
    
    public EnumRarity func_77613_e(final ItemStack itemstack) {
        return EnumRarity.EPIC;
    }
    
    public boolean func_82789_a(final ItemStack stack1, final ItemStack stack2) {
        return stack2.func_77969_a(new ItemStack(ItemsTC.ingots, 1, 1)) || super.func_82789_a(stack1, stack2);
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
        if (!world.field_72995_K && stack.func_77951_h() && entity.field_70173_aa % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.func_77972_a(-1, (EntityLivingBase)entity);
        }
    }
    
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack armor) {
        super.onArmorTick(world, player, armor);
        if (!world.field_72995_K && armor.func_77952_i() > 0 && player.field_70173_aa % 20 == 0) {
            armor.func_77972_a(-1, (EntityLivingBase)player);
        }
    }
    
    public boolean showNodes(final ItemStack itemstack, final EntityLivingBase player) {
        final EntityEquipmentSlot type = ((ItemArmor)itemstack.func_77973_b()).field_77881_a;
        return type == EntityEquipmentSlot.HEAD;
    }
    
    public boolean showIngamePopups(final ItemStack itemstack, final EntityLivingBase player) {
        final EntityEquipmentSlot type = ((ItemArmor)itemstack.func_77973_b()).field_77881_a;
        return type == EntityEquipmentSlot.HEAD;
    }
    
    public int getVisDiscount(final ItemStack stack, final EntityPlayer player) {
        return 5;
    }
    
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(final EntityLivingBase entityLiving, final ItemStack itemStack, final EntityEquipmentSlot armorSlot, final ModelBiped _default) {
        if (this.model1 == null) {
            this.model1 = new ModelRobe(1.0f);
        }
        if (this.model2 == null) {
            this.model2 = new ModelRobe(0.5f);
        }
        return this.model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, this.model, this.model1, this.model2);
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
    
    public ISpecialArmor.ArmorProperties getProperties(final EntityLivingBase player, final ItemStack armor, final DamageSource source, final double damage, final int slot) {
        int priority = 0;
        double ratio = this.field_77879_b / 25.0;
        if (source.func_82725_o()) {
            priority = 1;
            ratio = this.field_77879_b / 35.0;
        }
        else if (source.func_76363_c()) {
            priority = 0;
            ratio = 0.0;
        }
        return new ISpecialArmor.ArmorProperties(priority, ratio, armor.func_77958_k() + 1 - armor.func_77952_i());
    }
    
    public int getArmorDisplay(final EntityPlayer player, final ItemStack armor, final int slot) {
        return this.field_77879_b;
    }
    
    public void damageArmor(final EntityLivingBase entity, final ItemStack stack, final DamageSource source, final int damage, final int slot) {
        if (source != DamageSource.field_76379_h) {
            stack.func_77972_a(damage, entity);
        }
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
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 3;
    }
}
