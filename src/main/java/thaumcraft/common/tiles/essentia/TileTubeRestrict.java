package thaumcraft.common.tiles.essentia;

import thaumcraft.api.aspects.*;

public class TileTubeRestrict extends TileTube
{
    @Override
    void calculateSuction(final Aspect filter, final boolean restrict, final boolean dir) {
        super.calculateSuction(filter, true, dir);
    }
}
