package thaumcraft.api.research;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class ScanBlock implements IScanThing
{
    String research;
    Block[] blocks;
    
    public ScanBlock(final Block block) {
        this("!" + block.getRegistryName().toString(), new Block[] { block });
    }
    
    public ScanBlock(final String research, final Block... blocks) {
        this.research = research;
        this.blocks = blocks;
        for (final Block block : blocks) {
            ScanningManager.addScannableThing(new ScanItem(research, new ItemStack(block)));
        }
    }
    
    @Override
    public boolean checkThing(final EntityPlayer player, final Object obj) {
        if (obj != null && obj instanceof BlockPos) {
            for (final Block block : this.blocks) {
                if (player.field_70170_p.func_180495_p((BlockPos)obj).func_177230_c() == block) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getResearchKey(final EntityPlayer player, final Object object) {
        return this.research;
    }
}
