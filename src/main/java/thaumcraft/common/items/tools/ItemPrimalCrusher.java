package thaumcraft.common.items.tools;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import thaumcraft.common.lib.enchantment.*;
import net.minecraftforge.common.util.*;
import thaumcraft.api.items.*;
import net.minecraft.init.*;
import thaumcraft.api.blocks.*;
import com.google.common.collect.*;

public class ItemPrimalCrusher extends ItemTool implements IWarpingGear, IThaumcraftItems
{
    public static Item.ToolMaterial material;
    private static final Set isEffective;
    
    public ItemPrimalCrusher() {
        super(3.5f, -2.8f, ItemPrimalCrusher.material, ItemPrimalCrusher.isEffective);
        this.func_77637_a(ConfigItems.TABTC);
        this.setRegistryName("primal_crusher");
        this.func_77655_b("primal_crusher");
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
    
    public boolean func_150897_b(final IBlockState p_150897_1_) {
        return p_150897_1_.func_185904_a() != Material.field_151575_d && p_150897_1_.func_185904_a() != Material.field_151584_j && p_150897_1_.func_185904_a() != Material.field_151585_k;
    }
    
    public float func_150893_a(final ItemStack stack, final IBlockState state) {
        return (state.func_185904_a() != Material.field_151573_f && state.func_185904_a() != Material.field_151574_g && state.func_185904_a() != Material.field_151576_e) ? super.func_150893_a(stack, state) : this.field_77864_a;
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        return (Set<String>)ImmutableSet.of((Object)"shovel", (Object)"pickaxe");
    }
    
    private boolean isEffectiveAgainst(final Block block) {
        for (final Object b : ItemPrimalCrusher.isEffective) {
            if (b == block) {
                return true;
            }
        }
        return false;
    }
    
    public int func_77619_b() {
        return 20;
    }
    
    public int getWarp(final ItemStack itemstack, final EntityPlayer player) {
        return 2;
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
        if (stack.func_77951_h() && entity != null && entity.field_70173_aa % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.func_77972_a(-1, (EntityLivingBase)entity);
        }
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
        if (tab == ConfigItems.TABTC) {
            final ItemStack w1 = new ItemStack((Item)this);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.DESTRUCTIVE, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.REFINING, 1);
            items.add((Object)w1);
        }
    }
    
    static {
        ItemPrimalCrusher.material = EnumHelper.addToolMaterial("PRIMALVOID", 5, 500, 8.0f, 4.0f, 20).setRepairItem(new ItemStack(ItemsTC.ingots, 1, 1));
        isEffective = Sets.newHashSet((Object[])new Block[] { Blocks.field_150347_e, Blocks.field_150334_T, Blocks.field_150333_U, Blocks.field_150348_b, Blocks.field_150322_A, Blocks.field_150341_Y, Blocks.field_150366_p, Blocks.field_150339_S, Blocks.field_150365_q, Blocks.field_150340_R, Blocks.field_150352_o, Blocks.field_150482_ag, Blocks.field_150484_ah, Blocks.field_150432_aD, Blocks.field_150424_aL, Blocks.field_150369_x, Blocks.field_150368_y, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150448_aq, Blocks.field_150319_E, Blocks.field_150318_D, Blocks.field_150408_cc, Blocks.field_150349_c, Blocks.field_150346_d, Blocks.field_150354_m, Blocks.field_150351_n, Blocks.field_150431_aC, Blocks.field_150433_aE, Blocks.field_150435_aG, Blocks.field_150458_ak, Blocks.field_150425_aM, Blocks.field_150391_bh, BlocksTC.taintCrust, BlocksTC.taintRock, BlocksTC.taintSoil, BlocksTC.taintFeature, BlocksTC.taintLog, BlocksTC.taintFibre, Blocks.field_150343_Z });
    }
}
