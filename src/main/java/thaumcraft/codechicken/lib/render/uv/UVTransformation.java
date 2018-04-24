package thaumcraft.codechicken.lib.render.uv;

import thaumcraft.codechicken.lib.vec.*;
import thaumcraft.codechicken.lib.render.*;

public abstract class UVTransformation extends ITransformation<UV, UVTransformation> implements CCRenderState.IVertexOperation
{
    public static final int operationIndex;
    
    @Override
    public UVTransformation at(final UV point) {
        return new UVTransformationList(new UVTransformation[] { new UVTranslation(-point.u, -point.v), this, new UVTranslation(point.u, point.v) });
    }
    
    @Override
    public UVTransformationList with(final UVTransformation t) {
        return new UVTransformationList(new UVTransformation[] { this, t });
    }
    
    @Override
    public boolean load() {
        return !this.isRedundant();
    }
    
    @Override
    public void operate() {
        ((ITransformation<UV, Transformation>)this).apply(CCRenderState.vert.uv);
    }
    
    @Override
    public int operationID() {
        return UVTransformation.operationIndex;
    }
    
    static {
        operationIndex = CCRenderState.registerOperation();
    }
}
