package thaumcraft.codechicken.lib.render.uv;

import thaumcraft.codechicken.lib.math.*;
import java.math.*;
import thaumcraft.codechicken.lib.vec.*;

public class UVRotation extends UVTransformation
{
    public double angle;
    
    public UVRotation(final double angle) {
        this.angle = angle;
    }
    
    @Override
    public void apply(final UV uv) {
        final double c = MathHelper.cos(this.angle);
        final double s = MathHelper.sin(this.angle);
        final double u2 = c * uv.u + s * uv.v;
        uv.v = -s * uv.u + c * uv.v;
        uv.u = u2;
    }
    
    @Override
    public UVTransformation inverse() {
        return new UVRotation(-this.angle);
    }
    
    @Override
    public UVTransformation merge(final UVTransformation next) {
        if (next instanceof UVRotation) {
            return new UVRotation(this.angle + ((UVRotation)next).angle);
        }
        return null;
    }
    
    @Override
    public boolean isRedundant() {
        return MathHelper.between(-1.0E-5, this.angle, 1.0E-5);
    }
    
    @Override
    public String toString() {
        final MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "UVRotation(" + new BigDecimal(this.angle, cont) + ")";
    }
}
