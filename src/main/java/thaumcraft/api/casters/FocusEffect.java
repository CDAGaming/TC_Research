package thaumcraft.api.casters;

import net.minecraft.util.math.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public abstract class FocusEffect extends FocusNode
{
    @Override
    public IFocusElement.EnumUnitType getType() {
        return IFocusElement.EnumUnitType.EFFECT;
    }
    
    @Override
    public final EnumSupplyType[] mustBeSupplied() {
        return new EnumSupplyType[] { EnumSupplyType.TARGET };
    }
    
    @Override
    public EnumSupplyType[] willSupply() {
        return null;
    }
    
    public abstract boolean execute(final RayTraceResult p0, @Nullable final Trajectory p1, final float p2, final int p3);
    
    public float getDamageForDisplay(final float finalPower) {
        return 0.0f;
    }
    
    public abstract void renderParticleFX(final World p0, final double p1, final double p2, final double p3, final double p4, final double p5, final double p6);
    
    public void onCast(final Entity caster) {
    }
}
