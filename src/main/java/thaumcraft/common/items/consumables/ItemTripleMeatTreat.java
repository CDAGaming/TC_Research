package thaumcraft.common.items.consumables;

import thaumcraft.common.items.*;
import thaumcraft.common.config.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public class ItemTripleMeatTreat extends ItemFood implements IThaumcraftItems
{
    public ItemTripleMeatTreat() {
        super(6, 0.8f, true);
        this.func_77848_i();
        this.setRegistryName("triple_meat_treat");
        this.func_77655_b("triple_meat_treat");
        this.func_77637_a(ConfigItems.TABTC);
        this.func_185070_a(new PotionEffect(MobEffects.field_76428_l, 100, 0), 0.66f);
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
}
