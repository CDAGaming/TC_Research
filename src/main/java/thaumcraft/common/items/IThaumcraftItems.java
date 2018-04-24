package thaumcraft.common.items;

import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public interface IThaumcraftItems
{
    Item getItem();
    
    String[] getVariantNames();
    
    int[] getVariantMeta();
    
    ItemMeshDefinition getCustomMesh();
    
    ModelResourceLocation getCustomModelResourceLocation(final String p0);
}
