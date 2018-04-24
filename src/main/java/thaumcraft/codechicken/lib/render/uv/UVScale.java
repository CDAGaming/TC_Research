package thaumcraft.codechicken.lib.render.uv;

import java.math.*;
import thaumcraft.codechicken.lib.vec.*;

public class UVScale extends UVTransformation
{
    double su;
    double sv;
    
    public UVScale(final double scaleu, final double scalev) {
        this.su = scaleu;
        this.sv = scalev;
    }
    
    public UVScale(final double d) {
        this(d, d);
    }
    
    @Override
    public void apply(final UV uv) {
        uv.u *= this.su;
        uv.v *= this.sv;
    }
    
    @Override
    public UVTransformation inverse() {
        return new UVScale(1.0 / this.su, 1.0 / this.sv);
    }
    
    @Override
    public String toString() {
        final MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "UVScale(" + new BigDecimal(this.su, cont) + ", " + new BigDecimal(this.sv, cont) + ")";
    }
}
