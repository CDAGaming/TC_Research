package net.tofweb.starlite;

import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class ThaumcraftBlockManager extends BlockManager
{
    World world;
    
    public ThaumcraftBlockManager(final CellSpace space, final World world) {
        super(space);
        this.world = world;
    }
    
    @Override
    public boolean isBlocked(final Cell cell) {
        final BlockPos pos = new BlockPos(cell.getX(), cell.getY(), cell.getZ());
        return !this.world.isAirBlock(pos);
    }
}
