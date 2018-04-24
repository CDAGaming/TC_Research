package thaumcraft.common.entities.construct.golem.parts;

import thaumcraft.api.golems.parts.*;
import thaumcraft.api.golems.*;
import thaumcraft.client.fx.*;

public class GolemLegLevitator implements GolemLeg.ILegFunction
{
    @Override
    public void onUpdateTick(final IGolemAPI golem) {
        if (golem.getGolemWorld().field_72995_K && (!golem.getGolemEntity().field_70122_E || golem.getGolemEntity().field_70173_aa % 5 == 0)) {
            FXDispatcher.INSTANCE.drawGolemFlyParticles(golem.getGolemEntity().field_70165_t, golem.getGolemEntity().field_70163_u + 0.1, golem.getGolemEntity().field_70161_v, golem.getGolemWorld().field_73012_v.nextGaussian() / 100.0, -0.1, golem.getGolemWorld().field_73012_v.nextGaussian() / 100.0);
        }
    }
}
