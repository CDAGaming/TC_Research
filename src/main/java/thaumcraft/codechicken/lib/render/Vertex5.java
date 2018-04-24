package thaumcraft.codechicken.lib.render;

import thaumcraft.codechicken.lib.util.*;
import java.math.*;
import thaumcraft.codechicken.lib.vec.*;
import thaumcraft.codechicken.lib.render.uv.*;

public class Vertex5 implements Copyable<Vertex5>
{
    public Vector3 vec;
    public UV uv;
    
    public Vertex5() {
        this(new Vector3(), new UV());
    }
    
    public Vertex5(final Vector3 vert, final UV uv) {
        this.vec = vert;
        this.uv = uv;
    }
    
    public Vertex5(final Vector3 vert, final double u, final double v) {
        this(vert, new UV(u, v));
    }
    
    public Vertex5(final double x, final double y, final double z, final double u, final double v) {
        this(new Vector3(x, y, z), new UV(u, v));
    }
    
    public Vertex5 set(final double x, final double y, final double z, final double u, final double v) {
        this.vec.set(x, y, z);
        this.uv.set(u, v);
        return this;
    }
    
    public Vertex5 set(final double x, final double y, final double z, final double u, final double v, final int tex) {
        this.vec.set(x, y, z);
        this.uv.set(u, v, tex);
        return this;
    }
    
    public Vertex5 set(final Vertex5 vert) {
        this.vec.set(vert.vec);
        this.uv.set(vert.uv);
        return this;
    }
    
    public Vertex5(final Vertex5 vertex5) {
        this(vertex5.vec.copy(), vertex5.uv.copy());
    }
    
    @Override
    public Vertex5 copy() {
        return new Vertex5(this);
    }
    
    @Override
    public String toString() {
        final MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "Vertex: (" + new BigDecimal(this.vec.x, cont) + ", " + new BigDecimal(this.vec.y, cont) + ", " + new BigDecimal(this.vec.z, cont) + ") (" + new BigDecimal(this.uv.u, cont) + ", " + new BigDecimal(this.uv.v, cont) + ")";
    }
    
    public Vertex5 apply(final Transformation t) {
        this.vec.apply(t);
        return this;
    }
    
    public Vertex5 apply(final UVTransformation t) {
        this.uv.apply(t);
        return this;
    }
}
