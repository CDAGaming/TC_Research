package thaumcraft.client.lib.obj;

import net.minecraftforge.fml.relauncher.*;

public interface IModelCustom
{
    String getType();
    
    @SideOnly(Side.CLIENT)
    void renderAll();
    
    @SideOnly(Side.CLIENT)
    void renderOnly(final String... p0);
    
    @SideOnly(Side.CLIENT)
    void renderPart(final String p0);
    
    @SideOnly(Side.CLIENT)
    void renderAllExcept(final String... p0);
    
    @SideOnly(Side.CLIENT)
    String[] getPartNames();
}
