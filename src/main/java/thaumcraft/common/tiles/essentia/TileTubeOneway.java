package thaumcraft.common.tiles.essentia;

import thaumcraft.api.aspects.*;

public class TileTubeOneway extends TileTube
{
    @Override
    void calculateSuction(final Aspect filter, final boolean restrict, final boolean directional) {
        super.calculateSuction(filter, restrict, true);
    }
    
    @Override
    void equalizeWithNeighbours(final boolean directional) {
        super.equalizeWithNeighbours(true);
    }
}
