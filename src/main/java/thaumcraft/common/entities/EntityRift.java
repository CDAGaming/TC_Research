package thaumcraft.common.entities;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import thaumcraft.api.casters.*;
import thaumcraft.api.aspects.*;
import net.minecraft.nbt.*;
import thaumcraft.common.entities.monster.*;
import net.minecraft.network.datasync.*;

public class EntityRift extends Entity
{
    private static final DataParameter<String> TYPE;
    
    public EntityRift(final World par1World) {
        super(par1World);
    }
    
    public EntityRift(final FocusPackage pack, final double x, final double y, final double z, final Aspect a) {
        super(pack.world);
        this.func_70107_b(x, y, z);
        this.func_70105_a(3.0f, 3.0f);
        this.setType(a.getTag());
    }
    
    protected void func_70088_a() {
        this.func_184212_Q().func_187214_a((DataParameter)EntityRift.TYPE, (Object)String.valueOf(""));
    }
    
    public String getType() {
        return (String)this.func_184212_Q().func_187225_a((DataParameter)EntityRift.TYPE);
    }
    
    public void setType(final String t) {
        this.func_184212_Q().func_187227_b((DataParameter)EntityRift.TYPE, (Object)String.valueOf(t));
    }
    
    public void func_70014_b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.func_74778_a("Type", this.getType());
    }
    
    public void func_70037_a(final NBTTagCompound nbttagcompound) {
        this.setType(nbttagcompound.func_74779_i("Type"));
    }
    
    public void func_70071_h_() {
        super.func_70071_h_();
    }
    
    static {
        TYPE = EntityDataManager.func_187226_a((Class)EntityWisp.class, DataSerializers.field_187194_d);
    }
}
