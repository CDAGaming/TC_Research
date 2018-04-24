package thaumcraft.codechicken.lib.vec;

import net.minecraftforge.fml.relauncher.*;

public abstract class VariableTransformation extends Transformation
{
    public Matrix4 mat;
    
    public VariableTransformation(final Matrix4 mat) {
        this.mat = mat;
    }
    
    @Override
    public void applyN(final Vector3 normal) {
        ((ITransformation<Vector3, Transformation>)this).apply(normal);
    }
    
    @Override
    public void apply(final Matrix4 mat) {
        mat.multiply(this.mat);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void glApply() {
        this.mat.glApply();
    }
}
