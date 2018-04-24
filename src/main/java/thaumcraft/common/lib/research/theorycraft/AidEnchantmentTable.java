package thaumcraft.common.lib.research.theorycraft;

import net.minecraft.init.*;
import net.minecraft.item.*;
import thaumcraft.api.research.theorycraft.*;

public class AidEnchantmentTable implements ITheorycraftAid
{
    @Override
    public Object getAidObject() {
        return new ItemStack(Blocks.field_150381_bn);
    }
    
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return (Class<TheorycraftCard>[])new Class[] { CardEnchantment.class };
    }
}
